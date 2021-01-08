package hcl.policing.digitalpolicingplatform.models.process.fds.person.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LinkedPropertyItemsBean implements Serializable {
    @Expose
    @SerializedName("shortSummary")
    private String shortsummary;
    @Expose
    @SerializedName("relevantDateTime")
    private String relevantdatetime;
    @Expose
    @SerializedName("SecureData")
    private boolean securedata;
    @Expose
    @SerializedName("OvertFlag")
    private boolean overtflag;
    @Expose
    @SerializedName("ID")
    private String id;
    @Expose
    @SerializedName("FlagMessage")
    private String flagmessage;
    @Expose
    @SerializedName("FlagDescription")
    private String flagdescription;
    @Expose
    @SerializedName("DetailText")
    private String detailtext;
    @Expose
    @SerializedName("TotalRecordCount")
    private int totalrecordcount;
    @Expose
    @SerializedName("TotalPageCount")
    private int totalpagecount;
    @Expose
    @SerializedName("CurrentPage")
    private int currentpage;

    public String getShortsummary() {
        return shortsummary;
    }

    public void setShortsummary(String shortsummary) {
        this.shortsummary = shortsummary;
    }

    public String getRelevantdatetime() {
        return relevantdatetime;
    }

    public void setRelevantdatetime(String relevantdatetime) {
        this.relevantdatetime = relevantdatetime;
    }

    public boolean getSecuredata() {
        return securedata;
    }

    public void setSecuredata(boolean securedata) {
        this.securedata = securedata;
    }

    public boolean getOvertflag() {
        return overtflag;
    }

    public void setOvertflag(boolean overtflag) {
        this.overtflag = overtflag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlagmessage() {
        return flagmessage;
    }

    public void setFlagmessage(String flagmessage) {
        this.flagmessage = flagmessage;
    }

    public String getFlagdescription() {
        return flagdescription;
    }

    public void setFlagdescription(String flagdescription) {
        this.flagdescription = flagdescription;
    }

    public String getDetailtext() {
        return detailtext;
    }

    public void setDetailtext(String detailtext) {
        this.detailtext = detailtext;
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
