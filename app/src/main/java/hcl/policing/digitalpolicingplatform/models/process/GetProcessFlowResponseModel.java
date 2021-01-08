package hcl.policing.digitalpolicingplatform.models.process;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProcessFlowResponseModel {

    @Expose
    @SerializedName("successFlag")
    private boolean successflag;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("processFlow")
    private ProcessflowBean processflow;


    public ProcessflowBean getProcessflow() {
        return processflow;
    }

    public void setProcessflow(ProcessflowBean processflow) {
        this.processflow = processflow;
    }

    public boolean getSuccessflag() {
        return successflag;
    }

    public void setSuccessflag(boolean successflag) {
        this.successflag = successflag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class ProcessflowBean {
        @Expose
        @SerializedName("status")
        private String status;
        @Expose
        @SerializedName("modifiedDate")
        private String modifieddate;
        @Expose
        @SerializedName("createdDate")
        private String createddate;
        @Expose
        @SerializedName("version")
        private int version;
        @Expose
        @SerializedName("processFlowString")
        private String processflowstring;
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
        @SerializedName("processFlowId")
        private int processflowid;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

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

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getProcessflowstring() {
            return processflowstring;
        }

        public void setProcessflowstring(String processflowstring) {
            this.processflowstring = processflowstring;
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

        public int getProcessflowid() {
            return processflowid;
        }

        public void setProcessflowid(int processflowid) {
            this.processflowid = processflowid;
        }
    }
}
