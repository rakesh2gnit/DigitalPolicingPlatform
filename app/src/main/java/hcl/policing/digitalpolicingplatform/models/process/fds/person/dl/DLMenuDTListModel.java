package hcl.policing.digitalpolicingplatform.models.process.fds.person.dl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DLMenuDTListModel implements Serializable {
    @Expose
    @SerializedName("Document")
    private String document;
    @Expose
    @SerializedName("Description4")
    private String description4;
    @Expose
    @SerializedName("Description3")
    private String description3;
    @Expose
    @SerializedName("Description2")
    private String description2;
    @Expose
    @SerializedName("Description1")
    private String description1;
    @Expose
    @SerializedName("Date")
    private String date;

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getDescription4() {
        return description4;
    }

    public void setDescription4(String description4) {
        this.description4 = description4;
    }

    public String getDescription3() {
        return description3;
    }

    public void setDescription3(String description3) {
        this.description3 = description3;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
