-- ============================================================================
-- cappo-emu reverse-engineered MySQL schema
-- ----------------------------------------------------------------------------
-- Generated from the SQL embedded in cappo-emu's decompiled Java (112 distinct
-- statements across 44 tables). Column types are pinned by the ResultSet
-- getter calls that follow each query (getInt/getString/getFloat/getShort/
-- getLong/getBytes). No DDL ships with cappo-emu; this is the inferred skeleton.
--
-- Engine: InnoDB, charset: utf8mb4. No FOREIGN KEY constraints (the code uses
-- string-concatenated JOINs and deletes in arbitrary order; FKs would break
-- runtime deletes). Plain indexes on join columns only.
--
-- Spelling is preserved EXACTLY as the queries use it (e.g. user_pets.expirience
-- (sic), furnis.baseid/roomid/userid, room_discs.roomid). Renaming breaks boot.
-- ============================================================================

SET FOREIGN_KEY_CHECKS = 0;
SET NAMES utf8mb4;

-- ---------- Player / account tables ----------

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `real_name` VARCHAR(255) NULL,
  `mail` VARCHAR(255) NULL,
  `rank` INT NOT NULL DEFAULT 1,
  `look` TEXT NULL,
  `gender` CHAR(1) NULL,
  `motto` TEXT NULL,
  `account_created` VARCHAR(40) NULL,
  `achievement_points` INT NOT NULL DEFAULT 0,
  `credits` INT NOT NULL DEFAULT 0,
  `crystals` INT NOT NULL DEFAULT 0,
  `activity_points` INT NOT NULL DEFAULT 0,
  `activity_points_lastupdate` BIGINT NULL,
  `vip_points` INT NOT NULL DEFAULT 0,
  `home_room` INT NOT NULL DEFAULT 0,
  `respects` INT NOT NULL DEFAULT 0,
  `daily_respect_points` INT NOT NULL DEFAULT 0,
  `daily_pet_respect_points` INT NOT NULL DEFAULT 0,
  `newbie_status` INT NOT NULL DEFAULT 0,
  `block_newfriends` INT NOT NULL DEFAULT 0,
  `block_trade` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `user_id` INT NOT NULL,
  `login_timestamp` INT NULL,
  `bans` INT NULL,
  `cautions` INT NULL,
  `cfhs` INT NULL,
  `cfhs_abusive` INT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `user_badges`;
