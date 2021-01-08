package hcl.policing.digitalpolicingplatform.listeners.process.fds.person;

import java.io.Serializable;

import hcl.policing.digitalpolicingplatform.models.process.fds.person.PersonBean;

public interface PersonSelectionListener extends Serializable {

    void onPersonSelected(PersonBean personBean);
}
