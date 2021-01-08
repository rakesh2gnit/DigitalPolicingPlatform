package hcl.policing.digitalpolicingplatform.models.process.fds.investigation.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.SearchdetailresponseBean;

public class InvestigationAthenaDetailResponse {
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
