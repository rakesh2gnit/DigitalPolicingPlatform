package hcl.policing.digitalpolicingplatform.activities.micSearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.activities.micSearch.address.Address;
import hcl.policing.digitalpolicingplatform.activities.micSearch.incident.Incident;
import hcl.policing.digitalpolicingplatform.activities.micSearch.person.Person;
import hcl.policing.digitalpolicingplatform.activities.micSearch.vehicle.Vehicle;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.constants.api.ApiConstants;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.database.DatabaseHelper;
import hcl.policing.digitalpolicingplatform.database.ProcessSubProcessMapper;
import hcl.policing.digitalpolicingplatform.database.TriggerMapper;
import hcl.policing.digitalpolicingplatform.fragments.controlPannel.ControlPannelFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.event.IncidentReadDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.SubProcessDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.address.AddressReadDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.person.PersonReadDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.vehicle.VehicleReadDialogFragment;
import hcl.policing.digitalpolicingplatform.listeners.dialog.DialogCancelListener;
import hcl.policing.digitalpolicingplatform.listeners.dialog.ManuallyClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.event.EventSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.address.AddressSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.cases.CaseSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.communication.CommunicationSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.courtwarrant.CourtWarrantSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.investigation.InvestigationSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.PersonSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.vehicle.VehicleSelectionListener;
import hcl.policing.digitalpolicingplatform.models.login.BasedataModel;
import hcl.policing.digitalpolicingplatform.models.process.crime.EventSearchList;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.AddressBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.PersonBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.VehicleBean;
import hcl.policing.digitalpolicingplatform.models.process.officer.UserModel;
import hcl.policing.digitalpolicingplatform.models.search.ResponseSearchModel;
import hcl.policing.digitalpolicingplatform.models.search.SearchListBean;
import hcl.policing.digitalpolicingplatform.models.search.TriggerUploadRequest;
import hcl.policing.digitalpolicingplatform.networks.search.UploadTriggerApi;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;
import hcl.policing.digitalpolicingplatform.utils.Utilities;
import hcl.policing.digitalpolicingplatform.utils.speak.SpeakToPersonUtil;

