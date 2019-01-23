package com.java.urlShortener.infra;

import org.springframework.stereotype.Component;

@Component("environmentVariablesProvider")
public class EnvironmentVariablesProvider implements IEnvironmentVariablesProvider {

    @Override
    public String getVariable(String variableName) {
        return System.getenv().get(variableName);
    }

}