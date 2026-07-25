-- ============================================================================
-- cappo-emu content migration from Orion (CometDB.sql) -> cappo schema
-- ----------------------------------------------------------------------------
-- PREREQUISITES (run as root, which has access to both databases):
--   1. cappo schema loaded:            mysql ... cappo   < cappo_schema.sql
--   2. Orion dump loaded into orion_comet: mysql ... orion_comet < CometDB.sql
--      (Orion's CometDB.sql has a leading UTF-8 BOM and some mojibake rows that
--       exceed column width; load with strict mode OFF and the BOM stripped:
--       { echo "SET SESSION sql_mode=''"; tail -c +4 CometDB.sql; } | mariadb
--         --default-character-set=latin1 orion_comet)
--   3. then this script:               mysql ... cappo   < cappo_migrate_from_orion.sql
--
-- This is a BEST-EFFORT migration. Orion (Comet-derived) encodes furni data for
-- a different server/client revision than cappo-emu (Capostrike93,
-- RELEASE63-201307031203-797872414). The server-side tables are populated so
-- the emulator boots with content (catalog pages/items, furniture defs, room
-- models, songs, navigator categories), but client rendering fidelity is not
-- guaranteed (sprite_ids/interaction keys target Orion's client, not cappo's).
--
-- Conversions applied:
--   * furniture.type  enum('s','i','e','r','h','v') -> int 1/2/3/4/5/1
--     (cappo BaseItem.java line 228 maps 1->s 2->i 3->e 4->r 5->h)
--   * enum('0','1') / enum('1','0') columns -> int 0/1 via IF(col='1',1,0)
--     (NOT CAST(... AS UNSIGNED), which returns the enum's ORDINAL INDEX, and
--      NOT col+0, which also returns the index. Both are wrong here.)
--   * catalog_pages.page_images/page_texts JSON arrays -> separate
--     page_headline/teaser/special/text1/text2/text_details via JSON_EXTRACT
--   * catalog_items.cost_snow -> catalog_items_copy.cost_crystal
--
-- NOT migrated (incompatible structure / empty source):
--   * wordfilter        (Orion: string 'replacement'; cappo: int 'action' index)
--   * navigator_official (Orion navigator_publics is empty)
--   * ltd_items         (Orion items_limited_edition has no ltd_start/ltd_end)
--   * landing_news / poll_* / room_poll / rights_manager (no clean Orion source)
-- ============================================================================

USE cappo;

-- Clear any prior migration run so this script is idempotent.
DELETE FROM `room_models`;
DELETE FROM `songs`;
DELETE FROM `furnis_base`;
DELETE FROM `catalog_items_copy`;
DELETE FROM `catalog_pages`;
DELETE FROM `navigator_flatcats`;

-- ---------- room_models (clean mapping) ----------
INSERT INTO `room_models` (`id`, `door_x`, `door_y`, `door_z`, `door_dir`, `heightmap`, `club_only`)
SELECT
  `id`, `door_x`, `door_y`, `door_z`, `door_dir`, `heightmap`,
  IF(`club_only` = '1', 1, 0)
FROM `orion_comet`.`room_models`;

-- ---------- songs  (Orion furniture_music -> cappo songs, clean) ----------
INSERT INTO `songs` (`id`, `name`, `song_data`, `length`, `artist`)
SELECT `id`, `name`, `song_data`, `length`, `artist`
FROM `orion_comet`.`furniture_music`;

-- ---------- furnis_base (Orion furniture -> cappo furnis_base, type enum->int) ----------
INSERT INTO `furnis_base` (
  `baseid`, `id`, `classname`, `type`, `xdim`, `ydim`, `height`,
  `canstandon`, `canlayon`, `cansiton`, `allow_stack`, `specialtype`,
  `allow_recycle`, `allow_trade`, `allow_marketplace_sell`, `allow_gift`,
  `allow_inventory_stack`, `vending_ids`, `furni_logic`, `extradata_type`,
  `cycle_count`, `interaction_type`)
