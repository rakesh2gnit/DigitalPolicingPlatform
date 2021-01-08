package hcl.policing.digitalpolicingplatform.models.process.fds.investigation.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EnquiryLogsModel implements Serializable {
    @Expose
    @SerializedName("LogTime")
    private String logtime;
    @Expose
    @SerializedName("LogDate")
    private String logdate;
    @Expose
    @SerializedName("ID")
    private String id;
    @Expose
    @SerializedName("EntryType")
    private String entrytype;
    @Expose
    @SerializedName("EntryText")
    private String entrytext;
    @Expose
    @SerializedName("EntryIndex")
    private int entryindex;
    @Expose
    @SerializedName("EnteredBy")
    private String enteredby;
    @Expose
    @SerializedName("TotalRecordCount")
    private int totalrecordcount;
    @Expose
    @SerializedName("TotalPageCount")
    private int totalpagecount;
    @Expose
    @SerializedName("CurrentPage")
    private int currentpage;

    public String getLogtime() {
        return logtime;
    }

    public void setLogtime(String logtime) {
        this.logtime = logtime;
    }

    public String getLogdate() {
        return logdate;
    }

    public void setLogdate(String logdate) {
        this.logdate = logdate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntrytype() {
        return entrytype;
    }

    public void setEntrytype(String entrytype) {
        this.entrytype = entrytype;
    }

    public String getEntrytext() {
        return entrytext;
    }

    public void setEntrytext(String entrytext) {
        this.entrytext = entrytext;
    }

    public int getEntryindex() {
        return entryindex;
    }

    public void setEntryindex(int entryindex) {
        this.entryindex = entryindex;
    }

    public String getEnteredby() {
        return enteredby;
    }

    public void setEnteredby(String enteredby) {
        this.enteredby = enteredby;
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
