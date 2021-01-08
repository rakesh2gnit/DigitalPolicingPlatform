package hcl.policing.digitalpolicingplatform.fragments.process;

import android.content.Context;
import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.officer.OfficerSearchActivity;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.process.edit.EditAnswerActivity;
import hcl.policing.digitalpolicingplatform.activities.process.flow.DialogValue;
import hcl.policing.digitalpolicingplatform.activities.process.flow.PopulateFields;
import hcl.policing.digitalpolicingplatform.activities.process.flow.SearchRequest;
import hcl.policing.digitalpolicingplatform.adapters.process.RecentLogsAdaptor;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.fieldName.FieldsNameConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomLinearLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomRelativeLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextInputLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextView;
import hcl.policing.digitalpolicingplatform.customLibraries.layoutHelper.CreateLayout;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.AnswerValueDTO;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.LayoutFieldHelper;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.models.process.PageSection_detailListBean;
import hcl.policing.digitalpolicingplatform.models.process.crime.AllegationRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.crime.CrimeGroupRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.crime.EventRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.crime.OffenceRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.crime.OrgRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.AddressRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.PersonRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.team.TeamRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.VehicleRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.officer.OfficerBean;
import hcl.policing.digitalpolicingplatform.models.process.officer.OfficerRecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.process.officer.UserModel;
import hcl.policing.digitalpolicingplatform.models.search.RecentLogsBean;
import hcl.policing.digitalpolicingplatform.models.search.SearchListBean;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.CompareDate;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.SearchDialogUtil;

public class RecentLogsFragment extends Fragment {

