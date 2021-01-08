package hcl.policing.digitalpolicingplatform.prefs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import hcl.policing.digitalpolicingplatform.models.camera.PhotoListModel;
import hcl.policing.digitalpolicingplatform.models.controlPannel.ProcessIconsDTO;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.AnswerValueDTO;
import hcl.policing.digitalpolicingplatform.models.process.ExpectedQuestionListBean;
import hcl.policing.digitalpolicingplatform.models.process.GroupsLogDTO;
import hcl.policing.digitalpolicingplatform.models.process.PageSectionListBean;
import hcl.policing.digitalpolicingplatform.models.process.ProcessLogDTO;
import hcl.policing.digitalpolicingplatform.models.process.SubSectionListBean;
import hcl.policing.digitalpolicingplatform.models.process.crime.AllegationRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.crime.CrimeGroupList;
import hcl.policing.digitalpolicingplatform.models.process.crime.CrimeGroupRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.crime.EventRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.crime.EventSearchList;
import hcl.policing.digitalpolicingplatform.models.process.crime.HOOffenceList;
import hcl.policing.digitalpolicingplatform.models.process.crime.OffenceDefinitionList;
import hcl.policing.digitalpolicingplatform.models.process.crime.OffenceRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.crime.OrgRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.crime.OrganisationDetailsList;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.AddressBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.AddressRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.PersonBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.PersonRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.team.TeamBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.team.TeamRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.VehicleBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.VehicleRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.officer.OfficerBean;
import hcl.policing.digitalpolicingplatform.models.process.officer.OfficerRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.officer.UserBean;
import hcl.policing.digitalpolicingplatform.models.process.officer.UserModel;
import hcl.policing.digitalpolicingplatform.models.process.populate.PopulateListBean;
import hcl.policing.digitalpolicingplatform.models.search.RecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.search.SearchListBean;
import hcl.policing.digitalpolicingplatform.models.tasking.TaskDetailResponseDTO;

public class AppSession implements Serializable {

    //Declaration of variables
    private transient static final String SESSION_NAME = "hcl.policing.digitalpolicingplatform";
    private transient static final String APP_DEFAULT_LANGUAGE = "en";
    private transient SharedPreferences mSharedPreferences;
    private transient SharedPreferences.Editor prefsEditor;

    //Constructor
    @SuppressLint("CommitPrefEdits")
    public AppSession(Context context) {
        mSharedPreferences = context.getSharedPreferences(SESSION_NAME,
                Context.MODE_PRIVATE);
        prefsEditor = mSharedPreferences.edit();
    }


    /**
     * Clear Share preference
     */
    public void clear() {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.clear();
        prefsEditor.apply();
    }

    //FCM
    public String getFCMToken() {
        return mSharedPreferences.getString("FCMToken", "");
    }

