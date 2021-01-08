package hcl.policing.digitalpolicingplatform.models.process.officer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import hcl.policing.digitalpolicingplatform.models.login.BasedataModel;

public class SearchUserByWordRequestModel {

    @Expose
    @SerializedName("IsWildSearch")
    private boolean wildSearch;
    @Expose
    @SerializedName("SearchKey")
    private String searchKey;
    @Expose
    @SerializedName("BaseData")
    private BasedataModel basedata;


    public boolean isWildSearch() {
        return wildSearch;
    }

    public void setWildSearch(boolean wildSearch) {
        this.wildSearch = wildSearch;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public BasedataModel getBasedata() {
        return basedata;
    }

    public void setBasedata(BasedataModel basedata) {
        this.basedata = basedata;
    }

}
