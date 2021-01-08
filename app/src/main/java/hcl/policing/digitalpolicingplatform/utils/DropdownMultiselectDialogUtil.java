package hcl.policing.digitalpolicingplatform.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.adapters.layoutHelper.DropDownMultiselectAdaptor;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextView;
import hcl.policing.digitalpolicingplatform.database.DatabaseHelper;
import hcl.policing.digitalpolicingplatform.database.DropDown;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.LayoutFieldHelper;
import hcl.policing.digitalpolicingplatform.models.process.PageSection_detailListBean;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;

public class DropdownMultiselectDialogUtil {

    private static Dialog dialog;
    private static RecyclerView rvList;
    private static ArrayList<DropDown> list;
    private static ArrayList<DropDown> templist;
    private static CustomTextView customTextView;
    private static CustomTextView cTextViewHeading;
    private static Context context;
    private static EditText etSearch;
    private static DropDownMultiselectAdaptor dropDownListAdaptor;
    private static int selectCount = 0;
    private static PageSection_detailListBean pageSection_detailListBean;
    private static ProcessCreationActivity mActivityRef;

    public static void updateActivity(Activity activity) {
        mActivityRef = (ProcessCreationActivity) activity;
    }

    public static void spinnerDialog(Context mContext, CustomTextView cTextView, CustomTextView cTextViewHead, LayoutFieldHelper layoutFieldHelper) {
        context = mContext;
        if(layoutFieldHelper.getPageSectionDetailListBean().getMax_Select() == 0) {
            selectCount = 1000;
        } else {
            selectCount = layoutFieldHelper.getPageSectionDetailListBean().getMax_Select();
        }

        dialog = new Dialog(mContext, R.style.CustomDialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dropdown_multiselect_dialog);
        TextView tvHead = dialog.findViewById(R.id.tvHeader);
        tvHead.setText(cTextViewHead.getText().toString());
        etSearch = dialog.findViewById(R.id.et_search);
        rvList = dialog.findViewById(R.id.rvList);
        setRecyclerViewProperty(mContext, rvList);
        Button btnOk = dialog.findViewById(R.id.btn_ok);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dismiss();
                    StringBuilder answer = new StringBuilder();
                    cTextViewHeading.setTextColor(ContextCompat.getColor(context, R.color.hint_color_black));
                    customTextView.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_gray_corner));
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getStatus().equalsIgnoreCase("1")) {
                            answer.append(list.get(i).getDescription()+",");
                        }
                    }
                    customTextView.setText(context, customTextView, answer.toString());
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), DropdownMultiselectDialogUtil.class, "onOkClick");
                }
            }
        });
        cTextViewHeading = cTextViewHead;
        customTextView = cTextView;
        pageSection_detailListBean = layoutFieldHelper.getPageSectionDetailListBean();
        getSpinnerList(mContext, layoutFieldHelper.getDropdownArray());
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0) {
                    filter(s.toString());
                } else {
                    if (list != null && list.size() > 0) {
                        rvList.removeAllViews();
                        dropDownListAdaptor = new DropDownMultiselectAdaptor(context, list, onItemClick);
                        rvList.setAdapter(dropDownListAdaptor);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (etSearch.getRight() - etSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        etSearch.setText("");
                        if (list != null && list.size() > 0) {
                            rvList.removeAllViews();
                            dropDownListAdaptor = new DropDownMultiselectAdaptor(context, list, onItemClick);
                            rvList.setAdapter(dropDownListAdaptor);
                        }
                        return true;
                    }
                }
                return false;
            }
        });
        dialog.show();
    }

    private static void setRecyclerViewProperty(Context context, RecyclerView rvComponentList) {
        rvComponentList.setHasFixedSize(true);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(context);
        rvComponentList.setLayoutManager(layoutmanager);
    }

    private static void getSpinnerList(Context context, String dropdownArray) {
        list = new ArrayList<>();
        list.clear();
        list = getDropdownList(context, dropdownArray);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setStatus("0");
        }
        if (list != null && list.size() > 0) {
            dropDownListAdaptor = new DropDownMultiselectAdaptor(context, list, onItemClick);
            rvList.setAdapter(dropDownListAdaptor);
        }
    }

    private static void filter(String text){
        templist = new ArrayList();
        if(list != null && list.size() > 0) {
            for (DropDown d : list) {
                //or use .equal(text) with you want equal match
                //use .toLowerCase() for better matches
                if (d.getDescription().contains(text)) {
                    templist.add(d);
                }
            }
            //update recyclerview
            //dropDownListAdaptor.filterList(temp);
            rvList.removeAllViews();
            dropDownListAdaptor = new DropDownMultiselectAdaptor(context, templist, onItemClickSearch);
            rvList.setAdapter(dropDownListAdaptor);
        }
    }

    private static OnItemClickListener.OnItemClickCallback onItemClick = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                //Log.e("DROP DOWN "," MULTISELECT >>>> "+list.get(position).getDescription());
                int count = 0;
                for (int i = 0; i < list.size(); i++) {
                    if(list.get(i).getStatus().equalsIgnoreCase("1")) {
                        count = count + 1;
                    }
                }
                for (int i = 0; i < list.size(); i++) {
                    if (position == i) {
                        if (list.get(position).getStatus().equalsIgnoreCase("0")) {
                            if(count < selectCount)
                                list.get(position).setStatus("1");
                        } else {
                            list.get(position).setStatus("0");
                        }
                    }
                }
                Log.e("DROP DOWN "," MULTISELECT count >> "+selectCount);
                dropDownListAdaptor.notifyDataSetChanged();
                //rvList.removeAllViews();
                //dropDownListAdaptor = new DropDownMultiselectAdaptor(context, list, onItemClick);
                //rvList.setAdapter(dropDownListAdaptor);
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), DropdownMultiselectDialogUtil.class, "ondropdownClick");
            }
        }
    };

    private static OnItemClickListener.OnItemClickCallback onItemClickSearch = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                int count = 0;
                for (int i = 0; i < templist.size(); i++) {
                    if(templist.get(i).getStatus().equalsIgnoreCase("1")) {
                        count = count + 1;
                    }
                }
                for (int i = 0; i < templist.size(); i++) {
                    if (position == i) {
                        if (templist.get(position).getStatus().equalsIgnoreCase("0")) {
                            if(count < selectCount)
                                templist.get(position).setStatus("1");
                        } else {
                            templist.get(position).setStatus("0");
                        }
                    }
                }
                dropDownListAdaptor.notifyDataSetChanged();
                //rvList.removeAllViews();
                //dropDownListAdaptor = new DropDownMultiselectAdaptor(context, templist, onItemClickSearch);
                //rvList.setAdapter(dropDownListAdaptor);
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), DropdownMultiselectDialogUtil.class, "ondropdownClick");
            }
        }
    };

    public static void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    private static ArrayList<DropDown> getDropdownList(Context mContext, String groupCode) {
        ArrayList<DropDown> aDropDown = null;
        try {
            DatabaseHelper db = new DatabaseHelper(mContext);
            String processName = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.PROCESS_NAME, "");
            aDropDown = db.getDropdown(processName, groupCode);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "getDropdownList");
        }

        return aDropDown;
    }
}
