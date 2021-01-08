package hcl.policing.digitalpolicingplatform.models.process.fds.person.nflms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NflmsDetailsResponseModel {

    @Expose
    @SerializedName("LstWeapon")
    private List<LstWeaponBean> lstweapon;
    @Expose
    @SerializedName("LstPerson")
    private List<LstPersonBean> lstperson;
    @Expose
    @SerializedName("LstAddress")
    private List<LstAddressBean> lstaddress;

    public List<LstWeaponBean> getLstweapon() {
        return lstweapon;
    }

    public void setLstweapon(List<LstWeaponBean> lstweapon) {
        this.lstweapon = lstweapon;
    }

    public List<LstPersonBean> getLstperson() {
        return lstperson;
    }

    public void setLstperson(List<LstPersonBean> lstperson) {
        this.lstperson = lstperson;
    }

    public List<LstAddressBean> getLstaddress() {
        return lstaddress;
    }

    public void setLstaddress(List<LstAddressBean> lstaddress) {
        this.lstaddress = lstaddress;
    }

    public static class LstWeaponBean {
        @Expose
        @SerializedName("WeaponTypeDescription")
        private String weapontypedescription;
        @Expose
        @SerializedName("WeaponLocation")
        private String weaponlocation;
        @Expose
        @SerializedName("ValidFrom")
        private String validfrom;
        @Expose
        @SerializedName("Status")
        private String status;
        @Expose
        @SerializedName("SerialNumber")
        private String serialnumber;
        @Expose
        @SerializedName("SecurityTypeDescription")
        private String securitytypedescription;
        @Expose
        @SerializedName("ManufacturerDescription")
        private String manufacturerdescription;
        @Expose
        @SerializedName("ExpiryDate")
        private String expirydate;
        @Expose
        @SerializedName("CertificateType")
        private String certificatetype;
        @Expose
        @SerializedName("CertificateNumber")
        private String certificatenumber;
        @Expose
        @SerializedName("CalliberDescription")
        private String calliberdescription;
        @Expose
        @SerializedName("ActionDescription")
        private String actiondescription;

        public String getWeapontypedescription() {
            return weapontypedescription;
        }

        public void setWeapontypedescription(String weapontypedescription) {
            this.weapontypedescription = weapontypedescription;
        }

        public String getWeaponlocation() {
            return weaponlocation;
        }

        public void setWeaponlocation(String weaponlocation) {
            this.weaponlocation = weaponlocation;
        }

        public String getValidfrom() {
            return validfrom;
        }

        public void setValidfrom(String validfrom) {
            this.validfrom = validfrom;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSerialnumber() {
            return serialnumber;
        }

        public void setSerialnumber(String serialnumber) {
            this.serialnumber = serialnumber;
        }

        public String getSecuritytypedescription() {
            return securitytypedescription;
        }

        public void setSecuritytypedescription(String securitytypedescription) {
            this.securitytypedescription = securitytypedescription;
        }

        public String getManufacturerdescription() {
            return manufacturerdescription;
        }

        public void setManufacturerdescription(String manufacturerdescription) {
            this.manufacturerdescription = manufacturerdescription;
        }

        public String getExpirydate() {
            return expirydate;
        }

        public void setExpirydate(String expirydate) {
            this.expirydate = expirydate;
        }

        public String getCertificatetype() {
            return certificatetype;
        }

        public void setCertificatetype(String certificatetype) {
            this.certificatetype = certificatetype;
        }

        public String getCertificatenumber() {
            return certificatenumber;
        }

        public void setCertificatenumber(String certificatenumber) {
            this.certificatenumber = certificatenumber;
        }

        public String getCalliberdescription() {
            return calliberdescription;
        }

        public void setCalliberdescription(String calliberdescription) {
            this.calliberdescription = calliberdescription;
        }

        public String getActiondescription() {
            return actiondescription;
        }

        public void setActiondescription(String actiondescription) {
            this.actiondescription = actiondescription;
        }
    }

    public static class LstPersonBean {
        @Expose
        @SerializedName("TelephoneNumber")
        private String telephonenumber;
        @Expose
        @SerializedName("SurName")
        private String surname;
        @Expose
        @SerializedName("PersonNumber")
        private String personnumber;
        @Expose
        @SerializedName("PNC_ID")
        private String pncId;
        @Expose
        @SerializedName("Height")
        private String height;
        @Expose
        @SerializedName("ForeName")
        private String forename;
        @Expose
        @SerializedName("CompanyName")
        private String companyname;
        @Expose
        @SerializedName("BirthDate")
        private String birthdate;

        public String getTelephonenumber() {
            return telephonenumber;
        }

        public void setTelephonenumber(String telephonenumber) {
            this.telephonenumber = telephonenumber;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getPersonnumber() {
            return personnumber;
        }

        public void setPersonnumber(String personnumber) {
            this.personnumber = personnumber;
        }

        public String getPncId() {
            return pncId;
        }

        public void setPncId(String pncId) {
            this.pncId = pncId;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getForename() {
            return forename;
        }

        public void setForename(String forename) {
            this.forename = forename;
        }

        public String getCompanyname() {
            return companyname;
        }

        public void setCompanyname(String companyname) {
            this.companyname = companyname;
        }

        public String getBirthdate() {
            return birthdate;
        }

        public void setBirthdate(String birthdate) {
            this.birthdate = birthdate;
        }
    }

    public static class LstAddressBean {
        @Expose
        @SerializedName("UPRN")
        private String uprn;
        @Expose
        @SerializedName("TelephoneNumber")
        private String telephonenumber;
        @Expose
        @SerializedName("SecurityTypeDescription")
        private String securitytypedescription;
        @Expose
        @SerializedName("Postcode")
        private String postcode;
        @Expose
        @SerializedName("PersonNumber")
        private String personnumber;
        @Expose
        @SerializedName("GridReference")
        private String gridreference;
        @Expose
        @SerializedName("ContactCharacteristic_ID")
        private String contactcharacteristicId;
        @Expose
        @SerializedName("Blue8Reference")
        private String blue8reference;
        @Expose
        @SerializedName("AddressType_ID")
        private String addresstypeId;
        @Expose
        @SerializedName("AddressLine6")
        private String addressline6;
        @Expose
        @SerializedName("AddressLine5")
        private String addressline5;
        @Expose
        @SerializedName("AddressLine4")
        private String addressline4;
        @Expose
        @SerializedName("AddressLine3")
        private String addressline3;
        @Expose
        @SerializedName("AddressLine2")
        private String addressline2;
        @Expose
        @SerializedName("AddressLine1")
        private String addressline1;
        @Expose
        @SerializedName("AddressID")
        private String addressid;

        public String getUprn() {
            return uprn;
        }

        public void setUprn(String uprn) {
            this.uprn = uprn;
        }

        public String getTelephonenumber() {
            return telephonenumber;
        }

        public void setTelephonenumber(String telephonenumber) {
            this.telephonenumber = telephonenumber;
        }

        public String getSecuritytypedescription() {
            return securitytypedescription;
        }

        public void setSecuritytypedescription(String securitytypedescription) {
            this.securitytypedescription = securitytypedescription;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getPersonnumber() {
            return personnumber;
        }

        public void setPersonnumber(String personnumber) {
            this.personnumber = personnumber;
        }

        public String getGridreference() {
            return gridreference;
        }

        public void setGridreference(String gridreference) {
            this.gridreference = gridreference;
        }

        public String getContactcharacteristicId() {
            return contactcharacteristicId;
        }

        public void setContactcharacteristicId(String contactcharacteristicId) {
            this.contactcharacteristicId = contactcharacteristicId;
        }

        public String getBlue8reference() {
            return blue8reference;
        }

        public void setBlue8reference(String blue8reference) {
            this.blue8reference = blue8reference;
        }

        public String getAddresstypeId() {
            return addresstypeId;
        }

        public void setAddresstypeId(String addresstypeId) {
            this.addresstypeId = addresstypeId;
        }

        public String getAddressline6() {
            return addressline6;
        }

        public void setAddressline6(String addressline6) {
            this.addressline6 = addressline6;
        }

        public String getAddressline5() {
            return addressline5;
        }

        public void setAddressline5(String addressline5) {
            this.addressline5 = addressline5;
        }

        public String getAddressline4() {
            return addressline4;
        }

        public void setAddressline4(String addressline4) {
            this.addressline4 = addressline4;
        }

        public String getAddressline3() {
            return addressline3;
        }

        public void setAddressline3(String addressline3) {
            this.addressline3 = addressline3;
        }

        public String getAddressline2() {
            return addressline2;
        }

        public void setAddressline2(String addressline2) {
            this.addressline2 = addressline2;
        }

        public String getAddressline1() {
            return addressline1;
        }

        public void setAddressline1(String addressline1) {
            this.addressline1 = addressline1;
        }

        public String getAddressid() {
            return addressid;
        }

        public void setAddressid(String addressid) {
            this.addressid = addressid;
        }
    }
}
