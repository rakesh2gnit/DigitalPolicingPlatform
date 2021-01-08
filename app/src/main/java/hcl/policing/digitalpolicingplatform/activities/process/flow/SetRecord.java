package hcl.policing.digitalpolicingplatform.activities.process.flow;

import android.text.TextUtils;
import android.widget.LinearLayout;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.fieldName.FieldsNameConstant;
import hcl.policing.digitalpolicingplatform.constants.viewProperties.ViewPropertiesConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomLinearLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.layoutHelper.CreateDynamicView;
import hcl.policing.digitalpolicingplatform.customLibraries.layoutHelper.CreateLayout;
import hcl.policing.digitalpolicingplatform.models.camera.PhotoListModel;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.LayoutFieldHelper;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.DocsUtil;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class SetRecord {

    /**
     * Save the records which was entered in Dialog
     */
    public static void setRecords(ProcessCreationActivity act) {
        if (act.answerList != null && act.answerList.size() > 0) {
            //CustomLinearLayout cLinearLayout = act.findViewById(act.SECTION_COUNT + 1);

            String strJson = JsonUtil.getInstance().loadJsonFromAsset(act, GenericConstant.BLANK_PROPERTIES_JSON);
            PropertiesBean propertiesBeanL = (PropertiesBean) JsonUtil.getInstance().toModel(strJson, PropertiesBean.class);

            if (act.mExpectedQuestionListBean != null) {
                propertiesBeanL.setId(act.mExpectedQuestionListBean.getQuestion_Id());
            } else if (act.pageSectionId != 0) {
                propertiesBeanL.setId(act.pageSectionId);
            }
            propertiesBeanL.setPaddingLeft(10);
            propertiesBeanL.setPaddingRight(20);
            propertiesBeanL.setPaddingBottom(10);
            propertiesBeanL.setPaddingTop(7);
            propertiesBeanL.setMarginTop(10);
            propertiesBeanL.setBackGround(ViewPropertiesConstant.BackgroundUser);
            propertiesBeanL.setGravity(ViewPropertiesConstant.Center);
            propertiesBeanL.setLayoutGravity(ViewPropertiesConstant.End);
            propertiesBeanL.setOrientation(ViewPropertiesConstant.Vertical);
            CustomLinearLayout customLinearLayout = new CustomLinearLayout(act);
            customLinearLayout.SetLinearLayout(act, propertiesBeanL);
            customLinearLayout.setOnLongClickListener(SetClick.onLongClickEdit);
            act.llProcessLayout.addView(customLinearLayout);

            PropertiesBean propertiesBean = (PropertiesBean) JsonUtil.getInstance().toModel(strJson, PropertiesBean.class);
            propertiesBean.setText(act.appSession.getUserID());
            propertiesBean.setTextSize(12);
            propertiesBean.setTextColor(ViewPropertiesConstant.Color_Yellow);
            propertiesBean.setLayoutGravity(ViewPropertiesConstant.Start);

            LayoutFieldHelper layoutFieldHelper1 = new LayoutFieldHelper();
            layoutFieldHelper1.setViewType(ViewPropertiesConstant.textView);
            layoutFieldHelper1.setParentView(customLinearLayout);
            layoutFieldHelper1.setPropertiesBean(propertiesBean);

            CreateLayout.createLayoutFields(act, layoutFieldHelper1);

            for (int i = 0; i < act.answerList.size(); i++) {

                if (!act.answerList.get(i).getKey().contains(FieldsNameConstant.ID)
                        && !act.answerList.get(i).getKey().contains(FieldsNameConstant.SYSTEM)
                        && act.answerList.get(i).getValue() != null
                        && !act.answerList.get(i).getValue().equalsIgnoreCase("")) {
                    CreateDynamicView.userText(act, act.answerList.get(i).getKey(), act.answerList.get(i).getValue(), customLinearLayout);
                }
            }
        }
    }

    /**
     * Set Record of Answer Value
     *
     * @param act
     * @param asnwerValue
     */
    public static void setRecords(ProcessCreationActivity act, String asnwerValue) {

        if (!TextUtils.isEmpty(asnwerValue)) {
            //CustomLinearLayout cLinearLayout = act.findViewById(act.SECTION_COUNT + 1);

            //CreateDynamicView.userTextHeading(act, act.appSession.getUserID(), cLinearLayout);
            act.llProcessLayout.setTag(act.sectionIndex);
            String strJson = JsonUtil.getInstance().loadJsonFromAsset(act, GenericConstant.BLANK_PROPERTIES_JSON);
            PropertiesBean propertiesBeanL = (PropertiesBean) JsonUtil.getInstance().toModel(strJson, PropertiesBean.class);

            if (act.mExpectedQuestionListBean != null) {
                propertiesBeanL.setId(act.mExpectedQuestionListBean.getQuestion_Id());
            }
            propertiesBeanL.setPaddingLeft(10);
            propertiesBeanL.setPaddingRight(20);
            propertiesBeanL.setPaddingBottom(10);
            propertiesBeanL.setPaddingTop(7);
            propertiesBeanL.setMarginTop(10);
            propertiesBeanL.setBackGround(ViewPropertiesConstant.BackgroundUser);
            propertiesBeanL.setGravity(ViewPropertiesConstant.Center);
            propertiesBeanL.setLayoutGravity(ViewPropertiesConstant.End);
            propertiesBeanL.setOrientation(ViewPropertiesConstant.Vertical);
            CustomLinearLayout customLinearLayout = new CustomLinearLayout(act);
            customLinearLayout.SetLinearLayout(act, propertiesBeanL);
            customLinearLayout.setOnLongClickListener(SetClick.onLongClickEdit);
            act.llProcessLayout.addView(customLinearLayout);

            PropertiesBean propertiesBean = (PropertiesBean) JsonUtil.getInstance().toModel(strJson, PropertiesBean.class);
            propertiesBean.setText(act.appSession.getUserID());
            propertiesBean.setTextSize(12);
            propertiesBean.setTextColor(ViewPropertiesConstant.Color_Yellow);
            propertiesBean.setLayoutGravity(ViewPropertiesConstant.Start);

            LayoutFieldHelper layoutFieldHelper1 = new LayoutFieldHelper();
            layoutFieldHelper1.setViewType(ViewPropertiesConstant.textView);
            layoutFieldHelper1.setParentView(customLinearLayout);
            layoutFieldHelper1.setPropertiesBean(propertiesBean);

            CreateLayout.createLayoutFields(act, layoutFieldHelper1);

            CreateDynamicView.userText(act, "", asnwerValue, customLinearLayout);
        }
    }

    /**
     * Set the records from edit section
     *
     * @param act
     * @param cLinearLayout
     * @param id
     */
    public static void setRecordListFromEdit(ProcessCreationActivity act, LinearLayout cLinearLayout, int id) {
        if (act.answerList != null && act.answerList.size() > 0) {
            String strJson = JsonUtil.getInstance().loadJsonFromAsset(act, GenericConstant.BLANK_PROPERTIES_JSON);
            PropertiesBean propertiesBeanL = (PropertiesBean) JsonUtil.getInstance().toModel(strJson, PropertiesBean.class);

            propertiesBeanL.setId(id);
            propertiesBeanL.setPaddingLeft(10);
            propertiesBeanL.setPaddingRight(20);
            propertiesBeanL.setPaddingBottom(10);
            propertiesBeanL.setPaddingTop(7);
            propertiesBeanL.setMarginTop(10);
            propertiesBeanL.setBackGround(ViewPropertiesConstant.BackgroundUser);
            propertiesBeanL.setGravity(ViewPropertiesConstant.Center);
            propertiesBeanL.setLayoutGravity(ViewPropertiesConstant.End);
            propertiesBeanL.setOrientation(ViewPropertiesConstant.Vertical);
            CustomLinearLayout customLinearLayout = new CustomLinearLayout(act);
            customLinearLayout.SetLinearLayout(act, propertiesBeanL);
            customLinearLayout.setOnLongClickListener(SetClick.onLongClickEdit);
            cLinearLayout.addView(customLinearLayout);

            PropertiesBean propertiesBean = (PropertiesBean) JsonUtil.getInstance().toModel(strJson, PropertiesBean.class);
            propertiesBean.setText(act.appSession.getUserID());
            propertiesBean.setTextSize(12);
            propertiesBean.setTextColor(ViewPropertiesConstant.Color_Yellow);
            propertiesBean.setLayoutGravity(ViewPropertiesConstant.Start);

            LayoutFieldHelper layoutFieldHelper1 = new LayoutFieldHelper();
            layoutFieldHelper1.setViewType(ViewPropertiesConstant.textView);
            layoutFieldHelper1.setParentView(customLinearLayout);
            layoutFieldHelper1.setPropertiesBean(propertiesBean);

            CreateLayout.createLayoutFields(act, layoutFieldHelper1);

            for (int i = 0; i < act.answerList.size(); i++) {

                if (!act.answerList.get(i).getKey().contains(FieldsNameConstant.ID)
                        && !act.answerList.get(i).getKey().contains(FieldsNameConstant.SYSTEM)
                        && act.answerList.get(i).getValue() != null
                        && !act.answerList.get(i).getValue().equalsIgnoreCase("")) {
                    CreateDynamicView.userText(act, act.answerList.get(i).getKey(), act.answerList.get(i).getValue(), customLinearLayout);
                }
            }
        }
    }

    /**
     * Set the records from edit section based on below parameters
     *
     * @param act
     * @param cLinearLayout
     * @param id
     * @param index
     */
    public static void setRecordListFromEditAt(ProcessCreationActivity act, LinearLayout cLinearLayout, int id, int index) {
        if (act.answerList != null && act.answerList.size() > 0) {
            String strJson = JsonUtil.getInstance().loadJsonFromAsset(act, GenericConstant.BLANK_PROPERTIES_JSON);
            PropertiesBean propertiesBeanL = (PropertiesBean) JsonUtil.getInstance().toModel(strJson, PropertiesBean.class);

            propertiesBeanL.setId(id);
            propertiesBeanL.setPaddingLeft(10);
            propertiesBeanL.setPaddingRight(20);
            propertiesBeanL.setPaddingBottom(10);
            propertiesBeanL.setPaddingTop(7);
            propertiesBeanL.setMarginTop(10);
            propertiesBeanL.setBackGround(ViewPropertiesConstant.BackgroundUser);
            propertiesBeanL.setGravity(ViewPropertiesConstant.Center);
            propertiesBeanL.setLayoutGravity(ViewPropertiesConstant.End);
            propertiesBeanL.setOrientation(ViewPropertiesConstant.Vertical);
            CustomLinearLayout customLinearLayout = new CustomLinearLayout(act);
            customLinearLayout.SetLinearLayout(act, propertiesBeanL);
            customLinearLayout.setOnLongClickListener(SetClick.onLongClickEdit);
            cLinearLayout.addView(customLinearLayout, index);

            PropertiesBean propertiesBean = (PropertiesBean) JsonUtil.getInstance().toModel(strJson, PropertiesBean.class);
            propertiesBean.setText(act.appSession.getUserID());
            propertiesBean.setTextSize(12);
            propertiesBean.setTextColor(ViewPropertiesConstant.Color_Yellow);
            propertiesBean.setLayoutGravity(ViewPropertiesConstant.Start);

            LayoutFieldHelper layoutFieldHelper1 = new LayoutFieldHelper();
            layoutFieldHelper1.setViewType(ViewPropertiesConstant.textView);
            layoutFieldHelper1.setParentView(customLinearLayout);
            layoutFieldHelper1.setPropertiesBean(propertiesBean);

            CreateLayout.createLayoutFields(act, layoutFieldHelper1);

            for (int i = 0; i < act.answerList.size(); i++) {

                if (!act.answerList.get(i).getKey().contains(FieldsNameConstant.ID)
                        && !act.answerList.get(i).getKey().contains(FieldsNameConstant.SYSTEM)
                        && act.answerList.get(i).getValue() != null
                        && !act.answerList.get(i).getValue().equalsIgnoreCase("")) {
                    CreateDynamicView.userText(act, act.answerList.get(i).getKey(), act.answerList.get(i).getValue(), customLinearLayout);
                }
            }
        }
    }

    /**
     * Set records from edit section
     *
     * @param act
     * @param cLinear
     */
    public static void setRecordsFromEdit(ProcessCreationActivity act, CustomLinearLayout cLinear) {
        if (act.answerList != null && act.answerList.size() > 0) {
            String strJson = JsonUtil.getInstance().loadJsonFromAsset(act, GenericConstant.BLANK_PROPERTIES_JSON);

            PropertiesBean propertiesBean = (PropertiesBean) JsonUtil.getInstance().toModel(strJson, PropertiesBean.class);
            propertiesBean.setText(act.appSession.getUserID());
            propertiesBean.setTextSize(12);
            propertiesBean.setTextColor(ViewPropertiesConstant.Color_Yellow);
            propertiesBean.setLayoutGravity(ViewPropertiesConstant.Start);

            LayoutFieldHelper layoutFieldHelper1 = new LayoutFieldHelper();
            layoutFieldHelper1.setViewType(ViewPropertiesConstant.textView);
            layoutFieldHelper1.setParentView(cLinear);
            layoutFieldHelper1.setPropertiesBean(propertiesBean);

            CreateLayout.createLayoutFields(act, layoutFieldHelper1);

            for (int i = 0; i < act.answerList.size(); i++) {

                if (!act.answerList.get(i).getKey().contains(FieldsNameConstant.ID)
                        && !act.answerList.get(i).getKey().contains(FieldsNameConstant.SYSTEM)
                        && act.answerList.get(i).getValue() != null
                        && !act.answerList.get(i).getValue().equalsIgnoreCase("")) {
                    CreateDynamicView.userText(act, act.answerList.get(i).getKey(), act.answerList.get(i).getValue(), cLinear);
                }
            }
        }
    }

    /**
     * set records from edit section based on parameters
     *
     * @param act
     * @param asnwerValue
     * @param cLinear
     */
    public static void setRecordsFromEdit(ProcessCreationActivity act, String asnwerValue, CustomLinearLayout cLinear) {

        if (!TextUtils.isEmpty(asnwerValue)) {
            String strJson = JsonUtil.getInstance().loadJsonFromAsset(act, GenericConstant.BLANK_PROPERTIES_JSON);

            PropertiesBean propertiesBean = (PropertiesBean) JsonUtil.getInstance().toModel(strJson, PropertiesBean.class);
            propertiesBean.setText(act.appSession.getUserID());
            propertiesBean.setTextSize(12);
            propertiesBean.setTextColor(ViewPropertiesConstant.Color_Yellow);
            propertiesBean.setLayoutGravity(ViewPropertiesConstant.Start);

            LayoutFieldHelper layoutFieldHelper1 = new LayoutFieldHelper();
            layoutFieldHelper1.setViewType(ViewPropertiesConstant.textView);
            layoutFieldHelper1.setParentView(cLinear);
            layoutFieldHelper1.setPropertiesBean(propertiesBean);

            CreateLayout.createLayoutFields(act, layoutFieldHelper1);

            CreateDynamicView.userText(act, "", asnwerValue, cLinear);
        }
    }

    public static void setStorageRecord(ProcessCreationActivity act, String check, String checkQues) {

        if(check.equalsIgnoreCase(GenericConstant.Image_Present)) {

            CreateDynamicView.userImageList(act, new AppSession(act).getUserID(), act.llProcessLayout, act.imageListAct, SetClick.onClickImage, SetClick.onClickLongImageEdit);

        } else if(check.equalsIgnoreCase(GenericConstant.Signature_Present)) {

            CreateDynamicView.userSignatureList(act, new AppSession(act).getUserID(), act.llProcessLayout, act.signatureListAct, SetClick.onClickSignature, SetClick.onClickLongSignature);

        } else if(check.equalsIgnoreCase(GenericConstant.Sketch_Present)) {

            CreateDynamicView.userSketchList(act, new AppSession(act).getUserID(), act.llProcessLayout, act.sketchListAct, SetClick.onClickSketch, SetClick.onClickLongSketch);

        } else if(check.equalsIgnoreCase(GenericConstant.Document_Present)) {

            CreateDynamicView.userDocsList(act, new AppSession(act).getUserID(), act.llProcessLayout, act.docListAct, SetClick.onClickDoc, SetClick.onClickLongDocEdit);

        } else if(check.equalsIgnoreCase(GenericConstant.Audio_Present)) {

            CreateDynamicView.userAudioList(act, new AppSession(act).getUserID(), act.llProcessLayout, act.audioListAct, SetClick.onClickAudio, SetClick.onClickLongAudio);

        } else if(checkQues.equalsIgnoreCase(FieldsNameConstant.Pocketbook)) {

            CreateDynamicView.userPocketbookEntry(act, new AppSession(act).getUserID(), act.llProcessLayout, act.pocketbook, act.entryType, SetClick.onClickLongPocketbook);
        } else {
            SetRecord.setRecords(act);
        }
    }
}
