package hcl.policing.digitalpolicingplatform.utils;

import android.util.Log;

import okhttp3.internal.http2.ErrorCode;

public class ExceptionLogger extends Exception {

    public static void Logger(Throwable cause, String message, ErrorCode code) {

    }

    public static void Logger(Throwable cause, String message, Class className, String function) {
        Log.e("EXCEPTION ", " abc>>>>>   " + message + "  >>  "
                + cause + " class >> " + className.toString() + " fun>> " + function);
    }

}
