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

import java.util.ArrayList;
import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.AthenaLinkedSearchDetailsModel;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class LinkedSearchDetailDialogFragment extends DialogFragment implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private int listType;
    private ArrayList<List<?>> listDetails;
    private LinearLayout llDetails, llHeader, search_detail, llOfficerContent;
    private TextView tvOfficerCount;
    private TextView tvFlowName;
    private Dialog mProgressDialog;
    private AthenaLinkedSearchDetailsModel.SearchesdetailresponseBean searchesdetailresponseBean;
    private ArrayList<AthenaLinkedSearchDetailsModel.OfficersPresentBean> aOfficersPresentBean;
    private boolean isPopulate;

    public static LinkedSearchDetailDialogFragment newInstance(int type, ArrayList<List<?>> list, boolean isPopulate) {

        LinkedSearchDetailDialogFragment frag = new LinkedSearchDetailDialogFragment();
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

        View rootView = inflater.inflate(R.layout.athena_linked_search_fragment, container, false);
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
        search_detail = view.findViewById(R.id.search_detail);
        tvOfficerCount = view.findViewById(R.id.tvOfficerCount);
        llOfficerContent = view.findViewById(R.id.llOfficerContent);
        RelativeLayout rlOfficer = view.findViewById(R.id.rlOfficer);
        tvFlowName = view.findViewById(R.id.tvFlowName);

        isPopulateView();
        loadHeaderLayout();
        loadAthenaSearchDetails(GenericConstant.ATHENA_LINKED_SEARCH_DETAILS_JSON);
        rlOfficer.setOnClickListener(this);
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
        tvHeader.setText(getString(R.string.linked_search_header));

        String title = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.LINKED_HEADER, "");
        tvFlowName.setText(title);

        ivBack.setOnClickListener(this);

    }


    /**
     * Load Offence Data
     */
    private void loadSearchData() {


        TextView tvDateSubmittedValue = search_detail.findViewById(R.id.tvDateSubmittedValue);
        TextView tvStatusValue = search_detail.findViewById(R.id.tvStatusValue);
        TextView tvSearchTitleValue = search_detail.findViewById(R.id.tvSearchTitleValue);
        TextView tvSearchTypeValue = search_detail.findViewById(R.id.tvSearchTypeValue);
        TextView tvStatutoryPowerValue = search_detail.findViewById(R.id.tvStatutoryPowerValue);
        TextView tvSearchFromValue = search_detail.findViewById(R.id.tvSearchFromValue);
        TextView tvSearchToValue = search_detail.findViewById(R.id.tvSearchToValue);
        TextView tvAuthDateValue = search_detail.findViewById(R.id.tvAuthDateValue);
        TextView tvSearchStatusValue = search_detail.findViewById(R.id.tvSearchStatusValue);


        if (searchesdetailresponseBean != null) {
            tvDateSubmittedValue.setText(searchesdetailresponseBean.getDatesubmitted());
            tvStatusValue.setText(searchesdetailresponseBean.getStatus());
            tvSearchTitleValue.setText(searchesdetailresponseBean.getSearchtitle());
            tvSearchTypeValue.setText(searchesdetailresponseBean.getTypeofsearch());
            tvStatutoryPowerValue.setText(searchesdetailresponseBean.getStatutorypowerused());
            tvSearchFromValue.setText(searchesdetailresponseBean.getSearchfrom());
            tvSearchToValue.setText(searchesdetailresponseBean.getSearchto());
            tvAuthDateValue.setText(searchesdetailresponseBean.getAuthorisationdate() + " " + searchesdetailresponseBean.getAuthorisationtime());
            tvSearchStatusValue.setText(searchesdetailresponseBean.getSearchstatuscustody());


            aOfficersPresentBean = (ArrayList<AthenaLinkedSearchDetailsModel.OfficersPresentBean>) searchesdetailresponseBean.getOfficerspresent();
            if (aOfficersPresentBean != null && aOfficersPresentBean.size() > 0) {
                tvOfficerCount.setText(String.valueOf(aOfficersPresentBean.size()));
            }
        }


    }

    /**
     * load Officer Present Data
     */
    private void loadOfficerPresentData() {
        llOfficerContent.removeAllViews();
        if (aOfficersPresentBean != null && aOfficersPresentBean.size() > 0) {
            for (int i = 0; i < aOfficersPresentBean.size(); i++) {

                AthenaLinkedSearchDetailsModel.OfficersPresentBean officersPresentBean = aOfficersPresentBean.get(i);

                View view = layoutInflater.inflate(R.layout.athena_search_officer_present_items, null);

                TextView tvNameValue = view.findViewById(R.id.tvNameValue);
                TextView tvTypeValue = view.findViewById(R.id.tvTypeValue);

                tvNameValue.setText(officersPresentBean.getDisplayname());
                tvTypeValue.setText(officersPresentBean.getType());
                llOfficerContent.addView(view);
            }
        }
    }


    /**
     * Load Search Details JSON
     */
    public void loadAthenaSearchDetails(String fileName) {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {

                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);
                AthenaLinkedSearchDetailsModel athenaResponse = (AthenaLinkedSearchDetailsModel) JsonUtil.getInstance().toModel(strJson, AthenaLinkedSearchDetailsModel.class);
                if (athenaResponse != null) {
                    searchesdetailresponseBean = athenaResponse.getSearchesdetailresponse();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (searchesdetailresponseBean != null) {
                        loadSearchData();
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), LinkedSearchDetailDialogFragment.class, "loadAthenaSearchDetails");
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

            case R.id.rlOfficer:

                if (llOfficerContent.getVisibility() != View.VISIBLE) {
                    llOfficerContent.setVisibility(View.VISIBLE);
                    loadOfficerPresentData();
                } else {
                    llOfficerContent.setVisibility(View.GONE);
                }

                break;
        }
    }
}
