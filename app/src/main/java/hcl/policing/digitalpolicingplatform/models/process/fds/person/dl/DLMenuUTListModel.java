package hcl.policing.digitalpolicingplatform.models.process.fds.person.dl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DLMenuUTListModel implements Serializable {
    @Expose
    @SerializedName("Restriction4")
    private String restriction4;
    @Expose
    @SerializedName("Restriction3")
    private String restriction3;
    @Expose
    @SerializedName("Restriction2")
    private String restriction2;
    @Expose
    @SerializedName("Restriction1")
    private String restriction1;
    @Expose
    @SerializedName("Harm")
    private String harm;
    @Expose
    @SerializedName("Description5")
    private String description5;
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
    @SerializedName("Category")
    private String category;

    public String getRestriction4() {
        return restriction4;
    }

    public void setRestriction4(String restriction4) {
        this.restriction4 = restriction4;
    }

    public String getRestriction3() {
        return restriction3;
    }

    public void setRestriction3(String restriction3) {
        this.restriction3 = restriction3;
    }

    public String getRestriction2() {
        return restriction2;
    }

    public void setRestriction2(String restriction2) {
        this.restriction2 = restriction2;
    }

    public String getRestriction1() {
        return restriction1;
    }

    public void setRestriction1(String restriction1) {
        this.restriction1 = restriction1;
    }

    public String getHarm() {
        return harm;
    }

    public void setHarm(String harm) {
        this.harm = harm;
    }

    public String getDescription5() {
        return description5;
    }

    public void setDescription5(String description5) {
        this.description5 = description5;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