CREATE TABLE `user_badges` (
  `id` INT NOT NULL,
  `badge_id` VARCHAR(64) NOT NULL,
  `badge_slot` INT NOT NULL DEFAULT 0,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `user_wardrobe`;
CREATE TABLE `user_wardrobe` (
  `slot_id` INT NOT NULL,
  `look` TEXT NULL,
  `gender` CHAR(1) NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `slot_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `user_friends`;
CREATE TABLE `user_friends` (
  `user_id` INT NOT NULL,
  `friend_id` INT NOT NULL,
  `type` VARCHAR(8) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`, `friend_id`),
  KEY `idx_friend` (`friend_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `user_friendreqs`;
CREATE TABLE `user_friendreqs` (
  `user_id` INT NOT NULL,
  `friend_id` INT NOT NULL,
  `friend_name` VARCHAR(255) NULL,
  PRIMARY KEY (`user_id`, `friend_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `user_ignores`;
CREATE TABLE `user_ignores` (
  `user_id` INT NOT NULL,
  `ignore_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `ignore_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `user_favorites`;
CREATE TABLE `user_favorites` (
  `room_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`room_id`, `user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `user_bots`;
CREATE TABLE `user_bots` (
  `id` INT NOT NULL,
  `type` INT NOT NULL DEFAULT 0,
  `name` VARCHAR(255) NULL,
  `look` TEXT NULL,
  `gender` CHAR(1) NULL,
  `motto` TEXT NULL,
  `user_id` INT NOT NULL DEFAULT 0,
  `room_id` INT NOT NULL DEFAULT 0,
  `x` INT NULL,
  `y` INT NULL,
  `z` INT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user` (`user_id`),
  KEY `idx_room` (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `user_pets`;
CREATE TABLE `user_pets` (
  `id` INT NOT NULL,
  `type` INT NOT NULL DEFAULT 0,
  `name` VARCHAR(255) NULL,
  `race` VARCHAR(8) NULL,
  `color` TEXT NULL,
  `createstamp` INT NULL,
  `nutrition` INT NULL,
  `expirience` INT NULL,                -- sic: preserved exactly as in cappo SQL
  `energy` INT NULL,
  `respect` INT NULL,
  `user_id` INT NOT NULL DEFAULT 0,
  `room_id` INT NOT NULL DEFAULT 0,
  `x` INT NULL,
  `y` INT NULL,
  `z` INT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user` (`user_id`),
  KEY `idx_room` (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------- Moderation / server-status tables ----------

DROP TABLE IF EXISTS `bans`;
CREATE TABLE `bans` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `type` VARCHAR(16) NOT NULL,
  `reason` TEXT NULL,
  `text` TEXT NULL,
  `hours` INT NOT NULL DEFAULT 0,
  `created` BIGINT NOT NULL DEFAULT 0,
  `mod_id` INT NOT NULL DEFAULT 0,
  `issue_id` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `server_status`;
CREATE TABLE `server_status` (
  `id` INT NOT NULL,
  `users_online` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `stats_online`;
CREATE TABLE `stats_online` (
  `time` BIGINT NOT NULL,
  `data` INT NOT NULL,
  PRIMARY KEY (`time`, `data`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------- Room tables ----------

DROP TABLE IF EXISTS `room_models`;
CREATE TABLE `room_models` (
  `id` VARCHAR(32) NOT NULL,
  `door_x` INT NOT NULL DEFAULT 0,
  `door_y` INT NOT NULL DEFAULT 0,
  `door_z` FLOAT NOT NULL DEFAULT 0,
  `door_dir` INT NOT NULL DEFAULT 2,
  `heightmap` MEDIUMTEXT NULL,
  `club_only` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `room_custom_models`;
CREATE TABLE `room_custom_models` (
  `id` INT NOT NULL,
  `base` VARCHAR(32) NULL,
  `heightmap` MEDIUMTEXT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `rooms`;
CREATE TABLE `rooms` (
  `id` INT NOT NULL,
  `model_name` VARCHAR(32) NOT NULL,
  `caption` VARCHAR(255) NULL,
  `user_id` INT NULL,
  `user_name` VARCHAR(255) NULL,
  `description` TEXT NULL,
  `category` INT NOT NULL DEFAULT 0,
  `score` INT NOT NULL DEFAULT 0,
  `tags` VARCHAR(255) NULL,
  `icon_bg` INT NOT NULL DEFAULT 0,
  `icon_fg` INT NOT NULL DEFAULT 0,
  `icon_items` VARCHAR(255) NULL,
  `password` VARCHAR(64) NULL,
  `wallpaper` VARCHAR(16) NULL,
  `floor` VARCHAR(16) NULL,
  `landscape` VARCHAR(16) NULL,
  `allow_pets` CHAR(1) NOT NULL DEFAULT '0',
  `allow_pets_eat` CHAR(1) NOT NULL DEFAULT '0',
  `allow_walkthrough` CHAR(1) NOT NULL DEFAULT '1',
  `allow_hidewall` CHAR(1) NOT NULL DEFAULT '0',
  `wallthickness` SMALLINT NULL DEFAULT 0,
  `floorthickness` SMALLINT NULL DEFAULT 0,
  `staff_pickup` CHAR(1) NOT NULL DEFAULT '0',
  `public_ccts` VARCHAR(255) NULL,
  `state` INT NOT NULL DEFAULT 0,
  `users_max` INT NOT NULL DEFAULT 25,
  `settings_mod` INT NOT NULL DEFAULT 0,
  `settings_trd` INT NOT NULL DEFAULT 0,
  `settings_chat` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `idx_user` (`user_id`),
  KEY `idx_category` (`category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `room_discs`;
CREATE TABLE `room_discs` (
  `roomid` INT NOT NULL,
  `songid` INT NOT NULL,
  `itemid` INT NOT NULL,
  PRIMARY KEY (`roomid`, `itemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `room_rights`;
CREATE TABLE `room_rights` (
  `room_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`room_id`, `user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `room_poll`;
CREATE TABLE `room_poll` (
  `poll` INT NOT NULL,
  `roomid` INT NOT NULL,
  PRIMARY KEY (`poll`, `roomid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------- Furni / item tables ----------
-- Two parallel inventory schemas: "new" (furnis*) and "old/bfly" (items*).
-- Both sets must exist even if empty, or first room/inventory load throws
-- "table doesn't exist".

DROP TABLE IF EXISTS `furnis_base`;
CREATE TABLE `furnis_base` (
  `baseid` INT NOT NULL,
  `id` INT NOT NULL DEFAULT 0,           -- sprite id
  `classname` VARCHAR(255) NOT NULL,
  `type` INT NOT NULL DEFAULT 1,          -- 1=s 2=i 3=e 4=r 5=h (see BaseItem.java)
  `xdim` INT NOT NULL DEFAULT 1,
  `ydim` INT NOT NULL DEFAULT 1,
  `height` FLOAT NULL DEFAULT 0,
  `canstandon` INT NOT NULL DEFAULT 0,
  `canlayon` INT NOT NULL DEFAULT 0,
  `cansiton` INT NOT NULL DEFAULT 0,
  `allow_stack` INT NOT NULL DEFAULT 1,
  `specialtype` INT NOT NULL DEFAULT 0,
  `allow_recycle` INT NOT NULL DEFAULT 0,
  `allow_trade` INT NOT NULL DEFAULT 1,
  `allow_marketplace_sell` INT NOT NULL DEFAULT 0,
  `allow_gift` INT NOT NULL DEFAULT 1,
  `allow_inventory_stack` INT NOT NULL DEFAULT 1,
  `vending_ids` VARCHAR(255) NULL,
  `furni_logic` VARCHAR(64) NULL,
  `extradata_type` INT NOT NULL DEFAULT 0,
  `cycle_count` INT NOT NULL DEFAULT 0,
  `interaction_type` VARCHAR(64) NULL,
  PRIMARY KEY (`baseid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `furnis`;
CREATE TABLE `furnis` (
  `id` INT NOT NULL,
  `userid` INT NOT NULL DEFAULT 0,
  `baseid` INT NOT NULL DEFAULT 0,
  `roomid` INT NOT NULL DEFAULT 0,
  `data` LONGBLOB NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user` (`userid`),
  KEY `idx_room` (`roomid`),
  KEY `idx_base` (`baseid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `furnis_roomdata`;
CREATE TABLE `furnis_roomdata` (
  `id` INT NOT NULL,
  `a` FLOAT NOT NULL DEFAULT 0,
  `b` FLOAT NOT NULL DEFAULT 0,
  `r` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `furnis_floorextra`;
CREATE TABLE `furnis_floorextra` (
  `id` INT NOT NULL,
  `param` INT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `items`;
CREATE TABLE `items` (
  `item_id` INT NOT NULL,
  `base_id` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `items_rooms`;
CREATE TABLE `items_rooms` (
  `item_id` INT NOT NULL,
  `room_id` INT NOT NULL DEFAULT 0,
  `x` FLOAT NULL,
  `y` FLOAT NULL,
  `n` INT NULL,
  PRIMARY KEY (`item_id`),
  KEY `idx_room` (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `items_users`;
CREATE TABLE `items_users` (
  `item_id` INT NOT NULL,
  `user_id` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`item_id`),
  KEY `idx_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `items_extradata`;
CREATE TABLE `items_extradata` (
  `item_id` INT NOT NULL,
  `data` LONGBLOB NULL,                 -- getString (old) and getBytes (new) both work
  `extra_param` INT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `items_moodlight`;
CREATE TABLE `items_moodlight` (
  `item_id` INT NOT NULL,
  `enabled` INT NOT NULL DEFAULT 0,
  `current_preset` INT NOT NULL DEFAULT 0,
  `preset_one` VARCHAR(64) NULL,
  `preset_two` VARCHAR(64) NULL,
  `preset_three` VARCHAR(64) NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `items_tele_links`;
CREATE TABLE `items_tele_links` (
  `tele_one_id` INT NOT NULL,
  `tele_two_id` INT NOT NULL,
  PRIMARY KEY (`tele_one_id`, `tele_two_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------- Wired / trigger tables ----------

DROP TABLE IF EXISTS `trigger_item`;
CREATE TABLE `trigger_item` (
  `trigger_id` INT NOT NULL,
  `trigger_data` TEXT NULL,
  PRIMARY KEY (`trigger_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `trigger_in_place`;
CREATE TABLE `trigger_in_place` (
  `original_trigger` INT NOT NULL,
  `triggers_item` INT NOT NULL,
  PRIMARY KEY (`original_trigger`, `triggers_item`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `trigger_rotation`;
CREATE TABLE `trigger_rotation` (
  `item_id` INT NOT NULL,
  `movement_status` INT NOT NULL DEFAULT 0,
  `rotation_status` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------- Catalog tables ----------

DROP TABLE IF EXISTS `catalog_pages`;
CREATE TABLE `catalog_pages` (
  `id` INT NOT NULL,
  `parent_id` INT NOT NULL DEFAULT 0,
  `caption` VARCHAR(255) NOT NULL,
  `min_rank` INT NOT NULL DEFAULT 0,
  `club_only` CHAR(1) NOT NULL DEFAULT '0',
  `icon_color` INT NOT NULL DEFAULT 0,
  `icon_image` INT NOT NULL DEFAULT 0,
  `page_layout` VARCHAR(64) NOT NULL DEFAULT 'default_3x3',
  `page_headline` VARCHAR(255) NULL,
  `page_teaser` VARCHAR(255) NULL,
  `page_special` VARCHAR(255) NULL,
  `page_text1` TEXT NULL,
  `page_text2` TEXT NULL,
  `page_text_details` TEXT NULL,
  `order_num` INT NOT NULL DEFAULT 0,
  `enabled` CHAR(1) NOT NULL DEFAULT '1',
  `visible` CHAR(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `catalog_items_copy`;
CREATE TABLE `catalog_items_copy` (
  `id` INT NOT NULL,
  `page_id` INT NOT NULL DEFAULT 0,
  `catalog_name` VARCHAR(255) NOT NULL,
  `cost_credits` INT NOT NULL DEFAULT 0,
  `cost_pixels` INT NOT NULL DEFAULT 0,
  `cost_crystal` INT NOT NULL DEFAULT 0,
  `item_ids` TEXT NULL,
  `amount` INT NOT NULL DEFAULT 0,
  `extra_param` INT NULL,
  `extra_data` TEXT NULL,
  `ltd_id` INT NOT NULL DEFAULT 0,
  `limited_stack` INT NOT NULL DEFAULT 0,
  `limited_sells` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `idx_page` (`page_id`),
  KEY `idx_ltd` (`ltd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `ltd_items`;
CREATE TABLE `ltd_items` (
  `id` INT NOT NULL,
  `ltd_start` BIGINT NOT NULL DEFAULT 0,
  `ltd_end` BIGINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------- Navigator / landing / polls / misc reference tables ----------

DROP TABLE IF EXISTS `navigator_flatcats`;
CREATE TABLE `navigator_flatcats` (
  `id` INT NOT NULL,
  `caption` VARCHAR(255) NOT NULL,
  `min_rank` INT NOT NULL DEFAULT 0,
  `enabled` CHAR(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `navigator_official`;
CREATE TABLE `navigator_official` (
  `id` INT NOT NULL,
  `caption` VARCHAR(255) NOT NULL,
  `desc` VARCHAR(255) NULL,
  `show_details` INT NOT NULL DEFAULT 0,
  `image` VARCHAR(255) NULL,
  `parent_id` INT NOT NULL DEFAULT 0,
  `type` INT NOT NULL DEFAULT 0,
  `order_id` INT NOT NULL DEFAULT 0,
  `enabled` CHAR(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `landing_news`;
CREATE TABLE `landing_news` (
  `id` INT NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `text` TEXT NULL,
  `button` VARCHAR(255) NULL,
  `image` VARCHAR(255) NULL,
  `is_link` INT NOT NULL DEFAULT 0,
  `link` VARCHAR(255) NULL,
  `action` VARCHAR(64) NULL,
  `extra` VARCHAR(255) NULL,
  `enabled` CHAR(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `poll_data`;
CREATE TABLE `poll_data` (
  `id` INT NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `thanks` TEXT NULL,
  `active` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `poll_questions`;
CREATE TABLE `poll_questions` (
  `id` INT NOT NULL,
  `poll` INT NOT NULL DEFAULT 0,
  `type` INT NOT NULL DEFAULT 0,
  `question` TEXT NOT NULL,
  `answers` TEXT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `poll_answers`;
CREATE TABLE `poll_answers` (
  `userid` INT NOT NULL,
  `poll` INT NOT NULL,
  `question` INT NOT NULL,
  `answer` TEXT NULL,
  PRIMARY KEY (`userid`, `poll`, `question`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `rights_manager`;
CREATE TABLE `rights_manager` (
  `id` INT NOT NULL,                     -- rank
  `allow_roomalert` INT NOT NULL DEFAULT 0,
  `allow_pick` INT NOT NULL DEFAULT 0,
  `allow_eject` INT NOT NULL DEFAULT 0,
  `allow_roomcontrol` INT NOT NULL DEFAULT 0,
  `allow_modtools` INT NOT NULL DEFAULT 0,
  `allow_ban` INT NOT NULL DEFAULT 0,
  `allow_givebadge` INT NOT NULL DEFAULT 0,
  `allow_ha` INT NOT NULL DEFAULT 0,
  `allow_givemoney` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `wordfilter`;
CREATE TABLE `wordfilter` (
  `word` VARCHAR(255) NOT NULL,
  `action` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`word`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `songs`;
CREATE TABLE `songs` (
  `id` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `song_data` TEXT NOT NULL,
  `length` INT NOT NULL DEFAULT 0,
  `artist` VARCHAR(255) NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- Seed data (mandatory for clean boot / smoke test)
-- ============================================================================

-- server_status: the OnlineCounter UPDATEs this singleton row; without it the
-- UPDATE is a 0-row no-op (no error, but status never reflects reality).
INSERT INTO `server_status` (`id`, `users_online`) VALUES (1, 0);

-- A test account. There is no registration path in cappo-emu (the code only ever
-- SELECTs users; it never INSERTs), so a seeded row is the only way to log in.
INSERT INTO `users` (`id`, `username`, `real_name`, `mail`, `rank`, `look`, `gender`,
  `motto`, `account_created`, `achievement_points`, `credits`, `crystals`,
  `activity_points`, `activity_points_lastupdate`, `vip_points`, `home_room`,
  `respects`, `daily_respect_points`, `daily_pet_respect_points`, `newbie_status`,
  `block_newfriends`, `block_trade`)
VALUES (1, 'admin', 'Admin', 'admin@local', 7,
  'hr-100-.hd-180-1.ch-210-66.lg-270-82.sh-290-1408', 'M', 'Admin',
  '2026-07-25 00:00:00', 0, 10000, 1000, 1000, 0, 100, 0, 10, 5, 5, 0, 0, 0);

INSERT INTO `user_info` (`user_id`, `login_timestamp`, `bans`, `cautions`, `cfhs`, `cfhs_abusive`)
VALUES (1, 0, 0, 0, 0, 0);

SET FOREIGN_KEY_CHECKS = 1;