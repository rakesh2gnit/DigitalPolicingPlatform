package hcl.policing.digitalpolicingplatform.fragments.process.fds.person.athena;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.EntryLogModel;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;

public class AddEnquiryDialogFragment extends DialogFragment implements View.OnClickListener {

    private Context mContext;
    private Button btnAdd, btnCancel;
    private Spinner spinnerReason;
    private TextInputEditText etLogEntry;
    private LinearLayout llDetails;
    private HorizontalScrollView llSteps;
    private ImageView ivBack;
    private TextView tvHeader, tvStepOne, tvStepTwo, tvStepThree, tvStepFour;
    private Dialog mProgressDialog;
    private EntryLogModel mEntryLogModel;

    private String[] reasonList = {"ASB Related Entry", "Additonal Information for IMU", "Bad Character",
            "C&C Source System", "CCTV", "Closing Report", "DDM / FCR Comments", "Evidential Review", "Financial Enquiry", "Foriensic",
            "House 2 House", "Information In/Received", "Investigation Summary", "Investigation Copy", "Linking Releationship", "Local Enquiries",
            "PVR Related Entry", "PVR Safeguarding/Planning Issue", "Property", "Property Entry-Further Information", "Scene Management",
            "Solvability Assessment", "Suspect Related Entry", "Victim Related Entry", "Witness Related Entry"};

    private String selectedReason, investId;
    private boolean isPopulate;

    public static AddEnquiryDialogFragment newInstance(String id, boolean isPopulate) {

        AddEnquiryDialogFragment frag = new AddEnquiryDialogFragment();
        Bundle args = new Bundle();
        args.putString(GenericConstant.INVEST_ID, id);
        args.putBoolean(GenericConstant.IS_POPULATE, isPopulate);
        frag.setArguments(args);
        return frag;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.athena_add_enquiry_log_fragment, container, false);

        if (getArguments() != null) {
            investId = getArguments().getString(GenericConstant.INVEST_ID);
            isPopulate = getArguments().getBoolean(GenericConstant.IS_POPULATE);
        }
        initView(rootView);

        return rootView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().getAttributes().windowAnimations = R.style.custom_dialog;
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }


    /**
     * Initialize the view
     *
     * @param view
     */
    private void initView(View view) {

        llDetails = view.findViewById(R.id.llDetails);
        llSteps = view.findViewById(R.id.llSteps);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnCancel = view.findViewById(R.id.btnCancel);
        ivBack = view.findViewById(R.id.ivBack);

        spinnerReason = view.findViewById(R.id.spinnerReason);
        etLogEntry = view.findViewById(R.id.etLogEntry);

        isPopulateView();
        loadStepLayout();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, reasonList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReason.setAdapter(dataAdapter);


        spinnerReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedReason = reasonList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ivBack.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

    }

    /**
     * Check the condition for populate view.
     */
    private void isPopulateView() {
        if (!isPopulate) {
            LinearLayout.LayoutParams relativeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            relativeParams.setMargins(0, 0, 0, 0);
            llDetails.setLayoutParams(relativeParams);
        }
    }

    /**
     * Load Reason List
     */
    private void loadReasonList() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, reasonList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReason.setAdapter(dataAdapter);
    }

    /**
     * Load Step Layout
     */
    private void loadStepLayout() {

        TextView tvStepOne = llSteps.findViewById(R.id.tvStepOne);
        TextView tvStepTwo = llSteps.findViewById(R.id.tvStepTwo);
        TextView tvStepThree = llSteps.findViewById(R.id.tvStepThree);
        TextView tvStepFour = llSteps.findViewById(R.id.tvStepFour);

        String processName = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.PROCESS_NAME, "");
        String systemName = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.SYSTEM_NAME, "");
        String systemItems = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.SYSTEM_ITEM, "");
        String linkedItems = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.LINKED_ITEM, "");
        tvStepOne.setText(processName);
        tvStepTwo.setText(systemName);
        tvStepThree.setVisibility(View.VISIBLE);
        tvStepThree.setText(systemItems);
        tvStepFour.setVisibility(View.VISIBLE);
        tvStepFour.setText(linkedItems);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivBack:
                dismiss();
                break;

            case R.id.btnAdd:

                if (isValid()) {
                    mEntryLogModel = new EntryLogModel();
                    mEntryLogModel.setInvestigationId(investId);
                    mEntryLogModel.setReason(selectedReason);
                    mEntryLogModel.setEntryLog(etLogEntry.getText().toString().trim());

                    BasicMethodsUtil.getInstance().showToast(mContext, "Logs sumitted successfuly!");
                    dismiss();
                }

                break;

            case R.id.btnCancel:
                dismiss();
                break;

        }
    }


    private boolean isValid() {

        boolean isValid = false;
        if (TextUtils.isEmpty(selectedReason)) {

            BasicMethodsUtil.getInstance().showToast(mContext, "Select reason!");

        } else if (TextUtils.isEmpty(etLogEntry.getText().toString().trim())) {

            BasicMethodsUtil.getInstance().showToast(mContext, "Enter log entry!");
        } else {
            isValid = true;
        }

        return isValid;
    }
}

