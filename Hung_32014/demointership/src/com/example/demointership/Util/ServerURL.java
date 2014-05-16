package com.example.demointership.Util;

public class ServerURL {
	// public static String URL = "http://dev.magrabbit.com:8998";
	// public static String URL = "http://54.245.230.7:9000";
	public static String URL = "http://50.23.223.154:3000";
	// public static String URL = "http://50.23.223.155:3000";

	private static final String KEY_LOGIN_NORMAL = "/users/login.json";
	private static final String KEY_LOGIN_SOCIAL = "/users/check_token.json";
	private static final String KEY_SIGN_UP = "/users/register.json";
	private static final String KEY_SIGN_UP_SOCIAL = "/users/register_social.json";
	private static final String KEY_FORGOT = "/users/forgot.json";
	private static final String KEY_NORMALSEARCH = "/locations/normal_search.json?";
	private static final String KEY_LOGOUT = "/users/logout.json";
	private static final String KEY_ADVANCEDSEARCH = "/locations/advance.json?";
	private static final String KEY_LOCATION = "/locations/";
	private static final String KEY_AVATAR = "/users/avatar.json";
	private static final String KEY_UPDATE_USER = "/users/settings.json";
	private static final String KEY_ITEM = "/items/";
	private static final String KEY_MY_POINT = "/points/";
	private static final String KEY_GET_ALLSEARCHPROFILE = "/users/searchprofile.json?";
	private static final String KEY_GET_A_SEARCHPROFILE = "users/get searchprofile.json";


	private static final String KEY_GET_RUNDEFAULTSEARCHPROFILE = "/locations/run_default.json?";
	private static final String KEY_POST_ADDSEARCHPROFILE = "/users/addsearchprofile.json";
	private static final String KEY_POST_EDITSEARCHPROFILE = "/users/editsearchprofile.json";
	private static final String KEY_GET_DELETESEARCHPROFILE = "/users/deletesearchprofile.json?";
	private static final String KEY_POST_SETSEARCHPROFILE = "/users/set_default.json";
	private static final String KEY_GET_ALL_MENUTYPE = "/locations/menutype.json?";
	private static final String KEY_GET_ALL_ITEMTYPE = "/locations/itemtype.json?";
	private static final String KEY_POST_SHARE_POINT_SMS = "/invited/invite_sms_share.json";
	private static final String KEY_POST_SHARE_POINT_EMAIL = "/invited/invite_email_share.json";
	private static final String KEY_POST_SHARE_POINT_FRIEND = "/points/point_share_friend.json";
	private static final String KEY_INVITE_FIREND_SMS = "/invited/invite_sms.json";
	private static final String KEY_INVITE_FIREND_EMAIL = "/invited/invite_email.json";
	private static final String KEY_MYFRIENDS = "/invited/";
	private static final String KEY_MYNOTIFICATION_GLOBAL = "/message_global.json?";
	private static final String KEY_MYNOTIFICATION_CHAIN = "/message_restaurant.json?";
	private static final String KEY_MYNOTIFICATION_RESTAURANT = "/get_message.json?";
	private static final String KEY_MYNOTIFICATION_MESSAGE = "/get_detail_message.json?";
	private static final String KEY_MYNOTIFICATION_REPLY_MESSAGE = "/points/reply_message.json";
	private static final String KEY_UPDATE_RESTAURANT_COMMENT = "/locations/:location_id/update_comment.json";
	private static final String KEY_UPDATE_MENUITEM_COMMENT = "/items/:item_id/update_comment.json";
	private static final String KEY_GET_MYORDER_GLOBAL = "/order/order_global.json?";
	private static final String KEY_GET_MYORDER_CHAIN = "/order/order_chain.json?";
	private static final String KEY_GET_MYORDER_BY_LOCATION = "/order/orders.json?";
	private static final String KEY_GET_MYORDER_DETAILS = "/order/35/details.json?";
	private static final String KEY_GET_MYFAVORITE = "/favourite_global.json?";
	private static final String KEY_GET_MYFAVORITE_CHAIN = "/favourite_restaurant.json?";
	private static final String KEY_GET_MYFAVORITE_RESTAURANT = "/favourite_items.json?";
	private static final String KEY_CATEGORY = "/category/";
	private static final String KEY_MY_FEEDBACK = "/users/feedback.json";
	private static final String KEY_GET_CURRENT_ORDER = "/order/my_order.json";
	private static final String KEY_UPDATE_ORDER = "/order/update_order.json";
	private static final String KEY_MENUITEM_NEW_ORDER = "/order/new_order.json";
	private static final String KEY_MENUITEM_UPDATE_ORDER = "/order/update_item.json";
	private static final String KEY_MYINSTRUCTION = "/instruction/get_instruction_categories.json?";
	private static final String KEY_MYINSTRUCTION_ITEMS = "/instruction/get_instruction_items.json?";
	private static final String KEY_POST_SERVER_RATING = "/order/server_rating.json";
	private static final String KEY_GET_ALL_UNREAD_MSG = "/get_unread_by_chain.json?";
	private static final String KEY_PAY_ORDER = "/order/pay_order.json";
	private static final String KEY_GET_DELETE_NOTIFICATION_BY_RESTAURANT = "/delete_message_by_restaurant.json";
	private static final String KEY_GET_DELETE_NOTIFICATION_BY_MESSAGE = "/delete_message.json";
	private static final String KEY_URL_ADD_RATING_ORDER = "/order/add_update_rating_order.json";
	private static final String KEY_URL_SHARE_RESTAURANT_ADD_CONTACT = "/locations/share_via_social.json";
	private static final String KEY_URL_SHARE_MENUITEM_ADD_CONTACT = "/locations/share_via_social.json";
	private static final String KEY_ADD_FRIEND = "/invited/add_friend.json";
	private static final String KEY_SEARCH_FRIEND = "/users/searchfriend.json?";

