package hcl.policing.digitalpolicingplatform.models.process.fds.pole;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HistoryBean implements Serializable {
    @Expose
    @SerializedName("UpdatedDate")
    private String updateddate;
    @Expose
    @SerializedName("UpdatedBy")
    private String updatedby;
    @Expose
    @SerializedName("URN")
    private String urn;
    @Expose
    @SerializedName("Type")
    private String type;
    @Expose
    @SerializedName("Stage")
    private String stage;
    @Expose
    @SerializedName("Role")
    private String role;
    @Expose
    @SerializedName("ID")
    private String id;
    @Expose
    @SerializedName("Description")
    private String description;
    @Expose
    @SerializedName("RestrictedAccess")
    private String restrictedAccess;
    @Expose
    @SerializedName("CovertAccess")
    private String covertAccess;

    @Expose
    @SerializedName("CreatedOn")
    private String createdon;
    @Expose
    @SerializedName("CreatedDate")
    private String createddate;
    @Expose
    @SerializedName("CreatedBy")
    private String createdby;

    public String getUpdateddate() {
        return updateddate;
    }

    public void setUpdateddate(String updateddate) {
        this.updateddate = updateddate;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public String getUrn() {
        return urn;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRestrictedAccess() {
        return restrictedAccess;
    }

    public void setRestrictedAccess(String restrictedAccess) {
        this.restrictedAccess = restrictedAccess;
    }

    public String getCovertAccess() {
        return covertAccess;
    }

    public void setCovertAccess(String covertAccess) {
        this.covertAccess = covertAccess;
    }

    public String getCreatedon() {
        return createdon;
    }

    public void setCreatedon(String createdon) {
        this.createdon = createdon;
    }

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }
}
