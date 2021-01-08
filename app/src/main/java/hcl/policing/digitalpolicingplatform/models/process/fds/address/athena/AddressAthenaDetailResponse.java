package hcl.policing.digitalpolicingplatform.models.process.fds.address.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.SearchdetailresponseBean;

public class AddressAthenaDetailResponse implements Serializable {

    @Expose
    @SerializedName("searchDetailResponse")
    private SearchdetailresponseBean searchdetailresponse;

    public SearchdetailresponseBean getSearchdetailresponse() {
        return searchdetailresponse;
    }

    public void setSearchdetailresponse(SearchdetailresponseBean searchdetailresponse) {
        this.searchdetailresponse = searchdetailresponse;
    }
}
