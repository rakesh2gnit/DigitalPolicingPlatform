package hcl.policing.digitalpolicingplatform.activities.process.edit;

import android.view.View;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.process.flow.PrepareScreen;
import hcl.policing.digitalpolicingplatform.activities.process.flow.ServerRequest;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.viewProperties.ViewPropertiesConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomImageView;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomLinearLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextView;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class OpenSection {

    //after editing moving to section in main json
    public static void openSection(ProcessCreationActivity activity, int mainTabID) {
        try {
            activity.isEditEnabled = false;
            activity.isTabProcessEnabled = false;
            activity.isTabFlowEnabled = false;
            for (int i = 0; i < activity.aPageSectionListBean.size(); i++) {
                if (activity.aPageSectionListBean.get(i).getPageSection_Id() == mainTabID) {
                    String answer = ServerRequest.getSavedAnswer(activity, activity.aPageSectionListBean.get(i).getPageSection_Name(), GenericConstant.SECTION_COMPLETE);
                    if (answer.equalsIgnoreCase("true")) {

                        for (int j = 0; j < activity.aPageSectionListBean.size(); j++) {

                            int tabId = activity.aPageSectionListBean.get(j).getPageSection_Id();
                            String answerInside = ServerRequest.getSavedAnswer(activity, activity.aPageSectionListBean.get(j).getPageSection_Name(), GenericConstant.SECTION_COMPLETE);

                            if (answerInside.equalsIgnoreCase("true")) {
                                CustomLinearLayout customLinear = activity.findViewById(tabId);
                                View viewImage = customLinear.getChildAt(0);
                                CustomImageView customImageView = (CustomImageView) viewImage;
                                customImageView.setImageResource(R.drawable.ic_dot_green);

                                View view = customLinear.getChildAt(1);
                                CustomTextView customTextView = (CustomTextView) view;
                                customTextView.setTextColor(activity, customTextView, ViewPropertiesConstant.Color_Green);
                            } else {
                                CustomLinearLayout customLinear = activity.findViewById(tabId);
                                View viewImage = customLinear.getChildAt(0);
                                CustomImageView customImageView = (CustomImageView) viewImage;
                                customImageView.setImageResource(R.drawable.ic_dot_gray);

                                View view = customLinear.getChildAt(1);
                                CustomTextView customTextView = (CustomTextView) view;
                                customTextView.setTextColor(activity, customTextView, ViewPropertiesConstant.Color_Gray_Light);
                            }
                        }
                        CustomLinearLayout customLinearTab = activity.findViewById(mainTabID);
                        View viewImage = customLinearTab.getChildAt(0);
                        CustomImageView customImageView = (CustomImageView) viewImage;
                        customImageView.setImageResource(R.drawable.ic_dot_blue);

                        View view = customLinearTab.getChildAt(1);
                        CustomTextView customTextView = (CustomTextView) view;
                        customTextView.setTextColor(activity, customTextView, ViewPropertiesConstant.Color_Primary);

                        PrepareScreen.prepareMainScreen(activity, i);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), OpenSection.class, "editNextSection");
        }
    }
}
