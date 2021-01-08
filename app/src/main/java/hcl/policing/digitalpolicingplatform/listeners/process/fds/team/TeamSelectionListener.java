package hcl.policing.digitalpolicingplatform.listeners.process.fds.team;

import java.io.Serializable;

import hcl.policing.digitalpolicingplatform.models.process.fds.person.PersonBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.team.TeamBean;

public interface TeamSelectionListener extends Serializable {

    void onTeamSelected(TeamBean teamBean);
}
