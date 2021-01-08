package hcl.policing.digitalpolicingplatform.activities.process;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.activities.audio.AudioRecordActivity;
import hcl.policing.digitalpolicingplatform.activities.camera.CameraActivity;
import hcl.policing.digitalpolicingplatform.activities.document.DocsUploadActivity;
import hcl.policing.digitalpolicingplatform.activities.map.MapActivity;
import hcl.policing.digitalpolicingplatform.activities.officer.OfficerSearchActivity;
import hcl.policing.digitalpolicingplatform.activities.pocketbook.PocketBookActivity;
import hcl.policing.digitalpolicingplatform.activities.process.edit.EditAnswerActivity;
import hcl.policing.digitalpolicingplatform.activities.process.edit.EditPageSectionFields;
import hcl.policing.digitalpolicingplatform.activities.process.edit.EditProcess;
import hcl.policing.digitalpolicingplatform.activities.process.edit.OpenSection;
import hcl.policing.digitalpolicingplatform.activities.process.edit.RemoveValue;
import hcl.policing.digitalpolicingplatform.activities.process.flow.AddSubJsonList;
import hcl.policing.digitalpolicingplatform.activities.process.flow.CreateAnswer;
import hcl.policing.digitalpolicingplatform.activities.process.flow.CreateListFromJson;
import hcl.policing.digitalpolicingplatform.activities.process.flow.CreateQuestion;
import hcl.policing.digitalpolicingplatform.activities.process.flow.DialogValue;
import hcl.policing.digitalpolicingplatform.activities.process.flow.PageSection;
import hcl.policing.digitalpolicingplatform.activities.process.flow.PopulateFields;
import hcl.policing.digitalpolicingplatform.activities.process.flow.PrepareSubSection;
import hcl.policing.digitalpolicingplatform.activities.process.flow.ServerRequest;
import hcl.policing.digitalpolicingplatform.activities.process.flow.SetClick;
import hcl.policing.digitalpolicingplatform.activities.process.flow.SetRecord;
import hcl.policing.digitalpolicingplatform.activities.process.flow.TabClick;
import hcl.policing.digitalpolicingplatform.activities.signature.SignatureUploadActivity;
import hcl.policing.digitalpolicingplatform.activities.sketch.SketchActivity;
import hcl.policing.digitalpolicingplatform.activities.sketch.SketchFirstActivity;
import hcl.policing.digitalpolicingplatform.adapters.attachment.UserDocListAdaptor;
import hcl.policing.digitalpolicingplatform.adapters.audio.UserAudioListAdaptor;
import hcl.policing.digitalpolicingplatform.adapters.camera.UserImageListAdaptor;
import hcl.policing.digitalpolicingplatform.adapters.layoutHelper.CustomTabSubSectionAdapter;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.fieldName.FieldsNameConstant;
import hcl.policing.digitalpolicingplatform.constants.viewProperties.ViewPropertiesConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomCheckBox;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomImageView;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomLinearLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomRecyclerView;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextView;
import hcl.policing.digitalpolicingplatform.customLibraries.MovableFloatingActionButton;
import hcl.policing.digitalpolicingplatform.customLibraries.layoutHelper.CreateDynamicView;
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
import hcl.policing.digitalpolicingplatform.models.camera.PhotoListModel;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.AnswerValueDTO;
import hcl.policing.digitalpolicingplatform.models.process.ExpectedAnswerListBean;
import hcl.policing.digitalpolicingplatform.models.process.ExpectedQuestionListBean;
import hcl.policing.digitalpolicingplatform.models.process.PageSectionListBean;
import hcl.policing.digitalpolicingplatform.models.process.PageSection_detailListBean;
import hcl.policing.digitalpolicingplatform.models.process.ProcessListBean;
import hcl.policing.digitalpolicingplatform.models.process.ProcessLogDTO;
import hcl.policing.digitalpolicingplatform.models.process.ResponseModel;
import hcl.policing.digitalpolicingplatform.models.process.SubSectionListBean;
import hcl.policing.digitalpolicingplatform.models.process.TabFlowDTO;
import hcl.policing.digitalpolicingplatform.models.process.crime.CrimeGroupList;
import hcl.policing.digitalpolicingplatform.models.process.crime.EventSearchList;
import hcl.policing.digitalpolicingplatform.models.process.crime.HOOffenceList;
import hcl.policing.digitalpolicingplatform.models.process.crime.OffenceDefinitionList;
import hcl.policing.digitalpolicingplatform.models.process.crime.OrganisationDetailsList;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.AddressBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.PersonBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.team.TeamBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.VehicleBean;
import hcl.policing.digitalpolicingplatform.models.process.populate.PopulateListBean;
import hcl.policing.digitalpolicingplatform.models.process.populate.PopulateSectionListBean;
import hcl.policing.digitalpolicingplatform.models.process.populate.PopulateSubSectionListBean;
import hcl.policing.digitalpolicingplatform.models.process.populate.Populate_DetailListBean;
import hcl.policing.digitalpolicingplatform.models.process.subjsonlist.KeyValueListDTO;
import hcl.policing.digitalpolicingplatform.models.process.subjsonlist.TabNameDTO;
import hcl.policing.digitalpolicingplatform.models.search.ResponseSearchModel;
import hcl.policing.digitalpolicingplatform.models.search.SearchListBean;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.services.MyLocationServices;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.CompareDate;
import hcl.policing.digitalpolicingplatform.utils.DateUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;
import hcl.policing.digitalpolicingplatform.utils.LocationUtil;
import hcl.policing.digitalpolicingplatform.utils.SearchDialogUtil;
import hcl.policing.digitalpolicingplatform.utils.Utilities;
import hcl.policing.digitalpolicingplatform.utils.speak.SpeakToPersonUtil;
import hcl.policing.digitalpolicingplatform.utils.speak.SpeakToPhoneUtil;

public class ProcessCreationActivity extends BaseActivity implements TextToSpeech.OnInitListener,
        View.OnClickListener, PersonSelectionListener, TeamSelectionListener, ManuallyClickListener,
        DialogCancelListener, OnMapReadyCallback, VehicleSelectionListener, AddressSelectionListener,
        EventSelectionListener, AllegationSelectionListener, OffenceSelectionListener, CrimeGroupSelectionListener,
        OrganisationSelectionListener {

    private static String TAG = ProcessCreationActivity.class.getName();
    private Context mContext;

    public AppSession appSession;
    public TextToSpeech textToSpeech;
    public SpeechRecognizer mSpeechRecognizer;
    public Intent mSpeechRecognizerIntent;
    private SpeakToPersonUtil speakToPerson;
    public MediaPlayer mediaPlayer;

    public Dialog mProgressDialog;
    public ProgressBar progressBar;
    private NestedScrollView scrollViewProcess;
    private HorizontalScrollView horizontalScrollView;
    private ImageView ivSpeaker;
    private ImageView ivArrowLeft;
    private ImageView ivArrowRight;
    public EditText etInfo;
    public Dialog dialogLinear;
    public TextView tvHeader;
    public LinearLayout llAdd;
    public LinearLayout llProcessLayout;
    public RecyclerView rvProcessLayout;
    public LinearLayout llTabLayout;
    public static ImageView ivRings, ivSend;
    public static Button btnNext, btnReturn, btnSave, btnSubmit;
    public static MovableFloatingActionButton btnAdd;
    public static CardView cvEditText;
    private Animation micAnimation;
    public CustomLinearLayout customLinearLayoutEdit;

    public String[] breakAnswerValue = null;
    public int editId = 0;
    public int buttonId = 0;
    public String captureValue, captureValueEdit = "", questionEdit = "";
    public String currentQuestion = "";
    public String specialLogic;
    public String searchName = "";
    public int searchId = 0;
    public String QuestionDependentId = "";
    private String statement = "";

    public static int SECTION_COUNT = 0;
    public static int ATTRIBUTE_COUNT = 0;
    public static int QUESTION_COUNT = 0;
    private static int ANSWER_COUNT = 0;
    public static int sectionIndex = 0;
    public static int SectionTabID = 0;
    public int ClickedTabID = 0;
    public static int QuestionID = 0;
    public static int EditPosition = 0;
    public int skipQuestionEdit = 0;
    public int pageSectionId = 0;
    public int listPos = 0;

    public List<SearchListBean> aSearchListBean;
    public ProcessListBean mProcessListBean;
    public List<PageSectionListBean> aPageSectionListBean;
    public PageSectionListBean mPageSectionListBean;
    public List<SubSectionListBean> aSubSectionListBean;
    public SubSectionListBean mSubSectionListBean;
    public List<ExpectedQuestionListBean> aExpectedQuestionListBean;
    public ExpectedQuestionListBean mExpectedQuestionListBean;
    public List<ExpectedAnswerListBean> aExpectedAnswerListBeans;
    public ExpectedAnswerListBean mExpectedAnswerListBean;
    public List<PageSection_detailListBean> aPageSection_detailListBean;

    public List<PopulateListBean> aPopulateListBean;
    public PopulateListBean mPopulateListBean;
    public List<PopulateSectionListBean> aPopulateSectionList;
    public PopulateSectionListBean mPopulateSectionList;
    public List<Populate_DetailListBean> aPopulate_DetailList;
    public List<PopulateSubSectionListBean> aPopulateSubSectionList;
    public JSONObject subSectionJson = null;

    public static List<CustomCheckBox> checkboxList;
    public ArrayList<TabFlowDTO> tabFlowPosList = new ArrayList<>();
    public ArrayList<AnswerValueDTO> answerList = new ArrayList<>();
    public List<TabNameDTO> listTab = new ArrayList<>();
    public List<KeyValueListDTO> listKeyValue = new ArrayList<>();
    public ArrayList<PhotoListModel> imageListAct = new ArrayList<>();
    public ArrayList<PhotoListModel> docListAct = new ArrayList<>();
    public ArrayList<PhotoListModel> audioListAct = new ArrayList<>();
    public ArrayList<PhotoListModel> signatureListAct = new ArrayList<>();
    public ArrayList<PhotoListModel> sketchListAct = new ArrayList<>();
    public ArrayList<Integer> idSectinHideList = new ArrayList<>();
    public ArrayList<Integer> idShowSectionList = new ArrayList<>();
    public ArrayList<Integer> idHideSubSectionList = new ArrayList<>();
    public static ArrayList<Integer> idShowSubSectionList = new ArrayList<>();
    public ArrayList<Integer> idHideQuestionList = new ArrayList<>();
    public ArrayList<Integer> idShowQuestionList = new ArrayList<>();
    public ArrayList<Integer> idHideFieldList = new ArrayList<>();
    public ArrayList<Integer> idShowFieldList = new ArrayList<>();
    public ArrayList<CustomLinearLayout> listView = new ArrayList<>();

    public CustomTabSubSectionAdapter customTabSubSectionAdapter;

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

    private boolean doubleBackToExitPressedOnce = false;
    public boolean isFinalAnswerEdit = false;
    public boolean isEditEnabled = false;
    public boolean SectionDependent = false;
    public boolean QuestionDependent = false;
    public static boolean isEditPageSection = false;
    public boolean isNextQuestionOfSection = false;
    public boolean isTabFlowEnabled = false;
    public boolean isQuestionFlowEnabled = false;
    public boolean isTabProcessEnabled = false;
    public boolean isTabProcessListPage = false;
    public boolean isTabProcessAddPage = false;
    public boolean isListening = false;
    public boolean isListen = true;
    public static boolean isClickedSpeak = false;
    public static boolean isClicked = false;
    public static boolean isClickedQuestionOk = false;
    public boolean isPlaying = false, isPause = false;

    public Object object;
    public String populateType = null;
    private String jsonFileName = "";
    public static String processName = "";
    public String fileName = "";
    public String displayText = "";
    public JSONObject mainJSON = null, subDetailJson;
    public JSONObject copyJSON;

    public String pocketbook, entryType;

    public static String directoryDraft;
    public static String directoryImage;
    public static String directoryDoc;
    public static String directoryAudio;
    public static String directoryOffline;
    public String tabName = "";
    ResponseModel responseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.process_activity_new);
        mContext = this;

