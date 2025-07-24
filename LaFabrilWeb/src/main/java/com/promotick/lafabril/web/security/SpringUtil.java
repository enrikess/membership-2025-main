package com.promotick.lafabril.web.security;

public class SpringUtil {

    public static final String LOGIN_PROCESSING_URL = "/autenticar";
    public static final String LOGIN_PAGE = "/login";
    public static final String RECOVERY_PAGE = "/recuperar/**";
    public static final String REGISTER_PAGE = "/registro/**";
    public static final String AUTHENTICATION_FAILURE_URL = "/login?error";
    public static final String USERNAME_PARAMETER = "usernameTipoDoc";
    public static final String PASSWORD_PARAMETER = "password";
    public static final String LOGOUT_URL = "/logout";
    public static final String LOGOUT_SUCCESS_URL = "/login?logout";
    public static final String PREFIJO_ROL = "ROL_";
    public static final String JSESSIONID = "JSESSIONID";
    public static final String HEADER_AJAX = "X-Ajax-call";
    public static final String ERROR = "/error";
    public static final String RECOMPENSAS = "/recompensas/**";

}

