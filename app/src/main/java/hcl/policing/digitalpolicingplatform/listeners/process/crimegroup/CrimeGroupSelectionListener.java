package hcl.policing.digitalpolicingplatform.listeners.process.crimegroup;

import java.io.Serializable;

import hcl.policing.digitalpolicingplatform.models.process.crime.CrimeGroupList;
import hcl.policing.digitalpolicingplatform.models.process.crime.HOOffenceList;

public interface CrimeGroupSelectionListener extends Serializable {

    void onCrimeGroupSelected(CrimeGroupList crimeGroupList);
}
