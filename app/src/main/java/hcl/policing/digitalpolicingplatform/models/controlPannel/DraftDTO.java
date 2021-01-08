package hcl.policing.digitalpolicingplatform.models.controlPannel;

import java.io.Serializable;

public class DraftDTO implements Serializable {

    private String name;
    private String time;
    private int count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
