package hcl.policing.digitalpolicingplatform.fragments.process.fds.person.athena;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.LinkedArrestBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.LinkedBailBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.LinkedBailRefusalBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.LinkedBriefingBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.LinkedCasesBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.LinkedCategoryBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.LinkedCommunicationBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.LinkedCourtWarrentBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.LinkedCustodyBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.LinkedDocumentBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.LinkedIntelligenceBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.LinkedInvestigationBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.LinkedLocationBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.LinkedObjectIterationBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.LinkedOffenceBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.LinkedOrganisationBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.LinkedPersonBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.LinkedPersonSearchedBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.LinkedPropertyItemsBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.LinkedRiskAssessmentBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.LinkedSampleBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.LinkedSearchesBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.LinkedVehicleBean;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;

public class PersonATHENAListDetailsDialogFragment extends DialogFragment implements View.OnClickListener {

    private Context mContext;
    private LinearLayout llDetails, llListContent;
    private HorizontalScrollView llSteps;
    private TextView tvHeader;
    private LayoutInflater layoutInflater;
    private ArrayList<List<?>> listDetails;
    private int listType;
    private ImageView ivBack;
    ArrayList<String> labelList = new ArrayList<>(Arrays.asList("Short Summary", "Relevent Date/Time"));
    private boolean isPopulate;

