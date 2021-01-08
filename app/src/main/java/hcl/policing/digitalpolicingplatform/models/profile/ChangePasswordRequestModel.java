package hcl.policing.digitalpolicingplatform.models.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import hcl.policing.digitalpolicingplatform.models.login.BasedataModel;

public class ChangePasswordRequestModel implements Serializable {

    @Expose
    @SerializedName("UserId")
    private int userId;
    @Expose
    @SerializedName("Password")
    private String password;
    @Expose
    @SerializedName("OldPassword")
    private String oldPassword;
    @Expose
    @SerializedName("BaseData")
    private BasedataModel basedata;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    public BasedataModel getBasedata() {
        return basedata;
    }

    public void setBasedata(BasedataModel basedata) {
        this.basedata = basedata;
    }
}
