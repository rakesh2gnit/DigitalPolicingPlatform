package hcl.policing.digitalpolicingplatform.models.process.fds.pole;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AddressList implements Serializable {

    @SerializedName("AddressDescription")
    @Expose
    private String addressDescription;
    @SerializedName("Building")
    @Expose
    private String building;
    @SerializedName("BuildingNo")
    @Expose
    private String buildingNo;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("County")
    @Expose
    private String county;
    @SerializedName("CreatedBy")
    @Expose
    private Object createdBy;
    @SerializedName("CreatedDate")
    @Expose
    private Object createdDate;
    @SerializedName("District")
    @Expose
    private String district;
    @SerializedName("ExtraProp1")
    @Expose
    private Object extraProp1;
    @SerializedName("ExtraProp2")
    @Expose
    private Object extraProp2;
    @SerializedName("Flat")
    @Expose
    private String flat;
    @SerializedName("FlatNo")
    @Expose
    private String flatNo;
    @SerializedName("History")
    @Expose
    private Object history;
    @SerializedName("HistoryList")
    @Expose
    private List<HistoryBean> historyList;
    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("InformationMarkers")
    @Expose
    private Object informationMarkers;
    @SerializedName("LDS")
    @Expose
    private Object lDS;
    @SerializedName("PersonList")
    @Expose
    private List<PersonList> personList = null;
    @SerializedName("PostCode")
    @Expose
    private String postCode;
    @SerializedName("RiskMarkers")
    @Expose
    private Object riskMarkers;
    @SerializedName("Street")
    @Expose
    private String street;
    @SerializedName("URN")
    @Expose
    private String uRN;
    @SerializedName("UpdatedBy")
    @Expose
    private Object updatedBy;
    @SerializedName("UpdatedDate")
    @Expose
    private Object updatedDate;
    @Expose
    @SerializedName("ActionMarkers")
    private List<ActionMarkersBean> actionmarkers;
    @Expose
    @SerializedName("WarningSignals")
    private List<WarningSignalsBean> warningsignals;
    @SerializedName("XCoordinate")
    @Expose
    private Object xCoordinate;
    @SerializedName("YCoordinate")
    @Expose
    private Object yCoordinate;


    public String getAddressDescription() {
        return addressDescription;
    }

    public void setAddressDescription(String addressDescription) {
        this.addressDescription = addressDescription;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Object getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Object createdDate) {
        this.createdDate = createdDate;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Object getExtraProp1() {
        return extraProp1;
    }

    public void setExtraProp1(Object extraProp1) {
        this.extraProp1 = extraProp1;
    }

    public Object getExtraProp2() {
        return extraProp2;
    }

    public void setExtraProp2(Object extraProp2) {
        this.extraProp2 = extraProp2;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public Object getHistory() {
        return history;
    }

    public void setHistory(Object history) {
        this.history = history;
    }

    public List<HistoryBean> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<HistoryBean> historyList) {
        this.historyList = historyList;
    }

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public Object getInformationMarkers() {
        return informationMarkers;
    }

    public void setInformationMarkers(Object informationMarkers) {
        this.informationMarkers = informationMarkers;
    }

    public Object getLDS() {
        return lDS;
    }

    public void setLDS(Object lDS) {
        this.lDS = lDS;
    }

    public List<PersonList> getPersonList() {
        return personList;
    }

    public void setPersonList(List<PersonList> personList) {
        this.personList = personList;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Object getRiskMarkers() {
        return riskMarkers;
    }

    public void setRiskMarkers(Object riskMarkers) {
        this.riskMarkers = riskMarkers;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getURN() {
        return uRN;
    }

    public void setURN(String uRN) {
        this.uRN = uRN;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Object getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Object updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<ActionMarkersBean> getActionmarkers() {
        return actionmarkers;
    }

    public void setActionmarkers(List<ActionMarkersBean> actionmarkers) {
        this.actionmarkers = actionmarkers;
    }

    public List<WarningSignalsBean> getWarningsignals() {
        return warningsignals;
    }

    public void setWarningsignals(List<WarningSignalsBean> warningsignals) {
        this.warningsignals = warningsignals;
    }

    public Object getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(Object xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public Object getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(Object yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

}