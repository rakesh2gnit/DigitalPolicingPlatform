package hcl.policing.digitalpolicingplatform.models.process.fds.person.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectIterationAthenaDetailResponse {

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
