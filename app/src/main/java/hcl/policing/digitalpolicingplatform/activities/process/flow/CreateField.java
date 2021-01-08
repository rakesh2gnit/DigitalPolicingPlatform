package hcl.policing.digitalpolicingplatform.activities.process.flow;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.process.edit.EditAnswerActivity;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.viewProperties.ViewPropertiesConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.layoutHelper.CreateLayout;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.LayoutFieldHelper;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.models.process.PageSection_detailListBean;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CreateField {

    /**
     * Create Pages basis on the information of Page list
     * @param pageSection_detailListBean
     */
    public static void createAttributes(ProcessCreationActivity act, PageSection_detailListBean pageSection_detailListBean) {

        try {
            if (pageSection_detailListBean != null) {

                // create Page here
                createDynamicFields(act, pageSection_detailListBean);
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateField.class, "createAttributes");
        }
    }

    /**
     * for creating dynamic fields in dialog
     * @param act
     * @param pageSectionDetailListBean
     */
    private static void createDynamicFields(ProcessCreationActivity act, PageSection_detailListBean pageSectionDetailListBean) {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset(act, GenericConstant.PROPERTIES_JSON));
            Gson gson = new Gson();
            PropertiesBean propertiesBean = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBean.setId(pageSectionDetailListBean.getField_Id());
            propertiesBean.setHint(pageSectionDetailListBean.getField_Name());
            //Log.e("MEndatry "," >>>>> "+pageSectionDetailListBean.getField_Name()+" >>>>> "+pageSectionDetailListBean.isField_Mendatry());
            if(pageSectionDetailListBean.getField_Visibility().equalsIgnoreCase(ViewPropertiesConstant.Gone)) {
                propertiesBean.setMendatry(false);
                propertiesBean.setVisibility(pageSectionDetailListBean.getField_Visibility());
                if(act.idShowFieldList != null && act.idShowFieldList.size() > 0) {
                    for (int i = 0; i < act.idShowFieldList.size(); i++) {
                        if(act.idShowFieldList.get(i) == pageSectionDetailListBean.getField_Id()) {
                            propertiesBean.setVisibility(ViewPropertiesConstant.Visible);
                            propertiesBean.setMendatry(pageSectionDetailListBean.isField_Mendatry());
                            break;
                        }
                    }
                }
            } else {
                propertiesBean.setMendatry(pageSectionDetailListBean.isField_Mendatry());
                propertiesBean.setVisibility(pageSectionDetailListBean.getField_Visibility());
            }
            propertiesBean.setEnabled(pageSectionDetailListBean.isField_Enabled());
            propertiesBean.setInputType(pageSectionDetailListBean.getField_Input_Type());
            propertiesBean.setKeyboardAction(pageSectionDetailListBean.getField_Action());

            LayoutFieldHelper layoutFieldHelper = new LayoutFieldHelper();
            layoutFieldHelper.setBasicLayout("");
            layoutFieldHelper.setViewType(pageSectionDetailListBean.getField_Type());
            layoutFieldHelper.setParentView(act.llAdd);
            layoutFieldHelper.setPropertiesBean(propertiesBean/*pageSectionDetailListBean.getPropertiesBean()*/);
            layoutFieldHelper.setDropdownArray(pageSectionDetailListBean.getField_MasterData());
            layoutFieldHelper.setPageSectionDetailListBean(pageSectionDetailListBean);

            CreateLayout.createLayoutFields(act, layoutFieldHelper);
            if (act.dialogLinear != null) {
                act.dialogLinear.show();
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateField.class, "createDynamicFields");
        }
    }

    /**
     * Call the edit attribute methods
     * @param act
     * @param pageSection_detailListBean
     */
    public static void editAttributes(EditAnswerActivity act, PageSection_detailListBean pageSection_detailListBean) {

        try {
            if (pageSection_detailListBean != null) {

                // create Page here
                createDynamicFields(act, pageSection_detailListBean);
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateField.class, "createAttributes");
        }
    }

    /**
     * for creating dynamic fields in dialog edit process
     * @param act
     * @param pageSectionDetailListBean
     */
    private static void createDynamicFields(EditAnswerActivity act, PageSection_detailListBean pageSectionDetailListBean) {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset(act, GenericConstant.PROPERTIES_JSON));
            Gson gson = new Gson();
            PropertiesBean propertiesBean = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBean.setId(pageSectionDetailListBean.getField_Id());
            propertiesBean.setHint(pageSectionDetailListBean.getField_Name());
            if(pageSectionDetailListBean.getField_Visibility().equalsIgnoreCase(ViewPropertiesConstant.Gone)) {
                propertiesBean.setMendatry(false);
                propertiesBean.setVisibility(pageSectionDetailListBean.getField_Visibility());
                if(act.idShowField != null && act.idShowField.size() > 0) {
                    for (int i = 0; i < act.idShowField.size(); i++) {
                        if(act.idShowField.get(i) == pageSectionDetailListBean.getField_Id()) {
                            propertiesBean.setVisibility(ViewPropertiesConstant.Visible);
                            propertiesBean.setMendatry(pageSectionDetailListBean.isField_Mendatry());
                            break;
                        }
                    }
                }
            } else {
                propertiesBean.setMendatry(pageSectionDetailListBean.isField_Mendatry());
                propertiesBean.setVisibility(pageSectionDetailListBean.getField_Visibility());
            }
            propertiesBean.setEnabled(pageSectionDetailListBean.isField_Enabled());
            propertiesBean.setInputType(pageSectionDetailListBean.getField_Input_Type());
            propertiesBean.setKeyboardAction(pageSectionDetailListBean.getField_Action());

            LayoutFieldHelper layoutFieldHelper = new LayoutFieldHelper();
            layoutFieldHelper.setBasicLayout("");
            layoutFieldHelper.setViewType(pageSectionDetailListBean.getField_Type());
            layoutFieldHelper.setParentView(act.llAdd);
            layoutFieldHelper.setPropertiesBean(propertiesBean/*pageSectionDetailListBean.getPropertiesBean()*/);
            layoutFieldHelper.setDropdownArray(pageSectionDetailListBean.getField_MasterData());
            layoutFieldHelper.setPageSectionDetailListBean(pageSectionDetailListBean);

            CreateLayout.createLayoutFields(act, layoutFieldHelper);
            act.dialogLinear.show();
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateField.class, "createDynamicFieldsEdit");
        }
    }

    /**
     * Load Json from Assets
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
