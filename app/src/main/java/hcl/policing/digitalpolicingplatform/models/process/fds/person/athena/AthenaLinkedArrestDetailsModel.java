package hcl.policing.digitalpolicingplatform.models.process.fds.person.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AthenaLinkedArrestDetailsModel {

    @Expose
    @SerializedName("ArrestDetailResponse")
    private ArrestdetailresponseBean arrestdetailresponse;

    public ArrestdetailresponseBean getArrestdetailresponse() {
        return arrestdetailresponse;
    }

    public void setArrestdetailresponse(ArrestdetailresponseBean arrestdetailresponse) {
        this.arrestdetailresponse = arrestdetailresponse;
    }

    public static class ArrestdetailresponseBean {
        @Expose
        @SerializedName("SecureData")
        private boolean securedata;
        @Expose
        @SerializedName("Reason")
        private String reason;
        @Expose
        @SerializedName("OvertFlag")
        private boolean overtflag;
        @Expose
        @SerializedName("OfficerUnit")
        private String officerunit;
        @Expose
        @SerializedName("Officer")
        private String officer;
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
        @SerializedName("ArrestedDateTime")
        private String arresteddatetime;

        public boolean getSecuredata() {
            return securedata;
        }

        public void setSecuredata(boolean securedata) {
            this.securedata = securedata;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public boolean getOvertflag() {
            return overtflag;
        }

        public void setOvertflag(boolean overtflag) {
            this.overtflag = overtflag;
        }

        public String getOfficerunit() {
            return officerunit;
        }

        public void setOfficerunit(String officerunit) {
            this.officerunit = officerunit;
        }

        public String getOfficer() {
            return officer;
        }

        public void setOfficer(String officer) {
            this.officer = officer;
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

        public String getArresteddatetime() {
            return arresteddatetime;
        }

        public void setArresteddatetime(String arresteddatetime) {
            this.arresteddatetime = arresteddatetime;
        }
    }
}
