package hcl.policing.digitalpolicingplatform.fcm;//package com.hcl.demoapp.fcm;
//
//import android.content.Context;
//import android.provider.ContactsContract;
//import android.support.annotation.NonNull;
//import android.util.Log;
//
//import com.hcl.demoapp.util.AppSession;
//
//import org.json.JSONObject;
//
//import java.io.UnsupportedEncodingException;
//import java.util.HashMap;
//import java.util.Map;
//
//public class MyWorker extends Worker {
//
//    private AppSession appSession;
//    private Context context;
//
//    public MyWorker(@NonNull Context appContext, @NonNull WorkerParameters params) {
//        super(appContext, params);
//        appSession = new AppSession(appContext);
//        context = appContext;
//    }
//
//    @NonNull
//    @Override
//    public ListenableWorker.Result doWork() {
//        // Do your work here.
//        ContactsContract.Contacts.Data input = getInputData();
//
//        rejectAppointment(context, input.getString("appointment_id"), "Dear Customer, Our executive were not able to take your appointment at this moment. Please rebook after some time.");
//        // Return a ListenableWorker.Result
//        Data outputData = new Data.Builder()
//                .putString("", "")
//                .build();
//        //return Result.success(outputData);
//        return Result.success();
//    }
//
//    @Override
//    public void onStopped() {
//        // Cleanup because you are being stopped.
//    }
//
//    private void rejectAppointment(Context context, String appointmentId, String reason) {
//        String url = "https://map.my-looks.in/api/v1.0/Appointment/AppointmentRejected";
//        Log.e("SAVED Url >>>>>> ", url);
//
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("AppointmentId", appointmentId);
//            jsonObject.put("RejectionReason", reason);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        final String requestBody = jsonObject.toString();
//        Log.e("appointmentId", "appointmentId" + jsonObject.toString());
//
//        StringRequest getRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.e("Response >>>>>> ", response);
//                //{"Status":"Success","UserId":227,"SalonId":66,"AppointmentNo":"166244"}
//                try {
//                    JSONObject jsonObject1 = new JSONObject(response);
//                    String status = jsonObject1.getString("Status");
//                    if (status.equalsIgnoreCase("Success")) {
//                        //showAlertDialogAccept("Appointment rejected successfully");
//                        Log.e("Rejected >>>>>> ", "SUCCESS >>>>>");
//                    } else {
//                        Log.e("Rejected >>>>>> ", "Failed >>>>>");
//                        //showAlertDialogerror("Failed! Please try again later.");
//                    }
//                } catch (Error | Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        },
//                error -> {
//                    NetworkResponse networkResponse = error.networkResponse;
//                    String errorMessage = "Unknown error";
//                    if (networkResponse == null) {
//                        if (error.getClass().equals(TimeoutError.class)) {
//                            errorMessage = "Request timeout!\nPlease try again.";
//                            //callTask(1);
//                        } else if (error.getClass().equals(NoConnectionError.class)) {
//                            errorMessage = "Failed to connect server";
//                        }
//                    } else {
//                        String result = new String(networkResponse.data);
//                        try {
//                            if (networkResponse.statusCode == 404) {
//                                errorMessage = "Resource not found";
//                            } else if (networkResponse.statusCode == 401) {
//                                errorMessage = "Your session has expired!\nPlease login again";
//                            } else if (networkResponse.statusCode == 400) {
//                                errorMessage = "Invalid Input!\n Please check your inputs";
//                            } else if (networkResponse.statusCode == 500) {
//                                errorMessage = " Something is getting wrong!\nPlease try again later";
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    Log.i("Error", errorMessage);
//                    //showAlertDialogerror(errorMessage);
//                    error.printStackTrace();
//                }
//        ) {
//            @Override
//            public String getBodyContentType() {
//                return "application/json";
//            }
//
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                try {
//                    return requestBody == null ? null : requestBody.getBytes("utf-8");
//                } catch (UnsupportedEncodingException uee) {
//                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
//                    return null;
//                }
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " + appSession.getAccessToken());
//                System.out.println("PRINTING:::" + params);
//
//                return params;
//            }
//        };
//        getRequest.setShouldCache(false);
//        getRequest.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        requestQueue.add(getRequest);
//
//    }
//}
//
//
