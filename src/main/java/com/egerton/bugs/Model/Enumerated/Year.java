package com.egerton.bugs.Model.Enumerated;

public enum Year {
    ONE("ONE"), TWO("TWO"), THREE("THREE"), FOUR("FOUR"), FIVE("FIVE");

    private final String year;

    Year(String year){
        this.year = year;
    }

    public String getYear() {
        return year;
    }
}
