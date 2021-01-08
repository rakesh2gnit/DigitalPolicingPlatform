package hcl.policing.digitalpolicingplatform.models.process.fds.person.dl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DLMenuXRListModel implements Serializable {
    @Expose
    @SerializedName("Name4")
    private String name4;
    @Expose
    @SerializedName("Name3")
    private String name3;
    @Expose
    @SerializedName("Name2")
    private String name2;
    @Expose
    @SerializedName("Name1")
    private String name1;
    @Expose
    @SerializedName("Driver")
    private String driver;
    @Expose
    @SerializedName("Date")
    private String date;

    public String getName4() {
        return name4;
    }

    public void setName4(String name4) {
        this.name4 = name4;
    }

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
