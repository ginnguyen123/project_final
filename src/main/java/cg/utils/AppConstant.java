package cg.utils;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class AppConstant {
    public static final List<String> IMAGE_TYPES = Arrays.asList("image/jpeg", "image/png");
    public static final String INVALID_IMAGE_MESSAGE = "Only image files with .png or .jpeg";
    public static final String INVALID_DATE_MESSAGE = "The wrong date format! Format by yyyy-mm-dd.";
    public static final String INVALID_PRICE_FILTER_MESSAGE = "The filter by invalid prices.";
    public static final String ENTITY_NOT_EXIT_ERROR = "Data is not correct.";
    public static final String NOT_BLANK_FIELD_ERROR = " not blank.";
    public static final String REQUIED_IS_NUMBER = " required to enter number.";
    public static final String ACCOUNT_EXISTS = "Account already exists.";
    public static final String EMAIL_EXISTS = "Email already exists.";

    public static String GOOGLE_CLIENT_ID = "8529351399-hgik9nvcu2o24vbdqr6av0caahdtgeab.apps.googleusercontent.com";
    public static String GOOGLE_CLIENT_SECRET = "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";
    public static String GOOGLE_REDIRECT_URI = "http://localhost:8086/google";
    public static String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
    public static String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    public static String GOOGLE_GRANT_TYPE = "authorization_code";
}
