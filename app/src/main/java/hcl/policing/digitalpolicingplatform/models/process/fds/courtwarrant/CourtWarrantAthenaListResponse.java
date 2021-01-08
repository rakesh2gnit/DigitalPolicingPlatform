package hcl.policing.digitalpolicingplatform.models.process.fds.courtwarrant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourtWarrantAthenaListResponse {

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
        @SerializedName("courtWarrentList")
        private List<CourtWarrentListBean> courtwarrentlist;
        @Expose
        @SerializedName("TotalRecordCount")
        private int totalrecordcount;
        @Expose
        @SerializedName("TotalPageCount")
        private int totalpagecount;
        @Expose
        @SerializedName("CurrentPage")
        private int currentpage;

        public List<CourtWarrentListBean> getCourtwarrentlist() {
            return courtwarrentlist;
        }

        public void setCourtwarrentlist(List<CourtWarrentListBean> courtwarrentlist) {
            this.courtwarrentlist = courtwarrentlist;
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

    public static class CourtWarrentListBean {
        @Expose
        @SerializedName("WarrantReference")
        private String warrantreference;
        @Expose
        @SerializedName("Type")
        private String type;
        @Expose
        @SerializedName("SurName")
        private String surname;
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
        @SerializedName("FlagMessage")
        private String flagmessage;
        @Expose
        @SerializedName("FlagDescription")
        private String flagdescription;
        @Expose
        @SerializedName("DueDate")
        private String duedate;
        @Expose
        @SerializedName("Category")
        private String category;

        public String getWarrantreference() {
            return warrantreference;
        }

        public void setWarrantreference(String warrantreference) {
            this.warrantreference = warrantreference;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
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

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }
}
