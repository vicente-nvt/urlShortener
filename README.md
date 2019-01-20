# urlShortener

urlShortner is the resolution of this [proposal](https://gist.github.com/FylmTM/e3c4e5f337a176e94d6dd51703068925)



## How to use this

### Requirements:

* **Docker** - to start containers [here](https://www.docker.com/products/docker-desktop)
* **Apache Maven** - to compile and package the application [here](https://maven.apache.org/)

### Running


After prepare the environment, complete the steps below:

1. Create a network on docker

	> ```docker network create urlShortenerNetwork```

2. Start a MongoDB container

	> ```docker run --expose 27017 --network urlShortenerNetwork --rm --name mongo -d mongo:3.4.18-jessie```
	
3. Compile the Application

	> ```mvn package```
	
4. Build the application Dockerfile

	> ```docker build -t url-shortener .```

5. Start a container to run the app

	> ```docker run -p 8080:8080 --network urlShortenerNetwork --rm --name urlShortener url-shortener```



### Using the application

1. To get a new short URL you should POST to localhost:8080 a JSON like this:

> ```{ "url" : "https://github.com/vicente-nvt/urlShortener" }```

And then you will receive an answer as a JSON that contains the shortURL, like this:

>```
>{
>    "originalUrl": "https://github.com/vicente-nvt/urlShortener",
>    "shortUrl": "6552109a"
>}
>```

2. To retrieve your original URL and being redirected to the website then go at your web browser and put the link with your ShortURL, like this:

> localhost:8080/6552109a

##Have Fun!
