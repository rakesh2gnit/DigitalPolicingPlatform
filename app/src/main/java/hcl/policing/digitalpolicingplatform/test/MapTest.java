package hcl.policing.digitalpolicingplatform.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;

import hcl.policing.digitalpolicingplatform.customLibraries.CustomButton;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomCardView;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomFrameLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomLinearLayout;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;

public class MapTest extends AppCompatActivity {

    private CustomLinearLayout linLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        PropertiesBean linearLayoutDTO = new PropertiesBean();
        linearLayoutDTO.setWidth(-1);
        linearLayoutDTO.setHeight(-1);
        linearLayoutDTO.setGravity("centerhorizontal");
        linearLayoutDTO.setOrientation("vertical");
        linearLayoutDTO.setBackGroundColor("#d3d3d3");
        linLayout = new CustomLinearLayout(this);
        linLayout.SetLinearLayout(this, linearLayoutDTO);
        setContentView(linLayout);

        PropertiesBean cardViewDTO = new PropertiesBean();
        cardViewDTO.setHeight(300);
        cardViewDTO.setWidth(-1);
        cardViewDTO.setMarginLeft(10);
        cardViewDTO.setMarginRight(10);
        cardViewDTO.setMarginTop(10);
        cardViewDTO.setMarginBottom(10);
        cardViewDTO.setElevation(2);
        cardViewDTO.setRadius(5);

        CustomCardView customCardView = new CustomCardView(this);
        customCardView.SetCardView(this, cardViewDTO);

        linLayout.addView(customCardView);

        final PropertiesBean frameLyoutDTO = new PropertiesBean();
        frameLyoutDTO.setHeight(-1);
        frameLyoutDTO.setWidth(-1);
        frameLyoutDTO.setId(12345);

        final CustomFrameLayout customFrameLayout = new CustomFrameLayout(this);
        customFrameLayout.SetFrameLayout(this, frameLyoutDTO);

        customCardView.addView(customFrameLayout);

        PropertiesBean buttonDTO = new PropertiesBean();
        buttonDTO.setBackGroundColor("#000000");
        buttonDTO.setTextColor("#FFFFFF");
        buttonDTO.setMarginTop(50);
        buttonDTO.setText("Next");

        CustomButton customButton = new CustomButton(this);
        customButton.SetButton(this, buttonDTO);

        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapTest.this, CheckBoxTest.class);
                startActivity(intent);
            }
        });

        linLayout.addView(customButton);

        FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
        SupportMapFragment mFRaFragment = SupportMapFragment.newInstance();
        mTransaction.add(customFrameLayout.getId(), mFRaFragment);
        mTransaction.commit();

        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
