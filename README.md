# hangman-api

 This is Hangman API. Hangman is a simple game where one has to guess a word by proposing letters. For every incorrect letter a part of the hangman picture is drawn. If the picture is finished before the word has been correctly guessed the player looses.
    
 Further information on the game can be found in [Wikipedia](https://en.wikipedia.org/wiki/Hangman_(game)).
    
 Using this API you can integrate Hangman game into your products, applications, refigerators or wherever you want to.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

You need to install JRE and JDK

```
install JRE
install JDK
```

### Installing

Cloning a repository

```
$ git clone https://github.com/dcanaktemur/homework.git
```

Building

```
cd 'project-dir'
./gradlew clean build
```

Running

```
cd 'project-dir'
java -jar build/libs/hangman-api-0.0.1-SNAPSHOT.jar

or

./gradlew bootRun

```

Yes. That works! You can make a request to http://localhost:8080/hangman/v1/

## Running the tests

You can run the tests for this system with :

```
cd 'project-dir'
./gradlew test   
```

## Built With

* [Spring Boot](https://projects.spring.io/spring-boot/) - The web framework used
* [H2 Database](http://www.h2database.com/html/main.html) - In-memory database

## Authors

* **Dogus Can Aktemur** [My profile](https://github.com/dcanaktemur)

