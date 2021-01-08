package hcl.policing.digitalpolicingplatform.models.layoutHelper;

import java.io.Serializable;

public class MenuDTO implements Serializable {

    private String item;
    private int id;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
