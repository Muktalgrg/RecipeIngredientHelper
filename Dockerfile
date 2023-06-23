# FROM openjdk:17-alpine
FROM openjdk:17-jdk-slim

EXPOSE 8081

COPY ./target/RecipeIngredientHelper-*.jar /usr/app/
WORKDIR /usr/app

CMD java -jar RecipeIngredientHelper-*.jar

