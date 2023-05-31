package cg.model.enums;

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
        return this.value;
    }

}
