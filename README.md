# Screen-shoot

An api to generate a screenshot of the webpage passed in the url

It uses selenium webdriver, Chrome webdriver and requires Chrome browser to be installed in the system.

Also currently, tested on Windows only. Some additional configurations needs to be done for Linux, which I will update shortly.

## Running application locally

```
git clone https://github.com/viralharia/Screen-shoot.git
cd Screen-shoot
.\mvnw spring-boot:run
```

## Running tests

```
.\mvnw test
```

## API

```
{GET http://localhost:8080/api/screenshot/v1?url=http://wikipedia.org}
```

## Contributing/ToDos

1. Make it run on Linux
