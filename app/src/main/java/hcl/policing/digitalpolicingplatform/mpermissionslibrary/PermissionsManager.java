package hcl.policing.digitalpolicingplatform.mpermissionslibrary;

/**
 * Created by HCL Technologies on 11/05/19.
 * it is a single permission manager class which is used as a singleton
 */
public class PermissionsManager {

    private static PermissionsManager _instance;
    private boolean isStoragePermissionDenied;

    public static PermissionsManager getInstance() {
        if (_instance == null) {
            _instance = new PermissionsManager();
        }

        return _instance;
    }

    public boolean isStoragePermissionDenied() {
        return isStoragePermissionDenied;
    }

    public void setStoragePermissionDenied(boolean storagePermissionDenied) {
        isStoragePermissionDenied = storagePermissionDenied;
    }
}
