package ru.kpfu.itis.arifulina.base;

public class ParamsKey {
    public static final String AUTHORITY_USER = "ROLE_USER";
    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";

    // request mappings
    public static final String INDEX_RM = "/index";
    public static final String SIGN_IN_RM = "/sign_in";
    public static final String SIGN_UP_RM = "/sign_up";
    public static final String HELLO_RM = "/hello";
    public static final String PROFILE_RM = "/profile";
    public static final String ADMIN_RM = "/admin";
    public static final String EXCHANGE_RATES_RM = "/exchange-rates";
    public static final String KAZAN_WEATHER_RM = "/kazan-weather";
    public static final String LOGIN_PROCESSING_RM = "/login/processing";
    public static final String LOGIN_FAILURE_RM = "/sign_in?error=true";
    public static final String NOW_RM = "/now";
    public static final String USERS_RM = "/users";
    public static final String USER_RM = "/user";
    public static final String VERIFICATION_RM = "/verification";
    public static final String GLOBAL_CHAT_RM = "/global_chat";
    public static final String GROUP_CHATS_RM = "/group_chats";
    public static final String HISTORY_RM = "/history";

    // params
    public static final String USERNAME_PARAM = "username";
    public static final String PASSWORD_PARAM = "password";
    public static final String CODE_PARAM = "code";
    public static final String AUTH_FAIL_KEY = "error";
    public static final String NAME_PARAM = "name";
    public static final String ROOM_PARAM = "room";
    public static final String ROOM_PATH_VAR = "/{" + ROOM_PARAM + "}";

    // view names
    public static final String INDEX_VN = "index";
    public static final String SIGN_IN_VN = "sign_in";
    public static final String SIGN_UP_VN = "sign_up";
    public static final String PROFILE_VN = "profile";
    public static final String SIGN_UP_SUCCESS_VN = "sign_up_success";
    public static final String VERIFICATION_SUCCESS_VN = "verification_success";
    public static final String VERIFICATION_FAILED_VN = "verification_failed";
    public static final String GLOBAL_CHAT_VN = "global_chat";
    public static final String GROUP_CHATS_VN = "group_chats";

    // chat
    public static final String BROKER_DEST_PREFIX = "/topic";
    public static final String APP_DEST_PREFIX = "/app";
    public static final String ENDPOINT_PATH = "/message-websocket";
    public static final String MESSAGE_MAPPING = "/message";
    public static final String ROOM_MESSAGE_MAPPING = "/room-message";
    public static final String SERVER_SENDER_NAME = "server";


}
