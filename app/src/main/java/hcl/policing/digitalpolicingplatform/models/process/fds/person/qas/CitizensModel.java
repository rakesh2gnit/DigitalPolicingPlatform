package hcl.policing.digitalpolicingplatform.models.process.fds.person.qas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CitizensModel {
    @Expose
    @SerializedName("Title")
    private String title;
    @Expose
    @SerializedName("DisplayName")
    private String displayname;
    @Expose
    @SerializedName("CitizenNameSurname")
    private String citizennamesurname;
    @Expose
    @SerializedName("CitizenNameForename")
    private String citizennameforename;
    @Expose
    @SerializedName("AddressDetails")
    private AddressdetailsModel addressdetails;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getCitizennamesurname() {
        return citizennamesurname;
    }

    public void setCitizennamesurname(String citizennamesurname) {
        this.citizennamesurname = citizennamesurname;
    }

    public String getCitizennameforename() {
        return citizennameforename;
    }

    public void setCitizennameforename(String citizennameforename) {
        this.citizennameforename = citizennameforename;
    }

    public AddressdetailsModel getAddressdetails() {
        return addressdetails;
    }

    public void setAddressdetails(AddressdetailsModel addressdetails) {
        this.addressdetails = addressdetails;
    }
}
