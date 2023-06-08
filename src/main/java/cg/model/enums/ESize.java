package cg.model.enums;

import cg.exception.DataInputException;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ESize {
    XS("XS") ,S("S"), M("M"), L("L"), XL("XL"), XXL("XXL"),
    CHILD_14("14"), CHILD_15("15"), CHILD_16("16"), CHILD_17("17"), CHILD_18("18"), CHILD_19("19"), CHILD_20("20"),
    CHILD_21("21"), CHILD_22("22"), CHILD_23("23"),
    ADULT_36("36"), ADULT_37("37"), ADULT_38("38"), ADULT_39("39"), ADULT_40("40"), ADULT_41("41"), ADULT_42("42"),
    ADULT_43("43"), ADULT_44("44"), ADULT_45("45");

    private final String value;

    ESize(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


    private static final Map<String, ESize> NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(ESize::toString, Function.identity()));

    public static ESize fromString(final String name) {
        ESize eSize = NAME_MAP.get(name);
        if (null == eSize) {
            throw new DataInputException(String.format("'%s' has no corresponding value. Accepted values: %s", name, Arrays.asList(values())));
        }
        return eSize;
    }

    public static ESize getESize(String value){
        for (ESize esize: values()){
            if (esize.getValue().equalsIgnoreCase(value)){
                return esize;
            }
        }
        return null;
    }


}
