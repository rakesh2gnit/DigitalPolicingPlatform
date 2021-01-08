package hcl.policing.digitalpolicingplatform.models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BasedataModel {

    @Expose
    @SerializedName("SessionID")
    private String sessionid;
    @Expose
    @SerializedName("CollarId")
    private String collarid;
    @Expose
    @SerializedName("Function")
    private String function;
    @Expose
    @SerializedName("SubProcessName")
    private String subprocessname;
    @Expose
    @SerializedName("Process")
    private String process;
    @Expose
    @SerializedName("CustomerCode")
    private String customercode;

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getCollarid() {
        return collarid;
    }

    public void setCollarid(String collarid) {
        this.collarid = collarid;
    }
    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getSubprocessname() {
        return subprocessname;
    }

    public void setSubprocessname(String subprocessname) {
        this.subprocessname = subprocessname;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getCustomercode() {
        return customercode;
    }

    public void setCustomercode(String customercode) {
        this.customercode = customercode;
    }
}
