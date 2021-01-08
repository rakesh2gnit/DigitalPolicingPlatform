package hcl.policing.digitalpolicingplatform.models.process.subjsonlist;

import java.io.Serializable;
import java.util.List;

public class TabNameDTO implements Serializable {
    private String serverName;
    private String name;
    private int count;
    private int id;
    private boolean visibility;
    private List<KeyValueListDTO> listKeyValue;
    private List<TabNameDTO> listTab;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public List<KeyValueListDTO> getListKeyValue() {
        return listKeyValue;
    }

    public void setListKeyValue(List<KeyValueListDTO> listKeyValue) {
        this.listKeyValue = listKeyValue;
    }

    public List<TabNameDTO> getListTab() {
        return listTab;
    }

    public void setListTab(List<TabNameDTO> listTab) {
        this.listTab = listTab;
    }
}
