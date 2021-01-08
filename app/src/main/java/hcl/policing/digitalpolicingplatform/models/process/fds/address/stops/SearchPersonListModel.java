package hcl.policing.digitalpolicingplatform.models.process.fds.address.stops;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchPersonListModel {
    @Expose
    @SerializedName("PersonID")
    private String personid;
    @Expose
    @SerializedName("OfficerDefinedEthnicity")
    private String officerdefinedethnicity;
    @Expose
    @SerializedName("MiddleName")
    private String middlename;
    @Expose
    @SerializedName("LastName")
    private String lastname;
    @Expose
    @SerializedName("Gender")
    private String gender;
    @Expose
    @SerializedName("FirstName")
    private String firstname;
    @Expose
    @SerializedName("Ethnicity")
    private String ethnicity;
    @Expose
    @SerializedName("DOB")
    private String dob;
    @Expose
    @SerializedName("TotalRecordCount")
    private int totalrecordcount;
    @Expose
    @SerializedName("TotalPageCount")
    private int totalpagecount;
    @Expose
    @SerializedName("CurrentPage")
    private int currentpage;

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }

    public String getOfficerdefinedethnicity() {
        return officerdefinedethnicity;
    }

    public void setOfficerdefinedethnicity(String officerdefinedethnicity) {
        this.officerdefinedethnicity = officerdefinedethnicity;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getTotalrecordcount() {
        return totalrecordcount;
    }

    public void setTotalrecordcount(int totalrecordcount) {
        this.totalrecordcount = totalrecordcount;
    }

    public int getTotalpagecount() {
        return totalpagecount;
    }

    public void setTotalpagecount(int totalpagecount) {
        this.totalpagecount = totalpagecount;
    }

    public int getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(int currentpage) {
        this.currentpage = currentpage;
    }
}
