package hcl.policing.digitalpolicingplatform.fragments.process.fds.person.athena;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.models.process.fds.investigation.athena.EnquiryLogsModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.AthenaLinkedInvestigationDetailsModel;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class LinkedInvestigationDialogFragment extends DialogFragment implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private int listType;
    private ArrayList<List<?>> listDetails;
    private LinearLayout llDetails, investigation_detail, llHeader;
    private LinearLayout llInvestigationDetails, llFurtherInfoDetails, llPrimaryOffenceDetails, llEnquiryLogContent;
    private TextView tvFlowName;
    private Dialog mProgressDialog;
    private AthenaLinkedInvestigationDetailsModel.InvestigationdetailresponseBean investigationdetailresponseBean;
    private List<EnquiryLogsModel> aEnquiryLogsModel;
    private boolean isPopulate;

    public static LinkedInvestigationDialogFragment newInstance(int type, ArrayList<List<?>> list, boolean isPopulate) {

        LinkedInvestigationDialogFragment frag = new LinkedInvestigationDialogFragment();
        Bundle args = new Bundle();
        args.putInt(GenericConstant.TYPE_DETAILS, type);
        args.putSerializable(GenericConstant.ATHENA_LIST, list);
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

        View rootView = inflater.inflate(R.layout.athena_linked_investigation_fragment, container, false);
        layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (getArguments() != null) {
            listType = getArguments().getInt(GenericConstant.TYPE_DETAILS);
            listDetails = (ArrayList<List<?>>) getArguments().getSerializable(GenericConstant.ATHENA_LIST);
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


    private void initView(View view) {

        llDetails = view.findViewById(R.id.llDetails);
        llHeader = view.findViewById(R.id.llHeader);
        investigation_detail = view.findViewById(R.id.investigation_detail);
        tvFlowName = view.findViewById(R.id.tvFlowName);

        isPopulateView();
        loadHeaderLayout();
        loadAthenaLinkedInvestigationDetails(GenericConstant.ATHENA_LINKED_INVESTIGATION_DETAILS_JSON);
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
     * Load Step Layout
     */
    private void loadHeaderLayout() {
        TextView tvHeader = llHeader.findViewById(R.id.tvHeader);
        ImageView ivBack = llHeader.findViewById(R.id.ivBack);
        HorizontalScrollView llSteps = llHeader.findViewById(R.id.llSteps);
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
        tvHeader.setText(getString(R.string.linked_investigation_header));

        String title = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.LINKED_HEADER, "");
        tvFlowName.setText(title);

        ivBack.setOnClickListener(this);
    }


    /**
     * Load Investigation Data
     */
    private void loadInvestigationData() {

        RelativeLayout rlInvestigationDetails = investigation_detail.findViewById(R.id.rlInvestigationDetails);
        RelativeLayout rlPrimaryOffence = investigation_detail.findViewById(R.id.rlPrimaryOffence);
        RelativeLayout rlEnquiryLog = investigation_detail.findViewById(R.id.rlEnquiryLog);
        RelativeLayout rlFurtherInfo = investigation_detail.findViewById(R.id.rlFurtherInfo);
        llInvestigationDetails = investigation_detail.findViewById(R.id.llInvestigationDetails);
        llFurtherInfoDetails = investigation_detail.findViewById(R.id.llFurtherInfoDetails);
        llPrimaryOffenceDetails = investigation_detail.findViewById(R.id.llPrimaryOffenceDetails);
        llEnquiryLogContent = investigation_detail.findViewById(R.id.llEnquiryLogContent);
        Button btnAddEnquiryLog = investigation_detail.findViewById(R.id.btnAddEnquiryLog);
        TextView tvEnquiryLogCount = investigation_detail.findViewById(R.id.tvEnquiryLogCount);

        TextView tvInvestigationNO = investigation_detail.findViewById(R.id.tvInvestigationNO);
        TextView tvEndOnValue = investigation_detail.findViewById(R.id.tvEndOnValue);
        TextView tvReportedValue = investigation_detail.findViewById(R.id.tvReportedValue);
        TextView tvStatusValue = investigation_detail.findViewById(R.id.tvStatusValue);
        TextView tvPrimaryOffenceValue = investigation_detail.findViewById(R.id.tvPrimaryOffenceValue);
        TextView tvOICValue = investigation_detail.findViewById(R.id.tvOICValue);
        TextView tvOICUnitValue = investigation_detail.findViewById(R.id.tvOICUnitValue);
        TextView tvInvestigationTypeValue = investigation_detail.findViewById(R.id.tvInvestigationTypeValue);
        TextView tvOutcomeValue = investigation_detail.findViewById(R.id.tvOutcomeValue);
        TextView tvCCNoValue = investigation_detail.findViewById(R.id.tvCCNoValue);
        TextView tvEventLocationValue = investigation_detail.findViewById(R.id.tvEventLocationValue);
        TextView tvSummaryValue = investigation_detail.findViewById(R.id.tvSummaryValue);


        if (investigationdetailresponseBean != null) {
            tvInvestigationNO.setText(getString(R.string.investigations) + " " + investigationdetailresponseBean.getInvestigationnumber());
            tvEndOnValue.setText(investigationdetailresponseBean.getEventdate());
            tvReportedValue.setText(investigationdetailresponseBean.getReporteddate());
            tvStatusValue.setText(investigationdetailresponseBean.getStatus());
            tvPrimaryOffenceValue.setText(investigationdetailresponseBean.getPrimaryoffence());
            tvOICValue.setText(investigationdetailresponseBean.getOic());
            tvOICUnitValue.setText(investigationdetailresponseBean.getOicUnit());
            tvInvestigationTypeValue.setText(investigationdetailresponseBean.getType());
            tvOutcomeValue.setText(investigationdetailresponseBean.getOutcome());
            tvCCNoValue.setText(investigationdetailresponseBean.getCcNumber());
            tvEventLocationValue.setText(investigationdetailresponseBean.getEventlocation());
            if (investigationdetailresponseBean.getSummary() != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvSummaryValue.setText(Html.fromHtml(investigationdetailresponseBean.getSummary(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    tvSummaryValue.setText(Html.fromHtml(investigationdetailresponseBean.getSummary()));
                }
            }
        }

        if (aEnquiryLogsModel != null && aEnquiryLogsModel.size() > 0) {
            tvEnquiryLogCount.setText(String.valueOf(aEnquiryLogsModel.size()));
        }
        rlInvestigationDetails.setOnClickListener(this);
        rlFurtherInfo.setOnClickListener(this);
        rlPrimaryOffence.setOnClickListener(this);
        rlEnquiryLog.setOnClickListener(this);
        btnAddEnquiryLog.setOnClickListener(this);
    }


    /**
     * Load the further info
     */
    private void loadInvestigationAdditionalInfoData() {
        TextView tvCategoryValue = investigation_detail.findViewById(R.id.tvCategoryValue);
        TextView tvKeywordsValue = investigation_detail.findViewById(R.id.tvKeywordsValue);
        TextView tvReportingOfficerValue = investigation_detail.findViewById(R.id.tvReportingOfficerValue);
        TextView tvHOClassificationValue = investigation_detail.findViewById(R.id.tvHOClassificationValue);
        TextView tvVictimCrownValue = investigation_detail.findViewById(R.id.tvVictimCrownValue);

        if (investigationdetailresponseBean != null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvCategoryValue.setText(Html.fromHtml(investigationdetailresponseBean.getCategories(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                tvCategoryValue.setText(Html.fromHtml(investigationdetailresponseBean.getCategories()));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvKeywordsValue.setText(Html.fromHtml(investigationdetailresponseBean.getKeywords(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                tvKeywordsValue.setText(Html.fromHtml(investigationdetailresponseBean.getKeywords()));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvReportingOfficerValue.setText(Html.fromHtml(investigationdetailresponseBean.getReportingofficer(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                tvReportingOfficerValue.setText(Html.fromHtml(investigationdetailresponseBean.getReportingofficer()));
            }
            tvHOClassificationValue.setText("");
            tvVictimCrownValue.setText(investigationdetailresponseBean.getVictimiscrown());

        }
    }

    /**
     * Load the investigation Primary Offence Data
     */
    private void loadPrimaryOffenceData() {

        TextView tvPrimaryOffenceValue = llPrimaryOffenceDetails.findViewById(R.id.tvPrimaryOffenceValue);
        TextView tvCCCJSCodeValue = llPrimaryOffenceDetails.findViewById(R.id.tvCCCJSCodeValue);
        TextView tvACPOCodeValue = llPrimaryOffenceDetails.findViewById(R.id.tvACPOCodeValue);
        TextView tvSubGroupValue = llPrimaryOffenceDetails.findViewById(R.id.tvSubGroupValue);

        if (investigationdetailresponseBean != null) {
            tvPrimaryOffenceValue.setText(investigationdetailresponseBean.getSubgroup());
            tvCCCJSCodeValue.setText(investigationdetailresponseBean.getPrimaryoffence());
            tvACPOCodeValue.setText(investigationdetailresponseBean.getCccjsCode());
            tvSubGroupValue.setText(investigationdetailresponseBean.getAcpoCode());
        }
    }


    /**
     * load Enquiry Log Data
     */
    private void loadEnquiryLogData() {

        if (aEnquiryLogsModel != null && aEnquiryLogsModel.size() > 0) {
            for (int i = 0; i < aEnquiryLogsModel.size(); i++) {

                EnquiryLogsModel mEnquiryLogsModel = aEnquiryLogsModel.get(i);
                View view = layoutInflater.inflate(R.layout.person_athena_details_enquiry_log_item, null);
                TextView tvIndexNO = view.findViewById(R.id.tvIndexNO);
                TextView tvEnteredByValue = view.findViewById(R.id.tvEnteredByValue);
                TextView tvEnterTypeValue = view.findViewById(R.id.tvEnterTypeValue);
                TextView tvTextValue = view.findViewById(R.id.tvTextValue);

                tvIndexNO.setText(mEnquiryLogsModel.getEntryindex() + " - " + mEnquiryLogsModel.getLogdate() + " " + mEnquiryLogsModel.getLogtime());
                tvEnteredByValue.setText(mEnquiryLogsModel.getEnteredby());
                tvEnterTypeValue.setText(mEnquiryLogsModel.getEntrytype());
                tvTextValue.setText(mEnquiryLogsModel.getEntrytext());

                llEnquiryLogContent.addView(view);
            }
        }
    }

    /**
     * Load Person JSON
     */
    public void loadAthenaLinkedInvestigationDetails(String fileName) {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {

                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);
                AthenaLinkedInvestigationDetailsModel athenaResponse = (AthenaLinkedInvestigationDetailsModel) JsonUtil.getInstance().toModel(strJson, AthenaLinkedInvestigationDetailsModel.class);
                if (athenaResponse != null) {
                    investigationdetailresponseBean = athenaResponse.getInvestigationdetailresponse();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (investigationdetailresponseBean != null) {
                        aEnquiryLogsModel = investigationdetailresponseBean.getEnquirylogs();
                        loadInvestigationData();
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), LinkedInvestigationDialogFragment.class, "loadAthenaPersonDetails");
            }
        }).start();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivBack:

                dismiss();
                DialogUtil.cancelProgressDialog(mProgressDialog);
                break;

            case R.id.rlInvestigationDetails:

                if (llInvestigationDetails.getVisibility() != View.VISIBLE) {
                    llInvestigationDetails.setVisibility(View.VISIBLE);
                    loadInvestigationData();
                } else {
                    llInvestigationDetails.setVisibility(View.GONE);
                }

                break;

            case R.id.rlFurtherInfo:

                if (llFurtherInfoDetails.getVisibility() != View.VISIBLE) {
                    llFurtherInfoDetails.setVisibility(View.VISIBLE);
                    loadInvestigationAdditionalInfoData();
                } else {
                    llFurtherInfoDetails.setVisibility(View.GONE);
                }

                break;

            case R.id.rlPrimaryOffence:

                if (llPrimaryOffenceDetails.getVisibility() != View.VISIBLE) {
                    llPrimaryOffenceDetails.setVisibility(View.VISIBLE);
                    loadPrimaryOffenceData();
                } else {
                    llPrimaryOffenceDetails.setVisibility(View.GONE);
                }

                break;

            case R.id.rlEnquiryLog:

                if (llEnquiryLogContent.getVisibility() != View.VISIBLE) {
                    llEnquiryLogContent.setVisibility(View.VISIBLE);
                    loadEnquiryLogData();
                } else {
                    llEnquiryLogContent.setVisibility(View.GONE);
                }

                break;

            case R.id.btnAddEnquiryLog:
                loadEntryDialog();
                break;
        }
    }

    /**
     * Load Intelligence Dialog
     */
    public void loadEntryDialog() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.ADD_ENQUIRY_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        AddEnquiryDialogFragment linkedInvestigationDialogFragment = AddEnquiryDialogFragment.newInstance(investigationdetailresponseBean.getId(), isPopulate);
        linkedInvestigationDialogFragment.show(ft, GenericConstant.ADD_ENQUIRY_DIALOG);
    }

}
