package com.java.urlShortener.infra.dataAccess;

import com.java.urlShortener.infra.EnvironmentVariablesProvider;
import com.mongodb.MongoClientURI;

import org.springframework.stereotype.Component;

@Component("mongoClientURIProvider")
public class MongoClientURIProvider {

	public MongoClientURI getClientUri() {
		String databaseConnectionPath = EnvironmentVariablesProvider.getVariable("database_connection");
		return new MongoClientURI(databaseConnectionPath);
	}
}
