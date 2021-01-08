package hcl.policing.digitalpolicingplatform.models.serverRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServerDataBean implements Serializable {
    @Expose
    @SerializedName("value")
    private String value;
    @Expose
    @SerializedName("serverName")
    private String serverName;
    @Expose
    @SerializedName("fieldName")
    private String fieldName;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
