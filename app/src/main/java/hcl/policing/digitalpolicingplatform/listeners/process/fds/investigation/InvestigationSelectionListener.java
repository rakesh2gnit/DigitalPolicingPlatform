package hcl.policing.digitalpolicingplatform.listeners.process.fds.investigation;

import java.io.Serializable;

import hcl.policing.digitalpolicingplatform.models.process.fds.address.AddressBean;

public interface InvestigationSelectionListener extends Serializable {

    void onInvestigationSelected(AddressBean addressBean);
}

