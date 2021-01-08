package hcl.policing.digitalpolicingplatform.models.process.fds.address.qas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import hcl.policing.digitalpolicingplatform.models.process.fds.person.qas.AddressdetailsModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.qas.CitizensModel;

public class AddressQASDetailResponse {

    @Expose
    @SerializedName("Addresses")
    private List<AddressdetailsModel> addresses;
    @Expose
    @SerializedName("Citizens")
    private List<CitizensModel> citizens;

    public List<CitizensModel> getCitizens() {
        return citizens;
    }

    public void setCitizens(List<CitizensModel> citizens) {
        this.citizens = citizens;
    }


    public List<AddressdetailsModel> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressdetailsModel> addresses) {
        this.addresses = addresses;
    }
}
