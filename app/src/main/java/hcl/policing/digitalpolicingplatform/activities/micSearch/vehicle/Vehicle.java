package hcl.policing.digitalpolicingplatform.activities.micSearch.vehicle;

import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import hcl.policing.digitalpolicingplatform.activities.micSearch.MicSearchActivity;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.utils.SearchDialogUtil;
import hcl.policing.digitalpolicingplatform.utils.speak.SpeakToPersonUtil;

public class Vehicle {

    public void vehicleAction(MicSearchActivity context, TextToSpeech textToSpeech, String results) {
        String result = results;
        if(result.contains("vehicle")) {
            String substr = "vehicle";
            String after = result.substring(result.indexOf(substr) + substr.length()).trim();
            Log.e("vrm ", ">>>>> "+after);
            if(!after.equalsIgnoreCase("")) {
                SearchDialogUtil.createSearchDialog(context, context.aSearchListBean, GenericConstant.SEARCH_VEHICLE, null, GenericConstant.SPEAKING_NAME, after);
                setFlagValues(context, false, true);
                context.stopAnimation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        context.isListen = true;
                        SpeakToPersonUtil.speakToPerson(context, textToSpeech, "Please tell me the reason for search");
                    }
                }, 1000);
            } else {
                setFlagValues(context, true, false);
                context.isListen = true;
                SpeakToPersonUtil.speakToPerson(context, textToSpeech, "Please tell me the Vehicle VRM you wish to search?");
            }
            //String[] persons = result.split(" ");

        } else if(context.isName){
            SearchDialogUtil.createSearchDialog(context, context.aSearchListBean, GenericConstant.SEARCH_VEHICLE, null, GenericConstant.SPEAKING_NAME, result);
            setFlagValues(context, false, true);
            context.stopAnimation();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    context.isListen = true;
                    SpeakToPersonUtil.speakToPerson(context, textToSpeech, "Please tell me the reason for search");
                }
            }, 1000);
        } else if(context.isReason) {
            for (int i = 0; i < SearchDialogUtil.answerList.size(); i++) {
                if(SearchDialogUtil.answerList.get(i).getKey().equalsIgnoreCase("Reason For Search")) {
                    if(result != null) {
                        SearchDialogUtil.answerList.get(i).setValue(result);
                    }
                }
            }
            SearchDialogUtil.setValueInDialog(context, SearchDialogUtil.answerList);

            setFlagValues(context, false, false);
            context.stopAnimation();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SearchDialogUtil.dismiss();
                    SearchDialogUtil.callSearch(context, GenericConstant.SEARCH_VEHICLE);
                }
            }, 1500);

            //PersonSearch personSearch = new PersonSearch();
            //personSearch.doNominalSearch(context, fName, lName);
        }
    }

    public void setFlagValues(MicSearchActivity act, boolean isSearchName, boolean isSearchReason){
        act.isName = isSearchName;
        act.isReason = isSearchReason;
    }
}
