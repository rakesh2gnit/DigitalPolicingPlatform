package hcl.policing.digitalpolicingplatform.models.process.fds.cases.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HearingsModel {
    @Expose
    @SerializedName("Surname")
    private String surname;
    @Expose
    @SerializedName("ID")
    private String id;
    @Expose
    @SerializedName("HearingType")
    private String hearingtype;
    @Expose
    @SerializedName("FirstName2")
    private String firstname2;
    @Expose
    @SerializedName("FirstName1")
    private String firstname1;
    @Expose
    @SerializedName("CourtName")
    private String courtname;
    @Expose
    @SerializedName("CourtDate")
    private String courtdate;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHearingtype() {
        return hearingtype;
    }

    public void setHearingtype(String hearingtype) {
        this.hearingtype = hearingtype;
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

    public String getCourtname() {
        return courtname;
    }

    public void setCourtname(String courtname) {
        this.courtname = courtname;
    }

    public String getCourtdate() {
        return courtdate;
    }

    public void setCourtdate(String courtdate) {
        this.courtdate = courtdate;
    }
}
