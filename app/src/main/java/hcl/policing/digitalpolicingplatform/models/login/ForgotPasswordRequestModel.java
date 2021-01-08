package hcl.policing.digitalpolicingplatform.models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPasswordRequestModel {

    @Expose
    @SerializedName("UserName")
    private String username;
    @Expose
    @SerializedName("BaseData")
    private BasedataModel basedata;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BasedataModel getBasedata() {
        return basedata;
    }

    public void setBasedata(BasedataModel basedata) {
        this.basedata = basedata;
    }
}
