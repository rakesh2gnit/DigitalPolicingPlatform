package hcl.policing.digitalpolicingplatform.models.serverRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProcessRequestModel implements Serializable {


    @Expose
    @SerializedName("serverRequest")
    private List<ServerRequestBean> serverRequest;

    public List<ServerRequestBean> getServerRequest() {
        return serverRequest;
    }

    public void setServerRequest(List<ServerRequestBean> serverRequest) {
        this.serverRequest = serverRequest;
    }
}
