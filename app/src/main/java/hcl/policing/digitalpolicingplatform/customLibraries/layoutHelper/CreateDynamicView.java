package hcl.policing.digitalpolicingplatform.customLibraries.layoutHelper;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableRow;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.adapters.attachment.UserDocListAdaptor;
import hcl.policing.digitalpolicingplatform.adapters.audio.UserAudioListAdaptor;
import hcl.policing.digitalpolicingplatform.adapters.camera.UserImageListAdaptor;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.fieldName.FieldsNameConstant;
import hcl.policing.digitalpolicingplatform.constants.viewProperties.ViewPropertiesConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomCardView;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomCheckBox;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomCommonProperties;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomHorizontalScrollView;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomImageView;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomLinearLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomRecyclerView;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomRelativeLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextView;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.listeners.OnItemLongClickListener;
import hcl.policing.digitalpolicingplatform.models.camera.PhotoListModel;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.LayoutFieldHelper;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.models.process.ExpectedAnswerListBean;
import hcl.policing.digitalpolicingplatform.models.process.PageSection_detailListBean;
import hcl.policing.digitalpolicingplatform.models.process.SubSectionListBean;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CreateDynamicView {

    /**
     * Call the assitant text
     *
     * @param context
     * @param text
     * @param parentView
     * @param aExpectedAnswerListBeans
     * @param onClickAnswer
     */
    public static void assistantText(Context context, String text, ViewGroup parentView, List<ExpectedAnswerListBean> aExpectedAnswerListBeans, View.OnClickListener onClickAnswer) {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset(context, GenericConstant.BLANK_PROPERTIES_JSON));
            Gson gson = new Gson();

            PropertiesBean propertiesBean1 = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBean1.setPaddingLeft(20);
            propertiesBean1.setPaddingRight(10);
            propertiesBean1.setPaddingBottom(10);
            propertiesBean1.setPaddingTop(7);
            propertiesBean1.setMarginTop(10);
            propertiesBean1.setBackGround(ViewPropertiesConstant.BackgroundAssistant);
            propertiesBean1.setLayoutGravity(ViewPropertiesConstant.Start);
            propertiesBean1.setOrientation(ViewPropertiesConstant.Vertical);
            CustomLinearLayout customLinearLayoutVertical = new CustomLinearLayout(context);
            customLinearLayoutVertical.SetLinearLayout(context, propertiesBean1);
            //customLinearLayoutVertical.setElevation(1);
            parentView.addView(customLinearLayoutVertical);

            PropertiesBean propertiesBean = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBean.setText(FieldsNameConstant.Text_Assistant);
            propertiesBean.setTextSize(12);
            propertiesBean.setTextColor(ViewPropertiesConstant.Color_Primary);
            propertiesBean.setGravity(ViewPropertiesConstant.Center);
            propertiesBean.setLayoutGravity(ViewPropertiesConstant.Start);

            LayoutFieldHelper layoutFieldHelper = new LayoutFieldHelper();
            layoutFieldHelper.setViewType(ViewPropertiesConstant.textView);
            layoutFieldHelper.setParentView(customLinearLayoutVertical);
            layoutFieldHelper.setPropertiesBean(propertiesBean);

            CreateLayout.createLayoutFields(context, layoutFieldHelper);

            PropertiesBean propertiesBeanT = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBeanT.setText(text);
            propertiesBeanT.setTextSize(15);
            propertiesBeanT.setTextColor(ViewPropertiesConstant.Color_Gray);

            LayoutFieldHelper layoutFieldHelper1 = new LayoutFieldHelper();
            layoutFieldHelper1.setViewType(ViewPropertiesConstant.textView);
            layoutFieldHelper1.setParentView(customLinearLayoutVertical);
            layoutFieldHelper1.setPropertiesBean(propertiesBeanT);

            CreateLayout.createLayoutFields(context, layoutFieldHelper1);

            if (aExpectedAnswerListBeans != null) {
                PropertiesBean propertiesBeanHS = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
                }.getType());
                propertiesBeanHS.setLayoutGravity(ViewPropertiesConstant.Start);
                propertiesBeanHS.setOrientation(ViewPropertiesConstant.Horizontal);
                CustomHorizontalScrollView customHorizontalScrollView = new CustomHorizontalScrollView(context);
                customHorizontalScrollView.SetScrollView(context, propertiesBeanHS);
                customLinearLayoutVertical.addView(customHorizontalScrollView);

                PropertiesBean propertiesBeanL = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
                }.getType());
                propertiesBeanL.setLayoutGravity(ViewPropertiesConstant.Start);
                if (aExpectedAnswerListBeans.get(0).getAnswer().length() > 20 || aExpectedAnswerListBeans.size() > 3) {
                    propertiesBeanL.setOrientation(ViewPropertiesConstant.Vertical);
                } else {
                    propertiesBeanL.setOrientation(ViewPropertiesConstant.Horizontal);
                }
                CustomLinearLayout customLinearLayout = new CustomLinearLayout(context);
                customLinearLayout.SetLinearLayout(context, propertiesBeanL);
                customHorizontalScrollView.addView(customLinearLayout);

                for (int i = 0; i < aExpectedAnswerListBeans.size(); i++) {
                    PropertiesBean propertiesBeanY = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
                    }.getType());

                    propertiesBeanY.setId(aExpectedAnswerListBeans.get(i).getButton_Id());
                    propertiesBeanY.setHeight(45);
                    propertiesBeanY.setMinWidth(60);
                    propertiesBeanY.setMarginTop(10);
                    propertiesBeanY.setMarginRight(20);
                    propertiesBeanY.setPaddingLeft(10);
                    propertiesBeanY.setPaddingRight(10);
                    propertiesBeanY.setPaddingBottom(1);
                    propertiesBeanY.setText(aExpectedAnswerListBeans.get(i).getAnswer());
                    propertiesBeanY.setTextSize(15);
                    propertiesBeanY.setTextColor(ViewPropertiesConstant.Color_White);
                    propertiesBeanY.setBackGround(ViewPropertiesConstant.BackgroundGreen);
                    propertiesBeanY.setGravity(ViewPropertiesConstant.Center);
                    propertiesBeanY.setOnClickListener(onClickAnswer);

                    LayoutFieldHelper layoutFieldHelper2 = new LayoutFieldHelper();
                    layoutFieldHelper2.setViewType(ViewPropertiesConstant.button);
                    layoutFieldHelper2.setParentView(customLinearLayout);
                    layoutFieldHelper2.setPropertiesBean(propertiesBeanY);

                    CreateLayout.createLayoutFields(context, layoutFieldHelper2);
                }
            }
        } catch (JSONException e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateDynamicView.class, "assistantText");
        }
    }

    /**
     * Call the assistant text method
     *
     * @param context
     * @param text
     * @param parentView
     * @param aExpectedAnswerListBeans
     * @param onClickAnswer
     * @param index
     */
    public static void assistantTextAt(Context context, String text, ViewGroup parentView, List<ExpectedAnswerListBean> aExpectedAnswerListBeans, View.OnClickListener onClickAnswer, int index) {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset(context, GenericConstant.BLANK_PROPERTIES_JSON));
            Gson gson = new Gson();

            PropertiesBean propertiesBean1 = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBean1.setPaddingLeft(20);
            propertiesBean1.setPaddingRight(10);
            propertiesBean1.setPaddingBottom(10);
            propertiesBean1.setPaddingTop(7);
            propertiesBean1.setMarginTop(10);
            propertiesBean1.setBackGround(ViewPropertiesConstant.BackgroundAssistant);
            propertiesBean1.setLayoutGravity(ViewPropertiesConstant.Start);
            propertiesBean1.setOrientation(ViewPropertiesConstant.Vertical);
            CustomLinearLayout customLinearLayoutVertical = new CustomLinearLayout(context);
            customLinearLayoutVertical.SetLinearLayout(context, propertiesBean1);
            //customLinearLayoutVertical.setElevation(1);
            parentView.addView(customLinearLayoutVertical, index);

            PropertiesBean propertiesBean = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBean.setText(FieldsNameConstant.Text_Assistant);
            propertiesBean.setTextSize(12);
            propertiesBean.setTextColor(ViewPropertiesConstant.Color_Primary);
            propertiesBean.setGravity(ViewPropertiesConstant.Center);
            propertiesBean.setLayoutGravity(ViewPropertiesConstant.Start);

            LayoutFieldHelper layoutFieldHelper = new LayoutFieldHelper();
            layoutFieldHelper.setViewType(ViewPropertiesConstant.textView);
            layoutFieldHelper.setParentView(customLinearLayoutVertical);
            layoutFieldHelper.setPropertiesBean(propertiesBean);

            CreateLayout.createLayoutFields(context, layoutFieldHelper);

            PropertiesBean propertiesBeanT = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBeanT.setText(text);
            propertiesBeanT.setTextSize(15);
            propertiesBeanT.setTextColor(ViewPropertiesConstant.Color_Gray);

            LayoutFieldHelper layoutFieldHelper1 = new LayoutFieldHelper();
            layoutFieldHelper1.setViewType(ViewPropertiesConstant.textView);
            layoutFieldHelper1.setParentView(customLinearLayoutVertical);
            layoutFieldHelper1.setPropertiesBean(propertiesBeanT);

            CreateLayout.createLayoutFields(context, layoutFieldHelper1);

            if (aExpectedAnswerListBeans != null) {
                PropertiesBean propertiesBeanHS = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
                }.getType());
                propertiesBeanHS.setLayoutGravity(ViewPropertiesConstant.Start);
                propertiesBeanHS.setOrientation(ViewPropertiesConstant.Horizontal);
                CustomHorizontalScrollView customHorizontalScrollView = new CustomHorizontalScrollView(context);
                customHorizontalScrollView.SetScrollView(context, propertiesBeanHS);
                customLinearLayoutVertical.addView(customHorizontalScrollView);

                PropertiesBean propertiesBeanL = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
                }.getType());
                propertiesBeanL.setLayoutGravity(ViewPropertiesConstant.Start);
                if (aExpectedAnswerListBeans.get(0).getAnswer().length() > 20) {
                    propertiesBeanL.setOrientation(ViewPropertiesConstant.Vertical);
                } else {
                    propertiesBeanL.setOrientation(ViewPropertiesConstant.Horizontal);
                }
                CustomLinearLayout customLinearLayout = new CustomLinearLayout(context);
                customLinearLayout.SetLinearLayout(context, propertiesBeanL);
                customHorizontalScrollView.addView(customLinearLayout);

                for (int i = 0; i < aExpectedAnswerListBeans.size(); i++) {
                    PropertiesBean propertiesBeanY = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
                    }.getType());

                    propertiesBeanY.setId(aExpectedAnswerListBeans.get(i).getButton_Id());
                    propertiesBeanY.setHeight(45);
                    propertiesBeanY.setMinWidth(60);
                    propertiesBeanY.setMarginTop(10);
                    propertiesBeanY.setMarginRight(20);
                    propertiesBeanY.setPaddingLeft(10);
                    propertiesBeanY.setPaddingRight(10);
                    propertiesBeanY.setPaddingBottom(1);
                    propertiesBeanY.setText(aExpectedAnswerListBeans.get(i).getAnswer());
                    propertiesBeanY.setTextSize(15);
                    propertiesBeanY.setTextColor(ViewPropertiesConstant.Color_White);
                    propertiesBeanY.setBackGround(ViewPropertiesConstant.BackgroundGreen);
                    propertiesBeanY.setGravity(ViewPropertiesConstant.Center);
                    propertiesBeanY.setOnClickListener(onClickAnswer);

                    LayoutFieldHelper layoutFieldHelper2 = new LayoutFieldHelper();
                    layoutFieldHelper2.setViewType(ViewPropertiesConstant.button);
                    layoutFieldHelper2.setParentView(customLinearLayout);
                    layoutFieldHelper2.setPropertiesBean(propertiesBeanY);

                    CreateLayout.createLayoutFields(context, layoutFieldHelper2);
                }
            }
        } catch (JSONException e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateDynamicView.class, "assistantText");
        }
    }

    /**
     * Call the assistant text with multiple options
     *
     * @param context
     * @param text
     * @param statement
     * @param parentView
     * @param aExpectedAnswerListBeans
     * @param onClickQuestionOk
     */
    public static void assistantTextwithMultiple(Context context, String text, String statement, String answer, ViewGroup parentView, List<ExpectedAnswerListBean> aExpectedAnswerListBeans, View.OnClickListener onClickQuestionOk) {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset(context, GenericConstant.BLANK_PROPERTIES_JSON));
            Gson gson = new Gson();

            PropertiesBean propertiesBean1 = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBean1.setPaddingLeft(20);
            propertiesBean1.setPaddingRight(10);
            propertiesBean1.setPaddingBottom(10);
            propertiesBean1.setPaddingTop(7);
            propertiesBean1.setMarginTop(10);
            propertiesBean1.setBackGround(ViewPropertiesConstant.BackgroundAssistant);
            propertiesBean1.setLayoutGravity(ViewPropertiesConstant.Start);
            propertiesBean1.setOrientation(ViewPropertiesConstant.Vertical);
            CustomLinearLayout customLinearLayoutVertical = new CustomLinearLayout(context);
            customLinearLayoutVertical.SetLinearLayout(context, propertiesBean1);
            //customLinearLayoutVertical.setElevation(1);
            parentView.addView(customLinearLayoutVertical);

            PropertiesBean propertiesBean = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBean.setText(FieldsNameConstant.Text_Assistant);
            propertiesBean.setTextSize(12);
            propertiesBean.setTextColor(ViewPropertiesConstant.Color_Primary);
            propertiesBean.setGravity(ViewPropertiesConstant.Center);
            propertiesBean.setLayoutGravity(ViewPropertiesConstant.Start);

            LayoutFieldHelper layoutFieldHelper = new LayoutFieldHelper();
            layoutFieldHelper.setViewType(ViewPropertiesConstant.textView);
            layoutFieldHelper.setParentView(customLinearLayoutVertical);
            layoutFieldHelper.setPropertiesBean(propertiesBean);

            CreateLayout.createLayoutFields(context, layoutFieldHelper);

            PropertiesBean propertiesBeanT = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBeanT.setText(text);
            propertiesBeanT.setTextSize(15);
            propertiesBeanT.setTextColor(ViewPropertiesConstant.Color_Gray);

            LayoutFieldHelper layoutFieldHelper2 = new LayoutFieldHelper();
            layoutFieldHelper2.setViewType(ViewPropertiesConstant.textView);
            layoutFieldHelper2.setParentView(customLinearLayoutVertical);
            layoutFieldHelper2.setPropertiesBean(propertiesBeanT);

            CreateLayout.createLayoutFields(context, layoutFieldHelper2);

            PropertiesBean propertiesBeanStatmnt = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBeanStatmnt.setText(statement);
            propertiesBeanStatmnt.setTextSize(12);
            propertiesBeanStatmnt.setTextColor(ViewPropertiesConstant.Color_Gray_Light);

            LayoutFieldHelper layoutFieldHelperStmnt = new LayoutFieldHelper();
            layoutFieldHelperStmnt.setViewType(ViewPropertiesConstant.textView);
            layoutFieldHelperStmnt.setParentView(customLinearLayoutVertical);
            layoutFieldHelperStmnt.setPropertiesBean(propertiesBeanStatmnt);

            CreateLayout.createLayoutFields(context, layoutFieldHelperStmnt);

            if (aExpectedAnswerListBeans != null) {

                PropertiesBean propertiesBeanLinear = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
                }.getType());
                propertiesBeanLinear.setLayoutGravity(ViewPropertiesConstant.Start);
                propertiesBeanLinear.setOrientation(ViewPropertiesConstant.Vertical);
                CustomLinearLayout customLinearVertical = new CustomLinearLayout(context);
                customLinearVertical.SetLinearLayout(context, propertiesBeanLinear);
                customLinearLayoutVertical.addView(customLinearVertical);

                String CSV = aExpectedAnswerListBeans.get(0).getAnswer();

                String[] values = CSV.split(",");

                ProcessCreationActivity.checkboxList = new ArrayList<>();

                for (int i = 0; i < values.length; i++) {
                    PropertiesBean propertiesBeanC = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
                    }.getType());
                    //propertiesBeanC.setId(aExpectedAnswerListBeans.get(i).getButton_Id());
                    propertiesBeanC.setHeight(45);
                    propertiesBeanC.setMarginTop(10);
                    propertiesBeanC.setCheckColor(ViewPropertiesConstant.Color_Blue);
                    propertiesBeanC.setUnCheckColor(ViewPropertiesConstant.Color_Blue);
                    propertiesBeanC.setText(values[i]);
                    propertiesBeanC.setTextSize(15);
                    propertiesBeanC.setTextColor(ViewPropertiesConstant.Color_Gray);

                    CustomCheckBox customCheckBox = new CustomCheckBox(context);
                    customCheckBox.SetCheckBox(context, propertiesBeanC);
                    if(ProcessCreationActivity.isClickedQuestionOk) {
                        customCheckBox.setEnabled(true);
                    } else {
                        customCheckBox.setEnabled(false);
                    }
                    customLinearVertical.addView(customCheckBox);
                    ProcessCreationActivity.checkboxList.add(customCheckBox);

                    if(answer != null && !answer.equalsIgnoreCase("")) {
                        String[] answerValues = answer.split(",");
                        for (int j = 0; j < answerValues.length; j++) {
                            if(customCheckBox.getText().toString().equalsIgnoreCase(answerValues[j])) {
                                customCheckBox.setChecked(true);
                            }
                        }
                    }
                }

                PropertiesBean propertiesBeanOK = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
                }.getType());
                propertiesBeanOK.setId(aExpectedAnswerListBeans.get(0).getButton_Id());
                propertiesBeanOK.setWidth(60);
                propertiesBeanOK.setHeight(45);
                propertiesBeanOK.setMarginTop(10);
                propertiesBeanOK.setMarginRight(16);
                propertiesBeanOK.setPaddingBottom(1);
                propertiesBeanOK.setText(FieldsNameConstant.Text_Ok);
                propertiesBeanOK.setTextSize(15);
                propertiesBeanOK.setTextColor(ViewPropertiesConstant.Color_White);
                propertiesBeanOK.setBackGround(ViewPropertiesConstant.BackgroundGreen);
                propertiesBeanOK.setGravity(ViewPropertiesConstant.Center);
                propertiesBeanOK.setLayoutGravity(ViewPropertiesConstant.End);
                propertiesBeanOK.setOnClickListener(onClickQuestionOk);

                LayoutFieldHelper layoutFieldHelper4 = new LayoutFieldHelper();
                layoutFieldHelper4.setViewType(ViewPropertiesConstant.button);
                layoutFieldHelper4.setParentView(customLinearLayoutVertical);
                layoutFieldHelper4.setPropertiesBean(propertiesBeanOK);

                CreateLayout.createLayoutFields(context, layoutFieldHelper4);
            }
        } catch (JSONException e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateDynamicView.class, "assistantTextMultiple");
        }
    }

    /**
     * Call the assistant tab
     *
     * @param context
     * @param parentView
     * @param aPageSection_detailListBean
     * @param onClickTab
     */
    public static void assistantTabs(Context context, ViewGroup parentView, List<PageSection_detailListBean> aPageSection_detailListBean, View.OnClickListener onClickTab) {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset(context, GenericConstant.BLANK_PROPERTIES_JSON));
            Gson gson = new Gson();

            for (int i = 0; i < aPageSection_detailListBean.size(); i++) {

                PropertiesBean propertiesBean = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
                }.getType());
                propertiesBean.setId(aPageSection_detailListBean.get(i).getField_Id());
                propertiesBean.setWidth(-1);
                propertiesBean.setHeight(100);
                propertiesBean.setPaddingLeft(16);
                propertiesBean.setPaddingRight(16);
                propertiesBean.setPaddingBottom(16);
                propertiesBean.setPaddingTop(16);
                propertiesBean.setMarginTop(8);
                propertiesBean.setMarginBottom(8);
                propertiesBean.setRadius(8);
                propertiesBean.setElevation(2);
                propertiesBean.setBackGroundColor(ViewPropertiesConstant.Color_White);
                CustomCardView customCardView = new CustomCardView(context);
                customCardView.SetCardView(context, propertiesBean);
                customCardView.setOnClickListener(onClickTab);
                parentView.addView(customCardView);

                PropertiesBean propertiesBeanR = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
                }.getType());
                propertiesBeanR.setWidth(-1);
                propertiesBeanR.setHeight(-1);
                CustomRelativeLayout customRelativeLayout = new CustomRelativeLayout(context);
                customRelativeLayout.SetRelativeLayout(context, propertiesBeanR);
                customCardView.addView(customRelativeLayout);

                PropertiesBean propertiesBeanT = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
                }.getType());
                propertiesBeanT.setTextColor(ViewPropertiesConstant.Color_Gray);
                propertiesBeanT.setTextSize(15);
                propertiesBeanT.setText(aPageSection_detailListBean.get(i).getField_Name());
                CustomTextView customTextView = new CustomTextView(context);
                customTextView.SetTextView(context, propertiesBeanT);

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                customTextView.setLayoutParams(layoutParams);
                customRelativeLayout.addView(customTextView);

                PropertiesBean propertiesBeanTC = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
                }.getType());
                propertiesBeanTC.setTextColor(ViewPropertiesConstant.Color_White);
                propertiesBeanTC.setTextSize(10);
                propertiesBeanTC.setPaddingLeft(2);
                propertiesBeanTC.setPaddingRight(2);
                propertiesBeanTC.setPaddingBottom(2);
                propertiesBeanTC.setPaddingTop(2);
                propertiesBeanTC.setText("5");
                propertiesBeanTC.setGravity(ViewPropertiesConstant.Center);
                propertiesBeanTC.setBackGround(ViewPropertiesConstant.BackgroundRed);
                CustomTextView customTextViewCount = new CustomTextView(context);
                customTextViewCount.SetTextView(context, propertiesBeanTC);

                RelativeLayout.LayoutParams layoutParamsTC = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParamsTC.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                customTextViewCount.setLayoutParams(layoutParamsTC);
                CustomCommonProperties customCommonProperties = new CustomCommonProperties();
                customCommonProperties.setWidth(context, customTextViewCount, 30);
                customCommonProperties.setHeight(context, customTextViewCount, 30);

                customRelativeLayout.addView(customTextViewCount);
            }
        } catch (JSONException e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateDynamicView.class, "assistantText");
        }
    }

    /**
     * Call the User text
     *
     * @param context
     * @param textHead
     * @param textValue
     * @param parentView
     */
    public static void userText(Context context, String textHead, String textValue, ViewGroup parentView) {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset(context, GenericConstant.BLANK_PROPERTIES_JSON));
            Gson gson = new Gson();
            //Horizontal Layout
            PropertiesBean propertiesBeanL = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBeanL.setMarginTop(5);
            propertiesBeanL.setGravity(ViewPropertiesConstant.CenterVertical);
            propertiesBeanL.setLayoutGravity(ViewPropertiesConstant.Start);
            propertiesBeanL.setOrientation(ViewPropertiesConstant.Horizontal);
            CustomLinearLayout customLinearLayout = new CustomLinearLayout(context);
            customLinearLayout.SetLinearLayout(context, propertiesBeanL);
            parentView.addView(customLinearLayout);

            if (!TextUtils.isEmpty(textHead)) {

                //Heading Textview
                PropertiesBean propertiesBean = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
                }.getType());
                propertiesBean.setText(textHead);
                propertiesBean.setMarginRight(10);
                propertiesBean.setTextSize(12);
                propertiesBean.setTextStyle(ViewPropertiesConstant.Bold);
                propertiesBean.setTextColor(ViewPropertiesConstant.Color_Gray);
                propertiesBean.setLayoutGravity(ViewPropertiesConstant.Start);

                LayoutFieldHelper layoutFieldHelper1 = new LayoutFieldHelper();
                layoutFieldHelper1.setViewType(ViewPropertiesConstant.textView);
                layoutFieldHelper1.setParentView(customLinearLayout);
                layoutFieldHelper1.setPropertiesBean(propertiesBean);

                CreateLayout.createLayoutFields(context, layoutFieldHelper1);

                //Colan TextView
                PropertiesBean propertiesBeanC = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
                }.getType());
                propertiesBeanC.setText(":");
                propertiesBeanC.setMarginRight(10);
                propertiesBeanC.setTextSize(12);
                propertiesBeanC.setTextStyle(ViewPropertiesConstant.Bold);
                propertiesBeanC.setTextColor(ViewPropertiesConstant.Color_Gray);
                propertiesBeanC.setLayoutGravity(ViewPropertiesConstant.Start);

                LayoutFieldHelper layoutFieldHelper2 = new LayoutFieldHelper();
                layoutFieldHelper2.setViewType(ViewPropertiesConstant.textView);
                layoutFieldHelper2.setParentView(customLinearLayout);
                layoutFieldHelper2.setPropertiesBean(propertiesBeanC);

                CreateLayout.createLayoutFields(context, layoutFieldHelper2);

            }

            //Value TextView
            PropertiesBean propertiesBean1 = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBean1.setText(textValue);
            propertiesBean1.setTextSize(15);
            propertiesBean1.setTextColor(ViewPropertiesConstant.Color_Gray);
            propertiesBean1.setLayoutGravity(ViewPropertiesConstant.Start);

            LayoutFieldHelper layoutFieldHelper1 = new LayoutFieldHelper();
            layoutFieldHelper1.setViewType(ViewPropertiesConstant.textView);
            layoutFieldHelper1.setParentView(customLinearLayout);
            layoutFieldHelper1.setPropertiesBean(propertiesBean1);

            CreateLayout.createLayoutFields(context, layoutFieldHelper1);

        } catch (JSONException e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateDynamicView.class, "userText");
        }
    }

    /**
     * Call the JSON list
     *
     * @param context
     * @param textHead
     * @param textValue
     * @param parentView
     */
    public static void subJsonList(Context context, String textHead, String textValue, ViewGroup parentView) {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset(context, GenericConstant.BLANK_PROPERTIES_JSON));
            Gson gson = new Gson();
            //Horizontal Layout
            PropertiesBean propertiesBeanL = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBeanL.setWidth(-1);
            propertiesBeanL.setMarginTop(5);
            propertiesBeanL.setGravity(ViewPropertiesConstant.CenterVertical);
            propertiesBeanL.setLayoutGravity(ViewPropertiesConstant.Start);
            propertiesBeanL.setOrientation(ViewPropertiesConstant.Horizontal);
            CustomLinearLayout customLinearLayout = new CustomLinearLayout(context);
            customLinearLayout.SetLinearLayout(context, propertiesBeanL);
            parentView.addView(customLinearLayout);

            if (!TextUtils.isEmpty(textHead)) {

                //Heading Textview
                PropertiesBean propertiesBean = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
                }.getType());
                propertiesBean.setText(textHead);
                propertiesBean.setMarginRight(10);
                propertiesBean.setTextSize(12);
                propertiesBean.setTextStyle(ViewPropertiesConstant.Bold);
                propertiesBean.setTextColor(ViewPropertiesConstant.Color_Gray);
                CustomTextView customTextViewHead = new CustomTextView(context);
                customTextViewHead.SetTextView(context, propertiesBean);
                TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
                customTextViewHead.setLayoutParams(params);
                customLinearLayout.addView(customTextViewHead);

                //Colan TextView
                PropertiesBean propertiesBeanC = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
                }.getType());
                propertiesBeanC.setText(":");
                propertiesBeanC.setMarginRight(10);
                propertiesBeanC.setTextSize(12);
                propertiesBeanC.setTextStyle(ViewPropertiesConstant.Bold);
                propertiesBeanC.setTextColor(ViewPropertiesConstant.Color_Gray);

                CustomTextView customTextViewC = new CustomTextView(context);
                customTextViewC.SetTextView(context, propertiesBeanC);
                customLinearLayout.addView(customTextViewC);

            }

            //Value TextView
            PropertiesBean propertiesBean1 = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBean1.setText(textValue);
            propertiesBean1.setTextSize(15);
            propertiesBean1.setTextColor(ViewPropertiesConstant.Color_Gray);

            CustomTextView customTextViewValue = new CustomTextView(context);
            customTextViewValue.SetTextView(context, propertiesBean1);
            TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
            customTextViewValue.setLayoutParams(params);
            customLinearLayout.addView(customTextViewValue);

        } catch (JSONException e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateDynamicView.class, "userText");
        }
    }

    /**
     * Call the method to create tab
     *
     * @param context
     * @param id
     * @param textValue
     * @param parentView
     * @param onClickTab
     */
    public static void createTab(Context context, int id, String textValue, ViewGroup parentView, View.OnClickListener onClickTab) {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset(context, GenericConstant.BLANK_PROPERTIES_JSON));
            Gson gson = new Gson();

            PropertiesBean propertiesBean1 = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBean1.setId(id);
            propertiesBean1.setOrientation(ViewPropertiesConstant.Vertical);
            propertiesBean1.setGravity(ViewPropertiesConstant.Center);
            CustomLinearLayout customLinearLayoutVertical = new CustomLinearLayout(context);
            customLinearLayoutVertical.SetLinearLayout(context, propertiesBean1);
            customLinearLayoutVertical.setOnClickListener(onClickTab);
            parentView.addView(customLinearLayoutVertical);

            PropertiesBean propertiesBeanI = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBeanI.setWidth(-1);

            CustomImageView customImageView = new CustomImageView(context);
            customImageView.SetImageView(context, propertiesBeanI);
            customImageView.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_line));
            customImageView.setImageResource(R.drawable.ic_dot_gray);
            customLinearLayoutVertical.addView(customImageView);

            PropertiesBean propertiesBean = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBean.setPaddingLeft(10);
            propertiesBean.setPaddingRight(10);
            propertiesBean.setMinWidth(70);
            propertiesBean.setGravity(ViewPropertiesConstant.Center);
            propertiesBean.setTextColor(ViewPropertiesConstant.Color_Gray_Light);
            propertiesBean.setText(textValue);
            propertiesBean.setTextSize(12);
            propertiesBean.setTextStyle(ViewPropertiesConstant.Bold);

            CustomTextView customTextView = new CustomTextView(context);
            customTextView.SetTextView(context, propertiesBean);
            customLinearLayoutVertical.addView(customTextView);

        } catch (JSONException e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateDynamicView.class, "createTab");
        }
    }

    /**
     * Call the method to create process layout
     *
     * @param context
     * @param id
     * @param parentView
     */
    public static void createProcessLayout(Context context, int id, ViewGroup parentView) {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset(context, GenericConstant.BLANK_PROPERTIES_JSON));
            Gson gson = new Gson();
            //Horizontal Layout
            PropertiesBean propertiesBeanL = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBeanL.setHeight(-1);
            propertiesBeanL.setWidth(-1);
            propertiesBeanL.setId(id);
            propertiesBeanL.setVisibility(ViewPropertiesConstant.Gone);
            propertiesBeanL.setOrientation(ViewPropertiesConstant.Vertical);

            LayoutFieldHelper layoutFieldHelper1 = new LayoutFieldHelper();
            layoutFieldHelper1.setViewType(ViewPropertiesConstant.linearLayout);
            layoutFieldHelper1.setParentView(parentView);
            layoutFieldHelper1.setPropertiesBean(propertiesBeanL);

            CreateLayout.createLayoutFields(context, layoutFieldHelper1);

        } catch (JSONException e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateDynamicView.class, "createProcessLayout");
        }
    }


    /**
     * Call the method to get User image list
     *
     * @param context
     * @param text
     * @param parentView
     * @param imageList
     * @param onClickItem
     * @param onLongClick
     */
    public static void userImageList(ProcessCreationActivity context, String text, ViewGroup parentView, ArrayList<PhotoListModel> imageList,
                                     OnItemClickListener.OnItemClickCallback onClickItem,
                                     OnItemLongClickListener.OnItemLongClickCallback onLongClick) {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset(context, GenericConstant.BLANK_PROPERTIES_JSON));
            Gson gson = new Gson();

            PropertiesBean propertiesBean1 = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBean1.setPaddingLeft(5);
            propertiesBean1.setPaddingRight(10);
            propertiesBean1.setPaddingTop(10);
            propertiesBean1.setPaddingBottom(5);
            propertiesBean1.setMarginTop(10);
            propertiesBean1.setBackGround(ViewPropertiesConstant.BackgroundUser);
            propertiesBean1.setLayoutGravity(ViewPropertiesConstant.End);
            propertiesBean1.setOrientation(ViewPropertiesConstant.Vertical);
            CustomLinearLayout customLinearLayoutVertical = new CustomLinearLayout(context);
            customLinearLayoutVertical.SetLinearLayout(context, propertiesBean1);
            parentView.addView(customLinearLayoutVertical);

            PropertiesBean propertiesBean = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBean.setPaddingLeft(5);
            propertiesBean.setText(text);
            propertiesBean.setTextSize(12);
            propertiesBean.setTextColor(ViewPropertiesConstant.Color_Yellow);
            propertiesBean.setLayoutGravity(ViewPropertiesConstant.Start);

            LayoutFieldHelper layoutFieldHelper1 = new LayoutFieldHelper();
            layoutFieldHelper1.setViewType(ViewPropertiesConstant.textView);
            layoutFieldHelper1.setParentView(customLinearLayoutVertical);
            layoutFieldHelper1.setPropertiesBean(propertiesBean);

            CreateLayout.createLayoutFields(context, layoutFieldHelper1);

            PropertiesBean recyclerDTO = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            //recyclerDTO.setWidth(-1);
            recyclerDTO.setPaddingLeft(5);
            recyclerDTO.setPaddingRight(5);
            recyclerDTO.setPaddingTop(5);
            recyclerDTO.setPaddingBottom(5);
            recyclerDTO.setOrientation(ViewPropertiesConstant.Horizontal);

            CustomRecyclerView customRecyclerView = new CustomRecyclerView(context);
            customRecyclerView.SetRecyclerView(context, recyclerDTO);
            customRecyclerView.setId(R.id.rv_image);
            customLinearLayoutVertical.addView(customRecyclerView);

            if (imageList != null && imageList.size() > 0) {
                UserImageListAdaptor userImageListAdaptor = new UserImageListAdaptor(context, imageList, onClickItem, onLongClick, ProcessCreationActivity.directoryImage);
                customRecyclerView.setAdapter(userImageListAdaptor);
            }
        } catch (JSONException e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateDynamicView.class, "userImageList");
        }
    }

    /**
     * Call method to get the Signature list
     *
     * @param context
     * @param text
     * @param parentView
     * @param imageList
     * @param onClickItem
     * @param onLongClick
     */
    public static void userSignatureList(ProcessCreationActivity context, String text, ViewGroup parentView, ArrayList<PhotoListModel> imageList,
                                         OnItemClickListener.OnItemClickCallback onClickItem,
                                         OnItemLongClickListener.OnItemLongClickCallback onLongClick) {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset(context, GenericConstant.BLANK_PROPERTIES_JSON));
            Gson gson = new Gson();

            PropertiesBean propertiesBean1 = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            //propertiesBean1.setWidth(-1);
            propertiesBean1.setPaddingLeft(5);
            propertiesBean1.setPaddingRight(10);
            propertiesBean1.setPaddingTop(10);
            propertiesBean1.setPaddingBottom(5);
            propertiesBean1.setMarginTop(10);
            propertiesBean1.setBackGround(ViewPropertiesConstant.BackgroundUser);
            propertiesBean1.setLayoutGravity(ViewPropertiesConstant.End);
            propertiesBean1.setOrientation(ViewPropertiesConstant.Vertical);
            CustomLinearLayout customLinearLayoutVertical = new CustomLinearLayout(context);
            customLinearLayoutVertical.SetLinearLayout(context, propertiesBean1);
            parentView.addView(customLinearLayoutVertical);

            PropertiesBean propertiesBean = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBean.setPaddingLeft(5);
            propertiesBean.setText(text);
            propertiesBean.setTextSize(12);
            propertiesBean.setTextColor(ViewPropertiesConstant.Color_Yellow);
            propertiesBean.setLayoutGravity(ViewPropertiesConstant.Start);

            LayoutFieldHelper layoutFieldHelper1 = new LayoutFieldHelper();
            layoutFieldHelper1.setViewType(ViewPropertiesConstant.textView);
            layoutFieldHelper1.setParentView(customLinearLayoutVertical);
            layoutFieldHelper1.setPropertiesBean(propertiesBean);

            CreateLayout.createLayoutFields(context, layoutFieldHelper1);

            PropertiesBean recyclerDTO = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            //recyclerDTO.setWidth(-1);
            recyclerDTO.setPaddingLeft(5);
            recyclerDTO.setPaddingRight(5);
            recyclerDTO.setPaddingTop(5);
            recyclerDTO.setPaddingBottom(5);
            recyclerDTO.setOrientation(ViewPropertiesConstant.Horizontal);

            CustomRecyclerView customRecyclerView = new CustomRecyclerView(context);
            customRecyclerView.SetRecyclerView(context, recyclerDTO);
            customRecyclerView.setId(R.id.rv_signature);
            customLinearLayoutVertical.addView(customRecyclerView);

            if (imageList != null && imageList.size() > 0) {
                UserImageListAdaptor userImageListAdaptor = new UserImageListAdaptor(context, imageList, onClickItem, onLongClick, ProcessCreationActivity.directoryImage);
                customRecyclerView.setAdapter(userImageListAdaptor);
            }
        } catch (JSONException e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateDynamicView.class, "signatureList");
        }
    }

    /**
     * Call method to get the Signature list
     *
     * @param context
     * @param text
     * @param parentView
     * @param imageList
     * @param onClickItem
     * @param onLongClick
     */
    public static void userSketchList(ProcessCreationActivity context, String text, ViewGroup parentView, ArrayList<PhotoListModel> imageList,
                                         OnItemClickListener.OnItemClickCallback onClickItem,
                                         OnItemLongClickListener.OnItemLongClickCallback onLongClick) {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset(context, GenericConstant.BLANK_PROPERTIES_JSON));
            Gson gson = new Gson();

            PropertiesBean propertiesBean1 = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            //propertiesBean1.setWidth(-1);
            propertiesBean1.setPaddingLeft(5);
            propertiesBean1.setPaddingRight(10);
            propertiesBean1.setPaddingTop(10);
            propertiesBean1.setPaddingBottom(5);
            propertiesBean1.setMarginTop(10);
            propertiesBean1.setBackGround(ViewPropertiesConstant.BackgroundUser);
            propertiesBean1.setLayoutGravity(ViewPropertiesConstant.End);
            propertiesBean1.setOrientation(ViewPropertiesConstant.Vertical);
            CustomLinearLayout customLinearLayoutVertical = new CustomLinearLayout(context);
            customLinearLayoutVertical.SetLinearLayout(context, propertiesBean1);
            parentView.addView(customLinearLayoutVertical);

            PropertiesBean propertiesBean = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBean.setPaddingLeft(5);
            propertiesBean.setText(text);
            propertiesBean.setTextSize(12);
            propertiesBean.setTextColor(ViewPropertiesConstant.Color_Yellow);
            propertiesBean.setLayoutGravity(ViewPropertiesConstant.Start);

            LayoutFieldHelper layoutFieldHelper1 = new LayoutFieldHelper();
            layoutFieldHelper1.setViewType(ViewPropertiesConstant.textView);
            layoutFieldHelper1.setParentView(customLinearLayoutVertical);
            layoutFieldHelper1.setPropertiesBean(propertiesBean);

            CreateLayout.createLayoutFields(context, layoutFieldHelper1);

            PropertiesBean recyclerDTO = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            //recyclerDTO.setWidth(-1);
            recyclerDTO.setPaddingLeft(5);
            recyclerDTO.setPaddingRight(5);
            recyclerDTO.setPaddingTop(5);
            recyclerDTO.setPaddingBottom(5);
            recyclerDTO.setOrientation(ViewPropertiesConstant.Horizontal);

            CustomRecyclerView customRecyclerView = new CustomRecyclerView(context);
            customRecyclerView.SetRecyclerView(context, recyclerDTO);
            customRecyclerView.setId(R.id.rv_sketch);
            customLinearLayoutVertical.addView(customRecyclerView);

            if (imageList != null && imageList.size() > 0) {
                UserImageListAdaptor userImageListAdaptor = new UserImageListAdaptor(context, imageList, onClickItem, onLongClick, ProcessCreationActivity.directoryImage);
                customRecyclerView.setAdapter(userImageListAdaptor);
            }
        } catch (JSONException e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateDynamicView.class, "sketch");
        }
    }

    /**
     * Call the method for user doc list
     *
     * @param context
     * @param text
     * @param parentView
     * @param imageList
     * @param onClickItem
     * @param onLongClick
     */
    public static void userDocsList(ProcessCreationActivity context, String text, ViewGroup parentView, ArrayList<PhotoListModel> imageList,
                                    OnItemClickListener.OnItemClickCallback onClickItem,
                                    OnItemLongClickListener.OnItemLongClickCallback onLongClick) {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset(context, GenericConstant.BLANK_PROPERTIES_JSON));
            Gson gson = new Gson();

            PropertiesBean propertiesBean1 = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBean1.setPaddingLeft(5);
            propertiesBean1.setPaddingRight(10);
            propertiesBean1.setPaddingTop(10);
            propertiesBean1.setPaddingBottom(5);
            propertiesBean1.setMarginTop(10);
            propertiesBean1.setBackGround(ViewPropertiesConstant.BackgroundUser);
            propertiesBean1.setLayoutGravity(ViewPropertiesConstant.End);
            propertiesBean1.setOrientation(ViewPropertiesConstant.Vertical);
            CustomLinearLayout customLinearLayoutVertical = new CustomLinearLayout(context);
            customLinearLayoutVertical.SetLinearLayout(context, propertiesBean1);
            parentView.addView(customLinearLayoutVertical);

            PropertiesBean propertiesBean = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBean.setPaddingLeft(5);
            propertiesBean.setText(text);
            propertiesBean.setTextSize(12);
            propertiesBean.setTextColor(ViewPropertiesConstant.Color_Yellow);
            propertiesBean.setLayoutGravity(ViewPropertiesConstant.Start);

            LayoutFieldHelper layoutFieldHelper1 = new LayoutFieldHelper();
            layoutFieldHelper1.setViewType(ViewPropertiesConstant.textView);
            layoutFieldHelper1.setParentView(customLinearLayoutVertical);
            layoutFieldHelper1.setPropertiesBean(propertiesBean);

            CreateLayout.createLayoutFields(context, layoutFieldHelper1);

            PropertiesBean recyclerDTO = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            //recyclerDTO.setWidth(-1);
            recyclerDTO.setPaddingLeft(5);
            recyclerDTO.setPaddingRight(5);
            recyclerDTO.setPaddingTop(5);
            recyclerDTO.setPaddingBottom(5);
            recyclerDTO.setOrientation(ViewPropertiesConstant.Horizontal);

            CustomRecyclerView customRecyclerView = new CustomRecyclerView(context);
            customRecyclerView.SetRecyclerView(context, recyclerDTO);
            customRecyclerView.setId(R.id.rv_attachment);
            customLinearLayoutVertical.addView(customRecyclerView);

            if (imageList != null && imageList.size() > 0) {
                UserDocListAdaptor userDocListAdaptor = new UserDocListAdaptor(context, imageList, onClickItem, onLongClick);
                customRecyclerView.setAdapter(userDocListAdaptor);
            }
        } catch (JSONException e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateDynamicView.class, "docList");
        }
    }

    /**
     * Call the method for user doc list
     *
     * @param context
     * @param text
     * @param parentView
     * @param audioList
     * @param onClickItem
     * @param onLongClick
     */
    public static void userAudioList(ProcessCreationActivity context, String text, ViewGroup parentView, ArrayList<PhotoListModel> audioList,
                                    OnItemClickListener.OnItemClickCallback onClickItem,
                                    OnItemLongClickListener.OnItemLongClickCallback onLongClick) {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset(context, GenericConstant.BLANK_PROPERTIES_JSON));
            Gson gson = new Gson();

            PropertiesBean propertiesBean1 = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBean1.setPaddingLeft(5);
            propertiesBean1.setPaddingRight(10);
            propertiesBean1.setPaddingTop(10);
            propertiesBean1.setPaddingBottom(5);
            propertiesBean1.setMarginTop(10);
            propertiesBean1.setBackGround(ViewPropertiesConstant.BackgroundUser);
            propertiesBean1.setLayoutGravity(ViewPropertiesConstant.End);
            propertiesBean1.setOrientation(ViewPropertiesConstant.Vertical);
            CustomLinearLayout customLinearLayoutVertical = new CustomLinearLayout(context);
            customLinearLayoutVertical.SetLinearLayout(context, propertiesBean1);
            parentView.addView(customLinearLayoutVertical);

            PropertiesBean propertiesBean = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBean.setPaddingLeft(5);
            propertiesBean.setText(text);
            propertiesBean.setTextSize(12);
            propertiesBean.setTextColor(ViewPropertiesConstant.Color_Yellow);
            propertiesBean.setLayoutGravity(ViewPropertiesConstant.Start);

            LayoutFieldHelper layoutFieldHelper1 = new LayoutFieldHelper();
            layoutFieldHelper1.setViewType(ViewPropertiesConstant.textView);
            layoutFieldHelper1.setParentView(customLinearLayoutVertical);
            layoutFieldHelper1.setPropertiesBean(propertiesBean);

            CreateLayout.createLayoutFields(context, layoutFieldHelper1);

            PropertiesBean recyclerDTO = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            //recyclerDTO.setWidth(-1);
            recyclerDTO.setPaddingLeft(5);
            recyclerDTO.setPaddingRight(5);
            recyclerDTO.setPaddingTop(5);
            recyclerDTO.setPaddingBottom(5);
            recyclerDTO.setOrientation(ViewPropertiesConstant.Horizontal);

            CustomRecyclerView customRecyclerView = new CustomRecyclerView(context);
            customRecyclerView.SetRecyclerView(context, recyclerDTO);
            customRecyclerView.setId(R.id.rv_audio);
            customLinearLayoutVertical.addView(customRecyclerView);

            if (audioList != null && audioList.size() > 0) {
                UserAudioListAdaptor userAudioListAdaptor = new UserAudioListAdaptor(context, audioList, onClickItem, onLongClick);
                customRecyclerView.setAdapter(userAudioListAdaptor);
            }
        } catch (JSONException e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateDynamicView.class, "audioList");
        }
    }

    /**
     * Call the method for user doc list
     *
     * @param context
     * @param text
     * @param parentView
     * @param pocketbook
     * @param entryType
     * @param onLongClick
     */
    public static void userPocketbookEntry(ProcessCreationActivity context, String text, ViewGroup parentView, String pocketbook,
                                     String entryType,
                                     View.OnLongClickListener onLongClick) {

        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset(context, GenericConstant.BLANK_PROPERTIES_JSON));
            Gson gson = new Gson();
            //Horizontal Layout

            PropertiesBean propertiesBean1 = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBean1.setPaddingLeft(5);
            propertiesBean1.setPaddingRight(10);
            propertiesBean1.setPaddingTop(10);
            propertiesBean1.setPaddingBottom(5);
            propertiesBean1.setMarginTop(10);
            propertiesBean1.setBackGround(ViewPropertiesConstant.BackgroundUser);
            propertiesBean1.setLayoutGravity(ViewPropertiesConstant.End);
            propertiesBean1.setOrientation(ViewPropertiesConstant.Vertical);
            CustomLinearLayout customLinearLayoutVertical = new CustomLinearLayout(context);
            customLinearLayoutVertical.SetLinearLayout(context, propertiesBean1);
            customLinearLayoutVertical.setOnLongClickListener(onLongClick);
            parentView.addView(customLinearLayoutVertical);

            PropertiesBean propertiesBean = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBean.setPaddingLeft(5);
            propertiesBean.setText(text);
            propertiesBean.setTextSize(12);
            propertiesBean.setTextColor(ViewPropertiesConstant.Color_Yellow);
            propertiesBean.setLayoutGravity(ViewPropertiesConstant.Start);

            LayoutFieldHelper layoutFieldHelper1 = new LayoutFieldHelper();
            layoutFieldHelper1.setViewType(ViewPropertiesConstant.textView);
            layoutFieldHelper1.setParentView(customLinearLayoutVertical);
            layoutFieldHelper1.setPropertiesBean(propertiesBean);

            CreateLayout.createLayoutFields(context, layoutFieldHelper1);

            PropertiesBean propertiesBeanL = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBeanL.setPaddingLeft(5);
            propertiesBeanL.setPaddingRight(10);
            propertiesBeanL.setMarginTop(5);
            propertiesBeanL.setGravity(ViewPropertiesConstant.CenterVertical);
            propertiesBeanL.setLayoutGravity(ViewPropertiesConstant.Start);
            propertiesBeanL.setOrientation(ViewPropertiesConstant.Horizontal);
            CustomLinearLayout customLinearLayout = new CustomLinearLayout(context);
            customLinearLayout.SetLinearLayout(context, propertiesBeanL);
            customLinearLayoutVertical.addView(customLinearLayout);

            //Heading Textview
            PropertiesBean propertiesBeanTH = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBeanTH.setText(FieldsNameConstant.Pocketbook);
            propertiesBeanTH.setMarginRight(10);
            propertiesBeanTH.setTextSize(12);
            propertiesBeanTH.setTextStyle(ViewPropertiesConstant.Bold);
            propertiesBeanTH.setTextColor(ViewPropertiesConstant.Color_Gray);
            propertiesBeanTH.setLayoutGravity(ViewPropertiesConstant.Start);

            LayoutFieldHelper layoutFieldHelperTH = new LayoutFieldHelper();
            layoutFieldHelperTH.setViewType(ViewPropertiesConstant.textView);
            layoutFieldHelperTH.setParentView(customLinearLayout);
            layoutFieldHelperTH.setPropertiesBean(propertiesBeanTH);

            CreateLayout.createLayoutFields(context, layoutFieldHelperTH);

            //Colan TextView
            PropertiesBean propertiesBeanC = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBeanC.setText(":");
            propertiesBeanC.setMarginRight(10);
            propertiesBeanC.setTextSize(12);
            propertiesBeanC.setTextStyle(ViewPropertiesConstant.Bold);
            propertiesBeanC.setTextColor(ViewPropertiesConstant.Color_Gray);
            propertiesBeanC.setLayoutGravity(ViewPropertiesConstant.Start);

            LayoutFieldHelper layoutFieldHelper2 = new LayoutFieldHelper();
            layoutFieldHelper2.setViewType(ViewPropertiesConstant.textView);
            layoutFieldHelper2.setParentView(customLinearLayout);
            layoutFieldHelper2.setPropertiesBean(propertiesBeanC);

            CreateLayout.createLayoutFields(context, layoutFieldHelper2);

            //Value TextView
            PropertiesBean propertiesBeanTV = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBeanTV.setText(pocketbook);
            propertiesBeanTV.setTextSize(15);
            propertiesBeanTV.setTextColor(ViewPropertiesConstant.Color_Gray);
            propertiesBeanTV.setLayoutGravity(ViewPropertiesConstant.Start);

            CustomTextView customTextView = new CustomTextView(context);
            customTextView.SetTextView(context, propertiesBeanTV);
            customTextView.setId(R.id.tv_pocket);
            customLinearLayout.addView(customTextView);

        } catch (JSONException e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateDynamicView.class, "userText");
        }
    }

    /**
     * method for load the json from assets
     *
     * @param context
     * @param filename
     * @return
     */
    private static String loadJSONFromAsset(Context context, String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, GenericConstant.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
