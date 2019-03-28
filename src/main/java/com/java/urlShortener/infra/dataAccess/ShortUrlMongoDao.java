package com.java.urlShortener.infra.dataAccess;

import com.java.urlShortener.domain.ShortUrl;
import com.java.urlShortener.domain.ShortUrlRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("shortUrlDao")
public class ShortUrlMongoDao implements ShortUrlRepository {

	private static final int duplicatedKeyErrorCode = 11000;
	private static final String urlKeyField = "urlKey";
	private static final String originalUrlField = "originalUrl";
	private static final String collectionName = "shortUrl";
	private static final String indexName = "urlKey";
	
	private MongoDataSource mongoDataSource;

	@Autowired
	public ShortUrlMongoDao(MongoDataSource dataSource) {
		this.mongoDataSource = dataSource;
	}

	@Override
	public void addShortUrl(ShortUrl shortUrl) throws Exception {
		Document newShortUrl = new Document(urlKeyField, shortUrl.getKey()).append(originalUrlField,
				shortUrl.getOriginalUrl());

		try {
			getCollection().insertOne(newShortUrl);
		} catch (MongoWriteException e) {
			if (isDuplicatedKeyError(e) && originalUrlsAreTheSame(shortUrl))
				return;

			throw new Exception("It was not possible to create and store the shortUrl");
		}
	}

	private MongoCollection<Document> getCollection() {
		return mongoDataSource.getCollection(indexName, collectionName);
	}

	private boolean isDuplicatedKeyError(MongoWriteException e) {
		return e.getCode() == duplicatedKeyErrorCode;
	}

	@Override
	public String getOriginalUrlBy(String urlKey) {
		String originalUrl = getOriginalUrl(urlKey);

		return originalUrl;
	}

	private boolean originalUrlsAreTheSame(ShortUrl shortUrl) {
		String foundOriginalUrl = getOriginalUrl(shortUrl.getKey());

		return foundOriginalUrl.equals(shortUrl.getOriginalUrl());
	}

	private String getOriginalUrl(String urlKey) {
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put(urlKeyField, urlKey);

		BasicDBObject fields = new BasicDBObject();
		fields.put(originalUrlField, 1);

		Document result = getCollection().find(Filters.eq(urlKeyField, urlKey)).first();

		if (result == null)
			return "";

		return (String) result.get(originalUrlField);
	}
}