package hcl.policing.digitalpolicingplatform.activities.login;

import android.Manifest;
import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.concurrent.Executor;

import hcl.policing.digitalpolicingplatform.BuildConfig;
import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.constants.api.ApiConstants;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.database.DatabaseHelper;
import hcl.policing.digitalpolicingplatform.fragments.bioMetric.BiometricFragment;
import hcl.policing.digitalpolicingplatform.listeners.dialog.DialogEventListener;
import hcl.policing.digitalpolicingplatform.models.login.BasedataModel;
import hcl.policing.digitalpolicingplatform.models.login.ForgotPasswordRequestModel;
import hcl.policing.digitalpolicingplatform.models.login.LoginRequestDTO;
import hcl.policing.digitalpolicingplatform.models.login.LoginResponseDTO;
import hcl.policing.digitalpolicingplatform.models.process.officer.UserModel;
import hcl.policing.digitalpolicingplatform.mpermissionslibrary.PermissionRequest;
import hcl.policing.digitalpolicingplatform.mpermissionslibrary.PermissionRequestHandler;
import hcl.policing.digitalpolicingplatform.mpermissionslibrary.PermissionsManager;
import hcl.policing.digitalpolicingplatform.mpermissionslibrary.PermissionsUtil;
import hcl.policing.digitalpolicingplatform.networks.login.ForgotPasswordApi;
import hcl.policing.digitalpolicingplatform.networks.login.LoginApi;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.OnSwipeTouchListner;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class LoginActivity extends BaseActivity implements View.OnClickListener,
        PermissionRequest.RequestStorage, PermissionRequest.RequestMicrophone, PermissionRequest.RequestCustomPermissionGroup,
        PermissionRequest.RequestLocation, PermissionRequest.RequestSensor {

    private AppSession appSession;
    private Context context;
    private String notify = "", OTP = "";
    private EditText etUserId, etPassword, etUserId_, etPassword_, checkOTP;

    private ImageView ivLogo;
    private TextView tvLogin, tvPin, tvLoginBack, tvPinBack, tvError, tvForgotpass_;
    private ConstraintLayout llLogin, llPin;
    private RelativeLayout rlTop, rlCenter, rlParent;
    private Animation slideInWithBounce, slideOutRight, slideOutLeft, fadeIn, fadeOut, fadeInLong;
    private ImageView pin1, pin2, pin3, pin4;
    private LoginResponseDTO loginResponseDTO;
    private ConstraintLayout conPin, conLogin;
    private UserModel userModel;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_new);
        context = this;
        appSession = new AppSession(this);
        if (db == null) {
            db = new DatabaseHelper(this);
        }

        try {
            String noti = getIntent().getStringExtra(GenericConstant.TAST_ID);
            if (noti != null) {
                notify = noti;
                Log.e("MSG TYPE SPLASH ", "NOTIFICATION " + noti);
            } else {
                notify = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        initView();

        if (appSession.isPinEnabled()) {
            conPin.setVisibility(View.VISIBLE);
            conLogin.setVisibility(View.GONE);
            tvLogin.setVisibility(View.INVISIBLE);
            tvPin.setVisibility(View.VISIBLE);
            llLogin.setVisibility(View.GONE);
            llPin.setVisibility(View.VISIBLE);
        } else {
            conPin.setVisibility(View.GONE);
            conLogin.setVisibility(View.VISIBLE);
        }

        if (appSession.isLogin()) {
            loginFlow();
        } else {
            new Handler().postDelayed(() -> startingAnimation(), 700);
        }
    }

    /**
     * Initiate the views
     */
    private void initView() {
        conLogin = findViewById(R.id.con_login);
        conPin = findViewById(R.id.con_pin);

        ivLogo = findViewById(R.id.iv_logo);
        slideInWithBounce = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.slide_in_with_bounce);
        slideOutLeft = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.slide_out_left);
        slideOutRight = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.slide_out_right);
        fadeIn = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.fade_out);
        fadeInLong = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.fade_in_long);

        pin1 = findViewById(R.id.pin_1);
        pin2 = findViewById(R.id.pin_2);
        pin3 = findViewById(R.id.pin_3);
        pin4 = findViewById(R.id.pin_4);
        checkOTP = findViewById(R.id.checkOTP);
        tvLogin = findViewById(R.id.tv_login);
        tvPin = findViewById(R.id.tv_pin);
        tvLoginBack = findViewById(R.id.tv_login_back);
        tvPinBack = findViewById(R.id.tv_pin_back);


        llLogin = findViewById(R.id.ll_login);
        llPin = findViewById(R.id.ll_pin);
        rlTop = findViewById(R.id.rl_top);
        rlCenter = findViewById(R.id.rl_center);
        rlParent = findViewById(R.id.rl_parent);

        tvError = findViewById(R.id.tv_error);
        TextView tvForgotPin = findViewById(R.id.forgot_pin);

        Button btnLogin = findViewById(R.id.btn_login);
        Button btnLogin_ = findViewById(R.id.btn_login_);
        ImageView ivAbout = findViewById(R.id.iv_about);

        PermissionRequestHandler.requestCustomPermissionGroup(context, "", Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.RECORD_AUDIO);
        PermissionRequestHandler.requestStorage(context, "");
        PermissionRequestHandler.requestLocation(context, "");
        PermissionRequestHandler.requestMicrophone(context, "");

        etUserId = findViewById(R.id.edit_login);
        etPassword = findViewById(R.id.edit_password);
        etUserId_ = findViewById(R.id.edit_login_);
        etPassword_ = findViewById(R.id.edit_password_);
        tvForgotpass_ = findViewById(R.id.forgotpass_);

        btnLogin.setOnClickListener(this);
        btnLogin_.setOnClickListener(this);
        tvLoginBack.setOnClickListener(this);
        tvPinBack.setOnClickListener(this);
        ivAbout.setOnClickListener(this);
        tvForgotPin.setOnClickListener(this);
        tvForgotpass_.setOnClickListener(this);

        tvLogin.setOnTouchListener(new OnSwipeTouchListner(this) {
            public void onSwipeRight() {
                onClickPinAnimation();
            }
        });
        tvPin.setOnTouchListener(new OnSwipeTouchListner(this) {
            public void onSwipeLeft() {
                onClickLoginAnimation();
            }
        });

        rlCenter.setOnTouchListener(new OnSwipeTouchListner(this) {
            public void onSwipeLeft() {
                onClickPinAnimation();
            }

            public void onSwipeRight() {
                onClickLoginAnimation();
            }
        });
    }

    /**
     * Initialize the listeners
     */
    private void initializePinListeners() {
        checkOTP.requestFocus();
        Utilities.showKeyboard(LoginActivity.this);
        checkOTP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvError.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

                switch (s.length()) {
                    case 4:
                        pin4.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_green_circle));
                        if (appSession.getPin() != null && !appSession.getPin().equalsIgnoreCase("")) {
                            if (appSession.getPin().equalsIgnoreCase(s.toString())) {
                                Utilities.hideKeyboard(LoginActivity.this);

                                login(appSession.getUserID(), appSession.getPassword());
                            } else {
                                pin4.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_gray_border_circle));
                                pin3.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_gray_border_circle));
                                pin2.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_gray_border_circle));
                                pin1.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_gray_border_circle));
                                checkOTP.setText("");
                                tvError.setText(getResources().getString(R.string.incorrect_pin));
                            }
                        } else {
                            tvError.setText(getResources().getString(R.string.enable_pin));
                        }
                        break;
                    case 3:
                        pin4.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_gray_border_circle));
                        pin3.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_green_circle));
                        break;
                    case 2:
                        pin3.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_gray_border_circle));
                        pin2.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_green_circle));
                        break;
                    case 1:
                        if (appSession.getPin() != null && !appSession.getPin().equalsIgnoreCase("")) {
                            pin2.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_gray_border_circle));
                            pin1.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_green_circle));
                        } else {
                            checkOTP.setText("");
                            tvError.setText(getResources().getString(R.string.enable_pin));
                        }
                        break;
                    default:
                        pin1.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_gray_border_circle));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                try {
                    login(etUserId.getText().toString().trim(), etPassword.getText().toString().trim());
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), LoginActivity.class, "loginButtonClick");
                }
                break;
            case R.id.btn_login_:
                try {
                    login(etUserId_.getText().toString().trim(), etPassword_.getText().toString().trim());
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), LoginActivity.class, "loginButtonClick");
                }
                break;

            case R.id.tv_login_back:
                onClickLoginAnimation();
                break;

            case R.id.tv_pin_back:
                onClickPinAnimation();
                break;

            case R.id.iv_about:
                String versionName = BuildConfig.VERSION_NAME;
                DialogUtil.aboutDialog(context, "", versionName);
                break;

            case R.id.forgot_pin:
                onClickLoginAnimation();
                Toast.makeText(context, getResources().getString(R.string.forgot_pin_text), Toast.LENGTH_SHORT).show();
                break;

            case R.id.forgotpass_:
                onClickLoginAnimation();
                showForgotPasswordDialog();
                break;
        }
    }

    /**
     * Show ForgotPassword Dialog
     */
    private void showForgotPasswordDialog() {

        DialogUtil.showForgotPasswordDialog(context, new DialogEventListener() {
            @Override
            public void dialogEventClick(String customerCode, String username) {
                callForgotPasswordApi(customerCode, username);
            }
        });
    }

    /**
     * Call the api for forgot the password
     */
    private void callForgotPasswordApi(String customerCode, String username) {
        if (Utilities.getInstance(this).isNetworkAvailable()) {
            mProgressDialog = DialogUtil.showProgressDialog(context);
            ForgotPasswordRequestModel requestModel = getForgotPasswordRequest(customerCode, username);
            new ForgotPasswordApi().callForgotPassword(LoginActivity.this, requestModel);
        } else {
            BasicMethodsUtil.getInstance().showToast(context, getString(R.string.no_network));
        }
    }

    /**
     * Create forgot password Request
     *
     * @return
     */
    private ForgotPasswordRequestModel getForgotPasswordRequest(String customerCode, String username) {
        ForgotPasswordRequestModel forgotPasswordRequestModel = new ForgotPasswordRequestModel();
        BasedataModel mBasedataBean = new BasedataModel();
        mBasedataBean.setCustomercode(customerCode);
        mBasedataBean.setProcess(ApiConstants.PROCESS_ACCOUNT);
        mBasedataBean.setSubprocessname(ApiConstants.SUB_PROCESS_READ);
        mBasedataBean.setFunction(ApiConstants.METHOD_FORGOT_PASSWORD);
        forgotPasswordRequestModel.setBasedata(mBasedataBean);
        forgotPasswordRequestModel.setUsername(username);
        return forgotPasswordRequestModel;
    }

    /**
     * Start the animation method
     */
    private void startingAnimation() {
        rlTop.setVisibility(View.VISIBLE);
        rlTop.startAnimation(slideInWithBounce);

        slideInWithBounce.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rlTop.setVisibility(View.VISIBLE);
                AnimatedVectorDrawable logoAnimation = (AnimatedVectorDrawable) ivLogo.getDrawable();
                logoAnimation.start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fadeInCenterLayout();
                    }
                }, 700);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * Fade in animation method
     */
    private void fadeInCenterLayout() {
        rlCenter.setVisibility(View.VISIBLE);
        rlCenter.startAnimation(fadeInLong);

        fadeInLong.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rlCenter.setVisibility(View.VISIBLE);
                if (appSession.isBiometricEnabled()) {
                    /*Utilities.hideKeyboard(LoginActivity.this);
                    Fragment BiometricFragment = new BiometricFragment();
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.setCustomAnimations(R.anim.translate_in, R.anim.slide_out_left, R.anim.translate_in, R.anim.slide_out_left);
                    ft.replace(R.id.fragment_container_fingerprint, BiometricFragment);
                    ft.addToBackStack("BiometricFragment");
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();*/
                    Log.e("CALLED ", " BIOMETRIC >>>>>");
                    callBiometric();
                }
                if (appSession.isPinEnabled()) {
                    initializePinListeners();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * LoginApi click animation
     */
    public void onClickLoginAnimation() {
        Utilities.hideKeyboard(LoginActivity.this);
        tvLoginBack.setClickable(false);
        tvPinBack.setClickable(true);
        tvPin.setVisibility(View.VISIBLE);
        tvPin.startAnimation(slideOutLeft);

        slideOutLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                llPin.startAnimation(fadeOut);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvPin.setVisibility(View.INVISIBLE);
                tvLogin.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llPin.setVisibility(View.GONE);
                llLogin.setVisibility(View.VISIBLE);
                llLogin.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llLogin.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * On click pin animation method
     */
    public void onClickPinAnimation() {
        tvLoginBack.setClickable(true);
        tvPinBack.setClickable(false);

        tvLogin.setVisibility(View.VISIBLE);
        tvLogin.startAnimation(slideOutRight);

        slideOutRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                llLogin.startAnimation(fadeOut);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvPin.setVisibility(View.VISIBLE);
                tvLogin.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llLogin.setVisibility(View.GONE);
                llPin.setVisibility(View.VISIBLE);
                llPin.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llPin.setVisibility(View.VISIBLE);
                initializePinListeners();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * Call the bio metric method
     */
    private void callBiometric() {
        executor = ContextCompat.getMainExecutor(context);
        biometricPrompt = new BiometricPrompt(LoginActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if(errorCode != BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    Toast.makeText(context,"Authentication error: " + errString, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                //Toast.makeText(context,"Authentication succeeded!", Toast.LENGTH_SHORT).show();
                login(appSession.getUserID(), appSession.getPassword());
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(context, "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for DPP")
                .setSubtitle("Log in using your biometric credential")
                .setDeviceCredentialAllowed(false)
                .setNegativeButtonText("Cancel")
                .build();

        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.
        /*Button biometricLoginButton = findViewById(R.id.biometric_login);
        biometricLoginButton.setOnClickListener(view -> {
            biometricPrompt.authenticate(promptInfo);
        });*/
        biometricPrompt.authenticate(promptInfo);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtil.onRequestPermissionsResult(requestCode, permissions, grantResults, context);
    }

    @Override
    public void onAllCustomPermissionGroupGranted() {
        PermissionsManager.getInstance().setStoragePermissionDenied(false);
    }

    @Override
    public void onCustomPermissionGroupDenied() {

    }

    @Override
    public void onStoragePermissionGranted() {
    }

    @Override
    public void onStoragePermissionDenied() {
        BasicMethodsUtil.getInstance().showToast(context, getString(R.string.permission_required));
    }

    @Override
    public void onLocationPermissionGranted() {

    }

    @Override
    public void onLocationPermissionDenied() {
        BasicMethodsUtil.getInstance().showToast(context, getString(R.string.permission_required));
    }

    @Override
    public void onMicrophonePermissionGranted() {

    }

    @Override
    public void onMicrophonePermissionDenied() {
        BasicMethodsUtil.getInstance().showToast(context, getString(R.string.permission_required));
    }

    @Override
    public void onSensorPermissionGranted() {

    }

    @Override
    public void onSensorPermissionDenied() {
        BasicMethodsUtil.getInstance().showToast(context, getString(R.string.permission_required));
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

                if(appSession.getUserID() != null && !appSession.getUserID().equalsIgnoreCase(userId)) {
                    appSession.clear();
                    db.clearDB();
                }
                if (Utilities.getInstance(this).isNetworkAvailable()) {
                    mProgressDialog = DialogUtil.showProgressDialog(context);
                    appSession.setUserID(userId);
                    appSession.setPassword(pass);
                    LoginRequestDTO loginRequestDTO = getLoginRequest(userId, pass);
                    loginResponseDTO = new LoginApi().callLogin(LoginActivity.this, loginRequestDTO);
                } else {
                    loginFlow();
                }
            } else {
                BasicMethodsUtil.getInstance().showToast(context, getResources().getString(R.string.enter_credientials));
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), LoginActivity.class, "login");
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.getBackStackEntryCount() > 0) {
            //mgr.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
            for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                fragmentManager.popBackStack();
            }
        } else {
            super.onBackPressed();
        }
    }
}
