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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.AthenaLinkedIntellegenceDetailsModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.AthenaLinkedVehicleDetailsModel;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class LinkedIntelligenceReportsDialogFragment extends DialogFragment implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private int listType;
    private ArrayList<List<?>> listDetails;
    private LinearLayout llDetails,llHeader, llIntelligenceDetails, llAdditionalInfoDetails;
    private CardView cardViewRisk;
    private TextView tvFlowName, tvRiskText, tvIntelligenceName;
    private RelativeLayout rlIntelligence, rlAdditionalInfo, rlRiskAssesments;
    private Dialog mProgressDialog;
    private AthenaLinkedIntellegenceDetailsModel.IntelligencedetailresponseBean intelligencedetailresponseBean;
    private ArrayList<AthenaLinkedVehicleDetailsModel.PhotosBean> aPhotosBean;
    private boolean isPopulate;

    public static LinkedIntelligenceReportsDialogFragment newInstance(int type, ArrayList<List<?>> list,boolean isPopulate) {

        LinkedIntelligenceReportsDialogFragment frag = new LinkedIntelligenceReportsDialogFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.athena_linked_intelligence_report_fragment, container, false);
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
        tvFlowName = view.findViewById(R.id.tvFlowName);
        rlIntelligence = view.findViewById(R.id.rlIntelligence);
        rlAdditionalInfo = view.findViewById(R.id.rlAdditionalInfo);
        rlRiskAssesments = view.findViewById(R.id.rlAdditionalInfo);
        tvRiskText = view.findViewById(R.id.tvRiskText);
        tvIntelligenceName = view.findViewById(R.id.tvRiskText);

        llIntelligenceDetails = view.findViewById(R.id.llIntelligenceDetails);
        llAdditionalInfoDetails = view.findViewById(R.id.llAdditionalInfoDetails);
        cardViewRisk = view.findViewById(R.id.cardViewRisk);

        isPopulateView();
        loadHeaderLayout();
        loadAthenaLinkedIntelligenceDetails(GenericConstant.ATHENA_LINKED_INTELLIGENCE_DETAILS_JSON);

        rlIntelligence.setOnClickListener(this);
        rlAdditionalInfo.setOnClickListener(this);
        rlRiskAssesments.setOnClickListener(this);
    }

    /**
     * Check the condition for populate view.
     */
    private void isPopulateView() {
        if (!isPopulate) {
            CoordinatorLayout.LayoutParams relativeParams = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.MATCH_PARENT);
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
        tvHeader.setText(getString(R.string.linked_intelligence_header));

        String title = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.LINKED_HEADER, "");
        tvFlowName.setText(title);

        ivBack.setOnClickListener(this);
    }


    /**
     * Load the Person Details
     */
    private void loadIntelligenceData() {

        layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TextView tvDateSubmittedValue = llIntelligenceDetails.findViewById(R.id.tvDateSubmittedValue);
        TextView tvEventValue = llIntelligenceDetails.findViewById(R.id.tvEventValue);
        TextView tvNIMLevelValue = llIntelligenceDetails.findViewById(R.id.tvNIMLevelValue);
        TextView tvPriorityValue = llIntelligenceDetails.findViewById(R.id.tvPriorityValue);
        TextView tvSensitiveValue = llIntelligenceDetails.findViewById(R.id.tvSensitiveValue);
        TextView tvSourceCodeValue = llIntelligenceDetails.findViewById(R.id.tvSourceCodeValue);
        TextView tvEvaluationCodeValue = llIntelligenceDetails.findViewById(R.id.tvEvaluationCodeValue);
        TextView tvHandlingCodeValue = llIntelligenceDetails.findViewById(R.id.tvHandlingCodeValue);
        TextView tvForceValue = llIntelligenceDetails.findViewById(R.id.tvForceValue);
        TextView tvBCUValue = llIntelligenceDetails.findViewById(R.id.tvBCUValue);
        TextView tvDistrictValue = llIntelligenceDetails.findViewById(R.id.tvDistrictValue);
        TextView tvWardValue = llIntelligenceDetails.findViewById(R.id.tvWardValue);
        TextView tvTypeOfInfoValue = llIntelligenceDetails.findViewById(R.id.tvTypeOfInfoValue);
        TextView tvReportTitleValue = llIntelligenceDetails.findViewById(R.id.tvReportTitleValue);

        if (intelligencedetailresponseBean != null) {
            tvIntelligenceName.setText(getString(R.string.intelligence_report) + " " + intelligencedetailresponseBean.getIntelligencenumber());
            tvDateSubmittedValue.setText(intelligencedetailresponseBean.getDate());
            tvEventValue.setText(intelligencedetailresponseBean.getEventfromto());
            tvNIMLevelValue.setText(intelligencedetailresponseBean.getNimlevel());
            tvPriorityValue.setText(intelligencedetailresponseBean.getPriority());
            tvSensitiveValue.setText(intelligencedetailresponseBean.getSensitive());
            tvSourceCodeValue.setText(intelligencedetailresponseBean.getSourcecode());
            tvEvaluationCodeValue.setText(intelligencedetailresponseBean.getEvaluation());
            tvHandlingCodeValue.setText(intelligencedetailresponseBean.getHandling());
            tvForceValue.setText(intelligencedetailresponseBean.getForce());
            tvBCUValue.setText(intelligencedetailresponseBean.getBcu());
            tvDistrictValue.setText(intelligencedetailresponseBean.getDistrict());
            tvWardValue.setText(intelligencedetailresponseBean.getWard());
            tvReportTitleValue.setText(intelligencedetailresponseBean.getReporttitle());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvTypeOfInfoValue.setText(Html.fromHtml(intelligencedetailresponseBean.getTypeofinfo(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                tvTypeOfInfoValue.setText(Html.fromHtml(intelligencedetailresponseBean.getTypeofinfo()));
            }
        }
    }

    /**
     * Load Image list
     */
    private void loadRiskData() {

        List<AthenaLinkedIntellegenceDetailsModel.RiskAssessmentsBean> riskAssessmentsBean = intelligencedetailresponseBean.getRiskassessments();
        if (riskAssessmentsBean != null && riskAssessmentsBean.size() > 0) {
            tvRiskText.setText(riskAssessmentsBean.get(0).getSummary());
        }
    }


    /**
     * Load the further info
     */
    private void loadAdditionalInfoData() {

        if (intelligencedetailresponseBean != null) {

            TextView tvDateAddedValue = llAdditionalInfoDetails.findViewById(R.id.tvDateAddedValue);
            TextView tvSanitizetextValue = llAdditionalInfoDetails.findViewById(R.id.tvSanitizetextValue);
            TextView tvSouceInfoValue = llAdditionalInfoDetails.findViewById(R.id.tvSouceInfoValue);
            TextView tvMemberPublicNameValue = llAdditionalInfoDetails.findViewById(R.id.tvMemberPublicNameValue);
            TextView tvDOBValue = llAdditionalInfoDetails.findViewById(R.id.tvDOBValue);
            TextView tvOrganizationValue = llAdditionalInfoDetails.findViewById(R.id.tvOrganizationValue);
            TextView tvSubmittingOfficerValue = llAdditionalInfoDetails.findViewById(R.id.tvSubmittingOfficerValue);
            TextView tvOtherForceValue = llAdditionalInfoDetails.findViewById(R.id.tvOtherForceValue);
            TextView tvOtherForceOfficerValue = llAdditionalInfoDetails.findViewById(R.id.tvOtherForceOfficerValue);
            TextView tvISRNoValue = llAdditionalInfoDetails.findViewById(R.id.tvISRNoValue);
            TextView tvOtherSourceValue = llAdditionalInfoDetails.findViewById(R.id.tvOtherSourceValue);
            TextView tvOtherRefValue = llAdditionalInfoDetails.findViewById(R.id.tvOtherRefValue);
            TextView tvContactDetailsValue = llAdditionalInfoDetails.findViewById(R.id.tvContactDetailsValue);
            TextView tvHandlingCondValue = llAdditionalInfoDetails.findViewById(R.id.tvHandlingCondValue);
            TextView tvOfficerCommentsValue = llAdditionalInfoDetails.findViewById(R.id.tvOfficerCommentsValue);
            TextView tvProvenanceValue = llAdditionalInfoDetails.findViewById(R.id.tvProvenanceValue);


            tvDateAddedValue.setText(intelligencedetailresponseBean.getDateadded());
            tvSanitizetextValue.setText(intelligencedetailresponseBean.getSanitisedtext());

            AthenaLinkedIntellegenceDetailsModel.SourceBean sourceBean = intelligencedetailresponseBean.getSource();

            if (sourceBean != null) {

                tvSouceInfoValue.setText(sourceBean.getSourceofinfo());
                tvMemberPublicNameValue.setText(sourceBean.getMemberPublicName());
                tvDOBValue.setText(sourceBean.getDob());
                tvOrganizationValue.setText(sourceBean.getOrganisationname());
                tvSubmittingOfficerValue.setText(sourceBean.getSubmittingofficer());
                tvOtherForceValue.setText(sourceBean.getOtherforce());
                tvOtherForceOfficerValue.setText(sourceBean.getOtherfouceofficerdetails());
                tvISRNoValue.setText(sourceBean.getIsrnumber());
                tvOtherSourceValue.setText(sourceBean.getOthersource());
                tvOtherRefValue.setText(sourceBean.getOtherreference());
            }
            tvContactDetailsValue.setText("No contact details available");
            tvHandlingCondValue.setText(intelligencedetailresponseBean.getHandling());
            tvOfficerCommentsValue.setText("");
            tvProvenanceValue.setText(intelligencedetailresponseBean.getProvenance());
        }
    }

    /**
     * Load Person JSON
     */
    public void loadAthenaLinkedIntelligenceDetails(String fileName) {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {

                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);
                AthenaLinkedIntellegenceDetailsModel athenaResponse = (AthenaLinkedIntellegenceDetailsModel) JsonUtil.getInstance().toModel(strJson, AthenaLinkedIntellegenceDetailsModel.class);
                if (athenaResponse != null) {
                    intelligencedetailresponseBean = athenaResponse.getIntelligencedetailresponse();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (intelligencedetailresponseBean != null) {
                        tvIntelligenceName.setText(intelligencedetailresponseBean.getIntelligencenumber());
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), LinkedIntelligenceReportsDialogFragment.class, "loadAthenaPersonDetails");
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
            case R.id.rlIntelligence:

                if (llIntelligenceDetails.getVisibility() != View.VISIBLE) {
                    llIntelligenceDetails.setVisibility(View.VISIBLE);
                    loadIntelligenceData();
                } else {
                    llIntelligenceDetails.setVisibility(View.GONE);
                }

                break;

            case R.id.rlAdditionalInfo:

                if (llAdditionalInfoDetails.getVisibility() != View.VISIBLE) {
                    llAdditionalInfoDetails.setVisibility(View.VISIBLE);
                    loadAdditionalInfoData();
                } else {
                    llAdditionalInfoDetails.setVisibility(View.GONE);
                }

                break;

            case R.id.rlRiskAssesments:

                if (cardViewRisk.getVisibility() != View.VISIBLE) {
                    cardViewRisk.setVisibility(View.VISIBLE);
                    loadRiskData();
                } else {
                    cardViewRisk.setVisibility(View.GONE);
                }

                break;

        }
    }

}
