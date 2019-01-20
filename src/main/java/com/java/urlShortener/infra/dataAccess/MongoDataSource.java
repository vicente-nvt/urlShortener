package com.java.urlShortener.infra.dataAccess;

import org.bson.Document;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;

@Component("mongoDataSource")
public class MongoDataSource implements IDataSource {

	private String databaseName = "urlShortener";
	private MongoClient mongoClient;
	private MongoDatabase database;

	public MongoDataSource() {
		mongoClient = new MongoClient(new MongoClientURI("mongodb://mongo:27017"));
		database = mongoClient.getDatabase(databaseName);

		BasicDBObject index = new BasicDBObject("shortUrl", 1);
		IndexOptions unique = new IndexOptions().unique(true);
		database.getCollection("shortUrl").createIndex(index, unique);
	}

	@Override
	public MongoCollection<Document> getCollection(String collectionName) {
		return database.getCollection(collectionName);
	}
}