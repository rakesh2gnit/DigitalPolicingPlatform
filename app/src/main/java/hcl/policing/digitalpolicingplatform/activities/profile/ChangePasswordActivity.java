package hcl.policing.digitalpolicingplatform.activities.profile;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.constants.api.ApiConstants;
import hcl.policing.digitalpolicingplatform.listeners.dialog.DialogItemClickListener;
import hcl.policing.digitalpolicingplatform.models.login.BasedataModel;
import hcl.policing.digitalpolicingplatform.models.process.officer.UserModel;
import hcl.policing.digitalpolicingplatform.models.profile.ChangePasswordRequestModel;
import hcl.policing.digitalpolicingplatform.networks.profile.ChangePasswordApi;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {

    private EditText etOldPassword, etNewPassword, etConfirmPassword;
    private ImageView ivOldPassword, ivNewPassword, ivConfirmPassword, ivEdit;
    private TextView tvName, tvRank;
    private Button btnSubmit;
    private Context mContext;
    public Dialog mProgressDialog;
    private UserModel userModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        AppSession appSession = new AppSession(mContext);
        userModel = appSession.getUserData();
        setContentView(R.layout.change_password_activity);
        initView();
    }

    /**
     * Initailize the view
     */
    private void initView() {
        ImageView ivBack = findViewById(R.id.iv_back);
        etOldPassword = findViewById(R.id.etOldPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        ivOldPassword = findViewById(R.id.ivOldPassword);
        ivNewPassword = findViewById(R.id.ivNewPassword);
        ivConfirmPassword = findViewById(R.id.ivConfirmPassword);
        ivEdit = findViewById(R.id.ivEdit);
        tvName = findViewById(R.id.tvName);
        tvRank = findViewById(R.id.tvRank);
        btnSubmit = findViewById(R.id.btnSubmit);
        setData();

        ivBack.setOnClickListener(this);
        ivOldPassword.setOnClickListener(this);
        ivNewPassword.setOnClickListener(this);
        ivConfirmPassword.setOnClickListener(this);
        ivEdit.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        etOldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    ivOldPassword.setVisibility(View.VISIBLE);
                    ivOldPassword.setImageResource(R.drawable.ic_visibility_off);

                } else {
                    ivOldPassword.setVisibility(View.GONE);
                    etOldPassword.setInputType(129);
                    ivOldPassword.setImageResource(R.drawable.ic_visibility);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        etNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    ivNewPassword.setVisibility(View.VISIBLE);
                    ivNewPassword.setImageResource(R.drawable.ic_visibility_off);

                } else {
                    ivNewPassword.setVisibility(View.GONE);
                    etNewPassword.setInputType(129);
                    ivNewPassword.setImageResource(R.drawable.ic_visibility);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        etConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    ivConfirmPassword.setVisibility(View.VISIBLE);
                    ivConfirmPassword.setImageResource(R.drawable.ic_visibility_off);

                } else {
                    ivConfirmPassword.setVisibility(View.GONE);
                    etConfirmPassword.setInputType(129);
                    ivConfirmPassword.setImageResource(R.drawable.ic_visibility);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    /**
     * Set Change password page data
     */
    private void setData() {
        if (userModel != null) {
            tvName.setText(userModel.getForename() + " " + userModel.getSurname());
            tvRank.setText(userModel.getRank());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;

            case R.id.ivOldPassword:

                if (etOldPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    etOldPassword.setInputType(129);
                    ivOldPassword.setImageResource(R.drawable.ic_visibility);
                    etOldPassword.setSelection(etOldPassword.getText().length());
                } else {
                    etOldPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivOldPassword.setImageResource(R.drawable.ic_visibility_off);
                    etOldPassword.setSelection(etOldPassword.getText().length());
                }

                break;
            case R.id.ivNewPassword:

                if (etNewPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    etNewPassword.setInputType(129);
                    ivNewPassword.setImageResource(R.drawable.ic_visibility);
                    etNewPassword.setSelection(etNewPassword.getText().length());
                } else {
                    etNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivNewPassword.setImageResource(R.drawable.ic_visibility_off);
                    etNewPassword.setSelection(etNewPassword.getText().length());
                }

                break;
            case R.id.ivConfirmPassword:

                if (etConfirmPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    etConfirmPassword.setInputType(129);
                    ivConfirmPassword.setImageResource(R.drawable.ic_visibility);
                    etConfirmPassword.setSelection(etConfirmPassword.getText().length());
                } else {
                    etConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivConfirmPassword.setImageResource(R.drawable.ic_visibility_off);
                    etConfirmPassword.setSelection(etConfirmPassword.getText().length());
                }

                break;
            case R.id.ivEdit:

                break;

            case R.id.btnSubmit:

                if (isValidInput()) {
                    showConfirmation();
                }


                break;
        }
    }


    /**
     * Call the api for reset the password
     */
    private void callChangePasswordApi() {
        if (Utilities.getInstance(this).isNetworkAvailable()) {
            mProgressDialog = DialogUtil.showProgressDialog(mContext);
            ChangePasswordRequestModel changePasswordRequestModel = getChangePasswordRequest(etOldPassword.getText().toString().trim(),
                    etNewPassword.getText().toString().trim());
            new ChangePasswordApi().callChangePassword(ChangePasswordActivity.this, changePasswordRequestModel);
        } else {
            BasicMethodsUtil.getInstance().showToast(mContext, getString(R.string.no_network));
        }
    }


    /**
     * Show confirmation
     */
    private void showConfirmation() {

        DialogUtil.showConfirmationDialog(mContext, "", getString(R.string.change_pwd_text), getString(R.string.no),
                getString(R.string.yes), new DialogItemClickListener() {
                    @Override
                    public void onClickPositive() {
                        callChangePasswordApi();
                    }

                    @Override
                    public void onClickNegative() {
                    }
                });
    }


    /**
     * validate the input
     *
     * @return
     */
    private boolean isValidInput() {
        boolean isValid = false;

        if (TextUtils.isEmpty(etOldPassword.getText().toString().trim())) {
            BasicMethodsUtil.getInstance().showToast(mContext, getString(R.string.invalid_old_password));

        } else if (TextUtils.isEmpty(etNewPassword.getText().toString().trim())) {
            BasicMethodsUtil.getInstance().showToast(mContext, getString(R.string.invalid_new_password));

        } else if (TextUtils.isEmpty(etConfirmPassword.getText().toString().trim())) {
            BasicMethodsUtil.getInstance().showToast(mContext, getString(R.string.invalid_confirm_password));

        } else if (!etNewPassword.getText().toString().trim().equals(etConfirmPassword.getText().toString().trim())) {
            BasicMethodsUtil.getInstance().showToast(mContext, getString(R.string.password_mismatch));
        } else {
            isValid = true;
        }

        return isValid;
    }

    /**
     * Create Logout Request
     *
     * @return
     */
    private ChangePasswordRequestModel getChangePasswordRequest(String oldPwd, String newPwd) {

        ChangePasswordRequestModel changePasswordRequestModel = new ChangePasswordRequestModel();
        if (userModel != null) {
            BasedataModel mBasedataBean = new BasedataModel();
            mBasedataBean.setCustomercode(userModel.getCustomercode());
            mBasedataBean.setProcess(ApiConstants.PROCESS_ACCOUNT);
            mBasedataBean.setSubprocessname(ApiConstants.SUB_PROCESS_UPDATE);
            mBasedataBean.setFunction(ApiConstants.METHOD_CHANGE_PASSWORD);
            changePasswordRequestModel.setBasedata(mBasedataBean);
            changePasswordRequestModel.setUserId(userModel.getUserid());
            changePasswordRequestModel.setOldPassword(oldPwd);
            changePasswordRequestModel.setPassword(newPwd);
        }
        return changePasswordRequestModel;
    }
}
