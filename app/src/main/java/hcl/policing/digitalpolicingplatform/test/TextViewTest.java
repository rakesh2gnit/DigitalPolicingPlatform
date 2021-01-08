package hcl.policing.digitalpolicingplatform.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import hcl.policing.digitalpolicingplatform.customLibraries.CustomButton;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomCardView;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomLinearLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextView;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;

public class TextViewTest extends AppCompatActivity {

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
        cardViewDTO.setHeight(0);
        cardViewDTO.setWidth(-1);
        cardViewDTO.setMarginLeft(10);
        cardViewDTO.setMarginRight(10);
        cardViewDTO.setMarginTop(10);
        cardViewDTO.setMarginBottom(10);
        cardViewDTO.setElevation(2);
        cardViewDTO.setRadius(5);

        CustomCardView customCardView = new CustomCardView(this);
        customCardView.SetCardView(this, cardViewDTO);

        //linLayout.addView(customCardView);

        PropertiesBean textViewDTO = new PropertiesBean();
        textViewDTO.setWidth(-1);
        textViewDTO.setHeight(0);
        textViewDTO.setMarginTop(10);
        textViewDTO.setMarginLeft(10);
        textViewDTO.setMarginRight(10);
        textViewDTO.setId(111);
        textViewDTO.setGravity("center");
        //textViewDTO.setWeight(1);
        //textViewDTO.setLayoutGravity("centerhorizontal");
        textViewDTO.setTextStyle("italic");
        textViewDTO.setText("I am 1 TextView");
        textViewDTO.setVisibility("Visible");
        textViewDTO.setBackGroundColor("#FFFFFF");
        textViewDTO.setTextSize(22);

        CustomTextView customTextView = new CustomTextView(this);
        customTextView.SetTextView(this, textViewDTO);

        PropertiesBean textViewDTO2 = new PropertiesBean();
        textViewDTO2.setHeight(0);
        textViewDTO2.setWidth(0);
        textViewDTO2.setMarginTop(10);
        textViewDTO2.setId(112);
        //textViewDTO.setWeight(1);
        textViewDTO2.setLayoutGravity("centerhorizontal");
        textViewDTO2.setGravity("centervertical");
        textViewDTO2.setTextStyle("italic");
        textViewDTO2.setText("I am 2 TextView");
        textViewDTO2.setVisibility("Visible");
        textViewDTO2.setBackGroundColor("#FFFFFF");
        textViewDTO2.setTextSize(22);

        CustomTextView customTextView2 = new CustomTextView(this);
        customTextView2.SetTextView(this, textViewDTO2);

        PropertiesBean textViewDTO3 = new PropertiesBean();
        textViewDTO3.setHeight(0);
        textViewDTO3.setWidth(0);
        textViewDTO3.setMarginTop(10);
        textViewDTO3.setId(113);
        //textViewDTO.setWeight(1);
        textViewDTO3.setLayoutGravity("centerhorizontal");
        textViewDTO3.setGravity("centervertical");
        textViewDTO3.setTextStyle("italic");
        textViewDTO3.setText("I am 3 TextView");
        textViewDTO3.setVisibility("Visible");
        textViewDTO3.setBackGroundColor("#FFFFFF");
        textViewDTO3.setTextSize(22);

        CustomTextView customTextView3 = new CustomTextView(this);
        customTextView3.SetTextView(this, textViewDTO3);

        linLayout.addView(customTextView);
        linLayout.addView(customTextView2);
        linLayout.addView(customTextView3);

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
                Intent intent = new Intent(TextViewTest.this, CheckBoxTest.class);
                startActivity(intent);
            }
        });

        linLayout.addView(customButton);

        Log.e("ID ", " TextView 1 >> "+customTextView.getId()+" TextView 2 >> "+customTextView2.getId()+" TextView 3 >> "+customTextView3.getId());

    }
}
