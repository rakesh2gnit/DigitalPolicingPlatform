package hcl.policing.digitalpolicingplatform.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.controlPannel.ClassicControlPanelActivity;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.process.flow.TabClick;
import hcl.policing.digitalpolicingplatform.adapters.process.fds.person.athena.WarningListAdaptor;
import hcl.policing.digitalpolicingplatform.adapters.process.officer.OfficerListAdaptor;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.listeners.LogoutListener;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.listeners.dialog.DialogEventListener;
import hcl.policing.digitalpolicingplatform.listeners.dialog.DialogItemClickListener;
import hcl.policing.digitalpolicingplatform.listeners.dialog.LoginDialogListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.AddressBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.PersonBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.VehicleBean;
import hcl.policing.digitalpolicingplatform.models.process.officer.UserModel;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;


public class DialogUtil {
    private static Dialog dialog;
    private static AppSession appSession;

    /**
     * Show basic dialog
     *
     * @param mContext
     * @param header
     * @param subHeader
     * @return
     */
    public static Dialog showConfirmationDialog(Context mContext, String header, String subHeader) {
        Dialog dialog = new Dialog(mContext, R.style.DialogSlideAnim);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.confirmation_dialog);

        TextView tvHeader = dialog.findViewById(R.id.tvHeader);
        TextView tvSubHeader = dialog.findViewById(R.id.tvSubHeader);
        TextView tvCancel = dialog.findViewById(R.id.tvCancel);
        TextView tvConfirm = dialog.findViewById(R.id.tvConfirm);

        tvHeader.setText(header);
        tvSubHeader.setText(subHeader);
        tvCancel.setText("Cancel");
        tvConfirm.setText("Confirm");

        return dialog;
    }


    /**
     * Show Confirmation Dialog
     *
     * @param mContext
     * @param header
     * @param subHeader
     * @param listener
     */
    public static void showConfirmationDialog(Context mContext, String header, String subHeader, final DialogItemClickListener listener) {

        dialog = new Dialog(mContext, R.style.DialogSlideAnim);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.confirmation_dialog);
        TextView tvHeader = dialog.findViewById(R.id.tvHeader);
        TextView tvSubHeader = dialog.findViewById(R.id.tvSubHeader);
        TextView tvCancel = dialog.findViewById(R.id.tvCancel);
        TextView tvConfirm = dialog.findViewById(R.id.tvConfirm);

