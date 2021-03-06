# urlShortener

urlShortner is the resolution of this [proposal](https://gist.github.com/FylmTM/e3c4e5f337a176e94d6dd51703068925)



## How to use this

### Requirements:

* [**Docker**](https://www.docker.com/products/docker-desktop) - to start containers

### Running


After prepare the environment, complete the steps below:

1. Create a network on docker

	> ```docker network create urlShortenerNetwork```

2. Start a MongoDB container

	> ```docker run --expose 27017 --network urlShortenerNetwork --rm --name mongo -d mongo:3.4.18-jessie```
	
3. Start a container to run the app

	> ```docker run -p 8080:8080 -e database_connection="mongodb://mongo:27017" --network urlShortenerNetwork --rm --name urlShortener vicentenvt/url-shortener```



### Using the application

1. To get a new short URL you should POST a JSON, like below, to localhost:8080/shortify:

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

## Have Fun!
