# deftones2021

One Paragraph of project description goes here

### Prerequisites

* [OpenJDK 8](https://openjdk.java.net/install/) 
* [sbt](https://www.scala-sbt.org/1.x/docs/Setup.html)

### Start

```
sbt run
```

Available endpoints:
```
GET .../tracks
GET .../tracks/{id}
POST .../track/create
```
Create track must be requested with Content-Type = application/json header and request body with correct Truck structure.

Service will be available here: [http://localhost:8080/status](http://localhost:8080/status)

Missing Funcftionality:
```
truck id in db must be unique
all other types of validators
dates to date format for track Domain
unit tests reading data source file (rather pointles)
order / sort
...
```

## Running the tests

```
sbt test # Unit tests
sbt it:test # Integration tests
```

To validate coverage run
```
sbt clean coverage test
sbt clean coverage it:test
```

To generate the coverage reports run
```
sbt coverageReport
```

### Coding style

[Scalafmt](https://scalameta.org/scalafmt/) will run on every compile.  IntelliJ configuration [here](https://scalameta.org/scalafmt/docs/installation.html#intellij)
Configuration in [build.sbt](build.sbt)

## Built With

* [sbt](https://www.scala-sbt.org/) - build tool
* [akka-http](https://doc.akka.io/docs/akka-http/current/) - Web Framework
* [Json4s](http://json4s.org/) - Json parsing and generation
