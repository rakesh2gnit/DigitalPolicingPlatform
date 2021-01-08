package hcl.policing.digitalpolicingplatform.models.process.fds.pole;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WarningSignalsBean implements Serializable {
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
    @SerializedName("SupportingNotes")
    private String supportingnotes;
    @Expose
    @SerializedName("Reference")
    private String reference;
    @Expose
    @SerializedName("RecordedOn")
    private String recordedon;
    @Expose
    @SerializedName("Marker")
    private String marker;
    @Expose
    @SerializedName("ID")
    private String id;
    @Expose
    @SerializedName("ExtraProp2")
    private String extraprop2;
    @Expose
    @SerializedName("ExtraProp1")
    private String extraprop1;
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

    public String getSupportingnotes() {
        return supportingnotes;
    }

    public void setSupportingnotes(String supportingnotes) {
        this.supportingnotes = supportingnotes;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getRecordedon() {
        return recordedon;
    }

    public void setRecordedon(String recordedon) {
        this.recordedon = recordedon;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExtraprop2() {
        return extraprop2;
    }

    public void setExtraprop2(String extraprop2) {
        this.extraprop2 = extraprop2;
    }

    public String getExtraprop1() {
        return extraprop1;
    }

    public void setExtraprop1(String extraprop1) {
        this.extraprop1 = extraprop1;
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