public class MicSearchActivity extends BaseActivity implements ApiConstants, TextToSpeech.OnInitListener,
        View.OnClickListener, PersonSelectionListener, ManuallyClickListener, DialogCancelListener, VehicleSelectionListener, EventSelectionListener,
        AddressSelectionListener, CaseSelectionListener, InvestigationSelectionListener, CommunicationSelectionListener, CourtWarrantSelectionListener {

    public String TAG = "SpeechListener";
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    public TextToSpeech textToSpeech;
    private Animation micAnimation;
    private AppSession appSession;
    private ImageView ivRings, ivMic;
    private TextView tvSpeech;
    private DatabaseHelper db;
    private Context mContext;
    private UserModel userModel;
    public boolean isListen = false;
    private boolean isSearchVehicle = false;
    private boolean isSearchPerson = false;
    private boolean isSearchAddress = false;
    private boolean isSearchIncident = false;
    public boolean isName = false;
    public boolean isReason = false;
    private String searchedWord;

    public List<SearchListBean> aSearchListBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initDatabaseHelper();
        setContentView(R.layout.mic_search_activity);
        initView();
        try {
            textToSpeech = new TextToSpeech(this, this);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), MicSearchActivity.class, "textToSpeech");
        }
        appSession = new AppSession(this);
        ivMic.setAlpha(0.4f);
        loadSearchJSON();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isListen = true;
                SpeakToPersonUtil.speakToPerson(MicSearchActivity.this, textToSpeech, "Hello, how may I help you today");
            }
        }, 900);
    }

    /**
     * Initilize the view
     */
    private void initView() {
        micAnimation = AnimationUtils.loadAnimation(this, R.anim.fadescale);
        tvSpeech = findViewById(R.id.tv_speech);
        ivRings = findViewById(R.id.iv_rings);
        ivMic = findViewById(R.id.iv_mic);
        ivMic.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume ", "CALLED >>>>>");
        initializeSpeech();
    }

    /**
     * initiliase the database
     */
    private void initDatabaseHelper() {
        if (db == null) {
            db = new DatabaseHelper(this);
        }
        ArrayList<TriggerMapper> triggerList = db.getAllTriggerList();
        for (int i = 0; i < triggerList.size(); i++) {
            Log.e("TRIGGER ", " NAME >> " + triggerList.get(i).getTriggerName()
                    + " >> PROCESS NAME >> " + triggerList.get(i).getProcessName()
                    + " >> SUB PROCESS NAME >> " + triggerList.get(i).getSubProcessName());
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.setLanguage(Locale.UK);
            textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {

                }

                @Override
                public void onDone(String utteranceId) {
                    Log.e("UTTRANCE ", "CALLED >>>>>");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (isListen)
                                startListening();
                        }
                    });
                }

                @Override
                public void onError(String utteranceId) {

                }
            });
            //changeMicState(MicState.Speaking);
        }
    }

    /**
     * Initialize the speech
     */
    public void initializeSpeech() {
        try {
            mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
            mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false);
            mSpeechRecognizerIntent.putExtra("android.speech.extra.DICTATION_MODE", true);
            // mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true);
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());

            mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {
                    Log.e(TAG, "onReadyForSpeech");
                }

                @Override
                public void onBeginningOfSpeech() {
                    Log.e(TAG, "onBeginningOfSpeech");
                }

                @Override
                public void onRmsChanged(float rmsdB) {
                    Log.d(TAG, "onRmsChanged");
                }

                @Override
                public void onBufferReceived(byte[] buffer) {
                    Log.d(TAG, "onBufferReceived");
                }

                @Override
                public void onEndOfSpeech() {
                    //Toast.makeText(MainActivity.this,"onEndOfSpeech",Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onEndOfSpeech");
                    stopAnimation();
                }

                @Override
                public void onError(int error) {
                    Log.e(TAG, "onError" + error);
                    stopAnimation();
                }

                @Override
                public void onResults(Bundle results) {
                    try {
                        Log.e(TAG, "onResults");
                        stopAnimation();

                        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                        Log.e(TAG, matches.get(0).toString());

                        if (matches != null) {
                            searchedWord = null;
                            searchedWord = matches.get(0).toString();
                            //searchSpeechText(matches.get(0).toString());
                            searchSpeech(matches.get(0).toString());
                        }
                    } catch (Exception e) {
                        ExceptionLogger.Logger(e.getCause(), e.getMessage(), MicSearchActivity.class, "speechResult");
                    }
                }

                @Override
                public void onPartialResults(Bundle partialResults) {
                    Log.d(TAG, "onPartialResults");
                }

                @Override
                public void onEvent(int eventType, Bundle params) {
                    Log.d(TAG, "onEvent");
                }
            });
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), MicSearchActivity.class, "initializeSpeech");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_mic:
                startListening();
                break;
        }
    }

    /**
     * Call the method start listening
     */
    private void startListening() {
        try {
            ivMic.setAlpha(0.4f);
            ivRings.startAnimation(micAnimation);
            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
            tvSpeech.setText("");

        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), MicSearchActivity.class, "startListening");
            //Toast.makeText(this, "There was an error " + e.toString(), Toast.LENGTH_LONG).show()
        }
    }

    public void stopAnimation() {
        ivRings.clearAnimation();
        ivMic.setAlpha(1.0f);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        destroySpeech();
        Log.e("DESTROYED ", "DESTROYED ");
    }

    public void destroySpeech() {
        if (mSpeechRecognizer != null) {
            mSpeechRecognizer.cancel();
            mSpeechRecognizer.destroy();
        }
    }

    public void setFlagValues(boolean isPerson, boolean isVehicle, boolean isAddress, boolean isIncident) {
        isSearchAddress = isAddress;
        isSearchVehicle = isVehicle;
        isSearchPerson = isPerson;
        isSearchIncident = isIncident;
    }

    /**
     * Call the method search speech text
     *
     * @param searchText
     */
    private void searchSpeechText(String searchText) {

        appSession.setImageList(null);
        ProcessCreationActivity.SECTION_COUNT = 0;
        ProcessCreationActivity.QUESTION_COUNT = 0;
        Log.e("SEARCH ", "TEXT 1 >> " + searchText);
        Matcher mTor = torTypes.matcher(searchText);
        Matcher mStop = stopTypes.matcher(searchText);
        Matcher mFds = fdsTypes.matcher(searchText);
        Matcher mWitness = witnessTypes.matcher(searchText);
        Matcher mCrime = crimeTypes.matcher(searchText);
        Matcher mIncident = incidentType.matcher(searchText);
        Matcher mSearchPerson = searchPerson.matcher(searchText);
        Matcher mSearchVehicle = searchVehicle.matcher(searchText);
        Matcher mSearchAddress = searchAddress.matcher(searchText);
        Matcher mSearchIncident = searchIncident.matcher(searchText);

        if (mFds.find()) {

            loadSubProcessDialog("Search", "");

        } else if (mTor.find()) {

            setFlagValues(false, false, false, false);
            loadSubProcessDialog("TOR", "");

        } else if (mStop.find()) {

            setFlagValues(false, false, false, false);
            loadSubProcessDialog("Stops & Search", "");

        } else if (mCrime.find()) {

            setFlagValues(false, false, false, false);
            loadSubProcessDialog("Crime", "");

        } else if (mIncident.find()) {

            setFlagValues(false, false, false, false);
            loadSubProcessDialog("Incident", "");

        } else if (mWitness.find()) {

            setFlagValues(false, false, false, false);
            loadSubProcessDialog("Witness Statement", "");

        } else if (mSearchPerson.find()) {

            setFlagValues(true, false, false, false);
            Person person = new Person();
            person.personAction(MicSearchActivity.this, textToSpeech, searchText.toLowerCase());

        } else if (mSearchVehicle.find()) {

            setFlagValues(false, true, false, false);
            Vehicle vehicle = new Vehicle();
            vehicle.vehicleAction(MicSearchActivity.this, textToSpeech, searchText.toLowerCase());

        } else if (mSearchAddress.find()) {

            setFlagValues(false, false, true, false);
            Address address = new Address();
            address.addressAction(MicSearchActivity.this, textToSpeech, searchText.toLowerCase());

        } else if (mSearchIncident.find()) {

            setFlagValues(false, false, false, true);
            Incident incident = new Incident();
            incident.incidentAction(MicSearchActivity.this, textToSpeech, searchText.toLowerCase());

        } else if (isSearchPerson) {
            Log.e("PERSON ", "SEARCH >> ");
            Person person = new Person();
            person.personAction(MicSearchActivity.this, textToSpeech, searchText.toLowerCase());

        } else if (isSearchVehicle) {
            Log.e("Vehicle ", "SEARCH >> ");
            Vehicle vehicle = new Vehicle();
            vehicle.vehicleAction(MicSearchActivity.this, textToSpeech, searchText.toLowerCase());

        } else if (isSearchAddress) {
            Log.e("Address ", "SEARCH >> ");

        } else if (isSearchIncident) {
            Log.e("Incident ", "SEARCH >> ");
            Incident incident = new Incident();
            incident.incidentAction(MicSearchActivity.this, textToSpeech, searchText.toLowerCase());
        } else {
            textToSpeech.speak("Sorry, I am not able to understand. Please try again.", TextToSpeech.QUEUE_FLUSH, null, "");
        }
    }


    /**
     * Search Speech
     *
     * @param searchWord
     */
    private void searchSpeech(String searchWord) {
        TriggerMapper mTriggerMapper = null;
        appSession.setImageList(null);
        ProcessCreationActivity.SECTION_COUNT = 0;
        ProcessCreationActivity.QUESTION_COUNT = 0;
        String processName, subProcessName;
        try {
            if (!TextUtils.isEmpty(searchWord)) {
                mTriggerMapper = db.getTriggerDetails(searchWord);
                if (mTriggerMapper != null) {
                    processName = mTriggerMapper.getProcessName();
                    subProcessName = mTriggerMapper.getSubProcessName();
                    Log.e("PROCESS ", " NAME " + processName + " SUB >>>>> " + subProcessName);
                    launchProcess(searchWord, processName, subProcessName);
                } else {
                    textToSpeech.speak("Sorry, I am not able to understand. Please try again.", TextToSpeech.QUEUE_FLUSH, null, "");
                    // Call Trigger Upload Api
                    uploadTriggersValue();
                }
            } else {
                textToSpeech.speak("Sorry, I am not able to understand. Please try again.", TextToSpeech.QUEUE_FLUSH, null, "");
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ControlPannelFragment.class, "searchSpeech");
        }
    }


    /**
     * Launch Processes
     *
     * @param searchWord
     * @param processName
     * @param subProcessName
     */
    private void launchProcess(String searchWord, String processName, String subProcessName) {

        if (processName != null) {

            if (processName.equalsIgnoreCase(ApiConstants.PROCESS_TOR)) {

                setFlagValues(false, false, false, false);
                loadSubProcessDialog(processName, subProcessName);

            } else if (processName.equalsIgnoreCase(ApiConstants.PROCESS_STOPS)) {

                setFlagValues(false, false, false, false);
                loadSubProcessDialog(processName, subProcessName);

            } else if (processName.equalsIgnoreCase(ApiConstants.PROCESS_CRIME)) {

                setFlagValues(false, false, false, false);
                loadSubProcessDialog(processName, subProcessName);

            } else if (processName.equalsIgnoreCase(ApiConstants.PROCESS_INCIDENT)) {

                setFlagValues(false, false, false, false);
                loadSubProcessDialog(processName, subProcessName);

            } else if (processName.equalsIgnoreCase(ApiConstants.PROCESS_WITNESS)) {
                if (subProcessName != null && subProcessName.contains(ApiConstants.SUB_PROCESS_CREATE)) {
                    setFlagValues(false, false, false, false);
                    loadSubProcessDialog("Witness Statement", subProcessName);
                }
            } else if (processName.equalsIgnoreCase(ApiConstants.PROCESS_SEARCH)) {
                if (subProcessName != null && subProcessName.contains(ApiConstants.SUB_PROCESS_PERSON)) {
                    setFlagValues(true, false, false, false);
                    Person person = new Person();
                    person.personAction(MicSearchActivity.this, textToSpeech, searchWord.toLowerCase());

                }
                if (subProcessName != null && subProcessName.contains(ApiConstants.SUB_PROCESS_VEHICLE)) {
                    setFlagValues(false, true, false, false);
                    Vehicle vehicle = new Vehicle();
                    vehicle.vehicleAction(MicSearchActivity.this, textToSpeech, searchWord.toLowerCase());

                }
                if (subProcessName != null && subProcessName.contains(ApiConstants.SUB_PROCESS_ADDRESS)) {
                    setFlagValues(false, false, true, false);
                    Address address = new Address();
                    address.addressAction(MicSearchActivity.this, textToSpeech, searchWord.toLowerCase());

                }
                if (subProcessName != null && subProcessName.contains(ApiConstants.SUB_PROCESS_INCIDENT)) {
                    setFlagValues(false, false, false, true);
                    Incident incident = new Incident();
                    incident.incidentAction(MicSearchActivity.this, textToSpeech, searchWord.toLowerCase());

                }
            }
        } else {
            textToSpeech.speak("Sorry, I am not able to understand. Please try again.", TextToSpeech.QUEUE_FLUSH, null, "");
        }
    }

    /**
     * Call the api for upload trigger values
     */
    private void uploadTriggersValue() {
        if (Utilities.getInstance(this).isNetworkAvailable()) {
            TriggerUploadRequest triggerRequestModel = getTriggerRequest();
            new UploadTriggerApi().uploadTriggers(MicSearchActivity.this, triggerRequestModel);
        } else {
            BasicMethodsUtil.getInstance().showToast(mContext, getString(R.string.no_network));
        }
    }

    /**
     * Create Trigger request
     *
     * @return
     */
    private TriggerUploadRequest getTriggerRequest() {
        TriggerUploadRequest triggerUploadRequest = new TriggerUploadRequest();
        userModel = appSession.getUserData();
        if (userModel != null) {
            BasedataModel mBasedataBean = new BasedataModel();
            mBasedataBean.setCustomercode(userModel.getCustomercode());
            mBasedataBean.setProcess(ApiConstants.PROCESS_TRIGGER);
            mBasedataBean.setSubprocessname(ApiConstants.SUB_PROCESS_CREATE);
            mBasedataBean.setFunction(ApiConstants.METHOD_UPLOAD_TRIGGER);
            mBasedataBean.setCollarid(appSession.getUserID());
            mBasedataBean.setSessionid(userModel.getToken());
            triggerUploadRequest.setBasedata(mBasedataBean);

            TriggerUploadRequest.UploadTriggersBean mUploadTriggersBean = new TriggerUploadRequest.UploadTriggersBean();
            mUploadTriggersBean.setCustomerid(userModel.getCustomerid());
            mUploadTriggersBean.setTriggername(searchedWord);
            ArrayList<TriggerUploadRequest.UploadTriggersBean> aUploadTriggersBean = new ArrayList<>();
            aUploadTriggersBean.add(mUploadTriggersBean);
            triggerUploadRequest.setUploadtriggers(aUploadTriggersBean);
        }
        return triggerUploadRequest;
    }

    /**
     * Load Sub Process Dialog
     */
    private void loadSubProcessDialog(String processName, String subProcessName) {

        new Thread(() -> {
            try {
                int subProcessId = 0;
                int processId = db.getProcessId(processName);
                ArrayList<ProcessSubProcessMapper> processSubProcessMappers = db.getSubProcessList(processName, processId);
                if(subProcessName != null && !subProcessName.equalsIgnoreCase("")) {
                    for (int i = 0; i < processSubProcessMappers.size(); i++) {
                        if (processSubProcessMappers.get(i).getSubProcessName().equalsIgnoreCase(subProcessName)) {
                            subProcessId = processSubProcessMappers.get(i).getSubProcessId();
                            break;
                        }
                    }
                }
                int finalSubProcessId = subProcessId;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (processSubProcessMappers != null) {
                            if (processSubProcessMappers.size() > 1) {

                                if(subProcessName != null && !subProcessName.equalsIgnoreCase("")) {
                                    String flowStr = isProcessFlow(finalSubProcessId);
                                    if (!TextUtils.isEmpty(flowStr)) {
                                        loadFlow(processName, flowStr);
                                    } else {
                                        getProcessFlowApi(processId, finalSubProcessId);
                                    }
                                } else {
                                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                    Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.SUB_PROCESS_LIST_DIALOG);
                                    if (prev != null) {
                                        ft.remove(prev);
                                    }
                                    ft.addToBackStack(null);
                                    // Create and show the dialog.
                                    SubProcessDialogFragment subProcessDialogFragment = SubProcessDialogFragment.newInstance(processSubProcessMappers);
                                    subProcessDialogFragment.show(ft, GenericConstant.SUB_PROCESS_LIST_DIALOG);
                                }
                            } else if (processSubProcessMappers.size() == 1) {
                                int subProcessId = processSubProcessMappers.get(0).getSubProcessId();
                                String flowStr = isProcessFlow(subProcessId);
                                if (!TextUtils.isEmpty(flowStr)) {
                                    loadFlow(processName, flowStr);
                                } else {
                                    getProcessFlowApi(processId, subProcessId);
                                }
                            }
                        }
                    }
                });
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), ControlPannelFragment.class, "loadSubProcessDialog");
            }
        }).start();
    }


    /**
     * Load Sub Process Dialog
     */
