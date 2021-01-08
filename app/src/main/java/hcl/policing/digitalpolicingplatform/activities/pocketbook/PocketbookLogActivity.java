package hcl.policing.digitalpolicingplatform.activities.pocketbook;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.fragments.pocketbook.PocketbookGroups;
import hcl.policing.digitalpolicingplatform.fragments.pocketbook.PocketbookSingleEntries;

public class PocketbookLogActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private PocketbookPagerAdapter viewPagerAdaptor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pocketbook_log);
        initView();
    }

    private void initView() {
        ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText("Pocketbook Log");
        viewPager = findViewById(R.id.view_pager);
        setPagerData();
        TabLayout tabLayout = findViewById(R.id.tb_tab);
        tabLayout.setupWithViewPager(viewPager, true);
        //tvManually.setOnClickListener(this);
    }

    /**
     * Set Camera Photo in PagerAdaptor
     */
    private void setPagerData() {
        viewPagerAdaptor = new PocketbookPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdaptor);
    }

    /**
     * fragment pager Adapter.
     */
    class PocketbookPagerAdapter extends FragmentStatePagerAdapter {

        public PocketbookPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;
            if (position == 0)
            {
                fragment = new PocketbookSingleEntries();
                Bundle bundlePNC = new Bundle();
                //bundlePNC.putString(GenericConstant.SEARCH_KEYWORD, searchType);
                //bundlePNC.putParcelableArrayList(GenericConstant.ADDRESS, (ArrayList<? extends Parcelable>) addressList);
                fragment.setArguments(bundlePNC);
            }
            else if (position == 1)
            {
                fragment = new PocketbookGroups();
                Bundle bundlePNC = new Bundle();
                //bundlePNC.putString(GenericConstant.SEARCH_KEYWORD, searchType);
                fragment.setArguments(bundlePNC);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String name = "";
            if (position == 0) {
                name = "Single Entries";
            }
            else if (position == 1) {
                name = "Groups";
            }
            return name;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.iv_back:
                finish();
                break;
        }
    }
}
