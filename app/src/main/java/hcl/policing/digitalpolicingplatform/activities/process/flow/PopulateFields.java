package hcl.policing.digitalpolicingplatform.activities.process.flow;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.process.edit.OpenSection;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.fieldName.FieldsNameConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextInputEditText;
import hcl.policing.digitalpolicingplatform.customLibraries.layoutHelper.CreateDynamicView;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.AnswerValueDTO;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.models.process.ExpectedQuestionListBean;
import hcl.policing.digitalpolicingplatform.models.process.SubSectionListBean;
import hcl.policing.digitalpolicingplatform.models.process.crime.AllegationRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.crime.CrimeGroupList;
import hcl.policing.digitalpolicingplatform.models.process.crime.CrimeGroupRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.crime.EventRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.crime.EventSearchList;
import hcl.policing.digitalpolicingplatform.models.process.crime.HOOffenceList;
import hcl.policing.digitalpolicingplatform.models.process.crime.OffenceDefinitionList;
import hcl.policing.digitalpolicingplatform.models.process.crime.OffenceRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.crime.OrgRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.crime.OrganisationDetailsList;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.AddressBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.AddressRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.PersonBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.PersonRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.team.TeamBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.team.TeamRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.VehicleBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.VehicleRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.officer.OfficerBean;
import hcl.policing.digitalpolicingplatform.models.process.officer.OfficerRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.officer.UserBean;
import hcl.policing.digitalpolicingplatform.models.process.populate.PopulateSubSectionListBean;
import hcl.policing.digitalpolicingplatform.models.search.RecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.tasking.TaskDetailResponseDTO;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class PopulateFields {

    public static void populateSingleField(Context context, CustomTextInputEditText editText, String selectLogic) {
        if(selectLogic.equalsIgnoreCase(GenericConstant.POPULATE_OFFICER_NAME)) {
            editText.setText(new AppSession(context).getUser().getOfficer_Name());
        } else if(selectLogic.equalsIgnoreCase(GenericConstant.POPULATE_OFFICER_NUMBER)) {
            editText.setText(new AppSession(context).getUser().getOfficer_Collar_Number());
        }
    }

    public static void populateRecent(ProcessCreationActivity act, Object object, ArrayList<AnswerValueDTO> recentList, String type) {

        if (object != null) {
            act.dismissSearchDialog();
            act.object = object;
            act.populateType = type;
            act.specialLogic = null;
            act.llAdd.removeAllViews();

            act.answerList = new ArrayList<>();
            for (int j = 0; j < act.aPageSection_detailListBean.size(); j++) {
                AnswerValueDTO answerDTO = new AnswerValueDTO();
                answerDTO.setKey(act.aPageSection_detailListBean.get(j).getField_Name());
                answerDTO.setValue("");
                answerDTO.setDependentOn(act.aPageSection_detailListBean.get(j).getField_Dependent_On());
                answerDTO.setId(act.aPageSection_detailListBean.get(j).getField_Id());
                answerDTO.setSelectLogic(act.aPageSection_detailListBean.get(j).getSelect_Logic());
                answerDTO.setMendatry(act.aPageSection_detailListBean.get(j).isField_Mendatry());
                answerDTO.setPopulate(type);
                act.answerList.add(answerDTO);
            }

            populateFieldList(act, object);

            if(recentList != null && recentList.size() > 0) {
                for (int i = 0; i < act.answerList.size(); i++) {

                    for (int j = 0; j < recentList.size(); j++) {

                        if(act.answerList.get(i).getKey().equalsIgnoreCase(recentList.get(j).getKey())) {

                            act.answerList.get(i).setValue(recentList.get(j).getValue());
                        }
                    }
                }
            }

            boolean populateValue = false;
            for (int i = 0; i < act.answerList.size(); i++) {
                if(act.answerList.get(i).getKey().contains(FieldsNameConstant.SYSTEM)) {
                    if(act.answerList.get(i).getValue().equalsIgnoreCase(GenericConstant.POLE)) {
                        populateValue = true;
                        break;
                    }
                }
            }
            if(act.processName.equalsIgnoreCase(act.getResources().getString(R.string.stop_process))
                    || act.processName.equalsIgnoreCase(act.getResources().getString(R.string.crime))) {
                if(populateValue) {
                    populate(act, object, type);
                } else {
                    PageSection.createPageSectionDetails(act, act.aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                    DialogValue.setValueInDialog(act, act.answerList);
                }
            } else {
                PageSection.createPageSectionDetails(act, act.aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                DialogValue.setValueInDialog(act, act.answerList);
            }
            DialogUtil.dismiss();
        }
    }

    public static void populateType(ProcessCreationActivity act, Object object, String type) {
        if (object != null) {
            act.dismissSearchDialog();
            act.object = object;
            act.populateType = type;
            act.specialLogic = null;
            act.llAdd.removeAllViews();

            act.answerList = new ArrayList<>();
            for (int j = 0; j < act.aPageSection_detailListBean.size(); j++) {
                AnswerValueDTO answerDTO = new AnswerValueDTO();
                answerDTO.setKey(act.aPageSection_detailListBean.get(j).getField_Name());
                answerDTO.setValue("");
                answerDTO.setDependentOn(act.aPageSection_detailListBean.get(j).getField_Dependent_On());
                answerDTO.setId(act.aPageSection_detailListBean.get(j).getField_Id());
                answerDTO.setSelectLogic(act.aPageSection_detailListBean.get(j).getSelect_Logic());
                answerDTO.setMendatry(act.aPageSection_detailListBean.get(j).isField_Mendatry());
                answerDTO.setPopulate(type);
                act.answerList.add(answerDTO);
            }

            populateFieldList(act, object);

            boolean populateValue = false;
            for (int i = 0; i < act.answerList.size(); i++) {
                if(act.answerList.get(i).getKey().contains(FieldsNameConstant.SYSTEM)) {
                    if(act.answerList.get(i).getValue().equalsIgnoreCase(GenericConstant.POLE)) {
                        populateValue = true;
                        break;
                    }
                }
            }
            if(act.processName.equalsIgnoreCase(act.getResources().getString(R.string.stop_process))
                    || act.processName.equalsIgnoreCase(act.getResources().getString(R.string.crime))) {
                if(populateValue) {
                    populate(act, object, type);
                } else {
                    PageSection.createPageSectionDetails(act, act.aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                    DialogValue.setValueInDialog(act, act.answerList);
                }
            } else {
                PageSection.createPageSectionDetails(act, act.aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                DialogValue.setValueInDialog(act, act.answerList);
            }
            DialogUtil.dismiss();
        }
    }

    public static void populateUser(ProcessCreationActivity act, UserBean officerBean) {
        if (officerBean != null) {
            act.specialLogic = null;
            act.llAdd.removeAllViews();

            act.answerList = new ArrayList<>();
            for (int j = 0; j < act.aPageSection_detailListBean.size(); j++) {
                AnswerValueDTO answerDTO = new AnswerValueDTO();
                answerDTO.setKey(act.aPageSection_detailListBean.get(j).getField_Name());
                answerDTO.setValue("");
                answerDTO.setDependentOn(act.aPageSection_detailListBean.get(j).getField_Dependent_On());
                answerDTO.setId(act.aPageSection_detailListBean.get(j).getField_Id());
                answerDTO.setSelectLogic(act.aPageSection_detailListBean.get(j).getSelect_Logic());
                answerDTO.setMendatry(act.aPageSection_detailListBean.get(j).isField_Mendatry());
                act.answerList.add(answerDTO);
            }

            for (int i = 0; i < act.answerList.size(); i++) {

                if (act.answerList.get(i).getKey().contains(FieldsNameConstant.Force)) {
                    act.answerList.get(i).setValue(officerBean.getForce());
                }
                if (act.answerList.get(i).getKey().contains(FieldsNameConstant.Force_Code)) {
                    act.answerList.get(i).setValue(officerBean.getForce_Code());
                }
                if (act.answerList.get(i).getKey().contains(FieldsNameConstant.Officer_Service_Number)) {
                    act.answerList.get(i).setValue(officerBean.getOfficer_Service_Number());
                }
                if (act.answerList.get(i).getKey().contains(FieldsNameConstant.Officer_Collar_Number)) {
                    act.answerList.get(i).setValue(officerBean.getOfficer_Collar_Number());
                }
                if (act.answerList.get(i).getKey().contains(FieldsNameConstant.Officer_Name)) {
                    act.answerList.get(i).setValue(officerBean.getOfficer_Name());
                }
                if (act.answerList.get(i).getKey().contains(FieldsNameConstant.Officer_Rank)) {
                    act.answerList.get(i).setValue(officerBean.getOfficer_Rank());
                }
                if (act.answerList.get(i).getKey().contains(FieldsNameConstant.Officer_Gender)) {
                    act.answerList.get(i).setValue(officerBean.getOfficer_Gender());
                }
                if (act.answerList.get(i).getKey().contains(FieldsNameConstant.Officer_Station)) {
                    act.answerList.get(i).setValue(officerBean.getOfficer_Station());
                }
                if (act.answerList.get(i).getKey().contains(FieldsNameConstant.ID)) {
                    act.answerList.get(i).setValue(officerBean.getId());
                }
                if (act.answerList.get(i).getKey().contains(FieldsNameConstant.SYSTEM)) {
                    act.answerList.get(i).setValue(officerBean.getSystem());
                }
            }

            populate(act, officerBean, act.populateType);
        }
    }

    private static void populateFieldList(ProcessCreationActivity act, Object object) {
        if (act.aPopulateListBean != null && act.aPopulateListBean.size() > 0) {
            for (int i = 0; i < act.aPopulateListBean.size(); i++) {
                act.mPopulateListBean = act.aPopulateListBean.get(i);
                Log.e("Populate ", "LIST ");

                if(act.mPopulateListBean.getSearch_Name().equalsIgnoreCase(act.searchName)
                        && act.mPopulateListBean.getSearch_Id() == act.searchId) {

                    act.aPopulateSectionList = act.mPopulateListBean.getPopulateSectionList();

                    for (int j = 0; j < act.aPopulateSectionList.size(); j++) {
                        act.mPopulateSectionList = act.aPopulateSectionList.get(j);
                        Log.e("Populate ", "SECTION ");

                        for (int k = 0; k < act.aPageSectionListBean.size(); k++) {

                            if(act.mPopulateSectionList.getPageSection_Name().equalsIgnoreCase(act.aPageSectionListBean.get(k).getPageSection_Name())
                                    && act.mPopulateSectionList.getPageSection_Id() == act.aPageSectionListBean.get(k).getPageSection_Id()) {

                                List<ExpectedQuestionListBean> aExpectedQuestionList = act.aPageSectionListBean.get(k).getExpectedQuestionList();
                                List<SubSectionListBean> aSubSectionList = act.aPageSectionListBean.get(k).getSubSectionList();

                                act.aPopulate_DetailList = act.mPopulateSectionList.getPopulate_DetailList();
                                act.aPopulateSubSectionList = act.mPopulateSectionList.getPopulateSubSectionList();

                                if(act.aPopulate_DetailList != null && act.aPopulate_DetailList.size() > 0) {

                                    for (int m = 0; m < act.aPopulate_DetailList.size(); m++) {
                                        String populateFrom = act.aPopulate_DetailList.get(m).getPopulate_From();
                                        String fieldName = act.aPopulate_DetailList.get(m).getField_Name();
                                        int fieldId = act.aPopulate_DetailList.get(m).getField_Id();
                                        Log.e("Populate ", "Field Name " + fieldName);

                                        for (int n = 0; n < act.answerList.size(); n++) {

                                            if(fieldName != null && fieldName.equalsIgnoreCase(act.answerList.get(n).getKey())
                                                && fieldId == act.answerList.get(n).getId()) {
                                                try {
                                                    Field field = object.getClass().getDeclaredField(populateFrom);
                                                    field.setAccessible(true);
                                                    String value = (String) field.get(object);
                                                    if(value != null) {
                                                        act.answerList.get(n).setValue(value);
                                                        Log.e("Populate ", "Field VALUE " + value);
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }
                                }

                                if (act.aPopulateSubSectionList != null && act.aPopulateSubSectionList.size() > 0) {

                                    if (aSubSectionList != null && aSubSectionList.size() > 0) {
                                        populateSubSectionFieldList(act, act.aPopulateSubSectionList, aSubSectionList, object);
                                    }
                                    if(aExpectedQuestionList != null && aExpectedQuestionList.size() > 0) {
                                        for (int l = 0; l < aExpectedQuestionList.size(); l++) {

                                            ExpectedQuestionListBean mExpectedQuestionListBean = aExpectedQuestionList.get(l);

                                            if (mExpectedQuestionListBean.getSubSectionList() != null && mExpectedQuestionListBean.getSubSectionList().size() > 0) {
                                                populateSubSectionFieldList(act, act.aPopulateSubSectionList, mExpectedQuestionListBean.getSubSectionList(), object);
                                            }
                                        }
                                    }
                                }

                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

    private static void populateSubSectionFieldList(ProcessCreationActivity act, List<PopulateSubSectionListBean> aPopulateSubSectionList, List<SubSectionListBean> aSubSectionList, Object object) {

        if(aPopulateSubSectionList != null && aPopulateSubSectionList.size() > 0) {

            for (int i = 0; i < aPopulateSubSectionList.size(); i++) {

                PopulateSubSectionListBean mPopulateSubSectionList = aPopulateSubSectionList.get(i);

                for (int j = 0; j < aSubSectionList.size(); j++) {

                    if (mPopulateSubSectionList.getSubSection_Name().equalsIgnoreCase(aSubSectionList.get(j).getPageSection_Name())
                            && mPopulateSubSectionList.getSubSection_Id() == aSubSectionList.get(j).getPageSection_Id()) {

                        List<ExpectedQuestionListBean> aExpectedQuestionList = aSubSectionList.get(j).getExpectedQuestionList();
                        List<SubSectionListBean> aSubSectionListBean = aSubSectionList.get(j).getSubSectionList();

                        act.aPopulate_DetailList = mPopulateSubSectionList.getPopulate_DetailList();
                        List<PopulateSubSectionListBean> aPopulateSubSectionListBean = mPopulateSubSectionList.getPopulateSubSectionList();

                        if (act.aPopulate_DetailList != null && act.aPopulate_DetailList.size() > 0) {

                            for (int m = 0; m < act.aPopulate_DetailList.size(); m++) {
                                String populateFrom = act.aPopulate_DetailList.get(m).getPopulate_From();
                                String fieldName = act.aPopulate_DetailList.get(m).getField_Name();
                                int fieldId = act.aPopulate_DetailList.get(m).getField_Id();
                                Log.e("Populate Sub", "Field Name " + fieldName);

                                for (int n = 0; n < act.answerList.size(); n++) {

                                    if (fieldName != null && fieldName.equalsIgnoreCase(act.answerList.get(n).getKey())
                                            && fieldId == act.answerList.get(n).getId()) {
                                        try {
                                            Field field = object.getClass().getDeclaredField(populateFrom);
                                            field.setAccessible(true);
                                            String value = (String) field.get(object);
                                            if (value != null) {
                                                act.answerList.get(n).setValue(value);
                                                Log.e("Populate Sub", "Field VALUE " + value);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                        if (aPopulateSubSectionListBean != null && aPopulateSubSectionListBean.size() > 0) {

                            if (aSubSectionListBean != null && aSubSectionListBean.size() > 0) {
                                populateSubSectionFieldList(act, aPopulateSubSectionListBean, aSubSectionListBean, object);
                            }
                            if (aExpectedQuestionList != null && aExpectedQuestionList.size() > 0) {
                                for (int l = 0; l < aExpectedQuestionList.size(); l++) {

                                    ExpectedQuestionListBean mExpectedQuestionListBean = aExpectedQuestionList.get(l);

                                    if (mExpectedQuestionListBean.getSubSectionList() != null && mExpectedQuestionListBean.getSubSectionList().size() > 0) {
                                        populateSubSectionFieldList(act, aPopulateSubSectionListBean, mExpectedQuestionListBean.getSubSectionList(), object);
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    public static void populate(ProcessCreationActivity act, Object object, String populateType) {
        if(object != null) {
            addValueToObject(act, object);
        }
        if (act.isTabProcessEnabled) {
            for (int i = 0; i < act.answerList.size(); i++) {
                ServerRequest.setSubJsonServerRequest(act, act.answerList.get(i).getKey(), act.answerList.get(i).getValue(), null);
            }
        } else {
            for (int i = 0; i < act.answerList.size(); i++) {
                ServerRequest.setServerRequest(act, act.mPageSectionListBean.getPageSection_Name(), act.answerList.get(i).getKey(), act.answerList.get(i).getValue(), null);
            }
            act.saveDraft();
            //Utilities.getInstance(act).writeFile(act.mainJSON.toString(), act.fileName, act.directoryDraft);
        }
        if(populateType != null && populateType.equalsIgnoreCase(GenericConstant.PERSON)) {

            try{
                PersonBean personBean = PersonBean.class.cast(object);
                ArrayList<PersonRecentLogsBean> recentList = new ArrayList<>();
                if(act.appSession.getRecentSearchedPerson() != null) {
                    recentList = act.appSession.getRecentSearchedPerson();
                }
                PersonRecentLogsBean recentLogsBean = new PersonRecentLogsBean();
                recentLogsBean.setRecentValue(personBean.getFirstname() + " " + personBean.getLastname());
                recentLogsBean.setObjectList(personBean);
                recentLogsBean.setRecentLogsList(act.answerList);
                recentList.add(recentLogsBean);
                recentList = new ArrayList<PersonRecentLogsBean>(new LinkedHashSet<PersonRecentLogsBean>(recentList));
                act.appSession.setRecentSearchedPerson(null);
                act.appSession.setRecentSearchedPerson(recentList);
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if(populateType != null && populateType.equalsIgnoreCase(GenericConstant.ADDRESS)) {

            AddressBean addressBean = AddressBean.class.cast(object);
            ArrayList<AddressRecentLogsBean> recentList = new ArrayList<>();
            if(act.appSession.getSearchedAddress() != null) {
                recentList = act.appSession.getSearchedAddress();
            }
            AddressRecentLogsBean recentLogsBean = new AddressRecentLogsBean();
            recentLogsBean.setRecentValue(Objects.requireNonNull(addressBean).getAddress());
            recentLogsBean.setObjectList(addressBean);
            recentLogsBean.setRecentLogsList(act.answerList);
            recentList.add(recentLogsBean);
            recentList = new ArrayList<AddressRecentLogsBean>(new LinkedHashSet<AddressRecentLogsBean>(recentList));
            act.appSession.setSearchedAddress(null);
            act.appSession.setSearchedAddress(recentList);

        } else if(populateType != null && populateType.equalsIgnoreCase(GenericConstant.VEHICLE)) {

            VehicleBean vehicleBean = VehicleBean.class.cast(object);

            ArrayList<VehicleRecentLogsBean> recentList = new ArrayList<>();
            if(act.appSession.getSearchedVehicle() != null) {
                recentList = act.appSession.getSearchedVehicle();
            }
            VehicleRecentLogsBean recentLogsBean = new VehicleRecentLogsBean();
            recentLogsBean.setRecentValue(vehicleBean.getVrm());
            recentLogsBean.setObjectList(vehicleBean);
            recentLogsBean.setRecentLogsList(act.answerList);
            recentList.add(recentLogsBean);
            recentList = new ArrayList<VehicleRecentLogsBean>(new LinkedHashSet<VehicleRecentLogsBean>(recentList));
            act.appSession.setSearchedVehicle(null);
            act.appSession.setSearchedVehicle(recentList);

        } else if(populateType != null && populateType.equalsIgnoreCase(GenericConstant.OFFICER)) {

            OfficerBean officerBean = OfficerBean.class.cast(object);
            ArrayList<OfficerRecentLogsBean> recentList = new ArrayList<>();
            if(act.appSession.getSearchedOfficer() != null) {
                recentList = act.appSession.getSearchedOfficer();
            }
            OfficerRecentLogsBean recentLogsBean = new OfficerRecentLogsBean();
            recentLogsBean.setRecentValue(officerBean.getOfficer_Collar_Number() + " " + officerBean.getOfficer_Name());
            recentLogsBean.setObjectList(officerBean);
            recentLogsBean.setRecentLogsList(act.answerList);
            recentList.add(recentLogsBean);
            recentList = new ArrayList<OfficerRecentLogsBean>(new LinkedHashSet<OfficerRecentLogsBean>(recentList));
            act.appSession.setSearchedOfficer(null);
            act.appSession.setSearchedOfficer(recentList);

        } else if(populateType != null && populateType.equalsIgnoreCase(GenericConstant.TEAM)) {

            TeamBean teamBean = TeamBean.class.cast(object);
            ArrayList<TeamRecentLogsBean> recentList = new ArrayList<>();
            if(act.appSession.getSearchedTeam() != null) {
                recentList = act.appSession.getSearchedTeam();
            }
            TeamRecentLogsBean recentLogsBean = new TeamRecentLogsBean();
            recentLogsBean.setRecentValue(teamBean.getTeamname());
            recentLogsBean.setObjectList(teamBean);
            recentLogsBean.setRecentLogsList(act.answerList);
            recentList.add(recentLogsBean);
            recentList = new ArrayList<TeamRecentLogsBean>(new LinkedHashSet<TeamRecentLogsBean>(recentList));
            act.appSession.setSearchedTeam(null);
            act.appSession.setSearchedTeam(recentList);

        } else if(populateType != null && populateType.equalsIgnoreCase(GenericConstant.ORGANISATION)) {

            OrganisationDetailsList organisationDetailsList = OrganisationDetailsList.class.cast(object);
            ArrayList<OrgRecentLogsBean> recentList = new ArrayList<>();
            if(act.appSession.getSearchedOrg() != null) {
                recentList = act.appSession.getSearchedOrg();
            }
            OrgRecentLogsBean recentLogsBean = new OrgRecentLogsBean();
            recentLogsBean.setRecentValue(organisationDetailsList.getOrganisationName());
            recentLogsBean.setObjectList(organisationDetailsList);
            recentLogsBean.setRecentLogsList(act.answerList);
            recentList.add(recentLogsBean);
            recentList = new ArrayList<OrgRecentLogsBean>(new LinkedHashSet<OrgRecentLogsBean>(recentList));
            act.appSession.setSearchedOrg(null);
            act.appSession.setSearchedOrg(recentList);

        } else if(populateType != null && populateType.equalsIgnoreCase(GenericConstant.CRIMEGROUP)) {

            CrimeGroupList crimeGroupList = CrimeGroupList.class.cast(object);
            ArrayList<CrimeGroupRecentLogsBean> recentList = new ArrayList<>();
            if(act.appSession.getSearchedCrimeGroup() != null) {
                recentList = act.appSession.getSearchedCrimeGroup();
            }
            CrimeGroupRecentLogsBean recentLogsBean = new CrimeGroupRecentLogsBean();
            recentLogsBean.setRecentValue(crimeGroupList.getName());
            recentLogsBean.setObjectList(crimeGroupList);
            recentLogsBean.setRecentLogsList(act.answerList);
            recentList.add(recentLogsBean);
            recentList = new ArrayList<CrimeGroupRecentLogsBean>(new LinkedHashSet<CrimeGroupRecentLogsBean>(recentList));
            act.appSession.setSearchedCrimeGroup(null);
            act.appSession.setSearchedCrimeGroup(recentList);

        } else if(populateType != null && populateType.equalsIgnoreCase(GenericConstant.ALLEGATION)) {

            OffenceDefinitionList offenceDefinitionList = OffenceDefinitionList.class.cast(object);
            ArrayList<AllegationRecentLogsBean> recentList = new ArrayList<>();
            if(act.appSession.getSearchedAllegation() != null) {
                recentList = act.appSession.getSearchedAllegation();
            }
            AllegationRecentLogsBean recentLogsBean = new AllegationRecentLogsBean();
            recentLogsBean.setRecentValue(offenceDefinitionList.getDescription());
            recentLogsBean.setObjectList(offenceDefinitionList);
            recentLogsBean.setRecentLogsList(act.answerList);
            recentList.add(recentLogsBean);
            recentList = new ArrayList<AllegationRecentLogsBean>(new LinkedHashSet<AllegationRecentLogsBean>(recentList));
            act.appSession.setSearchedAllegation(null);
            act.appSession.setSearchedAllegation(recentList);

        } else if(populateType != null && populateType.equalsIgnoreCase(GenericConstant.EVENT)) {

            EventSearchList eventSearchList = EventSearchList.class.cast(object);
            ArrayList<EventRecentLogsBean> recentList = new ArrayList<>();
            if(act.appSession.getSearchedEvent() != null) {
                recentList = act.appSession.getSearchedEvent();
            }
            EventRecentLogsBean recentLogsBean = new EventRecentLogsBean();
            recentLogsBean.setRecentValue(eventSearchList.getURN());
            recentLogsBean.setObjectList(eventSearchList);
            recentLogsBean.setRecentLogsList(act.answerList);
            recentList.add(recentLogsBean);
            recentList = new ArrayList<EventRecentLogsBean>(new LinkedHashSet<EventRecentLogsBean>(recentList));
            act.appSession.setSearchedEvent(null);
            act.appSession.setSearchedEvent(recentList);

        } else if(populateType != null && populateType.equalsIgnoreCase(GenericConstant.OFFENCE)) {

            HOOffenceList hoOffenceList = HOOffenceList.class.cast(object);
            ArrayList<OffenceRecentLogsBean> recentList = new ArrayList<>();
            if(act.appSession.getSearchedOffence() != null) {
                recentList = act.appSession.getSearchedOffence();
            }
            OffenceRecentLogsBean recentLogsBean = new OffenceRecentLogsBean();
            recentLogsBean.setRecentValue(hoOffenceList.getOffence());
            recentLogsBean.setObjectList(hoOffenceList);
            recentLogsBean.setRecentLogsList(act.answerList);
            recentList.add(recentLogsBean);
            recentList = new ArrayList<OffenceRecentLogsBean>(new LinkedHashSet<OffenceRecentLogsBean>(recentList));
            act.appSession.setSearchedOffence(null);
            act.appSession.setSearchedOffence(recentList);

        }

        if (act.isEditPageSection) {
            act.customLinearLayoutEdit.removeAllViews();
            SetRecord.setRecordsFromEdit(act, act.customLinearLayoutEdit);

            act.captureValue = "";
            act.llAdd.removeAllViews();
            act.isEditPageSection = false;
            if (!act.isTabProcessEnabled)
                OpenSection.openSection(act, act.SectionTabID);
        } else {
            if (act.captureValue != null && !act.captureValue.equalsIgnoreCase("")) {
                SetRecord.setRecords(act, act.captureValue);

                if (act.answerList != null && act.answerList.size() > 0) {
                    String check = null;
                    for (int k = 0; k < act.answerList.size(); k++) {
                        if (act.answerList.get(k).getValue() != null && !act.answerList.get(k).getValue().equalsIgnoreCase("")) {
                            check = act.answerList.get(k).getValue();
                            break;
                        }
                    }
                    if (check != null && !check.equalsIgnoreCase("")) {
                        if (act.mExpectedQuestionListBean != null) {
                            //CustomLinearLayout cLinearLayout = findViewById(SECTION_COUNT + 1);
                            CreateDynamicView.assistantText(act, act.mExpectedQuestionListBean.getDialogHeading(), act.llProcessLayout, null, null);
                        }
                        SetRecord.setRecords(act);
                    }
                }
            } else {
                if (act.answerList.size() > 0) {
                    String check = null;
                    for (int k = 0; k < act.answerList.size(); k++) {
                        if (act.answerList.get(k).getValue() != null && !act.answerList.get(k).getValue().equalsIgnoreCase("")) {
                            check = act.answerList.get(k).getValue();
                            break;
                        }
                    }
                    if (check != null && !check.equalsIgnoreCase("")) {
                        SetRecord.setRecords(act);
                    } else {
                        SetRecord.setRecords(act, GenericConstant.No_Details);
                    }
                }
            }
            //Log.e("Get SERVER ", "REQUEST >> "+mainJSON.toString());
            act.captureValue = "";
            act.llAdd.removeAllViews();
            if (act.isNextQuestionOfSection) {
                act.isNextQuestion();
            } else {
                if(act.isTabProcessEnabled) {
                    act.isNextSectionTabProcess();
                } else {
                    act.isNextSection();
                }
            }
        }
        act.populateType = null;
        act.scrollDown();
    }

    public static void addValueToObject(ProcessCreationActivity act, Object object) {

        if (act.aPopulateListBean != null && act.aPopulateListBean.size() > 0) {

            for (int i = 0; i < act.aPopulateListBean.size(); i++) {

                act.mPopulateListBean = act.aPopulateListBean.get(i);
                //Log.e("Populate ", "LIST ");

                if(act.mPopulateListBean.getSearch_Name().equalsIgnoreCase(act.searchName)
                        && act.mPopulateListBean.getSearch_Id() == act.searchId) {

                    act.aPopulateSectionList = act.mPopulateListBean.getPopulateSectionList();

                    for (int j = 0; j < act.aPopulateSectionList.size(); j++) {
                        act.mPopulateSectionList = act.aPopulateSectionList.get(j);
                        //Log.e("Populate ", "SECTION ");

                        for (int k = 0; k < act.aPageSectionListBean.size(); k++) {
                            if(act.mPopulateSectionList.getPageSection_Name().equalsIgnoreCase(act.aPageSectionListBean.get(k).getPageSection_Name())
                                    && act.mPopulateSectionList.getPageSection_Id() == act.aPageSectionListBean.get(k).getPageSection_Id()) {

                                String section = act.aPageSectionListBean.get(k).getPageSection_Name();
                                String sectionJsonName = BasicMethodsUtil.getInstance().getServerName(section);
                                JSONObject jsonMain = null;
                                try {
                                    jsonMain = act.mainJSON.getJSONObject(sectionJsonName);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                List<ExpectedQuestionListBean> aExpectedQuestionList = act.aPageSectionListBean.get(k).getExpectedQuestionList();
                                List<SubSectionListBean> aSubSectionList = act.aPageSectionListBean.get(k).getSubSectionList();

                                act.aPopulate_DetailList = act.mPopulateSectionList.getPopulate_DetailList();
                                act.aPopulateSubSectionList = act.mPopulateSectionList.getPopulateSubSectionList();

                                if(act.aPopulate_DetailList != null && act.aPopulate_DetailList.size() > 0) {

                                    for (int m = 0; m < act.aPopulate_DetailList.size(); m++) {
                                        String populateFrom = act.aPopulate_DetailList.get(m).getPopulate_From();
                                        String fieldName = act.aPopulate_DetailList.get(m).getField_Name();
                                        //Log.e("Populate ", "Field Name " + fieldName);

                                        try {
                                            Field field = object.getClass().getDeclaredField(populateFrom);
                                            field.setAccessible(true);
                                            String value = (String) field.get(object);
                                            if(value != null) {
                                                ServerRequest.setServerRequest(act, act.aPageSectionListBean.get(k).getPageSection_Name(), fieldName, value, null);
                                                act.saveDraft();
                                                //Utilities.getInstance(act).writeFile(act.mainJSON.toString(), act.fileName, act.directoryDraft);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                if(act.aPopulateSubSectionList != null && act.aPopulateSubSectionList.size() > 0) {

                                    //act.subSectionList = new ArrayList<>();
                                    //act.subSectionList.add("Victim/Witness");
                                    //act.subSectionList.add("Organisation Addresses");
                                    if (aSubSectionList != null && aSubSectionList.size() > 0) {
                                        addSubSectionValueToObject(act, jsonMain, act.aPopulateSubSectionList, aSubSectionList, object);
                                    }
                                    if(aExpectedQuestionList != null && aExpectedQuestionList.size() > 0) {
                                        for (int l = 0; l < aExpectedQuestionList.size(); l++) {

                                            ExpectedQuestionListBean mExpectedQuestionListBean = aExpectedQuestionList.get(l);

                                            if (mExpectedQuestionListBean.getSubSectionList() != null && mExpectedQuestionListBean.getSubSectionList().size() > 0) {
                                                addSubSectionValueToObject(act, jsonMain, act.aPopulateSubSectionList, mExpectedQuestionListBean.getSubSectionList(), object);
                                            }
                                        }
                                    }
                                }
                                //break;
                            }
                        }
                    }
                    break;
                }
            }
            act.object = null;
        }
    }

    private static void addSubSectionValueToObject(ProcessCreationActivity act, JSONObject jsonObject, List<PopulateSubSectionListBean> aPopulateSubSectionList, List<SubSectionListBean> aSubSectionList, Object object) {

        if(aPopulateSubSectionList != null && aPopulateSubSectionList.size() > 0) {

            for (int i = 0; i < aPopulateSubSectionList.size(); i++) {

                PopulateSubSectionListBean mPopulateSubSectionList = aPopulateSubSectionList.get(i);
                String sectionJsonName = BasicMethodsUtil.getInstance().getServerName(mPopulateSubSectionList.getSubSection_Name());
                try {
                    act.subSectionJson = jsonObject.getJSONArray(sectionJsonName).getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int j = 0; j < aSubSectionList.size(); j++) {

                    if (mPopulateSubSectionList.getSubSection_Name().equalsIgnoreCase(aSubSectionList.get(j).getPageSection_Name())
                            && mPopulateSubSectionList.getSubSection_Id() == aSubSectionList.get(j).getPageSection_Id()) {

                        List<ExpectedQuestionListBean> aExpectedQuestionList = aSubSectionList.get(j).getExpectedQuestionList();
                        List<SubSectionListBean> aSubSectionListBean = aSubSectionList.get(j).getSubSectionList();

                        act.aPopulate_DetailList = mPopulateSubSectionList.getPopulate_DetailList();
                        List<PopulateSubSectionListBean> aPopulateSubSectionListBean = mPopulateSubSectionList.getPopulateSubSectionList();

                        if (act.aPopulate_DetailList != null && act.aPopulate_DetailList.size() > 0) {

                            for (int m = 0; m < act.aPopulate_DetailList.size(); m++) {
                                String populateFrom = act.aPopulate_DetailList.get(m).getPopulate_From();
                                String fieldName = act.aPopulate_DetailList.get(m).getField_Name();
                                Log.e("Add Sub", "Json Name " + fieldName);

                                try {
                                    Field field = object.getClass().getDeclaredField(populateFrom);
                                    field.setAccessible(true);
                                    String value = (String) field.get(object);
                                    if (value != null) {
                                        ServerRequest.setSubSectionObject(act.subSectionJson, fieldName, value, null);
                                        act.saveDraft();
                                        //Utilities.getInstance(act).writeFile(act.mainJSON.toString(), act.fileName, act.directoryDraft);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (aPopulateSubSectionListBean != null && aPopulateSubSectionListBean.size() > 0) {

                            if (aSubSectionListBean != null && aSubSectionListBean.size() > 0) {
                                addSubSectionValueToObject(act, act.subSectionJson, aPopulateSubSectionListBean, aSubSectionListBean, object);
                            }
                            if (aExpectedQuestionList != null && aExpectedQuestionList.size() > 0) {
                                for (int l = 0; l < aExpectedQuestionList.size(); l++) {

                                    ExpectedQuestionListBean mExpectedQuestionListBean = aExpectedQuestionList.get(l);

                                    if (mExpectedQuestionListBean.getSubSectionList() != null && mExpectedQuestionListBean.getSubSectionList().size() > 0) {
                                        addSubSectionValueToObject(act, act.subSectionJson, aPopulateSubSectionListBean, mExpectedQuestionListBean.getSubSectionList(), object);
                                    }
                                }
                            }
                        }
                        //break;
                    }
                }
            }
        }
    }
}
