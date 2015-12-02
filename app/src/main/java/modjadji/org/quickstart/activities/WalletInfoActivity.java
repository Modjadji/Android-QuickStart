package modjadji.org.quickstart.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

import modjadji.org.quickstart.Constants;
import modjadji.org.quickstart.R;
import modjadji.org.quickstart.models.WalletInfo;
import modjadji.org.quickstart.utils.AmountFormatter;
import modjadji.org.quickstart.utils.ApiUtils;

public class WalletInfoActivity extends BaseActivity {
    private TextView mAmountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_info);

        initializeToolbar("Wallet Info", true);
        initializeShowApiDocumentationLink();

        mAmountTextView = (TextView) findViewById(R.id.amount_textview);

        fetchWalletInfo();
    }

    @SuppressWarnings("deprecation")
    private void fetchWalletInfo() {
        showProgressDialog();
        (new AsyncTask<Void, Void, WalletInfo>() {
            @Override
            protected WalletInfo doInBackground(Void... params) {
                try {
                    List<NameValuePair> queryParams = new ArrayList<>();
                    queryParams.add(new BasicNameValuePair("walletCode", Constants.QUICKSTART_WALLET_CODE));

                    HttpGet request = new HttpGet(Constants.API_BASE_URL + "Wallet/GetWalletInfo?" + URLEncodedUtils.format(queryParams, "UTF-8"));
                    ApiUtils.appendCommonRequestParams(request);

                    HttpResponse response = (new DefaultHttpClient()).execute(request);
                    String jsonResponse = EntityUtils.toString(response.getEntity());
                    Log.d("API", jsonResponse);
                    WalletInfo walletInfo = ApiUtils.getSharedGson().fromJson(jsonResponse, WalletInfo.class);

                    if (!walletInfo.Status.equals(Constants.API_SUCCESS_CODE))
                        throw new Error(walletInfo.Message);

                    return walletInfo;
                } catch (Exception e) {
                    Log.e(WalletInfoActivity.this.getClass().toString(), e.getLocalizedMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(WalletInfo walletInfo) {
                hideProgressDialog();
                populateWalletInfo(walletInfo);

            }
        }).execute();
    }

    private void populateWalletInfo(WalletInfo walletInfo) {
        if (walletInfo != null) {
            mAmountTextView.setText(AmountFormatter.getInstance().formatAmount(walletInfo.Info.CurrentBalance));
        }
    }
}
