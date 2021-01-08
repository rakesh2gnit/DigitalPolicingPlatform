package hcl.policing.digitalpolicingplatform.activities.sketch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.activities.map.MapActivity;
import hcl.policing.digitalpolicingplatform.activities.map.PocketbookMapActivity;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.process.edit.EditProcess;
import hcl.policing.digitalpolicingplatform.activities.process.edit.RemoveValue;
import hcl.policing.digitalpolicingplatform.activities.process.flow.PopulateFields;
import hcl.policing.digitalpolicingplatform.activities.process.flow.ServerRequest;
import hcl.policing.digitalpolicingplatform.activities.process.flow.SetClick;
import hcl.policing.digitalpolicingplatform.activities.signature.SignatureUploadActivity;
import hcl.policing.digitalpolicingplatform.adapters.attachment.UserDocListAdaptor;
import hcl.policing.digitalpolicingplatform.adapters.audio.UserAudioListAdaptor;
import hcl.policing.digitalpolicingplatform.adapters.camera.UserImageListAdaptor;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.fieldName.FieldsNameConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomRecyclerView;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextView;
import hcl.policing.digitalpolicingplatform.customLibraries.layoutHelper.CreateDynamicView;
import hcl.policing.digitalpolicingplatform.models.camera.PhotoListModel;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.AnswerValueDTO;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.AddressBean;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.services.MyLocationServices;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.LocationUtil;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class SketchFirstActivity extends BaseActivity implements View.OnClickListener {

    private AppSession appSession;
    private ArrayList<PhotoListModel> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sketch_activity_first);
        appSession = new AppSession(this);
        initView();
    }

    /**
     * Initialize the view
     */
    private void initView() {
        LinearLayout llCamera = findViewById(R.id.ll_camera);
        LinearLayout llBrowse = findViewById(R.id.ll_browse);
        LinearLayout llFresh = findViewById(R.id.ll_fresh);
        LinearLayout llMap = findViewById(R.id.ll_map);
        llCamera.setOnClickListener(this);
        llBrowse.setOnClickListener(this);
        llFresh.setOnClickListener(this);
        llMap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_camera:
                try {
                    Intent sigIntent = new Intent(this, SketchActivity.class);
                    sigIntent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                    sigIntent.putExtra("REQ_VALUE", 1);
                    startActivity(sigIntent);
                    finish();
                    overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.ll_browse:
                try {
                    Intent sigIntent = new Intent(this, SketchActivity.class);
                    sigIntent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                    sigIntent.putExtra("REQ_VALUE", 2);
                    startActivity(sigIntent);
                    finish();
                    overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.ll_fresh:
                try {
                    Intent sigIntent = new Intent(this, SketchActivity.class);
                    sigIntent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                    sigIntent.putExtra("REQ_VALUE", 3);
                    startActivity(sigIntent);
                    finish();
                    overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.ll_map:
                try {
                    callMapActivity();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * Call the Map Activity
     */
    public void callMapActivity() {
        appSession.setCurrentLatitude(null);
        appSession.setCurrentLongitude(null);
        Intent mapIntent = new Intent(this, PocketbookMapActivity.class);
        startActivityForResult(mapIntent, GenericConstant.MAP_REQUEST);
        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(" RESULT ", " >>>>> " + resultCode + "  >>>>  " + data + " >>>>>  " + requestCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case GenericConstant.MAP_REQUEST:
                    try {
                        String sketchListAct = data.getStringExtra(GenericConstant.SKETCH_LIST);
                        if (sketchListAct != null && !sketchListAct.equalsIgnoreCase("")) {
                            Intent sigIntent = new Intent(this, SketchActivity.class);
                            sigIntent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                            //sigIntent.putExtra("REQ_VALUE", 3);
                            sigIntent.putExtra(GenericConstant.SKETCH_STRING, sketchListAct);
                            startActivity(sigIntent);
                            finish();
                            overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
                        }
                    } catch (Exception e) {
                        ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "mapResult");
                    }
                    break;
            }
        } else {
            if (Utilities.isMyServiceRunning(SketchFirstActivity.this, MyLocationServices.class)) {
                Intent i = new Intent(SketchFirstActivity.this, MyLocationServices.class);
                i.setAction(MyLocationServices.ACTION_STOP_FOREGROUND_SERVICE);
                ContextCompat.startForegroundService(SketchFirstActivity.this, i);
            }
        }
    }
}