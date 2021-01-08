package hcl.policing.digitalpolicingplatform.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CompareDate {

    public static int compareDates(String startDate, String endDate) {
        try {
            Log.e("DATES ", " >> "+startDate + " >>>>> " + endDate);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
            Date dateStart = sdf.parse(startDate);
            Date dateEnd = sdf.parse(endDate);

            assert dateStart != null;
            if (dateStart.compareTo(dateEnd) > 0) {
                System.out.println("start is after end");
                return 1;
            } else if (dateStart.compareTo(dateEnd) < 0) {
                System.out.println("start is before end");
                return 2;
            } else if (dateStart.compareTo(dateEnd) == 0) {
                System.out.println("start is equal to end");
                return 3;
            } else {
                System.out.println("Something weird happened...");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
