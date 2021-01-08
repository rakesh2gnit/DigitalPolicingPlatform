package hcl.policing.digitalpolicingplatform.models.process.fds.person.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AthenaLinkedSearchDetailsModel {

    @Expose
    @SerializedName("searchesDetailResponse")
    private SearchesdetailresponseBean searchesdetailresponse;

    public SearchesdetailresponseBean getSearchesdetailresponse() {
        return searchesdetailresponse;
    }

    public void setSearchesdetailresponse(SearchesdetailresponseBean searchesdetailresponse) {
        this.searchesdetailresponse = searchesdetailresponse;
    }

    public static class SearchesdetailresponseBean {
        @Expose
        @SerializedName("TypeOfSearch")
        private String typeofsearch;
        @Expose
        @SerializedName("StatutoryPowerUsed")
        private String statutorypowerused;
        @Expose
        @SerializedName("Status")
        private String status;
        @Expose
        @SerializedName("SecureData")
        private boolean securedata;
        @Expose
        @SerializedName("SearchToTime")
        private String searchtotime;
        @Expose
        @SerializedName("SearchTo")
        private String searchto;
        @Expose
        @SerializedName("SearchTitle")
        private String searchtitle;
        @Expose
        @SerializedName("SearchStatusCustody")
        private String searchstatuscustody;
        @Expose
        @SerializedName("SearchFromTime")
        private String searchfromtime;
        @Expose
        @SerializedName("SearchFrom")
        private String searchfrom;
        @Expose
        @SerializedName("Result")
        private String result;
        @Expose
        @SerializedName("OvertFlag")
        private boolean overtflag;
        @Expose
        @SerializedName("OfficersPresentCount")
        private int officerspresentcount;
        @Expose
        @SerializedName("OfficersPresent")
        private List<OfficersPresentBean> officerspresent;
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
        @SerializedName("DateSubmitted")
        private String datesubmitted;
        @Expose
        @SerializedName("AuthorisationTime")
        private String authorisationtime;
        @Expose
        @SerializedName("AuthorisationDate")
        private String authorisationdate;
        @Expose
        @SerializedName("TotalRecordCount")
        private int totalrecordcount;
        @Expose
        @SerializedName("TotalPageCount")
        private int totalpagecount;
        @Expose
        @SerializedName("CurrentPage")
        private int currentpage;

        public String getTypeofsearch() {
            return typeofsearch;
        }

        public void setTypeofsearch(String typeofsearch) {
            this.typeofsearch = typeofsearch;
        }

        public String getStatutorypowerused() {
            return statutorypowerused;
        }

        public void setStatutorypowerused(String statutorypowerused) {
            this.statutorypowerused = statutorypowerused;
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

        public String getSearchtotime() {
            return searchtotime;
        }

        public void setSearchtotime(String searchtotime) {
            this.searchtotime = searchtotime;
        }

        public String getSearchto() {
            return searchto;
        }

        public void setSearchto(String searchto) {
            this.searchto = searchto;
        }

        public String getSearchtitle() {
            return searchtitle;
        }

        public void setSearchtitle(String searchtitle) {
            this.searchtitle = searchtitle;
        }

        public String getSearchstatuscustody() {
            return searchstatuscustody;
        }

        public void setSearchstatuscustody(String searchstatuscustody) {
            this.searchstatuscustody = searchstatuscustody;
        }

        public String getSearchfromtime() {
            return searchfromtime;
        }

        public void setSearchfromtime(String searchfromtime) {
            this.searchfromtime = searchfromtime;
        }

        public String getSearchfrom() {
            return searchfrom;
        }

        public void setSearchfrom(String searchfrom) {
            this.searchfrom = searchfrom;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public boolean getOvertflag() {
            return overtflag;
        }

        public void setOvertflag(boolean overtflag) {
            this.overtflag = overtflag;
        }

        public int getOfficerspresentcount() {
            return officerspresentcount;
        }

        public void setOfficerspresentcount(int officerspresentcount) {
            this.officerspresentcount = officerspresentcount;
        }

        public List<OfficersPresentBean> getOfficerspresent() {
            return officerspresent;
        }

        public void setOfficerspresent(List<OfficersPresentBean> officerspresent) {
            this.officerspresent = officerspresent;
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

        public String getDatesubmitted() {
            return datesubmitted;
        }

        public void setDatesubmitted(String datesubmitted) {
            this.datesubmitted = datesubmitted;
        }

        public String getAuthorisationtime() {
            return authorisationtime;
        }

        public void setAuthorisationtime(String authorisationtime) {
            this.authorisationtime = authorisationtime;
        }

        public String getAuthorisationdate() {
            return authorisationdate;
        }

        public void setAuthorisationdate(String authorisationdate) {
            this.authorisationdate = authorisationdate;
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

    public static class OfficersPresentBean {
        @Expose
        @SerializedName("Type")
        private String type;
        @Expose
        @SerializedName("DisplayName")
        private String displayname;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDisplayname() {
            return displayname;
        }

        public void setDisplayname(String displayname) {
            this.displayname = displayname;
        }
    }
}
