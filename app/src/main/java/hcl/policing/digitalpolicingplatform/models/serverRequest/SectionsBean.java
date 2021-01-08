package hcl.policing.digitalpolicingplatform.models.serverRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SectionsBean implements Serializable {
    @Expose
    @SerializedName("serverData")
    private List<ServerDataBean> serverData;
    @Expose
    @SerializedName("sectionId")
    private int sectionId;
    @Expose
    @SerializedName("serverName")
    private String serverName;
    @Expose
    @SerializedName("sectioName")
    private String sectioName;

    public List<ServerDataBean> getServerData() {
        return serverData;
    }

    public void setServerData(List<ServerDataBean> serverData) {
        this.serverData = serverData;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getSectioName() {
        return sectioName;
    }

    public void setSectioName(String sectioName) {
        this.sectioName = sectioName;
    }
}
