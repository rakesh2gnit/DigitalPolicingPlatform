package hcl.policing.digitalpolicingplatform.listeners;

import java.io.Serializable;

public interface DateTimeListener extends Serializable {

    void onDateSet(String dateTime);
}
