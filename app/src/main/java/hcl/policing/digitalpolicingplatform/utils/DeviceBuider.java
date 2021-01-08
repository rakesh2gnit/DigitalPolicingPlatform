package hcl.policing.digitalpolicingplatform.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;
import android.util.SparseArray;

import java.util.regex.Pattern;

import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;

import static android.content.ContentValues.TAG;

public class DeviceBuider {

    private static final DeviceBuider ourInstance = new DeviceBuider();

    public static DeviceBuider getInstance() {
        return ourInstance;
    }

    private DeviceBuider() {
    }

    /**
     * Get the IMEI number of Device
     *
     * @param context
     * @return
     */
    public String getIMEINumber(Context context) {

        String deviceUniqueIdentifier = null;

        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            if (null != telephonyManager) {
                int simCount = telephonyManager.getPhoneCount();
                if (simCount > 1) {
                    deviceUniqueIdentifier = telephonyManager.getDeviceId(0);
                } else {
                    deviceUniqueIdentifier = telephonyManager.getDeviceId();
                }
            }
            if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
                deviceUniqueIdentifier = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return deviceUniqueIdentifier;
    }


    /**
     * Get Device Email Address
     *
     * @param context
     * @return
     */
    public String getEmail(Context context, String mailpattern) {

        String emailStr = null;
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;

        try {
            // Getting all registered Google Accounts;
            Account[] accounts = AccountManager.get(context).getAccountsByType(mailpattern);

            // Getting all registered Accounts;
            // Account[] accounts = AccountManager.get(context).getAccounts();

            for (Account account : accounts) {
                if (emailPattern.matcher(account.name).matches()) {
                    emailStr = account.name;
                    Log.d(TAG, String.format("%s - %s", emailStr, account.type));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emailStr;
    }


    /**
     * Get the Device Details
     *
     * @param context
     * @return
     */
    public SparseArray<String> getDeviceDetails(Context context) {

        SparseArray<String> stringSparseArray = new SparseArray<>();
        stringSparseArray.put(GenericConstant.SERIAL_NO, Build.SERIAL);
        stringSparseArray.put(GenericConstant.MODEL, Build.MODEL);
        stringSparseArray.put(GenericConstant.MANUFACTURER, Build.MANUFACTURER);
        stringSparseArray.put(GenericConstant.BRAND, Build.BRAND);
        stringSparseArray.put(GenericConstant.TYPE, Build.TYPE);
        stringSparseArray.put(GenericConstant.USER, Build.USER);
        stringSparseArray.put(GenericConstant.VERSION_CODE_BASE, String.valueOf(Build.VERSION_CODES.BASE));
        stringSparseArray.put(GenericConstant.VERSION_INCREMENTAL, Build.VERSION.INCREMENTAL);
        stringSparseArray.put(GenericConstant.VERSION_SDK, String.valueOf(Build.VERSION.SDK_INT));
        stringSparseArray.put(GenericConstant.BOARD, Build.BOARD);
        stringSparseArray.put(GenericConstant.HOST, Build.HOST);
        stringSparseArray.put(GenericConstant.FINGERPRINT, Build.FINGERPRINT);
        stringSparseArray.put(GenericConstant.RELEASE, Build.VERSION.RELEASE);

        return stringSparseArray;
    }
}
