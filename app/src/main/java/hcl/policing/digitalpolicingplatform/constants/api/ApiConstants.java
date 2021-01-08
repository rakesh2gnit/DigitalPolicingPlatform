package hcl.policing.digitalpolicingplatform.constants.api;

import java.util.regex.Pattern;

/**
 * Created by prateek.
 */
public interface ApiConstants {

    // Base Url
    String IMAGE_URL = "http://192.168.1.11:8091/";
    String NAV_URL = "http://maps.google.com/maps?saddr=";


    /*URL Parts*/
    String HTTPS="https://";
    String IP="ukpolicemobility.hcl.com/";
    String GATEWAY="HCL.DPP.Gateway/api/";
    String API_ACCOUNT="Account/";
    String API_CONFIGURATION="Configuration/";
    String API_USER="User/";
    String BASE_URL = HTTPS+IP+GATEWAY;

    /*
     * Methods for request on remote server
     */

    String METHOD_SIGN_IN="SignIn";
    String METHOD_SIGN_OUT="Signout";
    String METHOD_FORGOT_PASSWORD="ForgotPassword";
    String METHOD_CHANGE_PASSWORD="ChangePassword";
    String METHOD_GET_PROCESS_FLOW="GetProcessFlow";
    String METHOD_GET_USER_BY_USERID="GetUserDetailbyUserID";
    String METHOD_SEARCH_USER="SearchUser";
    String METHOD_GET_SUB_ORDINATE_LIST="GetSubOrdinatesList";
    String METHOD_UPDATE_USER="UpdateUser";
    String METHOD_SEARCH_TRIGGER="SearchAllTrigger";
    String METHOD_GET_MASTER_DATA="GetMasterData";
    String METHOD_UPLOAD_TRIGGER="UploadTriggers";

    String CUSTOMER_CODE_HCL="hcl";
    String PROCESS_USER="User";
    String PROCESS_ACCOUNT="Account";
    String PROCESS_FLOW="ProcessFlow";
    String PROCESS_TRIGGER="Trigger";
    String PROCESS_CONFIGURATION="Configuration";
    String PROCESS_TASKING="Tasking";
    String PROCESS_TOR = "TOR";
    String PROCESS_STOPS = "STOPS";
    String PROCESS_CRIME = "Crime";
    String PROCESS_BRIEFING="Briefing";
    String PROCESS_WITNESS="Witness";
    String PROCESS_INCIDENT="Incident";
    String PROCESS_SEARCH = "Search";

    String SUB_PROCESS_READ = "Read";
    String SUB_PROCESS_UPDATE = "Update";
    String SUB_PROCESS_CREATE = "Create";
    String SUB_PROCESS_DELETE="Delete";
    String SUB_PROCESS_CREATE_BRIEFING="CreateBriefing";
    String SUB_PROCESS_PERSON="Person";
    String SUB_PROCESS_VEHICLE="Vehicle";
    String SUB_PROCESS_ADDRESS="Address";
    String SUB_PROCESS_INCIDENT="Incident";
    String LOOKUP_TYPE_ALL="All";
    String TASK_TYPE="TaskType";




    // ************************ url's   ***************************************************

    String LOGIN = "Authenticate";
    String SIGNUP = "user/InsertUserDetails";
    String GET_CITIES = "Route/GetCities";
    String GET_ROUTE_DETAILS = "Route/GetRouteDetails";
    String GET_MENU = "Menu/GetMenu/";
    String CREATE_NEW_ORDER = "Order/CreateNewOrder";
    String CONFIRM_TRANSACTION = "Order/CommitTransaction";
    String CURRENT_ORDERS = "Order/MyOrders";
    String ALL_ORDER_LIST = "Order/AllOrderList";
    String ORDER_DETAIL = "Order/GetOrderDetail";
    String CANCEL_ORDER = "Order/CancelOrderByUser";
    String DELIVERED_ORDER = "Order/DeliveredOrder";
    String ACTIVE_ORDER = "Order/GetActiveOrderDetails/";


    /**
     * Parameter name for request on remote server
     */

    int STATUS_OK=200;
    int SEARCH_BY_ID=1;
    int SEARCH_BY_WORD=2;
    int LAST_HIT_TIME = 28800000;
    // **********************  key parameter  *************************************

    String PM_UserName = "UserName";
    String PM_Password = "Password";
    String PM_Assigned = "Assigned";

    //******************************** search types ******************************//

    Pattern pncTypes = Pattern.compile("CNC|PMC|PNG|PND|PNL|PLC|GMC|VLC|DLC|TNT|TLC|EMC");
    Pattern createType = Pattern.compile("crate|great|grate|creed|cracked|create|creator|career");
    Pattern pocketTypes = Pattern.compile("pop-up|pocket|pop|book");
    Pattern torTypes = Pattern.compile("tor|t o r|tore|tour|door|or|utah|store|traffic offence report|traffic offence|traffic|creator|crate tor|great tor|grate tor|creed tor|cracked tor|create tor|career");
    Pattern stopTypes = Pattern.compile("stop|stop n search|stop and search|stops|stops search|stops n search|stops and search|create stops|create stop n search|create stops and search|create stop and search|tops");
    Pattern crimeTypes = Pattern.compile("create crime|cratecrime|create rime|crime|rime|cratcrime|crat crime|crat rime");
    Pattern witnessTypes = Pattern.compile("witness|winners|ritz|which|weather|statement");
    Pattern fdsTypes = Pattern.compile("FBS|FBF|FDF|STS|STF|FTS|DLC|VDS");
    Pattern launchType = Pattern.compile("submit|launch|lunch|ok");
    Pattern incidentType = Pattern.compile("create incident|cratcident|create accident|crat cident");
    Pattern addressTypes = Pattern.compile("ROAD|PARK|AVENUE|STREET|COURT|WAY|WALK|DRIVE|PLACE|GROVE|HILL|GARDENS|HALL|MEADOWS|LANE");
    Pattern numericValues = Pattern.compile("[0-9]");
    Pattern yesTypes = Pattern.compile("yes|yeah|correct|yep|yup|ok|submit");
    Pattern noTypes = Pattern.compile("no|nah|nope|incorrect|know");
    Pattern riskTypes = Pattern.compile("RISK|DISC|RICK|MISC|ASSESSMENT|REST");
    Pattern searchPerson = Pattern.compile("person|prsn|prson|search person|earsch person|sir person");
    Pattern searchVehicle = Pattern.compile("vehicle|vekil|vkl|search vehicle|earsch vehicle|sir vehicle");
    Pattern searchAddress = Pattern.compile("address|addrs|add res|search address|earsch address|sir address");
    Pattern searchIncident = Pattern.compile("incident|dent|accident|icident|intident|int dent|search incident|earsch incident|sir incident");





}