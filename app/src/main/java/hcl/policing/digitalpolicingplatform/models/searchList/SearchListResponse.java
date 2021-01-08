
package hcl.policing.digitalpolicingplatform.models.searchList;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchListResponse implements Serializable
{

    @SerializedName("SearchList")
    @Expose
    private List<SearchList> searchList = null;
    private final static long serialVersionUID = 4878855595747478731L;

    public List<SearchList> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<SearchList> searchList) {
        this.searchList = searchList;
    }

}
