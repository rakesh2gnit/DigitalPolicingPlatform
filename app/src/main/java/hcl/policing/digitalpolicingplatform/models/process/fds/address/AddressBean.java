package hcl.policing.digitalpolicingplatform.models.process.fds.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddressBean implements Serializable {

    @Expose
    @SerializedName("houseNo")
    private String houseNo;
    @Expose
    @SerializedName("buildingNo")
    private String buildingNo;
    @Expose
    @SerializedName("flatNo")
    private String flatNo;
    @Expose
    @SerializedName("buildingName")
    private String buildingName;
    @Expose
    @SerializedName("flatName")
    private String flatName;
    @Expose
    @SerializedName("street")
    private String street;
    @Expose
    @SerializedName("city")
    private String city;
    @Expose
    @SerializedName("district")
    private String district;
    @Expose
    @SerializedName("country")
    private String country;
    @Expose
    @SerializedName("county")
    private String county;
    @Expose
    @SerializedName("postcode")
    private String postcode;
    @Expose
    @SerializedName("address")
    private String address;
    @Expose
    @SerializedName("Id")
    private String Id;
    @Expose
    @SerializedName("System")
    private String System;

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getBuildingno() {
        return buildingNo;
    }

    public void setBuildingno(String buildingno) {
        this.buildingNo = buildingno;
    }

    public String getFlatno() {
        return flatNo;
    }

    public void setFlatno(String flatno) {
        this.flatNo = flatno;
    }

    public String getBuildingname() {
        return buildingName;
    }

    public void setBuildingname(String buildingname) {
        this.buildingName = buildingname;
    }

    public String getFlatname() {
        return flatName;
    }

    public void setFlatname(String flatname) {
        this.flatName = flatname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSystem() {
        return System;
    }

    public void setSystem(String system) {
        System = system;
    }
}
