package hcl.policing.digitalpolicingplatform.fragments.process.fds.vehicle.pnc;

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
import hcl.policing.digitalpolicingplatform.listeners.dialog.ManuallyClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.PersonSelectionListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.investigation.athena.EnquiryLogsModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.pnc.VehiclePNCDetailResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.pnc.VehiclePNCReportResponse;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class VehiclePNCDetailDialogFragment extends DialogFragment implements View.OnClickListener {

    private Context mContext;
    private ManuallyClickListener manuallyClickListener;
    private PersonSelectionListener personSelectionListener;
    private LayoutInflater layoutInflater;
    private int flowType;
    private TextView tvVehicleReportCount, tvInsuranceReportCount;
    private LinearLayout llDetails,llHeader, llVehicleDetails, llVehicleReportContent, llInsuranceReportContent;
    private LinearLayout pnc_detail;
    private VehiclePNCDetailResponse.MotorvehicleBean mMotorvehicleBean = null;
    private Dialog mProgressDialog;
    private ArrayList<EnquiryLogsModel> aEnquiryLogsModel;
    private String vehicleAddress;
    private boolean isPopulate;

    public static VehiclePNCDetailDialogFragment newInstance(int type,boolean isPopulate) {

        VehiclePNCDetailDialogFragment frag = new VehiclePNCDetailDialogFragment();
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

        View rootView = inflater.inflate(R.layout.vehicle_pnc_detail_fragment, container, false);
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
        pnc_detail = view.findViewById(R.id.pnc_detail);
        RelativeLayout rlVehicleDetails = pnc_detail.findViewById(R.id.rlVehicleDetails);
        llVehicleDetails = pnc_detail.findViewById(R.id.llVehicleDetails);
        tvVehicleReportCount = pnc_detail.findViewById(R.id.tvVehicleReportCount);
        tvInsuranceReportCount = pnc_detail.findViewById(R.id.tvInsuranceReportCount);

        isPopulateView();
        loadHeaderLayout();

        if (flowType == GenericConstant.TYPE_VEHICLE) {
            pnc_detail.setVisibility(View.VISIBLE);
            loadVehiclePNCJSON(GenericConstant.VEHICLE_PNC_DETAILS_JSON);
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

        if (flowType == GenericConstant.TYPE_VEHICLE) {
            tvHeader.setText(getString(R.string.pnc_vehicle_header));

        }

        ivBack.setOnClickListener(this);
    }

    /**
     * Load the PNC Vehicle Details
     */
    private void loadVehicleData() {

        RelativeLayout rlVehicleReport = pnc_detail.findViewById(R.id.rlVehicleReport);
        RelativeLayout rlInsuranceReport = pnc_detail.findViewById(R.id.rlInsuranceReport);
        llVehicleReportContent = pnc_detail.findViewById(R.id.llVehicleReportContent);
        llInsuranceReportContent = pnc_detail.findViewById(R.id.llInsuranceReportContent);

        TextView tvVRMValue = pnc_detail.findViewById(R.id.tvVRMValue);
        TextView tvLostStolenValue = pnc_detail.findViewById(R.id.tvLostStolenValue);
        TextView tvRegisteredValue = pnc_detail.findViewById(R.id.tvRegisteredValue);
        TextView tvOwnerValue = pnc_detail.findViewById(R.id.tvOwnerValue);
        TextView tvAddressValue = pnc_detail.findViewById(R.id.tvAddressValue);
        TextView tvVehicleTypeValue = pnc_detail.findViewById(R.id.tvVehicleTypeValue);
        TextView tvMakeValue = pnc_detail.findViewById(R.id.tvMakeValue);
        TextView tvModelValue = pnc_detail.findViewById(R.id.tvModelValue);
        TextView tvPurchaseDateValue = pnc_detail.findViewById(R.id.tvPurchaseDateValue);
        TextView tvColorValue = pnc_detail.findViewById(R.id.tvColorValue);
        TextView tvVINValue = pnc_detail.findViewById(R.id.tvVINValue);
        TextView tvEngineNoValue = pnc_detail.findViewById(R.id.tvEngineNoValue);
        TextView tvMOTExpiryValue = pnc_detail.findViewById(R.id.tvMOTExpiryValue);
        TextView tvNotifyValue = pnc_detail.findViewById(R.id.tvNotifyValue);
        TextView tvVRolValue = pnc_detail.findViewById(R.id.tvVRolValue);
        TextView tvSummaryHazardValue = pnc_detail.findViewById(R.id.tvSummaryHazardValue);
        TextView tvCCValue = pnc_detail.findViewById(R.id.tvCCValue);
        TextView tvMarkerValue = pnc_detail.findViewById(R.id.tvMarkerValue);

        if (mMotorvehicleBean != null) {
            tvVRMValue.setText(mMotorvehicleBean.getNumberplate());
            tvLostStolenValue.setText(mMotorvehicleBean.getStolen());
            tvRegisteredValue.setText(mMotorvehicleBean.getRegistered());
            tvOwnerValue.setText(mMotorvehicleBean.getOwner());

            vehicleAddress = mMotorvehicleBean.getAddress();
            tvAddressValue.setText(vehicleAddress);
            tvVehicleTypeValue.setText(mMotorvehicleBean.getType());
            tvMakeValue.setText(mMotorvehicleBean.getMake());
            tvModelValue.setText(mMotorvehicleBean.getModel());
            tvPurchaseDateValue.setText(mMotorvehicleBean.getPurchasedate());
            tvColorValue.setText(mMotorvehicleBean.getColour());
            tvVINValue.setText(mMotorvehicleBean.getVin());
            tvEngineNoValue.setText(mMotorvehicleBean.getEnginenumber());
            tvMOTExpiryValue.setText(mMotorvehicleBean.getMotexpiry());
            tvNotifyValue.setText(mMotorvehicleBean.getKeepernotify());
            tvVRolValue.setText(mMotorvehicleBean.getVrolit());
            tvSummaryHazardValue.setText(mMotorvehicleBean.getSummaryhazards());
            tvCCValue.setText(mMotorvehicleBean.getGrossweight());
            tvMarkerValue.setText(mMotorvehicleBean.getMarkers());

        } else {
            BasicMethodsUtil.getInstance().showToast(getActivity(), getString(R.string.no_details_available));
        }

        tvAddressValue.setOnClickListener(this);
        rlVehicleReport.setOnClickListener(this);
        rlInsuranceReport.setOnClickListener(this);
    }


    /**
     * load Vehicle Reports
     */
    private void loadVehicleReports(List<VehiclePNCReportResponse.VehicleReportsBean> vehicleReportsBeans) {

        llVehicleReportContent.removeAllViews();
        if (vehicleReportsBeans != null && vehicleReportsBeans.size() > 0) {
            for (int i = 0; i < vehicleReportsBeans.size(); i++) {

                VehiclePNCReportResponse.VehicleReportsBean mVehicleReportsBean = vehicleReportsBeans.get(i);

                View view = layoutInflater.inflate(R.layout.pnc_vehicle_report_item, null);
                TextView tvTypeValue = view.findViewById(R.id.tvTypeValue);
                TextView tvStatusValue = view.findViewById(R.id.tvStatusValue);
                TextView tvOwnerValue = view.findViewById(R.id.tvOwnerValue);
                TextView tvReferenceValue = view.findViewById(R.id.tvReferenceValue);
                TextView tvIncDateValue = view.findViewById(R.id.tvIncDateValue);
                TextView tvCreatedDateValue = view.findViewById(R.id.tvCreatedDateValue);
                TextView tvTextValue = view.findViewById(R.id.tvTextValue);

                tvTypeValue.setText(mVehicleReportsBean.getType());
                tvStatusValue.setText(mVehicleReportsBean.getStatus());
                tvOwnerValue.setText(mVehicleReportsBean.getOwner());
                tvReferenceValue.setText(mVehicleReportsBean.getReference());
                tvIncDateValue.setText(mVehicleReportsBean.getIncdate());
                tvCreatedDateValue.setText(mVehicleReportsBean.getCreateddatetime());
                tvTextValue.setText(mVehicleReportsBean.getText());

                llVehicleReportContent.addView(view);
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
                FDSMapActivity.callMapActivity(mContext, vehicleAddress);
                break;

            case R.id.rlVehicleDetails:

                if (llVehicleDetails.getVisibility() != View.VISIBLE) {
                    llVehicleDetails.setVisibility(View.VISIBLE);
                    loadVehicleData();
                } else {
                    llVehicleDetails.setVisibility(View.GONE);
                }

                break;

            case R.id.rlVehicleReport:

                if (llVehicleReportContent.getVisibility() != View.VISIBLE) {
                    llVehicleReportContent.setVisibility(View.VISIBLE);

                    loadVehicleReportJSON();

                } else {
                    llVehicleReportContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlInsuranceReport:

                if (llInsuranceReportContent.getVisibility() != View.VISIBLE) {
                    llInsuranceReportContent.setVisibility(View.VISIBLE);

                } else {
                    llInsuranceReportContent.setVisibility(View.GONE);
                }

                break;
        }

    }


    /**
     * Load Vehicle PNC JSON
     */
    public void loadVehiclePNCJSON(String fileName) {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);
                VehiclePNCDetailResponse pncResponse = (VehiclePNCDetailResponse) JsonUtil.getInstance().toModel(strJson, VehiclePNCDetailResponse.class);
                if (pncResponse != null) {
                    mMotorvehicleBean = pncResponse.getMotorvehicle();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (mMotorvehicleBean != null) {
                        tvVehicleReportCount.setText(mMotorvehicleBean.getTotalreportcount());
                        tvInsuranceReportCount.setText(mMotorvehicleBean.getTotalinsurancecount());
                        llVehicleDetails.setVisibility(View.VISIBLE);
                        loadVehicleData();
                    }
                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), VehiclePNCDetailDialogFragment.class, "loadVehiclePNCJSON");
            }
        }).start();
    }


    /**
     * Load PNC Vehicle Report
     */
    public void loadVehicleReportJSON() {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), GenericConstant.VEHICLE_PNC_VEHICLE_REPORT_JSON);
                VehiclePNCReportResponse pncResponse = (VehiclePNCReportResponse) JsonUtil.getInstance().toModel(strJson, VehiclePNCReportResponse.class);
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (pncResponse != null) {
                        List<VehiclePNCReportResponse.VehicleReportsBean> aVehicleReportsBean = pncResponse.getVehiclereports();
                        loadVehicleReports(aVehicleReportsBean);
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }
                });
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), VehiclePNCDetailDialogFragment.class, "loadVehicleReportJSON");
            }
        }).start();
    }

}

