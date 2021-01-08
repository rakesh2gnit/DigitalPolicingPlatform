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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.AthenaLinkedLocationDetailsModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.MarkerModel;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class LinkedLocationDetailsDialogFragment extends DialogFragment implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private int listType;
    private ArrayList<List<?>> listDetails;
    private LinearLayout llDetails, llHeader, address_detail, llFurtherInfoDetails;
    private RelativeLayout rlFurtherInfo;
    private TextView tvFlowName;
    private Dialog mProgressDialog;
    private AthenaLinkedLocationDetailsModel.LocationdetailresponseBean locationdetailresponseBean;
    private ArrayList<MarkerModel> aMarkersBean;
    private boolean isPopulate;

    public static LinkedLocationDetailsDialogFragment newInstance(int type, ArrayList<List<?>> list, boolean isPopulate) {

        LinkedLocationDetailsDialogFragment frag = new LinkedLocationDetailsDialogFragment();
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

        View rootView = inflater.inflate(R.layout.athena_linked_location_details_fragment, container, false);
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


    /**
     * Load init view
     *
     * @param view
     */
    private void initView(View view) {

        llDetails = view.findViewById(R.id.llDetails);
        llHeader = view.findViewById(R.id.llHeader);
        address_detail = view.findViewById(R.id.address_detail);
        tvFlowName = view.findViewById(R.id.tvFlowName);

        isPopulateView();
        loadHeaderLayout();
        loadAthenaLocationDetails(GenericConstant.ATHENA_LINKED_LOCATION_DETAILS_JSON);
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

        tvHeader.setText(getString(R.string.linked_location_header));
        String title = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.LINKED_HEADER, "");
        tvFlowName.setText(title);

        ivBack.setOnClickListener(this);
    }


    /**
     * Load the location Details
     */
    private void loadLocationData() {

        llFurtherInfoDetails = address_detail.findViewById(R.id.llAdditionalInfoDetails);
        rlFurtherInfo = address_detail.findViewById(R.id.rlFurtherInfo);

        TextView tvAddresFlowName = address_detail.findViewById(R.id.tvAddresFlowName);
        View viewFlowName = address_detail.findViewById(R.id.viewFlowName);
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
        LinearLayout llWarning = address_detail.findViewById(R.id.llWarning);

        rlFurtherInfo.setOnClickListener(this);

        if (locationdetailresponseBean != null) {
            tvPremisesNameValue.setText(locationdetailresponseBean.getPremisesname());
            tvSubPremisesNameValue.setText(locationdetailresponseBean.getSubpremisesname());
            tvPremisesNoValue.setText(locationdetailresponseBean.getPremisesno());
            tvFlatNoValue.setText(locationdetailresponseBean.getFlatno());
            tvSubNOValue.setText(locationdetailresponseBean.getSubno());
            tvStreetNameValue.setText(locationdetailresponseBean.getStreetname());
            tvLocalityValue.setText(locationdetailresponseBean.getLocality());
            tvTownValue.setText(locationdetailresponseBean.getTown());
            tvCountyValue.setText(locationdetailresponseBean.getCounty());
            tvPostCodeValue.setText(locationdetailresponseBean.getPostcode());
            tvPOBoxValue.setText(locationdetailresponseBean.getPobox());
            tvCountryValue.setText(locationdetailresponseBean.getCountry());
            tvForceValue.setText(locationdetailresponseBean.getForce());
            tvPremisesTypeValue.setText(locationdetailresponseBean.getPremisestype());

            aMarkersBean = (ArrayList<MarkerModel>) locationdetailresponseBean.getMarkers();

            if (aMarkersBean != null && aMarkersBean.size() > 0) {
                for (int i = 0; i < aMarkersBean.size(); i++) {

                    View view = layoutInflater.inflate(R.layout.vehicle_details_warning_items, null);

                    LinearLayout llHeader = view.findViewById(R.id.llHeader);
                    TextView tvLabel = view.findViewById(R.id.tvLabel);
                    TextView tvLabelValue = view.findViewById(R.id.tvLabelValue);
                    TextView tvLabelDate = view.findViewById(R.id.tvLabelDate);
                    TextView tvLabelDescription = view.findViewById(R.id.tvLabelDescription);

                    tvLabel.setText(aMarkersBean.get(i).getMarkertype());
                    tvLabelValue.setText(aMarkersBean.get(i).getMarkervalue());
                    tvLabelDate.setText("(from " + aMarkersBean.get(i).getFromdate() + ")");
                    tvLabelDescription.setText(aMarkersBean.get(i).getDescription());

                    llHeader.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            loadLinkedWarningDialog(GenericConstant.TYPE_MARKER);
                        }
                    });

                    llWarning.addView(view);
                }
            }
        }
    }


    /**
     * Load Intelligence Dialog
     */
    public void loadLinkedWarningDialog(int type) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.LINKED_WARNING_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        LinkedWarningListDialogFragment linkedLocationDetailsDialogFragment = LinkedWarningListDialogFragment.newInstance(type, aMarkersBean, isPopulate);
        linkedLocationDetailsDialogFragment.show(ft, GenericConstant.LINKED_WARNING_DIALOG);
    }

    /**
     * Load the further info
     */
    private void loadAdditionalInfoData() {

        if (locationdetailresponseBean != null) {

            TextView tvBCUValue = address_detail.findViewById(R.id.tvBCUValue);
            TextView tvDistrictValue = address_detail.findViewById(R.id.tvDistrictValue);
            TextView tvWardValue = address_detail.findViewById(R.id.tvWardValue);
            TextView tvEastingValue = address_detail.findViewById(R.id.tvEastingValue);
            TextView tvNorthingValue = address_detail.findViewById(R.id.tvNorthingValue);

            tvBCUValue.setText(locationdetailresponseBean.getBcu());
            tvDistrictValue.setText(locationdetailresponseBean.getDistrict());
            tvWardValue.setText(locationdetailresponseBean.getWard());
            tvEastingValue.setText(locationdetailresponseBean.getEasting());
            tvNorthingValue.setText(locationdetailresponseBean.getNorthing());

        }
    }

    /**
     * Load Location JSON
     */
    public void loadAthenaLocationDetails(String fileName) {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {

                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);
                AthenaLinkedLocationDetailsModel athenaResponse = (AthenaLinkedLocationDetailsModel) JsonUtil.getInstance().toModel(strJson, AthenaLinkedLocationDetailsModel.class);
                if (athenaResponse != null) {
                    locationdetailresponseBean = athenaResponse.getLocationdetailresponse();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (locationdetailresponseBean != null) {
                        loadLocationData();
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), LinkedLocationDetailsDialogFragment.class, "loadAthenaPersonDetails");
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

            case R.id.rlFurtherInfo:

                if (llFurtherInfoDetails.getVisibility() != View.VISIBLE) {
                    llFurtherInfoDetails.setVisibility(View.VISIBLE);
                    loadAdditionalInfoData();
                } else {
                    llFurtherInfoDetails.setVisibility(View.GONE);
                }

                break;
        }
    }
}
