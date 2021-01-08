package hcl.policing.digitalpolicingplatform.listeners.process.allegation;

import java.io.Serializable;

import hcl.policing.digitalpolicingplatform.models.process.crime.EventSearchList;
import hcl.policing.digitalpolicingplatform.models.process.crime.OffenceDefinitionList;

public interface AllegationSelectionListener extends Serializable {

    void onAllegationSelected(OffenceDefinitionList offenceDefinitionList);
}
