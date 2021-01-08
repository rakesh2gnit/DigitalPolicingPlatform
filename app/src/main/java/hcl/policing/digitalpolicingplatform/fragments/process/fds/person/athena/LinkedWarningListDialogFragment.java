package hcl.policing.digitalpolicingplatform.fragments.process.fds.person.athena;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.AthenaLinkedCustodyDetailsModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.MarkerModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.WarningsModel;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;

public class LinkedWarningListDialogFragment extends DialogFragment implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private int listType;
    private List<?> warningList;
    private LinearLayout llDetails,llWarningContent;
    private ImageView ivBack;
    private HorizontalScrollView llSteps;
    private TextView tvHeader, tvStepOne, tvStepTwo, tvStepThree, tvStepFour;
    private Dialog mProgressDialog;
    private List<AthenaLinkedCustodyDetailsModel.ReasonsBean> aReasonsBean;
    private boolean isPopulate;

    public static LinkedWarningListDialogFragment newInstance(int type, ArrayList<?> list,boolean isPopulate) {

        LinkedWarningListDialogFragment frag = new LinkedWarningListDialogFragment();
        Bundle args = new Bundle();
        args.putInt(GenericConstant.TYPE_DETAILS, type);
        args.putSerializable(GenericConstant.WARNING_LIST, list);
        args.putBoolean(GenericConstant.IS_POPULATE, isPopulate);
        frag.setArguments(args);
        return frag;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.athena_linked_warning_fragment, container, false);
        layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (getArguments() != null) {
            listType = getArguments().getInt(GenericConstant.TYPE_DETAILS);
            warningList = (ArrayList<?>) getArguments().getSerializable(GenericConstant.WARNING_LIST);
            isPopulate = getArguments().getBoolean(GenericConstant.IS_POPULATE);
        }

        initView(rootView);

        return rootView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().getAttributes().windowAnimations = R.style.custom_dialog;
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }


    private void initView(View view) {

        llDetails = view.findViewById(R.id.llDetails);
        llSteps = view.findViewById(R.id.llSteps);
        llWarningContent = view.findViewById(R.id.llWarningContent);
        ivBack = view.findViewById(R.id.ivBack);

        isPopulateView();
        loadStepLayout();
        loadWarningData();
        ivBack.setOnClickListener(this);

    }
    /**
     * Check the condition for populate view.
     */
    private void isPopulateView() {
        if (!isPopulate) {
            CoordinatorLayout.LayoutParams relativeParams = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.MATCH_PARENT);
            relativeParams.setMargins(0, 0, 0, 0);
            llDetails.setLayoutParams(relativeParams);
        }
    }

    /**
     * Load Step Layout
     */
    private void loadStepLayout() {

        TextView tvStepOne = llSteps.findViewById(R.id.tvStepOne);
        TextView tvStepTwo = llSteps.findViewById(R.id.tvStepTwo);
        TextView tvStepThree = llSteps.findViewById(R.id.tvStepThree);
        TextView tvStepFour = llSteps.findViewById(R.id.tvStepFour);

        String processName = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.PROCESS_NAME, "");
        String systemName = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.SYSTEM_NAME, "");
        String systemItems = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.SYSTEM_ITEM, "");
        String linkedItems = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.LINKED_ITEM, "");
        tvStepOne.setText(processName);
        tvStepTwo.setText(systemName);
        tvStepThree.setVisibility(View.VISIBLE);
        tvStepThree.setText(systemItems);
        tvStepFour.setVisibility(View.VISIBLE);
        tvStepFour.setText(linkedItems);
    }


    /**
     * Load the location Details
     */
    private void loadWarningData() {


        if (warningList != null && warningList.size() > 0) {
            for (int i = 0; i < warningList.size(); i++) {

                View view = layoutInflater.inflate(R.layout.athena_details_warning_items, null);

                TextView tvHeader = view.findViewById(R.id.tvHeader);
                TextView tvFromDateValue = view.findViewById(R.id.tvFromDateValue);
                TextView tvToDateValue = view.findViewById(R.id.tvToDateValue);
                TextView tvFurtherDescriptionValue = view.findViewById(R.id.tvFurtherDescriptionValue);

                if (listType == GenericConstant.TYPE_MARKER) {

                    MarkerModel mMarkersBean = (MarkerModel) warningList.get(i);
                    tvHeader.setText(mMarkersBean.getMarkervalue());
                    tvFromDateValue.setText(mMarkersBean.getFromdate());

                    if (mMarkersBean.getTodate() != null) {
                        tvToDateValue.setText(mMarkersBean.getTodate());
                    } else {
                        tvToDateValue.setText("Not Specified");
                    }
                    tvFurtherDescriptionValue.setText(mMarkersBean.getDescription());
                } else if (listType == GenericConstant.TYPE_WARNING) {

                    WarningsModel mWarningsModel = (WarningsModel) warningList.get(i);
                    tvHeader.setText(mWarningsModel.getMarkervalue());
                    tvFromDateValue.setText(mWarningsModel.getFromdate());

                    if (mWarningsModel.getTodate() != null) {
                        tvToDateValue.setText(mWarningsModel.getTodate());
                    } else {
                        tvToDateValue.setText("Not Specified");
                    }
                    tvFurtherDescriptionValue.setText(mWarningsModel.getDescription());
                }


                llWarningContent.addView(view);
            }
        }


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivBack:

                dismiss();
                DialogUtil.cancelProgressDialog(mProgressDialog);
                break;

        }
    }
}
