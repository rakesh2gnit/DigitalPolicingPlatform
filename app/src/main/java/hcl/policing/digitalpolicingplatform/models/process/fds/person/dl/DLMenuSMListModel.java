package hcl.policing.digitalpolicingplatform.models.process.fds.person.dl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DLMenuSMListModel implements Serializable {
    @Expose
    @SerializedName("Description")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
