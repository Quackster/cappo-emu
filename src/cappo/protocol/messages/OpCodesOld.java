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
import cappo.protocol.messages.events.catalog.PurchaseFromCatalogAsGiftParser;
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
import cappo.protocol.messages.events.room.action.MuteUserParser;
import cappo.protocol.messages.events.room.action.RemoveAllRightsParser;
import cappo.protocol.messages.events.room.action.RemoveBanParser;
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
import cappo.protocol.messages.events.room.engine.UpdateRoomMapParser;
import cappo.protocol.messages.events.room.engine.UseFurnitureParser;
import cappo.protocol.messages.events.room.engine.UseWallItemParser;
import cappo.protocol.messages.events.room.furniture.AddSpamWallPostIt2Parser;
import cappo.protocol.messages.events.room.furniture.CreditFurniRedeemParser;
import cappo.protocol.messages.events.room.furniture.DiceOffParser;
import cappo.protocol.messages.events.room.furniture.OpenPostItParser;
import cappo.protocol.messages.events.room.furniture.PlacePostItParser;
import cappo.protocol.messages.events.room.furniture.PresentOpenParser;
import cappo.protocol.messages.events.room.furniture.RoomDimmerChangeStateParser;
import cappo.protocol.messages.events.room.furniture.RoomDimmerGetPresetsParser;
import cappo.protocol.messages.events.room.furniture.RoomDimmerSavePresetParser;
import cappo.protocol.messages.events.room.furniture.SetOutfitNameParser;
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

public class OpCodesOld
{
  public static byte Init;
  
