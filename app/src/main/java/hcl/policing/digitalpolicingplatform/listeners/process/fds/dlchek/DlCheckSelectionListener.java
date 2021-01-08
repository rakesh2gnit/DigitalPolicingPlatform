package hcl.policing.digitalpolicingplatform.listeners.process.fds.dlchek;

import java.io.Serializable;

import hcl.policing.digitalpolicingplatform.models.process.fds.person.PersonBean;

public interface DlCheckSelectionListener extends Serializable {

    void onDlCheckSelected(PersonBean personBean);
}
