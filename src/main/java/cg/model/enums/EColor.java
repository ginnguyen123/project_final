package cg.model.enums;

import cg.exception.DataInputException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public enum EColor {
    BLACK("Black"),
    WHITE("White"),
    RED("Red"),
    GRAY("Gray"),
    PURPLE("Purple"),
    GREEN("Green"),
    BLUE("Blue"),
    YELLOW("Yellow"),

    VIOLET("Violet");

    private final String value;

    EColor(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static final Map<String, EColor> NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(EColor::toString, Function.identity()));

    public static EColor fromString(final String name) {
        EColor eColor = NAME_MAP.get(name);
        if (null == eColor) {
            throw new DataInputException(String.format("'%s' has no corresponding value. Accepted values: %s", name, Arrays.asList(values())));
        }
        return eColor;
    }

}
