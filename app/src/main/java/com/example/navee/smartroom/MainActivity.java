package com.example.navee.smartroom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et1,et2;
    Button bt;
    String host,port;
    CheckBox box;
    String host1,port1;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1=(EditText)findViewById(R.id.editText);
        et2=(EditText)findViewById(R.id.editText2);
        bt=(Button)findViewById(R.id.button);
        box=(CheckBox)findViewById(R.id.checkBox);
        et1.setFocusable(true);
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        final NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        WifiManager wifiMgr = (WifiManager) getBaseContext().getSystemService(Context.WIFI_SERVICE);
        final WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        sharedPreferences=getSharedPreferences("mypref", Context.MODE_PRIVATE);
        if(sharedPreferences!=null)
        {
            if(sharedPreferences.getBoolean("bool", Boolean.parseBoolean(null))==true)
            {
                host1 = sharedPreferences.getString("host", null);
                port1 = sharedPreferences.getString("port", null);
                box.setChecked(true);
                et1.setText(host1);
                et2.setText(port1);
            }
        }
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                host=et1.getText().toString();
                port=et2.getText().toString();
              //  if(wifi.isConnected()) {
                    if (host.matches("")) {
                        Toast.makeText(MainActivity.this, "please input host", Toast.LENGTH_SHORT).show();
                    }
                    if (port.matches("")) {
                        Toast.makeText(MainActivity.this, "please input port", Toast.LENGTH_SHORT).show();
                    }
                    if (host.length() > 0 && port.length() > 0) {
                        if (box.isChecked()) {
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.putString("host", host);
                            edit.putString("port", port);
                            edit.putBoolean("bool", true);
                            edit.commit();
                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            intent.putExtra("host", host);
                            intent.putExtra("port", port);
                            finish();
                            startActivity(intent);
                        } else {
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.putBoolean("bool", false);
                            edit.commit();
                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            intent.putExtra("host", host);
                            intent.putExtra("port", port);
                            finish();
                            startActivity(intent);
                        }
                    }
             //   }
             /*   else{
                    Toast.makeText(MainActivity.this, "please turn on wifi", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

    }
}
