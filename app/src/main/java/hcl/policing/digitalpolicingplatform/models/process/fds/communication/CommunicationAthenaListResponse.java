package hcl.policing.digitalpolicingplatform.models.process.fds.communication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommunicationAthenaListResponse {

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
        @SerializedName("communicationList")
        private List<CommunicationListBean> communicationlist;
        @Expose
        @SerializedName("TotalRecordCount")
        private int totalrecordcount;
        @Expose
        @SerializedName("TotalPageCount")
        private int totalpagecount;
        @Expose
        @SerializedName("CurrentPage")
        private int currentpage;

        public List<CommunicationListBean> getCommunicationlist() {
            return communicationlist;
        }

        public void setCommunicationlist(List<CommunicationListBean> communicationlist) {
            this.communicationlist = communicationlist;
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

    public static class CommunicationListBean {
        @Expose
        @SerializedName("Type")
        private String type;
        @Expose
        @SerializedName("SecureData")
        private boolean securedata;
        @Expose
        @SerializedName("OvertFlag")
        private boolean overtflag;
        @Expose
        @SerializedName("LastName")
        private String lastname;
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
        @SerializedName("FirstName")
        private String firstname;
        @Expose
        @SerializedName("Details")
        private String details;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
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

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }
}


