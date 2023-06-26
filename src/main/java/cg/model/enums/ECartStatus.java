package cg.model.enums;

import cg.exception.DataInputException;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ECartStatus {

    PAID ("PAID"),
    UNPAID ("UNPAID");

    private final String value;

    ECartStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static final Map<String, ECartStatus> NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(ECartStatus::toString, Function.identity()));

    public static ECartStatus fromString(final String name) {
        ECartStatus eCartStatus = NAME_MAP.get(name);
        if (null == eCartStatus) {
            throw new DataInputException(String.format("'%s' has no corresponding value. Accepted values: %s", name, Arrays.asList(values())));
        }
        return eCartStatus;
    }

    public static ECartStatus getEProductStatus(String value){
        for (ECartStatus status: ECartStatus.values()){
            if (status.getValue().equalsIgnoreCase(value)){
                return status;
            }
        }
        return null;
    }
}
