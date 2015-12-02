package modjadji.org.quickstart.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class MemberInfo {
    @SerializedName("DataObject")
    public DataObject Info;

    public String Status;
    public String Message;

    public static class DataObject {
        public String FirstName;
        public String Surname;
        public Date DateOfBirth;
        public String Email;
        public String Gender;
    }
}