//        tvCancel.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(header)) {
            tvHeader.setVisibility(View.VISIBLE);
            tvHeader.setText(header);
        }
        tvSubHeader.setText(subHeader);
        tvCancel.setText("NO");
        tvConfirm.setText("YES");
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                listener.onClickPositive();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        dialog.show();

    }


    /**
     * Show the dialog
     *
     * @param mContext
     * @param header
     * @param subHeader
     * @param negative
     * @param positive
     * @param listener
     */
    public static void showConfirmationDialog(Context mContext, String header, String subHeader, String negative,
                                              String positive, final DialogItemClickListener listener) {
        dialog = new Dialog(mContext, R.style.DialogSlideAnim);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.confirmation_dialog);
        TextView tvHeader = dialog.findViewById(R.id.tvHeader);
        TextView tvSubHeader = dialog.findViewById(R.id.tvSubHeader);
        TextView tvCancel = dialog.findViewById(R.id.tvCancel);
        TextView tvConfirm = dialog.findViewById(R.id.tvConfirm);

        if (TextUtils.isEmpty(header.trim())) {
            tvHeader.setVisibility(View.GONE);
        }

        tvHeader.setText(header);
        tvSubHeader.setText(subHeader);
        tvCancel.setText(negative);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                listener.onClickNegative();
            }
        });
        tvConfirm.setText(positive);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                listener.onClickPositive();
            }
        });
        dialog.show();

    }

    public static void looseDataDialog(ProcessCreationActivity mContext, String text) {
        dialog = new Dialog(mContext, R.style.CustomDialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_ok_cancel);
        TextView tvText = dialog.findViewById(R.id.tv_text);
        tvText.setText(text);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnOk = dialog.findViewById(R.id.btn_ok);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                TabClick.clickCancel(mContext);
            }
        });
        dialog.show();
    }

    /**
     * Logout dialog
     *
     * @param mContext
     * @param text
     */
    public static void logoutDialog(Object mContext, String text, LogoutListener listener) {
        dialog = new Dialog((Context) mContext, R.style.CustomDialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_yes_no);
        TextView tvText = dialog.findViewById(R.id.tv_text);
        tvText.setText(text);
        Button btnYes = dialog.findViewById(R.id.btn_yes);
        Button btnNo = dialog.findViewById(R.id.btn_no);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                listener.onLoggedOut();
                ((Activity) mContext).overridePendingTransition(R.anim.translate_top_bottom, R.anim.translate_out);
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        dialog.show();
    }

    public static void confirmationDialog(ClassicControlPanelActivity mContext, String text) {
        dialog = new Dialog(mContext, R.style.CustomDialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_yes_no);
        TextView tvText = dialog.findViewById(R.id.tv_text);
        tvText.setText(text);
        Button btnYes = dialog.findViewById(R.id.btn_yes);
        Button btnNo = dialog.findViewById(R.id.btn_no);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mContext.finish();
                mContext.overridePendingTransition(R.anim.translate_top_bottom, R.anim.translate_out);
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        dialog.show();
    }

    public static void saveDraftDialog(ProcessCreationActivity mContext, String text) {
        dialog = new Dialog(mContext, R.style.CustomDialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_ok);
        TextView tvText = dialog.findViewById(R.id.tv_text);
        tvText.setText(text);
        Button btnYes = dialog.findViewById(R.id.btn_ok);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.saveDraft();
                //Utilities.getInstance(mContext).writeFile(mContext.mainJSON.toString(), mContext.fileName, mContext.directoryDraft);
                dismiss();
                mContext.finish();
                mContext.overridePendingTransition(R.anim.translate_top_bottom, R.anim.translate_out);
                //Toast.makeText(mContext, mContext.getResources().getString(R.string.draft_saved), Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    public static void errorDialog(Context mContext, String text) {
        dialog = new Dialog(mContext, R.style.CustomDialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_ok);
        TextView tvText = dialog.findViewById(R.id.tv_text);
        tvText.setText(text);
        Button btnYes = dialog.findViewById(R.id.btn_ok);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        dialog.show();
    }

    public static void refreshDialog(ProcessCreationActivity mContext, String text, View.OnClickListener onClickRefresh) {
        dialog = new Dialog(mContext, R.style.CustomDialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_yes_no);
        TextView tvText = dialog.findViewById(R.id.tv_text);
        tvText.setText(text);
        Button btnYes = dialog.findViewById(R.id.btn_yes);
        Button btnNo = dialog.findViewById(R.id.btn_no);

        btnYes.setOnClickListener(onClickRefresh);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        dialog.show();
    }

    public static void submitDialog(ProcessCreationActivity mContext, String text) {
        dialog = new Dialog(mContext, R.style.CustomDialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_ok);
        TextView tvText = dialog.findViewById(R.id.tv_text);
        tvText.setText(text);
        Button btnYes = dialog.findViewById(R.id.btn_ok);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mContext.finish();
                mContext.overridePendingTransition(R.anim.translate_top_bottom, R.anim.translate_out);
            }
        });
        dialog.show();
    }

    public static void aboutDialog(Context mContext, String text, String version) {
        dialog = new Dialog(mContext, R.style.CustomDialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.about_dialog);
        ImageView ivClose = dialog.findViewById(R.id.iv_close);
        TextView tvText = dialog.findViewById(R.id.tv_text);
        TextView tvVersion = dialog.findViewById(R.id.tv_version);
        tvText.setText(text);
        tvVersion.setText("Version " + version);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        dialog.show();
    }

    /**
     * Show the forgot password dialog
     *
     * @param mContext
     * @param listener
     */
    public static void showForgotPasswordDialog(Context mContext, final DialogEventListener listener) {
        dialog = new Dialog(mContext, R.style.CustomDialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.forgot_password_dialog);
        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        EditText etCustomerCode = dialog.findViewById(R.id.etCustomerCode);
        EditText etUserName = dialog.findViewById(R.id.etUserName);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);

        ivClose.setOnClickListener(v -> dismiss());

        btnSubmit.setOnClickListener(v -> {
            if (TextUtils.isEmpty(etCustomerCode.getText().toString().trim())) {
                BasicMethodsUtil.getInstance().showToast(mContext, mContext.getString(R.string.invalid_customer_code));

            } else if (TextUtils.isEmpty(etUserName.getText().toString().trim())) {
                BasicMethodsUtil.getInstance().showToast(mContext, mContext.getString(R.string.invalid_username));

            } else {
                listener.dialogEventClick(etCustomerCode.getText().toString().trim(), etUserName.getText().toString().trim());
            }
        });
        dialog.show();
    }

    /**
     * Show the login dialog
     * @param mContext
     * @param listener
     */
    public static void loginDialog(Context mContext, final LoginDialogListener listener) {
        dialog = new Dialog(mContext, R.style.CustomDialogAnimation);
        /*dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
*/
        dialog.getWindow().getAttributes().windowAnimations = R.style.custom_dialog;
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.login_dialog);
        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        EditText etUsername = dialog.findViewById(R.id.etUsername);
        EditText etPassword = dialog.findViewById(R.id.etPassword);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);

        ivClose.setOnClickListener(v -> dismiss());

        btnSubmit.setOnClickListener(v -> {

            if (TextUtils.isEmpty(etUsername.getText().toString().trim())) {
                BasicMethodsUtil.getInstance().showToast(mContext, mContext.getString(R.string.invalid_username));

            } else if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
                BasicMethodsUtil.getInstance().showToast(mContext, mContext.getString(R.string.invalid_password));

            } else {
                listener.loginClick(etUsername.getText().toString().trim(), etPassword.getText().toString().trim());
            }
        });
        dialog.show();
    }

    /**
     * Show Officer list in dialog
     *
     * @param mContext
     * @param aOfficerListModel
     */
    public static void showOfficerDialog(ProcessCreationActivity mContext, List<UserModel> aOfficerListModel, OnItemClickListener.OnItemClickCallback officerSelectionListener) {
        try {
            dialog = new Dialog(mContext, R.style.CustomDialogAnimation);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            //dialog.setCancelable(false);
            dialog.setContentView(R.layout.officerlist_dialog);
            TextView tvOfficeHeader = dialog.findViewById(R.id.tvOfficeHeader);
            ImageView ivOfficer = dialog.findViewById(R.id.ivOfficer);
            RecyclerView rvOfficerList = dialog.findViewById(R.id.rvOfficerList);

            setRecyclerViewProperty(mContext, rvOfficerList);
            if (aOfficerListModel != null && aOfficerListModel.size() > 0) {
                OfficerListAdaptor officerListAdaptor = new OfficerListAdaptor(mContext, aOfficerListModel, officerSelectionListener);
                rvOfficerList.setAdapter(officerListAdaptor);
            }


            ivOfficer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                public void onCancel(DialogInterface dialog) {
                    if (mContext.QUESTION_COUNT > 0) {
                        Log.e("CALLED ", " >>>> back ");
                        mContext.QUESTION_COUNT--;
                    }
                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Show the Person List in Dialog
     *
     * @param mContext
     */
    public static void showWarningDialog(Context mContext, List<?> aWarningsBean, int type) {
        dialog = new Dialog(mContext, R.style.DialogSlideAnim);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.personlist_dialog);

        TextView tvPersonHeader = dialog.findViewById(R.id.tvPersonHeader);
        ImageView ivCross = dialog.findViewById(R.id.ivCross);
        RecyclerView rvWarningList = dialog.findViewById(R.id.rvWarningList);
        rvWarningList.setHasFixedSize(true);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvWarningList.setLayoutManager(layoutmanager);


        if (aWarningsBean != null && aWarningsBean.size() > 0) {
            WarningListAdaptor officerListAdaptor = new WarningListAdaptor(mContext, aWarningsBean, type);
            rvWarningList.setAdapter(officerListAdaptor);
        }

        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        dialog.show();
    }


    /**
     * Show Person Details in dialog
     *
     * @param mContext
     * @param mPersonBean
     */
    public static void showPersonDetailDialog(Context mContext, PersonBean mPersonBean) {
        final Dialog[] detailDialog = {new Dialog(mContext, R.style.CustomDialogAnimation)};
        detailDialog[0].getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        detailDialog[0].setCanceledOnTouchOutside(false);
        detailDialog[0].setContentView(R.layout.person_detail_dialog);
        TextView tvName = detailDialog[0].findViewById(R.id.tvName);
        TextView tvNameValue = detailDialog[0].findViewById(R.id.tvNameValue);
        TextView tvDob = detailDialog[0].findViewById(R.id.tvDob);
        TextView tvDobValue = detailDialog[0].findViewById(R.id.tvDobValue);
        TextView tvGender = detailDialog[0].findViewById(R.id.tvGender);
        TextView tvGenderValue = detailDialog[0].findViewById(R.id.tvGenderValue);
        TextView tvAddress = detailDialog[0].findViewById(R.id.tvAddress);
        TextView tvAddressValue = detailDialog[0].findViewById(R.id.tvAddressValue);
        ImageView ivCross = detailDialog[0].findViewById(R.id.ivCross);

        if (mPersonBean != null) {
            tvNameValue.setText(mPersonBean.getFirstname() + " " + mPersonBean.getLastname());
            tvDobValue.setText(mPersonBean.getDob());
            tvGenderValue.setText(mPersonBean.getGender());
            tvAddressValue.setText(mPersonBean.getAddress());
        }


        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailDialog[0] != null && detailDialog[0].isShowing()) {
                    detailDialog[0].dismiss();
                    detailDialog[0] = null;
                }
            }
        });
        detailDialog[0].show();
        detailDialog[0].getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }


    /**
     * Show the vehicle Details
     *
     * @param mContext
     * @param object
     * @param type
     */
    public static void showVehicleDetailDialog(Context mContext, Object object, int type) {
        final Dialog[] detailDialog = {new Dialog(mContext, R.style.DialogSlideAnim)};
        detailDialog[0].getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        detailDialog[0].setCanceledOnTouchOutside(false);
        detailDialog[0].setContentView(R.layout.person_detail_dialog);
        TextView tvName = detailDialog[0].findViewById(R.id.tvName);
        TextView tvNameValue = detailDialog[0].findViewById(R.id.tvNameValue);
        TextView tvDob = detailDialog[0].findViewById(R.id.tvDob);
        TextView tvDobValue = detailDialog[0].findViewById(R.id.tvDobValue);
        TextView tvGender = detailDialog[0].findViewById(R.id.tvGender);
        TextView tvGenderValue = detailDialog[0].findViewById(R.id.tvGenderValue);
        TextView tvAddress = detailDialog[0].findViewById(R.id.tvAddress);
        TextView tvAddressValue = detailDialog[0].findViewById(R.id.tvAddressValue);
        ImageView ivCross = detailDialog[0].findViewById(R.id.ivCross);

        if (type == GenericConstant.TYPE_VEHICLE) {
            VehicleBean vehicleBean = (VehicleBean) object;
            if (vehicleBean != null) {
                tvName.setText(mContext.getString(R.string.vrm));
                tvDob.setText(mContext.getString(R.string.make));
                tvGender.setText(mContext.getString(R.string.model));
                tvAddress.setText(mContext.getString(R.string.color));
                tvNameValue.setText(vehicleBean.getVrm());
                tvDobValue.setText(vehicleBean.getMake());
                tvGenderValue.setText(vehicleBean.getModel());
                tvAddressValue.setText(vehicleBean.getColor_primary());
            }
        } else if (type == GenericConstant.TYPE_ADDRESS) {
            AddressBean addressBean = (AddressBean) object;

            tvName.setText(mContext.getString(R.string.house_no));
            tvDob.setText(mContext.getString(R.string.street));
            tvGender.setText(mContext.getString(R.string.city));
            tvAddress.setText(mContext.getString(R.string.country));
            tvNameValue.setText(addressBean.getHouseNo());
            tvDobValue.setText(addressBean.getStreet());
            tvGenderValue.setText(addressBean.getCity());
            tvAddressValue.setText(addressBean.getCountry());
        }


        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailDialog[0] != null && detailDialog[0].isShowing()) {
                    detailDialog[0].dismiss();
                    detailDialog[0] = null;
                }
            }
        });
        detailDialog[0].show();
        detailDialog[0].getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    private static void setRecyclerViewProperty(Context context, RecyclerView rvComponentList) {
        rvComponentList.setHasFixedSize(true);
//        rvComponentList.addItemDecoration(new SpacesItemDecoration(30));
        LinearLayoutManager layoutmanager = new LinearLayoutManager(context);
        rvComponentList.setLayoutManager(layoutmanager);
    }


    public static void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }


    /**
     * Show the dialog
     *
     * @param mContext
     * @param header
     * @param subHeader
     * @param positive
     */
    public static void showConfirmationDialog(Context mContext, String header, String subHeader, String positive) {

        dialog = new Dialog(mContext, R.style.DialogSlideAnim);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.confirmation_dialog_single);
        TextView tvHeader = dialog.findViewById(R.id.tvHeader);
        TextView tvSubHeader = dialog.findViewById(R.id.tvSubHeader);
        TextView tvConfirm = dialog.findViewById(R.id.tvConfirm);

        if (TextUtils.isEmpty(header.trim())) {
            tvHeader.setVisibility(View.GONE);
        }

        tvHeader.setText(header);
        tvSubHeader.setText(subHeader);
        tvConfirm.setText(positive);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        dialog.show();

    }

    public static void showZoomDialog(Context context, Bitmap imageId, String description) {

        Dialog selectoption_dialog = new Dialog(context, R.style.CustomDialogAnimation);
        selectoption_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectoption_dialog.setContentView(R.layout.dialog_zoom_image);
        selectoption_dialog.setCanceledOnTouchOutside(true);
        //Objects.requireNonNull(selectoption_dialog.getWindow()).setBackgroundDrawableResource(R.color.white);
        selectoption_dialog.show();

        //Log.e("Image ", "ID "+imageId);
        ImageView zoomView = selectoption_dialog.findViewById(R.id.zoomView);
        zoomView.setImageBitmap(imageId);

        TextView tvDescription = selectoption_dialog.findViewById(R.id.tv_description);
        tvDescription.setText(description);
        /*zoomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectoption_dialog.dismiss();
            }
        });*/

    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    /**
     * hide Soft Keyboard
     *
     * @param activity
     */
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * display progress dialog
     *
     * @param mContext
     * @return
     */
    public static Dialog showProgressDialog(Context mContext) {
        Dialog progressDialog = new Dialog(mContext);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();
        return progressDialog;
    }


    /**
     * cancel progress dialog
     *
     * @param progressDialog
     */
    public static void cancelProgressDialog(Dialog progressDialog) {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
