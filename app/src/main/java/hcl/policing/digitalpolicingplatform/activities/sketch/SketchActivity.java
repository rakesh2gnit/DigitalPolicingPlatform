package hcl.policing.digitalpolicingplatform.activities.sketch;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.activities.camera.CameraActivity;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.fieldName.FieldsNameConstant;
import hcl.policing.digitalpolicingplatform.models.camera.PhotoListModel;
import hcl.policing.digitalpolicingplatform.mpermissionslibrary.PermissionRequest;
import hcl.policing.digitalpolicingplatform.mpermissionslibrary.PermissionRequestHandler;
import hcl.policing.digitalpolicingplatform.mpermissionslibrary.PermissionsManager;
import hcl.policing.digitalpolicingplatform.mpermissionslibrary.PermissionsUtil;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.ChoiceDragListener;
import hcl.policing.digitalpolicingplatform.utils.ChoiceTouchListener;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class SketchActivity extends BaseActivity implements View.OnClickListener, PermissionRequest.RequestStorage,
        PermissionRequest.RequestCustomPermissionGroup, OnMapReadyCallback {

    private AppSession appSession;
    public static RelativeLayout rlDrop;
    private RelativeLayout rlMain, rlText;
    private LinearLayout llDrag, llDescription;
    private EditText etDescription;
    private ImageView ivImage;
    public int count = 1;
    public String current = null;
    private Bitmap mBitmap;
    private ArrayList<PhotoListModel> imageList = new ArrayList<>();
    private int reqValue;

    private File photoFile;
    private String picturePath = "";
    private String imagePath = "";
    private Uri cameraUri = null;

    private DrawingView drawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sketch_activity);
        appSession = new AppSession(this);
        initView();
        imageList = new ArrayList<>();
        imageList.clear();
        try {
            reqValue = getIntent().getIntExtra("REQ_VALUE", 0);
            imageList = (ArrayList<PhotoListModel>) getIntent().getSerializableExtra(GenericConstant.SKETCH_LIST);
            String sketchString = getIntent().getStringExtra(GenericConstant.SKETCH_STRING);
            if (imageList != null && imageList.size() > 0) {
                String image = Utilities.getInstance(this).readfile(imageList.get(0).getPhoto(), ProcessCreationActivity.directoryImage);
                if (image != null && !image.equalsIgnoreCase("")) {
                    byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                    mBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    ivImage.setBackground(new BitmapDrawable(getResources(), mBitmap));
                }
                etDescription.setText(imageList.get(0).getDescription());
            }
            if (sketchString != null && !sketchString.equalsIgnoreCase("")) {
                byte[] decodedString = Base64.decode(sketchString, Base64.DEFAULT);
                mBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                ivImage.setBackground(new BitmapDrawable(getResources(), mBitmap));
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), SketchActivity.class, "docSave");
        }
        openUpload(reqValue);
    }

    /**
     * Initialize the view
     */
    private void initView() {
        rlMain = findViewById(R.id.rl_main);
        rlDrop = findViewById(R.id.drop_layout);
        llDrag = findViewById(R.id.ll_drag);
        llDescription = findViewById(R.id.ll_description);
        etDescription = findViewById(R.id.et_description);
        rlText = findViewById(R.id.rl_text);
        rlText.setX(150);
        rlText.setY(50);
        ivImage = findViewById(R.id.iv_image);
        mBitmap = getWhiteBitmap();
        ivImage.setBackground(new BitmapDrawable(getResources(), mBitmap));
        drawingView = findViewById(R.id.ll_drawing);

        TextView tvSave = findViewById(R.id.tv_save);
        TextView tvClose = findViewById(R.id.tv_close);
        ImageView ivDraw = findViewById(R.id.iv_draw);
        ImageView ivErase = findViewById(R.id.iv_erase);
        ImageView ivDelete = findViewById(R.id.iv_delete);
        ImageView ivDrag = findViewById(R.id.iv_drag);
        ImageView ivText = findViewById(R.id.iv_text);
        ImageView ivDescription = findViewById(R.id.iv_description);
        tvSave.setOnClickListener(this);
        tvClose.setOnClickListener(this);
        ivDraw.setOnClickListener(this);
        ivErase.setOnClickListener(this);
        ivDelete.setOnClickListener(this);
        ivDrag.setOnClickListener(this);
        ivText.setOnClickListener(this);
        ivDescription.setOnClickListener(this);

        ImageView ivMan = findViewById(R.id.iv_man);
        ivMan.setOnTouchListener(new ChoiceTouchListener());
        ImageView ivOldMan = findViewById(R.id.iv_old_man);
        ivOldMan.setOnTouchListener(new ChoiceTouchListener());
        ImageView ivFallingMan = findViewById(R.id.iv_falling_man);
        ivFallingMan.setOnTouchListener(new ChoiceTouchListener());
        ImageView ivGroup = findViewById(R.id.iv_group);
        ivGroup.setOnTouchListener(new ChoiceTouchListener());
        ImageView ivTruck = findViewById(R.id.iv_truck);
        ivTruck.setOnTouchListener(new ChoiceTouchListener());
        ImageView ivCar = findViewById(R.id.iv_car);
        ivCar.setOnTouchListener(new ChoiceTouchListener());
        ImageView ivCarAccident = findViewById(R.id.iv_car_accident);
        ivCarAccident.setOnTouchListener(new ChoiceTouchListener());
        ImageView ivCarFire = findViewById(R.id.iv_car_fire);
        ivCarFire.setOnTouchListener(new ChoiceTouchListener());
        ImageView ivCarOverturn = findViewById(R.id.iv_car_overturn);
        ivCarOverturn.setOnTouchListener(new ChoiceTouchListener());
        ImageView ivTrafficSignal = findViewById(R.id.iv_traffic_signal);
        ivTrafficSignal.setOnTouchListener(new ChoiceTouchListener());
        ImageView ivFire = findViewById(R.id.iv_fire);
        ivFire.setOnTouchListener(new ChoiceTouchListener());
        ImageView ivFirstAid = findViewById(R.id.iv_first_aid);
        ivFirstAid.setOnTouchListener(new ChoiceTouchListener());
        ImageView ivLeft = findViewById(R.id.iv_left);
        ivLeft.setOnTouchListener(new ChoiceTouchListener());
        ImageView ivRight = findViewById(R.id.iv_right);
        ivRight.setOnTouchListener(new ChoiceTouchListener());
        ImageView ivUp = findViewById(R.id.iv_up);
        ivUp.setOnTouchListener(new ChoiceTouchListener());
        ImageView ivDown = findViewById(R.id.iv_down);
        ivDown.setOnTouchListener(new ChoiceTouchListener());

        rlDrop.setOnDragListener(new ChoiceDragListener(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_drag:
                try {
                    llDescription.setVisibility(View.GONE);
                    if(llDrag.getVisibility() == View.VISIBLE) {
                        llDrag.setVisibility(View.GONE);
                    } else {
                        llDrag.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.iv_text:
                try {
                    llDrag.setVisibility(View.GONE);
                    llDescription.setVisibility(View.GONE);
                    if(rlText.getVisibility() == View.VISIBLE) {
                        rlText.setVisibility(View.GONE);
                    } else {
                        rlText.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.iv_draw:
                try {
                    llDrag.setVisibility(View.GONE);
                    llDescription.setVisibility(View.GONE);
                    drawingView.setupDrawing();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.iv_erase:
                try {
                    llDrag.setVisibility(View.GONE);
                    llDescription.setVisibility(View.GONE);
                    drawingView.setErase(true);
                    drawingView.setBrushSize(drawingView.getBrushSize());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.iv_delete:
                try {
                    llDrag.setVisibility(View.GONE);
                    rlText.setVisibility(View.GONE);
                    llDescription.setVisibility(View.GONE);
                    rlDrop.removeAllViews();
                    Log.v("log_tag", "Panel Cleared");
                    ivImage.setBackground(new BitmapDrawable(getResources(), mBitmap));
                    drawingView.startNew();

                    if (imageList != null && imageList.size() > 0) {
                        Utilities.getInstance(SketchActivity.this).deleteProcessFile(imageList.get(0).getPhoto(), ProcessCreationActivity.directoryImage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.iv_description:
                try {
                    if(llDescription.getVisibility() == View.VISIBLE) {
                        llDescription.setVisibility(View.GONE);
                    } else {
                        llDescription.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.tv_save:
                try {
                    llDrag.setVisibility(View.GONE);
                    llDescription.setVisibility(View.GONE);
                    rlMain.setDrawingCacheEnabled(true);
                    save(rlMain);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.tv_close:
                try {
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void openUpload (int req) {
        switch (req) {
            case 1:
                PermissionRequestHandler.requestCustomPermissionGroup(SketchActivity.this, "", android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;

            case 2:
                PermissionRequestHandler.requestStorage(SketchActivity.this, "");
                break;

            case 3:
                mBitmap = getWhiteBitmap();
                ivImage.setBackground(new BitmapDrawable(getResources(), mBitmap));
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtil.onRequestPermissionsResult(requestCode, permissions, grantResults, SketchActivity.this);
    }

    @Override
    public void onStoragePermissionGranted() {
        openGallery();
    }

    @Override
    public void onStoragePermissionDenied() {

    }

    @Override
    public void onAllCustomPermissionGroupGranted() {
        PermissionsManager.getInstance().setStoragePermissionDenied(false);
        openCamera();
    }

    @Override
    public void onCustomPermissionGroupDenied() {

    }

    /**
     * Open Camera method
     */
    private void openCamera() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                // Ensure that there's a camera activity to handle the intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    photoFile = null;
                    try {
                        photoFile = createImageFile(SketchActivity.this);
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        ex.printStackTrace();
                        return;
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        cameraUri = FileProvider.getUriForFile(SketchActivity.this,
                                getPackageName() + GenericConstant.PROVIDER, photoFile);

                            /*cameraUri = FileProvider.getUriForFile(getActivity(),
                                    BuildConfig.APPLICATION_ID + ".provider", photoFile);*/

                        appSession.setImageUri(cameraUri);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
                        startActivityForResult(intent, GenericConstant.CAMERA_PIC_REQUEST);
                    }
                }
            } else {
                Log.i("imageChooser", " imageChooser 6");
                Intent intent = new Intent();
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String fileName = GenericConstant.IMAGE + System.currentTimeMillis() + GenericConstant.IMAGE_EXTENSION;
                cameraUri = Uri.fromFile(Utilities.getInstance(SketchActivity.this).getNewFile(GenericConstant.IMAGE_DIRECTORY, fileName));
                appSession.setImageUri(cameraUri);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, GenericConstant.CAMERA_PIC_REQUEST);
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CameraActivity.class, "openCamera");
        }
    }

    /**
     * Open Gallery Method
     */
    private void openGallery() {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(Intent.createChooser(intent, GenericConstant.Gallery), GenericConstant.GALLERY_PIC_REQUEST);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CameraActivity.class, "openGallery");
        }
    }

    /**
     * Create Image file
     *
     * @param mContext
     * @return
     * @throws IOException
     */
    private static File createImageFile(Context mContext) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());

        String fileName = GenericConstant.IMAGE + System.currentTimeMillis() + GenericConstant.IMAGE_EXTENSION;

        File image = Utilities.getInstance(mContext).getNewFile(GenericConstant.IMAGE_DIRECTORY, fileName);

        return image;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GenericConstant.GALLERY_PIC_REQUEST:

                    try {
                        Uri uriImage = data.getData();
                        if (uriImage != null) {

                            picturePath = Utilities.getInstance(SketchActivity.this).getAbsolutePath(uriImage);
                            if (picturePath == null || picturePath.equals(""))
                                picturePath = uriImage.getPath();
                            appSession.setImagePath(picturePath);
                            Log.i("GET IMAGE", "GALLERY picturePath : " + picturePath);

                            Cursor cursor = getContentResolver()
                                    .query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                            new String[]{android.provider.MediaStore.Images.Media._ID},
                                            android.provider.MediaStore.Images.Media.DATA + "=? ",
                                            new String[]{picturePath}, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                int id = cursor.getInt(cursor.getColumnIndex(android.provider.MediaStore.MediaColumns._ID));
                                uriImage = Uri.parse("content://media/external/images/media/" + id);
                                cursor.close();
                            }
                            Bitmap bm = uriToBitmap(SketchActivity.this, uriImage);

                            Bitmap rotatedBitmap = rotateImage(bm, picturePath);

                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            Objects.requireNonNull(rotatedBitmap).compress(Bitmap.CompressFormat.JPEG, 50, baos);
                            byte[] byt = baos.toByteArray();
                            String encodedImage = Base64.encodeToString(byt, Base64.DEFAULT);
                            Log.e("encoded ", "image >>>>> " + encodedImage);
                            Utilities.getInstance(SketchActivity.this).deletePicture();
                            setImage(SketchActivity.this, encodedImage);
                        } else {
                            Toast.makeText(SketchActivity.this,
                                    getResources().getString(R.string.image_not_picked),
                                    Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        ExceptionLogger.Logger(e.getCause(), e.getMessage(), CameraActivity.class, "GalleryResult");
                        Toast.makeText(SketchActivity.this,
                                getResources().getString(R.string.image_not_picked),
                                Toast.LENGTH_LONG).show();
                    }
                    break;

                case GenericConstant.CAMERA_PIC_REQUEST:

                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            //Uri uri = Uri.parse(photoFile.getAbsolutePath());
                            picturePath = photoFile.getAbsolutePath();
                            appSession.setImagePath(picturePath);
                            imagePath = picturePath;
                            Log.i("GET IMAGE", "Camera picturePath : " + picturePath);

                            Cursor cursor = getContentResolver()
                                    .query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                            new String[]{MediaStore.Images.Media._ID},
                                            MediaStore.Images.Media.DATA + "=? ",
                                            new String[]{imagePath}, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
                                cameraUri = Uri.parse("content://media/external/images/media/" + id);
                                cursor.close();
                            }

                            Bitmap bm = uriToBitmap(SketchActivity.this, cameraUri);

                            Bitmap rotatedBitmap = rotateImage(bm, imagePath);

                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            Objects.requireNonNull(rotatedBitmap).compress(Bitmap.CompressFormat.JPEG, 50, baos);
                            byte[] byt = baos.toByteArray();
                            String encodedImage = Base64.encodeToString(byt, Base64.DEFAULT);
                            Log.e("encoded ", "image >>>>> " + encodedImage);
                            Utilities.getInstance(SketchActivity.this).deletePicture();
                            setImage(SketchActivity.this, encodedImage);
                        } else {
                            try {
                                if (cameraUri == null)
                                    cameraUri = appSession.getImageUri();
                                if (cameraUri != null) {
                                    picturePath = Utilities.getInstance(SketchActivity.this).getAbsolutePath(cameraUri);
                                    if (picturePath == null || picturePath.equals(""))
                                        picturePath = cameraUri.getPath();
                                    appSession.setImagePath(picturePath);
                                    Log.i("GET IMAGE", "CAMERA picturePath : " + picturePath);

                                    Cursor cursor = getContentResolver()
                                            .query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                                    new String[]{MediaStore.Images.Media._ID},
                                                    MediaStore.Images.Media.DATA + "=? ",
                                                    new String[]{picturePath}, null);
                                    if (cursor != null && cursor.moveToFirst()) {
                                        int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
                                        cameraUri = Uri.parse("content://media/external/images/media/" + id);
                                        cursor.close();
                                    }
                                    Bitmap bm = BitmapFactory.decodeFile(picturePath);

                                    Bitmap rotatedBitmap = rotateImage(bm, picturePath);

                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                                    byte[] byt = baos.toByteArray();
                                    String encodedImage = Base64.encodeToString(byt, Base64.DEFAULT);
                                    Log.e("encoded ", "image >>>>> " + encodedImage);
                                    Utilities.getInstance(SketchActivity.this).deletePicture();
                                    setImage(SketchActivity.this, encodedImage);
                                } else {
                                    Toast.makeText(SketchActivity.this,
                                            getResources().getString(R.string.image_not_captured),
                                            Toast.LENGTH_LONG).show();
                                }

                            } catch (Exception e) {
                                ExceptionLogger.Logger(e.getCause(), e.getMessage(), CameraActivity.class, "CameraResult");
                                Toast.makeText(SketchActivity.this,
                                        getResources().getString(R.string.image_not_captured),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (Exception e) {
                        ExceptionLogger.Logger(e.getCause(), e.getMessage(), CameraActivity.class, "CameraResult");
                    }
                    break;
            }
        }
    }

    /**
     * Convert URI to bitmaps
     *
     * @param context
     * @param uri
     * @return
     */
    private Bitmap uriToBitmap(Context context, Uri uri) {
        Bitmap bm = null;
        try {
            ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            bm = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return bm;
            //bm = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        } catch (IOException e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CameraActivity.class, "uriToImage");
        }
        return bm;
    }

    /**
     * Set the Image in RecyclerView
     *
     * @param context
     * @param imageString
     */
    private void setImage(Context context, String imageString) {
        imageList = new ArrayList<>();
        if (imageString != null && !imageString.equalsIgnoreCase("")) {

            byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
            mBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ivImage.setBackground(new BitmapDrawable(getResources(), mBitmap));
        }
    }

    /**
     * Handle the bitmap orientation
     *
     * @param bitmap
     * @param photoPath
     * @return
     */
    public Bitmap rotateImage(Bitmap bitmap, String photoPath) {
        ExifInterface ei = null;
        try {
            ei = new ExifInterface(photoPath);
        } catch (IOException e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CameraActivity.class, "rotateImage");
        }
        int orientation = Objects.requireNonNull(ei).getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap rotatedBitmap = null;
        switch (orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(bitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(bitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(bitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = bitmap;
        }
        return rotatedBitmap;
    }

    /**
     * Rotate the bitmap by angle
     *
     * @param source
     * @param angle
     * @return
     */
    public Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    /**
     * Get the bitmap from view
     *
     * @param view
     * @return
     */
    private Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    /**
     * Get the bitmap from view
     *
     * @return
     */
    private Bitmap getWhiteBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(ContextCompat.getColor(this, R.color.white));
        return bitmap;
    }

    /**
     * Call the method save the data
     *
     * @param v
     */
    public void save(View v) {
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBitmap = getBitmapFromView(v);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    Objects.requireNonNull(mBitmap).compress(Bitmap.CompressFormat.JPEG, 50, baos);
                    byte[] byt = baos.toByteArray();
                    String encodedImage = Base64.encodeToString(byt, Base64.DEFAULT);
                    Log.e("encoded ", "signature >>>>> " + encodedImage);
                    if (encodedImage != null && !encodedImage.equalsIgnoreCase("")) {
                        String fileName = FieldsNameConstant.Sketch + "_" + System.currentTimeMillis();
                        PhotoListModel photoListModel = new PhotoListModel();
                        photoListModel.setPhoto(fileName);
                        photoListModel.setDescription(etDescription.getText().toString());
                        photoListModel.setStatus("0");
                        String path = Utilities.getInstance(SketchActivity.this).writeFile(encodedImage, fileName, ProcessCreationActivity.directoryImage);
                        photoListModel.setPath(path);
                        imageList = new ArrayList<>();
                        imageList.add(photoListModel);
                    }
                    v.setDrawingCacheEnabled(false);
                    Intent intentProcess = new Intent();
                    intentProcess.putExtra(GenericConstant.SKETCH_LIST, imageList);
                    setResult(RESULT_OK, intentProcess);
                    finish();
                    overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
                }
            }, 500);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), SketchActivity.class, "docSave");
        }
    }
}