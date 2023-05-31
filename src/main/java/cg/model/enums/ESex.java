package cg.model.enums;

import java.awt.datatransfer.FlavorEvent;

public enum ESex {
    MALE ("Male"),
    FEMALE ("Female");

    private final String value;

    ESex(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
