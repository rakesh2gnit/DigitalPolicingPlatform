package hcl.policing.digitalpolicingplatform.fragments.process.fds.person.pnc;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
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

import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.map.FDSMapActivity;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.listeners.dialog.ManuallyClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.PersonSelectionListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.pnc.AddressesBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.pnc.AliasListBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.pnc.PersonPNCAddressResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.pnc.PersonPNCAliasResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.pnc.PersonPNCDescriptionResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.pnc.PersonPNCDetailResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.pnc.PersonPNCDisqualifiedResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.pnc.PersonPNCDnaResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.pnc.PersonPNCPhotoLocResponse;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;
import hcl.policing.digitalpolicingplatform.utils.LocationUtil;

public class PersonPNCDetailDialogFragment extends DialogFragment implements View.OnClickListener {

    private Context mContext;
    public AppSession appSession;
    private ManuallyClickListener manuallyClickListener;
    private PersonSelectionListener personSelectionListener;
    private LayoutInflater layoutInflater;
    private int flowType;
    private LinearLayout llDetails, llHeader, llCommonDetails, llPersonDetail, llWantedMissingContent, llAddressesContent, llAliasNicknameContent,
            llDescriptionContent, llBailConditionContent, llImpendingProsecutionContent, llDriverReportContent, llPhotoLocContent, llDisposalHistoryContent,
            llDisposalSummaryContent, llDNAReportContent, llOperationInfoContent;
    private LinearLayout pnc_detail;
    private List<PersonPNCDetailResponse.PersonListBean> aPersonListBean = null;
    private Dialog mProgressDialog;
    private String nominalAddress;
    private boolean isPopulate;

    public static PersonPNCDetailDialogFragment newInstance(int type, boolean isPopulate) {

        PersonPNCDetailDialogFragment frag = new PersonPNCDetailDialogFragment();
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
        appSession = new AppSession(mContext);
        manuallyClickListener = (ManuallyClickListener) getActivity();
        personSelectionListener = (PersonSelectionListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.person_pnc_detail_fragment, container, false);
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
        llCommonDetails = view.findViewById(R.id.llCommonDetails);
        pnc_detail = view.findViewById(R.id.pnc_detail);

        isPopulateView();
        loadHeaderLayout();

        if (flowType == GenericConstant.TYPE_PERSON) {
            pnc_detail.setVisibility(View.VISIBLE);
            loadPersonPNCJSON(GenericConstant.PERSON_PNC_DETAILS_JSON);
        }
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
            tvHeader.setText(getString(R.string.pnc_nominal_header));

        }

