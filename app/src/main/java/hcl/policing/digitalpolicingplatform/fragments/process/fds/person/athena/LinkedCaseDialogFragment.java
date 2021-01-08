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
import hcl.policing.digitalpolicingplatform.models.process.fds.cases.athena.DefendantsModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.cases.athena.HearingsModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.cases.athena.OffencesModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.AthenaLinkedCaseDetailsModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.PersonModel;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class LinkedCaseDialogFragment extends DialogFragment implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private int listType;
    private ArrayList<List<?>> listDetails;
    private LinearLayout llDefendantsContent, llHearingsContent, llPersonsContent, llFurtherInfoDetails;
    private LinearLayout cases_detail, llDetails, llHeader;
    private ImageView ivBack;
    private TextView tvFlowName;
    private TextView tvDefendantsCount, tvHearingsCount, tvPersonsCount;
    private Dialog mProgressDialog;
    private AthenaLinkedCaseDetailsModel.CasedetailresponseBean casedetailresponseBean;
    List<DefendantsModel> aDefendantsBeans;
    List<HearingsModel> aHearingsBean;
    List<PersonModel> aPersonsBean;
    private boolean isPopulate;

    public static LinkedCaseDialogFragment newInstance(int type, ArrayList<List<?>> list, boolean isPopulate) {

        LinkedCaseDialogFragment frag = new LinkedCaseDialogFragment();
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

        View rootView = inflater.inflate(R.layout.athena_linked_case_fragment, container, false);
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
        cases_detail = view.findViewById(R.id.case_detail);
        tvFlowName = view.findViewById(R.id.tvFlowName);


        tvDefendantsCount = view.findViewById(R.id.tvDefendantsCount);
        tvHearingsCount = view.findViewById(R.id.tvHearingsCount);
        tvPersonsCount = view.findViewById(R.id.tvPersonsCount);

        isPopulateView();
        loadHeaderLayout();
        loadAthenaCaseDetails(GenericConstant.ATHENA_LINKED_CASE_DETAILS_JSON);
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
        tvHeader.setText(getString(R.string.linked_case_header));

        String title = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.LINKED_HEADER, "");
        tvFlowName.setText(title);

        ivBack.setOnClickListener(this);
    }


    /**
     * Load Case Deta
     */
    private void loadCasesData() {

        RelativeLayout rlDefendants = cases_detail.findViewById(R.id.rlDefendants);
        RelativeLayout rlHearings = cases_detail.findViewById(R.id.rlHearings);
        RelativeLayout rlPersons = cases_detail.findViewById(R.id.rlPersons);
        RelativeLayout rlFurtherInfo = cases_detail.findViewById(R.id.rlFurtherInfo);

        llDefendantsContent = cases_detail.findViewById(R.id.llDefendantsContent);
        llHearingsContent = cases_detail.findViewById(R.id.llHearingsContent);
        llPersonsContent = cases_detail.findViewById(R.id.llPersonsContent);
        llFurtherInfoDetails = cases_detail.findViewById(R.id.llAdditionalInfoDetails);

        TextView tvReferenceValue = cases_detail.findViewById(R.id.tvReferenceValue);
        TextView tvCreatedOnValue = cases_detail.findViewById(R.id.tvCreatedOnValue);
        TextView tvFileSubTypeValue = cases_detail.findViewById(R.id.tvFileSubTypeValue);
        TextView tvOICValue = cases_detail.findViewById(R.id.tvOICValue);
        TextView tvStatusValue = cases_detail.findViewById(R.id.tvStatusValue);
        TextView tvCPSBranchValue = cases_detail.findViewById(R.id.tvCPSBranchValue);
        TextView tvCPSBranchCaseValue = cases_detail.findViewById(R.id.tvCPSBranchCaseValue);
        TextView tvCaseWorkerValue = cases_detail.findViewById(R.id.tvCaseWorkerValue);
        TextView tvCareOfficerValue = cases_detail.findViewById(R.id.tvCareOfficerValue);

        if (casedetailresponseBean != null) {
            tvReferenceValue.setText(casedetailresponseBean.getCasereference());
            tvCreatedOnValue.setText(casedetailresponseBean.getCreatedon());
            tvFileSubTypeValue.setText(casedetailresponseBean.getFilesubtype());
            tvOICValue.setText(casedetailresponseBean.getOic());
            tvStatusValue.setText(casedetailresponseBean.getStatus());
            tvCPSBranchValue.setText(casedetailresponseBean.getCpsbranch());
            tvCPSBranchCaseValue.setText(casedetailresponseBean.getCpscasestatus());
            tvCaseWorkerValue.setText(casedetailresponseBean.getCaseworker());
            tvCareOfficerValue.setText(casedetailresponseBean.getWitnesscareofficer());
        }

        rlDefendants.setOnClickListener(this);
        rlFurtherInfo.setOnClickListener(this);
        rlHearings.setOnClickListener(this);
        rlPersons.setOnClickListener(this);
    }

    /**
     * Load the further info
     */
    private void loadCasesAdditionalInfoData() {

        if (casedetailresponseBean != null) {

            TextView tvName = cases_detail.findViewById(R.id.tvName);
            TextView tvDOB = cases_detail.findViewById(R.id.tvDOB);
            TextView tvNameValue = cases_detail.findViewById(R.id.tvNameValue);
            TextView tvDOBValue = cases_detail.findViewById(R.id.tvDOBValue);

            TextView tvOffenceNDate = cases_detail.findViewById(R.id.tvOffenceNDate);
            View viewGender = cases_detail.findViewById(R.id.viewGender);
            View viewType = cases_detail.findViewById(R.id.viewType);
            View viewOffenceNDate = cases_detail.findViewById(R.id.viewOffenceNDate);
            LinearLayout llGender = cases_detail.findViewById(R.id.llGender);
            LinearLayout llType = cases_detail.findViewById(R.id.llType);
            TextView tvOffenceNDateValue = cases_detail.findViewById(R.id.tvOffenceNDateValue);

            viewGender.setVisibility(View.GONE);
            viewType.setVisibility(View.GONE);
            viewOffenceNDate.setVisibility(View.GONE);
            llGender.setVisibility(View.GONE);
            llType.setVisibility(View.GONE);
            tvOffenceNDate.setVisibility(View.GONE);
            tvOffenceNDateValue.setVisibility(View.GONE);

            tvName.setText(getString(R.string.case_document_status));
            tvDOB.setText(getString(R.string.cju_unit_code));

            tvNameValue.setText(casedetailresponseBean.getDocumentstatus());
            tvDOBValue.setText(casedetailresponseBean.getUnitcode());
        }
    }

    /**
     * load Defendants Data
     */
    private void loadCasesDefendantData() {

        List<DefendantsModel> aDefendantsModel = casedetailresponseBean.getDefendants();
        llDefendantsContent.removeAllViews();
        if (aDefendantsModel != null && aDefendantsModel.size() > 0) {
            for (int i = 0; i < aDefendantsModel.size(); i++) {

                DefendantsModel mDefendantsModel = aDefendantsModel.get(i);

                View view = layoutInflater.inflate(R.layout.athena_details_defendants_item, null);
                TextView tvNameValue = view.findViewById(R.id.tvNameValue);
                TextView tvDOBValue = view.findViewById(R.id.tvDOBValue);
                TextView tvGenderValue = view.findViewById(R.id.tvGenderValue);
                TextView tvOffenceNDateValue = view.findViewById(R.id.tvOffenceNDateValue);
                View viewType = view.findViewById(R.id.viewType);
                LinearLayout llType = view.findViewById(R.id.llType);

                viewType.setVisibility(View.GONE);
                llType.setVisibility(View.GONE);

                tvNameValue.setText(mDefendantsModel.getFirstname1() + " " + mDefendantsModel.getFirstname2() + " " + mDefendantsModel.getSurname());
                tvDOBValue.setText(mDefendantsModel.getDob());
                tvGenderValue.setText(mDefendantsModel.getGender());

                List<OffencesModel> aOffencesModel = mDefendantsModel.getOffences();

                if (aOffencesModel != null && aOffencesModel.size() > 0) {

                    tvOffenceNDateValue.setText(aOffencesModel.get(0).getOffenceshorttitle() + " from " + aOffencesModel.get(0).getOffencedate() + " to");
                }

                llDefendantsContent.addView(view);
            }
        }
    }

    /**
     * load Hearing Data
     */
    private void loadCasesHearingsData() {
        List<HearingsModel> aHearingsModel = casedetailresponseBean.getHearings();
        llHearingsContent.removeAllViews();
        if (aHearingsModel != null && aHearingsModel.size() > 0) {
            for (int i = 0; i < aHearingsModel.size(); i++) {

                HearingsModel mHearingsModel = aHearingsModel.get(i);

                View view = layoutInflater.inflate(R.layout.athena_details_defendants_item, null);

                TextView tvName = view.findViewById(R.id.tvName);
                TextView tvDOB = view.findViewById(R.id.tvDOB);
                TextView tvGender = view.findViewById(R.id.tvGender);
                TextView tvOffenceNDate = view.findViewById(R.id.tvOffenceNDate);
                View viewOffenceNDate = view.findViewById(R.id.viewOffenceNDate);

                TextView tvNameValue = view.findViewById(R.id.tvNameValue);
                TextView tvDOBValue = view.findViewById(R.id.tvDOBValue);
                TextView tvGenderValue = view.findViewById(R.id.tvGenderValue);
                TextView tvOffenceNDateValue = view.findViewById(R.id.tvOffenceNDateValue);
                TextView tvTypeValue = view.findViewById(R.id.tvTypeValue);

                tvOffenceNDate.setVisibility(View.GONE);
                viewOffenceNDate.setVisibility(View.GONE);
                tvOffenceNDateValue.setVisibility(View.GONE);

                tvDOB.setText(getString(R.string.court));
                tvGender.setText(getString(R.string.date_time));
                tvNameValue.setText(mHearingsModel.getFirstname1() + " " + mHearingsModel.getFirstname2() + " " + mHearingsModel.getSurname());
                tvDOBValue.setText(mHearingsModel.getCourtname());
                tvGenderValue.setText(mHearingsModel.getCourtdate());
                tvTypeValue.setText(mHearingsModel.getHearingtype());

                llHearingsContent.addView(view);
            }
        }
    }

    /**
     * load Person Data
     */
    private void loadCasesPersonData() {
        List<PersonModel> aPersonModel = casedetailresponseBean.getPersons();
        llPersonsContent.removeAllViews();
        if (aPersonModel != null && aPersonModel.size() > 0) {
            for (int i = 0; i < aPersonModel.size(); i++) {

                PersonModel mPersonModel = aPersonModel.get(i);

                View view = layoutInflater.inflate(R.layout.athena_details_defendants_item, null);

                TextView tvType = view.findViewById(R.id.tvType);
                TextView tvOffenceNDate = view.findViewById(R.id.tvOffenceNDate);
                View viewOffenceNDate = view.findViewById(R.id.viewOffenceNDate);

                TextView tvNameValue = view.findViewById(R.id.tvNameValue);
                TextView tvDOBValue = view.findViewById(R.id.tvDOBValue);
                TextView tvGenderValue = view.findViewById(R.id.tvGenderValue);
                TextView tvTypeValue = view.findViewById(R.id.tvTypeValue);
                TextView tvOffenceNDateValue = view.findViewById(R.id.tvOffenceNDateValue);

                tvOffenceNDate.setVisibility(View.GONE);
                viewOffenceNDate.setVisibility(View.GONE);
                tvOffenceNDateValue.setVisibility(View.GONE);

                tvType.setText(getString(R.string.status));
                tvNameValue.setText(mPersonModel.getFirstname1() + " " + mPersonModel.getLastname());
                tvDOBValue.setText(mPersonModel.getDob());
                tvGenderValue.setText(mPersonModel.getGender());
                tvTypeValue.setText(mPersonModel.getStatus());

                llPersonsContent.addView(view);
            }
        }
    }


    /**
     * Load Person JSON
     */
    public void loadAthenaCaseDetails(String fileName) {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {

                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);
                AthenaLinkedCaseDetailsModel athenaResponse = (AthenaLinkedCaseDetailsModel) JsonUtil.getInstance().toModel(strJson, AthenaLinkedCaseDetailsModel.class);
                if (athenaResponse != null) {
                    casedetailresponseBean = athenaResponse.getCasedetailresponse();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (casedetailresponseBean != null) {
                        loadViewCount();
                        loadCasesData();
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), AthenaListDialogFragment.class, "loadAthenaPersonDetails");
            }
        }).start();
    }

    /**
     * Load View Count
     */
    private void loadViewCount() {
        aDefendantsBeans = casedetailresponseBean.getDefendants();
        aHearingsBean = casedetailresponseBean.getHearings();
        aPersonsBean = casedetailresponseBean.getPersons();
        if (aDefendantsBeans != null && aDefendantsBeans.size() > 0) {
            tvDefendantsCount.setVisibility(View.VISIBLE);
            tvDefendantsCount.setText(String.valueOf(aDefendantsBeans.size()));
        }
        if (aHearingsBean != null && aHearingsBean.size() > 0) {
            tvHearingsCount.setVisibility(View.VISIBLE);
            tvHearingsCount.setText(String.valueOf(aHearingsBean.size()));
        }
        if (aPersonsBean != null && aPersonsBean.size() > 0) {
            tvPersonsCount.setVisibility(View.VISIBLE);
            tvPersonsCount.setText(String.valueOf(aPersonsBean.size()));
        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivBack:

                dismiss();
                DialogUtil.cancelProgressDialog(mProgressDialog);
                break;

            case R.id.rlDefendants:

                if (llDefendantsContent.getVisibility() != View.VISIBLE) {
                    llDefendantsContent.setVisibility(View.VISIBLE);
                    loadCasesDefendantData();
                } else {
                    llDefendantsContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlHearings:

                if (llHearingsContent.getVisibility() != View.VISIBLE) {
                    llHearingsContent.setVisibility(View.VISIBLE);
                    loadCasesHearingsData();
                } else {
                    llHearingsContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlPersons:

                if (llPersonsContent.getVisibility() != View.VISIBLE) {
                    llPersonsContent.setVisibility(View.VISIBLE);
                    loadCasesPersonData();
                } else {
                    llPersonsContent.setVisibility(View.GONE);
                }

                break;
            case R.id.rlFurtherInfo:

                if (llFurtherInfoDetails.getVisibility() != View.VISIBLE) {
                    llFurtherInfoDetails.setVisibility(View.VISIBLE);
                    loadCasesAdditionalInfoData();
                } else {
                    llFurtherInfoDetails.setVisibility(View.GONE);
                }

                break;
        }
    }
}
