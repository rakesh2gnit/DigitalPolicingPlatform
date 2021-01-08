
package hcl.policing.digitalpolicingplatform.models.searchList;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LabelSectionDetail implements Serializable
{

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Value")
    @Expose
    private String value;
    @SerializedName("url")
    @Expose
    private Object url;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("viewType")
    @Expose
    private String viewType;
    @SerializedName("action")
    @Expose
    private String action;
    private final static long serialVersionUID = 7491735994674806737L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
