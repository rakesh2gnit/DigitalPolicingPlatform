package hcl.policing.digitalpolicingplatform.models.process.fds.person.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AthenaLinkedPersonDetailsModel {

    @Expose
    @SerializedName("personDetailResponse")
    private PersondetailresponseBean persondetailresponse;
    @Expose
    @SerializedName("LinkedPersonDetailResponse")
    private LinkedpersondetailresponseBean linkedpersondetailresponse;

    public PersondetailresponseBean getPersondetailresponse() {
        return persondetailresponse;
    }

    public void setPersondetailresponse(PersondetailresponseBean persondetailresponse) {
        this.persondetailresponse = persondetailresponse;
    }

    public LinkedpersondetailresponseBean getLinkedpersondetailresponse() {
        return linkedpersondetailresponse;
    }

    public void setLinkedpersondetailresponse(LinkedpersondetailresponseBean linkedpersondetailresponse) {
        this.linkedpersondetailresponse = linkedpersondetailresponse;
    }

    public static class PersondetailresponseBean {
        @Expose
        @SerializedName("Warnings")
        private List<PersonWarningsBean> warnings;
        @Expose
        @SerializedName("SecureData")
        private boolean securedata;
        @Expose
        @SerializedName("Photos")
        private List<PersonPhotosBean> photos;
        @Expose
        @SerializedName("PNC_ID")
        private String pncId;
        @Expose
        @SerializedName("PNC_FileName")
        private String pncFilename;
        @Expose
        @SerializedName("OvertFlag")
        private boolean overtflag;
        @Expose
        @SerializedName("LatestHomeAddress")
        private String latesthomeaddress;
        @Expose
        @SerializedName("LastName")
        private String lastname;
        @Expose
        @SerializedName("Height")
        private String height;
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
        @SerializedName("FirarmCertificateHolder")
        private String firarmcertificateholder;
        @Expose
        @SerializedName("DOB")
        private String dob;
        @Expose
        @SerializedName("Build")
        private String build;
        @Expose
        @SerializedName("Aliases")
        private List<AliasesBean> aliases;
        @Expose
        @SerializedName("AliasDetails")
        private String aliasdetails;

        public List<PersonWarningsBean> getWarnings() {
            return warnings;
        }

        public void setWarnings(List<PersonWarningsBean> warnings) {
            this.warnings = warnings;
        }

        public boolean getSecuredata() {
            return securedata;
        }

        public void setSecuredata(boolean securedata) {
            this.securedata = securedata;
        }

        public List<PersonPhotosBean> getPhotos() {
            return photos;
        }

        public void setPhotos(List<PersonPhotosBean> photos) {
            this.photos = photos;
        }

        public String getPncId() {
            return pncId;
        }

        public void setPncId(String pncId) {
            this.pncId = pncId;
        }

        public String getPncFilename() {
            return pncFilename;
        }

        public void setPncFilename(String pncFilename) {
            this.pncFilename = pncFilename;
        }

        public boolean getOvertflag() {
            return overtflag;
        }

        public void setOvertflag(boolean overtflag) {
            this.overtflag = overtflag;
        }

        public String getLatesthomeaddress() {
            return latesthomeaddress;
        }

        public void setLatesthomeaddress(String latesthomeaddress) {
            this.latesthomeaddress = latesthomeaddress;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
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

        public String getFirarmcertificateholder() {
            return firarmcertificateholder;
        }

        public void setFirarmcertificateholder(String firarmcertificateholder) {
            this.firarmcertificateholder = firarmcertificateholder;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getBuild() {
            return build;
        }

        public void setBuild(String build) {
            this.build = build;
        }

        public List<AliasesBean> getAliases() {
            return aliases;
        }

        public void setAliases(List<AliasesBean> aliases) {
            this.aliases = aliases;
        }

        public String getAliasdetails() {
            return aliasdetails;
        }

        public void setAliasdetails(String aliasdetails) {
            this.aliasdetails = aliasdetails;
        }
    }

    public static class WarningsBean {
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
    }

    public static class PhotosBean {
        @Expose
        @SerializedName("PhotoTakenDate")
        private String phototakendate;
        @Expose
        @SerializedName("PhotoData")
        private String photodata;

        public String getPhototakendate() {
            return phototakendate;
        }

        public void setPhototakendate(String phototakendate) {
            this.phototakendate = phototakendate;
        }

        public String getPhotodata() {
            return photodata;
        }

        public void setPhotodata(String photodata) {
            this.photodata = photodata;
        }
    }

    public static class AliasesBean {
        @Expose
        @SerializedName("ID")
        private String id;
        @Expose
        @SerializedName("DOB")
        private String dob;
        @Expose
        @SerializedName("AliasType")
        private String aliastype;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getAliastype() {
            return aliastype;
        }

        public void setAliastype(String aliastype) {
            this.aliastype = aliastype;
        }
    }

    public static class LinkedpersondetailresponseBean {
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
        @SerializedName("PNC_ID")
        private String pncId;
        @Expose
        @SerializedName("PNC_FileName")
        private String pncFilename;
        @Expose
        @SerializedName("OvertFlag")
        private boolean overtflag;
        @Expose
        @SerializedName("LatestHomeAddress")
        private String latesthomeaddress;
        @Expose
        @SerializedName("LastName")
        private String lastname;
        @Expose
        @SerializedName("Height")
        private String height;
        @Expose
        @SerializedName("Ethnicity")
        private String ethnicity;
        @Expose
        @SerializedName("Gender")
        private String gender;
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
        @SerializedName("FirarmCertificateHolder")
        private String firarmcertificateholder;
        @Expose
        @SerializedName("DOB")
        private String dob;
        @Expose
        @SerializedName("Build")
        private String build;
        @Expose
        @SerializedName("Aliases")
        private List<AliasesBean> aliases;
        @Expose
        @SerializedName("AliasDetails")
        private String aliasdetails;

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

        public String getPncId() {
            return pncId;
        }

        public void setPncId(String pncId) {
            this.pncId = pncId;
        }

        public String getPncFilename() {
            return pncFilename;
        }

        public void setPncFilename(String pncFilename) {
            this.pncFilename = pncFilename;
        }

        public String getEthnicity() {
            return ethnicity;
        }

        public void setEthnicity(String ethnicity) {
            this.ethnicity = ethnicity;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public boolean getOvertflag() {
            return overtflag;
        }

        public void setOvertflag(boolean overtflag) {
            this.overtflag = overtflag;
        }

        public String getLatesthomeaddress() {
            return latesthomeaddress;
        }

        public void setLatesthomeaddress(String latesthomeaddress) {
            this.latesthomeaddress = latesthomeaddress;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
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

        public String getFirarmcertificateholder() {
            return firarmcertificateholder;
        }

        public void setFirarmcertificateholder(String firarmcertificateholder) {
            this.firarmcertificateholder = firarmcertificateholder;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getBuild() {
            return build;
        }

        public void setBuild(String build) {
            this.build = build;
        }

        public List<AliasesBean> getAliases() {
            return aliases;
        }

        public void setAliases(List<AliasesBean> aliases) {
            this.aliases = aliases;
        }

        public String getAliasdetails() {
            return aliasdetails;
        }

        public void setAliasdetails(String aliasdetails) {
            this.aliasdetails = aliasdetails;
        }
    }

    public static class PersonWarningsBean {
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
    }

    public static class PersonPhotosBean {
        @Expose
        @SerializedName("PhotoTakenDate")
        private String phototakendate;
        @Expose
        @SerializedName("PhotoData")
        private String photodata;

        public String getPhototakendate() {
            return phototakendate;
        }

        public void setPhototakendate(String phototakendate) {
            this.phototakendate = phototakendate;
        }

        public String getPhotodata() {
            return photodata;
        }

        public void setPhotodata(String photodata) {
            this.photodata = photodata;
        }
    }

    public static class PersonAliasesBean {
        @Expose
        @SerializedName("ID")
        private String id;
        @Expose
        @SerializedName("DOB")
        private String dob;
        @Expose
        @SerializedName("AliasType")
        private String aliastype;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getAliastype() {
            return aliastype;
        }

        public void setAliastype(String aliastype) {
            this.aliastype = aliastype;
        }
    }
}
