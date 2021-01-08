package hcl.policing.digitalpolicingplatform.models.serverRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ServerRequestBean implements Serializable {
    @Expose
    @SerializedName("sections")
    private List<SectionsBean> sections;
    @Expose
    @SerializedName("serverName")
    private String serverName;
    @Expose
    @SerializedName("processName")
    private String processName;

    public List<SectionsBean> getSections() {
        return sections;
    }

    public void setSections(List<SectionsBean> sections) {
        this.sections = sections;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }
}
