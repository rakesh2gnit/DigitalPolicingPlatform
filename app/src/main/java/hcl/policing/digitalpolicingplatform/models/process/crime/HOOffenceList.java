package hcl.policing.digitalpolicingplatform.models.process.crime;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HOOffenceList implements Serializable {
    @SerializedName("Act")
    @Expose
    private String Act;
    @SerializedName("Class")
    @Expose
    private String _Class;
    @SerializedName("HOCode")
    @Expose
    private String HOCode;
    @SerializedName("HOName")
    @Expose
    private String HOName;
    @SerializedName("HOParentName")
    @Expose
    private String HOParentName;
    @SerializedName("ModelID")
    @Expose
    private String ModelID;
    @SerializedName("ModelState")
    @Expose
    private String ModelState;
    @SerializedName("Offence")
    @Expose
    private String Offence;
    @SerializedName("OffenceCategory")
    @Expose
    private String OffenceCategory;
    @SerializedName("OffenceCode")
    @Expose
    private String OffenceCode;
    @SerializedName("State")
    @Expose
    private String State;
    @SerializedName("SubClass")
    @Expose
    private String SubClass;
    @SerializedName("System")
    @Expose
    private String System;

    public String getAct() {
        return Act;
    }

    public void setAct(String act) {
        Act = act;
    }

    public String get_Class() {
        return _Class;
    }

    public void set_Class(String _Class) {
        this._Class = _Class;
    }

    public String getHOCode() {
        return HOCode;
    }

    public void setHOCode(String HOCode) {
        this.HOCode = HOCode;
    }

    public String getHOName() {
        return HOName;
    }

    public void setHOName(String HOName) {
        this.HOName = HOName;
    }

    public String getHOParentName() {
        return HOParentName;
    }

    public void setHOParentName(String HOParentName) {
        this.HOParentName = HOParentName;
    }

    public String getModelID() {
        return ModelID;
    }

    public void setModelID(String modelID) {
        ModelID = modelID;
    }

    public String getModelState() {
        return ModelState;
    }

    public void setModelState(String modelState) {
        ModelState = modelState;
    }

    public String getOffence() {
        return Offence;
    }

    public void setOffence(String offence) {
        Offence = offence;
    }

    public String getOffenceCategory() {
        return OffenceCategory;
    }

    public void setOffenceCategory(String offenceCategory) {
        OffenceCategory = offenceCategory;
    }

    public String getOffenceCode() {
        return OffenceCode;
    }

    public void setOffenceCode(String offenceCode) {
        OffenceCode = offenceCode;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getSubClass() {
        return SubClass;
    }

    public void setSubClass(String subClass) {
        SubClass = subClass;
    }

    public String getSystem() {
        return System;
    }

    public void setSystem(String system) {
        System = system;
    }
}