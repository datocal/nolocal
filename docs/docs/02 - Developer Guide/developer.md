---
sidebar_position: 1
---

# Quickstart

## Tech stack

| Technology     | Type                  | Description                                                                       | Reference |
|----------------|-----------------------|-----------------------------------------------------------------------------------|-----------|
| Kotlin         | Language              | Modern JVM based language with full java compatibility                            |[Link](https://kotlinlang.org/)               |
| Spring Boot    | Framework             | Robust and known framework to make the magic happens :)                           |[Link](https://spring.io/projects/spring-boot)|
| Javacord       | Library               | Discord Library implementing the Discord API with friendly interfaces             |[Link](https://github.com/Javacord/Javacord)  |
| Gradle         | Build Tool            | Tool to build the project, with all the tasks related to compile, test and more   |[Link](https://gradle.org/)                   |
| Github Actions | CI/CD                 | Tool to orchestrate the checks, deploy and everything happening after a commit    |[Link](https://github.com/features/actions)   |
| Sonarcloud     | Quality Analisis      | Tool to analyse the project to detect quality improvements (code smells, bugs...) |[Link](https://sonarcloud.io/)                |
| Codecov        | Coverage Analisis     | Tool to show the testing coverage and its evolution                               |[Link](https://about.codecov.io/)             |
| Docusaurus     | Static site generator | Tool to generate a static site for documentation                                  |[Link](https://docusaurus.io/)                |
| Oracle Cloud   | Hosting               | Cloud provider of this service deployment (free tier)                             |[Link](https://www.oracle.com/cloud/)         

## Building it

To build the executable jar, just run:

    ./gradlew build

This command will generate the jar output under build/libs

## Running it

Tu run it, you need to have an environment variable called DISCORD_TOKEN with the secret token of your bot.
You can change the [application.yml](https://github.com/datocal/nolocal/blob/master/src/main/resources/application.yml) too.

With that in mind, just do:

    ./gradlew bootRun

Or even simplier
    
    java -jar nolocal.jar

## Testing it 
All the tests are under the check task in gradle, so to run all the suite run:

    ./gradlew check

You won't need any token here since it will not connect to the discord server during tests.

### Integration testing
There is a specific module for integration testing on [integration-test](https://github.com/datocal/nolocal/tree/master/src/integration-test)
The tests are included inside the gradle _check_ task, using the [test-sets library](https://plugins.gradle.org/plugin/org.unbroken-dome.test-sets)

### Mutation testing
You can run the mutation testing too! to do that, simply run
    
    ./gradlew pitest

This will generate a report under build/reports/pitest/
    