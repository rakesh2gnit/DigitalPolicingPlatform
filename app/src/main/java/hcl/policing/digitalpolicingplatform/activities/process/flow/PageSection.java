package hcl.policing.digitalpolicingplatform.activities.process.flow;

import android.os.Handler;
import android.util.Log;

import java.util.List;

import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.models.process.PageSection_detailListBean;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.DropdownDialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.SearchDialogUtil;

public class PageSection {

    /**
     * ATTRIBUTE_COUNT -> It will use when OK Button pressed for next view
     * Create the Page Section Details
     *
     * @param pageSection_detailListBeans
     */
    public static void createPageSectionDetails(ProcessCreationActivity act, List<PageSection_detailListBean> pageSection_detailListBeans, int attCount) {

        new Handler().postDelayed(() -> {
            Log.e("SPECIAL Logic ", " >>>>>>>>>>>>>>>>> " + act.specialLogic);
            try {
                if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_OFFICER)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_OFFICER, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_TEAM)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_TEAM, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_PERSON)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_PERSON, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_VEHICLE)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_VEHICLE, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_ADDRESS)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_ADDRESS, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_ORGANISATION)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_ORGANISATION, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_INCIDENT)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_INCIDENT, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_ALLEGATION)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_ALLEGATION, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_MO)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_MO, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_OFFENCE)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_OFFENCE, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_CRIME_GROUP)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_CRIME_GROUP, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_EVENT)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_EVENT, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_DL)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_DL, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_AIRCRAFT)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_AIRCRAFT, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_MOTOR_VEHICLE)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_MOTOR_VEHICLE, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_WATERCRAFT)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_WATERCRAFT, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_ANIMAL)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_ANIMAL, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_ART)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_ART, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_BODY_PART)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_BODY_PART, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_BUILDING_MATERIAL)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_BUILDING_MATERIAL, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_CARAVAN)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_CARAVAN, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_CLOTHING)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_CLOTHING, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_CHEMICAL)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_CHEMICAL, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_CURRENCY)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_CURRENCY, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_DOCUMENT)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_DOCUMENT, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_DRUG)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_DRUG, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_ELECTRICAL)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_ELECTRICAL, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_ENGINE)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_ENGINE, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_JEWELLERY)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_JEWELLERY, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_MOBILE_DEVICE)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_MOBILE_DEVICE, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_OTHER)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_OTHER, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_PEDAL_CYCLE)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_PEDAL_CYCLE, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_MACHINERY)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_MACHINERY, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SEARCH_WEAPON)) {

                    act.loadSearchDialog(GenericConstant.SEARCH_WEAPON, null);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.POPULATE_OFFICER)) {

                    PopulateFields.populateUser(act, act.appSession.getUser());

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SHOW_IMAGE)) {

                    act.callCameraActivity("");

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SHOW_ATTACHMENT)) {

                    act.callAttachmentActivity("");

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SHOW_AUDIO)) {

                    act.callAudioActivity("");

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SHOW_SIGNATURE)) {

                    act.callSignatureActivity("");

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SHOW_SKETCH)) {

                    act.callSketchActivity("");

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SHOW_POCKETBOOK)) {

                    act.callPocketbookActivity("");

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SHOW_FINAL_DIALOG)) {

                    act.finalDialog(act.mExpectedAnswerListBean.getProcessingDetails());

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SAVE_REQUEST)) {

                    ProcessCreationActivity.SectionTabID = 0;
                    act.isNextSection();

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.OPEN_DOMESTIC)) {

                    act.openProcess(act.mExpectedAnswerListBean.getProcessingDetails());

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.CURRENT_ADDRESS)) {

                    GetLocation.getLocation(act, GenericConstant.CURRENT_ADDRESS);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.CURRENT_ADDRESS_SEARCH)) {

                    GetLocation.getLocation(act, GenericConstant.CURRENT_ADDRESS_SEARCH);

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SHOW_MAP)) {

                    act.callMapActivity();

                } else if (act.specialLogic != null && act.specialLogic.equalsIgnoreCase(GenericConstant.SHOW_MAP_SEARCH)) {

                    act.callMapActivitySearch();

                } else {
                    if(attCount == 0) {
                        ProcessCreationActivity.ATTRIBUTE_COUNT = 0;
                    }
                    for (int i = attCount; i < pageSection_detailListBeans.size(); i++) {

                        ProcessCreationActivity.ATTRIBUTE_COUNT++;
                        // Call Page method
                        if (!pageSection_detailListBeans.get(i).getShowFieldsInOneGo()) {
                            // Call all fiedls in one go creation like Name , DOB , EMAIL ets.
                            DropdownDialogUtil.updateActivity(act);
                            CreateField.createAttributes(act, pageSection_detailListBeans.get(i));
                            break;

                        } else {
                            // Call all fiedls in one go creation like Name , DOB , EMAIL ets.
                            CreateField.createAttributes(act, pageSection_detailListBeans.get(i));
                            //OK button design and stop loop
                        }
                        //OK button design

                    }
//                    mapPageSection.put(sectionIndex, pageSection_detailListBeans);
                    Log.d("SPECIAL LOGIC", "Moving to Next section/page");
                    // Call the next iteration of loops in same level.
                }


            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), PageSection.class, "pageSectionDetails");
            }
        }, 500);
    }
}
