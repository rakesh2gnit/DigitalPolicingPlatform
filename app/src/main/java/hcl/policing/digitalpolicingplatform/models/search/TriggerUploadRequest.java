package hcl.policing.digitalpolicingplatform.models.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import hcl.policing.digitalpolicingplatform.models.login.BasedataModel;

public class TriggerUploadRequest implements Serializable {

    @Expose
    @SerializedName("UploadTriggers")
    private List<UploadTriggersBean> uploadtriggers;
    @Expose
    @SerializedName("BaseData")
    private BasedataModel basedata;

    public List<UploadTriggersBean> getUploadtriggers() {
        return uploadtriggers;
    }

    public void setUploadtriggers(List<UploadTriggersBean> uploadtriggers) {
        this.uploadtriggers = uploadtriggers;
    }

    public BasedataModel getBasedata() {
        return basedata;
    }

    public void setBasedata(BasedataModel basedata) {
        this.basedata = basedata;
    }

    public static class UploadTriggersBean {
        @Expose
        @SerializedName("TriggerName")
        private String triggername;
        @Expose
        @SerializedName("CustomerId")
        private int customerid;

        public String getTriggername() {
            return triggername;
        }

        public void setTriggername(String triggername) {
            this.triggername = triggername;
        }

        public int getCustomerid() {
            return customerid;
        }

        public void setCustomerid(int customerid) {
            this.customerid = customerid;
        }
    }

}
