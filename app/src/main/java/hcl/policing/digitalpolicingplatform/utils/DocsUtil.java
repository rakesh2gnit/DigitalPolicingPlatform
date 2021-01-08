package hcl.policing.digitalpolicingplatform.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DocsUtil {

    private static final DocsUtil ourInstance = new DocsUtil();

    public static DocsUtil getInstance() {
        return ourInstance;
    }

    private DocsUtil() {
    }

    /**
     * Get the path of Uri
     *
     * @param docUri
     * @return
     */
    public String getPathFromURI(Uri docUri) {
        String path = null;
        try {
            File file = new File(docUri.getPath());//create path from uri
            final String[] split = file.getPath().split(":");//split the path.
            path = split[1];

        } catch (Exception e) {
            e.printStackTrace();
        }

        return path;
    }

    public String getFileName(String filePath) {
        String filename = null;

        try {
            File file = new File(filePath);
            filename = file.getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

    /**
     * Upload File on Server.
     *
     * @param context
     * @param sourceFilePath
     * @return
     */
    public int uploadFile(Context context, String sourceFilePath) {

        int serverResponseCode = 0;
        String upLoadServerUri = null;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFilePath);

        if (!sourceFile.isFile()) {

//            dialog.dismiss();
            ((Activity) context).runOnUiThread(() -> BasicMethodsUtil.getInstance().showToast(context, "Source File not exist :" + sourceFilePath));
            return 0;

        } else {
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", sourceFilePath);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + sourceFilePath + "\"" + lineEnd);


                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {

                    ((Activity) context).runOnUiThread(() -> {
                        String msg = "File Upload Completed.";
                        BasicMethodsUtil.getInstance().showToast(context, msg);
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

//                dialog.dismiss();
                ex.printStackTrace();

                ((Activity) context).runOnUiThread(() -> BasicMethodsUtil.getInstance().showToast(context, "MalformedURLException Exception : check script url."));

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

//                dialog.dismiss();
                e.printStackTrace();

                ((Activity) context).runOnUiThread(new Runnable() {
                    public void run() {
                        BasicMethodsUtil.getInstance().showToast(context, "Got Exception : see logcat");
                    }
                });
                Log.e("Server Exception", "Exception : "
                        + e.getMessage(), e);
            }
//            dialog.dismiss();
            return serverResponseCode;

        } // End else block
    }
}
