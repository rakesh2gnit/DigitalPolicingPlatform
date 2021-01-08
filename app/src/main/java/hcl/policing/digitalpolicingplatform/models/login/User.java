package hcl.policing.digitalpolicingplatform.models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    @SerializedName("ADGUID")
    @Expose
    private String aDGUID;
    @SerializedName("Active")
    @Expose
    private Boolean active;
    @SerializedName("Area")
    @Expose
    private String area;
    @SerializedName("AssignedToGroup")
    @Expose
    private Boolean assignedToGroup;
    @SerializedName("AuthTicket")
    @Expose
    private String authTicket;
    @SerializedName("BeatCode")
    @Expose
    private Object beatCode;
    @SerializedName("BeatCodeId")
    @Expose
    private Integer beatCodeId;
    @SerializedName("BeatCodeUid")
    @Expose
    private Object beatCodeUid;
    @SerializedName("CryptoKey")
    @Expose
    private Object cryptoKey;
    @SerializedName("CurrentProcessId")
    @Expose
    private Integer currentProcessId;
    @SerializedName("Department")
    @Expose
    private Object department;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("District")
    @Expose
    private String district;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("ErrorDescription")
    @Expose
    private Object errorDescription;
    @SerializedName("FirstLogin")
    @Expose
    private Boolean firstLogin;
    @SerializedName("ForceCode")
    @Expose
    private String forceCode;
    @SerializedName("ForceId")
    @Expose
    private Integer forceId;
    @SerializedName("GUID")
    @Expose
    private String gUID;
    @SerializedName("Groups")
    @Expose
    private List<Object> groups = null;
    @SerializedName("IsAuthenticated")
    @Expose
    private Boolean isAuthenticated;
    @SerializedName("IsOnDuty")
    @Expose
    private String isOnDuty;
    @SerializedName("Latitude")
    @Expose
    private Object latitude;
    @SerializedName("LockedCount")
    @Expose
    private Integer lockedCount;
    @SerializedName("Longitude")
    @Expose
    private Object longitude;
    @SerializedName("MobileNo")
    @Expose
    private Object mobileNo;
    @SerializedName("Password")
    @Expose
    private Object password;
    @SerializedName("Rank")
    @Expose
    private String rank;
    @SerializedName("Rights")
    @Expose
    private List<Right> rights = null;
    @SerializedName("Station")
    @Expose
    private String station;
    @SerializedName("UserDomain")
    @Expose
    private Object userDomain;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("UserType")
    @Expose
    private Object userType;
    @SerializedName("UserTypeId")
    @Expose
    private Object userTypeId;
    @SerializedName("UserTypeNumber")
    @Expose
    private Integer userTypeNumber;
    @SerializedName("ValidUpto")
    @Expose
    private Integer validUpto;
    @SerializedName("WarrantNo")
    @Expose
    private String warrantNo;

    public String getADGUID() {
        return aDGUID;
    }

    public void setADGUID(String aDGUID) {
        this.aDGUID = aDGUID;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Boolean getAssignedToGroup() {
        return assignedToGroup;
    }

    public void setAssignedToGroup(Boolean assignedToGroup) {
        this.assignedToGroup = assignedToGroup;
    }

    public String getAuthTicket() {
        return authTicket;
    }

    public void setAuthTicket(String authTicket) {
        this.authTicket = authTicket;
    }

    public Object getBeatCode() {
        return beatCode;
    }

    public void setBeatCode(Object beatCode) {
        this.beatCode = beatCode;
    }

    public Integer getBeatCodeId() {
        return beatCodeId;
    }

    public void setBeatCodeId(Integer beatCodeId) {
        this.beatCodeId = beatCodeId;
    }

    public Object getBeatCodeUid() {
        return beatCodeUid;
    }

    public void setBeatCodeUid(Object beatCodeUid) {
        this.beatCodeUid = beatCodeUid;
    }

    public Object getCryptoKey() {
        return cryptoKey;
    }

    public void setCryptoKey(Object cryptoKey) {
        this.cryptoKey = cryptoKey;
    }

    public Integer getCurrentProcessId() {
        return currentProcessId;
    }

    public void setCurrentProcessId(Integer currentProcessId) {
        this.currentProcessId = currentProcessId;
    }

    public Object getDepartment() {
        return department;
    }

    public void setDepartment(Object department) {
        this.department = department;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(Object errorDescription) {
        this.errorDescription = errorDescription;
    }

    public Boolean getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(Boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public String getForceCode() {
        return forceCode;
    }

    public void setForceCode(String forceCode) {
        this.forceCode = forceCode;
    }

    public Integer getForceId() {
        return forceId;
    }

    public void setForceId(Integer forceId) {
        this.forceId = forceId;
    }

    public String getGUID() {
        return gUID;
    }

    public void setGUID(String gUID) {
        this.gUID = gUID;
    }

    public List<Object> getGroups() {
        return groups;
    }

    public void setGroups(List<Object> groups) {
        this.groups = groups;
    }

    public Boolean getIsAuthenticated() {
        return isAuthenticated;
    }

    public void setIsAuthenticated(Boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public String getIsOnDuty() {
        return isOnDuty;
    }

    public void setIsOnDuty(String isOnDuty) {
        this.isOnDuty = isOnDuty;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    public Integer getLockedCount() {
        return lockedCount;
    }

    public void setLockedCount(Integer lockedCount) {
        this.lockedCount = lockedCount;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    public Object getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(Object mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public List<Right> getRights() {
        return rights;
    }

    public void setRights(List<Right> rights) {
        this.rights = rights;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Object getUserDomain() {
        return userDomain;
    }

    public void setUserDomain(Object userDomain) {
        this.userDomain = userDomain;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Object getUserType() {
        return userType;
    }

    public void setUserType(Object userType) {
        this.userType = userType;
    }

    public Object getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Object userTypeId) {
        this.userTypeId = userTypeId;
    }

    public Integer getUserTypeNumber() {
        return userTypeNumber;
    }

    public void setUserTypeNumber(Integer userTypeNumber) {
        this.userTypeNumber = userTypeNumber;
    }

    public Integer getValidUpto() {
        return validUpto;
    }

    public void setValidUpto(Integer validUpto) {
        this.validUpto = validUpto;
    }

    public String getWarrantNo() {
        return warrantNo;
    }

    public void setWarrantNo(String warrantNo) {
        this.warrantNo = warrantNo;
    }

}