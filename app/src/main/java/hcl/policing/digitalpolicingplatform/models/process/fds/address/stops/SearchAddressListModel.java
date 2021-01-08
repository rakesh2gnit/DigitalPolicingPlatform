package hcl.policing.digitalpolicingplatform.models.process.fds.address.stops;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchAddressListModel {
    @Expose
    @SerializedName("Street")
    private String street;
    @Expose
    @SerializedName("PostCode")
    private String postcode;
    @Expose
    @SerializedName("NearTo")
    private String nearto;
    @Expose
    @SerializedName("HouseNumber")
    private String housenumber;
    @Expose
    @SerializedName("FlatNumber")
    private String flatnumber;
    @Expose
    @SerializedName("Country")
    private String country;
    @Expose
    @SerializedName("City")
    private String city;
    @Expose
    @SerializedName("AddressID")
    private String addressid;
    @Expose
    @SerializedName("TotalRecordCount")
    private int totalrecordcount;
    @Expose
    @SerializedName("TotalPageCount")
    private int totalpagecount;
    @Expose
    @SerializedName("CurrentPage")
    private int currentpage;

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

    public String getNearto() {
        return nearto;
    }

    public void setNearto(String nearto) {
        this.nearto = nearto;
    }

    public String getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
    }

    public String getFlatnumber() {
        return flatnumber;
    }

    public void setFlatnumber(String flatnumber) {
        this.flatnumber = flatnumber;
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

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
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
