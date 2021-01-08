package hcl.policing.digitalpolicingplatform.models.process.officer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserRequestModel {

    @Expose
    @SerializedName("ImeiNumbers")
    private List<ImeiNumbersBean> imeinumbers;
    @Expose
    @SerializedName("Email")
    private String email;
    @Expose
    @SerializedName("UserPassword")
    private String userpassword;
    @Expose
    @SerializedName("UserName")
    private String username;
    @Expose
    @SerializedName("Directorate")
    private String directorate;
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
    @SerializedName("Division")
    private String division;
    @Expose
    @SerializedName("District")
    private String district;
    @Expose
    @SerializedName("Department")
    private String department;
    @Expose
    @SerializedName("DateOfJoining")
    private String dateofjoining;
    @Expose
    @SerializedName("DateOfBirth")
    private String dateofbirth;
    @Expose
    @SerializedName("SupForceCode")
    private String supforcecode;
    @Expose
    @SerializedName("Gender")
    private String gender;
    @Expose
    @SerializedName("SupServiceNo")
    private String supserviceno;
    @Expose
    @SerializedName("ForeName")
    private String forename;
    @Expose
    @SerializedName("SurName")
    private String surname;
    @Expose
    @SerializedName("Rank")
    private String rank;
    @Expose
    @SerializedName("Mobile")
    private String mobile;
    @Expose
    @SerializedName("WrntNo")
    private String wrntno;
    @Expose
    @SerializedName("UserType")
    private String usertype;
    @Expose
    @SerializedName("Status")
    private String status;
    @Expose
    @SerializedName("HintAnswer")
    private String hintanswer;
    @Expose
    @SerializedName("HintQuestion")
    private String hintquestion;
    @Expose
    @SerializedName("UserImage")
    private String userimage;
    @Expose
    @SerializedName("CustomerId")
    private int customerid;
    @Expose
    @SerializedName("UserId")
    private int userid;

    public List<ImeiNumbersBean> getImeinumbers() {
        return imeinumbers;
    }

    public void setImeinumbers(List<ImeiNumbersBean> imeinumbers) {
        this.imeinumbers = imeinumbers;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDirectorate() {
        return directorate;
    }

    public void setDirectorate(String directorate) {
        this.directorate = directorate;
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

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDateofjoining() {
        return dateofjoining;
    }

    public void setDateofjoining(String dateofjoining) {
        this.dateofjoining = dateofjoining;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getSupforcecode() {
        return supforcecode;
    }

    public void setSupforcecode(String supforcecode) {
        this.supforcecode = supforcecode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSupserviceno() {
        return supserviceno;
    }

    public void setSupserviceno(String supserviceno) {
        this.supserviceno = supserviceno;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWrntno() {
        return wrntno;
    }

    public void setWrntno(String wrntno) {
        this.wrntno = wrntno;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHintanswer() {
        return hintanswer;
    }

    public void setHintanswer(String hintanswer) {
        this.hintanswer = hintanswer;
    }

    public String getHintquestion() {
        return hintquestion;
    }

    public void setHintquestion(String hintquestion) {
        this.hintquestion = hintquestion;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public static class ImeiNumbersBean {
        @Expose
        @SerializedName("ImeiNumber")
        private String imeinumber;
        @Expose
        @SerializedName("ImeiNumberId")
        private int imeinumberid;

        public String getImeinumber() {
            return imeinumber;
        }

        public void setImeinumber(String imeinumber) {
            this.imeinumber = imeinumber;
        }

        public int getImeinumberid() {
            return imeinumberid;
        }

        public void setImeinumberid(int imeinumberid) {
            this.imeinumberid = imeinumberid;
        }
    }
}
