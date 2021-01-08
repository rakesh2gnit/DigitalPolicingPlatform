package hcl.policing.digitalpolicingplatform.models.process.populate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Populate_DetailListBean implements Serializable {

    @Expose
    @SerializedName("Field_Id")
    private int field_Id;
    @Expose
    @SerializedName("Field_Name")
    private String field_Name;
    @Expose
    @SerializedName("Populate_From")
    private String populate_From;

    public int getField_Id() {
        return field_Id;
    }

    public void setField_Id(int field_Id) {
        this.field_Id = field_Id;
    }

    public String getField_Name() {
        return field_Name;
    }

    public void setField_Name(String field_Name) {
        this.field_Name = field_Name;
    }

    public String getPopulate_From() {
        return populate_From;
    }

    public void setPopulate_From(String populate_From) {
        this.populate_From = populate_From;
    }
}
