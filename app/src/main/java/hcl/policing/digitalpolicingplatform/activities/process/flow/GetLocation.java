package hcl.policing.digitalpolicingplatform.activities.process.flow;

import android.content.Intent;
import android.location.Address;
import android.os.Handler;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.util.List;

import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.process.edit.EditAnswerActivity;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.AddressBean;
import hcl.policing.digitalpolicingplatform.services.MyLocationServices;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.LocationUtil;
import hcl.policing.digitalpolicingplatform.utils.SearchDialogUtil;

public class GetLocation {

    /**
     * Get the Location address from current location Lat , Long
     *
     * @param act
     */
    public static void getLocation(ProcessCreationActivity act, String searchType) {
        try {
            act.appSession.setLatitude(null);
            act.appSession.setLongitude(null);

            act.mProgressDialog = DialogUtil.showProgressDialog(act);
            Intent i = new Intent(act, MyLocationServices.class);
            i.setAction(MyLocationServices.ACTION_START_FOREGROUND_SERVICE);
            ContextCompat.startForegroundService(act, i);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e("Location ", "Latitude: " + act.appSession.getLatitude() + "  Longitude: " + act.appSession.getLongitude());
                    List<Address> addressList = null;
                    if (act.appSession.getLatitude() != null && !act.appSession.getLongitude().equalsIgnoreCase("")
                            && act.appSession.getLongitude() != null) {
                        try {
                            addressList = LocationUtil.getInstance().getAddressFromLocation(act,
                                    Double.parseDouble(act.appSession.getLatitude()), Double.parseDouble(act.appSession.getLongitude()));
                        } catch (Exception e) {
                            ExceptionLogger.Logger(e.getCause(), e.getMessage(), GetLocation.class, "getLocation");e.printStackTrace();
                        }
                    }
                    if(searchType.toLowerCase().equalsIgnoreCase(GenericConstant.CURRENT_ADDRESS_SEARCH.toLowerCase())) {
                        act.loadSearchDialog(GenericConstant.SEARCH_ADDRESS, addressList);
                    } else {
                        AddressBean addressBean = new AddressBean();
                        if (addressList != null && addressList.size() > 0) {

                            if (addressList.get(0).getFeatureName() != null) {
                                addressBean.setHouseNo(addressList.get(0).getFeatureName());
                                addressBean.setBuildingno(addressList.get(0).getFeatureName());
                                addressBean.setBuildingname(addressList.get(0).getFeatureName());
                                addressBean.setFlatno(addressList.get(0).getFeatureName());
                            } else {
                                addressBean.setHouseNo("");
                                addressBean.setBuildingno("");
                                addressBean.setBuildingname("");
                                addressBean.setFlatno("");
                            }

                            if (addressList.get(0).getSubLocality() != null) {
                                addressBean.setStreet(addressList.get(0).getSubLocality());
                            } else {
                                addressBean.setStreet("");
                            }

                            if (addressList.get(0).getLocality() != null) {
                                addressBean.setCity(addressList.get(0).getLocality());
                            } else {
                                addressBean.setCity("");
                            }

                            if (addressList.get(0).getCountryName() != null) {
                                addressBean.setCountry(addressList.get(0).getCountryName());
                            } else {
                                addressBean.setCountry("");
                            }

                            if (addressList.get(0).getPostalCode() != null) {
                                addressBean.setPostcode(addressList.get(0).getPostalCode());
                            } else {
                                addressBean.setPostcode("");
                            }
                        }
                        PopulateFields.populateType(act, addressBean, GenericConstant.ADDRESS);
                    }
                    DialogUtil.cancelProgressDialog(act.mProgressDialog);
                }
            }, 7000);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), GetLocation.class, "getLocation");
        }
    }

    /**
     * Call the get location method
     *
     * @param act
     */
    public static void getLocation(EditAnswerActivity act, String searchType) {
        try {
            act.appSession.setLatitude(null);
            act.appSession.setLongitude(null);

            act.mProgressDialog = DialogUtil.showProgressDialog(act);
            Intent i = new Intent(act, MyLocationServices.class);
            i.setAction(MyLocationServices.ACTION_START_FOREGROUND_SERVICE);
            ContextCompat.startForegroundService(act, i);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e("Location ", "Latitude: " + act.appSession.getLatitude() + "  Longitude: " + act.appSession.getLongitude());
                    if (act.appSession.getLatitude() != null && !act.appSession.getLongitude().equalsIgnoreCase("")
                            && act.appSession.getLongitude() != null) {
                        List<Address> addressList = null;
                        try {
                            addressList = LocationUtil.getInstance().getAddressFromLocation(act,
                                    Double.parseDouble(act.appSession.getLatitude()), Double.parseDouble(act.appSession.getLongitude()));
                        } catch (Exception e) {
                            ExceptionLogger.Logger(e.getCause(), e.getMessage(), GetLocation.class, "getLocation");
                        }

                        if(searchType.toLowerCase().equalsIgnoreCase(GenericConstant.CURRENT_ADDRESS_SEARCH.toLowerCase())) {
                            act.loadSearchDialog(GenericConstant.SEARCH_ADDRESS, addressList);
                        } else {
                            AddressBean addressBean = new AddressBean();
                            if (addressList != null && addressList.size() > 0) {

                                if (addressList.get(0).getFeatureName() != null) {
                                    addressBean.setHouseNo(addressList.get(0).getFeatureName());
                                    addressBean.setBuildingno(addressList.get(0).getFeatureName());
                                    addressBean.setBuildingname(addressList.get(0).getFeatureName());
                                    addressBean.setFlatno(addressList.get(0).getFeatureName());
                                } else {
                                    addressBean.setHouseNo("");
                                    addressBean.setBuildingno("");
                                    addressBean.setBuildingname("");
                                    addressBean.setFlatno("");
                                }

                                if (addressList.get(0).getSubLocality() != null) {
                                    addressBean.setStreet(addressList.get(0).getSubLocality());
                                } else {
                                    addressBean.setStreet("");
                                }

                                if (addressList.get(0).getLocality() != null) {
                                    addressBean.setCity(addressList.get(0).getLocality());
                                } else {
                                    addressBean.setCity("");
                                }

                                if (addressList.get(0).getCountryName() != null) {
                                    addressBean.setCountry(addressList.get(0).getCountryName());
                                } else {
                                    addressBean.setCountry("");
                                }

                                if (addressList.get(0).getPostalCode() != null) {
                                    addressBean.setPostcode(addressList.get(0).getPostalCode());
                                } else {
                                    addressBean.setPostcode("");
                                }
                            }
                            act.populateType(act, addressBean, GenericConstant.ADDRESS);
                        }
                    }
                    DialogUtil.cancelProgressDialog(act.mProgressDialog);
                }
            }, 7000);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), GetLocation.class, "getLocation");
        }
    }
}
