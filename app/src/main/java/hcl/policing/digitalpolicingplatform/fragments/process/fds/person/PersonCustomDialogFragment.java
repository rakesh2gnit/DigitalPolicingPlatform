package hcl.policing.digitalpolicingplatform.fragments.process.fds.person;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.database.DatabaseHelper;
import hcl.policing.digitalpolicingplatform.database.SectionMapper;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.person.athena.PersonATHENAFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.person.dl.PersonDLFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.person.nflms.PersonNFLMSFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.person.pnc.PersonPNCFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.person.pole.SearchListFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.person.qas.PersonQASFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.person.stops.PersonStopsFragment;
import hcl.policing.digitalpolicingplatform.listeners.dialog.DialogCancelListener;
import hcl.policing.digitalpolicingplatform.listeners.dialog.ManuallyClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.PersonSelectionListener;

public class PersonCustomDialogFragment extends DialogFragment implements View.OnClickListener {

    private TabLayout tbPerson;
    private ViewPager viewPagerPerson;
    private LinearLayout rlMain, llHeader;
    private String searchedWord, processName;
    private ManuallyClickListener manuallyClickListener;
    private DialogCancelListener dialogCancelListener;
    private PersonSelectionListener personSelectionListener;
    private ArrayList<SectionMapper> aRightsMapper;
    private ArrayList<SectionMapper> aRightsMapperUpdated;
    private DatabaseHelper db;
    private boolean isPopulate;
    private int processId = 10;
    private int subProcessId = 100;
    private Context context;

    public static PersonCustomDialogFragment newInstance(String searchPerson, String processName, boolean isPopulate) {

        PersonCustomDialogFragment frag = new PersonCustomDialogFragment();
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
        personSelectionListener = (PersonSelectionListener) getActivity();
        dialogCancelListener = (DialogCancelListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.custom_dialog_fragment, container, false);

        context = getActivity();
        if (getArguments() != null) {
            searchedWord = getArguments().getString(GenericConstant.SEARCH_KEYWORD);
            processName = getArguments().getString(GenericConstant.PROCESS_NAME);
            isPopulate = getArguments().getBoolean(GenericConstant.IS_POPULATE);
        }

        initView(rootView);

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
    private void initView(View view) {
        rlMain = view.findViewById(R.id.rlMain);
        llHeader = view.findViewById(R.id.llHeader);
        TextView tvManually = view.findViewById(R.id.tvManually);
        tbPerson = view.findViewById(R.id.tbPerson);
        viewPagerPerson = view.findViewById(R.id.viewPagerPerson);
        TextView tvPersonHeader = view.findViewById(R.id.tvPersonHeader);
        tvPersonHeader.setText(getString(R.string.choose_person));
        tbPerson.setTabMode(TabLayout.MODE_SCROLLABLE);

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
     * Set Camera Photo in PagerAdaptor
     */
    private void setPersonPagerData() {
        ViewPagePersonAdaptor viewPageAdaptor = new ViewPagePersonAdaptor(getChildFragmentManager());
        viewPagerPerson.setAdapter(viewPageAdaptor);
        tbPerson.setupWithViewPager(viewPagerPerson);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tvManually:
                manuallyClickListener.onManuallyClicker(GenericConstant.TYPE_PERSON);

                break;
        }
    }

    /**
     * fragment pager Adapter.
     */
    class ViewPagePersonAdaptor extends FragmentStatePagerAdapter {

        public ViewPagePersonAdaptor(FragmentManager fm) {
            super(fm);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {

            Fragment callingFragment = null;
            String sectionName=aRightsMapperUpdated.get(position).getSectionName();
            if (sectionName.equalsIgnoreCase(GenericConstant.DL)) {

                callingFragment = new PersonDLFragment();
                Bundle bundleDl = new Bundle();
                bundleDl.putSerializable(GenericConstant.SEARCH_KEYWORD, searchedWord);
                bundleDl.putSerializable(GenericConstant.LISTENER, personSelectionListener);
                bundleDl.putBoolean(GenericConstant.IS_POPULATE, isPopulate);
                callingFragment.setArguments(bundleDl);

            } else if (sectionName.equalsIgnoreCase(GenericConstant.NFLMS)) {

                callingFragment = new PersonNFLMSFragment();
                Bundle bundleNFLMS = new Bundle();
                bundleNFLMS.putSerializable(GenericConstant.SEARCH_KEYWORD, searchedWord);
                bundleNFLMS.putSerializable(GenericConstant.LISTENER, personSelectionListener);
                bundleNFLMS.putBoolean(GenericConstant.IS_POPULATE, isPopulate);
                callingFragment.setArguments(bundleNFLMS);

            } else if (sectionName.equalsIgnoreCase(GenericConstant.PNC)) {

                callingFragment = new PersonPNCFragment();
                Bundle bundlePNC = new Bundle();
                bundlePNC.putSerializable(GenericConstant.SEARCH_KEYWORD, searchedWord);
                bundlePNC.putSerializable(GenericConstant.LISTENER, personSelectionListener);
                bundlePNC.putBoolean(GenericConstant.IS_POPULATE, isPopulate);
                callingFragment.setArguments(bundlePNC);

            } else if (sectionName.equalsIgnoreCase(GenericConstant.ATHENA)) {

                callingFragment = new PersonATHENAFragment();
                Bundle bundleATHENA = new Bundle();
                bundleATHENA.putSerializable(GenericConstant.SEARCH_KEYWORD, searchedWord);
                bundleATHENA.putSerializable(GenericConstant.LISTENER, personSelectionListener);
                bundleATHENA.putBoolean(GenericConstant.IS_POPULATE, isPopulate);
                callingFragment.setArguments(bundleATHENA);

            } else if (sectionName.equalsIgnoreCase(GenericConstant.QAS)) {

                callingFragment = new PersonQASFragment();
                Bundle bundleQAS = new Bundle();
                bundleQAS.putSerializable(GenericConstant.SEARCH_KEYWORD, searchedWord);
                bundleQAS.putSerializable(GenericConstant.LISTENER, personSelectionListener);
                bundleQAS.putBoolean(GenericConstant.IS_POPULATE, isPopulate);
                callingFragment.setArguments(bundleQAS);

            } else if (sectionName.equalsIgnoreCase(GenericConstant.STOPS)) {
                callingFragment = new PersonStopsFragment();
                Bundle bundleStops = new Bundle();
                bundleStops.putSerializable(GenericConstant.SEARCH_KEYWORD, searchedWord);
                bundleStops.putSerializable(GenericConstant.LISTENER, personSelectionListener);
                bundleStops.putBoolean(GenericConstant.IS_POPULATE, isPopulate);
                callingFragment.setArguments(bundleStops);

            } else if (sectionName.equalsIgnoreCase(GenericConstant.POLE)) {
                callingFragment = new SearchListFragment();
                Bundle bundlePole = new Bundle();
                bundlePole.putSerializable(GenericConstant.SEARCH_KEYWORD, searchedWord);
                bundlePole.putSerializable(GenericConstant.LISTENER, personSelectionListener);
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

//            String[] strRights = aRightsMapperUpdated.get(position).getSectionName().split("\\s+");

            return aRightsMapperUpdated.get(position).getSectionName();
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
                if (aRightsMapper.get(i).getSectionName().contains(GenericConstant.PNC)) {

                    aRightsMapperUpdated.add(aRightsMapper.get(i));

                } else if (aRightsMapper.get(i).getSectionName().contains(GenericConstant.POLE)) {

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
            aRightsMapperUpdated.addAll(aRightsMapper);
        }
    }

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
