package com.java.urlShortener.infra.dataAccess;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;

@Component("mongoDataSource")
public class MongoDataSource implements IDataSource {

	@Autowired
	private IClientURIProvider mongoClientURIProvider;
	private String databaseName = "urlShortener";
	private MongoClient mongoClient;
	private MongoDatabase database;

	private void checkConnection() {
		if (mongoClient == null) {
			mongoClient = new MongoClient(mongoClientURIProvider.getClientUri());
			database = mongoClient.getDatabase(databaseName);
			
			BasicDBObject index = new BasicDBObject("shortUrl", 1);
			IndexOptions unique = new IndexOptions().unique(true);
			database.getCollection("shortUrl").createIndex(index, unique);
		}
	}

	@Override
	public MongoCollection<Document> getCollection(String collectionName) {
		checkConnection();
		
		return database.getCollection(collectionName);
	}
}