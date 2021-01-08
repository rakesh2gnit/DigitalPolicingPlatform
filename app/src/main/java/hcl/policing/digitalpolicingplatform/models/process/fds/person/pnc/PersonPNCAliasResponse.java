package hcl.policing.digitalpolicingplatform.models.process.fds.person.pnc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonPNCAliasResponse {

    @Expose
    @SerializedName("AliasList")
    private List<AliasListBean> aliaslist;
    @Expose
    @SerializedName("AliasDOBList")
    private String aliasdoblist;
    @Expose
    @SerializedName("BaseDataContract")
    private BasedatacontractBean basedatacontract;

    public List<AliasListBean> getAliaslist() {
        return aliaslist;
    }

    public void setAliaslist(List<AliasListBean> aliaslist) {
        this.aliaslist = aliaslist;
    }

    public String getAliasdoblist() {
        return aliasdoblist;
    }

    public void setAliasdoblist(String aliasdoblist) {
        this.aliasdoblist = aliasdoblist;
    }

    public BasedatacontractBean getBasedatacontract() {
        return basedatacontract;
    }

    public void setBasedatacontract(BasedatacontractBean basedatacontract) {
        this.basedatacontract = basedatacontract;
    }
}
