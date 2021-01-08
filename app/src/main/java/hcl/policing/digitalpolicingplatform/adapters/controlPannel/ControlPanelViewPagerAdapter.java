package hcl.policing.digitalpolicingplatform.adapters.controlPannel;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

import hcl.policing.digitalpolicingplatform.fragments.controlPannel.ControlPanelMainFrafment;
import hcl.policing.digitalpolicingplatform.fragments.controlPannel.ControlPannelFragment;
import hcl.policing.digitalpolicingplatform.fragments.controlPannel.MediaFragment;

public class ControlPanelViewPagerAdapter extends FragmentPagerAdapter {

    public ControlPanelViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new ControlPannelFragment();
        }
        else if (position == 1)
        {
            fragment = new ControlPanelMainFrafment();
        }
        else if (position == 2)
        {
            fragment = new MediaFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}