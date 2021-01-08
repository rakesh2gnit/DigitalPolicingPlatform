package hcl.policing.digitalpolicingplatform.listeners;

import java.io.Serializable;

public interface LogoutListener extends Serializable {

    void onLoggedOut();
}
