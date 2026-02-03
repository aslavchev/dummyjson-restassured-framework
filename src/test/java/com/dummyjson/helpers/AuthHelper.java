package com.dummyjson.helpers;

import io.qameta.allure.Step;

public class AuthHelper {

    @Step("Build login request body for user: {username}")
    public static String buildLoginBody(String username, String password) {
        return String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
    }
}