SELECT
  `id`,                                                -- baseid
  `sprite_id`,                                         -- id (sprite id)
  `item_name`,                                         -- classname
  CASE `type`
    WHEN 's' THEN 1 WHEN 'i' THEN 2 WHEN 'e' THEN 3
    WHEN 'r' THEN 4 WHEN 'h' THEN 5 ELSE 1
  END,                                                 -- type int (cappo encoding)
  `width`,                                             -- xdim
  `length`,                                            -- ydim
  CAST(`stack_height` AS DECIMAL(10,2)),               -- height (varchar -> number)
  IF(`is_walkable` = '1', 1, 0),                       -- canstandon (walkable ~= standon)
  IF(`canlayon` = '1', 1, 0),                          -- canlayon
  IF(`can_sit` = '1', 1, 0),                           -- cansiton
  IF(`can_stack` = '1', 1, 0),                         -- allow_stack
  `specialtype`,                                       -- specialtype
  IF(`allow_recycle` = '1', 1, 0),
  IF(`allow_trade` = '1', 1, 0),
  IF(`allow_marketplace_sell` = '1', 1, 0),
  IF(`allow_gift` = '1', 1, 0),
  IF(`allow_inventory_stack` = '1', 1, 0),
  `vending_ids`,                                       -- vending_ids
  `interaction_type`,                                  -- furni_logic (best-effort)
  0,                                                   -- extradata_type (no Orion source)
  `interaction_modes_count`,                           -- cycle_count
  `interaction_type`                                   -- interaction_type (best-effort)
FROM `orion_comet`.`furniture`;

-- ---------- catalog_items_copy (Orion catalog_items -> cappo catalog_items_copy) ----------
INSERT INTO `catalog_items_copy` (
  `id`, `page_id`, `catalog_name`, `cost_credits`, `cost_pixels`, `cost_crystal`,
  `item_ids`, `amount`, `extra_param`, `extra_data`, `ltd_id`, `limited_stack`,
  `limited_sells`)
SELECT
  `id`, `page_id`, `catalog_name`, `cost_credits`, `cost_pixels`, `cost_snow`,  -- cost_crystal <- cost_snow
  -- cappo splits item_ids on ';' and parseInt()s each token (Catalog.java:241-244).
  -- Orion plain products store a single furniture id; bundle products store
  -- "id:amt:extra,id:amt:extra,...". Strip the ":<digits>" suffixes and turn the
  -- comma separator into ';' so cappo gets a semicolon-list of furniture ids.
  REPLACE(REGEXP_REPLACE(`item_ids`, ':[0-9]+', ''), ',', ';'),
  `amount`,
  0,                          -- extra_param (no direct source)
  `extradata`,                -- extra_data
  0,                          -- ltd_id (non-limited; Orion's limited model differs)
  `limited_stack`, `limited_sells`
FROM `orion_comet`.`catalog_items`;

-- ---------- catalog_pages (Orion -> cappo; JSON arrays -> separate columns) ----------
INSERT INTO `catalog_pages` (
  `id`, `parent_id`, `caption`, `min_rank`, `club_only`, `icon_color`, `icon_image`,
  `page_layout`, `page_headline`, `page_teaser`, `page_special`,
  `page_text1`, `page_text2`, `page_text_details`, `order_num`, `enabled`, `visible`)
SELECT
  `id`, `parent_id`, `caption`, `min_rank`,
  IF(`club_only` = '1', '1', '0'),                     -- cappo reads getString().equals("1")
  `icon_color`, `icon_image`,
  `page_layout`,                                       -- passed through (some names won't match
                                                       -- cappo's switch; falls to default layout)
  JSON_UNQUOTE(JSON_EXTRACT(`page_images`, '$[0]')),   -- page_headline
  JSON_UNQUOTE(JSON_EXTRACT(`page_images`, '$[1]')),   -- page_teaser
  JSON_UNQUOTE(JSON_EXTRACT(`page_images`, '$[2]')),   -- page_special
  JSON_UNQUOTE(JSON_EXTRACT(`page_texts`,  '$[0]')),   -- page_text1
  JSON_UNQUOTE(JSON_EXTRACT(`page_texts`,  '$[1]')),   -- page_text2
  JSON_UNQUOTE(JSON_EXTRACT(`page_texts`,  '$[2]')),   -- page_text_details
  `order_num`,
  IF(`enabled` = '1', '1', '0'),
  IF(`visible` = '1', '1', '0')
FROM `orion_comet`.`catalog_pages`;

-- ---------- navigator_flatcats (Orion navigator_categories -> cappo navigator_flatcats) ----------
INSERT INTO `navigator_flatcats` (`id`, `caption`, `min_rank`, `enabled`)
SELECT `id`, `public_name`, `required_rank`, IF(`enabled` = '1', '1', '0')
FROM `orion_comet`.`navigator_categories`;