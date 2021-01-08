package hcl.policing.digitalpolicingplatform.activities.process.flow;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.audio.AudioRecordActivity;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.process.edit.OpenSection;
import hcl.policing.digitalpolicingplatform.activities.process.edit.RemoveValue;
import hcl.policing.digitalpolicingplatform.adapters.audio.UserAudioListAdaptor;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.viewProperties.ViewPropertiesConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomButton;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomCheckBox;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomImageView;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomLinearLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomRecyclerView;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextView;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.listeners.OnItemLongClickListener;
import hcl.policing.digitalpolicingplatform.models.process.ExpectedAnswerListBean;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class SetClick {

    static ProcessCreationActivity activity;


    /**
     * method for managing clicks for sub json
     *
     * @param act
     */
    public static void setProcessClick(ProcessCreationActivity act) {

        activity = act;
    }

    /**
     * Listener for refresh particular section
     */
    public static View.OnClickListener onClickRefresh = new View.OnClickListener() {
        public void onClick(View v) {
            try {
                ProcessCreationActivity.QUESTION_COUNT = 0;
                activity.currentQuestion = "";

                if(activity.isTabProcessEnabled) {

                    if(activity.isTabProcessAddPage) {
                        activity.llProcessLayout.removeAllViews();
                        activity.rvProcessLayout.setVisibility(View.GONE);

                        if (activity.aExpectedQuestionListBean != null) {
                            activity.subDetailJson = new JSONObject();
                            activity.subDetailJson = ServerRequest.createSubJsonDetail(activity);

                            CreateQuestion.createQuestions(activity, activity.aExpectedQuestionListBean);
                        }
                    }
                } else {
                    for (int i = 0; i < activity.aPageSectionListBean.size(); i++) {

                        if(activity.ClickedTabID == activity.aPageSectionListBean.get(i).getPageSection_Id()) {
                            ProcessCreationActivity.SECTION_COUNT = i;
                        }

                        if (activity.ClickedTabID <= activity.aPageSectionListBean.get(i).getPageSection_Id()) {

                            CustomLinearLayout customLinearLayout = activity.findViewById(activity.aPageSectionListBean.get(i).getPageSection_Id());
                            View viewImage = customLinearLayout.getChildAt(0);
                            CustomImageView customImageView = (CustomImageView) viewImage;
                            customImageView.setImageResource(R.drawable.ic_dot_gray);

                            View view = customLinearLayout.getChildAt(1);
                            CustomTextView customTextView = (CustomTextView) view;
                            customTextView.setTextColor(activity, customTextView, ViewPropertiesConstant.Color_Gray_Light);
                        }
                    }
                    CustomLinearLayout customLinearLayout = activity.findViewById(activity.ClickedTabID);
                    View viewImage = customLinearLayout.getChildAt(0);
                    CustomImageView customImageView = (CustomImageView) viewImage;
                    customImageView.setImageResource(R.drawable.ic_dot_blue);

                    View view = customLinearLayout.getChildAt(1);
                    CustomTextView customTextView = (CustomTextView) view;
                    customTextView.setTextColor(activity, customTextView, ViewPropertiesConstant.Color_Primary);
                    RemoveValue.removeSectionValue(activity, activity.aPageSectionListBean.get(ProcessCreationActivity.SECTION_COUNT).getPageSection_Name(), activity.aPageSectionListBean.get(ProcessCreationActivity.SECTION_COUNT).getExpectedQuestionList());
                    activity.prepareScreen();
                }
                DialogUtil.dismiss();
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), SetClick.class, "onClickRefresh");
            }
        }
    };

    // used to choose answer displayed with question
    public static View.OnClickListener onClickAnswer = new View.OnClickListener() {
        public void onClick(View v) {
            try {
                // do something when the button is clicked
                // Yes we will handle click here but which button clicked??? We don't know
                if (ProcessCreationActivity.isClicked) {
                    ProcessCreationActivity.isClicked = false;
                    activity.llAdd.removeAllViews();
                    CustomButton b = (CustomButton) v;
                    activity.captureValue = b.getText().toString();
                    activity.buttonId = b.getId();

                    if (activity.isTabProcessEnabled) {
                        try {
                            ServerRequest.setSubJsonServerRequest(activity, activity.mExpectedQuestionListBean.getActualQuestion(), activity.captureValue, null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            ServerRequest.setServerRequest(activity, activity.mPageSectionListBean.getPageSection_Name(), activity.mExpectedQuestionListBean.getActualQuestion(), activity.captureValue, null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    activity.etInfo.setText("");
                    activity.nextIteration();
                    activity.scrollDown();
                }
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), SetClick.class, "onClickAnswer");
            }
        }
    };

    //use to choose multiple answer
    public static View.OnClickListener onClickQuestionOk = new View.OnClickListener() {
        public void onClick(View v) {
            try {

                StringBuilder sb = new StringBuilder();
                if (ProcessCreationActivity.isClickedQuestionOk) {
                    ProcessCreationActivity.isClickedQuestionOk = false;

                    for (int i = 0; i < ProcessCreationActivity.checkboxList.size(); i++) {
                        ProcessCreationActivity.checkboxList.get(i).setEnabled(false);
                    }

                    ViewParent parent = v.getParent();
                    if (parent instanceof CustomLinearLayout) {
                        CustomLinearLayout customLinearLayout = (CustomLinearLayout) parent;

                        View view = customLinearLayout.getChildAt(3);
                        if (view instanceof CustomLinearLayout) {
                            CustomLinearLayout customLinearLayoutH = (CustomLinearLayout) view;
                            int count = customLinearLayoutH.getChildCount();
                            for (int i = 0; i < count; i++) {
                                View vCheck = customLinearLayoutH.getChildAt(i);
                                if (vCheck instanceof CustomCheckBox) {

                                    CustomCheckBox customCheckBox = (CustomCheckBox) vCheck;
                                    if (customCheckBox.isChecked()) {
                                        //captureValue = customCheckBox.getText().toString();
                                        sb.append(customCheckBox.getText() + ",");
                                    }
                                }
                            }
                        }
                    }
                    activity.llAdd.removeAllViews();
                    activity.captureValue = sb.toString().substring(0, sb.toString().length() - 1);
                    activity.etInfo.setText("");

                    if (activity.isTabProcessEnabled) {
                        ServerRequest.setSubJsonServerRequest(activity, activity.mExpectedQuestionListBean.getActualQuestion(), activity.captureValue, null);
                    } else {
                        ServerRequest.setServerRequest(activity, activity.mPageSectionListBean.getPageSection_Name(), activity.mExpectedQuestionListBean.getActualQuestion(), activity.captureValue, null);
                    }
                    activity.isNextQuestion();
                    activity.scrollDown();
                    Log.e("CAPTURE ", "VALUE >>>>> " + activity.captureValue);
                }
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), SetClick.class, "onClickQuestionOK");
            }
        }
    };

    /**
     * Listener for item click image
     */
    public static OnItemClickListener.OnItemClickCallback onClickImage = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                Log.e("IMAGE ", "PATH >> "+activity.imageListAct.get(position).getPath());
                String image = Utilities.getInstance(activity).readfile(activity.imageListAct.get(position).getPhoto(), ProcessCreationActivity.directoryImage);
                if(image != null && !image.equalsIgnoreCase("")) {
                    byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    DialogUtil.showZoomDialog(activity, bitmap, activity.imageListAct.get(position).getDescription());
                }
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "onClickImage");
            }
        }
    };

    //long click on image for editing
    public static OnItemLongClickListener.OnItemLongClickCallback onClickLongImageEdit = new OnItemLongClickListener.OnItemLongClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                activity.callCameraActivity(GenericConstant.IMAGES_EDIT);
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "onClickSignature");
            }
        }
    };

    public static OnItemClickListener.OnItemClickCallback onClickDoc = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                Log.e("DOC ", "PATH >> "+activity.docListAct.get(position).getPath());
                Uri path = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", new File(activity.docListAct.get(position).getPath()));
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(path, "*/*");
                activity.startActivity(intent);
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), SetClick.class, "onClickDoc");
            }
        }
    };

    public static OnItemLongClickListener.OnItemLongClickCallback onClickLongDocEdit = new OnItemLongClickListener.OnItemLongClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                activity.callAttachmentActivity(GenericConstant.ATTACHMENT_EDIT);
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), SetClick.class, "onClickDocEdit");
            }
        }
    };

    public static OnItemClickListener.OnItemClickCallback onClickSignature = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                Log.e("SIGNATURE ", "PATH >> " + activity.signatureListAct.get(position).getPath());
                String image = Utilities.getInstance(activity).readfile(activity.signatureListAct.get(position).getPhoto(), ProcessCreationActivity.directoryImage);
                if(image != null && !image.equalsIgnoreCase("")) {
                    byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    DialogUtil.showZoomDialog(activity, bitmap, activity.signatureListAct.get(position).getDescription());
                }
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "onClickSignature");
            }
        }
    };

    public static OnItemLongClickListener.OnItemLongClickCallback onClickLongSignature = new OnItemLongClickListener.OnItemLongClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                activity.callSignatureActivity(GenericConstant.SIGNATURE_EDIT);
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "onClickSignature");
            }
        }
    };

    public static OnItemClickListener.OnItemClickCallback onClickSketch = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                Log.e("SKETCH ", "PATH >> " + activity.sketchListAct.get(position).getPath());
                String image = Utilities.getInstance(activity).readfile(activity.sketchListAct.get(position).getPhoto(), ProcessCreationActivity.directoryImage);
                if(image != null && !image.equalsIgnoreCase("")) {
                    byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    DialogUtil.showZoomDialog(activity, bitmap, activity.sketchListAct.get(position).getDescription());
                }
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "onClickSketch");
            }
        }
    };

    public static OnItemLongClickListener.OnItemLongClickCallback onClickLongSketch = new OnItemLongClickListener.OnItemLongClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                activity.callSketchActivity(GenericConstant.SKETCH_EDIT);
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "onClickLongSketch");
            }
        }
    };

    /**
     * Listener for item click image
     */
    public static OnItemClickListener.OnItemClickCallback onClickAudio = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                //File file = Utilities.getInstance(activity).readDocFile(activity.docListAct.get(position).getPhoto(), ProcessCreationActivity.directoryAudio);
                //String audioPath = file.getAbsolutePath();
                Log.e("AUDIO ", "PATH >> "+activity.audioListAct.get(position).getPath());
                for (int i = 0; i < activity.audioListAct.size(); i++) {
                    if(position == i) {
                        if(activity.audioListAct.get(position).getStatus().equalsIgnoreCase("1") || activity.isPlaying) {
                            //activity.audioListAct.get(position).setStatus("0");
                            if(activity.isPause) {
                                activity.isPause = false;
                                activity.mediaPlayer.start();
                                activity.audioListAct.get(position).setStatus("1");
                            } else {
                                activity.isPause = true;
                                activity.audioListAct.get(position).setStatus("0");
                                activity.mediaPlayer.pause();
                            }
                        } else {
                            activity.audioListAct.get(position).setStatus("1");

                            activity.mediaPlayer = new MediaPlayer();
                            try {
                                activity.mediaPlayer.setDataSource(activity.audioListAct.get(position).getPath());
                                activity.mediaPlayer.prepare();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            activity.mediaPlayer.start();
                            activity.isPlaying = true;

                            activity.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    // TODO Auto-generated method stub
                                    //here you can stop your recording thread.
                                    activity.isPlaying = false;
                                    activity.audioListAct.get(position).setStatus("0");

                                    if(mp != null){
                                        mp.stop();
                                        mp.release();
                                        activity.mediaPlayer = null;
                                    }
                                    CustomRecyclerView customRecyclerView = activity.findViewById(R.id.rv_audio);
                                    if (customRecyclerView != null) {
                                        UserAudioListAdaptor userAudioListAdaptor = new UserAudioListAdaptor(activity, activity.audioListAct, onClickAudio, onClickLongAudio);
                                        customRecyclerView.setAdapter(userAudioListAdaptor);
                                    }
                                }
                            });
                        }
                    } else {
                        activity.audioListAct.get(i).setStatus("0");
                    }
                }
                CustomRecyclerView customRecyclerView = activity.findViewById(R.id.rv_audio);
                if (customRecyclerView != null) {
                    UserAudioListAdaptor userAudioListAdaptor = new UserAudioListAdaptor(activity, activity.audioListAct, onClickAudio, onClickLongAudio);
                    customRecyclerView.setAdapter(userAudioListAdaptor);
                }
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), SetClick.class, "onClickAudio  ");
            }
        }
    };

    //long click on image for editing
    public static OnItemLongClickListener.OnItemLongClickCallback onClickLongAudio = new OnItemLongClickListener.OnItemLongClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                activity.callAudioActivity(GenericConstant.AUDIO_EDIT);
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "onClickSignature");
            }
        }
    };

    //long click on image for editing
    public static View.OnLongClickListener onClickLongPocketbook = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            Log.e("PREESED ", " LONG Pocket");
            activity.callPocketbookActivity(GenericConstant.POCKETBOOK_EDIT);
            return false;
        }
    };

    // to select tab displayed above on process screen
    public static View.OnClickListener onClickTab = new View.OnClickListener() {
        public void onClick(View v) {
            if (!activity.isEditEnabled) {
                try {
                    activity.currentQuestion = "";
                    activity.ClickedTabID = v.getId();
                    Log.e("TAB", " >> ID >> "+activity.ClickedTabID);
                    OpenSection.openSection(activity, activity.ClickedTabID);
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "onClickTab");
                }
            }
        }
    };

    // long click on answer for edit
    public static View.OnLongClickListener onLongClickEdit = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            try {
                activity.customLinearLayoutEdit = (CustomLinearLayout) v;
                activity.registerForContextMenu(v);
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "onLongClickEdit");
            }
            return false;
        }
    };
}
