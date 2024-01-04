import time
import oci
from oci.identity.models import CreateCompartmentDetails


class IdentityOciClient:

    def __init__(self, config):
        self.configuration = config
        oci.config.validate_config(self.configuration.DICTIONARY)
        self.tenant = self.configuration.DICTIONARY["tenancy"]
        self.client = oci.identity.IdentityClient(self.configuration.DICTIONARY)

    def get_compartment(self):
        response = self.client.list_compartments(
            compartment_id=self.tenant,
            lifecycle_state="ACTIVE",
            name=self.configuration.COMPARTMENT_NAME
        )
        if not response.data:
            return None
        return response.data[0].id

    def create_compartment(self):
        details = CreateCompartmentDetails()
        details.compartment_id = self.tenant
        details.name = self.configuration.COMPARTMENT_NAME
        details.description = self.configuration.COMPARTMENT_DESCRIPTION
        compartment = self.client.create_compartment(
            create_compartment_details=details
        )
        prevent_bug_by_waiting_before_get()
        compartment = self.client.get_compartment(compartment.data.id)
        compartment = oci.wait_until(self.client, compartment, 'lifecycle_state', 'ACTIVE')
        return compartment.data.id

    def delete_compartment(self, compartment_id):
        self.client.delete_compartment(compartment_id)
        to_delete_compartment = self.client.get_compartment(compartment_id)
        oci.wait_until(self.client, to_delete_compartment, 'lifecycle_state', 'DELETED')


# Bug: get_compartment doesn't work right after creation, it should
def prevent_bug_by_waiting_before_get():
    time.sleep(15)
