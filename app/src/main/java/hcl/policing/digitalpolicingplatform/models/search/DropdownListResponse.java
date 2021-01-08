package hcl.policing.digitalpolicingplatform.models.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DropdownListResponse implements Serializable {

    @Expose
    @SerializedName("ParentID")
    private String parentid;
    @Expose
    @SerializedName("Description")
    private String description;
    @Expose
    @SerializedName("Code")
    private String code;
    @Expose
    @SerializedName("LookUpType")
    private String lookuptype;
    @Expose
    @SerializedName("ProcessName")
    private String processname;
    @Expose
    @SerializedName("LookUpName")
    private String lookupname;
    @Expose
    @SerializedName("LookupID")
    private String lookupid;

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLookuptype() {
        return lookuptype;
    }

    public void setLookuptype(String lookuptype) {
        this.lookuptype = lookuptype;
    }

    public String getProcessname() {
        return processname;
    }

    public void setProcessname(String processname) {
        this.processname = processname;
    }

    public String getLookupname() {
        return lookupname;
    }

    public void setLookupname(String lookupname) {
        this.lookupname = lookupname;
    }

    public String getLookupid() {
        return lookupid;
    }

    public void setLookupid(String lookupid) {
        this.lookupid = lookupid;
    }
}