        ivBack.setOnClickListener(this);
    }

    /**
     * Load the common view which are same for all type systems
     */
    private void loadCommonView() {

        RelativeLayout rlWantedMissing = llCommonDetails.findViewById(R.id.rlWantedMissing);
        RelativeLayout rlAddresses = llCommonDetails.findViewById(R.id.rlAddresses);
        RelativeLayout rlAliasNickname = llCommonDetails.findViewById(R.id.rlAliasNickname);
        RelativeLayout rlDescription = llCommonDetails.findViewById(R.id.rlDescription);
        RelativeLayout rlBailCondition = llCommonDetails.findViewById(R.id.rlBailCondition);
        RelativeLayout rlImpendingProsecution = llCommonDetails.findViewById(R.id.rlImpendingProsecution);
        RelativeLayout rlDriverReport = llCommonDetails.findViewById(R.id.rlDriverReport);
        RelativeLayout rlPhotoLoc = llCommonDetails.findViewById(R.id.rlPhotoLoc);
        RelativeLayout rlDisposalHistory = llCommonDetails.findViewById(R.id.rlDisposalHistory);
        RelativeLayout rlDisposalSummary = llCommonDetails.findViewById(R.id.rlDisposalSummary);
        RelativeLayout rlDNAReport = llCommonDetails.findViewById(R.id.rlDNAReport);
        RelativeLayout rlOperationInfo = llCommonDetails.findViewById(R.id.rlOperationInfo);

        llWantedMissingContent = llCommonDetails.findViewById(R.id.llWantedMissingContent);
        llAddressesContent = llCommonDetails.findViewById(R.id.llAddressesContent);
        llAliasNicknameContent = llCommonDetails.findViewById(R.id.llAliasNicknameContent);
        llDescriptionContent = llCommonDetails.findViewById(R.id.llDescriptionContent);
        llBailConditionContent = llCommonDetails.findViewById(R.id.llBailConditionContent);
        llImpendingProsecutionContent = llCommonDetails.findViewById(R.id.llImpendingProsecutionContent);
        llDriverReportContent = llCommonDetails.findViewById(R.id.llDriverReportContent);
        llPhotoLocContent = llCommonDetails.findViewById(R.id.llPhotoLocContent);
        llDisposalHistoryContent = llCommonDetails.findViewById(R.id.llDisposalHistoryContent);
        llDisposalSummaryContent = llCommonDetails.findViewById(R.id.llDisposalSummaryContent);
        llDNAReportContent = llCommonDetails.findViewById(R.id.llDNAReportContent);
        llOperationInfoContent = llCommonDetails.findViewById(R.id.llOperationInfoContent);

        TextView tvWantedMissingCount = llCommonDetails.findViewById(R.id.tvWantedMissingCount);
        TextView tvAddressesCount = llCommonDetails.findViewById(R.id.tvAddressesCount);
        TextView tvAliasNicknameCount = llCommonDetails.findViewById(R.id.tvAliasNicknameCount);
        TextView tvDescriptionCount = llCommonDetails.findViewById(R.id.tvDescriptionCount);
        TextView tvBailConditionCount = llCommonDetails.findViewById(R.id.tvBailConditionCount);
        TextView tvImpendingProsecutionCount = llCommonDetails.findViewById(R.id.tvImpendingProsecutionCount);
        TextView tvDriverReportCount = llCommonDetails.findViewById(R.id.tvDriverReportCount);
        TextView tvPhotoLocCount = llCommonDetails.findViewById(R.id.tvPhotoLocCount);
        TextView tvDisposalHistoryCount = llCommonDetails.findViewById(R.id.tvDisposalHistoryCount);
        TextView tvDisposalSummaryCount = llCommonDetails.findViewById(R.id.tvDisposalSummaryCount);
        TextView tvDNAReportCount = llCommonDetails.findViewById(R.id.tvDNAReportCount);
        TextView tvOperationInfoCount = llCommonDetails.findViewById(R.id.tvOperationInfoCount);

        if (aPersonListBean != null && aPersonListBean.size() > 0) {

            if (!TextUtils.isEmpty(aPersonListBean.get(0).getWantedandmissingcount())) {

                tvWantedMissingCount.setText(aPersonListBean.get(0).getWantedandmissingcount());
            }
            if (!TextUtils.isEmpty(aPersonListBean.get(0).getAddresscount())) {

                tvAddressesCount.setText(aPersonListBean.get(0).getAddresscount());
            }
            if (!TextUtils.isEmpty(aPersonListBean.get(0).getAliasnicknamecount())) {

                tvAliasNicknameCount.setText(aPersonListBean.get(0).getAliasnicknamecount());
            }
            if (!TextUtils.isEmpty(aPersonListBean.get(0).getDescriptioncount())) {

                tvDescriptionCount.setText(aPersonListBean.get(0).getDescriptioncount());
            }
            if (!TextUtils.isEmpty(aPersonListBean.get(0).getBailconditionscount())) {

                tvBailConditionCount.setText(aPersonListBean.get(0).getBailconditionscount());
            }
            if (!TextUtils.isEmpty(aPersonListBean.get(0).getImpendingprosecution())) {

                tvImpendingProsecutionCount.setText(aPersonListBean.get(0).getImpendingprosecution());
            }
            if (!TextUtils.isEmpty(aPersonListBean.get(0).getDisqualifieddrivercount())) {

                tvDriverReportCount.setText(aPersonListBean.get(0).getDisqualifieddrivercount());
            }
            if (!TextUtils.isEmpty(aPersonListBean.get(0).getPhotolocationcount())) {
                tvPhotoLocCount.setText(aPersonListBean.get(0).getPhotolocationcount());
            }
            if (!TextUtils.isEmpty(aPersonListBean.get(0).getDisposablehistorycount())) {
                tvDisposalHistoryCount.setText(aPersonListBean.get(0).getDisposablehistorycount());
            }
            if (!TextUtils.isEmpty(aPersonListBean.get(0).getDisposalsummarycount())) {
                tvDisposalSummaryCount.setText(aPersonListBean.get(0).getDisposalsummarycount());
            }
            if (!TextUtils.isEmpty(aPersonListBean.get(0).getDnacount())) {
                tvDNAReportCount.setText(aPersonListBean.get(0).getDnacount());
            }
            if (!TextUtils.isEmpty(aPersonListBean.get(0).getOperationalinfocount())) {
                tvOperationInfoCount.setText(aPersonListBean.get(0).getOperationalinfocount());
            }
        }

        rlWantedMissing.setOnClickListener(this);
        rlAddresses.setOnClickListener(this);
        rlAliasNickname.setOnClickListener(this);
        rlDescription.setOnClickListener(this);
        rlBailCondition.setOnClickListener(this);
        rlImpendingProsecution.setOnClickListener(this);
        rlDriverReport.setOnClickListener(this);
        rlPhotoLoc.setOnClickListener(this);
        rlDisposalHistory.setOnClickListener(this);
        rlDisposalSummary.setOnClickListener(this);
        rlDNAReport.setOnClickListener(this);
        rlOperationInfo.setOnClickListener(this);
    }

    /**
     * Load the PNC Person Details
     */
    private void loadPersonData() {

        RelativeLayout rlPersonDetails = pnc_detail.findViewById(R.id.rlPersonDetails);
        llPersonDetail = pnc_detail.findViewById(R.id.llPersonDetail);
        TextView tvWarningsValue = pnc_detail.findViewById(R.id.tvWarningsValue);
        TextView tvNameValue = pnc_detail.findViewById(R.id.tvNameValue);
        TextView tvPNCIdValue = pnc_detail.findViewById(R.id.tvPNCIdValue);
        TextView tvDlNoValue = pnc_detail.findViewById(R.id.tvDlNoValue);
        TextView tvDOBValue = pnc_detail.findViewById(R.id.tvDOBValue);
        TextView tvPOBValue = pnc_detail.findViewById(R.id.tvPOBValue);
        TextView tvGenderValue = pnc_detail.findViewById(R.id.tvGenderValue);
        TextView tvEthnicityValue = pnc_detail.findViewById(R.id.tvEthnicityValue);
        TextView tvTypeValue = pnc_detail.findViewById(R.id.tvTypeValue);
        TextView tvMarkScarsValue = pnc_detail.findViewById(R.id.tvMarkScarsValue);
        TextView tvInfoMarkerValue = pnc_detail.findViewById(R.id.tvInfoMarkerValue);
        TextView tvLastUpdateValue = pnc_detail.findViewById(R.id.tvLastUpdateValue);
        TextView tvNominalAddressValue = pnc_detail.findViewById(R.id.tvNominalAddressValue);
        ImageView ivNavigation = pnc_detail.findViewById(R.id.ivNavigation);
        TextView tvFileNameValue = pnc_detail.findViewById(R.id.tvFileNameValue);

        if (aPersonListBean != null && aPersonListBean.size() > 0) {
            tvWarningsValue.setText(aPersonListBean.get(0).getWarnings());
            tvNameValue.setText(aPersonListBean.get(0).getName());
            tvPNCIdValue.setText(aPersonListBean.get(0).getPncid());
            tvDlNoValue.setText(aPersonListBean.get(0).getDlno());
            tvDOBValue.setText(aPersonListBean.get(0).getDob());
            tvPOBValue.setText(aPersonListBean.get(0).getPlaceofbirth());
            tvGenderValue.setText(aPersonListBean.get(0).getGender());
            tvEthnicityValue.setText(aPersonListBean.get(0).getEthnicity());
            tvTypeValue.setText(aPersonListBean.get(0).getType());
            tvMarkScarsValue.setText(aPersonListBean.get(0).getMarksscars());
            tvInfoMarkerValue.setText(aPersonListBean.get(0).getInformationmarker());
            tvLastUpdateValue.setText(aPersonListBean.get(0).getLastupdateddatetime());
            tvFileNameValue.setText(aPersonListBean.get(0).getFirstname());

            nominalAddress = aPersonListBean.get(0).getNominaladdress();

            if (aPersonListBean.get(0).getNominaladdress() != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvNominalAddressValue.setText(Html.fromHtml(nominalAddress, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    tvNominalAddressValue.setText(Html.fromHtml(nominalAddress));
                }
            }
        }

        tvNominalAddressValue.setOnClickListener(this);
        ivNavigation.setOnClickListener(this);
        rlPersonDetails.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ivBack:
                DialogUtil.cancelProgressDialog(mProgressDialog);
                dismiss();

                break;

            case R.id.tvNominalAddressValue:
                FDSMapActivity.callMapActivity(mContext, nominalAddress);

                break;

            case R.id.ivNavigation:
                LocationUtil.getInstance().navigateTo(mContext, nominalAddress);
                break;

            case R.id.rlPersonDetails:

                if (llPersonDetail.getVisibility() != View.VISIBLE) {
                    llPersonDetail.setVisibility(View.VISIBLE);
                    loadPersonData();
                } else {
                    llPersonDetail.setVisibility(View.GONE);
                }

                break;

            case R.id.rlWantedMissing:

                if (llWantedMissingContent.getVisibility() != View.VISIBLE) {
                    llWantedMissingContent.setVisibility(View.VISIBLE);

                } else {
                    llWantedMissingContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlAddresses:

                if (llAddressesContent.getVisibility() != View.VISIBLE) {
                    llAddressesContent.setVisibility(View.VISIBLE);
                    loadAddressJSON();
                } else {
                    llAddressesContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlAliasNickname:

                if (llAliasNicknameContent.getVisibility() != View.VISIBLE) {
                    llAliasNicknameContent.setVisibility(View.VISIBLE);

                    loadAliasGroupJSON();

                } else {
                    llAliasNicknameContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlDescription:

                if (llDescriptionContent.getVisibility() != View.VISIBLE) {
                    llDescriptionContent.setVisibility(View.VISIBLE);
                    loadDescriptionJSON();
                } else {
                    llDescriptionContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlBailCondition:

                if (llBailConditionContent.getVisibility() != View.VISIBLE) {
                    llBailConditionContent.setVisibility(View.VISIBLE);

                } else {
                    llBailConditionContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlImpendingProsecution:

                if (llImpendingProsecutionContent.getVisibility() != View.VISIBLE) {
                    llImpendingProsecutionContent.setVisibility(View.VISIBLE);

                } else {
                    llImpendingProsecutionContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlDriverReport:

                if (llDriverReportContent.getVisibility() != View.VISIBLE) {
                    llDriverReportContent.setVisibility(View.VISIBLE);
                    loadDisqualifiedJSON();
                } else {
                    llDriverReportContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlPhotoLoc:

                if (llPhotoLocContent.getVisibility() != View.VISIBLE) {
                    llPhotoLocContent.setVisibility(View.VISIBLE);
                    loadPhotoLocationJSON();
                } else {
                    llPhotoLocContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlDisposalHistory:

                if (llDisposalHistoryContent.getVisibility() != View.VISIBLE) {
                    llDisposalHistoryContent.setVisibility(View.VISIBLE);

                } else {
                    llDisposalHistoryContent.setVisibility(View.GONE);
                }

                break;


            case R.id.rlDisposalSummary:

                if (llDisposalSummaryContent.getVisibility() != View.VISIBLE) {
                    llDisposalSummaryContent.setVisibility(View.VISIBLE);

                } else {
                    llDisposalSummaryContent.setVisibility(View.GONE);
                }

                break;
            case R.id.rlDNAReport:

                if (llDNAReportContent.getVisibility() != View.VISIBLE) {
                    llDNAReportContent.setVisibility(View.VISIBLE);
                    loadDNAJSON();
                } else {
                    llDNAReportContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlOperationInfo:

                if (llOperationInfoContent.getVisibility() != View.VISIBLE) {
                    llOperationInfoContent.setVisibility(View.VISIBLE);

                } else {
                    llOperationInfoContent.setVisibility(View.GONE);
                }

                break;

        }

    }


    /**
     * Load Person PNC JSON
     */
    public void loadPersonPNCJSON(String fileName) {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);
                PersonPNCDetailResponse pncResponse = (PersonPNCDetailResponse) JsonUtil.getInstance().toModel(strJson, PersonPNCDetailResponse.class);
                if (pncResponse != null) {
                    PersonPNCDetailResponse.PersonbynameBean personbynameBean = pncResponse.getPersonbyname();
                    if (personbynameBean != null) {
                        aPersonListBean = pncResponse.getPersonbyname().getPersonlist();
                    }
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (aPersonListBean != null) {
                        loadCommonView();
                        loadPersonData();
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), PersonPNCDetailDialogFragment.class, "loadPersonPNCJSON");
            }
        }).start();
    }

    /**
     * Load PNC Address
     */
    public void loadAddressJSON() {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), GenericConstant.PERSON_PNC_ADDRESS_DETAILS_JSON);
                PersonPNCAddressResponse pncResponse = (PersonPNCAddressResponse) JsonUtil.getInstance().toModel(strJson, PersonPNCAddressResponse.class);
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (pncResponse != null) {
                        List<AddressesBean> aAddressesBean = pncResponse.getAddresses();
                        loadAddressData(aAddressesBean);
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }
                });
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), PersonPNCDetailDialogFragment.class, "loadAddressJSON");
            }
        }).start();
    }

    /**
     * Load PNC Address
     */
    public void loadAliasGroupJSON() {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), GenericConstant.PERSON_PNC_ALIAS_GROUP_JSON);
                PersonPNCAliasResponse pncResponse = (PersonPNCAliasResponse) JsonUtil.getInstance().toModel(strJson, PersonPNCAliasResponse.class);
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (pncResponse != null) {
                        List<AliasListBean> aAliasListBean = pncResponse.getAliaslist();
                        loadAliasData(aAliasListBean);
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }
                });
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), PersonPNCDetailDialogFragment.class, "loadAliasGroupJSON");
            }
        }).start();
    }

    /**
     * Load PNC Description
     */
    public void loadDescriptionJSON() {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), GenericConstant.PERSON_PNC_DESCRIPTION_GROUP_JSON);
                PersonPNCDescriptionResponse pncResponse = (PersonPNCDescriptionResponse) JsonUtil.getInstance().toModel(strJson, PersonPNCDescriptionResponse.class);
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (pncResponse != null) {
                        List<PersonPNCDescriptionResponse.PersonDescriptionListBean> aPersonDescriptionListBean = pncResponse.getPersondescriptionlist();
                        loadDescriptionData(aPersonDescriptionListBean);
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }
                });
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), PersonPNCDetailDialogFragment.class, "loadPersonPNCJSON");
            }
        }).start();
    }

    /**
     * Load PNC Disqualified
     */
    public void loadDisqualifiedJSON() {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), GenericConstant.PERSON_PNC_DISQUALIFIED_DRIVER_JSON);
                PersonPNCDisqualifiedResponse pncResponse = (PersonPNCDisqualifiedResponse) JsonUtil.getInstance().toModel(strJson, PersonPNCDisqualifiedResponse.class);
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (pncResponse != null) {
                        List<PersonPNCDisqualifiedResponse.DisqualifiedDriverListBean> aDisqualifiedDriverListBean = pncResponse.getDisqualifieddriverlist();
                        loadDisqualifiedData(aDisqualifiedDriverListBean);
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }
                });
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), PersonPNCDetailDialogFragment.class, "loadPersonPNCJSON");
            }
        }).start();
    }

    /**
     * Load PNC DNA JSON
     */
    public void loadDNAJSON() {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), GenericConstant.PERSON_PNC_DNA_JSON);
                PersonPNCDnaResponse pncResponse = (PersonPNCDnaResponse) JsonUtil.getInstance().toModel(strJson, PersonPNCDnaResponse.class);
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (pncResponse != null) {
                        List<PersonPNCDnaResponse.DNAListBean> aDNAListBean = pncResponse.getDnalist();
                        loadDNAData(aDNAListBean);
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }
                });
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), PersonPNCDetailDialogFragment.class, "loadDNAJSON");
            }
        }).start();
    }

    /**
     * Load PNC Photo Location JSON
     */
    public void loadPhotoLocationJSON() {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), GenericConstant.PERSON_PNC_PHOTO_LOC_JSON);
                PersonPNCPhotoLocResponse pncResponse = (PersonPNCPhotoLocResponse) JsonUtil.getInstance().toModel(strJson, PersonPNCPhotoLocResponse.class);
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (pncResponse != null) {
                        List<PersonPNCPhotoLocResponse.PhotoLocationListBean> aPhotoLocationListBean = pncResponse.getPhotolocationlist();
                        loadPhotoLocData(aPhotoLocationListBean);
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }
                });
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), PersonPNCDetailDialogFragment.class, "loadPhotoLocationJSON");
            }
        }).start();
    }

    /**
     * Load the PNC Address
     */
    private void loadAddressData(List<AddressesBean> addressesBeans) {

        llAddressesContent.removeAllViews();
        if (addressesBeans != null && addressesBeans.size() > 0) {
            for (int i = 0; i < addressesBeans.size(); i++) {

                AddressesBean mAddressesBean = addressesBeans.get(i);
                View view = layoutInflater.inflate(R.layout.pnc_person_address_items, null);
                TextView tvDescValue = view.findViewById(R.id.tvDescValue);
                TextView tvAddressDescValue = view.findViewById(R.id.tvAddressDescValue);
                TextView tvDateAtValue = view.findViewById(R.id.tvDateAtValue);
                TextView tvDateUpdatedValue = view.findViewById(R.id.tvDateUpdatedValue);

                tvDescValue.setText(mAddressesBean.getDescription());
                tvAddressDescValue.setText(mAddressesBean.getAddressdescription());
                tvDateAtValue.setText(mAddressesBean.getDateat());
                tvDateUpdatedValue.setText(mAddressesBean.getDateupdated());

                llAddressesContent.addView(view);
            }
        }
    }

    /**
     * Load the PNC Alias Data
     */
    private void loadAliasData(List<AliasListBean> aliasListBeans) {

        llAliasNicknameContent.removeAllViews();
        if (aliasListBeans != null && aliasListBeans.size() > 0) {
            for (int i = 0; i < aliasListBeans.size(); i++) {

                AliasListBean mAliasListBean = aliasListBeans.get(i);
                View view = layoutInflater.inflate(R.layout.pnc_person_alias_items, null);
                TextView tvNameValue = view.findViewById(R.id.tvNameValue);
                TextView tvOwnerNameValue = view.findViewById(R.id.tvOwnerNameValue);
                TextView tvDateUpdatedValue = view.findViewById(R.id.tvDateUpdatedValue);
                TextView tvDOBValue = view.findViewById(R.id.tvDOBValue);

                tvNameValue.setText(mAliasListBean.getName());
                tvOwnerNameValue.setText(mAliasListBean.getOwnername());
                tvDateUpdatedValue.setText(mAliasListBean.getDateupdated());

                llAliasNicknameContent.addView(view);
            }
        }
    }


    /**
     * Load the PNC Description Data
     */
    private void loadDescriptionData(List<PersonPNCDescriptionResponse.PersonDescriptionListBean> personDescriptionListBeans) {

        llDescriptionContent.removeAllViews();
        if (personDescriptionListBeans != null && personDescriptionListBeans.size() > 0) {
            for (int i = 0; i < personDescriptionListBeans.size(); i++) {

                PersonPNCDescriptionResponse.PersonDescriptionListBean mPersonDescriptionListBean = personDescriptionListBeans.get(i);

                View view = layoutInflater.inflate(R.layout.pnc_person_description_items, null);
                TextView tvAccentValue = view.findViewById(R.id.tvAccentValue);
                TextView tvBuildValue = view.findViewById(R.id.tvBuildValue);
                TextView tvEyeColorValue = view.findViewById(R.id.tvEyeColorValue);
                TextView tvGlassesValue = view.findViewById(R.id.tvGlassesValue);
                TextView tvHandednessValue = view.findViewById(R.id.tvHandednessValue);
                TextView tvHeightValue = view.findViewById(R.id.tvHeightValue);
                TextView tvHairValue = view.findViewById(R.id.tvHairValue);
                TextView tvFacialHairValue = view.findViewById(R.id.tvFacialHairValue);

                tvAccentValue.setText(mPersonDescriptionListBean.getAccent());
                tvBuildValue.setText(mPersonDescriptionListBean.getBuild());
                tvEyeColorValue.setText(mPersonDescriptionListBean.getEyecolour());
                tvGlassesValue.setText(mPersonDescriptionListBean.getGlasses());
                tvHandednessValue.setText(mPersonDescriptionListBean.getHandedness());
                tvHeightValue.setText(mPersonDescriptionListBean.getHeight());
                tvHairValue.setText(mPersonDescriptionListBean.getHair());
                tvFacialHairValue.setText(mPersonDescriptionListBean.getFacialhair());

                llDescriptionContent.addView(view);
            }
        }
    }

    /**
     * Load the PNC Disqualified Data
     */
    private void loadDisqualifiedData(List<PersonPNCDisqualifiedResponse.DisqualifiedDriverListBean> disqualifiedDriverListBeans) {

        llDriverReportContent.removeAllViews();
        if (disqualifiedDriverListBeans != null && disqualifiedDriverListBeans.size() > 0) {
            for (int i = 0; i < disqualifiedDriverListBeans.size(); i++) {

                PersonPNCDisqualifiedResponse.DisqualifiedDriverListBean mDisqualifiedDriverListBean = disqualifiedDriverListBeans.get(i);

                View view = layoutInflater.inflate(R.layout.pnc_person_disqualified_items, null);
                TextView tvStatusValue = view.findViewById(R.id.tvStatusValue);
                TextView tvCourtValue = view.findViewById(R.id.tvCourtValue);
                TextView tvTextValue = view.findViewById(R.id.tvTextValue);
                TextView tvNoteValue = view.findViewById(R.id.tvNoteValue);
                TextView tvDOEValue = view.findViewById(R.id.tvDOEValue);

                tvStatusValue.setText(mDisqualifiedDriverListBean.getStatus());
                tvCourtValue.setText(mDisqualifiedDriverListBean.getCourt());
                tvTextValue.setText(mDisqualifiedDriverListBean.getText());
                tvNoteValue.setText(mDisqualifiedDriverListBean.getNote());
                tvDOEValue.setText(mDisqualifiedDriverListBean.getDateeffectivefrom());

                llDriverReportContent.addView(view);
            }
        }
    }

    /**
     * Load the PNC Disqualified Data
     */
    private void loadDNAData(List<PersonPNCDnaResponse.DNAListBean> dnaListBeans) {

        llDNAReportContent.removeAllViews();
        if (dnaListBeans != null && dnaListBeans.size() > 0) {
            for (int i = 0; i < dnaListBeans.size(); i++) {

                PersonPNCDnaResponse.DNAListBean mDNAListBean = dnaListBeans.get(i);

                View view = layoutInflater.inflate(R.layout.pnc_person_dna_items, null);
                TextView tvSampleBarcodeValue = view.findViewById(R.id.tvSampleBarcodeValue);
                TextView tvAsRefValue = view.findViewById(R.id.tvAsRefValue);
                TextView tvStatusValue = view.findViewById(R.id.tvStatusValue);
                TextView tvSampleDateValue = view.findViewById(R.id.tvSampleDateValue);
                TextView tvSentLabValue = view.findViewById(R.id.tvSentLabValue);
                TextView tvFSRefValue = view.findViewById(R.id.tvFSRefValue);
                TextView tvSampleTypeValue = view.findViewById(R.id.tvSampleTypeValue);

                tvSampleBarcodeValue.setText(mDNAListBean.getSamplebarcode());
                tvAsRefValue.setText(mDNAListBean.getAsref());
                tvStatusValue.setText(mDNAListBean.getDnastatus());
                tvSampleDateValue.setText(mDNAListBean.getDateofsample());
                tvSentLabValue.setText(mDNAListBean.getSenttolab());
                tvFSRefValue.setText(mDNAListBean.getFsref());
                tvSampleTypeValue.setText(mDNAListBean.getSampletype());

                llDNAReportContent.addView(view);
            }
        }
    }

    /**
     * Load the PNC Photo Location Data
     */
    private void loadPhotoLocData(List<PersonPNCPhotoLocResponse.PhotoLocationListBean> photoLocationListBeans) {

        llPhotoLocContent.removeAllViews();
        if (photoLocationListBeans != null && photoLocationListBeans.size() > 0) {
            for (int i = 0; i < photoLocationListBeans.size(); i++) {

                PersonPNCPhotoLocResponse.PhotoLocationListBean mPhotoLocationListBean = photoLocationListBeans.get(i);

                View view = layoutInflater.inflate(R.layout.pnc_person_photo_loc_items, null);
                TextView tvCreatorValue = view.findViewById(R.id.tvCreatorValue);
                TextView tvDOCValue = view.findViewById(R.id.tvDOCValue);
                TextView tvOwnerValue = view.findViewById(R.id.tvOwnerValue);
                TextView tvPhotoRefNoValue = view.findViewById(R.id.tvPhotoRefNoValue);
                TextView tvDateUpdatedValue = view.findViewById(R.id.tvDateUpdatedValue);

                tvCreatorValue.setText(mPhotoLocationListBean.getCreator());
                tvDOCValue.setText(mPhotoLocationListBean.getDateofcreation());
                tvOwnerValue.setText(mPhotoLocationListBean.getOwner());
                tvPhotoRefNoValue.setText(mPhotoLocationListBean.getRefnumberphotoid());
                tvDateUpdatedValue.setText(mPhotoLocationListBean.getUpdated());

                llPhotoLocContent.addView(view);
            }
        }
    }

}
