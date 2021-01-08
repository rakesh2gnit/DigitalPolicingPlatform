package hcl.policing.digitalpolicingplatform.activities.process.flow;

import org.json.JSONArray;

import hcl.policing.digitalpolicingplatform.constants.fieldName.FieldsNameConstant;

public class CheckArray {

    public static boolean isArray(String arrayName) {
        if(arrayName.equalsIgnoreCase(FieldsNameConstant.Images) || arrayName.equalsIgnoreCase(FieldsNameConstant.Signature)
                || arrayName.equalsIgnoreCase(FieldsNameConstant.Documents) || arrayName.equalsIgnoreCase(FieldsNameConstant.Audios)
                || arrayName.equalsIgnoreCase(FieldsNameConstant.Sketch)) {
            return true;
        }
        return false;
    }
}
