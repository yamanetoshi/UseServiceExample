package jp.shuri.useserviceexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;


public class MyActivity extends ActionBarActivity {

    private MyBroadcastReceiver mReceiver;
    private IntentFilter mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        mReceiver = new MyBroadcastReceiver();
//        receiver.activity = this;
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("MyIntentService_ACTION");
        registerReceiver(mReceiver, mIntentFilter);

        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClassName("jp.shuri.serviceexample", "jp.shuri.serviceexample.MyIntentService");

                if (!isIntentAvailable(MyActivity.this, i)) {
                    Toast.makeText(MyActivity.this, "service is not installed", Toast.LENGTH_LONG).show();
                    return;
                }

                i.setAction("jp.shuri.serviceexample.action.FOO");
                MyActivity.this.startService(i);
            }
        });
    }

    public static boolean isIntentAvailable(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();

        List list = packageManager.queryIntentServices(intent, 0);

        return list.size() > 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String message = bundle.getString("message");

            Log.d("MyBroadcastReceiver", "receive ; " + message);
            Toast.makeText(context, "receive : " + message, Toast.LENGTH_LONG).show();
        }
    }
}
