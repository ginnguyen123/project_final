package cg.model.enums;

public enum ECategoryStatus {
    SUMMER("summer"),
    WINTER("winter");

    private String value;

    ECategoryStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static ECategoryStatus getECategoryStatus(String value){
        for (ECategoryStatus eCategoryStatus : ECategoryStatus.values()){
            if (eCategoryStatus.getValue().equalsIgnoreCase(value)){
                return eCategoryStatus;
            }
        }
        return null;
    }
}
