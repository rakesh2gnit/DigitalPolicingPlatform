package hcl.policing.digitalpolicingplatform.models.process.fds.person.qas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonQASResponse {

    @Expose
    @SerializedName("Citizens")
    private List<CitizensModel> citizens;

    public List<CitizensModel> getCitizens() {
        return citizens;
    }

    public void setCitizens(List<CitizensModel> citizens) {
        this.citizens = citizens;
    }

}

