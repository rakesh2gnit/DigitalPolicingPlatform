package hcl.policing.digitalpolicingplatform.listeners.process.fds.cases;

import java.io.Serializable;

import hcl.policing.digitalpolicingplatform.models.process.fds.address.AddressBean;

public interface CaseSelectionListener extends Serializable {

    void onCaseSelected(AddressBean addressBean);
}
