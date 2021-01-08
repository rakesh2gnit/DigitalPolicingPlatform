package hcl.policing.digitalpolicingplatform.models.process.fds.team;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import hcl.policing.digitalpolicingplatform.models.process.fds.person.WarningsBean;

public class TeamBean implements Serializable {

    @Expose
    @SerializedName("teamname")
    private String teamname;
    @Expose
    @SerializedName("Id")
    private String Id;
    @Expose
    @SerializedName("System")
    private String System;

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSystem() {
        return System;
    }

    public void setSystem(String system) {
        System = system;
    }
}
