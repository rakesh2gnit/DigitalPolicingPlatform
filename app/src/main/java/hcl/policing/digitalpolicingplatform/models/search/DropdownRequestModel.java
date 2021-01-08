package hcl.policing.digitalpolicingplatform.models.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import hcl.policing.digitalpolicingplatform.models.login.BasedataModel;

public class DropdownRequestModel {

    @Expose
    @SerializedName("JsonRequest")
    private String jsonrequest;
    @Expose
    @SerializedName("BaseData")
    private BasedataModel basedata;

    public String getJsonrequest() {
        return jsonrequest;
    }

    public void setJsonrequest(String jsonrequest) {
        this.jsonrequest = jsonrequest;
    }

    public BasedataModel getBasedata() {
        return basedata;
    }

    public void setBasedata(BasedataModel basedata) {
        this.basedata = basedata;
    }

    public static class JsonrequestBean {
        @Expose
        @SerializedName("LookUpNames")
        private String lookupnames;
        @Expose
        @SerializedName("LookUpType")
        private String lookuptype;
        @Expose
        @SerializedName("Process")
        private String process;
        @Expose
        @SerializedName("CustomerId")
        private int customerid;

        public String getLookupnames() {
            return lookupnames;
        }

        public void setLookupnames(String lookupnames) {
            this.lookupnames = lookupnames;
        }

        public String getLookuptype() {
            return lookuptype;
        }

        public void setLookuptype(String lookuptype) {
            this.lookuptype = lookuptype;
        }

        public String getProcess() {
            return process;
        }

        public void setProcess(String process) {
            this.process = process;
        }

        public int getCustomerid() {
            return customerid;
        }

        public void setCustomerid(int customerid) {
            this.customerid = customerid;
        }
    }

}
