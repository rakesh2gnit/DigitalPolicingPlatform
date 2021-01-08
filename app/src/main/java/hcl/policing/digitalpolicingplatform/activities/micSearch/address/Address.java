package hcl.policing.digitalpolicingplatform.activities.micSearch.address;

import android.os.Handler;
import android.speech.tts.TextToSpeech;

import hcl.policing.digitalpolicingplatform.activities.micSearch.MicSearchActivity;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.utils.SearchDialogUtil;
import hcl.policing.digitalpolicingplatform.utils.speak.SpeakToPersonUtil;

public class Address {

    public void addressAction(MicSearchActivity context, TextToSpeech textToSpeech, String results) {

        setFlagValues(context, true, false);
        context.stopAnimation();
        SearchDialogUtil.createSearchDialog(context, context.aSearchListBean, GenericConstant.SEARCH_ADDRESS, null, GenericConstant.SPEAKING_NAME, "");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                context.isListen = false;
                SpeakToPersonUtil.speakToPerson(context, textToSpeech, "Please fill any of the 2 fields and click search");
            }
        }, 1500);
    }

    public void setFlagValues(MicSearchActivity act, boolean isSearchName, boolean isSearchReason){
        act.isName = isSearchName;
        act.isReason = isSearchReason;
    }
}
