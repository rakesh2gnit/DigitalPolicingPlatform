package hcl.policing.digitalpolicingplatform.constants.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public interface GenericConstant {

    ArrayList<String> mimeTypeArray = new ArrayList<>(Arrays.asList("application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
            "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
            "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
            "text/plain",
            "application/pdf"));

    String STANDARD_SEARCH = "Standard";
    String THOROUGH_SEARCH = "Thorough";
    String INTIMATE_SEARCH = "Intimate/Stripe";

    //    FDS Constant
    String MAP_ADDRESS = "mapAddress";
    String PNC = "PNC";
    String ATHENA = "ATHENA";
    String STOPS = "STOPS";
    String NFLMS = "NFLMS";
    String QAS = "QAS";
    String DL = "DL";
    String POLE = "POLE";
    String PERSON_LIST = "personList";
    String SEARCH_KEYWORD = "searchedOfficer";
    String OFFICER_LIST = "officerList";
    String POSITION = "position";
    String PROCESS_CREATION = "process";
    String LISTENER = "listener";
    String PROCESS_ID = "processId";
    String SUB_PROCESS_ID = "subProcessId";
    String SUB_PROCESS_LIST = "subProcessList";

    String SEARCH_LIST_DIALOG = "searchListdialog";
    String TEAM_LIST_DIALOG = "teamListdialog";
    String PERSON_LIST_DIALOG = "personListdialog";
    String VEHICLE_LIST_DIALOG = "vehicleListdialog";
    String ADDRESS_LIST_DIALOG = "addressListdialog";
    String EVENT_LIST_DIALOG = "eventListdialog";
    String ALLEGATION_LIST_DIALOG = "allegationListdialog";
    String OFFENCE_LIST_DIALOG = "offenceListdialog";
    String CRIME_GROUP_LIST_DIALOG = "crimeGroupListdialog";
    String CASE_LIST_DIALOG = "caseListdialog";
    String COMMUNICATION_LIST_DIALOG = "communicationListdialog";
    String COURTWARRANT_LIST_DIALOG = "courtWarrantListdialog";
    String INVESTIGATION_LIST_DIALOG = "investigationListdialog";
    String DLCHECK_LIST_DIALOG = "dlCheckListdialog";
    String SUB_PROCESS_LIST_DIALOG = "subProcessListdialog";
    String ORGANISATION_LIST_DIALOG = "organisationListdialog";

    String DETAILS_DIALOG = "detailsDialog";
    String PERSON_LIST_DETAILS_DIALOG = "personListDetailsdialog";
    String LINKED_PERSON_DETAILS_DIALOG = "linkedPersonDetailsdialog";
    String LINKED_VEHICLE_DETAILS_DIALOG = "linkedVehicleDetailsdialog";
    String LINKED_INTELLIGENCE_REPORT_DIALOG = "linkedIntelligencedialog";
    String LINKED_COURT_WARRANT_DIALOG = "linkedCourtWarrantdialog";
    String LINKED_INVESTIGATION_DETAILS_DIALOG = "linkedInvestigationDetailsdialog";
    String ATHENA_DETAILS = "athenaDetails";
    String ADD_ENQUIRY_DIALOG = "addEnquirydialog";
    String LINKED_CASE_DIALOG = "linkedCasedialog";
    String LINKED_CUSTODY_RECORDS_DIALOG = "linkedCustodyRecordsdialog";
    String LINKED_LOCATION_DIALOG = "linkedLocationdialog";
    String LINKED_WARNING_DIALOG = "linkedWarningialog";
    String LINKED_COMMUNICATION_DIALOG = "linkedCommunicationdialog";
    String LINKED_ARREST_DIALOG = "linkedArrestdialog";
    String LINKED_OFFENCE_DIALOG = "linkedOffencedialog";
    String LINKED_ORGANIZATION_DIALOG = "linkedOrganizationdialog";
    String LINKED_SEARCH_DETAIL_DIALOG = "linkedSearchdialog";
    String LINKED_PROPERTY_ITEMS_DIALOG = "linkedPropertyDialog";
    String LINKED_OBJECT_ITERATION_DIALOG = "linkedObjectIterationDialog";

    int TYPE_TEAM = 400;
    int TYPE_PERSON = 100;
    int TYPE_VEHICLE = 200;
    int TYPE_ADDRESS = 300;
    int TYPE_PNC = 1;
    int TYPE_ATHENA = 2;
    int TYPE_STOPS = 3;
    int TYPE_NFLMS = 4;
    int TYPE_QAS = 5;
    int TYPE_DL = 6;
    int TYPE_CASES = 7;
    int TYPE_INVESTIGATION = 8;
    int TYPE_COMMUNICATION = 9;
    int TYPE_COURT_WARRENT = 10;
    int TYPE_DL_CHECK = 11;
    int TYPE_POLE = 12;

    int TYPE_MARKER = 500;
    int TYPE_WARNING = 501;

    int ATHENA_PERSON_LIST = 1000;
    int ATHENA_VEHICLE_LIST = 1001;
    int ATHENA_INVESTIGATION_LIST = 1002;
    int ATHENA_INTELLIGENCE_LIST = 1003;
    int ATHENA_CASES_LIST = 1004;
    int ATHENA_LOCATION_LIST = 1005;
    int ATHENA_CUSTODY_LIST = 1006;
    int ATHENA_COMMUNICATION_LIST = 1007;
    int ATHENA_ARREST_LIST = 1008;
    int ATHENA_BAIL_LIST = 1009;
    int ATHENA_BAIL_REFUSAL_LIST = 1010;
    int ATHENA_DOCUMENT_LIST = 1011;
    int ATHENA_OFFENCE_LIST = 1012;
    int ATHENA_PERSON_SEARCH_LIST = 1013;
    int ATHENA_RISK_ASSESMENT_LIST = 1014;
    int ATHENA_SAMPLE_LIST = 1015;
    int ATHENA_BRIEFING_LIST = 1016;
    int ATHENA_WARNING_LIST = 1017;
    int ATHENA_COURT_WARRANT_LIST = 1018;
    int ATHENA_ORGANIZATION_LIST = 1019;
    int ATHENA_SEARCH_LIST = 1020;
    int ATHENA_PROPERTY_ITEMS_LIST = 1021;
    int ATHENA_CATEGORY_LIST = 1022;
    int ATHENA_OBJECT_ITERATION_LIST = 1023;


    String ATHENA_LIST = "athenaList";
    String PERSON_ATHENA_DETAIL = "Person Athena Details";
    String PERSON_NFLMS_DETAIL = "Person NFLMS Details";
    String ADDRESS_NFLMS_DETAIL = "Address NFLMS Details";
    String ADDRESS_POLE_DETAIL = "Address Pole Details";
    String PERSON_STOPS_DETAIL = "Person STOPS Details";
    String PERSON_POLE_DETAIL = "Person POLE Details";
    String PERSON_QAS_DETAIL = "Person QAS Details";
    String PERSON_DL_DETAIL = "Person DL Details";
    String DL_CHECK_DETAIL = "DL Nomial Details";
    String ADDRESS_QAS_DETAIL = "Address QAS Details";
    String ADDRESS_STOPS_DETAIL = "Address STOPS Details";
    String PERSON_PNC_DETAIL = "Person PNC Details";
    String VEHICLE_STOPS_DETAIL = "Vehicle STOPS Details";
    String VEHICLE_POLE_DETAIL = "Vehicle Pole Details";
    String VEHICLE_ATHENA_DETAIL = "Vehicle Athena Details";
    String VEHICLE_PNC_DETAIL = "Vehicle PNC Details";
    String ADDRESS_ATHENA_DETAIL = "Address Athena Details";
    String INVESTIGATION_ATHENA_DETAIL = "Investigation Athena Details";
    String CASES_ATHENA_DETAIL = "Cases Athena Details";
    String COMMUNICATION_ATHENA_DETAIL = "Communication Athena Details";
    String COOURT_WARRANT_ATHENA_DETAIL = "Court Warrant Athena Details";
    String SYSTEM_ITEM_PERSON = "Persons";
    String SYSTEM_ITEM_VEHICLE = "Vehicles";
    String SYSTEM_ITEM_ARREST = "Arrest";
    String WARNING_LIST = "warningList";

    //Intent Transfer Constants
    String TAST_ID = "TaskID";
    String FILE_NAME_DRAFT = "FileNameDraft";
    String JSON_FILE_NAME = "JsonFileName";
    String DIRECTORY_NAME = "DirectoryName";
    String PROCESS_NAME = "ProcessName";
    String IS_POPULATE = "isPopulate";

    //Normal Constants
    String YES = "Y";
    String NO = "N";
    String No_Details = "No Details";
    boolean POPULATE_TRUE = true;
    boolean POPULATE_FALSE = false;
    boolean LOGIN_YES = true;
    boolean LOGIN_NO = false;
    String JSON_OBJECT = "JSON_OBJECT";
    String Image_Present = "Image_Present";
    String Signature_Present = "Signature_Present";
    String Sketch_Present = "Sketch_Present";
    String Document_Present = "Document_Present";
    String Audio_Present = "Audio_Present";

    /*JSON Select Logic*/
    String NO_FUTURE_DATE = "nofuturedate";
    String LESSER_DATE = "lesserdate";
    String GREATER_DATE = "greaterdate";
    String POPULATE_CURRENT_DATE = "populate_currentdate";
    String POPULATE_FUTURE_DATE = "populate_futuredate";
    String POPULATE_OFFICER_NAME = "populate_officer_name";
    String POPULATE_OFFICER_NUMBER = "populate_officer_number";
    String POPULATE_ALLEGATION = "populate_allegation";
    String POPULATE_TASK_NAME = "populate_task_name";

    /*JSON Special Logic*/

    String SHOW_MAP = "ShowMap";
    String SHOW_MAP_SEARCH = "ShowMapSearch";
    String CURRENT_ADDRESS = "UseCurrentAddress";
    String CURRENT_ADDRESS_SEARCH = "UseCurrentAddressSearch";
    String SHOW_IMAGE = "ShowImageContainer";
    String OPEN_CAMERA = "opencamera";
    String OPEN_GALLERY = "opengallery";
    String SHOW_ATTACHMENT = "ShowAttachmentContainer";
    String SHOW_AUDIO = "ShowAudioContainer";
    String SHOW_SIGNATURE = "ShowSignatureContainer";
    String SHOW_SKETCH = "ShowSketchContainer";
    String SHOW_POCKETBOOK = "ShowPocketbookContainer";
    String SAVE_REQUEST = "SaveRequest";
    String OPEN_DOMESTIC = "openDomestic";
    String PAGESECTION_EDIT = "PAGESECTION_EDIT";
    String EDIT_ID = "EditId";

    String POPULATE_OFFICER = "populate_officer";
    String SEARCH_OFFICER = "OfficerSearch";
    String SEARCH_TEAM = "TeamSearch";
    String SEARCH_ADDRESS = "AddressSearch";
    String SEARCH_PERSON = "PersonSearch";
    String SEARCH_VEHICLE = "VehicleSearch";
    String SEARCH_INVESTIGATION = "InvestigationSearch";
    String SEARCH_CASES = "CasesSearch";
    String SEARCH_COMMUNICATION = "CommunicationSearch";
    String SEARCH_COURT_WARRANT = "CourtWarrantSearch";
    String SEARCH_DL_CHECK = "DlCheckSearch";
    String SEARCH_AIRCRAFT = "AircraftSearch";
    String SEARCH_ANIMAL = "AnimalSearch";
    String SEARCH_ART = "ArtSearch";
    String SEARCH_BODY_PART = "BodyPartSearch";
    String SEARCH_BUILDING_MATERIAL = "BuildingMaterialSearch";
    String SEARCH_CARAVAN = "CaravanSearch";
    String SEARCH_CLOTHING = "ClothingSearch";
    String SEARCH_CHEMICAL = "ChemicalSearch";
    String SEARCH_CURRENCY = "CurrencySearch";
    String SEARCH_DOCUMENT = "DocumentSearch";
    String SEARCH_DRUG = "DrugSearch";
    String SEARCH_ELECTRICAL = "ElectricalSearch";
    String SEARCH_ENGINE = "EngineSearch";
    String SEARCH_JEWELLERY = "JewellerySearch";
    String SEARCH_MOBILE_DEVICE = "MobileDeviceSearch";
    String SEARCH_MOTOR_VEHICLE = "MotorVehicleSearch";
    String SEARCH_OTHER = "OtherSearch";
    String SEARCH_PEDAL_CYCLE = "PedalCycleSearch";
    String SEARCH_MACHINERY = "PlantMachinerySearch";
    String SEARCH_WATERCRAFT = "WatercraftSearch";
    String SEARCH_WEAPON = "WeaponSearch";

    String SEARCH_INCIDENT = "IncidentSearch";
    String SEARCH_ORGANISATION = "OrganisationSearch";
    String SEARCH_ALLEGATION = "AllegationSearch";
    String SEARCH_MO = "MOSearch";
    String SEARCH_OFFENCE = "OffenceSearch";
    String SEARCH_CRIME_GROUP = "CrimeGroupSearch";
    String SEARCH_EVENT = "EventSearch";
    String SEARCH_DL = "DLSearch";

    String SHOW_ADDRESS = "showAddress";
    String SHOW_PERSON_LIST = "ShowPersonList";
    String SHOW_VEHICLE_LIST = "ShowVehicleList";
    String SHOW_FINAL_DIALOG = "showFinalDialog";
    String ATTACHMENT_LIST = "attachmentList";
    String AUDIO_LIST = "audioList";
    String SIGNATURE_LIST = "signatureList";
    String SIGNATURE_EDIT = "signatureEdit";
    String SKETCH_STRING = "sketchString";
    String SKETCH_LIST = "sketchList";
    String SKETCH_EDIT = "sketchEdit";
    String ATTACHMENT_EDIT = "attachmentEdit";
    String AUDIO_EDIT = "audioEdit";
    String POCKETBOOK_EDIT = "pocketbookEdit";
    String POCKETBOOK_STATUS = "pocketbookStatus";
    String IMAGES_EDIT = "imagesEdit";
    String ANSWER_LIST = "answerList";
    String ANSWER_VALUE = "answerValue";
    String OBJECT_REQUEST = "requestObject";
    String BUTTON_ID = "button_id";
    String QUESTION_EDIT = "questionEdit";
    String QUESTION_DEPENDENT_ID = "questionDependentId";
    String QUESTION_DEPENDENT = "questionDependent";
    String SECTION_DEPENDENT = "sectionDependent";
    String SECTION_NAME = "sectionName";
    String SEARCH_NAME = "searchName";
    String SEARCH_ID = "searchID";
    String CURRENT_QUESTION = "currentQuestion";
    String FINAL_ANSWER = "finalAnswer";
    String SKIP_QUESTION = "skipQuestion";
    String ID_LIST_HIDE = "idListHide";
    String ID_LIST_SHOW = "idListShow";
    String ID_QUESTION_HIDE = "idQuestionHide";
    String ID_QUESTION_SHOW = "idQuestionShow";
    String ID_FIELD_HIDE = "idFieldHide";
    String ID_FIELD_SHOW = "idFieldShow";

    String DATE_LOGIC = "dateLogic";

    String SECTION_VISIBLE = "SectionVisible";
    String SECTION_COMPLETE = "SectionComplete";


    //File Type
    String FDS_FILE = "FDSFile";
    String DRAFT_FILE = "draftFile";
    String OFFLINE_FILE = "offlineFile";
    String SERVER_FILE = "serverFile";

    int MAP_REQUEST = 222;
    int MAP_REQUEST_SEARCH = 522;
    int SIGNATURE_REQUEST = 225;
    int SKETCH_REQUEST = 228;
    int OFFICER_REQUEST = 221;
    int EDIT_REQUEST = 421;
    int EDIT_REQUEST_TAB_PROCESS = 422;
    int SYNC_PENDIND = 0;
    int ATTRIBUTE_INITIALIZE_COUNT = 0;


//    Camera , Attachement and other independent constants

    int CAMERA_TYPE = 1;
    int ATTACHMENT_TYPE = 2;
    int CAMERA_PIC_REQUEST = 2;
    int GALLERY_PIC_REQUEST = 1;
    int CAMERA_REQUEST = 11;
    int ATTACHMENT_REQUEST = 12;
    int AUDIO_REQUEST = 15;
    int POCKETBOOK_REQUEST = 17;
    int DOCS_PIC_REQUEST = 3;
    int MAX_COUNT = 5;
    int BITMAP_SAMPLE_SIZE = 10;
    int MEDIA_TYPE_IMAGE = 1;
    int MEDIA_TYPE_VIDEO = 2;
    int BIOMETRIC_REQUEST = 20;

    String FILE_DIRECTORY = "/DPPFiles/Development/";
    String FILE_FOR = "DPPFiles/Development/"; // Quality Development Training Production
    String FILE_DRAFT = "/Draft/";
    String FILE_IMAGES = "/Images/";
    String FILE_DOCS = "/Docs/";
    String FILE_AUDIOS = "/Audios/";
    String FILE_OFFLINE = "/Offline/";
    String IMAGE_DIRECTORY = "/DCIM/PICTURES";
    String IMAGE_DIRECTORY_CROP = "/DCIM/CROP_PICTURES";
    String VIDEO_DIRECTORY = "/DCIM/VIDEOS";
    String PHOTO = "Photo ";
    String ATTACHMENT = "Attachment ";
    String TYPE_DETAILS = "typeDetails";
    String IMAGE = "IMAGE_";
    String IMAGE_EXTENSION = ".jpg";
    String Gallery = "Gallery";
    String ChooseFile = "Choose File";
    String VIDEO_EXTENSION = "mp4";
    String FOLDER_NAME = "DPP_PHOTOS";
    String PHOTO_LIST = "photoList";
    String PATH = "path";
    String PROVIDER = ".provider";
    String UTF_8 = "UTF-8";
    String DROP_DOWN = "DropDown";
    String TRIGGER = "Trigger";
    String INVEST_ID = "investId";

//    Notification Constant

    CharSequence VERBOSE_NOTIFICATION_CHANNEL_NAME = "Verbose WorkManager Notifications";
    String VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION = "Shows notifications whenever work starts";
    CharSequence NOTIFICATION_TITLE = "WorkRequest Starting";
    String CHANNEL_ID = "VERBOSE_NOTIFICATION";
    int NOTIFICATION_ID = 1;


    //    Process Name
    String FDS_PROCESS = "FDS";
    String STOPNSEARCH_PROCESS = "STOPS";
    String TOR_PROCESS = "TOR";
    String CRIME_PROCESS = "CRIME";
    String WITNESS_PROCESS = "WITNESS";

    // JSON Files name

    String PROCESS_JSON = "process.json";
    String STOPNSEARCH_JSON = "stopnsearch_gmp.json";
    String TOR_PROCESS_JSON = "tor_creation_gmp.json";
    String CRIME_PROCESS_JSON = "crime_gmp.json";
    String DOMESTIC_PROCESS_JSON = "domestic_gmp.json";
    String SEARCH_FIELDS = "search_fields.json";
    String SEARCH_LIST = "person_search_list_demo.json";
    String WITNESS_STATEMENT_JSON = "witness_statement.json";
    String POCKET_BOOK_JSON = "pocketbook_gmp.json";
    String INCIDENT_JSON = "create_incident.json";
    String INTELLIGENCE_JSON = "create_intel.json";
    String PROBLEM_SOLVING_JSON = "create_problem_solving.json";

    /* PNC JSON*/
    String PERSON_PNC_LIST_JSON = "person_pnc_list.json";
    String VEHICLE_PNC_LIST_JSON = "vehicle_pnc_list.json";
    String PERSON_PNC_DETAILS_JSON = "person_pnc_details.json";
    String VEHICLE_PNC_DETAILS_JSON = "vehicle_pnc_details.json";
    String PERSON_PNC_ADDRESS_DETAILS_JSON = "person_pnc_address_details.json";
    String PERSON_PNC_ALIAS_GROUP_JSON = "person_pnc_alias_group.json";
    String PERSON_PNC_DESCRIPTION_GROUP_JSON = "person_pnc_description_group.json";
    String PERSON_PNC_DISQUALIFIED_DRIVER_JSON = "person_pnc_disqualified_driver.json";
    String PERSON_PNC_DNA_JSON = "person_pnc_dna.json";
    String PERSON_PNC_PHOTO_LOC_JSON = "person_pnc_photo_loc.json";
    String VEHICLE_PNC_VEHICLE_REPORT_JSON = "vehicle_pnc_vehicle_report.json";

    /*NFLMS JSON Files*/
    String ADDRESS_NFLMS_LIST_JSON = "address_nflms_list.json";
    String PERSON_NFLMS_LIST_JSON = "person_nflms_list.json";
    String PERSON_NFLMS_DETAILS_JSON = "person_nflms_details.json";
    String ADDRESS_NFLMS_DETAILS_JSON = "address_nflms_details.json";

    /*STOPS JSON Files*/
    String ADDRESS_STOPS_LIST_JSON = "address_stops_list.json";
    String ADDRESS_STOPS_DETAILS_JSON = "address_stops_details.json";
    String VEHICLE_STOPS_LIST_JSON = "vehicle_stops_list.json";
    String VEHICLE_STOPS_DETAILS_JSON = "vehicle_stops_details.json";
    String PERSON_STOPS_LIST_JSON = "person_stops_list.json";
    String PERSON_STOPS_DETAILS_JSON = "person_stops_details.json";

    /*QAS JSON Files*/
    String ADDRESS_QAS_LIST_JSON = "address_qas_list.json";
    String PERSON_QAS_LIST_JSON = "person_qas_list.json";
    String ADDRESS_QAS_DETAILS_JSON = "address_qas_details.json";
    String PERSON_QAS_DETAILS_JSON = "person_qas_details.json";

    /*DL JSON Files*/
    String DLCHECK_DL_LIST_JSON = "dlcheck_dl_list.json";
    String PERSON_DL_LIST_JSON = "person_dl_list.json";
    String PERSON_DL_DETAILS_JSON = "person_dl_details.json";

    /*Athena JSON Files*/

    String PERSON_ATHENA_LIST_JSON = "person_athena_list.json";
    String VEHICLE_ATHENA_LIST_JSON = "vehicle_athena_list.json";
    String ADDRESS_ATHENA_LIST_JSON = "address_athena_list.json";
    String CASE_ATHENA_LIST_JSON = "case_athena_list.json";
    String COMMUNICATION_ATHENA_LIST_JSON = "communication_athena_list.json";
    String COURT_WARRANT_ATHENA_LIST_JSON = "court_warrant_athena_list.json";
    String INVESTIGATION_ATHENA_LIST_JSON = "investigation_athena_list.json";
    String PERSON_ATHENA_DETAILS_JSON = "person_athena_details.json";
    String VEHICLE_ATHENA_DETAILS_JSON = "vehicle_athena_details.json";
    String ADDRESS_ATHENA_DETAILS_JSON = "address_athena_details.json";
    String CASE_ATHENA_DETAILS_JSON = "case_athena_details.json";
    String COMMUNICATION_ATHENA_DETAILS_JSON = "communication_athena_details.json";
    String COURT_WARRANT_ATHENA_DETAILS_JSON = "court_warrant_athena_details.json";
    String INVESTIGATION_ATHENA_DETAILS_JSON = "investigation_athena_details.json";
    String OBJECT_ITERATION_ATHENA_DETAILS_JSON = "object_iteration_athena_details.json";
    String ATHENA_LINKED_PERSON_DETAILS_JSON = "athena_linked_person_details.json";
    String ATHENA_LINKED_VEHICLE_DETAILS_JSON = "athena_linked_vehicle_details.json";
    String ATHENA_LINKED_INTELLIGENCE_DETAILS_JSON = "athena_linked_intelligence_details.json";
    String ATHENA_LINKED_INVESTIGATION_DETAILS_JSON = "athena_linked_investigation_details.json";
    String ATHENA_LINKED_CASE_DETAILS_JSON = "athena_linked_case_details.json";
    String ATHENA_LINKED_CUSTODY_DETAILS_JSON = "athena_linked_custody_details.json";
    String ATHENA_LINKED_LOCATION_DETAILS_JSON = "athena_linked_location_details.json";
    String ATHENA_LINKED_COMMUNICATION_DETAILS_JSON = "athena_linked_communication_details.json";
    String ATHENA_LINKED_COURT_WARRANT_DETAILS_JSON = "athena_linked_court_warrant_details.json";
    String ATHENA_LINKED_ARREST_DETAILS_JSON = "athena_linked_arrest_details.json";
    String ATHENA_LINKED_OFFENCE_DETAILS_JSON = "athena_linked_offence_details.json";
    String ATHENA_LINKED_ORGANIZATION_DETAILS_JSON = "athena_linked_organisation_details.json";
    String ATHENA_LINKED_SEARCH_DETAILS_JSON = "athena_linked_search_details.json";
    String ATHENA_LINKED_PROPERTY_DETAILS_JSON = "athena_linked_property_item_details.json";

    /* Pole Json Files */
    String PERSON_POLE_LIST_JSON = "person_pole_list.json";
    String PERSON_POLE_DETAILS_JSON = "person_pole_details.json";
    String ADDRESS_POLE_LIST_JSON = "address_pole_list.json";
    String ADDRESS_POLE_DETAILS_JSON = "address_pole_details.json";
    String VEHICLE_POLE_LIST_JSON = "vehicle_pole_list.json";
    String VEHICLE_POLE_DETAILS_JSON = "vehicle_pole_details.json";
    String TEAM_POLE_LIST_JSON = "team_pole_list.json";
    String EVENT_POLE_LIST_JSON = "event_search_list.json";
    String ALLEGATION_POLE_LIST_JSON = "allegation_search_list.json";
    String OFFENCE_POLE_LIST_JSON = "offence_search_list.json";
    String CRIME_GROUP_LIST_JSON = "crime_group_list.json";
    String ORGANISATION_LIST_JSON = "organisation_search_list.json";

    String PROCESS_LIST_JSON = "process_list.json";
    String AUTHENTICATION_JSON = "authenticate_response.json";
    String OFFICER_LIST_JSON = "officer_search_list.json";
    String SPINNER_JSON = "spinner_value.json";
    String TASK_LIST_JSON = "task_list.json";
    String RIGHTS_JSON = "rights.json";
    String TASK_3_JSON = "task_3.json";
    String TASK_4_JSON = "task_4.json";
    String TASK_8_JSON = "task_8.json";
    String TASK_9_JSON = "task_9.json";
    String TASK_11_JSON = "task_11.json";
    String BLANK_PROPERTIES_JSON = "blank_properties.json";
    String PROPERTIES_JSON = "properties.json";
    String DROPDOWN_MASTER_JSON = "dropdownmaster.json";
    String TRIGGER_LIST_JSON = "trigger_list.json";

    // Polplate Type
    String ORGANISATION = "Organisation";
    String TEAM = "Team";
    String OFFICER = "Officer";
    String PERSON = "Person";
    String ADDRESS = "Address";
    String VEHICLE = "Vehicle";
    String EVENT = "Event";
    String CRIMEGROUP = "CrimeGroup";
    String ALLEGATION = "Allegation";
    String OFFENCE = "Offence";
    String USER_POPULATE = "UserPopulate";

    // Speaking Type
    String SPEAKING_NAME = "speakingName";
    String SPEAKING_REASON = "speakingReason";


    int FDS_PARENT_ID = 53;


//    View Types

    String EDIT_TEXT = "edittext";
    String TEXT_VIEW = "textview";
    String SPPINER = "sppiner";
    String TEXT_INPUT_LAYOUT = "textinputlayout";

    int SHORTCUT_YES = 1;
    int SHORTCUT_NO = 0;

    //  * Constant for Intent calling     */
    int ACTIVITY_RESULT = 1001,
            ACTIVITY_FINISH = 1002,
            GALLERY = 111,
            CAMERA = 112,
            CROP = 113;

    /**
     * Validation ragular expression
     */
    String EMAIL_PATTERN = "com.google";
    Pattern EMAIL_ADDRESS_PATTERN = Pattern
            .compile("^([a-zA-Z0-9._-]+)@{1}(([a-zA-Z0-9_-]{1,67})|([a-zA-Z0-9-]+\\.[a-zA-Z0-9-]{1,67}))\\.(([a-zA-Z0-9]{2,6})(\\.[a-zA-Z0-9]{2,6})?)$");
    Pattern MOBILE_NUMBER_PATTERN = Pattern.compile("^[0-9]{8,14}$");
    Pattern USER_NAME_PATTERN = Pattern.compile("^([a-zA-Z0-9._-]){6,20}$");
    Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$");
    Pattern POSTCODE_VALIDATION = Pattern.compile("^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$");


    //    Device Info Constant

    int TYPE_WIFI = 1;
    int TYPE_MOBILE = 2;
    int TYPE_NOT_CONNECTED = 0;
    int SERIAL_NO = 100;
    int MODEL = 101;
    int ID = 102;
    int MANUFACTURER = 103;
    int BRAND = 104;
    int TYPE = 105;
    int USER = 106;
    int VERSION_CODE_BASE = 107;
    int VERSION_INCREMENTAL = 108;
    int VERSION_SDK = 109;
    int BOARD = 110;
    int HOST = 112;
    int FINGERPRINT = 113;
    int RELEASE = 114;


    enum Months {
        JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC
    }

    String OFF = "0";
    String ON = "1";


}
