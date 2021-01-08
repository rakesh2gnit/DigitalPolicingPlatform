package hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.pnc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehiclePNCDetailResponse {

    @Expose
    @SerializedName("MotorVehicle")
    private MotorvehicleBean motorvehicle;
    @Expose
    @SerializedName("BaseDataContract")
    private BasedatacontractBean basedatacontract;

    public MotorvehicleBean getMotorvehicle() {
        return motorvehicle;
    }

    public void setMotorvehicle(MotorvehicleBean motorvehicle) {
        this.motorvehicle = motorvehicle;
    }

    public BasedatacontractBean getBasedatacontract() {
        return basedatacontract;
    }

    public void setBasedatacontract(BasedatacontractBean basedatacontract) {
        this.basedatacontract = basedatacontract;
    }

    public static class MotorvehicleBean {
        @Expose
        @SerializedName("VROLit")
        private String vrolit;
        @Expose
        @SerializedName("VIN")
        private String vin;
        @Expose
        @SerializedName("VELDate")
        private String veldate;
        @Expose
        @SerializedName("Type")
        private String type;
        @Expose
        @SerializedName("TotalReportCount")
        private String totalreportcount;
        @Expose
        @SerializedName("TotalInsuranceCount")
        private String totalinsurancecount;
        @Expose
        @SerializedName("SummaryHazards")
        private String summaryhazards;
        @Expose
        @SerializedName("Stolen")
        private String stolen;
        @Expose
        @SerializedName("Registered")
        private String registered;
        @Expose
        @SerializedName("PurchaseDate")
        private String purchasedate;
        @Expose
        @SerializedName("PreviousVRNReg")
        private String previousvrnreg;
        @Expose
        @SerializedName("Owner")
        private String owner;
        @Expose
        @SerializedName("NumberPlate")
        private String numberplate;
        @Expose
        @SerializedName("Model")
        private String model;
        @Expose
        @SerializedName("Markers")
        private String markers;
        @Expose
        @SerializedName("Make")
        private String make;
        @Expose
        @SerializedName("MOTExpiry")
        private String motexpiry;
        @Expose
        @SerializedName("KeeperNotify")
        private String keepernotify;
        @Expose
        @SerializedName("InsuranceText")
        private String insurancetext;
        @Expose
        @SerializedName("HazardsFlag")
        private String hazardsflag;
        @Expose
        @SerializedName("GrossWeight")
        private String grossweight;
        @Expose
        @SerializedName("EngineNumber")
        private String enginenumber;
        @Expose
        @SerializedName("Colour")
        private String colour;
        @Expose
        @SerializedName("Address")
        private String address;

        public String getVrolit() {
            return vrolit;
        }

        public void setVrolit(String vrolit) {
            this.vrolit = vrolit;
        }

        public String getVin() {
            return vin;
        }

        public void setVin(String vin) {
            this.vin = vin;
        }

        public String getVeldate() {
            return veldate;
        }

        public void setVeldate(String veldate) {
            this.veldate = veldate;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTotalreportcount() {
            return totalreportcount;
        }

        public void setTotalreportcount(String totalreportcount) {
            this.totalreportcount = totalreportcount;
        }

        public String getTotalinsurancecount() {
            return totalinsurancecount;
        }

        public void setTotalinsurancecount(String totalinsurancecount) {
            this.totalinsurancecount = totalinsurancecount;
        }

        public String getSummaryhazards() {
            return summaryhazards;
        }

        public void setSummaryhazards(String summaryhazards) {
            this.summaryhazards = summaryhazards;
        }

        public String getStolen() {
            return stolen;
        }

        public void setStolen(String stolen) {
            this.stolen = stolen;
        }

        public String getRegistered() {
            return registered;
        }

        public void setRegistered(String registered) {
            this.registered = registered;
        }

        public String getPurchasedate() {
            return purchasedate;
        }

        public void setPurchasedate(String purchasedate) {
            this.purchasedate = purchasedate;
        }

        public String getPreviousvrnreg() {
            return previousvrnreg;
        }

        public void setPreviousvrnreg(String previousvrnreg) {
            this.previousvrnreg = previousvrnreg;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getNumberplate() {
            return numberplate;
        }

        public void setNumberplate(String numberplate) {
            this.numberplate = numberplate;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getMarkers() {
            return markers;
        }

        public void setMarkers(String markers) {
            this.markers = markers;
        }

        public String getMake() {
            return make;
        }

        public void setMake(String make) {
            this.make = make;
        }

        public String getMotexpiry() {
            return motexpiry;
        }

        public void setMotexpiry(String motexpiry) {
            this.motexpiry = motexpiry;
        }

        public String getKeepernotify() {
            return keepernotify;
        }

        public void setKeepernotify(String keepernotify) {
            this.keepernotify = keepernotify;
        }

        public String getInsurancetext() {
            return insurancetext;
        }

        public void setInsurancetext(String insurancetext) {
            this.insurancetext = insurancetext;
        }

        public String getHazardsflag() {
            return hazardsflag;
        }

        public void setHazardsflag(String hazardsflag) {
            this.hazardsflag = hazardsflag;
        }

        public String getGrossweight() {
            return grossweight;
        }

        public void setGrossweight(String grossweight) {
            this.grossweight = grossweight;
        }

        public String getEnginenumber() {
            return enginenumber;
        }

        public void setEnginenumber(String enginenumber) {
            this.enginenumber = enginenumber;
        }

        public String getColour() {
            return colour;
        }

        public void setColour(String colour) {
            this.colour = colour;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static class BasedatacontractBean {
        @Expose
        @SerializedName("TotalRecordCount")
        private int totalrecordcount;
        @Expose
        @SerializedName("TotalPageCount")
        private int totalpagecount;
        @Expose
        @SerializedName("SubProcess")
        private String subprocess;
        @Expose
        @SerializedName("ReasonCode")
        private String reasoncode;
        @Expose
        @SerializedName("PropertyExt5")
        private String propertyext5;
        @Expose
        @SerializedName("PropertyExt3")
        private String propertyext3;
        @Expose
        @SerializedName("Process")
        private String process;
        @Expose
        @SerializedName("LayerName")
        private String layername;
        @Expose
        @SerializedName("FDSRequestString")
        private String fdsrequeststring;
        @Expose
        @SerializedName("CurrentPage")
        private int currentpage;

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

        public String getSubprocess() {
            return subprocess;
        }

        public void setSubprocess(String subprocess) {
            this.subprocess = subprocess;
        }

        public String getReasoncode() {
            return reasoncode;
        }

        public void setReasoncode(String reasoncode) {
            this.reasoncode = reasoncode;
        }

        public String getPropertyext5() {
            return propertyext5;
        }

        public void setPropertyext5(String propertyext5) {
            this.propertyext5 = propertyext5;
        }

        public String getPropertyext3() {
            return propertyext3;
        }

        public void setPropertyext3(String propertyext3) {
            this.propertyext3 = propertyext3;
        }

        public String getProcess() {
            return process;
        }

        public void setProcess(String process) {
            this.process = process;
        }

        public String getLayername() {
            return layername;
        }

        public void setLayername(String layername) {
            this.layername = layername;
        }

        public String getFdsrequeststring() {
            return fdsrequeststring;
        }

        public void setFdsrequeststring(String fdsrequeststring) {
            this.fdsrequeststring = fdsrequeststring;
        }

        public int getCurrentpage() {
            return currentpage;
        }

        public void setCurrentpage(int currentpage) {
            this.currentpage = currentpage;
        }
    }
}
