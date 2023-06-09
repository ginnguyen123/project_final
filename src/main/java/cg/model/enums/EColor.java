package cg.model.enums;

import cg.exception.DataInputException;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public enum EColor {
    BLACK("BLACK"),
    WHITE("WHITE"),
    RED("RED"),
    GRAY("GRAY"),
    PURPLE("PURPLE"),
    GREEN("GREEN"),
    BLUE("BLUE"),
    YELLOW("YELLOW"),

    VIOLET("VIOLET");

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

    public static EColor getEColor(String value){
        for (EColor ecolor : EColor.values()){
            if (ecolor.getValue().equalsIgnoreCase(value)){
                return ecolor;
            }
        }
        return null;
    }


    public static List<EColor> getEnumValues(){
        return new ArrayList<>(Arrays.asList(values()));
    }

}
