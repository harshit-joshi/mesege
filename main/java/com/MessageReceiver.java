package com;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MessageReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle bundle=intent.getExtras();
        SmsMessage[] messages;
        String str="";
        if(bundle !=null)
        {
            Object[] pdus=(Object[]) bundle.get("pdus");
            messages=new SmsMessage[pdus !=null ? pdus.length:0];
            for (int i=0;i<messages.length;i++)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String format = bundle.getString("format");
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                }
                else {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                str +=messages[i].getOriginatingAddress();
                str +=": ";
                str +=messages[i].getMessageBody();
                str +="\n";

            }
            Toast.makeText(context,str,Toast.LENGTH_LONG).show();
            Intent broadcastIntent=new Intent();
            broadcastIntent.setAction("SMS_RECEIVED_ACTION");
            broadcastIntent.putExtra("message",str);
            context.sendBroadcast(broadcastIntent);
        }

    }

}
