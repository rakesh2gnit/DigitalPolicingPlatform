package hcl.policing.digitalpolicingplatform.activities.process.edit;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.process.flow.ServerRequest;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.fieldName.FieldsNameConstant;
import hcl.policing.digitalpolicingplatform.models.process.ExpectedAnswerListBean;
import hcl.policing.digitalpolicingplatform.models.process.ExpectedQuestionListBean;
import hcl.policing.digitalpolicingplatform.models.process.PageSectionListBean;
import hcl.policing.digitalpolicingplatform.models.process.PageSection_detailListBean;
import hcl.policing.digitalpolicingplatform.models.process.SubSectionListBean;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class RemoveValue {

    public static void removeObjectValue(ProcessCreationActivity act, String sectionName) {

        if(act.QuestionDependentId != null && !act.QuestionDependentId.equalsIgnoreCase("")) {
            act.QuestionDependentId = act.QuestionDependentId + "," + act.editId;
        } else {
            act.QuestionDependentId = ""+act.editId;
        }
        ArrayList<Integer> idRemoveList = new ArrayList<>();
        ArrayList<String> idTempArray = new ArrayList<>(Arrays.asList(act.QuestionDependentId.split(",")));
        for (int j = 0; j < idTempArray.size(); j++) {
            idRemoveList.add(Integer.parseInt(idTempArray.get(j)));
        }

        if(act.aExpectedQuestionListBean != null && act.aExpectedQuestionListBean.size() > 0) {

            for (int i = 0; i < act.aExpectedQuestionListBean.size(); i++) {

                ExpectedQuestionListBean mExpectedQuestionListBean = act.aExpectedQuestionListBean.get(i);

                List<ExpectedAnswerListBean> aExpectedAnswerListBeans = mExpectedQuestionListBean.getExpectedAnswerList();
                List<PageSection_detailListBean> aPageSection_detailListBean = mExpectedQuestionListBean.getPageSection_detailList();

                for (int j = 0; j < idRemoveList.size(); j++) {

                    if(mExpectedQuestionListBean.getQuestion_Id() == idRemoveList.get(j)) {

                        if (act.isTabProcessEnabled) {
                            ServerRequest.setSubJsonServerRequest(act, mExpectedQuestionListBean.getActualQuestion(), "", null);
                        } else {
                            ServerRequest.setServerRequest(act, sectionName, mExpectedQuestionListBean.getActualQuestion(), "", null);
                        }
                        if(aExpectedAnswerListBeans != null && aExpectedAnswerListBeans.size() > 0) {

                            for (int k = 0; k < aExpectedAnswerListBeans.size(); k++) {

                                ExpectedAnswerListBean mExpectedAnswerListBean = aExpectedAnswerListBeans.get(k);

                                List<PageSection_detailListBean> aPageSection_detailListBean1 = mExpectedAnswerListBean.getPageSection_detailList();

                                if(aPageSection_detailListBean1 != null && aPageSection_detailListBean1.size() > 0) {

                                    for (int l = 0; l < aPageSection_detailListBean1.size(); l++) {

                                        if (act.isTabProcessEnabled) {
                                            ServerRequest.setSubJsonServerRequest(act, aPageSection_detailListBean1.get(l).getField_Name(), "", null);
                                        } else {
                                            ServerRequest.setServerRequest(act, sectionName, aPageSection_detailListBean1.get(l).getField_Name(), "", null);
                                        }
                                    }
                                }
                            }
                        } else if(aPageSection_detailListBean != null && aPageSection_detailListBean.size() > 0) {

                            for (int k = 0; k < aPageSection_detailListBean.size(); k++) {
                                if (act.isTabProcessEnabled) {
                                    ServerRequest.setSubJsonServerRequest(act, aPageSection_detailListBean.get(k).getField_Name(), "", null);
                                } else {
                                    ServerRequest.setServerRequest(act, sectionName, aPageSection_detailListBean.get(k).getField_Name(), "", null);
                                }
                            }
                        }
                    }
                }
            }
        }
        act.QuestionDependentId = "";
    }

    public static void removeFieldValue(ProcessCreationActivity act) {

        for (int j = 0; j < act.aPageSectionListBean.size(); j++) {

            PageSectionListBean mPageSectionListBean = act.aPageSectionListBean.get(j);
            List<ExpectedQuestionListBean> aExpectedQuestionListBean = mPageSectionListBean.getExpectedQuestionList();

            if(aExpectedQuestionListBean != null && aExpectedQuestionListBean.size() > 0) {

                for (int i = 0; i < aExpectedQuestionListBean.size(); i++) {

                    ExpectedQuestionListBean mExpectedQuestionListBean = aExpectedQuestionListBean.get(i);

                    List<ExpectedAnswerListBean> aExpectedAnswerListBeans = mExpectedQuestionListBean.getExpectedAnswerList();
                    List<PageSection_detailListBean> aPageSection_detailListBean = mExpectedQuestionListBean.getPageSection_detailList();

                    if(aExpectedAnswerListBeans != null && aExpectedAnswerListBeans.size() > 0) {

                        for (int k = 0; k < aExpectedAnswerListBeans.size(); k++) {

                            ExpectedAnswerListBean mExpectedAnswerListBean = aExpectedAnswerListBeans.get(k);
                            List<PageSection_detailListBean> aPageSection_detailListBean1 = mExpectedAnswerListBean.getPageSection_detailList();

                            if(aPageSection_detailListBean1 != null && aPageSection_detailListBean1.size() > 0) {

                                for (int l = 0; l < aPageSection_detailListBean1.size(); l++) {

                                    PageSection_detailListBean mPageSection_detailListBean = aPageSection_detailListBean1.get(l);

                                    if(act.idHideFieldList != null && act.idHideFieldList.size() > 0) {

                                        for (int m = 0; m < act.idHideFieldList.size(); m++) {

                                            if(mPageSection_detailListBean.getField_Id() == act.idHideFieldList.get(m)) {

                                                if (!act.isTabProcessEnabled) {
                                                    ServerRequest.setServerRequest(act, mPageSectionListBean.getPageSection_Name(), mPageSection_detailListBean.getField_Name(), "", null);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if(aPageSection_detailListBean != null && aPageSection_detailListBean.size() > 0) {

                        for (int k = 0; k < aPageSection_detailListBean.size(); k++) {

                            PageSection_detailListBean mPageSection_detailListBean = aPageSection_detailListBean.get(k);

                            if(act.idHideFieldList != null && act.idHideFieldList.size() > 0) {

                                for (int l = 0; l < act.idHideFieldList.size(); l++) {

                                    if(mPageSection_detailListBean.getField_Id() == act.idHideFieldList.get(l)) {

                                        if (!act.isTabProcessEnabled) {
                                            ServerRequest.setServerRequest(act, mPageSectionListBean.getPageSection_Name(), mPageSection_detailListBean.getField_Name(), "", null);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void removeQuestionValue(ProcessCreationActivity act) {

        for (int j = 0; j < act.aPageSectionListBean.size(); j++) {

            PageSectionListBean mPageSectionListBean = act.aPageSectionListBean.get(j);
            List<ExpectedQuestionListBean> aExpectedQuestionListBean = mPageSectionListBean.getExpectedQuestionList();

            if(aExpectedQuestionListBean != null && aExpectedQuestionListBean.size() > 0) {

                for (int i = 0; i < aExpectedQuestionListBean.size(); i++) {

                    ExpectedQuestionListBean mExpectedQuestionListBean = aExpectedQuestionListBean.get(i);

                    if(act.idHideQuestionList != null && act.idHideQuestionList.size() > 0) {

                        for (int m = 0; m < act.idHideQuestionList.size(); m++) {

                            if(mExpectedQuestionListBean.getQuestion_Id() == act.idHideQuestionList.get(m)) {

                                List<ExpectedAnswerListBean> aExpectedAnswerListBeans = mExpectedQuestionListBean.getExpectedAnswerList();
                                List<PageSection_detailListBean> aPageSection_detailListBean = mExpectedQuestionListBean.getPageSection_detailList();

                                if (!act.isTabProcessEnabled) {
                                    ServerRequest.setServerRequest(act, mPageSectionListBean.getPageSection_Name(), mExpectedQuestionListBean.getActualQuestion(), "", null);
                                }
                                if(aExpectedAnswerListBeans != null && aExpectedAnswerListBeans.size() > 0) {

                                    for (int k = 0; k < aExpectedAnswerListBeans.size(); k++) {

                                        ExpectedAnswerListBean mExpectedAnswerListBean = aExpectedAnswerListBeans.get(k);
                                        List<PageSection_detailListBean> aPageSection_detailListBean1 = mExpectedAnswerListBean.getPageSection_detailList();

                                        if(aPageSection_detailListBean1 != null && aPageSection_detailListBean1.size() > 0) {

                                            for (int l = 0; l < aPageSection_detailListBean1.size(); l++) {

                                                if (!act.isTabProcessEnabled) {
                                                    ServerRequest.setServerRequest(act, mPageSectionListBean.getPageSection_Name(), aPageSection_detailListBean1.get(l).getField_Name(), "", null);
                                                }
                                            }
                                        }
                                    }
                                } else if(aPageSection_detailListBean != null && aPageSection_detailListBean.size() > 0) {

                                    for (int k = 0; k < aPageSection_detailListBean.size(); k++) {

                                        if (!act.isTabProcessEnabled) {
                                            ServerRequest.setServerRequest(act, mPageSectionListBean.getPageSection_Name(), aPageSection_detailListBean.get(k).getField_Name(), "", null);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void removeSectionValue(ProcessCreationActivity act, String sectionName, List<ExpectedQuestionListBean> aExpectedQuestionListBean) {

        if(aExpectedQuestionListBean != null && aExpectedQuestionListBean.size() > 0) {

            for (int i = 0; i < aExpectedQuestionListBean.size(); i++) {

                ExpectedQuestionListBean mExpectedQuestionListBean = aExpectedQuestionListBean.get(i);

                List<ExpectedAnswerListBean> aExpectedAnswerListBeans = mExpectedQuestionListBean.getExpectedAnswerList();
                List<PageSection_detailListBean> aPageSection_detailListBean = mExpectedQuestionListBean.getPageSection_detailList();

                if (act.isTabProcessEnabled) {
                    ServerRequest.setSubJsonServerRequest(act, mExpectedQuestionListBean.getActualQuestion(), "", null);
                } else {
                    ServerRequest.setServerRequest(act, sectionName, mExpectedQuestionListBean.getActualQuestion(), "", null);
                }
                if(aExpectedAnswerListBeans != null && aExpectedAnswerListBeans.size() > 0) {

                    for (int k = 0; k < aExpectedAnswerListBeans.size(); k++) {

                        ExpectedAnswerListBean mExpectedAnswerListBean = aExpectedAnswerListBeans.get(k);

                        List<PageSection_detailListBean> aPageSection_detailListBean1 = mExpectedAnswerListBean.getPageSection_detailList();

                        if(aPageSection_detailListBean1 != null && aPageSection_detailListBean1.size() > 0) {

                            for (int l = 0; l < aPageSection_detailListBean1.size(); l++) {

                                if (act.isTabProcessEnabled) {
                                    ServerRequest.setSubJsonServerRequest(act, aPageSection_detailListBean1.get(l).getField_Name(), "", null);
                                } else {
                                    ServerRequest.setServerRequest(act, sectionName, aPageSection_detailListBean1.get(l).getField_Name(), "", null);
                                }
                            }
                        }
                    }
                } else if(aPageSection_detailListBean != null && aPageSection_detailListBean.size() > 0) {

                    for (int k = 0; k < aPageSection_detailListBean.size(); k++) {
                        if (act.isTabProcessEnabled) {
                            ServerRequest.setSubJsonServerRequest(act, aPageSection_detailListBean.get(k).getField_Name(), "", null);
                        } else {
                            ServerRequest.setServerRequest(act, sectionName, aPageSection_detailListBean.get(k).getField_Name(), "", null);
                        }
                    }
                }
            }
        }
    }

    public static void removeSubSectionValue(ProcessCreationActivity act) {
        try {
            for (int j = 0; j < act.aPageSectionListBean.size(); j++) {

                PageSectionListBean mPageSectionListBean = act.aPageSectionListBean.get(j);
                List<SubSectionListBean> aSubSectionListBean = mPageSectionListBean.getSubSectionList();

                if (aSubSectionListBean != null && aSubSectionListBean.size() > 0) {

                    for (int i = 0; i < aSubSectionListBean.size(); i++) {

                        SubSectionListBean mSubSectionListBean = aSubSectionListBean.get(i);

                        List<ExpectedQuestionListBean> aExpectedQuestionListBean = mSubSectionListBean.getExpectedQuestionList();
                        List<SubSectionListBean> aSubSectionListBean1 = mSubSectionListBean.getSubSectionList();

                        if(aExpectedQuestionListBean != null && aExpectedQuestionListBean.size() > 0) {

                            if (act.idHideSubSectionList != null && act.idHideSubSectionList.size() > 0) {

                                for (int k = 0; k < act.idHideSubSectionList.size(); k++) {

                                    if (mSubSectionListBean.getPageSection_Id() == act.idHideSubSectionList.get(k)) {

                                        String sectionName = BasicMethodsUtil.getInstance().getServerName(mPageSectionListBean.getPageSection_Name());
                                        String subSectionName = BasicMethodsUtil.getInstance().getServerName(mSubSectionListBean.getPageSection_Name());
                                        JSONObject jsonSection = act.mainJSON.getJSONObject(sectionName);

                                        JSONArray jsonArray = jsonSection.getJSONArray(subSectionName);

                                        if(jsonArray != null && jsonArray.length() > 1) {

                                            while (jsonArray.length() != 1) {
                                                jsonArray.remove(1);
                                            }
                                        }
                                    }
                                }
                            }

                            for (int k = 0; k < aExpectedQuestionListBean.size(); k++) {

                                ExpectedQuestionListBean mExpectedQuestionListBean = aExpectedQuestionListBean.get(k);
                                List<SubSectionListBean> aSubSectionListBean2 = mExpectedQuestionListBean.getSubSectionList();

                                if(aSubSectionListBean2 != null && aSubSectionListBean2.size() > 0) {

                                    for (int m = 0; m < aSubSectionListBean2.size(); m++) {

                                        SubSectionListBean mSubSectionListBean2 = aSubSectionListBean2.get(m);

                                        if (act.idHideSubSectionList != null && act.idHideSubSectionList.size() > 0) {

                                            for (int l = 0; l < act.idHideSubSectionList.size(); l++) {

                                                if (mSubSectionListBean2.getPageSection_Id() == act.idHideSubSectionList.get(l)) {

                                                    String sectionName = BasicMethodsUtil.getInstance().getServerName(mPageSectionListBean.getPageSection_Name());
                                                    String subSectionName = BasicMethodsUtil.getInstance().getServerName(mSubSectionListBean.getPageSection_Name());
                                                    String subSectionName1 = BasicMethodsUtil.getInstance().getServerName(mSubSectionListBean2.getPageSection_Name());

                                                    JSONObject jsonSection = act.mainJSON.getJSONObject(sectionName);

                                                    JSONArray jsonArray = jsonSection.getJSONArray(subSectionName);

                                                    if(jsonArray.length() > 1) {

                                                        for (int n = 1; n < jsonArray.length(); n++) {

                                                            JSONArray jsonArray1 = jsonArray.getJSONObject(n).getJSONArray(subSectionName1);

                                                            if(jsonArray1 != null && jsonArray1.length() > 1) {

                                                                while (jsonArray1.length() != 1) {
                                                                    jsonArray1.remove(1);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else if(aSubSectionListBean1 != null && aSubSectionListBean1.size() > 0) {

                            for (int k = 0; k < aSubSectionListBean1.size(); k++) {

                                SubSectionListBean mSubSectionListBean1 = aSubSectionListBean1.get(k);
                                List<ExpectedQuestionListBean> aExpectedQuestionListBean1 = mSubSectionListBean1.getExpectedQuestionList();

                                if(aExpectedQuestionListBean1 != null && aExpectedQuestionListBean1.size() > 0) {

                                    if (act.idHideSubSectionList != null && act.idHideSubSectionList.size() > 0) {

                                        for (int l = 0; l < act.idHideSubSectionList.size(); l++) {

                                            if (mSubSectionListBean1.getPageSection_Id() == act.idHideSubSectionList.get(l)) {

                                                String sectionName = BasicMethodsUtil.getInstance().getServerName(mPageSectionListBean.getPageSection_Name());
                                                String subSectionName = BasicMethodsUtil.getInstance().getServerName(mSubSectionListBean.getPageSection_Name());
                                                String subSectionName1 = BasicMethodsUtil.getInstance().getServerName(mSubSectionListBean1.getPageSection_Name());

                                                JSONObject jsonSection = act.mainJSON.getJSONObject(sectionName);

                                                JSONArray jsonArray = jsonSection.getJSONArray(subSectionName).getJSONObject(0).getJSONArray(subSectionName1);


                                                if(jsonArray != null && jsonArray.length() > 1) {

                                                    while (jsonArray.length() != 1) {
                                                        jsonArray.remove(1);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), RemoveValue.class, "removeSubSectionValue");
        }
    }
}
