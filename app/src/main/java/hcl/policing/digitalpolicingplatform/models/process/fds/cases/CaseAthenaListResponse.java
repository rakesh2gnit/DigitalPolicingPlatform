package hcl.policing.digitalpolicingplatform.models.process.fds.cases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CaseAthenaListResponse {

    @Expose
    @SerializedName("SearchAthenaListResponse")
    private SearchathenalistresponseBean searchathenalistresponse;

    public SearchathenalistresponseBean getSearchathenalistresponse() {
        return searchathenalistresponse;
    }

    public void setSearchathenalistresponse(SearchathenalistresponseBean searchathenalistresponse) {
        this.searchathenalistresponse = searchathenalistresponse;
    }

    public static class SearchathenalistresponseBean {
        @Expose
        @SerializedName("caseList")
        private List<CaseListBean> caselist;
        @Expose
        @SerializedName("TotalRecordCount")
        private int totalrecordcount;
        @Expose
        @SerializedName("TotalPageCount")
        private int totalpagecount;
        @Expose
        @SerializedName("CurrentPage")
        private int currentpage;

        public List<CaseListBean> getCaselist() {
            return caselist;
        }

        public void setCaselist(List<CaseListBean> caselist) {
            this.caselist = caselist;
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

    public static class CaseListBean {
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
        @SerializedName("OIC")
        private String oic;
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
        @SerializedName("FileType")
        private String filetype;
        @Expose
        @SerializedName("CaseReference")
        private String casereference;

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

        public String getFiletype() {
            return filetype;
        }

        public void setFiletype(String filetype) {
            this.filetype = filetype;
        }

        public String getCasereference() {
            return casereference;
        }

        public void setCasereference(String casereference) {
            this.casereference = casereference;
        }
    }
}
