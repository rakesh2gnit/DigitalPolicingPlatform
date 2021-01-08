package hcl.policing.digitalpolicingplatform.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.constants.api.ApiConstants;
import hcl.policing.digitalpolicingplatform.models.process.ExpectedAnswerListBean;
import hcl.policing.digitalpolicingplatform.models.process.ExpectedQuestionListBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.PersonAthenaResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.dl.DrivinglicencebynamelistModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.nflms.PersonNFLMSResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.pnc.PersonPNCResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.qas.CitizensModel;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;

public class BasicMethodsUtil {
    private static final BasicMethodsUtil ourInstance = new BasicMethodsUtil();

    public static BasicMethodsUtil getInstance() {
        return ourInstance;
    }

    private BasicMethodsUtil() {
    }

    /**
     * Launch the activity of define type class
     *
     * @param mContext
     * @param cls
     */
    public void launchActivity(Context mContext, Class<?> cls) {
        Intent _intent = new Intent(mContext, cls);
        mContext.startActivity(_intent);
        ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    /**
     * Launch initial activity
     *
     * @param mContext
     * @param cls
     */
    public void launchInitialActivity(Context mContext, Class<?> cls) {
        Intent _intent = new Intent(mContext, cls);
        _intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(_intent);
        ((Activity) mContext).finish();
        ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    /**
     * method for launch the activity
     *
     * @param mContext
     * @param cls
     * @param bundle
     */
    public void launchActivity(Context mContext, Class<?> cls, Bundle bundle) {
        Intent _intent = new Intent(mContext, cls);
        if (bundle != null) {
            _intent.putExtras(bundle);
        }
        mContext.startActivity(_intent);
        ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }


    /**
     * Show the toast message
     *
     * @param context
     * @param message
     */
    public void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * No Data available
     *
     * @param recyclerView
     * @param tvNoData
     */
    public void noDataAvailable(RecyclerView recyclerView, TextView tvNoData) {
        recyclerView.setVisibility(View.GONE);
        tvNoData.setVisibility(View.VISIBLE);
    }

    /**
     * Check the session is active
     *
     * @param context
     * @return
     */
    public boolean isSessionActive(Context context) {

        boolean isActive = false;
        long lastCalled = SharedPrefUtils.getInstance(context).getLong(SharedPrefUtils.Key.LAST_TOKEN_TIME, 0);
        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastCalled) < ApiConstants.LAST_HIT_TIME) {
            lastCalled = currentTime;
            SharedPrefUtils.getInstance(context).setLong(SharedPrefUtils.Key.LAST_TOKEN_TIME, lastCalled);
            isActive = true;
        }
        return isActive;
    }

    /**
     * Replace the blank space with _
     *
     * @param inputData
     * @return
     */
    public String getServerName(String inputData) {
        String serverName = null;

        try {
            serverName = inputData.replaceAll("\\(", "").replaceAll("\\)", "");
            serverName = serverName.replaceAll("\\?", "");
            serverName = serverName.replaceAll("[^a-zA-Z0-9]", "_");
            serverName = serverName.replaceAll("__", "_");

        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "getServerName");
        }

