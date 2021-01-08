package hcl.policing.digitalpolicingplatform.models.process.fds.address.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AddressAthenaResponse implements Serializable {

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
        @SerializedName("addressList")
        private List<AddressListBean> addresslist;
        @Expose
        @SerializedName("TotalRecordCount")
        private int totalrecordcount;
        @Expose
        @SerializedName("TotalPageCount")
        private int totalpagecount;
        @Expose
        @SerializedName("CurrentPage")
        private int currentpage;

        public List<AddressListBean> getAddresslist() {
            return addresslist;
        }

        public void setAddresslist(List<AddressListBean> addresslist) {
            this.addresslist = addresslist;
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

    public static class AddressListBean {
        @Expose
        @SerializedName("Warnings")
        private List<WarningsBean> warnings;
        @Expose
        @SerializedName("Town")
        private String town;
        @Expose
        @SerializedName("Street")
        private String street;
        @Expose
        @SerializedName("SecureData")
        private boolean securedata;
        @Expose
        @SerializedName("PremisesNumber")
        private String premisesnumber;
        @Expose
        @SerializedName("Postcode")
        private String postcode;
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
        @SerializedName("County")
        private String county;

        public List<WarningsBean> getWarnings() {
            return warnings;
        }

        public void setWarnings(List<WarningsBean> warnings) {
            this.warnings = warnings;
        }

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public boolean getSecuredata() {
            return securedata;
        }

        public void setSecuredata(boolean securedata) {
            this.securedata = securedata;
        }

        public String getPremisesnumber() {
            return premisesnumber;
        }

        public void setPremisesnumber(String premisesnumber) {
            this.premisesnumber = premisesnumber;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
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

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
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
        @SerializedName("ToDate")
        private String todate;
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

        public String getTodate() {
            return todate;
        }

        public void setTodate(String todate) {
            this.todate = todate;
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