	public static String getURL() {
		return URL;
	}

	public static void setURL(String uRL) {
		URL = uRL;
	}

	public static String getKeyLoginNormal() {
		return KEY_LOGIN_NORMAL;
	}

	public static String getKeyLoginSocial() {
		return KEY_LOGIN_SOCIAL;
	}

	public static String getKeySignUp() {
		return KEY_SIGN_UP;
	}

	public static String getKeySignUpSocial() {
		return KEY_SIGN_UP_SOCIAL;
	}

	public static String getKeyForgot() {
		return KEY_FORGOT;
	}

	public static String getKeyNormalsearch() {
		return KEY_NORMALSEARCH;
	}

	public static String getKeyLogout() {
		return KEY_LOGOUT;
	}

	public static String getKeyAdvancedsearch() {
		return KEY_ADVANCEDSEARCH;
	}

	public static String getKeyLocation() {
		return KEY_LOCATION;
	}

	public static String getKeyAvatar() {
		return KEY_AVATAR;
	}

	public static String getKeyUpdateUser() {
		return KEY_UPDATE_USER;
	}

	public static String getKeyItem() {
		return KEY_ITEM;
	}

	public static String getKeyMyPoint() {
		return KEY_MY_POINT;
	}

	public static String getKeyGetAllsearchprofile() {
		return KEY_GET_ALLSEARCHPROFILE;
	}

	public static String getKeyGetRundefaultsearchprofile() {
		return KEY_GET_RUNDEFAULTSEARCHPROFILE;
	}

	public static String getKeyPostAddsearchprofile() {
		return KEY_POST_ADDSEARCHPROFILE;
	}

	public static String getKeyPostEditsearchprofile() {
		return KEY_POST_EDITSEARCHPROFILE;
	}

	public static String getKeyGetDeletesearchprofile() {
		return KEY_GET_DELETESEARCHPROFILE;
	}

	public static String getKeyPostSetsearchprofile() {
		return KEY_POST_SETSEARCHPROFILE;
	}

	public static String getKeyGetAllMenutype() {
		return KEY_GET_ALL_MENUTYPE;
	}

	public static String getKeyGetAllItemtype() {
		return KEY_GET_ALL_ITEMTYPE;
	}

	public static String getKeyPostSharePointSms() {
		return KEY_POST_SHARE_POINT_SMS;
	}

	public static String getKeyPostSharePointEmail() {
		return KEY_POST_SHARE_POINT_EMAIL;
	}

	public static String getKeyPostSharePointFriend() {
		return KEY_POST_SHARE_POINT_FRIEND;
	}

	public static String getKeyInviteFirendSms() {
		return KEY_INVITE_FIREND_SMS;
	}

	public static String getKeyInviteFirendEmail() {
		return KEY_INVITE_FIREND_EMAIL;
	}

	public static String getKeyMyfriends() {
		return KEY_MYFRIENDS;
	}

	public static String getKeyMynotificationGlobal() {
		return KEY_MYNOTIFICATION_GLOBAL;
	}

	public static String getKeyMynotificationChain() {
		return KEY_MYNOTIFICATION_CHAIN;
	}

