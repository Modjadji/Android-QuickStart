package modjadji.org.quickstart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import modjadji.org.quickstart.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeToolbar("Quick Start App", false);

        //wallet info button
        findViewById(R.id.wallet_info_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentToLaunch = new Intent(MainActivity.this, WalletInfoActivity.class);
                        MainActivity.this.startActivity(intentToLaunch);
                    }
                });

        //transaction history button
        findViewById(R.id.transaction_history_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentToLaunch = new Intent(MainActivity.this, TransactionsHistoryActivity.class);
                        MainActivity.this.startActivity(intentToLaunch);
                    }
                });

        //view member button
        findViewById(R.id.view_member_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentToLaunch = new Intent(MainActivity.this, ViewMemberActivity.class);
                        MainActivity.this.startActivity(intentToLaunch);
                    }
                });

        //register member button
        findViewById(R.id.register_member_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentToLaunch = new Intent(MainActivity.this, RegisterMemberActivity.class);
                        MainActivity.this.startActivity(intentToLaunch);
                    }
                });
    }
}
