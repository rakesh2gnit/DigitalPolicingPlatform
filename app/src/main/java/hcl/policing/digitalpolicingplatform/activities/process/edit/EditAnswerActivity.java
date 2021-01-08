package hcl.policing.digitalpolicingplatform.activities.process.edit;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.activities.map.MapActivity;
import hcl.policing.digitalpolicingplatform.activities.officer.OfficerSearchActivity;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.process.flow.DialogValue;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.fieldName.FieldsNameConstant;
import hcl.policing.digitalpolicingplatform.constants.viewProperties.ViewPropertiesConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomButton;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomLinearLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.layoutHelper.CreateDynamicView;
import hcl.policing.digitalpolicingplatform.customLibraries.layoutHelper.CreateLayout;
import hcl.policing.digitalpolicingplatform.fragments.process.SearchDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.allegation.AllegationCustomDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.crimegroup.CrimeGroupCustomDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.event.EventCustomDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.address.AddressCustomDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.person.PersonCustomDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.team.TeamCustomDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.vehicle.VehicleCustomDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.offence.OffenceCustomDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.organisation.OrganisationCustomDialogFragment;
import hcl.policing.digitalpolicingplatform.listeners.dialog.DialogCancelListener;
import hcl.policing.digitalpolicingplatform.listeners.dialog.ManuallyClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.allegation.AllegationSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.crimegroup.CrimeGroupSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.event.EventSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.address.AddressSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.PersonSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.team.TeamSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.vehicle.VehicleSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.offence.OffenceSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.organisation.OrganisationSelectionListener;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.AnswerValueDTO;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.LayoutFieldHelper;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.models.process.ExpectedAnswerListBean;
import hcl.policing.digitalpolicingplatform.models.process.ExpectedQuestionListBean;
import hcl.policing.digitalpolicingplatform.models.process.PageSectionListBean;
import hcl.policing.digitalpolicingplatform.models.process.PageSection_detailListBean;
import hcl.policing.digitalpolicingplatform.models.process.crime.CrimeGroupList;
import hcl.policing.digitalpolicingplatform.models.process.crime.EventSearchList;
import hcl.policing.digitalpolicingplatform.models.process.crime.HOOffenceList;
import hcl.policing.digitalpolicingplatform.models.process.crime.OffenceDefinitionList;
import hcl.policing.digitalpolicingplatform.models.process.crime.OrganisationDetailsList;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.AddressBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.PersonBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.team.TeamBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.VehicleBean;
import hcl.policing.digitalpolicingplatform.models.process.officer.UserBean;
import hcl.policing.digitalpolicingplatform.models.process.populate.PopulateListBean;
import hcl.policing.digitalpolicingplatform.models.process.populate.PopulateSectionListBean;
import hcl.policing.digitalpolicingplatform.models.process.populate.PopulateSubSectionListBean;
import hcl.policing.digitalpolicingplatform.models.process.populate.Populate_DetailListBean;
import hcl.policing.digitalpolicingplatform.models.search.SearchListBean;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.services.MyLocationServices;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.CompareDate;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;
import hcl.policing.digitalpolicingplatform.utils.LocationUtil;
import hcl.policing.digitalpolicingplatform.utils.SearchDialogUtil;
import hcl.policing.digitalpolicingplatform.utils.Utilities;
import hcl.policing.digitalpolicingplatform.utils.speak.SpeakToPhoneUtil;

