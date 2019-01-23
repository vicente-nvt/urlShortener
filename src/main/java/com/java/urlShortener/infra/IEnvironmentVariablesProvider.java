package com.java.urlShortener.infra;

public interface  IEnvironmentVariablesProvider {

    String getVariable(String variableName);
}