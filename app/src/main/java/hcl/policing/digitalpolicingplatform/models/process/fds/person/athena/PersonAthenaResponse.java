package hcl.policing.digitalpolicingplatform.models.process.fds.person.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonAthenaResponse {


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
        @SerializedName("personList")
        private List<AthenaPersonlistBean> personlist;
        @Expose
        @SerializedName("TotalRecordCount")
        private int totalrecordcount;
        @Expose
        @SerializedName("TotalPageCount")
        private int totalpagecount;
        @Expose
        @SerializedName("CurrentPage")
        private int currentpage;

        public List<AthenaPersonlistBean> getPersonlist() {
            return personlist;
        }

        public void setPersonlist(List<AthenaPersonlistBean> personlist) {
            this.personlist = personlist;
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

    public static class AthenaPersonlistBean {
        @Expose
        @SerializedName("Warnings")
        private List<WarningsBean> warnings;
        @Expose
        @SerializedName("SecureData")
        private boolean securedata;
        @Expose
        @SerializedName("Photos")
        private List<PhotosBean> photos;
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
        @SerializedName("FirstName1")
        private String firstname1;
        @Expose
        @SerializedName("DOB")
        private String dob;
        @Expose
        @SerializedName("LatestHomeAddress")
        private String latesthomeaddress;


        public void setGender(String gender) {
            this.gender = gender;
        }

        public List<WarningsBean> getWarnings() {
            return warnings;
        }

        public void setWarnings(List<WarningsBean> warnings) {
            this.warnings = warnings;
        }

        public boolean getSecuredata() {
            return securedata;
        }

        public void setSecuredata(boolean securedata) {
            this.securedata = securedata;
        }

        public List<PhotosBean> getPhotos() {
            return photos;
        }

        public void setPhotos(List<PhotosBean> photos) {
            this.photos = photos;
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

        public String getFirstname1() {
            return firstname1;
        }

        public void setFirstname1(String firstname1) {
            this.firstname1 = firstname1;
        }

        @Expose
        @SerializedName("Gender")
        private String gender;

        public String getGender() {
            return gender;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getLatesthomeaddress() {
            return latesthomeaddress;
        }

        public void setLatesthomeaddress(String latesthomeaddress) {
            this.latesthomeaddress = latesthomeaddress;
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
        @SerializedName("markerOrigin")
        private String markerorigin;
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
        @SerializedName("ToDate")
        private String todate;
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

        public String getMarkerorigin() {
            return markerorigin;
        }

        public void setMarkerorigin(String markerorigin) {
            this.markerorigin = markerorigin;
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

        public String getTodate() {
            return todate;
        }

        public void setTodate(String todate) {
            this.todate = todate;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class PhotosBean {
        @Expose
        @SerializedName("PhotoData")
        private String photodata;
        @Expose
        @SerializedName("ID")
        private String id;

        public String getPhotodata() {
            return photodata;
        }

        public void setPhotodata(String photodata) {
            this.photodata = photodata;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
