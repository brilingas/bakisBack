 
# Guest registry app

## Contents
- [About](#about)
- [Project status](#project-status)
- [Prerequisites](#prerequisites)
- [Getting started](#getting-started)
- [Requests](#requests)
- [Contributing](#contributing)

## About
Guest registry app allows you to manage your company's visitors and guests by creating cards for them. It lets you CRUD entities such as:

- Card

- Person

- Worker

- Guest

- Location

- Event

## Project status
This project is currently in development with plenty issues that need to be fixed.

## Prerequisites
`jdk 14+`, `maven 4.0.0+`, `mongo`, `docker`


## Getting started
```
git clone https://github.com/brilingas/guest-registry-app-back.git
cd guest-registry-app-back
```
To start database:
```
docker-compose up -d
```
```
cd guestregistry-root
mvn clean compile package -DskipTests
java -jar ./guestregistry-application/target/guestregistry-application-0.0.1-SNAPSHOT.jar

```

Open http://localhost:8080

## Requests
- Cards
    - POST /cards
    - PUT /cards/{cardId}
    - DELETE /cards/{cardId}
    - GET /cards
    - GET /cards/{cardId}
    - GET /cards/available/{cardType}/{locationId}
- Persons
    - POST /persons
    - PUT /persons/{personId}
    - DELETE /persons/{personId}
    - GET /persons
    - GET /persons/{personId}
- Workers
    - POST /workers
    - PUT /workers/{workerId}
    - DELETE /workers/{workerId}
    - GET /workers
    - GET /workers/{workerId}
- Guests
    - POST /guests
    - PUT /guests/{guestId}
    - DELETE /guests/{guestId}
    - GET /guests
    - GET /guests/{guestId}
- Locations
    - POST /locations
    - PUT /locations/{locationId}
    - DELETE /locations/{locationId}
    - GET /locations
    - GET /locations/{locationId}
- Events
    - POST /events
    - PUT /events/{eventId}
    - DELETE /events/{eventId}
    - GET /events
    - GET /events/{eventId}
## Contributing
If you have any ideas, just [open an issue](https://github.com/brilingas/guest-registry-app-back/issues/new)

If you want to contribute - please do. Fork the repo and make changes as you'd like. PRs are very welcomed too
