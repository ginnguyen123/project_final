package cg.utils;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class AppConstant {
    public static final List<String> IMAGE_TYPES = Arrays.asList("image/jpeg", "image/png");
    public static final String INVALID_IMAGE_MESSAGE = "Only image files with .png or .jpeg";

    public static final String INVALID_DATE_MESSAGE = "The wrong date format! Format by yyyy-mm-dd.";
}
