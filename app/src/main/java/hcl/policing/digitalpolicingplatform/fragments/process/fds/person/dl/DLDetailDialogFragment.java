package hcl.policing.digitalpolicingplatform.fragments.process.fds.person.dl;

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
import hcl.policing.digitalpolicingplatform.activities.map.FDSMapActivity;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.person.athena.AthenaListDialogFragment;
import hcl.policing.digitalpolicingplatform.listeners.dialog.ManuallyClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.PersonSelectionListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.dl.DLMenuDTListModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.dl.DLMenuEDListModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.dl.DLMenuESListModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.dl.DLMenuFEListModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.dl.DLMenuPEListModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.dl.DLMenuSMListModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.dl.DLMenuUTListModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.dl.DLMenuXRListModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.dl.DLResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.dl.DrivinglicencebyidlistModel;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class DLDetailDialogFragment extends DialogFragment implements View.OnClickListener {

    private Context mContext;
    private LinearLayout llDetails,llHeader, llDlDetails, llRecordTypeDetails, llDrivingSearchDetails, llEndorsementDetailsContent, llEndorsementSummaryContent,
            llUnclaimedTestContent, llCrossReferenceContent, llDocumentTrailContent, llStopsMarkersContent, llProvisionalEntitlementContent,
            llFullEntitlementContent;

    private Dialog mProgressDialog;
    private ManuallyClickListener manuallyClickListener;
    private PersonSelectionListener personSelectionListener;
    private int flowType;
    private DLResponse dlResponse;
    private ArrayList<DrivinglicencebyidlistModel> aDrivinglicencebyidlistModel;
    private List<DLMenuSMListModel> aDLMenuSMListModel;
    private List<DLMenuPEListModel> aDLMenuPEListModel;
    private List<DLMenuFEListModel> aDLMenuFEListModel;
    private List<DLMenuDTListModel> aDLMenuDTListModel;
    private List<DLMenuXRListModel> aDLMenuXRListModel;
    private List<DLMenuUTListModel> aDLMenuUTListModel;
    private List<DLMenuESListModel> aDLMenuESListModel;
    private List<DLMenuEDListModel> aDLMenuEDListModel;
    private String nominalAddress;
    private LayoutInflater layoutInflater;
    private boolean isPopulate;

    public static DLDetailDialogFragment newInstance(int type,boolean isPopulate) {

        DLDetailDialogFragment frag = new DLDetailDialogFragment();
        Bundle args = new Bundle();
        args.putInt(GenericConstant.TYPE_DETAILS, type);
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
        manuallyClickListener = (ManuallyClickListener) getActivity();
        personSelectionListener = (PersonSelectionListener) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dl_detail_fragment, container, false);
        layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (getArguments() != null) {
            flowType = getArguments().getInt(GenericConstant.TYPE_DETAILS);
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
     * Load the initial views
     * @param view
     */
    private void initView(View view) {

        llDetails = view.findViewById(R.id.llDetails);
        llHeader = view.findViewById(R.id.llHeader);
        llDlDetails = view.findViewById(R.id.llDlDetails);

        llRecordTypeDetails = llDlDetails.findViewById(R.id.llRecordTypeDetails);
        llDrivingSearchDetails = llDlDetails.findViewById(R.id.llDrivingSearchDetails);
        llEndorsementDetailsContent = llDlDetails.findViewById(R.id.llEndorsementDetailsContent);
        llEndorsementSummaryContent = llDlDetails.findViewById(R.id.llEndorsementSummaryContent);
        llUnclaimedTestContent = llDlDetails.findViewById(R.id.llUnclaimedTestContent);
        llCrossReferenceContent = llDlDetails.findViewById(R.id.llCrossReferenceContent);
        llDocumentTrailContent = llDlDetails.findViewById(R.id.llDocumentTrailContent);
        llStopsMarkersContent = llDlDetails.findViewById(R.id.llStopsMarkersContent);
        llProvisionalEntitlementContent = llDlDetails.findViewById(R.id.llProvisionalEntitlementContent);
        llFullEntitlementContent = llDlDetails.findViewById(R.id.llFullEntitlementContent);


        RelativeLayout rlRecordType = llDlDetails.findViewById(R.id.rlRecordType);
        RelativeLayout rlDrivingSearch = llDlDetails.findViewById(R.id.rlDrivingSearch);
        RelativeLayout rlEndorsementDetails = llDlDetails.findViewById(R.id.rlEndorsementDetails);
        RelativeLayout rlEndorsementSummary = llDlDetails.findViewById(R.id.rlEndorsementSummary);
        RelativeLayout rlUnclaimedTest = llDlDetails.findViewById(R.id.rlUnclaimedTest);
        RelativeLayout rlCrossReference = llDlDetails.findViewById(R.id.rlCrossReference);
        RelativeLayout rlDocumentTrail = llDlDetails.findViewById(R.id.rlDocumentTrail);
        RelativeLayout rlStopsMarkers = llDlDetails.findViewById(R.id.rlStopsMarkers);
        RelativeLayout rlProvisionalEntitlement = llDlDetails.findViewById(R.id.rlProvisionalEntitlement);
        RelativeLayout rlFullEntitlement = llDlDetails.findViewById(R.id.rlFullEntitlement);

        isPopulateView();
        loadHeaderLayout();

        if (flowType == GenericConstant.TYPE_PERSON) {
            loadDLJSONDetails(GenericConstant.PERSON_DL_DETAILS_JSON);

        } else if (flowType == GenericConstant.TYPE_DL_CHECK) {
            loadDLJSONDetails(GenericConstant.PERSON_DL_DETAILS_JSON);

        }

        rlRecordType.setOnClickListener(this);
        rlDrivingSearch.setOnClickListener(this);
        rlEndorsementDetails.setOnClickListener(this);
        rlEndorsementSummary.setOnClickListener(this);
        rlUnclaimedTest.setOnClickListener(this);
        rlCrossReference.setOnClickListener(this);
        rlDocumentTrail.setOnClickListener(this);
        rlStopsMarkers.setOnClickListener(this);
        rlProvisionalEntitlement.setOnClickListener(this);
        rlFullEntitlement.setOnClickListener(this);
    }


    /**
     * Check the condition for populate view.
     */
    private void isPopulateView() {
        if (!isPopulate) {
            CoordinatorLayout.LayoutParams relativeParams = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
            relativeParams.setMargins(0, 0, 0, 0);
            llDetails.setLayoutParams(relativeParams);
        }
    }


    /**
     * Load Header Layout
     */
    private void loadHeaderLayout() {

        TextView tvHeader = llHeader.findViewById(R.id.tvHeader);
        ImageView ivBack = llHeader.findViewById(R.id.ivBack);
        HorizontalScrollView llSteps = llHeader.findViewById(R.id.llSteps);
        TextView tvStepOne = llSteps.findViewById(R.id.tvStepOne);
        TextView tvStepTwo = llSteps.findViewById(R.id.tvStepTwo);

        String processName = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.PROCESS_NAME, "");
        String systemName = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.SYSTEM_NAME, "");

        tvStepOne.setText(processName);
        tvStepTwo.setText(systemName);

        if (flowType == GenericConstant.TYPE_PERSON) {
            tvHeader.setText(getString(R.string.dl_nominal_header));

        } else if (flowType == GenericConstant.TYPE_DL_CHECK) {
            tvHeader.setText(getString(R.string.dl_dlcheck_header));

        }

        ivBack.setOnClickListener(this);
    }


    /**
     * Load the Record Type Details
     */
    private void loadRecordType() {

        TextView tvLicenseCatValue = llRecordTypeDetails.findViewById(R.id.tvLicenseCatValue);
        TextView tvFromValue = llRecordTypeDetails.findViewById(R.id.tvFromValue);
        TextView tvUntilValue = llRecordTypeDetails.findViewById(R.id.tvUntilValue);

        if (aDrivinglicencebyidlistModel != null && aDrivinglicencebyidlistModel.size() > 0) {

            tvLicenseCatValue.setText(aDrivinglicencebyidlistModel.get(0).getLicencetype());
            tvFromValue.setText(aDrivinglicencebyidlistModel.get(0).getCommencementdate());
            tvUntilValue.setText(aDrivinglicencebyidlistModel.get(0).getExpirydate());
        }
    }

    /**
     * Load the Weapon Details
     */
    private void loadDrivingSearch() {

        TextView tvTitleValue = llDrivingSearchDetails.findViewById(R.id.tvTitleValue);
        TextView tvDisqualificationValue = llDrivingSearchDetails.findViewById(R.id.tvDisqualificationValue);
        TextView tvNameValue = llDrivingSearchDetails.findViewById(R.id.tvNameValue);
        TextView tvAddressValue = llDrivingSearchDetails.findViewById(R.id.tvAddressValue);
        TextView tvDlNoValue = llDrivingSearchDetails.findViewById(R.id.tvDlNoValue);
        TextView tvRecordTypeValue = llDrivingSearchDetails.findViewById(R.id.tvRecordTypeValue);
        TextView tvGenderValue = llDrivingSearchDetails.findViewById(R.id.tvGenderValue);
        TextView tvLastUpdateValue = llDrivingSearchDetails.findViewById(R.id.tvLastUpdateValue);
        TextView tvDOBValue = llDrivingSearchDetails.findViewById(R.id.tvDOBValue);
        TextView tvLicenceTypeValue = llDrivingSearchDetails.findViewById(R.id.tvLicenceTypeValue);
        TextView tvLicenceIssueNoValue = llDrivingSearchDetails.findViewById(R.id.tvLicenceIssueNoValue);
        TextView tvCounterPartIssueNoValue = llDrivingSearchDetails.findViewById(R.id.tvCounterPartIssueNoValue);
        TextView tvCommencementDateValue = llDrivingSearchDetails.findViewById(R.id.tvCommencementDateValue);
        TextView tvExpiryDateValue = llDrivingSearchDetails.findViewById(R.id.tvExpiryDateValue);
        TextView tvPostcodeValue = llDrivingSearchDetails.findViewById(R.id.tvPostcodeValue);
        TextView tvBirthPlaceValue = llDrivingSearchDetails.findViewById(R.id.tvBirthPlaceValue);

        if (aDrivinglicencebyidlistModel != null && aDrivinglicencebyidlistModel.size() > 0) {

            tvTitleValue.setText(aDrivinglicencebyidlistModel.get(0).getTitle());
            tvDisqualificationValue.setText(aDrivinglicencebyidlistModel.get(0).getDisqualification());
            tvNameValue.setText(aDrivinglicencebyidlistModel.get(0).getName());
            nominalAddress = aDrivinglicencebyidlistModel.get(0).getAddress();
            tvAddressValue.setText(nominalAddress);
            tvDlNoValue.setText(aDrivinglicencebyidlistModel.get(0).getDrivinglicenceno());
            tvRecordTypeValue.setText(aDrivinglicencebyidlistModel.get(0).getRecordtype());
            tvGenderValue.setText(aDrivinglicencebyidlistModel.get(0).getGender());
            tvLastUpdateValue.setText(aDrivinglicencebyidlistModel.get(0).getLastupdated());
            tvDOBValue.setText(aDrivinglicencebyidlistModel.get(0).getDob());
            tvLicenceTypeValue.setText(aDrivinglicencebyidlistModel.get(0).getLicencetype());
            tvLicenceIssueNoValue.setText(aDrivinglicencebyidlistModel.get(0).getLicenceissueno());
            tvCounterPartIssueNoValue.setText(aDrivinglicencebyidlistModel.get(0).getCounterpartissueno());
            tvCommencementDateValue.setText(aDrivinglicencebyidlistModel.get(0).getCommencementdate());
            tvExpiryDateValue.setText(aDrivinglicencebyidlistModel.get(0).getExpirydate());
            tvPostcodeValue.setText(aDrivinglicencebyidlistModel.get(0).getPostcode());
            tvBirthPlaceValue.setText(aDrivinglicencebyidlistModel.get(0).getBirthplace());

        }

        tvAddressValue.setOnClickListener(this);
    }


    /**
     * Load the Endorsement Details
     */
    private void loadEndorsementData() {

        llEndorsementDetailsContent.removeAllViews();

        if (aDLMenuEDListModel != null && aDLMenuEDListModel.size() > 0) {
            for (int i = 0; i < aDLMenuEDListModel.size(); i++) {

                DLMenuEDListModel mDLMenuEDListModel = aDLMenuEDListModel.get(i);

                View view = layoutInflater.inflate(R.layout.dl_endorsement_details_items, null);

                TextView tvConvictionCourtValue = view.findViewById(R.id.tvConvictionCourtValue);
                TextView tvConvictionDateValue = view.findViewById(R.id.tvConvictionDateValue);
                TextView tvOffenceCodeValue = view.findViewById(R.id.tvOffenceCodeValue);
                TextView tvOffenceDateValue = view.findViewById(R.id.tvOffenceDateValue);
                TextView tvFineValue = view.findViewById(R.id.tvFineValue);
                TextView tvPointsValue = view.findViewById(R.id.tvPointsValue);
                TextView tvDisqPeriodValue = view.findViewById(R.id.tvDisqPeriodValue);
                TextView tvOtherSentenceValue = view.findViewById(R.id.tvOtherSentenceValue);
                TextView tvSuspendSentenceValue = view.findViewById(R.id.tvSuspendSentenceValue);
                TextView tvDateDisqRemovedValue = view.findViewById(R.id.tvDateDisqRemovedValue);
                TextView tvDTTDSPValue = view.findViewById(R.id.tvDTTDSPValue);
                TextView tvSentencingCourtValue = view.findViewById(R.id.tvSentencingCourtValue);
                TextView tvSentencingDateValue = view.findViewById(R.id.tvSentencingDateValue);
                TextView tvDisqPendAppealValue = view.findViewById(R.id.tvDisqPendAppealValue);
                TextView tvAppealDateValue = view.findViewById(R.id.tvAppealDateValue);
                TextView tvDisqReImposedValue = view.findViewById(R.id.tvDisqReImposedValue);
                TextView tvAppealCourtValue = view.findViewById(R.id.tvAppealCourtValue);
                TextView tvRehabeReductionValue = view.findViewById(R.id.tvRehabeReductionValue);

                tvConvictionCourtValue.setText(mDLMenuEDListModel.getConvictioncourt());
                tvConvictionDateValue.setText(mDLMenuEDListModel.getConvictiondate());
                tvOffenceCodeValue.setText(mDLMenuEDListModel.getOffencecode());
                tvOffenceDateValue.setText(mDLMenuEDListModel.getOffencedate());
                tvFineValue.setText(mDLMenuEDListModel.getFine());
                tvPointsValue.setText(mDLMenuEDListModel.getPoints());
                tvDisqPeriodValue.setText(mDLMenuEDListModel.getDisqperiod());
                tvOtherSentenceValue.setText(mDLMenuEDListModel.getOthersentence());
                tvSuspendSentenceValue.setText(mDLMenuEDListModel.getSuspendsentence());
                tvDateDisqRemovedValue.setText(mDLMenuEDListModel.getDatedisqremoved());
                tvDTTDSPValue.setText(mDLMenuEDListModel.getDttpordps());
                tvSentencingCourtValue.setText(mDLMenuEDListModel.getSentencingcourt());
                tvSentencingDateValue.setText(mDLMenuEDListModel.getSentencingdate());
                tvDisqPendAppealValue.setText(mDLMenuEDListModel.getDisqpendappeal());
                tvAppealDateValue.setText(mDLMenuEDListModel.getAppealdate());
                tvDisqReImposedValue.setText(mDLMenuEDListModel.getDisqreimposed());
                tvAppealCourtValue.setText(mDLMenuEDListModel.getAppealcourt());
                tvRehabeReductionValue.setText(mDLMenuEDListModel.getRehabreduction());

                llEndorsementDetailsContent.addView(view);
            }
        }
    }


    /**
     * Load the DrivingSummary
     */
    private void loadDrivingSummaryData() {

        llEndorsementSummaryContent.removeAllViews();
        if (aDLMenuESListModel != null && aDLMenuESListModel.size() > 0) {
            for (int i = 0; i < aDLMenuESListModel.size(); i++) {

                DLMenuESListModel mDLMenuESListModel = aDLMenuESListModel.get(i);
                View view = layoutInflater.inflate(R.layout.dl_driving_summary_items, null);

                TextView tvDisqualifiedValue = view.findViewById(R.id.tvDisqualifiedValue);
                TextView tvDrinkDrugValue = view.findViewById(R.id.tvDrinkDrugValue);
                TextView tvOtherValue = view.findViewById(R.id.tvOtherValue);
                TextView tvPointsValue = view.findViewById(R.id.tvPointsValue);

                tvDisqualifiedValue.setText(mDLMenuESListModel.getDisqualified());
                tvDrinkDrugValue.setText(mDLMenuESListModel.getDrinkdrug());
                tvOtherValue.setText(mDLMenuESListModel.getOther());
                tvPointsValue.setText(mDLMenuESListModel.getPoints());

                llEndorsementSummaryContent.addView(view);
            }
        }
    }

    /**
     * Load the UnclaimedTest
     */
    private void loadUnclaimedTestData() {

        llUnclaimedTestContent.removeAllViews();
        if (aDLMenuUTListModel != null && aDLMenuUTListModel.size() > 0) {
            for (int i = 0; i < aDLMenuUTListModel.size(); i++) {

                DLMenuUTListModel mDLMenuUTListModel = aDLMenuUTListModel.get(i);
                View view = layoutInflater.inflate(R.layout.dl_unclaimed_test_items, null);

                TextView tvCategoryValue = view.findViewById(R.id.tvCategoryValue);
                TextView tvHarmValue = view.findViewById(R.id.tvHarmValue);
                TextView tvDescValue = view.findViewById(R.id.tvDescValue);
                TextView tvRestrictionValue = view.findViewById(R.id.tvRestrictionValue);

                tvCategoryValue.setText(mDLMenuUTListModel.getCategory());
                tvHarmValue.setText(mDLMenuUTListModel.getHarm());

                StringBuilder sbDesc = new StringBuilder();
                sbDesc.append(mDLMenuUTListModel.getDescription1()).append("\n").append(mDLMenuUTListModel.getDescription2());

                StringBuilder sbRes = new StringBuilder();
                sbRes.append(mDLMenuUTListModel.getRestriction1()).append("\n").append(mDLMenuUTListModel.getRestriction2());

                tvDescValue.setText(sbDesc.toString());
                tvRestrictionValue.setText(sbRes.toString());

                llUnclaimedTestContent.addView(view);
            }
        }
    }

    /**
     * Load the Driver Cross Reference
     */
    private void loadDriverCrossRefData() {

        llCrossReferenceContent.removeAllViews();
        if (aDLMenuXRListModel != null && aDLMenuXRListModel.size() > 0) {
            for (int i = 0; i < aDLMenuXRListModel.size(); i++) {

                DLMenuXRListModel mDLMenuXRListModel = aDLMenuXRListModel.get(i);
                View view = layoutInflater.inflate(R.layout.dl_document_trail_items, null);

                TextView tvDriver = view.findViewById(R.id.tvDocument);
                TextView tvDate = view.findViewById(R.id.tvDate);
                TextView tvName = view.findViewById(R.id.tvDescription);
                TextView tvDocumentValue = view.findViewById(R.id.tvDocumentValue);
                TextView tvDateValue = view.findViewById(R.id.tvDateValue);
                TextView tvDescriptionValue = view.findViewById(R.id.tvDescriptionValue);

                tvDriver.setText(getString(R.string.driver));
                tvDate.setText(getString(R.string.date));
                tvName.setText(getString(R.string.name));
                tvDocumentValue.setText(mDLMenuXRListModel.getDriver());
                tvDateValue.setText(mDLMenuXRListModel.getDate());
                StringBuilder sbName = new StringBuilder();
                sbName.append(mDLMenuXRListModel.getName1()).append(" ").append(mDLMenuXRListModel.getName2());
                tvDescriptionValue.setText(sbName);

                llCrossReferenceContent.addView(view);
            }
        }
    }

    /**
     * Load the Document Trail Details
     */
    private void loadDocumentTrailData() {

        llDocumentTrailContent.removeAllViews();
        if (aDLMenuDTListModel != null && aDLMenuDTListModel.size() > 0) {
            for (int i = 0; i < aDLMenuDTListModel.size(); i++) {

                DLMenuDTListModel mDLMenuDTListModel = aDLMenuDTListModel.get(i);
                View view = layoutInflater.inflate(R.layout.dl_document_trail_items, null);

                TextView tvDocumentValue = view.findViewById(R.id.tvDocumentValue);
                TextView tvDateValue = view.findViewById(R.id.tvDateValue);
                TextView tvDescriptionValue = view.findViewById(R.id.tvDescriptionValue);

                tvDocumentValue.setText(mDLMenuDTListModel.getDocument());
                tvDateValue.setText(mDLMenuDTListModel.getDate());
                StringBuilder sbDesc = new StringBuilder();
                sbDesc.append(mDLMenuDTListModel.getDescription1()).append("\n").append(mDLMenuDTListModel.getDescription2());
                tvDescriptionValue.setText(sbDesc.toString());

                llDocumentTrailContent.addView(view);
            }
        }
    }

    /**
     * Load the Stop Markers Details
     */
    private void loadStopMarkersData() {

        llStopsMarkersContent.removeAllViews();

        if (aDLMenuSMListModel != null && aDLMenuSMListModel.size() > 0) {
            for (int i = 0; i < aDLMenuSMListModel.size(); i++) {

                DLMenuSMListModel mDLMenuSMListModel = aDLMenuSMListModel.get(i);

                View view = layoutInflater.inflate(R.layout.dl_stop_markers_items, null);
                TextView tvDescriptionValue = view.findViewById(R.id.tvDescriptionValue);
                tvDescriptionValue.setText(mDLMenuSMListModel.getDescription());
                llStopsMarkersContent.addView(view);
            }
        }
    }


    /**
     * Load the Provisional Entitle Details
     */
    private void loadProvisionalEntitleData() {

        llProvisionalEntitlementContent.removeAllViews();

        if (aDLMenuPEListModel != null && aDLMenuPEListModel.size() > 0) {
            for (int i = 0; i < aDLMenuPEListModel.size(); i++) {

                DLMenuPEListModel mDLMenuPEListModel = aDLMenuPEListModel.get(i);

                View view = layoutInflater.inflate(R.layout.dl_provisional_entitle_items, null);

                TextView tvCategoryValue = view.findViewById(R.id.tvCategoryValue);
                TextView tvFromValue = view.findViewById(R.id.tvFromValue);
                TextView tvUntilValue = view.findViewById(R.id.tvUntilValue);
                TextView tvDescriptionValue = view.findViewById(R.id.tvDescriptionValue);
                TextView tvRestrictionValue = view.findViewById(R.id.tvRestrictionValue);

                tvCategoryValue.setText(mDLMenuPEListModel.getCategory());
                tvFromValue.setText(mDLMenuPEListModel.getFrom());
                tvUntilValue.setText(mDLMenuPEListModel.getUntil());

                StringBuilder sbDesc = new StringBuilder();
                sbDesc.append(mDLMenuPEListModel.getDescription1()).append("\n").append(mDLMenuPEListModel.getDescription2());
                tvDescriptionValue.setText(sbDesc.toString());

                StringBuilder sbRes = new StringBuilder();
                sbDesc.append(mDLMenuPEListModel.getRestriction1()).append("\n").append(mDLMenuPEListModel.getRestriction2());
                tvRestrictionValue.setText(sbRes.toString());


                llProvisionalEntitlementContent.addView(view);
            }
        }
    }

    /**
     * Load the Full Entitle Details
     */
    private void loadFullEntitleData() {

        llFullEntitlementContent.removeAllViews();

        if (aDLMenuFEListModel != null && aDLMenuFEListModel.size() > 0) {
            for (int i = 0; i < aDLMenuFEListModel.size(); i++) {

                DLMenuFEListModel mDLMenuFEListModel = aDLMenuFEListModel.get(i);

                View view = layoutInflater.inflate(R.layout.dl_provisional_entitle_items, null);

                TextView tvCategoryValue = view.findViewById(R.id.tvCategoryValue);
                TextView tvFromValue = view.findViewById(R.id.tvFromValue);
                TextView tvUntilValue = view.findViewById(R.id.tvUntilValue);
                TextView tvDescriptionValue = view.findViewById(R.id.tvDescriptionValue);
                TextView tvRestrictionValue = view.findViewById(R.id.tvRestrictionValue);

                tvCategoryValue.setText(mDLMenuFEListModel.getCategory());
                tvFromValue.setText(mDLMenuFEListModel.getFrom());
                tvUntilValue.setText(mDLMenuFEListModel.getUntil());

                StringBuilder sbDesc = new StringBuilder();
                sbDesc.append(mDLMenuFEListModel.getDescription1()).append("\n").append(mDLMenuFEListModel.getDescription2());
                tvDescriptionValue.setText(sbDesc.toString());

                StringBuilder sbRes = new StringBuilder();
                sbDesc.append(mDLMenuFEListModel.getRestriction1()).append("\n").append(mDLMenuFEListModel.getRestriction2());
                tvRestrictionValue.setText(sbRes.toString());


                llFullEntitlementContent.addView(view);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                DialogUtil.cancelProgressDialog(mProgressDialog);
                dismiss();

                break;

            case R.id.tvAddressValue:

                FDSMapActivity.callMapActivity(mContext, nominalAddress);

                break;

            case R.id.rlRecordType:

                if (llRecordTypeDetails.getVisibility() != View.VISIBLE) {
                    llRecordTypeDetails.setVisibility(View.VISIBLE);
                    loadRecordType();
                } else {
                    llRecordTypeDetails.setVisibility(View.GONE);
                }

                break;
            case R.id.rlDrivingSearch:

                if (llDrivingSearchDetails.getVisibility() != View.VISIBLE) {
                    llDrivingSearchDetails.setVisibility(View.VISIBLE);
                    loadDrivingSearch();
                } else {
                    llDrivingSearchDetails.setVisibility(View.GONE);
                }

                break;


            case R.id.rlEndorsementDetails:

                if (llEndorsementDetailsContent.getVisibility() != View.VISIBLE) {
                    llEndorsementDetailsContent.setVisibility(View.VISIBLE);

                    loadEndorsementData();

                } else {
                    llEndorsementDetailsContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlEndorsementSummary:

                if (llEndorsementSummaryContent.getVisibility() != View.VISIBLE) {
                    llEndorsementSummaryContent.setVisibility(View.VISIBLE);

                    loadDrivingSummaryData();
                } else {
                    llEndorsementSummaryContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlUnclaimedTest:

                if (llUnclaimedTestContent.getVisibility() != View.VISIBLE) {
                    llUnclaimedTestContent.setVisibility(View.VISIBLE);

                    loadUnclaimedTestData();
                } else {
                    llUnclaimedTestContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlCrossReference:

                if (llCrossReferenceContent.getVisibility() != View.VISIBLE) {
                    llCrossReferenceContent.setVisibility(View.VISIBLE);

                    loadDriverCrossRefData();
                } else {
                    llCrossReferenceContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlDocumentTrail:

                if (llDocumentTrailContent.getVisibility() != View.VISIBLE) {
                    llDocumentTrailContent.setVisibility(View.VISIBLE);
                    loadDocumentTrailData();
                } else {
                    llDocumentTrailContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlStopsMarkers:

                if (llStopsMarkersContent.getVisibility() != View.VISIBLE) {
                    llStopsMarkersContent.setVisibility(View.VISIBLE);
                    loadStopMarkersData();
                } else {
                    llStopsMarkersContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlProvisionalEntitlement:

                if (llProvisionalEntitlementContent.getVisibility() != View.VISIBLE) {
                    llProvisionalEntitlementContent.setVisibility(View.VISIBLE);
                    loadProvisionalEntitleData();
                } else {
                    llProvisionalEntitlementContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlFullEntitlement:

                if (llFullEntitlementContent.getVisibility() != View.VISIBLE) {
                    llFullEntitlementContent.setVisibility(View.VISIBLE);
                    loadFullEntitleData();
                } else {
                    llFullEntitlementContent.setVisibility(View.GONE);
                }

                break;
        }

    }


    /**
     * Load Person DL JSON
     */
    public void loadDLJSONDetails(String fileName) {
        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);
                dlResponse = (DLResponse) JsonUtil.getInstance().toModel(strJson, DLResponse.class);
                if (dlResponse != null) {
                    aDrivinglicencebyidlistModel = (ArrayList<DrivinglicencebyidlistModel>) dlResponse.getDrivinglicencebyidlist();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (aDrivinglicencebyidlistModel != null) {

                        aDLMenuSMListModel = dlResponse.getDlmenusmlist();
                        aDLMenuPEListModel = dlResponse.getDlmenupelist();
                        aDLMenuFEListModel = dlResponse.getDlmenufelist();
                        aDLMenuDTListModel = dlResponse.getDlmenudtlist();
                        aDLMenuXRListModel = dlResponse.getDlmenuxrlist();
                        aDLMenuUTListModel = dlResponse.getDlmenuutlist();
                        aDLMenuESListModel = dlResponse.getDlmenueslist();
                        aDLMenuEDListModel = dlResponse.getDlmenuedlist();

                        loadRecordType();

                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), AthenaListDialogFragment.class, "loadAthenaPersonDetails");
            }
        }).start();
    }
}

