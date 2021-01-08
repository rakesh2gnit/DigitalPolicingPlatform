package hcl.policing.digitalpolicingplatform.models.process.fds.cases.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DefendantsModel {
    @Expose
    @SerializedName("SurName")
    private String surname;
    @Expose
    @SerializedName("Offences")
    private List<OffencesModel> offences;
    @Expose
    @SerializedName("ID")
    private String id;
    @Expose
    @SerializedName("Gender")
    private String gender;
    @Expose
    @SerializedName("FirstName2")
    private String firstname2;
    @Expose
    @SerializedName("FirstName1")
    private String firstname1;
    @Expose
    @SerializedName("DOB")
    private String dob;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<OffencesModel> getOffences() {
        return offences;
    }

    public void setOffences(List<OffencesModel> offences) {
        this.offences = offences;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstname2() {
        return firstname2;
    }

    public void setFirstname2(String firstname2) {
        this.firstname2 = firstname2;
    }

    public String getFirstname1() {
        return firstname1;
    }

    public void setFirstname1(String firstname1) {
        this.firstname1 = firstname1;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
