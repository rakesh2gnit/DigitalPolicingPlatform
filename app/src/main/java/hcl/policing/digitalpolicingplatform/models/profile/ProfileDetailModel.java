package hcl.policing.digitalpolicingplatform.models.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProfileDetailModel implements Serializable {

    @Expose
    @SerializedName("ProfileDetails")
    private ProfiledetailsBean profiledetails;

    public ProfiledetailsBean getProfiledetails() {
        return profiledetails;
    }

    public void setProfiledetails(ProfiledetailsBean profiledetails) {
        this.profiledetails = profiledetails;
    }

    public static class ProfiledetailsBean {
        @Expose
        @SerializedName("OrganisationalDetails")
        private OrganisationaldetailsBean organisationaldetails;
        @Expose
        @SerializedName("BasicDetails")
        private BasicdetailsBean basicdetails;
        @Expose
        @SerializedName("ContactDetails")
        private ContactdetailsBean contactdetails;
        @Expose
        @SerializedName("Rank")
        private String rank;
        @Expose
        @SerializedName("Name")
        private String name;

        public OrganisationaldetailsBean getOrganisationaldetails() {
            return organisationaldetails;
        }

        public void setOrganisationaldetails(OrganisationaldetailsBean organisationaldetails) {
            this.organisationaldetails = organisationaldetails;
        }

        public BasicdetailsBean getBasicdetails() {
            return basicdetails;
        }

        public void setBasicdetails(BasicdetailsBean basicdetails) {
            this.basicdetails = basicdetails;
        }

        public ContactdetailsBean getContactdetails() {
            return contactdetails;
        }

        public void setContactdetails(ContactdetailsBean contactdetails) {
            this.contactdetails = contactdetails;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class OrganisationaldetailsBean {
        @Expose
        @SerializedName("Location")
        private String location;
        @Expose
        @SerializedName("Team")
        private String team;
        @Expose
        @SerializedName("Station")
        private String station;
        @Expose
        @SerializedName("Area")
        private String area;
        @Expose
        @SerializedName("Department")
        private String department;
        @Expose
        @SerializedName("Division")
        private String division;
        @Expose
        @SerializedName("Directorate")
        private String directorate;
        @Expose
        @SerializedName("SupervisorCollarNumber")
        private String supervisorcollarnumber;
        @Expose
        @SerializedName("JoiningDate")
        private String joiningdate;

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getTeam() {
            return team;
        }

        public void setTeam(String team) {
            this.team = team;
        }

        public String getStation() {
            return station;
        }

        public void setStation(String station) {
            this.station = station;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getDivision() {
            return division;
        }

        public void setDivision(String division) {
            this.division = division;
        }

        public String getDirectorate() {
            return directorate;
        }

        public void setDirectorate(String directorate) {
            this.directorate = directorate;
        }

        public String getSupervisorcollarnumber() {
            return supervisorcollarnumber;
        }

        public void setSupervisorcollarnumber(String supervisorcollarnumber) {
            this.supervisorcollarnumber = supervisorcollarnumber;
        }

        public String getJoiningdate() {
            return joiningdate;
        }

        public void setJoiningdate(String joiningdate) {
            this.joiningdate = joiningdate;
        }
    }

    public static class BasicdetailsBean {
        @Expose
        @SerializedName("CollarId")
        private String collarid;
        @Expose
        @SerializedName("UserId")
        private String userid;
        @Expose
        @SerializedName("DOB")
        private String dob;
        @Expose
        @SerializedName("Gender")
        private String gender;

        public String getCollarid() {
            return collarid;
        }

        public void setCollarid(String collarid) {
            this.collarid = collarid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }

    public static class ContactdetailsBean {
        @Expose
        @SerializedName("Mobile")
        private String mobile;
        @Expose
        @SerializedName("Email")
        private String email;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
