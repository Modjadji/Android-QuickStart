package modjadji.org.quickstart.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import modjadji.org.quickstart.R;

public class RegisterMemberActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_member);

        initializeToolbar("Register Member", true);
        initializeShowApiDocumentationLink();

        findViewById(R.id.get_api_key_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.modjadji.org/APIs"));
                RegisterMemberActivity.this.startActivity(intent);
            }
        });
    }
}
