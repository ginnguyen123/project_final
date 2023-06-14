package cg.model.enums;

import cg.exception.DataInputException;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum EProductStatus {
    STOCKING ("Stocking"),
    OUT_STOCK ("Out-stock");

    private final String value;

    EProductStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static final Map<String, EProductStatus> NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(EProductStatus::toString, Function.identity()));

    public static EProductStatus fromString(final String name) {
        EProductStatus productStatus = NAME_MAP.get(name);
        if (null == productStatus) {
            throw new DataInputException(String.format("'%s' has no corresponding value. Accepted values: %s", name, Arrays.asList(values())));
        }
        return productStatus;
    }

    public static EProductStatus getEProductStatus(String value){
        for (EProductStatus status: EProductStatus.values()){
            if (status.getValue().equalsIgnoreCase(value)){
                return status;
            }
        }
        return null;
    }
}
