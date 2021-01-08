package hcl.policing.digitalpolicingplatform.models.process.fds.person.nflms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonNFLMSDetailResponse {

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
