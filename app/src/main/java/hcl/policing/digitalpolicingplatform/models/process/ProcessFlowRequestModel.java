package hcl.policing.digitalpolicingplatform.models.process;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import hcl.policing.digitalpolicingplatform.models.login.BasedataModel;

public class ProcessFlowRequestModel {

    @Expose
    @SerializedName("SubProcessId")
    private int subProcessId;
    @Expose
    @SerializedName("BaseData")
    private BasedataModel basedata;

    public int getSubProcessId() {
        return subProcessId;
    }

    public void setSubProcessId(int subProcessId) {
        this.subProcessId = subProcessId;
    }

    public BasedataModel getBasedata() {
        return basedata;
    }

    public void setBasedata(BasedataModel basedata) {
        this.basedata = basedata;
    }
}
