package hcl.policing.digitalpolicingplatform.listeners.process.fds.courtwarrant;

import java.io.Serializable;

import hcl.policing.digitalpolicingplatform.models.process.fds.address.AddressBean;

public interface CourtWarrantSelectionListener extends Serializable {

    void onCourtWarrantSelected(AddressBean addressBean);
}

