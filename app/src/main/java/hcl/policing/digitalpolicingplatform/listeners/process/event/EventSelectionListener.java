package hcl.policing.digitalpolicingplatform.listeners.process.event;

import java.io.Serializable;

import hcl.policing.digitalpolicingplatform.models.process.crime.EventSearchList;

public interface EventSelectionListener extends Serializable {

    void onEventSelected(EventSearchList eventSearchList);
}
