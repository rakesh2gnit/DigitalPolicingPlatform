
package hcl.policing.digitalpolicingplatform.models.searchList;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchList implements Serializable
{

    @SerializedName("Label")
    @Expose
    private String label;
    @SerializedName("LabelId")
    @Expose
    private Integer labelId;
    @SerializedName("TotalCount")
    @Expose
    private Integer totalCount;
    @SerializedName("PageCount")
    @Expose
    private Integer pageCount;
    @SerializedName("isMoreData")
    @Expose
    private Boolean isMoreData;
    @SerializedName("viewType")
    @Expose
    private String viewType;
    @SerializedName("functionName")
    @Expose
    private String functionName;
    @SerializedName("LabelSection")
    @Expose
    private List<LabelSection> labelSection = null;
    private final static long serialVersionUID = -2621730582987994579L;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Boolean getIsMoreData() {
        return isMoreData;
    }

    public void setIsMoreData(Boolean isMoreData) {
        this.isMoreData = isMoreData;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public List<LabelSection> getLabelSection() {
        return labelSection;
    }

    public void setLabelSection(List<LabelSection> labelSection) {
        this.labelSection = labelSection;
    }

}
