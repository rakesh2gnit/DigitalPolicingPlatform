package hcl.policing.digitalpolicingplatform.activities.document;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.process.flow.SetClick;
import hcl.policing.digitalpolicingplatform.adapters.attachment.AttachmentListAdaptor;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.listeners.OnEditTextChangedListener;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.models.camera.PhotoListModel;
import hcl.policing.digitalpolicingplatform.mpermissionslibrary.PermissionRequest;
import hcl.policing.digitalpolicingplatform.mpermissionslibrary.PermissionRequestHandler;
import hcl.policing.digitalpolicingplatform.mpermissionslibrary.PermissionsUtil;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.DocsUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.SnapHelperOneByOne;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class DocsUploadActivity extends BaseActivity implements View.OnClickListener, PermissionRequest.RequestStorage {

    private AppSession appSession;
    private Context context;
    private RecyclerView rvAttachment;
    private AttachmentListAdaptor attachmentListAdaptor;
    private String selectedDocPath;
    private ArrayList<PhotoListModel> aPathList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attachment_activity);
        initView();
        context = this;
        appSession = new AppSession(context);
        aPathList = new ArrayList<>();
        aPathList.clear();
        try {
            aPathList = (ArrayList<PhotoListModel>) getIntent().getSerializableExtra(GenericConstant.ATTACHMENT_LIST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (aPathList != null && aPathList.size() > 0) {
            attachmentListAdaptor = new AttachmentListAdaptor(context, aPathList, onClickDoc, onClickDelete, new OnEditTextChangedListener() {
                @Override
                public void onTextChanged(int position, String charSequence) {
                    try {
                        if (charSequence.length() > 0) {
                            aPathList.get(position).setDescription(charSequence.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            rvAttachment.setAdapter(attachmentListAdaptor);
        }
    }

    /**
     * Initiate the views
     */
    private void initView() {
        rvAttachment = findViewById(R.id.rv_attachment);
        setRecyclerViewProperty(context, rvAttachment);
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(rvAttachment);
        Button btnDone = findViewById(R.id.btn_done);
        btnDone.setOnClickListener(this);
        TextView tvBrowse = findViewById(R.id.tv_browse);
        tvBrowse.setOnClickListener(this);
    }

    /**
     * Set the recycler view property
     *
     * @param context
     * @param rvtList
     */
    private static void setRecyclerViewProperty(Context context, RecyclerView rvtList) {
        rvtList.setHasFixedSize(true);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(context);
        rvtList.setLayoutManager(layoutmanager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_browse:
                PermissionRequestHandler.requestStorage(context, "");
                break;

            case R.id.btn_done:

                Intent intentProcess = new Intent();
                intentProcess.putExtra(GenericConstant.ATTACHMENT_LIST, aPathList);
                setResult(RESULT_OK, intentProcess);
                finish();
                overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtil.onRequestPermissionsResult(requestCode, permissions, grantResults, context);
    }

    @Override
    public void onStoragePermissionGranted() {

        if (!isMaxDocumetsTaken(GenericConstant.MAX_COUNT)) {
            browseDocuments();
        }
    }

    @Override
    public void onStoragePermissionDenied() {

    }

    /**
     * Browse docs from gallery
     */
    private void browseDocuments() {

        String[] mimeTypeArray = {"application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                "text/plain",
                "application/pdf"};
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            photoPickerIntent.setType(mimeTypeArray.length == 1 ? mimeTypeArray[0] : "*/*");
            if (mimeTypeArray.length > 0) {
                photoPickerIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypeArray);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypeArray) {
                mimeTypesStr += mimeType + "|";
            }
            photoPickerIntent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }
        startActivityForResult(Intent.createChooser(photoPickerIntent, GenericConstant.ChooseFile), GenericConstant.DOCS_PIC_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GenericConstant.DOCS_PIC_REQUEST:
                    try {
                        Uri docUri = Objects.requireNonNull(data).getData();
                        if (docUri != null) {
                            if (isDocTypeValid(docUri)) {
                                selectedDocPath = getPath(docUri);
                                String fileName = "Doc_"+ System.currentTimeMillis();
                                //String fileName = DocsUtil.getInstance().getFileName(selectedDocPath);
                                Log.e("DOC PATH", " >> PATH >> " + selectedDocPath);
                                if (aPathList != null && aPathList.size() > 0) {
                                    for (int i = 0; i < aPathList.size(); i++) {
                                        if (aPathList.get(i).getPhoto().equalsIgnoreCase(fileName)) {
                                            Log.e("DOC Name", " >> " + aPathList.get(i).getPhoto() + " file name >> " + fileName);
                                            DialogUtil.showConfirmationDialog(context, "", "The document " + fileName + " " + getString(R.string.attached_validation), context.getString(R.string.ok));
                                            break;
                                        } else if (i == (aPathList.size() - 1) && !aPathList.get(aPathList.size() - 1).getPhoto().equalsIgnoreCase(fileName)) {
                                            setDocsInRecycler(selectedDocPath, fileName);
                                        }
                                    }
                                } else {
                                    aPathList = new ArrayList<>();
                                    setDocsInRecycler(selectedDocPath, fileName);
                                }
                            }
                        }
                    } catch (Exception e) {
                        ExceptionLogger.Logger(e.getCause(), e.getMessage(), DocsUploadActivity.class, "FileResult");
                    }
                    break;
            }
        }
    }

    /**
     * Set Docs in Recyclerview
     */
    private void setDocsInRecycler(String docPath, String fileName) {
        if (docPath != null && !docPath.equalsIgnoreCase("")) {
            PhotoListModel photoListModel = new PhotoListModel();
            photoListModel.setPhoto(fileName);
            photoListModel.setStatus("0");
            photoListModel.setDescription("");
            File source = new File(/*Environment.getExternalStorageDirectory() + "/" + */docPath);
            String path = Utilities.getInstance(this).writeDocFile(source, fileName, ProcessCreationActivity.directoryDoc);
            photoListModel.setPath(path);
            aPathList.add(photoListModel);
        }
        attachmentListAdaptor = new AttachmentListAdaptor(context, aPathList, onClickDoc, onClickDelete, new OnEditTextChangedListener() {
            @Override
            public void onTextChanged(int position, String charSequence) {
                try {
                    if (charSequence.length() > 0) {
                        aPathList.get(position).setDescription(charSequence.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        rvAttachment.setAdapter(attachmentListAdaptor);
    }

    /**
     * Delete item click listener
     */
    public OnItemClickListener.OnItemClickCallback onClickDoc = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                Log.e("DOC ", "PATH >> " + aPathList.get(position).getPath());
                Uri path = FileProvider.getUriForFile(DocsUploadActivity.this, getPackageName() + ".provider", new File(aPathList.get(position).getPath()));
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(path, "*/*");
                startActivity(intent);
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), SetClick.class, "onClickDoc");
            }
        }
    };

    /**
     * Delete item click listener
     */
    public OnItemClickListener.OnItemClickCallback onClickDelete = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                deleteDocs(position);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * Check the selected docs is valid or not
     *
     * @param docUri
     * @return
     */
    private boolean isDocTypeValid(Uri docUri) {
        boolean isValid = false;
        try {
            String mimeType = getContentResolver().getType(docUri);

            if (GenericConstant.mimeTypeArray.contains(mimeType)) {
                isValid = true;
            } else {
                DialogUtil.showConfirmationDialog(context, "", getString(R.string.doc_validation),
                        getString(R.string.ok));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return isValid;
    }


    /**
     * Check the Maximum number of Documents taken
     *
     * @return
     */
    private boolean isMaxDocumetsTaken(int maxCount) {
        boolean maxDocs = false;

        if (aPathList != null && aPathList.size() == maxCount) {
            maxDocs = true;
            BasicMethodsUtil.getInstance().showToast(context, getString(R.string.max_documents_taken));
        }

        return maxDocs;
    }

    /**
     * Delete the file from app
     */
    private void deleteDocs(int pos) {

        if (aPathList != null && aPathList.size() > 0) {
            Utilities.getInstance(DocsUploadActivity.this).deleteFileFromPath(aPathList.get(pos).getPath());
            aPathList.remove(pos);
            attachmentListAdaptor.notifyDataSetChanged();
        }
    }

    public String getPath(Uri uri) {

        String path = null;
        String[] projection = { MediaStore.Files.FileColumns.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if(cursor == null){
            path = uri.getPath();
        }
        else{
            cursor.moveToFirst();
            int column_index = cursor.getColumnIndexOrThrow(projection[0]);
            path = cursor.getString(column_index);
            cursor.close();
        }

        return ((path == null || path.isEmpty()) ? (uri.getPath()) : path);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
    }
}
