package hcl.policing.digitalpolicingplatform.models.process.officer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import hcl.policing.digitalpolicingplatform.models.login.BasedataModel;

public class UpdateUserRequestModel {

    @Expose
    @SerializedName("User")
    private UserRequestModel user;
    @Expose
    @SerializedName("BaseData")
    private BasedataModel basedata;

    public UserRequestModel getUser() {
        return user;
    }

    public void setUser(UserRequestModel user) {
        this.user = user;
    }

    public BasedataModel getBasedata() {
        return basedata;
    }

    public void setBasedata(BasedataModel basedata) {
        this.basedata = basedata;
    }
}
