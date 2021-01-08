package hcl.policing.digitalpolicingplatform.models.process;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;

public class PageSection_detailListBean implements Serializable {
    @Expose
    @SerializedName("Field_Enabled")
    private boolean Field_Enabled;
    @Expose
    @SerializedName("ShowFieldsInOneGo")
    private boolean ShowFieldsInOneGo;
    @Expose
    @SerializedName("Field_Type")
    private String Field_Type;
    @Expose
    @SerializedName("Field_MasterData")
    private String Field_MasterData;
    @Expose
    @SerializedName("Field_Mendatry")
    private boolean Field_Mendatry;
    @Expose
    @SerializedName("Field_Value")
    private String Field_Value;
    @Expose
    @SerializedName("Field_Dependent_On")
    private int Field_Dependent_On;
    @Expose
    @SerializedName("Show_Yes_Child_Id")
    private String Show_Yes_Child_Id;
    @Expose
    @SerializedName("Show_No_Child_Id")
    private String Show_No_Child_Id;
    @Expose
    @SerializedName("Hide_Yes_Child_Id")
    private String Hide_Yes_Child_Id;
    @Expose
    @SerializedName("Hide_No_Child_Id")
    private String Hide_No_Child_Id;
    @Expose
    @SerializedName("Field_Visibility")
    private String Field_Visibility;
    @Expose
    @SerializedName("Field_Input_Type")
    private String Field_Input_Type;
    @Expose
    @SerializedName("Field_Action")
    private String Field_Action;
    @Expose
    @SerializedName("Max_Select")
    private int Max_Select;
    @Expose
    @SerializedName("Select_Logic")
    private String Select_Logic;
    @Expose
    @SerializedName("Field_Id")
    private int Field_Id;
    @Expose
    @SerializedName("Field_Name")
    private String Field_Name;
    @Expose
    @SerializedName("Properties")
    private PropertiesBean PropertiesBean;
    private String Field_ParentId = null;

    public boolean isField_Enabled() {
        return Field_Enabled;
    }

    public void setField_Enabled(boolean field_Enabled) {
        Field_Enabled = field_Enabled;
    }

    public boolean getShowFieldsInOneGo() {
        return ShowFieldsInOneGo;
    }

    public void setShowFieldsInOneGo(boolean ShowFieldsInOneGo) {
        this.ShowFieldsInOneGo = ShowFieldsInOneGo;
    }

    public String getField_Type() {
        return Field_Type;
    }

    public void setField_Type(String Field_Type) {
        this.Field_Type = Field_Type;
    }

    public String getField_MasterData() {
        return Field_MasterData;
    }

    public void setField_MasterData(String Field_MasterData) {
        this.Field_MasterData = Field_MasterData;
    }

    public boolean isField_Mendatry() {
        return Field_Mendatry;
    }

    public void setField_Mendatry(boolean field_Mendatry) {
        Field_Mendatry = field_Mendatry;
    }

    public String getField_Value() {
        return Field_Value;
    }

    public void setField_Value(String Field_Value) {
        this.Field_Value = Field_Value;
    }

    public int getField_Dependent_On() {
        return Field_Dependent_On;
    }

    public void setField_Dependent_On(int field_Dependent_On) {
        Field_Dependent_On = field_Dependent_On;
    }

    public int getField_Id() {
        return Field_Id;
    }

    public void setField_Id(int Field_Id) {
        this.Field_Id = Field_Id;
    }

    public String getShow_Yes_Child_Id() {
        return Show_Yes_Child_Id;
    }

    public void setShow_Yes_Child_Id(String show_Yes_Child_Id) {
        Show_Yes_Child_Id = show_Yes_Child_Id;
    }

    public String getShow_No_Child_Id() {
        return Show_No_Child_Id;
    }

    public void setShow_No_Child_Id(String show_No_Child_Id) {
        Show_No_Child_Id = show_No_Child_Id;
    }

    public String getHide_Yes_Child_Id() {
        return Hide_Yes_Child_Id;
    }

    public void setHide_Yes_Child_Id(String hide_Yes_Child_Id) {
        Hide_Yes_Child_Id = hide_Yes_Child_Id;
    }

    public String getHide_No_Child_Id() {
        return Hide_No_Child_Id;
    }

    public void setHide_No_Child_Id(String hide_No_Child_Id) {
        Hide_No_Child_Id = hide_No_Child_Id;
    }

    public String getField_Visibility() {
        return Field_Visibility;
    }

    public void setField_Visibility(String field_Visibility) {
        Field_Visibility = field_Visibility;
    }

    public String getField_Input_Type() {
        return Field_Input_Type;
    }

    public void setField_Input_Type(String field_Input_Type) {
        Field_Input_Type = field_Input_Type;
    }

    public String getField_Action() {
        return Field_Action;
    }

    public void setField_Action(String field_Action) {
        Field_Action = field_Action;
    }

    public int getMax_Select() {
        return Max_Select;
    }

    public void setMax_Select(int max_Select) {
        Max_Select = max_Select;
    }

    public String getSelect_Logic() {
        return Select_Logic;
    }

    public void setSelect_Logic(String select_Logic) {
        Select_Logic = select_Logic;
    }

    public String getField_Name() {
        return Field_Name;
    }

    public void setField_Name(String Field_Name) {
        this.Field_Name = Field_Name;
    }

    public PropertiesBean getPropertiesBean() {
        return PropertiesBean;
    }

    public void setPropertiesBean(PropertiesBean propertiesBean) {
        PropertiesBean = propertiesBean;
    }

    public String getField_ParentId() {
        return Field_ParentId;
    }

    public void setField_ParentId(String field_ParentId) {
        Field_ParentId = field_ParentId;
    }
}
