package hcl.policing.digitalpolicingplatform.activities.audio;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.micSearch.MicSearchActivity;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.adapters.audio.AudioListAdaptor;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.listeners.OnEditTextChangedListener;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.models.camera.PhotoListModel;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.SnapHelperOneByOne;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class AudioRecordActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonStart, buttonStop, buttonPlayLastRecordAudio;
    private String AudioSavePathInDevice = null;
    private MediaRecorder mediaRecorder;
    public static final int RequestPermissionCode = 1;
    private boolean isPlaying = false, isPause = false;
    private MediaPlayer mediaPlayer;
    private Animation micAnimation;
    private AppSession appSession;
    private ImageView ivRings, ivMic;

    String fileName = null;
    private ArrayList<PhotoListModel> aPathList;
    private RecyclerView rvAudio;
    private AudioListAdaptor audioListAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_activity);

        initView();

        appSession = new AppSession(this);
        aPathList = new ArrayList<>();
        try {
            aPathList = (ArrayList<PhotoListModel>) getIntent().getSerializableExtra(GenericConstant.AUDIO_LIST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (aPathList != null && aPathList.size() > 0) {
            audioListAdaptor = new AudioListAdaptor(this, aPathList, onClickDelete, onClickPlayPause, new OnEditTextChangedListener() {
                @Override
                public void onTextChanged(int position, String charSequence) {
                    try {
                        if (charSequence.length() > 0) {
                            aPathList.get(position).setDescription(charSequence.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            rvAudio.setAdapter(audioListAdaptor);
        }
    }

    private void initView() {
        micAnimation = AnimationUtils.loadAnimation(this, R.anim.fadescale);
        ivRings = findViewById(R.id.iv_rings);
        ivMic = findViewById(R.id.iv_mic);

        buttonStart = findViewById(R.id.btn_record);
        buttonStop = findViewById(R.id.btn_stop);
        buttonPlayLastRecordAudio = findViewById(R.id.btn_play);

        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
        buttonPlayLastRecordAudio.setOnClickListener(this);
        buttonStop.setEnabled(false);
        buttonPlayLastRecordAudio.setEnabled(false);

        rvAudio = findViewById(R.id.rv_audio_list);
        setRecyclerViewProperty(this, rvAudio);
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(rvAudio);
        Button btnDone = findViewById(R.id.btn_done);
        btnDone.setOnClickListener(this);
    }

    /**
     * Set the recycler view property
     *
     * @param context
     * @param rvtList
     */
    private static void setRecyclerViewProperty(Context context, RecyclerView rvtList) {
        rvtList.setHasFixedSize(true);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(context);
        rvtList.setLayoutManager(layoutmanager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_done:

                Intent intentProcess = new Intent();
                intentProcess.putExtra(GenericConstant.AUDIO_LIST, aPathList);
                setResult(RESULT_OK, intentProcess);
                finish();
                overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
                break;

            case R.id.btn_record:
                if(checkPermission()) {

                    if(fileName != null && !fileName.equalsIgnoreCase("")) {
                        Utilities.getInstance(AudioRecordActivity.this).deleteAudioFile(fileName, ProcessCreationActivity.directoryAudio);
                    }
                    fileName = "Audio_"+ System.currentTimeMillis();
                    File file = Utilities.getInstance(AudioRecordActivity.this).writeAudioFile(fileName, ProcessCreationActivity.directoryAudio);
                    AudioSavePathInDevice = file.getAbsolutePath();

                    Log.e("FILE ", " >> PATH >> " + AudioSavePathInDevice);

                    MediaRecorderReady();

                    try {
                        startAnimation();
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (IllegalStateException | IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    buttonStart.setEnabled(false);
                    buttonStop.setEnabled(true);

                    Toast.makeText(AudioRecordActivity.this, "Recording started",
                            Toast.LENGTH_LONG).show();
                } else {
                    requestPermission();
                }
                break;

            case R.id.btn_stop:
                mediaRecorder.stop();
                buttonStop.setEnabled(false);
                buttonPlayLastRecordAudio.setEnabled(true);
                buttonStart.setEnabled(true);

                stopAnimation();
                Toast.makeText(AudioRecordActivity.this, "Recording Completed",
                        Toast.LENGTH_LONG).show();
                setAudioInRecycler(AudioSavePathInDevice);
                break;

            case R.id.btn_play:
                if(isPlaying) {
                    if(isPause) {
                        isPause = false;
                        mediaPlayer.start();
                        buttonPlayLastRecordAudio.setText(getResources().getString(R.string.pause));
                    } else {
                        isPause = true;
                        mediaPlayer.pause();
                        buttonPlayLastRecordAudio.setText(getResources().getString(R.string.play));
                    }
                } else {
                    buttonStop.setEnabled(false);
                    buttonStart.setEnabled(false);

                    mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(AudioSavePathInDevice);
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mediaPlayer.start();
                    isPlaying = true;
                    buttonPlayLastRecordAudio.setText(getResources().getString(R.string.pause));
                    Toast.makeText(AudioRecordActivity.this, "Recording Playing",
                            Toast.LENGTH_LONG).show();

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            // TODO Auto-generated method stub
                            //here you can stop your recording thread.
                            isPlaying = false;
                            buttonPlayLastRecordAudio.setText(getResources().getString(R.string.play));
                            buttonStop.setEnabled(false);
                            buttonStart.setEnabled(true);
                            buttonPlayLastRecordAudio.setEnabled(true);

                            if(mp != null){
                                mp.stop();
                                mp.release();
                                mediaPlayer = null;
                            }
                        }
                    });
                }
                break;
        }
    }

    /**
     * Call the method start listening
     */
    private void startAnimation() {
        try {
            ivMic.setAlpha(0.4f);
            ivRings.startAnimation(micAnimation);

        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), MicSearchActivity.class, "startListening");
            //Toast.makeText(this, "There was an error " + e.toString(), Toast.LENGTH_LONG).show()
        }
    }

    public void stopAnimation() {
        ivRings.clearAnimation();
        ivMic.setAlpha(1.0f);
    }

    public void MediaRecorderReady(){
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    /**
     * Set Docs in Recyclerview
     */
    private void setAudioInRecycler(String docPath) {
        aPathList = new ArrayList<>();
        if (docPath != null && !docPath.equalsIgnoreCase("")) {
            PhotoListModel photoListModel = new PhotoListModel();
            photoListModel.setPhoto(fileName);
            photoListModel.setPath(docPath);
            photoListModel.setStatus("0");
            photoListModel.setDescription("");
            aPathList.add(photoListModel);
        }
        audioListAdaptor = new AudioListAdaptor(this, aPathList, onClickDelete, onClickPlayPause,new OnEditTextChangedListener() {
            @Override
            public void onTextChanged(int position, String charSequence) {
                try {
                    if (charSequence.length() > 0) {
                        aPathList.get(position).setDescription(charSequence.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        rvAudio.setAdapter(audioListAdaptor);
    }

    /**
     * Delete item click listener
     */
    public OnItemClickListener.OnItemClickCallback onClickPlayPause = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                for (int i = 0; i < aPathList.size(); i++) {
                    if(position == i) {
                        if(aPathList.get(position).getStatus().equalsIgnoreCase("1") || isPlaying) {
                            //aPathList.get(position).setStatus("0");
                            if(isPause) {
                                isPause = false;
                                mediaPlayer.start();
                                aPathList.get(position).setStatus("1");
                                buttonPlayLastRecordAudio.setText(getResources().getString(R.string.pause));
                            } else {
                                isPause = true;
                                aPathList.get(position).setStatus("0");
                                mediaPlayer.pause();
                                buttonPlayLastRecordAudio.setText(getResources().getString(R.string.play));
                            }
                        } else {
                            aPathList.get(position).setStatus("1");
                            buttonStop.setEnabled(false);
                            buttonStart.setEnabled(false);

                            mediaPlayer = new MediaPlayer();
                            try {
                                mediaPlayer.setDataSource(aPathList.get(position).getPath());
                                mediaPlayer.prepare();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            mediaPlayer.start();
                            isPlaying = true;
                            buttonPlayLastRecordAudio.setText(getResources().getString(R.string.pause));
                            Toast.makeText(AudioRecordActivity.this, "Recording Playing",
                                    Toast.LENGTH_LONG).show();

                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    // TODO Auto-generated method stub
                                    //here you can stop your recording thread.
                                    isPlaying = false;
                                    aPathList.get(position).setStatus("0");
                                    buttonPlayLastRecordAudio.setText(getResources().getString(R.string.play));
                                    buttonStop.setEnabled(false);
                                    buttonStart.setEnabled(true);
                                    buttonPlayLastRecordAudio.setEnabled(true);

                                    if(mp != null){
                                        mp.stop();
                                        mp.release();
                                        mediaPlayer = null;
                                    }
                                    audioListAdaptor.notifyDataSetChanged();
                                }
                            });
                        }
                    } else {
                        aPathList.get(i).setStatus("0");
                    }
                }
                audioListAdaptor.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * Delete item click listener
     */
    public OnItemClickListener.OnItemClickCallback onClickDelete = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                Utilities.getInstance(AudioRecordActivity.this).deleteFileFromPath(aPathList.get(position).getPath());
                aPathList.remove(position);
                audioListAdaptor.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void requestPermission() {
        ActivityCompat.requestPermissions(AudioRecordActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(AudioRecordActivity.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(AudioRecordActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}