package hcl.policing.digitalpolicingplatform.activities.tasking;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.adapters.tasking.ViewPagerAdapter;
import hcl.policing.digitalpolicingplatform.customLibraries.MovableFloatingActionButton;
import hcl.policing.digitalpolicingplatform.models.tasking.TaskDetailResponseDTO;
import hcl.policing.digitalpolicingplatform.networks.tasking.CompleteTask;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.TimeConverterUtil;
import hcl.policing.digitalpolicingplatform.utils.speak.SpeakToPersonUtil;

public class TaskDetailActivity extends BaseActivity implements TextToSpeech.OnInitListener, View.OnClickListener {

    public String TAG = "SpeechListener";
    private AppSession appSession;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    public TextToSpeech textToSpeech;
    private SpeakToPersonUtil speakText;
    public SpeechRecognizer mSpeechRecognizer;
    public Intent mSpeechRecognizerIntent;
    private TaskDetailResponseDTO taskDetailResponse;
    private boolean isAdditional = false;
    private boolean isLog = false;
    private MovableFloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskingdetails_activity);
        initView();
        try {
            textToSpeech = new TextToSpeech(this, this);
            speakText = new SpeakToPersonUtil();
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), TaskDetailActivity.class, "textToSpeech");
        }
        appSession = new AppSession(this);
        initializeSpeech();
        taskDetailResponse = new TaskDetailResponseDTO();
        if (appSession.getTaskDetail() != null) {
            taskDetailResponse = appSession.getTaskDetail();
        }
        //setDataListener = (SetDataListener) FragmentDetail;
    }

    /**
     * Initialize the view
     */
    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.getItem(0);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        TextView tvCompelte = findViewById(R.id.tv_complete);
        tvCompelte.setOnClickListener(this);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if (textToSpeech != null) {
                    if (textToSpeech.isSpeaking()) {
                        textToSpeech.stop();
                        fab.setImageResource(R.drawable.ic_play);
                    } else {
                        fab.setImageResource(R.drawable.ic_stop_blue);
                        speakTaskDetailResult(taskDetailResponse);
                    }
                }
                break;

            case R.id.tv_complete:
                try {
                    CompleteTask completeTask = new CompleteTask();
                    completeTask.completeTask(TaskDetailActivity.this, "" + taskDetailResponse.getTask().getTaskID(), appSession.getUserID(), "completeClicked");
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), TaskDetailActivity.class, "completeClicked");
                }
                break;
        }
    }

    public void result() {
        finish();
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
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());

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
                    //changeMicState(MicState.Speaking);
                }

                @Override
                public void onError(int error) {
                    if (error == SpeechRecognizer.ERROR_NO_MATCH) {

                    }
                    Log.e(TAG, "onError" + error);
                    if (error == SpeechRecognizer.ERROR_RECOGNIZER_BUSY) {
                        //mSpeechRecognizer.cancel();
                        //changeMicState(MicState.Listening);
                    }
                }

                @Override
                public void onResults(Bundle results) {
                    try {
                        Log.e(TAG, "onResults");

                        //noMatchCounter = 2;
                        //changeMicState(MicState.Dead);

                        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                        Log.e(TAG, matches.get(0));

                        if (matches != null) {

                            if (matches.get(0).toLowerCase().contains("yes")) {
                                if (isAdditional) {
                                    speakTaskAdditionalResult(taskDetailResponse);
                                    isAdditional = false;
                                }
                                if (isLog) {
                                    speakTaskLogsResult(taskDetailResponse);
                                    isLog = false;
                                }
                            } else {

                            }
                        }
                    } catch (Exception e) {
                        ExceptionLogger.Logger(e.getCause(), e.getMessage(), TaskDetailActivity.class, "speechResult");
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
            //mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), TaskDetailActivity.class, "initializeSpeech");
        }
    }

    /**
     * Speak task details result
     *
     * @param model
     */
    private void speakTaskDetailResult(TaskDetailResponseDTO model) {
        try {
            isAdditional = true;
            if (model != null && !model.equals("")) {
                String address = model.getTask().getHouseNo() + ", " + model.getTask().getStreet() + ", " + model.getTask().getTown() + ", " +
                        model.getTask().getCounty() + ", " + model.getTask().getPostCode();

                final String textToSpeak = "This is a High priority task of " + model.getTask().getTaskTypeName() +
                        " with " + TimeConverterUtil.convertTime(model.getTask().getTimeLeftExpire()) +
                        ". The location is " + address + " and is 52.1 miles away from you. Intelligence information is " + model.getTask().getIntelligenceInformation() +
                        ". The aim of task is " + model.getTask().getAim() + ". You can scroll right to view more details. Do you wish to know more details? Say yes to continue.";

                //speak(textToSpeak);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        speak(textToSpeak);
                    }
                }, 1000);
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), TaskDetailActivity.class, "speakTaskDetailResult");
        }
    }

    /**
     * Speak task details result
     *
     * @param model
     */
    private void speakTaskAdditionalResult(TaskDetailResponseDTO model) {
        try {
            if (model != null && !model.equals("")) {
                List<TaskDetailResponseDTO.RelevantNominalDetail> relevantNominalDetails = new ArrayList<>();
                relevantNominalDetails.addAll(model.getRelevantNominalDetails());

                List<TaskDetailResponseDTO.RelevantVehicleDetail> relevantVehicleDetails = new ArrayList<>();
                relevantVehicleDetails.addAll(model.getRelevantVehicleDetails());

                StringBuilder personSize = new StringBuilder();
                if (relevantNominalDetails != null && relevantNominalDetails.size() > 0) {
                    personSize.append("");
                    if (relevantNominalDetails.size() > 1) {
                        personSize.append("There are " + relevantNominalDetails.size() + " assciated persons named");
                        for (int i = 0; i < relevantNominalDetails.size(); i++) {
                            personSize.append(relevantNominalDetails.get(i).getFirstName() + " " + relevantNominalDetails.get(i).getLastName());
                            String dob = relevantNominalDetails.get(i).getDOB();
                            dob = dob.substring(0, 10);
                            personSize.append(" Date of Birth " + dob + ", ");
                        }
                    } else {
                        personSize.append("There is 1 assciated person named ");
                        personSize.append(relevantNominalDetails.get(0).getFirstName() + " " + relevantNominalDetails.get(0).getLastName());
                        String dob = relevantNominalDetails.get(0).getDOB();
                        dob = dob.substring(0, 10);
                        personSize.append(" Date of Birth " + dob + ". ");
                    }
                }

                StringBuilder vehicleSize = new StringBuilder();
                if (relevantVehicleDetails != null && relevantVehicleDetails.size() > 0) {

                    vehicleSize.append("");
                    if (relevantVehicleDetails.size() > 1) {
                        vehicleSize.append("There are " + relevantVehicleDetails.size() + " assciated vehicles with ");
                        for (int i = 0; i < relevantVehicleDetails.size(); i++) {
                            vehicleSize.append("VRM " + relevantVehicleDetails.get(i).getVehicleVRM() + " ");
                            vehicleSize.append("make " + relevantVehicleDetails.get(i).getMake() + " ");
                            vehicleSize.append("model " + relevantVehicleDetails.get(i).getModel() + " ");
                            vehicleSize.append("color " + relevantVehicleDetails.get(i).getColour() + ", ");
                        }
                    } else {
                        vehicleSize.append("There is 1 assciated vehicle with ");
                        vehicleSize.append("VRM " + relevantVehicleDetails.get(0).getVehicleVRM() + " ");
                        vehicleSize.append("make " + relevantVehicleDetails.get(0).getMake() + " ");
                        vehicleSize.append("model " + relevantVehicleDetails.get(0).getModel() + " ");
                        vehicleSize.append("color " + relevantVehicleDetails.get(0).getColour() + ", ");
                    }
                }

                final String textToSpeak = personSize.toString() + " " + vehicleSize.toString() +
                        "To add or view log scroll right.";

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        speak(textToSpeak);
                        //isLog = true;
                    }
                }, 1000);
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), TaskDetailActivity.class, "speakTaskAdditionalResult");
        }
    }

    /**
     * Speak the task log result
     *
     * @param model
     */
    private void speakTaskLogsResult(TaskDetailResponseDTO model) {
        try {
            if (model != null && !model.equals("")) {


                final String textToSpeak = "No logs are associated with task currently. To add log, either enter manually or click on mic button to speak.";

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        speak(textToSpeak);
                    }
                }, 1000);
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), TaskDetailActivity.class, "speakTaskLogsResult");
        }
    }

    public void speak(final String text) {
        SpeakToPersonUtil.speakToPerson(TaskDetailActivity.this, textToSpeech, text);
    }

    public void speakCancel() {
        if (textToSpeech != null) {
            textToSpeech.stop();
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
                    //startMic();
                    //initializeSpeech();
                    fab.setImageResource(R.drawable.ic_play);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (isAdditional || isLog) {
                                mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                            }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        Log.e("DESTROYED ", "DESTROYED ");
    }
}
