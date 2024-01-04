import sys

from client.identity_client import IdentityOciClient
from client.network_client import NetworkOciClient
from client.compute_client import ComputeOciClient
import logging

import config


def init_client(configuration):
    i_client = IdentityOciClient(configuration)
    compartment = i_client.get_compartment()
    return i_client, compartment


def terminate_instance(configuration, compartment):
    c_client = ComputeOciClient(configuration, compartment)
    instance = c_client.get_instance()
    if instance:
        c_client.delete_instance(instance)
    return instance


def terminate_subnet(n_client, vcn):
    subnet = n_client.get_subnet(vcn)
    if subnet:
        logging.info("Deleting subnet......")
        n_client.delete_subnet(subnet)
        logging.info("Subnet deleted")


def terminate_internet_gateway(n_client, vcn, route_table):
    n_client.delete_route_table_rules(route_table)
    internet_gateway = n_client.get_internet_gateway(vcn)
    if internet_gateway:
        logging.info("Deleting internet gateway......")
        n_client.delete_internet_gateway(internet_gateway)
        logging.info("Internet Gateway deleted")


def terminate_vcn(configuration, compartment):
    n_client = NetworkOciClient(configuration, compartment)
    vcn, route_table = n_client.get_vcn()
    if vcn:
        terminate_subnet(n_client, vcn)
        terminate_internet_gateway(n_client, vcn, route_table)
        n_client.delete_vcn(vcn)
    return vcn


def terminator(configuration):
    logging.info("Starting cleanup of nolocal environment")
    _, compartment = init_client(configuration)
    if not compartment:
        logging.info("No compartment found; exiting....")
        return

    logging.info("Deleting instance....")
    instance = terminate_instance(configuration, compartment)
    if not instance:
        logging.info("No instance found, skipping...")
    else:
        logging.info("Instance deleted")

    logging.info("Deleting VCN.....")
    vcn = terminate_vcn(configuration, compartment)
    if not vcn:
        logging.info("No vcn found, skipping...")
    else:
        logging.info("VCN deleted")

    logging.info("Deleting compartment....")
    logging.info("Compartment not deleted.")


if __name__ == "__main__":
    logging.basicConfig(level='INFO')
    config = config.Configuration()
    config.override_config_from_arguments(sys.argv)
    terminator(config)
