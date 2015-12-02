package modjadji.org.quickstart.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import modjadji.org.quickstart.Constants;
import modjadji.org.quickstart.R;
import modjadji.org.quickstart.adapters.TransactionsHistoryAdapter;
import modjadji.org.quickstart.models.TransactionsHistory;
import modjadji.org.quickstart.utils.ApiUtils;

public class TransactionsHistoryActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private TransactionsHistoryAdapter mTransactionsHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions_history);

        initializeToolbar("My Wallet Info", true);
        initializeShowApiDocumentationLink();

        initializeRecyclerView();

        fetchTransactionsHistory();
    }

    private void initializeRecyclerView() {
        //adapter
        mTransactionsHistoryAdapter = new TransactionsHistoryAdapter();

        //recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.transaction_history_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mTransactionsHistoryAdapter);
    }

    @SuppressWarnings("deprecation")
    private void fetchTransactionsHistory() {
        showProgressDialog();
        (new AsyncTask<Void, Void, TransactionsHistory>() {
            @Override
            protected TransactionsHistory doInBackground(Void... params) {
                try {
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(2000, 12, 12);
                    Date startDate = calendar.getTime();
                    Date endDate = new Date();

                    List<NameValuePair> queryParams = new ArrayList<>();
                    queryParams.add(new BasicNameValuePair("walletCode", Constants.QUICKSTART_WALLET_CODE));
                    queryParams.add(new BasicNameValuePair("startDate", dateFormatter.format(startDate)));
                    queryParams.add(new BasicNameValuePair("endDate", dateFormatter.format(endDate)));
                    queryParams.add(new BasicNameValuePair("pageSize", "0"));
                    queryParams.add(new BasicNameValuePair("pageNum", "0"));

                    HttpGet request = new HttpGet(Constants.API_BASE_URL + "Wallet/GetWalletTransactions?" + URLEncodedUtils.format(queryParams, "UTF-8"));
                    ApiUtils.appendCommonRequestParams(request);

                    HttpResponse response = (new DefaultHttpClient()).execute(request);
                    String jsonResponse = EntityUtils.toString(response.getEntity());
                    Log.d("API", jsonResponse);
                    TransactionsHistory transactionsHistory = ApiUtils.getSharedGson().fromJson(jsonResponse, TransactionsHistory.class);

                    if (!transactionsHistory.Status.equals(Constants.API_SUCCESS_CODE))
                        throw new Error(transactionsHistory.Message);

                    return transactionsHistory;
                } catch (Exception e) {
                    Log.e(TransactionsHistoryActivity.this.getClass().toString(), e.getLocalizedMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(TransactionsHistory transactionsHistory) {
                hideProgressDialog();
                populateTransactions(transactionsHistory);

            }
        }).execute();
    }

    private void populateTransactions(TransactionsHistory transactionsHistory) {
        mTransactionsHistoryAdapter.showTransactions(transactionsHistory.Transactions);
    }
}
