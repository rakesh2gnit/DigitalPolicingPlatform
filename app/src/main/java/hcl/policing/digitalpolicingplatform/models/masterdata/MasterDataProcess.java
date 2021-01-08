package hcl.policing.digitalpolicingplatform.models.masterdata;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MasterDataProcess implements Serializable {

    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("MasterDataGroups")
    @Expose
    private List<MasterDataGroup> masterDataGroups = null;
    @SerializedName("Name")
    @Expose
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<MasterDataGroup> getMasterDataGroups() {
        return masterDataGroups;
    }

    public void setMasterDataGroups(List<MasterDataGroup> masterDataGroups) {
        this.masterDataGroups = masterDataGroups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
