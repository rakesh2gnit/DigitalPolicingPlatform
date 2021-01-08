package hcl.policing.digitalpolicingplatform.models.process.fds.address.nflms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import hcl.policing.digitalpolicingplatform.models.process.fds.person.nflms.NflmsDetailsResponseModel;

public class AddressNFLMSDetailResponse {

    @Expose
    @SerializedName("NFLMSDetailsResponse")
    private NflmsDetailsResponseModel nflmsdetailsresponse;

    public NflmsDetailsResponseModel getNflmsdetailsresponse() {
        return nflmsdetailsresponse;
    }

    public void setNflmsdetailsresponse(NflmsDetailsResponseModel nflmsdetailsresponse) {
        this.nflmsdetailsresponse = nflmsdetailsresponse;
    }
}
