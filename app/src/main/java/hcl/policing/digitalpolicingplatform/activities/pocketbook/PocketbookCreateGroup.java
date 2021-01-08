package hcl.policing.digitalpolicingplatform.activities.pocketbook;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Objects;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.fragments.pocketbook.CreatePocketbookGroups;
import hcl.policing.digitalpolicingplatform.fragments.pocketbook.PocketbookGroups;
import hcl.policing.digitalpolicingplatform.fragments.pocketbook.PocketbookSaveGroup;
import hcl.policing.digitalpolicingplatform.fragments.pocketbook.PocketbookSingleEntries;
import hcl.policing.digitalpolicingplatform.models.process.ProcessLogDTO;

public class PocketbookCreateGroup extends BaseActivity implements View.OnClickListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pocketbook_create_group);
        initView();

        ArrayList<ProcessLogDTO> entriesList = (ArrayList<ProcessLogDTO>) getIntent().getSerializableExtra(GenericConstant.ANSWER_LIST);

        if(entriesList != null && entriesList.size() > 0) {
            Fragment fragment = new PocketbookSaveGroup();
            Bundle bun = new Bundle();
            bun.putSerializable(GenericConstant.ANSWER_LIST, entriesList);
            fragment.setArguments(bun);
            FragmentManager fm = getSupportFragmentManager();
            //fm.popBackStack("DashBoard", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_left);
            ft.replace(R.id.frame_layout, fragment);
            ft.commit();
        } else {
            Fragment fragment = new CreatePocketbookGroups();
            /*Bundle bun = new Bundle();
            bun.putString("Order_id", orderId);
            fragment.setArguments(bun);*/
            FragmentManager fm = getSupportFragmentManager();
            //fm.popBackStack("DashBoard", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_left);
            ft.replace(R.id.frame_layout, fragment);
            ft.commit();
        }
    }

    private void initView() {
        ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText("New Group");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
