package hcl.policing.digitalpolicingplatform.activities.profile;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.constants.api.ApiConstants;
import hcl.policing.digitalpolicingplatform.models.login.BasedataModel;
import hcl.policing.digitalpolicingplatform.models.process.officer.UpdateUserRequestModel;
import hcl.policing.digitalpolicingplatform.models.process.officer.UserModel;
import hcl.policing.digitalpolicingplatform.models.process.officer.UserRequestModel;
import hcl.policing.digitalpolicingplatform.networks.profile.UpdatedUserApi;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class ProfileEditActivity extends BaseActivity implements View.OnClickListener {

    private AppSession appSession;
    private Context mContext;
    public Dialog mProgressDialog;
    private EditText etEmail, etMobile, etUserId, etDob, etCollarId, etJoiningDate, etSupervisorCollar, etDirectorate,
            etDivision, etDepartment, etArea, etStation, etTeam, etLocation;
    private Spinner spinnerGender;
    private String[] genderList = {"Select Gender", "Male", "Female"};
    private String selectedGender;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.profile_edit_activity);
        appSession = new AppSession(mContext);
        initView();
    }

    private void initView() {
        ImageView ivBack = findViewById(R.id.iv_back);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        etEmail = findViewById(R.id.etEmail);
        etMobile = findViewById(R.id.etMobile);
        spinnerGender = findViewById(R.id.spinnerGender);
        etDob = findViewById(R.id.etDob);
        etUserId = findViewById(R.id.etUserId);
        etCollarId = findViewById(R.id.etCollarId);
        etJoiningDate = findViewById(R.id.etJoiningDate);
        etSupervisorCollar = findViewById(R.id.etSupervisorCollar);
        etDirectorate = findViewById(R.id.etDirectorate);
        etDivision = findViewById(R.id.etDivision);
        etDepartment = findViewById(R.id.etDepartment);
        etArea = findViewById(R.id.etArea);
        etStation = findViewById(R.id.etStation);
        etTeam = findViewById(R.id.etTeam);
        etLocation = findViewById(R.id.etLocation);

        etDob.setInputType(InputType.TYPE_NULL);
        etJoiningDate.setInputType(InputType.TYPE_NULL);
        etDob.setFocusable(true);
        etJoiningDate.setFocusable(true);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genderList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(dataAdapter);

        setData();

        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGender = genderList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etDob.setOnClickListener(this);
        etJoiningDate.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }


    /**
     * Set Data
     */
    private void setData() {
        UserModel userModel = appSession.getUserData();

        if (userModel != null) {
            etEmail.setText(userModel.getEmail());
            etMobile.setText(userModel.getMobile());

            String gender = userModel.getGender();
            if (gender.equalsIgnoreCase("Male")) {
                spinnerGender.setSelection(1);
            } else {
                spinnerGender.setSelection(2);
            }

            etUserId.setText(String.valueOf(userModel.getUserid()));
            etDob.setText(userModel.getDateofbirth());
            etCollarId.setText(userModel.getUsername());
            etJoiningDate.setText(userModel.getDateofjoining());
            etSupervisorCollar.setText(userModel.getSupserviceno());
            etDirectorate.setText(userModel.getDirectorate());
            etDivision.setText(userModel.getDivision());
            etDepartment.setText(userModel.getDepartment());
            etArea.setText(userModel.getArea());
            etStation.setText(userModel.getStation());
            etTeam.setText(userModel.getTeam());
            etLocation.setText(userModel.getArea());
        }
    }

    /**
     * Call the api for reset the password
     */
    private void updateUserDetailApi() {
        if (Utilities.getInstance(this).isNetworkAvailable()) {
            mProgressDialog = DialogUtil.showProgressDialog(mContext);
            UpdateUserRequestModel updateUserRequestModel = getUserUpdateRequest();
            new UpdatedUserApi().updateUser(ProfileEditActivity.this, updateUserRequestModel);
        } else {
            BasicMethodsUtil.getInstance().showToast(mContext, getString(R.string.no_network));
        }
    }

    /**
     * Create User Update Request
     *
     * @return
     */
    private UpdateUserRequestModel getUserUpdateRequest() {

        UpdateUserRequestModel updateUserRequestModel = new UpdateUserRequestModel();
        UserModel userModel = appSession.getUserData();
        if (userModel != null) {
            BasedataModel mBasedataBean = new BasedataModel();
            mBasedataBean.setCustomercode(userModel.getCustomercode());
            mBasedataBean.setProcess(ApiConstants.PROCESS_USER);
            mBasedataBean.setSubprocessname(ApiConstants.SUB_PROCESS_UPDATE);
            mBasedataBean.setFunction(ApiConstants.METHOD_UPDATE_USER);
            updateUserRequestModel.setBasedata(mBasedataBean);

            UserRequestModel userRequestModel = new UserRequestModel();

            userRequestModel.setUserid(userModel.getUserid());
            userRequestModel.setCustomerid(userModel.getCustomerid());
            userRequestModel.setHintquestion(userModel.getHintquestion());
            userRequestModel.setHintanswer(userModel.getHintanswer());
            userRequestModel.setStatus(userModel.getStatus());
            userRequestModel.setUsertype(userModel.getUsertype());
            userRequestModel.setWrntno(userModel.getWrntno());
            userRequestModel.setEmail(etEmail.getText().toString().trim());
            userRequestModel.setMobile(etMobile.getText().toString().trim());
            userRequestModel.setRank(userModel.getRank());
            userRequestModel.setUserimage(userModel.getUserimage());
            userRequestModel.setForename(userModel.getForename());
            userRequestModel.setSurname(userModel.getSurname());
            userRequestModel.setSupforcecode(userModel.getSupforcecode());
            userRequestModel.setSupserviceno(userModel.getSupserviceno());
            userRequestModel.setGender(selectedGender);
            userRequestModel.setDateofbirth(userModel.getDateofbirth());
            userRequestModel.setDateofjoining(userModel.getDateofjoining());
            userRequestModel.setSupforcecode(userModel.getSupforcecode());
            userRequestModel.setDepartment(etDepartment.getText().toString().trim());
            userRequestModel.setDivision(etDivision.getText().toString().trim());
            userRequestModel.setArea(etArea.getText().toString().trim());
            userRequestModel.setStation(etStation.getText().toString().trim());
            userRequestModel.setTeam(etTeam.getText().toString().trim());
            userRequestModel.setDirectorate(etDirectorate.getText().toString().trim());
            userRequestModel.setUsername(userModel.getUsername());

            List<UserModel.ImeiNumbersBean> aImeiNumbersBean = userModel.getImeinumbers();
            List<UserRequestModel.ImeiNumbersBean> imeiNumberlist = new ArrayList<>();
            imeiNumberlist.clear();
            if (aImeiNumbersBean!=null && aImeiNumbersBean.size()>0){
                for (int i = 0; i < aImeiNumbersBean.size(); i++) {

                    UserRequestModel.ImeiNumbersBean imeiNumbersBean = new UserRequestModel.ImeiNumbersBean();
                    imeiNumbersBean.setImeinumber(aImeiNumbersBean.get(i).getImeinumber());
                    imeiNumbersBean.setImeinumberid(aImeiNumbersBean.get(i).getImeinumberid());
                    imeiNumberlist.add(imeiNumbersBean);
                }
                userRequestModel.setImeinumbers(imeiNumberlist);
            }
            updateUserRequestModel.setUser(userRequestModel);
        }
        return updateUserRequestModel;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.etDob:
                showDateDialog(etDob);
                break;
            case R.id.etJoiningDate:
                showDateDialog(etDob);
                break;
            case R.id.iv_back:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.btnSubmit:
                updateUserDetailApi();
                break;
        }
    }

    /**
     * Show date pick dialog
     *
     * @param editText
     */
    private void showDateDialog(EditText editText) {
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        final Calendar newCalendar = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editText.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
