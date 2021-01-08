package hcl.policing.digitalpolicingplatform.models.process.fds.person.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AthenaLinkedCustodyDetailsModel {

    @Expose
    @SerializedName("CustodyDetailResponse")
    private CustodydetailresponseBean custodydetailresponse;

    public CustodydetailresponseBean getCustodydetailresponse() {
        return custodydetailresponse;
    }

    public void setCustodydetailresponse(CustodydetailresponseBean custodydetailresponse) {
        this.custodydetailresponse = custodydetailresponse;
    }

    public static class CustodydetailresponseBean {
        @Expose
        @SerializedName("Type")
        private String type;
        @Expose
        @SerializedName("Status")
        private String status;
        @Expose
        @SerializedName("Station")
        private String station;
        @Expose
        @SerializedName("SecureData")
        private boolean securedata;
        @Expose
        @SerializedName("RecordNo")
        private String recordno;
        @Expose
        @SerializedName("Reasons")
        private List<ReasonsBean> reasons;
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
        @SerializedName("Bail")
        private List<BailBean> bail;
        @Expose
        @SerializedName("Arrived")
        private String arrived;
        @Expose
        @SerializedName("ArrestTime")
        private String arresttime;

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

        public String getStation() {
            return station;
        }

        public void setStation(String station) {
            this.station = station;
        }

        public boolean getSecuredata() {
            return securedata;
        }

        public void setSecuredata(boolean securedata) {
            this.securedata = securedata;
        }

        public String getRecordno() {
            return recordno;
        }

        public void setRecordno(String recordno) {
            this.recordno = recordno;
        }

        public List<ReasonsBean> getReasons() {
            return reasons;
        }

        public void setReasons(List<ReasonsBean> reasons) {
            this.reasons = reasons;
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

        public List<BailBean> getBail() {
            return bail;
        }

        public void setBail(List<BailBean> bail) {
            this.bail = bail;
        }

        public String getArrived() {
            return arrived;
        }

        public void setArrived(String arrived) {
            this.arrived = arrived;
        }

        public String getArresttime() {
            return arresttime;
        }

        public void setArresttime(String arresttime) {
            this.arresttime = arresttime;
        }
    }

    public static class ReasonsBean {
        @Expose
        @SerializedName("OIC")
        private String oic;
        @Expose
        @SerializedName("ID")
        private String id;
        @Expose
        @SerializedName("Description")
        private String description;
        @Expose
        @SerializedName("DateTime")
        private String datetime;
        @Expose
        @SerializedName("CrimeNo")
        private String crimeno;

        public String getOic() {
            return oic;
        }

        public void setOic(String oic) {
            this.oic = oic;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getCrimeno() {
            return crimeno;
        }

        public void setCrimeno(String crimeno) {
            this.crimeno = crimeno;
        }
    }

    public static class BailBean {
    }
}
