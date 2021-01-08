package hcl.policing.digitalpolicingplatform.models.process.fds.address.nflms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressNFLMSResponse {

    @Expose
    @SerializedName("SearchResult")
    private List<SearchResultBean> searchresult;
    @Expose
    @SerializedName("BaseDataContract")
    private BasedatacontractBean basedatacontract;

    public List<SearchResultBean> getSearchresult() {
        return searchresult;
    }

    public void setSearchresult(List<SearchResultBean> searchresult) {
        this.searchresult = searchresult;
    }

    public BasedatacontractBean getBasedatacontract() {
        return basedatacontract;
    }

    public void setBasedatacontract(BasedatacontractBean basedatacontract) {
        this.basedatacontract = basedatacontract;
    }

    public static class SearchResultBean {
        @Expose
        @SerializedName("WeaponType")
        private String weapontype;
        @Expose
        @SerializedName("WeaponHeld")
        private String weaponheld;
        @Expose
        @SerializedName("ValidTo")
        private String validto;
        @Expose
        @SerializedName("ValidFrom")
        private String validfrom;
        @Expose
        @SerializedName("Surname")
        private String surname;
        @Expose
        @SerializedName("Street")
        private String street;
        @Expose
        @SerializedName("Name")
        private String name;
        @Expose
        @SerializedName("LicenceNumber")
        private String licencenumber;
        @Expose
        @SerializedName("HouseNo")
        private String houseno;
        @Expose
        @SerializedName("FirstName")
        private String firstname;
        @Expose
        @SerializedName("DOB")
        private String dob;
        @Expose
        @SerializedName("Address")
        private String address;

        public String getWeapontype() {
            return weapontype;
        }

        public void setWeapontype(String weapontype) {
            this.weapontype = weapontype;
        }

        public String getWeaponheld() {
            return weaponheld;
        }

        public void setWeaponheld(String weaponheld) {
            this.weaponheld = weaponheld;
        }

        public String getValidto() {
            return validto;
        }

        public void setValidto(String validto) {
            this.validto = validto;
        }

        public String getValidfrom() {
            return validfrom;
        }

        public void setValidfrom(String validfrom) {
            this.validfrom = validfrom;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLicencenumber() {
            return licencenumber;
        }

        public void setLicencenumber(String licencenumber) {
            this.licencenumber = licencenumber;
        }

        public String getHouseno() {
            return houseno;
        }

        public void setHouseno(String houseno) {
            this.houseno = houseno;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static class BasedatacontractBean {
        @Expose
        @SerializedName("TotalRecordCount")
        private int totalrecordcount;
        @Expose
        @SerializedName("TotalPageCount")
        private int totalpagecount;
        @Expose
        @SerializedName("SubProcess")
        private String subprocess;
        @Expose
        @SerializedName("PropertyExt3")
        private String propertyext3;
        @Expose
        @SerializedName("Process")
        private String process;
        @Expose
        @SerializedName("CurrentPage")
        private int currentpage;

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

        public String getSubprocess() {
            return subprocess;
        }

        public void setSubprocess(String subprocess) {
            this.subprocess = subprocess;
        }

        public String getPropertyext3() {
            return propertyext3;
        }

        public void setPropertyext3(String propertyext3) {
            this.propertyext3 = propertyext3;
        }

        public String getProcess() {
            return process;
        }

        public void setProcess(String process) {
            this.process = process;
        }

        public int getCurrentpage() {
            return currentpage;
        }

        public void setCurrentpage(int currentpage) {
            this.currentpage = currentpage;
        }
    }
}
