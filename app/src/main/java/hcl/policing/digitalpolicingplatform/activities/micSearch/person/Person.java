package hcl.policing.digitalpolicingplatform.activities.micSearch.person;

import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import hcl.policing.digitalpolicingplatform.activities.micSearch.MicSearchActivity;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.utils.SearchDialogUtil;
import hcl.policing.digitalpolicingplatform.utils.speak.SpeakToPersonUtil;

public class Person {

    public void personAction(MicSearchActivity context, TextToSpeech textToSpeech, String results) {

        String result = results;
        if(result.contains("person")) {
            String substr = "person";
            String after = result.substring(result.indexOf(substr) + substr.length()).trim();
            Log.e("name ", ">>>>> "+after);
            if(!after.equalsIgnoreCase("")) {
                SearchDialogUtil.createSearchDialog(context, context.aSearchListBean, GenericConstant.SEARCH_PERSON, null, GenericConstant.SPEAKING_NAME, after);
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
                SpeakToPersonUtil.speakToPerson(context, textToSpeech, "Please tell me the Person name you wish to search?");
            }
            //String[] persons = result.split(" ");

        } else if(context.isName){
            if(is_word(result)) {
                context.isListen = true;
                SpeakToPersonUtil.speakToPerson(context, textToSpeech, "Please tell me the full name you wish to search?");
            } else {
                SearchDialogUtil.createSearchDialog(context, context.aSearchListBean, GenericConstant.SEARCH_PERSON, null, GenericConstant.SPEAKING_NAME, result);
                setFlagValues(context, false, true);
                context.stopAnimation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        context.isListen = true;
                        SpeakToPersonUtil.speakToPerson(context, textToSpeech, "Please tell me the reason for search");
                    }
                }, 1000);
            }
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
                    SearchDialogUtil.callSearch(context, GenericConstant.SEARCH_PERSON);
                }
            }, 1500);

            //PersonSearch personSearch = new PersonSearch();
            //personSearch.doNominalSearch(context, fName, lName);
        }
    }

    public static boolean is_word(String s) {
        return (s.length() > 0 && s.split("\\s+").length == 1);
    }

    public void setFlagValues(MicSearchActivity act, boolean isSearchName, boolean isSearchReason){
        act.isName = isSearchName;
        act.isReason = isSearchReason;
    }
}
