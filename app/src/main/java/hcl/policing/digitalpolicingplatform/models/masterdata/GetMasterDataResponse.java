package hcl.policing.digitalpolicingplatform.models.masterdata;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetMasterDataResponse implements Serializable {

    @SerializedName("MasterDataProcesses")
    @Expose
    private List<MasterDataProcess> masterDataProcesses = null;
    @SerializedName("VersionNumber")
    @Expose
    private String versionNumber;

    public List<MasterDataProcess> getMasterDataProcesses() {
        return masterDataProcesses;
    }

    public void setMasterDataProcesses(List<MasterDataProcess> masterDataProcesses) {
        this.masterDataProcesses = masterDataProcesses;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }
}
