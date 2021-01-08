package hcl.policing.digitalpolicingplatform.models.process.fds.address.stops;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchVehicleListModel {
    @Expose
    @SerializedName("VRM")
    private String vrm;
    @Expose
    @SerializedName("Type")
    private String type;
    @Expose
    @SerializedName("Model")
    private String model;
    @Expose
    @SerializedName("Make")
    private String make;
    @Expose
    @SerializedName("Color")
    private String color;
    @Expose
    @SerializedName("Class")
    private String vehicleclass;
    @Expose
    @SerializedName("Category")
    private String category;
    @Expose
    @SerializedName("CC")
    private String cc;
    @Expose
    @SerializedName("TotalRecordCount")
    private int totalrecordcount;
    @Expose
    @SerializedName("TotalPageCount")
    private int totalpagecount;
    @Expose
    @SerializedName("CurrentPage")
    private int currentpage;

    public String getVrm() {
        return vrm;
    }

    public void setVrm(String vrm) {
        this.vrm = vrm;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getVehicleclass() {
        return vehicleclass;
    }

    public void setVehicleclass(String vehicleclass) {
        this.vehicleclass = vehicleclass;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
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
