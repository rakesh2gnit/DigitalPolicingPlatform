package hcl.policing.digitalpolicingplatform.listeners.process.fds.communication;

import java.io.Serializable;

import hcl.policing.digitalpolicingplatform.models.process.fds.address.AddressBean;

public interface CommunicationSelectionListener extends Serializable {

    void onCommunicationSelected(AddressBean addressBean);
}

