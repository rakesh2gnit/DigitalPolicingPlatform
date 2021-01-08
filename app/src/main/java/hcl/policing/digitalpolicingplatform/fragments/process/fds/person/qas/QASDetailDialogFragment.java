package hcl.policing.digitalpolicingplatform.fragments.process.fds.person.qas;

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

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.listeners.dialog.ManuallyClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.PersonSelectionListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.qas.AddressQASDetailResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.qas.AddressdetailsModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.qas.CitizensModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.qas.PersonQASDetailResponse;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;
import hcl.policing.digitalpolicingplatform.utils.LocationUtil;

public class QASDetailDialogFragment extends DialogFragment implements View.OnClickListener {

    private Context mContext;
    private LinearLayout llDetails,llHeader, qas_detail, llPersonDetails, llAddressDetails, llPersonDetailsContent, llAddressDetailsContent,
            llOccupantsContent;
    private Dialog mProgressDialog;
    private ManuallyClickListener manuallyClickListener;
    private PersonSelectionListener personSelectionListener;
    private int flowType;
    private ArrayList<CitizensModel> aCitizensModel;
    private ArrayList<AddressdetailsModel> aAddressdetailsModel;
    private LayoutInflater layoutInflater;
    private boolean isPopulate;

    public static QASDetailDialogFragment newInstance(int type,boolean isPopulate) {

        QASDetailDialogFragment frag = new QASDetailDialogFragment();
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

        View rootView = inflater.inflate(R.layout.qas_detail_fragment, container, false);
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
        qas_detail = view.findViewById(R.id.qas_detail);

        llPersonDetails = qas_detail.findViewById(R.id.llPersonDetails);
        llPersonDetailsContent = qas_detail.findViewById(R.id.llPersonDetailsContent);
        llAddressDetails = qas_detail.findViewById(R.id.llAddressDetails);
        llAddressDetailsContent = qas_detail.findViewById(R.id.llAddressDetailsContent);
        llOccupantsContent = qas_detail.findViewById(R.id.llOccupantsContent);

        RelativeLayout rlPersonDetails = qas_detail.findViewById(R.id.rlPersonDetails);
        RelativeLayout rlAddressDetails = qas_detail.findViewById(R.id.rlAddressDetails);
        RelativeLayout rlOccupants = qas_detail.findViewById(R.id.rlOccupants);

        isPopulateView();
        loadHeaderLayout();

        if (flowType == GenericConstant.TYPE_PERSON) {
            loadPersonQASJSONDetails();
        } else if (flowType == GenericConstant.TYPE_ADDRESS) {
            loadAddressQASJSONDetails();
        }
        rlPersonDetails.setOnClickListener(this);
        rlOccupants.setOnClickListener(this);
        rlAddressDetails.setOnClickListener(this);
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
            tvHeader.setText(getString(R.string.qas_nominal_header));

        } else if (flowType == GenericConstant.TYPE_ADDRESS) {
            tvHeader.setText(getString(R.string.qas_address_header));

        }
        ivBack.setOnClickListener(this);
    }


    /**
     * Load the Person Details
     */
    private void loadPersonData() {

        llPersonDetailsContent.removeAllViews();
        if (aCitizensModel != null && aCitizensModel.size() > 0) {

            CitizensModel mCitizensModel = aCitizensModel.get(0);
            View view = layoutInflater.inflate(R.layout.qas_person_details_items, null);

            TextView tvNameValue = view.findViewById(R.id.tvNameValue);
            TextView tvBuildingValue = view.findViewById(R.id.tvBuildingValue);
            TextView tvHouseNoValue = view.findViewById(R.id.tvHouseNoValue);
            TextView tvStreetValue = view.findViewById(R.id.tvStreetValue);
            TextView tvLocalityValue = view.findViewById(R.id.tvLocalityValue);
            TextView tvTownValue = view.findViewById(R.id.tvTownValue);
            TextView tvCountyValue = view.findViewById(R.id.tvCountyValue);
            TextView tvPostcodeValue = view.findViewById(R.id.tvPostcodeValue);
            ImageView ivNavigation = view.findViewById(R.id.ivNavigation);

            tvNameValue.setText(mCitizensModel.getDisplayname());
            AddressdetailsModel addressdetailsModel = mCitizensModel.getAddressdetails();
            StringBuilder sbAddress = new StringBuilder();
            if (addressdetailsModel != null) {
                sbAddress.append(addressdetailsModel.getHouseno()).append(" ").append(addressdetailsModel.getBuilding())
                        .append(",").append(addressdetailsModel.getStreet()).append(",").append(addressdetailsModel.getLocation())
                        .append(",").append(addressdetailsModel.getTown()).append(addressdetailsModel.getCounty()).append(",")
                        .append(addressdetailsModel.getPostcode());

                tvBuildingValue.setText(addressdetailsModel.getBuilding());
                tvHouseNoValue.setText(addressdetailsModel.getHouseno());
                tvStreetValue.setText(addressdetailsModel.getStreet());
                tvLocalityValue.setText(addressdetailsModel.getLocation());
                tvTownValue.setText(addressdetailsModel.getTown());
                tvCountyValue.setText(addressdetailsModel.getCounty());
                tvPostcodeValue.setText(addressdetailsModel.getPostcode());
            }
            ivNavigation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LocationUtil.getInstance().navigateTo(mContext, sbAddress.toString());

                }
            });

            llPersonDetailsContent.addView(view);
        }
    }

    /**
     * Load the Occupants Details
     */
    private void loadOccupantsData() {

        llOccupantsContent.removeAllViews();
        if (aCitizensModel != null && aCitizensModel.size() > 0) {
            for (int i = 0; i < aCitizensModel.size(); i++) {

                CitizensModel mCitizensModel = aCitizensModel.get(i);

                View view = layoutInflater.inflate(R.layout.nflms_weapon_details_items, null);
                TextView tvActionDescValue = view.findViewById(R.id.tvActionDescValue);
                TextView tvCaliberDescValue = view.findViewById(R.id.tvCaliberDescValue);
                TextView tvCertificateNoValue = view.findViewById(R.id.tvCertificateNoValue);
                TextView tvCertificateTypeValue = view.findViewById(R.id.tvCertificateTypeValue);
                TextView tvExpiryDateValue = view.findViewById(R.id.tvExpiryDateValue);
                TextView tvMdfDescValue = view.findViewById(R.id.tvMdfDescValue);
                TextView tvSecurityTypeDescValue = view.findViewById(R.id.tvSecurityTypeDescValue);

//                tvActionDescValue.setText(mCitizensModel.getActiondescription());
//                tvCaliberDescValue.setText(mCitizensModel.getCalliberdescription());
//                tvCertificateNoValue.setText(mCitizensModel.getCertificatenumber());
//                tvCertificateTypeValue.setText(mCitizensModel.getCertificatetype());
//                tvExpiryDateValue.setText(mCitizensModel.getExpirydate());
//                tvMdfDescValue.setText(mCitizensModel.getManufacturerdescription());
//                tvSecurityTypeDescValue.setText(mCitizensModel.getSecuritytypedescription());
//                tvSerialNoValue.setText(mCitizensModel.getSerialnumber());

                llOccupantsContent.addView(view);
            }
        }
    }

    /**
     * Load the Address Details
     */
    private void loadAddressData() {

        llAddressDetailsContent.removeAllViews();
        if (aAddressdetailsModel != null && aAddressdetailsModel.size() > 0) {
            for (int i = 0; i < aAddressdetailsModel.size(); i++) {

                AddressdetailsModel mAddressdetailsModel = aAddressdetailsModel.get(i);

                View view = layoutInflater.inflate(R.layout.qas_address_details_items, null);

                TextView tvBuildingValue = view.findViewById(R.id.tvBuildingValue);
                TextView tvHouseNoValue = view.findViewById(R.id.tvHouseNoValue);
                TextView tvStreetValue = view.findViewById(R.id.tvStreetValue);
                TextView tvLocalityValue = view.findViewById(R.id.tvLocalityValue);
                TextView tvTownValue = view.findViewById(R.id.tvTownValue);
                TextView tvCountyValue = view.findViewById(R.id.tvCountyValue);
                TextView tvPostcodeValue = view.findViewById(R.id.tvPostcodeValue);
                TextView tvCountryValue = view.findViewById(R.id.tvCountryValue);
                ImageView ivNavigation = view.findViewById(R.id.ivNavigation);

                StringBuilder sbAddress = new StringBuilder();
                if (mAddressdetailsModel != null) {
                    sbAddress.append(mAddressdetailsModel.getHouseno()).append(" ").append(mAddressdetailsModel.getBuilding())
                            .append(",").append(mAddressdetailsModel.getStreet())
                            .append(",").append(mAddressdetailsModel.getLocation())
                            .append(",").append(mAddressdetailsModel.getTown())
                            .append(",").append(mAddressdetailsModel.getCounty())
                            .append(",").append(mAddressdetailsModel.getCountry())
                            .append(",").append(mAddressdetailsModel.getPostcode());

                    tvBuildingValue.setText(mAddressdetailsModel.getBuilding());
                    tvHouseNoValue.setText(mAddressdetailsModel.getHouseno());
                    tvStreetValue.setText(mAddressdetailsModel.getStreet());
                    tvLocalityValue.setText(mAddressdetailsModel.getLocation());
                    tvTownValue.setText(mAddressdetailsModel.getTown());
                    tvCountyValue.setText(mAddressdetailsModel.getCounty());
                    tvPostcodeValue.setText(mAddressdetailsModel.getPostcode());
                    tvCountryValue.setText(mAddressdetailsModel.getCountry());
                }
                ivNavigation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LocationUtil.getInstance().navigateTo(mContext, sbAddress.toString());
                    }
                });

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
            case R.id.rlOccupants:

                if (llOccupantsContent.getVisibility() != View.VISIBLE) {
                    llOccupantsContent.setVisibility(View.VISIBLE);
                    loadOccupantsData();
                } else {
                    llOccupantsContent.setVisibility(View.GONE);
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
        }

    }


    /**
     * Load List Count
     */
    private void loadlistCount() {

        TextView tvOccupantsCount = qas_detail.findViewById(R.id.tvOccupantsCount);
        if (aCitizensModel != null && aCitizensModel.size() > 0) {
            tvOccupantsCount.setText(String.valueOf(aCitizensModel.size()));
        }
    }

    /**
     * Load Person QAS JSON
     */
    private void loadPersonQASJSONDetails() {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), GenericConstant.PERSON_QAS_DETAILS_JSON);
                PersonQASDetailResponse personQASDetailResponse = (PersonQASDetailResponse) JsonUtil.getInstance().toModel(strJson, PersonQASDetailResponse.class);
                if (personQASDetailResponse != null) {
                    aCitizensModel = (ArrayList<CitizensModel>) personQASDetailResponse.getCitizens();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (aCitizensModel != null) {
                        llPersonDetails.setVisibility(View.VISIBLE);
                        llPersonDetailsContent.setVisibility(View.VISIBLE);
                        loadlistCount();
                        loadPersonData();
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), QASDetailDialogFragment.class, "loadPersonQASJSONDetails");
            }
        }).start();
    }


    /**
     * Load Address QAS JSON
     */
    private void loadAddressQASJSONDetails() {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), GenericConstant.ADDRESS_QAS_DETAILS_JSON);
                AddressQASDetailResponse addressQASDetailResponse = (AddressQASDetailResponse) JsonUtil.getInstance().toModel(strJson, AddressQASDetailResponse.class);
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (addressQASDetailResponse != null) {
                        aAddressdetailsModel = (ArrayList<AddressdetailsModel>) addressQASDetailResponse.getAddresses();
                        aCitizensModel = (ArrayList<CitizensModel>) addressQASDetailResponse.getCitizens();
                        llAddressDetails.setVisibility(View.VISIBLE);
                        llAddressDetailsContent.setVisibility(View.VISIBLE);
                        loadlistCount();
                        loadAddressData();
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }
                });
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), QASDetailDialogFragment.class, "loadAddressQASJSONDetails");
            }
        }).start();
    }
}
