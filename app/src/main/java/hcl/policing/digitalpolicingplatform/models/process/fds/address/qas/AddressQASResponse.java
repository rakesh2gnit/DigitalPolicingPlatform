package hcl.policing.digitalpolicingplatform.models.process.fds.address.qas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import hcl.policing.digitalpolicingplatform.models.process.fds.person.qas.AddressdetailsModel;

public class AddressQASResponse {

    @Expose
    @SerializedName("Addresses")
    private List<AddressdetailsModel> addresses;

    public List<AddressdetailsModel> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressdetailsModel> addresses) {
        this.addresses = addresses;
    }
}
