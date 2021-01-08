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
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.AthenaLinkedPropertyDetailsModel;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class LinkedPropertyItemsDialogFragment extends DialogFragment implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private int listType;
    private ArrayList<List<?>> listDetails;
    private LinearLayout llDetails, llHeader, property_items_detail;
    private TextView tvFlowName;
    private Dialog mProgressDialog;
    private AthenaLinkedPropertyDetailsModel.PropertyitemsdetailresponseBean propertyitemsdetailresponse;
    private boolean isPopulate;

    public static LinkedPropertyItemsDialogFragment newInstance(int type, ArrayList<List<?>> list, boolean isPopulate) {

        LinkedPropertyItemsDialogFragment frag = new LinkedPropertyItemsDialogFragment();
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

        View rootView = inflater.inflate(R.layout.athena_linked_additional_item_fragment, container, false);
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
        property_items_detail = view.findViewById(R.id.property_items_detail);
        tvFlowName = view.findViewById(R.id.tvFlowName);

        property_items_detail.setVisibility(View.VISIBLE);
        isPopulateView();
        loadHeaderLayout();
        loadAthenaPropertyDetails(GenericConstant.ATHENA_LINKED_PROPERTY_DETAILS_JSON);
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
        tvHeader.setText(getString(R.string.linked_property_item_header));

        String title = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.LINKED_HEADER, "");
        tvFlowName.setText(title);

        ivBack.setOnClickListener(this);
    }


    /**
     * Load Communication Data
     */
    private void loadPropertyItemData() {

        TextView tvReferenceValue = property_items_detail.findViewById(R.id.tvReferenceValue);
        TextView tvCategoryValue = property_items_detail.findViewById(R.id.tvCategoryValue);
        TextView tvSubCategoryValue = property_items_detail.findViewById(R.id.tvSubCategoryValue);
        TextView tvItemValue = property_items_detail.findViewById(R.id.tvItemValue);
        TextView tvManufactureValue = property_items_detail.findViewById(R.id.tvManufactureValue);
        TextView tvModelValue = property_items_detail.findViewById(R.id.tvModelValue);
        TextView tvStatusValue = property_items_detail.findViewById(R.id.tvStatusValue);
        TextView tvDescriptionValue = property_items_detail.findViewById(R.id.tvDescriptionValue);
        TextView tvFirstColorValue = property_items_detail.findViewById(R.id.tvFirstColorValue);
        TextView tvSecondColorValue = property_items_detail.findViewById(R.id.tvSecondColorValue);


        if (propertyitemsdetailresponse != null) {

            tvReferenceValue.setText(propertyitemsdetailresponse.getReferenceno());
            tvCategoryValue.setText(propertyitemsdetailresponse.getCategory());
            tvSubCategoryValue.setText(propertyitemsdetailresponse.getSubcategory());
            tvItemValue.setText(propertyitemsdetailresponse.getItem());
            tvManufactureValue.setText(propertyitemsdetailresponse.getManufacturer());
            tvModelValue.setText(propertyitemsdetailresponse.getModel());
            tvStatusValue.setText(propertyitemsdetailresponse.getStatus());
            tvDescriptionValue.setText(propertyitemsdetailresponse.getDescription());
            tvFirstColorValue.setText(propertyitemsdetailresponse.getFirstcolour());
            tvSecondColorValue.setText(propertyitemsdetailresponse.getSecondcolour());

        }
    }

    /**
     * Load Location JSON
     */
    public void loadAthenaPropertyDetails(String fileName) {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {

                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);
                AthenaLinkedPropertyDetailsModel athenaResponse = (AthenaLinkedPropertyDetailsModel) JsonUtil.getInstance().toModel(strJson, AthenaLinkedPropertyDetailsModel.class);
                if (athenaResponse != null) {
                    propertyitemsdetailresponse = athenaResponse.getPropertyitemsdetailresponse();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (propertyitemsdetailresponse != null) {
                        loadPropertyItemData();
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), LinkedLocationDetailsDialogFragment.class, "loadAthenaArrestDetails");
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
        }
    }
}
