package hcl.policing.digitalpolicingplatform.activities.controlPannel;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.activities.notification.NotificationActivity;
import hcl.policing.digitalpolicingplatform.activities.profile.ProfileActivity;
import hcl.policing.digitalpolicingplatform.constants.api.ApiConstants;
import hcl.policing.digitalpolicingplatform.fragments.controlPannel.ClassicControlPannelFragment;
import hcl.policing.digitalpolicingplatform.listeners.LogoutListener;
import hcl.policing.digitalpolicingplatform.listeners.dialog.DialogCancelListener;
import hcl.policing.digitalpolicingplatform.listeners.dialog.ManuallyClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.address.AddressSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.cases.CaseSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.communication.CommunicationSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.courtwarrant.CourtWarrantSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.dlchek.DlCheckSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.investigation.InvestigationSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.PersonSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.vehicle.VehicleSelectionListener;
import hcl.policing.digitalpolicingplatform.models.login.BasedataModel;
import hcl.policing.digitalpolicingplatform.models.logout.LogoutRequestModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.AddressBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.PersonBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.VehicleBean;
import hcl.policing.digitalpolicingplatform.models.process.officer.UserModel;
import hcl.policing.digitalpolicingplatform.networks.logout.LogoutApi;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class ClassicControlPanelActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        CompoundButton.OnCheckedChangeListener, PersonSelectionListener, ManuallyClickListener, DialogCancelListener,
        VehicleSelectionListener, AddressSelectionListener, CaseSelectionListener, InvestigationSelectionListener,
        CommunicationSelectionListener, CourtWarrantSelectionListener, DlCheckSelectionListener, LogoutListener {

    private AppSession appSession;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private static Drawable toolbarDrawable = null;
    private Toolbar toolbar;
    public ImageView ivNavProfile;
    public TextView tvNavName;
    private TextView tvToolbarTitle;
    private boolean doubleBackToExitPressedOnce = false;
    private boolean isOpenDialog = false;
    private SwitchCompat swFingerprint, swPin, swClassic;
    public Dialog mProgressDialog;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.classic_activity_main);
        appSession = new AppSession(this);
        setToolbar();
        initView();

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
                //InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                //inputMethodManager.hideSoftInputFromWindow(new View(MainActivity.this).getWindowToken(), 0);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        showFragment(new ClassicControlPannelFragment(), "ControlFragment");

        if (appSession.isBiometricEnabled()) {
            swFingerprint.setChecked(true);
        }
        if (appSession.isPinEnabled()) {
            swPin.setChecked(true);
        }
        if (appSession.isClassicViewEnabled()) {
            swClassic.setChecked(true);
        }
    }

    /**
     * Set the tool bar
     */
    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setBackground(null);
            toolbar.setElevation(0);
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        }
    }

    /**
     * Initialize the view
     */
    private void initView() {
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        tvNavName = header.findViewById(R.id.tv_name);
        ivNavProfile = header.findViewById(R.id.iv_profile);
        drawerLayout = findViewById(R.id.drawer_layout);
        tvToolbarTitle = toolbar.findViewById(R.id.tv_toolbar_title);

        swFingerprint = (SwitchCompat) navigationView.getMenu().findItem(R.id.fingerprint).getActionView();
        swPin = (SwitchCompat) navigationView.getMenu().findItem(R.id.pin).getActionView();
        swClassic = (SwitchCompat) navigationView.getMenu().findItem(R.id.classic).getActionView();

        if (isSuppotBiometric()) {
            FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(this);
            if (fingerprintManagerCompat.hasEnrolledFingerprints()) {
                // User hasn't enrolled any fingerprints to authenticate with
                swFingerprint.setOnCheckedChangeListener(this);
            }
        }
        swPin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isOpenDialog = true;
                return false;
            }
        });
        swPin.setOnCheckedChangeListener(this);
        swClassic.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    /**
     * Create Menu Button
     *
     * @param title
     */
    public void createMenuButton(String title) {

        tvToolbarTitle.setText(title);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, R.color.white));
        if (toolbarDrawable == null) {
            toolbarDrawable = toolbar.getNavigationIcon();
        }
        actionBarDrawerToggle.syncState();

        actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(navigationView)) {
                    drawerLayout.closeDrawer(navigationView);
                } else {
                    Utilities.hideKeyboard(ClassicControlPanelActivity.this);
                    drawerLayout.openDrawer(navigationView);
                }
            }
        });
    }

    /**
     * Create Back button
     *
     * @param title
     */
    public void createBackButton(String title) {
        tvToolbarTitle.setText(title);
        Objects.requireNonNull(getSupportActionBar()).show();
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!actionBarDrawerToggle.isDrawerIndicatorEnabled()) {
                    // When Shows BACK BUTTON
                    popFragment();
                } else {
                    //When Shows MENU BUTTON
                    if (drawerLayout.isDrawerOpen(navigationView)) {
                        drawerLayout.closeDrawer(navigationView);
                    } else {
                        drawerLayout.openDrawer(navigationView);
                    }
                }
            }
        });
    }

    /**
     * Popup Fragment
     */
    public void popFragment() {
        Utilities.hideKeyboard(ClassicControlPanelActivity.this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            //showFragment(new Dashboard(), "DashBoard");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_home; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.notification);
        View view = menuItem.getActionView();
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ClassicControlPanelActivity.this, NotificationActivity.class);
                    startActivity(intent);
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.profile:
                drawerLayout.closeDrawer(GravityCompat.START);
                try {
                    Intent intent = new Intent(ClassicControlPanelActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), ClassicControlPanelActivity.class, "profile");
                }
                break;

            case R.id.logout:
                drawerLayout.closeDrawer(GravityCompat.START);
                DialogUtil.logoutDialog(this, getResources().getString(R.string.logout_text), this);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            Utilities.hideKeyboard(ClassicControlPanelActivity.this);

        } else if (fragmentManager.getBackStackEntryCount() > 0) {
            //mgr.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
            fragmentManager.popBackStack();
        } else {
            if (!doubleBackToExitPressedOnce) {
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            }
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
            }
            doubleBackToExitPressedOnce = true;

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.fingerprint:
                if (isChecked) {
                    Log.e("Clicked ", " fingerprint");
                    if (isSuppotBiometric()) {
                        FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(this);
                        if (fingerprintManagerCompat.hasEnrolledFingerprints()) {
                            // User hasn't enrolled any fingerprints to authenticate with
                            appSession.setBiometricEnabled(true);
                        } else {
                            buttonView.setChecked(false);
                            appSession.setBiometricEnabled(false);
                        }
                    } else {
                        buttonView.setChecked(false);
                        appSession.setBiometricEnabled(false);
                    }
                } else {
                    appSession.setBiometricEnabled(false);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.pin:
                drawerLayout.closeDrawer(GravityCompat.START);
                if (isChecked) {
                    Log.e("Clicked ", " pin >>>>>");
                    if (isOpenDialog) {
                        openPinDialog();
                    }
                } else {
                    appSession.setPinEnabled(false);
                    appSession.setPin("");
                }
                break;

            case R.id.classic:
                drawerLayout.closeDrawer(GravityCompat.START);
                if (isChecked) {
                    appSession.setClassicViewEnabled(true);
                } else {
                    appSession.setClassicViewEnabled(false);
                    Intent intent = new Intent(this, ControlPanelActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    finish();
                }
                break;
        }
    }


    /**
     * Check the device is support BioMetric
     *
     * @return
     */
    private boolean isSuppotBiometric() {
        PackageManager packageManager = this.getPackageManager();
        return packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT);
    }

    /**
     * Open the Pin Dialog
     */
    private void openPinDialog() {
        Dialog dialogLinear = new Dialog(this, R.style.CustomDialogAnimation);
        dialogLinear.setCanceledOnTouchOutside(false);
        dialogLinear.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialogLinear.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialogLinear.setContentView(R.layout.pin_dialog);
        Objects.requireNonNull(dialogLinear.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialogLinear.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        EditText etPin = dialogLinear.findViewById(R.id.et_pin);
        EditText etCnfPin = dialogLinear.findViewById(R.id.et_cnfrm_pin);
        Button btnOk = dialogLinear.findViewById(R.id.btn_ok);

        btnOk.setOnClickListener(v -> {
            // Call next section once the details is saved.

            if (etPin.getText().length() == 4 && etCnfPin.getText().length() == 4) {
                if (etPin.getText().toString().equalsIgnoreCase(etCnfPin.getText().toString())) {
                    Utilities.hideKeyboard(ClassicControlPanelActivity.this);
                    appSession.setPin(etPin.getText().toString());
                    appSession.setPinEnabled(true);
                    dialogLinear.dismiss();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.enter_correct_pin), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, getResources().getString(R.string.enter_pin), Toast.LENGTH_SHORT).show();
            }
        });
        dialogLinear.setOnCancelListener(new DialogInterface.OnCancelListener() {

            public void onCancel(DialogInterface dialog) {
                Log.e("CALLED linear ", ">> dialog >>>> back ");
                Utilities.hideKeyboard(ClassicControlPanelActivity.this);
                if (appSession.isPinEnabled()) {
                    swPin.setChecked(true);
                } else {
                    swPin.setChecked(false);
                }
            }
        });
        dialogLinear.show();
    }

    /**
     * Show the fragments
     *
     * @param targetFragment
     * @param className
     */
    public void showFragment(Fragment targetFragment, String className) {
        this.getSupportFragmentManager().beginTransaction()
                //.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left)
                .replace(R.id.container, targetFragment, className)
                //.addToBackStack(className)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public void dialogCancelled() {

    }

    @Override
    public void onManuallyClicker(int type) {

    }

    @Override
    public void onAddressSelected(AddressBean addressBean) {

    }

    @Override
    public void onCaseSelected(AddressBean addressBean) {

    }

    @Override
    public void onCommunicationSelected(AddressBean addressBean) {

    }

    @Override
    public void onCourtWarrantSelected(AddressBean addressBean) {

    }

    @Override
    public void onDlCheckSelected(PersonBean personBean) {

    }

    @Override
    public void onInvestigationSelected(AddressBean addressBean) {

    }

    @Override
    public void onPersonSelected(PersonBean personBean) {

    }

    @Override
    public void onVehicleSelected(VehicleBean vehicleBean) {

    }

    @Override
    public void onLoggedOut() {
        if (Utilities.getInstance(this).isNetworkAvailable()) {
            mProgressDialog = DialogUtil.showProgressDialog(mContext);
            LogoutRequestModel logoutRequestModel = getLogoutRequest();
            new LogoutApi().callLogout(ClassicControlPanelActivity.this, logoutRequestModel, mProgressDialog);
        } else {
            BasicMethodsUtil.getInstance().showToast(mContext, getString(R.string.no_network));
        }
    }

    /**
     * Create Logout Request
     *
     * @return
     */
    private LogoutRequestModel getLogoutRequest() {

        UserModel userModel = appSession.getUserData();
        LogoutRequestModel logoutRequestModel = new LogoutRequestModel();
        BasedataModel mBasedataBean = new BasedataModel();
        mBasedataBean.setCustomercode(ApiConstants.CUSTOMER_CODE_HCL);
        mBasedataBean.setProcess(ApiConstants.PROCESS_ACCOUNT);
        mBasedataBean.setSubprocessname(ApiConstants.SUB_PROCESS_UPDATE);
        mBasedataBean.setFunction(ApiConstants.METHOD_SIGN_OUT);
        logoutRequestModel.setBasedata(mBasedataBean);
        if (userModel != null) {
            logoutRequestModel.setUserId(userModel.getUserid());
        }

        return logoutRequestModel;
    }
}