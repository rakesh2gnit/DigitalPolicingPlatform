package hcl.policing.digitalpolicingplatform.mpermissionslibrary;

/**
 * Created by HCL Technologies on 11/05/19.
 * this interface is used to call different type of permission
 */
public interface PermissionRequest {

    interface RequestCamera extends BasePermissionsListener {
        void onCameraPermissionGranted();

        void onCameraPermissionDenied();
    }

    interface RequestContact extends BasePermissionsListener {
        void onContactPermissionGranted();

        void onContactPermissionDenied();
    }

    interface RequestLocation extends BasePermissionsListener {
        void onLocationPermissionGranted();

        void onLocationPermissionDenied();
    }

    interface RequestPhone extends BasePermissionsListener {
        void onPhonePermissionGranted();

        void onPhonePermissionDenied();
    }

    interface RequestSms extends BasePermissionsListener {
        void onSmsPermissionGranted();

        void onSmsPermissionDenied();
    }

    interface RequestCalender extends BasePermissionsListener {
        void onCalenderPermissionGranted();

        void onCalenderPermissionDenied();
    }

    interface RequestSensor extends BasePermissionsListener {
        void onSensorPermissionGranted();

        void onSensorPermissionDenied();
    }

    interface RequestMicrophone extends BasePermissionsListener {
        void onMicrophonePermissionGranted();

        void onMicrophonePermissionDenied();
    }

    interface RequestStorage extends BasePermissionsListener {
        void onStoragePermissionGranted();

        void onStoragePermissionDenied();
    }

    interface RequestCustomPermissionGroup extends BasePermissionsListener {
        void onAllCustomPermissionGroupGranted();

        void onCustomPermissionGroupDenied();
    }
}
