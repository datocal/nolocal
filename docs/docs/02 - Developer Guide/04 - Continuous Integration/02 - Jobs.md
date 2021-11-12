---
sidebar_position: 2
id: jobs
---

# Jobs
The pipeline execute multiple jobs in parallel. When every Job is finished, the deploy proceeds.

## Check
Executes the gradle _check_ task. It will run the unit tests and the integration tests. This task generates some
jacoco reports that will be uploaded [to codecov](https://app.codecov.io/gh/datocal/nolocal) to show the current coverage
of the project

## BuildDocs
This Job will build the docs under the docs folder. This folder contains a Docusaurus project that generates a static site
by documentation written in markdown. The generated static site will be uploaded to the _docs_ branch in the git repository.

This branch will trigger a deployment of the site in the github pages under [this url](https://datocal.github.io/nolocal/)

## Snyk
Executes an Snyk analysis to generate a vulnerability report. 
It will upload the report to [the official snyk site](https://app.snyk.io).

![Snyk Analisis](/img/developer/snyk.png)

Snyk will show which dependencies included a vulnerability and a version which solve them, if exists.

## PrepareImage
This Job will produce the necessary artifacts and images to pull and run the updated images.
The main steps are:
* Run the bootJar gradle task to create the jar
* Publish the following artifacts:
  * Caddyfile with the reverse-proxy configuration to route to the spring boot app
  * Docker-compose file to run the needed docker-images
  * The Jar with the latest spring boot app version.
* Build docker image with the corresponding jar
* Publish the docker image to the [Docker Hub](https://hub.docker.com/r/davidtca/nolocal)

## PrepareOracleCLoud
This Job will create the necessary instance in the Oracle Cloud.

If the instance already exists, it will just get the IP.

The output of this Job is the IP Address, so it will connect to the instance by ssh using that IP in the following Jobs, 
instead of using anything related to the oracle console.

This step will also update the DNS IP to the new one and install all prepare the machine by:

* Open the 443 port for HTTPS connections
* Install Docker


## Deploy 
The deployment will need all the other Jobs to be done before proceeding. It will download the artifacts generated in 
the PrepareImage Job and push them to the deployment machine.
It will pull the docker images and will restart the containers with the new version.

This will generate a little downtime where the spring boot application will be starting 