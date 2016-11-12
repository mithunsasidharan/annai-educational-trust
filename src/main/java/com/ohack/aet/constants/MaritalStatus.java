package com.ohack.aet.constants;


public enum MaritalStatus {

    Un_MARRIED("Unmarried"),
    MARRIED("Married"),
    WIDOW("Widow");

    private final String displayName;

    MaritalStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
