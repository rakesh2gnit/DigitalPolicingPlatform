package hcl.policing.digitalpolicingplatform.test;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;

class LayoutController {

    void createLayout(Context context, JSONObject jsonObject) {
        try {
            JSONObject jsonObject1 = jsonObject.getJSONObject("MainLayout");
            Gson gson = new Gson();
            PropertiesBean propertiesBean = gson.fromJson(jsonObject1.getJSONObject("Properties").toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            //CreateLayout.createLayoutFields(context, jsonObject1.getString("ViewType"), propertiesBean, null, "start", "");

            Activity activity = (Activity) context;
            ViewGroup parentView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            JSONArray jsonArray = jsonObject1.getJSONArray("Child");
            createLayout(context, jsonArray, parentView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createLayout(Context context, JSONArray jsonArray, ViewGroup parentView) {
        try {
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    Gson gson = new Gson();
                    PropertiesBean propertiesBean = gson.fromJson(jsonArray.getJSONObject(i).getJSONObject("Properties").toString(), new TypeToken<PropertiesBean>() {
                    }.getType());
                    //CreateLayout.createLayoutFields(context, jsonArray.getJSONObject(i).getString("ViewType"), propertiesBean, parentView, "", "");
                    if (jsonArray.getJSONObject(i).getJSONArray("Child").length() > 0) {
                        JSONArray jsonArray1 = jsonArray.getJSONObject(i).getJSONArray("Child");
                        View view = parentView.getChildAt(i);
                        if (view instanceof ViewGroup) {
                            createLayout(context, jsonArray1, (ViewGroup) view);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
