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
VCN_IP_RANGE = '10.0.0.0/16'
SUBNET_IP_RANGE = '10.0.0.0/24'

INSTANCE_NAME = "nolocal-instance"
INSTANCE_OPERATING_SYSTEM = "Canonical Ubuntu"
INSTANCE_OPERATING_SYSTEM_VERSION = "20.04"
INSTANCE_SHAPE = "VM.Standard.E2.1.Micro"


def from_base64(s):
    if s:
        return base64.b64decode(s).decode()


CONFIG = {
    "user": os.environ.get("USER_OCID"),
    "key_content": from_base64(os.environ.get("KEY_CONTENT")),
    "fingerprint": os.environ.get("KEY_FINGERPRINT"),
    "tenancy": os.environ.get("TENANCY"),
    "region": os.environ.get("REGION"),
    "availability_domain": os.environ.get("AVAILABILITY_DOMAIN"),
    "authorized_keys": os.environ.get("AUTHORIZED_KEYS")
}


def get_config():
    return CONFIG


def set_config(argv):
    if len(argv) > 7:
        CONFIG["availability_domain"] = argv[1]
        CONFIG["user"] = argv[2]
        CONFIG["fingerprint"] = argv[3]
        CONFIG["tenancy"] = argv[4]
        CONFIG["region"] = argv[5]
        CONFIG["key_content"] = from_base64(argv[6])
        CONFIG["authorized_keys"] = from_base64(argv[7])
