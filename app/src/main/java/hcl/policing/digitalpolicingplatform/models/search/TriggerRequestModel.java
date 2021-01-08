package hcl.policing.digitalpolicingplatform.models.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import hcl.policing.digitalpolicingplatform.models.login.BasedataModel;

public class TriggerRequestModel implements Serializable {

    @Expose
    @SerializedName("CustomerId")
    private int customerId;
    @Expose
    @SerializedName("BaseData")
    private BasedataModel basedata;


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public BasedataModel getBasedata() {
        return basedata;
    }

    public void setBasedata(BasedataModel basedata) {
        this.basedata = basedata;
    }
}

