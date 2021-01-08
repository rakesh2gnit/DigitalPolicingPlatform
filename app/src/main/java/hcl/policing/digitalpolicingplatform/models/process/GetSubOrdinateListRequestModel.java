package hcl.policing.digitalpolicingplatform.models.process;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import hcl.policing.digitalpolicingplatform.models.login.BasedataModel;

public class GetSubOrdinateListRequestModel {

    @Expose
    @SerializedName("SupervisorServiceNumber")
    private String supervisorServiceNumber;
    @Expose
    @SerializedName("BaseData")
    private BasedataModel basedata;


    public String getSupervisorServiceNumber() {
        return supervisorServiceNumber;
    }

    public void setSupervisorServiceNumber(String supervisorServiceNumber) {
        this.supervisorServiceNumber = supervisorServiceNumber;
    }

    public BasedataModel getBasedata() {
        return basedata;
    }

    public void setBasedata(BasedataModel basedata) {
        this.basedata = basedata;
    }

}
