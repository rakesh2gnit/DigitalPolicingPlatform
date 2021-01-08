package hcl.policing.digitalpolicingplatform.fragments.tasking;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.adapters.tasking.CommentListAdapter;
import hcl.policing.digitalpolicingplatform.models.tasking.CommentResponseDTO;
import hcl.policing.digitalpolicingplatform.networks.tasking.AddComment;
import hcl.policing.digitalpolicingplatform.networks.tasking.GetComment;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.customLibraries.MovableFloatingActionButton;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class FragmentLogs extends Fragment implements View.OnClickListener {

    public String TAG="SpeechListener";
    private AppSession appSession;
    private Context context;
    private TextView tvNoLogs;
    private RecyclerView rvLogs;
    private EditText etRemarks;
    public SpeechRecognizer mSpeechRecognizer;
    public Intent mSpeechRecognizerIntent;
    private String comment ="";
    private CommentListAdapter commentListAdapter;

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TaskDetailActivity mActivity = (TaskDetailActivity) getActivity();
        mActivity.setAboutDataListener(this);
    }*/

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_logs, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        appSession = new AppSession(Objects.requireNonNull(context));
        initView(view);
        initializeSpeech();
        context = getActivity();
        Log.e("Called", "Detail >>>>>>");
        try {
            GetComment getComment = new GetComment();
            getComment.getComment(FragmentLogs.this, ""+appSession.getTaskDetail().getTask().getTaskID());
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), FragmentLogs.class, "onViewCreated");
        }
    }

    private void initView(View view) {
        etRemarks = view.findViewById(R.id.et_remark);
        rvLogs = view.findViewById(R.id.rv_logs);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        rvLogs.setLayoutManager(mLayoutManager);
        rvLogs.setItemAnimator(new DefaultItemAnimator());
        tvNoLogs = view.findViewById(R.id.tv_nologs);
        MovableFloatingActionButton ivMic = view.findViewById(R.id.iv_mic);
        ivMic.setOnClickListener(this);

        AnimatedVectorDrawable micAnimation = (AnimatedVectorDrawable) ivMic.getDrawable();
        micAnimation.registerAnimationCallback(new Animatable2.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawableMic) {
                super.onAnimationEnd(drawableMic);
                micAnimation.start();
            }
        });
        micAnimation.start();

        TextView textView = view.findViewById(R.id.tv_submit);
        textView.setOnClickListener(this);
    }

    public void initializeSpeech() {
        try {
            mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
            mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false);
            mSpeechRecognizerIntent.putExtra("android.speech.extra.DICTATION_MODE", true);
            // mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true);
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.getPackageName());

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

                        //noMatchCounter = 2;
                        //changeMicState(MicState.Dead);

                        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    /*for (int i = 0; i < matches.size(); i++) {

                    }*/

                        Log.e(TAG, matches.get(0));

                        if (matches != null) {

                            etRemarks.setText(etRemarks.getText().toString().trim()+" "+ matches.get(0));
                        }
                    } catch (Exception e) {
                        ExceptionLogger.Logger(e.getCause(), e.getMessage(), FragmentLogs.class, "onResults");
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
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), FragmentLogs.class, "initializeSpeech");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_mic:
                mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                break;

            case R.id.tv_submit:
                comment = etRemarks.getText().toString().trim();
                AddComment addComment = new AddComment();
                addComment.addComment(FragmentLogs.this, ""+appSession.getTaskDetail().getTask().getTaskID(), appSession.getUserID(), comment, "0");
                break;
        }
    }

    public void result() {
        etRemarks.setText("");
        GetComment getComment = new GetComment();
        getComment.getComment(FragmentLogs.this, ""+appSession.getTaskDetail().getTask().getTaskID());
    }

    public void resultList(ArrayList<CommentResponseDTO> commentResponseDTO) {
        if (commentResponseDTO != null && commentResponseDTO.size()>0) {
            tvNoLogs.setVisibility(View.GONE);
            rvLogs.setVisibility(View.VISIBLE);
            commentListAdapter = new CommentListAdapter(context, commentResponseDTO);
            rvLogs.setAdapter(commentListAdapter);
        } else {
            tvNoLogs.setVisibility(View.VISIBLE);
            rvLogs.setVisibility(View.GONE);
        }
    }
}
