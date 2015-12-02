package modjadji.org.quickstart.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

import modjadji.org.quickstart.Constants;
import modjadji.org.quickstart.R;
import modjadji.org.quickstart.models.MemberInfo;
import modjadji.org.quickstart.utils.ApiUtils;

public class ViewMemberActivity extends BaseActivity {
    private TextView mNameTextView;
    private TextView mDateOfBirthTextView;
    private TextView mEmailTextView;
    private TextView mGenderTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_member);

        initializeToolbar("View Member", true);

        mNameTextView = (TextView) findViewById(R.id.name_textview);
        mDateOfBirthTextView = (TextView) findViewById(R.id.date_of_birth_textview);
        mEmailTextView = (TextView) findViewById(R.id.email_textview);
        mGenderTextView = (TextView) findViewById(R.id.gender_textview);

        fetchMemberInfo();
    }

    @SuppressWarnings("deprecation")
    private void fetchMemberInfo() {
        showProgressDialog();
        (new AsyncTask<Void, Void, MemberInfo>() {
            @Override
            protected MemberInfo doInBackground(Void... params) {
                try {
                    HttpGet request = new HttpGet(Constants.API_BASE_URL + "member/GetPersonalInfo");
                    ApiUtils.appendCommonRequestParams(request);

                    HttpResponse response = (new DefaultHttpClient()).execute(request);
                    String jsonResponse = EntityUtils.toString(response.getEntity());
                    Log.d("API", jsonResponse);
                    MemberInfo memberInfo = ApiUtils.getSharedGson().fromJson(jsonResponse, MemberInfo.class);

                    if (!memberInfo.Status.equals(Constants.API_SUCCESS_CODE))
                        throw new Error(memberInfo.Message);

                    return memberInfo;
                } catch (Exception e) {
                    Log.e(ViewMemberActivity.this.getClass().toString(), e.getLocalizedMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(MemberInfo memberInfo) {
                hideProgressDialog();
                populateMemberInfo(memberInfo);

            }
        }).execute();
    }

    private void populateMemberInfo(MemberInfo memberInfo) {
        if (memberInfo != null) {
            SimpleDateFormat dateOfBirthFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

            mNameTextView.setText(String.format("%s %s", memberInfo.Info.FirstName, memberInfo.Info.Surname));
            mDateOfBirthTextView.setText(dateOfBirthFormatter.format(memberInfo.Info.DateOfBirth));
            mEmailTextView.setText(memberInfo.Info.Email);
            mGenderTextView.setText(memberInfo.Info.Gender);
        }
    }
}
