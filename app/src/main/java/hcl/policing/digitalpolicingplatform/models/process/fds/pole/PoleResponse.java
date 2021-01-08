package hcl.policing.digitalpolicingplatform.models.process.fds.pole;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PoleResponse implements Serializable {
    @SerializedName("BaseDataContract")
    @Expose
    private BaseDataContract baseDataContract;
    @SerializedName("POLESearchResponse")
    @Expose
    private POLESearchResponse pOLESearchResponse;

    public BaseDataContract getBaseDataContract() {
        return baseDataContract;
    }

    public void setBaseDataContract(BaseDataContract baseDataContract) {
        this.baseDataContract = baseDataContract;
    }

    public POLESearchResponse getPOLESearchResponse() {
        return pOLESearchResponse;
    }

    public void setPOLESearchResponse(POLESearchResponse pOLESearchResponse) {
        this.pOLESearchResponse = pOLESearchResponse;
    }

    public class BaseDataContract {

        @SerializedName("CollarId")
        @Expose
        private String collarId;
        @SerializedName("ComponentName")
        @Expose
        private String componentName;
        @SerializedName("ComponentVersionNo")
        @Expose
        private String componentVersionNo;
        @SerializedName("ConnectionString")
        @Expose
        private String connectionString;
        @SerializedName("CurrentPage")
        @Expose
        private Integer currentPage;
        @SerializedName("DevicePhoneNo")
        @Expose
        private String devicePhoneNo;
        @SerializedName("DistrictCode")
        @Expose
        private String districtCode;
        @SerializedName("EndPoint")
        @Expose
        private String endPoint;
        @SerializedName("ExternalURL")
        @Expose
        private String externalURL;
        @SerializedName("FDSRequestString")
        @Expose
        private String fDSRequestString;
        @SerializedName("ForceCode")
        @Expose
        private String forceCode;
        @SerializedName("Function")
        @Expose
        private String function;
        @SerializedName("GeoCoordinates")
        @Expose
        private String geoCoordinates;
        @SerializedName("IMEINo")
        @Expose
        private String iMEINo;
        @SerializedName("LayerName")
        @Expose
        private String layerName;
        @SerializedName("Location")
        @Expose
        private String location;
        @SerializedName("PNCDATA")
        @Expose
        private String pNCDATA;
        @SerializedName("Process")
        @Expose
        private String process;
        @SerializedName("ProcessEndTime")
        @Expose
        private String processEndTime;
        @SerializedName("ProcessStartTime")
        @Expose
        private String processStartTime;
        @SerializedName("PropertyExt1")
        @Expose
        private String propertyExt1;
        @SerializedName("PropertyExt2")
        @Expose
        private String propertyExt2;
        @SerializedName("PropertyExt3")
        @Expose
        private String propertyExt3;
        @SerializedName("PropertyExt4")
        @Expose
        private String propertyExt4;
        @SerializedName("PropertyExt5")
        @Expose
        private String propertyExt5;
        @SerializedName("ReasonCode")
        @Expose
        private String reasonCode;
        @SerializedName("Screen")
        @Expose
        private String screen;
        @SerializedName("ScreenName")
        @Expose
        private String screenName;
        @SerializedName("SecurityContext")
        @Expose
        private String securityContext;
        @SerializedName("Session")
        @Expose
        private String session;
        @SerializedName("SessionID")
        @Expose
        private String sessionID;
        @SerializedName("StationCode")
        @Expose
        private String stationCode;
        @SerializedName("SubProcess")
        @Expose
        private String subProcess;
        @SerializedName("SubProcessName")
        @Expose
        private String subProcessName;
        @SerializedName("TotalPageCount")
        @Expose
        private Integer totalPageCount;
        @SerializedName("TotalRecordCount")
        @Expose
        private Integer totalRecordCount;
        @SerializedName("TransactionId")
        @Expose
        private String transactionId;
        @SerializedName("UserName")
        @Expose
        private String userName;

        public String getCollarId() {
            return collarId;
        }

        public void setCollarId(String collarId) {
            this.collarId = collarId;
        }

        public String getComponentName() {
            return componentName;
        }

        public void setComponentName(String componentName) {
            this.componentName = componentName;
        }

        public String getComponentVersionNo() {
            return componentVersionNo;
        }

        public void setComponentVersionNo(String componentVersionNo) {
            this.componentVersionNo = componentVersionNo;
        }

        public String getConnectionString() {
            return connectionString;
        }

        public void setConnectionString(String connectionString) {
            this.connectionString = connectionString;
        }

        public Integer getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(Integer currentPage) {
            this.currentPage = currentPage;
        }

        public String getDevicePhoneNo() {
            return devicePhoneNo;
        }

        public void setDevicePhoneNo(String devicePhoneNo) {
            this.devicePhoneNo = devicePhoneNo;
        }

        public String getDistrictCode() {
            return districtCode;
        }

        public void setDistrictCode(String districtCode) {
            this.districtCode = districtCode;
        }

        public String getEndPoint() {
            return endPoint;
        }

        public void setEndPoint(String endPoint) {
            this.endPoint = endPoint;
        }

        public String getExternalURL() {
            return externalURL;
        }

        public void setExternalURL(String externalURL) {
            this.externalURL = externalURL;
        }

        public String getFDSRequestString() {
            return fDSRequestString;
        }

        public void setFDSRequestString(String fDSRequestString) {
            this.fDSRequestString = fDSRequestString;
        }

        public String getForceCode() {
            return forceCode;
        }

        public void setForceCode(String forceCode) {
            this.forceCode = forceCode;
        }

        public String getFunction() {
            return function;
        }

        public void setFunction(String function) {
            this.function = function;
        }

        public String getGeoCoordinates() {
            return geoCoordinates;
        }

        public void setGeoCoordinates(String geoCoordinates) {
            this.geoCoordinates = geoCoordinates;
        }

        public String getIMEINo() {
            return iMEINo;
        }

        public void setIMEINo(String iMEINo) {
            this.iMEINo = iMEINo;
        }

        public String getLayerName() {
            return layerName;
        }

        public void setLayerName(String layerName) {
            this.layerName = layerName;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getPNCDATA() {
            return pNCDATA;
        }

        public void setPNCDATA(String pNCDATA) {
            this.pNCDATA = pNCDATA;
        }

        public String getProcess() {
            return process;
        }

        public void setProcess(String process) {
            this.process = process;
        }

        public String getProcessEndTime() {
            return processEndTime;
        }

        public void setProcessEndTime(String processEndTime) {
            this.processEndTime = processEndTime;
        }

        public String getProcessStartTime() {
            return processStartTime;
        }

        public void setProcessStartTime(String processStartTime) {
            this.processStartTime = processStartTime;
        }

        public String getPropertyExt1() {
            return propertyExt1;
        }

        public void setPropertyExt1(String propertyExt1) {
            this.propertyExt1 = propertyExt1;
        }

        public String getPropertyExt2() {
            return propertyExt2;
        }

        public void setPropertyExt2(String propertyExt2) {
            this.propertyExt2 = propertyExt2;
        }

        public String getPropertyExt3() {
            return propertyExt3;
        }

        public void setPropertyExt3(String propertyExt3) {
            this.propertyExt3 = propertyExt3;
        }

        public String getPropertyExt4() {
            return propertyExt4;
        }

        public void setPropertyExt4(String propertyExt4) {
            this.propertyExt4 = propertyExt4;
        }

        public String getPropertyExt5() {
            return propertyExt5;
        }

        public void setPropertyExt5(String propertyExt5) {
            this.propertyExt5 = propertyExt5;
        }

        public String getReasonCode() {
            return reasonCode;
        }

        public void setReasonCode(String reasonCode) {
            this.reasonCode = reasonCode;
        }

        public String getScreen() {
            return screen;
        }

        public void setScreen(String screen) {
            this.screen = screen;
        }

        public String getScreenName() {
            return screenName;
        }

        public void setScreenName(String screenName) {
            this.screenName = screenName;
        }

        public String getSecurityContext() {
            return securityContext;
        }

        public void setSecurityContext(String securityContext) {
            this.securityContext = securityContext;
        }

        public String getSession() {
            return session;
        }

        public void setSession(String session) {
            this.session = session;
        }

        public String getSessionID() {
            return sessionID;
        }

        public void setSessionID(String sessionID) {
            this.sessionID = sessionID;
        }

        public String getStationCode() {
            return stationCode;
        }

        public void setStationCode(String stationCode) {
            this.stationCode = stationCode;
        }

        public String getSubProcess() {
            return subProcess;
        }

        public void setSubProcess(String subProcess) {
            this.subProcess = subProcess;
        }

        public String getSubProcessName() {
            return subProcessName;
        }

        public void setSubProcessName(String subProcessName) {
            this.subProcessName = subProcessName;
        }

        public Integer getTotalPageCount() {
            return totalPageCount;
        }

        public void setTotalPageCount(Integer totalPageCount) {
            this.totalPageCount = totalPageCount;
        }

        public Integer getTotalRecordCount() {
            return totalRecordCount;
        }

        public void setTotalRecordCount(Integer totalRecordCount) {
            this.totalRecordCount = totalRecordCount;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

    }

    public class POLESearchResponse {

        @SerializedName("CollarId")
        @Expose
        private String collarId;
        @SerializedName("ComponentName")
        @Expose
        private String componentName;
        @SerializedName("ComponentVersionNo")
        @Expose
        private String componentVersionNo;
        @SerializedName("ConnectionString")
        @Expose
        private String connectionString;
        @SerializedName("CurrentPage")
        @Expose
        private Integer currentPage;
        @SerializedName("DevicePhoneNo")
        @Expose
        private String devicePhoneNo;
        @SerializedName("DistrictCode")
        @Expose
        private String districtCode;
        @SerializedName("EndPoint")
        @Expose
        private String endPoint;
        @SerializedName("ExternalURL")
        @Expose
        private String externalURL;
        @SerializedName("FDSRequestString")
        @Expose
        private String fDSRequestString;
        @SerializedName("ForceCode")
        @Expose
        private String forceCode;
        @SerializedName("Function")
        @Expose
        private String function;
        @SerializedName("GeoCoordinates")
        @Expose
        private String geoCoordinates;
        @SerializedName("IMEINo")
        @Expose
        private String iMEINo;
        @SerializedName("LayerName")
        @Expose
        private String layerName;
        @SerializedName("Location")
        @Expose
        private String location;
        @SerializedName("PNCDATA")
        @Expose
        private String pNCDATA;
        @SerializedName("Process")
        @Expose
        private String process;
        @SerializedName("ProcessEndTime")
        @Expose
        private String processEndTime;
        @SerializedName("ProcessStartTime")
        @Expose
        private String processStartTime;
        @SerializedName("PropertyExt1")
        @Expose
        private String propertyExt1;
        @SerializedName("PropertyExt2")
        @Expose
        private String propertyExt2;
        @SerializedName("PropertyExt3")
        @Expose
        private String propertyExt3;
        @SerializedName("PropertyExt4")
        @Expose
        private String propertyExt4;
        @SerializedName("PropertyExt5")
        @Expose
        private String propertyExt5;
        @SerializedName("ReasonCode")
        @Expose
        private String reasonCode;
        @SerializedName("Screen")
        @Expose
        private String screen;
        @SerializedName("ScreenName")
        @Expose
        private String screenName;
        @SerializedName("SecurityContext")
        @Expose
        private String securityContext;
        @SerializedName("Session")
        @Expose
        private String session;
        @SerializedName("SessionID")
        @Expose
        private String sessionID;
        @SerializedName("StationCode")
        @Expose
        private String stationCode;
        @SerializedName("SubProcess")
        @Expose
        private String subProcess;
        @SerializedName("SubProcessName")
        @Expose
        private String subProcessName;
        @SerializedName("TotalPageCount")
        @Expose
        private Integer totalPageCount;
        @SerializedName("TotalRecordCount")
        @Expose
        private Integer totalRecordCount;
        @SerializedName("TransactionId")
        @Expose
        private String transactionId;
        @SerializedName("UserName")
        @Expose
        private String userName;
        @SerializedName("AddressList")
        @Expose
        private List<AddressList> addressList;
        @SerializedName("CUAddressList")
        @Expose
        private Object cUAddressList;
        @SerializedName("CUPersonList")
        @Expose
        private Object cUPersonList;
        @SerializedName("LDSPersonList")
        @Expose
        private Object lDSPersonList;
        @SerializedName("OPUSAddressList")
        @Expose
        private Object oPUSAddressList;
        @SerializedName("OPUSVehicleList")
        @Expose
        private Object oPUSVehicleList;
        @SerializedName("ObjectDetails")
        @Expose
        private Object objectDetails;
        @SerializedName("ObjectDetailsList")
        @Expose
        private Object objectDetailsList;
        @SerializedName("PersonList")
        @Expose
        private List<PersonList> personList = null;
        @SerializedName("VehicleList")
        @Expose
        private List<VehicleList> vehicleList = null;



        public String getCollarId() {
            return collarId;
        }

        public void setCollarId(String collarId) {
            this.collarId = collarId;
        }

        public String getComponentName() {
            return componentName;
        }

        public void setComponentName(String componentName) {
            this.componentName = componentName;
        }

        public String getComponentVersionNo() {
            return componentVersionNo;
        }

        public void setComponentVersionNo(String componentVersionNo) {
            this.componentVersionNo = componentVersionNo;
        }

        public String getConnectionString() {
            return connectionString;
        }

        public void setConnectionString(String connectionString) {
            this.connectionString = connectionString;
        }

        public Integer getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(Integer currentPage) {
            this.currentPage = currentPage;
        }

        public String getDevicePhoneNo() {
            return devicePhoneNo;
        }

        public void setDevicePhoneNo(String devicePhoneNo) {
            this.devicePhoneNo = devicePhoneNo;
        }

        public String getDistrictCode() {
            return districtCode;
        }

        public void setDistrictCode(String districtCode) {
            this.districtCode = districtCode;
        }

        public String getEndPoint() {
            return endPoint;
        }

        public void setEndPoint(String endPoint) {
            this.endPoint = endPoint;
        }

        public String getExternalURL() {
            return externalURL;
        }

        public void setExternalURL(String externalURL) {
            this.externalURL = externalURL;
        }

        public String getFDSRequestString() {
            return fDSRequestString;
        }

        public void setFDSRequestString(String fDSRequestString) {
            this.fDSRequestString = fDSRequestString;
        }

        public String getForceCode() {
            return forceCode;
        }

        public void setForceCode(String forceCode) {
            this.forceCode = forceCode;
        }

        public String getFunction() {
            return function;
        }

        public void setFunction(String function) {
            this.function = function;
        }

        public String getGeoCoordinates() {
            return geoCoordinates;
        }

        public void setGeoCoordinates(String geoCoordinates) {
            this.geoCoordinates = geoCoordinates;
        }

        public String getIMEINo() {
            return iMEINo;
        }

        public void setIMEINo(String iMEINo) {
            this.iMEINo = iMEINo;
        }

        public String getLayerName() {
            return layerName;
        }

        public void setLayerName(String layerName) {
            this.layerName = layerName;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getPNCDATA() {
            return pNCDATA;
        }

        public void setPNCDATA(String pNCDATA) {
            this.pNCDATA = pNCDATA;
        }

        public String getProcess() {
            return process;
        }

        public void setProcess(String process) {
            this.process = process;
        }

        public String getProcessEndTime() {
            return processEndTime;
        }

        public void setProcessEndTime(String processEndTime) {
            this.processEndTime = processEndTime;
        }

        public String getProcessStartTime() {
            return processStartTime;
        }

        public void setProcessStartTime(String processStartTime) {
            this.processStartTime = processStartTime;
        }

        public String getPropertyExt1() {
            return propertyExt1;
        }

        public void setPropertyExt1(String propertyExt1) {
            this.propertyExt1 = propertyExt1;
        }

        public String getPropertyExt2() {
            return propertyExt2;
        }

        public void setPropertyExt2(String propertyExt2) {
            this.propertyExt2 = propertyExt2;
        }

        public String getPropertyExt3() {
            return propertyExt3;
        }

        public void setPropertyExt3(String propertyExt3) {
            this.propertyExt3 = propertyExt3;
        }

        public String getPropertyExt4() {
            return propertyExt4;
        }

        public void setPropertyExt4(String propertyExt4) {
            this.propertyExt4 = propertyExt4;
        }

        public String getPropertyExt5() {
            return propertyExt5;
        }

        public void setPropertyExt5(String propertyExt5) {
            this.propertyExt5 = propertyExt5;
        }

        public String getReasonCode() {
            return reasonCode;
        }

        public void setReasonCode(String reasonCode) {
            this.reasonCode = reasonCode;
        }

        public String getScreen() {
            return screen;
        }

        public void setScreen(String screen) {
            this.screen = screen;
        }

        public String getScreenName() {
            return screenName;
        }

        public void setScreenName(String screenName) {
            this.screenName = screenName;
        }

        public String getSecurityContext() {
            return securityContext;
        }

        public void setSecurityContext(String securityContext) {
            this.securityContext = securityContext;
        }

        public String getSession() {
            return session;
        }

        public void setSession(String session) {
            this.session = session;
        }

        public String getSessionID() {
            return sessionID;
        }

        public void setSessionID(String sessionID) {
            this.sessionID = sessionID;
        }

        public String getStationCode() {
            return stationCode;
        }

        public void setStationCode(String stationCode) {
            this.stationCode = stationCode;
        }

        public String getSubProcess() {
            return subProcess;
        }

        public void setSubProcess(String subProcess) {
            this.subProcess = subProcess;
        }

        public String getSubProcessName() {
            return subProcessName;
        }

        public void setSubProcessName(String subProcessName) {
            this.subProcessName = subProcessName;
        }

        public Integer getTotalPageCount() {
            return totalPageCount;
        }

        public void setTotalPageCount(Integer totalPageCount) {
            this.totalPageCount = totalPageCount;
        }

        public Integer getTotalRecordCount() {
            return totalRecordCount;
        }

        public void setTotalRecordCount(Integer totalRecordCount) {
            this.totalRecordCount = totalRecordCount;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public List<AddressList> getAddressList() {
            return addressList;
        }

        public void setAddressList(List<AddressList> addressList) {
            this.addressList = addressList;
        }

        public Object getCUAddressList() {
            return cUAddressList;
        }

        public void setCUAddressList(Object cUAddressList) {
            this.cUAddressList = cUAddressList;
        }

        public Object getCUPersonList() {
            return cUPersonList;
        }

        public void setCUPersonList(Object cUPersonList) {
            this.cUPersonList = cUPersonList;
        }

        public Object getLDSPersonList() {
            return lDSPersonList;
        }

        public void setLDSPersonList(Object lDSPersonList) {
            this.lDSPersonList = lDSPersonList;
        }

        public Object getOPUSAddressList() {
            return oPUSAddressList;
        }

        public void setOPUSAddressList(Object oPUSAddressList) {
            this.oPUSAddressList = oPUSAddressList;
        }

        public Object getOPUSVehicleList() {
            return oPUSVehicleList;
        }

        public void setOPUSVehicleList(Object oPUSVehicleList) {
            this.oPUSVehicleList = oPUSVehicleList;
        }

        public Object getObjectDetails() {
            return objectDetails;
        }

        public void setObjectDetails(Object objectDetails) {
            this.objectDetails = objectDetails;
        }

        public Object getObjectDetailsList() {
            return objectDetailsList;
        }

        public void setObjectDetailsList(Object objectDetailsList) {
            this.objectDetailsList = objectDetailsList;
        }

        public List<PersonList> getPersonList() {
            return personList;
        }

        public void setPersonList(List<PersonList> personList) {
            this.personList = personList;
        }

        public List<VehicleList> getVehicleList() {
            return vehicleList;
        }

        public void setVehicleList(List<VehicleList> vehicleList) {
            this.vehicleList = vehicleList;
        }

    }

}