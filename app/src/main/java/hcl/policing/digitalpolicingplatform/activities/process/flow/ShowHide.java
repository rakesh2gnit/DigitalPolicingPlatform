package hcl.policing.digitalpolicingplatform.activities.process.flow;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.process.edit.RemoveValue;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomLinearLayout;
import hcl.policing.digitalpolicingplatform.models.process.ExpectedAnswerListBean;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;

public class ShowHide {

    public static void showHideFromAnswer(ProcessCreationActivity activity, int buttonId) {
        ArrayList<Integer> idSectinHideList = new ArrayList<>();
        ArrayList<Integer> idShowSectionList = new ArrayList<>();
        ArrayList<Integer> idQuestionHideList = new ArrayList<>();
        ArrayList<Integer> idQuestionShowList = new ArrayList<>();
        ArrayList<Integer> idFieldHideList = new ArrayList<>();
        ArrayList<Integer> idFieldShowList = new ArrayList<>();

        for (int i = 0; i < activity.aExpectedAnswerListBeans.size(); i++) {
            if (activity.aExpectedAnswerListBeans.get(i).getShowSectionId() != null && !TextUtils.isEmpty(activity.aExpectedAnswerListBeans.get(i).getShowSectionId())) {
                ArrayList<String> idTempArray = new ArrayList<>(Arrays.asList(activity.aExpectedAnswerListBeans.get(i).getShowSectionId().split(",")));
                for (int j = 0; j < idTempArray.size(); j++) {
                    idShowSectionList.add(Integer.parseInt(idTempArray.get(j)));
                }
            }
            if (activity.aExpectedAnswerListBeans.get(i).getShowQuestionId() != null && !TextUtils.isEmpty(activity.aExpectedAnswerListBeans.get(i).getShowQuestionId())) {
                ArrayList<String> idTempArray = new ArrayList<>(Arrays.asList(activity.aExpectedAnswerListBeans.get(i).getShowQuestionId().split(",")));
                for (int j = 0; j < idTempArray.size(); j++) {
                    idQuestionShowList.add(Integer.parseInt(idTempArray.get(j)));
                }
            }
            if (activity.aExpectedAnswerListBeans.get(i).getShowFieldId() != null && !TextUtils.isEmpty(activity.aExpectedAnswerListBeans.get(i).getShowFieldId())) {
                ArrayList<String> idTempArray = new ArrayList<>(Arrays.asList(activity.aExpectedAnswerListBeans.get(i).getShowFieldId().split(",")));
                for (int j = 0; j < idTempArray.size(); j++) {
                    idFieldShowList.add(Integer.parseInt(idTempArray.get(j)));
                }
            }
        }
        idShowSectionList = new ArrayList<Integer>(new LinkedHashSet<Integer>(idShowSectionList));
        activity.idShowSubSectionList.addAll(idShowSectionList);
        activity.idShowSubSectionList = new ArrayList<Integer>(new LinkedHashSet<Integer>(activity.idShowSubSectionList));

        idQuestionShowList = new ArrayList<Integer>(new LinkedHashSet<Integer>(idQuestionShowList));
        activity.idShowQuestionList.addAll(idQuestionShowList);
        activity.idShowQuestionList = new ArrayList<Integer>(new LinkedHashSet<Integer>(activity.idShowQuestionList));

        idFieldShowList = new ArrayList<Integer>(new LinkedHashSet<Integer>(idFieldShowList));
        activity.idShowFieldList.addAll(idFieldShowList);
        activity.idShowFieldList = new ArrayList<Integer>(new LinkedHashSet<Integer>(activity.idShowFieldList));

        for (int i = 0; i < idShowSectionList.size(); i++) {
            int showId = idShowSectionList.get(i);
            CustomLinearLayout customLinearLayout = activity.findViewById(showId);
            if(customLinearLayout != null)
                customLinearLayout.setVisibility(View.VISIBLE);
            if (activity.aPageSectionListBean.get(i).getPageSection_Id() == showId) {
                ServerRequest.setServerRequest(activity, activity.aPageSectionListBean.get(i).getPageSection_Name(), GenericConstant.SECTION_VISIBLE, "true", null);
            }
        }
        if(activity.idHideSubSectionList != null && activity.idHideSubSectionList.size() > 0) {
            activity.idHideSubSectionList.removeAll(idShowSectionList);
        }

        if(activity.idHideQuestionList != null && activity.idHideQuestionList.size() > 0) {
            activity.idHideQuestionList.removeAll(idQuestionShowList);
        }

        if(activity.idHideFieldList != null && activity.idHideFieldList.size() > 0) {
            activity.idHideFieldList.removeAll(idFieldShowList);
        }

        ExpectedAnswerListBean expectedAnswerListBean = BasicMethodsUtil.getInstance().getSelectedAnswerObject(activity.aExpectedAnswerListBeans, buttonId);
        if (expectedAnswerListBean != null) {
            if (expectedAnswerListBean.getHideSectionId() != null && !TextUtils.isEmpty(expectedAnswerListBean.getHideSectionId())) {
                ArrayList<String> idArray = new ArrayList<>(Arrays.asList(expectedAnswerListBean.getHideSectionId().split(",")));
                if (idArray != null && idArray.size() > 0) {
                    for (int i = 0; i < idArray.size(); i++) {
                        idSectinHideList.add(Integer.parseInt(idArray.get(i)));
                    }
                    for (int i = 0; i < idSectinHideList.size(); i++) {
                        int hideId = idSectinHideList.get(i);
                        CustomLinearLayout customLinearLayout = activity.findViewById(hideId);
                        if(customLinearLayout != null)
                            customLinearLayout.setVisibility(View.GONE);
                    }
                    if(activity.idShowSubSectionList != null && activity.idShowSubSectionList.size() > 0) {
                        activity.idShowSubSectionList.removeAll(idSectinHideList);
                    }
                    for (int i = 0; i < activity.aPageSectionListBean.size(); i++) {
                        for (int j = 0; j < idSectinHideList.size(); j++) {
                            int hideId = idSectinHideList.get(j);
                            if (activity.aPageSectionListBean.get(i).getPageSection_Id() == hideId) {
                                ServerRequest.setServerRequest(activity, activity.aPageSectionListBean.get(i).getPageSection_Name(), GenericConstant.SECTION_VISIBLE, "false", null);
                                RemoveValue.removeSectionValue(activity, activity.aPageSectionListBean.get(i).getPageSection_Name(), activity.aPageSectionListBean.get(i).getExpectedQuestionList());
                            }
                        }
                    }
                }
            }
            if (expectedAnswerListBean.getHideQuestionId() != null && !TextUtils.isEmpty(expectedAnswerListBean.getHideQuestionId())) {
                ArrayList<String> idArray = new ArrayList<>(Arrays.asList(expectedAnswerListBean.getHideQuestionId().split(",")));
                if (idArray != null && idArray.size() > 0) {
                    for (int i = 0; i < idArray.size(); i++) {
                        idQuestionHideList.add(Integer.parseInt(idArray.get(i)));
                    }
                    if(activity.idShowQuestionList != null && activity.idShowQuestionList.size() > 0) {
                        activity.idShowQuestionList.removeAll(idQuestionHideList);
                    }
                }
            }
            if (expectedAnswerListBean.getHideFieldId() != null && !TextUtils.isEmpty(expectedAnswerListBean.getHideFieldId())) {
                ArrayList<String> idArray = new ArrayList<>(Arrays.asList(expectedAnswerListBean.getHideFieldId().split(",")));
                if (idArray != null && idArray.size() > 0) {
                    for (int i = 0; i < idArray.size(); i++) {
                        idFieldHideList.add(Integer.parseInt(idArray.get(i)));
                    }
                    if(activity.idShowFieldList != null && activity.idShowFieldList.size() > 0) {
                        activity.idShowFieldList.remove(idFieldHideList);
                    }
                }
            }
        }
        activity.idHideSubSectionList.addAll(idSectinHideList);
        activity.idHideSubSectionList = new ArrayList<Integer>(new LinkedHashSet<Integer>(activity.idHideSubSectionList));
        activity.idHideQuestionList.addAll(idQuestionHideList);
        activity.idHideQuestionList = new ArrayList<Integer>(new LinkedHashSet<Integer>(activity.idHideQuestionList));
        activity.idHideFieldList.addAll(idFieldHideList);
        activity.idHideFieldList = new ArrayList<Integer>(new LinkedHashSet<Integer>(activity.idHideFieldList));

        /*for (int i = 0; i < activity.idHideSubSectionList.size(); i++) {
            Log.e("HIDE ", "ID >>>>> " + activity.idHideSubSectionList.get(i));
        }

        for (int i = 0; i < activity.idShowSubSectionList.size(); i++) {
            Log.e("SHOW ", "ID >>>>> " + activity.idShowSubSectionList.get(i));
        }*/
    }

