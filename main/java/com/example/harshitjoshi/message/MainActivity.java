package com.example.harshitjoshi.message;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button send;
    EditText message;
    EditText number;
    IntentFilter intentFilter;
    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            TextView inText = findViewById(R.id.textMsg);
            inText.setText(intent.getExtras().getString("message"));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");
        send = findViewById(R.id.chat_send_button);
        message = findViewById(R.id.message);
        number = findViewById(R.id.phoneNumber);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myMsg=message.getText().toString();
                String theNumber=number.getText().toString();
                sendMessage(theNumber,myMsg);
            }
        });

    }

    private void sendMessage(String theNumber, String myMsg)
    {
        String SENT="Message Sent";
        String DELIVERED="Message Delivered";
        PendingIntent sentPI=PendingIntent.getBroadcast(this,0,new Intent(SENT),0);
        PendingIntent deliveredPI=PendingIntent.getBroadcast(this,0,new Intent(DELIVERED),0);

    }

    @Override
    protected void onResume() {
        registerReceiver(intentReceiver,intentFilter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(intentReceiver);
        super.onPause();
    }
}
