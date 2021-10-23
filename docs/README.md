# Documentation

####You can explore it on github pages, [HERE](https://datocal.github.io/nolocal/)

This documentation is built using [Docusaurus 2](https://docusaurus.io/), a static website generator.

### Installation
You will need [Node.js](https://nodejs.org/en/) to install and run it

```
$ npm install
```

### Local Development

```
$ npm run start
```

This command starts a local development server and opens up a browser window. Most changes are reflected live without having to restart the server.

### Build

```
$ npm run build
```

This command generates static content into the `build` directory and can be served using any static contents hosting service. 
This repo host it on github pages

### Deployment

This is being deployed by GitHub actions on every commit to master. You can view the pipeline [in the workflow of this repo, on the BuildDocs Job](../.github/pipeline-jobs.yml).