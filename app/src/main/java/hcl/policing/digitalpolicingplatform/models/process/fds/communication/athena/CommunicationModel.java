package hcl.policing.digitalpolicingplatform.models.process.fds.communication.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.WarningsModel;

public class CommunicationModel implements Serializable {
    @Expose
    @SerializedName("shortSummary")
    private String shortsummary;
    @Expose
    @SerializedName("Warnings")
    private List<WarningsModel> warnings;
    @Expose
    @SerializedName("Type")
    private String type;
    @Expose
    @SerializedName("Status")
    private String status;
    @Expose
    @SerializedName("SecureData")
    private boolean securedata;
    @Expose
    @SerializedName("Remarks")
    private String remarks;
    @Expose
    @SerializedName("OvertFlag")
    private boolean overtflag;
    @Expose
    @SerializedName("ID")
    private String id;
    @Expose
    @SerializedName("FlagMessage")
    private String flagmessage;
    @Expose
    @SerializedName("FlagDescription")
    private String flagdescription;
    @Expose
    @SerializedName("Details")
    private String details;
    @Expose
    @SerializedName("CreatedDate")
    private String createddate;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean getOvertflag() {
        return overtflag;
    }

    public void setOvertflag(boolean overtflag) {
        this.overtflag = overtflag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }
}
