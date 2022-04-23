from oci.core.models import LaunchInstanceDetails

from config import get_config
import config
import oci


def create_request(compartment_id, subnet_id, image_id):
    request = LaunchInstanceDetails()
    request.compartment_id = compartment_id
    request.image_id = image_id
    request.subnet_id = subnet_id
    request.availability_domain = config.INSTANCE_AVAILABILITY_DOMAIN
    request.shape = config.INSTANCE_SHAPE
    request.display_name = config.INSTANCE_NAME
    request.metadata = {"ssh_authorized_keys": config.INSTANCE_AUTHORIZED_KEYS}
    return request


class NoLocalComputeOciClient:

    def __init__(self, compartment_id):
        self.configuration = get_config()
        oci.config.validate_config(self.configuration)
        self.compartment_id = compartment_id
        self.client = oci.core.ComputeClient(self.configuration)

    def get_instance(self, name=config.INSTANCE_NAME):
        instance = self.client.list_instances(
            self.compartment_id,
            display_name=name,
            lifecycle_state='RUNNING',
        )
        if not instance.data:
            return None
        return instance.data[0].id

    def create_instance(self, subnet_id):
        image_id = self.get_image()
        request = create_request(self.compartment_id, subnet_id, image_id)
        instance = self.client.launch_instance(request)
        instance = self.client.get_instance(instance.data.id)
        instance = oci.wait_until(self.client, instance, 'lifecycle_state', 'RUNNING')
        return instance.data.id

    def get_image(self):
        images = self.client.list_images(
            compartment_id=self.compartment_id,
            operating_system=config.INSTANCE_OPERATING_SYSTEM,
            operating_system_version=config.INSTANCE_OPERATING_SYSTEM_VERSION,
            lifecycle_state='AVAILABLE',
            sort_by='TIMECREATED'
        )
        return [image for image in images.data if "aarch64" not in image.display_name][0].id

    def delete_instance(self, instance_id):
        self.client.terminate_instance(instance_id)
        instance = self.client.get_instance(instance_id)
        oci.wait_until(self.client, instance, 'lifecycle_state', 'TERMINATED')

