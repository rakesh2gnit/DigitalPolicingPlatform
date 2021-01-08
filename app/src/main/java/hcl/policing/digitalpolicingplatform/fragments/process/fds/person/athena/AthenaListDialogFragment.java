package hcl.policing.digitalpolicingplatform.fragments.process.fds.person.athena;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
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
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.database.DatabaseHelper;
import hcl.policing.digitalpolicingplatform.listeners.dialog.ManuallyClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.PersonSelectionListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.athena.AddressAthenaDetailResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.athena.AddressModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.cases.athena.CasedetailModels;
import hcl.policing.digitalpolicingplatform.models.process.fds.cases.athena.CasesAthenaDetailResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.cases.athena.DefendantsModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.cases.athena.HearingsModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.cases.athena.OffencesModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.communication.athena.CommunicationAthenaDetailResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.communication.athena.CommunicationModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.courtwarrant.athena.CourtWarrantAthenaDetailResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.courtwarrant.athena.CourtWarrantModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.investigation.athena.EnquiryLogsModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.investigation.athena.InvestigationAthenaDetailResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.investigation.athena.InvestigationModels;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.ObjectIterationAthenaDetailResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.PersonAthenaDetailResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.PersonModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.PhotosBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.SearchdetailresponseBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.WarningsModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.athena.VehicleAthenaDetailResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.athena.VehicleModel;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.Base64ToBitmapUtil;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class AthenaListDialogFragment extends DialogFragment implements View.OnClickListener {

    private Context mContext;
    private LinearLayout llDetails, llHeader, llCommonDetails, llAddionalLinkContent, llFurtherInfoDetails, llPrimaryOffenceDetails,
            llEnquiryLogContent, llInvestigationDetails, llDefendantsContent, llHearingsContent, llPersonsContent,
            llCommunicationDetails, llCourtWarrantDetails;
    private RelativeLayout rlImage, rlFurtherInfo;
    private LinearLayout person_detail, vehicle_detail, address_detail, investigation_detail, case_detail, communication_detail, court_warrent_detail,
            llObjectIteration;
    private TextView tvObjectIterationCount;
    private HorizontalScrollView hScrollViewImage;
    private String searchedWord;
    private SearchdetailresponseBean searchdetailresponseBean = null;
    private Dialog mProgressDialog;
    private ManuallyClickListener manuallyClickListener;
    private PersonSelectionListener personSelectionListener;
    private ArrayList<List<?>> listDetails;
    private int flowType;
    private DatabaseHelper db;
    private ArrayList<PersonModel> personModels;
    private ArrayList<VehicleModel> vehicleModels;
    private ArrayList<AddressModel> addressModels;
    private ArrayList<InvestigationModels> investigationModels;
    private ArrayList<CasedetailModels> casedetailModels;
    private ArrayList<CommunicationModel> communicationModels;
    private ArrayList<CourtWarrantModel> courtWarrantModels;
    private LayoutInflater layoutInflater;
    private ArrayList<PhotosBean> aPhotosBean;
    private List<EnquiryLogsModel> aEnquiryLogsModel;
    private ArrayList<String> labelList = new ArrayList<>(Arrays.asList("Short Summary", "Relevent Date/Time"));
    private PersonATHENAListDetailsDialogFragment personATHENAListDetailsDialogFragment;
    private StringBuilder sbLinkedHeader;
    private boolean isPopulate;


    public static AthenaListDialogFragment newInstance(int type, boolean isPopulate) {

        AthenaListDialogFragment frag = new AthenaListDialogFragment();
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

        View rootView = inflater.inflate(R.layout.athena_list_fragment, container, false);
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
     *
     * @param view
     */
    private void initView(View view) {

        llDetails = view.findViewById(R.id.llDetails);
        llHeader = view.findViewById(R.id.llHeader);
        llCommonDetails = view.findViewById(R.id.llCommonDetails);
        person_detail = view.findViewById(R.id.person_detail);
        vehicle_detail = view.findViewById(R.id.vehicle_detail);
        address_detail = view.findViewById(R.id.address_detail);
        investigation_detail = view.findViewById(R.id.investigation_detail);
        case_detail = view.findViewById(R.id.case_detail);
        communication_detail = view.findViewById(R.id.communication_detail);
        court_warrent_detail = view.findViewById(R.id.court_warrent_detail);

        sbLinkedHeader = new StringBuilder();
        isPopulateView();
        loadHeaderLayout();

        if (flowType == GenericConstant.TYPE_PERSON) {
            person_detail.setVisibility(View.VISIBLE);
            loadPersonJSONDetails(GenericConstant.PERSON_ATHENA_DETAILS_JSON);
        } else if (flowType == GenericConstant.TYPE_VEHICLE) {

            vehicle_detail.setVisibility(View.VISIBLE);
            loadVehicleJSONDetails(GenericConstant.VEHICLE_ATHENA_DETAILS_JSON);

        } else if (flowType == GenericConstant.TYPE_ADDRESS) {
            address_detail.setVisibility(View.VISIBLE);
            loadAddressJSONDetails(GenericConstant.ADDRESS_ATHENA_DETAILS_JSON);

        } else if (flowType == GenericConstant.TYPE_INVESTIGATION) {
            investigation_detail.setVisibility(View.VISIBLE);
            loadInvestigationJSONDetails(GenericConstant.INVESTIGATION_ATHENA_DETAILS_JSON);

        } else if (flowType == GenericConstant.TYPE_CASES) {
            case_detail.setVisibility(View.VISIBLE);
            loadCasesJSONDetails(GenericConstant.CASE_ATHENA_DETAILS_JSON);

        } else if (flowType == GenericConstant.TYPE_COMMUNICATION) {
            communication_detail.setVisibility(View.VISIBLE);
            loadCommunicationJSONDetails(GenericConstant.COMMUNICATION_ATHENA_DETAILS_JSON);

        } else if (flowType == GenericConstant.TYPE_COURT_WARRENT) {
            court_warrent_detail.setVisibility(View.VISIBLE);
            loadCourtWarrantJSONDetails(GenericConstant.COURT_WARRANT_ATHENA_DETAILS_JSON);
        }
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
     * Load the common view which are same for all type systems
     */
    private void loadCommonView() {

        RelativeLayout rlPersonList = llCommonDetails.findViewById(R.id.rlPersonList);
        RelativeLayout rlVehicle = llCommonDetails.findViewById(R.id.rlVehicle);
        RelativeLayout rlInvestigation = llCommonDetails.findViewById(R.id.rlInvestigation);
        RelativeLayout rlIntelligence = llCommonDetails.findViewById(R.id.rlIntelligence);
        RelativeLayout rlCourtWarrant = llCommonDetails.findViewById(R.id.rlCourtWarrant);
        RelativeLayout rlCases = llCommonDetails.findViewById(R.id.rlCases);
        RelativeLayout rlLocation = llCommonDetails.findViewById(R.id.rlLocation);
        RelativeLayout rlCustody = llCommonDetails.findViewById(R.id.rlCustody);
        RelativeLayout rlCommunication = llCommonDetails.findViewById(R.id.rlCommunication);
        RelativeLayout rlOperations = llCommonDetails.findViewById(R.id.rlOperations);
        RelativeLayout rlAddionalLink = llCommonDetails.findViewById(R.id.rlAddionalLink);
        RelativeLayout rlObjectIteration = llCommonDetails.findViewById(R.id.rlObjectIteration);

        LinearLayout llObjectIteration = llCommonDetails.findViewById(R.id.llObjectIteration);
        llAddionalLinkContent = llCommonDetails.findViewById(R.id.llAddionalLinkContent);

        TextView tvPersonListCount = llCommonDetails.findViewById(R.id.tvPersonListCount);
        TextView tvVehicleCount = llCommonDetails.findViewById(R.id.tvVehicleCount);
        TextView tvInvestigationCount = llCommonDetails.findViewById(R.id.tvInvestigationCount);
        TextView tvIntelligenceCount = llCommonDetails.findViewById(R.id.tvIntelligenceCount);
        TextView tvCourtWarrantCount = llCommonDetails.findViewById(R.id.tvCourtWarrantCount);
        TextView tvCasesCount = llCommonDetails.findViewById(R.id.tvCasesCount);
        TextView tvLocationCount = llCommonDetails.findViewById(R.id.tvLocationCount);
        TextView tvCustodyCount = llCommonDetails.findViewById(R.id.tvCustodyCount);
        TextView tvCommunicationCount = llCommonDetails.findViewById(R.id.tvCommunicationCount);
        TextView tvOperationsCount = llCommonDetails.findViewById(R.id.tvOperationsCount);
        tvObjectIterationCount = llCommonDetails.findViewById(R.id.tvObjectIterationCount);

        if (flowType == GenericConstant.TYPE_PERSON) {
            llObjectIteration.setVisibility(View.VISIBLE);
        }

        if (searchdetailresponseBean != null) {

            if (!TextUtils.isEmpty(searchdetailresponseBean.getLinkedpersoncount())) {

                tvPersonListCount.setText(searchdetailresponseBean.getLinkedpersoncount());
            }
            if (!TextUtils.isEmpty(searchdetailresponseBean.getVehiclecount())) {

                tvVehicleCount.setText(searchdetailresponseBean.getVehiclecount());
            }
            if (!TextUtils.isEmpty(searchdetailresponseBean.getInvestigationcount())) {

                tvInvestigationCount.setText(searchdetailresponseBean.getInvestigationcount());
            }
            if (!TextUtils.isEmpty(searchdetailresponseBean.getIntelligencecount())) {

                tvIntelligenceCount.setText(searchdetailresponseBean.getIntelligencecount());
            }
            if (!TextUtils.isEmpty(searchdetailresponseBean.getCourtwarrentcount())) {

                tvCourtWarrantCount.setText(searchdetailresponseBean.getCourtwarrentcount());
            }
            if (!TextUtils.isEmpty(searchdetailresponseBean.getOperationcount())) {

                tvOperationsCount.setText(searchdetailresponseBean.getOperationcount());
            }
            if (!TextUtils.isEmpty(searchdetailresponseBean.getObjectiterationcount())) {

                tvObjectIterationCount.setText(searchdetailresponseBean.getObjectiterationcount());
            }
            if (!TextUtils.isEmpty(searchdetailresponseBean.getCasecount())) {
                tvCasesCount.setText(searchdetailresponseBean.getCasecount());
            }
            if (!TextUtils.isEmpty(searchdetailresponseBean.getLocationcount())) {
                tvLocationCount.setText(searchdetailresponseBean.getLocationcount());
            }
            if (!TextUtils.isEmpty(searchdetailresponseBean.getCustodycount())) {
                tvCustodyCount.setText(searchdetailresponseBean.getCustodycount());
            }
            if (!TextUtils.isEmpty(searchdetailresponseBean.getCommunicationcount())) {
                tvCommunicationCount.setText(searchdetailresponseBean.getCommunicationcount());
            }

        }

        rlPersonList.setOnClickListener(this);
        rlVehicle.setOnClickListener(this);
        rlInvestigation.setOnClickListener(this);
        rlIntelligence.setOnClickListener(this);
        rlCourtWarrant.setOnClickListener(this);
        rlCases.setOnClickListener(this);
        rlLocation.setOnClickListener(this);
        rlCustody.setOnClickListener(this);
        rlCommunication.setOnClickListener(this);
        rlOperations.setOnClickListener(this);
        rlAddionalLink.setOnClickListener(this);
        rlObjectIteration.setOnClickListener(this);
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
            tvHeader.setText(getString(R.string.athena_nominal_header));

        } else if (flowType == GenericConstant.TYPE_VEHICLE) {
            tvHeader.setText(getString(R.string.athena_vehicle_header));

        } else if (flowType == GenericConstant.TYPE_ADDRESS) {
            tvHeader.setText(getString(R.string.athena_address_header));

        } else if (flowType == GenericConstant.TYPE_INVESTIGATION) {
            tvHeader.setText(getString(R.string.athena_investigation_header));

        } else if (flowType == GenericConstant.TYPE_CASES) {
            tvHeader.setText(getString(R.string.athena_cases_header));

        } else if (flowType == GenericConstant.TYPE_COMMUNICATION) {
            tvHeader.setText(getString(R.string.athena_communication_header));

        }else if (flowType == GenericConstant.TYPE_COURT_WARRENT) {
            tvHeader.setText(getString(R.string.athena_court_warrant_header));

        }

        ivBack.setOnClickListener(this);
    }

    /**
     * Load the Person Details
     */
    private void loadPersonData() {

        personModels = (ArrayList<PersonModel>) listDetails.get(0);
        List<WarningsModel> aWarningsBean = personModels.get(0).getWarnings();
        aPhotosBean = (ArrayList<PhotosBean>) personModels.get(0).getPhotos();

        llFurtherInfoDetails = person_detail.findViewById(R.id.llFurtherInfoDetails);
        rlImage = person_detail.findViewById(R.id.rlImage);
        rlFurtherInfo = person_detail.findViewById(R.id.rlFurtherInfo);
        hScrollViewImage = person_detail.findViewById(R.id.hScrollViewImage);

        TextView tvImageCount = person_detail.findViewById(R.id.tvImageCount);
        TextView tvFlowName = person_detail.findViewById(R.id.tvFlowName);
        TextView tvLPlaceValue = person_detail.findViewById(R.id.tvLPlaceValue);
        TextView tvDOBPersonValue = person_detail.findViewById(R.id.tvDOBPersonValue);
        TextView tvAddressValue = person_detail.findViewById(R.id.tvAddressValue);
        TextView tvPersonCertificateValue = person_detail.findViewById(R.id.tvPersonCertificateValue);
        TextView tvPNCIDValue = person_detail.findViewById(R.id.tvPNCIDValue);
        LinearLayout llWarning = person_detail.findViewById(R.id.llWarning);
        RelativeLayout rlBadge = person_detail.findViewById(R.id.rlBadge);
        TextView tvBadgeCount = person_detail.findViewById(R.id.tvBadgeCount);

        tvFlowName.setText(personModels.get(0).getFirstname1() + " " + personModels.get(0).getLastname());
        tvLPlaceValue.setText(personModels.get(0).getEthnicity());
        tvDOBPersonValue.setText(personModels.get(0).getDob());
        tvAddressValue.setText(personModels.get(0).getLatesthomeaddress());
        tvPersonCertificateValue.setText(personModels.get(0).getFirarmcertificateholder());
        tvPNCIDValue.setText(personModels.get(0).getPncId());

        rlImage.setOnClickListener(this);
        rlFurtherInfo.setOnClickListener(this);

        if (aWarningsBean != null && aWarningsBean.size() > 0) {
            rlBadge.setVisibility(View.VISIBLE);
            tvBadgeCount.setText(String.valueOf(aWarningsBean.size()));

            for (int i = 0; i < aWarningsBean.size(); i++) {

                View view = layoutInflater.inflate(R.layout.person_details_warning_items, null);
                TextView tvWarningLabel = view.findViewById(R.id.tvWarningLabel);
                TextView tvWarningDate = view.findViewById(R.id.tvWarningDate);

                tvWarningLabel.setText(aWarningsBean.get(i).getMarkervalue());
                tvWarningDate.setText("(" + aWarningsBean.get(i).getFromdate() + " - " + aWarningsBean.get(i).getTodate());

                llWarning.addView(view);
            }
        }

        if (aPhotosBean != null && aPhotosBean.size() > 0) {
            tvImageCount.setText(String.valueOf(aPhotosBean.size()));
        }
    }


    /**
     * Load the Vehicle Details
     */
    private void loadVehicleData() {

        vehicleModels = (ArrayList<VehicleModel>) listDetails.get(0);
        ArrayList<WarningsModel> aWarningsBean = (ArrayList<WarningsModel>) vehicleModels.get(0).getWarnings();
        aPhotosBean = (ArrayList<PhotosBean>) vehicleModels.get(0).getPhotos();

        llFurtherInfoDetails = vehicle_detail.findViewById(R.id.llAdditionalInfoDetails);
        rlImage = vehicle_detail.findViewById(R.id.rlImage);
        rlFurtherInfo = vehicle_detail.findViewById(R.id.rlFurtherInfo);
        hScrollViewImage = vehicle_detail.findViewById(R.id.hScrollViewImage);

        TextView tvImageCount = vehicle_detail.findViewById(R.id.tvImageCount);
        TextView tvVRMValue = vehicle_detail.findViewById(R.id.tvVRMValue);
        TextView tvMakeValue = vehicle_detail.findViewById(R.id.tvMakeValue);
        TextView tvModelValue = vehicle_detail.findViewById(R.id.tvModelValue);
        TextView tvFuelTypeValue = vehicle_detail.findViewById(R.id.tvFuelTypeValue);
        TextView tvCategoryValue = vehicle_detail.findViewById(R.id.tvCategoryValue);
        TextView tvForeignVehicleValue = vehicle_detail.findViewById(R.id.tvForeignVehicleValue);
        TextView tvBodyTypeValue = vehicle_detail.findViewById(R.id.tvBodyTypeValue);
        TextView tvColorValue = vehicle_detail.findViewById(R.id.tvColorValue);
        TextView tvANPRValue = vehicle_detail.findViewById(R.id.tvANPRValue);
        TextView tvANPRReasonValue = vehicle_detail.findViewById(R.id.tvANPRReasonValue);
        LinearLayout llWarning = vehicle_detail.findViewById(R.id.llWarning);

        tvVRMValue.setText(vehicleModels.get(0).getRegistrationnumber());
        tvMakeValue.setText(vehicleModels.get(0).getMake());
        tvModelValue.setText(vehicleModels.get(0).getModel());
        tvFuelTypeValue.setText(vehicleModels.get(0).getFueltype());
        tvCategoryValue.setText(vehicleModels.get(0).getCategory());
        tvForeignVehicleValue.setText(vehicleModels.get(0).getForeignvehicle());
        tvBodyTypeValue.setText(vehicleModels.get(0).getType());
        tvColorValue.setText(vehicleModels.get(0).getVehiclecolour());
        tvANPRReasonValue.setText(vehicleModels.get(0).getAnprreason());
        tvCategoryValue.setText(vehicleModels.get(0).getCategory());
        tvForeignVehicleValue.setText(vehicleModels.get(0).getForeignvehicle());
        tvBodyTypeValue.setText(vehicleModels.get(0).getType());

        rlImage.setOnClickListener(this);
        rlFurtherInfo.setOnClickListener(this);

        if (aWarningsBean != null && aWarningsBean.size() > 0) {
            for (int i = 0; i < aWarningsBean.size(); i++) {

                View view = layoutInflater.inflate(R.layout.vehicle_details_warning_items, null);
                LinearLayout llHeader = view.findViewById(R.id.llHeader);
                TextView tvLabelValue = view.findViewById(R.id.tvLabelValue);
                TextView tvLabelDate = view.findViewById(R.id.tvLabelDate);
                TextView tvLabelDescription = view.findViewById(R.id.tvLabelDescription);

                tvLabelValue.setText(aWarningsBean.get(i).getMarkervalue());
                tvLabelDate.setText("(from " + aWarningsBean.get(i).getFromdate() + ")");
                tvLabelDescription.setText(aWarningsBean.get(i).getDescription());

                llHeader.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        loadLinkedWarningDialog(GenericConstant.TYPE_WARNING, aWarningsBean);
                    }
                });


                llWarning.addView(view);
            }
        }
        if (aPhotosBean != null && aPhotosBean.size() > 0) {
            tvImageCount.setText(String.valueOf(aPhotosBean.size()));
        }
    }

    /**
     * Load Image list
     */
    private void loadImageData() {
        LinearLayout llImageContent = null;

        if (flowType == GenericConstant.TYPE_PERSON) {
            llImageContent = person_detail.findViewById(R.id.llImageContent);
        } else if (flowType == GenericConstant.TYPE_VEHICLE) {
            llImageContent = vehicle_detail.findViewById(R.id.llImageContent);
        }
        llImageContent.removeAllViews();
        if (aPhotosBean != null && aPhotosBean.size() > 0) {
            llImageContent.setVisibility(View.VISIBLE);

            for (int i = 0; i < aPhotosBean.size(); i++) {

                View view = layoutInflater.inflate(R.layout.person_athena_photo_item, null);
                ImageView ivPhoto = view.findViewById(R.id.ivPhoto);
                TextView tvDateValue = view.findViewById(R.id.tvDateValue);
                TextView tvPhotoCount = view.findViewById(R.id.tvPhotoCount);

                Bitmap bitmapPhoto = Base64ToBitmapUtil.base64ToBitmap(aPhotosBean.get(i).getPhotodata());
                ivPhoto.setImageBitmap(bitmapPhoto);
                tvDateValue.setText(aPhotosBean.get(i).getPhototakendate());
                tvPhotoCount.setText(String.valueOf(i + 1) + " / " + String.valueOf(aPhotosBean.size()));

                llImageContent.addView(view);
            }
        }
    }


    /**
     * Load the further info
     */
    private void loadPersonFurtherInfoData() {

        if (personModels != null) {
            TextView tvSurnameValue = person_detail.findViewById(R.id.tvSurnameValue);
            TextView tvForenameValue = person_detail.findViewById(R.id.tvForenameValue);
            TextView tvDOBValue = person_detail.findViewById(R.id.tvDOBValue);
            TextView tvCertificateValue = person_detail.findViewById(R.id.tvCertificateValue);
            TextView tvLatestHomeAddressValue = person_detail.findViewById(R.id.tvLatestHomeAddressValue);
            TextView tvPNCFileNameValue = person_detail.findViewById(R.id.tvPNCFileNameValue);
            TextView tvPNCIdValue = person_detail.findViewById(R.id.tvPNCIdValue);
            TextView tvGenderValue = person_detail.findViewById(R.id.tvGenderValue);
            TextView tvAliasDetailValue = person_detail.findViewById(R.id.tvAliasDetailValue);
            TextView tvEthinicValue = person_detail.findViewById(R.id.tvEthinicValue);
            TextView tvHeightValue = person_detail.findViewById(R.id.tvHeightValue);
            TextView tvBuildValue = person_detail.findViewById(R.id.tvBuildValue);


            tvSurnameValue.setText(personModels.get(0).getLastname());
            tvForenameValue.setText(personModels.get(0).getFirstname1());
            tvDOBValue.setText(personModels.get(0).getDob());
            tvCertificateValue.setText(personModels.get(0).getFirarmcertificateholder());
            tvLatestHomeAddressValue.setText(personModels.get(0).getLatesthomeaddress());
            tvPNCFileNameValue.setText(personModels.get(0).getPncFilename());
            tvPNCIdValue.setText(personModels.get(0).getPncId());
            tvGenderValue.setText(personModels.get(0).getGender());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvAliasDetailValue.setText(Html.fromHtml(personModels.get(0).getAliasdetails(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                tvAliasDetailValue.setText(Html.fromHtml(personModels.get(0).getAliasdetails()));
            }

            tvEthinicValue.setText(personModels.get(0).getEthnicity());
            tvHeightValue.setText(personModels.get(0).getHeight());
            tvBuildValue.setText(personModels.get(0).getBuild());

        }
    }


    /**
     * Load Address Data
     */
    private void loadAddressData() {

        addressModels = (ArrayList<AddressModel>) listDetails.get(0);
        List<WarningsModel> aWarningsBean = addressModels.get(0).getWarnings();

        rlFurtherInfo = address_detail.findViewById(R.id.rlFurtherInfo);
        llFurtherInfoDetails = address_detail.findViewById(R.id.llAdditionalInfoDetails);

        TextView tvPremisesNameValue = address_detail.findViewById(R.id.tvPremisesNameValue);
        TextView tvSubPremisesNameValue = address_detail.findViewById(R.id.tvSubPremisesNameValue);
        TextView tvPremisesNoValue = address_detail.findViewById(R.id.tvPremisesNoValue);
        TextView tvFlatNoValue = address_detail.findViewById(R.id.tvFlatNoValue);
        TextView tvSubNOValue = address_detail.findViewById(R.id.tvSubNOValue);
        TextView tvStreetNameValue = address_detail.findViewById(R.id.tvStreetNameValue);
        TextView tvLocalityValue = address_detail.findViewById(R.id.tvLocalityValue);
        TextView tvTownValue = address_detail.findViewById(R.id.tvTownValue);
        TextView tvCountyValue = address_detail.findViewById(R.id.tvCountyValue);
        TextView tvPostCodeValue = address_detail.findViewById(R.id.tvPostCodeValue);
        TextView tvPOBoxValue = address_detail.findViewById(R.id.tvPOBoxValue);
        TextView tvCountryValue = address_detail.findViewById(R.id.tvCountryValue);
        TextView tvForceValue = address_detail.findViewById(R.id.tvForceValue);
        TextView tvPremisesTypeValue = address_detail.findViewById(R.id.tvPremisesTypeValue);

        View viewWarning = address_detail.findViewById(R.id.viewWarning);
        LinearLayout llWarning = address_detail.findViewById(R.id.llWarning);

        rlFurtherInfo.setOnClickListener(this);

        if (addressModels != null) {
            tvPremisesNameValue.setText(addressModels.get(0).getPremisesname());
            tvSubPremisesNameValue.setText(addressModels.get(0).getSubpremisesname());
            tvPremisesNoValue.setText(addressModels.get(0).getPremisesnumber());
            tvFlatNoValue.setText(addressModels.get(0).getFlatno());
            tvSubNOValue.setText(addressModels.get(0).getSubno());
            tvStreetNameValue.setText(addressModels.get(0).getStreet());
            tvLocalityValue.setText(addressModels.get(0).getLocality());
            tvTownValue.setText(addressModels.get(0).getTown());
            tvCountyValue.setText(addressModels.get(0).getCounty());
            tvPostCodeValue.setText(addressModels.get(0).getPostcode());
            tvPOBoxValue.setText(addressModels.get(0).getPobox());
            tvCountryValue.setText(addressModels.get(0).getCountry());
            tvForceValue.setText(addressModels.get(0).getForce());
            tvPremisesTypeValue.setText(addressModels.get(0).getPremisestype());

            if (aWarningsBean != null && aWarningsBean.size() > 0) {

                viewWarning.setVisibility(View.VISIBLE);
                llWarning.setVisibility(View.VISIBLE);

                for (int i = 0; i < aWarningsBean.size(); i++) {

                    View view = layoutInflater.inflate(R.layout.vehicle_details_warning_items, null);

                    LinearLayout llHeader = view.findViewById(R.id.llHeader);
                    TextView tvLabel = view.findViewById(R.id.tvLabel);
                    TextView tvLabelValue = view.findViewById(R.id.tvLabelValue);
                    TextView tvLabelDate = view.findViewById(R.id.tvLabelDate);
                    TextView tvLabelDescription = view.findViewById(R.id.tvLabelDescription);

                    tvLabel.setText(aWarningsBean.get(i).getMarkertype());
                    tvLabelValue.setText(aWarningsBean.get(i).getMarkervalue());
                    tvLabelDate.setText("(from " + aWarningsBean.get(i).getFromdate() + ")");
                    tvLabelDescription.setText(aWarningsBean.get(i).getDescription());

                    llHeader.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });

                    llWarning.addView(view);
                }
            } else {
                viewWarning.setVisibility(View.GONE);
                llWarning.setVisibility(View.GONE);
            }
        }


    }


    /**
     * Load Investigation Data
     */
    private void loadInvestigationData() {

        investigationModels = (ArrayList<InvestigationModels>) listDetails.get(0);
        aEnquiryLogsModel = investigationModels.get(0).getEnquirylogs();

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


        if (investigationModels != null) {
            tvInvestigationNO.setText(getString(R.string.investigations) + " " + investigationModels.get(0).getInvestigationnumber());
            tvEndOnValue.setText(investigationModels.get(0).getEventdate());
            tvReportedValue.setText(investigationModels.get(0).getReporteddate());
            tvStatusValue.setText(investigationModels.get(0).getStatus());
            tvPrimaryOffenceValue.setText(investigationModels.get(0).getPrimaryoffence());
            tvOICValue.setText(investigationModels.get(0).getOic());
            tvOICUnitValue.setText(investigationModels.get(0).getOicUnit());
            tvInvestigationTypeValue.setText(investigationModels.get(0).getType());
            tvOutcomeValue.setText(investigationModels.get(0).getOutcome());
            tvCCNoValue.setText(investigationModels.get(0).getCcNumber());
            tvEventLocationValue.setText(investigationModels.get(0).getEventlocation());
            if (investigationModels.get(0).getSummary() != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvSummaryValue.setText(Html.fromHtml(investigationModels.get(0).getSummary(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    tvSummaryValue.setText(Html.fromHtml(investigationModels.get(0).getSummary()));
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
        if (investigationModels != null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvCategoryValue.setText(Html.fromHtml(investigationModels.get(0).getCategories(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                tvCategoryValue.setText(Html.fromHtml(investigationModels.get(0).getCategories()));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvKeywordsValue.setText(Html.fromHtml(investigationModels.get(0).getKeywords(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                tvKeywordsValue.setText(Html.fromHtml(investigationModels.get(0).getKeywords()));
            }
            tvReportingOfficerValue.setText(investigationModels.get(0).getReportingofficer());
            tvHOClassificationValue.setText("");
            tvVictimCrownValue.setText(investigationModels.get(0).getVictimiscrown());
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

        if (investigationModels != null) {
            tvPrimaryOffenceValue.setText(investigationModels.get(0).getSubgroup());
            tvCCCJSCodeValue.setText(investigationModels.get(0).getPrimaryoffence());
            tvACPOCodeValue.setText(investigationModels.get(0).getCccjsCode());
            tvSubGroupValue.setText(investigationModels.get(0).getAcpoCode());
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
     * Load the further info
     */
    private void loadVehicleAdditionalInfo() {

        if (searchdetailresponseBean != null) {

            TextView tvCharacteristicsValue = vehicle_detail.findViewById(R.id.tvCharacteristicsValue);
            TextView tvCountryOfRegistrationValue = vehicle_detail.findViewById(R.id.tvCountryOfRegistrationValue);
            TextView tvRemarksValue = vehicle_detail.findViewById(R.id.tvRemarksValue);
            TextView tvForeignVehicleValue = vehicle_detail.findViewById(R.id.tvForeignVehicleValue);
            TextView tvEngineNoValue = vehicle_detail.findViewById(R.id.tvEngineNoValue);
            TextView tvChassisNoValue = vehicle_detail.findViewById(R.id.tvChassisNoValue);

            tvCharacteristicsValue.setText(vehicleModels.get(0).getIdentifyingcharacteristics());
            tvCountryOfRegistrationValue.setText(vehicleModels.get(0).getRegistrationcountry());
            tvRemarksValue.setText(vehicleModels.get(0).getRemarks());
            tvForeignVehicleValue.setText(vehicleModels.get(0).getForeignvehicle());
            tvEngineNoValue.setText(vehicleModels.get(0).getEnginenumber());
            tvChassisNoValue.setText(vehicleModels.get(0).getChassisnumber());
        }
    }

    /**
     * Load the further info
     */
    private void loadAddressAdditionalInfo() {

        if (addressModels != null) {

            TextView tvBCUValue = address_detail.findViewById(R.id.tvBCUValue);
            TextView tvDistrictValue = address_detail.findViewById(R.id.tvDistrictValue);
            TextView tvWardValue = address_detail.findViewById(R.id.tvWardValue);
            TextView tvEastingValue = address_detail.findViewById(R.id.tvEastingValue);
            TextView tvNorthingValue = address_detail.findViewById(R.id.tvNorthingValue);

            tvBCUValue.setText(addressModels.get(0).getBcu());
            tvDistrictValue.setText(addressModels.get(0).getDistrict());
            tvWardValue.setText(addressModels.get(0).getWard());
            tvEastingValue.setText(addressModels.get(0).getEasting());
            tvNorthingValue.setText(addressModels.get(0).getNorthing());
        }
    }

    /**
     * Load Case Deta
     */
    private void loadCasesData() {

        casedetailModels = (ArrayList<CasedetailModels>) listDetails.get(0);

        RelativeLayout rlDefendants = case_detail.findViewById(R.id.rlDefendants);
        RelativeLayout rlHearings = case_detail.findViewById(R.id.rlHearings);
        RelativeLayout rlPersons = case_detail.findViewById(R.id.rlPersons);
        RelativeLayout rlFurtherInfo = case_detail.findViewById(R.id.rlFurtherInfo);

        llDefendantsContent = case_detail.findViewById(R.id.llDefendantsContent);
        llHearingsContent = case_detail.findViewById(R.id.llHearingsContent);
        llPersonsContent = case_detail.findViewById(R.id.llPersonsContent);
        llFurtherInfoDetails = case_detail.findViewById(R.id.llAdditionalInfoDetails);

        TextView tvReferenceValue = case_detail.findViewById(R.id.tvReferenceValue);
        TextView tvCreatedOnValue = case_detail.findViewById(R.id.tvCreatedOnValue);
        TextView tvFileSubTypeValue = case_detail.findViewById(R.id.tvFileSubTypeValue);
        TextView tvOICValue = case_detail.findViewById(R.id.tvOICValue);
        TextView tvStatusValue = case_detail.findViewById(R.id.tvStatusValue);
        TextView tvCPSBranchValue = case_detail.findViewById(R.id.tvCPSBranchValue);
        TextView tvCPSBranchCaseValue = case_detail.findViewById(R.id.tvCPSBranchCaseValue);
        TextView tvCaseWorkerValue = case_detail.findViewById(R.id.tvCaseWorkerValue);
        TextView tvCareOfficerValue = case_detail.findViewById(R.id.tvCareOfficerValue);

        if (casedetailModels != null) {
            tvReferenceValue.setText(casedetailModels.get(0).getCasereference());
            tvCreatedOnValue.setText(casedetailModels.get(0).getCreatedon());
            tvFileSubTypeValue.setText(casedetailModels.get(0).getFilesubtype());
            tvOICValue.setText(casedetailModels.get(0).getOic());
            tvStatusValue.setText(casedetailModels.get(0).getStatus());
            tvCPSBranchValue.setText(casedetailModels.get(0).getCpsbranch());
            tvCPSBranchCaseValue.setText(casedetailModels.get(0).getCpscasestatus());
            tvCaseWorkerValue.setText(casedetailModels.get(0).getCaseworker());
            tvCareOfficerValue.setText(casedetailModels.get(0).getWitnesscareofficer());
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

        if (casedetailModels != null) {

            TextView tvName = case_detail.findViewById(R.id.tvName);
            TextView tvDOB = case_detail.findViewById(R.id.tvDOB);
            TextView tvNameValue = case_detail.findViewById(R.id.tvNameValue);
            TextView tvDOBValue = case_detail.findViewById(R.id.tvDOBValue);

            TextView tvOffenceNDate = case_detail.findViewById(R.id.tvOffenceNDate);
            View viewGender = case_detail.findViewById(R.id.viewGender);
            View viewType = case_detail.findViewById(R.id.viewType);
            View viewOffenceNDate = case_detail.findViewById(R.id.viewOffenceNDate);
            LinearLayout llGender = case_detail.findViewById(R.id.llGender);
            LinearLayout llType = case_detail.findViewById(R.id.llType);
            TextView tvOffenceNDateValue = case_detail.findViewById(R.id.tvOffenceNDateValue);

            viewGender.setVisibility(View.GONE);
            viewType.setVisibility(View.GONE);
            viewOffenceNDate.setVisibility(View.GONE);
            llGender.setVisibility(View.GONE);
            llType.setVisibility(View.GONE);
            tvOffenceNDate.setVisibility(View.GONE);
            tvOffenceNDateValue.setVisibility(View.GONE);

            tvName.setText(getString(R.string.case_document_status));
            tvDOB.setText(getString(R.string.cju_unit_code));

            tvNameValue.setText(casedetailModels.get(0).getDocumentstatus());
            tvDOBValue.setText(casedetailModels.get(0).getUnitcode());
        }
    }

    /**
     * load Defendants Data
     */
    private void loadCasesDefendantData() {

        List<DefendantsModel> aDefendantsModel = casedetailModels.get(0).getDefendants();
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
        List<HearingsModel> aHearingsModel = casedetailModels.get(0).getHearings();
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
        List<PersonModel> aPersonModel = casedetailModels.get(0).getPersons();
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
     * Load Communication Data
     */
    private void loadCommunicationData() {

        communicationModels = (ArrayList<CommunicationModel>) listDetails.get(0);
        ArrayList<WarningsModel> aWarningsBean = (ArrayList<WarningsModel>) communicationModels.get(0).getWarnings();

        RelativeLayout rlCommunicationDetails = communication_detail.findViewById(R.id.rlCommunicationDetails);
        llCommunicationDetails = communication_detail.findViewById(R.id.llCommunicationDetails);

        TextView tvTypeValue = communication_detail.findViewById(R.id.tvTypeValue);
        TextView tvDetailsValue = communication_detail.findViewById(R.id.tvDetailsValue);
        TextView tvStatusValue = communication_detail.findViewById(R.id.tvStatusValue);
        TextView tvRemarksValue = communication_detail.findViewById(R.id.tvRemarksValue);
        TextView tvCreatedDateValue = communication_detail.findViewById(R.id.tvCreatedDateValue);
        View viewWarning = communication_detail.findViewById(R.id.viewWarning);
        LinearLayout llWarning = communication_detail.findViewById(R.id.llWarning);
        TextView tvMarker = communication_detail.findViewById(R.id.tvMarker);


        if (communicationModels != null) {
            tvTypeValue.setText(communicationModels.get(0).getType());
            tvDetailsValue.setText(communicationModels.get(0).getDetails());
            tvStatusValue.setText(communicationModels.get(0).getStatus());
            tvRemarksValue.setText(communicationModels.get(0).getRemarks());
            tvCreatedDateValue.setText(communicationModels.get(0).getCreateddate());

            if (aWarningsBean != null && aWarningsBean.size() > 0) {

                viewWarning.setVisibility(View.VISIBLE);
                llWarning.setVisibility(View.VISIBLE);
                tvMarker.setVisibility(View.VISIBLE);
                llWarning.removeAllViews();
                for (int i = 0; i < aWarningsBean.size(); i++) {

                    View view = layoutInflater.inflate(R.layout.vehicle_details_warning_items, null);

                    LinearLayout llHeader = view.findViewById(R.id.llHeader);
                    TextView tvLabel = view.findViewById(R.id.tvLabel);
                    TextView tvLabelValue = view.findViewById(R.id.tvLabelValue);
                    TextView tvLabelDate = view.findViewById(R.id.tvLabelDate);
                    TextView tvLabelDescription = view.findViewById(R.id.tvLabelDescription);

                    tvLabel.setText(aWarningsBean.get(i).getMarkertype());
                    tvLabelValue.setText(aWarningsBean.get(i).getMarkervalue());
                    tvLabelDate.setText("(from " + aWarningsBean.get(i).getFromdate() + ")");
                    tvLabelDescription.setText(aWarningsBean.get(i).getDescription());

                    llHeader.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            loadLinkedWarningDialog(GenericConstant.TYPE_WARNING, aWarningsBean);
                        }
                    });

                    llWarning.addView(view);
                }
            } else {
                viewWarning.setVisibility(View.GONE);
                llWarning.setVisibility(View.GONE);
            }
        }
        rlCommunicationDetails.setOnClickListener(this);
    }

    /**
     * Load Intelligence Dialog
     */
    public void loadLinkedWarningDialog(int type, ArrayList<WarningsModel> aWarningsBean) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.LINKED_WARNING_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        LinkedWarningListDialogFragment linkedLocationDetailsDialogFragment = LinkedWarningListDialogFragment.newInstance(type, aWarningsBean, isPopulate);
        linkedLocationDetailsDialogFragment.show(ft, GenericConstant.LINKED_WARNING_DIALOG);
    }

    /**
     * Load Case Deta
     */
    private void loadCourtWarrantData() {

        courtWarrantModels = (ArrayList<CourtWarrantModel>) listDetails.get(0);
        aEnquiryLogsModel = courtWarrantModels.get(0).getLogentries();
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

        if (courtWarrantModels != null) {
            tvReferenceValue.setText(courtWarrantModels.get(0).getWarrantreference());
            tvShortSummaryValue.setText(courtWarrantModels.get(0).getShortsummary());
            tvCategoryValue.setText(courtWarrantModels.get(0).getCategory());
            tvStatusValue.setText(courtWarrantModels.get(0).getStatus());
            tvTypeValue.setText(courtWarrantModels.get(0).getType());
            tvIssueDateValue.setText(courtWarrantModels.get(0).getIssuedate());
            tvDueDateValue.setText(courtWarrantModels.get(0).getDuedate());
        }

        if (aEnquiryLogsModel != null && aEnquiryLogsModel.size() > 0) {
            tvEnquiryLogCount.setText(String.valueOf(aEnquiryLogsModel.size()));
        }
        rlCourtWarrantDetails.setOnClickListener(this);
        rlFurtherInfo.setOnClickListener(this);
        rlEnquiryLog.setOnClickListener(this);
    }

    /**
     * Load the further info
     */
    private void loadWarrantAdditionalInfoData() {

        if (courtWarrantModels != null) {

            TextView tvWarrantNoValue = court_warrent_detail.findViewById(R.id.tvWarrantNoValue);
            TextView tvForceValue = court_warrent_detail.findViewById(R.id.tvForceValue);
            TextView tvBCUValue = court_warrent_detail.findViewById(R.id.tvBCUValue);
            TextView tvDistrictValue = court_warrent_detail.findViewById(R.id.tvDistrictValue);
            TextView tvWardValue = court_warrent_detail.findViewById(R.id.tvWardValue);
            TextView tvCCCJSValue = court_warrent_detail.findViewById(R.id.tvCCCJSValue);
            TextView tvWarrantIssuedValue = court_warrent_detail.findViewById(R.id.tvWarrantIssuedValue);
            TextView tvWarrantDueValue = court_warrent_detail.findViewById(R.id.tvWarrantDueValue);
            TextView tvSubjectBailedValue = court_warrent_detail.findViewById(R.id.tvSubjectBailedValue);

            tvWarrantNoValue.setText(courtWarrantModels.get(0).getWarrantreference());
            tvForceValue.setText(courtWarrantModels.get(0).getForce());
            tvBCUValue.setText(courtWarrantModels.get(0).getBcu());
            tvDistrictValue.setText(courtWarrantModels.get(0).getDistrict());
            tvWardValue.setText(courtWarrantModels.get(0).getWard());
            tvCCCJSValue.setText(courtWarrantModels.get(0).getCccjs());
            tvWarrantIssuedValue.setText(courtWarrantModels.get(0).getIssuedate());
            tvWarrantDueValue.setText(courtWarrantModels.get(0).getDuedate());
            tvSubjectBailedValue.setText(courtWarrantModels.get(0).getSubjectbailed());
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
    public void loadPersonJSONDetails(String fileName) {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);
                PersonAthenaDetailResponse athenaResponse = (PersonAthenaDetailResponse) JsonUtil.getInstance().toModel(strJson, PersonAthenaDetailResponse.class);
                if (athenaResponse != null) {
                    searchdetailresponseBean = athenaResponse.getSearchdetailresponse();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (searchdetailresponseBean != null) {
                        loadDetails();
                        loadCommonView();
                        loadPersonData();
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
     * Load Vehicle Details
     *
     * @param fileName
     */
    public void loadVehicleJSONDetails(String fileName) {
        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        searchdetailresponseBean = null;
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);
                VehicleAthenaDetailResponse athenaResponse = (VehicleAthenaDetailResponse) JsonUtil.getInstance().toModel(strJson, VehicleAthenaDetailResponse.class);
                if (athenaResponse != null) {
                    searchdetailresponseBean = athenaResponse.getSearchdetailresponse();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (searchdetailresponseBean != null) {
                        loadDetails();
                        loadCommonView();
                        loadVehicleData();
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), AthenaListDialogFragment.class, "loadVehicleJSONDetails");
            }
        }).start();
    }


    /**
     * Load Address Details
     *
     * @param fileName
     */
    public void loadAddressJSONDetails(String fileName) {
        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        searchdetailresponseBean = null;
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);
                AddressAthenaDetailResponse athenaResponse = (AddressAthenaDetailResponse) JsonUtil.getInstance().toModel(strJson, AddressAthenaDetailResponse.class);
                if (athenaResponse != null) {
                    searchdetailresponseBean = athenaResponse.getSearchdetailresponse();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (searchdetailresponseBean != null) {
                        loadDetails();
                        loadCommonView();
                        loadAddressData();
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), AthenaListDialogFragment.class, "loadAddressJSONDetails");
            }
        }).start();
    }

    /**
     * Load Investigation Details
     *
     * @param fileName
     */
    public void loadInvestigationJSONDetails(String fileName) {
        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        searchdetailresponseBean = null;
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);
                InvestigationAthenaDetailResponse athenaResponse = (InvestigationAthenaDetailResponse) JsonUtil.getInstance().toModel(strJson, InvestigationAthenaDetailResponse.class);
                if (athenaResponse != null) {
                    searchdetailresponseBean = athenaResponse.getSearchdetailresponse();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (searchdetailresponseBean != null) {
                        loadDetails();
                        loadCommonView();
                        loadInvestigationData();
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), AthenaListDialogFragment.class, "loadInvestigationJSONDetails");
            }
        }).start();
    }


    /**
     * Load Cases Details
     *
     * @param fileName
     */
    public void loadCasesJSONDetails(String fileName) {
        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        searchdetailresponseBean = null;
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);
                CasesAthenaDetailResponse athenaResponse = (CasesAthenaDetailResponse) JsonUtil.getInstance().toModel(strJson, CasesAthenaDetailResponse.class);
                if (athenaResponse != null) {
                    searchdetailresponseBean = athenaResponse.getSearchdetailresponse();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (searchdetailresponseBean != null) {
                        loadDetails();
                        loadCommonView();
                        loadCasesData();
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), AthenaListDialogFragment.class, "loadCasesJSONDetails");
            }
        }).start();
    }


    /**
     * Load Communication Details
     *
     * @param fileName
     */
    public void loadCommunicationJSONDetails(String fileName) {
        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        searchdetailresponseBean = null;
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);
                CommunicationAthenaDetailResponse athenaResponse = (CommunicationAthenaDetailResponse) JsonUtil.getInstance().toModel(strJson, CommunicationAthenaDetailResponse.class);
                if (athenaResponse != null) {
                    searchdetailresponseBean = athenaResponse.getSearchdetailresponse();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (searchdetailresponseBean != null) {
                        loadDetails();
                        loadCommonView();
                        loadCommunicationData();
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), AthenaListDialogFragment.class, "loadCommunicationJSONDetails");
            }
        }).start();
    }

    /**
     * Load CourtWarrant Details
     *
     * @param fileName
     */
    public void loadCourtWarrantJSONDetails(String fileName) {
        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        searchdetailresponseBean = null;
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);
                CourtWarrantAthenaDetailResponse athenaResponse = (CourtWarrantAthenaDetailResponse) JsonUtil.getInstance().toModel(strJson, CourtWarrantAthenaDetailResponse.class);
                if (athenaResponse != null) {
                    searchdetailresponseBean = athenaResponse.getSearchdetailresponse();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (searchdetailresponseBean != null) {
                        loadDetails();
                        loadCommonView();
                        loadCourtWarrantData();
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), AthenaListDialogFragment.class, "loadCourtWarrantJSONDetails");
            }
        }).start();
    }


    /**
     * Load ObjectIteration JSON
     */
    public void loadObjectIterationJSONDetails(String fileName) {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);
                ObjectIterationAthenaDetailResponse athenaResponse = (ObjectIterationAthenaDetailResponse) JsonUtil.getInstance().toModel(strJson, ObjectIterationAthenaDetailResponse.class);
                if (athenaResponse != null) {
                    searchdetailresponseBean = athenaResponse.getSearchdetailresponse();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (searchdetailresponseBean != null) {

                        if (!TextUtils.isEmpty(searchdetailresponseBean.getObjectiterationcount())) {
                            tvObjectIterationCount.setText(searchdetailresponseBean.getObjectiterationcount());
                            loadDetails();
                            SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_ITEM, getString(R.string.object_iteration));
                            loadPersonATHENAItemsDetailsDialog(GenericConstant.ATHENA_OBJECT_ITERATION_LIST);
                        }
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
     * Load Person Details
     */
    private void loadDetails() {
        listDetails = new ArrayList<>();
        listDetails.clear();

        List<PersonModel> aPerson = new ArrayList<>();
        aPerson.clear();
        List<VehicleModel> aVehicle = new ArrayList<>();
        aVehicle.clear();
        List<AddressModel> aAddress = new ArrayList<>();
        aAddress.clear();
        List<InvestigationModels> aInvestigationModels = new ArrayList<>();
        aInvestigationModels.clear();
        List<CasedetailModels> aCasedetailModels = new ArrayList<>();
        aCasedetailModels.clear();
        List<CommunicationModel> aCommunicationModel = new ArrayList<>();
        aCommunicationModel.clear();
        List<CourtWarrantModel> aCourtWarrantModel = new ArrayList<>();
        aCourtWarrantModel.clear();

        loadLinkedHeader(searchdetailresponseBean);

        if (flowType == GenericConstant.TYPE_PERSON) {

            aPerson.add(searchdetailresponseBean.getPerson());
            listDetails.add(aPerson);


        } else if (flowType == GenericConstant.TYPE_VEHICLE) {

            aVehicle.add(searchdetailresponseBean.getVehicle());
            listDetails.add(aVehicle);

        } else if (flowType == GenericConstant.TYPE_ADDRESS) {
            aAddress.add(searchdetailresponseBean.getAddress());
            listDetails.add(aAddress);

        } else if (flowType == GenericConstant.TYPE_INVESTIGATION) {

            aInvestigationModels.add(searchdetailresponseBean.getInvestigation());
            listDetails.add(aInvestigationModels);

        } else if (flowType == GenericConstant.TYPE_CASES) {
            aCasedetailModels.add(searchdetailresponseBean.getCasedetails());
            listDetails.add(aCasedetailModels);

        } else if (flowType == GenericConstant.TYPE_COMMUNICATION) {

            aCommunicationModel.add(searchdetailresponseBean.getCommunication());
            listDetails.add(aCommunicationModel);

        } else if (flowType == GenericConstant.TYPE_COURT_WARRENT) {

            aCourtWarrantModel.add(searchdetailresponseBean.getCourtwarrant());
            listDetails.add(aCourtWarrantModel);

        }
        listDetails.add(searchdetailresponseBean.getLinkedperson());
        listDetails.add(searchdetailresponseBean.getLinkedvehicle());
        listDetails.add(searchdetailresponseBean.getLinkedinvestigation());
        listDetails.add(searchdetailresponseBean.getLinkedintelligence());
        listDetails.add(searchdetailresponseBean.getLinkedcourtwarrent());
        listDetails.add(searchdetailresponseBean.getLinkedcases());
        listDetails.add(searchdetailresponseBean.getLinkedlocation());
        listDetails.add(searchdetailresponseBean.getLinkedcustody());
        listDetails.add(searchdetailresponseBean.getLinkedcommunication());
        listDetails.add(searchdetailresponseBean.getLinkedarrest());
        listDetails.add(searchdetailresponseBean.getLinkedbail());
        listDetails.add(searchdetailresponseBean.getLinkedbailrefusal());
        listDetails.add(searchdetailresponseBean.getLinkeddocument());
        listDetails.add(searchdetailresponseBean.getLinkedoffence());
        listDetails.add(searchdetailresponseBean.getLinkedpersonsearched());
        listDetails.add(searchdetailresponseBean.getLinkedriskassessment());
        listDetails.add(searchdetailresponseBean.getLinkedsample());
        listDetails.add(searchdetailresponseBean.getLinkedbriefing());
        listDetails.add(searchdetailresponseBean.getLinkedorganisation());
        listDetails.add(searchdetailresponseBean.getLinkedsearches());
        listDetails.add(searchdetailresponseBean.getLinkedpropertyitems());
        listDetails.add(searchdetailresponseBean.getLinkedcategory());
        listDetails.add(searchdetailresponseBean.getLinkedobjectiteration());
    }

    /**
     * Load Linked Item Header
     *
     * @param mSearchdetailresponseBean
     */
    private void loadLinkedHeader(SearchdetailresponseBean mSearchdetailresponseBean) {

        if (flowType == GenericConstant.TYPE_PERSON) {
            PersonModel personModel = mSearchdetailresponseBean.getPerson();
            if (personModel != null) {
                sbLinkedHeader.append(personModel.getFirstname1()).append(" ").append(personModel.getLastname()).append(",").append(personModel.getDob());
            }
        } else if (flowType == GenericConstant.TYPE_VEHICLE) {

            VehicleModel vehicleModel = mSearchdetailresponseBean.getVehicle();
            if (vehicleModel != null) {
                sbLinkedHeader.append(vehicleModel.getRegistrationnumber()).append(",").append(vehicleModel.getMake()).append(" ").append(vehicleModel.getModel()).append(",").append(vehicleModel.getVehiclecolour());
            }

        } else if (flowType == GenericConstant.TYPE_ADDRESS) {
            AddressModel addressModel = mSearchdetailresponseBean.getAddress();
            if (addressModel != null) {
                sbLinkedHeader.append(addressModel.getPremisesnumber()).append(",").append(addressModel.getForce()).append(",").append(addressModel.getCounty());
            }

        } else if (flowType == GenericConstant.TYPE_INVESTIGATION) {

            InvestigationModels investigationModel = mSearchdetailresponseBean.getInvestigation();
            if (investigationModel != null) {
                sbLinkedHeader.append(investigationModel.getInvestigationnumber()).append(",").append(investigationModel.getEventdate()).append(",").append(investigationModel.getSummary());
            }
        } else if (flowType == GenericConstant.TYPE_CASES) {

            CasedetailModels casedetailModel = mSearchdetailresponseBean.getCasedetails();
            if (casedetailModel != null) {
                sbLinkedHeader.append(casedetailModel.getCasereference()).append(",").append(casedetailModel.getStatus());
            }


        } else if (flowType == GenericConstant.TYPE_COMMUNICATION) {

            CommunicationModel communicationModel = mSearchdetailresponseBean.getCommunication();
            if (communicationModel != null) {
                sbLinkedHeader.append(communicationModel.getType()).append(",").append(communicationModel.getDetails());
            }

        } else if (flowType == GenericConstant.TYPE_COURT_WARRENT) {
            CourtWarrantModel courtWarrantModel = mSearchdetailresponseBean.getCourtwarrant();
            if (courtWarrantModel != null) {
                sbLinkedHeader.append(courtWarrantModel.getWarrantreference()).append(",").append(courtWarrantModel.getStatus()).append(",").append(courtWarrantModel.getType());
            }
        }
        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.LINKED_HEADER, sbLinkedHeader.toString());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ivBack:
                DialogUtil.cancelProgressDialog(mProgressDialog);
                dismiss();

                break;

            case R.id.rlInvestigationDetails:

                if (llInvestigationDetails.getVisibility() != View.VISIBLE) {
                    llInvestigationDetails.setVisibility(View.VISIBLE);
                    loadInvestigationData();
                } else {
                    llInvestigationDetails.setVisibility(View.GONE);
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

                    if (flowType == GenericConstant.TYPE_INVESTIGATION) {
                        loadEnquiryLogData();

                    } else if (flowType == GenericConstant.TYPE_COURT_WARRENT) {
                        loadWarrantEnquiryLogData();
                    }
                } else {
                    llEnquiryLogContent.setVisibility(View.GONE);
                }

                break;

            case R.id.btnAddEnquiryLog:
                loadEntryDialog();
                break;

            case R.id.rlImage:

                if (hScrollViewImage.getVisibility() != View.VISIBLE) {
                    hScrollViewImage.setVisibility(View.VISIBLE);
                    loadImageData();
                } else {
                    hScrollViewImage.setVisibility(View.GONE);
                }

                break;

            case R.id.rlFurtherInfo:

                if (llFurtherInfoDetails.getVisibility() != View.VISIBLE) {
                    llFurtherInfoDetails.setVisibility(View.VISIBLE);

                    if (flowType == GenericConstant.TYPE_PERSON) {

                        loadPersonFurtherInfoData();

                    } else if (flowType == GenericConstant.TYPE_VEHICLE) {
                        loadVehicleAdditionalInfo();

                    } else if (flowType == GenericConstant.TYPE_ADDRESS) {
                        loadAddressAdditionalInfo();

                    } else if (flowType == GenericConstant.TYPE_INVESTIGATION) {
                        loadInvestigationAdditionalInfoData();

                    } else if (flowType == GenericConstant.TYPE_CASES) {
                        loadCasesAdditionalInfoData();

                    } else if (flowType == GenericConstant.TYPE_COURT_WARRENT) {
                        loadWarrantAdditionalInfoData();
                    }

                } else {
                    llFurtherInfoDetails.setVisibility(View.GONE);
                }

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
            case R.id.rlCommunicationDetails:

                if (llCommunicationDetails.getVisibility() != View.VISIBLE) {
                    llCommunicationDetails.setVisibility(View.VISIBLE);
                    loadCommunicationData();
                } else {
                    llCommunicationDetails.setVisibility(View.GONE);
                }
                break;

            case R.id.rlCourtWarrantDetails:

                if (llCourtWarrantDetails.getVisibility() != View.VISIBLE) {
                    llCourtWarrantDetails.setVisibility(View.VISIBLE);
                    loadCourtWarrantData();
                } else {
                    llCourtWarrantDetails.setVisibility(View.GONE);
                }
                break;


            case R.id.rlPersonList:

                if (!TextUtils.isEmpty(searchdetailresponseBean.getLinkedpersoncount())) {
                    SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_ITEM, GenericConstant.SYSTEM_ITEM_PERSON);
                    loadPersonATHENAItemsDetailsDialog(GenericConstant.ATHENA_PERSON_LIST);
                }

                break;

            case R.id.rlVehicle:

                if (!TextUtils.isEmpty(searchdetailresponseBean.getVehiclecount())) {

                    SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_ITEM, GenericConstant.SYSTEM_ITEM_VEHICLE);
                    loadPersonATHENAItemsDetailsDialog(GenericConstant.ATHENA_VEHICLE_LIST);
                }


                break;

            case R.id.rlInvestigation:

                if (!TextUtils.isEmpty(searchdetailresponseBean.getInvestigationcount())) {

                    SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_ITEM, getString(R.string.investigations));
                    loadPersonATHENAItemsDetailsDialog(GenericConstant.ATHENA_INVESTIGATION_LIST);
                }


                break;

            case R.id.rlIntelligence:

                if (!TextUtils.isEmpty(searchdetailresponseBean.getIntelligencecount())) {

                    SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_ITEM, getString(R.string.intelligence_info));
                    loadPersonATHENAItemsDetailsDialog(GenericConstant.ATHENA_INTELLIGENCE_LIST);

                }

                break;

            case R.id.rlCourtWarrant:

                if (!TextUtils.isEmpty(searchdetailresponseBean.getCourtwarrentcount())) {
                    SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_ITEM, getString(R.string.court_warrants));
                    loadPersonATHENAItemsDetailsDialog(GenericConstant.ATHENA_COURT_WARRANT_LIST);
                }

                break;

            case R.id.rlCases:

                if (!TextUtils.isEmpty(searchdetailresponseBean.getCasecount())) {
                    SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_ITEM, getString(R.string.cases));
                    loadPersonATHENAItemsDetailsDialog(GenericConstant.ATHENA_CASES_LIST);

                }

                break;

            case R.id.rlLocation:

                if (!TextUtils.isEmpty(searchdetailresponseBean.getLocationcount())) {
                    SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_ITEM, getString(R.string.location));
                    loadPersonATHENAItemsDetailsDialog(GenericConstant.ATHENA_LOCATION_LIST);

                }

                break;

            case R.id.rlCustody:

                if (!TextUtils.isEmpty(searchdetailresponseBean.getCustodycount())) {
                    SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_ITEM, getString(R.string.custody_records));
                    loadPersonATHENAItemsDetailsDialog(GenericConstant.ATHENA_CUSTODY_LIST);

                }

                break;

            case R.id.rlCommunication:

                if (!TextUtils.isEmpty(searchdetailresponseBean.getCommunicationcount())) {
                    SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_ITEM, getString(R.string.communication));
                    loadPersonATHENAItemsDetailsDialog(GenericConstant.ATHENA_COMMUNICATION_LIST);

                }

                break;

            case R.id.rlOperations:


                break;

            case R.id.rlAddionalLink:

                if (llAddionalLinkContent.getVisibility() != View.VISIBLE) {
                    llAddionalLinkContent.setVisibility(View.VISIBLE);
                    loadAdditionalLinkData();
                } else {
                    llAddionalLinkContent.setVisibility(View.GONE);
                }
                break;

            case R.id.rlObjectIteration:

                loadObjectIterationJSONDetails(GenericConstant.OBJECT_ITERATION_ATHENA_DETAILS_JSON);

                break;

            case R.id.rlArrest:

                if (!TextUtils.isEmpty(searchdetailresponseBean.getArrestcount())) {
                    SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_ITEM, GenericConstant.SYSTEM_ITEM_ARREST);
                    loadPersonATHENAItemsDetailsDialog(GenericConstant.ATHENA_ARREST_LIST);
                }
                break;

            case R.id.rlBails:

                if (!TextUtils.isEmpty(searchdetailresponseBean.getBailcount())) {

                    SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_ITEM, getString(R.string.bails));
                    loadPersonATHENAItemsDetailsDialog(GenericConstant.ATHENA_BAIL_LIST);

                }
                break;
            case R.id.rlBailRefusal:
                if (!TextUtils.isEmpty(searchdetailresponseBean.getBailrefusalcount())) {

                    SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_ITEM, getString(R.string.bail_refusal));
                    loadPersonATHENAItemsDetailsDialog(GenericConstant.ATHENA_BAIL_REFUSAL_LIST);

                }

                break;
            case R.id.rlDocuments:

                if (!TextUtils.isEmpty(searchdetailresponseBean.getDocumentcount())) {

                    SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_ITEM, getString(R.string.documents));
                    loadPersonATHENAItemsDetailsDialog(GenericConstant.ATHENA_DOCUMENT_LIST);

                }

                break;
            case R.id.rlOffences:
                if (!TextUtils.isEmpty(searchdetailresponseBean.getOffencecount())) {

                    SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_ITEM, getString(R.string.offence));
                    loadPersonATHENAItemsDetailsDialog(GenericConstant.ATHENA_OFFENCE_LIST);

                }
                break;


            case R.id.rlOrganization:
                if (!TextUtils.isEmpty(searchdetailresponseBean.getOrganisationcount())) {

                    SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_ITEM, getString(R.string.organization));
                    loadPersonATHENAItemsDetailsDialog(GenericConstant.ATHENA_ORGANIZATION_LIST);

                }
                break;

            case R.id.rlSearch:
                if (!TextUtils.isEmpty(searchdetailresponseBean.getSearchcount())) {

                    SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_ITEM, getString(R.string.search));
                    loadPersonATHENAItemsDetailsDialog(GenericConstant.ATHENA_SEARCH_LIST);

                }
                break;

            case R.id.rlPersonSearches:

                if (!TextUtils.isEmpty(searchdetailresponseBean.getPerssonserachcount())) {

                    SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_ITEM, getString(R.string.person_searches));
                    loadPersonATHENAItemsDetailsDialog(GenericConstant.ATHENA_PERSON_SEARCH_LIST);

                }

                break;

            case R.id.rlPropertyItems:

                if (!TextUtils.isEmpty(searchdetailresponseBean.getPropertyitemscount())) {

                    SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_ITEM, getString(R.string.property_items));
                    loadPersonATHENAItemsDetailsDialog(GenericConstant.ATHENA_PROPERTY_ITEMS_LIST);

                }

                break;

            case R.id.rlRiskAssesments:
                if (!TextUtils.isEmpty(searchdetailresponseBean.getRiskassessmentcount())) {

                    SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_ITEM, getString(R.string.risk_assesments));
                    loadPersonATHENAItemsDetailsDialog(GenericConstant.ATHENA_RISK_ASSESMENT_LIST);

                }

                break;

            case R.id.rlSampleTaken:
                if (!TextUtils.isEmpty(searchdetailresponseBean.getSamplestakencount())) {
                    SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_ITEM, getString(R.string.sample_taken));
                    loadPersonATHENAItemsDetailsDialog(GenericConstant.ATHENA_SAMPLE_LIST);
                }

                break;

            case R.id.rlBriefingItems:
                if (!TextUtils.isEmpty(searchdetailresponseBean.getBriefingcount())) {

                    SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_ITEM, getString(R.string.briefing_items));
                    loadPersonATHENAItemsDetailsDialog(GenericConstant.ATHENA_BRIEFING_LIST);

                }
                break;
            case R.id.rlCategory:
                if (!TextUtils.isEmpty(searchdetailresponseBean.getCategorycount())) {

                    SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_ITEM, getString(R.string.categories));
                    loadPersonATHENAItemsDetailsDialog(GenericConstant.ATHENA_CATEGORY_LIST);

                }
                break;
        }
    }


    /**
     * Load Addtional Link Data
     */
    private void loadAdditionalLinkData() {

        RelativeLayout rlArrest = llCommonDetails.findViewById(R.id.rlArrest);
        RelativeLayout rlBails = llCommonDetails.findViewById(R.id.rlBails);
        RelativeLayout rlBailRefusal = llCommonDetails.findViewById(R.id.rlBailRefusal);
        RelativeLayout rlDocuments = llCommonDetails.findViewById(R.id.rlDocuments);
        RelativeLayout rlOffences = llCommonDetails.findViewById(R.id.rlOffences);
        RelativeLayout rlPersonSearches = llCommonDetails.findViewById(R.id.rlPersonSearches);
        RelativeLayout rlRiskAssesments = llCommonDetails.findViewById(R.id.rlRiskAssesments);
        RelativeLayout rlSampleTaken = llCommonDetails.findViewById(R.id.rlSampleTaken);
        RelativeLayout rlBriefingItems = llCommonDetails.findViewById(R.id.rlBriefingItems);
        RelativeLayout rlOrganization = llCommonDetails.findViewById(R.id.rlOrganization);
        RelativeLayout rlSearch = llCommonDetails.findViewById(R.id.rlSearch);
        RelativeLayout rlPropertyItems = llCommonDetails.findViewById(R.id.rlPropertyItems);
        RelativeLayout rlCategory = llCommonDetails.findViewById(R.id.rlCategory);


        TextView tvArrestCount = llCommonDetails.findViewById(R.id.tvArrestCount);
        TextView tvBailsCount = llCommonDetails.findViewById(R.id.tvBailsCount);
        TextView tvBailRefusalCount = llCommonDetails.findViewById(R.id.tvBailRefusalCount);
        TextView tvDocumentsCount = llCommonDetails.findViewById(R.id.tvDocumentsCount);
        TextView tvOffencesCount = llCommonDetails.findViewById(R.id.tvOffencesCount);
        TextView tvPersonSearchesCount = llCommonDetails.findViewById(R.id.tvPersonSearchesCount);
        TextView tvRiskAssesmentsCount = llCommonDetails.findViewById(R.id.tvRiskAssesmentsCount);
        TextView tvSampleTakenCount = llCommonDetails.findViewById(R.id.tvSampleTakenCount);
        TextView tvBriefingItemsCount = llCommonDetails.findViewById(R.id.tvBriefingItemsCount);
        TextView tvOrganizationCount = llCommonDetails.findViewById(R.id.tvOrganizationCount);
        TextView tvSearchCount = llCommonDetails.findViewById(R.id.tvSearchCount);
        TextView tvPropertyItemsCount = llCommonDetails.findViewById(R.id.tvPropertyItemsCount);
        TextView tvCategoryCount = llCommonDetails.findViewById(R.id.tvCategoryCount);


        if (!TextUtils.isEmpty(searchdetailresponseBean.getArrestcount())) {

            tvArrestCount.setText(searchdetailresponseBean.getArrestcount());
        }
        if (!TextUtils.isEmpty(searchdetailresponseBean.getBailcount())) {

            tvBailsCount.setText(searchdetailresponseBean.getBailcount());
        }
        if (!TextUtils.isEmpty(searchdetailresponseBean.getBailrefusalcount())) {

            tvBailRefusalCount.setText(searchdetailresponseBean.getBailrefusalcount());
        }
        if (!TextUtils.isEmpty(searchdetailresponseBean.getDocumentcount())) {

            tvDocumentsCount.setText(searchdetailresponseBean.getDocumentcount());
        }
        if (!TextUtils.isEmpty(searchdetailresponseBean.getOffencecount())) {

            tvOffencesCount.setText(searchdetailresponseBean.getOffencecount());
        }
        if (!TextUtils.isEmpty(searchdetailresponseBean.getPerssonserachcount())) {

            tvPersonSearchesCount.setText(searchdetailresponseBean.getPerssonserachcount());
        }
        if (!TextUtils.isEmpty(searchdetailresponseBean.getRiskassessmentcount())) {

            tvRiskAssesmentsCount.setText(searchdetailresponseBean.getRiskassessmentcount());
        }
        if (!TextUtils.isEmpty(searchdetailresponseBean.getSamplestakencount())) {

            tvSampleTakenCount.setText(searchdetailresponseBean.getSamplestakencount());
        }
        if (!TextUtils.isEmpty(searchdetailresponseBean.getBriefingcount())) {

            tvBriefingItemsCount.setText(searchdetailresponseBean.getBriefingcount());
        }
        if (!TextUtils.isEmpty(searchdetailresponseBean.getOrganisationcount())) {
            tvOrganizationCount.setText(searchdetailresponseBean.getOrganisationcount());

        }
        if (!TextUtils.isEmpty(searchdetailresponseBean.getSearchcount())) {
            tvSearchCount.setText(searchdetailresponseBean.getSearchcount());

        }
        if (!TextUtils.isEmpty(searchdetailresponseBean.getPropertyitemscount())) {
            tvPropertyItemsCount.setText(searchdetailresponseBean.getPropertyitemscount());

        }
        if (!TextUtils.isEmpty(searchdetailresponseBean.getCategorycount())) {
            tvCategoryCount.setText(searchdetailresponseBean.getCategorycount());
        }

        rlArrest.setOnClickListener(this);
        rlBails.setOnClickListener(this);
        rlBailRefusal.setOnClickListener(this);
        rlDocuments.setOnClickListener(this);
        rlOffences.setOnClickListener(this);
        rlPersonSearches.setOnClickListener(this);
        rlRiskAssesments.setOnClickListener(this);
        rlSampleTaken.setOnClickListener(this);
        rlBriefingItems.setOnClickListener(this);
        rlOrganization.setOnClickListener(this);
        rlSearch.setOnClickListener(this);
        rlPropertyItems.setOnClickListener(this);
        rlCategory.setOnClickListener(this);

    }

    /**
     * Load Person Dialog
     */
    public void loadPersonATHENAItemsDetailsDialog(int type) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.PERSON_LIST_DETAILS_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        personATHENAListDetailsDialogFragment = PersonATHENAListDetailsDialogFragment.newInstance(type, listDetails, isPopulate);
        personATHENAListDetailsDialogFragment.show(ft, GenericConstant.PERSON_LIST_DETAILS_DIALOG);
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
        AddEnquiryDialogFragment linkedInvestigationDialogFragment = AddEnquiryDialogFragment.newInstance(investigationModels.get(0).getId(),isPopulate);
        linkedInvestigationDialogFragment.show(ft, GenericConstant.ADD_ENQUIRY_DIALOG);
    }
}
