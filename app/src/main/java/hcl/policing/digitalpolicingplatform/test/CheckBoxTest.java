package hcl.policing.digitalpolicingplatform.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.customLibraries.CustomButton;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomCardView;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomCheckBox;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomLinearLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomRadioButton;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomRadioGroup;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;

public class CheckBoxTest extends AppCompatActivity {

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
        cardViewDTO.setMarginBottom(5);
        cardViewDTO.setElevation(2);
        cardViewDTO.setRadius(5);

        CustomCardView customCardView = new CustomCardView(this);
        customCardView.SetCardView(this, cardViewDTO);

        linLayout.addView(customCardView);

        PropertiesBean checkBoxDTO = new PropertiesBean();
        checkBoxDTO.setHeight(0);
        checkBoxDTO.setWidth(0);
        //checkBoxDTO.setWeight(1);
        //checkBoxDTO.setp
        checkBoxDTO.setPaddingLeft(5);
        checkBoxDTO.setText("I am CheckBox");
        checkBoxDTO.setVisibility("Visible");
        checkBoxDTO.setTextSize(12);
        checkBoxDTO.setTextColor("#000000");
        checkBoxDTO.setCheckColor("#ff0000");
        checkBoxDTO.setUnCheckColor("#ff0000");
        //checkBoxDTO.setButtonSelector("drawable");

        CustomCheckBox customCheckBox = new CustomCheckBox(this);
        customCheckBox.SetCheckBox(this, checkBoxDTO);

        customCardView.addView(customCheckBox);

        PropertiesBean cardViewDTO1 = new PropertiesBean();
        cardViewDTO1.setHeight(0);
        cardViewDTO1.setWidth(-1);
        cardViewDTO1.setMarginLeft(10);
        cardViewDTO1.setMarginRight(10);
        cardViewDTO1.setMarginTop(5);
        cardViewDTO1.setMarginBottom(10);
        cardViewDTO1.setElevation(2);
        cardViewDTO1.setRadius(5);

        CustomCardView customCardView1 = new CustomCardView(this);
        customCardView1.SetCardView(this, cardViewDTO1);

        linLayout.addView(customCardView1);

        PropertiesBean radioButtonDTO = new PropertiesBean();
        radioButtonDTO.setHeight(0);
        radioButtonDTO.setWidth(0);
        radioButtonDTO.setWeight(1);
        radioButtonDTO.setPaddingLeft(5);
        radioButtonDTO.setText("Male");
        radioButtonDTO.setVisibility("Visible");
        radioButtonDTO.setTextSize(12);
        radioButtonDTO.setTextColor("#000000");
        //radioButtonDTO.setCheckColor("#ff0000");
        //radioButtonDTO.setUnCheckColor("#ff0000");
        radioButtonDTO.setButtonSelector("drawable");

        CustomRadioButton customRadioButton = new CustomRadioButton(this);
        customRadioButton.SetRadioButton(this, radioButtonDTO);

        PropertiesBean radioButtonDTO1 = new PropertiesBean();
        radioButtonDTO1.setHeight(0);
        radioButtonDTO1.setWidth(0);
        radioButtonDTO1.setWeight(1);
        radioButtonDTO1.setPaddingLeft(5);
        radioButtonDTO1.setText("Female");
        radioButtonDTO1.setVisibility("Visible");
        radioButtonDTO1.setTextSize(12);
        radioButtonDTO1.setTextColor("#000000");
        //radioButtonDTO.setCheckColor("#ff0000");
        //radioButtonDTO.setUnCheckColor("#ff0000");
        radioButtonDTO1.setButtonSelector("drawable");

        CustomRadioButton customRadioButton1 = new CustomRadioButton(this);
        customRadioButton1.SetRadioButton(this, radioButtonDTO1);

        ArrayList<RadioButton> listButton = new ArrayList<>();
        listButton.add(customRadioButton);
        listButton.add(customRadioButton1);

        PropertiesBean radioGroupDTO = new PropertiesBean();
        radioGroupDTO.setHeight(0);
        radioGroupDTO.setWidth(-1);
        radioGroupDTO.setOrientation("horizontal");
        //radioGroupDTO.setRadioButtonList(listButton);

        CustomRadioGroup customRadioGroup = new CustomRadioGroup(this);
        customRadioGroup.SetRadioGroup(this, radioGroupDTO);

        customCardView1.addView(customRadioGroup);
        //customCardView1.addView(customRadioButton1);

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
                Intent intent = new Intent(CheckBoxTest.this, MapTest.class);
                startActivity(intent);
            }
        });

        linLayout.addView(customButton);

    }
}
