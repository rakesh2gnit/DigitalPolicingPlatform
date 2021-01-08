package hcl.policing.digitalpolicingplatform.models.process.fds.person.dl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonDLResponse {

    @Expose
    @SerializedName("DrivingLicenceByNameList")
    private List<DrivingLicenceByNameListBean> drivingLicenceByNameListBeans;


    public List<DrivingLicenceByNameListBean> getDrivingLicenceByNameListBeans() {
        return drivingLicenceByNameListBeans;
    }

    public void setDrivingLicenceByNameListBeans(List<DrivingLicenceByNameListBean> drivingLicenceByNameListBeans) {
        this.drivingLicenceByNameListBeans = drivingLicenceByNameListBeans;
    }


    public static class DrivingLicenceByNameListBean {
        @Expose
        @SerializedName("Gender")
        private String gender;
        @Expose
        @SerializedName("DOB")
        private String dob;
        @Expose
        @SerializedName("Name")
        private String name;
        @Expose
        @SerializedName("PlaceOfBirth")
        private String placeofbirth;
        @Expose
        @SerializedName("Postcode")
        private String postcode;

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPlaceofbirth() {
            return placeofbirth;
        }

        public void setPlaceofbirth(String placeofbirth) {
            this.placeofbirth = placeofbirth;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }


    }
}

