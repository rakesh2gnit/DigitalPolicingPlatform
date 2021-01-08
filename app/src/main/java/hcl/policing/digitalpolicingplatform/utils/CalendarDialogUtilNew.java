package hcl.policing.digitalpolicingplatform.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextView;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.LayoutFieldHelper;

public class CalendarDialogUtilNew {

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
        datePicker.setVisibility(View.GONE);
        timePicker.setVisibility(View.GONE);
        Button btnOk = dialog.findViewById(R.id.btn_ok);
        String[] year = { "17","19","22","20", "18", "21" };
        String[] months = { "01", "02","03","04","05","06","07","08","09","10","11","12"};


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,R.layout.autocomplete_text_color, year);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Find TextView control
        AutoCompleteTextView acTextView = dialog.findViewById(R.id.year);
        //Set the number of characters the user must type before the drop down list is shown
        acTextView.setThreshold(1);
        //Set the adapter
        acTextView.setAdapter(adapter);

        ArrayAdapter<String> ad = new ArrayAdapter<String>(mContext, R.layout.autocomplete_text_color, months);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AutoCompleteTextView spino = dialog.findViewById(R.id.month);
        spino.setThreshold(1);
        spino.setAdapter(ad);

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
