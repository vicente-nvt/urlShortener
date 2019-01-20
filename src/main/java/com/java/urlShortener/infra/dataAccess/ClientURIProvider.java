package com.java.urlShortener.infra.dataAccess;

import org.springframework.stereotype.Component;

import com.mongodb.MongoClientURI;

@Component("mongoClientURIProvider")
public class ClientURIProvider implements IMongoURIConnectionProvider {

	@Override
	public MongoClientURI getClientUri() {		
		return new MongoClientURI("mongodb://mongo:27017");
	}

}
