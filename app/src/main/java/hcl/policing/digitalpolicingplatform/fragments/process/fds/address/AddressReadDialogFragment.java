package hcl.policing.digitalpolicingplatform.fragments.process.fds.address;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.micSearch.MicSearchActivity;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.MovableFloatingActionButton;
import hcl.policing.digitalpolicingplatform.database.DatabaseHelper;
import hcl.policing.digitalpolicingplatform.database.SectionMapper;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.address.athena.AddressAthenaFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.address.nflms.AddressNFLMSFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.address.pole.AddressPoleFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.address.qas.AddressQASFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.address.stops.AddressSTOPSFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.person.PersonReadDialogFragment;
import hcl.policing.digitalpolicingplatform.listeners.dialog.DialogCancelListener;
import hcl.policing.digitalpolicingplatform.listeners.dialog.ManuallyClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.address.AddressSelectionListener;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.speak.SpeakToPersonUtil;

public class AddressReadDialogFragment extends DialogFragment implements View.OnClickListener, TextToSpeech.OnInitListener {

    public String TAG = "SpeechListener";
    private TabLayout tbAddress;
    private ViewPager viewPagerPerson;
    private TextView tvManually, tvPersonHeader;
    private String searchedWord, processName;
    private ViewPageAddressAdaptor viewPageAdaptor;
    private ManuallyClickListener manuallyClickListener;
    private DialogCancelListener dialogCancelListener;
    private AddressSelectionListener addressSelectionListener;
    private ArrayList<SectionMapper> aRightsMapper;
    private ArrayList<SectionMapper> aRightsMapperUpdated;
    private DatabaseHelper db;
    private boolean isPopulate;
    private LinearLayout rlMain, llHeader;
    private int processId = 10;
    private int subProcessId = 102;
    private Context context;
    public static boolean isListen = false, isAnswerYes = false;;
    private static boolean isReadRecord = false, isReadMoreRecord = false;
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    public TextToSpeech textToSpeech;
    private SparseArray<Fragment> registeredFragments = new SparseArray<>();
    private int fragPos = 0;
    private MovableFloatingActionButton fabPlay;

    public static AddressReadDialogFragment newInstance(String searchPerson, String processName, boolean isPopulate) {

        AddressReadDialogFragment frag = new AddressReadDialogFragment();
        Bundle args = new Bundle();
        args.putString(GenericConstant.SEARCH_KEYWORD, searchPerson);
        args.putString(GenericConstant.PROCESS_NAME, processName);
        args.putBoolean(GenericConstant.IS_POPULATE, isPopulate);
        frag.setArguments(args);
        return frag;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDatabaseHelper();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        manuallyClickListener = (ManuallyClickListener) getActivity();
        addressSelectionListener = (AddressSelectionListener) getActivity();
        dialogCancelListener = (DialogCancelListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.custom_dialog_fragment, container, false);

        context = getActivity();
        try {
            textToSpeech = new TextToSpeech(context, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initializeSpeech();
        if (getArguments() != null) {
            searchedWord = getArguments().getString(GenericConstant.SEARCH_KEYWORD);
            processName = getArguments().getString(GenericConstant.PROCESS_NAME);
            isPopulate = getArguments().getBoolean(GenericConstant.IS_POPULATE);
        }

        initView(rootView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isListen = true;
                setFlag(true, false);
                readData("Do you want me to read address data?");
            }
        }, 1500);
        return rootView;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().getAttributes().windowAnimations = R.style.custom_dialog;
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    /**
     * Load the initial views
     *
     * @param view
     */
    @SuppressLint("RestrictedApi")
    private void initView(View view) {
        rlMain = view.findViewById(R.id.rlMain);
        llHeader = view.findViewById(R.id.llHeader);
        tvManually = view.findViewById(R.id.tvManually);
        tbAddress = view.findViewById(R.id.tbPerson);
        viewPagerPerson = view.findViewById(R.id.viewPagerPerson);
        tvPersonHeader = view.findViewById(R.id.tvPersonHeader);
        tvPersonHeader.setText(getString(R.string.select_address));
        tbAddress.setTabMode(TabLayout.MODE_SCROLLABLE);
        fabPlay = view.findViewById(R.id.fab);
        fabPlay.setVisibility(View.VISIBLE);
        fabPlay.setOnClickListener(this);

        isPopulateView();

        aRightsMapper = db.getSectionList();
        validateRights();
        setPersonPagerData();
        tvManually.setOnClickListener(this);
    }

    /**
     * Check the condition for populate view.
     */
    private void isPopulateView() {
        if (!isPopulate) {
            llHeader.setVisibility(View.GONE);
            CoordinatorLayout.LayoutParams relativeParams = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
            relativeParams.setMargins(0, 0, 0, 0);
            rlMain.setLayoutParams(relativeParams);
        }
    }

    /**
     * Initialize the speech
     */
    private void initializeSpeech() {
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
                }

                @Override
                public void onError(int error) {
                    Log.e(TAG, "onError" + error);
                }

                @Override
                public void onResults(Bundle results) {
                    try {
                        Log.e(TAG, "onResults");

                        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                        Log.e(TAG, matches.get(0).toString());

                        if (matches != null) {
                            answerAfterSpeech(matches.get(0).toString());
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
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), PersonReadDialogFragment.class, "initializeSpeech");
        }
    }

