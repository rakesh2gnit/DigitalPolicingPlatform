package hcl.policing.digitalpolicingplatform.models.process.fds.person.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PersonModel implements Serializable {
    @Expose
    @SerializedName("shortSummary")
    private String shortsummary;
    @Expose
    @SerializedName("Warnings")
    private List<WarningsModel> warnings;
    @Expose
    @SerializedName("SecureData")
    private boolean securedata;
    @Expose
    @SerializedName("Photos")
    private List<PhotosBean> photos;
    @Expose
    @SerializedName("PNC_ID")
    private String pncId;
    @Expose
    @SerializedName("PNC_FileName")
    private String pncFilename;
    @Expose
    @SerializedName("OvertFlag")
    private boolean overtflag;
    @Expose
    @SerializedName("LatestHomeAddress")
    private String latesthomeaddress;
    @Expose
    @SerializedName("LastName")
    private String lastname;
    @Expose
    @SerializedName("Height")
    private String height;
    @Expose
    @SerializedName("Gender")
    private String gender;
    @Expose
    @SerializedName("FlagMessage")
    private String flagmessage;
    @Expose
    @SerializedName("FlagDescription")
    private String flagdescription;
    @Expose
    @SerializedName("FirstName1")
    private String firstname1;
    @Expose
    @SerializedName("FirarmCertificateHolder")
    private String firarmcertificateholder;
    @Expose
    @SerializedName("Ethnicity")
    private String ethnicity;
    @Expose
    @SerializedName("DOB")
    private String dob;
    @Expose
    @SerializedName("Build")
    private String build;
    @Expose
    @SerializedName("Status")
    private String status;
    @Expose
    @SerializedName("Aliases")
    private List<AliasesBean> aliases;
    @Expose
    @SerializedName("AliasDetails")
    private String aliasdetails;

    public String getShortsummary() {
        return shortsummary;
    }

    public void setShortsummary(String shortsummary) {
        this.shortsummary = shortsummary;
    }

    public List<WarningsModel> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<WarningsModel> warnings) {
        this.warnings = warnings;
    }

    public boolean getSecuredata() {
        return securedata;
    }

    public void setSecuredata(boolean securedata) {
        this.securedata = securedata;
    }

    public List<PhotosBean> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotosBean> photos) {
        this.photos = photos;
    }

    public String getPncId() {
        return pncId;
    }

    public void setPncId(String pncId) {
        this.pncId = pncId;
    }

    public String getPncFilename() {
        return pncFilename;
    }

    public void setPncFilename(String pncFilename) {
        this.pncFilename = pncFilename;
    }

    public boolean getOvertflag() {
        return overtflag;
    }

    public void setOvertflag(boolean overtflag) {
        this.overtflag = overtflag;
    }

    public String getLatesthomeaddress() {
        return latesthomeaddress;
    }

    public void setLatesthomeaddress(String latesthomeaddress) {
        this.latesthomeaddress = latesthomeaddress;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFlagmessage() {
        return flagmessage;
    }

    public void setFlagmessage(String flagmessage) {
        this.flagmessage = flagmessage;
    }

    public String getFlagdescription() {
        return flagdescription;
    }

    public void setFlagdescription(String flagdescription) {
        this.flagdescription = flagdescription;
    }

    public String getFirstname1() {
        return firstname1;
    }

    public void setFirstname1(String firstname1) {
        this.firstname1 = firstname1;
    }

    public String getFirarmcertificateholder() {
        return firarmcertificateholder;
    }

    public void setFirarmcertificateholder(String firarmcertificateholder) {
        this.firarmcertificateholder = firarmcertificateholder;
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

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public List<AliasesBean> getAliases() {
        return aliases;
    }

    public void setAliases(List<AliasesBean> aliases) {
        this.aliases = aliases;
    }

    public String getAliasdetails() {
        return aliasdetails;
    }

    public void setAliasdetails(String aliasdetails) {
        this.aliasdetails = aliasdetails;
    }
}
