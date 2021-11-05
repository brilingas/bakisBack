#build
FROM maven:3.6.3-openjdk-14 AS build
#RUN mkdir /guest-registry-app
COPY /src/ /guest-registry-app
RUN mvn -f /guest-registry-app/src/main/java/pom.xml clean package -DskipTests

#run
FROM openjdk:14
RUN mkdir /guest-registry-app
COPY --from=build /src/main/java/guestregistry-controller/target/guestregistry-controller-0.0.1-SNAPSHOT.jar /guest-registry-app
ENTRYPOINT ["java","-jar","/guest-registry-app/guestregistry-controller-0.0.1-SNAPSHOT.jar"]