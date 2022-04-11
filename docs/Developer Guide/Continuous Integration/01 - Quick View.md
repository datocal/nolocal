---
sidebar_position: 1
---

# Quick View

This project contains a full pipeline from 0 to production using Github Actions.

There is a bunch of pipelines configured but the important one is the pipeline-jobs pipeline.

 * docs: This pipeline only generates the docs. But the main one generates them too when pushing to master, so it's useless
 * mutation: This pipeline runs the mutation tests and generates a report showing the real coverage.
 * pipeline: This pipeline was the same as the main one but in one machine, without paralleling the jobs. It's deprecated
 * pipeline-jobs: This is the main pipeline with parallel jobs and commit to production.

The following image shows the current jobs for the main pipeline:

![Actions Pipeline](/img/developer/pipeline.png)

## Generated artifacts

 * caddyfile: The configuration file for caddy, it's on the root of the project.
 * compose: The docker-compose file. Used in the deployment to launch the docker images
 * documentation: The documentation site in a compressed zip file.
 * artifact: The jar containing the NoLocal Spring Boot application.