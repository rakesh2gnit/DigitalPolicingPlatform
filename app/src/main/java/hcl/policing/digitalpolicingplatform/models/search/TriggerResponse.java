package hcl.policing.digitalpolicingplatform.models.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TriggerResponse implements Serializable {

    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("successFlag")
    private boolean successflag;
    @Expose
    @SerializedName("triggers")
    private List<TriggersBean> triggers;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getSuccessflag() {
        return successflag;
    }

    public void setSuccessflag(boolean successflag) {
        this.successflag = successflag;
    }

    public List<TriggersBean> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<TriggersBean> triggers) {
        this.triggers = triggers;
    }

    public static class TriggersBean {
        @Expose
        @SerializedName("modifiedDate")
        private String modifieddate;
        @Expose
        @SerializedName("createdDate")
        private String createddate;
        @Expose
        @SerializedName("triggerName")
        private String triggername;
        @Expose
        @SerializedName("subProcessName")
        private String subprocessname;
        @Expose
        @SerializedName("subProcessId")
        private int subprocessid;
        @Expose
        @SerializedName("processName")
        private String processname;
        @Expose
        @SerializedName("processId")
        private int processid;
        @Expose
        @SerializedName("customerId")
        private int customerid;
        @Expose
        @SerializedName("triggerId")
        private int triggerid;

        public String getModifieddate() {
            return modifieddate;
        }

        public void setModifieddate(String modifieddate) {
            this.modifieddate = modifieddate;
        }

        public String getCreateddate() {
            return createddate;
        }

        public void setCreateddate(String createddate) {
            this.createddate = createddate;
        }

        public String getTriggername() {
            return triggername;
        }

        public void setTriggername(String triggername) {
            this.triggername = triggername;
        }

        public String getSubprocessname() {
            return subprocessname;
        }

        public void setSubprocessname(String subprocessname) {
            this.subprocessname = subprocessname;
        }

        public int getSubprocessid() {
            return subprocessid;
        }

        public void setSubprocessid(int subprocessid) {
            this.subprocessid = subprocessid;
        }

        public String getProcessname() {
            return processname;
        }

        public void setProcessname(String processname) {
            this.processname = processname;
        }

        public int getProcessid() {
            return processid;
        }

        public void setProcessid(int processid) {
            this.processid = processid;
        }

        public int getCustomerid() {
            return customerid;
        }

        public void setCustomerid(int customerid) {
            this.customerid = customerid;
        }

        public int getTriggerid() {
            return triggerid;
        }

        public void setTriggerid(int triggerid) {
            this.triggerid = triggerid;
        }
    }

}
