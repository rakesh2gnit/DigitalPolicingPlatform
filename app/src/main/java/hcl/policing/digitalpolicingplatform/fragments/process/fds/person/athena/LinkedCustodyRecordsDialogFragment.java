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
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.AthenaLinkedCustodyDetailsModel;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class LinkedCustodyRecordsDialogFragment extends DialogFragment implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private int listType;
    private ArrayList<List<?>> listDetails;
    private LinearLayout llDetails, llHeader, custodyDetail, llReasonsOffenceContent, llBailDetailsContent;
    private RelativeLayout rlReasonsOffence, rlBailDetails;
    private ImageView ivBack;
    private TextView tvFlowName;
    private TextView tvReasonsOffenceCount, tvBailDetailsCount;
    private Dialog mProgressDialog;
    private AthenaLinkedCustodyDetailsModel.CustodydetailresponseBean custodydetailresponseBean;
    List<AthenaLinkedCustodyDetailsModel.ReasonsBean> aReasonsBean;
    List<AthenaLinkedCustodyDetailsModel.BailBean> aBailBean;
    private boolean isPopulate;

    public static LinkedCustodyRecordsDialogFragment newInstance(int type, ArrayList<List<?>> list, boolean isPopulate) {

        LinkedCustodyRecordsDialogFragment frag = new LinkedCustodyRecordsDialogFragment();
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

        View rootView = inflater.inflate(R.layout.athena_linked_custody_records_fragment, container, false);
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
        tvFlowName = view.findViewById(R.id.tvFlowName);
        rlReasonsOffence = view.findViewById(R.id.rlReasonsOffence);
        rlBailDetails = view.findViewById(R.id.rlBailDetails);

        custodyDetail = view.findViewById(R.id.custodyDetail);
        llReasonsOffenceContent = view.findViewById(R.id.llReasonsOffenceContent);
        llBailDetailsContent = view.findViewById(R.id.llBailDetailsContent);


        tvReasonsOffenceCount = view.findViewById(R.id.tvReasonsOffenceCount);
        tvBailDetailsCount = view.findViewById(R.id.tvBailDetailsCount);

        isPopulateView();
        loadHeaderLayout();
        loadAthenaCustodyDetails(GenericConstant.ATHENA_LINKED_CUSTODY_DETAILS_JSON);

        rlReasonsOffence.setOnClickListener(this);
        rlBailDetails.setOnClickListener(this);

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
        tvHeader.setText(getString(R.string.linked_custody_record_header));

        String title = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.LINKED_HEADER, "");
        tvFlowName.setText(title);

        ivBack.setOnClickListener(this);
    }


    /**
     * Load the Case Details
     */
    private void loadCustodyData() {

        TextView tvCustodyNoValue = custodyDetail.findViewById(R.id.tvCustodyNoValue);
        TextView tvCustodyStationValue = custodyDetail.findViewById(R.id.tvCustodyStationValue);
        TextView tvCustodyStatusValue = custodyDetail.findViewById(R.id.tvCustodyStatusValue);
        TextView tvCustodyTypeValue = custodyDetail.findViewById(R.id.tvCustodyTypeValue);
        TextView tvCustodyArrestTimeValue = custodyDetail.findViewById(R.id.tvCustodyArrestTimeValue);
        TextView tvCustodyArrivedValue = custodyDetail.findViewById(R.id.tvCustodyArrivedValue);

        if (custodydetailresponseBean != null) {
            tvCustodyNoValue.setText(custodydetailresponseBean.getRecordno());
            tvCustodyStationValue.setText(custodydetailresponseBean.getStation());
            tvCustodyStatusValue.setText(custodydetailresponseBean.getStatus());
            tvCustodyTypeValue.setText(custodydetailresponseBean.getType());
            tvCustodyArrestTimeValue.setText(custodydetailresponseBean.getArresttime());
            tvCustodyArrivedValue.setText(custodydetailresponseBean.getArrived());
        }
    }


    /**
     * load Defendants Data
     */
    private void loadReasonsData() {

        if (aReasonsBean != null && aReasonsBean.size() > 0) {
            for (int i = 0; i < aReasonsBean.size(); i++) {

                AthenaLinkedCustodyDetailsModel.ReasonsBean mReasonsBean = aReasonsBean.get(i);

                View view = layoutInflater.inflate(R.layout.athena_custody_record_expand_items, null);
                TextView tvTitle = view.findViewById(R.id.tvTitle);
                TextView tvCustodyNo = view.findViewById(R.id.tvCustodyNo);
                TextView tvCustodyNoValue = view.findViewById(R.id.tvCustodyNoValue);
                TextView tvCustodyStation = view.findViewById(R.id.tvCustodyStation);
                TextView tvCustodyStationValue = view.findViewById(R.id.tvCustodyStationValue);
                TextView tvCustodyStatus = view.findViewById(R.id.tvCustodyStatus);
                TextView tvCustodyStatusValue = view.findViewById(R.id.tvCustodyStatusValue);

                View viewCustodyArrestTime = view.findViewById(R.id.viewCustodyArrestTime);
                View viewCustodyArrived = view.findViewById(R.id.viewCustodyArrived);
                View viewCustodyType = view.findViewById(R.id.viewCustodyType);
                LinearLayout llCustodyArrestTime = view.findViewById(R.id.llCustodyArrestTime);
                LinearLayout llCustodyArrived = view.findViewById(R.id.llCustodyArrived);
                LinearLayout llCustodyType = view.findViewById(R.id.llCustodyType);

                viewCustodyArrestTime.setVisibility(View.GONE);
                viewCustodyArrived.setVisibility(View.GONE);
                viewCustodyType.setVisibility(View.GONE);
                llCustodyArrestTime.setVisibility(View.GONE);
                llCustodyArrived.setVisibility(View.GONE);
                llCustodyType.setVisibility(View.GONE);

                tvCustodyNo.setText(getString(R.string.custody_arrest_date_time));
                tvCustodyStation.setText(getString(R.string.custody_associate_crime_no));
                tvCustodyStatus.setText(getString(R.string.custody_oic));

                tvTitle.setText(mReasonsBean.getDescription());
                tvCustodyNoValue.setText(mReasonsBean.getDatetime());
                tvCustodyStationValue.setText(mReasonsBean.getCrimeno());
                tvCustodyStatusValue.setText(mReasonsBean.getOic());

                llReasonsOffenceContent.addView(view);
            }
        }
    }


    /**
     * load Hearing Data
     */
    private void loadBailsData() {

        if (aBailBean != null && aBailBean.size() > 0) {
            for (int i = 0; i < aBailBean.size(); i++) {
                AthenaLinkedCustodyDetailsModel.BailBean mBailBean = aBailBean.get(i);

                View view = layoutInflater.inflate(R.layout.athena_custody_record_expand_items, null);
                TextView tvTitle = view.findViewById(R.id.tvTitle);
                TextView tvCustodyNo = view.findViewById(R.id.tvCustodyNo);
                TextView tvCustodyNoValue = view.findViewById(R.id.tvCustodyNoValue);
                TextView tvCustodyStation = view.findViewById(R.id.tvCustodyStation);
                TextView tvCustodyStationValue = view.findViewById(R.id.tvCustodyStationValue);
                TextView tvCustodyStatus = view.findViewById(R.id.tvCustodyStatus);
                TextView tvCustodyStatusValue = view.findViewById(R.id.tvCustodyStatusValue);

                View viewCustodyArrestTime = view.findViewById(R.id.viewCustodyArrestTime);
                View viewCustodyArrived = view.findViewById(R.id.viewCustodyArrived);
                View viewCustodyType = view.findViewById(R.id.viewCustodyType);
                LinearLayout llCustodyArrestTime = view.findViewById(R.id.llCustodyArrestTime);
                LinearLayout llCustodyArrived = view.findViewById(R.id.llCustodyArrived);
                LinearLayout llCustodyType = view.findViewById(R.id.llCustodyType);

                viewCustodyArrestTime.setVisibility(View.GONE);
                viewCustodyArrived.setVisibility(View.GONE);
                viewCustodyType.setVisibility(View.GONE);
                llCustodyArrestTime.setVisibility(View.GONE);
                llCustodyArrived.setVisibility(View.GONE);
                llCustodyType.setVisibility(View.GONE);

            }
        }
    }


    /**
     * Load Person JSON
     */
    public void loadAthenaCustodyDetails(String fileName) {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {

                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);
                AthenaLinkedCustodyDetailsModel athenaResponse = (AthenaLinkedCustodyDetailsModel) JsonUtil.getInstance().toModel(strJson, AthenaLinkedCustodyDetailsModel.class);
                if (athenaResponse != null) {
                    custodydetailresponseBean = athenaResponse.getCustodydetailresponse();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (custodydetailresponseBean != null) {
                        loadViewCount();
                        loadCustodyData();
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), LinkedCustodyRecordsDialogFragment.class, "loadAthenaPersonDetails");
            }
        }).start();
    }

    /**
     * Load View Count
     */
    private void loadViewCount() {
        aReasonsBean = custodydetailresponseBean.getReasons();
        if (aReasonsBean != null && aReasonsBean.size() > 0) {
            tvReasonsOffenceCount.setText(String.valueOf(aReasonsBean.size()));
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivBack:

                dismiss();
                DialogUtil.cancelProgressDialog(mProgressDialog);
                break;

            case R.id.rlReasonsOffence:

                if (llReasonsOffenceContent.getVisibility() != View.VISIBLE) {
                    llReasonsOffenceContent.setVisibility(View.VISIBLE);
                    loadReasonsData();
                } else {
                    llReasonsOffenceContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlBailDetails:

                if (llBailDetailsContent.getVisibility() != View.VISIBLE) {
                    llBailDetailsContent.setVisibility(View.VISIBLE);
                    loadBailsData();
                } else {
                    llBailDetailsContent.setVisibility(View.GONE);
                }

                break;

        }
    }
}