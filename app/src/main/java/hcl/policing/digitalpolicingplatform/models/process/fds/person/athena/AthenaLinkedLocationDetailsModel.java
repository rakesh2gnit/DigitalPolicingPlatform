package hcl.policing.digitalpolicingplatform.models.process.fds.person.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AthenaLinkedLocationDetailsModel {

    @Expose
    @SerializedName("LocationDetailResponse")
    private LocationdetailresponseBean locationdetailresponse;

    public LocationdetailresponseBean getLocationdetailresponse() {
        return locationdetailresponse;
    }

    public void setLocationdetailresponse(LocationdetailresponseBean locationdetailresponse) {
        this.locationdetailresponse = locationdetailresponse;
    }

    public static class LocationdetailresponseBean {
        @Expose
        @SerializedName("shortSummary")
        private String shortsummary;
        @Expose
        @SerializedName("relevantDateTime")
        private String relevantdatetime;
        @Expose
        @SerializedName("Ward")
        private String ward;
        @Expose
        @SerializedName("Town")
        private String town;
        @Expose
        @SerializedName("SubNo")
        private String subno;
        @Expose
        @SerializedName("StreetName")
        private String streetname;
        @Expose
        @SerializedName("SecureData")
        private boolean securedata;
        @Expose
        @SerializedName("PremisesType")
        private String premisestype;
        @Expose
        @SerializedName("PremisesNo")
        private String premisesno;
        @Expose
        @SerializedName("PremisesName")
        private String premisesname;
        @Expose
        @SerializedName("SubPremesisName")
        private String subpremisesname;
        @Expose
        @SerializedName("PostCode")
        private String postcode;
        @Expose
        @SerializedName("POBox")
        private String pobox;
        @Expose
        @SerializedName("OvertFlag")
        private boolean overtflag;
        @Expose
        @SerializedName("Northing")
        private String northing;
        @Expose
        @SerializedName("Markers")
        private List<MarkerModel> markers;
        @Expose
        @SerializedName("Locality")
        private String locality;
        @Expose
        @SerializedName("ID")
        private String id;
        @Expose
        @SerializedName("Force")
        private String force;
        @Expose
        @SerializedName("FlatNo")
        private String flatno;
        @Expose
        @SerializedName("FlagMessage")
        private String flagmessage;
        @Expose
        @SerializedName("FlagDescription")
        private String flagdescription;
        @Expose
        @SerializedName("Easting")
        private String easting;
        @Expose
        @SerializedName("District")
        private String district;
        @Expose
        @SerializedName("DetailText")
        private String detailtext;
        @Expose
        @SerializedName("CreatedDate")
        private String createddate;
        @Expose
        @SerializedName("County")
        private String county;
        @Expose
        @SerializedName("Country")
        private String country;
        @Expose
        @SerializedName("BCU")
        private String bcu;

        public String getShortsummary() {
            return shortsummary;
        }

        public void setShortsummary(String shortsummary) {
            this.shortsummary = shortsummary;
        }

        public String getRelevantdatetime() {
            return relevantdatetime;
        }

        public void setRelevantdatetime(String relevantdatetime) {
            this.relevantdatetime = relevantdatetime;
        }

        public String getWard() {
            return ward;
        }

        public void setWard(String ward) {
            this.ward = ward;
        }

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public String getSubno() {
            return subno;
        }

        public void setSubno(String subno) {
            this.subno = subno;
        }

        public String getStreetname() {
            return streetname;
        }

        public void setStreetname(String streetname) {
            this.streetname = streetname;
        }

        public boolean getSecuredata() {
            return securedata;
        }

        public void setSecuredata(boolean securedata) {
            this.securedata = securedata;
        }

        public String getPremisestype() {
            return premisestype;
        }

        public void setPremisestype(String premisestype) {
            this.premisestype = premisestype;
        }

        public String getPremisesno() {
            return premisesno;
        }

        public void setPremisesno(String premisesno) {
            this.premisesno = premisesno;
        }

        public String getPremisesname() {
            return premisesname;
        }

        public void setPremisesname(String premisesname) {
            this.premisesname = premisesname;
        }

        public String getSubpremisesname() {
            return subpremisesname;
        }

        public void setSubpremisesname(String subpremisesname) {
            this.subpremisesname = subpremisesname;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getPobox() {
            return pobox;
        }

        public void setPobox(String pobox) {
            this.pobox = pobox;
        }

        public boolean getOvertflag() {
            return overtflag;
        }

        public void setOvertflag(boolean overtflag) {
            this.overtflag = overtflag;
        }

        public String getNorthing() {
            return northing;
        }

        public void setNorthing(String northing) {
            this.northing = northing;
        }

        public List<MarkerModel> getMarkers() {
            return markers;
        }

        public void setMarkers(List<MarkerModel> markers) {
            this.markers = markers;
        }

        public String getLocality() {
            return locality;
        }

        public void setLocality(String locality) {
            this.locality = locality;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getForce() {
            return force;
        }

        public void setForce(String force) {
            this.force = force;
        }

        public String getFlatno() {
            return flatno;
        }

        public void setFlatno(String flatno) {
            this.flatno = flatno;
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

        public String getEasting() {
            return easting;
        }

        public void setEasting(String easting) {
            this.easting = easting;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getDetailtext() {
            return detailtext;
        }

        public void setDetailtext(String detailtext) {
            this.detailtext = detailtext;
        }

        public String getCreateddate() {
            return createddate;
        }

        public void setCreateddate(String createddate) {
            this.createddate = createddate;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getBcu() {
            return bcu;
        }

        public void setBcu(String bcu) {
            this.bcu = bcu;
        }
    }

    public static class MarkersBean {
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
