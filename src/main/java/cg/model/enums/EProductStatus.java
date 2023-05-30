package cg.model.enums;

public enum EProductStatus {
    STOCKING ("Stocking"),
    OUT_OF_STOCK ("Out-of-stock"),
    ENTERING ("entering");

    private final String value;

    EProductStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
