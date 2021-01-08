package hcl.policing.digitalpolicingplatform.fragments.process;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.listeners.dialog.DialogCancelListener;
import hcl.policing.digitalpolicingplatform.listeners.dialog.ManuallyClickListener;

public class SearchDialogFragment extends DialogFragment implements View.OnClickListener {

    private ViewPager viewPager;
    private SearchViewPagerAdapter viewPagerAdaptor;
    private String searchType;
    private ManuallyClickListener manuallyClickListener;
    private DialogCancelListener dialogCancelListener;
    private List<Address> addressList;

    public static SearchDialogFragment newInstance(String searchType, List<Address> addressList) {

        SearchDialogFragment frag = new SearchDialogFragment();
        Bundle args = new Bundle();
        args.putString(GenericConstant.SEARCH_KEYWORD, searchType);
        args.putParcelableArrayList(GenericConstant.ADDRESS, (ArrayList<? extends Parcelable>) addressList);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        manuallyClickListener = (ManuallyClickListener) getActivity();
        dialogCancelListener = (DialogCancelListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.search_dialog_fragment, container, false);

        if (getArguments() != null) {
            searchType = getArguments().getString(GenericConstant.SEARCH_KEYWORD);
            addressList = getArguments().getParcelableArrayList(GenericConstant.ADDRESS);
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
        viewPager = view.findViewById(R.id.view_pager);
        setPagerData();
        //tvManually.setOnClickListener(this);
    }

    /**
     * Set Camera Photo in PagerAdaptor
     */
    private void setPagerData() {
        viewPagerAdaptor = new SearchViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(viewPagerAdaptor);
    }

    /**
     * fragment pager Adapter.
     */
    class SearchViewPagerAdapter extends FragmentStatePagerAdapter {

        public SearchViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;
            if (position == 0)
            {
                fragment = new SearchFragment();
                Bundle bundlePNC = new Bundle();
                bundlePNC.putString(GenericConstant.SEARCH_KEYWORD, searchType);
                bundlePNC.putParcelableArrayList(GenericConstant.ADDRESS, (ArrayList<? extends Parcelable>) addressList);
                fragment.setArguments(bundlePNC);
            }
            else if (position == 1)
            {
                fragment = new RecentLogsFragment();
                Bundle bundlePNC = new Bundle();
                bundlePNC.putString(GenericConstant.SEARCH_KEYWORD, searchType);
                fragment.setArguments(bundlePNC);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tvManually:
                manuallyClickListener.onManuallyClicker(GenericConstant.TYPE_VEHICLE);

                break;
        }
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        dialogCancelListener.dialogCancelled();
    }
}
