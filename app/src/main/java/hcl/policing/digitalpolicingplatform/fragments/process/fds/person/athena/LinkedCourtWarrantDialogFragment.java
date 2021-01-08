package hcl.policing.digitalpolicingplatform.fragments.process.fds.person.athena;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.models.process.fds.investigation.athena.EnquiryLogsModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.AthenaLinkedCourtWarrantDetailsModel;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class LinkedCourtWarrantDialogFragment extends DialogFragment implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private int listType;
    private ArrayList<List<?>> listDetails;
    private LinearLayout llDetails, court_warrent_detail, llHeader;
    private LinearLayout llFurtherInfoDetails, llCourtWarrantDetails, llEnquiryLogContent;
    private TextView tvFlowName;
    private Dialog mProgressDialog;
    private AthenaLinkedCourtWarrantDetailsModel.WarrentdetailresponseBean warrentdetailresponseBean;
    private List<EnquiryLogsModel> aEnquiryLogsModel;
    private boolean isPopulate;

    public static LinkedCourtWarrantDialogFragment newInstance(int type, ArrayList<List<?>> list, boolean isPopulate) {

        LinkedCourtWarrantDialogFragment frag = new LinkedCourtWarrantDialogFragment();
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

        View rootView = inflater.inflate(R.layout.athena_linked_court_warrant_fragment, container, false);
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
        court_warrent_detail = view.findViewById(R.id.court_warrent_detail);
        tvFlowName = view.findViewById(R.id.tvFlowName);
        loadHeaderLayout();
        loadLinkedCourtWarrantDetails(GenericConstant.ATHENA_LINKED_COURT_WARRANT_DETAILS_JSON);
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
        tvHeader.setText(getString(R.string.linked_court_warrant_header));

        String title = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.LINKED_HEADER, "");
        tvFlowName.setText(title);

        ivBack.setOnClickListener(this);
    }


    /**
     * Load Case Deta
     */
    private void loadCourtWarrantData() {

        aEnquiryLogsModel = warrentdetailresponseBean.getLogentries();
        RelativeLayout rlCourtWarrantDetails = court_warrent_detail.findViewById(R.id.rlCourtWarrantDetails);
        RelativeLayout rlEnquiryLog = court_warrent_detail.findViewById(R.id.rlEnquiryLog);
        RelativeLayout rlFurtherInfo = court_warrent_detail.findViewById(R.id.rlFurtherInfo);

        llCourtWarrantDetails = court_warrent_detail.findViewById(R.id.llCourtWarrantDetails);
        llEnquiryLogContent = court_warrent_detail.findViewById(R.id.llEnquiryLogContent);
        llFurtherInfoDetails = court_warrent_detail.findViewById(R.id.llFurtherInfoDetails);

        TextView tvEnquiryLogCount = court_warrent_detail.findViewById(R.id.tvEnquiryLogCount);
        TextView tvReferenceValue = court_warrent_detail.findViewById(R.id.tvReferenceValue);
        TextView tvShortSummaryValue = court_warrent_detail.findViewById(R.id.tvShortSummaryValue);
        TextView tvCategoryValue = court_warrent_detail.findViewById(R.id.tvCategoryValue);
        TextView tvStatusValue = court_warrent_detail.findViewById(R.id.tvStatusValue);
        TextView tvTypeValue = court_warrent_detail.findViewById(R.id.tvTypeValue);
        TextView tvIssueDateValue = court_warrent_detail.findViewById(R.id.tvIssueDateValue);
        TextView tvDueDateValue = court_warrent_detail.findViewById(R.id.tvDueDateValue);


        rlCourtWarrantDetails.setVisibility(View.GONE);
        llCourtWarrantDetails.setVisibility(View.VISIBLE);

        if (warrentdetailresponseBean != null) {
            tvReferenceValue.setText(warrentdetailresponseBean.getWarrantreference());
            tvShortSummaryValue.setText(warrentdetailresponseBean.getShortsummary());
            tvCategoryValue.setText(warrentdetailresponseBean.getCategory());
            tvStatusValue.setText(warrentdetailresponseBean.getStatus());
            tvTypeValue.setText(warrentdetailresponseBean.getType());
            tvIssueDateValue.setText(warrentdetailresponseBean.getIssuedate());
            tvDueDateValue.setText(warrentdetailresponseBean.getDuedate());
        }

        if (aEnquiryLogsModel != null && aEnquiryLogsModel.size() > 0) {
            tvEnquiryLogCount.setText(String.valueOf(aEnquiryLogsModel.size()));
        }
        rlFurtherInfo.setOnClickListener(this);
        rlEnquiryLog.setOnClickListener(this);
    }

    /**
     * Load the further info
     */
    private void loadWarrantAdditionalInfoData() {

        if (warrentdetailresponseBean != null) {

            TextView tvWarrantNoValue = court_warrent_detail.findViewById(R.id.tvWarrantNoValue);
            TextView tvForceValue = court_warrent_detail.findViewById(R.id.tvForceValue);
            TextView tvBCUValue = court_warrent_detail.findViewById(R.id.tvBCUValue);
            TextView tvDistrictValue = court_warrent_detail.findViewById(R.id.tvDistrictValue);
            TextView tvWardValue = court_warrent_detail.findViewById(R.id.tvWardValue);
            TextView tvCCCJSValue = court_warrent_detail.findViewById(R.id.tvCCCJSValue);
            TextView tvWarrantIssuedValue = court_warrent_detail.findViewById(R.id.tvWarrantIssuedValue);
            TextView tvWarrantDueValue = court_warrent_detail.findViewById(R.id.tvWarrantDueValue);
            TextView tvSubjectBailedValue = court_warrent_detail.findViewById(R.id.tvSubjectBailedValue);

            tvWarrantNoValue.setText(warrentdetailresponseBean.getWarrantreference());
            tvForceValue.setText(warrentdetailresponseBean.getForce());
            tvBCUValue.setText(warrentdetailresponseBean.getBcu());
            tvDistrictValue.setText(warrentdetailresponseBean.getDistrict());
            tvWardValue.setText(warrentdetailresponseBean.getWard());
            tvCCCJSValue.setText(warrentdetailresponseBean.getCccjs());
            tvWarrantIssuedValue.setText(warrentdetailresponseBean.getIssuedate());
            tvWarrantDueValue.setText(warrentdetailresponseBean.getDuedate());
            tvSubjectBailedValue.setText(warrentdetailresponseBean.getSubjectbailed());
        }
    }


    /**
     * load Enquiry Log Data
     */
    private void loadWarrantEnquiryLogData() {
        llEnquiryLogContent.removeAllViews();
        if (aEnquiryLogsModel != null && aEnquiryLogsModel.size() > 0) {
            for (int i = 0; i < aEnquiryLogsModel.size(); i++) {

                EnquiryLogsModel mEnquiryLogsModel = aEnquiryLogsModel.get(i);
                View view = layoutInflater.inflate(R.layout.athena_details_warrant_enquirylog_item, null);
                TextView tvLogDateValue = view.findViewById(R.id.tvLogDateValue);
                TextView tvTypeValue = view.findViewById(R.id.tvTypeValue);
                TextView tvDetailsValue = view.findViewById(R.id.tvDetailsValue);

                tvLogDateValue.setText(mEnquiryLogsModel.getLogdate());
                tvTypeValue.setText(mEnquiryLogsModel.getEntrytype());
                tvDetailsValue.setText(mEnquiryLogsModel.getEntrytext());

                llEnquiryLogContent.addView(view);
            }
        }
    }

    /**
     * Load Person JSON
     */
    public void loadLinkedCourtWarrantDetails(String fileName) {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {

                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);
                AthenaLinkedCourtWarrantDetailsModel athenaResponse = (AthenaLinkedCourtWarrantDetailsModel) JsonUtil.getInstance().toModel(strJson, AthenaLinkedCourtWarrantDetailsModel.class);
                if (athenaResponse != null) {
                    warrentdetailresponseBean = athenaResponse.getWarrentdetailresponse();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (warrentdetailresponseBean != null) {
                        aEnquiryLogsModel = warrentdetailresponseBean.getLogentries();
                        loadCourtWarrantData();
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

            case R.id.rlEnquiryLog:

                if (llEnquiryLogContent.getVisibility() != View.VISIBLE) {
                    llEnquiryLogContent.setVisibility(View.VISIBLE);

                    loadWarrantEnquiryLogData();
                } else {
                    llEnquiryLogContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlFurtherInfo:

                if (llFurtherInfoDetails.getVisibility() != View.VISIBLE) {
                    llFurtherInfoDetails.setVisibility(View.VISIBLE);

                    loadWarrantAdditionalInfoData();

                } else {
                    llFurtherInfoDetails.setVisibility(View.GONE);
                }


                break;

        }
    }
}