//        jsonFileName = getIntent().getStringExtra(GenericConstant.JSON_FILE_NAME);
        responseModel = (ResponseModel) getIntent().getSerializableExtra(GenericConstant.JSON_FILE_NAME);
        processName = getIntent().getStringExtra(GenericConstant.PROCESS_NAME);
        fileName = getIntent().getStringExtra(GenericConstant.FILE_NAME_DRAFT);
        try {
            if (getIntent().getStringExtra(GenericConstant.JSON_OBJECT) != null && !getIntent().getStringExtra(GenericConstant.JSON_OBJECT).equalsIgnoreCase("")) {
                copyJSON = new JSONObject(getIntent().getStringExtra(GenericConstant.JSON_OBJECT));
                //Log.e("COPY JSON ", "OBJECT >>>>> " + copyJSON.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Log.e("File INTENT ", "NAME >>>>> " + fileName);
        directoryDraft = GenericConstant.FILE_DRAFT + processName + "/";
        directoryImage = GenericConstant.FILE_IMAGES + processName + "/";
        directoryDoc = GenericConstant.FILE_DOCS + processName + "/";
        directoryAudio = GenericConstant.FILE_AUDIOS + processName + "/";
        directoryOffline = GenericConstant.FILE_OFFLINE + processName + "/";

        /*File fileDraft = new File(Utilities.getDirPath(this, directoryDraft));
        boolean isPresentDraft = fileDraft.exists() && fileDraft.isDirectory();
        if(! isPresentDraft) {
            fileDraft.mkdir();
        }*/

        initView();
        SECTION_COUNT = 0;
        QUESTION_COUNT = 0;
        appSession = new AppSession(this);
        mProgressDialog = DialogUtil.showProgressDialog(this);
        loadJSON();
        loadSearchJSON();
    }

    /**
     * Load Initial JSON
     */
    private void loadJSON() {
        new Thread(() -> {
            try {
//                String strJson = JsonUtil.getInstance().loadJsonFromAsset(mContext, jsonFileName);
//                ResponseModel responseModel = (ResponseModel) JsonUtil.getInstance().toModel(strJson, ResponseModel.class);

                runOnUiThread(() -> {
                    if (responseModel != null) {
                        mProcessListBean = responseModel.getProcessList().get(0);
                        aPopulateListBean = responseModel.getPopulateList();
                        appSession.setPopulateList(null);
                        appSession.setPopulateList(aPopulateListBean);
                    }
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    loadInitialComponent();
                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "loadJson");
            }
        }).start();
    }

    /**
     * Load Initial JSON
     */
    private void loadSearchJSON() {
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(mContext, GenericConstant.SEARCH_FIELDS);
                ResponseSearchModel responseModel = (ResponseSearchModel) JsonUtil.getInstance().toModel(strJson, ResponseSearchModel.class);

                runOnUiThread(() -> {
                    if (responseModel != null) {
                        aSearchListBean = responseModel.getSearchList();
                        appSession.setSearchList(null);
                        appSession.setSearchList(aSearchListBean);
                    }
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "loadSearchJSON");
            }
        }).start();
    }

    /**
     * Load initial basis component
     */
    private void loadInitialComponent() {
        try {
            initializeSpeech();
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "initializeSpeech");
        }
        if (mProcessListBean != null) {
            if (fileName != null && !fileName.equalsIgnoreCase("")) {
                Log.e("File ", "NAME >>>>> " + fileName);
                String draftJSON = Utilities.getInstance(this).readfile(fileName, directoryDraft);
                if (draftJSON != null && !draftJSON.equalsIgnoreCase("")) {
                    try {
                        mainJSON = new JSONObject(draftJSON);
                        Log.e("DRAFT ", "JSON >>>>> " + mainJSON.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                //Log.e("File ", "name >>>>> " + fileName);
                fileName = BasicMethodsUtil.getInstance().getServerName(mProcessListBean.getProcess_Name() + "_" + System.currentTimeMillis());
                mainJSON = new JSONObject();
            }
        }
        createDialog();
        SECTION_COUNT = 0;
        new Handler().postDelayed(() -> {
            try {
                prepareTab();
                SetClick.setProcessClick(this);
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "loadingTab");
            }
        }, 700);
    }

    /**
     * Load the views
     */
    private void initView() {
        ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(processName);
        llTabLayout = findViewById(R.id.ll_horizontal);
        progressBar = findViewById(R.id.progress_horizontal);
        llProcessLayout = findViewById(R.id.llProcessLayout);
        rvProcessLayout = findViewById(R.id.rv_process);
        cvEditText = findViewById(R.id.cv_edittext);
        btnNext = findViewById(R.id.btn_next);
        btnSubmit = findViewById(R.id.btn_submit);
        btnReturn = findViewById(R.id.btn_return);
        btnAdd = findViewById(R.id.btn_add);
        btnSave = findViewById(R.id.btn_save);
        etInfo = findViewById(R.id.et_info);
        ivSend = findViewById(R.id.iv_send);

        micAnimation = AnimationUtils.loadAnimation(this, R.anim.fadescale);
        ivRings = findViewById(R.id.iv_rings);
        horizontalScrollView = findViewById(R.id.horizontal_scroll);
        scrollViewProcess = findViewById(R.id.scrollViewProcess);
        ivSpeaker = findViewById(R.id.iv_speaker);
        ImageView ivRefresh = findViewById(R.id.iv_refresh);
        ImageView ivDraft = findViewById(R.id.iv_draft);
        ivArrowLeft = findViewById(R.id.iv_arrow_left);
        ivArrowRight = findViewById(R.id.iv_arrow_right);

        btnNext.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnReturn.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        ivSpeaker.setOnClickListener(this);
        ivSend.setOnClickListener(this);
        ivRefresh.setOnClickListener(this);
        ivDraft.setOnClickListener(this);
        scrollViewProcess.post(new Runnable() {
            public void run() {
                scrollViewProcess.fullScroll(scrollViewProcess.FOCUS_DOWN);
            }
        });

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

        horizontalScrollView.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (horizontalScrollView.getChildAt(0).getRight()
                                <= (horizontalScrollView.getWidth() + horizontalScrollView.getScrollX())) {
                            //scroll view is at right
                            ivArrowRight.setVisibility(View.GONE);
                        } else {
                            //scroll view is not at right
                            ivArrowRight.setVisibility(View.VISIBLE);
                        }
                        if (horizontalScrollView.getScrollX() == 0) {
                            //scroll view is at left
                            ivArrowLeft.setVisibility(View.GONE);
                        } else {
                            //scroll view is not at right
                            ivArrowLeft.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    /**
     * Initialize the speech engine.
     */
    private void initializeSpeech() throws Exception {
        try {
            mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
            mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false);
            mSpeechRecognizerIntent.putExtra("android.speech.extra.DICTATION_MODE", true);
            // mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true);
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());

            speakToPerson = new SpeakToPersonUtil();

            SpeakToPhoneUtil.TypeSpeechResult typeSpeechResult = new SpeakToPhoneUtil.TypeSpeechResult() {
                @Override
                public void speechResult(String content) {
                    try {
                        if (isClickedSpeak) {
                            isClickedSpeak = false;
                            llAdd.removeAllViews();
                            captureValue = content;
                            etInfo.setText("");
                            if (aExpectedAnswerListBeans != null && aExpectedAnswerListBeans.size() > 0) {
                                for (int i = 0; i < aExpectedAnswerListBeans.size(); i++) {
                                    if (aExpectedAnswerListBeans.get(i).getAnswer().toLowerCase().contains(captureValue.toLowerCase())) {
                                        buttonId = aExpectedAnswerListBeans.get(i).getButton_Id();
                                        break;
                                    }
                                }
                            }
                            if (isTabProcessEnabled) {
                                ServerRequest.setSubJsonServerRequest(ProcessCreationActivity.this, mExpectedQuestionListBean.getActualQuestion(), captureValue, null);
                            } else {
                                ServerRequest.setServerRequest(ProcessCreationActivity.this, mPageSectionListBean.getPageSection_Name(), mExpectedQuestionListBean.getActualQuestion(), captureValue, null);
                            }
                            nextIteration();
                            scrollDown();
                            Utilities.hideKeyboard(ProcessCreationActivity.this);
                        }
                    } catch (Exception e) {
                        ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "speechResult");
                    }
                }
            };
            SpeakToPhoneUtil speakToPhone = new SpeakToPhoneUtil();
            speakToPhone.speak(mContext, mSpeechRecognizer, mSpeechRecognizerIntent, typeSpeechResult);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "initializeSpeech");
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.setLanguage(Locale.UK);
            //changeMicState(MicState.Speaking);
            textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {

                }

                @Override
                public void onDone(String utteranceId) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (isListen) {
                                ivSend.setAlpha(0.4f);
                                ivRings.setVisibility(View.VISIBLE);
                                ivRings.startAnimation(micAnimation);
                                mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                            }
                        }
                    });
                }

                @Override
                public void onError(String utteranceId) {

                }
            });
        }
    }

    // prepare section tabs according to section name
    private void prepareTab() throws Exception {
        //try {;
        if (mProcessListBean != null) {
            System.out.println("List Size : " + mProcessListBean.getPageSectionList().size());

            aPageSectionListBean = mProcessListBean.getPageSectionList();
            appSession.setSectionList(null);
            appSession.setSectionList(aPageSectionListBean);

            if (aPageSectionListBean != null && aPageSectionListBean.size() > 0) {
                //setting progress bar
                progressBar.setMax(aPageSectionListBean.size() * 100);

                for (int i = 1; i <= aPageSectionListBean.size(); i++) {

                    CreateDynamicView.createTab(mContext, aPageSectionListBean.get(i - 1).getPageSection_Id(), aPageSectionListBean.get(i - 1).getPageSection_Name(), llTabLayout, SetClick.onClickTab);

                    //CreateDynamicView.createProcessLayout(mContext, i, llProcessLayout);
                }
                if (mainJSON == null || mainJSON.length() == 0) {
                    mainJSON = new JSONObject();
                    mainJSON = ServerRequest.createServerRequest(this);
                    if (copyJSON != null && copyJSON.length() > 0) {
                        //Log.e("COPY JSON ", "OBJECT >>>>> " + copyJSON.toString());
                        ServerRequest.copyJSON(this, copyJSON);
                        //Log.e("COPY JSON ", "AFTER OBJECT >>>>> " + mainJSON.toString());
                    }
                    saveDraft();
                    //Utilities.getInstance(mContext).writeFile(mainJSON.toString(), fileName, directoryDraft);
                }
                prepareScreen();
            } else {
                BasicMethodsUtil.getInstance().showToast(mContext, "Sections are completed");
            }
        }
        /*} catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "prepareTab");
        }*/
    }

    /**
     * Method for prepare the screen
     */
    public void prepareScreen() throws Exception {
        try {
            for (int i = SECTION_COUNT; i < aPageSectionListBean.size(); i++) {

                Log.e("SECTION ", "COUNT >>>>> " + SECTION_COUNT);

                for (int j = 0; j < SECTION_COUNT; j++) {
                    CustomLinearLayout customLinear = findViewById(aPageSectionListBean.get(j).getPageSection_Id());
                    View viewImage = customLinear.getChildAt(0);
                    CustomImageView customImageView = (CustomImageView) viewImage;
                    customImageView.setImageResource(R.drawable.ic_dot_green);

                    View view = customLinear.getChildAt(1);
                    CustomTextView customTextView = (CustomTextView) view;
                    customTextView.setTextColor(mContext, customTextView, ViewPropertiesConstant.Color_Green);
                }

                CustomLinearLayout customLinearLayout = findViewById(aPageSectionListBean.get(i).getPageSection_Id());
                SectionTabID = aPageSectionListBean.get(i).getPageSection_Id();
                ClickedTabID = aPageSectionListBean.get(i).getPageSection_Id();
                mPageSectionListBean = aPageSectionListBean.get(i);
                if (customLinearLayout.getVisibility() == View.GONE) {
                    QUESTION_COUNT = 0;
                    isNextSection();
                    break;
                } else {
                    View viewImage = customLinearLayout.getChildAt(0);
                    CustomImageView customImageView = (CustomImageView) viewImage;
                    customImageView.setImageResource(R.drawable.ic_dot_blue);

                    View view = customLinearLayout.getChildAt(1);
                    CustomTextView customTextView = (CustomTextView) view;
                    customTextView.setTextColor(mContext, customTextView, ViewPropertiesConstant.Color_Primary);
                    scrollRight(customLinearLayout);

                    ServerRequest.setServerRequest(this, mPageSectionListBean.getPageSection_Name(), "SectionComplete", "true", null);
                    llProcessLayout.removeAllViews();
                    rvProcessLayout.setVisibility(View.GONE);
                    setProgressAnimate(progressBar, SECTION_COUNT);

                    sectionIndex = i;
                    specialLogic = null;
                    QUESTION_COUNT = 0;
                    pageSectionId = 0;
                    captureValue = "";
                    mExpectedQuestionListBean = null;
                    mExpectedAnswerListBean = null;

                    Log.d(TAG, "Statement: " + aPageSectionListBean.get(i).getStatement());
                    statement = aPageSectionListBean.get(i).getStatement();
                    aSubSectionListBean = aPageSectionListBean.get(i).getSubSectionList();

                    if (aSubSectionListBean != null && aSubSectionListBean.size() > 0) {

                        isTabFlowEnabled = true;
                        if(processName.equalsIgnoreCase(getResources().getString(R.string.pocket_book))) {
                            ProcessCreationActivity.btnSubmit.setVisibility(View.VISIBLE);
                            ProcessCreationActivity.btnNext.setVisibility(View.GONE);
                        } else {
                            ProcessCreationActivity.btnSubmit.setVisibility(View.GONE);
                            ProcessCreationActivity.btnNext.setVisibility(View.VISIBLE);
                        }
                        ProcessCreationActivity.cvEditText.setVisibility(View.GONE);

                        PrepareSubSection.prepareScreen(this);

                    } else {
                        isTabFlowEnabled = false;
                        ProcessCreationActivity.btnNext.setVisibility(View.GONE);
                        ProcessCreationActivity.cvEditText.setVisibility(View.VISIBLE);

                        aExpectedQuestionListBean = aPageSectionListBean.get(i).getExpectedQuestionList();
                        aPageSection_detailListBean = aPageSectionListBean.get(i).getPageSection_detailList();

                        if (aExpectedQuestionListBean != null && aExpectedQuestionListBean.size() > 0) {

                            //Call the create Question list method
                            appSession.setExpectedQuestionList(null);
                            appSession.setExpectedQuestionList(aExpectedQuestionListBean);
                            CreateQuestion.createQuestions(this, aExpectedQuestionListBean);

                        } else if (aPageSection_detailListBean != null && aPageSection_detailListBean.size() > 0) {
                            //call the Pagelist method

                            pageSectionId = aPageSectionListBean.get(i).getPageSection_Id() * 1000;
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
                            String check = null;
                            for (int k = 0; k < answerList.size(); k++) {
                                if (answerList.get(k).getValue() != null && !answerList.get(k).getValue().equalsIgnoreCase("")) {
                                    check = answerList.get(k).getValue();
                                    break;
                                }
                            }
                            if (check != null && !check.equalsIgnoreCase("")) {
                                SetRecord.setRecords(this);
                                isNextSection();
                                break;
                            }
                            PageSection.createPageSectionDetails(this, aPageSectionListBean.get(i).getPageSection_detailList(), GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);

                        }
                    }
                    break;
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "prepareScreen");
        }
    }

    /**
     * Set the Progress animate method
     *
     * @param pb
     * @param progressTo
     */
    private void setProgressAnimate(ProgressBar pb, int progressTo) {
        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", pb.getProgress(), progressTo * 100);
        animation.setDuration(700);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    /**
     * Create Custom Dialog to load dynamic fields
     */
    private void createDialog() {
        dialogLinear = new Dialog(this, R.style.CustomDialogAnimation);
        dialogLinear.setCanceledOnTouchOutside(false);
        dialogLinear.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialogLinear.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialogLinear.setContentView(R.layout.linear_dialog);
        Objects.requireNonNull(dialogLinear.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialogLinear.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        tvHeader = dialogLinear.findViewById(R.id.tv_header);
        llAdd = dialogLinear.findViewById(R.id.ll_add);
        TextView tvOk = dialogLinear.findViewById(R.id.tv_submit);

        tvOk.setOnClickListener(v -> {
            // Call next section once the details is saved.
            if (Utilities.isMyServiceRunning(mContext, MyLocationServices.class)) {
                Intent i = new Intent(mContext, MyLocationServices.class);
                i.setAction(MyLocationServices.ACTION_STOP_FOREGROUND_SERVICE);
                ContextCompat.startForegroundService(mContext, i);
            }

            DialogValue.getValuesFromDialog(this);
            if (answerList != null && answerList.size() > 0) {

                for (int i = 0; i < answerList.size(); i++) {
                    if (answerList.get(i).isMendatry() && answerList.get(i).getValue().equalsIgnoreCase("")) {

                        BasicMethodsUtil.getInstance().showToast(mContext, getResources().getString(R.string.mandatory_fields_require));
                        Log.e("Mendatry ", " >>>>> " + answerList.get(i).getKey() + " >> " + answerList.get(i).getValue());
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
                }
                PopulateFields.populate(this, object, populateType);
            }
            dialogLinear.dismiss();
            scrollDown();

        });

        dialogLinear.setOnCancelListener(new DialogInterface.OnCancelListener() {

            public void onCancel(DialogInterface dialog) {
                Log.e("Question count ", ">> dialog >>>> back " + QUESTION_COUNT);
                isEditPageSection = false;
                llAdd.removeAllViews();
                if (QUESTION_COUNT > 0) {
                    if (mExpectedAnswerListBean != null) {
                        QUESTION_COUNT = QUESTION_COUNT - mExpectedAnswerListBean.getSkipQuestion();
                    }
                    if (mExpectedQuestionListBean != null) {
                        QUESTION_COUNT = QUESTION_COUNT - mExpectedQuestionListBean.getSkipQuestion();
                    }
                    QUESTION_COUNT--;
                    isNextQuestion();
                    scrollDown();
                } else if (QUESTION_COUNT == 0 && SECTION_COUNT >= 0) {
                    if (!isTabProcessEnabled) {
                        SectionTabID = 0;
                        currentQuestion = "";
                        SECTION_COUNT--;
                        isNextSection();
                    }
                }
                isClicked = true;
                isClickedSpeak = true;
            }
        });
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_speaker:
                if (isListening) {
                    ivSpeaker.setImageResource(R.drawable.ic_speaker_off);
                    if (textToSpeech != null) {
                        textToSpeech.stop();
                        textToSpeech.shutdown();
                    }
                    isListening = false;
                } else {
                    ivSpeaker.setImageResource(R.drawable.ic_speaker_on);
                    textToSpeech = new TextToSpeech(this, this);
                    isListening = true;
                }
                break;

            case R.id.iv_send:
                Utilities.hideKeyboard(ProcessCreationActivity.this);
                if (isListen) {
                    ivSend.setAlpha(0.4f);
                    ivRings.setVisibility(View.VISIBLE);
                    ivRings.startAnimation(micAnimation);
                    mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                } else {
                    if (!isClickedQuestionOk) {
                        llAdd.removeAllViews();
                        captureValue = etInfo.getText().toString().trim();
                        etInfo.setText("");
                        if (aExpectedAnswerListBeans != null && aExpectedAnswerListBeans.size() > 0) {
                            for (int i = 0; i < aExpectedAnswerListBeans.size(); i++) {
                                if (aExpectedAnswerListBeans.get(i).getAnswer().toLowerCase().contains(captureValue.toLowerCase())) {
                                    buttonId = aExpectedAnswerListBeans.get(i).getButton_Id();
                                    break;
                                }
                            }
                        }
                        if (isTabProcessEnabled) {
                            ServerRequest.setSubJsonServerRequest(ProcessCreationActivity.this, mExpectedQuestionListBean.getActualQuestion(), captureValue, null);
                        } else {
                            ServerRequest.setServerRequest(ProcessCreationActivity.this, mPageSectionListBean.getPageSection_Name(), mExpectedQuestionListBean.getActualQuestion(), captureValue, null);
                        }
                        nextIteration();
                        scrollDown();
                    }
                }
                break;

            case R.id.iv_refresh:
                if(isTabProcessEnabled) {
                    if(isTabProcessAddPage) {
                        DialogUtil.refreshDialog(this, getResources().getString(R.string.refresh), SetClick.onClickRefresh);
                    }
                } else {
                    DialogUtil.refreshDialog(this, getResources().getString(R.string.refresh), SetClick.onClickRefresh);
                }
                break;

            case R.id.iv_draft:
                saveDraft();
                //Utilities.getInstance(mContext).writeFile(mainJSON.toString(), fileName, directoryDraft);
                Toast.makeText(mContext, getResources().getString(R.string.draft_saved), Toast.LENGTH_SHORT).show();
                break;

            case R.id.iv_back:
                if (isTabFlowEnabled) {
                    createTabBack();
                } else if (isQuestionFlowEnabled) {
                    isQuestionFlowEnabled = false;
                    OpenSection.openSection(this, SectionTabID);
                } else {
                    DialogUtil.saveDraftDialog(this, getResources().getString(R.string.save_draft));
                }
                break;

            case R.id.btn_return:
                try {
                    if (isTabFlowEnabled) {
                        createTabBack();
                    } else {
                        OpenSection.openSection(this, SectionTabID);
                    }
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "btnReturnClick");
                }
                break;

            case R.id.btn_add:
                btnNext.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.GONE);
                cvEditText.setVisibility(View.VISIBLE);
                btnReturn.setVisibility(View.GONE);
                btnAdd.setVisibility(View.GONE);

                try {
                    TabClick.openAddScreen();
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "btnAddClick");
                }
                break;

            case R.id.btn_next:
                try {
                    isTabFlowEnabled = false;
                    isTabProcessEnabled = false;
                    isTabProcessListPage = false;
                    isTabProcessAddPage = false;
                    isNextSection();
                    btnNext.setVisibility(View.GONE);
                    btnSubmit.setVisibility(View.GONE);
                    cvEditText.setVisibility(View.VISIBLE);
                    btnReturn.setVisibility(View.GONE);
                    btnAdd.setVisibility(View.GONE);
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "btnNesxtClick");
                }
                break;

            case R.id.btn_submit:
                try {
                    isNextSection();
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "btnSubmitClick");
                }
                break;

            case R.id.btn_save:
                try {
                    isTabProcessListPage = true;
                    isTabProcessAddPage = false;

                    String mainName = BasicMethodsUtil.getInstance().getNormalServerName(tabFlowPosList.get(tabFlowPosList.size() - 1).getArrayName());
                    String btnText = getResources().getString(R.string.return_to) + " " + mainName;
                    ProcessCreationActivity.btnReturn.setText(btnText);

                    if (isTabProcessEnabled) {
                        isNextSectionTabProcess();
                    }

                    btnNext.setVisibility(View.GONE);
                    btnSubmit.setVisibility(View.GONE);
                    btnSave.setVisibility(View.GONE);
                    cvEditText.setVisibility(View.GONE);
                    btnReturn.setVisibility(View.VISIBLE);
                    btnAdd.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "btnNesxtClick");
                }
                break;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.process_menu, menu);
        //menu.setHeaderTitle("Context Menu");
        //menu.add(0, v.getId(), 0, "Edit");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                try {
                    ProcessCreationActivity.QuestionID = ProcessCreationActivity.QUESTION_COUNT;
                    if (customLinearLayoutEdit.getChildCount() > 2) {
                        isEditPageSection = true;
                    } else {
                        View vChild = customLinearLayoutEdit.getChildAt(1);
                        if (vChild instanceof CustomLinearLayout) {
                            CustomLinearLayout cLinear = (CustomLinearLayout) vChild;
                            if (cLinear.getChildCount() == 1) {
                                isEditPageSection = false;
                            } else {
                                isEditPageSection = true;
                            }
                        }
                    }
                    editId = customLinearLayoutEdit.getId();
                    Log.e("LONG PRESS", " >>>>> " + editId);

                    if (isEditPageSection) {
                        if (isTabProcessEnabled) {
                            EditPageSectionFields.editAnswerTabProcess(this, editId);
                        } else {
                            EditPageSectionFields.editAnswer(this, editId);
                        }
                        String valueCheck = null;
                        for (int i = 0; i < answerList.size(); i++) {
                            if(answerList.get(i).getKey().contains(FieldsNameConstant.SYSTEM)) {
                                valueCheck = answerList.get(i).getValue();
                                break;
                            }
                        }
                        Log.e("valueCheck", " >>>>> " + valueCheck);
                        if (valueCheck != null && !valueCheck.equalsIgnoreCase("")) {
                            isEditPageSection = false;
                            Intent editIntent = new Intent(mContext, EditAnswerActivity.class);
                            editIntent.putExtra(GenericConstant.SECTION_NAME, mPageSectionListBean.getPageSection_Name());
                            editIntent.putExtra(GenericConstant.EDIT_ID, editId);
                            editIntent.putExtra(GenericConstant.PROCESS_NAME, processName);
                            startActivityForResult(editIntent, GenericConstant.EDIT_REQUEST);
                            overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
                        } else {
                            llAdd.removeAllViews();
                            EditPageSectionFields.createPageSectionDetails(this, aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                        }
                    } else {
                        Intent editIntent = new Intent(mContext, EditAnswerActivity.class);
                        editIntent.putExtra(GenericConstant.SECTION_NAME, mPageSectionListBean.getPageSection_Name());
                        editIntent.putExtra(GenericConstant.EDIT_ID, editId);
                        editIntent.putExtra(GenericConstant.PROCESS_NAME, processName);
                        startActivityForResult(editIntent, GenericConstant.EDIT_REQUEST);
                        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
                    }
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "ClickEditContextMenu");
                }
                break;
        }
        return true;
    }

    /**
     * Speak Method to call for voice
     *
     * @param speakText
     */
    public void speakNow(String speakText) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SpeakToPersonUtil.speakToPerson(mContext, textToSpeech, speakText);
            }
        }, 700);
    }

    /**
     * Check the Question flow basis on the QUESTION_COUNT
     */
    public void isNextQuestion() {
        try {
            QUESTION_COUNT++;
            if (aExpectedQuestionListBean != null) {
                if (QUESTION_COUNT == aExpectedQuestionListBean.size()) {
                    if (isTabProcessEnabled) {
                        isNextSectionTabProcess();
                    } else {
                        isNextSection();
                    }
                } else {
                    CreateQuestion.createQuestions(this, aExpectedQuestionListBean);
                }
            } else {
                if (isTabProcessEnabled) {
                    isNextSectionTabProcess();
                } else {
                    isNextSection();
                }
            }
            saveDraft();
            //Utilities.getInstance(mContext).writeFile(mainJSON.toString(), fileName, directoryDraft);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "isNextQuestion");
        }
    }

    /**
     * Check the iteration of Flow
     */
    public void nextIteration() {
        CreateAnswer.createAnswers(this);
    }

    /**
     * Check the section count and move to next tab or section
     */
    public void isNextSection() {
        try {
            isEditEnabled = false;
            if (SectionTabID != 0 && SectionTabID != mPageSectionListBean.getPageSection_Id()) {
                OpenSection.openSection(this, SectionTabID);
            } else {
                SECTION_COUNT++;
                if (SECTION_COUNT < aPageSectionListBean.size()) {
                    prepareScreen();
                    saveDraft();
                    //Utilities.getInstance(mContext).writeFile(mainJSON.toString(), fileName, directoryDraft);
                } else {
                    saveOffline();
                    //Utilities.getInstance(mContext).writeFile(mainJSON.toString(), fileName, directoryOffline);
                    //Utilities.getInstance(mContext).deleteProcessFile(fileName, directoryDraft);
                    Log.e("Section ", "Server Request : " + mainJSON.toString());
                    finalDialog(getResources().getString(R.string.submit_congratulations));
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "isNextSection");
        }
    }

    /**
     * move to tab screen on sub section like in crime module
     */
    public void isNextSectionTabProcess() {
        try {
            isEditEnabled = false;
            isTabProcessAddPage = false;
            AddSubJsonList.addList(this);
            CreateListFromJson.createListFromAddNBack(this);
            AddSubJsonList.showList(this);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "isNextSection");
        }
    }

    public void saveDraft() {
        Utilities.getInstance(mContext).writeFile(mainJSON.toString(), fileName, directoryDraft);

        ArrayList<ProcessLogDTO> draftFileList = new ArrayList<>();
        if(appSession.getDraftList() != null) {
            draftFileList.addAll(appSession.getDraftList());
        }

        if(draftFileList.size() > 0) {
            for (int i = 0; i < draftFileList.size(); i++) {
                if (draftFileList.get(i).getFileName().equalsIgnoreCase(fileName)) {
                    return;
                }
            }
        }
        ProcessLogDTO draftDTO = new ProcessLogDTO();
        draftDTO.setProcessName(processName);
        draftDTO.setDisplayText(displayText);
        draftDTO.setFileType(GenericConstant.DRAFT_FILE);
        draftDTO.setFilePath(directoryDraft);
        draftDTO.setFileName(fileName);
        draftDTO.setTime(DateUtil.getDateTime(fileName));
        draftDTO.setDate(DateUtil.getDate(fileName));
        draftDTO.setGroupFlag("0");
        draftDTO.setPinFlag("0");
        draftFileList.add(draftDTO);
        appSession.setDraftList(null);
        appSession.setDraftList(draftFileList);
    }

    public void saveOffline() {
        Utilities.getInstance(mContext).writeFile(mainJSON.toString(), fileName, directoryOffline);
        Utilities.getInstance(mContext).deleteProcessFile(fileName, directoryDraft);

        ArrayList<ProcessLogDTO> draftFileList = new ArrayList<>();
        if(appSession.getDraftList() != null) {
            draftFileList.addAll(appSession.getDraftList());
        }
        if(draftFileList.size() > 0) {
            for (int i = 0; i < draftFileList.size(); i++) {
                if (draftFileList.get(i).getFileName().equalsIgnoreCase(fileName)) {
                    draftFileList.remove(i);
                    break;
                }
            }
        }
        appSession.setDraftList(null);
        appSession.setDraftList(draftFileList);

        ArrayList<ProcessLogDTO> offlineList = new ArrayList<>();
        if(appSession.getOfflineList() != null) {
            offlineList.addAll(appSession.getOfflineList());
        }

        if(offlineList.size() > 0) {
            for (int i = 0; i < offlineList.size(); i++) {
                if (offlineList.get(i).getFileName().equalsIgnoreCase(fileName)) {
                    return;
                }
            }
        }
        ProcessLogDTO draftDTO = new ProcessLogDTO();
        draftDTO.setProcessName(processName);
        draftDTO.setDisplayText(displayText);
        draftDTO.setFileType(GenericConstant.OFFLINE_FILE);
        draftDTO.setFilePath(directoryOffline);
        draftDTO.setFileName(fileName);
        draftDTO.setTime(DateUtil.getDateTime(fileName));
        draftDTO.setDate(DateUtil.getDate(fileName));
        draftDTO.setGroupFlag("0");
        draftDTO.setPinFlag("0");
        offlineList.add(draftDTO);
        appSession.setOfflineList(null);
        appSession.setOfflineList(offlineList);
    }

    public void finalDialog(String text) {
        DialogUtil.submitDialog(this, text);
    }

    public void openProcess(String text) {
        appSession.setImageList(null);
        ProcessCreationActivity.SECTION_COUNT = 0;
        ProcessCreationActivity.QUESTION_COUNT = 0;
        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);
        Intent intent1 = new Intent(ProcessCreationActivity.this, ProcessCreationActivity.class);
        intent1.putExtra(GenericConstant.FILE_NAME_DRAFT, "");
        intent1.putExtra(GenericConstant.JSON_OBJECT, mainJSON.toString());
        intent1.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.DOMESTIC_PROCESS_JSON);
        intent1.putExtra(GenericConstant.PROCESS_NAME, text);
        startActivity(intent1);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        if (mSpeechRecognizer != null) {
            mSpeechRecognizer.cancel();
            mSpeechRecognizer.destroy();
        }
        super.onDestroy();
    }

    // automatically scroll down on screen with question
    public void scrollDown() {
        scrollViewProcess.post(new Runnable() {
            @Override
            public void run() {
                scrollViewProcess.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    // automatically scroll tabs right with completion of section
    public void scrollRight(CustomLinearLayout customLinearLayout) {
        int x, y;
        x = customLinearLayout.getLeft();
        y = customLinearLayout.getTop();
        //Log.e("VALUE ", "X >>>>> "+x+" Y >>>>> "+y);
        horizontalScrollView.scrollTo((x - 200), y);
    }

    public void callTempPageSectionDetails(ArrayList<AnswerValueDTO> searchList) {
        specialLogic = null;
        llAdd.removeAllViews();
        PageSection.createPageSectionDetails(ProcessCreationActivity.this, aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
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
            PopulateFields.populateType(this, organisationDetailsList, GenericConstant.ORGANISATION);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "onPersonSelected");
        }
    }

    @Override
    public void onTeamSelected(TeamBean teamBean) {
        try {
            teamCustomDialogFragment.dismiss();
            //appSession.setTeam(teamBean);
            PopulateFields.populateType(this, teamBean, GenericConstant.TEAM);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "onPersonSelected");
        }
    }

    @Override
    public void onPersonSelected(PersonBean personBean) {
        try {
            personCustomDialogFragment.dismiss();
            //appSession.setPerson(personBean);
            PopulateFields.populateType(this, personBean, GenericConstant.PERSON);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "onPersonSelected");
        }
    }

    @Override
    public void onVehicleSelected(VehicleBean vehicleBean) {
        vehicleCustomDialogFragment.dismiss();
        //appSession.setVehicle(vehicleBean);
        PopulateFields.populateType(this, vehicleBean, GenericConstant.VEHICLE);
    }

    @Override
    public void onAddressSelected(AddressBean addressBean) {
        //appSession.setAddress(addressBean);
        addressCustomDialogFragment.dismiss();
        PopulateFields.populateType(this, addressBean, GenericConstant.ADDRESS);
    }

    @Override
    public void onEventSelected(EventSearchList eventSearchList) {
        try {
            eventCustomDialogFragment.dismiss();
            //appSession.setEvent(eventSearchList);
            PopulateFields.populateType(this, eventSearchList, GenericConstant.EVENT);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "onPersonSelected");
        }
    }

    @Override
    public void onAllegationSelected(OffenceDefinitionList offenceDefinitionList) {
        allegationCustomDialogFragment.dismiss();
        //appSession.setAllegation(offenceDefinitionList);
        PopulateFields.populateType(this, offenceDefinitionList, GenericConstant.ALLEGATION);
    }

    @Override
    public void onOffenceSelected(HOOffenceList hoOffenceList) {
        offenceCustomDialogFragment.dismiss();
        //appSession.setOffence(hoOffenceList);
        PopulateFields.populateType(this, hoOffenceList, GenericConstant.OFFENCE);
    }

    @Override
    public void onCrimeGroupSelected(CrimeGroupList crimeGroupList) {
        crimeGroupCustomDialogFragment.dismiss();
        //appSession.setCrimeGroup(crimeGroupList);
        PopulateFields.populateType(this, crimeGroupList, GenericConstant.CRIMEGROUP);
    }

    @Override
    public void onManuallyClicker(int type) {

        switch (type) {
            case GenericConstant.TYPE_PERSON:
                personCustomDialogFragment.dismiss();
                specialLogic = null;
                llAdd.removeAllViews();
                PageSection.createPageSectionDetails(this, aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                DialogValue.setValueInDialog(this, answerList);

                break;

            case GenericConstant.TYPE_VEHICLE:
                vehicleCustomDialogFragment.dismiss();
                specialLogic = null;
                llAdd.removeAllViews();
                PageSection.createPageSectionDetails(this, aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                DialogValue.setValueInDialog(this, answerList);

                break;

            case GenericConstant.TYPE_ADDRESS:
                addressCustomDialogFragment.dismiss();
                specialLogic = null;
                llAdd.removeAllViews();
                PageSection.createPageSectionDetails(this, aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                DialogValue.setValueInDialog(this, answerList);

                break;
        }
    }

    @Override
    public void dialogCancelled() {
        Log.e("Question count ", ">> Fragmrnt dialog >>>> cancelled " + QUESTION_COUNT);
        if (mExpectedAnswerListBean != null) {
            QUESTION_COUNT = QUESTION_COUNT - mExpectedAnswerListBean.getSkipQuestion();
        }
        if (mExpectedQuestionListBean != null) {
            QUESTION_COUNT = QUESTION_COUNT - mExpectedQuestionListBean.getSkipQuestion();
        }
        isEditPageSection = false;
        if (QUESTION_COUNT > 0) {
            QUESTION_COUNT--;
            isNextQuestion();
            scrollDown();
        } else if (QUESTION_COUNT == 0 && SECTION_COUNT > 0) {
            if (!isTabProcessEnabled) {
                SectionTabID = 0;
                currentQuestion = "";
                SECTION_COUNT--;
                isNextSection();
            }
        }
        isClicked = true;
        isClickedSpeak = true;
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
            organisationCustomDialogFragment = OrganisationCustomDialogFragment.newInstance(captureValue, processName);
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
            teamCustomDialogFragment = TeamCustomDialogFragment.newInstance(captureValue, processName);
            teamCustomDialogFragment.show(ft, GenericConstant.TEAM_LIST_DIALOG);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "loadTeamDialog");
        }
    }

    /**
     * Load Person Dialog
     */
    public void loadPersonDialog(ArrayList<AnswerValueDTO> searchList) {
        try {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.PERSON_LIST_DIALOG);
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            // Create and show the dialog.
            personCustomDialogFragment = PersonCustomDialogFragment.newInstance(captureValue, processName, GenericConstant.POPULATE_TRUE);
            personCustomDialogFragment.show(ft, GenericConstant.PERSON_LIST_DIALOG);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "loadPersonDialog");
        }
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
        vehicleCustomDialogFragment = VehicleCustomDialogFragment.newInstance(captureValue, processName, GenericConstant.POPULATE_TRUE);
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
        addressCustomDialogFragment = AddressCustomDialogFragment.newInstance(captureValue, processName, GenericConstant.POPULATE_TRUE);
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
        eventCustomDialogFragment = EventCustomDialogFragment.newInstance(captureValue, processName);
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
        allegationCustomDialogFragment = AllegationCustomDialogFragment.newInstance(captureValue, processName);
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
        offenceCustomDialogFragment = OffenceCustomDialogFragment.newInstance(captureValue, processName);
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
        crimeGroupCustomDialogFragment = CrimeGroupCustomDialogFragment.newInstance(captureValue, processName);
        crimeGroupCustomDialogFragment.show(ft, GenericConstant.CRIME_GROUP_LIST_DIALOG);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
    }

    /**
     * Call the Camera Activity
     */
    public void callCameraActivity(String status) {
        Intent cameraIntent = new Intent(mContext, CameraActivity.class);
        if (status.equalsIgnoreCase(GenericConstant.IMAGES_EDIT)) {
            if (imageListAct != null && imageListAct.size() > 0) {
                appSession.setImageList(null);
                appSession.setImageList(imageListAct);
            }
        } else {
            appSession.setImageList(null);
        }
        startActivityForResult(cameraIntent, GenericConstant.CAMERA_REQUEST);
        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
    }

    /**
     * Call the Attachment Activity
     */
    public void callAttachmentActivity(String status) {
        Intent attachmentIntent = new Intent(mContext, DocsUploadActivity.class);
        if (status.equalsIgnoreCase(GenericConstant.ATTACHMENT_EDIT)) {
            if (docListAct != null && docListAct.size() > 0) {
                attachmentIntent.putExtra(GenericConstant.ATTACHMENT_LIST, docListAct);
            }
        }
        startActivityForResult(attachmentIntent, GenericConstant.ATTACHMENT_REQUEST);
        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
    }

    /**
     * Call the Attachment Activity
     */
    public void callAudioActivity(String status) {
        Intent attachmentIntent = new Intent(mContext, AudioRecordActivity.class);
        if (status.equalsIgnoreCase(GenericConstant.AUDIO_EDIT)) {
            if (audioListAct != null && audioListAct.size() > 0) {
                attachmentIntent.putExtra(GenericConstant.AUDIO_LIST, audioListAct);
            }
        }
        startActivityForResult(attachmentIntent, GenericConstant.AUDIO_REQUEST);
        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
    }

    /**
     * Call the Attachment Activity
     */
    public void callPocketbookActivity(String status) {
        Intent attachmentIntent = new Intent(mContext, PocketBookActivity.class);
        if (status.equalsIgnoreCase(GenericConstant.POCKETBOOK_EDIT)) {
            CustomTextView customTextView = findViewById(R.id.tv_pocket);
            if (customTextView != null) {
                pocketbook = customTextView.getText().toString();
            }
            if (pocketbook != null && !pocketbook.equalsIgnoreCase("")) {
                attachmentIntent.putExtra(GenericConstant.POCKETBOOK_EDIT, pocketbook);
            }
        }
        startActivityForResult(attachmentIntent, GenericConstant.POCKETBOOK_REQUEST);
        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
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
     * Call the Signature Activity
     */
    public void callSignatureActivity(String status) {
        Intent sigIntent = new Intent(mContext, SignatureUploadActivity.class);
        if (status.equalsIgnoreCase(GenericConstant.SIGNATURE_EDIT)) {
            if (signatureListAct != null && signatureListAct.size() > 0) {
                sigIntent.putExtra(GenericConstant.SIGNATURE_LIST, signatureListAct);
            }
        }
        startActivityForResult(sigIntent, GenericConstant.SIGNATURE_REQUEST);
        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
    }

    /**
     * Call the Signature Activity
     */
    public void callSketchActivity(String status) {
        Intent sigIntent = null;
        if (status.equalsIgnoreCase(GenericConstant.SKETCH_EDIT)) {
            if (sketchListAct != null && sketchListAct.size() > 0) {
                sigIntent = new Intent(mContext, SketchActivity.class);
                sigIntent.putExtra(GenericConstant.SKETCH_LIST, sketchListAct);
            }
        } else {
            sigIntent = new Intent(mContext, SketchFirstActivity.class);
        }
        startActivityForResult(sigIntent, GenericConstant.SKETCH_REQUEST);
        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
    }

    /**
     * Call the Officer Activity
     */
    public void callOfficerActivity(ArrayList<AnswerValueDTO> searchList) {
        Intent officerIntent = new Intent(mContext, OfficerSearchActivity.class);
        officerIntent.putExtra("", "");
        startActivityForResult(officerIntent, GenericConstant.OFFICER_REQUEST);
        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(" RESULT ", " >>>>> " + resultCode + "  >>>>  " + data + " >>>>>  " + requestCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GenericConstant.CAMERA_REQUEST:
                    try {
                        imageListAct = new ArrayList<>();
                        imageListAct.addAll(appSession.getImageList());

                        if (imageListAct != null && imageListAct.size() > 0) {
                            appSession.setImageList(null);
                            if (isTabProcessEnabled) {
                                ServerRequest.setSubJsonServerRequest(this, FieldsNameConstant.Images, "", imageListAct);
                            } else {
                                ServerRequest.setServerRequest(this, mPageSectionListBean.getPageSection_Name(), FieldsNameConstant.Images, "", imageListAct);
                            }
                            CustomRecyclerView customRecyclerView = findViewById(R.id.rv_image);
                            if (customRecyclerView != null) {
                                for (int i = 0; i < imageListAct.size(); i++) {
                                    Log.e("Image ", "Name 1 >> " + imageListAct.get(i).getPhoto());
                                }
                                UserImageListAdaptor userImageListAdaptor = new UserImageListAdaptor(ProcessCreationActivity.this, imageListAct, SetClick.onClickImage, SetClick.onClickLongImageEdit, directoryImage);
                                customRecyclerView.setAdapter(userImageListAdaptor);
                            } else {
                                CreateDynamicView.userImageList(ProcessCreationActivity.this, appSession.getUserID(), llProcessLayout, imageListAct, SetClick.onClickImage, SetClick.onClickLongImageEdit);
                                llAdd.removeAllViews();
                                if (isNextQuestionOfSection) {
                                    isNextQuestion();
                                } else {
                                    if (isTabProcessEnabled) {
                                        isNextSectionTabProcess();
                                    } else {
                                        isNextSection();
                                    }
                                }
                            }
                        }
                        scrollDown();
                    } catch (Exception e) {
                        ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "CameraResult");
                    }
                    break;

                case GenericConstant.ATTACHMENT_REQUEST:
                    try {
                        docListAct.clear();
                        docListAct = (ArrayList<PhotoListModel>) data.getSerializableExtra(GenericConstant.ATTACHMENT_LIST);
                        if (docListAct != null && docListAct.size() > 0) {
                            if (isTabProcessEnabled) {
                                ServerRequest.setSubJsonServerRequest(this, FieldsNameConstant.Documents, "", docListAct);
                            } else {
                                ServerRequest.setServerRequest(this, mPageSectionListBean.getPageSection_Name(), FieldsNameConstant.Documents, "", docListAct);
                            }
                            CustomRecyclerView customRecyclerView = findViewById(R.id.rv_attachment);
                            if (customRecyclerView != null) {
                                UserDocListAdaptor userDocListAdaptor = new UserDocListAdaptor(ProcessCreationActivity.this, docListAct, SetClick.onClickDoc, SetClick.onClickLongDocEdit);
                                customRecyclerView.setAdapter(userDocListAdaptor);
                            } else {
                                //CustomLinearLayout cLinearLayout = findViewById(SECTION_COUNT + 1);
                                CreateDynamicView.userDocsList(ProcessCreationActivity.this, appSession.getUserID(), llProcessLayout, docListAct, SetClick.onClickDoc, SetClick.onClickLongDocEdit);
                                llAdd.removeAllViews();
                                if (isNextQuestionOfSection) {
                                    isNextQuestion();
                                } else {
                                    if (isTabProcessEnabled) {
                                        isNextSectionTabProcess();
                                    } else {
                                        isNextSection();
                                    }
                                }
                            }
                        }
                        scrollDown();
                    } catch (Exception e) {
                        ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "attachmentResult");
                    }

                    break;

                case GenericConstant.AUDIO_REQUEST:
                    try {
                        audioListAct = new ArrayList<>();
                        audioListAct = (ArrayList<PhotoListModel>) data.getSerializableExtra(GenericConstant.AUDIO_LIST);
                        if (audioListAct != null && audioListAct.size() > 0) {
                            if (isTabProcessEnabled) {
                                ServerRequest.setSubJsonServerRequest(this, FieldsNameConstant.Audios, "", audioListAct);
                            } else {
                                ServerRequest.setServerRequest(this, mPageSectionListBean.getPageSection_Name(), FieldsNameConstant.Audios, "", audioListAct);
                            }
                            CustomRecyclerView customRecyclerView = findViewById(R.id.rv_audio);
                            if (customRecyclerView != null) {
                                UserAudioListAdaptor userAudioListAdaptor = new UserAudioListAdaptor(ProcessCreationActivity.this, audioListAct, SetClick.onClickAudio, SetClick.onClickLongAudio);
                                customRecyclerView.setAdapter(userAudioListAdaptor);
                            } else {
                                //CustomLinearLayout cLinearLayout = findViewById(SECTION_COUNT + 1);
                                CreateDynamicView.userAudioList(ProcessCreationActivity.this, appSession.getUserID(), llProcessLayout, audioListAct, SetClick.onClickAudio, SetClick.onClickLongAudio);
                                llAdd.removeAllViews();
                                if (isNextQuestionOfSection) {
                                    isNextQuestion();
                                } else {
                                    if (isTabProcessEnabled) {
                                        isNextSectionTabProcess();
                                    } else {
                                        isNextSection();
                                    }
                                }
                            }
                        }
                        scrollDown();
                    } catch (Exception e) {
                        ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "attachmentResult");
                    }

                    break;

                case GenericConstant.SIGNATURE_REQUEST:
                    try {
                        signatureListAct = new ArrayList<>();
                        signatureListAct = (ArrayList<PhotoListModel>) data.getSerializableExtra(GenericConstant.SIGNATURE_LIST);

                        if (signatureListAct != null && signatureListAct.size() > 0) {
                            if (isTabProcessEnabled) {
                                ServerRequest.setSubJsonServerRequest(this, FieldsNameConstant.Signature, "", signatureListAct);
                            } else {
                                ServerRequest.setServerRequest(this, mPageSectionListBean.getPageSection_Name(), FieldsNameConstant.Signature, "", signatureListAct);
                            }
                            CustomRecyclerView customRecyclerView = findViewById(R.id.rv_signature);
                            if (customRecyclerView != null) {
                                UserImageListAdaptor userImageListAdaptor = new UserImageListAdaptor(ProcessCreationActivity.this, signatureListAct, SetClick.onClickSignature, SetClick.onClickLongSignature, directoryImage);
                                customRecyclerView.setAdapter(userImageListAdaptor);
                            } else {
                                //CustomLinearLayout cLinearLayout = findViewById(SECTION_COUNT + 1);
                                CreateDynamicView.userSignatureList(ProcessCreationActivity.this, appSession.getUserID(), llProcessLayout, signatureListAct, SetClick.onClickSignature, SetClick.onClickLongSignature);
                                llAdd.removeAllViews();
                                if (isNextQuestionOfSection) {
                                    isNextQuestion();
                                } else {
                                    if (isTabProcessEnabled) {
                                        isNextSectionTabProcess();
                                    } else {
                                        isNextSection();
                                    }
                                }
                            }
                        }
                        scrollDown();
                    } catch (Exception e) {
                        ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "signatureResult");
                    }
                    break;

                case GenericConstant.SKETCH_REQUEST:
                    try {
                        Log.e("SKETCH ", "RESULT >>>>>");
                        sketchListAct = new ArrayList<>();
                        sketchListAct = (ArrayList<PhotoListModel>) data.getSerializableExtra(GenericConstant.SKETCH_LIST);

                        if (sketchListAct != null && sketchListAct.size() > 0) {
                            if (isTabProcessEnabled) {
                                ServerRequest.setSubJsonServerRequest(this, FieldsNameConstant.Sketch, "", sketchListAct);
                            } else {
                                ServerRequest.setServerRequest(this, mPageSectionListBean.getPageSection_Name(), FieldsNameConstant.Sketch, "", sketchListAct);
                            }
                            CustomRecyclerView customRecyclerView = findViewById(R.id.rv_sketch);
                            if (customRecyclerView != null) {
                                UserImageListAdaptor userImageListAdaptor = new UserImageListAdaptor(ProcessCreationActivity.this, sketchListAct, SetClick.onClickSketch, SetClick.onClickLongSketch, directoryImage);
                                customRecyclerView.setAdapter(userImageListAdaptor);
                            } else {
                                //CustomLinearLayout cLinearLayout = findViewById(SECTION_COUNT + 1);
                                CreateDynamicView.userSketchList(ProcessCreationActivity.this, appSession.getUserID(), llProcessLayout, sketchListAct, SetClick.onClickSketch, SetClick.onClickLongSketch);
                                llAdd.removeAllViews();
                                if (isNextQuestionOfSection) {
                                    isNextQuestion();
                                } else {
                                    if (isTabProcessEnabled) {
                                        isNextSectionTabProcess();
                                    } else {
                                        isNextSection();
                                    }
                                }
                            }
                        }
                        scrollDown();
                    } catch (Exception e) {
                        ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "signatureResult");
                    }
                    break;

                case GenericConstant.POCKETBOOK_REQUEST:
                    try {
                        pocketbook = Objects.requireNonNull(data).getStringExtra(GenericConstant.POCKETBOOK_EDIT);
                        entryType = Objects.requireNonNull(data).getStringExtra(GenericConstant.POCKETBOOK_STATUS);

                        displayText = pocketbook;

                        if (pocketbook != null && !pocketbook.equalsIgnoreCase("")) {
                            if (isTabProcessEnabled) {
                                ServerRequest.setSubJsonServerRequest(this, FieldsNameConstant.Pocketbook, pocketbook, null);
                                ServerRequest.setSubJsonServerRequest(this, FieldsNameConstant.EntryType, entryType, null);
                            } else {
                                ServerRequest.setServerRequest(this, mPageSectionListBean.getPageSection_Name(), FieldsNameConstant.Pocketbook, pocketbook, null);
                                ServerRequest.setServerRequest(this, mPageSectionListBean.getPageSection_Name(), FieldsNameConstant.EntryType, entryType, null);
                            }
                            CustomTextView customTextView = findViewById(R.id.tv_pocket);
                            if (customTextView != null) {
                                customTextView.setText(pocketbook);
                            } else {

                                CreateDynamicView.userPocketbookEntry(this, new AppSession(this).getUserID(), llProcessLayout, pocketbook, entryType, SetClick.onClickLongPocketbook);
                                llAdd.removeAllViews();
                                if (isNextQuestionOfSection) {
                                    isNextQuestion();
                                } else {
                                    if (isTabProcessEnabled) {
                                        isNextSectionTabProcess();
                                    } else {
                                        isNextSection();
                                    }
                                }
                            }
                        }
                        scrollDown();
                    } catch (Exception e) {
                        ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "signatureResult");
                    }
                    break;

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
                        PopulateFields.populateType(this, addressBean, GenericConstant.ADDRESS);
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
                        Log.e("PROCESS  ", " NAME >>>> " + processName);
                        PopulateFields.populateType(this, appSession.getSearchedOfficer().get(appSession.getSearchedOfficer().size()-1).getObjectList(), GenericConstant.OFFICER);
                    } catch (Exception e) {
                        ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "officerResult");
                    }

                    break;

                case GenericConstant.EDIT_REQUEST:
                    try {
                        Log.e("PROCESS  ", " NAME >>>> " + processName);
                        editId = data.getIntExtra(GenericConstant.EDIT_ID, 0);
                        String sectionName = data.getStringExtra(GenericConstant.SECTION_NAME);
                        captureValueEdit = data.getStringExtra(GenericConstant.ANSWER_VALUE);
                        buttonId = data.getIntExtra(GenericConstant.BUTTON_ID, 0);
                        questionEdit = data.getStringExtra(GenericConstant.QUESTION_EDIT);
                        isFinalAnswerEdit = data.getBooleanExtra(GenericConstant.FINAL_ANSWER, false);
                        skipQuestionEdit = data.getIntExtra(GenericConstant.SKIP_QUESTION, 0);

                        QuestionDependentId = data.getStringExtra(GenericConstant.QUESTION_DEPENDENT_ID);
                        QuestionDependent = data.getBooleanExtra(GenericConstant.QUESTION_DEPENDENT, false);
                        SectionDependent = data.getBooleanExtra(GenericConstant.SECTION_DEPENDENT, false);

                        idSectinHideList = new ArrayList<>();
                        idSectinHideList = (ArrayList<Integer>) data.getSerializableExtra(GenericConstant.ID_LIST_HIDE);
                        idShowSectionList = new ArrayList<>();
                        idShowSectionList = (ArrayList<Integer>) data.getSerializableExtra(GenericConstant.ID_LIST_SHOW);
                        ArrayList<Integer> idQuestionHideList = (ArrayList<Integer>) data.getSerializableExtra(GenericConstant.ID_QUESTION_HIDE);
                        ArrayList<Integer> idQuesShow = (ArrayList<Integer>) data.getSerializableExtra(GenericConstant.ID_QUESTION_SHOW);
                        ArrayList<Integer> idFieldHideList = (ArrayList<Integer>) data.getSerializableExtra(GenericConstant.ID_FIELD_HIDE);
                        ArrayList<Integer> idFieldShowList = (ArrayList<Integer>) data.getSerializableExtra(GenericConstant.ID_FIELD_SHOW);

                        if (idShowSubSectionList != null && idShowSubSectionList.size() > 0)
                            idShowSubSectionList.removeAll(idSectinHideList);

                        if (idHideSubSectionList != null && idHideSubSectionList.size() > 0)
                            idHideSubSectionList.removeAll(idShowSectionList);

                        if (idShowQuestionList != null && idShowQuestionList.size() > 0)
                            idShowQuestionList.removeAll(idQuestionHideList);

                        if (idHideQuestionList != null && idHideQuestionList.size() > 0)
                            idHideQuestionList.removeAll(idQuesShow);

                        if (idShowFieldList != null && idShowFieldList.size() > 0)
                            idShowFieldList.removeAll(idFieldHideList);

                        if (idHideFieldList != null && idHideFieldList.size() > 0)
                            idHideFieldList.removeAll(idFieldShowList);

                        idHideSubSectionList.addAll(idSectinHideList);
                        idHideSubSectionList = new ArrayList<Integer>(new LinkedHashSet<Integer>(idHideSubSectionList));

                        idShowSubSectionList.addAll(idShowSectionList);
                        idShowSubSectionList = new ArrayList<Integer>(new LinkedHashSet<Integer>(idShowSubSectionList));

                        for (int i = 0; i < idHideSubSectionList.size(); i++) {
                            Log.e("HIDE RESULT ", "ID >>>>> " + idHideSubSectionList.get(i));
                        }

                        idHideQuestionList.addAll(idQuestionHideList);
                        idHideQuestionList = new ArrayList<Integer>(new LinkedHashSet<Integer>(idHideQuestionList));

                        idShowQuestionList.addAll(idQuesShow);
                        idShowQuestionList = new ArrayList<Integer>(new LinkedHashSet<Integer>(idShowQuestionList));

                        idHideFieldList.addAll(idFieldHideList);
                        idHideFieldList = new ArrayList<Integer>(new LinkedHashSet<Integer>(idHideFieldList));

                        idShowFieldList.addAll(idFieldShowList);
                        idShowFieldList = new ArrayList<Integer>(new LinkedHashSet<Integer>(idShowFieldList));

                        RemoveValue.removeObjectValue(this, mPageSectionListBean.getPageSection_Name());
                        if (idHideFieldList != null && idHideFieldList.size() > 0) {
                            RemoveValue.removeFieldValue(this);
                        }
                        if (idHideQuestionList != null && idHideQuestionList.size() > 0) {
                            RemoveValue.removeQuestionValue(this);
                        }
                        if (idHideSubSectionList != null && idHideSubSectionList.size() > 0) {
                            RemoveValue.removeSubSectionValue(this);
                        }

                        Gson gson = new Gson();
                        object = gson.fromJson(data.getStringExtra(GenericConstant.OBJECT_REQUEST), Object.class);
                        //object = (Object) data.getSerializableExtra(GenericConstant.OBJECT_REQUEST);
                        searchId = data.getIntExtra(GenericConstant.SEARCH_ID, 0);
                        searchName = data.getStringExtra(GenericConstant.SEARCH_NAME);

                        if (object != null) {
                            PopulateFields.addValueToObject(this, object);
                        }

                        ArrayList<AnswerValueDTO> tempList = new ArrayList<>();
                        tempList = (ArrayList<AnswerValueDTO>) data.getSerializableExtra(GenericConstant.ANSWER_LIST);

                        answerList = new ArrayList<>();
                        answerList.addAll(tempList);

                        if (captureValueEdit != null && !captureValueEdit.equalsIgnoreCase("")) {
                            if (isTabProcessEnabled) {
                                ServerRequest.setSubJsonServerRequest(this, questionEdit, captureValueEdit, null);
                            } else {
                                ServerRequest.setServerRequest(this, sectionName, questionEdit, captureValueEdit, null);
                            }
                        }

                        if (answerList != null && answerList.size() > 0) {
                            for (int i = 0; i < answerList.size(); i++) {
                                if (isTabProcessEnabled) {
                                    ServerRequest.setSubJsonServerRequest(this, answerList.get(i).getKey(), answerList.get(i).getValue(), null);
                                } else {
                                    ServerRequest.setServerRequest(this, sectionName, answerList.get(i).getKey(), answerList.get(i).getValue(), null);
                                }
                            }
                        }

                        if (!QuestionDependent) {
                            if (tabFlowPosList != null && tabFlowPosList.size() > 0) {
                                if (tabFlowPosList.get(tabFlowPosList.size() - 1).getPosition() != 0) {
                                    btnSave.setVisibility(View.VISIBLE);
                                    cvEditText.setVisibility(View.GONE);
                                } else {
                                    btnSave.setVisibility(View.GONE);
                                    cvEditText.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                        EditProcess.processEdit(this);
                    } catch (Exception e) {
                        ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "ActivityResultEdit");
                    }
                    break;
            }
        } else {
            Log.e("CALLED ", " >>>> back " + QUESTION_COUNT);
            isClicked = true;
            isClickedSpeak = true;
            if (mExpectedAnswerListBean != null) {
                QUESTION_COUNT = QUESTION_COUNT - mExpectedAnswerListBean.getSkipQuestion();
            }
            if (mExpectedQuestionListBean != null) {
                QUESTION_COUNT = QUESTION_COUNT - mExpectedQuestionListBean.getSkipQuestion();
            }
            if (QUESTION_COUNT > 0) {
                QUESTION_COUNT--;
                isNextQuestion();
                scrollDown();
            } else if (QUESTION_COUNT == 0 && SECTION_COUNT >= 0) {
                if (!isTabProcessEnabled) {
                    SectionTabID = 0;
                    currentQuestion = "";
                    SECTION_COUNT--;
                    isNextSection();
                }
            }
            if (Utilities.isMyServiceRunning(mContext, MyLocationServices.class)) {
                Intent i = new Intent(mContext, MyLocationServices.class);
                i.setAction(MyLocationServices.ACTION_STOP_FOREGROUND_SERVICE);
                ContextCompat.startForegroundService(mContext, i);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (isTabFlowEnabled) {
            createTabBack();
        } else if (isQuestionFlowEnabled) {
            isQuestionFlowEnabled = false;
            OpenSection.openSection(this, SectionTabID);
        } else {
            if (!doubleBackToExitPressedOnce) {
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            }
            if (doubleBackToExitPressedOnce) {
                //super.onBackPressed();
                DialogUtil.saveDraftDialog(this, getResources().getString(R.string.save_draft));
            }
            doubleBackToExitPressedOnce = true;

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    /**
     * Call the method createTabBack
     */
    public void createTabBack() {

        if (isTabProcessAddPage) {
            DialogUtil.looseDataDialog(this, getResources().getString(R.string.loose_data));
        } else if (isTabProcessListPage) {
            isTabProcessListPage = false;
            if (tabFlowPosList != null && tabFlowPosList.size() > 2) {
                tabFlowPosList.remove(tabFlowPosList.size() - 1);
                TabClick.clickBack();
            } else if (tabFlowPosList != null && tabFlowPosList.size() == 2) {
                tabFlowPosList.remove(tabFlowPosList.size() - 1);
                aSubSectionListBean = appSession.getSubSectionList();
                PrepareSubSection.prepareScreen(this);
            }
        } else if (tabFlowPosList != null && tabFlowPosList.size() > 2) {
            tabFlowPosList.remove(tabFlowPosList.size() - 1);
            TabClick.clickBack();
        } else if (tabFlowPosList != null && tabFlowPosList.size() == 2) {
            if (isTabProcessEnabled) {
                aSubSectionListBean = appSession.getSubSectionList();
                PrepareSubSection.prepareScreen(this);
            }
        } else {
            isTabFlowEnabled = false;
            isTabProcessEnabled = false;
        }
    }
}