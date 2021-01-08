package hcl.policing.digitalpolicingplatform.listeners.process.fds.address;

import java.io.Serializable;

import hcl.policing.digitalpolicingplatform.models.process.fds.address.AddressBean;

public interface AddressSelectionListener extends Serializable {

    void onAddressSelected(AddressBean addressBean);
}
