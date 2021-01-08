package hcl.policing.digitalpolicingplatform.listeners.process.organisation;

import java.io.Serializable;

import hcl.policing.digitalpolicingplatform.models.process.crime.CrimeGroupList;
import hcl.policing.digitalpolicingplatform.models.process.crime.OrganisationDetailsList;

public interface OrganisationSelectionListener extends Serializable {

    void onOrganisationSelected(OrganisationDetailsList organisationDetailsList);
}
