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

    // params
    public static final String USERNAME_PARAM = "username";
    public static final String PASSWORD_PARAM = "password";
    public static final String CODE_PARAM = "code";
    public static final String AUTH_FAIL_KEY = "error";
    public static final String NAME_PARAM = "name";

    // view names
    public static final String INDEX_VN = "index";
    public static final String SIGN_IN_VN = "sign_in";
    public static final String SIGN_UP_VN = "sign_up";
    public static final String PROFILE_VN = "profile";
    public static final String SIGN_UP_SUCCESS_VN = "sign_up_success";
    public static final String VERIFICATION_SUCCESS_VN = "verification_success";
    public static final String VERIFICATION_FAILED_VN = "verification_failed";

}
