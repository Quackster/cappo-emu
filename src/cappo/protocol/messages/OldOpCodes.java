package cappo.protocol.messages;

import cappo.protocol.messages.events.HorseMountUpdateParser;
import cappo.protocol.messages.events.PutHorseSaddleParser;
import cappo.protocol.messages.events.RemoveHorseSaddleParser;
import cappo.protocol.messages.events.RidingPermissionParser;
import cappo.protocol.messages.events.advertisement.GetInterstitialParser;
import cappo.protocol.messages.events.avatar.ChangeUserNameParser;
import cappo.protocol.messages.events.avatar.CheckUserNameParser;
import cappo.protocol.messages.events.avatar.GetWardrobeParser;
import cappo.protocol.messages.events.avatar.SaveWardrobeOutfitParser;
import cappo.protocol.messages.events.catalog.GetBundleDynamicDiscountsParser;
import cappo.protocol.messages.events.catalog.GetCatalogIndexParser;
import cappo.protocol.messages.events.catalog.GetCatalogPageParser;
import cappo.protocol.messages.events.catalog.GetClubOffersParser;
import cappo.protocol.messages.events.catalog.GetGiftWrappingConfigurationParser;
import cappo.protocol.messages.events.catalog.GetHabboClubExtendOfferParser;
import cappo.protocol.messages.events.catalog.GetIsOfferGiftableParser;
import cappo.protocol.messages.events.catalog.GetSellablePetBreedsParser;
import cappo.protocol.messages.events.catalog.GetSnowWarTokensParser;
import cappo.protocol.messages.events.catalog.GetUniqueLimitedItemParser;
import cappo.protocol.messages.events.catalog.PurchaseFromCatalogParser;
import cappo.protocol.messages.events.friendlist.AcceptFriendParser;
import cappo.protocol.messages.events.friendlist.DeclineFriendParser;
import cappo.protocol.messages.events.friendlist.FollowFriendParser;
import cappo.protocol.messages.events.friendlist.FriendListUpdateParser;
import cappo.protocol.messages.events.friendlist.GetBuddyRequestsParser;
import cappo.protocol.messages.events.friendlist.HabboSearchParser;
import cappo.protocol.messages.events.friendlist.MessengerInitParser;
import cappo.protocol.messages.events.friendlist.RemoveFriendParser;
import cappo.protocol.messages.events.friendlist.RequestBuddyParser;
import cappo.protocol.messages.events.friendlist.SendMsgParser;
import cappo.protocol.messages.events.friendlist.SendRoomInviteParser;
import cappo.protocol.messages.events.friendlist.SetRelationshipStatusParser;
import cappo.protocol.messages.events.games.gamecenter.GetGameAchievementsParser;
import cappo.protocol.messages.events.games.gamecenter.GetGameListParser;
import cappo.protocol.messages.events.games.gamecenter.GetStatusGameParser;
import cappo.protocol.messages.events.games.gamecenter.JoinPlayerQueueParser;
import cappo.protocol.messages.events.games.snowwar.CheckGameDirectoryStatusParser;
import cappo.protocol.messages.events.games.snowwar.ExitGameParser;
import cappo.protocol.messages.events.games.snowwar.GameChatParser;
import cappo.protocol.messages.events.games.snowwar.GetAccountGameStatusParser;
import cappo.protocol.messages.events.games.snowwar.LeaveGameParser;
import cappo.protocol.messages.events.games.snowwar.LoadStageReadyParser;
import cappo.protocol.messages.events.games.snowwar.MakeSnowballParser;
import cappo.protocol.messages.events.games.snowwar.PlayAgainParser;
import cappo.protocol.messages.events.games.snowwar.QuickJoinGameParser;
import cappo.protocol.messages.events.games.snowwar.RequestFullStatusUpdateParser;
import cappo.protocol.messages.events.games.snowwar.SetUserMoveTargetParser;
import cappo.protocol.messages.events.games.snowwar.ThrowSnowballAtHumanParser;
import cappo.protocol.messages.events.games.snowwar.ThrowSnowballAtPositionParser;
import cappo.protocol.messages.events.guides.SetDutyGuideToolParser;
import cappo.protocol.messages.events.handshake.DisconnectParser;
import cappo.protocol.messages.events.handshake.GenerateSecretKeyParser;
import cappo.protocol.messages.events.handshake.InfoRetrieveParser;
import cappo.protocol.messages.events.handshake.InitCryptoParser;
import cappo.protocol.messages.events.handshake.PongParser;
import cappo.protocol.messages.events.handshake.SSOTicketParser;
import cappo.protocol.messages.events.handshake.UniqueIDParser;
import cappo.protocol.messages.events.handshake.VersionCheckParser;
import cappo.protocol.messages.events.help.CallForHelp2Parser;
import cappo.protocol.messages.events.help.CallForHelpInRoomParser;
import cappo.protocol.messages.events.help.CallForHelpOpenParser;
import cappo.protocol.messages.events.help.CallForHelpParser;
import cappo.protocol.messages.events.help.CallForHelpRoomPanicParser;
import cappo.protocol.messages.events.help.CallForHelpRoomParser;
import cappo.protocol.messages.events.inventory.achievements.GetAchievementsParser;
import cappo.protocol.messages.events.inventory.avatareffect.AvatarEffectActivatedParser;
import cappo.protocol.messages.events.inventory.avatareffect.AvatarEffectSelectedParser;
import cappo.protocol.messages.events.inventory.badges.GetBadgePointLimitsParser;
import cappo.protocol.messages.events.inventory.badges.GetBadgesParser;
import cappo.protocol.messages.events.inventory.badges.SetActivatedBadgesParser;
import cappo.protocol.messages.events.inventory.bots.RequestBotInventoryParser;
import cappo.protocol.messages.events.inventory.furni.RequestFurniInventoryParser;
import cappo.protocol.messages.events.inventory.furni.RequestRoomPropertySetParser;
import cappo.protocol.messages.events.inventory.pets.RequestPetInventoryParser;
import cappo.protocol.messages.events.inventory.purse.GetCreditsInfoParser;
import cappo.protocol.messages.events.inventory.trading.AcceptTradingParser;
import cappo.protocol.messages.events.inventory.trading.AddItemToTradeParser;
import cappo.protocol.messages.events.inventory.trading.CloseTradingParser;
import cappo.protocol.messages.events.inventory.trading.ConfirmAcceptTradingParser;
import cappo.protocol.messages.events.inventory.trading.ConfirmDeclineTradingParser;
import cappo.protocol.messages.events.inventory.trading.OpenTradingParser;
import cappo.protocol.messages.events.inventory.trading.RemoveItemFromTradeParser;
import cappo.protocol.messages.events.inventory.trading.UnacceptTradingParser;
import cappo.protocol.messages.events.landing.GetLandingNewsParser;
import cappo.protocol.messages.events.landing.GetLandingView6Parser;
import cappo.protocol.messages.events.landing.GetNextLimitedAvailableParser;
import cappo.protocol.messages.events.landing.RefreshLandingViewParser;
import cappo.protocol.messages.events.marketplace.GetMarketplaceCanMakeOfferParser;
import cappo.protocol.messages.events.marketplace.GetMarketplaceConfigurationParser;
import cappo.protocol.messages.events.moderator.CloseIssuesParser;
import cappo.protocol.messages.events.moderator.GetModeratorRoomInfoParser;
import cappo.protocol.messages.events.moderator.GetModeratorUserInfoParser;
import cappo.protocol.messages.events.moderator.ModBanParser;
import cappo.protocol.messages.events.moderator.ModKickParser;
import cappo.protocol.messages.events.moderator.ModMessageParser;
import cappo.protocol.messages.events.moderator.ModMuteParser;
import cappo.protocol.messages.events.moderator.ModerateRoomParser;
import cappo.protocol.messages.events.moderator.ModeratorActionParser;
import cappo.protocol.messages.events.moderator.ModeratorRoomActionParser;
import cappo.protocol.messages.events.moderator.PickIssuesParser;
import cappo.protocol.messages.events.moderator.ReleaseIssuesParser;
import cappo.protocol.messages.events.navigator.AddFavouriteRoomParser;
import cappo.protocol.messages.events.navigator.CanCreateRoomParser;
import cappo.protocol.messages.events.navigator.CreateFlatParser;
import cappo.protocol.messages.events.navigator.DeleteFavouriteRoomParser;
import cappo.protocol.messages.events.navigator.EditEventParser;
import cappo.protocol.messages.events.navigator.GetGuestRoomParser;
import cappo.protocol.messages.events.navigator.GetOfficialRoomsParser;
import cappo.protocol.messages.events.navigator.GetPopularRoomTagsParser;
import cappo.protocol.messages.events.navigator.GetUserFlatCatsParser;
import cappo.protocol.messages.events.navigator.LatestEventsSearchParser;
import cappo.protocol.messages.events.navigator.MyFavouriteRoomsSearchParser;
import cappo.protocol.messages.events.navigator.MyFriendsRoomsSearchParser;
import cappo.protocol.messages.events.navigator.MyRoomHistorySearchParser;
import cappo.protocol.messages.events.navigator.MyRoomsSearchParser;
import cappo.protocol.messages.events.navigator.PopularRoomsSearchParser;
import cappo.protocol.messages.events.navigator.RateFlatParser;
import cappo.protocol.messages.events.navigator.RoomTagSearchParser;
import cappo.protocol.messages.events.navigator.RoomTextSearchParser;
import cappo.protocol.messages.events.navigator.RoomsWhereMyFriendsAreSearchParser;
import cappo.protocol.messages.events.navigator.RoomsWithHighestScoreSearchParser;
import cappo.protocol.messages.events.navigator.ToggleStaffPickParser;
import cappo.protocol.messages.events.navigator.UpdateNavigatorSettingsParser;
import cappo.protocol.messages.events.notifications.ResetUnseenItemsParser;
import cappo.protocol.messages.events.poll.PollAnswerParser;
import cappo.protocol.messages.events.poll.PollRejectParser;
import cappo.protocol.messages.events.poll.PollStartParser;
import cappo.protocol.messages.events.quest.FriendRequestQuestCompleteParser;
import cappo.protocol.messages.events.recycler.GetRecyclerPrizesParser;
import cappo.protocol.messages.events.register.UpdateFigureDataParser;
import cappo.protocol.messages.events.room.action.AssignRightsParser;
import cappo.protocol.messages.events.room.action.BanUserParser;
import cappo.protocol.messages.events.room.action.DropCarryObjectParser;
import cappo.protocol.messages.events.room.action.KickUserParser;
import cappo.protocol.messages.events.room.action.LetUserInParser;
import cappo.protocol.messages.events.room.action.RemoveAllRightsParser;
import cappo.protocol.messages.events.room.action.RemoveRightsParser;
import cappo.protocol.messages.events.room.action.ShareCarryObjectParser;
import cappo.protocol.messages.events.room.avatar.ChangeMottoParser;
import cappo.protocol.messages.events.room.avatar.ChangePostureParser;
import cappo.protocol.messages.events.room.avatar.DanceParser;
import cappo.protocol.messages.events.room.avatar.LookToParser;
import cappo.protocol.messages.events.room.avatar.SetAvatarExpressionParser;
import cappo.protocol.messages.events.room.avatar.SignParser;
import cappo.protocol.messages.events.room.bots.RequestBotSkillParser;
import cappo.protocol.messages.events.room.bots.SetBotSkillParser;
import cappo.protocol.messages.events.room.chat.CancelTypingParser;
import cappo.protocol.messages.events.room.chat.ChatParser;
import cappo.protocol.messages.events.room.chat.ShoutParser;
import cappo.protocol.messages.events.room.chat.StartTypingParser;
import cappo.protocol.messages.events.room.chat.WhisperParser;
import cappo.protocol.messages.events.room.engine.GetPetCommandsParser;
import cappo.protocol.messages.events.room.engine.GetRoomCampaignAdsParser;
import cappo.protocol.messages.events.room.engine.GetRoomCompetitionParser;
import cappo.protocol.messages.events.room.engine.GetRoomEntryDataParser;
import cappo.protocol.messages.events.room.engine.MoveAvatarParser;
import cappo.protocol.messages.events.room.engine.MoveObjectParser;
import cappo.protocol.messages.events.room.engine.MoveWallItemParser;
import cappo.protocol.messages.events.room.engine.ObjectSaveStuffDataParser;
import cappo.protocol.messages.events.room.engine.PickupObjectParser;
import cappo.protocol.messages.events.room.engine.PlaceObjectParser;
import cappo.protocol.messages.events.room.engine.PlacePetParser;
import cappo.protocol.messages.events.room.engine.PlaceRentalBotParser;
import cappo.protocol.messages.events.room.engine.RemoveBotFromFlatParser;
import cappo.protocol.messages.events.room.engine.RemovePetFromFlatParser;
import cappo.protocol.messages.events.room.engine.SetClothingChangeDataParser;
import cappo.protocol.messages.events.room.engine.UseFurnitureParser;
import cappo.protocol.messages.events.room.engine.UseWallItemParser;
import cappo.protocol.messages.events.room.furniture.AddSpamWallPostIt2Parser;
import cappo.protocol.messages.events.room.furniture.CreditFurniRedeemParser;
import cappo.protocol.messages.events.room.furniture.DiceOffParser;
import cappo.protocol.messages.events.room.furniture.OpenPostItParser;
import cappo.protocol.messages.events.room.furniture.PlacePostItParser;
import cappo.protocol.messages.events.room.furniture.RoomDimmerChangeStateParser;
import cappo.protocol.messages.events.room.furniture.RoomDimmerGetPresetsParser;
import cappo.protocol.messages.events.room.furniture.RoomDimmerSavePresetParser;
import cappo.protocol.messages.events.room.furniture.SetOutfitNameParser;
import cappo.protocol.messages.events.room.furniture.SpinWheelOfFortuneParser;
import cappo.protocol.messages.events.room.furniture.ThrowDiceParser;
import cappo.protocol.messages.events.room.furniture.UpdateOutfitParser;
import cappo.protocol.messages.events.room.pets.GetPetInfoParser;
import cappo.protocol.messages.events.room.pets.RespectPetParser;
import cappo.protocol.messages.events.room.session.ChangeQueueParser;
import cappo.protocol.messages.events.room.session.GoToFlatParser;
import cappo.protocol.messages.events.room.session.OpenFlatConnectionParser;
import cappo.protocol.messages.events.room.session.QuitParser;
import cappo.protocol.messages.events.roomsettings.DeleteRoomParser;
import cappo.protocol.messages.events.roomsettings.GetBannedUsersParser;
import cappo.protocol.messages.events.roomsettings.GetFlatControllersParser;
import cappo.protocol.messages.events.roomsettings.GetRoomSettingsParser;
import cappo.protocol.messages.events.roomsettings.SaveRoomSettingsMessageEvent;
import cappo.protocol.messages.events.roomsettings.SetRoomMuteStateParser;
import cappo.protocol.messages.events.sound.AddJukeboxDiskParser;
import cappo.protocol.messages.events.sound.GetJukeboxPlayListParser;
import cappo.protocol.messages.events.sound.GetNowPlayingParser;
import cappo.protocol.messages.events.sound.GetSongInfoParser;
import cappo.protocol.messages.events.sound.GetUserSongDisksParser;
import cappo.protocol.messages.events.sound.RemoveJukeboxDiskParser;
import cappo.protocol.messages.events.sound.SetSoundSettingsParser;
import cappo.protocol.messages.events.talents.GetTalentTrackParser;
import cappo.protocol.messages.events.tracking.EventLogParser;
import cappo.protocol.messages.events.tracking.LatencyPingReportParser;
import cappo.protocol.messages.events.tracking.LatencyPingRequestParser;
import cappo.protocol.messages.events.tracking.PerformanceLogParser;
import cappo.protocol.messages.events.userdefinedroomevents.OpenParser;
import cappo.protocol.messages.events.userdefinedroomevents.UpdateActionParser;
import cappo.protocol.messages.events.userdefinedroomevents.UpdateConditionParser;
import cappo.protocol.messages.events.userdefinedroomevents.UpdateTriggerParser;
import cappo.protocol.messages.events.users.ApproveNameParser;
import cappo.protocol.messages.events.users.GetExtendedProfileParser;
import cappo.protocol.messages.events.users.GetHabboGroupBadgesParser;
import cappo.protocol.messages.events.users.GetIgnoredUsersParser;
import cappo.protocol.messages.events.users.GetRelationshipStatusParser;
import cappo.protocol.messages.events.users.GetSelectedBadgesParser;
import cappo.protocol.messages.events.users.GetUserNotificationsParser;
import cappo.protocol.messages.events.users.GetUserSettingsParser;
import cappo.protocol.messages.events.users.GetUserTagsParser;
import cappo.protocol.messages.events.users.RespectUserParser;
import cappo.protocol.messages.events.users.ScrGetUserInfoParser;
import cappo.protocol.messages.events.users.SetUserChatSettingMessageEvent;

