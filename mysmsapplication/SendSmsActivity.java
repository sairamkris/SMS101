package com.example.neusoft.mysmsapplication;

import android.app.Activity;
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

public class SendSmsActivity extends Activity {

    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        // Get the test SMS number
        getNumber(this.getContentResolver());

        //Send SMS
        sendSms(phoneNumber);
    }

    public void getNumber(ContentResolver cr)
    {
        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            if (name.contains("TestSms")) {
                phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                System.out.println(".................." + phoneNumber);
                Log.i("com.example.neusoft", " MO SMS Phone Number: " + phoneNumber);
                Toast.makeText(getApplicationContext(), " MO SMS Phonenumber: " + phoneNumber,
                        Toast.LENGTH_LONG).show();
                //aa.add(phoneNumber);
                phones.close();// close cursor
                break;
            }
        }
        phones.close();// close cursor
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        //        android.R.layout.simple_list_item_1,aa);
        //lv.setAdapter(adapter);
        //display contact numbers in the list
    }

    public void sendSms(String phoneNo){

        try {
            //String phoneNo = "9197xxxxxx"; //Testing Only
            if (phoneNo.length() > 0) {
                String sms = "NeuTestD SMS : SMS Sent Completed";
                PendingIntent sentPI;
                String SENT = "SMS_SENT";
                sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);

                PendingIntent deliveredPI;
                String DELIVERED = "SMS_DELIVERED";
                deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo, null, sms, sentPI, deliveredPI);
                Toast.makeText(getApplicationContext(), "Test SMS Sent!",
                        Toast.LENGTH_LONG).show();
                Log.i("com.example.neusoft", "Test SMS Sent!!");
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "Test SMS failed, please try again later!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
            Log.i("com.example.neusoft", "Test SMS failed");
        }

    }
}
