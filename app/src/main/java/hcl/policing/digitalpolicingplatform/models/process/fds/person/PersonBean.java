package hcl.policing.digitalpolicingplatform.models.process.fds.person;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PersonBean implements Serializable {

    @Expose
    @SerializedName("warnings")
    private List<WarningsBean> warnings;
    @Expose
    @SerializedName("address")
    private String address;
    @Expose
    @SerializedName("gender")
    private String gender;
    @Expose
    @SerializedName("dob")
    private String dob;
    @Expose
    @SerializedName("age")
    private String age;
    @Expose
    @SerializedName("lastName")
    private String lastName;
    @Expose
    @SerializedName("firstName")
    private String firstName;
    @Expose
    @SerializedName("nationality")
    private String nationality;
    @Expose
    @SerializedName("officerDefinedEthnicity")
    private String officerDefinedEthnicity;
    @Expose
    @SerializedName("selfDefinedEthnicity")
    private String selfDefinedEthnicity;
    @Expose
    @SerializedName("occupation")
    private String occupation;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("adultDetail")
    private String adultDetail;
    @Expose
    @SerializedName("Id")
    private String Id;
    @Expose
    @SerializedName("System")
    private String System;
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
    @SerializedName("Address_Id")
    private String Address_Id;

    public List<WarningsBean> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<WarningsBean> warnings) {
        this.warnings = warnings;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLastname() {
        return lastName;
    }

    public void setLastname(String lastname) {
        this.lastName = lastname;
    }

    public String getFirstname() {
        return firstName;
    }

    public void setFirstname(String firstname) {
        this.firstName = firstname;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getOfficerDefinedEthnicity() {
        return officerDefinedEthnicity;
    }

    public void setOfficerDefinedEthnicity(String officerDefinedEthnicity) {
        this.officerDefinedEthnicity = officerDefinedEthnicity;
    }

    public String getSelfDefinedEthnicity() {
        return selfDefinedEthnicity;
    }

    public void setSelfDefinedEthnicity(String selfDefinedEthnicity) {
        this.selfDefinedEthnicity = selfDefinedEthnicity;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdultDetail() {
        return adultDetail;
    }

    public void setAdultDetail(String adultDetail) {
        this.adultDetail = adultDetail;
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

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingno) {
        this.buildingNo = buildingno;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatno) {
        this.flatNo = flatno;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingname) {
        this.buildingName = buildingname;
    }

    public String getFlatName() {
        return flatName;
    }

    public void setFlatName(String flatname) {
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

    public String getAddress_Id() {
        return Address_Id;
    }

    public void setAddress_Id(String address_Id) {
        Address_Id = address_Id;
    }
}
