package hcl.policing.digitalpolicingplatform.models.process.fds.pole;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ActionMarkersBean implements Serializable {
    @Expose
    @SerializedName("UpdatedDate")
    private String updateddate;
    @Expose
    @SerializedName("UpdatedBy")
    private String updatedby;
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