    /**
     * Call the method start listening
     */
    private void startListening() {
        try {
            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), PersonReadDialogFragment.class, "startListening");
        }
    }

    private void answerAfterSpeech(String answer) {
        if(answer != null && !answer.equalsIgnoreCase("")) {
            if(answer.toLowerCase().equalsIgnoreCase("yes")) {
                isAnswerYes = true;
                if(isReadRecord) {
                    Fragment fragment = (Fragment) viewPageAdaptor.getRegisteredFragment(fragPos);
                    readFragmentData(fragment);
                } else if(isReadMoreRecord) {
                    Fragment fragment = (Fragment) viewPageAdaptor.getRegisteredFragment(fragPos);
                    readFragmentData(fragment);
                }
            } else if(answer.toLowerCase().equalsIgnoreCase("no")) {
                isAnswerYes = false;
            }
        }
    }

    public static void setFlag(boolean rr, boolean rmr) {
        isReadRecord = rr;
        isReadMoreRecord = rmr;
    }

    /**
     * Set Camera Photo in PagerAdaptor
     */
    private void setPersonPagerData() {
        viewPageAdaptor = new ViewPageAddressAdaptor(getChildFragmentManager());
        viewPagerPerson.setAdapter(viewPageAdaptor);
        tbAddress.setupWithViewPager(viewPagerPerson);
        viewPagerPerson.setCurrentItem(0);

        viewPagerPerson.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                fragPos = position;
                //finding current fragment position on scroll
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(isAnswerYes) {
                            isListen = true;
                            setFlag(true, false);
                            readData("Do you want me to read address data?");
                        }
                    }
                }, 1000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void readFragmentData(Fragment fragment) {
        fabPlay.setImageResource(R.drawable.ic_pause);
        isListen = false;
        if(fragment instanceof AddressAthenaFragment) {

            AddressAthenaFragment.readData();
            readData(AddressAthenaFragment.textRead);

        } else if(fragment instanceof AddressNFLMSFragment) {

            AddressNFLMSFragment.readData();
            readData(AddressNFLMSFragment.textRead);

        } else if(fragment instanceof AddressSTOPSFragment) {

            AddressSTOPSFragment.readData();
            readData(AddressSTOPSFragment.textRead);

        } else if(fragment instanceof AddressQASFragment) {

            AddressQASFragment.readData();
            readData(AddressQASFragment.textRead);

        } else if(fragment instanceof AddressPoleFragment) {

            AddressPoleFragment.readData();
            readData(AddressPoleFragment.textRead);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tvManually:

                manuallyClickListener.onManuallyClicker(GenericConstant.TYPE_ADDRESS);

                break;

            case R.id.fab:
                if (textToSpeech != null) {
                    if (textToSpeech.isSpeaking()) {
                        textToSpeech.stop();
                        fabPlay.setImageResource(R.drawable.ic_play);
                    } else {
                        fabPlay.setImageResource(R.drawable.ic_pause);
                        Fragment fragment = (Fragment) viewPageAdaptor.getRegisteredFragment(fragPos);
                        readFragmentData(fragment);
                    }
                }
                break;
        }
    }

    private void readData(String textSpeak) {
        if (textSpeak != null)
            SpeakToPersonUtil.speakToPerson(context, textToSpeech, textSpeak);
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
                    Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                        public void run() {
                            fabPlay.setImageResource(R.drawable.ic_play);
                            if(isListen)
                                startListening();
                        }
                    });
                }

                @Override
                public void onError(String utteranceId) {

                }
            });
        }
    }

    /**
     * fragment pager Adapter.
     */
    class ViewPageAddressAdaptor extends FragmentStatePagerAdapter {

        public ViewPageAddressAdaptor(FragmentManager fm) {
            super(fm);
        }

        @NotNull
        @Override
        public Object instantiateItem(@NotNull ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {

            Fragment callingFragment = null;
            String sectionName = aRightsMapperUpdated.get(position).getSectionName();

            if (sectionName.equalsIgnoreCase(GenericConstant.ATHENA)) {

                callingFragment = new AddressAthenaFragment();
                Bundle bundleAthena = new Bundle();
                bundleAthena.putSerializable(GenericConstant.SEARCH_KEYWORD, searchedWord);
                bundleAthena.putSerializable(GenericConstant.LISTENER, addressSelectionListener);
                bundleAthena.putBoolean(GenericConstant.IS_POPULATE, isPopulate);
                callingFragment.setArguments(bundleAthena);

            } else if (sectionName.equalsIgnoreCase(GenericConstant.NFLMS)) {

                callingFragment = new AddressNFLMSFragment();
                Bundle bundleNFLMS = new Bundle();
                bundleNFLMS.putSerializable(GenericConstant.SEARCH_KEYWORD, searchedWord);
                bundleNFLMS.putSerializable(GenericConstant.LISTENER, addressSelectionListener);
                bundleNFLMS.putBoolean(GenericConstant.IS_POPULATE, isPopulate);
                callingFragment.setArguments(bundleNFLMS);

            } else if (sectionName.equalsIgnoreCase(GenericConstant.STOPS)) {

                callingFragment = new AddressSTOPSFragment();
                Bundle bundleStops = new Bundle();
                bundleStops.putSerializable(GenericConstant.SEARCH_KEYWORD, searchedWord);
                bundleStops.putSerializable(GenericConstant.LISTENER, addressSelectionListener);
                bundleStops.putBoolean(GenericConstant.IS_POPULATE, isPopulate);
                callingFragment.setArguments(bundleStops);

            } else if (sectionName.equalsIgnoreCase(GenericConstant.QAS)) {
                callingFragment = new AddressQASFragment();
                Bundle bundleQAS = new Bundle();
                bundleQAS.putSerializable(GenericConstant.SEARCH_KEYWORD, searchedWord);
                bundleQAS.putSerializable(GenericConstant.LISTENER, addressSelectionListener);
                bundleQAS.putBoolean(GenericConstant.IS_POPULATE, isPopulate);
                callingFragment.setArguments(bundleQAS);

            } else if (sectionName.equalsIgnoreCase(GenericConstant.POLE)) {
                callingFragment = new AddressPoleFragment();
                Bundle bundlePole = new Bundle();
                bundlePole.putSerializable(GenericConstant.SEARCH_KEYWORD, searchedWord);
                bundlePole.putSerializable(GenericConstant.LISTENER, addressSelectionListener);
                bundlePole.putBoolean(GenericConstant.IS_POPULATE, isPopulate);
                callingFragment.setArguments(bundlePole);
            }
            return callingFragment;
        }

        @Override
        public int getCount() {
            return aRightsMapperUpdated.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return aRightsMapperUpdated.get(position).getSectionName();
        }

        @Override
        public void destroyItem(@NotNull ViewGroup container, int position, @NotNull Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }

    /**
     *  * Validate Rights for Vehicle
     *  
     */
    private void validateRights() {

        aRightsMapperUpdated = new ArrayList<>();
        aRightsMapperUpdated.clear();
        if (processName != null && processName.equalsIgnoreCase(getResources().getString(R.string.tor_creation))) {
            for (int i = 0; i < aRightsMapper.size(); i++) {
                if (aRightsMapper.get(i).getSectionName().contains(GenericConstant.POLE)) {

                    aRightsMapperUpdated.add(aRightsMapper.get(i));

                } else if (aRightsMapper.get(i).getSectionName().contains(GenericConstant.QAS)) {

                    aRightsMapperUpdated.add(aRightsMapper.get(i));
                }
            }
        } else if ((processName != null && processName.equalsIgnoreCase(getResources().getString(R.string.stop_process)))
                || (processName != null && processName.equalsIgnoreCase(getResources().getString(R.string.crime)))) {
            for (int i = 0; i < aRightsMapper.size(); i++) {
                if (aRightsMapper.get(i).getSectionName().contains(GenericConstant.POLE)) {

                    aRightsMapperUpdated.add(aRightsMapper.get(i));
                }
            }
        } else {
            for (int i = 0; i < aRightsMapper.size(); i++) {

                if (!aRightsMapper.get(i).getSectionName().contains(GenericConstant.DL)) {
                    aRightsMapperUpdated.add(aRightsMapper.get(i));
                }
            }
        }
    }

    /**
     * Initialize the Database helper
     */
    private void initDatabaseHelper() {
        if (db == null) {
            db = new DatabaseHelper(getActivity());
        }
    }


    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        dialogCancelListener.dialogCancelled();
    }
}
