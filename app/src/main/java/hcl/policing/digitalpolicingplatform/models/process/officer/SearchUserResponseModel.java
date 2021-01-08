package hcl.policing.digitalpolicingplatform.models.process.officer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchUserResponseModel {

    @Expose
    @SerializedName("SuccessFlag")
    private boolean successflag;
    @Expose
    @SerializedName("Message")
    private String message;
    @Expose
    @SerializedName("Users")
    private List<UserModel> users;


    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }

    public boolean getSuccessflag() {
        return successflag;
    }


    public boolean isSuccessflag() {
        return successflag;
    }

    public void setSuccessflag(boolean successflag) {
        this.successflag = successflag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
