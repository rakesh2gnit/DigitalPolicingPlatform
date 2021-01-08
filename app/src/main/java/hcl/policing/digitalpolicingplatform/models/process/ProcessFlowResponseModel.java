package hcl.policing.digitalpolicingplatform.models.process;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProcessFlowResponseModel {

    @Expose
    @SerializedName("successFlag")
    private boolean successflag;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("processFlow")
    private ProcessFlowModel processflow;

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

    public ProcessFlowModel getProcessflow() {
        return processflow;
    }

    public void setProcessflow(ProcessFlowModel processflow) {
        this.processflow = processflow;
    }

}
