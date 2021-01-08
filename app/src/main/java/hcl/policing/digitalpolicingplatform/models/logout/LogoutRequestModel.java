package hcl.policing.digitalpolicingplatform.models.logout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import hcl.policing.digitalpolicingplatform.models.login.BasedataModel;

public class LogoutRequestModel {

    @Expose
    @SerializedName("UserId")
    private int userId;
    @Expose
    @SerializedName("BaseData")
    private BasedataModel basedata;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public BasedataModel getBasedata() {
        return basedata;
    }

    public void setBasedata(BasedataModel basedata) {
        this.basedata = basedata;
    }
}
