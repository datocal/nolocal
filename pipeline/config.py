import os
import base64


def from_base64(to_decode):
    if to_decode:
        return base64.b64decode(to_decode).decode()


class Configuration:

    CLOUD_REGION = "uk-london-1"
    CLOUD_AVAILABILITY_DOMAIN = "RXTD:UK-LONDON-1-AD-2"

    COMPARTMENT_NAME = "nolocal-compartment"
    COMPARTMENT_DESCRIPTION = "nolocal compartment for all the configured resources with the nolocal application"

    NETWORK_VCN_NAME = "nolocal-vcn"
    NETWORK_SUBNET_NAME = 'nolocal-subnet'
    NETWORK_SECURITY_LIST_RULES = [
        {
            "port": 443,
            "description": "HTTPs traffic to connect to the nolocal application"
        }
    ]
    NETWORK_VCN_IP_RANGE = '10.0.0.0/16'
    NETWORK_SUBNET_IP_RANGE = '10.0.0.0/24'
    NETWORK_VCN_INTERNET_GATEWAY_NAME = 'nolocal-internet-gateway'
    NETWORK_VCN_ROUTE_TABLE_NAME = "nolocal-routetable"

    INSTANCE_NAME = "nolocal-instance"
    INSTANCE_OPERATING_SYSTEM = "Canonical Ubuntu"
    INSTANCE_OPERATING_SYSTEM_VERSION = "22.04"

    # leave this dictionary to None if there is no shape config
    INSTANCE_SHAPE_CONFIG = None
    INSTANCE_SHAPE = "VM.Standard.E2.1.Micro"

    DICTIONARY = {
        "availability_domain": CLOUD_AVAILABILITY_DOMAIN,
        "user": os.environ.get("USER_OCID"),
        "fingerprint": os.environ.get("KEY_FINGERPRINT"),
        "tenancy": os.environ.get("TENANCY"),
        "region": CLOUD_REGION,
        "key_content": from_base64(os.environ.get("KEY_CONTENT")),
        "authorized_keys": from_base64(os.environ.get("AUTHORIZED_KEYS"))
    }

    def is_aarch64(self):
        return self.INSTANCE_SHAPE == "VM.Standard.A1.Flex"

    def override_config_from_arguments(self, argv):
        if len(argv) > 4:
            self.DICTIONARY["user"] = argv[1]
            self.DICTIONARY["fingerprint"] = argv[2]
            self.DICTIONARY["tenancy"] = argv[3]
            self.DICTIONARY["key_content"] = from_base64(argv[4])
            self.DICTIONARY["authorized_keys"] = from_base64(argv[5])
