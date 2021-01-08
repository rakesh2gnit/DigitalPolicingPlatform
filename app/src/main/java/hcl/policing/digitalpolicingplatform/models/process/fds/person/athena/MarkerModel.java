package hcl.policing.digitalpolicingplatform.models.process.fds.person.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarkerModel {
    @Expose
    @SerializedName("markerType")
    private String markertype;
    @Expose
    @SerializedName("markerSource")
    private String markersource;
    @Expose
    @SerializedName("markerOrigin")
    private String markerorigin;
    @Expose
    @SerializedName("lastModifiedDateTime")
    private String lastmodifieddatetime;
    @Expose
    @SerializedName("createdDateTime")
    private String createddatetime;
    @Expose
    @SerializedName("ToDate")
    private String todate;
    @Expose
    @SerializedName("MarkerValue")
    private String markervalue;
    @Expose
    @SerializedName("ID")
    private String id;
    @Expose
    @SerializedName("FromDate")
    private String fromdate;
    @Expose
    @SerializedName("Description")
    private String description;

    public String getMarkertype() {
        return markertype;
    }

    public void setMarkertype(String markertype) {
        this.markertype = markertype;
    }

    public String getMarkersource() {
        return markersource;
    }

    public void setMarkersource(String markersource) {
        this.markersource = markersource;
    }

    public String getMarkerorigin() {
        return markerorigin;
    }

    public void setMarkerorigin(String markerorigin) {
        this.markerorigin = markerorigin;
    }

    public String getLastmodifieddatetime() {
        return lastmodifieddatetime;
    }

    public void setLastmodifieddatetime(String lastmodifieddatetime) {
        this.lastmodifieddatetime = lastmodifieddatetime;
    }

    public String getCreateddatetime() {
        return createddatetime;
    }

    public void setCreateddatetime(String createddatetime) {
        this.createddatetime = createddatetime;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public String getMarkervalue() {
        return markervalue;
    }

    public void setMarkervalue(String markervalue) {
        this.markervalue = markervalue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
