package hcl.policing.digitalpolicingplatform.activities.controlPannel;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import androidx.biometric.BiometricManager;
import androidx.core.content.ContextCompat;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Objects;

import hcl.policing.digitalpolicingplatform.BuildConfig;
import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.activities.micSearch.MicSearchActivity;
import hcl.policing.digitalpolicingplatform.activities.moduleShortcut.ModuleShortcut;
import hcl.policing.digitalpolicingplatform.activities.pocketbook.PocketbookLogActivity;
import hcl.policing.digitalpolicingplatform.activities.profile.ChangePasswordActivity;
import hcl.policing.digitalpolicingplatform.activities.profile.ProfileActivity;
import hcl.policing.digitalpolicingplatform.adapters.controlPannel.ControlPanelViewPagerAdapter;
import hcl.policing.digitalpolicingplatform.adapters.controlPannel.DashboardIconsAdapter;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.MovableFloatingActionButton;
import hcl.policing.digitalpolicingplatform.database.DatabaseHelper;
import hcl.policing.digitalpolicingplatform.database.ProcessSubProcessMapper;
import hcl.policing.digitalpolicingplatform.fragments.controlPannel.ControlPannelFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.SubProcessDialogFragment;
import hcl.policing.digitalpolicingplatform.listeners.LogoutListener;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
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
import hcl.policing.digitalpolicingplatform.utils.Base64ToBitmapUtil;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

import static android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

