package hcl.policing.digitalpolicingplatform.fragments.process;

import android.content.Context;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.process.edit.EditAnswerActivity;
import hcl.policing.digitalpolicingplatform.activities.process.flow.DialogValue;
import hcl.policing.digitalpolicingplatform.activities.process.flow.PopulateFields;
import hcl.policing.digitalpolicingplatform.activities.process.flow.SearchRequest;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.fieldName.FieldsNameConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomLinearLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomRelativeLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextInputLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextView;
import hcl.policing.digitalpolicingplatform.customLibraries.layoutHelper.CreateLayout;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.AnswerValueDTO;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.LayoutFieldHelper;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.models.process.PageSection_detailListBean;
import hcl.policing.digitalpolicingplatform.models.search.SearchListBean;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.CompareDate;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.SearchDialogUtil;

public class SearchFragment extends Fragment {

    private Context mContext;
    private AppSession appSession;
    private LinearLayout llFields;
    private List<PageSection_detailListBean> pageSection_detailList;
    private ProcessCreationActivity mActivityRef;
    private EditAnswerActivity mActivityEditRef;
    public JSONObject searchObject;
    private TextView tvHeader, tvRecent, tvSearch;
    public ArrayList<AnswerValueDTO> answerList;
    private List<SearchListBean> aSearchList;
    private SearchListBean mSearchList;
    private String searchType;
    private List<Address> addressList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_search, container, false);

        mContext = getActivity();
        appSession = new AppSession(mContext);

        if (getArguments() != null) {
            searchType = getArguments().getString(GenericConstant.SEARCH_KEYWORD);
            addressList = getArguments().getParcelableArrayList(GenericConstant.ADDRESS);
        }

        if (mContext instanceof ProcessCreationActivity ) {
            mActivityRef = (ProcessCreationActivity) this.getActivity();
        }

        if (mContext instanceof EditAnswerActivity ) {
            mActivityEditRef = (EditAnswerActivity) this.getActivity();
        }
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecent(searchType);

        aSearchList = new ArrayList<>();
        aSearchList.addAll(appSession.getSearchList());

        pageSection_detailList = new ArrayList<>();

        if(aSearchList != null && aSearchList.size() > 0) {
            for (int i = 0; i < aSearchList.size(); i++) {
                if (aSearchList.get(i).getSearch_Name().equalsIgnoreCase(searchType)) {
                    mSearchList = aSearchList.get(i);
                    pageSection_detailList.addAll(aSearchList.get(i).getPageSection_detailList());
                    tvHeader.setText(aSearchList.get(i).getDialogHeading());
                    break;
                }
            }
        }

        searchObject = new JSONObject();
        searchObject = SearchRequest.createSearchRequest(pageSection_detailList);

        if (searchType.equalsIgnoreCase(GenericConstant.SEARCH_ADDRESS)) {
            setLocation(addressList);
        }

        answerList = new ArrayList<>();
        for (int i = 0; i < pageSection_detailList.size(); i++) {
            createAttributes(mContext, pageSection_detailList.get(i));
            AnswerValueDTO answerDTO = new AnswerValueDTO();
            answerDTO.setKey(pageSection_detailList.get(i).getField_Name());
            answerDTO.setValue("");
            answerDTO.setDependentOn(pageSection_detailList.get(i).getField_Dependent_On());
            answerDTO.setId(pageSection_detailList.get(i).getField_Id());
            answerDTO.setSelectLogic(pageSection_detailList.get(i).getSelect_Logic());
            answerDTO.setMendatry(pageSection_detailList.get(i).isField_Mendatry());
            answerList.add(answerDTO);
        }

        tvSearch.setOnClickListener(v -> {
            // Call next section once the details is saved.
            try {
                getValuesFromDialog(mContext);
                if (answerList != null && answerList.size() > 0) {
                    int count = 0;
                    for (int i = 0; i < answerList.size(); i++) {
                        if (answerList.get(i).isMendatry() && answerList.get(i).getValue().equalsIgnoreCase("")) {

                            BasicMethodsUtil.getInstance().showToast(mContext, mContext.getResources().getString(R.string.mandatory_fields_require));
                            Log.e("Mendatry ", " SEARCH >>>>> " + answerList.get(i).getKey() + " >> " + answerList.get(i).getValue());
                            return;
                        }
                        if (answerList.get(i).getDependentOn() != 0 && answerList.get(i).getSelectLogic().equalsIgnoreCase(GenericConstant.LESSER_DATE)) {
                            for (int j = 0; j < answerList.size(); j++) {
                                if (answerList.get(i).getDependentOn() == answerList.get(j).getId()) {
                                    int answer = CompareDate.compareDates(answerList.get(j).getValue(), answerList.get(i).getValue());
                                    if (answer == 2) {
                                        return;
                                    }
                                    break;
                                }
                            }
                        }
                        if (answerList.get(i).getDependentOn() != 0 && answerList.get(i).getSelectLogic().equalsIgnoreCase(GenericConstant.GREATER_DATE)) {
                            for (int j = 0; j < answerList.size(); j++) {
                                if (answerList.get(i).getDependentOn() == answerList.get(j).getId()) {
                                    int answer = CompareDate.compareDates(answerList.get(j).getValue(), answerList.get(i).getValue());
                                    if (answer == 1) {
                                        return;
                                    }
                                    break;
                                }
                            }
                        }

                        if (answerList.get(i).getValue() != null && !answerList.get(i).getValue().equalsIgnoreCase("")) {
                            count = count + 1;
                        }
                    }
                    if (count < mSearchList.getMandatory_Count()) {
                        String error = "Please enter any of the " + mSearchList.getMandatory_Count() + " search criteria";
                        DialogUtil.errorDialog(mContext, error);
                        return;
                    }
                }
                if(mActivityEditRef != null) {
                    mActivityEditRef.dismissSearchDialog();
                }
                if(mActivityRef != null) {
                    mActivityRef.dismissSearchDialog();
                }

                if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_OFFICER)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.callOfficerActivity(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.callOfficerActivity(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_PERSON)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadPersonDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadPersonDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_VEHICLE)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadVehicleDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadVehicleDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_AIRCRAFT)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadVehicleDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadVehicleDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_MOTOR_VEHICLE)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadVehicleDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadVehicleDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_WATERCRAFT)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadVehicleDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadVehicleDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_ANIMAL)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadVehicleDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadVehicleDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_ART)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadVehicleDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadVehicleDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_BODY_PART)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadVehicleDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadVehicleDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_BUILDING_MATERIAL)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadVehicleDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadVehicleDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_CARAVAN)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadVehicleDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadVehicleDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_CLOTHING)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadVehicleDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadVehicleDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_CHEMICAL)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadVehicleDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadVehicleDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_CURRENCY)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadVehicleDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadVehicleDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_DOCUMENT)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadVehicleDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadVehicleDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_DRUG)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadVehicleDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadVehicleDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_ELECTRICAL)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadVehicleDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadVehicleDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_ENGINE)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadVehicleDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadVehicleDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_JEWELLERY)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadVehicleDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadVehicleDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_MOBILE_DEVICE)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadVehicleDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadVehicleDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_OTHER)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadVehicleDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadVehicleDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_PEDAL_CYCLE)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadVehicleDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadVehicleDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_MACHINERY)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadVehicleDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadVehicleDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_WEAPON)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadVehicleDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadVehicleDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_ADDRESS)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadAddressDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadAddressDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_DL)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.callTempPageSectionDetails(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.callTempPageSectionDetails(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_TEAM)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadTeamDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadTeamDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_ORGANISATION)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadOrganisation(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadOrganisation(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_INCIDENT)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadCrimeDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadCrimeDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_ALLEGATION)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadAllegationDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadAllegationDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_MO)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.callTempPageSectionDetails(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.callTempPageSectionDetails(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_OFFENCE)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadOffenceDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadOffenceDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_EVENT)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadCrimeDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadCrimeDialog(answerList);
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_CRIME_GROUP)) {

                    if (mActivityEditRef != null) {
                        mActivityEditRef.loadCrimeGroupDialog(answerList);
                    } else if (mActivityRef != null) {
                        mActivityRef.loadCrimeGroupDialog(answerList);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Load the initial views
     *
     * @param view
     */
    private void initView(View view) {
        tvHeader = view.findViewById(R.id.tv_header);
        tvRecent = view.findViewById(R.id.tv_recent);
        llFields = view.findViewById(R.id.ll_add);
        tvSearch = view.findViewById(R.id.tv_search);

        llFields.removeAllViews();
    }

    private void setRecent(String searchType) {

        if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_OFFICER)) {

            if (appSession.getSearchedOfficer() != null) {

                String val = appSession.getSearchedOfficer().get(appSession.getSearchedOfficer().size()-1).getRecentValue();
                tvRecent.setText(val);
            }
        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_PERSON)) {

            if (appSession.getRecentSearchedPerson() != null) {
                String val = appSession.getRecentSearchedPerson().get(appSession.getRecentSearchedPerson().size()-1).getRecentValue();
                tvRecent.setText(val);
            }
        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_VEHICLE)) {

            if (appSession.getSearchedVehicle() != null) {
                String vehicleBean = appSession.getSearchedVehicle().get(appSession.getSearchedVehicle().size()-1).getRecentValue();
                tvRecent.setText(vehicleBean);
            }
        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_ADDRESS)) {

            if (appSession.getSearchedAddress() != null) {
                String addressBean = appSession.getSearchedAddress().get(appSession.getSearchedAddress().size()-1).getRecentValue();
                tvRecent.setText(addressBean);
            }

        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_DL)) {


        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_TEAM)) {

            if (appSession.getSearchedTeam() != null) {
                String teamBean = appSession.getSearchedTeam().get(appSession.getSearchedTeam().size()-1).getRecentValue();
                tvRecent.setText(teamBean);
            }

        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_ORGANISATION)) {

            if (appSession.getSearchedOrg() != null) {
                String organisationDetailsList = appSession.getSearchedOrg().get(appSession.getSearchedOrg().size()-1).getRecentValue();
                tvRecent.setText(organisationDetailsList);
            }

        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_INCIDENT)) {

            if (appSession.getSearchedEvent() != null) {
                String eventSearchList = appSession.getSearchedEvent().get(appSession.getSearchedEvent().size()-1).getRecentValue();
                tvRecent.setText(eventSearchList);
            }

        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_ALLEGATION)) {

            if (appSession.getSearchedAllegation() != null) {
                String offenceDefinitionList = appSession.getSearchedAllegation().get(appSession.getSearchedAllegation().size()-1).getRecentValue();
                tvRecent.setText(offenceDefinitionList);
            }

        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_MO)) {


        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_OFFENCE)) {

            if (appSession.getSearchedOffence() != null) {
                String hoOffenceList = appSession.getSearchedOffence().get(appSession.getSearchedOffence().size()-1).getRecentValue();
                tvRecent.setText(hoOffenceList);
            }

        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_EVENT)) {

            if (appSession.getSearchedEvent() != null) {
                String eventSearchList = appSession.getSearchedEvent().get(appSession.getSearchedEvent().size()-1).getRecentValue();
                tvRecent.setText(eventSearchList);
            }

        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_CRIME_GROUP)) {

            if (appSession.getSearchedCrimeGroup() != null) {
                String crimeGroupList = appSession.getSearchedCrimeGroup().get(appSession.getSearchedCrimeGroup().size()-1).getRecentValue();
                tvRecent.setText(crimeGroupList);
            }
        }

        tvRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_OFFICER)) {

                    if (mActivityEditRef != null) {
                        if (appSession.getSearchedOfficer() != null) {
                            int position = appSession.getSearchedOfficer().size()-1;
                            mActivityEditRef.populateRecent(mActivityEditRef, appSession.getSearchedOfficer().get(position).getObjectList(), appSession.getSearchedOfficer().get(position).getRecentLogsList(), GenericConstant.OFFICER);
                        }
                    } else if (mActivityRef != null) {
                        if (appSession.getSearchedOfficer() != null) {
                            int position = appSession.getSearchedOfficer().size()-1;
                            PopulateFields.populateRecent(mActivityRef, appSession.getSearchedOfficer().get(position).getObjectList(), appSession.getSearchedOfficer().get(position).getRecentLogsList(), GenericConstant.OFFICER);
                        }
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_PERSON)) {

                    if (mActivityEditRef != null) {
                        if (appSession.getRecentSearchedPerson() != null) {
                            int position = appSession.getRecentSearchedPerson().size()-1;
                            mActivityEditRef.populateRecent(mActivityEditRef, appSession.getRecentSearchedPerson().get(position).getObjectList(), appSession.getRecentSearchedPerson().get(position).getRecentLogsList(), GenericConstant.PERSON);
                        }
                    } else if (mActivityRef != null) {
                        if (appSession.getRecentSearchedPerson() != null) {
                            int position = appSession.getRecentSearchedPerson().size()-1;
                            PopulateFields.populateRecent(mActivityRef, appSession.getRecentSearchedPerson().get(position).getObjectList(), appSession.getRecentSearchedPerson().get(position).getRecentLogsList(), GenericConstant.PERSON);
                        }
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_VEHICLE)) {

                    if (mActivityEditRef != null) {
                        if (appSession.getSearchedVehicle() != null) {
                            int position = appSession.getSearchedVehicle().size()-1;
                            mActivityEditRef.populateRecent(mActivityEditRef, appSession.getSearchedVehicle().get(position).getObjectList(), appSession.getSearchedVehicle().get(position).getRecentLogsList(), GenericConstant.VEHICLE);
                        }
                    } else if (mActivityRef != null) {
                        if (appSession.getSearchedVehicle() != null) {
                            int position = appSession.getSearchedVehicle().size()-1;
                            PopulateFields.populateRecent(mActivityRef, appSession.getSearchedVehicle().get(position).getObjectList(), appSession.getSearchedVehicle().get(position).getRecentLogsList(), GenericConstant.VEHICLE);
                        }
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_ADDRESS)) {

                    if (mActivityEditRef != null) {
                        if (appSession.getSearchedAddress() != null) {
                            int position = appSession.getSearchedAddress().size()-1;
                            mActivityEditRef.populateRecent(mActivityEditRef, appSession.getSearchedAddress().get(position).getObjectList(), appSession.getSearchedAddress().get(position).getRecentLogsList(), GenericConstant.ADDRESS);
                        }
                    } else if (mActivityRef != null) {
                        if (appSession.getSearchedAddress() != null) {
                            int position = appSession.getSearchedAddress().size()-1;
                            PopulateFields.populateRecent(mActivityRef, appSession.getSearchedAddress().get(position).getObjectList(), appSession.getSearchedAddress().get(position).getRecentLogsList(), GenericConstant.ADDRESS);
                        }
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_TEAM)) {

                    if (mActivityEditRef != null) {
                        if (appSession.getSearchedTeam() != null) {
                            int position = appSession.getSearchedTeam().size()-1;
                            mActivityEditRef.populateRecent(mActivityEditRef, appSession.getSearchedTeam().get(position).getObjectList(), appSession.getSearchedTeam().get(position).getRecentLogsList(), GenericConstant.TEAM);
                        }
                    } else if (mActivityRef != null) {
                        if (appSession.getSearchedTeam() != null) {
                            int position = appSession.getSearchedTeam().size()-1;
                            PopulateFields.populateRecent(mActivityRef, appSession.getSearchedTeam().get(position).getObjectList(), appSession.getSearchedTeam().get(position).getRecentLogsList(), GenericConstant.TEAM);
                        }
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_EVENT)) {

                    if (mActivityEditRef != null) {
                        if (appSession.getSearchedEvent() != null) {
                            int position = appSession.getSearchedEvent().size()-1;
                            mActivityEditRef.populateRecent(mActivityEditRef, appSession.getSearchedEvent().get(position).getObjectList(), appSession.getSearchedEvent().get(position).getRecentLogsList(), GenericConstant.EVENT);
                        }
                    } else if (mActivityRef != null) {
                        if (appSession.getSearchedEvent() != null) {
                            int position = appSession.getSearchedEvent().size()-1;
                            PopulateFields.populateRecent(mActivityRef, appSession.getSearchedEvent().get(position).getObjectList(), appSession.getSearchedEvent().get(position).getRecentLogsList(), GenericConstant.EVENT);
                        }
                    }
                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_INCIDENT)) {

                    if (mActivityEditRef != null) {
                        if (appSession.getSearchedEvent() != null) {
                            int position = appSession.getSearchedEvent().size()-1;
                            mActivityEditRef.populateRecent(mActivityEditRef, appSession.getSearchedEvent().get(position).getObjectList(), appSession.getSearchedEvent().get(position).getRecentLogsList(), GenericConstant.EVENT);
                        }
                    } else if (mActivityRef != null) {
                        if (appSession.getSearchedEvent() != null) {
                            int position = appSession.getSearchedEvent().size()-1;
                            PopulateFields.populateRecent(mActivityRef, appSession.getSearchedEvent().get(position).getObjectList(), appSession.getSearchedEvent().get(position).getRecentLogsList(), GenericConstant.EVENT);
                        }
                    }
                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_ORGANISATION)) {

                    if (mActivityEditRef != null) {
                        if (appSession.getSearchedOrg() != null) {
                            int position = appSession.getSearchedOrg().size()-1;
                            mActivityEditRef.populateRecent(mActivityEditRef, appSession.getSearchedOrg().get(position).getObjectList(), appSession.getSearchedOrg().get(position).getRecentLogsList(), GenericConstant.ORGANISATION);
                        }
                    } else if (mActivityRef != null) {
                        if (appSession.getSearchedOrg() != null) {
                            int position = appSession.getSearchedOrg().size()-1;
                            PopulateFields.populateRecent(mActivityRef, appSession.getSearchedOrg().get(position).getObjectList(), appSession.getSearchedOrg().get(position).getRecentLogsList(), GenericConstant.ORGANISATION);
                        }
                    }
                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_OFFENCE)) {

                    if (mActivityEditRef != null) {
                        if (appSession.getSearchedOffence() != null) {
                            int position = appSession.getSearchedOffence().size()-1;
                            mActivityEditRef.populateRecent(mActivityEditRef, appSession.getSearchedOffence().get(position).getObjectList(), appSession.getSearchedOffence().get(position).getRecentLogsList(), GenericConstant.OFFENCE);
                        }
                    } else if (mActivityRef != null) {
                        if (appSession.getSearchedOffence() != null) {
                            int position = appSession.getSearchedOffence().size()-1;
                            PopulateFields.populateRecent(mActivityRef, appSession.getSearchedOffence().get(position).getObjectList(), appSession.getSearchedOffence().get(position).getRecentLogsList(), GenericConstant.OFFENCE);
                        }
                    }
                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_ALLEGATION)) {

                    if (mActivityEditRef != null) {
                        if (appSession.getSearchedAllegation() != null) {
                            int position = appSession.getSearchedAllegation().size()-1;
                            mActivityEditRef.populateRecent(mActivityEditRef, appSession.getSearchedAllegation().get(position).getObjectList(), appSession.getSearchedAllegation().get(position).getRecentLogsList(), GenericConstant.ALLEGATION);
                        }
                    } else if (mActivityRef != null) {
                        if (appSession.getSearchedAllegation() != null) {
                            int position = appSession.getSearchedAllegation().size()-1;
                            PopulateFields.populateRecent(mActivityRef, appSession.getSearchedAllegation().get(position).getObjectList(), appSession.getSearchedAllegation().get(position).getRecentLogsList(), GenericConstant.ALLEGATION);
                        }
                    }
                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_CRIME_GROUP)) {

                    if (mActivityEditRef != null) {
                        if (appSession.getSearchedCrimeGroup() != null) {
                            int position = appSession.getSearchedCrimeGroup().size()-1;
                            mActivityEditRef.populateRecent(mActivityEditRef, appSession.getSearchedCrimeGroup().get(position).getObjectList(), appSession.getSearchedCrimeGroup().get(position).getRecentLogsList(), GenericConstant.CRIMEGROUP);
                        }
                    } else if (mActivityRef != null) {
                        if (appSession.getSearchedCrimeGroup() != null) {
                            int position = appSession.getSearchedCrimeGroup().size()-1;
                            PopulateFields.populateRecent(mActivityRef, appSession.getSearchedCrimeGroup().get(position).getObjectList(), appSession.getSearchedCrimeGroup().get(position).getRecentLogsList(), GenericConstant.CRIMEGROUP);
                        }
                    }
                }
            }
        });
    }

    private void setLocation(List<Address> addressList) {
        if (addressList != null && addressList.size() > 0) {

            if (pageSection_detailList != null && pageSection_detailList.size() > 0) {
                for (int j = 0; j < pageSection_detailList.size(); j++) {
                    if (pageSection_detailList.get(j).getField_Name().contains(FieldsNameConstant.FlatNumber)) {
                        if (addressList.get(0).getFeatureName() != null) {
                            pageSection_detailList.get(j).setField_Value(addressList.get(0).getFeatureName());
                        } else {
                            pageSection_detailList.get(j).setField_Value("");
                        }
                    }
                    if (pageSection_detailList.get(j).getField_Name().contains(FieldsNameConstant.BuildingNumber)) {
                        if (addressList.get(0).getFeatureName() != null) {
                            pageSection_detailList.get(j).setField_Value(addressList.get(0).getFeatureName());
                        } else {
                            pageSection_detailList.get(j).setField_Value("");
                        }
                    }
                    if (pageSection_detailList.get(j).getField_Name().contains(FieldsNameConstant.BuildingName)) {
                        if (addressList.get(0).getFeatureName() != null) {
                            pageSection_detailList.get(j).setField_Value(addressList.get(0).getFeatureName());
                        } else {
                            pageSection_detailList.get(j).setField_Value("");
                        }
                    }
                    if (pageSection_detailList.get(j).getField_Name().contains(FieldsNameConstant.FlatName)) {
                        if (addressList.get(0).getFeatureName() != null) {
                            pageSection_detailList.get(j).setField_Value(addressList.get(0).getFeatureName());
                        } else {
                            pageSection_detailList.get(j).setField_Value("");
                        }
                    }
                    if (pageSection_detailList.get(j).getField_Name().contains(FieldsNameConstant.StreetName)) {
                        if (addressList.get(0).getSubLocality() != null) {
                            pageSection_detailList.get(j).setField_Value(addressList.get(0).getSubLocality());
                        } else {
                            pageSection_detailList.get(j).setField_Value("");
                        }
                    }
                    if (pageSection_detailList.get(j).getField_Name().contains(FieldsNameConstant.City)) {
                        if (addressList.get(0).getLocality() != null) {
                            pageSection_detailList.get(j).setField_Value(addressList.get(0).getLocality());
                        } else {
                            pageSection_detailList.get(j).setField_Value("");
                        }
                    }
                    if (pageSection_detailList.get(j).getField_Name().contains(FieldsNameConstant.Country)) {
                        if (addressList.get(0).getCountryName() != null) {
                            pageSection_detailList.get(j).setField_Value(addressList.get(0).getCountryName());
                        } else {
                            pageSection_detailList.get(j).setField_Value("");
                        }
                    }
                    if (pageSection_detailList.get(j).getField_Name().contains(FieldsNameConstant.PostCode)) {
                        if (addressList.get(0).getPostalCode() != null) {
                            pageSection_detailList.get(j).setField_Value(addressList.get(0).getPostalCode());
                        } else {
                            pageSection_detailList.get(j).setField_Value("");
                        }
                    }
                }
            }
        }
    }

    /**
     * Get the values from Dialogs and check validations
     *
     * @param act
     */
    public void getValuesFromDialog(Context act) {
        try {

            int count = llFields.getChildCount();
            Log.e("CHILD COUNT ", "getValue from dialog >>>>> " + count);
            for (int i = 0; i < count; i++) {
                View view = llFields.getChildAt(i);

                if (view instanceof CustomTextInputLayout) {

                    CustomTextInputLayout textInputLayout = (CustomTextInputLayout) view;
                    answerList.get(i).setValue(textInputLayout.getEditText().getText().toString());
                    if (answerList.get(i).isMendatry() && answerList.get(i).getValue().equalsIgnoreCase("")) {
                        textInputLayout.setErrorEnabled(true);
                        textInputLayout.setError(act.getResources().getString(R.string.field_cannot_blank));
                    }
                } else if (view instanceof CustomRelativeLayout) {

                    CustomRelativeLayout customRelativeLayout = (CustomRelativeLayout) view;
                    View vHeading = customRelativeLayout.getChildAt(1);
                    View v = customRelativeLayout.getChildAt(0);

                    if (v instanceof CustomTextView) {
                        CustomTextView customTextHead = (CustomTextView) vHeading;
                        CustomTextView customTextView = (CustomTextView) v;
                        Log.e("TEXT", "VALUE >>>>> " + customTextView.getText().toString());
                        Log.e("TEXT", "Heading >>>>> " + customTextHead.getText().toString());
                        answerList.get(i).setValue(customTextView.getText().toString());
                        answerList.get(i).setCode(customTextView.getTag(customTextView));
                        if (answerList.get(i).isMendatry() && answerList.get(i).getValue().equalsIgnoreCase("")) {
                            customTextHead.setTextColor(ContextCompat.getColor(act, R.color.red));
                            customTextView.setBackground(ContextCompat.getDrawable(act, R.drawable.bg_red_corner));
                        }
                        if (answerList.get(i).getDependentOn() != 0 && answerList.get(i).getSelectLogic().equalsIgnoreCase(GenericConstant.LESSER_DATE)) {
                            for (int j = 0; j < answerList.size(); j++) {
                                if (answerList.get(i).getDependentOn() == answerList.get(j).getId()) {
                                    int answer = CompareDate.compareDates(answerList.get(j).getValue(), answerList.get(i).getValue());
                                    if (answer == 2) {
                                        customTextHead.setTextColor(ContextCompat.getColor(act, R.color.red));
                                        customTextView.setBackground(ContextCompat.getDrawable(act, R.drawable.bg_red_corner));
                                        String error = answerList.get(i).getKey() + " can not be greater than " + answerList.get(j).getKey();
                                        DialogUtil.errorDialog(act, error);
                                        break;
                                    }
                                    break;
                                }
                            }
                        }
                        if (answerList.get(i).getDependentOn() != 0 && answerList.get(i).getSelectLogic().equalsIgnoreCase(GenericConstant.GREATER_DATE)) {
                            for (int j = 0; j < answerList.size(); j++) {
                                if (answerList.get(i).getDependentOn() == answerList.get(j).getId()) {
                                    Log.e("GREATER DATES ", " >> " + answerList.get(j).getValue() + " >>>>> " + answerList.get(i).getValue());
                                    int answer = CompareDate.compareDates(answerList.get(j).getValue(), answerList.get(i).getValue());
                                    if (answer == 1) {
                                        customTextHead.setTextColor(ContextCompat.getColor(act, R.color.red));
                                        customTextView.setBackground(ContextCompat.getDrawable(act, R.drawable.bg_red_corner));
                                        String error = answerList.get(i).getKey() + " can not be lesser than " + answerList.get(j).getKey();
                                        DialogUtil.errorDialog(act, error);
                                        break;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                } else if (view instanceof CustomLinearLayout) {
                    CustomLinearLayout customLinearLayout = (CustomLinearLayout) view;
                    View vTextV = customLinearLayout.getChildAt(0);
                    View vLinearHorizontal = customLinearLayout.getChildAt(1);

                    CustomTextView customTextHead = null;
                    CustomLinearLayout customLinearH = null;

                    if (vTextV instanceof CustomTextView) {
                        customTextHead = (CustomTextView) vTextV;
                    }
                    if (vLinearHorizontal instanceof CustomLinearLayout) {
                        customLinearH = (CustomLinearLayout) vLinearHorizontal;
                    }

                    String answerText = SearchRequest.getSavedAnswer(answerList.get(i).getKey());
                    if (answerText != null && !answerText.equalsIgnoreCase("")) {
                        answerList.get(i).setValue(answerText);
                    }

                    if (answerList.get(i).isMendatry()) {
                        if (answerText == null || answerText.equalsIgnoreCase("")) {
                            if (customTextHead != null)
                                customTextHead.setTextColor(ContextCompat.getColor(act, R.color.red));

                            if (customTextHead != null)
                                customLinearH.setBackground(ContextCompat.getDrawable(act, R.drawable.bg_red_corner));
                        }
                    }
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DialogValue.class, "getValuesFromDialog");
        }
    }

    /**
     * Create Pages basis on the information of Page list
     *
     * @param pageSection_detailListBean
     */
    public void createAttributes(Context act, PageSection_detailListBean pageSection_detailListBean) {

        try {
            if (pageSection_detailListBean != null) {

                // create Page here
                createDynamicFields(act, pageSection_detailListBean);
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), SearchDialogUtil.class, "createAttributes");
        }
    }

    /**
     * for creating dynamic fields in dialog
     *
     * @param act
     * @param pageSectionDetailListBean
     */
    private void createDynamicFields(Context act, PageSection_detailListBean pageSectionDetailListBean) {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset(act, GenericConstant.PROPERTIES_JSON));
            Gson gson = new Gson();
            PropertiesBean propertiesBean = gson.fromJson(jsonObject.toString(), new TypeToken<PropertiesBean>() {
            }.getType());
            propertiesBean.setId(pageSectionDetailListBean.getField_Id());
            propertiesBean.setHint(pageSectionDetailListBean.getField_Name());
            propertiesBean.setText(pageSectionDetailListBean.getField_Value());
            propertiesBean.setMendatry(pageSectionDetailListBean.isField_Mendatry());
            propertiesBean.setEnabled(pageSectionDetailListBean.isField_Enabled());
            propertiesBean.setVisibility(pageSectionDetailListBean.getField_Visibility());
            propertiesBean.setInputType(pageSectionDetailListBean.getField_Input_Type());
            propertiesBean.setKeyboardAction(pageSectionDetailListBean.getField_Action());

            LayoutFieldHelper layoutFieldHelper = new LayoutFieldHelper();
            layoutFieldHelper.setBasicLayout("");
            layoutFieldHelper.setViewType(pageSectionDetailListBean.getField_Type());
            layoutFieldHelper.setParentView(llFields);
            layoutFieldHelper.setPropertiesBean(propertiesBean/*pageSectionDetailListBean.getPropertiesBean()*/);
            layoutFieldHelper.setDropdownArray(pageSectionDetailListBean.getField_MasterData());
            layoutFieldHelper.setPageSectionDetailListBean(pageSectionDetailListBean);

            CreateLayout.createLayoutFields(act, layoutFieldHelper);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), SearchDialogUtil.class, "createDynamicFields");
        }
    }

    /**
     * Load Json from Assets
     *
     * @param context
     * @param filename
     * @return
     */
    private String loadJSONFromAsset(Context context, String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, GenericConstant.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
