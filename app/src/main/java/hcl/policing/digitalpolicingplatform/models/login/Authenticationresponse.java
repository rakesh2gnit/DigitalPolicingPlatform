package hcl.policing.digitalpolicingplatform.models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import hcl.policing.digitalpolicingplatform.models.masterdata.GetMasterDataResponse;

public class Authenticationresponse implements Serializable {

    @SerializedName("BaseDataContract")
    @Expose
    private BaseDataContract baseDataContract;
    @SerializedName("GetMasterDataResponse")
    @Expose
    private GetMasterDataResponse masterDataResponse;
    @SerializedName("MasterDataVersionNumber")
    @Expose
    private String MasterDataVersionNumber;
    @SerializedName("User")
    @Expose
    private User user;

    public BaseDataContract getBaseDataContract() {
        return baseDataContract;
    }

    public void setBaseDataContract(BaseDataContract baseDataContract) {
        this.baseDataContract = baseDataContract;
    }

    public GetMasterDataResponse getMasterDataResponse() {
        return masterDataResponse;
    }

    public void setMasterDataResponse(GetMasterDataResponse masterDataResponse) {
        this.masterDataResponse = masterDataResponse;
    }

    public String getMasterDataVersionNumber() {
        return MasterDataVersionNumber;
    }

    public void setMasterDataVersionNumber(String masterDataVersionNumber) {
        MasterDataVersionNumber = masterDataVersionNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
