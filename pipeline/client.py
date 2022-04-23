from oci.core.models import CreateVcnDetails, CreateSubnetDetails, UpdateSecurityListDetails, IngressSecurityRule, \
    TcpOptions, PortRange
from oci.identity.models import CreateCompartmentDetails

from config import get_config
import config
import oci
import time


class NoLocalIdentityOciClient:

    def __init__(self):
        self.configuration = get_config()
        oci.config.validate_config(self.configuration)
        self.tenant = self.configuration["tenancy"]
        self.client = oci.identity.IdentityClient(self.configuration)

    def get_compartment(self):
        response = self.client.list_compartments(
            compartment_id=self.tenant,
            lifecycle_state="ACTIVE",
            name=config.COMPARTMENT_NAME
        )
        if not response.data:
            return None
        return response.data[0].id

    def create_compartment(self, name=config.COMPARTMENT_NAME, description=config.COMPARTMENT_DESCRIPTION):
        details = CreateCompartmentDetails()
        details.compartment_id = self.tenant
        details.name = name
        details.description = description
        compartment = self.client.create_compartment(
            create_compartment_details=details
        )
        time.sleep(15)  # Bug: get_compartment doesn't work right after creation, it should
        compartment = self.client.get_compartment(compartment.data.id)
        compartment = oci.wait_until(self.client, compartment, 'lifecycle_state', 'ACTIVE')
        return compartment.data.id

    def delete_compartment(self, compartment_id):
        self.client.delete_compartment(compartment_id)
        to_delete_compartment = self.client.get_compartment(compartment_id)
        oci.wait_until(self.client, to_delete_compartment, 'lifecycle_state', 'DELETED')


def to_oci_rule(rule):
    oci_rule = IngressSecurityRule()
    oci_rule.description = rule["description"]
    oci_rule.protocol = "6"
    oci_rule.source = "0.0.0.0/0"
    oci_rule.tcp_options = TcpOptions(
        destination_port_range=PortRange(
            max=rule["port"],
            min=rule["port"],
        )
    )
    return oci_rule


def to_rules(additional_rules):
    return [to_oci_rule(rule) for rule in additional_rules]


class NoLocalNetworkOciClient:

    def __init__(self, compartment_id):
        self.configuration = get_config()
        oci.config.validate_config(self.configuration)
        self.compartment_id = compartment_id
        self.client = oci.core.VirtualNetworkClient(self.configuration)

    def get_vcn(self):
        vcn = self.client.list_vcns(
            compartment_id=self.compartment_id,
            limit=1,
        )
        if not vcn.data:
            return None
        return vcn.data[0].id

    def create_vcn(self, name=config.VCN_NAME):
        request = CreateVcnDetails(
            cidr_block='10.0.0.0/16',
            display_name=name,
            compartment_id=self.compartment_id
        )
        created = self.client.create_vcn(request)
        vcn = self.client.get_vcn(created.data.id)
        vcn = oci.wait_until(self.client, vcn, 'lifecycle_state', 'AVAILABLE')
        return vcn.data.id

    def get_subnet(self, vcn_id):
        subnet = self.client.list_subnets(
            compartment_id=self.compartment_id,
            vcn_id=vcn_id,
        )
        if not subnet.data:
            return None
        return subnet.data[0].id

    def create_subnet(self, vcn_id, name=config.SUBNET_NAME):
        request = CreateSubnetDetails(
            compartment_id=self.compartment_id,
            vcn_id=vcn_id,
            display_name=name,
            cidr_block='10.0.0.0/24'
        )
        subnet = self.client.create_subnet(request)
        subnet = self.client.get_subnet(subnet.data.id)
        subnet = oci.wait_until(self.client, subnet, 'lifecycle_state', 'AVAILABLE')
        return subnet.data.id

    def get_security_list(self, vcn_id):
        security_list = self.client.list_security_lists(
            compartment_id=self.compartment_id,
            vcn_id=vcn_id
        )
        if not security_list.data:
            return None
        return security_list.data[0].id

    def add_rules(self, security_list_id, additional_rules=config.SECURITY_LIST_RULES):
        current = self.client.get_security_list(security_list_id)
        request = UpdateSecurityListDetails()
        request.display_name = current.data.display_name
        request.ingress_security_rules = current.data.ingress_security_rules + to_rules(additional_rules)
        self.client.update_security_list(security_list_id, request)

