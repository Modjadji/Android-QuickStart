package modjadji.org.quickstart.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class TransactionsHistory {
    @SerializedName("ListOfObjects")
    public List<Transaction> Transactions;

    public String Status;
    public String Message;

    public static class Transaction {
        public String Description;
        public double TranAmount;
        public Date DatePosted;
    }
}
