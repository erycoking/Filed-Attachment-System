package com.egerton.bugs.Model.Enumerated;

public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}
