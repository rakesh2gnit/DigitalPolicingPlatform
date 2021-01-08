package hcl.policing.digitalpolicingplatform.models.process.fds.person.pnc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressesBean {
    @Expose
    @SerializedName("Description")
    private String description;
    @Expose
    @SerializedName("DateUpdated")
    private String dateupdated;
    @Expose
    @SerializedName("DateAt")
    private String dateat;
    @Expose
    @SerializedName("AddressDescription")
    private String addressdescription;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(String dateupdated) {
        this.dateupdated = dateupdated;
    }

    public String getDateat() {
        return dateat;
    }

    public void setDateat(String dateat) {
        this.dateat = dateat;
    }

    public String getAddressdescription() {
        return addressdescription;
    }

    public void setAddressdescription(String addressdescription) {
        this.addressdescription = addressdescription;
    }
}
