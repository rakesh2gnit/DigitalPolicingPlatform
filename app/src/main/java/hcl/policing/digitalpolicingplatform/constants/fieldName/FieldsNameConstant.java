package hcl.policing.digitalpolicingplatform.constants.fieldName;

import java.util.regex.Pattern;

/**
 * Created by prateek.
 */
public interface FieldsNameConstant {

    String ID = "_Id";
    String SYSTEM = "_System";

    // Officer
    String Force = "Force";
    String Force_Code = "Force Code";
    String Officer_Service_Number = "Service Number";
    String Officer_Collar_Number = "Collar Number";
    String Officer_Name = "Name";
    String Officer_Rank = "Rank";
    String Officer_Gender = "Gender";
    String Officer_Station = "Home Station";

    //Person
    String Title = "Title";
    String First_Name = "First Name";
    String Last_Name = "Last Name";
    String Fore_Name = "Forename";
    String Sur_Name = "Surname";
    String DOB = "DOB";
    String Person_Gender = "Gender";
    String Officer_Defined_Ethnicity = "Officer Defined Ethnicity";
    String Self_Defined_Ethnicity = "Self Defined Ethnicity";
    String Occupation = "Occupation";
    String Appropriate_Adult_Detail = "Appropriate Adult Detail";

    //Vehicle
    String VRM = "VRM";
    String Make = "Make";
    String Model = "Model";
    String Color = "Color";
    String Primary_Color = "Primary Color";
    String Secondary_Color = "Secondary Color";

    //Location fields
    String BuildingNumber = "Building Number";
    String BuildingName = "Building Name";
    String BuildingNoName = "Building No/Name";
    String FlatName = "Flat Name";
    String FlatNumber = "Flat Number";
    String FlatNoName = "Flat No/Name";
    String HouseNo = "House No";
    String Street = "Street";
    String StreetName = "Street Name";
    String PostCode = "Postcode";
    String City = "Town/City";
    String Country = "Country";
    String County = "County";
    String District = "District";

    //Allegation
    String Statute = "Statute";
    String Code = "Code";
    String Allegation = "Allegation";

    //Offence
    String Offence = "Offence";
    String OffenceCategory = "OffenceCategory";
    String Act = "Act";
    String OffenceClass = "Class";
    String SubClass = "Sub-class";
    String HomeOfficeCode = "Home Office Code";

    //Crime Group
    String CrimeGroup = "Crime Group";
    String Type = "Type";
    String Status = "status";
    String RefNumber = "Ref Number";

    //Organisation
    String Organisation_Name = "Organisation Name";
    String Company_Number = "Company Number";
    String Nature_of_Business = "Nature of Business";

    String TeamName = "Team Name";
    String EventName = "Event Name";
    String URN = "URN";
    String Images = "Images";
    String Signature = "Signature";
    String Sketch = "Sketch";
    String Documents = "Documents";
    String Audios = "Audios";
    String Pocketbook = "Pocketbook";
    String EntryType = "Entry Type";
    String Item_Name = "Name";
    String Item_Description = "Description";
    String Item_Status = "Status";
    String Item_Path = "Path";

    String Text_Assistant = "Assistant";
    String Text_Ok = "OK";
    String Text_Edit = "Edit";
    String Text_Next = "Next";
    String Text_Done = "Done";

}