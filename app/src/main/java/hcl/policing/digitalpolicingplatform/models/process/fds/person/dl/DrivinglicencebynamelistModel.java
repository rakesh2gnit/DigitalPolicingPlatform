package hcl.policing.digitalpolicingplatform.models.process.fds.person.dl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DrivinglicencebynamelistModel implements Serializable {
    @Expose
    @SerializedName("SequenceNo")
    private String sequenceno;
    @Expose
    @SerializedName("Postcode")
    private String postcode;
    @Expose
    @SerializedName("PlaceOfBirth")
    private String placeofbirth;
    @Expose
    @SerializedName("Name")
    private String name;
    @Expose
    @SerializedName("Gender")
    private String gender;
    @Expose
    @SerializedName("DisqualifiedDriving")
    private String disqualifieddriving;
    @Expose
    @SerializedName("DOB")
    private String dob;

    public String getSequenceno() {
        return sequenceno;
    }

    public void setSequenceno(String sequenceno) {
        this.sequenceno = sequenceno;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPlaceofbirth() {
        return placeofbirth;
    }

    public void setPlaceofbirth(String placeofbirth) {
        this.placeofbirth = placeofbirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDisqualifieddriving() {
        return disqualifieddriving;
    }

    public void setDisqualifieddriving(String disqualifieddriving) {
        this.disqualifieddriving = disqualifieddriving;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
