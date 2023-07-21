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
}
