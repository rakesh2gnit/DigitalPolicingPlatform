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
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.AthenaLinkedOrganizationDetailsModel;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class LinkedOrganizationDialogFragment extends DialogFragment implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private int listType;
    private ArrayList<List<?>> listDetails;
    private LinearLayout llDetails, llHeader, arrest_detail, offence_detail, organization_detail;
    private TextView tvFlowName;
    private Dialog mProgressDialog;
    private AthenaLinkedOrganizationDetailsModel.OrganisationdetailresponseBean organisationdetailresponseBean;
    private boolean isPopulate;


    public static LinkedOrganizationDialogFragment newInstance(int type, ArrayList<List<?>> list, boolean isPopulate) {

        LinkedOrganizationDialogFragment frag = new LinkedOrganizationDialogFragment();
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
        organization_detail = view.findViewById(R.id.organization_detail);
        tvFlowName = view.findViewById(R.id.tvFlowName);

        organization_detail.setVisibility(View.VISIBLE);

        isPopulateView();
        loadHeaderLayout();
        loadAthenaOrganizationDetails(GenericConstant.ATHENA_LINKED_ORGANIZATION_DETAILS_JSON);
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
        tvHeader.setText(getString(R.string.linked_organisation_header));

        String title = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.LINKED_HEADER, "");
        tvFlowName.setText(title);

        ivBack.setOnClickListener(this);
    }


    /**
     * Load Offence Data
     */
    private void loadOrganizationData() {

        TextView tvNameValue = organization_detail.findViewById(R.id.tvNameValue);
        TextView tvTypeValue = organization_detail.findViewById(R.id.tvTypeValue);
        TextView tvBranchValue = organization_detail.findViewById(R.id.tvBranchValue);
        TextView tvOrgRemarksValue = organization_detail.findViewById(R.id.tvOrgRemarksValue);


        if (organisationdetailresponseBean != null) {
            tvNameValue.setText(organisationdetailresponseBean.getName());
            tvTypeValue.setText(organisationdetailresponseBean.getType() + " " + organisationdetailresponseBean.getTypetwo());
            tvBranchValue.setText(organisationdetailresponseBean.getBranch());
            tvOrgRemarksValue.setText(organisationdetailresponseBean.getOrganisationremarks());

        }
    }

    /**
     * Load Location JSON
     */
    public void loadAthenaOrganizationDetails(String fileName) {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {

                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);
                AthenaLinkedOrganizationDetailsModel athenaResponse = (AthenaLinkedOrganizationDetailsModel) JsonUtil.getInstance().toModel(strJson, AthenaLinkedOrganizationDetailsModel.class);
                if (athenaResponse != null) {
                    organisationdetailresponseBean = athenaResponse.getOrganisationdetailresponse();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (organisationdetailresponseBean != null) {
                        loadOrganizationData();
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
