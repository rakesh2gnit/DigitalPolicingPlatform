package hcl.policing.digitalpolicingplatform.fragments.process.fds.person.athena;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
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
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.AthenaLinkedVehicleDetailsModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.WarningsModel;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.Base64ToBitmapUtil;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class LinkedVehicleDetailsDialogFragment extends DialogFragment implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private int listType;
    private ArrayList<List<?>> listDetails;
    private LinearLayout llDetails, llHeader, llFurtherInfoDetails;
    private RelativeLayout rlImage, rlFurtherInfo;
    private LinearLayout vehicle_detail;
    private HorizontalScrollView hScrollViewImage;
    private Dialog mProgressDialog;
    private TextView tvFlowName;
    private AthenaLinkedVehicleDetailsModel.VehicledetailresponseBean vehicledetailresponseBean;
    private ArrayList<AthenaLinkedVehicleDetailsModel.PhotosBean> aPhotosBean;
    private boolean isPopulate;

    public static LinkedVehicleDetailsDialogFragment newInstance(int type, ArrayList<List<?>> list, boolean isPopulate) {

        LinkedVehicleDetailsDialogFragment frag = new LinkedVehicleDetailsDialogFragment();
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

        View rootView = inflater.inflate(R.layout.athena_linked_vehicle_detail_fragment, container, false);
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
     * Initialize the view
     *
     * @param view
     */
    private void initView(View view) {

        llDetails = view.findViewById(R.id.llDetails);
        llHeader = view.findViewById(R.id.llHeader);
        vehicle_detail = view.findViewById(R.id.vehicle_detail);
        hScrollViewImage = view.findViewById(R.id.hScrollViewImage);
        tvFlowName = view.findViewById(R.id.tvFlowName);

        isPopulateView();
        loadHeaderLayout();
        loadAthenaLinkedVehicleDetails(GenericConstant.ATHENA_LINKED_VEHICLE_DETAILS_JSON);
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
        tvHeader.setText(getString(R.string.linked_vehicle_header));

        String title = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.LINKED_HEADER, "");
        tvFlowName.setText(title);
        ivBack.setOnClickListener(this);
    }


    /**
     * Load the Person Details
     */
    private void loadVehicleData() {

        layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ArrayList<WarningsModel> aWarningsBean = (ArrayList<WarningsModel>) vehicledetailresponseBean.getWarnings();
        aPhotosBean = (ArrayList<AthenaLinkedVehicleDetailsModel.PhotosBean>) vehicledetailresponseBean.getPhotos();

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

        tvVRMValue.setText(vehicledetailresponseBean.getRegistrationnumber());
        tvMakeValue.setText(vehicledetailresponseBean.getMake());
        tvModelValue.setText(vehicledetailresponseBean.getModel());
        tvFuelTypeValue.setText(vehicledetailresponseBean.getFueltype());
        tvCategoryValue.setText(vehicledetailresponseBean.getCategory());
        tvForeignVehicleValue.setText(vehicledetailresponseBean.getForeignvehicle());
        tvBodyTypeValue.setText(vehicledetailresponseBean.getType());
        tvColorValue.setText(vehicledetailresponseBean.getVehiclecolour());
        tvANPRReasonValue.setText(vehicledetailresponseBean.getAnprreason());
        tvCategoryValue.setText(vehicledetailresponseBean.getCategory());
        tvForeignVehicleValue.setText(vehicledetailresponseBean.getForeignvehicle());
        tvBodyTypeValue.setText(vehicledetailresponseBean.getType());

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

        if (aPhotosBean != null && aPhotosBean.size() > 0) {
            LinearLayout llImageContent = vehicle_detail.findViewById(R.id.llImageContent);
            llImageContent.setVisibility(View.VISIBLE);
            llImageContent.removeAllViews();
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
    private void loadVehicleAdditionalInfo() {

        if (vehicledetailresponseBean != null) {

            TextView tvCharacteristicsValue = vehicle_detail.findViewById(R.id.tvCharacteristicsValue);
            TextView tvCountryOfRegistrationValue = vehicle_detail.findViewById(R.id.tvCountryOfRegistrationValue);
            TextView tvRemarksValue = vehicle_detail.findViewById(R.id.tvRemarksValue);
            TextView tvForeignVehicleValue = vehicle_detail.findViewById(R.id.tvForeignVehicleValue);
            TextView tvEngineNoValue = vehicle_detail.findViewById(R.id.tvEngineNoValue);
            TextView tvChassisNoValue = vehicle_detail.findViewById(R.id.tvChassisNoValue);

            tvCharacteristicsValue.setText(vehicledetailresponseBean.getIdentifyingcharacteristics());
            tvCountryOfRegistrationValue.setText(vehicledetailresponseBean.getRegistrationcountry());
            tvRemarksValue.setText(vehicledetailresponseBean.getRemarks());
            tvForeignVehicleValue.setText(vehicledetailresponseBean.getForeignvehicle());
            tvEngineNoValue.setText(vehicledetailresponseBean.getEnginenumber());
            tvChassisNoValue.setText(vehicledetailresponseBean.getChassisnumber());
        }
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
     * Load Person JSON
     */
    public void loadAthenaLinkedVehicleDetails(String fileName) {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {

                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);
                AthenaLinkedVehicleDetailsModel athenaResponse = (AthenaLinkedVehicleDetailsModel) JsonUtil.getInstance().toModel(strJson, AthenaLinkedVehicleDetailsModel.class);
                if (athenaResponse != null) {
                    vehicledetailresponseBean = athenaResponse.getVehicledetailresponse();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (vehicledetailresponseBean != null) {
                        loadVehicleData();
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), LinkedVehicleDetailsDialogFragment.class, "loadAthenaPersonDetails");
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
                    loadVehicleAdditionalInfo();
                } else {
                    llFurtherInfoDetails.setVisibility(View.GONE);
                }

                break;
        }
    }

}
