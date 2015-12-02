package modjadji.org.quickstart.activities;

import android.os.Bundle;

import modjadji.org.quickstart.R;

public class RegisterMemberActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_member);

        initializeToolbar("Register Member", true);
        initializeShowApiDocumentationLink();
    }
}
