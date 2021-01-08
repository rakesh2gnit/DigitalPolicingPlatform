package hcl.policing.digitalpolicingplatform.models.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import hcl.policing.digitalpolicingplatform.models.process.PageSection_detailListBean;

public class SearchListBean implements Serializable {
    @Expose
    @SerializedName("PageSection_detailList")
    private List<PageSection_detailListBean> PageSection_detailList;
    @Expose
    @SerializedName("Search_Name")
    private String Search_Name;
    @Expose
    @SerializedName("Search_Id")
    private int Search_Id;
    @Expose
    @SerializedName("DialogHeading")
    private String DialogHeading;
    @Expose
    @SerializedName("Mandatory_Count")
    private int Mandatory_Count;

    public List<PageSection_detailListBean> getPageSection_detailList() {
        return PageSection_detailList;
    }

    public void setPageSection_detailList(List<PageSection_detailListBean> PageSection_detailList) {
        this.PageSection_detailList = PageSection_detailList;
    }

    public String getSearch_Name() {
        return Search_Name;
    }

    public void setSearch_Name(String search_Name) {
        Search_Name = search_Name;
    }

    public int getSearch_Id() {
        return Search_Id;
    }

    public void setSearch_Id(int search_Id) {
        Search_Id = search_Id;
    }

    public String getDialogHeading() {
        return DialogHeading;
    }

    public void setDialogHeading(String dialogHeading) {
        DialogHeading = dialogHeading;
    }

    public int getMandatory_Count() {
        return Mandatory_Count;
    }

    public void setMandatory_Count(int mandatory_Count) {
        Mandatory_Count = mandatory_Count;
    }
}