    private Context mContext;
    private AppSession appSession;
    private ProcessCreationActivity mActivityRef;
    private EditAnswerActivity mActivityEditRef;
    private RecyclerView rvRecent;
    private String searchType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_logs_fragment, container, false);

        mContext = getActivity();

        if (getArguments() != null) {
            searchType = getArguments().getString(GenericConstant.SEARCH_KEYWORD);
        }

        if (mContext instanceof ProcessCreationActivity ) {
            mActivityRef = (ProcessCreationActivity) this.getActivity();
        }

        if (mContext instanceof EditAnswerActivity ) {
            mActivityEditRef = (EditAnswerActivity) this.getActivity();
        }
        appSession = new AppSession(mContext);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecent(searchType);
    }

    /**
     * Load the initial views
     *
     * @param view
     */
    private void initView(View view) {
        rvRecent = view.findViewById(R.id.rv_add);
        setRecyclerViewProperty(mContext, rvRecent);
    }

    /**
     * Set the recyclerview property.
     *
     * @param context
     * @param rvRecent
     */
    private static void setRecyclerViewProperty(Context context, RecyclerView rvRecent) {
        rvRecent.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvRecent.setLayoutManager(layoutManager);
    }

    private void setRecent(String searchType) {

        if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_OFFICER)) {

            if (appSession.getSearchedOfficer() != null) {

                ArrayList<OfficerRecentLogsBean> officerRecentList = appSession.getSearchedOfficer();
                ArrayList<RecentLogsBean> recentList = new ArrayList<>();

                for (int i = 0; i < officerRecentList.size(); i++) {
                    RecentLogsBean recentLogsBean = new RecentLogsBean();
                    recentLogsBean.setRecentValue(officerRecentList.get(i).getRecentValue());
                    recentLogsBean.setObjectList(officerRecentList.get(i).getObjectList());
                    recentLogsBean.setRecentLogsList(officerRecentList.get(i).getRecentLogsList());
                    recentList.add(recentLogsBean);
                }

                if (recentList != null && recentList.size() > 0) {
                    RecentLogsAdaptor recentLogsAdaptor = new RecentLogsAdaptor(mContext, recentList, onItemClickRecent);
                    rvRecent.setAdapter(recentLogsAdaptor);
                }
            }
        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_PERSON)) {

            if (appSession.getRecentSearchedPerson() != null) {

                ArrayList<PersonRecentLogsBean> officerRecentList = appSession.getRecentSearchedPerson();
                ArrayList<RecentLogsBean> recentList = new ArrayList<>();

                for (int i = 0; i < officerRecentList.size(); i++) {
                    RecentLogsBean recentLogsBean = new RecentLogsBean();
                    recentLogsBean.setRecentValue(officerRecentList.get(i).getRecentValue());
                    recentLogsBean.setObjectList(officerRecentList.get(i).getObjectList());
                    recentLogsBean.setRecentLogsList(officerRecentList.get(i).getRecentLogsList());
                    recentList.add(recentLogsBean);
                }

                if (recentList != null && recentList.size() > 0) {
                    RecentLogsAdaptor recentLogsAdaptor = new RecentLogsAdaptor(mContext, recentList, onItemClickRecent);
                    rvRecent.setAdapter(recentLogsAdaptor);
                }
            }
        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_VEHICLE)) {

            if (appSession.getSearchedVehicle() != null) {

                ArrayList<VehicleRecentLogsBean> officerRecentList = appSession.getSearchedVehicle();
                ArrayList<RecentLogsBean> recentList = new ArrayList<>();

                for (int i = 0; i < officerRecentList.size(); i++) {
                    RecentLogsBean recentLogsBean = new RecentLogsBean();
                    recentLogsBean.setRecentValue(officerRecentList.get(i).getRecentValue());
                    recentLogsBean.setObjectList(officerRecentList.get(i).getObjectList());
                    recentLogsBean.setRecentLogsList(officerRecentList.get(i).getRecentLogsList());
                    recentList.add(recentLogsBean);
                }

                if (recentList != null && recentList.size() > 0) {
                    RecentLogsAdaptor recentLogsAdaptor = new RecentLogsAdaptor(mContext, recentList, onItemClickRecent);
                    rvRecent.setAdapter(recentLogsAdaptor);
                }
            }
        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_ADDRESS)) {

            if (appSession.getSearchedAddress() != null) {

                ArrayList<AddressRecentLogsBean> officerRecentList = appSession.getSearchedAddress();
                ArrayList<RecentLogsBean> recentList = new ArrayList<>();

                for (int i = 0; i < officerRecentList.size(); i++) {
                    RecentLogsBean recentLogsBean = new RecentLogsBean();
                    recentLogsBean.setRecentValue(officerRecentList.get(i).getRecentValue());
                    recentLogsBean.setObjectList(officerRecentList.get(i).getObjectList());
                    recentLogsBean.setRecentLogsList(officerRecentList.get(i).getRecentLogsList());
                    recentList.add(recentLogsBean);
                }

                if (recentList != null && recentList.size() > 0) {
                    RecentLogsAdaptor recentLogsAdaptor = new RecentLogsAdaptor(mContext, recentList, onItemClickRecent);
                    rvRecent.setAdapter(recentLogsAdaptor);
                }
            }

        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_DL)) {


        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_TEAM)) {

            if (appSession.getSearchedTeam() != null) {

                ArrayList<TeamRecentLogsBean> officerRecentList = appSession.getSearchedTeam();
                ArrayList<RecentLogsBean> recentList = new ArrayList<>();

                for (int i = 0; i < officerRecentList.size(); i++) {
                    RecentLogsBean recentLogsBean = new RecentLogsBean();
                    recentLogsBean.setRecentValue(officerRecentList.get(i).getRecentValue());
                    recentLogsBean.setObjectList(officerRecentList.get(i).getObjectList());
                    recentLogsBean.setRecentLogsList(officerRecentList.get(i).getRecentLogsList());
                    recentList.add(recentLogsBean);
                }

                if (recentList != null && recentList.size() > 0) {
                    RecentLogsAdaptor recentLogsAdaptor = new RecentLogsAdaptor(mContext, recentList, onItemClickRecent);
                    rvRecent.setAdapter(recentLogsAdaptor);
                }
            }

        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_ORGANISATION)) {

            if (appSession.getSearchedOrg() != null) {

                ArrayList<OrgRecentLogsBean> officerRecentList = appSession.getSearchedOrg();
                ArrayList<RecentLogsBean> recentList = new ArrayList<>();

                for (int i = 0; i < officerRecentList.size(); i++) {
                    RecentLogsBean recentLogsBean = new RecentLogsBean();
                    recentLogsBean.setRecentValue(officerRecentList.get(i).getRecentValue());
                    recentLogsBean.setObjectList(officerRecentList.get(i).getObjectList());
                    recentLogsBean.setRecentLogsList(officerRecentList.get(i).getRecentLogsList());
                    recentList.add(recentLogsBean);
                }

                if (recentList != null && recentList.size() > 0) {
                    RecentLogsAdaptor recentLogsAdaptor = new RecentLogsAdaptor(mContext, recentList, onItemClickRecent);
                    rvRecent.setAdapter(recentLogsAdaptor);
                }
            }

        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_INCIDENT)) {

            if (appSession.getSearchedEvent() != null) {

                ArrayList<EventRecentLogsBean> officerRecentList = appSession.getSearchedEvent();
                ArrayList<RecentLogsBean> recentList = new ArrayList<>();

                for (int i = 0; i < officerRecentList.size(); i++) {
                    RecentLogsBean recentLogsBean = new RecentLogsBean();
                    recentLogsBean.setRecentValue(officerRecentList.get(i).getRecentValue());
                    recentLogsBean.setObjectList(officerRecentList.get(i).getObjectList());
                    recentLogsBean.setRecentLogsList(officerRecentList.get(i).getRecentLogsList());
                    recentList.add(recentLogsBean);
                }
                if (recentList != null && recentList.size() > 0) {
                    RecentLogsAdaptor recentLogsAdaptor = new RecentLogsAdaptor(mContext, recentList, onItemClickRecent);
                    rvRecent.setAdapter(recentLogsAdaptor);
                }
            }

        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_ALLEGATION)) {

            if (appSession.getSearchedAllegation() != null) {

                ArrayList<AllegationRecentLogsBean> officerRecentList = appSession.getSearchedAllegation();
                ArrayList<RecentLogsBean> recentList = new ArrayList<>();

                for (int i = 0; i < officerRecentList.size(); i++) {
                    RecentLogsBean recentLogsBean = new RecentLogsBean();
                    recentLogsBean.setRecentValue(officerRecentList.get(i).getRecentValue());
                    recentLogsBean.setObjectList(officerRecentList.get(i).getObjectList());
                    recentLogsBean.setRecentLogsList(officerRecentList.get(i).getRecentLogsList());
                    recentList.add(recentLogsBean);
                }

                if (recentList != null && recentList.size() > 0) {
                    RecentLogsAdaptor recentLogsAdaptor = new RecentLogsAdaptor(mContext, recentList, onItemClickRecent);
                    rvRecent.setAdapter(recentLogsAdaptor);
                }
            }

        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_MO)) {


        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_OFFENCE)) {

            if (appSession.getSearchedOffence() != null) {

                ArrayList<OffenceRecentLogsBean> officerRecentList = appSession.getSearchedOffence();
                ArrayList<RecentLogsBean> recentList = new ArrayList<>();

                for (int i = 0; i < officerRecentList.size(); i++) {
                    RecentLogsBean recentLogsBean = new RecentLogsBean();
                    recentLogsBean.setRecentValue(officerRecentList.get(i).getRecentValue());
                    recentLogsBean.setObjectList(officerRecentList.get(i).getObjectList());
                    recentLogsBean.setRecentLogsList(officerRecentList.get(i).getRecentLogsList());
                    recentList.add(recentLogsBean);
                }

                if (recentList != null && recentList.size() > 0) {
                    RecentLogsAdaptor recentLogsAdaptor = new RecentLogsAdaptor(mContext, recentList, onItemClickRecent);
                    rvRecent.setAdapter(recentLogsAdaptor);
                }
            }

        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_EVENT)) {

            if (appSession.getSearchedEvent() != null) {

                ArrayList<EventRecentLogsBean> officerRecentList = appSession.getSearchedEvent();
                ArrayList<RecentLogsBean> recentList = new ArrayList<>();

                for (int i = 0; i < officerRecentList.size(); i++) {
                    RecentLogsBean recentLogsBean = new RecentLogsBean();
                    recentLogsBean.setRecentValue(officerRecentList.get(i).getRecentValue());
                    recentLogsBean.setObjectList(officerRecentList.get(i).getObjectList());
                    recentLogsBean.setRecentLogsList(officerRecentList.get(i).getRecentLogsList());
                    recentList.add(recentLogsBean);
                }

                if (recentList != null && recentList.size() > 0) {
                    RecentLogsAdaptor recentLogsAdaptor = new RecentLogsAdaptor(mContext, recentList, onItemClickRecent);
                    rvRecent.setAdapter(recentLogsAdaptor);
                }
            }

        } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_CRIME_GROUP)) {

            if (appSession.getSearchedCrimeGroup() != null) {

                ArrayList<CrimeGroupRecentLogsBean> officerRecentList = appSession.getSearchedCrimeGroup();
                ArrayList<RecentLogsBean> recentList = new ArrayList<>();

                for (int i = 0; i < officerRecentList.size(); i++) {
                    RecentLogsBean recentLogsBean = new RecentLogsBean();
                    recentLogsBean.setRecentValue(officerRecentList.get(i).getRecentValue());
                    recentLogsBean.setObjectList(officerRecentList.get(i).getObjectList());
                    recentLogsBean.setRecentLogsList(officerRecentList.get(i).getRecentLogsList());
                    recentList.add(recentLogsBean);
                }

                if (recentList != null && recentList.size() > 0) {
                    RecentLogsAdaptor recentLogsAdaptor = new RecentLogsAdaptor(mContext, recentList, onItemClickRecent);
                    rvRecent.setAdapter(recentLogsAdaptor);
                }
            }
        }
    }

    /**
     * Officer selection listener
     */
    public OnItemClickListener.OnItemClickCallback onItemClickRecent = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_OFFICER)) {

                    if (mActivityEditRef != null) {
                        if (appSession.getSearchedOfficer() != null) {
                            mActivityEditRef.populateRecent(mActivityEditRef, appSession.getSearchedOfficer().get(position).getObjectList(), appSession.getSearchedOfficer().get(position).getRecentLogsList(), GenericConstant.OFFICER);
                        }
                    } else if (mActivityRef != null) {
                        if (appSession.getSearchedOfficer() != null) {
                            PopulateFields.populateRecent(mActivityRef, appSession.getSearchedOfficer().get(position).getObjectList(), appSession.getSearchedOfficer().get(position).getRecentLogsList(), GenericConstant.OFFICER);
                        }
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_PERSON)) {

                    if (mActivityEditRef != null) {
                        if (appSession.getRecentSearchedPerson() != null) {
                            mActivityEditRef.populateRecent(mActivityEditRef, appSession.getRecentSearchedPerson().get(position).getObjectList(), appSession.getRecentSearchedPerson().get(position).getRecentLogsList(), GenericConstant.PERSON);
                        }
                    } else if (mActivityRef != null) {
                        if (appSession.getRecentSearchedPerson() != null) {
                            PopulateFields.populateRecent(mActivityRef, appSession.getRecentSearchedPerson().get(position).getObjectList(), appSession.getRecentSearchedPerson().get(position).getRecentLogsList(), GenericConstant.PERSON);
                        }
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_VEHICLE)) {

                    if (mActivityEditRef != null) {
                        if (appSession.getSearchedVehicle() != null) {
                            mActivityEditRef.populateRecent(mActivityEditRef, appSession.getSearchedVehicle().get(position).getObjectList(), appSession.getSearchedVehicle().get(position).getRecentLogsList(), GenericConstant.VEHICLE);
                        }
                    } else if (mActivityRef != null) {
                        if (appSession.getSearchedVehicle() != null) {
                            PopulateFields.populateRecent(mActivityRef, appSession.getSearchedVehicle().get(position).getObjectList(), appSession.getSearchedVehicle().get(position).getRecentLogsList(), GenericConstant.VEHICLE);
                        }
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_ADDRESS)) {

                    if (mActivityEditRef != null) {
                        if (appSession.getSearchedAddress() != null) {
                            mActivityEditRef.populateRecent(mActivityEditRef, appSession.getSearchedAddress().get(position).getObjectList(), appSession.getSearchedAddress().get(position).getRecentLogsList(), GenericConstant.ADDRESS);
                        }
                    } else if (mActivityRef != null) {
                        if (appSession.getSearchedAddress() != null) {
                            PopulateFields.populateRecent(mActivityRef, appSession.getSearchedAddress().get(position).getObjectList(), appSession.getSearchedAddress().get(position).getRecentLogsList(), GenericConstant.ADDRESS);
                        }
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_TEAM)) {

                    if (mActivityEditRef != null) {
                        if (appSession.getSearchedTeam() != null) {
                            mActivityEditRef.populateRecent(mActivityEditRef, appSession.getSearchedTeam().get(position).getObjectList(), appSession.getSearchedTeam().get(position).getRecentLogsList(), GenericConstant.TEAM);
                        }
                    } else if (mActivityRef != null) {
                        if (appSession.getSearchedTeam() != null) {
                            PopulateFields.populateRecent(mActivityRef, appSession.getSearchedTeam().get(position).getObjectList(), appSession.getSearchedTeam().get(position).getRecentLogsList(), GenericConstant.TEAM);
                        }
                    }

                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_EVENT)) {

                    if (mActivityEditRef != null) {
                        if (appSession.getSearchedEvent() != null) {
                            mActivityEditRef.populateRecent(mActivityEditRef, appSession.getSearchedEvent().get(position).getObjectList(), appSession.getSearchedEvent().get(position).getRecentLogsList(), GenericConstant.EVENT);
                        }
                    } else if (mActivityRef != null) {
                        if (appSession.getSearchedEvent() != null) {
                            PopulateFields.populateRecent(mActivityRef, appSession.getSearchedEvent().get(position).getObjectList(), appSession.getSearchedEvent().get(position).getRecentLogsList(), GenericConstant.EVENT);
                        }
                    }
                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_INCIDENT)) {

                    if (mActivityEditRef != null) {
                        if (appSession.getSearchedEvent() != null) {
                            mActivityEditRef.populateRecent(mActivityEditRef, appSession.getSearchedEvent().get(position).getObjectList(), appSession.getSearchedEvent().get(position).getRecentLogsList(), GenericConstant.EVENT);
                        }
                    } else if (mActivityRef != null) {
                        if (appSession.getSearchedEvent() != null) {
                            PopulateFields.populateRecent(mActivityRef, appSession.getSearchedEvent().get(position).getObjectList(), appSession.getSearchedEvent().get(position).getRecentLogsList(), GenericConstant.EVENT);
                        }
                    }
                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_ORGANISATION)) {

                    if (mActivityEditRef != null) {
                        if (appSession.getSearchedOrg() != null) {
                            mActivityEditRef.populateRecent(mActivityEditRef, appSession.getSearchedOrg().get(position).getObjectList(), appSession.getSearchedOrg().get(position).getRecentLogsList(), GenericConstant.ORGANISATION);
                        }
                    } else if (mActivityRef != null) {
                        if (appSession.getSearchedOrg() != null) {
                            PopulateFields.populateRecent(mActivityRef, appSession.getSearchedOrg().get(position).getObjectList(), appSession.getSearchedOrg().get(position).getRecentLogsList(), GenericConstant.ORGANISATION);
                        }
                    }
                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_OFFENCE)) {

                    if (mActivityEditRef != null) {
                        if (appSession.getSearchedOffence() != null) {
                            mActivityEditRef.populateRecent(mActivityEditRef, appSession.getSearchedOffence().get(position).getObjectList(), appSession.getSearchedOffence().get(position).getRecentLogsList(), GenericConstant.OFFENCE);
                        }
                    } else if (mActivityRef != null) {
                        if (appSession.getSearchedOffence() != null) {
                            PopulateFields.populateRecent(mActivityRef, appSession.getSearchedOffence().get(position).getObjectList(), appSession.getSearchedOffence().get(position).getRecentLogsList(), GenericConstant.OFFENCE);
                        }
                    }
                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_ALLEGATION)) {

                    if (mActivityEditRef != null) {
                        if (appSession.getSearchedAllegation() != null) {
                            mActivityEditRef.populateRecent(mActivityEditRef, appSession.getSearchedAllegation().get(position).getObjectList(), appSession.getSearchedAllegation().get(position).getRecentLogsList(), GenericConstant.ALLEGATION);
                        }
                    } else if (mActivityRef != null) {
                        if (appSession.getSearchedAllegation() != null) {
                            PopulateFields.populateRecent(mActivityRef, appSession.getSearchedAllegation().get(position).getObjectList(), appSession.getSearchedAllegation().get(position).getRecentLogsList(), GenericConstant.ALLEGATION);
                        }
                    }
                } else if (searchType != null && searchType.equalsIgnoreCase(GenericConstant.SEARCH_CRIME_GROUP)) {

                    if (mActivityEditRef != null) {
                        if (appSession.getSearchedCrimeGroup() != null) {
                            mActivityEditRef.populateRecent(mActivityEditRef, appSession.getSearchedCrimeGroup().get(position).getObjectList(), appSession.getSearchedCrimeGroup().get(position).getRecentLogsList(), GenericConstant.CRIMEGROUP);
                        }
                    } else if (mActivityRef != null) {
                        if (appSession.getSearchedCrimeGroup() != null) {
                            PopulateFields.populateRecent(mActivityRef, appSession.getSearchedCrimeGroup().get(position).getObjectList(), appSession.getSearchedCrimeGroup().get(position).getRecentLogsList(), GenericConstant.CRIMEGROUP);
                        }
                    }
                }
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), RecentLogsFragment.class, "onOfficerClick");
            }
        }
    };

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
