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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.AthenaLinkedCommunicationDetailsModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.WarningsModel;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class LinkedCommunicationDialogFragment extends DialogFragment implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private int listType;
    private ArrayList<List<?>> listDetails;
    private LinearLayout llDetails, llHeader, communication_detail;
    private TextView tvFlowName;
    private Dialog mProgressDialog;
    private AthenaLinkedCommunicationDetailsModel.CommunicationdetailresponseBean communicationdetailresponseBean;
    private boolean isPopulate;

    public static LinkedCommunicationDialogFragment newInstance(int type, ArrayList<List<?>> list, boolean isPopulate) {

        LinkedCommunicationDialogFragment frag = new LinkedCommunicationDialogFragment();
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

        View rootView = inflater.inflate(R.layout.athena_linked_communication_fragment, container, false);
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
        communication_detail = view.findViewById(R.id.communication_detail);
        tvFlowName = view.findViewById(R.id.tvFlowName);

        isPopulateView();
        loadHeaderLayout();
        loadAthenaCommunicationDetails(GenericConstant.ATHENA_LINKED_COMMUNICATION_DETAILS_JSON);
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
        tvHeader.setText(getString(R.string.linked_communication_header));

        String title = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.LINKED_HEADER, "");
        tvFlowName.setText(title);

        ivBack.setOnClickListener(this);
    }


    /**
     * Load Communication Data
     */
    private void loadCommunicationData() {

        ArrayList<WarningsModel> aWarningsBean = (ArrayList<WarningsModel>) communicationdetailresponseBean.getWarnings();

        TextView tvTypeValue = communication_detail.findViewById(R.id.tvTypeValue);
        TextView tvDetailsValue = communication_detail.findViewById(R.id.tvDetailsValue);
        TextView tvStatusValue = communication_detail.findViewById(R.id.tvStatusValue);
        TextView tvRemarksValue = communication_detail.findViewById(R.id.tvRemarksValue);
        TextView tvCreatedDateValue = communication_detail.findViewById(R.id.tvCreatedDateValue);
        View viewWarning = communication_detail.findViewById(R.id.viewWarning);
        LinearLayout llWarning = communication_detail.findViewById(R.id.llWarning);
        TextView tvMarker = communication_detail.findViewById(R.id.tvMarker);


        if (communicationdetailresponseBean != null) {
            tvTypeValue.setText(communicationdetailresponseBean.getType());
            tvDetailsValue.setText(communicationdetailresponseBean.getDetails());
            tvStatusValue.setText(communicationdetailresponseBean.getStatus());
            tvRemarksValue.setText(communicationdetailresponseBean.getRemarks());
            tvCreatedDateValue.setText(communicationdetailresponseBean.getCreateddate());

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
        LinkedWarningListDialogFragment linkedLocationDetailsDialogFragment = LinkedWarningListDialogFragment.newInstance(type, aWarningsBean,isPopulate);
        linkedLocationDetailsDialogFragment.show(ft, GenericConstant.LINKED_WARNING_DIALOG);
    }


    /**
     * Load Location JSON
     */
    public void loadAthenaCommunicationDetails(String fileName) {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {

                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);
                AthenaLinkedCommunicationDetailsModel athenaResponse = (AthenaLinkedCommunicationDetailsModel) JsonUtil.getInstance().toModel(strJson, AthenaLinkedCommunicationDetailsModel.class);
                if (athenaResponse != null) {
                    communicationdetailresponseBean = athenaResponse.getCommunicationdetailresponse();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (communicationdetailresponseBean != null) {
                        loadCommunicationData();
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
        }
    }
}
