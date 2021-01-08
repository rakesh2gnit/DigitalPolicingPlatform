package hcl.policing.digitalpolicingplatform.database;

public class RightsMapper {

    public static final String TABLE_NAME = "rightsmap";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_LEVEL = "level";
    public static final String COLUMN_PARENTID = "parentid";
    public static final String COLUMN_ICON = "icon";
    public static final String COLUMN_ICON_NAME = "icon_name";
    public static final String COLUMN_ICON_ADDRESS = "icon_address";
    public static final String COLUMN_ACTIVE = "active";

    private int id;
    private String name;
    private String code;
    private String type;
    private Integer level;
    private Integer parentid;
    private String icon;
    private String iconname;
    private String iconaddress;
    private String active;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_CODE + " TEXT,"
                    + COLUMN_TYPE + " TEXT,"
                    + COLUMN_LEVEL + " INTEGER,"
                    + COLUMN_PARENTID + " INTEGER,"
                    + COLUMN_ICON + " TEXT DEFAULT NULL,"
                    + COLUMN_ICON_NAME + " TEXT DEFAULT NULL,"
                    + COLUMN_ICON_ADDRESS + " TEXT DEFAULT NULL,"
                    + COLUMN_ACTIVE + " INTEGER DEFAULT 0"
                    //+ COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public RightsMapper() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconname() {
        return iconname;
    }

    public void setIconname(String iconname) {
        this.iconname = iconname;
    }

    public String getIconaddress() {
        return iconaddress;
    }

    public void setIconaddress(String iconaddress) {
        this.iconaddress = iconaddress;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
