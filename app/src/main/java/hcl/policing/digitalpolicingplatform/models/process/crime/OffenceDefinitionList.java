package hcl.policing.digitalpolicingplatform.models.process.crime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OffenceDefinitionList implements Serializable {
    @SerializedName("Code")
    @Expose
    private String Code;
    @SerializedName("CompletionDate")
    @Expose
    private String CompletionDate;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("Id")
    @Expose
    private String Id;
    @SerializedName("IsActive")
    @Expose
    private String IsActive;
    @SerializedName("Offence")
    @Expose
    private String Offence;
    @SerializedName("ReferenceNumber")
    @Expose
    private String ReferenceNumber;
    @SerializedName("ReferenceType")
    @Expose
    private String ReferenceType;
    @SerializedName("Statute")
    @Expose
    private String Statute;
    @SerializedName("System")
    @Expose
    private String System;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getCompletionDate() {
        return CompletionDate;
    }

    public void setCompletionDate(String completionDate) {
        CompletionDate = completionDate;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }

    public String getOffence() {
        return Offence;
    }

    public void setOffence(String offence) {
        Offence = offence;
    }

    public String getReferenceNumber() {
        return ReferenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        ReferenceNumber = referenceNumber;
    }

    public String getReferenceType() {
        return ReferenceType;
    }

    public void setReferenceType(String referenceType) {
        ReferenceType = referenceType;
    }

    public String getStatute() {
        return Statute;
    }

    public void setStatute(String statute) {
        Statute = statute;
    }

    public String getSystem() {
        return System;
    }

    public void setSystem(String system) {
        System = system;
    }
}