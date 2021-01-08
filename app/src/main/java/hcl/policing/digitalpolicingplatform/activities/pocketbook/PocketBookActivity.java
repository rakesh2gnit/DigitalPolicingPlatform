package hcl.policing.digitalpolicingplatform.activities.pocketbook;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.activities.micSearch.MicSearchActivity;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class PocketBookActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    /**
     * Initialize the views
     */
    private AppSession appSession;
    private Context context;

    private EditText etPocketBook;
    private Spinner spnStatus;
    private String status = "";
    private List<String> categories;

    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    public static boolean isListening = false;
    private Dialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pocketbook_activity);
        context = this;
        appSession = new AppSession(context);
        initView();

        try {
            initializeSpeech(PocketBookActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String pocket = getIntent().getStringExtra(GenericConstant.POCKETBOOK_EDIT);

        if (pocket != null && !pocket.equalsIgnoreCase("")) {
            etPocketBook.setText(pocket);
        }

        categories = new ArrayList<String>();
        categories.add("Private");
        categories.add("Public");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnStatus.setAdapter(dataAdapter);
    }

    /**
     * Initiate the views
     */
    private void initView() {
        etPocketBook = findViewById(R.id.et_pocketbook);
        spnStatus = findViewById(R.id.spn_status);
        spnStatus.setOnItemSelectedListener(this);
        ImageView ivMic = findViewById(R.id.iv_mic);
        ivMic.setOnClickListener(this);
        TextView tvBookOn = findViewById(R.id.tv_book_on);
        tvBookOn.setOnClickListener(this);
        TextView tvBookOff = findViewById(R.id.tv_book_off);
        tvBookOff.setOnClickListener(this);
        Button btnDone = findViewById(R.id.btn_done);
        btnDone.setOnClickListener(this);
    }

    /**
     * Onclick method of Click listener
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_mic:
                startListening();
                break;

            case R.id.tv_book_on:
                String texton = etPocketBook.getText().toString();
                if(texton != null && !texton.equalsIgnoreCase("")) {
                    etPocketBook.setText(texton + " I am booking on at " + getTime() );
                } else {
                    etPocketBook.setText("I am booking on at " + getTime() );
                }
                break;

            case R.id.tv_book_off:
                String text = etPocketBook.getText().toString();
                if(text != null && !text.equalsIgnoreCase("")) {
                    etPocketBook.setText(text + " I am booking off at " + getTime() );
                } else {
                    etPocketBook.setText("I am booking off at " + getTime() );
                }
                break;

            case R.id.btn_done:
                try {
                    if(etPocketBook.getText() != null && !etPocketBook.getText().toString().equalsIgnoreCase("")) {
                        Intent intentProcess = new Intent();
                        intentProcess.putExtra(GenericConstant.POCKETBOOK_EDIT, etPocketBook.getText().toString());
                        intentProcess.putExtra(GenericConstant.POCKETBOOK_STATUS, status);
                        setResult(RESULT_OK, intentProcess);
                        finish();
                        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        status = parent.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
    }

    /**
     * Get the date from file
     *
     * @return
     */
    private String getTime() {

        String date = "";

        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a", Locale.US);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        date = formatter.format(calendar.getTime());

        return date;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**
     * Initialize the speech engine.
     */
    private void initializeSpeech(Context context) throws Exception {
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
                    Log.e("TextInputLayout", "onReadyForSpeech");
                    //setDrawablesLRTB(context, CustomTextInputEditText.this, 0, 0, R.drawable.ic_mic_blue, 0);
                }

                @Override
                public void onBeginningOfSpeech() {
                    Log.e("TextInputLayout", "onBeginningOfSpeech");
                    //setDrawablesLRTB(context, CustomTextInputEditText.this, 0, 0, R.drawable.ic_mic_blue, 0);
                }

                @Override
                public void onRmsChanged(float rmsdB) {
                    Log.d("TextInputLayout", "onRmsChanged");
                    //setDrawablesLRTB(context, CustomTextInputEditText.this, 0, 0, R.drawable.ic_mic_blue, 0);
                }

                @Override
                public void onBufferReceived(byte[] buffer) {
                    Log.d("TextInputLayout", "onBufferReceived");
                }

                @Override
                public void onEndOfSpeech() {
                    //Toast.makeText(MainActivity.this,"onEndOfSpeech",Toast.LENGTH_SHORT).show();
                    Log.e("TextInputLayout", "onEndOfSpeech");
                    isListening = false;
                    mProgressDialog = DialogUtil.showProgressDialog(context);
                    //setDrawablesLRTB(context, CustomTextInputEditText.this, 0, 0, R.drawable.ic_mic_gray, 0);
                }

                @Override
                public void onError(int error) {
                    Log.e("TextInputLayout", "onError" + error);
                    isListening = false;
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    //setDrawablesLRTB(context, CustomTextInputEditText.this, 0, 0, R.drawable.ic_mic_gray, 0);
                }

                @Override
                public void onResults(Bundle results) {
                    try {
                        Log.e("TextInputLayout", "onResults");
                        isListening = false;
                        DialogUtil.cancelProgressDialog(mProgressDialog);
                        //setDrawablesLRTB(context, CustomTextInputEditText.this, 0, 0, R.drawable.ic_mic_gray, 0);

                        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                        Log.e("TextInputLayout ", matches.get(0).toString());

                        if (matches != null) {
                            //searchSpeechText(matches.get(0).toString());
                            String writeText = "";
                            if(etPocketBook.getText() != null && !etPocketBook.getText().toString().equalsIgnoreCase("")) {
                                writeText = etPocketBook.getText() + matches.get(0).toString() + " ";
                            } else {
                                writeText = matches.get(0).toString() + " ";
                            }
                            etPocketBook.setText(writeText);
                        }
                    } catch (Exception e) {
                        ExceptionLogger.Logger(e.getCause(), e.getMessage(), MicSearchActivity.class, "speechResult");
                    }
                }

                @Override
                public void onPartialResults(Bundle partialResults) {
                    Log.d("TextInputLayout", "onPartialResults");
                }

                @Override
                public void onEvent(int eventType, Bundle params) {
                    Log.d("TextInputLayout", "onEvent");
                }
            });
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), PocketBookActivity.class, "initializeSpeech");
        }
    }

    /**
     * Call the method start listening
     */
    private void startListening() {
        try {
            isListening = true;
            //setDrawablesLRTB(context, CustomTextInputEditText.this, 0, 0, R.drawable.ic_mic_blue, 0);
            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), PocketBookActivity.class, "startListening");
            //Toast.makeText(this, "There was an error " + e.toString(), Toast.LENGTH_LONG).show()
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
