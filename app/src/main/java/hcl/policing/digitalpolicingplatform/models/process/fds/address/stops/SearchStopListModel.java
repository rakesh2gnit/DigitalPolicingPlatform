package hcl.policing.digitalpolicingplatform.models.process.fds.address.stops;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchStopListModel {

    @Expose
    @SerializedName("StopsDateTime")
    private String stopsdatetime;
    @Expose
    @SerializedName("StopID")
    private String stopid;
    @Expose
    @SerializedName("ScreenName")
    private String screenname;
    @Expose
    @SerializedName("PowerUsed")
    private String powerused;
    @Expose
    @SerializedName("Object")
    private String object;
    @Expose
    @SerializedName("Grounds")
    private String grounds;
    @Expose
    @SerializedName("StopAddress")
    private String stopaddress;
    @Expose
    @SerializedName("TotalRecordCount")
    private int totalrecordcount;
    @Expose
    @SerializedName("TotalPageCount")
    private int totalpagecount;
    @Expose
    @SerializedName("CurrentPage")
    private int currentpage;

    public String getStopsdatetime() {
        return stopsdatetime;
    }

    public void setStopsdatetime(String stopsdatetime) {
        this.stopsdatetime = stopsdatetime;
    }

    public String getStopid() {
        return stopid;
    }

    public void setStopid(String stopid) {
        this.stopid = stopid;
    }
    public String getScreenname() {
        return screenname;
    }

    public void setScreenname(String screenname) {
        this.screenname = screenname;
    }
    public String getPowerused() {
        return powerused;
    }

    public void setPowerused(String powerused) {
        this.powerused = powerused;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getGrounds() {
        return grounds;
    }

    public void setGrounds(String grounds) {
        this.grounds = grounds;
    }
    public String getStopaddress() {
        return stopaddress;
    }

    public void setStopaddress(String stopaddress) {
        this.stopaddress = stopaddress;
    }

    public int getTotalrecordcount() {
        return totalrecordcount;
    }

    public void setTotalrecordcount(int totalrecordcount) {
        this.totalrecordcount = totalrecordcount;
    }

    public int getTotalpagecount() {
        return totalpagecount;
    }

    public void setTotalpagecount(int totalpagecount) {
        this.totalpagecount = totalpagecount;
    }

    public int getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(int currentpage) {
        this.currentpage = currentpage;
    }
}
