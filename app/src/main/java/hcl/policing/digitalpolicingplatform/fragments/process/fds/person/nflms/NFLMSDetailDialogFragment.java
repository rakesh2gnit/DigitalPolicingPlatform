package hcl.policing.digitalpolicingplatform.fragments.process.fds.person.nflms;

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

import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.map.FDSMapActivity;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.person.athena.AthenaListDialogFragment;
import hcl.policing.digitalpolicingplatform.listeners.dialog.ManuallyClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.PersonSelectionListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.nflms.AddressNFLMSDetailResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.nflms.NflmsDetailsResponseModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.nflms.PersonNFLMSDetailResponse;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class NFLMSDetailDialogFragment extends DialogFragment implements View.OnClickListener {

    private Context mContext;
    private LinearLayout llDetails,llHeader, nflms_detail, llWeaponDetails, llPersonDetailsContent, llWeaponDetailsContent, llAddressDetailsContent;
    private NflmsDetailsResponseModel nflmsDetailsResponseModel = null;
    private Dialog mProgressDialog;
    private ManuallyClickListener manuallyClickListener;
    private PersonSelectionListener personSelectionListener;
    private int flowType;
    private List<NflmsDetailsResponseModel.LstPersonBean> aLstPersonBean;
    private List<NflmsDetailsResponseModel.LstWeaponBean> aLstWeaponBean;
    private List<NflmsDetailsResponseModel.LstAddressBean> aLstAddressBean;
    private LayoutInflater layoutInflater;
    private boolean isPopulate;

    public static NFLMSDetailDialogFragment newInstance(int type,boolean isPopulate) {

        NFLMSDetailDialogFragment frag = new NFLMSDetailDialogFragment();
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

        View rootView = inflater.inflate(R.layout.nflms_detail_fragment, container, false);
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
        nflms_detail = view.findViewById(R.id.nflms_detail);
        llWeaponDetails = nflms_detail.findViewById(R.id.llWeaponDetails);
        llPersonDetailsContent = nflms_detail.findViewById(R.id.llPersonDetailsContent);
        llWeaponDetailsContent = nflms_detail.findViewById(R.id.llWeaponDetailsContent);
        llAddressDetailsContent = nflms_detail.findViewById(R.id.llAddressDetailsContent);

        RelativeLayout rlPersonDetails = nflms_detail.findViewById(R.id.rlPersonDetails);
        RelativeLayout rlWeaponDetails = nflms_detail.findViewById(R.id.rlWeaponDetails);
        RelativeLayout rlAddressDetails = nflms_detail.findViewById(R.id.rlAddressDetails);

        isPopulateView();
        loadHeaderLayout();

        if (flowType == GenericConstant.TYPE_PERSON) {
            loadNFLMSJSONDetails(GenericConstant.PERSON_NFLMS_DETAILS_JSON, flowType);

        } else if (flowType == GenericConstant.TYPE_ADDRESS) {
            loadNFLMSJSONDetails(GenericConstant.ADDRESS_NFLMS_DETAILS_JSON, flowType);

        }

        rlPersonDetails.setOnClickListener(this);
        rlWeaponDetails.setOnClickListener(this);
        rlAddressDetails.setOnClickListener(this);
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
            tvHeader.setText(getString(R.string.nflms_nominal_header));

        } else if (flowType == GenericConstant.TYPE_ADDRESS) {
            tvHeader.setText(getString(R.string.nflms_address_header));

        }

        ivBack.setOnClickListener(this);
    }


    /**
     * Load the Person Details
     */
    private void loadPersonData() {


        llPersonDetailsContent.removeAllViews();
        if (aLstPersonBean != null && aLstPersonBean.size() > 0) {
            for (int i = 0; i < aLstPersonBean.size(); i++) {

                NflmsDetailsResponseModel.LstPersonBean mLstPersonBean = aLstPersonBean.get(i);

                View view = layoutInflater.inflate(R.layout.nflms_person_details_items, null);
                TextView tvNameValue = view.findViewById(R.id.tvNameValue);
                TextView tvDOBValue = view.findViewById(R.id.tvDOBValue);
                TextView tvCompanyNameValue = view.findViewById(R.id.tvCompanyNameValue);
                TextView tvHeightValue = view.findViewById(R.id.tvHeightValue);
                TextView tvTelephoneNoValue = view.findViewById(R.id.tvTelephoneNoValue);
                TextView tvPersonNoValue = view.findViewById(R.id.tvPersonNoValue);
                TextView tvPNCIdValue = view.findViewById(R.id.tvPNCIdValue);

                tvNameValue.setText(mLstPersonBean.getForename() + " " + mLstPersonBean.getSurname());
                tvDOBValue.setText(mLstPersonBean.getBirthdate());
                tvCompanyNameValue.setText(mLstPersonBean.getCompanyname());
                tvHeightValue.setText(mLstPersonBean.getHeight());
                tvTelephoneNoValue.setText(mLstPersonBean.getTelephonenumber());
                tvPersonNoValue.setText(mLstPersonBean.getPersonnumber());
                tvPNCIdValue.setText(mLstPersonBean.getPncId());
                llPersonDetailsContent.addView(view);
            }
        }
    }

    /**
     * Load the Weapon Details
     */
    private void loadWeaponData() {


        llWeaponDetailsContent.removeAllViews();
        if (aLstWeaponBean != null && aLstWeaponBean.size() > 0) {
            for (int i = 0; i < aLstWeaponBean.size(); i++) {

                NflmsDetailsResponseModel.LstWeaponBean mLstWeaponBean = aLstWeaponBean.get(i);

                View view = layoutInflater.inflate(R.layout.nflms_weapon_details_items, null);
                TextView tvActionDescValue = view.findViewById(R.id.tvActionDescValue);
                TextView tvCaliberDescValue = view.findViewById(R.id.tvCaliberDescValue);
                TextView tvCertificateNoValue = view.findViewById(R.id.tvCertificateNoValue);
                TextView tvCertificateTypeValue = view.findViewById(R.id.tvCertificateTypeValue);
                TextView tvExpiryDateValue = view.findViewById(R.id.tvExpiryDateValue);
                TextView tvMdfDescValue = view.findViewById(R.id.tvMdfDescValue);
                TextView tvSecurityTypeDescValue = view.findViewById(R.id.tvSecurityTypeDescValue);
                TextView tvSerialNoValue = view.findViewById(R.id.tvSerialNoValue);
                TextView tvStatusValue = view.findViewById(R.id.tvStatusValue);
                TextView tvValidFromValue = view.findViewById(R.id.tvValidFromValue);
                TextView tvWeaponLocValue = view.findViewById(R.id.tvWeaponLocValue);
                TextView tvWeaponTypeValue = view.findViewById(R.id.tvWeaponTypeValue);


                tvActionDescValue.setText(mLstWeaponBean.getActiondescription());
                tvCaliberDescValue.setText(mLstWeaponBean.getCalliberdescription());
                tvCertificateNoValue.setText(mLstWeaponBean.getCertificatenumber());
                tvCertificateTypeValue.setText(mLstWeaponBean.getCertificatetype());
                tvExpiryDateValue.setText(mLstWeaponBean.getExpirydate());
                tvMdfDescValue.setText(mLstWeaponBean.getManufacturerdescription());
                tvSecurityTypeDescValue.setText(mLstWeaponBean.getSecuritytypedescription());
                tvSerialNoValue.setText(mLstWeaponBean.getSerialnumber());
                tvStatusValue.setText(mLstWeaponBean.getStatus());
                tvValidFromValue.setText(mLstWeaponBean.getValidfrom());
                tvWeaponLocValue.setText(mLstWeaponBean.getWeaponlocation());
                tvWeaponTypeValue.setText(mLstWeaponBean.getWeapontypedescription());

                llWeaponDetailsContent.addView(view);
            }
        }
    }

    /**
     * Load the Address Details
     */
    private void loadAddressData() {

        llAddressDetailsContent.removeAllViews();
        if (aLstAddressBean != null && aLstAddressBean.size() > 0) {
            for (int i = 0; i < aLstAddressBean.size(); i++) {

                NflmsDetailsResponseModel.LstAddressBean mLstAddressBean = aLstAddressBean.get(i);

                View view = layoutInflater.inflate(R.layout.nflms_address_details_items, null);
                TextView tvAddressValue = view.findViewById(R.id.tvAddressValue);
                TextView tvBlue8RefValue = view.findViewById(R.id.tvBlue8RefValue);
                TextView tvContactCharacterValue = view.findViewById(R.id.tvContactCharacterValue);
                TextView tvGridRefValue = view.findViewById(R.id.tvGridRefValue);
                TextView tvValidFromValue = view.findViewById(R.id.tvValidFromValue);
                TextView tvPersonNoValue = view.findViewById(R.id.tvPersonNoValue);
                TextView tvPostcodeValue = view.findViewById(R.id.tvPostcodeValue);
                TextView tvSecurityTypeValue = view.findViewById(R.id.tvSecurityTypeValue);
                TextView tvTelephoneNoValue = view.findViewById(R.id.tvTelephoneNoValue);
                TextView tvUPRNValue = view.findViewById(R.id.tvUPRNValue);


                StringBuilder sbAddress = new StringBuilder();
                sbAddress.append(mLstAddressBean.getAddressline1()).append("\n").append(mLstAddressBean.getAddressline2()).append("\n")
                        .append(mLstAddressBean.getAddressline3()).append("\n").append(mLstAddressBean.getAddressline4());

                tvAddressValue.setText(sbAddress.toString());
                tvBlue8RefValue.setText("");
                tvContactCharacterValue.setText(mLstAddressBean.getContactcharacteristicId());
                tvGridRefValue.setText(mLstAddressBean.getGridreference());
                tvValidFromValue.setText("");
                tvPersonNoValue.setText(mLstAddressBean.getPersonnumber());
                tvTelephoneNoValue.setText(mLstAddressBean.getTelephonenumber());
                tvPostcodeValue.setText(mLstAddressBean.getPostcode());
                tvSecurityTypeValue.setText(mLstAddressBean.getSecuritytypedescription());
                tvUPRNValue.setText(mLstAddressBean.getUprn());


                tvAddressValue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FDSMapActivity.callMapActivity(mContext, sbAddress.toString());
                    }
                });

                llAddressDetailsContent.addView(view);
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

            case R.id.rlPersonDetails:

                if (llPersonDetailsContent.getVisibility() != View.VISIBLE) {
                    llPersonDetailsContent.setVisibility(View.VISIBLE);
                    loadPersonData();
                } else {
                    llPersonDetailsContent.setVisibility(View.GONE);
                }

                break;
            case R.id.rlWeaponDetails:

                if (llWeaponDetailsContent.getVisibility() != View.VISIBLE) {
                    llWeaponDetailsContent.setVisibility(View.VISIBLE);
                    loadWeaponData();
                } else {
                    llWeaponDetailsContent.setVisibility(View.GONE);
                }

                break;

            case R.id.rlAddressDetails:

                if (llAddressDetailsContent.getVisibility() != View.VISIBLE) {
                    llAddressDetailsContent.setVisibility(View.VISIBLE);
                    loadAddressData();
                } else {
                    llAddressDetailsContent.setVisibility(View.GONE);
                }

                break;
        }

    }


    /**
     * Load List Count
     */
    private void loadlistCount() {

        TextView tvPersonDetailsCount = nflms_detail.findViewById(R.id.tvPersonDetailsCount);
        TextView tvWeaponDetailsCount = nflms_detail.findViewById(R.id.tvWeaponDetailsCount);
        TextView tvAddressDetailsCount = nflms_detail.findViewById(R.id.tvAddressDetailsCount);

        if (aLstPersonBean != null && aLstPersonBean.size() > 0) {
            tvPersonDetailsCount.setText(String.valueOf(aLstPersonBean.size()));
        }
        if (aLstWeaponBean != null && aLstWeaponBean.size() > 0) {
            llWeaponDetails.setVisibility(View.VISIBLE);
            tvWeaponDetailsCount.setText(String.valueOf(aLstWeaponBean.size()));
        }
        if (aLstAddressBean != null && aLstAddressBean.size() > 0) {
            tvAddressDetailsCount.setText(String.valueOf(aLstAddressBean.size()));
        }
    }

    /**
     * Load Person NFLMS JSON
     */
    public void loadNFLMSJSONDetails(String fileName, int type) {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), fileName);

                if (flowType == GenericConstant.TYPE_PERSON) {
                    PersonNFLMSDetailResponse personNFLMSDetailResponse = (PersonNFLMSDetailResponse) JsonUtil.getInstance().toModel(strJson, PersonNFLMSDetailResponse.class);
                    if (personNFLMSDetailResponse != null) {
                        nflmsDetailsResponseModel = personNFLMSDetailResponse.getNflmsdetailsresponse();
                    }
                } else if (flowType == GenericConstant.TYPE_ADDRESS) {
                    AddressNFLMSDetailResponse addressNFLMSDetailResponse = (AddressNFLMSDetailResponse) JsonUtil.getInstance().toModel(strJson, AddressNFLMSDetailResponse.class);
                    if (addressNFLMSDetailResponse != null) {
                        nflmsDetailsResponseModel = addressNFLMSDetailResponse.getNflmsdetailsresponse();
                    }
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (nflmsDetailsResponseModel != null) {
                        aLstPersonBean = nflmsDetailsResponseModel.getLstperson();
                        aLstWeaponBean = nflmsDetailsResponseModel.getLstweapon();
                        aLstAddressBean = nflmsDetailsResponseModel.getLstaddress();
                        loadlistCount();
                        llPersonDetailsContent.setVisibility(View.VISIBLE);
                        loadPersonData();
                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), "No details available!");
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), AthenaListDialogFragment.class, "loadAthenaPersonDetails");
            }
        }).start();
    }
}
