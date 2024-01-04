from oci.core.models import LaunchInstanceDetails, LaunchInstanceShapeConfigDetails

import oci


class ComputeOciClient:

    def __init__(self, configuration, compartment_id):
        self.configuration = configuration
        oci.config.validate_config(self.configuration.DICTIONARY)
        self.compartment_id = compartment_id
        self.client = oci.core.ComputeClient(self.configuration.DICTIONARY)

    def get_instance(self):
        instance = self.client.list_instances(
            self.compartment_id,
            display_name=self.configuration.INSTANCE_NAME,
            lifecycle_state='RUNNING',
        )
        if not instance.data:
            return None
        return instance.data[0].id

    def get_vnic_id(self, instance_id):
        vnics = self.client.list_vnic_attachments(
            self.compartment_id,
            instance_id=instance_id,
        )
        if not vnics:
            return None
        return vnics.data[0].vnic_id

    def create_instance(self, subnet_id):
        image_id = self.get_image()
        request = self.__create_request(self.compartment_id, subnet_id, image_id)
        instance = self.client.launch_instance(request)
        instance = self.client.get_instance(instance.data.id)
        instance = oci.wait_until(self.client, instance, 'lifecycle_state', 'RUNNING')
        return instance.data.id

    def get_image(self):
        images = self.client.list_images(
            compartment_id=self.compartment_id,
            operating_system=self.configuration.INSTANCE_OPERATING_SYSTEM,
            operating_system_version=self.configuration.INSTANCE_OPERATING_SYSTEM_VERSION,
            lifecycle_state='AVAILABLE',
            sort_by='TIMECREATED'
        )
        is_aarch64 = self.configuration.is_aarch64()
        if is_aarch64:
            return [image.id for image in images.data if "aarch64" in image.display_name][0]
        else:
            return [image.id for image in images.data if "aarch64" not in image.display_name][0]

    def delete_instance(self, instance_id):
        self.client.terminate_instance(instance_id)
        instance = self.client.get_instance(instance_id)
        oci.wait_until(self.client, instance, 'lifecycle_state', 'TERMINATED')

    def __create_request(self, compartment_id, subnet_id, image_id):
        request = LaunchInstanceDetails()
        request.compartment_id = compartment_id
        request.image_id = image_id
        request.subnet_id = subnet_id
        request.availability_domain = self.configuration.DICTIONARY["availability_domain"]
        request.shape = self.configuration.INSTANCE_SHAPE
        request.shape_config = self.__create_shape_config()
        request.display_name = self.configuration.INSTANCE_NAME
        request.metadata = {"ssh_authorized_keys": self.configuration.DICTIONARY["authorized_keys"]}
        return request

    def __create_shape_config(self):
        if self.configuration.INSTANCE_SHAPE_CONFIG is None:
            return None
        shape_config = LaunchInstanceShapeConfigDetails()
        shape_config.ocpus = self.configuration.INSTANCE_SHAPE_CONFIG["ocpus"]
        shape_config.memory_in_gbs = self.configuration.INSTANCE_SHAPE_CONFIG["memory_in_gbs"]
        return shape_config
