package hcl.policing.digitalpolicingplatform.activities.camera;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.matthewtamlin.android_utilities_library.BuildConfig;

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
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.adapters.camera.ImageListAdaptor;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.fieldName.FieldsNameConstant;
import hcl.policing.digitalpolicingplatform.listeners.OnEditTextChangedListener;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.models.camera.PhotoListModel;
import hcl.policing.digitalpolicingplatform.mpermissionslibrary.PermissionRequest;
import hcl.policing.digitalpolicingplatform.mpermissionslibrary.PermissionRequestHandler;
import hcl.policing.digitalpolicingplatform.mpermissionslibrary.PermissionsManager;
import hcl.policing.digitalpolicingplatform.mpermissionslibrary.PermissionsUtil;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.SnapHelperOneByOne;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class CameraActivity extends BaseActivity implements View.OnClickListener,
        PermissionRequest.RequestStorage, PermissionRequest.RequestCustomPermissionGroup {

    /**
     * Initialize the views
     */
    private AppSession appSession;
    private Context context;

    private File photoFile;
    private String picturePath = "";
    private String imagePath = "";
    private Uri cameraUri = null;
    private RecyclerView rvImage;
    private ArrayList<PhotoListModel> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);
        context = this;
        appSession = new AppSession(context);
        initView();
        imageList.clear();

        if (appSession.getImageList() != null && appSession.getImageList().size() > 0) {
            imageList.addAll(appSession.getImageList());
        }

        if (imageList != null && imageList.size() > 0) {
            ImageListAdaptor imageListAdaptor = new ImageListAdaptor(context, imageList, onClickDelete, new OnEditTextChangedListener() {
                @Override
                public void onTextChanged(int position, String charSequence) {
                    try {
                        if (charSequence.length() > 0) {
                            imageList.get(position).setDescription(charSequence.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, ProcessCreationActivity.directoryImage);
            rvImage.setAdapter(imageListAdaptor);
        }
    }

    /**
     * Initiate the views
     */
    private void initView() {

        rvImage = findViewById(R.id.rv_image);
        setRecyclerViewProperty(context, rvImage);
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(rvImage);
        Button btnDone = findViewById(R.id.btn_done);
        btnDone.setOnClickListener(this);
        TextView tvCamera = findViewById(R.id.tv_camera);
        tvCamera.setOnClickListener(this);
        TextView tvGallery = findViewById(R.id.tv_gallery);
        tvGallery.setOnClickListener(this);
    }

    /**
     * Set the recycler view property
     *
     * @param context
     * @param rvtList
     */
    private static void setRecyclerViewProperty(Context context, RecyclerView rvtList) {
        rvtList.setHasFixedSize(true);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        rvtList.setLayoutManager(layoutmanager);
    }

    /**
     * Onclick method of Click listener
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_camera:
                PermissionRequestHandler.requestCustomPermissionGroup(context, "", android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;

            case R.id.tv_gallery:
                PermissionRequestHandler.requestStorage(context, "");
                break;

            case R.id.btn_done:
                try {
                    appSession.setImageList(null);
                    appSession.setImageList(imageList);
                    Intent intentProcess = new Intent();
                    //intentProcess.putExtra(ApiConstants.PHOTO_LIST, imageList);
                    setResult(RESULT_OK, intentProcess);
                    finish();
                    overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtil.onRequestPermissionsResult(requestCode, permissions, grantResults, context);
    }

    @Override
    public void onAllCustomPermissionGroupGranted() {
        PermissionsManager.getInstance().setStoragePermissionDenied(false);
        openCamera();
    }

    @Override
    public void onCustomPermissionGroupDenied() {

    }

    @Override
    public void onStoragePermissionGranted() {
        openGallery();
    }

    @Override
    public void onStoragePermissionDenied() {
        showToast(getString(R.string.permission_required));
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
                        photoFile = createImageFile(context);
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        ex.printStackTrace();
                        return;
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        cameraUri = FileProvider.getUriForFile(context,
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
                cameraUri = Uri.fromFile(Utilities.getInstance(context).getNewFile(GenericConstant.IMAGE_DIRECTORY, fileName));
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GenericConstant.GALLERY_PIC_REQUEST:

                    try {
                        Uri uriImage = data.getData();
                        if (uriImage != null) {

                            picturePath = Utilities.getInstance(context).getAbsolutePath(uriImage);
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
                            Bitmap bm = uriToBitmap(context, uriImage);

                            Bitmap rotatedBitmap = rotateImage(bm, picturePath);

                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            Objects.requireNonNull(rotatedBitmap).compress(Bitmap.CompressFormat.JPEG, 50, baos);
                            byte[] byt = baos.toByteArray();
                            String encodedImage = Base64.encodeToString(byt, Base64.DEFAULT);
                            Log.e("encoded ", "image >>>>> " + encodedImage);
                            Utilities.getInstance(context).deletePicture();
                            setImageInRecycler(context, encodedImage);
                        } else {
                            Toast.makeText(context,
                                    getResources().getString(R.string.image_not_picked),
                                    Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        ExceptionLogger.Logger(e.getCause(), e.getMessage(), CameraActivity.class, "GalleryResult");
                        Toast.makeText(context,
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

                            Bitmap bm = uriToBitmap(context, cameraUri);

                            Bitmap rotatedBitmap = rotateImage(bm, imagePath);

                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            Objects.requireNonNull(rotatedBitmap).compress(Bitmap.CompressFormat.JPEG, 50, baos);
                            byte[] byt = baos.toByteArray();
                            String encodedImage = Base64.encodeToString(byt, Base64.DEFAULT);
                            Log.e("encoded ", "image >>>>> " + encodedImage);
                            Utilities.getInstance(context).deletePicture();
                            setImageInRecycler(context, encodedImage);
                        } else {
                            try {
                                if (cameraUri == null)
                                    cameraUri = appSession.getImageUri();
                                if (cameraUri != null) {
                                    picturePath = Utilities.getInstance(context).getAbsolutePath(cameraUri);
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
                                    Utilities.getInstance(context).deletePicture();
                                    setImageInRecycler(context, encodedImage);
                                } else {
                                    Toast.makeText(context,
                                            getResources().getString(R.string.image_not_captured),
                                            Toast.LENGTH_LONG).show();
                                }

                            } catch (Exception e) {
                                ExceptionLogger.Logger(e.getCause(), e.getMessage(), CameraActivity.class, "CameraResult");
                                Toast.makeText(context,
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
    private void setImageInRecycler(Context context, String imageString) {
        if (imageString != null && !imageString.equalsIgnoreCase("")) {
            String fileName = FieldsNameConstant.Images + "_" + System.currentTimeMillis();
            PhotoListModel photoListModel = new PhotoListModel();
            photoListModel.setPhoto(fileName);
            photoListModel.setDescription("");
            photoListModel.setStatus("0");
            String path = Utilities.getInstance(this).writeFile(imageString, fileName, ProcessCreationActivity.directoryImage);
            photoListModel.setPath(path);
            imageList.add(photoListModel);
        }
        ImageListAdaptor imageListAdaptor = new ImageListAdaptor(context, imageList, onClickDelete, new OnEditTextChangedListener() {
            @Override
            public void onTextChanged(int position, String charSequence) {
                try {
                    if (charSequence.length() > 0) {
                        imageList.get(position).setDescription(charSequence.toString());
                    }
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), CameraActivity.class, "setImageIn Recycler");
                }
            }
        }, ProcessCreationActivity.directoryImage);
        rvImage.setAdapter(imageListAdaptor);
    }

    /**
     * Item Click Listener
     */
    public OnItemClickListener.OnItemClickCallback onClickDelete = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                if(imageList.size() > 0) {
                    Utilities.getInstance(CameraActivity.this).deleteProcessFile(imageList.get(position).getPhoto(), ProcessCreationActivity.directoryImage);
                    imageList.remove(position);
                    setImageInRecycler(context, "");
                }
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), CameraActivity.class, "onClickDelete");
            }
        }
    };

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
    }
}
