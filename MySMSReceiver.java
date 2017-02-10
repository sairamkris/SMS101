package com.example.neusoft.mysmsapplication;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MySMSReceiver extends BroadcastReceiver {

    String senderNumber;
    String senderName;
    private MainSMSActivity ma;

    public MySMSReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        StringBuilder sb = new StringBuilder();

        /* The SMS-Messages are 'hiding' within the extras of the Intent. */
        Bundle bundle = intent.getExtras();
        if (bundle != null) {


            /* Get all messages contained in the Intent*/
            SmsMessage[] messages =
                    Telephony.Sms.Intents.getMessagesFromIntent(intent);

            /* Feed the StringBuilder with all Messages found. */
            for (SmsMessage currentMessage : messages) {
                sb.append("SMS From: ");
                /* Sender-Number */
                senderNumber = currentMessage.getDisplayOriginatingAddress();
                sb.append("< " + senderNumber + " >\n");

                if (senderNumber.length() > 0) {

                      /* Logger Debug-Output */
                    Log.i("RECEIVE SMS", "[SMSApp] senderNumber: " + senderNumber);
                    //senderName = ma.getName(senderNumber);
                    //sb.append("< " + senderName + " >\n");
                }

                sb.append("[ ");
                /* Actual Message-Content */
                sb.append(currentMessage.getDisplayMessageBody());
                sb.append(" ]");
            }
        }
        /* Logger Debug-Output */
        Log.i("RECEIVE SMS", "[SMSApp] onReceiveIntent: " + sb);

        /* Show the Notification containing the Message. */
        Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG).show();
        Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG).show();
        //throw new UnsupportedOperationException("Not yet implemented");
    }

}