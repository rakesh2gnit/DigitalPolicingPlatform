package hcl.policing.digitalpolicingplatform.models.process.fds.investigation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class InvestigationAthenaListResponse implements Serializable {

    @Expose
    @SerializedName("SearchAthenaListResponse")
    private SearchathenalistresponseBean searchathenalistresponse;

    public SearchathenalistresponseBean getSearchathenalistresponse() {
        return searchathenalistresponse;
    }

    public void setSearchathenalistresponse(SearchathenalistresponseBean searchathenalistresponse) {
        this.searchathenalistresponse = searchathenalistresponse;
    }

    public static class SearchathenalistresponseBean implements Serializable{
        @Expose
        @SerializedName("investigationList")
        private List<InvestigationListBean> investigationlist;
        @Expose
        @SerializedName("TotalRecordCount")
        private int totalrecordcount;
        @Expose
        @SerializedName("TotalPageCount")
        private int totalpagecount;
        @Expose
        @SerializedName("CurrentPage")
        private int currentpage;

        public List<InvestigationListBean> getInvestigationlist() {
            return investigationlist;
        }

        public void setInvestigationlist(List<InvestigationListBean> investigationlist) {
            this.investigationlist = investigationlist;
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

    public static class InvestigationListBean implements Serializable{
        @Expose
        @SerializedName("relevantDateTime")
        private String relevantdatetime;
        @Expose
        @SerializedName("Summary")
        private String summary;
        @Expose
        @SerializedName("SecureData")
        private boolean securedata;
        @Expose
        @SerializedName("OvertFlag")
        private boolean overtflag;
        @Expose
        @SerializedName("OIC")
        private String oic;
        @Expose
        @SerializedName("InvestigationNumber")
        private String investigationnumber;
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
        @SerializedName("CC_Number")
        private String ccNumber;

        public String getRelevantdatetime() {
            return relevantdatetime;
        }

        public void setRelevantdatetime(String relevantdatetime) {
            this.relevantdatetime = relevantdatetime;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
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

        public String getOic() {
            return oic;
        }

        public void setOic(String oic) {
            this.oic = oic;
        }

        public String getInvestigationnumber() {
            return investigationnumber;
        }

        public void setInvestigationnumber(String investigationnumber) {
            this.investigationnumber = investigationnumber;
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

        public String getCcNumber() {
            return ccNumber;
        }

        public void setCcNumber(String ccNumber) {
            this.ccNumber = ccNumber;
        }
    }
}
