# meetup
By Rodrigo Enjamio

Proyecto generado con JHipster 6.4.1, se opto por este generador porque permite realizar aplicaciones con front y back de forma rapida.

API weather: api.openweathermap.org/data/2.5/forecast?q=san salvador de jujuy,ar&appid=9708a7edfc916f605d42968670aad6eb&units=metric
Se utilizo dicha api porque la propuesta no permitía hacer pruebas sin ingresar la tarjeta de crédito.

Consideraciones del diseño:
Si no se encuentran temperaturas dentro del periodo de ± 2 horas de la hora de la fiesta. Se muestra un mensaje de error.
Para simplificar el desarrollo se uso el nombre de la ciudad para buscar la temperatura y no las coordenadas.
Se implemento circuit breaker en la llamada a openweather, si por algún motivo no responde en 1.5seg da como fallback una temperatura de 20°c.

Respecto del diseño de la base de datos, faltaría un poco mas de información para saber donde conviene guardar los datos y las relaciones. 
Por ejemplo si vale la pena que meetup tengo embebido  las personas que confirmaron asistencia o no o si dentro de la entidad user haga referencia a las notifications. 
De eso depende las pantallas que se van a mostrar y como se va hacer.

# Tecnologías Utilizadas:
- Vue
- Java8: core de la app
- SpringBoot: Api para dar manejo de aplicacion Rest
- Mongo
- Log-back: Logueo
- jUnit, mokito, hamcrest, wiremock: Testing
- Maven: Gestión del proyecto
- Swagger: Documentador de API endpoints.
- Jacoco: Cobertura y reportes
- Docker-compose
- Kafka: Se usa para incrementar la cantidad de personas que van a asistir a la meetup. Es decir cuando un usuario informa que va a asistir
se crea a ese usuario como follow de la meetup y a su vez se manda un a mensaje a kafka para que luego otro hilo lo tome e incremente
en 1 el valor de la cantidad de personas confirmadas a esa meetup (Por lo que hay consistencia eventual). Y por ultimo se manda una notification al usuario.
 Lo ideal seria desacoplarlo totalmente con otra base de datos y otro microservicio, si el negocio lo amerita.
- Ehcache
- Map-struct: Para mappear un DTO a un Entity de forma rapida, el mas performante del mercado
- Spring-security: Se deshabilitaron algunas medidas de seguridad para que sea mas facil hacer las request desde postman
 (user="admin", pass="admin") le damos seguridad a la aplicación
- Prometheus y Grafana: Monitoreo de la aplicación
-  CI/CD pipeline se puede configurar gihu actions, Travis, jenkins, circleCI etc. Para el caso de jenkins 
       docker-compose -f src/main/docker/jenkins.yml up -d


- Acceder via explorador a:

    User: admin
    pass: admin

    localhost:8080/
    swagger: http://localhost:8080/admin/docs 
    
En la raiz adjunto documento SantanderMeetup.odt

Puntos que no llegue a terminar:
- Utilizar MongoReactive
- Agregar un microservicio para las notificaciones aprovechando kafka.
- Agregar dentro de las opciones de Admin Meetup y User meetup, las funcionalidades de cada tipo de usuario.
- Agregar coordenadas a la busqueeda de la temperatura.

Para lanzar la app:

dentro del directorio principal lanzar 
docker-compose -f mongodb.yml up
docker-compose -f kafka.yml up
./mvnw


Dentro de config/jmeter hay info sobre test de stress.

This application was generated using JHipster 6.4.1

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

    npm install


    ./mvnw
    npm start

## Building for production

### Packaging as jar

To build the final jar and optimize the meetup application for production, run:

    ./mvnw -Pprod clean verify

This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
To ensure everything worked, run:

    java -jar target/*.jar

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

Refer to [Using JHipster in production][] for more details.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

    ./mvnw -Pprod,war clean verify

## Testing

To launch your application's tests, run:
./mvnw verify

### Client tests

Unit tests are run by [Jest][] and written with [Jasmine][]. They're located in [src/test/javascript/](src/test/javascript/) and can be run with:
npm test

For more information, refer to the [Running tests page][].

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

You can run a Sonar analysis with using the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the maven plugin.
Then, run a Sonar analysis:

```
./mvnw -Pprod clean verify sonar:sonar
```

If you need to re-run the Sonar phase, please be sure to specify at least the `initialize` phase since Sonar properties are loaded from the sonar-project.properties file.

```
./mvnw initialize sonar:sonar
```

or
For more information, refer to the [Code quality page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a mongodb database in a docker container, run:
docker-compose -f src/main/docker/mongodb.yml up -d
To stop it and remove the container, run:
docker-compose -f src/main/docker/mongodb.yml down

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:
./mvnw -Pprod verify jib:dockerBuild
Then run:
docker-compose -f src/main/docker/app.yml up -d
For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.
