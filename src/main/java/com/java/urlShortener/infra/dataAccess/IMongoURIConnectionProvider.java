package com.java.urlShortener.infra.dataAccess;

import com.mongodb.MongoClientURI;

public interface IMongoURIConnectionProvider {

	MongoClientURI getClientUri();
}
