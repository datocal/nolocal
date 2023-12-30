from client.identity_client import IdentityOciClient
from client.network_client import NetworkOciClient
from client.compute_client import ComputeOciClient
import logging
import os
import sys
import config


def create_compartment(configuration):
    client = IdentityOciClient(configuration)
    compartment = client.get_compartment()
    if compartment:
        logging.info("Compartment found.... skipping creation")
    else:
        compartment = client.create_compartment()
        logging.info("Compartment created")
    return compartment


def create_subnet(client, vcn):
    logging.info("Creating subnet.....")
    subnet = client.get_subnet(vcn)
    if subnet:
        logging.info("Subnet found..... skipping creation")
    else:
        subnet = client.create_subnet(vcn)
        logging.info("Subnet created")
    return subnet


def add_rules(client, vcn):
    logging.info("Adding rules to security list.....")
    security_list = client.get_security_list(vcn)
    client.add_rules(security_list)
    logging.info("Rules added.....")


def create_internet_gateway(client, vcn):
    logging.info("Creating Internet Gateway.....")
    internet_gateway = client.get_internet_gateway(vcn)
    if internet_gateway:
        logging.info("Internet Gateway found.... skipping creation")
    else:
        internet_gateway = client.create_internet_gateway(vcn)
        logging.info("Internet Gateway created")
    return internet_gateway


def add_route_rule(client, route_table, internet_gateway):
    logging.info("Adding route rules.....")
    client.add_internet_gateway_to_router(route_table, internet_gateway)
    logging.info("Route rules added")


def create_network(configuration, compartment):
    client = NetworkOciClient(configuration, compartment)
    vcn, route_table = client.get_vcn()
    if vcn:
        logging.info("VCN found.... skipping creation")
    else:
        vcn, route_table = client.create_vcn()
        logging.info("VCN created")
    subnet = create_subnet(client, vcn)
    add_rules(client, vcn)
    internet_gateway = create_internet_gateway(client, vcn)
    add_route_rule(client, route_table, internet_gateway)
    return subnet


def create_instance(configuration, compartment, subnet):
    client = ComputeOciClient(configuration, compartment)
    instance = client.get_instance()
    created = False
    if instance:
        logging.info("Instance found.... skipping creation")
    else:
        instance = client.create_instance(subnet)
        created = True
        logging.info("Instance created")
    return instance, created


def get_ip(configuration, compartment, instance):
    compute_client = ComputeOciClient(configuration, compartment)
    network_client = NetworkOciClient(configuration, compartment)
    vnic_id = compute_client.get_vnic_id(instance)
    return network_client.get_ip(vnic_id)


def creator(configuration):
    logging.info("Creating compartment...")
    compartment = create_compartment(configuration)
    logging.info("Creating VCN....")
    subnet = create_network(configuration, compartment)
    logging.info("Creating instance.....")
    instance, created = create_instance(configuration, compartment, subnet)
    logging.info("Getting IP.....")
    ip = get_ip(configuration, compartment, instance)
    print(ip)
    if created:
        print(created)


if __name__ == "__main__":
    LOGLEVEL = os.environ.get('LOGLEVEL', 'INFO').upper()
    logging.basicConfig(level=LOGLEVEL)
    config = config.Configuration()
    config.override_config_from_arguments(sys.argv)
    creator(config)