public class OldOpCodes
{
  public static byte Init;
  
  static
  {
    cappo.protocol.messages.composers.landing.UpdateLandingComposer.HEADER = 1286;
    cappo.protocol.messages.composers.landing.LandingNewsComposer.HEADER = 2915;
    cappo.protocol.messages.composers.landing.BadgeButtonStatusComposer.HEADER = 211;
    cappo.protocol.messages.composers.landing.LandingView6Composer.HEADER = 2210;
    cappo.protocol.messages.composers.landing.RewardResultComposer.HEADER = 634;
    

    cappo.protocol.messages.composers.advertisement.InterstitialComposer.HEADER = 2347;
    

    cappo.protocol.messages.composers.availability.Pending2029Composer.HEADER = 1151;
    cappo.protocol.messages.composers.availability.Pending548Composer.HEADER = 2931;
    cappo.protocol.messages.composers.availability.AvailabilityStatusComposer.HEADER = 134;
    cappo.protocol.messages.composers.availability.Pending2850Composer.HEADER = 2931;
    
    cappo.protocol.messages.composers.avatar.WardrobeComposer.HEADER = 1538;
    cappo.protocol.messages.composers.avatar.ResultChangeUserNameComposer.HEADER = 452;
    cappo.protocol.messages.composers.avatar.ResultCheckUserNameComposer.HEADER = 1387;
    
    cappo.protocol.messages.composers.notifications.BuyNotificationComposer.HEADER = 797;
    cappo.protocol.messages.composers.catalog.BundleDynamicDiscountsComposer.HEADER = 2342;
    cappo.protocol.messages.composers.catalog.CatalogIndexComposer.HEADER = 755;
    cappo.protocol.messages.composers.catalog.CatalogPageComposer.HEADER = 1484;
    cappo.protocol.messages.composers.catalog.ErrorPurchaseFromCatalogComposer.HEADER = 3149;
    cappo.protocol.messages.composers.catalog.GiftWrappingConfigurationComposer.HEADER = 3305;
    cappo.protocol.messages.composers.catalog.HabboClubExtendOfferComposer.HEADER = 2694;
    cappo.protocol.messages.composers.catalog.HabboClubOffersComposer.HEADER = 161;
    cappo.protocol.messages.composers.catalog.SellablePetBreedsComposer.HEADER = 189;
    cappo.protocol.messages.composers.catalog.SnowWarTokensComposer.HEADER = 2706;
    cappo.protocol.messages.composers.catalog.UniqueLimitedItemComposer.HEADER = 2002;
    cappo.protocol.messages.composers.catalog.UniqueLimitedItemSoldOutComposer.HEADER = 2651;
    cappo.protocol.messages.composers.catalog.ErrorBuyComposer.HEADER = 1374;
    
    cappo.protocol.messages.composers.error.ErrorComposer.HEADER = 2814;
    
    cappo.protocol.messages.composers.facebook.Pending1298Composer.HEADER = 2569;
    cappo.protocol.messages.composers.facebook.Pending2310Composer.HEADER = 803;
    cappo.protocol.messages.composers.facebook.Pending3136Composer.HEADER = 3370;
    
    cappo.protocol.messages.composers.friendlist.BuddyMessageComposer.HEADER = 968;
    cappo.protocol.messages.composers.friendlist.BuddyRequestsComposer.HEADER = 3815;
    cappo.protocol.messages.composers.friendlist.FollowFriendFailedComposer.HEADER = 2639;
    cappo.protocol.messages.composers.friendlist.InstantMessageErrorComposer.HEADER = 901;
    cappo.protocol.messages.composers.friendlist.MessengerErrorComposer.HEADER = 1156;
    cappo.protocol.messages.composers.friendlist.HabboSearchResultsComposer.HEADER = 3457;
    cappo.protocol.messages.composers.friendlist.MessengerInitComposer.HEADER = 1514;
    cappo.protocol.messages.composers.friendlist.NewBuddyRequestComposer.HEADER = 1203;
    cappo.protocol.messages.composers.friendlist.FriendsUpdatesComposer.HEADER = 363;
    cappo.protocol.messages.composers.friendlist.RoomInviteComposer.HEADER = 3015;
    cappo.protocol.messages.composers.friendlist.RoomInviteErrorComposer.HEADER = 3507;
    
    cappo.protocol.messages.composers.guides.UpdateGuideToolComposer.HEADER = 1235;
    
    cappo.protocol.messages.composers.games.gamecenter.JoinedPlayerQueueComposer.HEADER = 607;
    cappo.protocol.messages.composers.games.gamecenter.GameListComposer.HEADER = 2661;
    cappo.protocol.messages.composers.games.gamecenter.StatusGameComposer.HEADER = 1045;
    cappo.protocol.messages.composers.games.gamecenter.LoadGameComposer.HEADER = 117;
    cappo.protocol.messages.composers.games.gamecenter.GameAchievementsComposer.HEADER = 1729;
    
    cappo.protocol.messages.composers.games.snowwar.ArenaEnteredComposer.HEADER = 1451;
    cappo.protocol.messages.composers.games.snowwar.EnterArenaComposer.HEADER = 2310;
    cappo.protocol.messages.composers.games.snowwar.EnterArenaFailedComposer.HEADER = 3131;
    cappo.protocol.messages.composers.games.snowwar.FriendsLeaderboardComposer.HEADER = 3579;
    cappo.protocol.messages.composers.games.snowwar.FullGameStatusComposer.HEADER = 329;
    cappo.protocol.messages.composers.games.snowwar.GameCancelledComposer.HEADER = 2152;
    cappo.protocol.messages.composers.games.snowwar.GameChatFromPlayerComposer.HEADER = 3617;
    cappo.protocol.messages.composers.games.snowwar.GameCreatedComposer.HEADER = 1606;
    cappo.protocol.messages.composers.games.snowwar.GameDirectoryStatusComposer.HEADER = 418;
    cappo.protocol.messages.composers.games.snowwar.GameEndingComposer.HEADER = 24;
    cappo.protocol.messages.composers.games.snowwar.GameLongDataComposer.HEADER = 2077;
    cappo.protocol.messages.composers.games.snowwar.GameRejoinComposer.HEADER = 997;
    cappo.protocol.messages.composers.games.snowwar.GameStatusComposer.HEADER = 577;
    cappo.protocol.messages.composers.games.snowwar.InArenaQueueComposer.HEADER = 194;
    cappo.protocol.messages.composers.games.snowwar.JoiningGameFailedComposer.HEADER = 1078;
    cappo.protocol.messages.composers.games.snowwar.PlayerExitedGameArenaComposer.HEADER = 2267;
    cappo.protocol.messages.composers.games.snowwar.PlayerRematchesComposer.HEADER = 3574;
    cappo.protocol.messages.composers.games.snowwar.StageEndingComposer.HEADER = 1068;
    cappo.protocol.messages.composers.games.snowwar.StageLoadComposer.HEADER = 3855;
    cappo.protocol.messages.composers.games.snowwar.StageRunningComposer.HEADER = 3770;
    cappo.protocol.messages.composers.games.snowwar.StageStartingComposer.HEADER = 793;
    cappo.protocol.messages.composers.games.snowwar.StageStillLoadingComposer.HEADER = 1553;
    cappo.protocol.messages.composers.games.snowwar.StartCounterComposer.HEADER = 603;
    cappo.protocol.messages.composers.games.snowwar.StartingGameFailedComposer.HEADER = 1926;
    cappo.protocol.messages.composers.games.snowwar.StopCounterComposer.HEADER = 887;
    cappo.protocol.messages.composers.games.snowwar.TotalLeaderboardComposer.HEADER = 2072;
    cappo.protocol.messages.composers.games.snowwar.UserBlockedComposer.HEADER = 1131;
    cappo.protocol.messages.composers.games.snowwar.UserJoinedGameComposer.HEADER = 3954;
    cappo.protocol.messages.composers.games.snowwar.UserLeftGameComposer.HEADER = 433;
    cappo.protocol.messages.composers.games.snowwar.AccountGameStatusComposer.HEADER = 3349;
    cappo.protocol.messages.composers.games.snowwar.GameStartedComposer.HEADER = 2759;
    
    cappo.protocol.messages.composers.games.snowwar.WeeklyLeaderboardComposer.HEADER = 2637;
    
    cappo.protocol.messages.composers.handshake.GenericErrorComposer.HEADER = 2117;
    cappo.protocol.messages.composers.handshake.UserLevelsComposer.HEADER = 815;
    cappo.protocol.messages.composers.handshake.AuthOKComposer.HEADER = 3695;
    cappo.protocol.messages.composers.handshake.BannerTokenComposer.HEADER = 2226;
    cappo.protocol.messages.composers.handshake.PerkAllowancesComposer.HEADER = 2313;
    cappo.protocol.messages.composers.handshake.ConnectionPingComposer.HEADER = 3576;
    cappo.protocol.messages.composers.handshake.ServerPublicKeyComposer.HEADER = 2949;
    cappo.protocol.messages.composers.handshake.UserDisconnectComposer.HEADER = 4000;
    cappo.protocol.messages.composers.handshake.UserInfoComposer.HEADER = 3540;
    
    cappo.protocol.messages.composers.help.CallForHelpMutedComposer.HEADER = 1211;
    cappo.protocol.messages.composers.help.CallForHelpOpenComposer.HEADER = 1939;
    cappo.protocol.messages.composers.help.CallForHelpPendingCallsComposer.HEADER = 3586;
    cappo.protocol.messages.composers.help.CallForHelpReplyComposer.HEADER = 2721;
    cappo.protocol.messages.composers.help.CallForHelpResultComposer.HEADER = 2559;
    cappo.protocol.messages.composers.help.IssueCloseNotificationComposer.HEADER = 488;
    
    cappo.protocol.messages.composers.inventory.achievements.AchievementsComposer.HEADER = 3913;
    cappo.protocol.messages.composers.inventory.achievements.AchievementsScoreComposer.HEADER = 2282;
    
    cappo.protocol.messages.composers.inventory.avatareffect.EffectAddedComposer.HEADER = 3137;
    cappo.protocol.messages.composers.inventory.avatareffect.EffectEnabledComposer.HEADER = 2758;
    cappo.protocol.messages.composers.inventory.avatareffect.EffectStopedComposer.HEADER = 3605;
    cappo.protocol.messages.composers.inventory.avatareffect.EffectsComposer.HEADER = 444;
    
    cappo.protocol.messages.composers.inventory.badges.BadgesComposer.HEADER = 2773;
    
    cappo.protocol.messages.composers.inventory.furni.FurniListComposer.HEADER = 2303;
    cappo.protocol.messages.composers.inventory.furni.FurniListAddOrUpdateComposer.HEADER = 201;
    cappo.protocol.messages.composers.inventory.furni.FurniListRemoveComposer.HEADER = 1944;
    cappo.protocol.messages.composers.inventory.furni.FurniListUpdateComposer.HEADER = 3798;
    cappo.protocol.messages.composers.inventory.furni.PostItPlacedComposer.HEADER = 3749;
    
    cappo.protocol.messages.composers.inventory.pets.AddPetToInventoryComposer.HEADER = 2074;
    cappo.protocol.messages.composers.inventory.pets.PetsInventoryComposer.HEADER = 2813;
    cappo.protocol.messages.composers.inventory.pets.RemovePetInventoryComposer.HEADER = 3842;
    
    cappo.protocol.messages.composers.inventory.bots.BotsInventoryComposer.HEADER = 3241;
    cappo.protocol.messages.composers.inventory.bots.AddBotToInventoryComposer.HEADER = 507;
    cappo.protocol.messages.composers.inventory.bots.RemoveBotInventoryComposer.HEADER = 29;
    
    cappo.protocol.messages.composers.inventory.purse.CreditBalanceComposer.HEADER = 3045;
    
    cappo.protocol.messages.composers.inventory.trading.TradingAcceptComposer.HEADER = 2860;
    cappo.protocol.messages.composers.inventory.trading.TradingAlreadyOpenComposer.HEADER = 2232;
    cappo.protocol.messages.composers.inventory.trading.TradingCloseComposer.HEADER = 3373;
    cappo.protocol.messages.composers.inventory.trading.TradingCompletedComposer.HEADER = 315;
    cappo.protocol.messages.composers.inventory.trading.TradingConfirmationComposer.HEADER = 491;
    cappo.protocol.messages.composers.inventory.trading.TradingItemListComposer.HEADER = 924;
    cappo.protocol.messages.composers.inventory.trading.TradingOpenComposer.HEADER = 2290;
    
    cappo.protocol.messages.composers.landing.NextLimitedAvailableComposer.HEADER = 3279;
    
    cappo.protocol.messages.composers.marketplace.MarketplaceCanMakeOfferComposer.HEADER = 605;
    cappo.protocol.messages.composers.marketplace.MarketplaceConfigComposer.HEADER = 3996;
    
    cappo.protocol.messages.composers.moderation.IssueInfoComposer.HEADER = 2137;
    cappo.protocol.messages.composers.moderation.IssuePickFailedComposer.HEADER = 487;
    cappo.protocol.messages.composers.moderation.ModMessageComposer.HEADER = 812;
    cappo.protocol.messages.composers.moderation.ModeratorInitComposer.HEADER = 621;
    cappo.protocol.messages.composers.moderation.ModeratorRoomInfoComposer.HEADER = 1815;
    cappo.protocol.messages.composers.moderation.ModeratorUserInfoComposer.HEADER = 1193;
    
    cappo.protocol.messages.composers.navigator.OfficialRoomsComposer.HEADER = 2392;
    cappo.protocol.messages.composers.navigator.FlatCreatedComposer.HEADER = 3484;
    cappo.protocol.messages.composers.navigator.DoorbellUserComposer.HEADER = 1610;
    cappo.protocol.messages.composers.room.session.FlatAccessibleComposer.HEADER = 3176;
    cappo.protocol.messages.composers.navigator.DoorBellNoAnswerComposer.HEADER = 2216;
    cappo.protocol.messages.composers.navigator.GuestRoomResultComposer.HEADER = 1664;
    cappo.protocol.messages.composers.navigator.CanCreateEventComposer.HEADER = 2073;
    cappo.protocol.messages.composers.navigator.CanCreateRoomComposer.HEADER = 684;
    cappo.protocol.messages.composers.navigator.EventComposer.HEADER = 2389;
    cappo.protocol.messages.composers.navigator.FavouritesComposer.HEADER = 479;
    cappo.protocol.messages.composers.navigator.FavouriteChangedComposer.HEADER = 1764;
    cappo.protocol.messages.composers.navigator.FlatCategoriesComposer.HEADER = 1177;
    cappo.protocol.messages.composers.navigator.NavigatorSettingsComposer.HEADER = 2499;
    cappo.protocol.messages.composers.navigator.GuestRoomSearchResultComposer.HEADER = 3439;
    cappo.protocol.messages.composers.navigator.PopularRoomTagsResultComposer.HEADER = 468;
    cappo.protocol.messages.composers.navigator.RoomRatingComposer.HEADER = 1263;
    cappo.protocol.messages.composers.navigator.RoomUpdatedComposer.HEADER = 2263;
    cappo.protocol.messages.composers.navigator.FlatAccessDeniedComposer.HEADER = 2150;
    cappo.protocol.messages.composers.navigator.RoomForwardComposer.HEADER = 3502;
    
    cappo.protocol.messages.composers.notifications.ActivityPointsComposer.HEADER = 3901;
    cappo.protocol.messages.composers.notifications.BroadcastImageComposer.HEADER = 1849;
    cappo.protocol.messages.composers.notifications.HabboActivityPointNotificationComposer.HEADER = 2549;
    cappo.protocol.messages.composers.notifications.ClubGiftNotificationComposer.HEADER = 2822;
    cappo.protocol.messages.composers.notifications.HabboBroadcastComposer.HEADER = 1248;
    cappo.protocol.messages.composers.notifications.HabboBroadcastCustomComposer.HEADER = 2515;
    cappo.protocol.messages.composers.notifications.InfoFeedEnableComposer.HEADER = 2452;
    cappo.protocol.messages.composers.notifications.MOTDComposer.HEADER = 760;
    cappo.protocol.messages.composers.notifications.PetLevelNotificationComposer.HEADER = 3121;
    cappo.protocol.messages.composers.notifications.UnseenItemsComposer.HEADER = 1287;
    
    cappo.protocol.messages.composers.poll.PollContentsMessageComposer.HEADER = 1984;
    cappo.protocol.messages.composers.poll.PollOfferMessageComposer.HEADER = 2520;
    cappo.protocol.messages.composers.poll.PollErrorMessageComposer.HEADER = 2304;
    
    cappo.protocol.messages.composers.recycler.RecyclerOkComposer.HEADER = 3856;
    cappo.protocol.messages.composers.recycler.RecyclerPrizesComposer.HEADER = 1028;
    cappo.protocol.messages.composers.recycler.RecyclerStatusComposer.HEADER = 48;
    
    cappo.protocol.messages.composers.room.action.UserDanceComposer.HEADER = 3010;
    cappo.protocol.messages.composers.room.action.AvatarExpressionComposer.HEADER = 112;
    cappo.protocol.messages.composers.room.action.CarryObjectComposer.HEADER = 1457;
    cappo.protocol.messages.composers.room.action.UserAsleepComposer.HEADER = 2067;
    cappo.protocol.messages.composers.room.action.UserEffectComposer.HEADER = 2157;
    
    cappo.protocol.messages.composers.room.chat.ChatComposer.HEADER = 3381;
    cappo.protocol.messages.composers.room.chat.FloodControlComposer.HEADER = 85;
    cappo.protocol.messages.composers.room.chat.ShoutComposer.HEADER = 980;
    cappo.protocol.messages.composers.room.chat.UserTypingComposer.HEADER = 2016;
    cappo.protocol.messages.composers.room.chat.WhisperComposer.HEADER = 668;
    
    cappo.protocol.messages.composers.room.bots.BotSkillComposer.HEADER = 1139;
    cappo.protocol.messages.composers.room.bots.BotErrorComposer.HEADER = 1838;
    
    cappo.protocol.messages.composers.room.engine.FloorHeightMapComposer.HEADER = 3067;
    cappo.protocol.messages.composers.room.engine.HeightMapComposer.HEADER = 1956;
    cappo.protocol.messages.composers.room.engine.HeightMapUpdateComposer.HEADER = 3281;
    cappo.protocol.messages.composers.room.engine.ObjectsComposer.HEADER = 2289;
    cappo.protocol.messages.composers.room.engine.ObjectAddComposer.HEADER = 3983;
    cappo.protocol.messages.composers.room.engine.ObjectRemoveComposer.HEADER = 2997;
    cappo.protocol.messages.composers.room.engine.ObjectUpdateComposer.HEADER = 2946;
    cappo.protocol.messages.composers.room.engine.ObjectDataUpdateComposer.HEADER = 1475;
    cappo.protocol.messages.composers.room.engine.ObjectsDataUpdateComposer.HEADER = 2782;
    cappo.protocol.messages.composers.room.engine.PublicRoomObjectsMessageParser.HEADER = 3596;
    cappo.protocol.messages.composers.room.engine.ItemsComposer.HEADER = 387;
    cappo.protocol.messages.composers.room.engine.ItemAddComposer.HEADER = 128;
    cappo.protocol.messages.composers.room.engine.ItemRemoveComposer.HEADER = 3811;
    cappo.protocol.messages.composers.room.engine.ItemUpdateComposer.HEADER = 2121;
    cappo.protocol.messages.composers.room.engine.UsersComposer.HEADER = 467;
    cappo.protocol.messages.composers.room.engine.UserUpdateComposer.HEADER = 1720;
    cappo.protocol.messages.composers.room.engine.UserChangeComposer.HEADER = 921;
    cappo.protocol.messages.composers.room.engine.UserRemoveComposer.HEADER = 3163;
    cappo.protocol.messages.composers.room.engine.RoomVisualizationSettingsComposer.HEADER = 1699;
    cappo.protocol.messages.composers.room.engine.RoomEntryInfoComposer.HEADER = 3884;
    cappo.protocol.messages.composers.room.engine.RoomPropertyComposer.HEADER = 3620;
    cappo.protocol.messages.composers.room.engine.RoomCampaignAdsComposer.HEADER = 312;
    cappo.protocol.messages.composers.room.engine.SlideObjectBundleComposer.HEADER = 2845;
    cappo.protocol.messages.composers.room.engine.PlaceObjectErrorComposer.HEADER = 2787;
    
    cappo.protocol.messages.composers.room.furniture.RequestSpamWallPostItComposer.HEADER = 1804;
    cappo.protocol.messages.composers.room.furniture.RoomDimmerPresetsComposer.HEADER = 3940;
    
    cappo.protocol.messages.composers.room.permissions.YouAreControllerComposer.HEADER = 739;
    cappo.protocol.messages.composers.room.permissions.YouAreNotControllerComposer.HEADER = 190;
    cappo.protocol.messages.composers.room.permissions.YouAreOwnerComposer.HEADER = 41;
    
    cappo.protocol.messages.composers.room.pets.PetCommandsComposer.HEADER = 1918;
    cappo.protocol.messages.composers.room.pets.PetInfoComposer.HEADER = 2730;
    cappo.protocol.messages.composers.room.pets.PetPlacingErrorComposer.HEADER = 1706;
    cappo.protocol.messages.composers.notifications.PetRespectFailedComposer.HEADER = 2891;
    
    cappo.protocol.messages.composers.room.publicroom.ParkBusCannotEnterComposer.HEADER = 225;
    
    cappo.protocol.messages.composers.room.session.YouArePlayingGameComposer.HEADER = 2791;
    cappo.protocol.messages.composers.room.session.OpenConnectionComposer.HEADER = 2487;
    cappo.protocol.messages.composers.room.session.RoomReadyComposer.HEADER = 2445;
    cappo.protocol.messages.composers.room.session.CloseConnectionComposer.HEADER = 1959;
    cappo.protocol.messages.composers.room.session.RoomQueueStatusComposer.HEADER = 2426;
    cappo.protocol.messages.composers.room.session.YouAreSpectatorComposer.HEADER = 2416;
    
    cappo.protocol.messages.composers.roomsettings.RoomMuteStateComposer.HEADER = 3126;
    cappo.protocol.messages.composers.roomsettings.BannedUsersComposer.HEADER = 2138;
    cappo.protocol.messages.composers.roomsettings.RoomBanRemoved.HEADER = 242;
    cappo.protocol.messages.composers.roomsettings.FlatControllerAddedComposer.HEADER = 3379;
    cappo.protocol.messages.composers.roomsettings.FlatControllerRemovedComposer.HEADER = 2748;
    cappo.protocol.messages.composers.roomsettings.FlatControllersComposer.HEADER = 79;
    cappo.protocol.messages.composers.roomsettings.RoomSettingsDataComposer.HEADER = 2398;
    cappo.protocol.messages.composers.roomsettings.RoomSettingsSavedComposer.HEADER = 10;
    cappo.protocol.messages.composers.roomsettings.RoomSettingsErrorComposer.HEADER = 1801;
    
    cappo.protocol.messages.composers.users.UserSettingsComposer.HEADER = 1471;
    cappo.protocol.messages.composers.sound.JukeboxSongDisksComposer.HEADER = 2930;
    cappo.protocol.messages.composers.sound.TraxSongInfoComposer.HEADER = 834;
    cappo.protocol.messages.composers.sound.UserSongDisksInventoryComposer.HEADER = 3594;
    cappo.protocol.messages.composers.sound.NowPlayingComposer.HEADER = 3191;
    cappo.protocol.messages.composers.sound.JukeboxPlayListFullComposer.HEADER = 1458;
    
    cappo.protocol.messages.composers.talents.TalentTrackComposer.HEADER = 3330;
    
    cappo.protocol.messages.composers.tracking.PingResponseComposer.HEADER = 3773;
    
    cappo.protocol.messages.composers.userdefinedroomevents.OpenWiredComposer.HEADER = 688;
    cappo.protocol.messages.composers.userdefinedroomevents.WiredUpdateFailedComposer.HEADER = 1181;
    cappo.protocol.messages.composers.userdefinedroomevents.WiredUpdatedComposer.HEADER = 447;
    cappo.protocol.messages.composers.userdefinedroomevents.WiredRewardNotificationComposer.HEADER = 634;
    
    cappo.protocol.messages.composers.users.ApproveNameComposer.HEADER = 3606;
    cappo.protocol.messages.composers.users.UserTagsComposer.HEADER = 708;
    cappo.protocol.messages.composers.users.HabboGroupBadgesComposer.HEADER = 3360;
    cappo.protocol.messages.composers.users.IgnoredUsersComposer.HEADER = 2017;
    cappo.protocol.messages.composers.users.NotifyUserNameChangeComposer.HEADER = 3985;
    cappo.protocol.messages.composers.users.PetRespectedComposer.HEADER = 908;
    cappo.protocol.messages.composers.users.RelationshipStatusComposer.HEADER = 3530;
    cappo.protocol.messages.composers.users.UserBadgesComposer.HEADER = 1567;
    cappo.protocol.messages.composers.users.UserProfileInfoComposer.HEADER = 1011;
    cappo.protocol.messages.composers.users.ScrUserInfoComposer.HEADER = 3319;
    cappo.protocol.messages.composers.users.UserRespectedComposer.HEADER = 3442;
    



    IncomingMessageEvent.callBacks[3831] = new GetLandingNewsParser();
    IncomingMessageEvent.callBacks[2062] = new RefreshLandingViewParser();
    
    IncomingMessageEvent.callBacks[424] = new GetLandingView6Parser();
    



    IncomingMessageEvent.callBacks[2286] = new PutHorseSaddleParser();
    IncomingMessageEvent.callBacks[1673] = new HorseMountUpdateParser();
    IncomingMessageEvent.callBacks[1897] = new RemoveHorseSaddleParser();
    IncomingMessageEvent.callBacks[2523] = new RidingPermissionParser();
    
    IncomingMessageEvent.callBacks[3663] = new GetInterstitialParser();
    

    IncomingMessageEvent.callBacks[1280] = new ChangeUserNameParser();
    IncomingMessageEvent.callBacks[502] = new CheckUserNameParser();
    IncomingMessageEvent.callBacks[15] = new GetWardrobeParser();
    IncomingMessageEvent.callBacks[1267] = new SaveWardrobeOutfitParser();
    
    IncomingMessageEvent.callBacks[''] = new GetGiftWrappingConfigurationParser();
    IncomingMessageEvent.callBacks[1416] = new GetHabboClubExtendOfferParser();
    IncomingMessageEvent.callBacks[2984] = new GetSellablePetBreedsParser();
    
    IncomingMessageEvent.callBacks[1512] = new GetIsOfferGiftableParser();
    IncomingMessageEvent.callBacks['º'] = new GetClubOffersParser();
    IncomingMessageEvent.callBacks[299] = new GetCatalogIndexParser();
    
    IncomingMessageEvent.callBacks[289] = new PurchaseFromCatalogParser();
    IncomingMessageEvent.callBacks[1750] = new GetCatalogPageParser();
    IncomingMessageEvent.callBacks[3626] = new GetSnowWarTokensParser();
    IncomingMessageEvent.callBacks[800] = new GetBundleDynamicDiscountsParser();
    IncomingMessageEvent.callBacks[12] = new GetUniqueLimitedItemParser();
    
    IncomingMessageEvent.callBacks[3323] = new SetRelationshipStatusParser();
    IncomingMessageEvent.callBacks[3155] = new SendMsgParser();
    IncomingMessageEvent.callBacks[520] = new SendRoomInviteParser();
    IncomingMessageEvent.callBacks[3731] = new AcceptFriendParser();
    IncomingMessageEvent.callBacks[3045] = new DeclineFriendParser();
    IncomingMessageEvent.callBacks[3237] = new RequestBuddyParser();
    IncomingMessageEvent.callBacks[2989] = new RemoveFriendParser();
    IncomingMessageEvent.callBacks[3199] = new HabboSearchParser();
    IncomingMessageEvent.callBacks[1749] = new MessengerInitParser();
    IncomingMessageEvent.callBacks[3110] = new FriendListUpdateParser();
    IncomingMessageEvent.callBacks[1609] = new GetBuddyRequestsParser();
    IncomingMessageEvent.callBacks[1066] = new FollowFriendParser();
    

    IncomingMessageEvent.callBacks[3724] = new SetDutyGuideToolParser();
    
    IncomingMessageEvent.callBacks[569] = new JoinPlayerQueueParser();
    IncomingMessageEvent.callBacks[778] = new GetStatusGameParser();
    IncomingMessageEvent.callBacks[''] = new GetGameListParser();
    IncomingMessageEvent.callBacks[3146] = new GetGameAchievementsParser();
    
    IncomingMessageEvent.callBacks[3001] = new CheckGameDirectoryStatusParser();
    IncomingMessageEvent.callBacks[3484] = new GetAccountGameStatusParser();
    IncomingMessageEvent.callBacks[1534] = new ExitGameParser();
    IncomingMessageEvent.callBacks[1925] = new QuickJoinGameParser();
    IncomingMessageEvent.callBacks[1153] = new LeaveGameParser();
    IncomingMessageEvent.callBacks[2682] = new GameChatParser();
    IncomingMessageEvent.callBacks[2285] = new LoadStageReadyParser();
    IncomingMessageEvent.callBacks[422] = new SetUserMoveTargetParser();
    IncomingMessageEvent.callBacks[3728] = new RequestFullStatusUpdateParser();
    IncomingMessageEvent.callBacks[1296] = new MakeSnowballParser();
    IncomingMessageEvent.callBacks[49] = new PlayAgainParser();
    IncomingMessageEvent.callBacks[3076] = new ThrowSnowballAtHumanParser();
    IncomingMessageEvent.callBacks[66] = new ThrowSnowballAtPositionParser();
    






    IncomingMessageEvent.callBacks[1067] = new InfoRetrieveParser();
    IncomingMessageEvent.callBacks[1227] = new PongParser();
    IncomingMessageEvent.callBacks[3838] = new InitCryptoParser();
    IncomingMessageEvent.callBacks[3395] = new SSOTicketParser();
    IncomingMessageEvent.callBacks[71] = new DisconnectParser();
    IncomingMessageEvent.callBacks[2294] = new UniqueIDParser();
    IncomingMessageEvent.callBacks[1069] = new VersionCheckParser();
    IncomingMessageEvent.callBacks[1337] = new GenerateSecretKeyParser();
    
    IncomingMessageEvent.callBacks[2092] = new CallForHelpOpenParser();
    IncomingMessageEvent.callBacks[297] = new CallForHelp2Parser();
    IncomingMessageEvent.callBacks[894] = new CallForHelpParser();
    IncomingMessageEvent.callBacks[671] = new CallForHelpInRoomParser();
    IncomingMessageEvent.callBacks[121] = new CallForHelpRoomPanicParser();
    IncomingMessageEvent.callBacks[832] = new CallForHelpRoomParser();
    
    IncomingMessageEvent.callBacks[734] = new GetAchievementsParser();
    
    IncomingMessageEvent.callBacks[1022] = new AvatarEffectSelectedParser();
    IncomingMessageEvent.callBacks[2604] = new AvatarEffectActivatedParser();
    
    IncomingMessageEvent.callBacks[1039] = new GetBadgesParser();
    IncomingMessageEvent.callBacks[1410] = new SetActivatedBadgesParser();
    IncomingMessageEvent.callBacks[3108] = new GetBadgePointLimitsParser();
    
    IncomingMessageEvent.callBacks[1648] = new RequestRoomPropertySetParser();
    IncomingMessageEvent.callBacks[3415] = new RequestFurniInventoryParser();
    
    IncomingMessageEvent.callBacks[2579] = new RequestPetInventoryParser();
    
    IncomingMessageEvent.callBacks[2450] = new RequestBotInventoryParser();
    
    IncomingMessageEvent.callBacks[1802] = new GetCreditsInfoParser();
    
    IncomingMessageEvent.callBacks[3540] = new OpenTradingParser();
    IncomingMessageEvent.callBacks[1005] = new AddItemToTradeParser();
    IncomingMessageEvent.callBacks[3020] = new RemoveItemFromTradeParser();
    IncomingMessageEvent.callBacks[378] = new ConfirmAcceptTradingParser();
    IncomingMessageEvent.callBacks[3374] = new ConfirmDeclineTradingParser();
    IncomingMessageEvent.callBacks[3026] = new AcceptTradingParser();
    IncomingMessageEvent.callBacks[3282] = new UnacceptTradingParser();
    IncomingMessageEvent.callBacks[1178] = new CloseTradingParser();
    
    IncomingMessageEvent.callBacks[3785] = new GetNextLimitedAvailableParser();
    
    IncomingMessageEvent.callBacks[3575] = new GetMarketplaceConfigurationParser();
    IncomingMessageEvent.callBacks[980] = new GetMarketplaceCanMakeOfferParser();
    
    IncomingMessageEvent.callBacks[1941] = new GetModeratorUserInfoParser();
    IncomingMessageEvent.callBacks[3075] = new GetModeratorRoomInfoParser();
    IncomingMessageEvent.callBacks[1623] = new ModeratorActionParser();
    IncomingMessageEvent.callBacks[1759] = new ModMessageParser();
    IncomingMessageEvent.callBacks[2102] = new ModKickParser();
    IncomingMessageEvent.callBacks[1470] = new ModMuteParser();
    IncomingMessageEvent.callBacks[1282] = new ModBanParser();
    IncomingMessageEvent.callBacks[1658] = new ModerateRoomParser();
    IncomingMessageEvent.callBacks[945] = new ModeratorRoomActionParser();
    IncomingMessageEvent.callBacks[1478] = new PickIssuesParser();
    IncomingMessageEvent.callBacks[''] = new ReleaseIssuesParser();
    IncomingMessageEvent.callBacks[2663] = new CloseIssuesParser();
    
    IncomingMessageEvent.callBacks[915] = new AddFavouriteRoomParser();
    IncomingMessageEvent.callBacks[3019] = new DeleteFavouriteRoomParser();
    IncomingMessageEvent.callBacks[''] = new CreateFlatParser();
    IncomingMessageEvent.callBacks[3757] = new RateFlatParser();
    


    IncomingMessageEvent.callBacks[1303] = new EditEventParser();
    IncomingMessageEvent.callBacks[964] = new GetOfficialRoomsParser();
    IncomingMessageEvent.callBacks[2872] = new GetPopularRoomTagsParser();
    IncomingMessageEvent.callBacks[1519] = new UpdateNavigatorSettingsParser();
    IncomingMessageEvent.callBacks[1480] = new GetGuestRoomParser();
    IncomingMessageEvent.callBacks[367] = new CanCreateRoomParser();
    IncomingMessageEvent.callBacks[3171] = new PopularRoomsSearchParser();
    IncomingMessageEvent.callBacks[604] = new RoomsWithHighestScoreSearchParser();
    IncomingMessageEvent.callBacks[2312] = new MyFriendsRoomsSearchParser();
    IncomingMessageEvent.callBacks[1586] = new RoomsWhereMyFriendsAreSearchParser();
    IncomingMessageEvent.callBacks[3562] = new MyRoomsSearchParser();
    IncomingMessageEvent.callBacks[3451] = new MyFavouriteRoomsSearchParser();
    IncomingMessageEvent.callBacks[2254] = new MyRoomHistorySearchParser();
    IncomingMessageEvent.callBacks[1898] = new RoomTextSearchParser();
    IncomingMessageEvent.callBacks[1586] = new RoomTagSearchParser();
    IncomingMessageEvent.callBacks[627] = new LatestEventsSearchParser();
    IncomingMessageEvent.callBacks[1492] = new GetUserFlatCatsParser();
    IncomingMessageEvent.callBacks[902] = new ToggleStaffPickParser();
    
    IncomingMessageEvent.callBacks[1912] = new ResetUnseenItemsParser();
    
    IncomingMessageEvent.callBacks[2780] = new PollStartParser();
    IncomingMessageEvent.callBacks[3030] = new PollRejectParser();
    IncomingMessageEvent.callBacks[1762] = new PollAnswerParser();
    

    IncomingMessageEvent.callBacks[1639] = new FriendRequestQuestCompleteParser();
    
    IncomingMessageEvent.callBacks[1529] = new GetRecyclerPrizesParser();
    


    IncomingMessageEvent.callBacks[3699] = new UpdateFigureDataParser();
    
    IncomingMessageEvent.callBacks[3175] = new KickUserParser();
    IncomingMessageEvent.callBacks[2289] = new BanUserParser();
    IncomingMessageEvent.callBacks[1171] = new AssignRightsParser();
    IncomingMessageEvent.callBacks[2015] = new RemoveRightsParser();
    IncomingMessageEvent.callBacks[1573] = new RemoveAllRightsParser();
    IncomingMessageEvent.callBacks[78] = new LetUserInParser();
    IncomingMessageEvent.callBacks['Ð'] = new DropCarryObjectParser();
    IncomingMessageEvent.callBacks[3766] = new ShareCarryObjectParser();
    
    IncomingMessageEvent.callBacks[''] = new LookToParser();
    IncomingMessageEvent.callBacks[2272] = new DanceParser();
    IncomingMessageEvent.callBacks[2940] = new SignParser();
    IncomingMessageEvent.callBacks[2387] = new ChangePostureParser();
    IncomingMessageEvent.callBacks[3835] = new ChangeMottoParser();
    IncomingMessageEvent.callBacks[1316] = new SetAvatarExpressionParser();
    
    IncomingMessageEvent.callBacks[3768] = new StartTypingParser();
    IncomingMessageEvent.callBacks[2753] = new CancelTypingParser();
    IncomingMessageEvent.callBacks[2642] = new ChatParser();
    IncomingMessageEvent.callBacks[1388] = new ShoutParser();
    IncomingMessageEvent.callBacks[819] = new WhisperParser();
    
    IncomingMessageEvent.callBacks[1737] = new PickupObjectParser();
    IncomingMessageEvent.callBacks[2820] = new MoveObjectParser();
    IncomingMessageEvent.callBacks[683] = new MoveAvatarParser();
    IncomingMessageEvent.callBacks[3691] = new PlaceObjectParser();
    IncomingMessageEvent.callBacks[2061] = new MoveWallItemParser();
    IncomingMessageEvent.callBacks['ë'] = new GetRoomEntryDataParser();
    IncomingMessageEvent.callBacks[1232] = new UseFurnitureParser();
    IncomingMessageEvent.callBacks[3353] = new UseWallItemParser();
    IncomingMessageEvent.callBacks[2051] = new SetClothingChangeDataParser();
    IncomingMessageEvent.callBacks[2045] = new PlacePetParser();
    IncomingMessageEvent.callBacks[3438] = new RemovePetFromFlatParser();
    IncomingMessageEvent.callBacks[1254] = new GetPetCommandsParser();
    IncomingMessageEvent.callBacks[3168] = new GetRoomCampaignAdsParser();
    IncomingMessageEvent.callBacks[1677] = new PlaceRentalBotParser();
    IncomingMessageEvent.callBacks[2729] = new RemoveBotFromFlatParser();
    IncomingMessageEvent.callBacks[478] = new GetRoomCompetitionParser();
    IncomingMessageEvent.callBacks[2475] = new ObjectSaveStuffDataParser();
    

    IncomingMessageEvent.callBacks[3576] = new RoomDimmerGetPresetsParser();
    IncomingMessageEvent.callBacks[2215] = new RoomDimmerSavePresetParser();
    IncomingMessageEvent.callBacks[596] = new RoomDimmerChangeStateParser();
    
    IncomingMessageEvent.callBacks[3908] = new PlacePostItParser();
    IncomingMessageEvent.callBacks[757] = new OpenPostItParser();
    
    IncomingMessageEvent.callBacks[2676] = new AddSpamWallPostIt2Parser();
    IncomingMessageEvent.callBacks[5] = new CreditFurniRedeemParser();
    IncomingMessageEvent.callBacks[2418] = new ThrowDiceParser();
    IncomingMessageEvent.callBacks[995] = new DiceOffParser();
    IncomingMessageEvent.callBacks[757] = new SpinWheelOfFortuneParser();
    IncomingMessageEvent.callBacks[3934] = new SetOutfitNameParser();
    IncomingMessageEvent.callBacks[3358] = new UpdateOutfitParser();
    
    IncomingMessageEvent.callBacks[375] = new GetPetInfoParser();
    IncomingMessageEvent.callBacks[1784] = new RespectPetParser();
    
    IncomingMessageEvent.callBacks[2521] = new SetBotSkillParser();
    IncomingMessageEvent.callBacks[950] = new RequestBotSkillParser();
    
    IncomingMessageEvent.callBacks[2749] = new OpenFlatConnectionParser();
    
    IncomingMessageEvent.callBacks[1317] = new GoToFlatParser();
    IncomingMessageEvent.callBacks[2320] = new ChangeQueueParser();
    IncomingMessageEvent.callBacks[3281] = new QuitParser();
    
    IncomingMessageEvent.callBacks[3519] = new SetRoomMuteStateParser();
    IncomingMessageEvent.callBacks[1894] = new DeleteRoomParser();
    IncomingMessageEvent.callBacks[2825] = new GetRoomSettingsParser();
    IncomingMessageEvent.callBacks[19] = new SaveRoomSettingsMessageEvent();
    IncomingMessageEvent.callBacks[651] = new GetFlatControllersParser();
    IncomingMessageEvent.callBacks[3572] = new GetBannedUsersParser();
    
    IncomingMessageEvent.callBacks[3697] = new GetNowPlayingParser();
    IncomingMessageEvent.callBacks[2268] = new AddJukeboxDiskParser();
    IncomingMessageEvent.callBacks[943] = new RemoveJukeboxDiskParser();
    IncomingMessageEvent.callBacks[267] = new GetUserSongDisksParser();
    IncomingMessageEvent.callBacks[1524] = new GetJukeboxPlayListParser();
    IncomingMessageEvent.callBacks[1257] = new GetSongInfoParser();
    IncomingMessageEvent.callBacks[1223] = new GetUserSettingsParser();
    IncomingMessageEvent.callBacks[2273] = new SetSoundSettingsParser();
    
    IncomingMessageEvent.callBacks[1676] = new GetTalentTrackParser();
    
    IncomingMessageEvent.callBacks[1617] = new LatencyPingRequestParser();
    IncomingMessageEvent.callBacks[418] = new LatencyPingReportParser();
    IncomingMessageEvent.callBacks[3998] = new PerformanceLogParser();
    IncomingMessageEvent.callBacks[3695] = new EventLogParser();
    
    IncomingMessageEvent.callBacks[411] = new UpdateTriggerParser();
    IncomingMessageEvent.callBacks[1448] = new UpdateActionParser();
    IncomingMessageEvent.callBacks[1604] = new UpdateConditionParser();
    IncomingMessageEvent.callBacks[2448] = new OpenParser();
    
    IncomingMessageEvent.callBacks[1991] = new ScrGetUserInfoParser();
    IncomingMessageEvent.callBacks[2889] = new GetExtendedProfileParser();
    IncomingMessageEvent.callBacks[1310] = new ApproveNameParser();
    IncomingMessageEvent.callBacks[2869] = new GetUserTagsParser();
    IncomingMessageEvent.callBacks[901] = new GetIgnoredUsersParser();
    IncomingMessageEvent.callBacks[3577] = new GetRelationshipStatusParser();
    IncomingMessageEvent.callBacks[2178] = new GetSelectedBadgesParser();
    IncomingMessageEvent.callBacks[3800] = new RespectUserParser();
    IncomingMessageEvent.callBacks[3823] = new GetUserNotificationsParser();
    IncomingMessageEvent.callBacks[2823] = new GetHabboGroupBadgesParser();
    




    cappo.protocol.messages.composers.room.chat.ChatSettingsComposer.HEADER = 2165;
    cappo.protocol.messages.composers.notifications.PetReceivedMessageComposer.HEADER = 2928;
    IncomingMessageEvent.callBacks[2435] = new SetUserChatSettingMessageEvent();
  }
}


