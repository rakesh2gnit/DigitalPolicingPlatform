package hcl.policing.digitalpolicingplatform.activities.profile;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.constants.api.ApiConstants;
import hcl.policing.digitalpolicingplatform.models.login.BasedataModel;
import hcl.policing.digitalpolicingplatform.models.process.officer.UserModel;
import hcl.policing.digitalpolicingplatform.models.profile.GetUserDetailResponseModel;
import hcl.policing.digitalpolicingplatform.models.profile.UserDetailRequestModel;
import hcl.policing.digitalpolicingplatform.networks.profile.GetUserDetailApi;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.Base64ToBitmapUtil;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {

    private AppSession appSession;
    private Context mContext;
    public Dialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.profile_activity);
        appSession = new AppSession(mContext);
        initView();
    }

    private void initView() {
        ImageView ivBack = findViewById(R.id.iv_back);
        ImageView ivEdit = findViewById(R.id.ivEdit);
        ivBack.setOnClickListener(this);
        ivEdit.setOnClickListener(this);
        callUserDetailApi();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.ivEdit:
                BasicMethodsUtil.getInstance().launchActivity(mContext, ProfileEditActivity.class);
                break;
        }
    }


    /**
     * Call the api for reset the password
     */
    private void callUserDetailApi() {
        if (Utilities.getInstance(this).isNetworkAvailable()) {
            mProgressDialog = DialogUtil.showProgressDialog(mContext);
            UserDetailRequestModel userDetailRequestModel = getUserDetailRequest();
            new GetUserDetailApi().getUserDetail(ProfileActivity.this, userDetailRequestModel);
        } else {
            BasicMethodsUtil.getInstance().showToast(mContext, getString(R.string.no_network));
        }
    }


    /**
     * Create User Details Request
     *
     * @return
     */
    private UserDetailRequestModel getUserDetailRequest() {

        UserDetailRequestModel userDetailRequestModel = new UserDetailRequestModel();
        UserModel userModel = appSession.getUserData();
        if (userModel != null) {
            BasedataModel mBasedataBean = new BasedataModel();
            mBasedataBean.setCustomercode(userModel.getCustomercode());
            mBasedataBean.setProcess(ApiConstants.PROCESS_USER);
            mBasedataBean.setSubprocessname(ApiConstants.SUB_PROCESS_READ);
            mBasedataBean.setFunction(ApiConstants.METHOD_GET_USER_BY_USERID);
            userDetailRequestModel.setBasedata(mBasedataBean);
            userDetailRequestModel.setUserId(userModel.getUserid());
        }
        return userDetailRequestModel;
    }


    /**
     * Set profile Data
     *
     * @param getUserDetailResponseModel
     */
    public void setProfileData(GetUserDetailResponseModel getUserDetailResponseModel) {

        ImageView iv_profile = findViewById(R.id.iv_profile);
        if (getUserDetailResponseModel != null) {
            UserModel userModel = getUserDetailResponseModel.getUser();
            if (userModel != null) {

                Bitmap decodedImage = Base64ToBitmapUtil.base64ToBitmap(userModel.getUserimage());
                iv_profile.setImageBitmap(decodedImage);
                TextView tv_name = findViewById(R.id.tv_name);
                TextView tv_rank = findViewById(R.id.tv_rank);
                TextView tv_email = findViewById(R.id.tv_email);
                TextView tv_mobile = findViewById(R.id.tv_mobile);
                TextView tv_gender = findViewById(R.id.tv_gender);
                TextView tv_dob = findViewById(R.id.tv_dob);
                TextView tv_user_id = findViewById(R.id.tv_user_id);
                TextView tv_collar_id = findViewById(R.id.tv_collar_id);
                TextView tv_joining_date = findViewById(R.id.tv_joining_date);
                TextView tv_supervisor = findViewById(R.id.tv_supervisor);
                TextView tv_directorate = findViewById(R.id.tv_directorate);
                TextView tv_division = findViewById(R.id.tv_division);
                TextView tv_department = findViewById(R.id.tv_department);
                TextView tv_area = findViewById(R.id.tv_area);
                TextView tv_station = findViewById(R.id.tv_station);
                TextView tv_team = findViewById(R.id.tv_team);
                TextView tv_location = findViewById(R.id.tv_location);

                tv_name.setText(userModel.getForename() + " " + userModel.getSurname());
                tv_rank.setText(userModel.getRank());
                tv_email.setText(userModel.getEmail());
                tv_mobile.setText(userModel.getMobile());
                tv_gender.setText(userModel.getGender());
                tv_dob.setText(userModel.getDateofbirth());
                tv_user_id.setText(String.valueOf(userModel.getUserid()));
                tv_collar_id.setText(userModel.getUsername());
                tv_joining_date.setText(userModel.getDateofjoining());
                tv_supervisor.setText(userModel.getSupserviceno());
                tv_directorate.setText(userModel.getDirectorate());
                tv_division.setText(userModel.getDivision());
                tv_department.setText(userModel.getDepartment());
                tv_area.setText(userModel.getArea());
                tv_station.setText(userModel.getStation());
                tv_team.setText(userModel.getTeam());
                tv_location.setText(userModel.getArea());
            }
        }
    }
}
