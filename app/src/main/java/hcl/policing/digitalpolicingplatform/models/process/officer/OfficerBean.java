package hcl.policing.digitalpolicingplatform.models.process.officer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OfficerBean implements Serializable {
    @Expose
    @SerializedName("Force")
    private
    String Force;
    @Expose
    @SerializedName("Force_Code")
    private
    String Force_Code;
    @Expose
    @SerializedName("Officer_Service_Number")
    private
    String Officer_Service_Number;
    @Expose
    @SerializedName("Officer_Collar_Number")
    private
    String Officer_Collar_Number;
    @Expose
    @SerializedName("Officer_Name")
    private
    String Officer_Name;
    @Expose
    @SerializedName("Officer_Rank")
    private
    String Officer_Rank;
    @Expose
    @SerializedName("Officer_Gender")
    private
    String Officer_Gender;
    @Expose
    @SerializedName("Officer_Station")
    private
    String Officer_Station;
    @Expose
    @SerializedName("Id")
    private String Id;
    @Expose
    @SerializedName("System")
    private String System;

    public String getForce() {
        return Force;
    }

    public void setForce(String force) {
        Force = force;
    }

    public String getForce_Code() {
        return Force_Code;
    }

    public void setForce_Code(String force_Code) {
        Force_Code = force_Code;
    }

    public String getOfficer_Service_Number() {
        return Officer_Service_Number;
    }

    public void setOfficer_Service_Number(String officer_Service_Number) {
        Officer_Service_Number = officer_Service_Number;
    }

    public String getOfficer_Collar_Number() {
        return Officer_Collar_Number;
    }

    public void setOfficer_Collar_Number(String officer_Collar_Number) {
        Officer_Collar_Number = officer_Collar_Number;
    }

    public String getOfficer_Name() {
        return Officer_Name;
    }

    public void setOfficer_Name(String officer_Name) {
        Officer_Name = officer_Name;
    }

    public String getOfficer_Rank() {
        return Officer_Rank;
    }

    public void setOfficer_Rank(String officer_Rank) {
        Officer_Rank = officer_Rank;
    }

    public String getOfficer_Gender() {
        return Officer_Gender;
    }

    public void setOfficer_Gender(String officer_Gender) {
        Officer_Gender = officer_Gender;
    }

    public String getOfficer_Station() {
        return Officer_Station;
    }

    public void setOfficer_Station(String officer_Station) {
        Officer_Station = officer_Station;
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