    public void setFCMToken(String FCMToken) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("FCMToken", FCMToken);
        prefsEditor.apply();
    }

    public String getForce() {
        return mSharedPreferences.getString("getForce", "");
    }

    public void setForce(String accessToken) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getForce", accessToken);
        prefsEditor.apply();
    }

    public String getPin() {
        return mSharedPreferences.getString("getPin", "");
    }

    public void setDbVersion(int version) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putInt("DBVersion", version);
        prefsEditor.apply();
    }

    public int getDbVersion() {
        return mSharedPreferences.getInt("DBVersion", 0);
    }

    public void setPin(String accessToken) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getPin", accessToken);
        prefsEditor.apply();
    }


    public boolean isLogin() {
        return mSharedPreferences.getBoolean("LoginApi", false);
    }

    public void setLogin(boolean Login) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putBoolean("LoginApi", Login);
        prefsEditor.apply();
    }

    public boolean isPinEnabled() {
        return mSharedPreferences.getBoolean("setPinEnabled", false);
    }

    public void setPinEnabled(boolean Login) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putBoolean("setPinEnabled", Login);
        prefsEditor.apply();
    }

    public boolean isBiometricEnabled() {
        return mSharedPreferences.getBoolean("setBiometricEnabled", false);
    }

    public void setBiometricEnabled(boolean Login) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putBoolean("setBiometricEnabled", Login);
        prefsEditor.apply();
    }

    public boolean isClassicViewEnabled() {
        return mSharedPreferences.getBoolean("setClassicViewEnabled", false);
    }

    public void setClassicViewEnabled(boolean Login) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putBoolean("setClassicViewEnabled", Login);
        prefsEditor.apply();
    }

    public UserBean getUser() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getUser", "");
        Type type = new TypeToken<UserBean>() {
        }.getType();
        UserBean loginDTO = new UserBean();
        loginDTO = gson.fromJson(json, type);
        return loginDTO;
    }

    public void setUser(UserBean loginDTO) {
        Gson gson = new Gson();
        String json = gson.toJson(loginDTO);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getUser", json);
        prefsEditor.apply();
    }

    public UserModel getUserData() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getUserData", "");
        Type type = new TypeToken<UserModel>() {
        }.getType();
        UserModel userModel = new UserModel();
        userModel = gson.fromJson(json, type);
        return userModel;
    }

    public void setUserData(UserModel user) {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getUserData", json);
        prefsEditor.apply();
    }

    public String getAuthToken() {
        return mSharedPreferences.getString("getAuthToken", "");
    }

    public void setAuthToken(String accessToken) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getAuthToken", accessToken);
        prefsEditor.apply();
    }

    public String getUserID() {
        return mSharedPreferences.getString("getUserID", "");
    }

    public void setUserID(String accessToken) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getUserID", accessToken);
        prefsEditor.apply();
    }

    public String getPassword() {
        return mSharedPreferences.getString("getPassword", "");
    }

    public void setPassword(String accessToken) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getPassword", accessToken);
        prefsEditor.apply();
    }

    public String getMasterDataNo() {
        return mSharedPreferences.getString("getMasterDataNo", "");
    }

    public void setMasterDataNo(String accessToken) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getMasterDataNo", accessToken);
        prefsEditor.apply();
    }

    public TaskDetailResponseDTO getTaskDetail() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getTaskDetail", "");
        Type type = new TypeToken<TaskDetailResponseDTO>() {
        }.getType();
        TaskDetailResponseDTO loginDTO = new TaskDetailResponseDTO();
        loginDTO = gson.fromJson(json, type);
        return loginDTO;
    }

    public void setTaskDetail(TaskDetailResponseDTO loginDTO) {
        Gson gson = new Gson();
        String json = gson.toJson(loginDTO);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getTaskDetail", json);
        prefsEditor.apply();
    }

    public ArrayList<ProcessIconsDTO> getShortcutIcons() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getShortcutIcons", "");
        Type type = new TypeToken<ArrayList<ProcessIconsDTO>>() {
        }.getType();
        ArrayList<ProcessIconsDTO> listService = new ArrayList<>();
        listService = gson.fromJson(json, type);
        return listService;
    }

    public void setShortcutIcons(ArrayList<ProcessIconsDTO> listService) {
        Gson gson = new Gson();
        String json = gson.toJson(listService);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getShortcutIcons", json);
        prefsEditor.apply();
    }

    /*public LoginResponse getUser() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getUser", "");
        Type type = new TypeToken<LoginResponse>() {
        }.getType();
        LoginResponse loginDTO = new LoginResponse();
        loginDTO = gson.fromJson(json, type);
        return loginDTO;
    }

    public void setUser(LoginResponse loginDTO) {
        Gson gson = new Gson();
        String json = gson.toJson(loginDTO);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getUser", json);
        prefsEditor.commit();
    }*/

    public List<PageSectionListBean> getSectionList() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getSectionList", "");
        Type type = new TypeToken<List<PageSectionListBean>>() {
        }.getType();
        List<PageSectionListBean> listService = new ArrayList<>();
        listService = gson.fromJson(json, type);
        return listService;
    }

    public void setSectionList(List<PageSectionListBean> listService) {
        Gson gson = new Gson();
        String json = gson.toJson(listService);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getSectionList", json);
        prefsEditor.apply();
    }

    public List<SubSectionListBean> getSubSectionList() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getSubSectionList", "");
        Type type = new TypeToken<List<SubSectionListBean>>() {
        }.getType();
        List<SubSectionListBean> listService = new ArrayList<>();
        listService = gson.fromJson(json, type);
        return listService;
    }

    public void setSubSectionList(List<SubSectionListBean> listService) {
        Gson gson = new Gson();
        String json = gson.toJson(listService);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getSubSectionList", json);
        prefsEditor.apply();
    }

    public List<SearchListBean> getSearchList() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getSearchList", "");
        Type type = new TypeToken<List<SearchListBean>>() {
        }.getType();
        List<SearchListBean> listService = new ArrayList<>();
        listService = gson.fromJson(json, type);
        return listService;
    }

    public void setSearchList(List<SearchListBean> listService) {
        Gson gson = new Gson();
        String json = gson.toJson(listService);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getSearchList", json);
        prefsEditor.apply();
    }

    public List<PopulateListBean> getPopulateList() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getPopulateList", "");
        Type type = new TypeToken<List<PopulateListBean>>() {
        }.getType();
        List<PopulateListBean> listService = new ArrayList<>();
        listService = gson.fromJson(json, type);
        return listService;
    }

    public void setPopulateList(List<PopulateListBean> listService) {
        Gson gson = new Gson();
        String json = gson.toJson(listService);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getPopulateList", json);
        prefsEditor.apply();
    }

    public List<ExpectedQuestionListBean> getExpectedQuestionList() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getExpectedQuestionList", "");
        Type type = new TypeToken<List<ExpectedQuestionListBean>>() {
        }.getType();
        List<ExpectedQuestionListBean> listService = new ArrayList<>();
        listService = gson.fromJson(json, type);
        return listService;
    }

    public void setExpectedQuestionList(List<ExpectedQuestionListBean> listService) {
        Gson gson = new Gson();
        String json = gson.toJson(listService);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getExpectedQuestionList", json);
        prefsEditor.apply();
    }

    public ArrayList<PhotoListModel> getImageList() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getImageList", "");
        Type type = new TypeToken<ArrayList<PhotoListModel>>() {
        }.getType();
        ArrayList<PhotoListModel> listService = new ArrayList<>();
        listService = gson.fromJson(json, type);
        return listService;
    }

    public void setImageList(ArrayList<PhotoListModel> listService) {
        Gson gson = new Gson();
        String json = gson.toJson(listService);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getImageList", json);
        prefsEditor.apply();
    }

    public ArrayList<PhotoListModel> getSignatureList() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getSignatureList", "");
        Type type = new TypeToken<ArrayList<PhotoListModel>>() {
        }.getType();
        ArrayList<PhotoListModel> listService = new ArrayList<>();
        listService = gson.fromJson(json, type);
        return listService;
    }

    public void setSignatureList(ArrayList<PhotoListModel> listService) {
        Gson gson = new Gson();
        String json = gson.toJson(listService);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getSignatureList", json);
        prefsEditor.apply();
    }

    public ArrayList<ProcessLogDTO> getDraftList() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getDraftList", "");
        Type type = new TypeToken<ArrayList<ProcessLogDTO>>() {
        }.getType();
        ArrayList<ProcessLogDTO> listService = new ArrayList<>();
        listService = gson.fromJson(json, type);
        return listService;
    }

    public void setDraftList(ArrayList<ProcessLogDTO> listService) {
        Gson gson = new Gson();
        String json = gson.toJson(listService);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getDraftList", json);
        prefsEditor.apply();
    }

    public ArrayList<ProcessLogDTO> getOfflineList() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getOfflineList", "");
        Type type = new TypeToken<ArrayList<ProcessLogDTO>>() {
        }.getType();
        ArrayList<ProcessLogDTO> listService = new ArrayList<>();
        listService = gson.fromJson(json, type);
        return listService;
    }

    public void setOfflineList(ArrayList<ProcessLogDTO> listService) {
        Gson gson = new Gson();
        String json = gson.toJson(listService);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getOfflineList", json);
        prefsEditor.apply();
    }

    public ArrayList<ProcessLogDTO> getFDSList() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getFDSList", "");
        Type type = new TypeToken<ArrayList<ProcessLogDTO>>() {
        }.getType();
        ArrayList<ProcessLogDTO> listService = new ArrayList<>();
        listService = gson.fromJson(json, type);
        return listService;
    }

    public void setFDSList(ArrayList<ProcessLogDTO> listService) {
        Gson gson = new Gson();
        String json = gson.toJson(listService);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getFDSList", json);
        prefsEditor.apply();
    }

    public ArrayList<GroupsLogDTO> getLogGroupsList() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getLogGroupsList", "");
        Type type = new TypeToken<ArrayList<GroupsLogDTO>>() {
        }.getType();
        ArrayList<GroupsLogDTO> listService = new ArrayList<>();
        listService = gson.fromJson(json, type);
        return listService;
    }

    public void setLogGroupsList(ArrayList<GroupsLogDTO> listService) {
        Gson gson = new Gson();
        String json = gson.toJson(listService);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getLogGroupsList", json);
        prefsEditor.apply();
    }

    //latitude
    public String getLatitude() {
        return mSharedPreferences.getString("getLatitude", "");
    }

    public void setLatitude(String latitude) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getLatitude", latitude);
        prefsEditor.apply();
    }

    //longitude
    public String getLongitude() {
        return mSharedPreferences.getString("getLongitude", "");
    }

    public void setLongitude(String longitude) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getLongitude", longitude);
        prefsEditor.apply();
    }

    //Current latitude
    public String getCurrentLatitude() {
        return mSharedPreferences.getString("getCurrentLatitude", "");
    }

    public void setCurrentLatitude(String latitude) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getCurrentLatitude", latitude);
        prefsEditor.apply();
    }

    //Current longitude
    public String getCurrentLongitude() {
        return mSharedPreferences.getString("getCurrentLongitude", "");
    }

    public void setCurrentLongitude(String longitude) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getCurrentLongitude", longitude);
        prefsEditor.apply();
    }

    public ArrayList<OrgRecentLogsBean> getSearchedOrg() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getSearchedOrg", "");
        Type type = new TypeToken<ArrayList<OrgRecentLogsBean>>() {
        }.getType();
        ArrayList<OrgRecentLogsBean> listService = new ArrayList<>();
        listService = gson.fromJson(json, type);
        return listService;
    }

    public void setSearchedOrg(ArrayList<OrgRecentLogsBean> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getSearchedOrg", json);
        prefsEditor.apply();
    }

    public ArrayList<TeamRecentLogsBean> getSearchedTeam() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getSearchedTeam", "");
        Type type = new TypeToken<ArrayList<TeamRecentLogsBean>>() {
        }.getType();
        ArrayList<TeamRecentLogsBean> listService = new ArrayList<>();
        listService = gson.fromJson(json, type);
        return listService;
    }

    public void setSearchedTeam(ArrayList<TeamRecentLogsBean> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getSearchedTeam", json);
        prefsEditor.apply();
    }

    public ArrayList<EventRecentLogsBean> getSearchedEvent() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getSearchedEvent", "");
        Type type = new TypeToken<ArrayList<EventRecentLogsBean>>() {
        }.getType();
        ArrayList<EventRecentLogsBean> listService = new ArrayList<>();
        listService = gson.fromJson(json, type);
        return listService;
    }

    public void setSearchedEvent(ArrayList<EventRecentLogsBean> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getSearchedEvent", json);
        prefsEditor.apply();
    }

    public ArrayList<AllegationRecentLogsBean> getSearchedAllegation() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getSearchedAllegation", "");
        Type type = new TypeToken<ArrayList<AllegationRecentLogsBean>>() {
        }.getType();
        ArrayList<AllegationRecentLogsBean> listService = new ArrayList<>();
        listService = gson.fromJson(json, type);
        return listService;
    }

    public void setSearchedAllegation(ArrayList<AllegationRecentLogsBean> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getSearchedAllegation", json);
        prefsEditor.apply();
    }

    public ArrayList<OffenceRecentLogsBean> getSearchedOffence() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getSearchedOffence", "");
        Type type = new TypeToken<ArrayList<OffenceRecentLogsBean>>() {
        }.getType();
        ArrayList<OffenceRecentLogsBean> listService = new ArrayList<>();
        listService = gson.fromJson(json, type);
        return listService;
    }

    public void setSearchedOffence(ArrayList<OffenceRecentLogsBean> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getSearchedOffence", json);
        prefsEditor.apply();
    }

    public ArrayList<CrimeGroupRecentLogsBean> getSearchedCrimeGroup() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getSearchedCrimeGroup", "");
        Type type = new TypeToken<ArrayList<CrimeGroupRecentLogsBean>>() {
        }.getType();
        ArrayList<CrimeGroupRecentLogsBean> listService = new ArrayList<>();
        listService = gson.fromJson(json, type);
        return listService;
    }

    public void setSearchedCrimeGroup(ArrayList<CrimeGroupRecentLogsBean> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getSearchedCrimeGroup", json);
        prefsEditor.apply();
    }

    public ArrayList<PersonRecentLogsBean> getRecentSearchedPerson() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getRecentSearchedPerson", "");
        Type type = new TypeToken<ArrayList<PersonRecentLogsBean>>() {
        }.getType();
        ArrayList<PersonRecentLogsBean> listService = new ArrayList<>();
        listService = gson.fromJson(json, type);
        return listService;
    }

    public void setRecentSearchedPerson(ArrayList<PersonRecentLogsBean> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getRecentSearchedPerson", json);
        prefsEditor.apply();
    }

    public ArrayList<VehicleRecentLogsBean> getSearchedVehicle() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getSearchedVehicle", "");
        Type type = new TypeToken<ArrayList<VehicleRecentLogsBean>>() {
        }.getType();
        ArrayList<VehicleRecentLogsBean> listService = new ArrayList<>();
        listService = gson.fromJson(json, type);
        return listService;
    }

    public void setSearchedVehicle(ArrayList<VehicleRecentLogsBean> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getSearchedVehicle", json);
        prefsEditor.apply();
    }

    public ArrayList<AddressRecentLogsBean> getSearchedAddress() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getSearchedAddress", "");
        Type type = new TypeToken<ArrayList<AddressRecentLogsBean>>() {
        }.getType();
        ArrayList<AddressRecentLogsBean> listService = new ArrayList<>();
        listService = gson.fromJson(json, type);
        return listService;
    }

    public void setSearchedAddress(ArrayList<AddressRecentLogsBean> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getSearchedAddress", json);
        prefsEditor.apply();
    }

    public ArrayList<OfficerRecentLogsBean> getSearchedOfficer() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("getSearchedOfficer", "");
        Type type = new TypeToken<ArrayList<OfficerRecentLogsBean>>() {
        }.getType();
        ArrayList<OfficerRecentLogsBean> listService = new ArrayList<>();
        listService = gson.fromJson(json, type);
        return listService;
    }

    public void setSearchedOfficer(ArrayList<OfficerRecentLogsBean> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getSearchedOfficer", json);
        prefsEditor.apply();
    }

    public Uri getImageUri() {
        String imageUri = mSharedPreferences.getString("getImageUri", "");
        if (imageUri.equals(""))
            return null;
        return Uri.parse(imageUri);
    }

    public void setImageUri(Uri imageUri) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getImageUri", imageUri.toString());
        prefsEditor.apply();
    }

    public String getImagePath() {
        return mSharedPreferences.getString("getImagePath", "");
    }

    public void setImagePath(String imagePath) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getImagePath", imagePath);
        prefsEditor.apply();
    }

    public String getCropImagePath() {
        return mSharedPreferences.getString("getCropImagePath", "");
    }

    public void setCropImagePath(String cropImagePath) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getCropImagePath", cropImagePath);
        prefsEditor.apply();
    }
}
