package hcl.policing.digitalpolicingplatform.models.process.fds.person.dl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DrivinglicencebyidlistModel implements Serializable {

    @Expose
    @SerializedName("Title")
    private String title;
    @Expose
    @SerializedName("RecordType")
    private String recordtype;
    @Expose
    @SerializedName("Postcode")
    private String postcode;
    @Expose
    @SerializedName("Points")
    private String points;
    @Expose
    @SerializedName("Other")
    private String other;
    @Expose
    @SerializedName("Name")
    private String name;
    @Expose
    @SerializedName("LicenceType")
    private String licencetype;
    @Expose
    @SerializedName("LicenceIssueNo")
    private String licenceissueno;
    @Expose
    @SerializedName("LastUpdated")
    private String lastupdated;
    @Expose
    @SerializedName("Gender")
    private String gender;
    @Expose
    @SerializedName("ExpiryDate")
    private String expirydate;
    @Expose
    @SerializedName("DrivingLicenceNo")
    private String drivinglicenceno;
    @Expose
    @SerializedName("DrinkDrug")
    private String drinkdrug;
    @Expose
    @SerializedName("Disqualified")
    private String disqualified;
    @Expose
    @SerializedName("Disqualification")
    private String disqualification;
    @Expose
    @SerializedName("DOB")
    private String dob;
    @Expose
    @SerializedName("CounterPartIssueNo")
    private String counterpartissueno;
    @Expose
    @SerializedName("CommencementDate")
    private String commencementdate;
    @Expose
    @SerializedName("BirthPlace")
    private String birthplace;
    @Expose
    @SerializedName("Address")
    private String address;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRecordtype() {
        return recordtype;
    }

    public void setRecordtype(String recordtype) {
        this.recordtype = recordtype;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicencetype() {
        return licencetype;
    }

    public void setLicencetype(String licencetype) {
        this.licencetype = licencetype;
    }

    public String getLicenceissueno() {
        return licenceissueno;
    }

    public void setLicenceissueno(String licenceissueno) {
        this.licenceissueno = licenceissueno;
    }

    public String getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(String lastupdated) {
        this.lastupdated = lastupdated;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    public String getDrivinglicenceno() {
        return drivinglicenceno;
    }

    public void setDrivinglicenceno(String drivinglicenceno) {
        this.drivinglicenceno = drivinglicenceno;
    }

    public String getDrinkdrug() {
        return drinkdrug;
    }

    public void setDrinkdrug(String drinkdrug) {
        this.drinkdrug = drinkdrug;
    }

    public String getDisqualified() {
        return disqualified;
    }

    public void setDisqualified(String disqualified) {
        this.disqualified = disqualified;
    }

    public String getDisqualification() {
        return disqualification;
    }

    public void setDisqualification(String disqualification) {
        this.disqualification = disqualification;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCounterpartissueno() {
        return counterpartissueno;
    }

    public void setCounterpartissueno(String counterpartissueno) {
        this.counterpartissueno = counterpartissueno;
    }

    public String getCommencementdate() {
        return commencementdate;
    }

    public void setCommencementdate(String commencementdate) {
        this.commencementdate = commencementdate;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
