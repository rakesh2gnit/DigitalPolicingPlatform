
package hcl.policing.digitalpolicingplatform.models.searchList;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LabelSection implements Serializable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("audit_detail")
    @Expose
    private String auditDetail;
    @SerializedName("action")
    @Expose
    private Object action;
    @SerializedName("warnings")
    @Expose
    private List<Warning> warnings = null;
    @SerializedName("LabelSectionDetails")
    @Expose
    private List<LabelSectionDetail> labelSectionDetails = null;
    private final static long serialVersionUID = 5587442588547823575L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuditDetail() {
        return auditDetail;
    }

    public void setAuditDetail(String auditDetail) {
        this.auditDetail = auditDetail;
    }

    public Object getAction() {
        return action;
    }

    public void setAction(Object action) {
        this.action = action;
    }

    public List<Warning> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Warning> warnings) {
        this.warnings = warnings;
    }

    public List<LabelSectionDetail> getLabelSectionDetails() {
        return labelSectionDetails;
    }

    public void setLabelSectionDetails(List<LabelSectionDetail> labelSectionDetails) {
        this.labelSectionDetails = labelSectionDetails;
    }

}
