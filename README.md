# urlShortener


Create a network

	docker network create urlShortenerNetwork

Start MongoDB container

	docker run --expose 27017 --network urlShortenerNetwork --rm --name mongo -d mongo:3.4.18-jessie
	
Compile Application

	mvn package
	
Build dockerfile

	docker build -t url-shortener .

Start app 

	docker run -p 8080:8080 --network urlShortenerNetwork --rm --name urlShortener url-shortener
