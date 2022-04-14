# Secrets
Multiple secrets are used in the project. Most of them used during CI processes. But some other for the application.

The secrets are managed by Github Secrets.


### DOCKERHUB_TOKEN

Token used to upload to [the docker hub](https://hub.docker.com/u/davidtca) the produced artifacts. 
 * [The jar application](./jobs.md#PrepareImage) 
 * [The custom caddy build](./jobs.md#SetUpCaddy)

It looks like an uuid
    
    97963003-29b6-4484-9dae-6a9c7beda9df

Another token can be generated at the dockerhub settings

![Dockerhub settings](../img/ci/secrets/dockerhub.png)

### DUCKDNS_TOKEN

The token is used to link the current machine IP to the Duckdns domain. 
Useful to automate the recreation of the instance. 
It's used on the [PrepareOracleCLoud job](./jobs#PrepareOracleCLoud) after the ip is obtained

It looks like an uuid

    97963003-29b6-4484-9dae-6a9c7beda9df

The token is one per user at the duckdns site. The domain is linked to this same GitHub account

![Duckdns settings](../img/ci/secrets/duckdns.png)


### SNYK_TOKEN

### TOKEN_GITHUB

### OCI_FINGERPRINT

### OCI_KEY_FILE

### OCI_REGION

### OCI_TENANCY

### OCI_USER_OCID

### VM_AVAILABILITY_DOMAIN

### VM_COMPARTMENT_OCID

### VM_CUSTOM_IMAGE_OCID

### VM_SHAPE

### VM_SSH_PRIVATE_KEY

### VM_SSH_PUB_KEY

### VM_SUBNET_OCID
