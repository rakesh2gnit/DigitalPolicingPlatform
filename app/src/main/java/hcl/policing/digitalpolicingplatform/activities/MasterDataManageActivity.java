package hcl.policing.digitalpolicingplatform.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.database.DatabaseHelper;
import hcl.policing.digitalpolicingplatform.models.login.Authenticationresponse;
import hcl.policing.digitalpolicingplatform.models.masterdata.DropdownResponse;
import hcl.policing.digitalpolicingplatform.services.DownloadManagerService;

public class MasterDataManageActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private TextView timeout1;
    private boolean isRightExist;
    private int oldVersionNumber = 1;
    private int currentVersionNumber = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.masterdata_activity);

        timeout1 = findViewById(R.id.timeout1);

        db = new DatabaseHelper(this);

        long startTime = System.currentTimeMillis();

        if (!isRightExist)
            readAndInsertRights();
        readAndInsertDropdown();

        Log.e("Count", "count::" + getRightCount());
        Log.e("Count", "count::" + getDropDownCount());

        deleteDropDown();

        long endTime = System.currentTimeMillis();

        long totalTime = endTime - startTime;
        timeout1.setText("Total time::" + totalTime);

        if (currentVersionNumber != oldVersionNumber) {
            deleteDropDown();
            readAndInsertDropdown();
        }

        if (isRightExist)
            readAndUpdateRights();
    }

    /**
     * Read / Insert the dropdown method
     */
    public void readAndInsertDropdown() {
        final DropdownResponse dropdownResponse = new Gson().fromJson(loadJSONFromAsset("dropdownmaster.json"), DropdownResponse.class);
        if (dropdownResponse.getMasterDataResponse().getMasterDataProcesses() != null) {
//            db.addDropDownList(dropdownResponse.getMasterDataResponse().getMasterDataProcesses());
        }
    }

    /**
     * Read/Insert rights
     */
    public void readAndInsertRights() {
        final Authenticationresponse userResponse = new Gson().fromJson(loadJSONFromAsset(GenericConstant.AUTHENTICATION_JSON), Authenticationresponse.class);
        if (userResponse.getUser().getRights() != null) {
            db.add_rightsList(userResponse.getUser().getRights());
        }
    }

    /**
     * Read / Updated the rights
     */
    public void readAndUpdateRights() {
        final Authenticationresponse userResponse = new Gson().fromJson(loadJSONFromAsset(GenericConstant.AUTHENTICATION_JSON), Authenticationresponse.class);
        if (userResponse.getUser().getRights() != null) {
            db.update_rightsActiveList();
            db.update_rightsList(userResponse.getUser().getRights());
        }
    }

    /**
     * download the empty icons
     */
    public void downloadEmptyIcon() {
        ArrayList<Integer> iconList = new ArrayList<>();
        if (db.getNullImage() != -1) {
            iconList.add(db.getNullImage());
        }


        Intent intent = new Intent(this, DownloadManagerService.class);
        //Intent intent = new Intent(this, DownloadService.class);
        //intent.putExtra("iconlist",iconList);
        //intent.putExtra("url", "url of the file to download");
        //intent.putExtra("receiver", new DownloadReceiver(new Handler()));
        startService(intent);
    }

    /**
     * delete the dropdown
     */
    public void deleteDropDown() {
        db.deleteAllDropdownValues();
    }

    /**
     * Get the dropdown count
     *
     * @return
     */
    public int getDropDownCount() {
        int count = db.getDropDownCount();
        return count;
    }

    /**
     * Get the rights count
     *
     * @return
     */
    public int getRightCount() {
        int count = db.getRightsCount();
        return count;
    }

    /**
     * load the json from assets
     *
     * @param fileName
     * @return
     */
    public String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
