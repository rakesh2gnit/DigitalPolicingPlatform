package hcl.policing.digitalpolicingplatform.fragments.process.fds.person.athena;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
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
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.AthenaLinkedPersonDetailsModel;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.Base64ToBitmapUtil;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class LinkedPersonDetailsDialogFragment extends DialogFragment implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private LinearLayout llDetails, llHeader, llFurtherInfoDetails;
    private RelativeLayout rlImage, rlFurtherInfo;
    private LinearLayout person_detail;
    private HorizontalScrollView hScrollViewImage;
    private Dialog mProgressDialog;
    private int listType;
    private ArrayList<List<?>> listDetails;
    private AthenaLinkedPersonDetailsModel.LinkedpersondetailresponseBean linkedpersondetailresponse;
    private ArrayList<AthenaLinkedPersonDetailsModel.PhotosBean> aPhotosBean;
    private boolean isPopulate;

    public static LinkedPersonDetailsDialogFragment newInstance(int type, ArrayList<List<?>> list, boolean isPopulate) {

        LinkedPersonDetailsDialogFragment frag = new LinkedPersonDetailsDialogFragment();
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

        View rootView = inflater.inflate(R.layout.athena_linked_person_detail_fragment, container, false);
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
        person_detail = view.findViewById(R.id.person_detail);

        isPopulateView();
        loadHeaderLayout();
        loadLinkedPersonDetails(GenericConstant.ATHENA_LINKED_PERSON_DETAILS_JSON);

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

        tvHeader.setText(getString(R.string.linked_person_header));
        ivBack.setOnClickListener(this);
    }


    /**
     * Load the Person Details
     */

    /**
     * Load the Person Details
     */
    private void loadPersonData() {

        layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ArrayList<AthenaLinkedPersonDetailsModel.WarningsBean> aWarningsBean = (ArrayList<AthenaLinkedPersonDetailsModel.WarningsBean>) linkedpersondetailresponse.getWarnings();
        aPhotosBean = (ArrayList<AthenaLinkedPersonDetailsModel.PhotosBean>) linkedpersondetailresponse.getPhotos();

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

        tvLPlaceValue.setText(linkedpersondetailresponse.getEthnicity());
        tvDOBPersonValue.setText(linkedpersondetailresponse.getDob());
        tvAddressValue.setText(linkedpersondetailresponse.getLatesthomeaddress());
        tvPersonCertificateValue.setText(linkedpersondetailresponse.getFirarmcertificateholder());
        tvPNCIDValue.setText(linkedpersondetailresponse.getPncId());

        String title = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.LINKED_HEADER, "");
        tvFlowName.setText(title);

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
     * Load Image list
     */

    private void loadPersonImageData() {

        LinearLayout llImageContent = person_detail.findViewById(R.id.llImageContent);
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


    private void loadFurtherInfoData() {

        if (linkedpersondetailresponse != null) {

            TextView tvSurnameValue = llFurtherInfoDetails.findViewById(R.id.tvSurnameValue);
            TextView tvForenameValue = llFurtherInfoDetails.findViewById(R.id.tvForenameValue);
            TextView tvDOBValue = llFurtherInfoDetails.findViewById(R.id.tvDOBValue);
            TextView tvCertificateValue = llFurtherInfoDetails.findViewById(R.id.tvCertificateValue);
            TextView tvLatestHomeAddressValue = llFurtherInfoDetails.findViewById(R.id.tvLatestHomeAddressValue);
            TextView tvPNCFileNameValue = llFurtherInfoDetails.findViewById(R.id.tvPNCFileNameValue);
            TextView tvPNCIdValue = llFurtherInfoDetails.findViewById(R.id.tvPNCIdValue);
            TextView tvGenderValue = llFurtherInfoDetails.findViewById(R.id.tvGenderValue);
            TextView tvAliasDetailValue = llFurtherInfoDetails.findViewById(R.id.tvAliasDetailValue);
            TextView tvEthinicValue = llFurtherInfoDetails.findViewById(R.id.tvEthinicValue);
            TextView tvHeightValue = llFurtherInfoDetails.findViewById(R.id.tvHeightValue);
            TextView tvBuildValue = llFurtherInfoDetails.findViewById(R.id.tvBuildValue);
            tvSurnameValue.setText(linkedpersondetailresponse.getLastname());
            tvForenameValue.setText(linkedpersondetailresponse.getFirstname1());
            tvDOBValue.setText(linkedpersondetailresponse.getDob());
            tvCertificateValue.setText(linkedpersondetailresponse.getFirarmcertificateholder());
            tvLatestHomeAddressValue.setText(linkedpersondetailresponse.getLatesthomeaddress());
            tvPNCFileNameValue.setText(linkedpersondetailresponse.getPncFilename());
            tvPNCIdValue.setText(linkedpersondetailresponse.getPncId());
            tvGenderValue.setText(linkedpersondetailresponse.getGender());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvAliasDetailValue.setText(Html.fromHtml(linkedpersondetailresponse.getAliasdetails(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                tvAliasDetailValue.setText(Html.fromHtml(linkedpersondetailresponse.getAliasdetails()));
            }

            tvEthinicValue.setText(linkedpersondetailresponse.getEthnicity());
            tvHeightValue.setText(linkedpersondetailresponse.getHeight());
            tvBuildValue.setText(linkedpersondetailresponse.getBuild());

        }
    }

    /**
     * Load Person JSON
     */
    public void loadLinkedPersonDetails(String fileName) {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {

                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);
                AthenaLinkedPersonDetailsModel athenaResponse = (AthenaLinkedPersonDetailsModel) JsonUtil.getInstance().toModel(strJson, AthenaLinkedPersonDetailsModel.class);
                if (athenaResponse != null) {
                    linkedpersondetailresponse = athenaResponse.getLinkedpersondetailresponse();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (linkedpersondetailresponse != null) {
                        loadPersonData();
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), LinkedPersonDetailsDialogFragment.class, "loadAthenaPersonDetails");
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
                    loadPersonImageData();
                } else {
                    hScrollViewImage.setVisibility(View.GONE);
                }

                break;

            case R.id.rlFurtherInfo:

                if (llFurtherInfoDetails.getVisibility() != View.VISIBLE) {
                    llFurtherInfoDetails.setVisibility(View.VISIBLE);
                    loadFurtherInfoData();
                } else {
                    llFurtherInfoDetails.setVisibility(View.GONE);
                }

                break;
        }
    }


}