/*    public void loadSubProcessDialog(String processName) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int processId = db.getProcessId(processName);
                    ArrayList<ProcessSubProcessMapper> processSubProcessMappers = db.getSubProcessList(processName,processId);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (processSubProcessMappers != null) {
                                if (processSubProcessMappers.size() > 1) {

                                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                    Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.SUB_PROCESS_LIST_DIALOG);
                                    if (prev != null) {
                                        ft.remove(prev);
                                    }
                                    ft.addToBackStack(null);
                                    // Create and show the dialog.
                                    SubProcessDialogFragment subProcessDialogFragment = SubProcessDialogFragment.newInstance(processSubProcessMappers);
                                    subProcessDialogFragment.show(ft, GenericConstant.SUB_PROCESS_LIST_DIALOG);

                                } else if (processSubProcessMappers.size() == 1) {

                                    if (processName.equalsIgnoreCase("TOR")) {

                                        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);
                                        Intent intent = new Intent(MicSearchActivity.this, ProcessCreationActivity.class);
                                        intent.putExtra(GenericConstant.FILE_NAME_DRAFT, "");
                                        intent.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.TOR_PROCESS_JSON);
                                        intent.putExtra(GenericConstant.PROCESS_NAME, getResources().getString(R.string.tor_creation));
                                        startActivity(intent);
                                        finish();
                                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                                    } else if (processName.equalsIgnoreCase("Stops & Search")) {

                                        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.STOPNSEARCH_PROCESS);
                                        Intent intent1 = new Intent(MicSearchActivity.this, ProcessCreationActivity.class);
                                        intent1.putExtra(GenericConstant.FILE_NAME_DRAFT, "");
                                        intent1.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.STOPNSEARCH_JSON);
                                        intent1.putExtra(GenericConstant.PROCESS_NAME, getResources().getString(R.string.stop_process));
                                        startActivity(intent1);
                                        finish();
                                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                                    } else if (processName.equalsIgnoreCase("Crime")) {

                                        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.CRIME_PROCESS);
                                        Intent intent1 = new Intent(MicSearchActivity.this, ProcessCreationActivity.class);
                                        intent1.putExtra(GenericConstant.FILE_NAME_DRAFT, "");
                                        intent1.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.CRIME_PROCESS_JSON);
                                        intent1.putExtra(GenericConstant.PROCESS_NAME, getResources().getString(R.string.crime));
                                        startActivity(intent1);
                                        finish();
                                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                                    } else if (processName.equalsIgnoreCase("Witness Statement")) {

                                        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.WITNESS_PROCESS);
                                        Intent intent = new Intent(MicSearchActivity.this, ProcessCreationActivity.class);
                                        intent.putExtra(GenericConstant.FILE_NAME_DRAFT, "");
                                        intent.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.WITNESS_STATEMENT_JSON);
                                        intent.putExtra(GenericConstant.PROCESS_NAME, getResources().getString(R.string.witness_statement));
                                        startActivity(intent);
                                        finish();
                                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                                    }
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), ControlPannelFragment.class, "loadSubProcessDialog");
                }
            }
        }).start();
    }*/

    /**
     * Load Person Dialog
     */
    public void loadPersonDialog() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.PERSON_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        PersonReadDialogFragment personReadDialogFragment = PersonReadDialogFragment.newInstance("", "", false);
        personReadDialogFragment.show(ft, GenericConstant.PERSON_LIST_DIALOG);
    }

    /**
     * Load Vehicle Dialog
     */
    public void loadVehicleDialog() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.VEHICLE_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        VehicleReadDialogFragment vehicleReadDialogFragment = VehicleReadDialogFragment.newInstance("", "", false);
        vehicleReadDialogFragment.show(ft, GenericConstant.VEHICLE_LIST_DIALOG);
    }

    /**
     * Load Address Dialog
     */
    public void loadAddressDialog() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.ADDRESS_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        AddressReadDialogFragment addressReadDialogFragment = AddressReadDialogFragment.newInstance("", "", false);
        addressReadDialogFragment.show(ft, GenericConstant.ADDRESS_LIST_DIALOG);
    }

    /**
     * Load Address Dialog
     */
    public void loadIncidentDialog() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(GenericConstant.EVENT_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        IncidentReadDialogFragment incidentReadDialogFragment = IncidentReadDialogFragment.newInstance("", "", false);
        incidentReadDialogFragment.show(ft, GenericConstant.EVENT_LIST_DIALOG);
    }

    @Override
    public void dialogCancelled() {

    }

    @Override
    public void onManuallyClicker(int type) {

    }

    @Override
    public void onAddressSelected(AddressBean addressBean) {

    }

    @Override
    public void onCaseSelected(AddressBean addressBean) {

    }

    @Override
    public void onCommunicationSelected(AddressBean addressBean) {

    }

    @Override
    public void onCourtWarrantSelected(AddressBean addressBean) {

    }

    @Override
    public void onInvestigationSelected(AddressBean addressBean) {

    }

    @Override
    public void onPersonSelected(PersonBean personBean) {

    }

    @Override
    public void onVehicleSelected(VehicleBean vehicleBean) {

    }

    @Override
    public void onEventSelected(EventSearchList eventSearchList) {

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
                    }
                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), MicSearchActivity.class, "loadSearchJSON");
            }
        }).start();
    }
}
