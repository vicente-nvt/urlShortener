package com.java.urlShortener.infra.dataAccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.urlShortener.infra.IEnvironmentVariablesProvider;
import com.mongodb.MongoClientURI;

@Component("mongoClientURIProvider")
public class MongoClientURIProvider implements IClientURIProvider {

	@Autowired
	IEnvironmentVariablesProvider environmentVariablesProvider;

	@Override
	public MongoClientURI getClientUri() {
		return new MongoClientURI(environmentVariablesProvider.getVariable("database_connection"));
	}
}
