package hcl.policing.digitalpolicingplatform.models.process.fds.person.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AthenaLinkedOffenceDetailsModel {

    @Expose
    @SerializedName("OffenceDetailResponse")
    private OffencedetailresponseBean offencedetailresponse;

    public OffencedetailresponseBean getOffencedetailresponse() {
        return offencedetailresponse;
    }

    public void setOffencedetailresponse(OffencedetailresponseBean offencedetailresponse) {
        this.offencedetailresponse = offencedetailresponse;
    }

    public static class OffencedetailresponseBean {
        @Expose
        @SerializedName("StatusChange")
        private String statuschange;
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
        @SerializedName("OffenceDate")
        private String offencedate;
        @Expose
        @SerializedName("Location")
        private String location;
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
        @SerializedName("CreatedDate")
        private String createddate;
        @Expose
        @SerializedName("ChargeWording")
        private String chargewording;
        @Expose
        @SerializedName("AssociatedCrime")
        private String associatedcrime;

        public String getStatuschange() {
            return statuschange;
        }

        public void setStatuschange(String statuschange) {
            this.statuschange = statuschange;
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

        public String getOffencedate() {
            return offencedate;
        }

        public void setOffencedate(String offencedate) {
            this.offencedate = offencedate;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
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

        public String getCreateddate() {
            return createddate;
        }

        public void setCreateddate(String createddate) {
            this.createddate = createddate;
        }

        public String getChargewording() {
            return chargewording;
        }

        public void setChargewording(String chargewording) {
            this.chargewording = chargewording;
        }

        public String getAssociatedcrime() {
            return associatedcrime;
        }

        public void setAssociatedcrime(String associatedcrime) {
            this.associatedcrime = associatedcrime;
        }
    }
}
