package hcl.policing.digitalpolicingplatform.activities.officer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.adapters.process.officer.OfficerListAdaptor;
import hcl.policing.digitalpolicingplatform.constants.api.ApiConstants;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.models.login.BasedataModel;
import hcl.policing.digitalpolicingplatform.models.process.officer.OfficerBean;
import hcl.policing.digitalpolicingplatform.models.process.officer.OfficerRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.officer.OfficerResponse;
import hcl.policing.digitalpolicingplatform.models.process.officer.SearchUserByIdRequestModel;
import hcl.policing.digitalpolicingplatform.models.process.officer.SearchUserByWordRequestModel;
import hcl.policing.digitalpolicingplatform.models.process.officer.UserModel;
import hcl.policing.digitalpolicingplatform.networks.process.officer.SearchUserApi;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class OfficerSearchActivity extends BaseActivity {

    private Context mContext;
    private AppSession appSession;
    private RecyclerView rvList;
    private ArrayList<OfficerResponse.OfficerDetailsList> list;
    public Dialog mProgressDialog;
    private String searchWord = "Satish1";
    private List<UserModel> aUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.officerlist_dialog);
        initView();
        appSession = new AppSession(mContext);
//        callSearchOfficerByWordApi();
        callSearchOfficerByIdApi();
    }

    /**
     * Initialize the view
     */
    private void initView() {
        rvList = findViewById(R.id.rvOfficerList);
        setRecyclerViewProperty(mContext, rvList);
    }

    /**
     * Set the recycler view property
     *
     * @param context
     * @param rvComponentList
     */
    private static void setRecyclerViewProperty(Context context, RecyclerView rvComponentList) {
        rvComponentList.setHasFixedSize(true);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(context);
        rvComponentList.setLayoutManager(layoutmanager);
    }


    /**
     * Call the api for search Officer
     */
    private void callSearchOfficerByWordApi() {
        if (Utilities.getInstance(this).isNetworkAvailable()) {
            mProgressDialog = DialogUtil.showProgressDialog(mContext);
            SearchUserByWordRequestModel requestModel = getSearchOfficerByWordRequest();
            new SearchUserApi().SearchUser(OfficerSearchActivity.this, requestModel, ApiConstants.SEARCH_BY_WORD);
        } else {
            BasicMethodsUtil.getInstance().showToast(mContext, getString(R.string.no_network));
        }
    }


    /**
     * Call the api for search Officer
     */
    private void callSearchOfficerByIdApi() {
        if (Utilities.getInstance(this).isNetworkAvailable()) {
            mProgressDialog = DialogUtil.showProgressDialog(mContext);
            SearchUserByIdRequestModel requestModel = getSearchOfficerByIdRequest();
            new SearchUserApi().SearchUser(OfficerSearchActivity.this, requestModel, ApiConstants.SEARCH_BY_ID);
        } else {
            BasicMethodsUtil.getInstance().showToast(mContext, getString(R.string.no_network));
        }
    }

    /**
     * Create Search User by Word Request
     *
     * @return
     */
    private SearchUserByWordRequestModel getSearchOfficerByWordRequest() {
        SearchUserByWordRequestModel searchUserByWordRequestModel = new SearchUserByWordRequestModel();
        BasedataModel mBasedataBean = new BasedataModel();

        UserModel userModel = appSession.getUserData();
        if (userModel != null) {
            mBasedataBean.setCustomercode(userModel.getCustomercode());
            mBasedataBean.setProcess(ApiConstants.PROCESS_USER);
            mBasedataBean.setSubprocessname(ApiConstants.SUB_PROCESS_READ);
            mBasedataBean.setFunction(ApiConstants.METHOD_SEARCH_USER);
        }
        searchUserByWordRequestModel.setBasedata(mBasedataBean);
        searchUserByWordRequestModel.setWildSearch(true);
        searchUserByWordRequestModel.setSearchKey(searchWord);

        return searchUserByWordRequestModel;
    }

    /**
     * Create Search User by Word Request
     *
     * @return
     */
    private SearchUserByIdRequestModel getSearchOfficerByIdRequest() {
        SearchUserByIdRequestModel searchUserByIdRequestModel = new SearchUserByIdRequestModel();
        BasedataModel mBasedataBean = new BasedataModel();

        UserModel userModel = appSession.getUserData();
        if (userModel != null) {
            mBasedataBean.setCustomercode(userModel.getCustomercode());
            mBasedataBean.setProcess(ApiConstants.PROCESS_USER);
            mBasedataBean.setSubprocessname(ApiConstants.SUB_PROCESS_READ);
            mBasedataBean.setFunction(ApiConstants.METHOD_SEARCH_USER);
            searchUserByIdRequestModel.setBasedata(mBasedataBean);
            searchUserByIdRequestModel.setCustomerId(userModel.getCustomerid());
            searchUserByIdRequestModel.setUserName(userModel.getUsername());
        }
        return searchUserByIdRequestModel;
    }

    /**
     * Officer selection listener
     */
    public OnItemClickListener.OnItemClickCallback officerSelectionListener = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                if (aUserModel != null) {
                    UserModel mUserModel = aUserModel.get(position);
                    OfficerBean officerBean = new OfficerBean();
                    officerBean.setForce("" + mUserModel.getSupforcecode());
                    officerBean.setForce_Code("" + mUserModel.getSupforcecode());
                    officerBean.setOfficer_Collar_Number("" + mUserModel.getSupserviceno());
                    officerBean.setOfficer_Service_Number("" + mUserModel.getSupserviceno());
                    officerBean.setOfficer_Gender("" + mUserModel.getGender());
                    officerBean.setOfficer_Name(mUserModel.getForename() + " " + mUserModel.getSurname());
                    officerBean.setOfficer_Rank("" + mUserModel.getRank());
                    officerBean.setOfficer_Station(mUserModel.getStation());
                    officerBean.setId("" + mUserModel.getUserid());
                    officerBean.setSystem(GenericConstant.POLE);

                    ArrayList<OfficerRecentLogsBean> recentList = new ArrayList<>();
                    if(appSession.getSearchedOfficer() != null) {
                        recentList = appSession.getSearchedOfficer();
                    }
                    OfficerRecentLogsBean recentLogsBean = new OfficerRecentLogsBean();
                    recentLogsBean.setRecentValue(officerBean.getOfficer_Collar_Number() + " " + officerBean.getOfficer_Name());
                    recentLogsBean.setObjectList(officerBean);
                    recentLogsBean.setRecentLogsList(null);
                    recentList.add(recentLogsBean);
                    recentList = new ArrayList<OfficerRecentLogsBean>(new LinkedHashSet<OfficerRecentLogsBean>(recentList));
                    appSession.setSearchedOfficer(null);
                    appSession.setSearchedOfficer(recentList);

                    Intent intentProcess = new Intent();
                    setResult(RESULT_OK, intentProcess);
                    finish();
                    overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
                }
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), OfficerSearchActivity.class, "onOfficerClick");
            }
        }
    };

    /**
     * Get he list of Offline date
     */
    public void setOfficerList(List<UserModel> users) {

        aUserModel = users;
        if (users != null && users.size() > 0) {
            OfficerListAdaptor officerListAdaptor = new OfficerListAdaptor(mContext, users, officerSelectionListener);
            rvList.setAdapter(officerListAdaptor);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
    }
}


