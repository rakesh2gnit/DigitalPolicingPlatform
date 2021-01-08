package hcl.policing.digitalpolicingplatform.utils;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

public class JsonUtil {
    private static final JsonUtil ourInstance = new JsonUtil();

    public static JsonUtil getInstance() {
        return ourInstance;
    }

    private JsonUtil() {
    }

    public Object toModel(String jsonString, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, type);
    }

    public String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /**
     * Load json from assets folder
     * @param context
     * @param fileName
     * @return
     */
    public static String loadJsonFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            if(is.available() == 0) {
                return null;
            } else {
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
