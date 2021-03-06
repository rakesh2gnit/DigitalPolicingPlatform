package hcl.policing.digitalpolicingplatform.models.process.fds.courtwarrant.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import hcl.policing.digitalpolicingplatform.models.process.fds.investigation.athena.EnquiryLogsModel;

public class CourtWarrantModel implements Serializable {

    @Expose
    @SerializedName("logEntries")
    private List<EnquiryLogsModel> logentries;
    @Expose
    @SerializedName("WarrantReference")
    private String warrantreference;
    @Expose
    @SerializedName("Warnings")
    private List<String> warnings;
    @Expose
    @SerializedName("Ward")
    private String ward;
    @Expose
    @SerializedName("Type")
    private String type;
    @Expose
    @SerializedName("SubjectBailed")
    private String subjectbailed;
    @Expose
    @SerializedName("Status")
    private String status;
    @Expose
    @SerializedName("SecureData")
    private boolean securedata;
    @Expose
    @SerializedName("OvertFlag")
    private boolean overtflag;
    @Expose
    @SerializedName("IssueDate")
    private String issuedate;
    @Expose
    @SerializedName("ID")
    private String id;
    @Expose
    @SerializedName("Force")
    private String force;
    @Expose
    @SerializedName("FlagMessage")
    private String flagmessage;
    @Expose
    @SerializedName("FlagDescription")
    private String flagdescription;
    @Expose
    @SerializedName("DueDate")
    private String duedate;
    @Expose
    @SerializedName("District")
    private String district;
    @Expose
    @SerializedName("Details")
    private String details;
    @Expose
    @SerializedName("Category")
    private String category;
    @Expose
    @SerializedName("CCCJS")
    private String cccjs;
    @Expose
    @SerializedName("BCU")
    private String bcu;
    @Expose
    @SerializedName("shortSummary")
    private String shortsummary;

    public List<EnquiryLogsModel> getLogentries() {
        return logentries;
    }

    public void setLogentries(List<EnquiryLogsModel> logentries) {
        this.logentries = logentries;
    }

    public String getWarrantreference() {
        return warrantreference;
    }

    public void setWarrantreference(String warrantreference) {
        this.warrantreference = warrantreference;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubjectbailed() {
        return subjectbailed;
    }

    public void setSubjectbailed(String subjectbailed) {
        this.subjectbailed = subjectbailed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getSecuredata() {
        return securedata;
    }

    public void setSecuredata(boolean securedata) {
        this.securedata = securedata;
    }

    public boolean getOvertflag() {
        return overtflag;
    }

    public void setOvertflag(boolean overtflag) {
        this.overtflag = overtflag;
    }

    public String getIssuedate() {
        return issuedate;
    }

    public void setIssuedate(String issuedate) {
        this.issuedate = issuedate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getForce() {
        return force;
    }

    public void setForce(String force) {
        this.force = force;
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

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCccjs() {
        return cccjs;
    }

    public void setCccjs(String cccjs) {
        this.cccjs = cccjs;
    }

    public String getBcu() {
        return bcu;
    }

    public void setBcu(String bcu) {
        this.bcu = bcu;
    }

    public String getShortsummary() {
        return shortsummary;
    }

    public void setShortsummary(String shortsummary) {
        this.shortsummary = shortsummary;
    }


}
