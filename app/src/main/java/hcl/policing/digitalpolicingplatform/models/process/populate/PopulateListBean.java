package hcl.policing.digitalpolicingplatform.models.process.populate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import hcl.policing.digitalpolicingplatform.models.process.PageSectionListBean;

public class PopulateListBean implements Serializable {

    @Expose
    @SerializedName("Populate_Section")
    private List<PopulateSectionListBean> populateSectionList;
    @Expose
    @SerializedName("Search_Id")
    private int search_Id;
    @Expose
    @SerializedName("Search_Name")
    private String search_Name;

    public List<PopulateSectionListBean> getPopulateSectionList() {
        return populateSectionList;
    }

    public void setPopulateSectionList(List<PopulateSectionListBean> populateSectionList) {
        this.populateSectionList = populateSectionList;
    }

    public int getSearch_Id() {
        return search_Id;
    }

    public void setSearch_Id(int search_Id) {
        this.search_Id = search_Id;
    }

    public String getSearch_Name() {
        return search_Name;
    }

    public void setSearch_Name(String search_Name) {
        this.search_Name = search_Name;
    }
}
