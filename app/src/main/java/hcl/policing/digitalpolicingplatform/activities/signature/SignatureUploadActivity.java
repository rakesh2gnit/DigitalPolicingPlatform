package hcl.policing.digitalpolicingplatform.activities.signature;

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
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Objects;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.fieldName.FieldsNameConstant;
import hcl.policing.digitalpolicingplatform.models.camera.PhotoListModel;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class SignatureUploadActivity extends BaseActivity implements View.OnClickListener {

    private Context context;
    private LinearLayout llSignature;
    private signature mSignature;
    public int count = 1;
    public String current = null;
    private Bitmap mBitmap;
    private ArrayList<PhotoListModel> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signature_activity);
        //appSession = new AppSession(this);
        initView();
        imageList = new ArrayList<>();
        imageList.clear();
        try {
            imageList = (ArrayList<PhotoListModel>) getIntent().getSerializableExtra(GenericConstant.SIGNATURE_LIST);
            if (imageList != null && imageList.size() > 0) {
                String image = Utilities.getInstance(this).readfile(imageList.get(0).getPhoto(), ProcessCreationActivity.directoryImage);
                if (image != null && !image.equalsIgnoreCase("")) {
                    byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    mSignature.setBackground(new BitmapDrawable(getResources(), bitmap));
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), SignatureUploadActivity.class, "docSave");
        }
    }

    /**
     * Initialize the view
     */
    private void initView() {
        llSignature = findViewById(R.id.ll_signature);
        mSignature = new signature(this, null);
        mSignature.clear();
        mSignature.setBackgroundColor(Color.WHITE);
        llSignature.addView(mSignature, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        Button btnClear = findViewById(R.id.btn_clear);
        Button btnDone = findViewById(R.id.btn_done);
        btnClear.setOnClickListener(this);
        btnDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
                try {
                    Log.v("log_tag", "Panel Cleared");
                    mSignature.clear();
                    mSignature.setBackgroundColor(Color.WHITE);
                    if (imageList != null && imageList.size() > 0) {
                        Utilities.getInstance(SignatureUploadActivity.this).deleteProcessFile(imageList.get(0).getPhoto(), ProcessCreationActivity.directoryImage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btn_done:
                try {
                    Log.v("log_tag", "Panel Saved");
                    llSignature.setDrawingCacheEnabled(true);
                    mSignature.save(llSignature);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * call the method for signature
     */
    public class signature extends View {
        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(STROKE_WIDTH);
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
                            String fileName = FieldsNameConstant.Signature + "_" + System.currentTimeMillis();
                            PhotoListModel photoListModel = new PhotoListModel();
                            photoListModel.setPhoto(fileName);
                            photoListModel.setDescription("");
                            photoListModel.setStatus("0");
                            String path = Utilities.getInstance(SignatureUploadActivity.this).writeFile(encodedImage, fileName, ProcessCreationActivity.directoryImage);
                            photoListModel.setPath(path);
                            imageList = new ArrayList<>();
                            imageList.add(photoListModel);
                        }
                        v.setDrawingCacheEnabled(false);
                        Intent intentProcess = new Intent();
                        intentProcess.putExtra(GenericConstant.SIGNATURE_LIST, imageList);
                        setResult(RESULT_OK, intentProcess);
                        finish();
                        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
                    }
                }, 500);
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), SignatureUploadActivity.class, "docSave");
            }
        }

        public void clear() {
            path.reset();
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {
        }

        /**
         * call the method for expand rect
         *
         * @param historicalX
         * @param historicalY
         */
        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        /**
         * Reset the rect
         *
         * @param eventX
         * @param eventY
         */
        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
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
}