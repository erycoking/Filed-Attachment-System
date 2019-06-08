package com.egerton.bugs.Model.Enumerated;

public enum Role {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_STUDENT("ROLE_STUDENT"),
    ROLE_BUGS("ROLE_BUGS"),
    ROLE_STAFF("ROLE_STAFF"),
    ROLE_COORDINATOR("ROLE_COORDINATOR"),
    ROLE_COMPANY("ROLE_COMPANY");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}