    public static void showHideFromDropDown(ProcessCreationActivity activity, int buttonId) {
        ArrayList<Integer> idSectinHideList = new ArrayList<>();
        ArrayList<Integer> idShowSectionList = new ArrayList<>();
        ArrayList<Integer> idQuestionHideList = new ArrayList<>();
        ArrayList<Integer> idQuestionShowList = new ArrayList<>();
        ArrayList<Integer> idFieldHideList = new ArrayList<>();
        ArrayList<Integer> idFieldShowList = new ArrayList<>();

        for (int i = 0; i < activity.aExpectedAnswerListBeans.size(); i++) {
            if (activity.aExpectedAnswerListBeans.get(i).getShowSectionId() != null && !TextUtils.isEmpty(activity.aExpectedAnswerListBeans.get(i).getShowSectionId())) {
                ArrayList<String> idTempArray = new ArrayList<>(Arrays.asList(activity.aExpectedAnswerListBeans.get(i).getShowSectionId().split(",")));
                for (int j = 0; j < idTempArray.size(); j++) {
                    idShowSectionList.add(Integer.parseInt(idTempArray.get(j)));
                }
            }
            if (activity.aExpectedAnswerListBeans.get(i).getShowQuestionId() != null && !TextUtils.isEmpty(activity.aExpectedAnswerListBeans.get(i).getShowQuestionId())) {
                ArrayList<String> idTempArray = new ArrayList<>(Arrays.asList(activity.aExpectedAnswerListBeans.get(i).getShowQuestionId().split(",")));
                for (int j = 0; j < idTempArray.size(); j++) {
                    idQuestionShowList.add(Integer.parseInt(idTempArray.get(j)));
                }
            }
            if (activity.aExpectedAnswerListBeans.get(i).getShowFieldId() != null && !TextUtils.isEmpty(activity.aExpectedAnswerListBeans.get(i).getShowFieldId())) {
                ArrayList<String> idTempArray = new ArrayList<>(Arrays.asList(activity.aExpectedAnswerListBeans.get(i).getShowFieldId().split(",")));
                for (int j = 0; j < idTempArray.size(); j++) {
                    idFieldShowList.add(Integer.parseInt(idTempArray.get(j)));
                }
            }
        }
        idShowSectionList = new ArrayList<Integer>(new LinkedHashSet<Integer>(idShowSectionList));
        activity.idShowSubSectionList.addAll(idShowSectionList);
        activity.idShowSubSectionList = new ArrayList<Integer>(new LinkedHashSet<Integer>(activity.idShowSubSectionList));

        idQuestionShowList = new ArrayList<Integer>(new LinkedHashSet<Integer>(idQuestionShowList));
        activity.idShowQuestionList.addAll(idQuestionShowList);
        activity.idShowQuestionList = new ArrayList<Integer>(new LinkedHashSet<Integer>(activity.idShowQuestionList));

        idFieldShowList = new ArrayList<Integer>(new LinkedHashSet<Integer>(idFieldShowList));
        activity.idShowFieldList.addAll(idFieldShowList);
        activity.idShowFieldList = new ArrayList<Integer>(new LinkedHashSet<Integer>(activity.idShowFieldList));

        for (int i = 0; i < idShowSectionList.size(); i++) {
            int showId = idShowSectionList.get(i);
            CustomLinearLayout customLinearLayout = activity.findViewById(showId);
            if(customLinearLayout != null)
                customLinearLayout.setVisibility(View.VISIBLE);
            if (activity.aPageSectionListBean.get(i).getPageSection_Id() == showId) {
                ServerRequest.setServerRequest(activity, activity.aPageSectionListBean.get(i).getPageSection_Name(), GenericConstant.SECTION_VISIBLE, "true", null);
            }
        }
        if(activity.idHideSubSectionList != null && activity.idHideSubSectionList.size() > 0) {
            activity.idHideSubSectionList.removeAll(idShowSectionList);
        }

        if(activity.idHideQuestionList != null && activity.idHideQuestionList.size() > 0) {
            activity.idHideQuestionList.removeAll(idQuestionShowList);
        }

        if(activity.idHideFieldList != null && activity.idHideFieldList.size() > 0) {
            activity.idHideFieldList.removeAll(idFieldShowList);
        }

        ExpectedAnswerListBean expectedAnswerListBean = BasicMethodsUtil.getInstance().getSelectedAnswerObject(activity.aExpectedAnswerListBeans, buttonId);
        if (expectedAnswerListBean != null) {
            if (expectedAnswerListBean.getHideSectionId() != null && !TextUtils.isEmpty(expectedAnswerListBean.getHideSectionId())) {
                ArrayList<String> idArray = new ArrayList<>(Arrays.asList(expectedAnswerListBean.getHideSectionId().split(",")));
                if (idArray != null && idArray.size() > 0) {
                    for (int i = 0; i < idArray.size(); i++) {
                        idSectinHideList.add(Integer.parseInt(idArray.get(i)));
                    }
                    for (int i = 0; i < idSectinHideList.size(); i++) {
                        int hideId = idSectinHideList.get(i);
                        CustomLinearLayout customLinearLayout = activity.findViewById(hideId);
                        if(customLinearLayout != null)
                            customLinearLayout.setVisibility(View.GONE);
                    }
                    if(activity.idShowSubSectionList != null && activity.idShowSubSectionList.size() > 0) {
                        activity.idShowSubSectionList.removeAll(idSectinHideList);
                    }
                    for (int i = 0; i < activity.aPageSectionListBean.size(); i++) {
                        for (int j = 0; j < idSectinHideList.size(); j++) {
                            int hideId = idSectinHideList.get(j);
                            if (activity.aPageSectionListBean.get(i).getPageSection_Id() == hideId) {
                                ServerRequest.setServerRequest(activity, activity.aPageSectionListBean.get(i).getPageSection_Name(), GenericConstant.SECTION_VISIBLE, "false", null);
                                RemoveValue.removeSectionValue(activity, activity.aPageSectionListBean.get(i).getPageSection_Name(), activity.aPageSectionListBean.get(i).getExpectedQuestionList());
                            }
                        }
                    }
                }
            }
            if (expectedAnswerListBean.getHideQuestionId() != null && !TextUtils.isEmpty(expectedAnswerListBean.getHideQuestionId())) {
                ArrayList<String> idArray = new ArrayList<>(Arrays.asList(expectedAnswerListBean.getHideQuestionId().split(",")));
                if (idArray != null && idArray.size() > 0) {
                    for (int i = 0; i < idArray.size(); i++) {
                        idQuestionHideList.add(Integer.parseInt(idArray.get(i)));
                    }
                    if(activity.idShowQuestionList != null && activity.idShowQuestionList.size() > 0) {
                        activity.idShowQuestionList.removeAll(idQuestionHideList);
                    }
                }
            }
            if (expectedAnswerListBean.getHideFieldId() != null && !TextUtils.isEmpty(expectedAnswerListBean.getHideFieldId())) {
                ArrayList<String> idArray = new ArrayList<>(Arrays.asList(expectedAnswerListBean.getHideFieldId().split(",")));
                if (idArray != null && idArray.size() > 0) {
                    for (int i = 0; i < idArray.size(); i++) {
                        idFieldHideList.add(Integer.parseInt(idArray.get(i)));
                    }
                    if(activity.idShowFieldList != null && activity.idShowFieldList.size() > 0) {
                        activity.idShowFieldList.remove(idFieldHideList);
                    }
                }
            }
        }
        activity.idHideSubSectionList.addAll(idSectinHideList);
        activity.idHideSubSectionList = new ArrayList<Integer>(new LinkedHashSet<Integer>(activity.idHideSubSectionList));
        activity.idHideQuestionList.addAll(idQuestionHideList);
        activity.idHideQuestionList = new ArrayList<Integer>(new LinkedHashSet<Integer>(activity.idHideQuestionList));
        activity.idHideFieldList.addAll(idFieldHideList);
        activity.idHideFieldList = new ArrayList<Integer>(new LinkedHashSet<Integer>(activity.idHideFieldList));

        /*for (int i = 0; i < activity.idHideSubSectionList.size(); i++) {
            Log.e("HIDE ", "ID >>>>> " + activity.idHideSubSectionList.get(i));
        }

        for (int i = 0; i < activity.idShowSubSectionList.size(); i++) {
            Log.e("SHOW ", "ID >>>>> " + activity.idShowSubSectionList.get(i));
        }*/
    }
}
