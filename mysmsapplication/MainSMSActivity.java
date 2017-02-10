package com.example.neusoft.mysmsapplication;

import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class MainSMSActivity extends AppCompatActivity {

    public ContentResolver cr = getContentResolver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sms);

        //Send SMS
        startSms();
    }

    public void startSms() {

        Intent smsIntent = new Intent(this, SendSmsActivity.class);
        startActivity(smsIntent);
    }


    public static String getName(String senderNumber) {

        String senderName;
        MainSMSActivity ma = new MainSMSActivity();
        Cursor phones = ma.cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            String snumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            if (snumber.compareTo(senderNumber) > 0) {
                senderName = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                System.out.println(".................." + snumber);
                Log.i("com.example.neusoft", " Sender Phone Number: " + snumber + " Sender Name: " + senderName);
                phones.close();// close cursor
                return senderName;
             }
        }
        phones.close();// close cursor
        return null;
    }
}
