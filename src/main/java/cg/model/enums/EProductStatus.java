package cg.model.enums;

public enum EProductStatus {
    STOCKING ("Stocking"),
    OUT_STOCK ("Out-stock"),
    ENTERING ("Entering");

    private final String value;

    EProductStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
