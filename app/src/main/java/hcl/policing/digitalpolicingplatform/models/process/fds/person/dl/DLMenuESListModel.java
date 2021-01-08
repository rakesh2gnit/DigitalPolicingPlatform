package hcl.policing.digitalpolicingplatform.models.process.fds.person.dl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DLMenuESListModel implements Serializable {
    @Expose
    @SerializedName("Points")
    private String points;
    @Expose
    @SerializedName("Other")
    private String other;
    @Expose
    @SerializedName("DrinkDrug")
    private String drinkdrug;
    @Expose
    @SerializedName("Disqualified")
    private String disqualified;

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getDrinkdrug() {
        return drinkdrug;
    }

    public void setDrinkdrug(String drinkdrug) {
        this.drinkdrug = drinkdrug;
    }

    public String getDisqualified() {
        return disqualified;
    }

    public void setDisqualified(String disqualified) {
        this.disqualified = disqualified;
    }
}
