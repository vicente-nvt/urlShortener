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
public class MongoDataSource {

	private MongoClientURIProvider mongoClientURIProvider;	
	private String databaseName = "urlShortener";
	private MongoClient mongoClient;
	private MongoDatabase database;

	@Autowired
	public MongoDataSource(MongoClientURIProvider clientUriProvider) {
		this.mongoClientURIProvider = clientUriProvider;
	}

	public MongoCollection<Document> getCollection(String indexName, String collectionName) {
		checkConnection();
		ensureThatIndexIsCreated(indexName, collectionName);
		return database.getCollection(collectionName);
	}

	private void checkConnection() {
		if (mongoClient == null) {
			mongoClient = new MongoClient(mongoClientURIProvider.getClientUri());
			database = mongoClient.getDatabase(databaseName);
		}
	}

	private void ensureThatIndexIsCreated(String indexName, String collectionName){
		BasicDBObject index = new BasicDBObject(indexName, 1);
		IndexOptions unique = new IndexOptions().unique(true);
		database.getCollection(collectionName).createIndex(index, unique);
	}
}
