package hcl.policing.digitalpolicingplatform.database;

public class SectionMapper {

    static final String TABLE_NAME = "sectionMap";
    public static final String COLUMN_ID = "id";
    static final String COLUMN_PROCESS_ID = "process_id";
    static final String COLUMN_SUB_PROCESS_ID = "sub_process_id";
    static final String COLUMN_SECTION_ID = "section_id";
    static final String COLUMN_SECTION_NAME = "section_name";

    private int id;
    private int processId;
    private int subProcessId;
    private int sectionId;
    private String sectionName;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PROCESS_ID + " INTEGER,"
                    + COLUMN_SUB_PROCESS_ID + " INTEGER,"
                    + COLUMN_SECTION_ID + " INTEGER,"
                    + COLUMN_SECTION_NAME + " TEXT DEFAULT NULL )";

    public SectionMapper() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public int getSubProcessId() {
        return subProcessId;
    }

    public void setSubProcessId(int subProcessId) {
        this.subProcessId = subProcessId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
