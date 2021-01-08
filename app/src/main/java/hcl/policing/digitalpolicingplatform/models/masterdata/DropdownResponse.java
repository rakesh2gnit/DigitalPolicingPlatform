package hcl.policing.digitalpolicingplatform.models.masterdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DropdownResponse implements Serializable {

    @SerializedName("GetMasterDataResponse")
    @Expose
    private GetMasterDataResponse masterDataResponse;

    public GetMasterDataResponse getMasterDataResponse() {
        return masterDataResponse;
    }

    public void setMasterDataResponse(GetMasterDataResponse user) {
        this.masterDataResponse = masterDataResponse;
    }
}