    public static PersonATHENAListDetailsDialogFragment newInstance(int type, ArrayList<List<?>> list, boolean isPopulate) {

        PersonATHENAListDetailsDialogFragment frag = new PersonATHENAListDetailsDialogFragment();
        Bundle args = new Bundle();
        args.putInt(GenericConstant.TYPE_DETAILS, type);
        args.putSerializable(GenericConstant.ATHENA_LIST, list);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.person_athena_list_detail_fragment, container, false);
        layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (getArguments() != null) {
            listType = getArguments().getInt(GenericConstant.TYPE_DETAILS);
            listDetails = (ArrayList<List<?>>) getArguments().getSerializable(GenericConstant.ATHENA_LIST);
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
        llListContent = view.findViewById(R.id.llListContent);
        tvHeader = view.findViewById(R.id.tvHeader);
        llSteps = view.findViewById(R.id.llSteps);
        ivBack = view.findViewById(R.id.ivBack);

        isPopulateView();
        loadStepLayout();
        loadListData();

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

        String processName = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.PROCESS_NAME, "");
        String systemName = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.SYSTEM_NAME, "");
        String systemItems = SharedPrefUtils.getInstance(mContext).getString(SharedPrefUtils.Key.SYSTEM_ITEM, "");

        tvStepOne.setText(processName);
        tvStepTwo.setText(systemName);
        tvStepThree.setVisibility(View.VISIBLE);
        tvStepThree.setText(systemItems);
    }

    /**
     * Load List Details Data
     */
    private void loadListData() {

        switch (listType) {

            case GenericConstant.ATHENA_PERSON_LIST:

                tvHeader.setText(getString(R.string.person));
                List<LinkedPersonBean> linkedPersonBeans = (List<LinkedPersonBean>) listDetails.get(1);
                if (linkedPersonBeans != null) {
                    for (int i = 0; i < linkedPersonBeans.size(); i++) {

                        View view = layoutInflater.inflate(R.layout.person_athena_details_vehicle_item, null);
                        TextView tvLabelFirst = view.findViewById(R.id.tvLabelFirst);
                        TextView tvLabelFirstValue = view.findViewById(R.id.tvLabelFirstValue);
                        TextView tvLabelSecond = view.findViewById(R.id.tvLabelSecond);
                        TextView tvLabelSecondValue = view.findViewById(R.id.tvLabelSecondValue);
                        TextView tvLabelThirdValue = view.findViewById(R.id.tvLabelThirdValue);

                        tvLabelFirst.setText(labelList.get(0));
                        tvLabelSecond.setText(labelList.get(1));
                        tvLabelFirstValue.setText(linkedPersonBeans.get(i).getShortsummary());
                        tvLabelSecondValue.setText(linkedPersonBeans.get(i).getRelevantdatetime());
                        tvLabelThirdValue.setText(linkedPersonBeans.get(i).getDetailtext());


                        tvLabelFirstValue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.LINKED_ITEM, getString(R.string.linked_person_header));
                                loadLinkedPersonDialog(GenericConstant.ATHENA_PERSON_LIST);
                            }
                        });


                        llListContent.addView(view);
                    }
                }

                break;

            case GenericConstant.ATHENA_VEHICLE_LIST:

                tvHeader.setText(getString(R.string.vehicle));
                List<LinkedVehicleBean> linkedVehicleBeans = (List<LinkedVehicleBean>) listDetails.get(2);
                if (linkedVehicleBeans != null) {
                    for (int i = 0; i < linkedVehicleBeans.size(); i++) {

                        View view = layoutInflater.inflate(R.layout.person_athena_details_vehicle_item, null);
                        TextView tvLabelFirst = view.findViewById(R.id.tvLabelFirst);
                        TextView tvLabelFirstValue = view.findViewById(R.id.tvLabelFirstValue);
                        TextView tvLabelSecond = view.findViewById(R.id.tvLabelSecond);
                        TextView tvLabelSecondValue = view.findViewById(R.id.tvLabelSecondValue);
                        TextView tvLabelThirdValue = view.findViewById(R.id.tvLabelThirdValue);

                        tvLabelFirst.setText(labelList.get(0));
                        tvLabelSecond.setText(labelList.get(1));
                        tvLabelFirstValue.setText(linkedVehicleBeans.get(i).getShortsummary());
                        tvLabelSecondValue.setText(linkedVehicleBeans.get(i).getRelevantdatetime());
                        tvLabelThirdValue.setText(linkedVehicleBeans.get(i).getDetailtext());


                        tvLabelFirstValue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.LINKED_ITEM, getString(R.string.linked_vehicle_header));
                                loadLinkedVehicleDialog(GenericConstant.ATHENA_VEHICLE_LIST);
                            }
                        });

                        llListContent.addView(view);
                    }
                }


                break;
            case GenericConstant.ATHENA_INVESTIGATION_LIST:

                tvHeader.setText(getString(R.string.investigations));
                List<LinkedInvestigationBean> linkedInvestigationBeans = (List<LinkedInvestigationBean>) listDetails.get(3);
                if (linkedInvestigationBeans != null) {
                    for (int i = 0; i < linkedInvestigationBeans.size(); i++) {

                        View view = layoutInflater.inflate(R.layout.person_athena_details_vehicle_item, null);
                        TextView tvLabelFirst = view.findViewById(R.id.tvLabelFirst);
                        TextView tvLabelFirstValue = view.findViewById(R.id.tvLabelFirstValue);
                        TextView tvLabelSecond = view.findViewById(R.id.tvLabelSecond);
                        TextView tvLabelSecondValue = view.findViewById(R.id.tvLabelSecondValue);
                        TextView tvLabelThirdValue = view.findViewById(R.id.tvLabelThirdValue);

                        tvLabelFirst.setText(labelList.get(0));
                        tvLabelSecond.setText(labelList.get(1));
                        tvLabelFirstValue.setText(linkedInvestigationBeans.get(i).getShortsummary());
                        tvLabelSecondValue.setText(linkedInvestigationBeans.get(i).getRelevantdatetime());
                        tvLabelThirdValue.setText(linkedInvestigationBeans.get(i).getDetailtext());

                        tvLabelFirstValue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.LINKED_ITEM, getString(R.string.linked_investigation_header));
                                loadLinkedInvestigationDialog(GenericConstant.ATHENA_INVESTIGATION_LIST);
                            }
                        });

                        llListContent.addView(view);
                    }
                }

                break;
            case GenericConstant.ATHENA_INTELLIGENCE_LIST:

                tvHeader.setText(getString(R.string.intelligence_info));
                List<LinkedIntelligenceBean> linkedIntelligenceBeans = (List<LinkedIntelligenceBean>) listDetails.get(4);
                if (linkedIntelligenceBeans != null) {
                    for (int i = 0; i < linkedIntelligenceBeans.size(); i++) {

                        View view = layoutInflater.inflate(R.layout.person_athena_details_vehicle_item, null);
                        TextView tvLabelFirst = view.findViewById(R.id.tvLabelFirst);
                        TextView tvLabelFirstValue = view.findViewById(R.id.tvLabelFirstValue);
                        TextView tvLabelSecond = view.findViewById(R.id.tvLabelSecond);
                        TextView tvLabelSecondValue = view.findViewById(R.id.tvLabelSecondValue);
                        TextView tvLabelThirdValue = view.findViewById(R.id.tvLabelThirdValue);

                        tvLabelFirst.setText(labelList.get(0));
                        tvLabelSecond.setText(labelList.get(1));
                        tvLabelFirstValue.setText(linkedIntelligenceBeans.get(i).getShortsummary());
                        tvLabelSecondValue.setText(linkedIntelligenceBeans.get(i).getRelevantdatetime());
                        tvLabelThirdValue.setText(linkedIntelligenceBeans.get(i).getDetailtext());

                        tvLabelFirstValue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.LINKED_ITEM, getString(R.string.intelligence_report));
                                loadLinkedIntelligenceDialog(GenericConstant.ATHENA_INTELLIGENCE_LIST);
                            }
                        });

                        llListContent.addView(view);
                    }
                }

                break;

            case GenericConstant.ATHENA_COURT_WARRANT_LIST:

                tvHeader.setText(getString(R.string.court_warrants));
                List<LinkedCourtWarrentBean> linkedCourtWarrentBeans = (List<LinkedCourtWarrentBean>) listDetails.get(5);
                if (linkedCourtWarrentBeans != null) {
                    for (int i = 0; i < linkedCourtWarrentBeans.size(); i++) {

                        View view = layoutInflater.inflate(R.layout.person_athena_details_vehicle_item, null);
                        TextView tvLabelFirst = view.findViewById(R.id.tvLabelFirst);
                        TextView tvLabelFirstValue = view.findViewById(R.id.tvLabelFirstValue);
                        TextView tvLabelSecond = view.findViewById(R.id.tvLabelSecond);
                        TextView tvLabelSecondValue = view.findViewById(R.id.tvLabelSecondValue);
                        TextView tvLabelThirdValue = view.findViewById(R.id.tvLabelThirdValue);

                        tvLabelFirst.setText(labelList.get(0));
                        tvLabelSecond.setText(labelList.get(1));
                        tvLabelFirstValue.setText(linkedCourtWarrentBeans.get(i).getShortsummary());
                        tvLabelSecondValue.setText(linkedCourtWarrentBeans.get(i).getRelevantdatetime());
                        tvLabelThirdValue.setText(linkedCourtWarrentBeans.get(i).getDetailtext());

                        tvLabelFirstValue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.LINKED_ITEM, getString(R.string.court_warrants));
                                loadLinkedCourtWarrantDialog(GenericConstant.ATHENA_COURT_WARRANT_LIST);
                            }
                        });

                        llListContent.addView(view);
                    }
                }

                break;

            case GenericConstant.ATHENA_CASES_LIST:

                tvHeader.setText(getString(R.string.cases));
                List<LinkedCasesBean> linkedCasesBeans = (List<LinkedCasesBean>) listDetails.get(6);
                if (linkedCasesBeans != null) {
                    for (int i = 0; i < linkedCasesBeans.size(); i++) {

                        View view = layoutInflater.inflate(R.layout.person_athena_details_vehicle_item, null);
                        TextView tvLabelFirst = view.findViewById(R.id.tvLabelFirst);
                        TextView tvLabelFirstValue = view.findViewById(R.id.tvLabelFirstValue);
                        TextView tvLabelSecond = view.findViewById(R.id.tvLabelSecond);
                        TextView tvLabelSecondValue = view.findViewById(R.id.tvLabelSecondValue);
                        TextView tvLabelThirdValue = view.findViewById(R.id.tvLabelThirdValue);

                        tvLabelFirst.setText(labelList.get(0));
                        tvLabelSecond.setText(labelList.get(1));
                        tvLabelFirstValue.setText(linkedCasesBeans.get(i).getShortsummary());
                        tvLabelSecondValue.setText(linkedCasesBeans.get(i).getRelevantdatetime());
                        tvLabelThirdValue.setText(linkedCasesBeans.get(i).getDetailtext());

                        tvLabelFirstValue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.LINKED_ITEM, getString(R.string.linked_cases));
                                loadLinkedCaseDialog(GenericConstant.ATHENA_CASES_LIST);
                            }
                        });

                        llListContent.addView(view);
                    }
                }

                break;
            case GenericConstant.ATHENA_LOCATION_LIST:

                tvHeader.setText(getString(R.string.location));
                List<LinkedLocationBean> linkedLocationBeans = (List<LinkedLocationBean>) listDetails.get(7);
                if (linkedLocationBeans != null) {
                    for (int i = 0; i < linkedLocationBeans.size(); i++) {

                        View view = layoutInflater.inflate(R.layout.person_athena_details_vehicle_item, null);
                        TextView tvLabelFirst = view.findViewById(R.id.tvLabelFirst);
                        TextView tvLabelFirstValue = view.findViewById(R.id.tvLabelFirstValue);
                        TextView tvLabelSecond = view.findViewById(R.id.tvLabelSecond);
                        TextView tvLabelSecondValue = view.findViewById(R.id.tvLabelSecondValue);
                        TextView tvLabelThirdValue = view.findViewById(R.id.tvLabelThirdValue);

                        tvLabelFirst.setText(labelList.get(0));
                        tvLabelSecond.setText(labelList.get(1));
                        tvLabelFirstValue.setText(linkedLocationBeans.get(i).getShortsummary());
                        tvLabelSecondValue.setText(linkedLocationBeans.get(i).getRelevantdatetime());

                        if (linkedLocationBeans.get(i).getDetailtext() != null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                tvLabelThirdValue.setText(Html.fromHtml(linkedLocationBeans.get(i).getDetailtext(), Html.FROM_HTML_MODE_COMPACT));
                            } else {
                                tvLabelThirdValue.setText(Html.fromHtml(linkedLocationBeans.get(i).getDetailtext()));
                            }
                        }

                        tvLabelFirstValue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.LINKED_ITEM, getString(R.string.linked_location));
                                loadLinkedLocationDialog(GenericConstant.ATHENA_LOCATION_LIST);
                            }
                        });

                        llListContent.addView(view);
                    }
                }

                break;
            case GenericConstant.ATHENA_CUSTODY_LIST:

                tvHeader.setText(getString(R.string.custody_records));
                List<LinkedCustodyBean> linkedCustodyBeans = (List<LinkedCustodyBean>) listDetails.get(8);
                if (linkedCustodyBeans != null) {
                    for (int i = 0; i < linkedCustodyBeans.size(); i++) {

                        View view = layoutInflater.inflate(R.layout.person_athena_details_vehicle_item, null);
                        TextView tvLabelFirst = view.findViewById(R.id.tvLabelFirst);
                        TextView tvLabelFirstValue = view.findViewById(R.id.tvLabelFirstValue);
                        TextView tvLabelSecond = view.findViewById(R.id.tvLabelSecond);
                        TextView tvLabelSecondValue = view.findViewById(R.id.tvLabelSecondValue);
                        TextView tvLabelThirdValue = view.findViewById(R.id.tvLabelThirdValue);

                        tvLabelFirst.setText(labelList.get(0));
                        tvLabelSecond.setText(labelList.get(1));
                        tvLabelFirstValue.setText(linkedCustodyBeans.get(i).getShortsummary());
                        tvLabelSecondValue.setText(linkedCustodyBeans.get(i).getRelevantdatetime());
                        tvLabelThirdValue.setText(linkedCustodyBeans.get(i).getDetailtext());

                        tvLabelFirstValue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.LINKED_ITEM, getString(R.string.custody_records));
                                loadLinkedCustodyRecordsDialog(GenericConstant.ATHENA_CUSTODY_LIST);
                            }
                        });

                        llListContent.addView(view);
                    }
                }

                break;
            case GenericConstant.ATHENA_COMMUNICATION_LIST:

                tvHeader.setText(getString(R.string.communication));
                List<LinkedCommunicationBean> linkedCommunicationBeans = (List<LinkedCommunicationBean>) listDetails.get(9);
                if (linkedCommunicationBeans != null) {
                    for (int i = 0; i < linkedCommunicationBeans.size(); i++) {

                        View view = layoutInflater.inflate(R.layout.person_athena_details_vehicle_item, null);
                        TextView tvLabelFirst = view.findViewById(R.id.tvLabelFirst);
                        TextView tvLabelFirstValue = view.findViewById(R.id.tvLabelFirstValue);
                        TextView tvLabelSecond = view.findViewById(R.id.tvLabelSecond);
                        TextView tvLabelSecondValue = view.findViewById(R.id.tvLabelSecondValue);
                        TextView tvLabelThirdValue = view.findViewById(R.id.tvLabelThirdValue);

                        tvLabelFirst.setText(labelList.get(0));
                        tvLabelSecond.setText(labelList.get(1));
                        tvLabelFirstValue.setText(linkedCommunicationBeans.get(i).getShortsummary());
                        tvLabelSecondValue.setText(linkedCommunicationBeans.get(i).getRelevantdatetime());

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            tvLabelThirdValue.setText(Html.fromHtml(linkedCommunicationBeans.get(i).getDetailtext(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            tvLabelThirdValue.setText(Html.fromHtml(linkedCommunicationBeans.get(i).getDetailtext()));
                        }

                        tvLabelFirstValue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.LINKED_ITEM, getString(R.string.communication));
                                loadLinkedCommunicationDialog(GenericConstant.ATHENA_COMMUNICATION_LIST);
                            }
                        });
                        llListContent.addView(view);
                    }
                }

                break;
            case GenericConstant.ATHENA_ARREST_LIST:

                tvHeader.setText(getString(R.string.arrest));
                List<LinkedArrestBean> linkedArrestBeans = (List<LinkedArrestBean>) listDetails.get(10);
                if (linkedArrestBeans != null) {
                    for (int i = 0; i < linkedArrestBeans.size(); i++) {

                        View view = layoutInflater.inflate(R.layout.person_athena_details_vehicle_item, null);
                        TextView tvLabelFirst = view.findViewById(R.id.tvLabelFirst);
                        TextView tvLabelFirstValue = view.findViewById(R.id.tvLabelFirstValue);
                        TextView tvLabelSecond = view.findViewById(R.id.tvLabelSecond);
                        TextView tvLabelSecondValue = view.findViewById(R.id.tvLabelSecondValue);
                        TextView tvLabelThirdValue = view.findViewById(R.id.tvLabelThirdValue);

                        tvLabelFirst.setText(labelList.get(0));
                        tvLabelSecond.setText(labelList.get(1));
                        tvLabelFirstValue.setText(linkedArrestBeans.get(i).getShortsummary());
                        tvLabelSecondValue.setText(linkedArrestBeans.get(i).getRelevantdatetime());

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            tvLabelThirdValue.setText(Html.fromHtml(linkedArrestBeans.get(i).getDetailtext(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            tvLabelThirdValue.setText(Html.fromHtml(linkedArrestBeans.get(i).getDetailtext()));
                        }

                        tvLabelFirstValue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.LINKED_ITEM, getString(R.string.arrest));
                                loadLinkedArrestDialog(GenericConstant.ATHENA_ARREST_LIST);
                            }
                        });
                        llListContent.addView(view);
                    }
                }


                break;
            case GenericConstant.ATHENA_BAIL_LIST:

                tvHeader.setText(getString(R.string.bails));
                List<LinkedBailBean> linkedBailBeans = (List<LinkedBailBean>) listDetails.get(11);
                if (linkedBailBeans != null) {
                    for (int i = 0; i < linkedBailBeans.size(); i++) {

                        View view = layoutInflater.inflate(R.layout.person_athena_details_vehicle_item, null);
                        TextView tvLabelFirst = view.findViewById(R.id.tvLabelFirst);
                        TextView tvLabelFirstValue = view.findViewById(R.id.tvLabelFirstValue);
                        TextView tvLabelSecond = view.findViewById(R.id.tvLabelSecond);
                        TextView tvLabelSecondValue = view.findViewById(R.id.tvLabelSecondValue);
                        TextView tvLabelThirdValue = view.findViewById(R.id.tvLabelThirdValue);

                        tvLabelFirstValue.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey));
                        tvLabelFirstValue.setTypeface(tvLabelFirstValue.getTypeface(), Typeface.NORMAL);
                        tvLabelFirstValue.setTextSize(12.0f);

                        tvLabelFirst.setText(labelList.get(0));
                        tvLabelSecond.setText(labelList.get(1));
                        tvLabelFirstValue.setText(linkedBailBeans.get(i).getShortsummary());
                        tvLabelSecondValue.setText(linkedBailBeans.get(i).getRelevantdatetime());
                        tvLabelThirdValue.setText(linkedBailBeans.get(i).getDetailtext());

                        llListContent.addView(view);
                    }
                }


                break;
            case GenericConstant.ATHENA_BAIL_REFUSAL_LIST:

                tvHeader.setText(getString(R.string.bail_refusal));
                List<LinkedBailRefusalBean> linkedBailRefusalBeans = (List<LinkedBailRefusalBean>) listDetails.get(12);
                if (linkedBailRefusalBeans != null) {
                    for (int i = 0; i < linkedBailRefusalBeans.size(); i++) {

                        View view = layoutInflater.inflate(R.layout.person_athena_details_vehicle_item, null);
                        TextView tvLabelFirst = view.findViewById(R.id.tvLabelFirst);
                        TextView tvLabelFirstValue = view.findViewById(R.id.tvLabelFirstValue);
                        TextView tvLabelSecond = view.findViewById(R.id.tvLabelSecond);
                        TextView tvLabelSecondValue = view.findViewById(R.id.tvLabelSecondValue);
                        TextView tvLabelThirdValue = view.findViewById(R.id.tvLabelThirdValue);

                        tvLabelFirstValue.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey));
                        tvLabelFirstValue.setTypeface(tvLabelFirstValue.getTypeface(), Typeface.NORMAL);
                        tvLabelFirstValue.setTextSize(12.0f);

                        tvLabelFirst.setText(labelList.get(0));
                        tvLabelSecond.setText(labelList.get(1));
                        tvLabelFirstValue.setText(linkedBailRefusalBeans.get(i).getShortsummary());
                        tvLabelSecondValue.setText(linkedBailRefusalBeans.get(i).getRelevantdatetime());
                        tvLabelThirdValue.setText(linkedBailRefusalBeans.get(i).getDetailtext());

                        llListContent.addView(view);
                    }
                }

                break;

            case GenericConstant.ATHENA_DOCUMENT_LIST:

                tvHeader.setText(getString(R.string.documents));
                List<LinkedDocumentBean> linkedDocumentBeans = (List<LinkedDocumentBean>) listDetails.get(13);
                if (linkedDocumentBeans != null) {
                    for (int i = 0; i < linkedDocumentBeans.size(); i++) {

                        View view = layoutInflater.inflate(R.layout.person_athena_details_vehicle_item, null);
                        TextView tvLabelFirst = view.findViewById(R.id.tvLabelFirst);
                        TextView tvLabelFirstValue = view.findViewById(R.id.tvLabelFirstValue);
                        TextView tvLabelSecond = view.findViewById(R.id.tvLabelSecond);
                        TextView tvLabelSecondValue = view.findViewById(R.id.tvLabelSecondValue);
                        TextView tvLabelThirdValue = view.findViewById(R.id.tvLabelThirdValue);

                        tvLabelFirstValue.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey));
                        tvLabelFirstValue.setTypeface(tvLabelFirstValue.getTypeface(), Typeface.NORMAL);
                        tvLabelFirstValue.setTextSize(12.0f);

                        tvLabelFirst.setText(labelList.get(0));
                        tvLabelSecond.setText(labelList.get(1));
                        tvLabelFirstValue.setText(linkedDocumentBeans.get(i).getShortsummary());
                        tvLabelSecondValue.setText(linkedDocumentBeans.get(i).getRelevantdatetime());

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            tvLabelThirdValue.setText(Html.fromHtml(linkedDocumentBeans.get(i).getDetailtext(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            tvLabelThirdValue.setText(Html.fromHtml(linkedDocumentBeans.get(i).getDetailtext()));
                        }

                        llListContent.addView(view);
                    }
                }


                break;

            case GenericConstant.ATHENA_OFFENCE_LIST:

                tvHeader.setText(getString(R.string.offence));
                List<LinkedOffenceBean> linkedOffenceBeans = (List<LinkedOffenceBean>) listDetails.get(14);
                if (linkedOffenceBeans != null) {
                    for (int i = 0; i < linkedOffenceBeans.size(); i++) {

                        View view = layoutInflater.inflate(R.layout.person_athena_details_vehicle_item, null);
                        TextView tvLabelFirst = view.findViewById(R.id.tvLabelFirst);
                        TextView tvLabelFirstValue = view.findViewById(R.id.tvLabelFirstValue);
                        TextView tvLabelSecond = view.findViewById(R.id.tvLabelSecond);
                        TextView tvLabelSecondValue = view.findViewById(R.id.tvLabelSecondValue);
                        TextView tvLabelThirdValue = view.findViewById(R.id.tvLabelThirdValue);

                        tvLabelFirst.setText(labelList.get(0));
                        tvLabelSecond.setText(labelList.get(1));
                        tvLabelFirstValue.setText(linkedOffenceBeans.get(i).getShortsummary());
                        tvLabelSecondValue.setText(linkedOffenceBeans.get(i).getRelevantdatetime());

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            tvLabelThirdValue.setText(Html.fromHtml(linkedOffenceBeans.get(i).getDetailtext(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            tvLabelThirdValue.setText(Html.fromHtml(linkedOffenceBeans.get(i).getDetailtext()));
                        }

                        tvLabelFirstValue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.LINKED_ITEM, getString(R.string.offence));
                                loadLinkedOffenceDialog(GenericConstant.ATHENA_OFFENCE_LIST);
                            }
                        });

                        llListContent.addView(view);
                    }
                }


                break;
            case GenericConstant.ATHENA_PERSON_SEARCH_LIST:

                tvHeader.setText(getString(R.string.person_searches));
                List<LinkedPersonSearchedBean> linkedPersonSearchedBeans = (List<LinkedPersonSearchedBean>) listDetails.get(15);
                if (linkedPersonSearchedBeans != null) {
                    for (int i = 0; i < linkedPersonSearchedBeans.size(); i++) {

                        View view = layoutInflater.inflate(R.layout.person_athena_details_vehicle_item, null);
                        TextView tvLabelFirst = view.findViewById(R.id.tvLabelFirst);
                        TextView tvLabelFirstValue = view.findViewById(R.id.tvLabelFirstValue);
                        TextView tvLabelSecond = view.findViewById(R.id.tvLabelSecond);
                        TextView tvLabelSecondValue = view.findViewById(R.id.tvLabelSecondValue);
                        TextView tvLabelThirdValue = view.findViewById(R.id.tvLabelThirdValue);

                        tvLabelFirstValue.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey));
                        tvLabelFirstValue.setTypeface(tvLabelFirstValue.getTypeface(), Typeface.NORMAL);
                        tvLabelFirstValue.setTextSize(12.0f);

                        tvLabelFirst.setText(labelList.get(0));
                        tvLabelSecond.setText(labelList.get(1));
                        tvLabelFirstValue.setText(linkedPersonSearchedBeans.get(i).getShortsummary());
                        tvLabelSecondValue.setText(linkedPersonSearchedBeans.get(i).getRelevantdatetime());
                        tvLabelThirdValue.setText(linkedPersonSearchedBeans.get(i).getDetailtext());

                        llListContent.addView(view);
                    }
                }


                break;
            case GenericConstant.ATHENA_RISK_ASSESMENT_LIST:

                tvHeader.setText(getString(R.string.risk_assesments));
                List<LinkedRiskAssessmentBean> linkedRiskAssessmentBeans = (List<LinkedRiskAssessmentBean>) listDetails.get(16);
                if (linkedRiskAssessmentBeans != null) {
                    for (int i = 0; i < linkedRiskAssessmentBeans.size(); i++) {

                        View view = layoutInflater.inflate(R.layout.person_athena_details_vehicle_item, null);
                        TextView tvLabelFirst = view.findViewById(R.id.tvLabelFirst);
                        TextView tvLabelFirstValue = view.findViewById(R.id.tvLabelFirstValue);
                        TextView tvLabelSecond = view.findViewById(R.id.tvLabelSecond);
                        TextView tvLabelSecondValue = view.findViewById(R.id.tvLabelSecondValue);
                        TextView tvLabelThirdValue = view.findViewById(R.id.tvLabelThirdValue);

                        tvLabelFirstValue.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey));
                        tvLabelFirstValue.setTypeface(tvLabelFirstValue.getTypeface(), Typeface.NORMAL);
                        tvLabelFirstValue.setTextSize(12.0f);

                        tvLabelFirst.setText(labelList.get(0));
                        tvLabelSecond.setText(labelList.get(1));
                        tvLabelFirstValue.setText(linkedRiskAssessmentBeans.get(i).getShortsummary());
                        tvLabelSecondValue.setText(linkedRiskAssessmentBeans.get(i).getRelevantdatetime());
                        tvLabelThirdValue.setText(linkedRiskAssessmentBeans.get(i).getDetailtext());

                        llListContent.addView(view);
                    }
                }

                break;
            case GenericConstant.ATHENA_SAMPLE_LIST:

                tvHeader.setText(getString(R.string.sample_taken));
                List<LinkedSampleBean> linkedSampleBeans = (List<LinkedSampleBean>) listDetails.get(17);
                if (linkedSampleBeans != null) {
                    for (int i = 0; i < linkedSampleBeans.size(); i++) {

                        View view = layoutInflater.inflate(R.layout.person_athena_details_vehicle_item, null);
                        TextView tvLabelFirst = view.findViewById(R.id.tvLabelFirst);
                        TextView tvLabelFirstValue = view.findViewById(R.id.tvLabelFirstValue);
                        TextView tvLabelSecond = view.findViewById(R.id.tvLabelSecond);
                        TextView tvLabelSecondValue = view.findViewById(R.id.tvLabelSecondValue);
                        TextView tvLabelThirdValue = view.findViewById(R.id.tvLabelThirdValue);

                        tvLabelFirstValue.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey));
                        tvLabelFirstValue.setTypeface(tvLabelFirstValue.getTypeface(), Typeface.NORMAL);
                        tvLabelFirstValue.setTextSize(12.0f);
                        ;

                        tvLabelFirst.setText(labelList.get(0));
                        tvLabelSecond.setText(labelList.get(1));
                        tvLabelFirstValue.setText(linkedSampleBeans.get(i).getShortsummary());
                        tvLabelSecondValue.setText(linkedSampleBeans.get(i).getRelevantdatetime());
                        tvLabelThirdValue.setText(linkedSampleBeans.get(i).getDetailtext());

                        llListContent.addView(view);
                    }
                }

                break;

            case GenericConstant.ATHENA_BRIEFING_LIST:

                tvHeader.setText(getString(R.string.briefing_items));
                List<LinkedBriefingBean> linkedBriefingBeans = (List<LinkedBriefingBean>) listDetails.get(18);
                if (linkedBriefingBeans != null) {
                    for (int i = 0; i < linkedBriefingBeans.size(); i++) {

                        View view = layoutInflater.inflate(R.layout.person_athena_details_vehicle_item, null);
                        TextView tvLabelFirst = view.findViewById(R.id.tvLabelFirst);
                        TextView tvLabelFirstValue = view.findViewById(R.id.tvLabelFirstValue);
                        TextView tvLabelSecond = view.findViewById(R.id.tvLabelSecond);
                        TextView tvLabelSecondValue = view.findViewById(R.id.tvLabelSecondValue);
                        TextView tvLabelThirdValue = view.findViewById(R.id.tvLabelThirdValue);

                        tvLabelFirstValue.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey));
                        tvLabelFirstValue.setTypeface(tvLabelFirstValue.getTypeface(), Typeface.NORMAL);
                        tvLabelFirstValue.setTextSize(12.0f);

                        tvLabelFirst.setText(labelList.get(0));
                        tvLabelSecond.setText(labelList.get(1));
                        tvLabelFirstValue.setText(linkedBriefingBeans.get(i).getShortsummary());
                        tvLabelSecondValue.setText(linkedBriefingBeans.get(i).getRelevantdatetime());
                        tvLabelThirdValue.setText(linkedBriefingBeans.get(i).getDetailtext());

                        llListContent.addView(view);
                    }
                }

                break;


            case GenericConstant.ATHENA_ORGANIZATION_LIST:

                tvHeader.setText(getString(R.string.organization));
                List<LinkedOrganisationBean> linkedOrganisationBeans = (List<LinkedOrganisationBean>) listDetails.get(19);
                if (linkedOrganisationBeans != null) {
                    for (int i = 0; i < linkedOrganisationBeans.size(); i++) {

                        View view = layoutInflater.inflate(R.layout.person_athena_details_vehicle_item, null);
                        TextView tvLabelFirst = view.findViewById(R.id.tvLabelFirst);
                        TextView tvLabelFirstValue = view.findViewById(R.id.tvLabelFirstValue);
                        TextView tvLabelSecond = view.findViewById(R.id.tvLabelSecond);
                        TextView tvLabelSecondValue = view.findViewById(R.id.tvLabelSecondValue);
                        TextView tvLabelThirdValue = view.findViewById(R.id.tvLabelThirdValue);

                        tvLabelFirst.setText(labelList.get(0));
                        tvLabelSecond.setText(labelList.get(1));
                        tvLabelFirstValue.setText(linkedOrganisationBeans.get(i).getShortsummary());
                        tvLabelSecondValue.setText(linkedOrganisationBeans.get(i).getRelevantdatetime());

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            tvLabelThirdValue.setText(Html.fromHtml(linkedOrganisationBeans.get(i).getDetailtext(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            tvLabelThirdValue.setText(Html.fromHtml(linkedOrganisationBeans.get(i).getDetailtext()));
                        }

                        tvLabelFirstValue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.LINKED_ITEM, getString(R.string.organization));
                                loadLinkedOrganizationDialog(GenericConstant.ATHENA_ORGANIZATION_LIST);
                            }
                        });
                        llListContent.addView(view);
                    }
                }

                break;

            case GenericConstant.ATHENA_SEARCH_LIST:

                tvHeader.setText(getString(R.string.search));
                List<LinkedSearchesBean> linkedSearchesBeans = (List<LinkedSearchesBean>) listDetails.get(20);
                if (linkedSearchesBeans != null) {
                    for (int i = 0; i < linkedSearchesBeans.size(); i++) {

                        View view = layoutInflater.inflate(R.layout.person_athena_details_vehicle_item, null);
                        TextView tvLabelFirst = view.findViewById(R.id.tvLabelFirst);
                        TextView tvLabelFirstValue = view.findViewById(R.id.tvLabelFirstValue);
                        TextView tvLabelSecond = view.findViewById(R.id.tvLabelSecond);
                        TextView tvLabelSecondValue = view.findViewById(R.id.tvLabelSecondValue);
                        TextView tvLabelThirdValue = view.findViewById(R.id.tvLabelThirdValue);

                        tvLabelFirst.setText(labelList.get(0));
                        tvLabelSecond.setText(labelList.get(1));
                        tvLabelFirstValue.setText(linkedSearchesBeans.get(i).getShortsummary());
                        tvLabelSecondValue.setText(linkedSearchesBeans.get(i).getRelevantdatetime());

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            tvLabelThirdValue.setText(Html.fromHtml(linkedSearchesBeans.get(i).getDetailtext(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            tvLabelThirdValue.setText(Html.fromHtml(linkedSearchesBeans.get(i).getDetailtext()));
                        }

                        tvLabelFirstValue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.LINKED_ITEM, getString(R.string.search));
                                loadLinkedSearchDetailDialog(GenericConstant.ATHENA_SEARCH_LIST);
                            }
                        });
                        llListContent.addView(view);
                    }
                }

                break;

            case GenericConstant.ATHENA_PROPERTY_ITEMS_LIST:

                tvHeader.setText(getString(R.string.property_items));
                List<LinkedPropertyItemsBean> linkedPropertyItemsBeans = (List<LinkedPropertyItemsBean>) listDetails.get(21);
                if (linkedPropertyItemsBeans != null) {
                    for (int i = 0; i < linkedPropertyItemsBeans.size(); i++) {

                        View view = layoutInflater.inflate(R.layout.person_athena_details_vehicle_item, null);
                        TextView tvLabelFirst = view.findViewById(R.id.tvLabelFirst);
                        TextView tvLabelFirstValue = view.findViewById(R.id.tvLabelFirstValue);
                        TextView tvLabelSecond = view.findViewById(R.id.tvLabelSecond);
                        TextView tvLabelSecondValue = view.findViewById(R.id.tvLabelSecondValue);
                        TextView tvLabelThirdValue = view.findViewById(R.id.tvLabelThirdValue);

                        tvLabelFirst.setText(labelList.get(0));
                        tvLabelSecond.setText(labelList.get(1));
                        tvLabelFirstValue.setText(linkedPropertyItemsBeans.get(i).getShortsummary());
                        tvLabelSecondValue.setText(linkedPropertyItemsBeans.get(i).getRelevantdatetime());

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            tvLabelThirdValue.setText(Html.fromHtml(linkedPropertyItemsBeans.get(i).getDetailtext(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            tvLabelThirdValue.setText(Html.fromHtml(linkedPropertyItemsBeans.get(i).getDetailtext()));
                        }

                        tvLabelFirstValue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.LINKED_ITEM, getString(R.string.property_items));
                                loadLinkedPropertyItemsDialog(GenericConstant.ATHENA_PROPERTY_ITEMS_LIST);
                            }
                        });
                        llListContent.addView(view);
                    }
                }

                break;

            case GenericConstant.ATHENA_CATEGORY_LIST:

                tvHeader.setText(getString(R.string.categories));
                List<LinkedCategoryBean> linkedCategoryBeans = (List<LinkedCategoryBean>) listDetails.get(22);
                if (linkedCategoryBeans != null) {
                    for (int i = 0; i < linkedCategoryBeans.size(); i++) {

                        View view = layoutInflater.inflate(R.layout.person_athena_details_vehicle_item, null);
                        TextView tvLabelFirst = view.findViewById(R.id.tvLabelFirst);
                        TextView tvLabelFirstValue = view.findViewById(R.id.tvLabelFirstValue);
                        TextView tvLabelSecond = view.findViewById(R.id.tvLabelSecond);
                        TextView tvLabelSecondValue = view.findViewById(R.id.tvLabelSecondValue);
                        TextView tvLabelThirdValue = view.findViewById(R.id.tvLabelThirdValue);

                        tvLabelFirstValue.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey));
                        tvLabelFirstValue.setTypeface(tvLabelFirstValue.getTypeface(), Typeface.NORMAL);
                        tvLabelFirstValue.setTextSize(12.0f);

                        tvLabelFirst.setText(labelList.get(0));
                        tvLabelSecond.setText(labelList.get(1));
                        tvLabelFirstValue.setText(linkedCategoryBeans.get(i).getShortsummary());
                        tvLabelSecondValue.setText(linkedCategoryBeans.get(i).getRelevantdatetime());

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            tvLabelThirdValue.setText(Html.fromHtml(linkedCategoryBeans.get(i).getDetailtext(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            tvLabelThirdValue.setText(Html.fromHtml(linkedCategoryBeans.get(i).getDetailtext()));
                        }

                        llListContent.addView(view);
                    }
                }

                break;

            case GenericConstant.ATHENA_OBJECT_ITERATION_LIST:

                tvHeader.setText(getString(R.string.object_iteration));
                List<LinkedObjectIterationBean> linkedObjectIterationBeanList = (List<LinkedObjectIterationBean>) listDetails.get(23);
                if (linkedObjectIterationBeanList != null) {
                    for (int i = 0; i < linkedObjectIterationBeanList.size(); i++) {

                        View view = layoutInflater.inflate(R.layout.person_athena_details_vehicle_item, null);
                        TextView tvLabelFirst = view.findViewById(R.id.tvLabelFirst);
                        TextView tvLabelFirstValue = view.findViewById(R.id.tvLabelFirstValue);
                        TextView tvLabelSecond = view.findViewById(R.id.tvLabelSecond);
                        TextView tvLabelSecondValue = view.findViewById(R.id.tvLabelSecondValue);
                        TextView tvLabelThirdValue = view.findViewById(R.id.tvLabelThirdValue);

                        tvLabelFirst.setText(labelList.get(0));
                        tvLabelSecond.setText(labelList.get(1));
                        tvLabelFirstValue.setText(linkedObjectIterationBeanList.get(i).getShortsummary());
                        tvLabelSecondValue.setText(linkedObjectIterationBeanList.get(i).getRelevantdatetime());
                        tvLabelThirdValue.setText(linkedObjectIterationBeanList.get(i).getDetailtext());


                        tvLabelFirstValue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.LINKED_ITEM, getString(R.string.object_iteration_header));
                                loadLinkedPersonDialog(GenericConstant.ATHENA_OBJECT_ITERATION_LIST);
                            }
                        });


                        llListContent.addView(view);
                    }
                }

                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ivBack:
                dismiss();
                break;
        }
    }


    /**
     * Load Person Dialog
     */
    public void loadLinkedPersonDialog(int type) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.LINKED_PERSON_DETAILS_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        LinkedPersonDetailsDialogFragment linkedPersonDetailsDialogFragment = LinkedPersonDetailsDialogFragment.newInstance(type, listDetails, isPopulate);
        linkedPersonDetailsDialogFragment.show(ft, GenericConstant.LINKED_PERSON_DETAILS_DIALOG);
    }

    /**
     * Load Vehicle Dialog
     */
    public void loadLinkedVehicleDialog(int type) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.LINKED_VEHICLE_DETAILS_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        LinkedVehicleDetailsDialogFragment linkedVehicleDetailsDialogFragment = LinkedVehicleDetailsDialogFragment.newInstance(type, listDetails, isPopulate);
        linkedVehicleDetailsDialogFragment.show(ft, GenericConstant.LINKED_VEHICLE_DETAILS_DIALOG);
    }

    /**
     * Load Intelligence Dialog
     */
    public void loadLinkedIntelligenceDialog(int type) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.LINKED_INTELLIGENCE_REPORT_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        LinkedIntelligenceReportsDialogFragment linkedIntelligenceReportsDialogFragment = LinkedIntelligenceReportsDialogFragment.newInstance(type, listDetails, isPopulate);
        linkedIntelligenceReportsDialogFragment.show(ft, GenericConstant.LINKED_INTELLIGENCE_REPORT_DIALOG);
    }

    /**
     * Load CourtWarrant Dialog
     */
    public void loadLinkedCourtWarrantDialog(int type) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.LINKED_COURT_WARRANT_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        LinkedCourtWarrantDialogFragment linkedCourtWarrantDialogFragment = LinkedCourtWarrantDialogFragment.newInstance(type, listDetails, isPopulate);
        linkedCourtWarrantDialogFragment.show(ft, GenericConstant.LINKED_COURT_WARRANT_DIALOG);
    }


    /**
     * Load Investigation Dialog
     */
    public void loadLinkedInvestigationDialog(int type) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.LINKED_INVESTIGATION_DETAILS_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        LinkedInvestigationDialogFragment linkedInvestigationDialogFragment = LinkedInvestigationDialogFragment.newInstance(type, listDetails, isPopulate);
        linkedInvestigationDialogFragment.show(ft, GenericConstant.LINKED_INVESTIGATION_DETAILS_DIALOG);
    }

    /**
     * Load Case Dialog
     */
    public void loadLinkedCaseDialog(int type) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.LINKED_CASE_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        LinkedCaseDialogFragment linkedCaseDialogFragment = LinkedCaseDialogFragment.newInstance(type, listDetails, isPopulate);
        linkedCaseDialogFragment.show(ft, GenericConstant.LINKED_CASE_DIALOG);
    }


    /**
     * Load CustodyRecords Dialog
     */
    public void loadLinkedCustodyRecordsDialog(int type) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.LINKED_CUSTODY_RECORDS_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        LinkedCustodyRecordsDialogFragment linkedCustodyRecordsDialogFragment = LinkedCustodyRecordsDialogFragment.newInstance(type, listDetails, isPopulate);
        linkedCustodyRecordsDialogFragment.show(ft, GenericConstant.LINKED_CUSTODY_RECORDS_DIALOG);
    }

    /**
     * Load Location Dialog
     */
    public void loadLinkedLocationDialog(int type) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.LINKED_LOCATION_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        LinkedLocationDetailsDialogFragment linkedLocationDetailsDialogFragment = LinkedLocationDetailsDialogFragment.newInstance(type, listDetails, isPopulate);
        linkedLocationDetailsDialogFragment.show(ft, GenericConstant.LINKED_LOCATION_DIALOG);
    }

    /**
     * Load Communication Dialog
     */
    public void loadLinkedCommunicationDialog(int type) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.LINKED_COMMUNICATION_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        LinkedCommunicationDialogFragment linkedCommunicationDialogFragment = LinkedCommunicationDialogFragment.newInstance(type, listDetails, isPopulate);
        linkedCommunicationDialogFragment.show(ft, GenericConstant.LINKED_COMMUNICATION_DIALOG);
    }

    public void loadLinkedArrestDialog(int type) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.LINKED_ARREST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        LinkedArrestDialogFragment linkedArrestDialogFragment = LinkedArrestDialogFragment.newInstance(type, listDetails, isPopulate);
        linkedArrestDialogFragment.show(ft, GenericConstant.LINKED_ARREST_DIALOG);
    }

    public void loadLinkedOffenceDialog(int type) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.LINKED_OFFENCE_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        LinkedOffenceDialogFragment linkedOffenceDialogFragment = LinkedOffenceDialogFragment.newInstance(type, listDetails, isPopulate);
        linkedOffenceDialogFragment.show(ft, GenericConstant.LINKED_OFFENCE_DIALOG);
    }

    public void loadLinkedOrganizationDialog(int type) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.LINKED_ORGANIZATION_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        LinkedOrganizationDialogFragment linkedOrganizationDialogFragment = LinkedOrganizationDialogFragment.newInstance(type, listDetails, isPopulate);
        linkedOrganizationDialogFragment.show(ft, GenericConstant.LINKED_ORGANIZATION_DIALOG);
    }

    public void loadLinkedSearchDetailDialog(int type) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.LINKED_SEARCH_DETAIL_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        LinkedSearchDetailDialogFragment linkedSearchDetailDialogFragment = LinkedSearchDetailDialogFragment.newInstance(type, listDetails, isPopulate);
        linkedSearchDetailDialogFragment.show(ft, GenericConstant.LINKED_SEARCH_DETAIL_DIALOG);
    }


    public void loadLinkedPropertyItemsDialog(int type) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.LINKED_PROPERTY_ITEMS_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        LinkedPropertyItemsDialogFragment linkedPropertyItemsDialogFragment = LinkedPropertyItemsDialogFragment.newInstance(type, listDetails, isPopulate);
        linkedPropertyItemsDialogFragment.show(ft, GenericConstant.LINKED_PROPERTY_ITEMS_DIALOG);
    }

}
