package hcl.policing.digitalpolicingplatform.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.micSearch.MicSearchActivity;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.process.edit.EditAnswerActivity;
import hcl.policing.digitalpolicingplatform.activities.process.edit.PopulateEditFields;
import hcl.policing.digitalpolicingplatform.activities.process.flow.DialogValue;
import hcl.policing.digitalpolicingplatform.activities.process.flow.PopulateFields;
import hcl.policing.digitalpolicingplatform.activities.process.flow.SearchRequest;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.fieldName.FieldsNameConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomLinearLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomRelativeLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomSwitchButton;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextInputLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextView;
import hcl.policing.digitalpolicingplatform.customLibraries.layoutHelper.CreateLayout;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.SubProcessDialogFragment;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.AnswerValueDTO;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.LayoutFieldHelper;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.models.process.PageSection_detailListBean;
import hcl.policing.digitalpolicingplatform.models.search.SearchListBean;
import hcl.policing.digitalpolicingplatform.utils.speak.SpeakToPersonUtil;

public class SearchDialogUtil {

    private static Dialog searchDialog;
    private static LinearLayout llFields;
    private static List<PageSection_detailListBean> pageSection_detailList;
    private static ProcessCreationActivity mActivityRef;
    private static EditAnswerActivity mActivityEditRef;
    public static JSONObject searchObject;
    private static TextView tvRecent, tvSearch, tvHeader;
    public static ArrayList<AnswerValueDTO> answerList;
    private static SearchListBean mSearchList;

