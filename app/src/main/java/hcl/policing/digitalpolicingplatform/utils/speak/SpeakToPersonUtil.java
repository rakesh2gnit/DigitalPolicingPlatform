package hcl.policing.digitalpolicingplatform.utils.speak;

import android.content.Context;
import android.speech.tts.TextToSpeech;

public class SpeakToPersonUtil {

    public static void speakToPerson(Context context, TextToSpeech textToSpeech, String textToSpeak) {
        textToSpeech.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, "");
    }
}
