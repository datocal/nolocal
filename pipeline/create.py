from client.identity_client import NoLocalIdentityOciClient
from client.network_client import NoLocalNetworkOciClient
from client.compute_client import NoLocalComputeOciClient
import logging
import os
import sys
import config


def create_compartment():
    client = NoLocalIdentityOciClient()
    compartment = client.get_compartment()
    if compartment:
        logging.info("Compartment found.... skipping creation")
    else:
        compartment = client.create_compartment()
        logging.info("Compartment created")
    return compartment


def create_subnet(client, vcn):
    subnet = client.get_subnet(vcn)
    if subnet:
        logging.info("Subnet found..... skipping creation")
    else:
        subnet = client.create_subnet(vcn)
        logging.info("Subnet created")
    return subnet


def create_network(compartment):
    client = NoLocalNetworkOciClient(compartment)
    vcn = client.get_vcn()
    if vcn:
        logging.info("VCN found.... skipping creation")
    else:
        vcn = client.create_vcn()
        logging.info("VCN created")
    logging.info("Creating subnet.....")
    subnet = create_subnet(client, vcn)
    security_list = client.get_security_list(vcn)
    client.add_rules(security_list)
    return subnet


def create_instance(compartment, subnet):
    client = NoLocalComputeOciClient(compartment)
    instance = client.get_instance()
    if instance:
        logging.info("Instance found.... skipping creation")
    else:
        instance = client.create_instance(subnet)
        logging.info("Instance created")
    return instance


def get_ip(compartment, instance):
    compute_client = NoLocalComputeOciClient(compartment)
    network_client = NoLocalNetworkOciClient(compartment)
    vnic_id = compute_client.get_vnic_id(instance)
    return network_client.get_ip(vnic_id)


def creator():
    logging.info("Creating compartment...")
    compartment = create_compartment()
    logging.info("Creating VCN....")
    subnet = create_network(compartment)
    logging.info("Creating instance.....")
    instance = create_instance(compartment, subnet)
    logging.info("Getting IP.....")
    ip = get_ip(compartment, instance)
    print(ip)


if __name__ == "__main__":
    LOGLEVEL = os.environ.get('LOGLEVEL', 'WARN').upper()
    logging.basicConfig(level=LOGLEVEL)
    config.set_config(sys.argv)
    creator()