        return serverName;
    }

    public String getNormalServerName(String inputData) {
        String serverName = null;

        try {
            //serverName = inputData.replaceAll("\\(", "").replaceAll("\\)", "");
            //serverName = serverName.replaceAll("\\?", "");
            //serverName = serverName.replaceAll("[^a-zA-Z0-9]", "_");
            serverName = inputData.replaceAll("_", " ");

        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "getServerName");
        }

        return serverName;
    }


    /**
     * Search Person On PNC Server
     *
     * @param pncPersonlistBeans
     * @param personName
     * @return
     */
    public ArrayList<PersonPNCResponse.PNCPersonlistBean> searchPNCPerson(ArrayList<PersonPNCResponse.PNCPersonlistBean> pncPersonlistBeans, String personName) {

        ArrayList<PersonPNCResponse.PNCPersonlistBean> aPNCPersonlistBean = new ArrayList<>();
        aPNCPersonlistBean.clear();

        try {
            for (PersonPNCResponse.PNCPersonlistBean personlistBean : pncPersonlistBeans) {

                if (personName.toLowerCase().contains(personlistBean.getFirstname().toLowerCase())
                        || personName.toLowerCase().contains(personlistBean.getLastname().toLowerCase())) {
                    aPNCPersonlistBean.add(personlistBean);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aPNCPersonlistBean;
    }


    /**
     * Search Person On PNC Server
     *
     * @param personlistBeans
     * @param personName
     * @return
     */
    public ArrayList<PersonAthenaResponse.AthenaPersonlistBean> searchAthenaPerson(ArrayList<PersonAthenaResponse.AthenaPersonlistBean> personlistBeans, String personName) {

        ArrayList<PersonAthenaResponse.AthenaPersonlistBean> aAthenaPersonlistBean = new ArrayList<>();
        aAthenaPersonlistBean.clear();

        try {
            for (PersonAthenaResponse.AthenaPersonlistBean model : personlistBeans) {

                if (personName.toLowerCase().contains(model.getFirstname1().toLowerCase())
                        || personName.toLowerCase().contains(model.getLastname().toLowerCase())) {
                    aAthenaPersonlistBean.add(model);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aAthenaPersonlistBean;
    }

    /**
     * Serach Person in DL Search
     *
     * @param personlistBeans
     * @param personName
     * @return
     */
    public ArrayList<DrivinglicencebynamelistModel> searchDLPerson(ArrayList<DrivinglicencebynamelistModel> personlistBeans, String personName) {

        ArrayList<DrivinglicencebynamelistModel> aDrivingLicenceByNameListBean = new ArrayList<>();
        aDrivingLicenceByNameListBean.clear();

        try {
            for (DrivinglicencebynamelistModel model : personlistBeans) {

                if (personName.toLowerCase().contains(model.getName().toLowerCase())) {
                    aDrivingLicenceByNameListBean.add(model);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aDrivingLicenceByNameListBean;
    }

    /**
     * Serach Person in QAS Search
     *
     * @param personlistBeans
     * @param personName
     * @return
     */
    public ArrayList<CitizensModel> searchQASPerson(ArrayList<CitizensModel> personlistBeans, String personName) {

        ArrayList<CitizensModel> aDrivingLicenceByNameListBean = new ArrayList<>();
        aDrivingLicenceByNameListBean.clear();

        try {
            for (CitizensModel model : personlistBeans) {

                if (personName.toLowerCase().contains(model.getDisplayname().toLowerCase())) {
                    aDrivingLicenceByNameListBean.add(model);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aDrivingLicenceByNameListBean;
    }

    /**
     * Serach Person in DL Search
     *
     * @param personlistBeans
     * @param personName
     * @return
     */
    public ArrayList<PersonNFLMSResponse.SearchResultBean> searchNFLMSPerson(ArrayList<PersonNFLMSResponse.SearchResultBean> personlistBeans, String personName) {

        ArrayList<PersonNFLMSResponse.SearchResultBean> aSearchResultBean = new ArrayList<>();
        aSearchResultBean.clear();

        try {
            for (PersonNFLMSResponse.SearchResultBean model : personlistBeans) {

                if (personName.toLowerCase().contains(model.getName().toLowerCase())) {
                    aSearchResultBean.add(model);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aSearchResultBean;
    }


    /**
     * Get the Selected Answerlist Object
     *
     * @param aExpectedAnswerListBeans
     * @param buttonId
     * @return
     */
    public ExpectedAnswerListBean getSelectedAnswerObject(List<ExpectedAnswerListBean> aExpectedAnswerListBeans, int buttonId) {
        ExpectedAnswerListBean expectedAnswerListBean = null;
        try {
            for (ExpectedAnswerListBean model : aExpectedAnswerListBeans) {
                if (buttonId == model.getButton_Id()) {
                    expectedAnswerListBean = model;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expectedAnswerListBean;
    }

    /**
     * Get the Selected Answerlist Object
     *
     * @param aExpectedAnswerListBeans
     * @param buttonText
     * @return
     */
    public ExpectedAnswerListBean getSelectedAnswerObject(List<ExpectedAnswerListBean> aExpectedAnswerListBeans, String buttonText) {
        ExpectedAnswerListBean expectedAnswerListBean = null;
        try {
            for (ExpectedAnswerListBean model : aExpectedAnswerListBeans) {
                if (buttonText.equalsIgnoreCase(model.getAnswer())) {
                    expectedAnswerListBean = model;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expectedAnswerListBean;
    }

    public ExpectedQuestionListBean getSelectedQuestionObject(List<ExpectedQuestionListBean> aExpectedQuestionListBeans, int buttonId) {
        ExpectedQuestionListBean expectedQuestionListBean = null;
        try {
            for (ExpectedQuestionListBean model : aExpectedQuestionListBeans) {
                if (buttonId == model.getQuestion_Id()) {
                    expectedQuestionListBean = model;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expectedQuestionListBean;
    }
}
