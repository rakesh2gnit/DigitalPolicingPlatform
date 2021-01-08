package hcl.policing.digitalpolicingplatform.models.process.fds.vehicle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VehicleBean implements Serializable {
    @Expose
    @SerializedName("color_primary")
    private String color_primary;
    @Expose
    @SerializedName("color_secondary")
    private String color_secondary;
    @Expose
    @SerializedName("model")
    private String model;
    @Expose
    @SerializedName("make")
    private String make;
    @Expose
    @SerializedName("vrm")
    private String vrm;
    @Expose
    @SerializedName("Id")
    private String Id;
    @Expose
    @SerializedName("System")
    private String System;

    public String getColor_primary() {
        return color_primary;
    }

    public void setColor_primary(String color_primary) {
        this.color_primary = color_primary;
    }

    public String getColor_secondary() {
        return color_secondary;
    }

    public void setColor_secondary(String color_secondary) {
        this.color_secondary = color_secondary;
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

    public String getVrm() {
        return vrm;
    }

    public void setVrm(String vrm) {
        this.vrm = vrm;
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