public class ControlPanelActivity extends BaseActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener, CompoundButton.OnCheckedChangeListener, PersonSelectionListener,
        ManuallyClickListener, DialogCancelListener, VehicleSelectionListener, AddressSelectionListener, CaseSelectionListener,
        InvestigationSelectionListener, CommunicationSelectionListener, CourtWarrantSelectionListener, DlCheckSelectionListener, LogoutListener {

    private AppSession appSession;
    BiometricManager biometricManager;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private static Drawable toolbarDrawable = null;
    private Toolbar toolbar;
    public ImageView ivNavProfile;
    public TextView tvNavName;
    private TextView tvToolbarTitle;
    private boolean doubleBackToExitPressedOnce = false;
    private ViewPager viewPager;
    private ControlPanelViewPagerAdapter viewPagerAdapter;
    private RecyclerView rvIcons;
    private DashboardIconsAdapter dashboardIconsAdapter;
    private boolean isOpenDialog = false;
    private SwitchCompat swFingerprint, swPin, swClassic;
    private Context mContext;
    public DatabaseHelper db;
    private ArrayList<ProcessSubProcessMapper> aProcessSubProcessMapper;
    public Dialog mProgressDialog;
    private ArrayList<String> dirList;
    private String directoryOffline = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initDatabaseHelper();
        setContentView(R.layout.activity_main);
        appSession = new AppSession(this);
        biometricManager = BiometricManager.from(this);
        setToolbar();
        initView();

        directoryOffline = GenericConstant.FILE_OFFLINE /*+ getResources().getString(R.string.tor_creation) + "/"*/;

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
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
     * Setup the Tool bar
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
     * initiliase the database
     */
    private void initDatabaseHelper() {
        if (db == null) {
            db = new DatabaseHelper(this);
        }
    }

    /**
     * Initialize the view
     */
    private void initView() {
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        Menu menu = navigationView.getMenu();
        MenuItem itemVersion = menu.findItem(R.id.version);
        itemVersion.setTitle(getString(R.string.version) + " - " + BuildConfig.VERSION_NAME);
        tvNavName = header.findViewById(R.id.tv_name);
        ivNavProfile = header.findViewById(R.id.iv_profile);
        drawerLayout = findViewById(R.id.drawer_layout);
        tvToolbarTitle = toolbar.findViewById(R.id.tv_toolbar_title);

        swFingerprint = (SwitchCompat) navigationView.getMenu().findItem(R.id.fingerprint).getActionView();
        swPin = (SwitchCompat) navigationView.getMenu().findItem(R.id.pin).getActionView();
        swClassic = (SwitchCompat) navigationView.getMenu().findItem(R.id.classic).getActionView();

        loadProfilePic();

        swFingerprint.setOnCheckedChangeListener(this);

        swPin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isOpenDialog = true;
                return false;
            }
        });
        swPin.setOnCheckedChangeListener(this);
        swClassic.setOnCheckedChangeListener(this);

        MovableFloatingActionButton btnMic = findViewById(R.id.btn_mic);
        btnMic.setOnClickListener(this);

        rvIcons = findViewById(R.id.rv_icons);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvIcons.setLayoutManager(mLayoutManager);
        rvIcons.setItemAnimator(new DefaultItemAnimator());

        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new ControlPanelViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
        viewPager.setCurrentItem(1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    tvToolbarTitle.setText(getResources().getString(R.string.control_pannel));
                } else if (position == 1) {
                    tvToolbarTitle.setText(getResources().getString(R.string.my_dashboard));
                } else if (position == 2) {
                    tvToolbarTitle.setText(getResources().getString(R.string.media));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        aProcessSubProcessMapper = db.getShortcutProcessList(GenericConstant.SHORTCUT_YES);
        if (aProcessSubProcessMapper == null || aProcessSubProcessMapper.size() == 0) {
            aProcessSubProcessMapper = db.getProcessList();
        }

        if (aProcessSubProcessMapper != null && aProcessSubProcessMapper.size() > 0) {
            dashboardIconsAdapter = new DashboardIconsAdapter(this, aProcessSubProcessMapper, onClickIcon);
            rvIcons.setAdapter(dashboardIconsAdapter);
        }
    }

    /**
     * Load profile Pic
     */
    private void loadProfilePic() {
        if (userModel != null) {
            //decode base64 string to image
            Bitmap decodedImage = Base64ToBitmapUtil.base64ToBitmap(userModel.getUserimage());
            ivNavProfile.setImageBitmap(decodedImage);
//            Picasso.get().load(userModel.getUserimage()).error(R.drawable.ic_person).placeholder(R.drawable.ic_person).into(ivNavProfile);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mic:
                BasicMethodsUtil.getInstance().launchActivity(this, MicSearchActivity.class);
                break;
        }
    }

    /**
     * Item Click listener
     */
    private OnItemClickListener.OnItemClickCallback onClickIcon = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                int processId = aProcessSubProcessMapper.get(position).getProcessId();
                String processName = aProcessSubProcessMapper.get(position).getProcessName();
                loadSubProcessDialog(processId, processName);

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), ControlPanelActivity.class, "onClickItem");
            }
        }
    };

    /**
     * Create Menu Button
     *
     * @param title
     */
    public void createMenuButton(String title) {

        getSupportActionBar().setElevation(0);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
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
                    Utilities.hideKeyboard(ControlPanelActivity.this);
                    drawerLayout.openDrawer(navigationView);
                }
            }
        });
    }

    /**
     * Create the Back Button
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
     * Pop up Fragment
     */
    public void popFragment() {
        Utilities.hideKeyboard(ControlPanelActivity.this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
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
                    //Intent intent = new Intent(ControlPanelActivity.this, NotificationActivity.class);
                    //startActivity(intent);
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
                    Intent intent = new Intent(ControlPanelActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), ControlPanelActivity.class, "profile");
                }
                break;

            case R.id.module:
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(ControlPanelActivity.this, ModuleShortcut.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;

            case R.id.changepassword:
                drawerLayout.closeDrawer(GravityCompat.START);
                try {
                    Intent intentPassword = new Intent(ControlPanelActivity.this, ChangePasswordActivity.class);
                    startActivity(intentPassword);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), ControlPanelActivity.class, "profile");
                }

                break;

            case R.id.offline:
                drawerLayout.closeDrawer(GravityCompat.START);
                getOffline();
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
            Utilities.hideKeyboard(ControlPanelActivity.this);

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
                    switch (biometricManager.canAuthenticate()) {
                        case BiometricManager.BIOMETRIC_SUCCESS:
                            Log.d("MY_APP_TAG", "App can authenticate using biometrics.");
                            appSession.setBiometricEnabled(true);
                            break;
                        case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                            Log.e("MY_APP_TAG", "No biometric features available on this device.");
                            buttonView.setChecked(false);
                            appSession.setBiometricEnabled(false);
                            break;
                        case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                            Log.e("MY_APP_TAG", "Biometric features are currently unavailable.");
                            buttonView.setChecked(false);
                            appSession.setBiometricEnabled(false);
                            break;
                        case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                            // Prompts the user to create credentials that your app accepts.
                            final Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                            enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                                    BIOMETRIC_STRONG | DEVICE_CREDENTIAL);
                            startActivityForResult(enrollIntent, GenericConstant.BIOMETRIC_REQUEST);
                            buttonView.setChecked(false);
                            appSession.setBiometricEnabled(false);
                            break;
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
                    Intent intent = new Intent(this, ClassicControlPanelActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    finish();
                } else {
                    appSession.setClassicViewEnabled(false);
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
        ImageView ivShowPin = dialogLinear.findViewById(R.id.ivShowPin);
        ImageView ivShowConfirmPin = dialogLinear.findViewById(R.id.ivShowConfirmPin);

        Button btnOk = dialogLinear.findViewById(R.id.btn_ok);


        etPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    ivShowPin.setVisibility(View.VISIBLE);
                    ivShowPin.setImageResource(R.drawable.ic_visibility_off);

                } else {
                    ivShowPin.setVisibility(View.GONE);
                    etPin.setInputType(129);
                    ivShowPin.setImageResource(R.drawable.ic_visibility);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        etCnfPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    ivShowConfirmPin.setVisibility(View.VISIBLE);
                    ivShowConfirmPin.setImageResource(R.drawable.ic_visibility_off);

                } else {
                    ivShowConfirmPin.setVisibility(View.GONE);
                    etPin.setInputType(129);
                    ivShowConfirmPin.setImageResource(R.drawable.ic_visibility);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ivShowPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPin.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    etPin.setInputType(129);
                    ivShowPin.setImageResource(R.drawable.ic_visibility);
                    etPin.setSelection(etPin.getText().length());
                } else {
                    etPin.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivShowPin.setImageResource(R.drawable.ic_visibility_off);
                    etPin.setSelection(etPin.getText().length());
                }
            }
        });

        ivShowConfirmPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCnfPin.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    etCnfPin.setInputType(129);
                    ivShowConfirmPin.setImageResource(R.drawable.ic_visibility);
                    etCnfPin.setSelection(etCnfPin.getText().length());
                } else {
                    etCnfPin.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivShowConfirmPin.setImageResource(R.drawable.ic_visibility_off);
                    etCnfPin.setSelection(etCnfPin.getText().length());
                }
            }
        });

        btnOk.setOnClickListener(v -> {
            // Call next section once the details is saved.

            if (etPin.getText().length() == 4 && etCnfPin.getText().length() == 4) {
                if (etPin.getText().toString().equalsIgnoreCase(etCnfPin.getText().toString())) {
                    Utilities.hideKeyboard(ControlPanelActivity.this);
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
                Utilities.hideKeyboard(ControlPanelActivity.this);
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
     * Load Sub Process Dialog
     */
    private void loadSubProcessDialog(int processId, String processName) {
        try {
            ArrayList<ProcessSubProcessMapper> processSubProcessMappers = db.getSubProcessList(processName, processId);
            if (processSubProcessMappers != null) {
                if (processSubProcessMappers.size() > 1) {

                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.SUB_PROCESS_LIST_DIALOG);
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);
                    // Create and show the dialog.
                    SubProcessDialogFragment subProcessDialogFragment = SubProcessDialogFragment.newInstance(processSubProcessMappers);
                    subProcessDialogFragment.show(ft, GenericConstant.SUB_PROCESS_LIST_DIALOG);

                } else if (processSubProcessMappers.size() == 1) {
                    int subProcessId = processSubProcessMappers.get(0).getSubProcessId();
                    String flowStr = isProcessFlow(subProcessId);
                    Log.e("FLOW STRING ", " CONTROL PANEL >>>>> " + flowStr);
                    if (!TextUtils.isEmpty(flowStr)) {
                        loadFlow(processName, flowStr);
                    } else {
                        getProcessFlowApi(processId, subProcessId);
                    }
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ControlPannelFragment.class, "loadSubProcessDialog");
        }

    }

    /**
     * Load Sub Process Dialog
     */
    /*public void loadSubProcessDialog(int processId, String processName) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<ProcessSubProcessMapper> processSubProcessMappers = db.getSubProcessList(processName, processId);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (processSubProcessMappers != null) {
                                if (processSubProcessMappers.size() > 1) {

                                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                    Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.SUB_PROCESS_LIST_DIALOG);
                                    if (prev != null) {
                                        ft.remove(prev);
                                    }
                                    ft.addToBackStack(null);

                                    // Create and show the dialog.
                                    SubProcessDialogFragment subProcessDialogFragment = SubProcessDialogFragment.newInstance(processSubProcessMappers);
                                    subProcessDialogFragment.show(ft, GenericConstant.SUB_PROCESS_LIST_DIALOG);

                                } else if (processSubProcessMappers.size() == 1) {

                                    if (processName.equalsIgnoreCase("Search")) {
                                        appSession.setImageList(null);
                                        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.FDS_PROCESS);
                                        Intent intent = new Intent(ControlPanelActivity.this, FDSSearchActivity.class);
                                        intent.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.TOR_PROCESS_JSON);
                                        intent.putExtra(GenericConstant.PROCESS_NAME, getResources().getString(R.string.fds_search));
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                                    } else if (processName.equalsIgnoreCase("TOR")) {
                                        appSession.setImageList(null);
                                        ProcessCreationActivity.SECTION_COUNT = 0;
                                        ProcessCreationActivity.QUESTION_COUNT = 0;
                                        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);
                                        Intent intent = new Intent(ControlPanelActivity.this, ProcessCreationActivity.class);
                                        intent.putExtra(GenericConstant.FILE_NAME_DRAFT, "");
                                        intent.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.TOR_PROCESS_JSON);
                                        intent.putExtra(GenericConstant.PROCESS_NAME, getResources().getString(R.string.tor_creation));
                                        intent.putExtra(GenericConstant.JSON_OBJECT, "");
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                                    } else if (processName.equalsIgnoreCase("Stops & Search")) {

                                        appSession.setImageList(null);
                                        ProcessCreationActivity.SECTION_COUNT = 0;
                                        ProcessCreationActivity.QUESTION_COUNT = 0;
                                        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.STOPNSEARCH_PROCESS);
                                        Intent intent1 = new Intent(ControlPanelActivity.this, ProcessCreationActivity.class);
                                        intent1.putExtra(GenericConstant.FILE_NAME_DRAFT, "");
                                        intent1.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.STOPNSEARCH_JSON);
                                        intent1.putExtra(GenericConstant.PROCESS_NAME, getResources().getString(R.string.stop_process));
                                        intent1.putExtra(GenericConstant.JSON_OBJECT, "");
                                        startActivity(intent1);
                                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                                    } else if (processName.equalsIgnoreCase("Crime")) {

                                        appSession.setImageList(null);
                                        ProcessCreationActivity.SECTION_COUNT = 0;
                                        ProcessCreationActivity.QUESTION_COUNT = 0;
                                        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);
                                        Intent intent1 = new Intent(ControlPanelActivity.this, ProcessCreationActivity.class);
                                        intent1.putExtra(GenericConstant.FILE_NAME_DRAFT, "");
                                        intent1.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.CRIME_PROCESS_JSON);
                                        intent1.putExtra(GenericConstant.PROCESS_NAME, getResources().getString(R.string.crime));
                                        intent1.putExtra(GenericConstant.JSON_OBJECT, "");
                                        startActivity(intent1);
                                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                                    } else if (processName.equalsIgnoreCase("Domestic Abuse")) {

                                        appSession.setImageList(null);
                                        ProcessCreationActivity.SECTION_COUNT = 0;
                                        ProcessCreationActivity.QUESTION_COUNT = 0;
                                        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);
                                        Intent intent1 = new Intent(ControlPanelActivity.this, ProcessCreationActivity.class);
                                        intent1.putExtra(GenericConstant.FILE_NAME_DRAFT, "");
                                        intent1.putExtra(GenericConstant.JSON_OBJECT, "");
                                        intent1.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.DOMESTIC_PROCESS_JSON);
                                        intent1.putExtra(GenericConstant.PROCESS_NAME, getResources().getString(R.string.domestic_abuse));
                                        startActivity(intent1);
                                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                                    } else if (processName.equalsIgnoreCase("Witness Statement")) {
                                        appSession.setImageList(null);
                                        ProcessCreationActivity.SECTION_COUNT = 0;
                                        ProcessCreationActivity.QUESTION_COUNT = 0;
                                        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);
                                        Intent intent = new Intent(ControlPanelActivity.this, ProcessCreationActivity.class);
                                        intent.putExtra(GenericConstant.FILE_NAME_DRAFT, "");
                                        intent.putExtra(GenericConstant.JSON_OBJECT, "");
                                        intent.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.WITNESS_STATEMENT_JSON);
                                        intent.putExtra(GenericConstant.PROCESS_NAME, getResources().getString(R.string.witness_statement));
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                                    } else if (processName.equalsIgnoreCase("Pocket Book")) {
                                        appSession.setImageList(null);
                                        ProcessCreationActivity.SECTION_COUNT = 0;
                                        ProcessCreationActivity.QUESTION_COUNT = 0;
                                        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);
                                        Intent intent = new Intent(ControlPanelActivity.this, ProcessCreationActivity.class);
                                        intent.putExtra(GenericConstant.FILE_NAME_DRAFT, "");
                                        intent.putExtra(GenericConstant.JSON_OBJECT, "");
                                        intent.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.POCKET_BOOK_JSON);
                                        intent.putExtra(GenericConstant.PROCESS_NAME, getResources().getString(R.string.pocket_book));
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                                    } else if (processName.equalsIgnoreCase("Incident")) {
                                        appSession.setImageList(null);
                                        ProcessCreationActivity.SECTION_COUNT = 0;
                                        ProcessCreationActivity.QUESTION_COUNT = 0;
                                        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);
                                        Intent intent = new Intent(ControlPanelActivity.this, ProcessCreationActivity.class);
                                        intent.putExtra(GenericConstant.FILE_NAME_DRAFT, "");
                                        intent.putExtra(GenericConstant.JSON_OBJECT, "");
                                        intent.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.INCIDENT_JSON);
                                        intent.putExtra(GenericConstant.PROCESS_NAME, getResources().getString(R.string.incident));
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                                    } else if (processName.equalsIgnoreCase("Intelligence")) {
                                        appSession.setImageList(null);
                                        ProcessCreationActivity.SECTION_COUNT = 0;
                                        ProcessCreationActivity.QUESTION_COUNT = 0;
                                        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);
                                        Intent intent = new Intent(ControlPanelActivity.this, ProcessCreationActivity.class);
                                        intent.putExtra(GenericConstant.FILE_NAME_DRAFT, "");
                                        intent.putExtra(GenericConstant.JSON_OBJECT, "");
                                        intent.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.INTELLIGENCE_JSON);
                                        intent.putExtra(GenericConstant.PROCESS_NAME, getResources().getString(R.string.intelligence));
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                                    } else if (processName.equalsIgnoreCase("Problem Solving")) {
                                        appSession.setImageList(null);
                                        ProcessCreationActivity.SECTION_COUNT = 0;
                                        ProcessCreationActivity.QUESTION_COUNT = 0;
                                        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);
                                        Intent intent = new Intent(ControlPanelActivity.this, ProcessCreationActivity.class);
                                        intent.putExtra(GenericConstant.FILE_NAME_DRAFT, "");
                                        intent.putExtra(GenericConstant.JSON_OBJECT, "");
                                        intent.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.PROBLEM_SOLVING_JSON);
                                        intent.putExtra(GenericConstant.PROCESS_NAME, getResources().getString(R.string.problem_solving));
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                                    }
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), ControlPannelFragment.class, "loadSubProcessDialog");
                }
            }
        }).start();
    }*/

    @Override
    public void dialogCancelled() {

    }

    @Override
    public void onManuallyClicker(int type) {

    }

    @Override
    public void onPersonSelected(PersonBean personBean) {

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
    public void onVehicleSelected(VehicleBean vehicleBean) {

    }

    @Override
    public void onLoggedOut() {
        if (Utilities.getInstance(this).isNetworkAvailable()) {
            mProgressDialog = DialogUtil.showProgressDialog(mContext);
            LogoutRequestModel logoutRequestModel = getLogoutRequest();
            new LogoutApi().callLogout(ControlPanelActivity.this, logoutRequestModel, mProgressDialog);
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
        mBasedataBean.setCustomercode("hcl");
        mBasedataBean.setProcess("Account");
        mBasedataBean.setSubprocessname("Update");
        mBasedataBean.setFunction("Signout");
        logoutRequestModel.setBasedata(mBasedataBean);
        if (userModel != null) {
            logoutRequestModel.setUserId(userModel.getUserid());
        }

        return logoutRequestModel;
    }

    private void getOffline() {
        /*try {
            dirList = new ArrayList<>();
            dirList = Utilities.getInstance(this).getDirectoryList(directoryOffline);

            int count = 0;

            if(dirList != null && dirList.size() > 0) {
                for (int i = 0; i < dirList.size(); i++) {
                    count = count + Utilities.getInstance(this).getNumberOfFiles(directoryOffline + dirList.get(i)+ "/");
                }
            }
            if(count > 0) {
                Intent intent = new Intent(ControlPanelActivity.this, OfflineActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        } catch (FileNotFoundException e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ControlPanelMainFrafment.class, "getDraft");
        }*/
        Intent intent = new Intent(ControlPanelActivity.this, PocketbookLogActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }
}