    /**
     * Load Dynamic Search dialog for FDS
     *
     * @param context
     * @param aSearchList
     * @param searchType
     * @param addressList
     */
    public static void createSearchDialog(MicSearchActivity context, List<SearchListBean> aSearchList, String searchType,
                                          List<Address> addressList, String speakingType, String result) {

        searchDialog = new Dialog(context, R.style.CustomDialogAnimation);
        searchDialog.setCanceledOnTouchOutside(false);
        searchDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(searchDialog.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        searchDialog.setContentView(R.layout.dialog_search);
        Objects.requireNonNull(searchDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Window window = searchDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        tvHeader = searchDialog.findViewById(R.id.tv_header);
        tvRecent = searchDialog.findViewById(R.id.tv_recent);
        llFields = searchDialog.findViewById(R.id.ll_add);
        tvSearch = searchDialog.findViewById(R.id.tv_search);

        tvRecent.setVisibility(View.GONE);
        llFields.removeAllViews();

        pageSection_detailList = new ArrayList<>();
        for (int i = 0; i < aSearchList.size(); i++) {
            if (aSearchList.get(i).getSearch_Name().equalsIgnoreCase(searchType)) {
                mSearchList = aSearchList.get(i);
                pageSection_detailList.addAll(aSearchList.get(i).getPageSection_detailList());
                tvHeader.setText(aSearchList.get(i).getDialogHeading());
                break;
            }
        }

        searchObject = new JSONObject();
        searchObject = SearchRequest.createSearchRequest(pageSection_detailList);

        if (searchType.equalsIgnoreCase(GenericConstant.SEARCH_ADDRESS)) {
            setLocation(addressList);
        }

        answerList = new ArrayList<>();
        for (int i = 0; i < pageSection_detailList.size(); i++) {
            createAttributes(context, pageSection_detailList.get(i));
            AnswerValueDTO answerDTO = new AnswerValueDTO();
            answerDTO.setKey(pageSection_detailList.get(i).getField_Name());
            answerDTO.setValue("");
            answerDTO.setDependentOn(pageSection_detailList.get(i).getField_Dependent_On());
            answerDTO.setId(pageSection_detailList.get(i).getField_Id());
            answerDTO.setSelectLogic(pageSection_detailList.get(i).getSelect_Logic());
            answerDTO.setMendatry(pageSection_detailList.get(i).isField_Mendatry());
            answerList.add(answerDTO);
        }

        tvSearch.setOnClickListener(v -> {
            // Call next section once the details is saved.
            callSearch(context, searchType);
        });

        if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_PERSON)) {

            String firstName = null, middleName = null, lastName = null, reason = null;
            if (speakingType != null && speakingType.equalsIgnoreCase(GenericConstant.SPEAKING_NAME)) {
                String[] breakAnswerValue = result.split("\\s+");
                if(breakAnswerValue.length > 1) {
                    String fName = result.substring(0, result.lastIndexOf(" "));
                    lastName = result.substring(result.lastIndexOf(" ") + 1);

                    String[] breakfName = fName.split("\\s+");
                    if(breakfName.length > 1) {
                        firstName = fName.substring(0, fName.lastIndexOf(" "));
                        middleName = fName.substring(fName.lastIndexOf(" ") + 1);
                    } else {
                        firstName = fName;
                    }
                } else {
                    firstName = result;
                }
                for (int i = 0; i < answerList.size(); i++) {
                    if(answerList.get(i).getKey().equalsIgnoreCase("Forename")) {
                        if(firstName != null) {
                            answerList.get(i).setValue(firstName);
                        }
                    }
                    if(answerList.get(i).getKey().equalsIgnoreCase("Middle Name")) {
                        if(middleName != null) {
                            answerList.get(i).setValue(middleName);
                        }
                    }
                    if(answerList.get(i).getKey().equalsIgnoreCase("Surname")) {
                        if(lastName != null) {
                            answerList.get(i).setValue(lastName);
                        }
                    }
                }
                setValueInDialog(context, answerList);
            }
        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_VEHICLE)) {

            String vrm = null;
            if (speakingType != null && speakingType.equalsIgnoreCase(GenericConstant.SPEAKING_NAME)) {
                vrm = result;

                for (int i = 0; i < answerList.size(); i++) {
                    if(answerList.get(i).getKey().equalsIgnoreCase("VRM")) {
                        if(vrm != null) {
                            answerList.get(i).setValue(vrm);
                        }
                    }
                }
                setValueInDialog(context, answerList);
            }

        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_ADDRESS)) {

            if (context != null) {
                context.loadAddressDialog();
            }
        }  else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_INCIDENT)) {

            String incidentNo = null;
            if (speakingType != null && speakingType.equalsIgnoreCase(GenericConstant.SPEAKING_NAME)) {
                incidentNo = result;

                for (int i = 0; i < answerList.size(); i++) {
                    if(answerList.get(i).getKey().equalsIgnoreCase("Incident Number")) {
                        if(incidentNo != null) {
                            answerList.get(i).setValue(incidentNo);
                        }
                    }
                }
                setValueInDialog(context, answerList);
            }
        }

        searchDialog.show();
    }

    public static void callSearch(MicSearchActivity context, String searchType) {
        try {
            getValuesFromDialog(context);
            if (answerList != null && answerList.size() > 0) {

                for (int i = 0; i < answerList.size(); i++) {
                    if (answerList.get(i).isMendatry() && answerList.get(i).getValue().equalsIgnoreCase("")) {

                        context.isListen = true;
                        SpeakToPersonUtil.speakToPerson(context, context.textToSpeech, context.getResources().getString(R.string.mandatory_fields_require));
                        BasicMethodsUtil.getInstance().showToast(context, context.getResources().getString(R.string.mandatory_fields_require));
                        return;
                    }
                }
            }

            if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_PERSON)) {

                if (context != null) {
                    context.isListen = false;
                    SpeakToPersonUtil.speakToPerson(context, context.textToSpeech, "Ok, searching person");
                    context.loadPersonDialog();
                }

            } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_VEHICLE)) {

                if (context != null) {
                    context.isListen = false;
                    SpeakToPersonUtil.speakToPerson(context, context.textToSpeech, "Ok, searching vehicle");
                    context.loadVehicleDialog();
                }

            } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_ADDRESS)) {

                if (context != null) {
                    context.isListen = false;
                    SpeakToPersonUtil.speakToPerson(context, context.textToSpeech, "Ok, searching address");
                    context.loadAddressDialog();
                }

            }
            else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_INCIDENT)) {

                if (context != null) {
                    context.isListen = false;
                    SpeakToPersonUtil.speakToPerson(context, context.textToSpeech, "Ok, searching incident");
                    context.loadIncidentDialog();
                }

            }        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load Dynamic Search dialog for FDS
     *
     * @param context
     * @param aSearchList
     * @param searchType
     * @param addressList
     */
    public static void createSearchDialog(SubProcessDialogFragment context, List<SearchListBean> aSearchList, String searchType,
                                          List<Address> addressList) {

        searchDialog = new Dialog(context.getActivity(), R.style.CustomDialogAnimation);
        searchDialog.setCanceledOnTouchOutside(false);
        searchDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(searchDialog.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        searchDialog.setContentView(R.layout.dialog_search);
        Objects.requireNonNull(searchDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Window window = searchDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        TextView tvHeader = searchDialog.findViewById(R.id.tv_header);
        tvRecent = searchDialog.findViewById(R.id.tv_recent);
        llFields = searchDialog.findViewById(R.id.ll_add);
        TextView tvSearch = searchDialog.findViewById(R.id.tv_search);

        tvRecent.setVisibility(View.GONE);
        llFields.removeAllViews();

        pageSection_detailList = new ArrayList<>();
        for (int i = 0; i < aSearchList.size(); i++) {
            if (aSearchList.get(i).getSearch_Name().equalsIgnoreCase(searchType)) {
                mSearchList = aSearchList.get(i);
                pageSection_detailList.addAll(aSearchList.get(i).getPageSection_detailList());
                tvHeader.setText(aSearchList.get(i).getDialogHeading());
                break;
            }
        }

        searchObject = new JSONObject();
        searchObject = SearchRequest.createSearchRequest(pageSection_detailList);

        if (searchType.equalsIgnoreCase(GenericConstant.SEARCH_ADDRESS)) {
            setLocation(addressList);
        }

        answerList = new ArrayList<>();
        for (int i = 0; i < pageSection_detailList.size(); i++) {
            createAttributes(context.getActivity(), pageSection_detailList.get(i));
            AnswerValueDTO answerDTO = new AnswerValueDTO();
            answerDTO.setKey(pageSection_detailList.get(i).getField_Name());
            answerDTO.setValue("");
            answerDTO.setDependentOn(pageSection_detailList.get(i).getField_Dependent_On());
            answerDTO.setId(pageSection_detailList.get(i).getField_Id());
            answerDTO.setSelectLogic(pageSection_detailList.get(i).getSelect_Logic());
            answerDTO.setMendatry(pageSection_detailList.get(i).isField_Mendatry());
            answerList.add(answerDTO);
        }

        tvSearch.setOnClickListener(v -> {
            // Call next section once the details is saved.
            try {
                getValuesFromDialog(context.getActivity());
                if (answerList != null && answerList.size() > 0) {
                    int count = 0;
                    for (int i = 0; i < answerList.size(); i++) {
                        if (answerList.get(i).isMendatry() && answerList.get(i).getValue().equalsIgnoreCase("")) {

                            BasicMethodsUtil.getInstance().showToast(context.getActivity(), context.getResources().getString(R.string.mandatory_fields_require));
                            Log.e("Mendatry ", " SEARCH >>>>> " + answerList.get(i).getKey() + " >> " + answerList.get(i).getValue());
                            return;
                        }
                        if (answerList.get(i).getDependentOn() != 0 && answerList.get(i).getSelectLogic().equalsIgnoreCase(GenericConstant.LESSER_DATE)) {
                            for (int j = 0; j < answerList.size(); j++) {
                                if (answerList.get(i).getDependentOn() == answerList.get(j).getId()) {
                                    int answer = CompareDate.compareDates(answerList.get(j).getValue(), answerList.get(i).getValue());
                                    if (answer == 2) {
                                        return;
                                    }
                                    break;
                                }
                            }
                        }
                        if (answerList.get(i).getDependentOn() != 0 && answerList.get(i).getSelectLogic().equalsIgnoreCase(GenericConstant.GREATER_DATE)) {
                            for (int j = 0; j < answerList.size(); j++) {
                                if (answerList.get(i).getDependentOn() == answerList.get(j).getId()) {
                                    int answer = CompareDate.compareDates(answerList.get(j).getValue(), answerList.get(i).getValue());
                                    if (answer == 1) {
                                        return;
                                    }
                                    break;
                                }
                            }
                        }

                        if (answerList.get(i).getValue() != null && !answerList.get(i).getValue().equalsIgnoreCase("")) {
                            count = count + 1;
                        }
                    }
                    if (count < mSearchList.getMandatory_Count()) {
                        String error = "Please enter any of the " + mSearchList.getMandatory_Count() + " search criteria";
                        DialogUtil.errorDialog(context.getActivity(), error);
                        return;
                    }
                }
//                dismiss();

                if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_PERSON)) {

                    if (context != null) {
                        context.loadPersonDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_VEHICLE)) {

                    if (context != null) {
                        context.loadVehicleDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_ADDRESS)) {

                    if (context != null) {
                        context.loadAddressDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_INVESTIGATION)) {

                    if (context != null) {
                        context.loadInvestigationDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_CASES)) {

                    if (context != null) {
                        context.loadCasesDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_COMMUNICATION)) {

                    if (context != null) {
                        context.loadCommunicationDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_COURT_WARRANT)) {

                    if (context != null) {
                        context.loadCourtWarrantDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_DL_CHECK)) {

                    if (context != null) {
                        context.loadDLCheckDialog(answerList);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        /*searchDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            public void onCancel(DialogInterface dialog) {
                //isEditPageSection = false;
            }
        });*/
        searchDialog.show();
    }


    /**
     * Create Pages basis on the information of Page list
     *
     * @param pageSection_detailListBean
     */
    public static void createAttributes(Context act, PageSection_detailListBean pageSection_detailListBean) {

        try {
            if (pageSection_detailListBean != null) {

                // create Page here
                createDynamicFields(act, pageSection_detailListBean);
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), SearchDialogUtil.class, "createAttributes");
        }
    }

    /**
     * for creating dynamic fields in dialog
     *
     * @param act
     * @param pageSectionDetailListBean
     */
    private static void createDynamicFields(Context act, PageSection_detailListBean pageSectionDetailListBean) {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset(act, GenericConstant.PROPERTIES_JSON));
            Gson gson = new Gson();
            PropertiesBean propertiesBean = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBean.setId(pageSectionDetailListBean.getField_Id());
            propertiesBean.setHint(pageSectionDetailListBean.getField_Name());
            propertiesBean.setText(pageSectionDetailListBean.getField_Value());
            propertiesBean.setMendatry(pageSectionDetailListBean.isField_Mendatry());
            propertiesBean.setEnabled(pageSectionDetailListBean.isField_Enabled());
            propertiesBean.setVisibility(pageSectionDetailListBean.getField_Visibility());
            propertiesBean.setInputType(pageSectionDetailListBean.getField_Input_Type());
            propertiesBean.setKeyboardAction(pageSectionDetailListBean.getField_Action());

            LayoutFieldHelper layoutFieldHelper = new LayoutFieldHelper();
            layoutFieldHelper.setBasicLayout("");
            layoutFieldHelper.setViewType(pageSectionDetailListBean.getField_Type());
            layoutFieldHelper.setParentView(llFields);
            layoutFieldHelper.setPropertiesBean(propertiesBean/*pageSectionDetailListBean.getPropertiesBean()*/);
            layoutFieldHelper.setDropdownArray(pageSectionDetailListBean.getField_MasterData());
            layoutFieldHelper.setPageSectionDetailListBean(pageSectionDetailListBean);

            CreateLayout.createLayoutFields(act, layoutFieldHelper);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), SearchDialogUtil.class, "createDynamicFields");
        }
    }

    private static void setLocation(List<Address> addressList) {
        if (addressList != null && addressList.size() > 0) {

            if (pageSection_detailList != null && pageSection_detailList.size() > 0) {
                for (int j = 0; j < pageSection_detailList.size(); j++) {
                    if (pageSection_detailList.get(j).getField_Name().contains(FieldsNameConstant.FlatNumber)) {
                        if (addressList.get(0).getFeatureName() != null) {
                            pageSection_detailList.get(j).setField_Value(addressList.get(0).getFeatureName());
                        } else {
                            pageSection_detailList.get(j).setField_Value("");
                        }
                    }
                    if (pageSection_detailList.get(j).getField_Name().contains(FieldsNameConstant.BuildingNumber)) {
                        if (addressList.get(0).getFeatureName() != null) {
                            pageSection_detailList.get(j).setField_Value(addressList.get(0).getFeatureName());
                        } else {
                            pageSection_detailList.get(j).setField_Value("");
                        }
                    }
                    if (pageSection_detailList.get(j).getField_Name().contains(FieldsNameConstant.BuildingName)) {
                        if (addressList.get(0).getFeatureName() != null) {
                            pageSection_detailList.get(j).setField_Value(addressList.get(0).getFeatureName());
                        } else {
                            pageSection_detailList.get(j).setField_Value("");
                        }
                    }
                    if (pageSection_detailList.get(j).getField_Name().contains(FieldsNameConstant.FlatName)) {
                        if (addressList.get(0).getFeatureName() != null) {
                            pageSection_detailList.get(j).setField_Value(addressList.get(0).getFeatureName());
                        } else {
                            pageSection_detailList.get(j).setField_Value("");
                        }
                    }
                    if (pageSection_detailList.get(j).getField_Name().contains(FieldsNameConstant.StreetName)) {
                        if (addressList.get(0).getSubLocality() != null) {
                            pageSection_detailList.get(j).setField_Value(addressList.get(0).getSubLocality());
                        } else {
                            pageSection_detailList.get(j).setField_Value("");
                        }
                    }
                    if (pageSection_detailList.get(j).getField_Name().contains(FieldsNameConstant.City)) {
                        if (addressList.get(0).getLocality() != null) {
                            pageSection_detailList.get(j).setField_Value(addressList.get(0).getLocality());
                        } else {
                            pageSection_detailList.get(j).setField_Value("");
                        }
                    }
                    if (pageSection_detailList.get(j).getField_Name().contains(FieldsNameConstant.Country)) {
                        if (addressList.get(0).getCountryName() != null) {
                            pageSection_detailList.get(j).setField_Value(addressList.get(0).getCountryName());
                        } else {
                            pageSection_detailList.get(j).setField_Value("");
                        }
                    }
                    if (pageSection_detailList.get(j).getField_Name().contains(FieldsNameConstant.PostCode)) {
                        if (addressList.get(0).getPostalCode() != null) {
                            pageSection_detailList.get(j).setField_Value(addressList.get(0).getPostalCode());
                        } else {
                            pageSection_detailList.get(j).setField_Value("");
                        }
                    }
                }
            }
        }
    }

    /**
     * Set the value in dialog
     *
     * @param act
     * @param fieldArray
     */
    public static void setValueInDialog(MicSearchActivity act, ArrayList<AnswerValueDTO> fieldArray) {

        new Handler().postDelayed(() -> {
            try {
                int count = llFields.getChildCount();
                Log.e("CHILD COUNT ", "setValue in dialog >>>>> " + count);
                if ((fieldArray != null && fieldArray.size() > 0) && (count > 0)) {
                    for (int i = 0; i < fieldArray.size(); i++) {
                        Log.e("FIELD ", "HEADING >>>>> " + fieldArray.get(i).getKey() + " mendatry >>>>> " + fieldArray.get(i).isMendatry() + "\n");
                        View view = llFields.getChildAt(i);
                        if (view instanceof CustomTextInputLayout) {
                            CustomTextInputLayout textInputLayout = (CustomTextInputLayout) view;
                            EditText etFields = textInputLayout.getEditText();
                            if (etFields != null) {
                                if (etFields.getHint().toString().contains(fieldArray.get(i).getKey())) {
                                    if(etFields.getText() == null || etFields.getText().toString().equalsIgnoreCase(""))
                                        etFields.setText(fieldArray.get(i).getValue());
                                }
                            }
                            if (fieldArray.get(i).isMendatry() && fieldArray.get(i).getValue().equalsIgnoreCase("")) {
                                textInputLayout.setErrorEnabled(true);
                                textInputLayout.setError(act.getResources().getString(R.string.field_cannot_blank));
                            }
                        } else if (view instanceof CustomRelativeLayout) {
                            CustomRelativeLayout customRelativeLayout = (CustomRelativeLayout) view;

                            View vHeading = customRelativeLayout.getChildAt(1);
                            View v = customRelativeLayout.getChildAt(0);

                            if (vHeading instanceof CustomTextView) {
                                CustomTextView customTextView = (CustomTextView) vHeading;
                                if (customTextView.getText().toString().contains(fieldArray.get(i).getKey())) {
                                    if (v instanceof CustomTextView) {
                                        CustomTextView customText = (CustomTextView) v;
                                        if(customText.getText() == null || customText.getText().toString().equalsIgnoreCase(""))
                                            customText.setText(fieldArray.get(i).getValue());
                                        if (fieldArray.get(i).isMendatry() && fieldArray.get(i).getValue().equalsIgnoreCase("")) {
                                            customTextView.setTextColor(ContextCompat.getColor(act, R.color.red));
                                            customText.setBackground(ContextCompat.getDrawable(act, R.drawable.bg_red_corner));
                                        }
                                    }
                                }
                            }
                        } else if (view instanceof CustomSwitchButton) {
                            CustomSwitchButton customSwitchButton = (CustomSwitchButton) view;

                            if (customSwitchButton.getText().toString().contains(fieldArray.get(i).getKey())) {
                                if (fieldArray.get(i).getValue().toLowerCase().equalsIgnoreCase(act.getResources().getString(R.string.yes).toLowerCase())) {
                                    customSwitchButton.setChecked(true);
                                } else {
                                    customSwitchButton.setChecked(false);
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), SearchDialogUtil.class, "setValueInDialog");
            }
        }, 1500);

    }

    /**
     * Get the values from Dialogs and check validations
     *
     * @param act
     */
    public static void getValuesFromDialog(Context act) {
        try {

            int count = llFields.getChildCount();
            Log.e("CHILD COUNT ", "getValue from dialog >>>>> " + count);
            for (int i = 0; i < count; i++) {
                View view = llFields.getChildAt(i);

                if (view instanceof CustomTextInputLayout) {

                    CustomTextInputLayout textInputLayout = (CustomTextInputLayout) view;
                    answerList.get(i).setValue(textInputLayout.getEditText().getText().toString());
                    if (answerList.get(i).isMendatry() && answerList.get(i).getValue().equalsIgnoreCase("")) {
                        textInputLayout.setErrorEnabled(true);
                        textInputLayout.setError(act.getResources().getString(R.string.field_cannot_blank));
                    }
                } else if (view instanceof CustomRelativeLayout) {

                    CustomRelativeLayout customRelativeLayout = (CustomRelativeLayout) view;
                    View vHeading = customRelativeLayout.getChildAt(1);
                    View v = customRelativeLayout.getChildAt(0);

                    if (v instanceof CustomTextView) {
                        CustomTextView customTextHead = (CustomTextView) vHeading;
                        CustomTextView customTextView = (CustomTextView) v;
                        Log.e("TEXT", "VALUE >>>>> " + customTextView.getText().toString());
                        Log.e("TEXT", "Heading >>>>> " + customTextHead.getText().toString());
                        answerList.get(i).setValue(customTextView.getText().toString());
                        answerList.get(i).setCode(customTextView.getTag(customTextView));
                        if (answerList.get(i).isMendatry() && answerList.get(i).getValue().equalsIgnoreCase("")) {
                            customTextHead.setTextColor(ContextCompat.getColor(act, R.color.red));
                            customTextView.setBackground(ContextCompat.getDrawable(act, R.drawable.bg_red_corner));
                        }
                        if (answerList.get(i).getDependentOn() != 0 && answerList.get(i).getSelectLogic().equalsIgnoreCase(GenericConstant.LESSER_DATE)) {
                            for (int j = 0; j < answerList.size(); j++) {
                                if (answerList.get(i).getDependentOn() == answerList.get(j).getId()) {
                                    int answer = CompareDate.compareDates(answerList.get(j).getValue(), answerList.get(i).getValue());
                                    if (answer == 2) {
                                        customTextHead.setTextColor(ContextCompat.getColor(act, R.color.red));
                                        customTextView.setBackground(ContextCompat.getDrawable(act, R.drawable.bg_red_corner));
                                        String error = answerList.get(i).getKey() + " can not be greater than " + answerList.get(j).getKey();
                                        DialogUtil.errorDialog(act, error);
                                        break;
                                    }
                                    break;
                                }
                            }
                        }
                        if (answerList.get(i).getDependentOn() != 0 && answerList.get(i).getSelectLogic().equalsIgnoreCase(GenericConstant.GREATER_DATE)) {
                            for (int j = 0; j < answerList.size(); j++) {
                                if (answerList.get(i).getDependentOn() == answerList.get(j).getId()) {
                                    Log.e("GREATER DATES ", " >> " + answerList.get(j).getValue() + " >>>>> " + answerList.get(i).getValue());
                                    int answer = CompareDate.compareDates(answerList.get(j).getValue(), answerList.get(i).getValue());
                                    if (answer == 1) {
                                        customTextHead.setTextColor(ContextCompat.getColor(act, R.color.red));
                                        customTextView.setBackground(ContextCompat.getDrawable(act, R.drawable.bg_red_corner));
                                        String error = answerList.get(i).getKey() + " can not be lesser than " + answerList.get(j).getKey();
                                        DialogUtil.errorDialog(act, error);
                                        break;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                } else if (view instanceof CustomLinearLayout) {
                    CustomLinearLayout customLinearLayout = (CustomLinearLayout) view;
                    View vTextV = customLinearLayout.getChildAt(0);
                    View vLinearHorizontal = customLinearLayout.getChildAt(1);

                    CustomTextView customTextHead = null;
                    CustomLinearLayout customLinearH = null;

                    if (vTextV instanceof CustomTextView) {
                        customTextHead = (CustomTextView) vTextV;
                    }
                    if (vLinearHorizontal instanceof CustomLinearLayout) {
                        customLinearH = (CustomLinearLayout) vLinearHorizontal;
                    }

                    String answerText = SearchRequest.getSavedAnswer(answerList.get(i).getKey());
                    if (answerText != null && !answerText.equalsIgnoreCase("")) {
                        answerList.get(i).setValue(answerText);
                    }

                    if (answerList.get(i).isMendatry()) {
                        if (answerText == null || answerText.equalsIgnoreCase("")) {
                            if (customTextHead != null)
                                customTextHead.setTextColor(ContextCompat.getColor(act, R.color.red));

                            if (customTextHead != null)
                                customLinearH.setBackground(ContextCompat.getDrawable(act, R.drawable.bg_red_corner));
                        }
                    }
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DialogValue.class, "getValuesFromDialog");
        }
    }

    /**
     * Load Json from Assets
     *
     * @param context
     * @param filename
     * @return
     */
    private static String loadJSONFromAsset(Context context, String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, GenericConstant.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static void dismiss() {
        if (searchDialog != null && searchDialog.isShowing()) {
            searchDialog.dismiss();
            searchDialog = null;
        }
    }
}
