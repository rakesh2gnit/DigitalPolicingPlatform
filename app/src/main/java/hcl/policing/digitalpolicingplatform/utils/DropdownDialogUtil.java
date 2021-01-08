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
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.process.flow.PageSection;
import hcl.policing.digitalpolicingplatform.adapters.layoutHelper.DropDownListAdaptor;
import hcl.policing.digitalpolicingplatform.constants.fieldName.FieldsNameConstant;
import hcl.policing.digitalpolicingplatform.constants.viewProperties.ViewPropertiesConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextView;
import hcl.policing.digitalpolicingplatform.database.DatabaseHelper;
import hcl.policing.digitalpolicingplatform.database.DropDown;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.LayoutFieldHelper;
import hcl.policing.digitalpolicingplatform.models.process.PageSection_detailListBean;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;

public class DropdownDialogUtil {

    private static Dialog dialog;
    private static RecyclerView rvList;
    private static ArrayList<DropDown> list;
    private static ArrayList<DropDown> templist;
    private static CustomTextView cTextViewHeading;
    private static CustomTextView customTextView;
    private static Context context;
    private static EditText etSearch;
    private static DropDownListAdaptor dropDownListAdaptor;
    private static PageSection_detailListBean pageSection_detailListBean;
    private static ProcessCreationActivity mActivityRef;

    public static void updateActivity(Activity activity) {
        mActivityRef = (ProcessCreationActivity) activity;
    }

    public static void spinnerDialog(Context mContext, CustomTextView cTextView, CustomTextView cTextViewHead, LayoutFieldHelper layoutFieldHelper) {
        context = mContext;
        dialog = new Dialog(mContext, R.style.CustomDialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dropdown_dialog);
        TextView tvHead = dialog.findViewById(R.id.tvHeader);
        tvHead.setText(layoutFieldHelper.getPageSectionDetailListBean().getField_Name());
        etSearch = dialog.findViewById(R.id.et_search);
        rvList = dialog.findViewById(R.id.rvList);
        setRecyclerViewProperty(mContext, rvList);

        cTextViewHeading = cTextViewHead;
        customTextView = cTextView;
        pageSection_detailListBean = layoutFieldHelper.getPageSectionDetailListBean();
        getSpinnerList(mContext, layoutFieldHelper);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    filter(s.toString());
                } else {
                    if (list != null && list.size() > 0) {
                        rvList.removeAllViews();
                        dropDownListAdaptor = new DropDownListAdaptor(context, list, onItemClick);
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

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (etSearch.getRight() - etSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        etSearch.setText("");
                        if (list != null && list.size() > 0) {
                            rvList.removeAllViews();
                            dropDownListAdaptor = new DropDownListAdaptor(context, list, onItemClick);
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

    private static void getSpinnerList(Context context, LayoutFieldHelper layoutFieldHelper) {
        list = new ArrayList<>();
        list.clear();

//        list = getSpinnerList(dropdownArray);
        list = getDropdownList(context, layoutFieldHelper.getDropdownArray(), layoutFieldHelper.getPageSectionDetailListBean().getField_ParentId());

        if (list != null && list.size() > 0) {
            dropDownListAdaptor = new DropDownListAdaptor(context, list, onItemClick);
            rvList.setAdapter(dropDownListAdaptor);
        }
    }

    private static void filter(String text) {
        templist = new ArrayList();
        if (list != null && list.size() > 0) {
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
            dropDownListAdaptor = new DropDownListAdaptor(context, templist, onItemClickSearch);
            rvList.setAdapter(dropDownListAdaptor);
        }
    }

    private static OnItemClickListener.OnItemClickCallback onItemClick = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                dismiss();
                if(pageSection_detailListBean.getShowFieldsInOneGo()) {
                    cTextViewHeading.setTextColor(ContextCompat.getColor(context, R.color.hint_color_black));
                    customTextView.setText(context, customTextView, list.get(position).getDescription());
                    customTextView.setTag(customTextView, list.get(position).getCode());
                    customTextView.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_gray_corner));
                } else {
                    //Log.e("DROPDOWN ",">> GROUP CODE >> "+list.get(position).getGroupcode());
                    //Log.e("DROPDOWN ",">> CODE >> "+list.get(position).getCode());
                    //Log.e("DROPDOWN ",">> ParentID >> "+list.get(position).getParentid());

                    cTextViewHeading.setTextColor(ContextCompat.getColor(context, R.color.hint_color_black));
                    customTextView.setText(context, customTextView, list.get(position).getDescription());
                    customTextView.setTag(customTextView, list.get(position).getCode());
                    customTextView.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_gray_corner));

                    for (int i = 0; i < mActivityRef.aPageSection_detailListBean.size(); i++) {
                        if(mActivityRef.aPageSection_detailListBean.get(i).getField_Type().toLowerCase().equalsIgnoreCase(ViewPropertiesConstant.spinner)) {
                            Log.e("DROPDOWN ",">> getField_Dependent_On >> "+mActivityRef.aPageSection_detailListBean.get(i).getField_Dependent_On());

                            if(mActivityRef.aPageSection_detailListBean.get(i).getField_Dependent_On() == pageSection_detailListBean.getField_Id()) {
                                Log.e("DROPDOWN ",">> getField_Id >> "+pageSection_detailListBean.getField_Id());
                                Log.e("DROPDOWN ",">> PARENT_Id >> "+list.get(position).getParentid());
                                //mActivityRef.aPageSection_detailListBean.get(i).setField_MasterData(list.get(position).getGroupcode());
                                mActivityRef.aPageSection_detailListBean.get(i).setField_Visibility(ViewPropertiesConstant.Visible);
                                mActivityRef.aPageSection_detailListBean.get(i).setField_ParentId(list.get(position).getParentid());
                            }
                        }
                    }
                    mActivityRef.specialLogic = null;
                    PageSection.createPageSectionDetails(mActivityRef, mActivityRef.aPageSection_detailListBean, ProcessCreationActivity.ATTRIBUTE_COUNT);
                }
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), DropdownDialogUtil.class, "ondropdownClick");
            }
        }
    };

    private static OnItemClickListener.OnItemClickCallback onItemClickSearch = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                dismiss();
                cTextViewHeading.setTextColor(ContextCompat.getColor(context, R.color.hint_color_black));
                customTextView.setText(context, customTextView, templist.get(position).getDescription());
                customTextView.setTag(customTextView, list.get(position).getCode());
                customTextView.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_gray_corner));
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), DropdownDialogUtil.class, "ondropdownClick");
            }
        }
    };

    public static void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    /**
     * Get the dropdown list
     *
     * @param mContext
     * @param groupCode
     * @return
     */
    private static ArrayList<DropDown> getDropdownList(Context mContext, String groupCode, String parentID) {
        ArrayList<DropDown> aDropDown = null;
        try {
            DatabaseHelper db = new DatabaseHelper(mContext);
            String processName = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.PROCESS_NAME, "");
            //aDropDown = db.dependantDropdown(processName, "REASONCODE-Nominal-PNC", null);
            aDropDown = db.dependantDropdown(processName, "Department", null);
            Log.e("DROP DOWN ", " VALUE >>>>> " + aDropDown.get(0).getDescription());
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "getDropdownList");
        }

        return aDropDown;
    }

}
