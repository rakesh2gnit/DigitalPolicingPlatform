package hcl.policing.digitalpolicingplatform.listeners.process.offence;

import java.io.Serializable;

import hcl.policing.digitalpolicingplatform.models.process.crime.HOOffenceList;
import hcl.policing.digitalpolicingplatform.models.process.crime.OffenceDefinitionList;

public interface OffenceSelectionListener extends Serializable {

    void onOffenceSelected(HOOffenceList hoOffenceList);
}
