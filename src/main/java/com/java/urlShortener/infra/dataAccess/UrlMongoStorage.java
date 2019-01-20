package com.java.urlShortener.infra.dataAccess;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.urlShortener.domain.ShortUrl;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

@Component("urlStorage")
public class UrlMongoStorage implements IUrlStorage {

	@Autowired
	private IDataSource mongoDataSource;
	private String collectionName = "shortUrl";

	@Override
	public void addShortUrl(ShortUrl shortUrl) throws Exception {

		Document newShortUrl = new Document("shortUrl", shortUrl.getShortUrl()).append("originalUrl",
				shortUrl.getOriginalUrl());

		MongoCollection<Document> collection = mongoDataSource.getCollection(collectionName);

		try {
			collection.insertOne(newShortUrl);
		} catch (MongoWriteException e) {
			if (e.getCode() == 11000 && checkIfOriginalUrlIsTheSame(shortUrl))
				return;
			else
				throw new Exception("It was not possible to create and store the shortUrl");
		}
	}

	@Override
	public ShortUrl getShortUrl(String shortUrl) {
		String originalUrl = getOriginalUrl(shortUrl);
		if ("".equals(originalUrl))
			return null;

		return new ShortUrl(shortUrl, originalUrl);
	}

	private boolean checkIfOriginalUrlIsTheSame(ShortUrl shortUrl) {
		String foundOriginalUrl = getOriginalUrl(shortUrl.getShortUrl());

		return foundOriginalUrl.equals(shortUrl.getOriginalUrl());
	}

	private String getOriginalUrl(String shortUrl) {
		MongoCollection<Document> collection = mongoDataSource.getCollection(collectionName);

		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("shortUrl", shortUrl);
		BasicDBObject fields = new BasicDBObject();
		fields.put("originalUrl", 1);

		Document result = collection.find(Filters.eq("shortUrl", shortUrl)).first();

		if (result == null)
			return "";

		return (String) result.get("originalUrl");
	}
}