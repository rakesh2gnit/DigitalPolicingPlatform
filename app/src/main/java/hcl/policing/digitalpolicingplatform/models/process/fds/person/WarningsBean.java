package hcl.policing.digitalpolicingplatform.models.process.fds.person;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WarningsBean implements Serializable {
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("toDate")
    private String todate;
    @Expose
    @SerializedName("fromDate")
    private String fromdate;
    @Expose
    @SerializedName("warningName")
    private String warningname;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getWarningname() {
        return warningname;
    }

    public void setWarningname(String warningname) {
        this.warningname = warningname;
    }
}
