import os
import base64

COMPARTMENT_NAME = "nolocal-compartment"
COMPARTMENT_DESCRIPTION = "NoLocal compartment for all the configured resources with the NoLocal application"

VCN_NAME = "nolocal-vcn"
SUBNET_NAME = 'nolocal-subnet'
SECURITY_LIST_RULES = [
    {
        "port": 80,
        "description": "HTTP traffic for discord and monitoring connection to the bot"
    },
    {
        "port": 443,
        "description": "HTTPs traffic for discord and monitoring connection to the bot"
    }
]

INSTANCE_NAME = "nolocal-instance"
INSTANCE_AVAILABILITY_DOMAIN = os.environ.get("AVAILABILITY_DOMAIN")
INSTANCE_OPERATING_SYSTEM = "Canonical Ubuntu"
INSTANCE_OPERATING_SYSTEM_VERSION = "20.04"
INSTANCE_SHAPE = "VM.Standard.E2.1.Micro"
INSTANCE_AUTHORIZED_KEYS = os.environ.get("AUTHORIZED_KEYS")


def from_base64(s):
    return base64.b64decode(s).decode()


def get_config():
    return {
            "user": os.environ.get("USER_OCID"),
            "key_content": from_base64(os.environ.get("KEY_CONTENT")),
            "fingerprint": os.environ.get("KEY_FINGERPRINT"),
            "tenancy": os.environ.get("TENANCY"),
            "region": os.environ.get("REGION")
    }
