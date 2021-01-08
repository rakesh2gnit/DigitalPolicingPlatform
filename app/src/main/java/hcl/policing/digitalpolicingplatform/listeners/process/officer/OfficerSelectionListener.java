package hcl.policing.digitalpolicingplatform.listeners.process.officer;

import hcl.policing.digitalpolicingplatform.models.process.officer.OfficerResponse;

public interface OfficerSelectionListener {

    void onOfficerSelected(OfficerResponse officerListModel);
}
