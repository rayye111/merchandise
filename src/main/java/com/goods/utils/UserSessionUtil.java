package com.goods.utils;

public class UserSessionUtil {
    private static UserSessionUtil instance;
    private String currentUserEmail;
    private String currentUserPassword;
    private String currentUserType;

    private UserSessionUtil() {
    }

    public static UserSessionUtil getInstance() {
        if (instance == null) {
            instance = new UserSessionUtil();
        }
        return instance;
    }

    public static void setInstance(UserSessionUtil instance) {
        UserSessionUtil.instance = instance;
    }

    public String getCurrentUserPassword() {
        return currentUserPassword;
    }

    public void setCurrentUserPassword(String currentUserPassword) {
        this.currentUserPassword = currentUserPassword;
    }

    public String getCurrentUserType() {
        return currentUserType;
    }

    public void setCurrentUserType(String currentUserType) {
        this.currentUserType = currentUserType;
    }

    public void setCurrentUserEmail(String email) {
        this.currentUserEmail = email;
    }

    public String getCurrentUserEmail() {
        return currentUserEmail;
    }
}

