package hcl.policing.digitalpolicingplatform.models.process;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResponseSubJsonModel implements Serializable {

    @Expose
    @SerializedName("subJsonProcessList")
    private List<SubJsonProcessListBean> subJsonProcessList;

    public List<SubJsonProcessListBean> getSubJsonProcessList() {
        return subJsonProcessList;
    }

    public void setSubJsonProcessList(List<SubJsonProcessListBean> subJsonProcessList) {
        this.subJsonProcessList = subJsonProcessList;
    }
}
