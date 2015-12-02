package modjadji.org.quickstart.models;

import com.google.gson.annotations.SerializedName;

public class WalletInfo {
    @SerializedName("DataObject")
    public DataObject Info;

    public String Status;
    public String Message;

    public static class DataObject {
        public double CurrentBalance;
    }
}
