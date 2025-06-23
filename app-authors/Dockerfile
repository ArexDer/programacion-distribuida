# - - -COMANDOS PARA EJECUTAR DESDE LA CMD - - -
# Para construir la imagen de Docker, ejecuta el siguiente comando en la terminal:
# docker build -f src/main/docker/Dockerfile.jvm -t quarkus/code-with-quarkus-jvm .



#docker build -t app-authors .
# -t es el nombre de la imagen que se va a crear
#  . indica que el Dockerfile se encuentra en el directorio actual
# docker build -t nombre/app-authors .

#docker build -t diegorivash/app-authors .
#docker login
# docker push diegorivash/app-authors


#LO EJECUTO EN LA CASA.
#docker run app-authors



FROM eclipse-temurin:21.0.7_6-jre-alpine-3.21

#CREAMOS UN DIRECTORIO PARA LA APLICACION
RUN mkdir /app
WORKDIR /app

#IDIOMA
ENV LANGUAGE='en_US:en'

#COPIAMOS LOS ARCHIVOS DE LA APLICACION LOCALES
COPY  build/quarkus-app/lib ./lib
COPY  build/quarkus-app/quarkus ./quarkus
COPY  build/quarkus-app/app ./app
COPY  build/quarkus-app/quarkus-run.jar app.jar

#ENTRYPOINT ["java", "-Dquarkus.http.host=
CMD ["java", "-jar", "app.jar"]






