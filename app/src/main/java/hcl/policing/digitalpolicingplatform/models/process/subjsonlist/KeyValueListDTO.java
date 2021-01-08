package hcl.policing.digitalpolicingplatform.models.process.subjsonlist;

import java.io.Serializable;
import java.util.List;

public class KeyValueListDTO implements Serializable {

    private List<KeyValueDTO> listKeyValue;

    public List<KeyValueDTO> getListKeyValue() {
        return listKeyValue;
    }

    public void setListKeyValue(List<KeyValueDTO> listKeyValue) {
        this.listKeyValue = listKeyValue;
    }
}
