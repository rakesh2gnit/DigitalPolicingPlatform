package hcl.policing.digitalpolicingplatform.models.process.fds.person.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AthenaLinkedPropertyDetailsModel {

    @Expose
    @SerializedName("PropertyItemsDetailResponse")
    private PropertyitemsdetailresponseBean propertyitemsdetailresponse;

    public PropertyitemsdetailresponseBean getPropertyitemsdetailresponse() {
        return propertyitemsdetailresponse;
    }

    public void setPropertyitemsdetailresponse(PropertyitemsdetailresponseBean propertyitemsdetailresponse) {
        this.propertyitemsdetailresponse = propertyitemsdetailresponse;
    }

    public static class PropertyitemsdetailresponseBean {
        @Expose
        @SerializedName("SubCategory")
        private String subcategory;
        @Expose
        @SerializedName("Status")
        private String status;
        @Expose
        @SerializedName("SecureData")
        private boolean securedata;
        @Expose
        @SerializedName("SecondColour")
        private String secondcolour;
        @Expose
        @SerializedName("ReferenceNo")
        private String referenceno;
        @Expose
        @SerializedName("OvertFlag")
        private boolean overtflag;
        @Expose
        @SerializedName("Model")
        private String model;
        @Expose
        @SerializedName("Manufacturer")
        private String manufacturer;
        @Expose
        @SerializedName("Item")
        private String item;
        @Expose
        @SerializedName("ID")
        private String id;
        @Expose
        @SerializedName("FlagMessage")
        private String flagmessage;
        @Expose
        @SerializedName("FlagDescription")
        private String flagdescription;
        @Expose
        @SerializedName("FirstColour")
        private String firstcolour;
        @Expose
        @SerializedName("Description")
        private String description;
        @Expose
        @SerializedName("Category")
        private String category;
        @Expose
        @SerializedName("TotalRecordCount")
        private int totalrecordcount;
        @Expose
        @SerializedName("TotalPageCount")
        private int totalpagecount;
        @Expose
        @SerializedName("CurrentPage")
        private int currentpage;

        public String getSubcategory() {
            return subcategory;
        }

        public void setSubcategory(String subcategory) {
            this.subcategory = subcategory;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public boolean getSecuredata() {
            return securedata;
        }

        public void setSecuredata(boolean securedata) {
            this.securedata = securedata;
        }

        public String getSecondcolour() {
            return secondcolour;
        }

        public void setSecondcolour(String secondcolour) {
            this.secondcolour = secondcolour;
        }

        public String getReferenceno() {
            return referenceno;
        }

        public void setReferenceno(String referenceno) {
            this.referenceno = referenceno;
        }

        public boolean getOvertflag() {
            return overtflag;
        }

        public void setOvertflag(boolean overtflag) {
            this.overtflag = overtflag;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFlagmessage() {
            return flagmessage;
        }

        public void setFlagmessage(String flagmessage) {
            this.flagmessage = flagmessage;
        }

        public String getFlagdescription() {
            return flagdescription;
        }

        public void setFlagdescription(String flagdescription) {
            this.flagdescription = flagdescription;
        }

        public String getFirstcolour() {
            return firstcolour;
        }

        public void setFirstcolour(String firstcolour) {
            this.firstcolour = firstcolour;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getTotalrecordcount() {
            return totalrecordcount;
        }

        public void setTotalrecordcount(int totalrecordcount) {
            this.totalrecordcount = totalrecordcount;
        }

        public int getTotalpagecount() {
            return totalpagecount;
        }

        public void setTotalpagecount(int totalpagecount) {
            this.totalpagecount = totalpagecount;
        }

        public int getCurrentpage() {
            return currentpage;
        }

        public void setCurrentpage(int currentpage) {
            this.currentpage = currentpage;
        }
    }
}

