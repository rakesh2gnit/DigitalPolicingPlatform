package hcl.policing.digitalpolicingplatform.activities.process.draft;

import android.view.View;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.process.edit.OpenSection;
import hcl.policing.digitalpolicingplatform.activities.process.flow.ServerRequest;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.viewProperties.ViewPropertiesConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomImageView;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomLinearLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextView;

public class LoadDraft {

    /**
     * Prepare the draft screen
     * @param act
     */
    public static void prepareDraftScreen(ProcessCreationActivity act) throws Exception{
        //try {
            int idCount = 0;

            for (int i = 0; i < act.aPageSectionListBean.size(); i++) {
                int tabId = act.aPageSectionListBean.get(i).getPageSection_Id();
                String answer = ServerRequest.getSavedAnswer(act, act.aPageSectionListBean.get(i).getPageSection_Name(), GenericConstant.SECTION_COMPLETE);
                String visiblity = ServerRequest.getSavedAnswer(act, act.aPageSectionListBean.get(i).getPageSection_Name(), GenericConstant.SECTION_VISIBLE);

                if (answer.equalsIgnoreCase("true")) {
                    CustomLinearLayout customLinear = act.findViewById(tabId);
                    View viewImage = customLinear.getChildAt(0);
                    CustomImageView customImageView = (CustomImageView) viewImage;
                    customImageView.setImageResource(R.drawable.ic_dot_green);

                    View view = customLinear.getChildAt(1);
                    CustomTextView customTextView = (CustomTextView) view;
                    customTextView.setTextColor(act, customTextView, ViewPropertiesConstant.Color_Green);

                    idCount = i;
                    ProcessCreationActivity.SECTION_COUNT = idCount;
                    ProcessCreationActivity.SectionTabID = (act.aPageSectionListBean.get(idCount).getPageSection_Id());
                    OpenSection.openSection(act, act.aPageSectionListBean.get(i).getPageSection_Id());
                } else {
                    CustomLinearLayout customLinear = act.findViewById(tabId);
                    View viewImage = customLinear.getChildAt(0);
                    CustomImageView customImageView = (CustomImageView) viewImage;
                    customImageView.setImageResource(R.drawable.ic_dot_gray);

                    View view = customLinear.getChildAt(1);
                    CustomTextView customTextView = (CustomTextView) view;
                    customTextView.setTextColor(act, customTextView, ViewPropertiesConstant.Color_Gray_Light);
                }
                if (visiblity.equalsIgnoreCase("true")) {
                    CustomLinearLayout customLinear = act.findViewById(tabId);
                    customLinear.setVisibility(View.VISIBLE);
                } else {
                    CustomLinearLayout customLinear = act.findViewById(tabId);
                    customLinear.setVisibility(View.GONE);
                }
            }

            /*CustomLinearLayout customLinearTab = act.findViewById(act.aPageSectionListBean.get(idCount).getPageSection_Id());
            View viewImage = customLinearTab.getChildAt(0);
            CustomImageView customImageView = (CustomImageView) viewImage;
            customImageView.setImageResource(R.drawable.ic_dot_blue);

            View view = customLinearTab.getChildAt(1);
            CustomTextView customTextView = (CustomTextView) view;
            customTextView.setTextColor(act, customTextView, ViewPropertiesConstant.Color_Primary);

            ProcessCreationActivity.SectionTabID = (act.aPageSectionListBean.get(idCount).getPageSection_Id());

            PrepareScreen.prepareMainScreen(act, idCount);*/
        /*} catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), LoadDraft.class, "prepareScreenDraft");
        }*/
    }
}
