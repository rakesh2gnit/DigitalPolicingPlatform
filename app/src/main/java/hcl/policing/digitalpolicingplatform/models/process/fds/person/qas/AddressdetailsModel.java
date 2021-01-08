package hcl.policing.digitalpolicingplatform.models.process.fds.person.qas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressdetailsModel {

    @Expose
    @SerializedName("Town")
    private String town;
    @Expose
    @SerializedName("Street")
    private String street;
    @Expose
    @SerializedName("Postcode")
    private String postcode;
    @Expose
    @SerializedName("Location")
    private String location;
    @Expose
    @SerializedName("HouseNo")
    private String houseno;
    @Expose
    @SerializedName("County")
    private String county;
    @Expose
    @SerializedName("Country")
    private String country;
    @Expose
    @SerializedName("City")
    private String city;
    @Expose
    @SerializedName("Building")
    private String building;
    @Expose
    @SerializedName("AddressID")
    private String addressid;

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

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHouseno() {
        return houseno;
    }

    public void setHouseno(String houseno) {
        this.houseno = houseno;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }
}
