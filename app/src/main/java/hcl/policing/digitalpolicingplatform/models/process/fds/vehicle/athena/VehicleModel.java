package hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.PhotosBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.WarningsModel;

public class VehicleModel implements Serializable {
    @Expose
    @SerializedName("type")
    private String type;
    @Expose
    @SerializedName("make")
    private String make;
    @Expose
    @SerializedName("Warnings")
    private List<WarningsModel> warnings;
    @Expose
    @SerializedName("VehicleFeatures")
    private List<String> vehiclefeatures;
    @Expose
    @SerializedName("VehicleColour")
    private String vehiclecolour;
    @Expose
    @SerializedName("VehicleAnpr")
    private String vehicleanpr;
    @Expose
    @SerializedName("SecureData")
    private boolean securedata;
    @Expose
    @SerializedName("Remarks")
    private String remarks;
    @Expose
    @SerializedName("RegistrationNumber")
    private String registrationnumber;
    @Expose
    @SerializedName("RegistrationCountry")
    private String registrationcountry;
    @Expose
    @SerializedName("Photos")
    private List<PhotosBean> photos;
    @Expose
    @SerializedName("OvertFlag")
    private boolean overtflag;
    @Expose
    @SerializedName("Model")
    private String model;
    @Expose
    @SerializedName("IdentifyingCharacteristics")
    private String identifyingcharacteristics;
    @Expose
    @SerializedName("ID")
    private String id;
    @Expose
    @SerializedName("FuelType")
    private String fueltype;
    @Expose
    @SerializedName("ForeignVehicle")
    private String foreignvehicle;
    @Expose
    @SerializedName("FlagMessage")
    private String flagmessage;
    @Expose
    @SerializedName("FlagDescription")
    private String flagdescription;
    @Expose
    @SerializedName("EngineNumber")
    private String enginenumber;
    @Expose
    @SerializedName("ChassisNumber")
    private String chassisnumber;
    @Expose
    @SerializedName("Category")
    private String category;
    @Expose
    @SerializedName("ANPRReason")
    private String anprreason;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public List<WarningsModel> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<WarningsModel> warnings) {
        this.warnings = warnings;
    }

    public List<String> getVehiclefeatures() {
        return vehiclefeatures;
    }

    public void setVehiclefeatures(List<String> vehiclefeatures) {
        this.vehiclefeatures = vehiclefeatures;
    }

    public String getVehiclecolour() {
        return vehiclecolour;
    }

    public void setVehiclecolour(String vehiclecolour) {
        this.vehiclecolour = vehiclecolour;
    }

    public String getVehicleanpr() {
        return vehicleanpr;
    }

    public void setVehicleanpr(String vehicleanpr) {
        this.vehicleanpr = vehicleanpr;
    }

    public boolean getSecuredata() {
        return securedata;
    }

    public void setSecuredata(boolean securedata) {
        this.securedata = securedata;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRegistrationnumber() {
        return registrationnumber;
    }

    public void setRegistrationnumber(String registrationnumber) {
        this.registrationnumber = registrationnumber;
    }

    public String getRegistrationcountry() {
        return registrationcountry;
    }

    public void setRegistrationcountry(String registrationcountry) {
        this.registrationcountry = registrationcountry;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getIdentifyingcharacteristics() {
        return identifyingcharacteristics;
    }

    public void setIdentifyingcharacteristics(String identifyingcharacteristics) {
        this.identifyingcharacteristics = identifyingcharacteristics;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFueltype() {
        return fueltype;
    }

    public void setFueltype(String fueltype) {
        this.fueltype = fueltype;
    }

    public String getForeignvehicle() {
        return foreignvehicle;
    }

    public void setForeignvehicle(String foreignvehicle) {
        this.foreignvehicle = foreignvehicle;
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

    public String getEnginenumber() {
        return enginenumber;
    }

    public void setEnginenumber(String enginenumber) {
        this.enginenumber = enginenumber;
    }

    public String getChassisnumber() {
        return chassisnumber;
    }

    public void setChassisnumber(String chassisnumber) {
        this.chassisnumber = chassisnumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAnprreason() {
        return anprreason;
    }

    public void setAnprreason(String anprreason) {
        this.anprreason = anprreason;
    }
}