	public static String getKeyMynotificationRestaurant() {
		return KEY_MYNOTIFICATION_RESTAURANT;
	}

	public static String getKeyMynotificationMessage() {
		return KEY_MYNOTIFICATION_MESSAGE;
	}

	public static String getKeyMynotificationReplyMessage() {
		return KEY_MYNOTIFICATION_REPLY_MESSAGE;
	}

	public static String getKeyUpdateRestaurantComment() {
		return KEY_UPDATE_RESTAURANT_COMMENT;
	}

	public static String getKeyUpdateMenuitemComment() {
		return KEY_UPDATE_MENUITEM_COMMENT;
	}

	public static String getKeyGetMyorderGlobal() {
		return KEY_GET_MYORDER_GLOBAL;
	}

	public static String getKeyGetMyorderChain() {
		return KEY_GET_MYORDER_CHAIN;
	}

	public static String getKeyGetMyorderByLocation() {
		return KEY_GET_MYORDER_BY_LOCATION;
	}

	public static String getKeyGetMyorderDetails() {
		return KEY_GET_MYORDER_DETAILS;
	}

	public static String getKeyGetMyfavorite() {
		return KEY_GET_MYFAVORITE;
	}

	public static String getKeyGetMyfavoriteChain() {
		return KEY_GET_MYFAVORITE_CHAIN;
	}

	public static String getKeyGetMyfavoriteRestaurant() {
		return KEY_GET_MYFAVORITE_RESTAURANT;
	}

	public static String getKeyCategory() {
		return KEY_CATEGORY;
	}

	public static String getKeyMyFeedback() {
		return KEY_MY_FEEDBACK;
	}

	public static String getKeyGetCurrentOrder() {
		return KEY_GET_CURRENT_ORDER;
	}

	public static String getKeyUpdateOrder() {
		return KEY_UPDATE_ORDER;
	}

	public static String getKeyMenuitemNewOrder() {
		return KEY_MENUITEM_NEW_ORDER;
	}

	public static String getKeyMenuitemUpdateOrder() {
		return KEY_MENUITEM_UPDATE_ORDER;
	}

	public static String getKeyMyinstruction() {
		return KEY_MYINSTRUCTION;
	}

	public static String getKeyMyinstructionItems() {
		return KEY_MYINSTRUCTION_ITEMS;
	}

	public static String getKeyPostServerRating() {
		return KEY_POST_SERVER_RATING;
	}

	public static String getKeyGetAllUnreadMsg() {
		return KEY_GET_ALL_UNREAD_MSG;
	}

	public static String getKeyPayOrder() {
		return KEY_PAY_ORDER;
	}

	public static String getKeyGetDeleteNotificationByRestaurant() {
		return KEY_GET_DELETE_NOTIFICATION_BY_RESTAURANT;
	}

	public static String getKeyGetDeleteNotificationByMessage() {
		return KEY_GET_DELETE_NOTIFICATION_BY_MESSAGE;
	}

	public static String getKeyUrlAddRatingOrder() {
		return KEY_URL_ADD_RATING_ORDER;
	}

	public static String getKeyUrlShareRestaurantAddContact() {
		return KEY_URL_SHARE_RESTAURANT_ADD_CONTACT;
	}

	public static String getKeyUrlShareMenuitemAddContact() {
		return KEY_URL_SHARE_MENUITEM_ADD_CONTACT;
	}

	public static String getKeySearchFriend() {
		return KEY_SEARCH_FRIEND;
	}

	public static String getKeyGetNotificationGroup() {
		return KEY_GET_NOTIFICATION_GROUP;
	}

	public static String getKeyGetFriendInvitation() {
		return KEY_GET_FRIEND_INVITATION;
	}

	public static String getKeyUrlFriendConfirm() {
		return KEY_URL_FRIEND_CONFIRM;
	}

	public static String getKeyUrlChangeStatusMessage() {
		return KEY_URL_CHANGE_STATUS_MESSAGE;
	}

	private static final String KEY_GET_NOTIFICATION_GROUP = "/invited/get_total_message.json?";
	private static final String KEY_GET_FRIEND_INVITATION = "/invited/get_invitation.json?";
	private static final String KEY_URL_FRIEND_CONFIRM = "/invited/reply_friend_request.json";
	private static final String KEY_URL_CHANGE_STATUS_MESSAGE = "/invited/change_status_message.json";

	public static String getKeyAddFriend() {
		return KEY_ADD_FRIEND;
	}
	public static String getKeyGetASearchprofile() {
		return KEY_GET_A_SEARCHPROFILE;
	}
}
