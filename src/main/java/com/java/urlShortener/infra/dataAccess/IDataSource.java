package com.java.urlShortener.infra.dataAccess;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

public interface IDataSource {
	MongoCollection<Document> getCollection(String collectionName);
}
