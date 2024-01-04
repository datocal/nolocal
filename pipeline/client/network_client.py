import time
import oci
from oci.core.models import \
    IngressSecurityRule, \
    TcpOptions, \
    PortRange, \
    CreateVcnDetails, \
    CreateSubnetDetails, \
    UpdateSecurityListDetails, CreateInternetGatewayDetails, UpdateRouteTableDetails, RouteRule


def __to_oci_rule(rule):
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
    return [__to_oci_rule(rule) for rule in additional_rules]


class NetworkOciClient:

    def __init__(self, config, compartment_id):
        self.configuration = config
        oci.config.validate_config(self.configuration.DICTIONARY)
        self.compartment_id = compartment_id
        self.client = oci.core.VirtualNetworkClient(self.configuration.DICTIONARY)

    def get_vcn(self):
        vcn = self.client.list_vcns(
            compartment_id=self.compartment_id,
            limit=1,
            lifecycle_state='AVAILABLE'
        )
        if not vcn.data:
            return None, None
        return vcn.data[0].id, vcn.data[0].default_route_table_id

    def create_vcn(self):
        request = CreateVcnDetails(
            cidr_block=self.configuration.NETWORK_VCN_IP_RANGE,
            display_name=self.configuration.NETWORK_VCN_NAME,
            compartment_id=self.compartment_id
        )
        created = self.client.create_vcn(request)
        vcn = self.client.get_vcn(created.data.id)
        vcn = oci.wait_until(self.client, vcn, 'lifecycle_state', 'AVAILABLE')
        return vcn.data.id, vcn.data.default_route_table_id

    def get_subnet(self, vcn_id):
        subnet = self.client.list_subnets(
            compartment_id=self.compartment_id,
            vcn_id=vcn_id,
            lifecycle_state='AVAILABLE'
        )
        if not subnet.data:
            return None
        return subnet.data[0].id

    def create_subnet(self, vcn_id):
        request = CreateSubnetDetails(
            compartment_id=self.compartment_id,
            vcn_id=vcn_id,
            display_name=self.configuration.NETWORK_SUBNET_NAME,
            cidr_block=self.configuration.NETWORK_SUBNET_IP_RANGE
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

    def get_ip(self, vnic_id):
        ip = self.client.get_vnic(vnic_id)
        if not ip.data:
            return None
        else:
            return ip.data.public_ip

    def add_rules(self, security_list_id):
        current = self.client.get_security_list(security_list_id)
        request = UpdateSecurityListDetails()
        request.display_name = current.data.display_name
        request.ingress_security_rules = current.data.ingress_security_rules + to_rules(self.configuration.NETWORK_SECURITY_LIST_RULES)
        self.client.update_security_list(security_list_id, request)

    def get_internet_gateway(self, vcn_id):
        internet_gateway = self.client.list_internet_gateways(
            self.compartment_id,
            vcn_id=vcn_id,
            display_name=self.configuration.NETWORK_VCN_INTERNET_GATEWAY_NAME
        )
        if not internet_gateway.data:
            return None
        else:
            return internet_gateway.data[0].id

    def create_internet_gateway(self, vcn_id):
        request = CreateInternetGatewayDetails()
        request.compartment_id = self.compartment_id
        request.vcn_id = vcn_id
        request.is_enabled = True
        request.display_name = self.configuration.NETWORK_VCN_INTERNET_GATEWAY_NAME
        response = self.client.create_internet_gateway(request)
        return response.data.id

    def add_internet_gateway_to_router(self, route_table_id, internet_gateway_id):
        new_rule = RouteRule(
            network_entity_id=internet_gateway_id,
            destination='0.0.0.0/0'
        )
        current = self.client.get_route_table(route_table_id)
        request = UpdateRouteTableDetails()
        request.display_name = self.configuration.NETWORK_VCN_ROUTE_TABLE_NAME
        if not current.data.route_rules:
            request.route_rules = [new_rule]
        self.client.update_route_table(route_table_id, request)

    def delete_vcn(self, vcn_id):
        self.client.delete_vcn(vcn_id)
        prevent_bug_by_waiting_for_deletion()

    def delete_subnet(self, subnet_id):
        self.client.delete_subnet(subnet_id)
        prevent_bug_by_waiting_for_deletion()

    def delete_internet_gateway(self, internet_gateway_id):
        self.client.delete_internet_gateway(internet_gateway_id)
        prevent_bug_by_waiting_for_deletion()

    def delete_route_table_rules(self, route_table_id):
        request = UpdateRouteTableDetails()
        request.route_rules = []
        self.client.update_route_table(route_table_id, request)


# Some bugs: some resources (network) gets deleted completely, without changing state, so following commands may fail.
# sleep of 5 seconds instead of get_vcn and wait_until lifecycle_state TERMINATED
def prevent_bug_by_waiting_for_deletion():
    time.sleep(5)

