package hcl.policing.digitalpolicingplatform.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import hcl.policing.digitalpolicingplatform.customLibraries.CustomButton;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomLinearLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextInputEditText;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextInputLayout;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;

public class TextInputLayoutTest extends AppCompatActivity {

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

        PropertiesBean editTextDTO = new PropertiesBean();
        editTextDTO.setHeight(0);
        editTextDTO.setWidth(-1);
        //editTextDTO.setWeight(1);
        editTextDTO.setDesignType("box");
        editTextDTO.setMarginLeft(20);
        editTextDTO.setMarginRight(20);
        editTextDTO.setMarginTop(20);
        editTextDTO.setMarginBottom(20);
        editTextDTO.setPaddingLeft(20);
        editTextDTO.setPaddingRight(10);
        editTextDTO.setPaddingTop(10);
        editTextDTO.setPaddingBottom(10);
        editTextDTO.setInputType("email");
        editTextDTO.setGravity("centervertical");
        editTextDTO.setTextStyle("italic");
        editTextDTO.setHint("Full Name");
        //editTextDTO.setText("Hello");
        editTextDTO.setVisibility("Visible");
        editTextDTO.setTextSize(12);
        editTextDTO.setMaxLength(25);
        editTextDTO.setMinLength(6);
        editTextDTO.setMendatry(false);
        editTextDTO.setErrorMsg("Field can not be blank");

        final CustomTextInputEditText customEditText = new CustomTextInputEditText(this);
        customEditText.SetTextInputEditText(this, editTextDTO);

        PropertiesBean textInputLayoutDTO = new PropertiesBean();
        textInputLayoutDTO.setHeight(0);
        textInputLayoutDTO.setWidth(-1);
        //textInputLayoutDTO.setWeight(1);
        textInputLayoutDTO.setDesignType("box");
        textInputLayoutDTO.setMarginLeft(20);
        textInputLayoutDTO.setMarginRight(20);
        textInputLayoutDTO.setMarginTop(20);
        textInputLayoutDTO.setMarginBottom(20);
        textInputLayoutDTO.setInputType("email");
        textInputLayoutDTO.setHint("Full Name");
        //textInputLayoutDTO.setText("Hello");
        textInputLayoutDTO.setVisibility("Visible");
        textInputLayoutDTO.setMaxLength(25);
        textInputLayoutDTO.setMinLength(6);
        textInputLayoutDTO.setMendatry(false);
        textInputLayoutDTO.setErrorMsg("Field can not be blank");

        CustomTextInputLayout customTextInputLayout = new CustomTextInputLayout(TextInputLayoutTest.this);
        customTextInputLayout.SetTextInputLayout(TextInputLayoutTest.this, textInputLayoutDTO, customEditText);

        //customTextInputLayout.addView(customEditText);
        linLayout.addView(customTextInputLayout);

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
                Intent intent = new Intent(TextInputLayoutTest.this, TextViewTest.class);
                startActivity(intent);
            }
        });

        linLayout.addView(customButton);

    }
}
