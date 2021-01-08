package hcl.policing.digitalpolicingplatform.activities.fdssearch;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.database.ProcessSubProcessMapper;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.SubProcessDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.address.AddressCustomDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.cases.CasesDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.communications.CommunicationDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.courtwarrant.CourtWarrantDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.dlcheck.DLCheckDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.investigation.InvestigationDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.person.PersonCustomDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.vehicle.VehicleCustomDialogFragment;
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
import hcl.policing.digitalpolicingplatform.models.process.ResponseModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.AddressBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.PersonBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.VehicleBean;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class FDSSearchActivity extends BaseActivity implements View.OnClickListener, PersonSelectionListener, ManuallyClickListener,
        VehicleSelectionListener, AddressSelectionListener, CaseSelectionListener, InvestigationSelectionListener, CommunicationSelectionListener,
        CourtWarrantSelectionListener, DlCheckSelectionListener, DialogCancelListener {

    private static String TAG = ProcessCreationActivity.class.getName();
    private Context mContext;
    private String jsonFileName = "";
    private String processName = "";
    private AppSession appSession;
    public Dialog mProgressDialog;
    private String captureValue = "Peter Parker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.fds_search_activity);
        appSession = new AppSession(mContext);

        jsonFileName = getIntent().getStringExtra(GenericConstant.JSON_FILE_NAME);
        processName = getIntent().getStringExtra(GenericConstant.PROCESS_NAME);

        initView();
        appSession = new AppSession(mContext);
    }

    /**
     * Initialize the view
     */
    private void initView() {

        TextView tvPerson = findViewById(R.id.tvPerson);
        TextView tvVehicle = findViewById(R.id.tvVehicle);
        TextView tvAddress = findViewById(R.id.tvAddress);
        TextView tvInvestigation = findViewById(R.id.tvInvestigation);
        TextView tvCases = findViewById(R.id.tvCases);
        TextView tvDLCheck = findViewById(R.id.tvDLCheck);
        TextView tvCommunication = findViewById(R.id.tvCommunication);
        TextView tvCourtWarrant = findViewById(R.id.tvCourtWarrant);
        ImageView ivBack = findViewById(R.id.ivBack);

        tvPerson.setOnClickListener(this);
        tvVehicle.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
        tvInvestigation.setOnClickListener(this);
        tvCases.setOnClickListener(this);
        tvDLCheck.setOnClickListener(this);
        tvCommunication.setOnClickListener(this);
        tvCourtWarrant.setOnClickListener(this);
        ivBack.setOnClickListener(this);

    }

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
                    }
                });
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "loadJson");
            }
        }).start();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tvPerson:
                loadPersonDialog();
                break;

            case R.id.tvVehicle:
                loadVehicleDialog();
                break;

            case R.id.tvAddress:
                loadAddressDialog();
                break;

            case R.id.tvInvestigation:
                loadInvestigationDialog();
                break;

            case R.id.tvCases:
                loadCasesDialog();
                break;

            case R.id.tvDLCheck:
                loadDLCheckDialog();
                break;

            case R.id.tvCommunication:
                loadCommunicationDialog();
                break;

            case R.id.tvCourtWarrant:
                loadCourtWarrantDialog();
                break;
            case R.id.ivBack:

                finish();

                break;
        }
    }

    /**
     * Load Person Dialog
     */
    public void loadPersonDialog() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.PERSON_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        PersonCustomDialogFragment personCustomDialogFragment = PersonCustomDialogFragment.newInstance(captureValue, "", false);
        personCustomDialogFragment.show(ft, GenericConstant.PERSON_LIST_DIALOG);
    }


    /**
     * Load Vehicle Dialog
     */
    public void loadVehicleDialog() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.VEHICLE_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        VehicleCustomDialogFragment vehicleCustomDialogFragment = VehicleCustomDialogFragment.newInstance(captureValue, "", false);
        vehicleCustomDialogFragment.show(ft, GenericConstant.VEHICLE_LIST_DIALOG);
    }


    /**
     * Load Address Dialog
     */
    public void loadAddressDialog() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.ADDRESS_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        AddressCustomDialogFragment addressCustomDialogFragment = AddressCustomDialogFragment.newInstance(captureValue, "", false);
        addressCustomDialogFragment.show(ft, GenericConstant.ADDRESS_LIST_DIALOG);
    }

    /**
     * Load CASE Dialog
     */
    public void loadCasesDialog() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.CASE_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        CasesDialogFragment casesDialogFragment = CasesDialogFragment.newInstance(captureValue, "", false);
        casesDialogFragment.show(ft, GenericConstant.CASE_LIST_DIALOG);
    }

    /**
     * Load Communication Dialog
     */
    public void loadCommunicationDialog() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.COMMUNICATION_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        CommunicationDialogFragment communicationDialogFragment = CommunicationDialogFragment.newInstance(captureValue, "", false);
        communicationDialogFragment.show(ft, GenericConstant.COMMUNICATION_LIST_DIALOG);
    }

    /**
     * Load Court Warrant Dialog
     */
    public void loadCourtWarrantDialog() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.COURTWARRANT_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        CourtWarrantDialogFragment courtWarrantDialogFragment = CourtWarrantDialogFragment.newInstance(captureValue, "", false);
        courtWarrantDialogFragment.show(ft, GenericConstant.COURTWARRANT_LIST_DIALOG);
    }

    /**
     * Load Court Warrant Dialog
     */
    public void loadInvestigationDialog() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.INVESTIGATION_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        InvestigationDialogFragment investigationDialogFragment = InvestigationDialogFragment.newInstance(captureValue, "", false);
        investigationDialogFragment.show(ft, GenericConstant.INVESTIGATION_LIST_DIALOG);
    }


    /**
     * Load DL Check Dialog
     */
    public void loadDLCheckDialog() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.DLCHECK_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DLCheckDialogFragment dlCheckDialogFragment = DLCheckDialogFragment.newInstance(captureValue, this, false);
        dlCheckDialogFragment.show(ft, GenericConstant.DLCHECK_LIST_DIALOG);
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
    public void onVehicleSelected(VehicleBean vehicleBean) {

    }

    @Override
    public void onCaseSelected(AddressBean addressBean) {

    }

    @Override
    public void onInvestigationSelected(AddressBean addressBean) {

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
    public void dialogCancelled() {

    }
}
