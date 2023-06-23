FROM openjdk:17-alpine

EXPOSE 8081

COPY ./target/RecipeIngredientHelper-*.jar /usr/app/
WORKDIR /usr/app

CMD java -jar java-maven-app-*.jar

