package hcl.policing.digitalpolicingplatform.test;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomAppBarLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomCoordinateLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomDrawerLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomNavigationView;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextView;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomToolbar;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.MenuDTO;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;

public class NavigationTest extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private CustomDrawerLayout customDrawerLayout;
    private CustomNavigationView customNavigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CustomToolbar customToolbar;
    private CustomTextView customTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        PropertiesBean drawerLayoutDTO = new PropertiesBean();
        drawerLayoutDTO.setWidth(-1);
        drawerLayoutDTO.setHeight(-1);
        //drawerLayoutDTO.set
        customDrawerLayout = new CustomDrawerLayout(this);
        customDrawerLayout.SetDrawerLayout(this, drawerLayoutDTO);
        setContentView(customDrawerLayout);

        ////////////////////////////////////////////////////////////////////

        PropertiesBean coordinateLayoutDTO = new PropertiesBean();
        coordinateLayoutDTO.setHeight(-1);
        coordinateLayoutDTO.setWidth(-1);
        coordinateLayoutDTO.setIsFitSystemWindow(true);

        CustomCoordinateLayout customCoordinateLayout = new CustomCoordinateLayout(this);
        customCoordinateLayout.SetCoordinateLayout(this, coordinateLayoutDTO);

        //customDrawerLayout.addView(customCoordinateLayout);

        PropertiesBean appBarLayoutDTO = new PropertiesBean();
        appBarLayoutDTO.setHeight(0);
        appBarLayoutDTO.setWidth(-1);
        appBarLayoutDTO.setBackGroundColor("#000000");

        CustomAppBarLayout customAppBarLayout = new CustomAppBarLayout(this);
        customAppBarLayout.SetAppBarLayout(this, appBarLayoutDTO);

        //customCoordinateLayout.addView(customAppBarLayout);

        PropertiesBean toolbarDTO = new PropertiesBean();
        toolbarDTO.setHeight(0);
        toolbarDTO.setWidth(-1);
        toolbarDTO.setBackGroundColor("#000000");
        //toolbarDTO.setTextColor("#000000");
        //toolbarDTO.setTitle("NavigationView Test");

        customToolbar = new CustomToolbar(this);
        customToolbar.SetToolbar(this, toolbarDTO);

        PropertiesBean textViewDTO = new PropertiesBean();
        textViewDTO.setHeight(0);
        textViewDTO.setWidth(0);
        textViewDTO.setTextSize(22);
        textViewDTO.setText("Nav Test");
        textViewDTO.setTextColor("#FFFFFF");
        textViewDTO.setLayoutGravity("center");
        //textViewDTO.setGravity("center");

        customTextView = new CustomTextView(this);
        customTextView.SetTextView(this, textViewDTO);


        customAppBarLayout.addView(customToolbar);
        customCoordinateLayout.addView(customAppBarLayout);

        customToolbar.addView(customTextView);

        customDrawerLayout.addView(customCoordinateLayout);

        List<MenuDTO> listMenu = new ArrayList<>();
        MenuDTO listItem1 = new MenuDTO();
        listItem1.setItem("Item 1");
        listItem1.setId(11);
        listMenu.add(listItem1);

        MenuDTO listItem2 = new MenuDTO();
        listItem2.setItem("Item 2");
        listItem2.setId(12);
        listMenu.add(listItem2);

        MenuDTO listItem3 = new MenuDTO();
        listItem3.setItem("Item 3");
        listItem3.setId(13);
        listMenu.add(listItem3);

        MenuDTO listItem4 = new MenuDTO();
        listItem4.setItem("Item 4");
        listItem4.setId(14);
        listMenu.add(listItem4);

        PropertiesBean navigationViewDTO = new PropertiesBean();
        navigationViewDTO.setWidth(250);
        navigationViewDTO.setHeight(-1);
        navigationViewDTO.setLayoutGravity("start");
        navigationViewDTO.setBackGroundColor("#d3d3d3");
        navigationViewDTO.setTextColorSelected("#ff0000");
        navigationViewDTO.setTextColorUnselected("#000000");
        navigationViewDTO.setIsFitSystemWindow(true);
        navigationViewDTO.setElevation(5);
        //navigationViewDTO.setMenuList(listMenu);

        customNavigationView = new CustomNavigationView(this);
        customNavigationView.SetNavigationView(this, navigationViewDTO);

        customDrawerLayout.addView(customNavigationView);

        if (customToolbar != null) {
            setSupportActionBar(customToolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        }

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, customDrawerLayout, customToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                //Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
                //InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                //inputMethodManager.hideSoftInputFromWindow(new View(MainActivity.this).getWindowToken(), 0);
            }
        };
        customDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, R.color.white));
        actionBarDrawerToggle.syncState();

        customNavigationView.setNavigationItemSelectedListener(this);

        //Log.e("ID ", " TextView 1 >> "+customTextView.getId()+" TextView 2 >> "+customTextView2.getId()+" TextView 3 >> "+customTextView3.getId());
    }

    public void createMenuButton(String title, String titleColor) {

        customTextView.setText(title);
        if (titleColor.equalsIgnoreCase("white")) {
            Objects.requireNonNull(getSupportActionBar()).show();
            customTextView.setTextColor(ContextCompat.getColor(this, R.color.white));
            actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        } else {
            Objects.requireNonNull(getSupportActionBar()).hide();
        }

        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, R.color.white));
        /*if (toolbarDrawable == null) {
            toolbarDrawable = toolbar.getNavigationIcon();
        }*/
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customDrawerLayout.isDrawerOpen(customNavigationView)) {
                    customDrawerLayout.closeDrawer(customNavigationView);
                } else {
                    //Utilities.hideKeyboard();
                    customDrawerLayout.openDrawer(customNavigationView);
                }
            }
        });
    }

    public void createBackButton(String title, String titleColor) {
        Objects.requireNonNull(getSupportActionBar()).show();
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!actionBarDrawerToggle.isDrawerIndicatorEnabled()) {
                    // When Shows BACK BUTTON
                    popFragment();
                } else {
                    //When Shows MENU BUTTON
                    if (customDrawerLayout.isDrawerOpen(customNavigationView)) {
                        customDrawerLayout.closeDrawer(customNavigationView);
                    } else {
                        customDrawerLayout.openDrawer(customNavigationView);
                    }
                }
            }
        });
        customTextView.setText(title);
        if (titleColor.equalsIgnoreCase("white")) {
            customTextView.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else {
            customTextView.setTextColor(ContextCompat.getColor(this, R.color.black));
        }
    }

    public void popFragment() {
        //Utilities.hideKeyboard();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            //showFragment(new Dashboard(), "DashBoard");
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        int mSelectedId = item.getItemId();
        Log.e("PRESSED ", "ID >>>>> " + mSelectedId);
        itemSelection(mSelectedId);
        return true;
    }

    private void itemSelection(int mSelectedId) {

        switch (mSelectedId) {

            case 11:
                try {
                    Log.e("PRESSED ", "PRESSED 1");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                customDrawerLayout.closeDrawer(GravityCompat.START);
                break;

            case 12:
                try {
                    Log.e("PRESSED ", "PRESSED 2");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                customDrawerLayout.closeDrawer(GravityCompat.START);
                break;
        }
    }
}