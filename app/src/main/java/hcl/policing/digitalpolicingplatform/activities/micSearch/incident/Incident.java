package hcl.policing.digitalpolicingplatform.activities.micSearch.incident;

import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import hcl.policing.digitalpolicingplatform.activities.micSearch.MicSearchActivity;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.utils.SearchDialogUtil;
import hcl.policing.digitalpolicingplatform.utils.speak.SpeakToPersonUtil;

public class Incident {

    public void incidentAction(MicSearchActivity context, TextToSpeech textToSpeech, String results) {

        String result = results;
        if(result.contains("incident")) {
            String substr = "incident";
            String after = result.substring(result.indexOf(substr) + substr.length()).trim();
            Log.e("incidentNo ", ">>>>> "+after);
            if(!after.equalsIgnoreCase("")) {
                SearchDialogUtil.createSearchDialog(context, context.aSearchListBean, GenericConstant.SEARCH_INCIDENT, null, GenericConstant.SPEAKING_NAME, after);
                setFlagValues(context, false, false);
                context.stopAnimation();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SearchDialogUtil.dismiss();
                        SearchDialogUtil.callSearch(context, GenericConstant.SEARCH_INCIDENT);
                    }
                }, 1500);
            } else {
                setFlagValues(context, true, false);
                context.isListen = true;
                SpeakToPersonUtil.speakToPerson(context, textToSpeech, "Please tell me the incident number you wish to search?");
            }
            //String[] persons = result.split(" ");

        } else if(context.isName){
            SearchDialogUtil.createSearchDialog(context, context.aSearchListBean, GenericConstant.SEARCH_INCIDENT, null, GenericConstant.SPEAKING_NAME, result);
            setFlagValues(context, false, false);
            context.stopAnimation();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SearchDialogUtil.dismiss();
                    SearchDialogUtil.callSearch(context, GenericConstant.SEARCH_INCIDENT);
                }
            }, 1500);
        }
    }

    public void setFlagValues(MicSearchActivity act, boolean isSearchName, boolean isSearchReason){
        act.isName = isSearchName;
        act.isReason = isSearchReason;
    }
}
