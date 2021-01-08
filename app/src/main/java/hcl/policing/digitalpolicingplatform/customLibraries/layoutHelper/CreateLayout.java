package hcl.policing.digitalpolicingplatform.customLibraries.layoutHelper;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.process.flow.PopulateFields;
import hcl.policing.digitalpolicingplatform.activities.process.flow.SearchRequest;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.viewProperties.ViewPropertiesConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomButton;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomCardView;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomCheckBox;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomCommonProperties;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomFrameLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomImageView;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomLinearLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomRelativeLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomScrollView;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomSwitchButton;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextInputEditText;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextInputLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextView;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomView;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.LayoutFieldHelper;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.models.process.PageSection_detailListBean;
import hcl.policing.digitalpolicingplatform.utils.CalendarDialogUtil;
import hcl.policing.digitalpolicingplatform.utils.CalendarDialogUtilNew;
import hcl.policing.digitalpolicingplatform.utils.DateUtil;
import hcl.policing.digitalpolicingplatform.utils.DropdownDialogUtil;
import hcl.policing.digitalpolicingplatform.utils.DropdownMultiselectDialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CreateLayout {

    /**
     * Create layout fields
     * @param context
     * @param layoutFieldHelper
     */

    public static void createLayoutFields(Context context, LayoutFieldHelper layoutFieldHelper) {

        String viewType = layoutFieldHelper.getViewType();
        PropertiesBean propertiesBean = layoutFieldHelper.getPropertiesBean();
        ViewGroup parentView = layoutFieldHelper.getParentView();
        String basicLayout = layoutFieldHelper.getBasicLayout();
        PageSection_detailListBean pageSection_detailListBean = layoutFieldHelper.getPageSectionDetailListBean();

        switch (viewType.toLowerCase()) {
            case ViewPropertiesConstant.linearLayout:
                try {
                    CustomLinearLayout customLinearLayout = new CustomLinearLayout(context);
                    customLinearLayout.SetLinearLayout(context, propertiesBean);

                    if (parentView != null) {
                        parentView.addView(customLinearLayout);
                    }
                    if (basicLayout != null && basicLayout.equalsIgnoreCase("start")) {
                        Activity a = (Activity) context;
                        a.setContentView(customLinearLayout);
                    }
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateLayout.class, "linearLayout");
                }
                break;

            case ViewPropertiesConstant.textView:
                try {
                    CustomTextView customTextView = new CustomTextView(context);
                    customTextView.SetTextView(context, propertiesBean);

                    if (parentView != null) {
                        parentView.addView(customTextView);
                    }
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateLayout.class, "textView");
                }
                break;

            case ViewPropertiesConstant.textInputLayout:
                try {
                    CustomTextInputEditText customTextInputEditText = new CustomTextInputEditText(context);
                    customTextInputEditText.SetTextInputEditText(context, propertiesBean);
                    customTextInputEditText.setVisibility(View.VISIBLE);

                    CustomCommonProperties customCommonProperties = new CustomCommonProperties();
                    customCommonProperties.setWidth(context, customTextInputEditText, -1);

                    CustomTextInputLayout customTextInputLayout = new CustomTextInputLayout(context);
                    propertiesBean.setId(pageSection_detailListBean.getField_Id());
                    customTextInputLayout.SetTextInputLayout(context, propertiesBean, customTextInputEditText);

                    if (parentView != null) {
                        parentView.addView(customTextInputLayout);
                    }
                    String selectLogic = layoutFieldHelper.getPageSectionDetailListBean().getSelect_Logic();
                    if(selectLogic != null && !selectLogic.equalsIgnoreCase("")) {
                        PopulateFields.populateSingleField(context, customTextInputEditText, selectLogic);
                    }
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateLayout.class, "textInputLayout");
                }
                break;

            case ViewPropertiesConstant.textInputLayoutMultiline:
                try {
                    CustomTextInputEditText customTextInputEditText = new CustomTextInputEditText(context);
                    propertiesBean.setMinHeight(200);
                    propertiesBean.setMaxLines(10);
                    propertiesBean.setGravity(ViewPropertiesConstant.ClipVertical);
                    customTextInputEditText.SetTextInputEditText(context, propertiesBean);
                    customTextInputEditText.setVisibility(View.VISIBLE);

                    CustomCommonProperties customCommonProperties = new CustomCommonProperties();
                    customCommonProperties.setWidth(context, customTextInputEditText, -1);

                    CustomTextInputLayout customTextInputLayout = new CustomTextInputLayout(context);
                    propertiesBean.setId(pageSection_detailListBean.getField_Id());
                    customTextInputLayout.SetTextInputLayout(context, propertiesBean, customTextInputEditText);

                    if (parentView != null) {
                        parentView.addView(customTextInputLayout);
                    }
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateLayout.class, "textInputLayout");
                }
                break;

            case ViewPropertiesConstant.button:
                try {
                    CustomButton customButton = new CustomButton(context);
                    customButton.SetButton(context, propertiesBean);

                    if (parentView != null) {
                        parentView.addView(customButton);
                    }
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateLayout.class, "button");
                }
                break;

            case ViewPropertiesConstant.imageView:
                try {
                    CustomImageView customImageView = new CustomImageView(context);
                    customImageView.SetImageView(context, propertiesBean);

                    if (parentView != null) {
                        parentView.addView(customImageView);
                    }
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateLayout.class, "imageView");
                }
                break;

            case ViewPropertiesConstant.cardView:
                try {
                    CustomCardView customCardView = new CustomCardView(context);
                    customCardView.SetCardView(context, propertiesBean);

                    if (parentView != null) {
                        parentView.addView(customCardView);
                    }
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateLayout.class, "cardView");
                }
                break;

            case ViewPropertiesConstant.scrollView:
                try {
                    CustomScrollView customScrollView = new CustomScrollView(context);
                    customScrollView.SetScrollView(context, propertiesBean);

                    if (parentView != null) {
                        parentView.addView(customScrollView);
                    }
                    if (basicLayout.equalsIgnoreCase("start")) {
                        Activity a = (Activity) context;
                        a.setContentView(customScrollView);
                    }
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateLayout.class, "scrollView");
                }
                break;

            case ViewPropertiesConstant.spinner:
                try {
                    CustomTextView customTextView = new CustomTextView(context);
                    propertiesBean.setWidth(-1);
                    customTextView.SetTextView(context, propertiesBean);
                    customTextView.setVisibility(View.VISIBLE);
                    customTextView.setDrawablesLRTB(context, customTextView, 0, 0, R.drawable.ic_arrow_down, 0);
                    CustomCommonProperties customCommonProperties = new CustomCommonProperties();
                    customCommonProperties.setBackgroundDrawable(context, customTextView, ViewPropertiesConstant.BackgroundSpinner);

                    CustomRelativeLayout mainRelativeLayout = new CustomRelativeLayout(context);
                    propertiesBean.setWidth(-1);
                    propertiesBean.setHeight(0);
                    propertiesBean.setId(pageSection_detailListBean.getField_Id());
                    propertiesBean.setText(pageSection_detailListBean.getField_Name());

                    mainRelativeLayout.SetRelativeLayout(context, propertiesBean);
                    customCommonProperties.setMargins(context, mainRelativeLayout, 0, 5, 0, 0);
                    customCommonProperties.setPadding(context, mainRelativeLayout, 0, 0, 0, 0);

                    mainRelativeLayout.addView(customTextView);

                    CustomTextView customTextV = new CustomTextView(context);
                    propertiesBean.setWidth(0);
                    propertiesBean.setHeight(0);

                    customTextV.SetTextView(context, propertiesBean);
                    customTextV.setVisibility(View.VISIBLE);
                    customTextV.setText(context, customTextV, propertiesBean.getHint());
                    customTextV.setTextColor(context, customTextV, propertiesBean.getHintColor());
                    customCommonProperties.setPadding(context, customTextV, 5, 0, 5, 0);
                    customCommonProperties.setMargins(context, customTextV, 15, -5, 0, 0);
                    customCommonProperties.setBackgroundColor(context, customTextV, ViewPropertiesConstant.Color_White);

                    mainRelativeLayout.addView(customTextV);

                    if (parentView != null) {
                        parentView.addView(mainRelativeLayout);
                    }
                    new Handler().postDelayed(() -> {
                        if (layoutFieldHelper.getParentView().getChildCount() == 1
                            || layoutFieldHelper.getParentView().indexOfChild(mainRelativeLayout) == 0) {
                            DropdownDialogUtil.spinnerDialog(context, customTextView, customTextV, layoutFieldHelper);
                        }
                    }, 700);

                    customTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DropdownDialogUtil.spinnerDialog(context, customTextView, customTextV, layoutFieldHelper);
                        }
                    });
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateLayout.class, "spinner");
                }
                break;

            case ViewPropertiesConstant.spinnerMultiselect:
                try {
                    CustomTextView customTextView = new CustomTextView(context);
                    propertiesBean.setWidth(-1);
                    customTextView.SetTextView(context, propertiesBean);
                    customTextView.setVisibility(View.VISIBLE);
                    customTextView.setDrawablesLRTB(context, customTextView, 0, 0, R.drawable.ic_arrow_down, 0);
                    CustomCommonProperties customCommonProperties = new CustomCommonProperties();
                    customCommonProperties.setBackgroundDrawable(context, customTextView, ViewPropertiesConstant.BackgroundSpinner);

                    CustomRelativeLayout mainRelativeLayout = new CustomRelativeLayout(context);
                    propertiesBean.setWidth(-1);
                    propertiesBean.setHeight(0);
                    propertiesBean.setId(pageSection_detailListBean.getField_Id());
                    propertiesBean.setText(pageSection_detailListBean.getField_Name());

                    mainRelativeLayout.SetRelativeLayout(context, propertiesBean);
                    customCommonProperties.setMargins(context, mainRelativeLayout, 0, 5, 0, 0);
                    customCommonProperties.setPadding(context, mainRelativeLayout, 0, 0, 0, 0);

                    mainRelativeLayout.addView(customTextView);

                    CustomTextView customTextV = new CustomTextView(context);
                    propertiesBean.setWidth(0);
                    propertiesBean.setHeight(0);

                    customTextV.SetTextView(context, propertiesBean);
                    customTextV.setVisibility(View.VISIBLE);
                    customTextV.setText(context, customTextV, propertiesBean.getHint());
                    customTextV.setTextColor(context, customTextV, propertiesBean.getHintColor());
                    customCommonProperties.setPadding(context, customTextV, 5, 0, 5, 0);
                    customCommonProperties.setMargins(context, customTextV, 15, -5, 0, 0);
                    customCommonProperties.setBackgroundColor(context, customTextV, ViewPropertiesConstant.Color_White);

                    mainRelativeLayout.addView(customTextV);

                    if (parentView != null) {
                        parentView.addView(mainRelativeLayout);
                    }
                    new Handler().postDelayed(() -> {
                        if (layoutFieldHelper.getParentView().getChildCount() == 1
                                || layoutFieldHelper.getParentView().indexOfChild(mainRelativeLayout) == 0) {
                            DropdownMultiselectDialogUtil.spinnerDialog(context, customTextView, customTextV, layoutFieldHelper);
                        }
                    }, 700);

                    customTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DropdownMultiselectDialogUtil.spinnerDialog(context, customTextView, customTextV, layoutFieldHelper);
                        }
                    });
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateLayout.class, "spinnerMultiselect");
                }
                break;

            case ViewPropertiesConstant.spinnerNested:
                try {
                    CustomTextView customTextView = new CustomTextView(context);
                    propertiesBean.setWidth(-1);
                    customTextView.SetTextView(context, propertiesBean);
                    customTextView.setVisibility(View.VISIBLE);
                    customTextView.setDrawablesLRTB(context, customTextView,0,0, R.drawable.ic_arrow_down, 0);
                    CustomCommonProperties customCommonProperties = new CustomCommonProperties();
                    customCommonProperties.setBackgroundDrawable(context, customTextView, ViewPropertiesConstant.BackgroundSpinner);

                    CustomRelativeLayout mainRelativeLayout = new CustomRelativeLayout(context);
                    propertiesBean.setWidth(-1);
                    propertiesBean.setHeight(0);
                    propertiesBean.setId(pageSection_detailListBean.getField_Id());
                    propertiesBean.setText(pageSection_detailListBean.getField_Name());

                    mainRelativeLayout.SetRelativeLayout(context, propertiesBean);
                    customCommonProperties.setMargins(context, mainRelativeLayout, 0, 5, 0, 0);
                    customCommonProperties.setPadding(context, mainRelativeLayout, 0, 0, 0, 0);

                    mainRelativeLayout.addView(customTextView);

                    CustomTextView customTextV = new CustomTextView(context);
                    propertiesBean.setWidth(0);
                    propertiesBean.setHeight(0);

                    customTextV.SetTextView(context, propertiesBean);
                    customTextV.setVisibility(View.VISIBLE);
                    customTextV.setText(context, customTextV, propertiesBean.getHint());
                    customTextV.setTextColor(context, customTextV, propertiesBean.getHintColor());
                    customCommonProperties.setPadding(context, customTextV, 5, 0, 5, 0);
                    customCommonProperties.setMargins(context, customTextV, 15, -5, 0, 0);
                    customCommonProperties.setBackgroundColor(context, customTextV, ViewPropertiesConstant.Color_White);

                    mainRelativeLayout.addView(customTextV);

                    if (parentView != null) {
                        parentView.addView(mainRelativeLayout);
                    }
                    new Handler().postDelayed(() -> {
                        if (layoutFieldHelper.getParentView().getChildCount() == 1) {
                            DropdownMultiselectDialogUtil.spinnerDialog(context, customTextView, customTextV, layoutFieldHelper);
                        }
                    }, 700);

                    customTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DropdownMultiselectDialogUtil.spinnerDialog(context, customTextView, customTextV, layoutFieldHelper);
                        }
                    });
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateLayout.class, "spinnerMultiselect");
                }
                break;

            case ViewPropertiesConstant.view:
                try {
                    CustomView customView = new CustomView(context);
                    customView.SetView(context, propertiesBean);

                    if (parentView != null) {
                        parentView.addView(customView);
                    }
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateLayout.class, "parentView");
                }
                break;

            case ViewPropertiesConstant.checkBox:
                try {
                    CustomCheckBox customCheckBox = new CustomCheckBox(context);
                    customCheckBox.SetCheckBox(context, propertiesBean);

                    if (parentView != null) {
                        parentView.addView(customCheckBox);
                    }
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateLayout.class, "checkBox");
                }
                break;

            case ViewPropertiesConstant.frameLayout:
                try {
                    CustomFrameLayout customFrameLayout = new CustomFrameLayout(context);
                    customFrameLayout.SetFrameLayout(context, propertiesBean);

                    if (parentView != null) {
                        parentView.addView(customFrameLayout);
                    }
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateLayout.class, "frameLayout");
                }
                break;

            case ViewPropertiesConstant.selector:
                try {
                    String [] textSelect = propertiesBean.getText().split(",");
                    CustomLinearLayout customLinearLayout = new CustomLinearLayout(context);
                    propertiesBean.setWidth(-1);
                    propertiesBean.setPaddingTop(0);
                    propertiesBean.setPaddingBottom(0);
                    propertiesBean.setPaddingLeft(0);
                    propertiesBean.setPaddingRight(0);
                    propertiesBean.setOrientation(ViewPropertiesConstant.Vertical);
                    customLinearLayout.SetLinearLayout(context, propertiesBean);

                    CustomTextView customTextView = new CustomTextView(context);
                    propertiesBean.setWidth(-1);
                    propertiesBean.setMarginTop(0);
                    propertiesBean.setMarginBottom(0);
                    propertiesBean.setMarginLeft(0);
                    propertiesBean.setMarginRight(0);
                    customTextView.SetTextView(context, propertiesBean);
                    customTextView.setText(propertiesBean.getHint());
                    customLinearLayout.addView(customTextView);

                    CustomLinearLayout customLinearH = new CustomLinearLayout(context);
                    propertiesBean.setWidth(0);
                    propertiesBean.setHeight(40);
                    propertiesBean.setMarginTop(5);
                    propertiesBean.setOrientation(ViewPropertiesConstant.Horizontal);
                    customLinearH.SetLinearLayout(context, propertiesBean);
                    CustomCommonProperties customCommonP = new CustomCommonProperties();
                    customCommonP.setBackgroundDrawable(context, customLinearH, ViewPropertiesConstant.BGYellowCorner);

                    CustomTextView customText1 = new CustomTextView(context);
                    propertiesBean.setWidth(0);
                    propertiesBean.setHeight(40);
                    propertiesBean.setMarginTop(0);
                    propertiesBean.setMinWidth(70);
                    propertiesBean.setPaddingLeft(16);
                    propertiesBean.setPaddingRight(16);
                    propertiesBean.setGravity(ViewPropertiesConstant.Center);
                    customText1.SetTextView(context, propertiesBean);
                    customText1.setText(textSelect[0]);
                    customLinearH.addView(customText1);

                    CustomTextView customView = new CustomTextView(context);
                    propertiesBean.setWidth(2);
                    propertiesBean.setHeight(40);
                    customView.SetTextView(context, propertiesBean);
                    CustomCommonProperties customCommon = new CustomCommonProperties();
                    customCommon.setBackgroundColor(context, customView, ViewPropertiesConstant.Color_Gray_Light);
                    customLinearH.addView(customView);

                    CustomTextView customText2 = new CustomTextView(context);
                    propertiesBean.setWidth(0);
                    propertiesBean.setHeight(40);
                    propertiesBean.setMarginTop(0);
                    propertiesBean.setMinWidth(70);
                    propertiesBean.setPaddingLeft(16);
                    propertiesBean.setPaddingRight(16);
                    propertiesBean.setGravity(ViewPropertiesConstant.Center);
                    customText2.SetTextView(context, propertiesBean);
                    customText2.setText(textSelect[1]);
                    customLinearH.addView(customText2);

                    customLinearLayout.addView(customLinearH);

                    if (parentView != null) {
                        parentView.addView(customLinearLayout);
                    }

                    customText1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SearchRequest.setSearchRequest(customTextView.getText().toString(), customText1.getText().toString());
                            customTextView.setTextColor(context, customTextView, ViewPropertiesConstant.Color_Black);
                            CustomCommonProperties customCommonProperties = new CustomCommonProperties();

                            customCommonProperties.setBackgroundDrawable(context, customLinearH, ViewPropertiesConstant.BGYellowCorner);

                            customCommonProperties.setBackgroundDrawable(context, customText1, ViewPropertiesConstant.BGLeftBlue);
                            customText1.setTextColor(context, customText1, ViewPropertiesConstant.Color_White);

                            customCommonProperties.setBackgroundColor(context, customText2, ViewPropertiesConstant.Color_Transparent);
                            customText2.setTextColor(context, customText2, ViewPropertiesConstant.Color_Black);
                        }
                    });
                    customText2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SearchRequest.setSearchRequest(customTextView.getText().toString(), customText2.getText().toString());
                            customTextView.setTextColor(context, customTextView, ViewPropertiesConstant.Color_Black);
                            CustomCommonProperties customCommonProperties = new CustomCommonProperties();

                            customCommonProperties.setBackgroundDrawable(context, customLinearH, ViewPropertiesConstant.BGYellowCorner);

                            customCommonProperties.setBackgroundColor(context, customText1, ViewPropertiesConstant.Color_Transparent);
                            customText1.setTextColor(context, customText1, ViewPropertiesConstant.Color_Black);

                            customCommonProperties.setBackgroundDrawable(context, customText2, ViewPropertiesConstant.BGRightBlue);
                            customText2.setTextColor(context, customText2, ViewPropertiesConstant.Color_White);
                        }
                    });
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateLayout.class, "checkBox");
                }
                break;

            case ViewPropertiesConstant.dateTime:
                try {
                    CustomTextView customTextView = new CustomTextView(context);
                    propertiesBean.setWidth(-1);
                    customTextView.SetTextView(context, propertiesBean);
                    customTextView.setVisibility(View.VISIBLE);
                    CustomCommonProperties customCommonProperties = new CustomCommonProperties();
                    customCommonProperties.setBackgroundDrawable(context, customTextView, ViewPropertiesConstant.BackgroundSpinner);

                    CustomRelativeLayout mainRelativeLayout = new CustomRelativeLayout(context);
                    propertiesBean.setWidth(-1);
                    propertiesBean.setHeight(0);
                    propertiesBean.setId(pageSection_detailListBean.getField_Id());
                    mainRelativeLayout.SetRelativeLayout(context, propertiesBean);
                    customCommonProperties.setMargins(context, mainRelativeLayout, 0, 5, 0, 0);
                    customCommonProperties.setPadding(context, mainRelativeLayout, 0, 0, 0, 0);

                    mainRelativeLayout.addView(customTextView);

                    CustomTextView customTextV = new CustomTextView(context);
                    propertiesBean.setWidth(0);
                    propertiesBean.setHeight(0);
                    customTextV.SetTextView(context, propertiesBean);
                    customTextV.setVisibility(View.VISIBLE);
                    customTextV.setText(context, customTextV, propertiesBean.getHint());
                    customTextV.setTextColor(context, customTextV, propertiesBean.getHintColor());
                    customCommonProperties.setPadding(context, customTextV, 5, 0, 5, 0);
                    customCommonProperties.setMargins(context, customTextV, 15, -5, 0, 0);
                    customCommonProperties.setBackgroundColor(context, customTextV, ViewPropertiesConstant.Color_White);

                    mainRelativeLayout.addView(customTextV);

                    if (parentView != null) {
                        parentView.addView(mainRelativeLayout);
                    }
                    String selectLogic = layoutFieldHelper.getPageSectionDetailListBean().getSelect_Logic();
                    if(selectLogic != null && !selectLogic.equalsIgnoreCase("")) {
                        if(selectLogic.equalsIgnoreCase(GenericConstant.POPULATE_CURRENT_DATE)) {
                            customTextView.setText(DateUtil.currentDateTime());
                        } else if(selectLogic.equalsIgnoreCase(GenericConstant.POPULATE_FUTURE_DATE)) {
                            customTextView.setText(DateUtil.futureDateTime());
                        }
                    }
                    customTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CalendarDialogUtil.dateTimeDialog(context, customTextView, customTextV, layoutFieldHelper);
                        }
                    });
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateLayout.class, "calendar");
                }
                break;

            case ViewPropertiesConstant.date:
                try {
                    CustomTextView customTextView = new CustomTextView(context);
                    propertiesBean.setWidth(-1);
                    customTextView.SetTextView(context, propertiesBean);
                    customTextView.setVisibility(View.VISIBLE);
                    CustomCommonProperties customCommonProperties = new CustomCommonProperties();
                    customCommonProperties.setBackgroundDrawable(context, customTextView, ViewPropertiesConstant.BackgroundSpinner);

                    CustomRelativeLayout mainRelativeLayout = new CustomRelativeLayout(context);
                    propertiesBean.setWidth(-1);
                    propertiesBean.setHeight(0);
                    propertiesBean.setId(pageSection_detailListBean.getField_Id());
                    mainRelativeLayout.SetRelativeLayout(context, propertiesBean);
                    customCommonProperties.setMargins(context, mainRelativeLayout, 0, 5, 0, 0);
                    customCommonProperties.setPadding(context, mainRelativeLayout, 0, 0, 0, 0);

                    mainRelativeLayout.addView(customTextView);

                    CustomTextView customTextV = new CustomTextView(context);
                    propertiesBean.setWidth(0);
                    propertiesBean.setHeight(0);
                    customTextV.SetTextView(context, propertiesBean);
                    customTextV.setVisibility(View.VISIBLE);
                    customTextV.setText(context, customTextV, propertiesBean.getHint());
                    customTextV.setTextColor(context, customTextV, propertiesBean.getHintColor());
                    customCommonProperties.setPadding(context, customTextV, 5, 0, 5, 0);
                    customCommonProperties.setMargins(context, customTextV, 15, -5, 0, 0);
                    customCommonProperties.setBackgroundColor(context, customTextV, ViewPropertiesConstant.Color_White);

                    mainRelativeLayout.addView(customTextV);

                    if (parentView != null) {
                        parentView.addView(mainRelativeLayout);
                    }
                    String selectLogic = layoutFieldHelper.getPageSectionDetailListBean().getSelect_Logic();
                    if(selectLogic != null && !selectLogic.equalsIgnoreCase("")) {
                        if(selectLogic.equalsIgnoreCase(GenericConstant.POPULATE_CURRENT_DATE)) {
                            customTextView.setText(DateUtil.currentDate());
                        } else if(selectLogic.equalsIgnoreCase(GenericConstant.POPULATE_FUTURE_DATE)) {
                            customTextView.setText(DateUtil.futureDate());
                        }
                    }
                    customTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //CalendarDialogUtil.dateDialog(context, customTextView, customTextV, layoutFieldHelper);
                            CalendarDialogUtilNew.dateDialog(context, customTextView, customTextV, layoutFieldHelper);
                        }
                    });
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateLayout.class, "calendar");
                }
                break;

            case ViewPropertiesConstant.time:
                try {
                    CustomTextView customTextView = new CustomTextView(context);
                    propertiesBean.setWidth(-1);
                    customTextView.SetTextView(context, propertiesBean);
                    customTextView.setVisibility(View.VISIBLE);
                    CustomCommonProperties customCommonProperties = new CustomCommonProperties();
                    customCommonProperties.setBackgroundDrawable(context, customTextView, ViewPropertiesConstant.BackgroundSpinner);

                    CustomRelativeLayout mainRelativeLayout = new CustomRelativeLayout(context);
                    propertiesBean.setWidth(-1);
                    propertiesBean.setHeight(0);
                    propertiesBean.setId(pageSection_detailListBean.getField_Id());
                    mainRelativeLayout.SetRelativeLayout(context, propertiesBean);
                    customCommonProperties.setMargins(context, mainRelativeLayout, 0, 5, 0, 0);
                    customCommonProperties.setPadding(context, mainRelativeLayout, 0, 0, 0, 0);

                    mainRelativeLayout.addView(customTextView);

                    CustomTextView customTextV = new CustomTextView(context);
                    propertiesBean.setWidth(0);
                    propertiesBean.setHeight(0);
                    customTextV.SetTextView(context, propertiesBean);
                    customTextV.setVisibility(View.VISIBLE);
                    customTextV.setText(context, customTextV, propertiesBean.getHint());
                    customTextV.setTextColor(context, customTextV, propertiesBean.getHintColor());
                    customCommonProperties.setPadding(context, customTextV, 5, 0, 5, 0);
                    customCommonProperties.setMargins(context, customTextV, 15, -5, 0, 0);
                    customCommonProperties.setBackgroundColor(context, customTextV, ViewPropertiesConstant.Color_White);

                    mainRelativeLayout.addView(customTextV);

                    if (parentView != null) {
                        parentView.addView(mainRelativeLayout);
                    }
                    customTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CalendarDialogUtil.timeDialog(context, customTextView, customTextV);
                        }
                    });
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateLayout.class, "calendar");
                }
                break;

            case ViewPropertiesConstant.switchButton:
                try {
                    CustomSwitchButton customSwitchButton = new CustomSwitchButton(context);
                    propertiesBean.setId(pageSection_detailListBean.getField_Id());
                    customSwitchButton.SetButton(context, propertiesBean);

                    if (parentView != null) {
                        parentView.addView(customSwitchButton);
                    }

                    customSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                            if (!isChecked) {
                                ArrayList<String> childIdArrayShow = new ArrayList<>(Arrays.asList(pageSection_detailListBean.getShow_No_Child_Id().split(",")));
                                ArrayList<String> childIdArrayHide = new ArrayList<>(Arrays.asList(pageSection_detailListBean.getHide_No_Child_Id().split(",")));

                                int viewCount = parentView.getChildCount();
                                if (viewCount > 0) {
                                    for (int i = 0; i < viewCount; i++) {
                                        View view = parentView.getChildAt(i);
                                        if (view instanceof CustomRelativeLayout) {
                                            CustomRelativeLayout customRelativeLayout = (CustomRelativeLayout) view;
                                            int id = customRelativeLayout.getId();
                                            if (childIdArrayShow.contains(String.valueOf(id))) {
                                                customRelativeLayout.setVisibility(View.VISIBLE);
                                            }
                                            if (childIdArrayHide.contains(String.valueOf(id))) {
                                                customRelativeLayout.setVisibility(View.GONE);
                                            }
                                        }
                                        if (view instanceof CustomTextInputLayout) {
                                            CustomTextInputLayout customTextInputLayout = (CustomTextInputLayout) view;
                                            int id = customTextInputLayout.getId();
                                            if (childIdArrayShow.contains(String.valueOf(id))) {
                                                customTextInputLayout.setVisibility(View.VISIBLE);
                                            }
                                            if (childIdArrayHide.contains(String.valueOf(id))) {
                                                customTextInputLayout.setVisibility(View.GONE);
                                            }
                                        }
                                        if (view instanceof CustomSwitchButton) {
                                            CustomSwitchButton customSwitchButton1 = (CustomSwitchButton) view;
                                            int id = customSwitchButton1.getId();
                                            if (childIdArrayShow.contains(String.valueOf(id))) {
                                                customSwitchButton1.setVisibility(View.VISIBLE);
                                            }
                                            if (childIdArrayHide.contains(String.valueOf(id))) {
                                                customSwitchButton1.setVisibility(View.GONE);
                                            }
                                        }
                                    }
                                }
                            } else {

                                ArrayList<String> childIdArrayShow = new ArrayList<>(Arrays.asList(pageSection_detailListBean.getShow_Yes_Child_Id().split(",")));
                                ArrayList<String> childIdArrayHide = new ArrayList<>(Arrays.asList(pageSection_detailListBean.getHide_Yes_Child_Id().split(",")));

                                int viewCount = parentView.getChildCount();
                                if (viewCount > 0) {
                                    for (int i = 0; i < viewCount; i++) {
                                        View view = parentView.getChildAt(i);
                                        if (view instanceof CustomRelativeLayout) {
                                            CustomRelativeLayout customRelativeLayout = (CustomRelativeLayout) view;
                                            int id = customRelativeLayout.getId();
                                            if (childIdArrayShow.contains(String.valueOf(id))) {
                                                customRelativeLayout.setVisibility(View.VISIBLE);
                                            }
                                            if (childIdArrayHide.contains(String.valueOf(id))) {
                                                customRelativeLayout.setVisibility(View.GONE);
                                            }
                                        }
                                        if (view instanceof CustomTextInputLayout) {
                                            CustomTextInputLayout customTextInputLayout = (CustomTextInputLayout) view;
                                            int id = customTextInputLayout.getId();
                                            if (childIdArrayShow.contains(String.valueOf(id))) {
                                                customTextInputLayout.setVisibility(View.VISIBLE);
                                            }
                                            if (childIdArrayHide.contains(String.valueOf(id))) {
                                                customTextInputLayout.setVisibility(View.GONE);
                                            }
                                        }
                                        if (view instanceof CustomSwitchButton) {
                                            CustomSwitchButton customSwitchButton1 = (CustomSwitchButton) view;
                                            int id = customSwitchButton1.getId();
                                            if (childIdArrayShow.contains(String.valueOf(id))) {
                                                customSwitchButton1.setVisibility(View.VISIBLE);
                                            }
                                            if (childIdArrayHide.contains(String.valueOf(id))) {
                                                customSwitchButton1.setVisibility(View.GONE);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });

                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateLayout.class, "switchButton");
                }
                break;
        }
    }

}
