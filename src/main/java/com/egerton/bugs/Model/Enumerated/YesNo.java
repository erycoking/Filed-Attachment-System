package com.egerton.bugs.Model.Enumerated;

public enum YesNo {
    YES("YES"), NO("NO");

    private final String yesNo;

    YesNo(String yesNo) {
        this.yesNo = yesNo;
    }

    public String getYesNo() {
        return yesNo;
    }
}
