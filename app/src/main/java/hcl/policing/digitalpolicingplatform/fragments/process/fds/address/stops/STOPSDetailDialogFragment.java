package hcl.policing.digitalpolicingplatform.fragments.process.fds.address.stops;

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
import hcl.policing.digitalpolicingplatform.listeners.dialog.ManuallyClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.PersonSelectionListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.stops.AddressStopDetailsResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.stops.SearchAddressListModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.stops.SearchPersonListModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.stops.SearchStopListModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.stops.SearchVehicleListModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.stops.PersonStopDetailResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.stops.VehicleStopDetailsResponse;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class STOPSDetailDialogFragment extends DialogFragment implements View.OnClickListener {

    private Context mContext;
    private LinearLayout llDetails,llHeader, stops_detail, llVehicle, llAddressDetails, llVehicleDetails, llVehicleContent, llAddressDetailsContent, llPersonDetailsContent, llStopDetailsContent,
            llVehicleDetailsContent;
    private Dialog mProgressDialog;
    private ManuallyClickListener manuallyClickListener;
    private PersonSelectionListener personSelectionListener;
    private int flowType;
    private List<SearchAddressListModel> aSearchAddressListModel;
    private List<SearchPersonListModel> aSearchPersonListModel;
    private List<SearchStopListModel> aSearchStopListModel;
    private List<SearchVehicleListModel> aSearchVehicleListModel;
    private LayoutInflater layoutInflater;
    private boolean isPopulate;

    public static STOPSDetailDialogFragment newInstance(int type,boolean isPopulate) {

        STOPSDetailDialogFragment frag = new STOPSDetailDialogFragment();
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

        View rootView = inflater.inflate(R.layout.stops_detail_fragment, container, false);
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
        stops_detail = view.findViewById(R.id.stops_detail);

        llVehicle = stops_detail.findViewById(R.id.llVehicle);
        llVehicleDetails = stops_detail.findViewById(R.id.llVehicleDetails);
        llAddressDetails = stops_detail.findViewById(R.id.llAddressDetails);

        llVehicleContent = stops_detail.findViewById(R.id.llVehicleContent);
        llAddressDetailsContent = stops_detail.findViewById(R.id.llAddressDetailsContent);
        llPersonDetailsContent = stops_detail.findViewById(R.id.llPersonDetailsContent);
        llStopDetailsContent = stops_detail.findViewById(R.id.llStopDetailsContent);
        llVehicleDetailsContent = stops_detail.findViewById(R.id.llVehicleDetailsContent);

        RelativeLayout rlAddressDetails = stops_detail.findViewById(R.id.rlAddressDetails);
        RelativeLayout rlPersonDetails = stops_detail.findViewById(R.id.rlPersonDetails);
        RelativeLayout rlStopDetails = stops_detail.findViewById(R.id.rlStopDetails);
        RelativeLayout rlVehicleDetails = stops_detail.findViewById(R.id.rlVehicleDetails);
        RelativeLayout rlVehicle = stops_detail.findViewById(R.id.rlVehicle);

        isPopulateView();
        loadHeaderLayout();

        if (flowType == GenericConstant.TYPE_PERSON) {
            loadStopJSONDetails(GenericConstant.PERSON_STOPS_DETAILS_JSON, flowType);

        } else if (flowType == GenericConstant.TYPE_ADDRESS) {
            loadStopJSONDetails(GenericConstant.ADDRESS_STOPS_DETAILS_JSON, flowType);

        } else if (flowType == GenericConstant.TYPE_VEHICLE) {
            loadStopJSONDetails(GenericConstant.VEHICLE_STOPS_DETAILS_JSON, flowType);

        }

        rlAddressDetails.setOnClickListener(this);
        rlPersonDetails.setOnClickListener(this);
        rlStopDetails.setOnClickListener(this);
        rlVehicleDetails.setOnClickListener(this);
        rlVehicle.setOnClickListener(this);
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
            tvHeader.setText(getString(R.string.stops_nominal_header));

        } else if (flowType == GenericConstant.TYPE_VEHICLE) {
            tvHeader.setText(getString(R.string.stops_vehicle_header));

        }else if (flowType == GenericConstant.TYPE_ADDRESS) {
            tvHeader.setText(getString(R.string.stops_address_header));

        }

        ivBack.setOnClickListener(this);
    }


    /**
     * Load the Person Details
     */
    private void loadPersonData() {

        llPersonDetailsContent.removeAllViews();
        if (aSearchPersonListModel != null && aSearchPersonListModel.size() > 0) {
            for (int i = 0; i < aSearchPersonListModel.size(); i++) {

                SearchPersonListModel mSearchPersonListModel = aSearchPersonListModel.get(i);

                View view = layoutInflater.inflate(R.layout.stops_person_details_items, null);
                TextView tvFNameValue = view.findViewById(R.id.tvFNameValue);
                TextView tvLNameValue = view.findViewById(R.id.tvLNameValue);
                TextView tvDOBValue = view.findViewById(R.id.tvDOBValue);
                TextView tvGenderValue = view.findViewById(R.id.tvGenderValue);
                TextView tvEthnicityValue = view.findViewById(R.id.tvEthnicityValue);

                tvFNameValue.setText(mSearchPersonListModel.getFirstname());
                tvLNameValue.setText(mSearchPersonListModel.getLastname());
                tvDOBValue.setText(mSearchPersonListModel.getDob());
                tvGenderValue.setText(mSearchPersonListModel.getGender());
                tvEthnicityValue.setText(mSearchPersonListModel.getEthnicity());
                llPersonDetailsContent.addView(view);
            }
        }
    }

    /**
     * Load the Stops Details
     */
    private void loadStopData() {

        llStopDetailsContent.removeAllViews();
        if (aSearchStopListModel != null && aSearchStopListModel.size() > 0) {
            for (int i = 0; i < aSearchStopListModel.size(); i++) {

                SearchStopListModel mSearchStopListModel = aSearchStopListModel.get(i);

                View view = layoutInflater.inflate(R.layout.stops_stop_details_items, null);
                TextView tvSearchIdValue = view.findViewById(R.id.tvSearchIdValue);
                TextView tvLegislationValue = view.findViewById(R.id.tvLegislationValue);
                TextView tvGroundSearchValue = view.findViewById(R.id.tvGroundSearchValue);
                TextView tvStopDateValue = view.findViewById(R.id.tvStopDateValue);
                TextView tvStopPlaceValue = view.findViewById(R.id.tvStopPlaceValue);
                TextView tvStoppingOfficerValue = view.findViewById(R.id.tvStoppingOfficerValue);
                View viewStopPlace = view.findViewById(R.id.viewStopPlace);
                View viewStoppingOfficer = view.findViewById(R.id.viewStoppingOfficer);
                LinearLayout llStopPlace = view.findViewById(R.id.llStopPlace);
                LinearLayout llStoppingOfficer = view.findViewById(R.id.llStoppingOfficer);

                if (flowType == GenericConstant.TYPE_ADDRESS || flowType == GenericConstant.TYPE_VEHICLE) {
                    viewStopPlace.setVisibility(View.GONE);
                    viewStoppingOfficer.setVisibility(View.GONE);
                    llStopPlace.setVisibility(View.GONE);
                    llStoppingOfficer.setVisibility(View.GONE);
                }

                tvSearchIdValue.setText(mSearchStopListModel.getStopid());
                tvLegislationValue.setText(mSearchStopListModel.getGrounds());
                tvGroundSearchValue.setText(mSearchStopListModel.getPowerused());
                tvStopDateValue.setText(mSearchStopListModel.getStopsdatetime());
                tvStopPlaceValue.setText(mSearchStopListModel.getStopaddress());
                tvStoppingOfficerValue.setText(mSearchStopListModel.getScreenname());

                llStopDetailsContent.addView(view);
            }
        }
    }


    /**
     * Load the Vehicle Details
     */
    private void loadVehicleData() {

        if (flowType == GenericConstant.TYPE_VEHICLE) {
            llVehicleContent.removeAllViews();
        } else {
            llVehicleDetailsContent.removeAllViews();
        }
        if (aSearchVehicleListModel != null && aSearchVehicleListModel.size() > 0) {
            for (int i = 0; i < aSearchVehicleListModel.size(); i++) {

                SearchVehicleListModel mSearchVehicleListModel = aSearchVehicleListModel.get(i);

                View view = layoutInflater.inflate(R.layout.stops_vehicle_details_items, null);
                TextView tvVRMValue = view.findViewById(R.id.tvVRMValue);
                TextView tvMakeValue = view.findViewById(R.id.tvMakeValue);
                TextView tvModelValue = view.findViewById(R.id.tvModelValue);
                TextView tvColorValue = view.findViewById(R.id.tvColorValue);
                TextView tvClassificationValue = view.findViewById(R.id.tvClassificationValue);

                tvVRMValue.setText(mSearchVehicleListModel.getVrm());
                tvMakeValue.setText(mSearchVehicleListModel.getMake());
                tvModelValue.setText(mSearchVehicleListModel.getModel());
                tvColorValue.setText(mSearchVehicleListModel.getColor());
                tvClassificationValue.setText(mSearchVehicleListModel.getVehicleclass());

                if (flowType == GenericConstant.TYPE_VEHICLE) {
                    llVehicleContent.addView(view);
                } else {
                    llVehicleDetailsContent.addView(view);
                }
            }
        }
    }

    /**
     * Load the Address Details
     */
    private void loadAddressData() {

        llAddressDetailsContent.removeAllViews();
        if (aSearchAddressListModel != null && aSearchAddressListModel.size() > 0) {
            for (int i = 0; i < aSearchAddressListModel.size(); i++) {

                SearchAddressListModel mSearchAddressListModel = aSearchAddressListModel.get(i);

                View view = layoutInflater.inflate(R.layout.stops_address_details_items, null);
                TextView tvHouseNoValue = view.findViewById(R.id.tvHouseNoValue);
                TextView tvStreetValue = view.findViewById(R.id.tvStreetValue);
                TextView tvCityValue = view.findViewById(R.id.tvCityValue);
                TextView tvPostcodeValue = view.findViewById(R.id.tvPostcodeValue);

                tvHouseNoValue.setText(mSearchAddressListModel.getHousenumber());
                tvStreetValue.setText(mSearchAddressListModel.getStreet());
                tvCityValue.setText(mSearchAddressListModel.getCity());
                tvPostcodeValue.setText(mSearchAddressListModel.getPostcode());

                llAddressDetailsContent.addView(view);
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

            case R.id.rlPersonDetails:

                if (llPersonDetailsContent.getVisibility() != View.VISIBLE) {
                    llPersonDetailsContent.setVisibility(View.VISIBLE);
                    loadPersonData();
                } else {
                    llPersonDetailsContent.setVisibility(View.GONE);
                }

                break;
            case R.id.rlStopDetails:

                if (llStopDetailsContent.getVisibility() != View.VISIBLE) {
                    llStopDetailsContent.setVisibility(View.VISIBLE);
                    loadStopData();
                } else {
                    llStopDetailsContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlAddressDetails:

                if (llAddressDetailsContent.getVisibility() != View.VISIBLE) {
                    llAddressDetailsContent.setVisibility(View.VISIBLE);
                    loadAddressData();
                } else {
                    llAddressDetailsContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlVehicleDetails:

                if (llVehicleDetailsContent.getVisibility() != View.VISIBLE) {
                    llVehicleDetailsContent.setVisibility(View.VISIBLE);
                    loadVehicleData();
                } else {
                    llVehicleDetailsContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlVehicle:

                if (llVehicleContent.getVisibility() != View.VISIBLE) {
                    llVehicleContent.setVisibility(View.VISIBLE);
                    loadVehicleData();
                } else {
                    llVehicleContent.setVisibility(View.GONE);
                }

                break;
        }

    }


    /**
     * Load List Count
     */
    private void loadlistCount() {

        TextView tvPersonDetailsCount = stops_detail.findViewById(R.id.tvPersonDetailsCount);
        TextView tvStopDetailsCount = stops_detail.findViewById(R.id.tvStopDetailsCount);
        TextView tvVehicleDetailsCount = stops_detail.findViewById(R.id.tvVehicleDetailsCount);

        if (flowType == GenericConstant.TYPE_PERSON) {
            tvPersonDetailsCount.setVisibility(View.GONE);

        }
        if (aSearchPersonListModel != null && aSearchPersonListModel.size() > 0) {
            tvPersonDetailsCount.setText(String.valueOf(aSearchPersonListModel.size()));
        }
        if (aSearchStopListModel != null && aSearchStopListModel.size() > 0) {
            tvStopDetailsCount.setText(String.valueOf(aSearchStopListModel.size()));
        }
        if (aSearchVehicleListModel != null && aSearchVehicleListModel.size() > 0) {
            tvVehicleDetailsCount.setText(String.valueOf(aSearchVehicleListModel.size()));
        }
    }

    /**
     * Load Stops details JSON
     */
    public void loadStopJSONDetails(String fileName, int type) {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);

                if (flowType == GenericConstant.TYPE_PERSON) {
                    PersonStopDetailResponse personStopDetailResponse = (PersonStopDetailResponse) JsonUtil.getInstance().toModel(strJson, PersonStopDetailResponse.class);
                    if (personStopDetailResponse != null) {
                        aSearchAddressListModel = personStopDetailResponse.getSearchaddresslist();
                        aSearchPersonListModel = personStopDetailResponse.getSearchpersonlist();
                        aSearchStopListModel = personStopDetailResponse.getSearchstoplist();
                        aSearchVehicleListModel = personStopDetailResponse.getSearchvehiclelist();
                    }
                } else if (flowType == GenericConstant.TYPE_ADDRESS) {
                    AddressStopDetailsResponse addressStopDetailsResponse = (AddressStopDetailsResponse) JsonUtil.getInstance().toModel(strJson, AddressStopDetailsResponse.class);
                    if (addressStopDetailsResponse != null) {
                        aSearchAddressListModel = addressStopDetailsResponse.getSearchaddresslist();
                        aSearchPersonListModel = addressStopDetailsResponse.getSearchpersonlist();
                        aSearchStopListModel = addressStopDetailsResponse.getSearchstoplist();
                        aSearchVehicleListModel = addressStopDetailsResponse.getSearchvehiclelist();

                    }
                } else if (flowType == GenericConstant.TYPE_VEHICLE) {
                    VehicleStopDetailsResponse vehicleStopDetailsResponse = (VehicleStopDetailsResponse) JsonUtil.getInstance().toModel(strJson, VehicleStopDetailsResponse.class);
                    if (vehicleStopDetailsResponse != null) {
                        aSearchAddressListModel = vehicleStopDetailsResponse.getSearchaddresslist();
                        aSearchPersonListModel = vehicleStopDetailsResponse.getSearchpersonlist();
                        aSearchStopListModel = vehicleStopDetailsResponse.getSearchstoplist();
                        aSearchVehicleListModel = vehicleStopDetailsResponse.getSearchvehiclelist();

                    }
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (aSearchPersonListModel != null) {
                        loadlistCount();
                        if (flowType == GenericConstant.TYPE_ADDRESS) {

                            llAddressDetails.setVisibility(View.VISIBLE);
                            llAddressDetailsContent.setVisibility(View.VISIBLE);
                            loadAddressData();

                        } else if (flowType == GenericConstant.TYPE_PERSON) {
                            llPersonDetailsContent.setVisibility(View.VISIBLE);
                            loadPersonData();

                        } else if (flowType == GenericConstant.TYPE_VEHICLE) {
                            llVehicle.setVisibility(View.VISIBLE);
                            llVehicleContent.setVisibility(View.VISIBLE);
                            llVehicleDetails.setVisibility(View.GONE);
                            loadVehicleData();
                        }
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), STOPSDetailDialogFragment.class, "loadStopJSONDetails");
            }
        }).start();
    }
}
