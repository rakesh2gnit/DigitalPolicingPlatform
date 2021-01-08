package hcl.policing.digitalpolicingplatform.models.process.fds.person.pnc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonPNCPhotoLocResponse {

    @Expose
    @SerializedName("PhotoLocationList")
    private List<PhotoLocationListBean> photolocationlist;

    public List<PhotoLocationListBean> getPhotolocationlist() {
        return photolocationlist;
    }

    public void setPhotolocationlist(List<PhotoLocationListBean> photolocationlist) {
        this.photolocationlist = photolocationlist;
    }

    public static class PhotoLocationListBean {
        @Expose
        @SerializedName("Updated")
        private String updated;
        @Expose
        @SerializedName("RefNumberPhotoID")
        private String refnumberphotoid;
        @Expose
        @SerializedName("Owner")
        private String owner;
        @Expose
        @SerializedName("DateOfCreation")
        private String dateofcreation;
        @Expose
        @SerializedName("Creator")
        private String creator;

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getRefnumberphotoid() {
            return refnumberphotoid;
        }

        public void setRefnumberphotoid(String refnumberphotoid) {
            this.refnumberphotoid = refnumberphotoid;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getDateofcreation() {
            return dateofcreation;
        }

        public void setDateofcreation(String dateofcreation) {
            this.dateofcreation = dateofcreation;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }
    }

}
