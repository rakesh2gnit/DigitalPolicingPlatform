package hcl.policing.digitalpolicingplatform.models.process.fds.person.pnc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BasedatacontractBean {
    @Expose
    @SerializedName("TotalRecordCount")
    private int totalrecordcount;
    @Expose
    @SerializedName("TotalPageCount")
    private int totalpagecount;
    @Expose
    @SerializedName("SubProcess")
    private String subprocess;
    @Expose
    @SerializedName("PropertyExt3")
    private String propertyext3;
    @Expose
    @SerializedName("Process")
    private String process;
    @Expose
    @SerializedName("FDSRequestString")
    private String fdsrequeststring;
    @Expose
    @SerializedName("CurrentPage")
    private int currentpage;

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

    public String getSubprocess() {
        return subprocess;
    }

    public void setSubprocess(String subprocess) {
        this.subprocess = subprocess;
    }

    public String getPropertyext3() {
        return propertyext3;
    }

    public void setPropertyext3(String propertyext3) {
        this.propertyext3 = propertyext3;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getFdsrequeststring() {
        return fdsrequeststring;
    }

    public void setFdsrequeststring(String fdsrequeststring) {
        this.fdsrequeststring = fdsrequeststring;
    }

    public int getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(int currentpage) {
        this.currentpage = currentpage;
    }
}
