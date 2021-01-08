package hcl.policing.digitalpolicingplatform.models.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DropdownResponseModel implements Serializable {

    @Expose
    @SerializedName("SuccessFlag")
    private boolean successflag;
    @Expose
    @SerializedName("Message")
    private String message;
    @Expose
    @SerializedName("Id")
    private String id;
    @Expose
    @SerializedName("jsonData")
    private String jsondata;

    public boolean getSuccessflag() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJsondata() {
        return jsondata;
    }

    public void setJsondata(String jsondata) {
        this.jsondata = jsondata;
    }
}
