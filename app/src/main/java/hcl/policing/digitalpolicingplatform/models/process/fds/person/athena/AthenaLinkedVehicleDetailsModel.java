package hcl.policing.digitalpolicingplatform.models.process.fds.person.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AthenaLinkedVehicleDetailsModel {


    @Expose
    @SerializedName("vehicleDetailResponse")
    private VehicledetailresponseBean vehicledetailresponse;

    public VehicledetailresponseBean getVehicledetailresponse() {
        return vehicledetailresponse;
    }

    public void setVehicledetailresponse(VehicledetailresponseBean vehicledetailresponse) {
        this.vehicledetailresponse = vehicledetailresponse;
    }

    public static class VehicledetailresponseBean {
        @Expose
        @SerializedName("type")
        private String type;
        @Expose
        @SerializedName("shortSummary")
        private String shortsummary;
        @Expose
        @SerializedName("relevantDateTime")
        private String relevantdatetime;
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
        @SerializedName("DetailText")
        private String detailtext;
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

        public String getDetailtext() {
            return detailtext;
        }

        public void setDetailtext(String detailtext) {
            this.detailtext = detailtext;
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
}
