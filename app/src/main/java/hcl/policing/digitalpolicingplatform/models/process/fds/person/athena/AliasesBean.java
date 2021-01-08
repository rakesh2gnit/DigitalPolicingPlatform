package hcl.policing.digitalpolicingplatform.models.process.fds.person.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AliasesBean {
    @Expose
    @SerializedName("ID")
    private String id;
    @Expose
    @SerializedName("DOB")
    private String dob;
    @Expose
    @SerializedName("AliasType")
    private String aliastype;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAliastype() {
        return aliastype;
    }

    public void setAliastype(String aliastype) {
        this.aliastype = aliastype;
    }
}
