package hcl.policing.digitalpolicingplatform.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.core.content.ContextCompat;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextView;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.LayoutFieldHelper;

public class CalendarDialogUtil {

    private static Dialog dialog;
    private static DatePicker datePicker;
    private static TimePicker timePicker;
    private static String selectLogic;

    public static void dateDialog(Context mContext, CustomTextView customTextView, CustomTextView cTextHead, LayoutFieldHelper layoutFieldHelper) {
        dialog = new Dialog(mContext, R.style.CustomDialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.calendar_activity);
        datePicker = dialog.findViewById(R.id.datePicker);
        timePicker = dialog.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(false);
        datePicker.setVisibility(View.VISIBLE);
        timePicker.setVisibility(View.GONE);
        Button btnOk = dialog.findViewById(R.id.btn_ok);

        selectLogic = layoutFieldHelper.getPageSectionDetailListBean().getSelect_Logic();

        if(selectLogic.equalsIgnoreCase(GenericConstant.NO_FUTURE_DATE)) {
            datePicker.setMaxDate(System.currentTimeMillis());
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                cTextHead.setTextColor(ContextCompat.getColor(mContext, R.color.hint_color_black));
                customTextView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_gray_corner));
                customTextView.setText(mContext, customTextView, getDate());
            }
        });
        dialog.show();
    }

    public static void timeDialog(Context mContext, CustomTextView customTextView, CustomTextView cTextHead) {
        dialog = new Dialog(mContext, R.style.CustomDialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.calendar_activity);
        datePicker = dialog.findViewById(R.id.datePicker);
        timePicker = dialog.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(false);
        datePicker.setVisibility(View.GONE);
        timePicker.setVisibility(View.VISIBLE);
        Button btnOk = dialog.findViewById(R.id.btn_ok);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                cTextHead.setTextColor(ContextCompat.getColor(mContext, R.color.hint_color_black));
                customTextView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_gray_corner));
                customTextView.setText(mContext, customTextView, getTime());
            }
        });
        dialog.show();
    }

    public static void dateTimeDialog(Context mContext, CustomTextView customTextView, CustomTextView cTextHead, LayoutFieldHelper layoutFieldHelper) {
        dialog = new Dialog(mContext, R.style.CustomDialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.calendar_activity);
        datePicker = dialog.findViewById(R.id.datePicker);
        timePicker = dialog.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(false);
        datePicker.setVisibility(View.VISIBLE);
        timePicker.setVisibility(View.VISIBLE);
        Button btnOk = dialog.findViewById(R.id.btn_ok);

        selectLogic = layoutFieldHelper.getPageSectionDetailListBean().getSelect_Logic();

        if(selectLogic.equalsIgnoreCase(GenericConstant.NO_FUTURE_DATE)) {
            datePicker.setMaxDate(System.currentTimeMillis());
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                cTextHead.setTextColor(ContextCompat.getColor(mContext, R.color.hint_color_black));
                customTextView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_gray_corner));
                customTextView.setText(mContext, customTextView, getDate() + " " + getTime());
            }
        });
        dialog.show();
    }

    public static String getDate() {
        int monthLength = 0;
        String month = "";
        monthLength = datePicker.getMonth() + 1; //month is 0 based

        if(monthLength < 10) {
            month = "0"+monthLength;
        } else {
            month = ""+monthLength;
        }

        int dateLength = 0;
        String date = "";
        dateLength = datePicker.getDayOfMonth();

        if(dateLength < 10) {
            date = "0"+dateLength;
        } else {
            date = ""+dateLength;
        }
        return date + "/" + (month) + "/" + datePicker.getYear();
    }

    public static String getTime() {
        int hourLength = 0;
        String hour = "";
        hourLength = timePicker.getHour();

        if(hourLength < 10) {
            hour = "0"+hourLength;
        } else {
            hour = ""+hourLength;
        }

        int minuteLength = 0;
        String minute = "";
        minuteLength = timePicker.getMinute();

        if(minuteLength < 10) {
            minute = "0"+minuteLength;
        } else {
            minute = ""+minuteLength;
        }

        return hour + ":" + minute;
    }

    public static void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
