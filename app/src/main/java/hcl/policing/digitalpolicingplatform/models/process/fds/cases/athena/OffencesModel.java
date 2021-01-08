package hcl.policing.digitalpolicingplatform.models.process.fds.cases.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OffencesModel {
    @Expose
    @SerializedName("offenceShortTitle")
    private String offenceshorttitle;
    @Expose
    @SerializedName("SecureData")
    private boolean securedata;
    @Expose
    @SerializedName("OvertFlag")
    private boolean overtflag;
    @Expose
    @SerializedName("OffenceDate")
    private String offencedate;
    @Expose
    @SerializedName("ID")
    private String id;

    public String getOffenceshorttitle() {
        return offenceshorttitle;
    }

    public void setOffenceshorttitle(String offenceshorttitle) {
        this.offenceshorttitle = offenceshorttitle;
    }

    public boolean getSecuredata() {
        return securedata;
    }

    public void setSecuredata(boolean securedata) {
        this.securedata = securedata;
    }

    public boolean getOvertflag() {
        return overtflag;
    }

    public void setOvertflag(boolean overtflag) {
        this.overtflag = overtflag;
    }

    public String getOffencedate() {
        return offencedate;
    }

    public void setOffencedate(String offencedate) {
        this.offencedate = offencedate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
