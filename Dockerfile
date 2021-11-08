#build
FROM maven:3.6.3-openjdk-14 AS build
COPY /. /guest-registry-app/src
RUN ls /guest-registry-app/src
RUN mvn -f /guest-registry-app/src/guestregistry-root/pom.xml clean package -DskipTests

#run
FROM openjdk:14-alpine AS run
COPY --from=build /guest-registry-app/src/guestregistry-root/guestregistry-application/target/guestregistry-controller-0.0.1-SNAPSHOT.jar /guest-registry-app
ENTRYPOINT ["java","-jar","/guest-registry-app/guestregistry-controller-0.0.1-SNAPSHOT.jar"]