public class EditAnswerActivity extends BaseActivity implements View.OnClickListener, PersonSelectionListener, ManuallyClickListener,
        DialogCancelListener, VehicleSelectionListener, AddressSelectionListener, TeamSelectionListener, EventSelectionListener,
        AllegationSelectionListener, OffenceSelectionListener, CrimeGroupSelectionListener, OrganisationSelectionListener {

    public AppSession appSession;
    private Context mContext;
    public SpeechRecognizer mSpeechRecognizer;
    public Intent mSpeechRecognizerIntent;
    public Dialog mProgressDialog;
    public ArrayList<AnswerValueDTO> answerList = new ArrayList<>();
    public Dialog dialogLinear;
    public TextView tvHeader;
    public LinearLayout llAdd;
    private LinearLayout llProcessLayout;
    private TextView tvOk;
    public List<PageSectionListBean> aPageSectionListBean;
    public List<SearchListBean> aSearchListBean;
    private List<ExpectedQuestionListBean> aExpectedQuestionListBeans;
    private List<ExpectedAnswerListBean> aExpectedAnswerListBeans;
    public ExpectedAnswerListBean mExpectedAnswerListBean;
    public List<PageSection_detailListBean> aPageSection_detailListBean;

    private SearchDialogFragment searchDialogFragment;
    private TeamCustomDialogFragment teamCustomDialogFragment;
    private PersonCustomDialogFragment personCustomDialogFragment;
    private VehicleCustomDialogFragment vehicleCustomDialogFragment;
    private AddressCustomDialogFragment addressCustomDialogFragment;
    private EventCustomDialogFragment eventCustomDialogFragment;
    private AllegationCustomDialogFragment allegationCustomDialogFragment;
    private OffenceCustomDialogFragment offenceCustomDialogFragment;
    private CrimeGroupCustomDialogFragment crimeGroupCustomDialogFragment;
    private OrganisationCustomDialogFragment organisationCustomDialogFragment;

    public List<PopulateListBean> aPopulateListBean;
    public PopulateListBean mPopulateListBean;
    public List<PopulateSectionListBean> aPopulateSectionList;
    public PopulateSectionListBean mPopulateSectionList;
    public List<Populate_DetailListBean> aPopulate_DetailList;
    public List<PopulateSubSectionListBean> aPopulateSubSectionList;
    public JSONObject subSectionJson = null;

    public Object object;
    public String populateType = null;
    public String searchName = "";
    public int searchId = 0;

    private String answerValue = "", captureValueEdit = "", questionEdit = "";
    public String specialLogic, processName;
    private int Id = 0;
    private int buttonId = 0;
    private boolean isListen = true;
    private EditText etInfo;
    public static ImageView ivRings, ivSend;
    private Animation micAnimation;
    public static boolean isClickedSpeak = false;
    public static boolean isClicked = false;
    private boolean QuestionDependent = false;
    private boolean SectionDependent = false;
    private String QuestionDependentId = "";
    private ArrayList<Integer> idArrayHide = new ArrayList<>();
    private ArrayList<Integer> idArrayShow = new ArrayList<>();
    private ArrayList<Integer> idHideQuestion = new ArrayList<>();
    private ArrayList<Integer> idShowQuestion = new ArrayList<>();
    public ArrayList<Integer> idHideField = new ArrayList<>();
    public ArrayList<Integer> idShowField = new ArrayList<>();
    private String sectionName = "";
    private boolean isFinalAnswer = false;
    private int SkipQuestion = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_dialog);
        appSession = new AppSession(this);
        mContext = this;
        initView();
        createDialog();

        Id = getIntent().getIntExtra(GenericConstant.EDIT_ID, 0);
        sectionName = getIntent().getStringExtra(GenericConstant.SECTION_NAME);
        processName = getIntent().getStringExtra(GenericConstant.PROCESS_NAME);

        aPageSectionListBean = new ArrayList<>();
        if (appSession.getSectionList() != null && appSession.getSectionList().size() > 0) {
            aPageSectionListBean.addAll(appSession.getSectionList());
        }

        aPopulateListBean = new ArrayList<>();
        if (appSession.getPopulateList() != null && appSession.getPopulateList().size() > 0) {
            aPopulateListBean.addAll(appSession.getPopulateList());
        }

        aSearchListBean = new ArrayList<>();
        if (appSession.getSearchList() != null && appSession.getSearchList().size() > 0) {
            aSearchListBean.addAll(appSession.getSearchList());
        }

        aExpectedQuestionListBeans = new ArrayList<>();
        if (appSession.getExpectedQuestionList() != null && appSession.getExpectedQuestionList().size() > 0) {
            aExpectedQuestionListBeans.addAll(appSession.getExpectedQuestionList());
            editAnswer(Id);
        }
        initializeSpeech();
    }

    /**
     * Initialize the view
     */
    private void initView() {
        llProcessLayout = findViewById(R.id.llEditLayout);
        Button btnOk = findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);
        ivSend = findViewById(R.id.iv_send);
        ivRings = findViewById(R.id.iv_rings);
        etInfo = findViewById(R.id.et_info);
        ivSend.setOnClickListener(this);

        micAnimation = AnimationUtils.loadAnimation(this, R.anim.fadescale);

        etInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    isListen = false;
                    ivSend.setImageResource(R.drawable.ic_play);
                } else {
                    isListen = true;
                    ivSend.setImageResource(R.drawable.ic_mic);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_ok:
                Intent intentProcess = new Intent();
                intentProcess.putExtra(GenericConstant.EDIT_ID, Id);
                intentProcess.putExtra(GenericConstant.SECTION_NAME, sectionName);
                intentProcess.putExtra(GenericConstant.FINAL_ANSWER, isFinalAnswer);
                intentProcess.putExtra(GenericConstant.SKIP_QUESTION, SkipQuestion);
                intentProcess.putExtra(GenericConstant.ANSWER_VALUE, answerValue);
                intentProcess.putExtra(GenericConstant.BUTTON_ID, buttonId);
                intentProcess.putExtra(GenericConstant.QUESTION_EDIT, questionEdit);
                intentProcess.putExtra(GenericConstant.QUESTION_DEPENDENT_ID, QuestionDependentId);
                intentProcess.putExtra(GenericConstant.QUESTION_DEPENDENT, QuestionDependent);
                intentProcess.putExtra(GenericConstant.SECTION_DEPENDENT, SectionDependent);
                intentProcess.putExtra(GenericConstant.ID_LIST_HIDE, idArrayHide);
                intentProcess.putExtra(GenericConstant.ID_LIST_SHOW, idArrayShow);
                intentProcess.putExtra(GenericConstant.ID_QUESTION_HIDE, idHideQuestion);
                intentProcess.putExtra(GenericConstant.ID_QUESTION_SHOW, idShowQuestion);
                intentProcess.putExtra(GenericConstant.ID_FIELD_HIDE, idHideField);
                intentProcess.putExtra(GenericConstant.ID_FIELD_SHOW, idShowField);
                intentProcess.putExtra(GenericConstant.ANSWER_LIST, answerList);
                Gson gson = new Gson();
                String myJson = gson.toJson(object);
                intentProcess.putExtra(GenericConstant.OBJECT_REQUEST, myJson);
                intentProcess.putExtra(GenericConstant.SEARCH_NAME, searchName);
                intentProcess.putExtra(GenericConstant.SEARCH_ID, searchId);
                setResult(RESULT_OK, intentProcess);
                finish();
                overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
                break;

            case R.id.iv_send:
                Utilities.hideKeyboard(EditAnswerActivity.this);
                if (isListen) {
                    ivSend.setAlpha(0.4f);
                    ivRings.setVisibility(View.VISIBLE);
                    ivRings.startAnimation(micAnimation);
                    mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                } else {
                    llAdd.removeAllViews();
                    captureValueEdit = etInfo.getText().toString().trim();
                    etInfo.setText("");
                    if (aExpectedAnswerListBeans != null && aExpectedAnswerListBeans.size() > 0) {
                        for (int i = 0; i < aExpectedAnswerListBeans.size(); i++) {
                            if (aExpectedAnswerListBeans.get(i).getAnswer().toLowerCase().contains(captureValueEdit.toLowerCase())) {
                                buttonId = aExpectedAnswerListBeans.get(i).getButton_Id();
                                break;
                            }
                        }
                    }
                    createAnswers();
                }
                break;
        }
    }

    /**
     * Initialize the Speech recognizer
     */
    private void initializeSpeech() {
        try {
            mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
            mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false);
            mSpeechRecognizerIntent.putExtra("android.speech.extra.DICTATION_MODE", true);
            // mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true);
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());

            SpeakToPhoneUtil.TypeSpeechResult typeSpeechResult = new SpeakToPhoneUtil.TypeSpeechResult() {
                @Override
                public void speechResult(String content) {
                    try {
                        if (isClickedSpeak) {
                            isClickedSpeak = false;
                            Log.e("SPEAK ", "RESULT >>>>> " + content);
                            llAdd.removeAllViews();
                            captureValueEdit = content;
                            etInfo.setText("");
                            if (aExpectedAnswerListBeans != null && aExpectedAnswerListBeans.size() > 0) {
                                for (int i = 0; i < aExpectedAnswerListBeans.size(); i++) {
                                    if (aExpectedAnswerListBeans.get(i).getAnswer().toLowerCase().contains(captureValueEdit.toLowerCase())) {
                                        buttonId = aExpectedAnswerListBeans.get(i).getButton_Id();
                                        break;
                                    }
                                }
                            }
                            createAnswers();
                            Utilities.hideKeyboard(EditAnswerActivity.this);
                        }
                    } catch (Exception e) {
                        ExceptionLogger.Logger(e.getCause(), e.getMessage(), EditAnswerActivity.class, "speechResult");
                    }
                }
            };
            SpeakToPhoneUtil speakToPhone = new SpeakToPhoneUtil();
            speakToPhone.speak(mContext, mSpeechRecognizer, mSpeechRecognizerIntent, typeSpeechResult);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "initializeSpeech");
        }
    }

    /**
     * Call Edit Answer based on Id
     *
     * @param Id
     */
    private void editAnswer(int Id) {
        llProcessLayout.removeAllViews();
        if (aExpectedQuestionListBeans != null && aExpectedQuestionListBeans.size() > 0) {
            for (int j = 0; j < aExpectedQuestionListBeans.size(); j++) {
                if (aExpectedQuestionListBeans.get(j).getQuestion_Id() == Id) {

                    ExpectedQuestionListBean mExpectedQuestionListBean = aExpectedQuestionListBeans.get(j);
                    aExpectedAnswerListBeans = new ArrayList<>();
                    aExpectedAnswerListBeans = mExpectedQuestionListBean.getExpectedAnswerList();
                    editQuestion(mExpectedQuestionListBean);
                    break;
                }
            }
        }/* else if (aPageSectionListBean.get(i).getPageSection_detailList() != null && aPageSectionListBean.get(i).getPageSection_detailList().size() > 0) {
                if (aPageSectionListBean.get(i).getPageSection_Id() == Id / 1000) {
                    answerList = new ArrayList<>();
                    for (int j = 0; j < aPageSectionListBean.get(i).getPageSection_detailList().size(); j++) {
                        AnswerValueDTO answerValueDTO = new AnswerValueDTO();
                        answerValueDTO.setKey(aPageSectionListBean.get(i).getPageSection_detailList().get(j).getField_Name());
                        answerValueDTO.setValue("");
                        answerValueDTO.setId(aPageSectionListBean.get(i).getPageSection_detailList().get(j).getField_Id());
                        answerValueDTO.setSelectLogic(aPageSectionListBean.get(i).getPageSection_detailList().get(j).getSelect_Logic());
                        answerValueDTO.setMendatry(aPageSectionListBean.get(i).getPageSection_detailList().get(j).isField_Mendatry());
                        answerList.add(answerValueDTO);
                    }
                    llAdd.removeAllViews();
                    //specialLogic = null;
                    //specialLogic = mExpectedQuestionListBean.getSpecialLogic();
                    PageSectionEdit.createPageSectionDetails(this, aPageSectionListBean.get(i).getPageSection_detailList(), GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                    break;
                }
            }*/
    }

    /**
     * Call the edit Question method
     *
     * @param mExpectedQuestionListBean
     */
    private void editQuestion(ExpectedQuestionListBean mExpectedQuestionListBean) {
        llProcessLayout.removeAllViews();
        isClickedSpeak = true;
        QuestionDependent = mExpectedQuestionListBean.isQuestionDependent();
        SectionDependent = mExpectedQuestionListBean.isSectionDependent();
        QuestionDependentId = mExpectedQuestionListBean.getQuestionDependentId();
        if (mExpectedQuestionListBean.isDisplayAnswerswithQuestion()) {
            if (!mExpectedQuestionListBean.isAnswermultiselect()) {
                isClicked = true;
                CreateDynamicView.assistantText(this, mExpectedQuestionListBean.getActualQuestion(), llProcessLayout,
                        mExpectedQuestionListBean.getExpectedAnswerList(), onClickAnswerEdit);
                questionEdit = mExpectedQuestionListBean.getActualQuestion();
            } else {
                CreateDynamicView.assistantTextwithMultiple(this, mExpectedQuestionListBean.getActualQuestion(), mExpectedQuestionListBean.getActualQuestion(), "", llProcessLayout,
                        mExpectedQuestionListBean.getExpectedAnswerList(), onClickAnswerEdit);
                questionEdit = mExpectedQuestionListBean.getActualQuestion();
            }
        } else {
            CreateDynamicView.assistantText(this, mExpectedQuestionListBean.getActualQuestion(), llProcessLayout, null, null);
            questionEdit = mExpectedQuestionListBean.getActualQuestion();
        }

        aExpectedAnswerListBeans = mExpectedQuestionListBean.getExpectedAnswerList();
        aPageSection_detailListBean = mExpectedQuestionListBean.getPageSection_detailList();

        if (aExpectedAnswerListBeans == null && (aPageSection_detailListBean != null && aPageSection_detailListBean.size() > 0)) {
            //call the Pagelist method

            answerList = new ArrayList<>();
            for (int j = 0; j < aPageSection_detailListBean.size(); j++) {
                AnswerValueDTO answerValueDTO = new AnswerValueDTO();
                answerValueDTO.setKey(aPageSection_detailListBean.get(j).getField_Name());
                answerValueDTO.setValue("");
                answerValueDTO.setDependentOn(aPageSection_detailListBean.get(j).getField_Dependent_On());
                answerValueDTO.setId(aPageSection_detailListBean.get(j).getField_Id());
                answerValueDTO.setSelectLogic(aPageSection_detailListBean.get(j).getSelect_Logic());
                answerValueDTO.setMendatry(aPageSection_detailListBean.get(j).isField_Mendatry());
                answerList.add(answerValueDTO);
            }
            llAdd.removeAllViews();
            specialLogic = null;
            specialLogic = mExpectedQuestionListBean.getSpecialLogic();
            searchName = mExpectedQuestionListBean.getSpecialLogic();
            searchId = mExpectedQuestionListBean.getQuestion_Id();
            if (specialLogic != null && !specialLogic.equalsIgnoreCase("")) {
                SkipQuestion = mExpectedQuestionListBean.getSkipQuestion();
            }
            PageSectionEdit.createPageSectionDetails(this, aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
        }
    }

    /**
     * answer edit listener
     */
    public View.OnClickListener onClickAnswerEdit = new View.OnClickListener() {
        public void onClick(View v) {
            try {
                // do something when the button is clicked
                // Yes we will handle click here but which button clicked??? We don't know
                if (isClicked) {
                    isClicked = false;
                    CustomButton b = (CustomButton) v;
                    captureValueEdit = b.getText().toString();
                    buttonId = b.getId();
                    Log.e("captureValueEdit ", " captureValueEdit >>>>> : " + captureValueEdit);

                    ExpectedAnswerListBean expectedAnswerListBean = BasicMethodsUtil.getInstance().getSelectedAnswerObject(aExpectedAnswerListBeans, buttonId);
                    if (expectedAnswerListBean != null) {
                        if (expectedAnswerListBean.getHideSectionId() != null && !TextUtils.isEmpty(expectedAnswerListBean.getHideSectionId())) {
                            ArrayList<String> idArrayTemp = new ArrayList<>(Arrays.asList(expectedAnswerListBean.getHideSectionId().split(",")));
                            idArrayHide = new ArrayList<>();
                            for (int i = 0; i < idArrayTemp.size(); i++) {
                                idArrayHide.add(Integer.parseInt(idArrayTemp.get(i)));
                            }
                        }
                        if (expectedAnswerListBean.getHideQuestionId() != null && !TextUtils.isEmpty(expectedAnswerListBean.getHideQuestionId())) {
                            ArrayList<String> idArrayTemp = new ArrayList<>(Arrays.asList(expectedAnswerListBean.getHideQuestionId().split(",")));
                            idHideQuestion = new ArrayList<>();
                            for (int i = 0; i < idArrayTemp.size(); i++) {
                                idHideQuestion.add(Integer.parseInt(idArrayTemp.get(i)));
                            }
                        }
                        if (expectedAnswerListBean.getHideFieldId() != null && !TextUtils.isEmpty(expectedAnswerListBean.getHideFieldId())) {
                            ArrayList<String> idArrayTemp = new ArrayList<>(Arrays.asList(expectedAnswerListBean.getHideFieldId().split(",")));
                            idHideField = new ArrayList<>();
                            for (int i = 0; i < idArrayTemp.size(); i++) {
                                idHideField.add(Integer.parseInt(idArrayTemp.get(i)));
                            }
                        }
                    }
                    idArrayShow = new ArrayList<>();
                    idShowQuestion = new ArrayList<>();
                    idShowField = new ArrayList<>();
                    for (int i = 0; i < aExpectedAnswerListBeans.size(); i++) {
                        if (aExpectedAnswerListBeans.get(i).getShowSectionId() != null && !TextUtils.isEmpty(aExpectedAnswerListBeans.get(i).getShowSectionId())) {
                            ArrayList<String> idTempArray = new ArrayList<>(Arrays.asList(aExpectedAnswerListBeans.get(i).getShowSectionId().split(",")));
                            for (int j = 0; j < idTempArray.size(); j++) {
                                idArrayShow.add(Integer.parseInt(idTempArray.get(j)));
                            }
                        }
                        if (aExpectedAnswerListBeans.get(i).getShowQuestionId() != null && !TextUtils.isEmpty(aExpectedAnswerListBeans.get(i).getShowQuestionId())) {
                            ArrayList<String> idTempArray = new ArrayList<>(Arrays.asList(aExpectedAnswerListBeans.get(i).getShowQuestionId().split(",")));
                            for (int j = 0; j < idTempArray.size(); j++) {
                                idShowQuestion.add(Integer.parseInt(idTempArray.get(j)));
                            }
                        }
                        if (aExpectedAnswerListBeans.get(i).getShowFieldId() != null && !TextUtils.isEmpty(aExpectedAnswerListBeans.get(i).getShowFieldId())) {
                            ArrayList<String> idTempArray = new ArrayList<>(Arrays.asList(aExpectedAnswerListBeans.get(i).getShowFieldId().split(",")));
                            for (int j = 0; j < idTempArray.size(); j++) {
                                idShowField.add(Integer.parseInt(idTempArray.get(j)));
                            }
                        }
                    }
                    idArrayShow = new ArrayList<Integer>(new LinkedHashSet<Integer>(idArrayShow));
                    if (idArrayShow != null && idArrayShow.size() > 0)
                        idArrayShow.removeAll(idArrayHide);
                    idShowQuestion = new ArrayList<Integer>(new LinkedHashSet<Integer>(idShowQuestion));
                    if (idShowQuestion != null && idShowQuestion.size() > 0)
                        idShowQuestion.removeAll(idHideQuestion);
                    idShowField = new ArrayList<Integer>(new LinkedHashSet<Integer>(idShowField));
                    if (idShowField != null && idShowField.size() > 0)
                        idShowField.removeAll(idHideField);
                    createAnswers();
                }
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), EditAnswerActivity.class, "onClickAnswer");
            }
        }
    };


    /**
     * call the create anwser method
     */
    public void createAnswers() {
        try {
            answerList = new ArrayList<>();
            answerValue = captureValueEdit; // Answer receive from user
            mExpectedAnswerListBean = null;
            if (aExpectedAnswerListBeans != null && aExpectedAnswerListBeans.size() > 0) {

                for (int i = 0; i < aExpectedAnswerListBeans.size(); i++) {

                    mExpectedAnswerListBean = aExpectedAnswerListBeans.get(i);

                    Log.e("ANSWER EDIT", "mExpectedAnswerListBean : " + mExpectedAnswerListBean.getAnswer());

                    if (mExpectedAnswerListBean.getMatchAnswer()) {

                        Log.e("ANSWER ", " >>>>> : " + mExpectedAnswerListBean.getAnswer());

                        if (mExpectedAnswerListBean.getAnswer().toLowerCase().contains(answerValue.toLowerCase())) {

                            Log.e("ANSWER ", " match >>>>> : " + mExpectedAnswerListBean.getAnswer());

                            setRecords(answerValue);

                            isFinalAnswer = mExpectedAnswerListBean.getIsFinalAnswer();
                            SkipQuestion = mExpectedAnswerListBean.getSkipQuestion();

                            aPageSection_detailListBean = mExpectedAnswerListBean.getPageSection_detailList();

                            if (aPageSection_detailListBean != null && aPageSection_detailListBean.size() > 0) {
                                for (int j = 0; j < aPageSection_detailListBean.size(); j++) {
                                    AnswerValueDTO answerValueDTO = new AnswerValueDTO();
                                    answerValueDTO.setKey(aPageSection_detailListBean.get(j).getField_Name());
                                    answerValueDTO.setValue("");
                                    answerValueDTO.setDependentOn(aPageSection_detailListBean.get(j).getField_Dependent_On());
                                    answerValueDTO.setId(aPageSection_detailListBean.get(j).getField_Id());
                                    answerValueDTO.setSelectLogic(aPageSection_detailListBean.get(j).getSelect_Logic());
                                    answerValueDTO.setMendatry(aPageSection_detailListBean.get(j).isField_Mendatry());
                                    answerList.add(answerValueDTO);
                                }
                                //call the Pagelist method
                                llAdd.removeAllViews();
                                specialLogic = null;
                                searchName = mExpectedAnswerListBean.getSpecialLogic();
                                searchId = mExpectedAnswerListBean.getButton_Id();
                                specialLogic = mExpectedAnswerListBean.getSpecialLogic();
                                PageSectionEdit.createPageSectionDetails(this, mExpectedAnswerListBean.getPageSection_detailList(), GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);

                                Log.d("ANSWER", "Moving to Next section/page");
                                break;
                            }
                            break;
                        }
                    } else {
                        // Match Answer is not true
                        setRecords(answerValue);

                        isFinalAnswer = mExpectedAnswerListBean.getIsFinalAnswer();
                        SkipQuestion = mExpectedAnswerListBean.getSkipQuestion();

                        aPageSection_detailListBean = mExpectedAnswerListBean.getPageSection_detailList();

                        if (aPageSection_detailListBean != null && aPageSection_detailListBean.size() > 0) {
                            for (int j = 0; j < aPageSection_detailListBean.size(); j++) {
                                AnswerValueDTO answerValueDTO = new AnswerValueDTO();
                                answerValueDTO.setKey(aPageSection_detailListBean.get(j).getField_Name());
                                answerValueDTO.setValue("");
                                answerValueDTO.setDependentOn(aPageSection_detailListBean.get(j).getField_Dependent_On());
                                answerValueDTO.setId(aPageSection_detailListBean.get(j).getField_Id());
                                answerValueDTO.setSelectLogic(aPageSection_detailListBean.get(j).getSelect_Logic());
                                answerValueDTO.setMendatry(aPageSection_detailListBean.get(j).isField_Mendatry());
                                answerList.add(answerValueDTO);
                            }
                            //call the Pagelist method
                            llAdd.removeAllViews();
                            specialLogic = null;
                            specialLogic = mExpectedAnswerListBean.getSpecialLogic();
                            searchName = mExpectedAnswerListBean.getSpecialLogic();
                            searchId = mExpectedAnswerListBean.getButton_Id();
                            PageSectionEdit.createPageSectionDetails(this, mExpectedAnswerListBean.getPageSection_detailList(), GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);

                            break;
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), EditAnswerActivity.class, "createAnswers");
        }
    }

    /**
     * Call method for set records based on answer value
     *
     * @param asnwerValue
     */
    public void setRecords(String asnwerValue) {

        if (!TextUtils.isEmpty(asnwerValue)) {
            String strJson = JsonUtil.getInstance().loadJsonFromAsset(this, GenericConstant.BLANK_PROPERTIES_JSON);
            PropertiesBean propertiesBeanL = (PropertiesBean) JsonUtil.getInstance().toModel(strJson, PropertiesBean.class);

            propertiesBeanL.setPaddingLeft(10);
            propertiesBeanL.setPaddingRight(20);
            propertiesBeanL.setPaddingBottom(10);
            propertiesBeanL.setPaddingTop(7);
            propertiesBeanL.setMarginTop(10);
            propertiesBeanL.setBackGround(ViewPropertiesConstant.BackgroundUser);
            propertiesBeanL.setGravity(ViewPropertiesConstant.Center);
            propertiesBeanL.setLayoutGravity(ViewPropertiesConstant.End);
            propertiesBeanL.setOrientation(ViewPropertiesConstant.Vertical);
            CustomLinearLayout customLinearLayout = new CustomLinearLayout(this);
            customLinearLayout.SetLinearLayout(this, propertiesBeanL);
            llProcessLayout.addView(customLinearLayout);

            PropertiesBean propertiesBean = (PropertiesBean) JsonUtil.getInstance().toModel(strJson, PropertiesBean.class);
            propertiesBean.setText(appSession.getUserID());
            propertiesBean.setTextSize(12);
            propertiesBean.setTextColor(ViewPropertiesConstant.Color_Yellow);
            propertiesBean.setLayoutGravity(ViewPropertiesConstant.Start);

            LayoutFieldHelper layoutFieldHelper1 = new LayoutFieldHelper();
            layoutFieldHelper1.setViewType(ViewPropertiesConstant.textView);
            layoutFieldHelper1.setParentView(customLinearLayout);
            layoutFieldHelper1.setPropertiesBean(propertiesBean);

            CreateLayout.createLayoutFields(this, layoutFieldHelper1);

            CreateDynamicView.userText(this, "", asnwerValue, customLinearLayout);
        }
    }

    /**
     * Call method for set records
     */
    public void setRecords() {
        if (answerList != null && answerList.size() > 0) {
            String strJson = JsonUtil.getInstance().loadJsonFromAsset(this, GenericConstant.BLANK_PROPERTIES_JSON);
            PropertiesBean propertiesBeanL = (PropertiesBean) JsonUtil.getInstance().toModel(strJson, PropertiesBean.class);

            propertiesBeanL.setPaddingLeft(10);
            propertiesBeanL.setPaddingRight(20);
            propertiesBeanL.setPaddingBottom(10);
            propertiesBeanL.setPaddingTop(7);
            propertiesBeanL.setMarginTop(10);
            propertiesBeanL.setBackGround(ViewPropertiesConstant.BackgroundUser);
            propertiesBeanL.setGravity(ViewPropertiesConstant.Center);
            propertiesBeanL.setLayoutGravity(ViewPropertiesConstant.End);
            propertiesBeanL.setOrientation(ViewPropertiesConstant.Vertical);
            CustomLinearLayout customLinearLayout = new CustomLinearLayout(this);
            customLinearLayout.SetLinearLayout(this, propertiesBeanL);
            llProcessLayout.addView(customLinearLayout);

            PropertiesBean propertiesBean = (PropertiesBean) JsonUtil.getInstance().toModel(strJson, PropertiesBean.class);
            propertiesBean.setText(appSession.getUserID());
            propertiesBean.setTextSize(12);
            propertiesBean.setTextColor(ViewPropertiesConstant.Color_Yellow);
            propertiesBean.setLayoutGravity(ViewPropertiesConstant.Start);

            LayoutFieldHelper layoutFieldHelper1 = new LayoutFieldHelper();
            layoutFieldHelper1.setViewType(ViewPropertiesConstant.textView);
            layoutFieldHelper1.setParentView(customLinearLayout);
            layoutFieldHelper1.setPropertiesBean(propertiesBean);

            CreateLayout.createLayoutFields(this, layoutFieldHelper1);

            for (int i = 0; i < answerList.size(); i++) {

                if (answerList.get(i).getValue() != null && !answerList.get(i).getValue().equalsIgnoreCase("")) {
                    CreateDynamicView.userText(this, answerList.get(i).getKey(), answerList.get(i).getValue(), customLinearLayout);
                }
            }
        }
    }

    /**
     * Create the dialog method
     */
    private void createDialog() {
        dialogLinear = new Dialog(this, R.style.CustomDialogAnimation);
        dialogLinear.setCanceledOnTouchOutside(false);
        dialogLinear.setCancelable(false);
        dialogLinear.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialogLinear.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialogLinear.setContentView(R.layout.linear_dialog);
        Objects.requireNonNull(dialogLinear.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialogLinear.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        tvHeader = dialogLinear.findViewById(R.id.tv_header);
        llAdd = dialogLinear.findViewById(R.id.ll_add);
        tvOk = dialogLinear.findViewById(R.id.tv_submit);

        tvOk.setOnClickListener(v -> {
            // Call next section once the details is saved.

            if (Utilities.isMyServiceRunning(this, MyLocationServices.class)) {
                Intent i = new Intent(this, MyLocationServices.class);
                i.setAction(MyLocationServices.ACTION_STOP_FOREGROUND_SERVICE);
                ContextCompat.startForegroundService(this, i);
            }

            DialogValue.getValuesFromDialog(this);
            if (answerList != null && answerList.size() > 0) {
                for (int i = 0; i < answerList.size(); i++) {
                    if (answerList.get(i).isMendatry() && answerList.get(i).getValue().equalsIgnoreCase("")) {

                        BasicMethodsUtil.getInstance().showToast(this, getString(R.string.mandatory_fields_require));
                        Log.e("Mendatry ", " >>>>> " + answerList.get(i).getKey() + " >> " + answerList.get(i).getValue());
                        return;
                    }
                    if (answerList.get(0).getSelectLogic() != null && !answerList.get(0).getSelectLogic().equalsIgnoreCase("")
                            && answerList.get(0).getSelectLogic().equalsIgnoreCase(GenericConstant.DATE_LOGIC)) {

                        if (i > 0) {
                            if (answerList.get(i).getSelectLogic() != null && !answerList.get(i).getSelectLogic().equalsIgnoreCase("")
                                    && answerList.get(i).getSelectLogic().equalsIgnoreCase(GenericConstant.DATE_LOGIC)) {
                                if (answerList.get(0).getValue() != null && !answerList.get(0).getValue().equalsIgnoreCase("")
                                        && answerList.get(i).getValue() != null && !answerList.get(i).getValue().equalsIgnoreCase("")) {
                                    int answer = CompareDate.compareDates(answerList.get(0).getValue(), answerList.get(i).getValue());
                                    if (answer == 1) {
                                        return;
                                    }
                                }

                            }
                        }
                    }
                }
                setRecords();
                llAdd.removeAllViews();
            }
            dialogLinear.dismiss();
        });
    }

    public void callTempPageSectionDetails(ArrayList<AnswerValueDTO> searchList) {
        specialLogic = null;
        llAdd.removeAllViews();
        PageSectionEdit.createPageSectionDetails(EditAnswerActivity.this, aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
        answerList = new ArrayList<>();
        for (int j = 0; j < aPageSection_detailListBean.size(); j++) {
            AnswerValueDTO answerDTO = new AnswerValueDTO();
            answerDTO.setKey(aPageSection_detailListBean.get(j).getField_Name());
            answerDTO.setValue("");
            answerDTO.setDependentOn(aPageSection_detailListBean.get(j).getField_Dependent_On());
            answerDTO.setId(aPageSection_detailListBean.get(j).getField_Id());
            answerDTO.setSelectLogic(aPageSection_detailListBean.get(j).getSelect_Logic());
            answerDTO.setMendatry(aPageSection_detailListBean.get(j).isField_Mendatry());
            answerList.add(answerDTO);
        }
    }

    @Override
    public void onOrganisationSelected(OrganisationDetailsList organisationDetailsList) {
        try {
            organisationCustomDialogFragment.dismiss();
            //appSession.setOrganisation(organisationDetailsList);
            populateType(this, organisationDetailsList, GenericConstant.ORGANISATION);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "onPersonSelected");
        }
    }

    @Override
    public void onTeamSelected(TeamBean teamBean) {
        try {
            teamCustomDialogFragment.dismiss();
            //appSession.setTeam(teamBean);
            populateType(this, teamBean, GenericConstant.TEAM);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "onPersonSelected");
        }
    }

    @Override
    public void onPersonSelected(PersonBean personBean) {
        try {
            personCustomDialogFragment.dismiss();
            //appSession.setPerson(personBean);
            populateType(this, personBean, GenericConstant.PERSON);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "onPersonSelected");
        }
    }

    @Override
    public void onVehicleSelected(VehicleBean vehicleBean) {
        vehicleCustomDialogFragment.dismiss();
        //appSession.setVehicle(vehicleBean);
        populateType(this, vehicleBean, GenericConstant.VEHICLE);
    }

    @Override
    public void onAddressSelected(AddressBean addressBean) {
        //appSession.setAddress(addressBean);
        addressCustomDialogFragment.dismiss();
        populateType(this, addressBean, GenericConstant.ADDRESS);
    }

    @Override
    public void onEventSelected(EventSearchList eventSearchList) {
        try {
            eventCustomDialogFragment.dismiss();
            //appSession.setEvent(eventSearchList);
            populateType(this, eventSearchList, GenericConstant.EVENT);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "onPersonSelected");
        }
    }

    @Override
    public void onAllegationSelected(OffenceDefinitionList offenceDefinitionList) {
        allegationCustomDialogFragment.dismiss();
        //appSession.setAllegation(offenceDefinitionList);
        populateType(this, offenceDefinitionList, GenericConstant.ALLEGATION);
    }

    @Override
    public void onOffenceSelected(HOOffenceList hoOffenceList) {
        offenceCustomDialogFragment.dismiss();
        //appSession.setOffence(hoOffenceList);
        populateType(this, hoOffenceList, GenericConstant.OFFENCE);
    }

    @Override
    public void onCrimeGroupSelected(CrimeGroupList crimeGroupList) {
        crimeGroupCustomDialogFragment.dismiss();
        //appSession.setCrimeGroup(crimeGroupList);
        populateType(this, crimeGroupList, GenericConstant.CRIMEGROUP);
    }

    public void populateRecent(EditAnswerActivity act, Object object, ArrayList<AnswerValueDTO> recentList, String type) {
        if (object != null) {
            act.dismissSearchDialog();
            act.object = object;
            act.populateType = type;
            act.specialLogic = null;
            act.llAdd.removeAllViews();

            act.answerList = new ArrayList<>();
            for (int j = 0; j < act.aPageSection_detailListBean.size(); j++) {
                AnswerValueDTO answerDTO = new AnswerValueDTO();
                answerDTO.setKey(act.aPageSection_detailListBean.get(j).getField_Name());
                answerDTO.setValue("");
                answerDTO.setDependentOn(act.aPageSection_detailListBean.get(j).getField_Dependent_On());
                answerDTO.setId(act.aPageSection_detailListBean.get(j).getField_Id());
                answerDTO.setSelectLogic(act.aPageSection_detailListBean.get(j).getSelect_Logic());
                answerDTO.setMendatry(act.aPageSection_detailListBean.get(j).isField_Mendatry());
                answerDTO.setPopulate(type);
                act.answerList.add(answerDTO);
            }

            PopulateEditFields.populateFieldList(act, object);

            if (recentList != null && recentList.size() > 0) {
                for (int i = 0; i < act.answerList.size(); i++) {

                    for (int j = 0; j < recentList.size(); j++) {

                        if (act.answerList.get(i).getKey().equalsIgnoreCase(recentList.get(j).getKey())) {

                            act.answerList.get(i).setValue(recentList.get(j).getValue());
                        }
                    }
                }
            }

            boolean populateValue = false;
            for (int i = 0; i < act.answerList.size(); i++) {
                if (act.answerList.get(i).getKey().contains(FieldsNameConstant.SYSTEM)) {
                    if (act.answerList.get(i).getValue().equalsIgnoreCase(GenericConstant.POLE)) {
                        populateValue = true;
                        break;
                    }
                }
            }
            if (act.processName.equalsIgnoreCase(act.getResources().getString(R.string.stop_process))
                    || act.processName.equalsIgnoreCase(act.getResources().getString(R.string.crime))) {
                if (populateValue) {
                    setRecords();
                    //populate(act, object, type);
                } else {
                    PageSectionEdit.createPageSectionDetails(act, act.aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                    DialogValue.setValueInDialog(act, act.answerList);
                }
            } else {
                PageSectionEdit.createPageSectionDetails(act, act.aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                DialogValue.setValueInDialog(act, act.answerList);
            }
            DialogUtil.dismiss();
        }
    }

    public void populateType(EditAnswerActivity act, Object object, String type) {
        if (object != null) {
            act.object = object;
            act.populateType = type;
            act.specialLogic = null;
            act.llAdd.removeAllViews();

            act.answerList = new ArrayList<>();
            for (int j = 0; j < act.aPageSection_detailListBean.size(); j++) {
                AnswerValueDTO answerDTO = new AnswerValueDTO();
                answerDTO.setKey(act.aPageSection_detailListBean.get(j).getField_Name());
                answerDTO.setValue("");
                answerDTO.setDependentOn(act.aPageSection_detailListBean.get(j).getField_Dependent_On());
                answerDTO.setId(act.aPageSection_detailListBean.get(j).getField_Id());
                answerDTO.setSelectLogic(act.aPageSection_detailListBean.get(j).getSelect_Logic());
                answerDTO.setMendatry(act.aPageSection_detailListBean.get(j).isField_Mendatry());
                answerDTO.setPopulate(type);
                act.answerList.add(answerDTO);
            }

            PopulateEditFields.populateFieldList(act, object);

            boolean populateValue = false;
            for (int i = 0; i < act.answerList.size(); i++) {
                if (act.answerList.get(i).getKey().contains(FieldsNameConstant.SYSTEM)) {
                    if (act.answerList.get(i).getValue().equalsIgnoreCase(GenericConstant.POLE)) {
                        populateValue = true;
                        break;
                    }
                }
            }
            if (act.processName.equalsIgnoreCase(act.getResources().getString(R.string.stop_process))
                    || act.processName.equalsIgnoreCase(act.getResources().getString(R.string.crime))) {
                if (populateValue) {
                    setRecords();
                    //populate(act, object, type);
                } else {
                    PageSectionEdit.createPageSectionDetails(act, act.aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                    DialogValue.setValueInDialog(act, act.answerList);
                }
            } else {
                PageSectionEdit.createPageSectionDetails(act, act.aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                DialogValue.setValueInDialog(act, act.answerList);
            }
            DialogUtil.dismiss();
        }
    }

    public void populateUser(UserBean officerBean) {
        if (officerBean != null) {
            specialLogic = null;
            llAdd.removeAllViews();

            answerList = new ArrayList<>();
            for (int j = 0; j < aPageSection_detailListBean.size(); j++) {
                AnswerValueDTO answerDTO = new AnswerValueDTO();
                answerDTO.setKey(aPageSection_detailListBean.get(j).getField_Name());
                answerDTO.setValue("");
                answerDTO.setDependentOn(aPageSection_detailListBean.get(j).getField_Dependent_On());
                answerDTO.setId(aPageSection_detailListBean.get(j).getField_Id());
                answerDTO.setSelectLogic(aPageSection_detailListBean.get(j).getSelect_Logic());
                answerDTO.setMendatry(aPageSection_detailListBean.get(j).isField_Mendatry());
                answerList.add(answerDTO);
            }
            for (int i = 0; i < answerList.size(); i++) {

                if (answerList.get(i).getKey().contains(FieldsNameConstant.Force)) {
                    answerList.get(i).setValue(officerBean.getForce());
                }
                if (answerList.get(i).getKey().contains(FieldsNameConstant.Force_Code)) {
                    answerList.get(i).setValue(officerBean.getForce_Code());
                }
                if (answerList.get(i).getKey().contains(FieldsNameConstant.Officer_Service_Number)) {
                    answerList.get(i).setValue(officerBean.getOfficer_Service_Number());
                }
                if (answerList.get(i).getKey().contains(FieldsNameConstant.Officer_Collar_Number)) {
                    answerList.get(i).setValue(officerBean.getOfficer_Collar_Number());
                }
                if (answerList.get(i).getKey().contains(FieldsNameConstant.Officer_Name)) {
                    answerList.get(i).setValue(officerBean.getOfficer_Name());
                }
                if (answerList.get(i).getKey().contains(FieldsNameConstant.Officer_Rank)) {
                    answerList.get(i).setValue(officerBean.getOfficer_Rank());
                }
                if (answerList.get(i).getKey().contains(FieldsNameConstant.Officer_Gender)) {
                    answerList.get(i).setValue(officerBean.getOfficer_Gender());
                }
                if (answerList.get(i).getKey().contains(FieldsNameConstant.Officer_Station)) {
                    answerList.get(i).setValue(officerBean.getOfficer_Station());
                }
                if (answerList.get(i).getKey().contains(FieldsNameConstant.ID)) {
                    answerList.get(i).setValue(officerBean.getId());
                }
                if (answerList.get(i).getKey().contains(FieldsNameConstant.SYSTEM)) {
                    answerList.get(i).setValue(officerBean.getSystem());
                }
            }
            setRecords();
        }
    }

    @Override
    public void onManuallyClicker(int type) {

        switch (type) {
            case GenericConstant.TYPE_PERSON:
                personCustomDialogFragment.dismiss();
                specialLogic = null;
                llAdd.removeAllViews();
                PageSectionEdit.createPageSectionDetails(EditAnswerActivity.this, aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                DialogValue.setValueInDialog(this, answerList);

                break;

            case GenericConstant.TYPE_VEHICLE:
                vehicleCustomDialogFragment.dismiss();
                specialLogic = null;
                llAdd.removeAllViews();
                PageSectionEdit.createPageSectionDetails(EditAnswerActivity.this, aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                DialogValue.setValueInDialog(this, answerList);

                break;

            case GenericConstant.TYPE_ADDRESS:
                addressCustomDialogFragment.dismiss();
                specialLogic = null;
                llAdd.removeAllViews();
                PageSectionEdit.createPageSectionDetails(EditAnswerActivity.this, aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                DialogValue.setValueInDialog(this, answerList);

                break;
        }
    }

    @Override
    public void dialogCancelled() {
    }

    /**
     * Load Crime Group Dialog
     */
    public void loadSearchDialog(String searchType, List<Address> addressList) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.SEARCH_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        searchDialogFragment = SearchDialogFragment.newInstance(searchType, addressList);
        searchDialogFragment.show(ft, GenericConstant.SEARCH_LIST_DIALOG);
    }

    public void dismissSearchDialog() {
        searchDialogFragment.dismiss();
    }

    /**
     * Load Person Dialog
     */
    public void loadOrganisation(ArrayList<AnswerValueDTO> searchList) {
        try {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.ORGANISATION_LIST_DIALOG);
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            // Create and show the dialog.
            organisationCustomDialogFragment = OrganisationCustomDialogFragment.newInstance(captureValueEdit, processName);
            organisationCustomDialogFragment.show(ft, GenericConstant.ORGANISATION_LIST_DIALOG);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "loadTeamDialog");
        }
    }

    /**
     * Load Person Dialog
     */
    public void loadTeamDialog(ArrayList<AnswerValueDTO> searchList) {
        try {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.TEAM_LIST_DIALOG);
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            // Create and show the dialog.
            teamCustomDialogFragment = TeamCustomDialogFragment.newInstance(captureValueEdit, processName);
            teamCustomDialogFragment.show(ft, GenericConstant.TEAM_LIST_DIALOG);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "loadPersonDialog");
        }
    }

    /**
     * Load Person Dialog
     */
    public void loadPersonDialog(ArrayList<AnswerValueDTO> searchList) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.PERSON_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        personCustomDialogFragment = PersonCustomDialogFragment.newInstance(captureValueEdit, processName, GenericConstant.POPULATE_TRUE);
        personCustomDialogFragment.show(ft, GenericConstant.PERSON_LIST_DIALOG);
    }

    /**
     * Load Vehicle Dialog
     */
    public void loadVehicleDialog(ArrayList<AnswerValueDTO> searchList) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.VEHICLE_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        vehicleCustomDialogFragment = VehicleCustomDialogFragment.newInstance(captureValueEdit, processName, GenericConstant.POPULATE_TRUE);
        vehicleCustomDialogFragment.show(ft, GenericConstant.VEHICLE_LIST_DIALOG);
    }

    /**
     * Load Address Dialog
     */
    public void loadAddressDialog(ArrayList<AnswerValueDTO> searchList) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.ADDRESS_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        addressCustomDialogFragment = AddressCustomDialogFragment.newInstance(captureValueEdit, processName, GenericConstant.POPULATE_TRUE);
        addressCustomDialogFragment.show(ft, GenericConstant.ADDRESS_LIST_DIALOG);
    }

    /**
     * Load Event Dialog
     */
    public void loadCrimeDialog(ArrayList<AnswerValueDTO> searchList) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.EVENT_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        eventCustomDialogFragment = EventCustomDialogFragment.newInstance(captureValueEdit, processName);
        eventCustomDialogFragment.show(ft, GenericConstant.EVENT_LIST_DIALOG);
    }

    /**
     * Load Event Dialog
     */
    public void loadAllegationDialog(ArrayList<AnswerValueDTO> searchList) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.ALLEGATION_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        allegationCustomDialogFragment = AllegationCustomDialogFragment.newInstance(captureValueEdit, processName);
        allegationCustomDialogFragment.show(ft, GenericConstant.ALLEGATION_LIST_DIALOG);
    }

    /**
     * Load Offence Dialog
     */
    public void loadOffenceDialog(ArrayList<AnswerValueDTO> searchList) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.OFFENCE_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        offenceCustomDialogFragment = OffenceCustomDialogFragment.newInstance(captureValueEdit, processName);
        offenceCustomDialogFragment.show(ft, GenericConstant.OFFENCE_LIST_DIALOG);
    }

    /**
     * Load Crime Group Dialog
     */
    public void loadCrimeGroupDialog(ArrayList<AnswerValueDTO> searchList) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.CRIME_GROUP_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        crimeGroupCustomDialogFragment = CrimeGroupCustomDialogFragment.newInstance(captureValueEdit, processName);
        crimeGroupCustomDialogFragment.show(ft, GenericConstant.CRIME_GROUP_LIST_DIALOG);
    }

    /**
     * Call the Map Activity
     */
    public void callMapActivity() {
        appSession.setCurrentLatitude(null);
        appSession.setCurrentLongitude(null);
        Intent mapIntent = new Intent(mContext, MapActivity.class);
        startActivityForResult(mapIntent, GenericConstant.MAP_REQUEST);
        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
    }

    /**
     * Call the Map Activity
     */
    public void callMapActivitySearch() {
        appSession.setCurrentLatitude(null);
        appSession.setCurrentLongitude(null);
        Intent mapIntent = new Intent(mContext, MapActivity.class);
        startActivityForResult(mapIntent, GenericConstant.MAP_REQUEST_SEARCH);
        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
    }

    /**
     * Call the Officer Activity
     */
    public void callOfficerActivity(ArrayList<AnswerValueDTO> searchList) {
        Intent mapIntent = new Intent(mContext, OfficerSearchActivity.class);
        startActivityForResult(mapIntent, GenericConstant.OFFICER_REQUEST);
        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(" RESULT ", " >>>>> " + resultCode + "  >>>>  " + data + " >>>>>  " + requestCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GenericConstant.MAP_REQUEST:
                    try {
                        List<Address> addressList = null;
                        if (appSession.getLatitude() != null && !appSession.getLongitude().equalsIgnoreCase("")
                                && appSession.getLongitude() != null) {

                            addressList = LocationUtil.getInstance().getAddressFromLocation(mContext,
                                    Double.parseDouble(appSession.getLatitude()), Double.parseDouble(appSession.getLongitude()));

                            Log.e("address ", " >>>>>>>> " + addressList.get(0).getAddressLine(0));
                        }
                        AddressBean addressBean = new AddressBean();
                        if (addressList != null && addressList.size() > 0) {

                            if (addressList.get(0).getFeatureName() != null) {
                                addressBean.setHouseNo(addressList.get(0).getFeatureName());
                                addressBean.setBuildingno(addressList.get(0).getFeatureName());
                                addressBean.setBuildingname(addressList.get(0).getFeatureName());
                                addressBean.setFlatno(addressList.get(0).getFeatureName());
                            } else {
                                addressBean.setHouseNo("");
                                addressBean.setBuildingno("");
                                addressBean.setBuildingname("");
                                addressBean.setFlatno("");
                            }

                            if (addressList.get(0).getSubLocality() != null) {
                                addressBean.setStreet(addressList.get(0).getSubLocality());
                            } else {
                                addressBean.setStreet("");
                            }

                            if (addressList.get(0).getLocality() != null) {
                                addressBean.setCity(addressList.get(0).getLocality());
                            } else {
                                addressBean.setCity("");
                            }

                            if (addressList.get(0).getCountryName() != null) {
                                addressBean.setCountry(addressList.get(0).getCountryName());
                            } else {
                                addressBean.setCountry("");
                            }

                            if (addressList.get(0).getPostalCode() != null) {
                                addressBean.setPostcode(addressList.get(0).getPostalCode());
                            } else {
                                addressBean.setPostcode("");
                            }
                        }
                        populateType(this, addressBean, GenericConstant.ADDRESS);
                    } catch (Exception e) {
                        ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "mapResult");
                    }
                    break;

                case GenericConstant.MAP_REQUEST_SEARCH:
                    try {
                        List<Address> addressList = null;
                        if (appSession.getLatitude() != null && !appSession.getLongitude().equalsIgnoreCase("")
                                && appSession.getLongitude() != null) {

                            addressList = LocationUtil.getInstance().getAddressFromLocation(mContext,
                                    Double.parseDouble(appSession.getLatitude()), Double.parseDouble(appSession.getLongitude()));

                            Log.e("address ", " >>>>>>>> " + addressList.get(0).getAddressLine(0));
                        }
                        loadSearchDialog(GenericConstant.SEARCH_ADDRESS, addressList);
                    } catch (Exception e) {
                        ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "mapResult");
                    }
                    break;

                case GenericConstant.OFFICER_REQUEST:
                    try {
                        Log.e("PROCESS EDIT ", " NAME >>>> " + processName);
                        ArrayList<AnswerValueDTO> tempList = new ArrayList<>();
                        tempList = (ArrayList<AnswerValueDTO>) data.getSerializableExtra(GenericConstant.OFFICER_LIST);
                        specialLogic = null;
                        llAdd.removeAllViews();
                        PageSectionEdit.createPageSectionDetails(EditAnswerActivity.this, mExpectedAnswerListBean.getPageSection_detailList(), GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                        answerList = new ArrayList<>();
                        for (int j = 0; j < mExpectedAnswerListBean.getPageSection_detailList().size(); j++) {
                            AnswerValueDTO answerValueDTO = new AnswerValueDTO();
                            answerValueDTO.setKey(mExpectedAnswerListBean.getPageSection_detailList().get(j).getField_Name());
                            answerValueDTO.setValue("");
                            answerValueDTO.setDependentOn(mExpectedAnswerListBean.getPageSection_detailList().get(j).getField_Dependent_On());
                            answerValueDTO.setId(mExpectedAnswerListBean.getPageSection_detailList().get(j).getField_Id());
                            answerValueDTO.setSelectLogic(mExpectedAnswerListBean.getPageSection_detailList().get(j).getSelect_Logic());
                            answerValueDTO.setMendatry(mExpectedAnswerListBean.getPageSection_detailList().get(j).isField_Mendatry());
                            answerList.add(answerValueDTO);
                        }

                        for (int i = 0; i < answerList.size(); i++) {
                            for (int j = 0; j < tempList.size(); j++) {
                                if (answerList.get(i).getKey().contains(tempList.get(j).getKey())) {
                                    answerList.get(i).setValue(tempList.get(j).getValue());
                                }
                            }
                        }
                        DialogValue.setValueInDialog(this, answerList);
                    } catch (Exception e) {
                        ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "officerResult");
                    }

                    break;
            }
        } else {
            if (Utilities.isMyServiceRunning(this, MyLocationServices.class)) {
                Intent i = new Intent(this, MyLocationServices.class);
                i.setAction(MyLocationServices.ACTION_STOP_FOREGROUND_SERVICE);
                ContextCompat.startForegroundService(this, i);
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mSpeechRecognizer != null) {
            mSpeechRecognizer.cancel();
            mSpeechRecognizer.destroy();
        }
        super.onDestroy();
    }
}
