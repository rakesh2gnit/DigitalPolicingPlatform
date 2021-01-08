package hcl.policing.digitalpolicingplatform.fragments.process.fds.investigation;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.database.DatabaseHelper;
import hcl.policing.digitalpolicingplatform.database.SectionMapper;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.investigation.athena.InvestigationAthenaFragment;
import hcl.policing.digitalpolicingplatform.listeners.dialog.ManuallyClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.investigation.InvestigationSelectionListener;

public class InvestigationDialogFragment extends DialogFragment implements View.OnClickListener {

    private TabLayout tbPerson;
    private ViewPager viewPagerPerson;
    private String searchedWord, processName;
    private ViewPageAddressAdaptor viewPageAdaptor;
    private ManuallyClickListener manuallyClickListener;
    private InvestigationSelectionListener investigationSelectionListener;
    private ArrayList<SectionMapper> aRightsMapper;
    private DatabaseHelper db;
    private LinearLayout rlMain, llHeader;
    private boolean isPopulate;


    public static InvestigationDialogFragment newInstance(String searchPerson, String processName, boolean isPopulate) {

        InvestigationDialogFragment frag = new InvestigationDialogFragment();
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
        investigationSelectionListener = (InvestigationSelectionListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.custom_dialog_fragment, container, false);

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
        TextView tvPersonHeader = view.findViewById(R.id.tvPersonHeader);
        tbPerson = view.findViewById(R.id.tbPerson);
        viewPagerPerson = view.findViewById(R.id.viewPagerPerson);
        tvPersonHeader.setText(getString(R.string.select_cases));

        isPopulateView();
        aRightsMapper = db.getSectionList();
        validateRights();
        setPagerData();
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
    private void setPagerData() {
        viewPageAdaptor = new ViewPageAddressAdaptor(getChildFragmentManager());
        viewPagerPerson.setAdapter(viewPageAdaptor);
        tbPerson.setupWithViewPager(viewPagerPerson);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tvManually:

                manuallyClickListener.onManuallyClicker(GenericConstant.TYPE_ADDRESS);

                break;
        }
    }

    /**
     * fragment pager Adapter.
     */
    class ViewPageAddressAdaptor extends FragmentStatePagerAdapter {

        public ViewPageAddressAdaptor(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment callingFragment = null;

            if (aRightsMapper.get(position).getSectionName().contains(GenericConstant.ATHENA)) {

                callingFragment = new InvestigationAthenaFragment();
                Bundle bundleAthena = new Bundle();
                bundleAthena.putSerializable(GenericConstant.SEARCH_KEYWORD, searchedWord);
                bundleAthena.putSerializable(GenericConstant.LISTENER, investigationSelectionListener);
                callingFragment.setArguments(bundleAthena);

            } else {

                callingFragment = new InvestigationAthenaFragment();
                Bundle bundleAthena = new Bundle();
                bundleAthena.putSerializable(GenericConstant.SEARCH_KEYWORD, searchedWord);
                bundleAthena.putSerializable(GenericConstant.LISTENER, investigationSelectionListener);
                callingFragment.setArguments(bundleAthena);

            }
            return callingFragment;
        }

        @Override
        public int getCount() {
            return aRightsMapper.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return aRightsMapper.get(position).getSectionName();
        }
    }


    /**
     * Validate Rights for Vehicle
     */
    private void validateRights() {

        for (int i = 0; i < aRightsMapper.size(); i++) {

            if (!aRightsMapper.get(i).getSectionName().contains(GenericConstant.ATHENA)) {
                aRightsMapper.remove(i);
                i--;
            }
        }

    }

    /**
     * Initialize the database helper
     */
    private void initDatabaseHelper() {
        if (db == null) {
            db = new DatabaseHelper(getActivity());
        }
    }


    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        if (ProcessCreationActivity.QUESTION_COUNT > 0) {
            Log.e("CALLED person ", " >>>> back ");
            ProcessCreationActivity.QUESTION_COUNT--;
        }
        ProcessCreationActivity.isClickedSpeak = true;
    }
}
