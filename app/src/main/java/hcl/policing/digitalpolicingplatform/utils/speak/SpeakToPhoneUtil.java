package hcl.policing.digitalpolicingplatform.utils.speak;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;

public class SpeakToPhoneUtil {

    private int noMatchCounter = 2;
    private TypeSpeechResult typeSpeechResult;

    public void speak(Context context, SpeechRecognizer mSpeechRecognizer, Intent mSpeechRecognizerIntent, final TypeSpeechResult typeSpeechResult) {
        this.typeSpeechResult = typeSpeechResult;
        try {

            mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {
                    Log.e("TAG", "onReadyForSpeech");
                }

                @Override
                public void onBeginningOfSpeech() {
                    Log.e("TAG", "onBeginningOfSpeech");
                }

                @Override
                public void onRmsChanged(float rmsdB) {
                    Log.e("TAG", "onRmsChanged");
                }

                @Override
                public void onBufferReceived(byte[] buffer) {
                    Log.e("TAG", "onBufferReceived");
                }

                @Override
                public void onEndOfSpeech() {
                    Log.e("TAG", "onEndOfSpeech");
                    //changeMicState(MicState.Speaking);
                    ProcessCreationActivity.ivRings.clearAnimation();
                    ProcessCreationActivity.ivRings.setVisibility(View.GONE);
                    ProcessCreationActivity.ivSend.setAlpha(1.0f);
                }

                @Override
                public void onError(int error) {
                    ProcessCreationActivity.ivRings.clearAnimation();
                    ProcessCreationActivity.ivRings.setVisibility(View.GONE);
                    ProcessCreationActivity.ivSend.setAlpha(1.0f);
                    if (error == SpeechRecognizer.ERROR_NO_MATCH) {

                        if (noMatchCounter > 0) {
                            //changeMicState(MicState.Listening);
                            noMatchCounter--;
                        } else if (noMatchCounter == 0) {
                            //changeMicState(MicState.Dead);
                            noMatchCounter = 2;
                        }
                    }
                    Log.e("TAG", "onError" + error);
                    if (error == SpeechRecognizer.ERROR_RECOGNIZER_BUSY) {
                        //mSpeechRecognizer.cancel();
                        //changeMicState(MicState.Listening);
                    }
                }

                @Override
                public void onResults(Bundle results) {
                    Log.e("TAG", "onResults");
                    ProcessCreationActivity.ivRings.clearAnimation();
                    ProcessCreationActivity.ivRings.setVisibility(View.GONE);
                    ProcessCreationActivity.ivSend.setAlpha(1.0f);
                    noMatchCounter = 2;
                    //changeMicState(MicState.Dead);


                    ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    for (int i = 0; i < matches.size(); i++) {
                        Log.e("TAG", matches.get(i));
                    }

                    if (matches != null) {
                        String speakText = matches.get(0).toLowerCase();
                        typeSpeechResult.speechResult(speakText);
                    }
                }

                @Override
                public void onPartialResults(Bundle partialResults) {
                    Log.e("TAG", "onPartialResults");
                }

                @Override
                public void onEvent(int eventType, Bundle params) {
                    Log.e("TAG", "onEvent");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface TypeSpeechResult {
        public void speechResult(String content);
    }
}


