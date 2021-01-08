package hcl.policing.digitalpolicingplatform.activities.notification;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Locale;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.activities.controlPannel.ControlPanelActivity;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.MovableFloatingActionButton;
import hcl.policing.digitalpolicingplatform.models.tasking.TaskDetailResponseDTO;
import hcl.policing.digitalpolicingplatform.networks.notification.AcceptDeclineNotification;
import hcl.policing.digitalpolicingplatform.networks.tasking.TaskDetail;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.GPSTracker;
import hcl.policing.digitalpolicingplatform.utils.TimeConverterUtil;
import hcl.policing.digitalpolicingplatform.utils.speak.SpeakToPersonUtil;

public class NotificationDetailActivity extends BaseActivity implements TextToSpeech.OnInitListener, View.OnClickListener, OnMapReadyCallback {

    public String TAG = "SpeechListener";
    private AppSession appSession;
    private Marker destMarker;
    private GoogleMap googleMap;
    private double toLat, toLng;
    public TextToSpeech textToSpeech;
    private SpeakToPersonUtil speakText;
    public SpeechRecognizer mSpeechRecognizer;
    public Intent mSpeechRecognizerIntent;
    private TaskDetailResponseDTO taskDetailResponse;
    private boolean isAdditional = false;
    private boolean isLog = false;
    private String taskId = "";
    private TextView tvTaskType, tvExpiryDate, tvDescription, tvIntelligence, tvMethod, tvAim, tvOfficer, tvLocation;
    private MovableFloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_detail_activity);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initView();
        appSession = new AppSession(this);
        try {
            taskId = getIntent().getStringExtra(GenericConstant.TAST_ID);
            Log.e("TASK NOTI ", "ID >>>> " + taskId);
            if (taskId != null && !taskId.equalsIgnoreCase("")) {

                TaskDetail taskDetail = new TaskDetail();
                taskDetail.getTaskDetail(NotificationDetailActivity.this, taskId);
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), NotificationDetailActivity.class, "TaskDetailCalling");
        }
        try {
            textToSpeech = new TextToSpeech(this, this);
            speakText = new SpeakToPersonUtil();
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), NotificationDetailActivity.class, "TextToSpeech");
        }
        initializeSpeech();
    }

    /**
     * Initialize the view
     */
    private void initView() {
        tvTaskType = findViewById(R.id.tv_task_type);
        tvExpiryDate = findViewById(R.id.tv_expiry_date);
        tvDescription = findViewById(R.id.tv_task_description);
        tvIntelligence = findViewById(R.id.tv_intelligence);
        tvMethod = findViewById(R.id.tv_method);
        tvAim = findViewById(R.id.tv_aim);
        tvOfficer = findViewById(R.id.tv_safety);
        tvLocation = findViewById(R.id.tv_location);

        Button navButton = findViewById(R.id.btn_navigation);
        navButton.setOnClickListener(this);
        TextView tvAccept = findViewById(R.id.tv_accept);
        TextView tvDecline = findViewById(R.id.tv_decline);

        ImageView ivGps = findViewById(R.id.iv_gps);
        ivGps.setOnClickListener(this);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        tvAccept.setOnClickListener(this);
        tvDecline.setOnClickListener(this);
    }

    /**
     * Initialize the Speech
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

                    /*if(noMatchCounter > 0){
                        changeMicState(MicState.Listening);
                        noMatchCounter--;
                    }
                    else if(noMatchCounter == 0){
                        changeMicState(MicState.Dead);
                        noMatchCounter = 2;
                    }*/
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
                        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                        Log.e(TAG, matches.get(0));
                        if (matches != null) {

                            if (matches.get(0).toLowerCase().contains("accept")) {
                                acceptDecline(GenericConstant.YES);
                            } else if (matches.get(0).toLowerCase().contains("decline")) {
                                acceptDecline(GenericConstant.NO);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), NotificationDetailActivity.class, "initializeSpeech");
        }
    }

    /**
     * Set the task details
     *
     * @param model
     */
    public void setTaskDetailResult(final TaskDetailResponseDTO model) {
        if (model != null) {
            taskDetailResponse = new TaskDetailResponseDTO();
            taskDetailResponse = model;
            appSession.setTaskDetail(null);
            appSession.setTaskDetail(model);

            try {
                tvTaskType.setText(model.getTask().getTaskTypeName());
                tvExpiryDate.setText(TimeConverterUtil.convertTime(model.getTask().getTimeLeftExpire()));
                tvDescription.setText(model.getTask().getTaskDescription());
                tvIntelligence.setText(model.getTask().getIntelligenceInformation());
                tvMethod.setText(model.getTask().getMethodUtilised());
                tvAim.setText(model.getTask().getAim());
                tvOfficer.setText(model.getTask().getOfficerSafety());

                final String address = model.getTask().getHouseNo() + ", " + model.getTask().getStreet() + ", " + model.getTask().getTown() + ", " +
                        model.getTask().getCounty() + ", " + model.getTask().getPostCode();
                tvLocation.setText(address);

                toLat = Double.parseDouble(model.getTask().getLatitude());
                toLng = Double.parseDouble(model.getTask().getLongitude());

                LatLng latlngDest = new LatLng(toLat, toLng);
                showMarkerDestination(latlngDest);
                animateCamera(latlngDest);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        speakTaskDetailResult(model);
                    }
                }, 1000);

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), NotificationDetailActivity.class, "setTaskDetail");
            }
        }
    }

    /**
     * Speak the task details result
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
                        ". The location is " + address + " and is 52.1 miles away from you." +
                        " Please take necessary action by saying or clicking Accept or Decline.";

                //speak(textToSpeak);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        speak(textToSpeak);
                    }
                }, 1000);
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), NotificationDetailActivity.class, "speakTaskDetailResult");
        }
    }

    /**
     * call the method for accept / decline the result
     *
     * @param status
     */
    public void acceptDeclineResult(String status) {
        String text = "";
        if (status.equalsIgnoreCase(GenericConstant.YES)) {
            text = getResources().getString(R.string.task_assigned);
        } else {
            text = getResources().getString(R.string.task_declined);
        }
        speak(text);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(NotificationDetailActivity.this, ControlPanelActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }

    /**
     * Call Speak method
     *
     * @param text
     */
    public void speak(final String text) {
        speakText.speakToPerson(NotificationDetailActivity.this, textToSpeech, text);
    }

    /**
     * Cancel Speak
     */
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
                            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
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
        mSpeechRecognizer.stopListening();
        mSpeechRecognizer.destroy();
        destMarker = null;
        googleMap = null;
        Log.e("DESTROYED ", "DESTROYED ");
    }

    /**
     * Naviagte GPS from lat long
     *
     * @param toLat
     * @param toLng
     */
    private void navigate(double toLat, double toLng) {
        double frmLat, frmLng;

        GPSTracker mGPS = new GPSTracker(this);
        if (mGPS.canGetLocation) {
            mGPS.getLocation();
            frmLat = mGPS.getLatitude();
            frmLng = mGPS.getLongitude();

            Intent navigation = new Intent(Intent.ACTION_VIEW, Uri
                    .parse("http://maps.google.com/maps?saddr="
                            + frmLat + ","
                            + frmLng + "&daddr="
                            + toLat + "," + toLng));
            startActivity(navigation);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_navigation:
                navigate(toLat, toLng);
                break;

            case R.id.tv_accept:
                try {
                    if (textToSpeech != null) {
                        if (textToSpeech.isSpeaking()) {
                            textToSpeech.stop();
                        }
                    }
                    acceptDecline(GenericConstant.YES);
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), NotificationDetailActivity.class, "acceptClicked");
                }
                break;

            case R.id.tv_decline:
                try {
                    if (textToSpeech != null) {
                        if (textToSpeech.isSpeaking()) {
                            textToSpeech.stop();
                        }
                    }
                    acceptDecline(GenericConstant.NO);
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), NotificationDetailActivity.class, "declineClicked");
                }
                break;

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

            case R.id.iv_gps:
                LatLng latlngDest = new LatLng(toLat, toLng);
                animateCamera(latlngDest);
                break;
        }
    }

    /**
     * Call the method accept decline
     *
     * @param status
     */
    private void acceptDecline(String status) {
        try {
            AcceptDeclineNotification acceptDeclineNotification = new AcceptDeclineNotification();
            acceptDeclineNotification.updateTask(NotificationDetailActivity.this, taskId, appSession.getUserID(), status);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), NotificationDetailActivity.class, "acceptDecline");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    /**
     * show the marker destination
     *
     * @param latLng
     */
    private void showMarkerDestination(@NonNull LatLng latLng) {
        if (destMarker == null)
            destMarker = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(latLng));
    }

    /**
     * Animate the camera view
     *
     * @param latLng
     */
    private void animateCamera(@NonNull LatLng latLng) {
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(getCameraPositionWithBearing(latLng)));
    }

    @NonNull
    private CameraPosition getCameraPositionWithBearing(LatLng latLng) {
        return new CameraPosition.Builder().target(latLng).zoom(15).build();
    }
}
