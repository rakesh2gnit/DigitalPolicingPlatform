package hcl.policing.digitalpolicingplatform.models.layoutHelper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AnswerValueDTO implements Serializable {

    @Expose
    @SerializedName("key")
    private String key;
    @Expose
    @SerializedName("value")
    private String value;
    @Expose
    @SerializedName("mendatry")
    private boolean mendatry;
    @Expose
    @SerializedName("code")
    private String code;
    @Expose
    @SerializedName("selectLogic")
    private String selectLogic;
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("dependentOn")
    private int dependentOn;
    @Expose
    @SerializedName("populate")
    private String populate;
    @Expose
    @SerializedName("entryType")
    private String entryType;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isMendatry() {
        return mendatry;
    }

    public void setMendatry(boolean mendatry) {
        this.mendatry = mendatry;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSelectLogic() {
        return selectLogic;
    }

    public void setSelectLogic(String selectLogic) {
        this.selectLogic = selectLogic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDependentOn() {
        return dependentOn;
    }

    public void setDependentOn(int dependentOn) {
        this.dependentOn = dependentOn;
    }

    public String getPopulate() {
        return populate;
    }

    public void setPopulate(String populate) {
        this.populate = populate;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }
}
