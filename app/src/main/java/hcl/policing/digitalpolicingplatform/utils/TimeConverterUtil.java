package hcl.policing.digitalpolicingplatform.utils;

import android.util.Log;

public class TimeConverterUtil {

    public static String convertTime(String value) {
        String requiredTime = "";
        value = padLeftZeros(value, 4);
        String hours = value.substring(0, 2);
        String minutes = value.substring(2, 4);
        Log.e("testtime", ">>>>>>>>>>>>>>>>>>" + hours + ">>" + minutes);
        int displayhours = Integer.parseInt(hours) / 24;
        if (displayhours > 0)
            requiredTime = displayhours + " days";
        else
            requiredTime = hours + ":" + minutes + " hours";
        return requiredTime;
    }

    private static String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);
        return sb.toString();
    }
}
