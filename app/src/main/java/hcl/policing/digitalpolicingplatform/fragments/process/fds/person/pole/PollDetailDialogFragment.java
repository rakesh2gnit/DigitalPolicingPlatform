package hcl.policing.digitalpolicingplatform.fragments.process.fds.person.pole;

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

import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.address.stops.STOPSDetailDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.person.athena.AthenaListDialogFragment;
import hcl.policing.digitalpolicingplatform.listeners.dialog.ManuallyClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.PersonSelectionListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.pole.ActionMarkersBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.pole.AddressList;
import hcl.policing.digitalpolicingplatform.models.process.fds.pole.HistoryBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.pole.PersonList;
import hcl.policing.digitalpolicingplatform.models.process.fds.pole.PoleResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.pole.VehicleList;
import hcl.policing.digitalpolicingplatform.models.process.fds.pole.WarningSignalsBean;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class PollDetailDialogFragment extends DialogFragment implements View.OnClickListener {

    private Context mContext;
    private LinearLayout llDetails, llHeader, llCommonDetails, llHistoryContent, llMarkerContent, llWarningSignalsContent, llRiskContent,
            llActionContent, llInfoMarkerContent;

    private LinearLayout llPersonDetailContent, llDescriptiveFeaturesContent, llIDNumberContent, llAssociationsContent, llFlagContent,
            llOccupationsContent, llTaskContent, llPersonSummaryContent, llPhotosContent, llNamesContent, llAddressesContent,
            llEmailsContent, llPhoneNumbersContent;
    private LinearLayout llKnownAssociatesContent, llOrganizationContent, llPeopleContent, llOccupationContent;
    private LinearLayout person_detail, vehicle_detail, address_detail, llVehicleDetailContent, llAddressDetailContent, llAssociatedPeopleContent;
    private Dialog mProgressDialog;
    private ManuallyClickListener manuallyClickListener;
    private PersonSelectionListener personSelectionListener;
    private int flowType;
    private List<PersonList> aPersonList;
    private List<VehicleList> aVehicleList;
    private List<AddressList> aAddressList;
    private LayoutInflater layoutInflater;
    private boolean isPopulate;

    public static PollDetailDialogFragment newInstance(int type, boolean isPopulate) {

        PollDetailDialogFragment frag = new PollDetailDialogFragment();
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

        View rootView = inflater.inflate(R.layout.poll_detail_fragment, container, false);
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
        RelativeLayout rlVehicleDetails = vehicle_detail.findViewById(R.id.rlVehicleDetails);

        llVehicleDetailContent = vehicle_detail.findViewById(R.id.llVehicleDetailContent);

        isPopulateView();
        loadHeaderLayout();

        if (flowType == GenericConstant.TYPE_PERSON) {
            person_detail.setVisibility(View.VISIBLE);
            loadPollJSONDetails(GenericConstant.PERSON_POLE_DETAILS_JSON);

        } else if (flowType == GenericConstant.TYPE_VEHICLE) {
            vehicle_detail.setVisibility(View.VISIBLE);
            loadPollJSONDetails(GenericConstant.VEHICLE_POLE_DETAILS_JSON);

        } else if (flowType == GenericConstant.TYPE_ADDRESS) {
            address_detail.setVisibility(View.VISIBLE);
            loadPollJSONDetails(GenericConstant.ADDRESS_POLE_DETAILS_JSON);
        }

        rlVehicleDetails.setOnClickListener(this);

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
            tvHeader.setText(getString(R.string.pole_nominal_header));

        } else if (flowType == GenericConstant.TYPE_VEHICLE) {
            tvHeader.setText(getString(R.string.pole_vehicle_header));

        } else if (flowType == GenericConstant.TYPE_ADDRESS) {
            tvHeader.setText(getString(R.string.pole_address_header));
        }
        ivBack.setOnClickListener(this);
    }


    /**
     * Load the common view which are same for all type systems
     */
    private void loadCommonView() {

        List<ActionMarkersBean> aActionMarkersBean = null;
        List<HistoryBean> aHistoryBean = null;
        List<WarningSignalsBean> aWarningSignalsBean = null;

        RelativeLayout rlMarker = llCommonDetails.findViewById(R.id.rlMarker);
        RelativeLayout rlHistory = llCommonDetails.findViewById(R.id.rlHistory);

        llMarkerContent = llCommonDetails.findViewById(R.id.llMarkerContent);
        llHistoryContent = llCommonDetails.findViewById(R.id.llHistoryContent);
        TextView tvMarkerCount = llCommonDetails.findViewById(R.id.tvMarkerCount);
        TextView tvHistoryCount = llCommonDetails.findViewById(R.id.tvHistoryCount);

        if (flowType == GenericConstant.TYPE_PERSON) {
            if (aPersonList != null && aPersonList.size() > 0) {
                PersonList mPersonList = aPersonList.get(0);
                aActionMarkersBean = mPersonList.getActionmarkers();
                aWarningSignalsBean = mPersonList.getWarningsignals();
                aHistoryBean = mPersonList.getHistory();
            }
        } else if (flowType == GenericConstant.TYPE_VEHICLE) {
            if (aVehicleList != null && aVehicleList.size() > 0) {
                VehicleList mVehicleList = aVehicleList.get(0);
                aActionMarkersBean = mVehicleList.getActionmarkers();
                aWarningSignalsBean = mVehicleList.getWarningsignals();
                aHistoryBean = mVehicleList.getHistoryList();
            }
        } else if (flowType == GenericConstant.TYPE_ADDRESS) {
            if (aAddressList != null && aAddressList.size() > 0) {
                AddressList mAddressList = aAddressList.get(0);
                aActionMarkersBean = mAddressList.getActionmarkers();
                aWarningSignalsBean = mAddressList.getWarningsignals();
                aHistoryBean = mAddressList.getHistoryList();
            }
        }
        if (aActionMarkersBean != null) {
            tvMarkerCount.setText(String.valueOf(aActionMarkersBean.size()));
        }else if (aWarningSignalsBean!=null){
            tvMarkerCount.setText(String.valueOf(aWarningSignalsBean.size()));
        }
        if (aHistoryBean != null) {
            tvHistoryCount.setText(String.valueOf(aHistoryBean.size()));
        }
        rlMarker.setOnClickListener(this);
        rlHistory.setOnClickListener(this);
    }


    /**
     * Load Addtional Link Data
     */
    private void loadMarkerData() {

        List<WarningSignalsBean> aWarningSignalsBean = null;
        List<ActionMarkersBean> aActionMarkersBean = null;

        if (flowType == GenericConstant.TYPE_PERSON) {
            aWarningSignalsBean = aPersonList.get(0).getWarningsignals();
            aActionMarkersBean = aPersonList.get(0).getActionmarkers();

        } else if (flowType == GenericConstant.TYPE_VEHICLE) {
            aWarningSignalsBean = aVehicleList.get(0).getWarningsignals();
            aActionMarkersBean = aVehicleList.get(0).getActionmarkers();

        } else if (flowType == GenericConstant.TYPE_ADDRESS) {
            aWarningSignalsBean = aAddressList.get(0).getWarningsignals();
            aActionMarkersBean = aAddressList.get(0).getActionmarkers();
        }

        RelativeLayout rlWarningSignals = llCommonDetails.findViewById(R.id.rlWarningSignals);
        RelativeLayout rlRisk = llCommonDetails.findViewById(R.id.rlRisk);
        RelativeLayout rlAction = llCommonDetails.findViewById(R.id.rlAction);
        RelativeLayout rlInfoMarker = llCommonDetails.findViewById(R.id.rlInfoMarker);

        llWarningSignalsContent = llCommonDetails.findViewById(R.id.llWarningSignalsContent);
        llRiskContent = llCommonDetails.findViewById(R.id.llRiskContent);
        llActionContent = llCommonDetails.findViewById(R.id.llActionContent);
        llInfoMarkerContent = llCommonDetails.findViewById(R.id.llInfoMarkerContent);

        TextView tvWarningSignalsCount = llCommonDetails.findViewById(R.id.tvWarningSignalsCount);
        TextView tvRiskCount = llCommonDetails.findViewById(R.id.tvRiskCount);
        TextView tvActionCount = llCommonDetails.findViewById(R.id.tvActionCount);
        TextView tvInfoMarkerCount = llCommonDetails.findViewById(R.id.tvInfoMarkerCount);


        if (aWarningSignalsBean != null && aWarningSignalsBean.size() > 0) {

            tvWarningSignalsCount.setText(String.valueOf(aWarningSignalsBean.size()));
        }
        if (aActionMarkersBean != null && aActionMarkersBean.size() > 0) {

            tvActionCount.setText(String.valueOf(aActionMarkersBean.size()));
        }
        rlWarningSignals.setOnClickListener(this);
        rlRisk.setOnClickListener(this);
        rlAction.setOnClickListener(this);
        rlInfoMarker.setOnClickListener(this);
    }


    /**
     * load History Data
     */
    private void loadHistoryData() {

        List<HistoryBean> aHistoryBean = null;

        if (flowType == GenericConstant.TYPE_PERSON) {
            aHistoryBean = aPersonList.get(0).getHistory();

        } else if (flowType == GenericConstant.TYPE_VEHICLE) {
            aHistoryBean = aVehicleList.get(0).getHistoryList();

        } else if (flowType == GenericConstant.TYPE_ADDRESS) {
            aHistoryBean = aAddressList.get(0).getHistoryList();
        }

        llHistoryContent.removeAllViews();
        if (aHistoryBean != null && aHistoryBean.size() > 0) {
            for (int i = 0; i < aHistoryBean.size(); i++) {

                HistoryBean mHistoryBean = aHistoryBean.get(i);

                View view = layoutInflater.inflate(R.layout.poll_history_items, null);
                TextView tvURNValue = view.findViewById(R.id.tvURNValue);
                TextView tvCreatedOnValue = view.findViewById(R.id.tvCreatedOnValue);
                TextView tvTypeValue = view.findViewById(R.id.tvTypeValue);
                TextView tvRoleValue = view.findViewById(R.id.tvRoleValue);
                TextView tvDescriptionValue = view.findViewById(R.id.tvDescriptionValue);
                TextView tvStageValue = view.findViewById(R.id.tvStageValue);
                TextView tvRestrictedValue = view.findViewById(R.id.tvRestrictedValue);
                TextView tvCovertAccessValue = view.findViewById(R.id.tvCovertAccessValue);

                tvURNValue.setText(mHistoryBean.getUrn());
                tvCreatedOnValue.setText(mHistoryBean.getCreatedon());
                tvTypeValue.setText(mHistoryBean.getType());
                tvRoleValue.setText(mHistoryBean.getRole());
                tvDescriptionValue.setText(mHistoryBean.getDescription());
                tvStageValue.setText(mHistoryBean.getStage());
                tvRestrictedValue.setText(mHistoryBean.getRestrictedAccess());
                tvCovertAccessValue.setText(mHistoryBean.getCovertAccess());

                llHistoryContent.addView(view);
            }
        }
    }

    /**
     * load Action Marker Data
     */
    private void loadActionMarkerData() {

        List<ActionMarkersBean> aActionMarkersBean = null;
        if (flowType == GenericConstant.TYPE_PERSON) {
            aActionMarkersBean = aPersonList.get(0).getActionmarkers();

        } else if (flowType == GenericConstant.TYPE_VEHICLE) {
            aActionMarkersBean = aVehicleList.get(0).getActionmarkers();

        } else if (flowType == GenericConstant.TYPE_ADDRESS) {
            aActionMarkersBean = aAddressList.get(0).getActionmarkers();
        }

        llActionContent.removeAllViews();
        if (aActionMarkersBean != null && aActionMarkersBean.size() > 0) {
            for (int i = 0; i < aActionMarkersBean.size(); i++) {

                ActionMarkersBean mActionMarkersBean = aActionMarkersBean.get(i);
                View view = layoutInflater.inflate(R.layout.poll_action_items, null);
                TextView tvMarkerValue = view.findViewById(R.id.tvMarkerValue);
                TextView tvSupportingNotesValue = view.findViewById(R.id.tvSupportingNotesValue);
                TextView tvRecordedOnValue = view.findViewById(R.id.tvRecordedOnValue);
                TextView tvReferenceValue = view.findViewById(R.id.tvReferenceValue);

                tvMarkerValue.setText(mActionMarkersBean.getMarker());
                tvRecordedOnValue.setText(mActionMarkersBean.getRecordedon());

                llActionContent.addView(view);
            }
        }
    }
    /**
     * load Warning Singnal Data
     */
    private void loadWarningSignalData() {

        List<WarningSignalsBean> aWarningSignalsBean = null;
        if (flowType == GenericConstant.TYPE_PERSON) {
            aWarningSignalsBean = aPersonList.get(0).getWarningsignals();

        } else if (flowType == GenericConstant.TYPE_VEHICLE) {
            aWarningSignalsBean = aVehicleList.get(0).getWarningsignals();

        } else if (flowType == GenericConstant.TYPE_ADDRESS) {
            aWarningSignalsBean = aAddressList.get(0).getWarningsignals();
        }

        llWarningSignalsContent.removeAllViews();
        if (aWarningSignalsBean != null && aWarningSignalsBean.size() > 0) {
            for (int i = 0; i < aWarningSignalsBean.size(); i++) {

                WarningSignalsBean mWarningSignalsBean = aWarningSignalsBean.get(i);
                View view = layoutInflater.inflate(R.layout.poll_action_items, null);
                TextView tvMarkerValue = view.findViewById(R.id.tvMarkerValue);
                TextView tvSupportingNotesValue = view.findViewById(R.id.tvSupportingNotesValue);
                TextView tvRecordedOnValue = view.findViewById(R.id.tvRecordedOnValue);
                TextView tvReferenceValue = view.findViewById(R.id.tvReferenceValue);

                tvMarkerValue.setText(mWarningSignalsBean.getMarker());
                tvSupportingNotesValue.setText(mWarningSignalsBean.getSupportingnotes());
                tvRecordedOnValue.setText(mWarningSignalsBean.getRecordedon());
                tvReferenceValue.setText(mWarningSignalsBean.getReference());

                llWarningSignalsContent.addView(view);
            }
        }
    }
    /**
     * Load the Person Details
     */
    private void loadPersonData() {

        RelativeLayout rlPersonDetails = person_detail.findViewById(R.id.rlPersonDetails);
        RelativeLayout rlDescriptiveFeatures = person_detail.findViewById(R.id.rlDescriptiveFeatures);
        RelativeLayout rlIDNumber = person_detail.findViewById(R.id.rlIDNumber);
        RelativeLayout rlAssociations = person_detail.findViewById(R.id.rlAssociations);
        RelativeLayout rlFlag = person_detail.findViewById(R.id.rlFlag);
        RelativeLayout rlOccupations = person_detail.findViewById(R.id.rlOccupations);
        RelativeLayout rlTask = person_detail.findViewById(R.id.rlTask);

        llPersonDetailContent = person_detail.findViewById(R.id.llPersonDetailContent);
        llDescriptiveFeaturesContent = person_detail.findViewById(R.id.llDescriptiveFeaturesContent);
        llIDNumberContent = person_detail.findViewById(R.id.llIDNumberContent);
        llAssociationsContent = person_detail.findViewById(R.id.llAssociationsContent);
        llFlagContent = person_detail.findViewById(R.id.llFlagContent);
        llOccupationsContent = person_detail.findViewById(R.id.llOccupationsContent);
        llTaskContent = person_detail.findViewById(R.id.llTaskContent);

        TextView tvDescriptiveFeaturesCount = person_detail.findViewById(R.id.tvDescriptiveFeaturesCount);
        TextView tvIDNumberCount = person_detail.findViewById(R.id.tvIDNumberCount);
        TextView tvAssociationsCount = person_detail.findViewById(R.id.tvAssociationsCount);
        TextView tvOccupationsCount = person_detail.findViewById(R.id.tvOccupationsCount);
        TextView tvTaskCount = person_detail.findViewById(R.id.tvTaskCount);

        if (aPersonList != null && aPersonList.size() > 0) {
            PersonList mPersonList = aPersonList.get(0);
            if (mPersonList.getDescriptivefeatures() != null) {
                tvDescriptiveFeaturesCount.setText("1");
            }
            if (mPersonList.getAssociation() != null) {
                tvAssociationsCount.setText("1");
            }
            if (mPersonList.getOccupations() != null) {
                tvOccupationsCount.setText("1");
            }
        }

        rlPersonDetails.setOnClickListener(this);
        rlDescriptiveFeatures.setOnClickListener(this);
        rlIDNumber.setOnClickListener(this);
        rlAssociations.setOnClickListener(this);
        rlFlag.setOnClickListener(this);
        rlOccupations.setOnClickListener(this);
        rlTask.setOnClickListener(this);
    }

    /**
     * Load vehicle data
     */
    private void loadVehicleData() {

        TextView tvCategoryValue = vehicle_detail.findViewById(R.id.tvCategoryValue);
        TextView tvTypeValue = vehicle_detail.findViewById(R.id.tvTypeValue);
        TextView tvUsageTypeValue = vehicle_detail.findViewById(R.id.tvUsageTypeValue);
        TextView tvDescriptionValue = vehicle_detail.findViewById(R.id.tvDescriptionValue);
        TextView tvMakeValue = vehicle_detail.findViewById(R.id.tvMakeValue);
        TextView tvModelValue = vehicle_detail.findViewById(R.id.tvModelValue);
        TextView tvVRMValue = vehicle_detail.findViewById(R.id.tvVRMValue);
        TextView tvVINValue = vehicle_detail.findViewById(R.id.tvVINValue);
        TextView tvNoDoorsValue = vehicle_detail.findViewById(R.id.tvNoDoorsValue);
        TextView tvNoWheelsValue = vehicle_detail.findViewById(R.id.tvNoWheelsValue);
        TextView tvWheelDriveValue = vehicle_detail.findViewById(R.id.tvWheelDriveValue);
        TextView tvEngineFuelTypeValue = vehicle_detail.findViewById(R.id.tvEngineFuelTypeValue);
        TextView tvEngineCapacityValue = vehicle_detail.findViewById(R.id.tvEngineCapacityValue);
        TextView tvEngineNumberValue = vehicle_detail.findViewById(R.id.tvEngineNumberValue);
        TextView tvEngineTwoNumberValue = vehicle_detail.findViewById(R.id.tvEngineTwoNumberValue);
        TextView tvMainColorValue = vehicle_detail.findViewById(R.id.tvMainColorValue);
        TextView tvSpecialMarkingValue = vehicle_detail.findViewById(R.id.tvSpecialMarkingValue);
        TextView tvSpecialInterestValue = vehicle_detail.findViewById(R.id.tvSpecialInterestValue);
        TextView tvVehicleValueValue = vehicle_detail.findViewById(R.id.tvVehicleValueValue);
        TextView tvOwnerValue = vehicle_detail.findViewById(R.id.tvOwnerValue);

        VehicleList mVehicleList = aVehicleList.get(0);
        if (mVehicleList != null) {

            tvCategoryValue.setText(mVehicleList.getCategory());
            tvTypeValue.setText(mVehicleList.getType());
            tvUsageTypeValue.setText(mVehicleList.getUsageType());
            tvDescriptionValue.setText(mVehicleList.getDescription());
            tvMakeValue.setText(mVehicleList.getMake());
            tvModelValue.setText(mVehicleList.getModel());
            tvVRMValue.setText(mVehicleList.getVRM());
            tvVINValue.setText(mVehicleList.getVin());
            tvNoDoorsValue.setText(mVehicleList.getNumberOfDoors());
            tvNoWheelsValue.setText(mVehicleList.getNumberOfWheels());
            tvWheelDriveValue.setText(mVehicleList.getWheelDrive());
            tvEngineFuelTypeValue.setText(mVehicleList.getEngineFuelType());
            tvEngineCapacityValue.setText(mVehicleList.getEngineCapacity());
            tvEngineNumberValue.setText(mVehicleList.getEngineNumber());
            tvEngineTwoNumberValue.setText(mVehicleList.getEngineTwoNumber());
            tvMainColorValue.setText(mVehicleList.getMainColour());
            tvSpecialMarkingValue.setText(mVehicleList.getSpecialMarking());
            tvSpecialInterestValue.setText(mVehicleList.getSpecialInterest());
            tvVehicleValueValue.setText(mVehicleList.getValue());
            tvOwnerValue.setText(mVehicleList.getOwner());
        }
    }


    /**
     * Load Address Page
     */
    private void loadAddressPage() {

        RelativeLayout rlAddressDetails = address_detail.findViewById(R.id.rlAddressDetails);
        RelativeLayout rlAssociatedPeople = address_detail.findViewById(R.id.rlAssociatedPeople);
        llAddressDetailContent = address_detail.findViewById(R.id.llAddressDetailContent);
        llAssociatedPeopleContent = address_detail.findViewById(R.id.llAssociatedPeopleContent);
        TextView tvAssociatedPeopleCount = address_detail.findViewById(R.id.tvAssociatedPeopleCount);

        List<PersonList> aPersonList = aAddressList.get(0).getPersonList();
        if (aPersonList != null) {
            tvAssociatedPeopleCount.setText(String.valueOf(aPersonList.size()));
        }

        rlAddressDetails.setOnClickListener(this);
        rlAssociatedPeople.setOnClickListener(this);
    }

    /**
     * Load address data
     */
    private void loadAddressData() {

        TextView tvFlatNoValue = address_detail.findViewById(R.id.tvFlatNoValue);
        TextView tvBuildingNoValue = address_detail.findViewById(R.id.tvBuildingNoValue);
        TextView tvStreetValue = address_detail.findViewById(R.id.tvStreetValue);
        TextView tvDistrictValue = address_detail.findViewById(R.id.tvDistrictValue);
        TextView tvCityValue = address_detail.findViewById(R.id.tvCityValue);
        TextView tvCountryValue = address_detail.findViewById(R.id.tvCountyValue);
        TextView tvPostcodeValue = address_detail.findViewById(R.id.tvPostcodeValue);
        TextView tvDescriptionValue = address_detail.findViewById(R.id.tvDescriptionValue);

        AddressList mAddressList = aAddressList.get(0);
        if (mAddressList != null) {

            tvFlatNoValue.setText(mAddressList.getFlat());
            tvBuildingNoValue.setText(mAddressList.getBuilding());
            tvStreetValue.setText(mAddressList.getStreet());
            tvDistrictValue.setText(mAddressList.getDistrict());
            tvCityValue.setText(mAddressList.getCity());
            tvCountryValue.setText(mAddressList.getCounty());
            tvPostcodeValue.setText(mAddressList.getPostCode());

            StringBuilder sbDesc = new StringBuilder();
            sbDesc.append(mAddressList.getFlat()).append(" ").append(mAddressList.getBuilding()).append(" ").append(mAddressList.getStreet())
                    .append(", ").append(mAddressList.getDistrict()).append(", ").append(mAddressList.getCity()).append("\n")
                    .append(mAddressList.getCounty()).append(", ").append(mAddressList.getPostCode());

            tvDescriptionValue.setText(sbDesc.toString());
        }
    }

    /**
     * load AssociatedPeople list
     */
    private void loadAssociatedPeopleList() {

        try {
            List<PersonList> aPersonList = aAddressList.get(0).getPersonList();
            llAssociatedPeopleContent.removeAllViews();
            if (aPersonList != null && aPersonList.size() > 0) {
                for (int i = 0; i < aPersonList.size(); i++) {

                    PersonList mPersonList = aPersonList.get(i);
                    View view = layoutInflater.inflate(R.layout.pole_associated_people_item, null);

                    TextView tvNameValue = view.findViewById(R.id.tvNameValue);
                    TextView tvDOBValue = view.findViewById(R.id.tvDOBValue);
                    TextView tvGenderValue = view.findViewById(R.id.tvGenderValue);
                    TextView tvEthnicityValue = view.findViewById(R.id.tvEthnicityValue);
                    TextView tvRoleValue = view.findViewById(R.id.tvRoleValue);
                    TextView tvMarkersValue = view.findViewById(R.id.tvMarkersValue);
                    TextView tvFlagsValue = view.findViewById(R.id.tvFlagsValue);
                    TextView tvAddedOnValue = view.findViewById(R.id.tvAddedOnValue);

                    tvNameValue.setText(mPersonList.getForename());
                    tvDOBValue.setText(mPersonList.getDateOfBirth());
                    tvGenderValue.setText(mPersonList.getGender());
                    tvEthnicityValue.setText(mPersonList.getOfficerDefinedEthnicity());
                    tvRoleValue.setText(mPersonList.getRoleText());
                    tvMarkersValue.setText(mPersonList.getMarker());
                    tvFlagsValue.setText(mPersonList.getFlagText());
                    tvAddedOnValue.setText(mPersonList.getCreateddate());

                    llAssociatedPeopleContent.addView(view);
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), AthenaListDialogFragment.class, "loadAssociatedPeopleList");
        }

    }


    /**
     * Load person details
     */
    private void loadPersonDetailData() {

        RelativeLayout rlPersonSummary = person_detail.findViewById(R.id.rlPersonSummary);
        RelativeLayout rlPhotos = person_detail.findViewById(R.id.rlPhotos);
        RelativeLayout rlNames = person_detail.findViewById(R.id.rlNames);
        RelativeLayout rlAddresses = person_detail.findViewById(R.id.rlAddresses);
        RelativeLayout rlEmails = person_detail.findViewById(R.id.rlEmails);
        RelativeLayout rlPhoneNumbers = person_detail.findViewById(R.id.rlPhoneNumbers);

        llPersonSummaryContent = person_detail.findViewById(R.id.llPersonSummaryContent);
        llPhotosContent = person_detail.findViewById(R.id.llPhotosContent);
        llNamesContent = person_detail.findViewById(R.id.llNamesContent);
        llAddressesContent = person_detail.findViewById(R.id.llAddressesContent);
        llEmailsContent = person_detail.findViewById(R.id.llEmailsContent);
        llPhoneNumbersContent = person_detail.findViewById(R.id.llPhoneNumbersContent);


        TextView tvPhotosCount = person_detail.findViewById(R.id.tvPhotosCount);
        TextView tvNamesCount = person_detail.findViewById(R.id.tvNamesCount);
        TextView tvAddressesCount = person_detail.findViewById(R.id.tvAddressesCount);
        TextView tvEmailsCount = person_detail.findViewById(R.id.tvEmailsCount);
        TextView tvPhoneNumbersCount = person_detail.findViewById(R.id.tvPhoneNumbersCount);


        PersonList mPersonList = aPersonList.get(0);

        if (mPersonList != null) {
            if (mPersonList.getNamelist() != null) {
                tvNamesCount.setText(String.valueOf(mPersonList.getNamelist().size()));
            }
            if (mPersonList.getAddresslist() != null) {
                tvAddressesCount.setText(String.valueOf(mPersonList.getAddresslist().size()));
            }
            if (mPersonList.getEmailaddresses() != null) {
                tvEmailsCount.setText(String.valueOf(mPersonList.getEmailaddresses().size()));
            }
            if (mPersonList.getPhonenumbers() != null) {
                tvPhoneNumbersCount.setText(String.valueOf(mPersonList.getPhonenumbers().size()));
            }
        }

        rlPersonSummary.setOnClickListener(this);
        rlPhotos.setOnClickListener(this);
        rlNames.setOnClickListener(this);
        rlAddresses.setOnClickListener(this);
        rlEmails.setOnClickListener(this);
        rlPhoneNumbers.setOnClickListener(this);
    }

    /**
     * load Person Summary
     */
    private void loadPersonSummary() {

        TextView tvMainNameValue = llPersonSummaryContent.findViewById(R.id.tvMainNameValue);
        TextView tvDOBValue = llPersonSummaryContent.findViewById(R.id.tvDOBValue);
        TextView tvGenderValue = llPersonSummaryContent.findViewById(R.id.tvGenderValue);
        TextView tvAgeValue = llPersonSummaryContent.findViewById(R.id.tvAgeValue);
        TextView tvEthinicValue = llPersonSummaryContent.findViewById(R.id.tvEthinicValue);
        TextView tvHeightValue = llPersonSummaryContent.findViewById(R.id.tvHeightValue);
        TextView tvBuildValue = llPersonSummaryContent.findViewById(R.id.tvBuildValue);
        TextView tvMainAddressValue = llPersonSummaryContent.findViewById(R.id.tvMainAddressValue);

        PersonList mPersonList = aPersonList.get(0);
        if (mPersonList != null) {

            tvMainNameValue.setText(mPersonList.getMainName());
            tvDOBValue.setText(mPersonList.getDateOfBirth());
            tvGenderValue.setText(mPersonList.getGender());
            tvAgeValue.setText(mPersonList.getAge());
            tvEthinicValue.setText(mPersonList.getOfficerDefinedEthnicity());
            tvHeightValue.setText(mPersonList.getMetricHeight());
            tvMainAddressValue.setText(mPersonList.getMainAddress());

        }
    }

    /**
     * load person photos
     */
    private void loadPhoto() {

    }

    /**
     * load Name list
     */
    private void loadNameList() {

        try {
            List<PersonList.NameListBean> aNameListBean = aPersonList.get(0).getNamelist();
            llNamesContent.removeAllViews();
            if (aNameListBean != null && aNameListBean.size() > 0) {
                for (int i = 0; i < aNameListBean.size(); i++) {

                    PersonList.NameListBean mNameListBean = aNameListBean.get(i);
                    View view = layoutInflater.inflate(R.layout.poll_person_name_items, null);
                    TextView tvTitleValue = view.findViewById(R.id.tvTitleValue);
                    TextView tvNameValue = view.findViewById(R.id.tvNameValue);
                    TextView tvMainNameValue = view.findViewById(R.id.tvMainNameValue);
                    TextView tvPNCFileValue = view.findViewById(R.id.tvPNCFileValue);

                    tvTitleValue.setText(mNameListBean.getTitle());
                    tvNameValue.setText(mNameListBean.getName());
                    tvMainNameValue.setText(mNameListBean.getMainName());
                    tvPNCFileValue.setText(mNameListBean.getPncfilename());

                    llNamesContent.addView(view);
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), AthenaListDialogFragment.class, "loadNameList");
        }

    }

    /**
     * load Address list
     */
    private void loadAddressList() {
        try {
            List<PersonList.AddressListBean> aAddressListBean = aPersonList.get(0).getAddresslist();
            llAddressesContent.removeAllViews();
            if (aAddressListBean != null && aAddressListBean.size() > 0) {
                for (int i = 0; i < aAddressListBean.size(); i++) {

                    PersonList.AddressListBean mAddressListBean = aAddressListBean.get(i);
                    View view = layoutInflater.inflate(R.layout.poll_address_items, null);
                    TextView tvAddressValue = view.findViewById(R.id.tvAddressValue);
                    TextView tvPostcodeValue = view.findViewById(R.id.tvPostcodeValue);
                    TextView tvTypeValue = view.findViewById(R.id.tvTypeValue);
                    TextView tvMainValue = view.findViewById(R.id.tvMainValue);

                    tvAddressValue.setText(mAddressListBean.getAddress());
                    tvPostcodeValue.setText(mAddressListBean.getPostcode());
                    tvTypeValue.setText(mAddressListBean.getType());
                    tvMainValue.setText(mAddressListBean.getMain());

                    llAddressesContent.addView(view);
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), AthenaListDialogFragment.class, "loadAddressList");
        }
    }

    /**
     * load Email list
     */
    private void loadEmailList() {
        try {
            List<PersonList.EmailAddressesBean> aEmailAddressesBean = aPersonList.get(0).getEmailaddresses();
            llEmailsContent.removeAllViews();
            if (aEmailAddressesBean != null && aEmailAddressesBean.size() > 0) {
                for (int i = 0; i < aEmailAddressesBean.size(); i++) {

                    PersonList.EmailAddressesBean mEmailAddressesBean = aEmailAddressesBean.get(i);
                    View view = layoutInflater.inflate(R.layout.poll_email_items, null);
                    TextView tvEmailAddressValue = view.findViewById(R.id.tvEmailAddressValue);
                    TextView tvMainValue = view.findViewById(R.id.tvMainValue);

                    tvEmailAddressValue.setText(mEmailAddressesBean.getEmailid());
                    tvMainValue.setText(mEmailAddressesBean.getIsmain());

                    llEmailsContent.addView(view);
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), AthenaListDialogFragment.class, "loadEmailList");
        }
    }

    /**
     * load Phone list
     */
    private void loadPhoneList() {

        try {
            List<PersonList.PhoneNumbersBean> aPhoneNumbersBean = aPersonList.get(0).getPhonenumbers();
            llPhoneNumbersContent.removeAllViews();
            if (aPhoneNumbersBean != null && aPhoneNumbersBean.size() > 0) {
                for (int i = 0; i < aPhoneNumbersBean.size(); i++) {

                    PersonList.PhoneNumbersBean mPhoneNumbersBean = aPhoneNumbersBean.get(i);
                    View view = layoutInflater.inflate(R.layout.poll_phone_items, null);
                    TextView tvCountryCodeValue = view.findViewById(R.id.tvCountryCodeValue);
                    TextView tvNumberValue = view.findViewById(R.id.tvNumberValue);
                    TextView tvExtensionValue = view.findViewById(R.id.tvExtensionValue);
                    TextView tvTypeValue = view.findViewById(R.id.tvTypeValue);
                    TextView tvMainValue = view.findViewById(R.id.tvMainValue);

                    tvCountryCodeValue.setText(mPhoneNumbersBean.getCountrycode());
                    tvNumberValue.setText(mPhoneNumbersBean.getNumber());
                    tvExtensionValue.setText(mPhoneNumbersBean.getExtension());
                    tvTypeValue.setText(mPhoneNumbersBean.getType());
                    tvMainValue.setText(mPhoneNumbersBean.getIsmain());

                    llPhoneNumbersContent.addView(view);
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), AthenaListDialogFragment.class, "loadPhoneList");
        }
    }


    /**
     * Load association details
     */
    private void loadAssociationData() {

        RelativeLayout rlKnownAssociates = person_detail.findViewById(R.id.rlKnownAssociates);
        RelativeLayout rlOrganization = person_detail.findViewById(R.id.rlOrganization);
        RelativeLayout rlPeople = person_detail.findViewById(R.id.rlPeople);

        llKnownAssociatesContent = person_detail.findViewById(R.id.llKnownAssociatesContent);
        llOrganizationContent = person_detail.findViewById(R.id.llOrganizationContent);
        llPeopleContent = person_detail.findViewById(R.id.llPeopleContent);

        TextView tvKnownAssociatesCount = person_detail.findViewById(R.id.tvKnownAssociatesCount);
        TextView tvOrganizationCount = person_detail.findViewById(R.id.tvOrganizationCount);
        TextView tvPeopleCount = person_detail.findViewById(R.id.tvPeopleCount);

        PersonList.AssociationBean mAssociationBean = aPersonList.get(0).getAssociation();

        if (mAssociationBean != null) {
            if (mAssociationBean.getKnownassociateslist() != null) {
                tvKnownAssociatesCount.setText(String.valueOf(mAssociationBean.getKnownassociateslist().size()));
            }
            if (mAssociationBean.getOrganisationlist() != null) {
                tvOrganizationCount.setText(String.valueOf(mAssociationBean.getOrganisationlist().size()));
            }
            if (mAssociationBean.getPeoplelist() != null) {
                tvPeopleCount.setText(String.valueOf(mAssociationBean.getPeoplelist().size()));
            }
        }

        rlKnownAssociates.setOnClickListener(this);
        rlOrganization.setOnClickListener(this);
        rlPeople.setOnClickListener(this);
    }

    /**
     * Load Occupation details
     */
    private void loadOccupationData() {

        RelativeLayout rlOccupation = person_detail.findViewById(R.id.rlOccupation);
        llOccupationContent = person_detail.findViewById(R.id.llOccupationContent);

        TextView tvOccupationCount = person_detail.findViewById(R.id.tvOccupationCount);

        PersonList.OccupationsBean mOccupationsBean = aPersonList.get(0).getOccupations();

        if (mOccupationsBean != null) {
            if (mOccupationsBean.getOccupationlist() != null) {
                tvOccupationCount.setText(String.valueOf(mOccupationsBean.getOccupationlist().size()));
            }
        }

        rlOccupation.setOnClickListener(this);
    }

    /**
     * load KnownAssociate list
     */
    private void loadKnownAssociateData() {
        try {
            PersonList.AssociationBean mAssociationBean = aPersonList.get(0).getAssociation();
            if (mAssociationBean != null) {
                List<PersonList.KnownAssociatesListBean> aKnownAssociatesListBean = mAssociationBean.getKnownassociateslist();
                llKnownAssociatesContent.removeAllViews();
                if (aKnownAssociatesListBean != null && aKnownAssociatesListBean.size() > 0) {
                    for (int i = 0; i < aKnownAssociatesListBean.size(); i++) {

                        PersonList.KnownAssociatesListBean mKnownAssociatesListBean = aKnownAssociatesListBean.get(i);
                        View view = layoutInflater.inflate(R.layout.poll_associate_items, null);
                        TextView tvNameValue = view.findViewById(R.id.tvNameValue);
                        TextView tvDOBValue = view.findViewById(R.id.tvDOBValue);
                        TextView tvRelationshipValue = view.findViewById(R.id.tvRelationshipValue);

                        tvNameValue.setText(mKnownAssociatesListBean.getName());
                        tvDOBValue.setText(mKnownAssociatesListBean.getDob());
                        tvRelationshipValue.setText(mKnownAssociatesListBean.getRelationshiptoperson());

                        llKnownAssociatesContent.addView(view);
                    }
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), AthenaListDialogFragment.class, "loadEmailList");
        }
    }

    /**
     * load Occupation list
     */
    private void loadOccupationItemsData() {
        try {
            PersonList.OccupationsBean mOccupationsBean = aPersonList.get(0).getOccupations();
            if (mOccupationsBean != null) {
                List<PersonList.OccupationListBean> aOccupationListBean = mOccupationsBean.getOccupationlist();
                llOccupationContent.removeAllViews();
                if (aOccupationListBean != null && aOccupationListBean.size() > 0) {
                    for (int i = 0; i < aOccupationListBean.size(); i++) {

                        PersonList.OccupationListBean mOccupationListBean = aOccupationListBean.get(i);
                        View view = layoutInflater.inflate(R.layout.poll_occupation_items, null);
                        TextView tvOccupationValue = view.findViewById(R.id.tvOccupationValue);
                        TextView tvRecordedOnValue = view.findViewById(R.id.tvRecordedOnValue);
                        TextView tvReferenceValue = view.findViewById(R.id.tvReferenceValue);

                        tvOccupationValue.setText(mOccupationListBean.getOccupation());
                        tvRecordedOnValue.setText("");
                        tvReferenceValue.setText("");

                        llOccupationContent.addView(view);
                    }
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), AthenaListDialogFragment.class, "loadEmailList");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ivBack:
                DialogUtil.cancelProgressDialog(mProgressDialog);
                dismiss();

                break;

            case R.id.rlPersonDetails:

                if (llPersonDetailContent.getVisibility() != View.VISIBLE) {
                    llPersonDetailContent.setVisibility(View.VISIBLE);
                    loadPersonDetailData();
                } else {
                    llPersonDetailContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlVehicleDetails:

                if (llVehicleDetailContent.getVisibility() != View.VISIBLE) {
                    llVehicleDetailContent.setVisibility(View.VISIBLE);
                    loadVehicleData();
                } else {
                    llVehicleDetailContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlAddressDetails:

                if (llAddressDetailContent.getVisibility() != View.VISIBLE) {
                    llAddressDetailContent.setVisibility(View.VISIBLE);
                    loadAddressData();
                } else {
                    llAddressDetailContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlAssociatedPeople:

                if (llAssociatedPeopleContent.getVisibility() != View.VISIBLE) {
                    llAssociatedPeopleContent.setVisibility(View.VISIBLE);
                    loadAssociatedPeopleList();
                } else {
                    llAssociatedPeopleContent.setVisibility(View.GONE);
                }
                break;

            case R.id.rlPersonSummary:

                if (llPersonSummaryContent.getVisibility() != View.VISIBLE) {
                    llPersonSummaryContent.setVisibility(View.VISIBLE);
                    loadPersonSummary();
                } else {
                    llPersonSummaryContent.setVisibility(View.GONE);
                }

                break;
            case R.id.rlPhotos:

                if (llPhotosContent.getVisibility() != View.VISIBLE) {
                    llPhotosContent.setVisibility(View.VISIBLE);
                    loadPhoto();
                } else {
                    llPhotosContent.setVisibility(View.GONE);
                }

                break;
            case R.id.rlNames:

                if (llNamesContent.getVisibility() != View.VISIBLE) {
                    llNamesContent.setVisibility(View.VISIBLE);
                    loadNameList();
                } else {
                    llNamesContent.setVisibility(View.GONE);
                }

                break;
            case R.id.rlAddresses:

                if (llAddressesContent.getVisibility() != View.VISIBLE) {
                    llAddressesContent.setVisibility(View.VISIBLE);
                    loadAddressList();
                } else {
                    llAddressesContent.setVisibility(View.GONE);
                }

                break;
            case R.id.rlEmails:

                if (llEmailsContent.getVisibility() != View.VISIBLE) {
                    llEmailsContent.setVisibility(View.VISIBLE);
                    loadEmailList();
                } else {
                    llEmailsContent.setVisibility(View.GONE);
                }

                break;
            case R.id.rlPhoneNumbers:

                if (llPhoneNumbersContent.getVisibility() != View.VISIBLE) {
                    llPhoneNumbersContent.setVisibility(View.VISIBLE);
                    loadPhoneList();
                } else {
                    llPhoneNumbersContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlDescriptiveFeatures:

                if (llDescriptiveFeaturesContent.getVisibility() != View.VISIBLE) {
                    llDescriptiveFeaturesContent.setVisibility(View.VISIBLE);

                } else {
                    llDescriptiveFeaturesContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlIDNumber:

                if (llIDNumberContent.getVisibility() != View.VISIBLE) {
                    llIDNumberContent.setVisibility(View.VISIBLE);

                } else {
                    llIDNumberContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlFlag:

                if (llFlagContent.getVisibility() != View.VISIBLE) {
                    llFlagContent.setVisibility(View.VISIBLE);

                } else {
                    llFlagContent.setVisibility(View.GONE);
                }

                break;
            case R.id.rlAssociations:

                if (llAssociationsContent.getVisibility() != View.VISIBLE) {
                    llAssociationsContent.setVisibility(View.VISIBLE);
                    loadAssociationData();
                } else {
                    llAssociationsContent.setVisibility(View.GONE);
                }
                break;

            case R.id.rlKnownAssociates:

                if (llKnownAssociatesContent.getVisibility() != View.VISIBLE) {
                    llKnownAssociatesContent.setVisibility(View.VISIBLE);
                    loadKnownAssociateData();
                } else {
                    llKnownAssociatesContent.setVisibility(View.GONE);
                }
                break;

            case R.id.rlOrganization:

                if (llOrganizationContent.getVisibility() != View.VISIBLE) {
                    llOrganizationContent.setVisibility(View.VISIBLE);
                } else {
                    llOrganizationContent.setVisibility(View.GONE);
                }
                break;

            case R.id.rlPeople:

                if (llPeopleContent.getVisibility() != View.VISIBLE) {
                    llPeopleContent.setVisibility(View.VISIBLE);
                } else {
                    llPeopleContent.setVisibility(View.GONE);
                }
                break;

            case R.id.rlOccupations:

                if (llOccupationsContent.getVisibility() != View.VISIBLE) {
                    llOccupationsContent.setVisibility(View.VISIBLE);
                    loadOccupationData();
                } else {
                    llOccupationsContent.setVisibility(View.GONE);
                }
                break;

            case R.id.rlOccupation:

                if (llOccupationContent.getVisibility() != View.VISIBLE) {
                    llOccupationContent.setVisibility(View.VISIBLE);
                    loadOccupationItemsData();
                } else {
                    llOccupationContent.setVisibility(View.GONE);
                }
                break;
            case R.id.rlTask:

                if (llTaskContent.getVisibility() != View.VISIBLE) {
                    llTaskContent.setVisibility(View.VISIBLE);

                } else {
                    llTaskContent.setVisibility(View.GONE);
                }
                break;

            case R.id.rlMarker:

                if (llMarkerContent.getVisibility() != View.VISIBLE) {
                    llMarkerContent.setVisibility(View.VISIBLE);
                    loadMarkerData();
                } else {
                    llMarkerContent.setVisibility(View.GONE);
                }
                break;
            case R.id.rlHistory:

                if (llHistoryContent.getVisibility() != View.VISIBLE) {
                    llHistoryContent.setVisibility(View.VISIBLE);
                    loadHistoryData();
                } else {
                    llHistoryContent.setVisibility(View.GONE);
                }
                break;

            case R.id.rlWarningSignals:

                if (llWarningSignalsContent.getVisibility() != View.VISIBLE) {
                    llWarningSignalsContent.setVisibility(View.VISIBLE);
                    loadWarningSignalData();
                } else {
                    llWarningSignalsContent.setVisibility(View.GONE);
                }
                break;

            case R.id.llRisk:

                if (llRiskContent.getVisibility() != View.VISIBLE) {
                    llRiskContent.setVisibility(View.VISIBLE);
                    loadHistoryData();
                } else {
                    llRiskContent.setVisibility(View.GONE);
                }
                break;
            case R.id.rlAction:

                if (llActionContent.getVisibility() != View.VISIBLE) {
                    llActionContent.setVisibility(View.VISIBLE);
                    loadActionMarkerData();
                } else {
                    llActionContent.setVisibility(View.GONE);
                }
                break;
            case R.id.rlInfoMarker:

                if (llInfoMarkerContent.getVisibility() != View.VISIBLE) {
                    llInfoMarkerContent.setVisibility(View.VISIBLE);
                    // Load InfoMarker
                } else {
                    llInfoMarkerContent.setVisibility(View.GONE);
                }
                break;
        }

    }


    /**
     * Load Poll details JSON
     */
    public void loadPollJSONDetails(String fileName) {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);
                PoleResponse poleResponse = (PoleResponse) JsonUtil.getInstance().toModel(strJson, PoleResponse.class);

                if (poleResponse != null) {

                    PoleResponse.POLESearchResponse poleSearchResponse = poleResponse.getPOLESearchResponse();
                    if (poleSearchResponse != null) {

                        if (flowType == GenericConstant.TYPE_PERSON) {
                            aPersonList = poleSearchResponse.getPersonList();

                        } else if (flowType == GenericConstant.TYPE_VEHICLE) {
                            aVehicleList = poleSearchResponse.getVehicleList();

                        } else if (flowType == GenericConstant.TYPE_ADDRESS) {
                            aAddressList = poleSearchResponse.getAddressList();
                        }
                    }
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);

                    if (flowType == GenericConstant.TYPE_PERSON) {
                        person_detail.setVisibility(View.VISIBLE);
                        loadPersonData();

                    } else if (flowType == GenericConstant.TYPE_VEHICLE) {
                        vehicle_detail.setVisibility(View.VISIBLE);
                        loadVehicleData();

                    } else if (flowType == GenericConstant.TYPE_ADDRESS) {
                        address_detail.setVisibility(View.VISIBLE);
                        loadAddressPage();
                    }

                    loadCommonView();
                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), STOPSDetailDialogFragment.class, "loadStopJSONDetails");
            }
        }).start();
    }
}
