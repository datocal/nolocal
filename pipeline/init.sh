#! /bin/bash

# $1 - AWSACCESSKEYID
# $2 - AWSSECRETACCESSKEY
# $3 - tenant_id
# $4 - region-id

sudo apt-get update -y

# Install docker
sudo apt-get install apt-transport-https ca-certificates curl software-properties-common gnupg lsb-release -y
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu focal stable"
sudo apt-get update -y
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-compose -y
