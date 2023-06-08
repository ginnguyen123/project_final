package cg.model.enums;

import java.util.LinkedHashMap;
import java.util.Map;

public enum ECategory {
    HOME("showing"),
    NO_HOME("notShowing");

    private String value;

    ECategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }



}
