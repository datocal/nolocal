import time
import oci
from oci.identity.models import CreateCompartmentDetails

import config
from config import get_config


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
