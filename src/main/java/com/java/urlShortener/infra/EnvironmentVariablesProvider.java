package com.java.urlShortener.infra;

public class EnvironmentVariablesProvider {

    public static String getVariable(String variableName) {
        return System.getenv().get(variableName);
    }
}