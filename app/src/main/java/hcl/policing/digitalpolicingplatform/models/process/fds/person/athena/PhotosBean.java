package hcl.policing.digitalpolicingplatform.models.process.fds.person.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PhotosBean implements Serializable {
    @Expose
    @SerializedName("PhotoTakenDate")
    private String phototakendate;
    @Expose
    @SerializedName("PhotoData")
    private String photodata;

    public String getPhototakendate() {
        return phototakendate;
    }

    public void setPhototakendate(String phototakendate) {
        this.phototakendate = phototakendate;
    }

    public String getPhotodata() {
        return photodata;
    }

    public void setPhotodata(String photodata) {
        this.photodata = photodata;
    }
}
