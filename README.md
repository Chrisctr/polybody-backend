# Polybody

1. [Endpoints (API)](#endpoints)
    1. [GET](#GET)
1. [Requirements](#requirements)
1. [Build and run the project](#build-and-run-the-project)

##Endpoints

### GET

### `GET /findSpecificUser/{username}`

#### Successful

```json
[
   {
      "_id": "611be0d7e17315ce09335455",
      "username": "Calvin",
      "email": "calvin@gmail.com",
      "password": "Sasquatch12!",
      "age": 25,
      "gender": "male",
      "height": 140,
      "previousWeight": [
         {
            "dateTime": "2020-06-09",
            "weight": 250
         },
         {
            "dateTime": "2021-06-09",
            "weight": 180
         },
         {
            "dateTime": "2021-07-09",
            "weight": 170
         },
         {
            "dateTime": "2021-08-09",
            "weight": 165
         }
      ],
      "targetWeight": 140,
      "macroStat": [
         {
            "dateTime": "2021-08-09",
            "activityLevel": "Very Active",
            "setGoal": 150,
            "proteinPreference": 150,
            "fatPreference": 50,
            "carbPreference": 300,
            "bodyFat": 13,
            "equationPreference": "Default",
            "maintenanceCalories": 2900,
            "targetCalories": 2500,
            "timeToGoal": 90
         }
      ]
   }
]

```

#### Unsuccessful 

## Requirements
* Java Software Developer's Kit (SE) 1.8 or higher
* sbt 1.3.4 or higher. Note: if you downloaded this project as a zip file from <https://developer.lightbend.com>, the file includes an sbt distribution for your convenience.

To check your Java version, enter the following in a command window:

```bash
java -version
```

To check your sbt version, enter the following in a command window:

```bash
sbt sbtVersion
```

If you do not have the required versions, follow these links to obtain them:

* [Java SE](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [sbt](http://www.scala-sbt.org/download.html)

## Build and run the project

This example Play project was created from a seed template. It includes all Play components and an Akka HTTP server. The project is also configured with filters for Cross-Site Request Forgery (CSRF) protection and security headers.

To build and run the project:

1. Use a command window to change into the example project directory, for example: `cd play-scala-hello-world-web`

2. Build the project. Enter: `sbt run`. The project builds and starts the embedded HTTP server. Since this downloads libraries and dependencies, the amount of time required depends partly on your connection's speed.

3. After the message `Server started, ...` displays, enter the following URL in a browser: <http://localhost:9000>

The Play application responds: `Welcome to the Hello World Tutorial!`
