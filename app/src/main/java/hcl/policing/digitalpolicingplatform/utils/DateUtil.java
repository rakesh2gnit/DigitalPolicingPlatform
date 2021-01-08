package hcl.policing.digitalpolicingplatform.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static String currentDate() {
        String date = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            date = sdf.format(System.currentTimeMillis());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String currentDateTime() {
        String date = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
            date = sdf.format(System.currentTimeMillis());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String futureDate() {
        String date = "";
        try {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, 1);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            date = sdf.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String futureDateTime() {
        String date = "";
        try {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, 1);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
            date = sdf.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Get the date from file
     *
     * @param fileN
     * @return
     */
    public static String getDateTime(String fileN) {

        String date = "";

        //String fileName = fileN.replace(".txt", "");

        String[] breakFileName = fileN.split("_");

        String timeinMillies = breakFileName[breakFileName.length - 1];

        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.US);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(timeinMillies));
        date = formatter.format(calendar.getTime());

        return date;
    }

    /**
     * Get the date from file
     *
     * @param fileN
     * @return
     */
    public static String getDate(String fileN) {

        String date = "";

        //String fileName = fileN.replace(".txt", "");

        String[] breakFileName = fileN.split("_");

        String timeinMillies = breakFileName[breakFileName.length - 1];

        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(timeinMillies));
        date = formatter.format(calendar.getTime());

        return date;
    }

    /**
     * Get the date from file
     *
     * @param longTime
     * @return
     */
    public static String getBackDate(long longTime) {

        String date = "";

        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(longTime);
        calendar.add(Calendar.DATE, -15);
        date = formatter.format(calendar.getTime());

        return date;
    }

    public static int compareDates(String listDate, String backDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = formatter.parse(listDate);
            d2 = formatter.parse(backDate);
            Log.e("D1 ", " >>>>> " + d1);
            Log.e("D2 ", " >>>>> " + d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(d1.compareTo(d2) > 0) {
            //System.out.println("Date 1 occurs after Date 2");
            return 0;
        } else if(d1.compareTo(d2) < 0) {
            return 1;
            //System.out.println("Date 1 occurs before Date 2");
        } else if(d1.compareTo(d2) == 0) {
            return 0;
            //System.out.println("Both dates are equal");
        }
        return 0;
    }
}
