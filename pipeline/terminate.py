from client.identity_client import NoLocalIdentityOciClient
from client.network_client import NoLocalNetworkOciClient
from client.compute_client import NoLocalComputeOciClient
import logging


def init_client():
    i_client = NoLocalIdentityOciClient()
    compartment = i_client.get_compartment()
    return i_client, compartment


def terminate_instance(compartment):
    c_client = NoLocalComputeOciClient(compartment)
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


def terminate_vcn(compartment):
    n_client = NoLocalNetworkOciClient(compartment)
    vcn = n_client.get_vcn()
    if vcn:
        terminate_subnet(n_client, vcn)
        n_client.delete_vcn(vcn)
    return vcn


def terminator():
    logging.info("Starting cleanup of nolocal environment")
    i_client, compartment = init_client()
    if not compartment:
        logging.info("No compartment found; exiting....")
        return

    logging.info("Deleting instance....")
    instance = terminate_instance(compartment)
    if not instance:
        logging.info("No instance found, skipping...")
    else:
        logging.info("Instance deleted")

    logging.info("Deleting VCN.....")
    vcn = terminate_vcn(compartment)
    if not vcn:
        logging.info("No vcn found, skipping...")
    else:
        logging.info("VCN deleted")

    logging.info("Deleting compartment....")
    logging.info("Compartment not deleted.")


if __name__ == "__main__":
    logging.basicConfig(level=logging.INFO)
    terminator()