  public static void registerComposers()
    throws Exception
  {
    OpCodesManager.setComposerId("landing.UpdateLandingComposer", 134);
    OpCodesManager.setComposerId("landing.LandingNewsComposer", 2031);
    OpCodesManager.setComposerId("landing.BadgeButtonStatusComposer", 1629);
    OpCodesManager.setComposerId("landing.LandingView6Composer", 196);
    OpCodesManager.setComposerId("landing.RewardResultComposer", 606);
    OpCodesManager.setComposerId("landing.PersonalMessagesComposer", 3412);
    
    OpCodesManager.setComposerId("advertisement.InterstitialComposer", 2549);
    

    OpCodesManager.setComposerId("availability.Pending2029Composer", 789);
    OpCodesManager.setComposerId("availability.Pending548Composer", 1753);
    OpCodesManager.setComposerId("availability.AvailabilityStatusComposer", 552);
    OpCodesManager.setComposerId("availability.Pending2850Composer", 1753);
    
    OpCodesManager.setComposerId("avatar.WardrobeComposer", 3812);
    OpCodesManager.setComposerId("avatar.ResultChangeUserNameComposer", 454);
    OpCodesManager.setComposerId("avatar.ResultCheckUserNameComposer", 965);
    
    OpCodesManager.setComposerId("catalog.BundleDynamicDiscountsComposer", 3397);
    OpCodesManager.setComposerId("catalog.CatalogIndexComposer", 2485);
    OpCodesManager.setComposerId("catalog.CatalogPageComposer", 3312);
    OpCodesManager.setComposerId("catalog.ErrorPurchaseFromCatalogComposer", 381);
    OpCodesManager.setComposerId("catalog.GiftWrappingConfigurationComposer", 695);
    OpCodesManager.setComposerId("catalog.HabboClubExtendOfferComposer", 2938);
    OpCodesManager.setComposerId("catalog.HabboClubOffersComposer", 3407);
    OpCodesManager.setComposerId("catalog.SellablePetBreedsComposer", 3708);
    OpCodesManager.setComposerId("catalog.SnowWarTokensComposer", 545);
    OpCodesManager.setComposerId("catalog.UniqueLimitedItemComposer", 2002);
    OpCodesManager.setComposerId("catalog.UniqueLimitedItemSoldOutComposer", 3996);
    OpCodesManager.setComposerId("catalog.ErrorBuyComposer", 2626);
    
    OpCodesManager.setComposerId("error.ErrorComposer", 1092);
    
    OpCodesManager.setComposerId("facebook.Pending1298Composer", 1986);
    OpCodesManager.setComposerId("facebook.Pending2310Composer", 3559);
    OpCodesManager.setComposerId("facebook.Pending3136Composer", 1071);
    
    OpCodesManager.setComposerId("friendlist.BuddyMessageComposer", 2041);
    OpCodesManager.setComposerId("friendlist.BuddyRequestsComposer", 2217);
    OpCodesManager.setComposerId("friendlist.FollowFriendFailedComposer", 3864);
    OpCodesManager.setComposerId("friendlist.InstantMessageErrorComposer", 224);
    OpCodesManager.setComposerId("friendlist.MessengerErrorComposer", 1796);
    OpCodesManager.setComposerId("friendlist.HabboSearchResultsComposer", 2270);
    OpCodesManager.setComposerId("friendlist.MessengerInitComposer", 87);
    OpCodesManager.setComposerId("friendlist.NewBuddyRequestComposer", 1568);
    OpCodesManager.setComposerId("friendlist.FriendsUpdatesComposer", 3359);
    OpCodesManager.setComposerId("friendlist.RoomInviteComposer", 1833);
    OpCodesManager.setComposerId("friendlist.RoomInviteErrorComposer", 1410);
    
    OpCodesManager.setComposerId("guides.UpdateGuideToolComposer", 3731);
    
    OpCodesManager.setComposerId("games.gamecenter.JoinedPlayerQueueComposer", 3098);
    OpCodesManager.setComposerId("games.gamecenter.GameListComposer", 1753);
    OpCodesManager.setComposerId("games.gamecenter.StatusGameComposer", 1509);
    OpCodesManager.setComposerId("games.gamecenter.LoadGameComposer", 1814);
    OpCodesManager.setComposerId("games.gamecenter.GameAchievementsComposer", 277);
    
    OpCodesManager.setComposerId("games.snowwar.ArenaEnteredComposer", 2318);
    OpCodesManager.setComposerId("games.snowwar.EnterArenaComposer", 3246);
    OpCodesManager.setComposerId("games.snowwar.EnterArenaFailedComposer", 3476);
    OpCodesManager.setComposerId("games.snowwar.FriendsLeaderboardComposer", 3647);
    OpCodesManager.setComposerId("games.snowwar.FullGameStatusComposer", 539);
    OpCodesManager.setComposerId("games.snowwar.GameCancelledComposer", 237);
    OpCodesManager.setComposerId("games.snowwar.GameChatFromPlayerComposer", 3750);
    OpCodesManager.setComposerId("games.snowwar.GameCreatedComposer", 728);
    OpCodesManager.setComposerId("games.snowwar.GameDirectoryStatusComposer", 475);
    OpCodesManager.setComposerId("games.snowwar.GameEndingComposer", 1168);
    OpCodesManager.setComposerId("games.snowwar.GameLongDataComposer", 1337);
    OpCodesManager.setComposerId("games.snowwar.GameRejoinComposer", 3155);
    OpCodesManager.setComposerId("games.snowwar.GameStatusComposer", 2033);
    OpCodesManager.setComposerId("games.snowwar.InArenaQueueComposer", 310);
    OpCodesManager.setComposerId("games.snowwar.JoiningGameFailedComposer", 539);
    OpCodesManager.setComposerId("games.snowwar.PlayerExitedGameArenaComposer", 3982);
    OpCodesManager.setComposerId("games.snowwar.PlayerRematchesComposer", 3961);
    OpCodesManager.setComposerId("games.snowwar.StageEndingComposer", 457);
    OpCodesManager.setComposerId("games.snowwar.StageLoadComposer", 3647);
    OpCodesManager.setComposerId("games.snowwar.StageRunningComposer", 3829);
    OpCodesManager.setComposerId("games.snowwar.StageStartingComposer", 718);
    OpCodesManager.setComposerId("games.snowwar.StageStillLoadingComposer", 2292);
    OpCodesManager.setComposerId("games.snowwar.StartCounterComposer", 3066);
    OpCodesManager.setComposerId("games.snowwar.StartingGameFailedComposer", 1849);
    OpCodesManager.setComposerId("games.snowwar.StopCounterComposer", 3066);
    OpCodesManager.setComposerId("games.snowwar.TotalLeaderboardComposer", 3905);
    OpCodesManager.setComposerId("games.snowwar.UserBlockedComposer", 2865);
    OpCodesManager.setComposerId("games.snowwar.UserJoinedGameComposer", 1916);
    OpCodesManager.setComposerId("games.snowwar.UserLeftGameComposer", 425);
    OpCodesManager.setComposerId("games.snowwar.AccountGameStatusComposer", 959);
    OpCodesManager.setComposerId("games.snowwar.GameStartedComposer", 2665);
    
    OpCodesManager.setComposerId("games.snowwar.WeeklyLeaderboardComposer", 3155);
    
    OpCodesManager.setComposerId("handshake.GenericErrorComposer", 1821);
    OpCodesManager.setComposerId("handshake.UserLevelsComposer", 3469);
    OpCodesManager.setComposerId("handshake.AuthOKComposer", 1838);
    OpCodesManager.setComposerId("handshake.BannerTokenComposer", 2668);
    OpCodesManager.setComposerId("handshake.PerkAllowancesComposer", 535);
    OpCodesManager.setComposerId("handshake.ConnectionPingComposer", 3919);
    OpCodesManager.setComposerId("handshake.ServerPublicKeyComposer", 2759);
    OpCodesManager.setComposerId("handshake.UserDisconnectComposer", 4000);
    OpCodesManager.setComposerId("handshake.UserInfoComposer", 3674);
    
    OpCodesManager.setComposerId("help.CallForHelpMutedComposer", 3073);
    OpCodesManager.setComposerId("help.CallForHelpOpenComposer", 3827);
    OpCodesManager.setComposerId("help.CallForHelpPendingCallsComposer", 1058);
    OpCodesManager.setComposerId("help.CallForHelpReplyComposer", 1098);
    OpCodesManager.setComposerId("help.CallForHelpResultComposer", 625);
    OpCodesManager.setComposerId("help.IssueCloseNotificationComposer", 948);
    
    OpCodesManager.setComposerId("inventory.achievements.AchievementsComposer", 3115);
    OpCodesManager.setComposerId("inventory.achievements.AchievementsScoreComposer", 3590);
    
    OpCodesManager.setComposerId("inventory.avatareffect.EffectAddedComposer", 3515);
    OpCodesManager.setComposerId("inventory.avatareffect.EffectEnabledComposer", 1545);
    OpCodesManager.setComposerId("inventory.avatareffect.EffectStopedComposer", 1325);
    OpCodesManager.setComposerId("inventory.avatareffect.EffectsComposer", 574);
    
    OpCodesManager.setComposerId("inventory.badges.BadgesComposer", 2997);
    
    OpCodesManager.setComposerId("inventory.furni.FurniListComposer", 1388);
    OpCodesManager.setComposerId("inventory.furni.FurniListAddOrUpdateComposer", 735);
    OpCodesManager.setComposerId("inventory.furni.FurniListRemoveComposer", 2299);
    OpCodesManager.setComposerId("inventory.furni.FurniListUpdateComposer", 1178);
    OpCodesManager.setComposerId("inventory.furni.PostItPlacedComposer", 1358);
    
    OpCodesManager.setComposerId("inventory.pets.AddPetToInventoryComposer", 2268);
    OpCodesManager.setComposerId("inventory.pets.PetsInventoryComposer", 2065);
    OpCodesManager.setComposerId("inventory.pets.RemovePetInventoryComposer", 3220);
    
    OpCodesManager.setComposerId("inventory.bots.BotsInventoryComposer", 456);
    OpCodesManager.setComposerId("inventory.bots.AddBotToInventoryComposer", 897);
    OpCodesManager.setComposerId("inventory.bots.RemoveBotInventoryComposer", 1444);
    
    OpCodesManager.setComposerId("inventory.purse.CreditBalanceComposer", 545);
    
    OpCodesManager.setComposerId("inventory.trading.TradingAcceptComposer", 1139);
    OpCodesManager.setComposerId("inventory.trading.TradingAlreadyOpenComposer", 847);
    OpCodesManager.setComposerId("inventory.trading.TradingCloseComposer", 1291);
    OpCodesManager.setComposerId("inventory.trading.TradingCompletedComposer", 1052);
    OpCodesManager.setComposerId("inventory.trading.TradingConfirmationComposer", 496);
    OpCodesManager.setComposerId("inventory.trading.TradingItemListComposer", 1025);
    OpCodesManager.setComposerId("inventory.trading.TradingOpenComposer", 2081);
    
    OpCodesManager.setComposerId("landing.NextLimitedAvailableComposer", 3928);
    

    OpCodesManager.setComposerId("marketplace.MarketplaceConfigComposer", 3554);
    
    OpCodesManager.setComposerId("moderation.IssueInfoComposer", 2703);
    OpCodesManager.setComposerId("moderation.IssuePickFailedComposer", 2261);
    OpCodesManager.setComposerId("moderation.ModMessageComposer", 1992);
    OpCodesManager.setComposerId("moderation.ModeratorInitComposer", 1078);
    OpCodesManager.setComposerId("moderation.ModeratorRoomInfoComposer", 1204);
    OpCodesManager.setComposerId("moderation.ModeratorUserInfoComposer", 1896);
    
    OpCodesManager.setComposerId("navigator.OfficialRoomsComposer", 458);
    OpCodesManager.setComposerId("navigator.FlatCreatedComposer", 951);
    OpCodesManager.setComposerId("navigator.DoorbellUserComposer", 1296);
    OpCodesManager.setComposerId("room.session.FlatAccessibleComposer", 2808);
    OpCodesManager.setComposerId("navigator.DoorBellNoAnswerComposer", 1777);
    OpCodesManager.setComposerId("navigator.GuestRoomResultComposer", 398);
    
    OpCodesManager.setComposerId("navigator.CanCreateRoomComposer", 2912);
    OpCodesManager.setComposerId("navigator.EventComposer", 1261);
    OpCodesManager.setComposerId("navigator.FavouritesComposer", 5);
    OpCodesManager.setComposerId("navigator.FavouriteChangedComposer", 3216);
    OpCodesManager.setComposerId("navigator.FlatCategoriesComposer", 1007);
    OpCodesManager.setComposerId("navigator.NavigatorSettingsComposer", 104);
    OpCodesManager.setComposerId("navigator.GuestRoomSearchResultComposer", 766);
    OpCodesManager.setComposerId("navigator.PopularRoomTagsResultComposer", 2993);
    OpCodesManager.setComposerId("navigator.RoomRatingComposer", 2515);
    OpCodesManager.setComposerId("navigator.RoomUpdatedComposer", 1764);
    OpCodesManager.setComposerId("navigator.FlatAccessDeniedComposer", 905);
    OpCodesManager.setComposerId("navigator.RoomForwardComposer", 866);
    
    OpCodesManager.setComposerId("notifications.PetReceivedMessageComposer", 2928);
    OpCodesManager.setComposerId("notifications.PetRespectFailedComposer", 793);
    OpCodesManager.setComposerId("notifications.BuyNotificationComposer", 1062);
    OpCodesManager.setComposerId("notifications.ActivityPointsComposer", 2206);
    OpCodesManager.setComposerId("notifications.BroadcastImageComposer", 405);
    OpCodesManager.setComposerId("notifications.HabboActivityPointNotificationComposer", 36);
    OpCodesManager.setComposerId("notifications.ClubGiftNotificationComposer", 84);
    OpCodesManager.setComposerId("notifications.HabboBroadcastComposer", 2037);
    OpCodesManager.setComposerId("notifications.HabboBroadcastCustomComposer", 918);
    OpCodesManager.setComposerId("notifications.InfoFeedEnableComposer", 476);
    OpCodesManager.setComposerId("notifications.MOTDComposer", 3331);
    OpCodesManager.setComposerId("notifications.PetLevelNotificationComposer", 1539);
    OpCodesManager.setComposerId("notifications.UnseenItemsComposer", 2541);
    
    OpCodesManager.setComposerId("poll.PollContentsMessageComposer", 2019);
    OpCodesManager.setComposerId("poll.PollOfferMessageComposer", 669);
    OpCodesManager.setComposerId("poll.PollErrorMessageComposer", 859);
    
    OpCodesManager.setComposerId("recycler.RecyclerOkComposer", 629);
    OpCodesManager.setComposerId("recycler.RecyclerPrizesComposer", 1564);
    OpCodesManager.setComposerId("recycler.RecyclerStatusComposer", 3397);
    
    OpCodesManager.setComposerId("room.action.UserDanceComposer", 2063);
    OpCodesManager.setComposerId("room.action.AvatarExpressionComposer", 185);
    OpCodesManager.setComposerId("room.action.CarryObjectComposer", 2203);
    OpCodesManager.setComposerId("room.action.UserAsleepComposer", 2744);
    OpCodesManager.setComposerId("room.action.UserEffectComposer", 3190);
    
    OpCodesManager.setComposerId("room.chat.ChatSettingsComposer", 2165);
    OpCodesManager.setComposerId("room.chat.ChatComposer", 755);
    OpCodesManager.setComposerId("room.chat.ShoutComposer", 1957);
    OpCodesManager.setComposerId("room.chat.WhisperComposer", 3153);
    OpCodesManager.setComposerId("room.chat.FloodControlComposer", 3030);
    OpCodesManager.setComposerId("room.chat.UserTypingComposer", 2799);
    
    OpCodesManager.setComposerId("room.bots.BotSkillComposer", 2887);
    OpCodesManager.setComposerId("room.bots.BotErrorComposer", 1749);
    
    OpCodesManager.setComposerId("room.engine.FloorHeightMapComposer", 1094);
    OpCodesManager.setComposerId("room.engine.HeightMapComposer", 3513);
    OpCodesManager.setComposerId("room.engine.HeightMapUpdateComposer", 2287);
    OpCodesManager.setComposerId("room.engine.ObjectsComposer", 2711);
    OpCodesManager.setComposerId("room.engine.ObjectAddComposer", 563);
    OpCodesManager.setComposerId("room.engine.ObjectRemoveComposer", 265);
    OpCodesManager.setComposerId("room.engine.ObjectUpdateComposer", 2061);
    OpCodesManager.setComposerId("room.engine.ObjectDataUpdateComposer", 3798);
    OpCodesManager.setComposerId("room.engine.ObjectsDataUpdateComposer", 2896);
    OpCodesManager.setComposerId("room.engine.PublicRoomObjectsMessageParser", 3978);
    OpCodesManager.setComposerId("room.engine.ItemsComposer", 3097);
    OpCodesManager.setComposerId("room.engine.ItemAddComposer", 1632);
    OpCodesManager.setComposerId("room.engine.ItemRemoveComposer", 2902);
    OpCodesManager.setComposerId("room.engine.ItemUpdateComposer", 401);
    OpCodesManager.setComposerId("room.engine.UsersComposer", 492);
    OpCodesManager.setComposerId("room.engine.UserUpdateComposer", 2790);
    OpCodesManager.setComposerId("room.engine.UserChangeComposer", 3888);
    OpCodesManager.setComposerId("room.engine.UserRemoveComposer", 1096);
    OpCodesManager.setComposerId("room.engine.RoomVisualizationSettingsComposer", 324);
    OpCodesManager.setComposerId("room.engine.RoomEntryInfoComposer", 3179);
    OpCodesManager.setComposerId("room.engine.RoomPropertyComposer", 2139);
    OpCodesManager.setComposerId("room.engine.RoomCampaignAdsComposer", 555);
    OpCodesManager.setComposerId("room.engine.SlideObjectBundleComposer", 3862);
    OpCodesManager.setComposerId("room.engine.PlaceObjectErrorComposer", 30);
    
    OpCodesManager.setComposerId("room.furniture.RequestSpamWallPostItComposer", 3751);
    OpCodesManager.setComposerId("room.furniture.RoomDimmerPresetsComposer", 2770);
    
    OpCodesManager.setComposerId("room.permissions.YouAreControllerComposer", 144);
    OpCodesManager.setComposerId("room.permissions.YouAreNotControllerComposer", 3382);
    OpCodesManager.setComposerId("room.permissions.YouAreOwnerComposer", 1694);
    
    OpCodesManager.setComposerId("room.pets.PetCommandsComposer", 2076);
    OpCodesManager.setComposerId("room.pets.PetInfoComposer", 2834);
    OpCodesManager.setComposerId("room.pets.PetPlacingErrorComposer", 3343);
    
    OpCodesManager.setComposerId("room.publicroom.ParkBusCannotEnterComposer", 2131);
    
    OpCodesManager.setComposerId("room.session.YouArePlayingGameComposer", 2681);
    OpCodesManager.setComposerId("room.session.OpenConnectionComposer", 1803);
    OpCodesManager.setComposerId("room.session.RoomReadyComposer", 3026);
    OpCodesManager.setComposerId("room.session.CloseConnectionComposer", 3502);
    OpCodesManager.setComposerId("room.session.RoomQueueStatusComposer", 1728);
    OpCodesManager.setComposerId("room.session.YouAreSpectatorComposer", 3332);
    
    OpCodesManager.setComposerId("roomsettings.RoomMuteStateComposer", 3166);
    OpCodesManager.setComposerId("roomsettings.BannedUsersComposer", 2432);
    OpCodesManager.setComposerId("roomsettings.RoomBanRemoved", 2394);
    OpCodesManager.setComposerId("roomsettings.FlatControllerAddedComposer", 1791);
    OpCodesManager.setComposerId("roomsettings.FlatControllerRemovedComposer", 2100);
    OpCodesManager.setComposerId("roomsettings.FlatControllersComposer", 11);
    OpCodesManager.setComposerId("roomsettings.RoomSettingsDataComposer", 2619);
    OpCodesManager.setComposerId("roomsettings.RoomSettingsSavedComposer", 682);
    OpCodesManager.setComposerId("roomsettings.RoomSettingsErrorComposer", 493);
    
    OpCodesManager.setComposerId("users.UserSettingsComposer", 836);
    OpCodesManager.setComposerId("sound.JukeboxSongDisksComposer", 3896);
    OpCodesManager.setComposerId("sound.TraxSongInfoComposer", 3989);
    OpCodesManager.setComposerId("sound.UserSongDisksInventoryComposer", 3916);
    OpCodesManager.setComposerId("sound.NowPlayingComposer", 3520);
    OpCodesManager.setComposerId("sound.JukeboxPlayListFullComposer", 1495);
    
    OpCodesManager.setComposerId("talents.TalentTrackComposer", 3251);
    
    OpCodesManager.setComposerId("tracking.PingResponseComposer", 1199);
    
    OpCodesManager.setComposerId("userdefinedroomevents.OpenWiredComposer", 2490);
    OpCodesManager.setComposerId("userdefinedroomevents.WiredUpdateFailedComposer", 362);
    OpCodesManager.setComposerId("userdefinedroomevents.WiredUpdatedComposer", 44);
    OpCodesManager.setComposerId("userdefinedroomevents.WiredRewardNotificationComposer", 606);
    
    OpCodesManager.setComposerId("users.ApproveNameComposer", 785);
    OpCodesManager.setComposerId("users.UserTagsComposer", 1383);
    OpCodesManager.setComposerId("users.HabboGroupBadgesComposer", 560);
    OpCodesManager.setComposerId("users.IgnoredUsersComposer", 2982);
    OpCodesManager.setComposerId("users.NotifyUserNameChangeComposer", 55);
    OpCodesManager.setComposerId("users.PetRespectedComposer", 1327);
    OpCodesManager.setComposerId("users.RelationshipStatusComposer", 1759);
    OpCodesManager.setComposerId("users.UserBadgesComposer", 219);
    OpCodesManager.setComposerId("users.UserProfileInfoComposer", 744);
    OpCodesManager.setComposerId("users.ScrUserInfoComposer", 719);
    OpCodesManager.setComposerId("users.UserRespectedComposer", 921);
  }
  
