# urlShortener

urlShortner is the resolution of this proposal:

	https://gist.github.com/FylmTM/e3c4e5f337a176e94d6dd51703068925

## Running the application

#### Requirements:
* **Docker** - to start containers [here](https://www.docker.com/products/docker-desktop)
* **Apache Maven** - to compile and package the application [link here](https://maven.apache.org/)

#### Running

After prepare the environment, complete the steps below:

1. Create a network

	> docker network create urlShortenerNetwork

2. Start MongoDB container

	> docker run --expose 27017 --network urlShortenerNetwork --rm --name mongo -d mongo:3.4.18-jessie
	
3. Compile Application

	> mvn package
	
4. Build dockerfile

	> docker build -t url-shortener .

5. Start app

	> docker run -p 8080:8080 --network urlShortenerNetwork --rm --name urlShortener url-shortener
