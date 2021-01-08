package hcl.policing.digitalpolicingplatform.models.process.fds.pole;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VehicleList implements Serializable {

    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("Color1")
    @Expose
    private String color1;
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("DateAccessed")
    @Expose
    private Object dateAccessed;
    @SerializedName("DateAdded")
    @Expose
    private Object dateAdded;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("EngineCapacity")
    @Expose
    private String engineCapacity;
    @SerializedName("EngineFuelType")
    @Expose
    private String engineFuelType;
    @SerializedName("EngineNumber")
    @Expose
    private String engineNumber;
    @SerializedName("EngineTwoNumber")
    @Expose
    private String engineTwoNumber;
    @SerializedName("ExtraProp1")
    @Expose
    private String extraProp1;
    @SerializedName("ExtraProp2")
    @Expose
    private String extraProp2;
    @SerializedName("FirstRegistrationDate")
    @Expose
    private String firstRegistrationDate;
    @SerializedName("HistoryList")
    @Expose
    private List<HistoryBean> historyList = null;
    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("InformationMarkers")
    @Expose
    private List<Object> informationMarkers = null;
    @SerializedName("LDS")
    @Expose
    private String lDS;
    @SerializedName("MainColour")
    @Expose
    private String mainColour;
    @SerializedName("Make")
    @Expose
    private String make;
    @SerializedName("Marker")
    @Expose
    private String marker;
    @SerializedName("Markers")
    @Expose
    private Markers markers;
    @SerializedName("Model")
    @Expose
    private String model;
    @SerializedName("NumberOfDoors")
    @Expose
    private String numberOfDoors;
    @SerializedName("NumberOfWheels")
    @Expose
    private String numberOfWheels;
    @SerializedName("Owner")
    @Expose
    private String owner;
    @SerializedName("Relevance")
    @Expose
    private String relevance;
    @SerializedName("RiskMarkers")
    @Expose
    private List<Object> riskMarkers = null;
    @SerializedName("Role")
    @Expose
    private String role;
    @SerializedName("SpecialInterest")
    @Expose
    private String specialInterest;
    @SerializedName("SpecialMarking")
    @Expose
    private String specialMarking;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("URN")
    @Expose
    private String uRN;
    @SerializedName("UpdatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("UpdatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("UsageType")
    @Expose
    private String usageType;
    @SerializedName("VIN")
    @Expose
    private String vin;
    @SerializedName("VRM")
    @Expose
    private String vRM;
    @SerializedName("Value")
    @Expose
    private String value;
    @Expose
    @SerializedName("ActionMarkers")
    private List<ActionMarkersBean> actionmarkers;
    @Expose
    @SerializedName("WarningSignals")
    private List<WarningSignalsBean> warningsignals;
    @SerializedName("WheelDrive")
    @Expose
    private String wheelDrive;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Object getDateAccessed() {
        return dateAccessed;
    }

    public void setDateAccessed(Object dateAccessed) {
        this.dateAccessed = dateAccessed;
    }

    public Object getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Object dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(String engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public String getEngineFuelType() {
        return engineFuelType;
    }

    public void setEngineFuelType(String engineFuelType) {
        this.engineFuelType = engineFuelType;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getEngineTwoNumber() {
        return engineTwoNumber;
    }

    public void setEngineTwoNumber(String engineTwoNumber) {
        this.engineTwoNumber = engineTwoNumber;
    }

    public String getExtraProp1() {
        return extraProp1;
    }

    public void setExtraProp1(String extraProp1) {
        this.extraProp1 = extraProp1;
    }

    public String getExtraProp2() {
        return extraProp2;
    }

    public void setExtraProp2(String extraProp2) {
        this.extraProp2 = extraProp2;
    }

    public String getFirstRegistrationDate() {
        return firstRegistrationDate;
    }

    public void setFirstRegistrationDate(String firstRegistrationDate) {
        this.firstRegistrationDate = firstRegistrationDate;
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

    public List<Object> getInformationMarkers() {
        return informationMarkers;
    }

    public void setInformationMarkers(List<Object> informationMarkers) {
        this.informationMarkers = informationMarkers;
    }

    public String getLDS() {
        return lDS;
    }

    public void setLDS(String lDS) {
        this.lDS = lDS;
    }

    public String getMainColour() {
        return mainColour;
    }

    public void setMainColour(String mainColour) {
        this.mainColour = mainColour;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public Markers getMarkers() {
        return markers;
    }

    public void setMarkers(Markers markers) {
        this.markers = markers;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(String numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    public String getNumberOfWheels() {
        return numberOfWheels;
    }

    public void setNumberOfWheels(String numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRelevance() {
        return relevance;
    }

    public void setRelevance(String relevance) {
        this.relevance = relevance;
    }

    public List<Object> getRiskMarkers() {
        return riskMarkers;
    }

    public void setRiskMarkers(List<Object> riskMarkers) {
        this.riskMarkers = riskMarkers;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSpecialInterest() {
        return specialInterest;
    }

    public void setSpecialInterest(String specialInterest) {
        this.specialInterest = specialInterest;
    }

    public String getSpecialMarking() {
        return specialMarking;
    }

    public void setSpecialMarking(String specialMarking) {
        this.specialMarking = specialMarking;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getURN() {
        return uRN;
    }

    public void setURN(String uRN) {
        this.uRN = uRN;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getVRM() {
        return vRM;
    }

    public void setVRM(String vRM) {
        this.vRM = vRM;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public String getWheelDrive() {
        return wheelDrive;
    }

    public void setWheelDrive(String wheelDrive) {
        this.wheelDrive = wheelDrive;
    }

    public class Markers {

        @SerializedName("ActionMarkers")
        @Expose
        private Object actionMarkers;
        @SerializedName("InformationMarkers")
        @Expose
        private Object informationMarkers;
        @SerializedName("RiskMarkers")
        @Expose
        private Object riskMarkers;
        @SerializedName("WarningSignals")
        @Expose
        private Object warningSignals;

        public Object getActionMarkers() {
            return actionMarkers;
        }

        public void setActionMarkers(Object actionMarkers) {
            this.actionMarkers = actionMarkers;
        }

        public Object getInformationMarkers() {
            return informationMarkers;
        }

        public void setInformationMarkers(Object informationMarkers) {
            this.informationMarkers = informationMarkers;
        }

        public Object getRiskMarkers() {
            return riskMarkers;
        }

        public void setRiskMarkers(Object riskMarkers) {
            this.riskMarkers = riskMarkers;
        }

        public Object getWarningSignals() {
            return warningSignals;
        }

        public void setWarningSignals(Object warningSignals) {
            this.warningSignals = warningSignals;
        }

    }

}