  static
  {
    IncomingMessageEvent.callBacks[2242] = new GetLandingNewsParser();
    IncomingMessageEvent.callBacks[2479] = new RefreshLandingViewParser();
    
    IncomingMessageEvent.callBacks[669] = new GetLandingView6Parser();
    



    IncomingMessageEvent.callBacks[693] = new PutHorseSaddleParser();
    IncomingMessageEvent.callBacks[2544] = new HorseMountUpdateParser();
    IncomingMessageEvent.callBacks[2814] = new RemoveHorseSaddleParser();
    IncomingMessageEvent.callBacks[2898] = new RidingPermissionParser();
    
    IncomingMessageEvent.callBacks[1007] = new GetInterstitialParser();
    

    IncomingMessageEvent.callBacks[''] = new ChangeUserNameParser();
    IncomingMessageEvent.callBacks[2824] = new CheckUserNameParser();
    IncomingMessageEvent.callBacks[2472] = new GetWardrobeParser();
    IncomingMessageEvent.callBacks[2129] = new SaveWardrobeOutfitParser();
    
    IncomingMessageEvent.callBacks[1150] = new GetGiftWrappingConfigurationParser();
    IncomingMessageEvent.callBacks[779] = new GetHabboClubExtendOfferParser();
    IncomingMessageEvent.callBacks[1919] = new GetSellablePetBreedsParser();
    
    IncomingMessageEvent.callBacks[3370] = new GetIsOfferGiftableParser();
    IncomingMessageEvent.callBacks[1342] = new GetClubOffersParser();
    IncomingMessageEvent.callBacks[25] = new GetCatalogIndexParser();
    
    IncomingMessageEvent.callBacks[698] = new PurchaseFromCatalogParser();
    IncomingMessageEvent.callBacks[3324] = new PurchaseFromCatalogAsGiftParser();
    IncomingMessageEvent.callBacks[600] = new GetCatalogPageParser();
    IncomingMessageEvent.callBacks[3470] = new GetSnowWarTokensParser();
    IncomingMessageEvent.callBacks[1418] = new GetBundleDynamicDiscountsParser();
    IncomingMessageEvent.callBacks[2503] = new GetUniqueLimitedItemParser();
    
    IncomingMessageEvent.callBacks[3783] = new SetRelationshipStatusParser();
    IncomingMessageEvent.callBacks[3534] = new SendMsgParser();
    IncomingMessageEvent.callBacks[2228] = new SendRoomInviteParser();
    IncomingMessageEvent.callBacks[3688] = new AcceptFriendParser();
    IncomingMessageEvent.callBacks[3645] = new DeclineFriendParser();
    IncomingMessageEvent.callBacks[103] = new RequestBuddyParser();
    IncomingMessageEvent.callBacks[2456] = new RemoveFriendParser();
    IncomingMessageEvent.callBacks[3712] = new HabboSearchParser();
    IncomingMessageEvent.callBacks[3967] = new MessengerInitParser();
    IncomingMessageEvent.callBacks[615] = new FriendListUpdateParser();
    IncomingMessageEvent.callBacks[3116] = new GetBuddyRequestsParser();
    IncomingMessageEvent.callBacks[334] = new FollowFriendParser();
    

    IncomingMessageEvent.callBacks[''] = new SetDutyGuideToolParser();
    
    IncomingMessageEvent.callBacks[2967] = new JoinPlayerQueueParser();
    IncomingMessageEvent.callBacks[2607] = new GetStatusGameParser();
    IncomingMessageEvent.callBacks[2874] = new GetGameListParser();
    IncomingMessageEvent.callBacks[297] = new GetGameAchievementsParser();
    
    IncomingMessageEvent.callBacks[2715] = new CheckGameDirectoryStatusParser();
    IncomingMessageEvent.callBacks[1633] = new GetAccountGameStatusParser();
    IncomingMessageEvent.callBacks[3607] = new ExitGameParser();
    IncomingMessageEvent.callBacks[1300] = new QuickJoinGameParser();
    IncomingMessageEvent.callBacks[3487] = new LeaveGameParser();
    IncomingMessageEvent.callBacks['ü'] = new GameChatParser();
    IncomingMessageEvent.callBacks[455] = new LoadStageReadyParser();
    IncomingMessageEvent.callBacks[2291] = new SetUserMoveTargetParser();
    IncomingMessageEvent.callBacks[1308] = new RequestFullStatusUpdateParser();
    IncomingMessageEvent.callBacks[3088] = new MakeSnowballParser();
    IncomingMessageEvent.callBacks[3108] = new PlayAgainParser();
    IncomingMessageEvent.callBacks[3715] = new ThrowSnowballAtHumanParser();
    IncomingMessageEvent.callBacks[2590] = new ThrowSnowballAtPositionParser();
    








    IncomingMessageEvent.callBacks[2294] = new InfoRetrieveParser();
    IncomingMessageEvent.callBacks[667] = new PongParser();
    IncomingMessageEvent.callBacks[1712] = new InitCryptoParser();
    IncomingMessageEvent.callBacks[1777] = new SSOTicketParser();
    IncomingMessageEvent.callBacks[3410] = new DisconnectParser();
    IncomingMessageEvent.callBacks['à'] = new UniqueIDParser();
    IncomingMessageEvent.callBacks[2401] = new VersionCheckParser();
    IncomingMessageEvent.callBacks[2722] = new GenerateSecretKeyParser();
    
    IncomingMessageEvent.callBacks[2838] = new CallForHelpOpenParser();
    IncomingMessageEvent.callBacks[3797] = new CallForHelp2Parser();
    IncomingMessageEvent.callBacks[778] = new CallForHelpParser();
    IncomingMessageEvent.callBacks[2050] = new CallForHelpInRoomParser();
    IncomingMessageEvent.callBacks[268] = new CallForHelpRoomPanicParser();
    IncomingMessageEvent.callBacks[1946] = new CallForHelpRoomParser();
    
    IncomingMessageEvent.callBacks[3448] = new GetAchievementsParser();
    
    IncomingMessageEvent.callBacks[1560] = new AvatarEffectSelectedParser();
    IncomingMessageEvent.callBacks[1770] = new AvatarEffectActivatedParser();
    
    IncomingMessageEvent.callBacks[3887] = new GetBadgesParser();
    IncomingMessageEvent.callBacks['Ý'] = new SetActivatedBadgesParser();
    IncomingMessageEvent.callBacks[2612] = new GetBadgePointLimitsParser();
    
    IncomingMessageEvent.callBacks[2122] = new RequestRoomPropertySetParser();
    IncomingMessageEvent.callBacks[3588] = new RequestFurniInventoryParser();
    
    IncomingMessageEvent.callBacks[2316] = new RequestPetInventoryParser();
    
    IncomingMessageEvent.callBacks[1715] = new RequestBotInventoryParser();
    
    IncomingMessageEvent.callBacks[2425] = new GetCreditsInfoParser();
    
    IncomingMessageEvent.callBacks[64] = new OpenTradingParser();
    IncomingMessageEvent.callBacks[986] = new AddItemToTradeParser();
    IncomingMessageEvent.callBacks[3548] = new RemoveItemFromTradeParser();
    IncomingMessageEvent.callBacks[36] = new ConfirmAcceptTradingParser();
    IncomingMessageEvent.callBacks[1989] = new ConfirmDeclineTradingParser();
    IncomingMessageEvent.callBacks[3864] = new AcceptTradingParser();
    IncomingMessageEvent.callBacks[3574] = new UnacceptTradingParser();
    IncomingMessageEvent.callBacks[2634] = new CloseTradingParser();
    
    IncomingMessageEvent.callBacks[2973] = new GetNextLimitedAvailableParser();
    
    IncomingMessageEvent.callBacks[2380] = new GetMarketplaceConfigurationParser();
    IncomingMessageEvent.callBacks[3262] = new GetMarketplaceCanMakeOfferParser();
    
    IncomingMessageEvent.callBacks[2454] = new GetModeratorUserInfoParser();
    IncomingMessageEvent.callBacks[2067] = new GetModeratorRoomInfoParser();
    IncomingMessageEvent.callBacks[1623] = new ModeratorActionParser();
    IncomingMessageEvent.callBacks[1884] = new ModMessageParser();
    IncomingMessageEvent.callBacks[1958] = new ModKickParser();
    IncomingMessageEvent.callBacks[2412] = new ModMuteParser();
    IncomingMessageEvent.callBacks[2978] = new ModBanParser();
    IncomingMessageEvent.callBacks[527] = new ModerateRoomParser();
    IncomingMessageEvent.callBacks[1944] = new ModeratorRoomActionParser();
    IncomingMessageEvent.callBacks['¾'] = new PickIssuesParser();
    IncomingMessageEvent.callBacks[2540] = new ReleaseIssuesParser();
    IncomingMessageEvent.callBacks[3160] = new CloseIssuesParser();
    
    IncomingMessageEvent.callBacks[3288] = new AddFavouriteRoomParser();
    IncomingMessageEvent.callBacks[2349] = new DeleteFavouriteRoomParser();
    IncomingMessageEvent.callBacks[2420] = new CreateFlatParser();
    IncomingMessageEvent.callBacks[3209] = new RateFlatParser();
    


    IncomingMessageEvent.callBacks[2851] = new EditEventParser();
    IncomingMessageEvent.callBacks[2056] = new GetOfficialRoomsParser();
    IncomingMessageEvent.callBacks[1367] = new GetPopularRoomTagsParser();
    IncomingMessageEvent.callBacks[1762] = new UpdateNavigatorSettingsParser();
    IncomingMessageEvent.callBacks[3552] = new GetGuestRoomParser();
    IncomingMessageEvent.callBacks[1030] = new CanCreateRoomParser();
    IncomingMessageEvent.callBacks[1192] = new PopularRoomsSearchParser();
    IncomingMessageEvent.callBacks[604] = new RoomsWithHighestScoreSearchParser();
    IncomingMessageEvent.callBacks[2312] = new MyFriendsRoomsSearchParser();
    IncomingMessageEvent.callBacks[3094] = new RoomsWhereMyFriendsAreSearchParser();
    IncomingMessageEvent.callBacks[959] = new MyRoomsSearchParser();
    IncomingMessageEvent.callBacks[3437] = new MyFavouriteRoomsSearchParser();
    IncomingMessageEvent.callBacks[3122] = new MyRoomHistorySearchParser();
    IncomingMessageEvent.callBacks['ó'] = new RoomTextSearchParser();
    IncomingMessageEvent.callBacks[3094] = new RoomTagSearchParser();
    IncomingMessageEvent.callBacks[2091] = new LatestEventsSearchParser();
    IncomingMessageEvent.callBacks[329] = new GetUserFlatCatsParser();
    IncomingMessageEvent.callBacks[3889] = new ToggleStaffPickParser();
    
    IncomingMessageEvent.callBacks[2439] = new ResetUnseenItemsParser();
    
    IncomingMessageEvent.callBacks[723] = new PollStartParser();
    IncomingMessageEvent.callBacks[1036] = new PollRejectParser();
    IncomingMessageEvent.callBacks[1323] = new PollAnswerParser();
    

    IncomingMessageEvent.callBacks[1405] = new FriendRequestQuestCompleteParser();
    
    IncomingMessageEvent.callBacks[738] = new GetRecyclerPrizesParser();
    


    IncomingMessageEvent.callBacks[1276] = new UpdateFigureDataParser();
    
    IncomingMessageEvent.callBacks[876] = new KickUserParser();
    IncomingMessageEvent.callBacks[543] = new BanUserParser();
    IncomingMessageEvent.callBacks['¬'] = new AssignRightsParser();
    IncomingMessageEvent.callBacks[1999] = new RemoveRightsParser();
    IncomingMessageEvent.callBacks[3414] = new RemoveAllRightsParser();
    IncomingMessageEvent.callBacks[2154] = new RemoveBanParser();
    IncomingMessageEvent.callBacks[3197] = new MuteUserParser();
    IncomingMessageEvent.callBacks[1004] = new LetUserInParser();
    IncomingMessageEvent.callBacks[2631] = new DropCarryObjectParser();
    IncomingMessageEvent.callBacks[790] = new ShareCarryObjectParser();
    
    IncomingMessageEvent.callBacks[1395] = new LookToParser();
    IncomingMessageEvent.callBacks[1933] = new DanceParser();
    IncomingMessageEvent.callBacks[2478] = new SignParser();
    IncomingMessageEvent.callBacks[1760] = new ChangePostureParser();
    IncomingMessageEvent.callBacks[1871] = new ChangeMottoParser();
    IncomingMessageEvent.callBacks[3843] = new SetAvatarExpressionParser();
    
    IncomingMessageEvent.callBacks[3479] = new StartTypingParser();
    IncomingMessageEvent.callBacks[3215] = new CancelTypingParser();
    IncomingMessageEvent.callBacks[616] = new ChatParser();
    IncomingMessageEvent.callBacks[569] = new ShoutParser();
    IncomingMessageEvent.callBacks[1472] = new WhisperParser();
    
    IncomingMessageEvent.callBacks[1959] = new UpdateRoomMapParser();
    IncomingMessageEvent.callBacks[3382] = new PickupObjectParser();
    IncomingMessageEvent.callBacks[804] = new MoveObjectParser();
    IncomingMessageEvent.callBacks[1948] = new MoveAvatarParser();
    IncomingMessageEvent.callBacks[2642] = new PlaceObjectParser();
    IncomingMessageEvent.callBacks[1508] = new MoveWallItemParser();
    IncomingMessageEvent.callBacks[587] = new GetRoomEntryDataParser();
    IncomingMessageEvent.callBacks[792] = new UseFurnitureParser();
    IncomingMessageEvent.callBacks[2032] = new UseWallItemParser();
    IncomingMessageEvent.callBacks[387] = new SetClothingChangeDataParser();
    IncomingMessageEvent.callBacks[3046] = new PlacePetParser();
    IncomingMessageEvent.callBacks[1229] = new RemovePetFromFlatParser();
    IncomingMessageEvent.callBacks[3535] = new GetPetCommandsParser();
    IncomingMessageEvent.callBacks[996] = new GetRoomCampaignAdsParser();
    IncomingMessageEvent.callBacks[2073] = new PlaceRentalBotParser();
    IncomingMessageEvent.callBacks[3693] = new RemoveBotFromFlatParser();
    IncomingMessageEvent.callBacks[3948] = new GetRoomCompetitionParser();
    IncomingMessageEvent.callBacks[1003] = new ObjectSaveStuffDataParser();
    
    IncomingMessageEvent.callBacks[3919] = new RoomDimmerGetPresetsParser();
    IncomingMessageEvent.callBacks[3336] = new RoomDimmerSavePresetParser();
    IncomingMessageEvent.callBacks['Ü'] = new RoomDimmerChangeStateParser();
    
    IncomingMessageEvent.callBacks[3625] = new PlacePostItParser();
    IncomingMessageEvent.callBacks[813] = new OpenPostItParser();
    
    IncomingMessageEvent.callBacks[1610] = new AddSpamWallPostIt2Parser();
    IncomingMessageEvent.callBacks[2647] = new CreditFurniRedeemParser();
    IncomingMessageEvent.callBacks[2489] = new ThrowDiceParser();
    IncomingMessageEvent.callBacks[2867] = new DiceOffParser();
    
    IncomingMessageEvent.callBacks[2477] = new SetOutfitNameParser();
    IncomingMessageEvent.callBacks[1767] = new UpdateOutfitParser();
    IncomingMessageEvent.callBacks[''] = new PresentOpenParser();
    

    IncomingMessageEvent.callBacks[3239] = new GetPetInfoParser();
    IncomingMessageEvent.callBacks[1860] = new RespectPetParser();
    
    IncomingMessageEvent.callBacks[1098] = new SetBotSkillParser();
    IncomingMessageEvent.callBacks[3518] = new RequestBotSkillParser();
    
    IncomingMessageEvent.callBacks[1190] = new OpenFlatConnectionParser();
    
    IncomingMessageEvent.callBacks[817] = new GoToFlatParser();
    IncomingMessageEvent.callBacks[2065] = new ChangeQueueParser();
    IncomingMessageEvent.callBacks[549] = new QuitParser();
    
    IncomingMessageEvent.callBacks[1220] = new SetRoomMuteStateParser();
    IncomingMessageEvent.callBacks[3174] = new DeleteRoomParser();
    IncomingMessageEvent.callBacks[2523] = new GetRoomSettingsParser();
    IncomingMessageEvent.callBacks[3232] = new SaveRoomSettingsMessageEvent();
    IncomingMessageEvent.callBacks[3969] = new GetFlatControllersParser();
    IncomingMessageEvent.callBacks[115] = new GetBannedUsersParser();
    
    IncomingMessageEvent.callBacks[''] = new GetNowPlayingParser();
    IncomingMessageEvent.callBacks[3755] = new AddJukeboxDiskParser();
    IncomingMessageEvent.callBacks[2492] = new RemoveJukeboxDiskParser();
    IncomingMessageEvent.callBacks[2355] = new GetUserSongDisksParser();
    IncomingMessageEvent.callBacks[3577] = new GetJukeboxPlayListParser();
    IncomingMessageEvent.callBacks[752] = new GetSongInfoParser();
    IncomingMessageEvent.callBacks[1691] = new SetSoundSettingsParser();
    
    IncomingMessageEvent.callBacks[2386] = new GetTalentTrackParser();
    
    IncomingMessageEvent.callBacks[3084] = new LatencyPingRequestParser();
    IncomingMessageEvent.callBacks[''] = new LatencyPingReportParser();
    IncomingMessageEvent.callBacks[2621] = new PerformanceLogParser();
    IncomingMessageEvent.callBacks[2040] = new EventLogParser();
    
    IncomingMessageEvent.callBacks[3917] = new UpdateTriggerParser();
    IncomingMessageEvent.callBacks[1329] = new UpdateActionParser();
    IncomingMessageEvent.callBacks[750] = new UpdateConditionParser();
    IncomingMessageEvent.callBacks[1932] = new OpenParser();
    
    IncomingMessageEvent.callBacks[2143] = new ScrGetUserInfoParser();
    IncomingMessageEvent.callBacks[851] = new GetExtendedProfileParser();
    IncomingMessageEvent.callBacks[1819] = new ApproveNameParser();
    IncomingMessageEvent.callBacks[3626] = new GetUserTagsParser();
    IncomingMessageEvent.callBacks[3342] = new GetIgnoredUsersParser();
    IncomingMessageEvent.callBacks[2525] = new GetRelationshipStatusParser();
    IncomingMessageEvent.callBacks[2683] = new GetSelectedBadgesParser();
    IncomingMessageEvent.callBacks[3244] = new RespectUserParser();
    IncomingMessageEvent.callBacks[2994] = new GetUserNotificationsParser();
    IncomingMessageEvent.callBacks[3953] = new GetHabboGroupBadgesParser();
    IncomingMessageEvent.callBacks[518] = new GetUserSettingsParser();
    IncomingMessageEvent.callBacks[2435] = new SetUserChatSettingMessageEvent();
  }
}


