package hcl.policing.digitalpolicingplatform.models.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePasswordResponseModel {
    @Expose
    @SerializedName("SuccessFlag")
    private boolean successflag;
    @Expose
    @SerializedName("Message")
    private String message;

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
