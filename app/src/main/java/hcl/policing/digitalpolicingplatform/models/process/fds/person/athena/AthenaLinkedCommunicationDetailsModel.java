package hcl.policing.digitalpolicingplatform.models.process.fds.person.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AthenaLinkedCommunicationDetailsModel {

    @Expose
    @SerializedName("CommunicationDetailResponse")
    private CommunicationdetailresponseBean communicationdetailresponse;

    public CommunicationdetailresponseBean getCommunicationdetailresponse() {
        return communicationdetailresponse;
    }

    public void setCommunicationdetailresponse(CommunicationdetailresponseBean communicationdetailresponse) {
        this.communicationdetailresponse = communicationdetailresponse;
    }

    public static class CommunicationdetailresponseBean {
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

    public static class WarningsBean {
        @Expose
        @SerializedName("markerType")
        private String markertype;
        @Expose
        @SerializedName("markerSource")
        private String markersource;
        @Expose
        @SerializedName("lastModifiedDateTime")
        private String lastmodifieddatetime;
        @Expose
        @SerializedName("createdDateTime")
        private String createddatetime;
        @Expose
        @SerializedName("MarkerValue")
        private String markervalue;
        @Expose
        @SerializedName("ID")
        private String id;
        @Expose
        @SerializedName("FromDate")
        private String fromdate;
        @Expose
        @SerializedName("Description")
        private String description;

        public String getMarkertype() {
            return markertype;
        }

        public void setMarkertype(String markertype) {
            this.markertype = markertype;
        }

        public String getMarkersource() {
            return markersource;
        }

        public void setMarkersource(String markersource) {
            this.markersource = markersource;
        }

        public String getLastmodifieddatetime() {
            return lastmodifieddatetime;
        }

        public void setLastmodifieddatetime(String lastmodifieddatetime) {
            this.lastmodifieddatetime = lastmodifieddatetime;
        }

        public String getCreateddatetime() {
            return createddatetime;
        }

        public void setCreateddatetime(String createddatetime) {
            this.createddatetime = createddatetime;
        }

        public String getMarkervalue() {
            return markervalue;
        }

        public void setMarkervalue(String markervalue) {
            this.markervalue = markervalue;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFromdate() {
            return fromdate;
        }

        public void setFromdate(String fromdate) {
            this.fromdate = fromdate;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}

