package hcl.policing.digitalpolicingplatform.models.process.fds.address.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.WarningsModel;

public class AddressModel implements Serializable {
    @Expose
    @SerializedName("shortSummary")
    private String shortsummary;
    @Expose
    @SerializedName("Warnings")
    private List<WarningsModel> warnings;
    @Expose
    @SerializedName("Ward")
    private String ward;
    @Expose
    @SerializedName("Town")
    private String town;
    @Expose
    @SerializedName("SubPremisesName")
    private String subpremisesname;

    @Expose
    @SerializedName("FlatNo")
    private String flatno;

    @Expose
    @SerializedName("Street")
    private String street;

    @Expose
    @SerializedName("SubNo")
    private String subno;

    @Expose
    @SerializedName("SecureData")
    private boolean securedata;
    @Expose
    @SerializedName("PremisesType")
    private String premisestype;
    @Expose
    @SerializedName("PremisesNumber")
    private String premisesnumber;
    @Expose
    @SerializedName("PremisesName")
    private String premisesname;
    @Expose
    @SerializedName("Postcode")
    private String postcode;
    @Expose
    @SerializedName("OvertFlag")
    private boolean overtflag;
    @Expose
    @SerializedName("Northing")
    private String northing;
    @Expose
    @SerializedName("ID")
    private String id;
    @Expose
    @SerializedName("Force")
    private String force;

    @Expose
    @SerializedName("Locality")
    private String locality;
    @Expose
    @SerializedName("POBox")
    private String pobox;
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
    @SerializedName("County")
    private String county;
    @Expose
    @SerializedName("Country")
    private String country;
    @Expose
    @SerializedName("Building")
    private String building;
    @Expose
    @SerializedName("BCU")
    private String bcu;

    public String getShortsummary() {
        return shortsummary;
    }

    public void setShortsummary(String shortsummary) {
        this.shortsummary = shortsummary;
    }

    public List<WarningsModel> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<WarningsModel> warnings) {
        this.warnings = warnings;
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

    public String getSubpremisesname() {
        return subpremisesname;
    }

    public void setSubpremisesname(String subpremisesname) {
        this.subpremisesname = subpremisesname;
    }

    public String getSubno() {
        return subno;
    }

    public void setSubno(String subno) {
        this.subno = subno;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getFlatno() {
        return flatno;
    }

    public void setFlatno(String flatno) {
        this.flatno = flatno;
    }

    public String getPobox() {
        return pobox;
    }

    public void setPobox(String pobox) {
        this.pobox = pobox;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
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

    public String getPremisesnumber() {
        return premisesnumber;
    }

    public void setPremisesnumber(String premisesnumber) {
        this.premisesnumber = premisesnumber;
    }

    public String getPremisesname() {
        return premisesname;
    }

    public void setPremisesname(String premisesname) {
        this.premisesname = premisesname;
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

    public String getNorthing() {
        return northing;
    }

    public void setNorthing(String northing) {
        this.northing = northing;
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

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getBcu() {
        return bcu;
    }

    public void setBcu(String bcu) {
        this.bcu = bcu;
    }
}
