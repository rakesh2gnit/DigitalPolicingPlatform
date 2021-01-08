package hcl.policing.digitalpolicingplatform.adapters.tasking;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import hcl.policing.digitalpolicingplatform.fragments.tasking.FragmentAdditional;
import hcl.policing.digitalpolicingplatform.fragments.tasking.FragmentDetail;
import hcl.policing.digitalpolicingplatform.fragments.tasking.FragmentLogs;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new FragmentDetail();
        } else if (position == 1) {
            fragment = new FragmentAdditional();
        } else if (position == 2) {
            fragment = new FragmentLogs();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "Task details";
        } else if (position == 1) {
            title = "Other details";
        } else if (position == 2) {
            title = "Logs";
        }
        return title;
    }
}