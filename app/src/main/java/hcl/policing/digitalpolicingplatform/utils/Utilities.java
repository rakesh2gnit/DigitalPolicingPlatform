package hcl.policing.digitalpolicingplatform.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import hcl.policing.digitalpolicingplatform.BuildConfig;
import hcl.policing.digitalpolicingplatform.constants.api.ApiConstants;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by devendra on 4/7/16.
 */
public class Utilities implements ApiConstants, GenericConstant {
    private ConnectivityManager cm;
    private static Context context;
    private static Utilities singleton = null;
    public static String input = "yyyy-MM-dd hh:mm:ss";
    public static String outPut = "MMM dd, yyyy,  hh:mm a";

    public static String inputForAcc = "yyyy-MM-dd";
    public static String outPutForAcc = "MMM dd, yyyy";
    public static String outPutForedit = "dd-MM-yyyy";

    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private Utilities() {
    }

    /* Static 'instance' method */
    public static Utilities getInstance(Context mContext) {
        context = mContext;
        if (singleton == null)
            singleton = new Utilities();
        return singleton;
    }

    /**
     * Method for checking network availability
     */

    public boolean isNetworkAvailable() {
        try {
            cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);

            if (cm != null) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
                    if (capabilities != null) {
                        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                            return true;
                        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                            return true;
                        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                            return true;
                        }
                    }
                } else {

                    try {
                        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
                        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                            Log.i("update_statut", "Network is available : true");
                            return true;
                        }
                    } catch (Exception e) {
                        Log.i("update_statut", "" + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("update_statut", "Network is available : FALSE ");
        return false;
    }

    /**
     * Method for checking network availability
     */
    public boolean isNetworkAvailable(String s) {
        try {
            cm = (ConnectivityManager) context
                    .getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();

            // if no network is available networkInfo will be null
            // otherwise check if we are connected
            if (networkInfo != null && networkInfo.isConnected()) {
                if (isConnectionFast(networkInfo.getType(), networkInfo.getSubtype())) {
                    return true;
                } else {
                    dialogOK(context, "", "Your connection is too low", "OK", false);
                    return false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Check if the connection is fast
     *
     * @param type
     * @param subType
     * @return
     */

    public boolean isConnectionFast(int type, int subType) {
        if (type == ConnectivityManager.TYPE_WIFI) {
            Log.i(getClass().getName(), "Wifi State");
            return true;
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    Log.i(getClass().getName(), "50 - 100 kbps");
                    return true; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    Log.i(getClass().getName(), "14 - 64 kbps");
                    return true; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    Log.i(getClass().getName(), "50 - 100 kbps");
                    return true; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    Log.i(getClass().getName(), "400 - 1000 kbps");
                    return true; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    Log.i(getClass().getName(), "600 - 1400 kbps");
                    return true; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    Log.i(getClass().getName(), " 100 kbps");
                    return false; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    Log.i(getClass().getName(), "2 - 14 Mbps");
                    return true; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    Log.i(getClass().getName(), "700 - 1700 kbps");
                    return true; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    Log.i(getClass().getName(), "1 - 23 Mbps");
                    return true; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    Log.i(getClass().getName(), "400 - 7000 kbps");
                    return true; // ~ 400-7000 kbps
                /*
                 * Above API level 7, make sure to set android:targetSdkVersion
                 * to appropriate level to use these
                 */
                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                    Log.i(getClass().getName(), "1 - 2 Mbps");
                    return true; // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                    Log.i(getClass().getName(), "5 Mbps");
                    return true; // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                    Log.i(getClass().getName(), "10 - 20 Mbps");
                    return true; // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                    Log.i(getClass().getName(), "25 kbps");
                    return false; // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                    Log.i(getClass().getName(), "10+ Mbps");
                    return true; // ~ 10+ Mbps
                // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Method for getting device unique number i.e IMEI
     */
    public String getDeviceId() {
        String deviceId = "";
        if (isEmulator()) return "ANDROID_EMULATOR";
        try {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceId;
    }

    @SuppressLint("MissingPermission")
    public String getIMEIorDeviceId() {
        String deviceId = "";
        if (isEmulator()) return "ANDROID_EMULATOR";
        try {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceId;
    }

    private static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT)
                || Build.PRODUCT.equals("sdk")
                || Build.PRODUCT.contains("_sdk")
                || Build.PRODUCT.contains("sdk_");
    }

    /**
     * Method for getting application version code
     */
    public String getAppVersion() {
        String appVersion = "";
        try {
            appVersion = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersion;
    }

    public void exit() {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            ((Activity) context).finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for Checking application running on emulator or real device
     */
    public boolean isAndroidEmulator() {
        String product = Build.PRODUCT;
        boolean isEmulator = false;
        if (product != null) {
            isEmulator = product.equals("sdk") || product.contains("_sdk")
                    || product.contains("sdk_");
        }
        return isEmulator;
    }

    /**
     * method for email validation
     */
    public boolean checkEmail(String email) {
        try {
            return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
        } catch (NullPointerException exception) {
            return false;
        }
    }

    public boolean checkMobile(String mobile) {
        try {
            mobile = mobile.replaceAll("[^0-9]", "");
            return MOBILE_NUMBER_PATTERN.matcher(mobile).matches();
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * method for Password validation
     */
    public boolean checkPassword(String password) {
        try {
            return PASSWORD_PATTERN.matcher(password).matches();
        } catch (NullPointerException exception) {
            return false;
        }
    }

    /**
     * method for Password validation
     */
    public boolean checkPostcode(String postcode) {
        try {
            return POSTCODE_VALIDATION.matcher(postcode).matches();
        } catch (NullPointerException exception) {
            return false;
        }
    }

    MediaPlayer mMediaPlayer;
    AudioManager am;

    public void setSound(int sound) {
        try {
            // Log.i(getClass().getName(), "setSound................");
            int maxVol;
            am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            maxVol = am.getStreamVolume(AudioManager.STREAM_MUSIC);
            am.setStreamVolume(AudioManager.STREAM_MUSIC, maxVol, 0);
            if (mMediaPlayer == null) {
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.setVolume(maxVol, maxVol);
                mMediaPlayer = MediaPlayer.create(context, sound);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSound(int sound) {
        try {
            setSound(sound);
            if (mMediaPlayer != null) {
                if (am.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
                    mMediaPlayer.start();
                }
            }
            // Log.i(getClass().getName(), "playSound.................");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public void vibrate() {
        try {
            // Get instance of Vibrator from current Context
            am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (am.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE
                    || am.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
                Vibrator v = (Vibrator) context
                        .getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public static String getDirPath(Context ctx, String optionalPath) {
        String opPath = GenericConstant.FILE_FOR + new AppSession(ctx).getUserID() + "/";
        String rootPath;
        if (optionalPath != null && !optionalPath.equals("")) {
            rootPath = ctx.getExternalFilesDir(opPath + optionalPath).getAbsolutePath();
        } else {
            rootPath = ctx.getExternalFilesDir(opPath).getAbsolutePath();
        }
        // extraPortion is extra part of file path
        String extraPortion = "Android/data/" + BuildConfig.APPLICATION_ID
                + File.separator + "files" + File.separator;
        // Remove extraPortion
        rootPath = rootPath.replace(extraPortion, "");
        return rootPath;
    }

    /**
     * Write JSON file in internal storage
     *
     * @param jsonStr
     * @param fileName
     * @return
     */
    public String writeFile(String jsonStr, String fileName, String directory) {
        String path = "";
        try {
            String dir = FILE_DIRECTORY + new AppSession(context).getUserID() + directory;  //getDirPath(context, directory);

            File jsonFIle = getNewFile(dir, fileName + ".txt");
            FileOutputStream fOut = new FileOutputStream(jsonFIle);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(jsonStr);
            myOutWriter.close();
            fOut.flush();
            fOut.close();

            path = jsonFIle.getAbsolutePath();

        } catch (IOException e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), Utilities.class, "writeFile");
        }
        return path;
    }

    /**
     * Write JSON file in internal storage
     *
     * @param source
     * @param fileName
     * @return
     */
    public String writeDocFile(File source, String fileName, String directory) {
        String path = "";
        try {
            String dir = FILE_DIRECTORY + new AppSession(context).getUserID() + directory;  //getDirPath(context, directory);

            File jsonFIle = getNewFile(dir, fileName);

            FileChannel in = new FileInputStream(source).getChannel();
            FileChannel out = new FileOutputStream(jsonFIle).getChannel();

            try {
                in.transferTo(0, in.size(), out);
            } catch(Exception e){
                // post to log
            } finally {
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
            }
            path = jsonFIle.getAbsolutePath();
        } catch (IOException e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), Utilities.class, "writeFile");
        }
        return path;
    }

    /**
     * Write JSON file in internal storage
     *
     * @param fileName
     * @return
     */
    public File writeAudioFile(String fileName, String directory) {
        File filepath = null;
        try {
            String dir = FILE_DIRECTORY + new AppSession(context).getUserID() + directory;  //getDirPath(context, directory);

            filepath = getNewFile(dir, fileName + ".3gp");

        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), Utilities.class, "writeFile");
        }
        return filepath;
    }

    public String readfile(String strFileName, String directory) {
        String line = null;
        try {
            String dir = FILE_DIRECTORY + new AppSession(context).getUserID() + directory;  //getDirPath(context, directory);

            File jsonFIle = getNewFile(dir, strFileName + ".txt");
            if (jsonFIle.exists()) {
                FileInputStream fileInputStream = new FileInputStream(jsonFIle); // set file path & name to read
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream); // create input steam reader
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) { // read line by line
                    stringBuilder.append(line + System.getProperty("line.separator")); // append the readed text line by line
                }
                fileInputStream.close();
                line = stringBuilder.toString(); // finially the whole date into an single string
                bufferedReader.close();
            } else {
                Toast.makeText(context, "File does not exists", Toast.LENGTH_SHORT).show();
            }
            //Log.e("DataStoreSD 3.1 ", line);
        } catch (FileNotFoundException ex) {
            Log.e("DataStoreSD 3.2 ", ex.getMessage());
        } catch (IOException ex) {
            Log.e("DataStoreSD 3.3 ", ex.getMessage());
        }
        return line;
    }

    public File readDocFile(String strFileName, String directory) {
        File line = null;
        String dir = FILE_DIRECTORY + new AppSession(context).getUserID() + directory;  //getDirPath(context, directory);

        Log.e("DIRECTORY NAME ", " >> DIR >> " + dir + " >> FILE >> " + strFileName);

        line = getNewFile(dir, strFileName);
        //Log.e("DataStoreSD 3.1 ", line);
        return line;
    }

    public File readAudioFile(String strFileName, String directory) {
        File line = null;
        String dir = FILE_DIRECTORY + new AppSession(context).getUserID() + directory;  //getDirPath(context, directory);

        Log.e("DIRECTORY NAME ", " >> DIR >> " + dir + " >> FILE >> " + strFileName);

        line = getNewFile(dir, strFileName+".3gp");
        //Log.e("DataStoreSD 3.1 ", line);
        return line;
    }

    public void deleteProcessFile(String fileName, String directory) {
        String dir = FILE_DIRECTORY + new AppSession(context).getUserID() + directory;  //getDirPath(context, directory);

        String root = Environment.getExternalStorageDirectory()
                + dir;

        File f = new File(root);
        if (!f.exists()) try {
            Toast.makeText(context, "There is no saved file present.",
                    Toast.LENGTH_SHORT).show();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

        File[] files = f.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().equalsIgnoreCase(fileName + ".txt")) {
                    file.delete();
                    break;
                }
            }
        }
    }

    public void deleteAudioFile(String fileName, String directory) {
        String dir = FILE_DIRECTORY + new AppSession(context).getUserID() + directory;  //getDirPath(context, directory);

        String root = Environment.getExternalStorageDirectory()
                + dir;

        File f = new File(root);
        if (!f.exists()) try {
            Toast.makeText(context, "There is no saved file present.",
                    Toast.LENGTH_SHORT).show();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

        File[] files = f.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().equalsIgnoreCase(fileName + ".3gp")) {
                    file.delete();
                    break;
                }
            }
        }
    }

    public void deleteFile(String fileName, String directory) {
        String root = Environment.getExternalStorageDirectory()
                + directory;

        File f = new File(root);
        if (!f.exists()) try {
            Toast.makeText(context, "There is no saved file present.",
                    Toast.LENGTH_SHORT).show();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

        File[] files = f.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().equalsIgnoreCase(fileName)) {
                    file.delete();
                    break;
                }
            }
        }
    }

    public void deleteFileFromPath(String path) {

        File f = new File(path);
        if (!f.exists()) try {
            Toast.makeText(context, "There is no saved file present.",
                    Toast.LENGTH_SHORT).show();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

        f.delete();
    }

    /**
     * Write JSON file in internal storage
     *
     * @param jsonStr
     * @param fileName
     * @return
     */
    public void writeFileNew(String jsonStr, String fileName) {
        try {
            File jsonFile = getNewFile(GenericConstant.FILE_DIRECTORY, fileName + ".txt");
            FileWriter fOut = new FileWriter(jsonFile);
            fOut.write(jsonStr);
            fOut.flush();
            fOut.close();

        } catch (IOException e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), Utilities.class, "writeFile");
        }
    }

    /**
     * This method used to create new file if not exist .
     */
    public File getNewFile(String directoryName, String fileName) {
        String root = Environment.getExternalStorageDirectory()
                + directoryName;
        File file;
        if (isSDCARDMounted()) {
            new File(root).mkdirs();
            file = new File(root, fileName);
        } else {
            file = new File(context.getFilesDir(), fileName);
        }
        return file;
    }

    public ArrayList<String> getDirectoryList(String dirPath) {
        String dir = FILE_DIRECTORY + new AppSession(context).getUserID() + dirPath;
        String root = Environment.getExternalStorageDirectory()
                + dir;

        File f = new File(root);
        if (!f.exists()) try {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        File[] files = f.listFiles();

        ArrayList<String> dirList = null;
        if (files != null) {
            dirList = new ArrayList<>();
            for (File file : files) {
                if (file.isDirectory()) {
                    dirList.add(file.getName());
                }
            }
        }
        return dirList;
    }

    public ArrayList<String> getFilesList(String dirPath) {
        String dir = FILE_DIRECTORY + new AppSession(context).getUserID() + dirPath;

        String root = Environment.getExternalStorageDirectory()
                + dir;

        File f = new File(root);
        if (!f.exists()) try {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        File[] files = f.listFiles();

        ArrayList<String> dirList = null;
        if (files != null) {
            dirList = new ArrayList<>();
            for (File file : files) {
                if (file.isFile()) {
                    dirList.add(file.getName());
                }
            }
        }
        return dirList;
    }

    public int getNumberOfFiles(String outFolder) throws FileNotFoundException {
        String dir = FILE_DIRECTORY + new AppSession(context).getUserID() + outFolder;
        String root = Environment.getExternalStorageDirectory()
                + dir;

        File outFile = new File(root);
        if (!outFile.exists()) {
            return 0;
        }

        int numberofFiles = Objects.requireNonNull(outFile.listFiles(new FileFilter() {

            @Override
            public boolean accept(File file) {
                return (file.getPath().endsWith(".txt"));
            }
        })).length;
        return numberofFiles;
    }

    public boolean isSDCARDMounted() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

    public String getAbsolutePath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        // Cursor cursor = ((Activity) context).managedQuery(uri, projection,
        // null, null, null);
        Cursor cursor = context.getContentResolver().query(uri, projection,
                null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public void deletePicture() {
        try {
            String root = Environment.getExternalStorageDirectory().toString();
            new File(root + IMAGE_DIRECTORY).mkdirs();
            File f = new File(root + IMAGE_DIRECTORY);
            File[] files = f.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile())
                    files[i].delete();
            }
            f.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getAbsolutePath(Context context, Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        // Cursor cursor = ((Activity) context).managedQuery(uri, projection,
        // null, null, null);
        Cursor cursor = context.getContentResolver().query(uri, projection,
                null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        } else
            return null;
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.showSoftInput(view, 0);
    }


    public void dialogOK(final Context context, String title, String message, String btnText, final boolean isFinish) {
        // https://www.google.com/design/spec/components/dialogs.html#dialogs-specs
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("");
        alertDialogBuilder.setMessage(Html.fromHtml(message));
//        alertDialogBuilder.setView(new TextView(context));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(btnText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isFinish)
                    ((Activity) context).finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public void dialogOK2(final Context context, String title, String message,
                          String btnText, final boolean isFinish) {
        // https://www.google.com/design/spec/components/dialogs.html#dialogs-specs
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("");
        alertDialogBuilder.setMessage(message);
//        alertDialogBuilder.setView(new TextView(context));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(btnText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isFinish) ((Activity) context).finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public static Bitmap decodeFile(File f, int REQUIRED_WIDTH,
                                    int REQUIRED_HEIGHT) {
        try {
            ExifInterface exif = new ExifInterface(f.getPath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            // Log.i("exif.getAttribute(ExifInterface.TAG_ORIENTATION)",
            // exif.getAttribute(ExifInterface.TAG_ORIENTATION));
            int angle = 0;

            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                angle = 90;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                angle = 180;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                angle = 270;
            }
            // Log.i("path & orientation & angle", f.getPath() + " & "
            // + orientation + " & " + angle);
            Matrix mat = new Matrix();
            mat.postRotate(angle);
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            // Find the correct scale value. It should be the power of 2.
            int REQUIRED_SIZE = 100; // 70
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            // Log.i("W*H Before.....................", scale + " " + width_tmp
            // + "*" + height_tmp);
            if (width_tmp > height_tmp) {
                REQUIRED_SIZE = REQUIRED_HEIGHT;
                REQUIRED_HEIGHT = REQUIRED_WIDTH;
                REQUIRED_WIDTH = REQUIRED_SIZE;
            }
            while (true) {
                if (width_tmp / 2 < REQUIRED_WIDTH
                        && height_tmp / 2 < REQUIRED_HEIGHT)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            // Log.i("W*H After.....................", scale + " " + width_tmp
            // + "*" + height_tmp);
            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            o2.inPurgeable = true;
            // return BitmapFactory.decodeStream(new FileInputStream(f), null,
            // o2);
            Bitmap correctBmp = BitmapFactory.decodeStream(new FileInputStream(
                    f), null, o2);
            correctBmp = Bitmap.createBitmap(correctBmp, 0, 0,
                    correctBmp.getWidth(), correctBmp.getHeight(), mat, true);
            return correctBmp;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFilePath(Bitmap bitmap, Context context, String path) {
        //  File cacheDir;
        File file;

        try {
//            if (android.os.Environment.getExternalStorageState().equals(
//                    android.os.Environment.MEDIA_MOUNTED))
//                cacheDir = new File(
//                        android.os.Environment.getExternalStorageDirectory(),
//                        "NIGHTLIVE/Media/Images");
//            else
//                cacheDir = context.getCacheDir();
//            if (!cacheDir.exists())
//                cacheDir.mkdirs();

            if (bitmap != null) {
                String FILE_NAME = "dnd_" + new Date().getTime() + ".jpg";
                file = new File(path);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                FileOutputStream fo;

                fo = new FileOutputStream(file);
                fo.write(bytes.toByteArray());
                fo.close();

                return file.getAbsolutePath();
            }

        } catch (Exception e1) {
            e1.printStackTrace();
            Log.v("", "getMessage " + e1.getMessage());

        } catch (Error e1) {
            e1.printStackTrace();
            Log.v("", "getMessage " + e1.getMessage());
        }
        /*
         * if (myDir.isDirectory()) { String[] children = myDir.list(); for (int
         * i = 0; i < children.length; i++) { new File(myDir,
         * children[i]).delete(); } }
         */
        return "";
    }

    public String getFormatedAmountString(Float amt) {
        DecimalFormat decimalFormat = new DecimalFormat("#");
        decimalFormat.setMinimumFractionDigits(6);
        String value = decimalFormat.format(amt);
        String str = "" + value.charAt(0);
        if (str.equals(".")) {
            value = "0" + value;
        }
        return value;
    }
    /*public void dialogWithOkCancel(Context context, String title, String message,
                                   String posBtnTest, String negBtnText,
                                   final OnDialogConfirmListener listener) {
        // https://www.google.com/design/spec/components/dialogs.html#dialogs-specs

        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(Html.fromHtml(message));

        alertDialogBuilder.setPositiveButton(posBtnTest, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onYes();
            }
        });
        alertDialogBuilder.setNegativeButton(negBtnText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onNo();
            }
        });


        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }*/

    public static boolean isStringNullOrBlank(String str) {
        if (str == null) {
            return true;
        } else if (str.equals("null") || str.equals("")) {
            return true;
        }
        return false;
    }


    public String getDeviceInfo() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        int version = Build.VERSION.SDK_INT;
        String versionRelease = Build.VERSION.RELEASE;

        Log.e("MyActivity", "manufacturer - " + manufacturer + " \n model - " + model
                + " \n version - " + version
                + " \n versionRelease - " + versionRelease);

        String info = "manufacturer - " + manufacturer + " \n model - " + model
                + " \n version - " + version + " \n versionRelease - " + versionRelease;

        return info;
    }

    public String getModel() {
        String model = Build.MODEL;
        return model;
    }

    public String getOsVersion() {
        String versionRelease = Build.VERSION.RELEASE;
        return versionRelease;
    }

    public String getPath(final Context context, final Uri uri) {

        // check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


//    public  boolean isConnectionFast(int type, int subType){
//        if(type==ConnectivityManager.TYPE_WIFI){
//            Log.i(getClass().getName(),"Wifi State");
//            return true;
//        }else if(type==ConnectivityManager.TYPE_MOBILE){
//            switch(subType)
// {
//                case TelephonyManager.NETWORK_TYPE_1xRTT:
//                    Log.i(getClass().getName(),"50 - 100 kbps");
//                    return false; // ~ 50-100 kbps
//                case TelephonyManager.NETWORK_TYPE_CDMA:
//                    Log.i(getClass().getName(),"14 - 64 kbps");
//                    return false; // ~ 14-64 kbps
//                case TelephonyManager.NETWORK_TYPE_EDGE:
//                    Log.i(getClass().getName(),"50 - 100 kbps");
//                    return false; // ~ 50-100 kbps
//                case TelephonyManager.NETWORK_TYPE_EVDO_0:
//                    Log.i(getClass().getName(),"400 - 1000 kbps");
//                    return true; // ~ 400-1000 kbps
//                case TelephonyManager.NETWORK_TYPE_EVDO_A:
//                    Log.i(getClass().getName(),"600 - 1400 kbps");
//                    return true; // ~ 600-1400 kbps
//                case TelephonyManager.NETWORK_TYPE_GPRS:
//                    Log.i(getClass().getName()," 100 kbps");
//                    return false; // ~ 100 kbps
//                case TelephonyManager.NETWORK_TYPE_HSDPA:
//                    Log.i(getClass().getName(),"2 - 14 Mbps");
//                    return true; // ~ 2-14 Mbps
//                case TelephonyManager.NETWORK_TYPE_HSPA:
//                    Log.i(getClass().getName(),"700 - 1700 kbps");
//                    return true; // ~ 700-1700 kbps
//                case TelephonyManager.NETWORK_TYPE_HSUPA:
//                    Log.i(getClass().getName(),"1 - 23 Mbps");
//                    return true; // ~ 1-23 Mbps
//                case TelephonyManager.NETWORK_TYPE_UMTS:
//                    Log.i(getClass().getName(),"400 - 7000 kbps");
//                    return true; // ~ 400-7000 kbps
//            /*
//             * Above API level 7, make sure to set android:targetSdkVersion
//             * to appropriate level to use these
//             */
//                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
//                    Log.i(getClass().getName(),"1 - 2 Mbps");
//                    return true; // ~ 1-2 Mbps
//                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
//                    Log.i(getClass().getName(),"5 Mbps");
//                    return true; // ~ 5 Mbps
//                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
//                    Log.i(getClass().getName(),"10 - 20 Mbps");
//                    return true; // ~ 10-20 Mbps
//                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
//                    Log.i(getClass().getName(),"25 kbps");
//                    return false; // ~25 kbps
//                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
//                    Log.i(getClass().getName(),"10+ Mbps");
//                    return true; // ~ 10+ Mbps
//                // Unknown
//                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
//                default_pro:
//                    return false;
//            }
//        }else{
//            return false;
//        }
//    }


    /**
     * Method for checking network availability
     */
//    public boolean isNetworkAvailable() {
//        try {
//            cm = (ConnectivityManager) context
//                    .getSystemService(CONNECTIVITY_SERVICE);
//            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
//
//            // if no network is available networkInfo will be null
//            // otherwise check if we are connected
//            if (networkInfo != null && networkInfo.isConnected()){
//                if(isConnectionFast(networkInfo.getType(),networkInfo.getSubtype())){
//                    return true;
//                }else {
//                    dialogOK(context,"","Your connection is too low", "OK", false);
//                    return false;
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

}

