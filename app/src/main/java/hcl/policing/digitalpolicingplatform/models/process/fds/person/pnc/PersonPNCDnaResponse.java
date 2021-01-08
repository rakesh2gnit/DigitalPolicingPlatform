package hcl.policing.digitalpolicingplatform.models.process.fds.person.pnc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonPNCDnaResponse {

    @Expose
    @SerializedName("DNAList")
    private List<DNAListBean> dnalist;

    public List<DNAListBean> getDnalist() {
        return dnalist;
    }

    public void setDnalist(List<DNAListBean> dnalist) {
        this.dnalist = dnalist;
    }

    public static class DNAListBean {
        @Expose
        @SerializedName("SentToLab")
        private String senttolab;
        @Expose
        @SerializedName("SampleType")
        private String sampletype;
        @Expose
        @SerializedName("SampleBarcode")
        private String samplebarcode;
        @Expose
        @SerializedName("FSRef")
        private String fsref;
        @Expose
        @SerializedName("DateOfSample")
        private String dateofsample;
        @Expose
        @SerializedName("DNAStatus")
        private String dnastatus;
        @Expose
        @SerializedName("ASRef")
        private String asref;

        public String getSenttolab() {
            return senttolab;
        }

        public void setSenttolab(String senttolab) {
            this.senttolab = senttolab;
        }

        public String getSampletype() {
            return sampletype;
        }

        public void setSampletype(String sampletype) {
            this.sampletype = sampletype;
        }

        public String getSamplebarcode() {
            return samplebarcode;
        }

        public void setSamplebarcode(String samplebarcode) {
            this.samplebarcode = samplebarcode;
        }

        public String getFsref() {
            return fsref;
        }

        public void setFsref(String fsref) {
            this.fsref = fsref;
        }

        public String getDateofsample() {
            return dateofsample;
        }

        public void setDateofsample(String dateofsample) {
            this.dateofsample = dateofsample;
        }

        public String getDnastatus() {
            return dnastatus;
        }

        public void setDnastatus(String dnastatus) {
            this.dnastatus = dnastatus;
        }

        public String getAsref() {
            return asref;
        }

        public void setAsref(String asref) {
            this.asref = asref;
        }
    }

}
