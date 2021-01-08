package hcl.policing.digitalpolicingplatform.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.controlPannel.ClassicControlPanelActivity;
import hcl.policing.digitalpolicingplatform.activities.controlPannel.ControlPanelActivity;
import hcl.policing.digitalpolicingplatform.activities.fdssearch.FDSSearchActivity;
import hcl.policing.digitalpolicingplatform.activities.login.LoginActivity;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.constants.api.ApiConstants;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.database.DatabaseHelper;
import hcl.policing.digitalpolicingplatform.database.ProcessFlowMapper;
import hcl.policing.digitalpolicingplatform.database.ProcessSubProcessMapper;
import hcl.policing.digitalpolicingplatform.listeners.dialog.LoginDialogListener;
import hcl.policing.digitalpolicingplatform.models.login.Authenticationresponse;
import hcl.policing.digitalpolicingplatform.models.login.BasedataModel;
import hcl.policing.digitalpolicingplatform.models.login.LoginRequestDTO;
import hcl.policing.digitalpolicingplatform.models.login.LoginResponseDTO;
import hcl.policing.digitalpolicingplatform.models.masterdata.GetMasterDataResponse;
import hcl.policing.digitalpolicingplatform.models.process.ProcessFlowRequestModel;
import hcl.policing.digitalpolicingplatform.models.process.ResponseModel;
import hcl.policing.digitalpolicingplatform.models.process.officer.UserBean;
import hcl.policing.digitalpolicingplatform.models.process.officer.UserModel;
import hcl.policing.digitalpolicingplatform.models.search.DropdownListResponse;
import hcl.policing.digitalpolicingplatform.models.search.DropdownRequestModel;
import hcl.policing.digitalpolicingplatform.models.search.TriggerRequestModel;
import hcl.policing.digitalpolicingplatform.models.search.TriggerResponse;
import hcl.policing.digitalpolicingplatform.networks.login.LoginApi;
import hcl.policing.digitalpolicingplatform.networks.process.GetProcessFlowApi;
import hcl.policing.digitalpolicingplatform.networks.search.DropdownApi;
import hcl.policing.digitalpolicingplatform.networks.search.GetAllTriggersApi;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class BaseActivity extends AppCompatActivity {

    Context mContext;
    AppSession appSession = null;
    protected static int AUTHENTICATION_REQUEST_CODE = 1000;
    public Dialog mProgressDialog;
    private LoginResponseDTO loginResponseDTO;
    public DatabaseHelper db;
    public UserModel userModel;
    public String jsonFlow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        appSession = new AppSession(this);
        db = new DatabaseHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        userModel = appSession.getUserData();
        if (!(mContext instanceof LoginActivity)) {
            if (!BasicMethodsUtil.getInstance().isSessionActive(mContext)) {
                BasicMethodsUtil.getInstance().showToast(mContext, getString(R.string.session_expired));
                appSession.setLogin(GenericConstant.LOGIN_NO);
                showLoginDialog();
//            startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class), AUTHENTICATION_REQUEST_CODE);
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        appSession.setLogin(GenericConstant.LOGIN_NO);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTHENTICATION_REQUEST_CODE) {
        }
    }

    /**
     * Show Login Dialog
     */
    private void showLoginDialog() {

        DialogUtil.loginDialog(mContext, new LoginDialogListener() {
            @Override
            public void loginClick(String username, String password) {
//                BasicMethodsUtil.getInstance().showToast(mContext, username + " and " + password);
                login(username, password);
            }
        });
    }

    /**
     * Call method for login with respective credentials
     *
     * @param userId
     * @param pass
     */
    private void login(String userId, String pass) {
        try {
            if (!TextUtils.isEmpty(userId) || !TextUtils.isEmpty(pass)) {

                if (Utilities.getInstance(this).isNetworkAvailable()) {
                    mProgressDialog = DialogUtil.showProgressDialog(mContext);
                    appSession.setUserID(userId);
                    appSession.setPassword(pass);
                    LoginRequestDTO loginRequestDTO = getLoginRequest(userId, pass);
                    loginResponseDTO = new LoginApi().callLogin(BaseActivity.this, loginRequestDTO);
                } else {
                    BasicMethodsUtil.getInstance().showToast(mContext, getResources().getString(R.string.no_network));
                }
            } else {
                BasicMethodsUtil.getInstance().showToast(mContext, getResources().getString(R.string.enter_credientials));
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), LoginActivity.class, "login");
        }
    }

    /**
     * Create login request
     *
     * @param username
     * @param password
     * @return
     */
    public LoginRequestDTO getLoginRequest(String username, String password) {

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        BasedataModel mBasedataBean = new BasedataModel();
        mBasedataBean.setCollarid(username);
        mBasedataBean.setCustomercode(ApiConstants.CUSTOMER_CODE_HCL);
        mBasedataBean.setProcess(ApiConstants.PROCESS_ACCOUNT);
        mBasedataBean.setSubprocessname(ApiConstants.SUB_PROCESS_READ);
        mBasedataBean.setFunction(ApiConstants.METHOD_SIGN_IN);
        loginRequestDTO.setBasedata(mBasedataBean);
        loginRequestDTO.setUsername(username);
        loginRequestDTO.setPassword(password);
        return loginRequestDTO;
    }


    /**
     * LoginApi flow
     */
    public void loginFlow() {
        if (appSession.isClassicViewEnabled()) {
            Intent intent = new Intent(mContext, ClassicControlPanelActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            finish();
        } else {
            Intent intent = new Intent(mContext, ControlPanelActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            finish();
        }
    }

    /**
     * load User Rights post login
     */
    public void loadUserRights(LoginResponseDTO loginResponse) {
        loginResponseDTO = loginResponse;
        userModel = appSession.getUserData();
        loadProcesses();
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(mContext, GenericConstant.AUTHENTICATION_JSON);
                Authenticationresponse userResponse = (Authenticationresponse) JsonUtil.getInstance().toModel(strJson, Authenticationresponse.class);
            /*if (userResponse.getUser().getRights() != null) {
                if (!db.isRightExist()) {
                    db.add_rightsList(userResponse.getUser().getRights());
                }
            }*/
                UserBean userBean = new UserBean();
                userBean.setForce(loginResponseDTO.getUser().getSupforcecode());
                userBean.setForce_Code(loginResponseDTO.getUser().getSupforcecode());
                userBean.setId(String.valueOf(loginResponseDTO.getUser().getUserid()));
                userBean.setOfficer_Service_Number(String.valueOf(loginResponseDTO.getUser().getUserid()));
                userBean.setOfficer_Collar_Number(String.valueOf(loginResponseDTO.getUser().getUserid()));
                userBean.setOfficer_Name(loginResponseDTO.getUser().getUsername());
                userBean.setOfficer_Rank(loginResponseDTO.getUser().getRank());
                userBean.setOfficer_Station(loginResponseDTO.getUser().getArea());
                userBean.setSystem(GenericConstant.POLE);
                appSession.setUser(userBean);
                manageVersionData(userResponse.getMasterDataResponse());
                //DialogUtil.cancelProgressDialog(mProgressDialog);
                //processFlowVersionHandle();
                runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    processFlowVersionHandle();
                    if (mContext instanceof LoginActivity) {
                        loginFlow();
                    } else {
                        DialogUtil.dismiss();
                    }
                });
            } catch (Exception e) {
                DialogUtil.cancelProgressDialog(mProgressDialog);
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), LoginActivity.class, "loadUserRights");
            }
        }).start();
    }


    /**
     * Check weather process is exist or not
     *
     * @param subProcessId
     * @return
     */
    public String isProcessFlow(int subProcessId) {
        ProcessFlowMapper mProcessFlowMapper = db.getProcessflowJSON(subProcessId);
        if (mProcessFlowMapper != null) {
            jsonFlow = mProcessFlowMapper.getProcessFlow();
        }
        return jsonFlow;
    }

    /**
     * Load Initial JSON
     */
    public void loadFlow(String processName, String flowstr) {
        mProgressDialog = DialogUtil.showProgressDialog(mContext);
        new Thread(() -> {
            try {
                String string1 = flowstr.replaceAll("\\\\n", "");
                String string2 = string1.replaceAll("\\\\", "");
                String string3 = string2.replaceAll("^\"|\"$", "");
                Log.e("PROCESS ", " JSON >>>>>>> " + string3);
                ResponseModel responseModel = (ResponseModel) JsonUtil.getInstance().toModel(string3, ResponseModel.class);
                runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (responseModel != null) {

                        if (processName.equalsIgnoreCase("Search")) {

                            SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.FDS_PROCESS);
                            Intent intent = new Intent(this, FDSSearchActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(GenericConstant.JSON_FILE_NAME, responseModel);
                            bundle.putString(GenericConstant.PROCESS_NAME, processName);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                        } else {
                            SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, processName);
                            appSession.setImageList(null);
                            ProcessCreationActivity.SECTION_COUNT = 0;
                            ProcessCreationActivity.QUESTION_COUNT = 0;

                            Intent intent = new Intent(this, ProcessCreationActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(GenericConstant.FILE_NAME_DRAFT, "");
                            bundle.putSerializable(GenericConstant.JSON_FILE_NAME, responseModel);
                            bundle.putString(GenericConstant.PROCESS_NAME, processName);
                            bundle.putString(GenericConstant.JSON_OBJECT, "");
                            intent.putExtras(bundle);

                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        }
                    }
                });
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "loadJson");
            }
        }).start();
    }

    /**
     * Load Initial JSON
     */
    public void loadFlowDraft(String processName, String flowstr, String fileName) {
        mProgressDialog = DialogUtil.showProgressDialog(mContext);
        new Thread(() -> {
            try {
                String string1 = flowstr.replaceAll("\\\\n", "");
                String string2 = string1.replaceAll("\\\\", "");
                String string3 = string2.replaceAll("^\"|\"$", "");
                Log.e("PROCESS DRAFT ", " JSON >>>>>>> " + string3);
                ResponseModel responseModel = (ResponseModel) JsonUtil.getInstance().toModel(string3, ResponseModel.class);
                runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (responseModel != null) {

                        if (processName.equalsIgnoreCase("Search")) {

                            SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.FDS_PROCESS);
                            Intent intent = new Intent(this, FDSSearchActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(GenericConstant.JSON_FILE_NAME, responseModel);
                            bundle.putString(GenericConstant.PROCESS_NAME, processName);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                        } else {
                            SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);
                            appSession.setImageList(null);
                            ProcessCreationActivity.SECTION_COUNT = 0;
                            ProcessCreationActivity.QUESTION_COUNT = 0;

                            Intent intent = new Intent(this, ProcessCreationActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(GenericConstant.FILE_NAME_DRAFT, fileName);
                            bundle.putSerializable(GenericConstant.JSON_FILE_NAME, responseModel);
                            bundle.putString(GenericConstant.PROCESS_NAME, processName);
                            bundle.putString(GenericConstant.JSON_OBJECT, "");
                            intent.putExtras(bundle);

                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        }
                    }
                });
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), BaseActivity.class, "loadJsonDraft");
            }
        }).start();
    }

    /**
     * Insert Dropdown and Triggers value with the version
     */
    public void manageVersionData(GetMasterDataResponse getMasterDataResponse) {

        List<LoginResponseDTO.VersionManagementsBean> aVersionManagementsBean = loginResponseDTO.getVersionmanagements();
        if (aVersionManagementsBean != null && aVersionManagementsBean.size() > 0) {

            if (aVersionManagementsBean.size() == 1) {
                int versionExist = db.isVersionExist(aVersionManagementsBean.get(0).getVersionname(), aVersionManagementsBean.get(0).getVersionnumber());
                if (aVersionManagementsBean.get(0).getVersionname().equalsIgnoreCase(GenericConstant.DROP_DOWN)) {
                    if (versionExist == 1) {
                        db.updateVersion(aVersionManagementsBean.get(0).getVersionnumber(), aVersionManagementsBean.get(0).getVersionname());
                        if (db.isDropDownExist()) {
                            db.deleteAllDropdownValues();
                        }
                        //db.addDropDownList2(getMasterDataResponse.getMasterDataProcesses());
                        getDropdownApi();
                    } else if (versionExist == 0) {
                        db.addVersion(aVersionManagementsBean.get(0).getVersionname(), aVersionManagementsBean.get(0).getVersionnumber());
                        if (!db.isDropDownExist()) {
                            //db.addDropDownList2(getMasterDataResponse.getMasterDataProcesses());
                            getDropdownApi();
                        }
                    }
                    getTriggersApi();
                } else if (aVersionManagementsBean.get(0).getVersionname().equalsIgnoreCase(GenericConstant.TRIGGER)) {
                    if (versionExist == 1) {
                        db.updateVersion(aVersionManagementsBean.get(0).getVersionnumber(), aVersionManagementsBean.get(0).getVersionname());
                        if (db.isTriggerExist()) {
                            db.deleteTriggerValues();
                        }
                        getTriggersApi();
                    } else if (versionExist == 0) {
                        db.addVersion(aVersionManagementsBean.get(0).getVersionname(), aVersionManagementsBean.get(0).getVersionnumber());
                        if (!db.isTriggerExist()) {
                            getTriggersApi();
                        }
                    }
                    getDropdownApi();
                }
            } else {
                for (LoginResponseDTO.VersionManagementsBean model : aVersionManagementsBean) {

                    int versionExist = db.isVersionExist(model.getVersionname(), model.getVersionnumber());

                    if (model.getVersionname().equalsIgnoreCase(GenericConstant.DROP_DOWN)) {
                        if (versionExist == 1) {
                            db.updateVersion(model.getVersionnumber(), model.getVersionname());
                            if (db.isDropDownExist()) {
                                db.deleteAllDropdownValues();
                            }
                            //db.addDropDownList2(getMasterDataResponse.getMasterDataProcesses());
                            getDropdownApi();
                        } else if (versionExist == 0) {
                            db.addVersion(model.getVersionname(), model.getVersionnumber());
                            if (!db.isDropDownExist()) {
                                //db.addDropDownList2(getMasterDataResponse.getMasterDataProcesses());
                                getDropdownApi();
                            }
                        }
                    } else if (model.getVersionname().equalsIgnoreCase(GenericConstant.TRIGGER)) {
                        if (versionExist == 1) {
                            db.updateVersion(model.getVersionnumber(), model.getVersionname());
                            if (db.isTriggerExist()) {
                                db.deleteTriggerValues();
                            }
                            getTriggersApi();
                        } else if (versionExist == 0) {
                            db.addVersion(model.getVersionname(), model.getVersionnumber());
                            if (!db.isTriggerExist()) {
                                getTriggersApi();
                            }
                        }
                    }
                }
            }

        } else {
            //db.addDropDownList2(getMasterDataResponse.getMasterDataProcesses());
            getDropdownApi();
            getTriggersApi();
        }
    }

    /**
     * Load the drop down data
     *
     * @param dropdownStr
     */
    public void loadDropdownData(String dropdownStr) {
        new Thread(() -> {
            try {
//                    String ddstr1 = dropdownStr.replaceAll("\\\\n", "");
                String ddstr2 = dropdownStr.replaceAll("\\\\", "");
                String ddstr3 = ddstr2.replaceAll("^\"|\"$", "");
                Log.e("Dropdown Json string", "After Replace : " + ddstr3);
                //Utilities.getInstance(BaseActivity.this).writeFile(ddstr3, "DROPDOWN", "/DROPDOWN");
                Gson gson = new Gson();
                List<DropdownListResponse> aDropdownListResponse = gson.fromJson(ddstr3, new TypeToken<ArrayList<DropdownListResponse>>() {
                }.getType());
                //List<DropdownListResponse> aDropdownListResponse = (List<DropdownListResponse>) JsonUtil.getInstance().toModel(ddstr3, DropdownListResponse.class);
                db.addDropDownList(aDropdownListResponse);
                runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                });
            } catch (Exception e) {
                DialogUtil.cancelProgressDialog(mProgressDialog);
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), LoginActivity.class, "loadDropdownData");
            }
        }).start();
    }

    /**
     * Load the Processes
     */
    private void loadProcesses() {
        new Thread(() -> {
            try {
                LoginResponseDTO.UseraccessrightsformattedBean mUseraccessrightsformattedBean = loginResponseDTO.getUseraccessrightsformatted();
                if (mUseraccessrightsformattedBean != null) {
                    List<LoginResponseDTO.ProcessesBean> aProcessesBean = mUseraccessrightsformattedBean.getProcesses();
                    if (aProcessesBean != null && aProcessesBean.size() > 0) {
                        //db = new DatabaseHelper(this);
                        if (!db.isProcessExist()) {
                            db.addProcessSubProcessList(aProcessesBean);
                            runOnUiThread(() -> {
                                for (int i = 0; i < aProcessesBean.size(); i++) {
                                    List<LoginResponseDTO.SubProcessesBean> subProcessList = aProcessesBean.get(i).getSubprocesses();
                                    if(subProcessList != null && subProcessList.size() > 0) {
                                        for (int j = 0; j < subProcessList.size(); j++) {
                                            Log.e("CALLED API FOR", " SUB PROCESS >>>>> " + subProcessList.get(j).getSubprocessname());
                                            getUpdateProcessFlowApi(Integer.parseInt(aProcessesBean.get(i).getProcessid()), Integer.parseInt(subProcessList.get(j).getSubprocessid()));
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), BaseActivity.class, "loadProcesses");
            }
        }).start();
    }

    /**
     * Handle Process Flow
     */
    private void processFlowVersionHandle() {
        try {
            List<LoginResponseDTO.ProcessFlowVersionsBean> aProcessFlowVersionsBean = loginResponseDTO.getProcessflowversions();
            if (aProcessFlowVersionsBean != null && aProcessFlowVersionsBean.size() > 0) {

                for (LoginResponseDTO.ProcessFlowVersionsBean model : aProcessFlowVersionsBean) {
                    if (!db.isFlowExist(model.getProcessid(), model.getSubprocessid(), model.getVersionnumber())) {
                        db.addProcessFlowVersion(model);
                        getUpdateProcessFlowApi(model.getProcessid(), model.getSubprocessid());
                    }
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), LoginActivity.class, "processFlowVersionHandle");
        }
    }

    /**
     * Call the api for reset the password
     */
    private void getTriggersApi() {
        if (Utilities.getInstance(this).isNetworkAvailable()) {
            TriggerRequestModel triggerRequestModel = getTriggerRequest();
            new GetAllTriggersApi().getTriggers(BaseActivity.this, triggerRequestModel);
        } else {
            BasicMethodsUtil.getInstance().showToast(mContext, getString(R.string.no_network));
        }
    }

    /**
     * Call the api for reset the password
     */
    private void getDropdownApi() {
        if (Utilities.getInstance(this).isNetworkAvailable()) {
            DropdownRequestModel dropdownRequestModel = getDropdownRequest();
            new DropdownApi().getDropdown(BaseActivity.this, dropdownRequestModel);
        } else {
            BasicMethodsUtil.getInstance().showToast(mContext, getString(R.string.no_network));
        }
    }

    /**
     * Create Trigger request
     *
     * @return
     */
    private TriggerRequestModel getTriggerRequest() {
        TriggerRequestModel triggerRequestModel = new TriggerRequestModel();
        if (userModel != null) {
            BasedataModel mBasedataBean = new BasedataModel();
            mBasedataBean.setCollarid(appSession.getUserID());
            mBasedataBean.setSessionid(SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.SESSION_ID, ""));
            mBasedataBean.setCustomercode(userModel.getCustomercode());
            mBasedataBean.setProcess(ApiConstants.PROCESS_TRIGGER);
            mBasedataBean.setSubprocessname(ApiConstants.SUB_PROCESS_READ);
            mBasedataBean.setFunction(ApiConstants.METHOD_SEARCH_TRIGGER);
            triggerRequestModel.setBasedata(mBasedataBean);
            triggerRequestModel.setCustomerId(userModel.getCustomerid());
        }
        return triggerRequestModel;
    }

    /**
     * Create Dropdown request
     *
     * @return
     */
    private DropdownRequestModel getDropdownRequest() {
        DropdownRequestModel dropdownRequestModel = new DropdownRequestModel();
        if (userModel != null) {
            BasedataModel mBasedataBean = new BasedataModel();
            mBasedataBean.setCustomercode(userModel.getCustomercode());
            mBasedataBean.setProcess(ApiConstants.PROCESS_CONFIGURATION);
            mBasedataBean.setSubprocessname(ApiConstants.SUB_PROCESS_READ);
            mBasedataBean.setFunction(ApiConstants.METHOD_GET_MASTER_DATA);
            mBasedataBean.setCollarid(appSession.getUserID());
            mBasedataBean.setSessionid(SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.SESSION_ID, ""));
            dropdownRequestModel.setBasedata(mBasedataBean);

            DropdownRequestModel.JsonrequestBean jsonRequestBean = new DropdownRequestModel.JsonrequestBean();
            jsonRequestBean.setCustomerid(userModel.getCustomerid());
            jsonRequestBean.setProcess("");
            jsonRequestBean.setLookuptype(ApiConstants.LOOKUP_TYPE_ALL);
            jsonRequestBean.setLookupnames(ApiConstants.TASK_TYPE);

            String jsonReq = JsonUtil.getInstance().toJson(jsonRequestBean).toString();
            dropdownRequestModel.setJsonrequest(jsonReq);
        }
        return dropdownRequestModel;
    }

    /**
     * Get the process flow API
     *
     * @param subProcessId
     */
    public void getProcessFlowApi(int processId, int subProcessId) {
        if (Utilities.getInstance(this).isNetworkAvailable()) {
            mProgressDialog = DialogUtil.showProgressDialog(mContext);
            ProcessFlowRequestModel processFlowRequestModel = getProcessFlowRequest(subProcessId);
            new GetProcessFlowApi().callProcessFlow(BaseActivity.this, processFlowRequestModel, processId, subProcessId);
        } else {
            BasicMethodsUtil.getInstance().showToast(mContext, getString(R.string.no_network));
        }
    }

    /**
     * Get the process flow API
     *
     * @param subProcessId
     */
    public void getUpdateProcessFlowApi(int processId, int subProcessId) {
        if (Utilities.getInstance(this).isNetworkAvailable()) {
            mProgressDialog = DialogUtil.showProgressDialog(mContext);
            ProcessFlowRequestModel processFlowRequestModel = getProcessFlowRequest(subProcessId);
            new GetProcessFlowApi().callUpdateProcessFlow(BaseActivity.this, processFlowRequestModel, processId, subProcessId);
        } else {
            BasicMethodsUtil.getInstance().showToast(mContext, getString(R.string.no_network));
        }
    }

    /**
     * process flow request
     *
     * @param subProcessId
     * @return
     */
    private ProcessFlowRequestModel getProcessFlowRequest(int subProcessId) {
        ProcessFlowRequestModel processFlowRequestModel = new ProcessFlowRequestModel();
        if (userModel != null) {
            BasedataModel mBasedataBean = new BasedataModel();
            mBasedataBean.setCustomercode(userModel.getCustomercode());
            mBasedataBean.setCollarid(appSession.getUserID());
            mBasedataBean.setSessionid(userModel.getToken());
            mBasedataBean.setProcess(ApiConstants.PROCESS_FLOW);
            mBasedataBean.setSubprocessname(ApiConstants.SUB_PROCESS_READ);
            mBasedataBean.setFunction(ApiConstants.METHOD_GET_PROCESS_FLOW);
            processFlowRequestModel.setBasedata(mBasedataBean);
            processFlowRequestModel.setSubProcessId(subProcessId);
        }
        return processFlowRequestModel;
    }

    /**
     * Load the Triggers
     */
    private void loadTrigger() {
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(mContext, GenericConstant.TRIGGER_LIST_JSON);
                TriggerResponse triggerResponse = (TriggerResponse) JsonUtil.getInstance().toModel(strJson, TriggerResponse.class);
                if (triggerResponse != null) {
                    List<TriggerResponse.TriggersBean> aTriggersBean = triggerResponse.getTriggers();
                    if (aTriggersBean != null && aTriggersBean.size() > 0) {
                        if (!db.isTriggerExist()) {
                            db.addTriggerList(aTriggersBean);
                        }
                    }
                }
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), LoginActivity.class, "loadTrigger");
            }
        }).start();
    }
}
