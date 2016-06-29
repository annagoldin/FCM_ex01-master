package com.abs.samih.fcm_ex01;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class MySmsReceiver extends BroadcastReceiver {
    public MySmsReceiver() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Toast.makeText(context, "hii sms", Toast.LENGTH_LONG).show();
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"))
        {
            Bundle bundle = intent.getExtras();
            Object[] pdus=(Object[])bundle.get("pdus");
            String smsInfo="";
            String inPhoneNum="";
            Date date= Calendar.getInstance().getTime();
            for (int i = 0; i < pdus.length; i++) {
                SmsMessage smsMsg=SmsMessage.createFromPdu((byte[])pdus[i]);
                inPhoneNum = smsMsg.getDisplayOriginatingAddress();
                smsInfo+=smsMsg.getDisplayMessageBody()+",";


            }
            if (FirebaseDatabase.getInstance()!=null) {
                MyTask myTask=new MyTask(smsInfo,false,date,1,"",inPhoneNum);

                //6 save
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
                //
                String email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
                //all my task will be under my mail under the root MyTasks
                //child can not contain chars: $,#,[,], . ,.....
                reference.child(email.replace(".","_")).child("MyTasks").push().setValue(myTask, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError==null)
                        {
                            Toast.makeText(context,"Save successful",
                                    Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(context,"Save Failed"+databaseError.getMessage(),
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
            //auto sms response
            if(smsInfo.contains("hello")){

                SmsManager manager = SmsManager.getDefault();
                manager.sendTextMessage(inPhoneNum, null, "welcome", null, null);
            }
        }
    }
    private void makeNotification(Context context){

        NotificationCompat.Builder builder = (NotificationCompat.Builder)new NotificationCompat.Builder(context).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Task Manager").setContentText("new sms added to your tasks");
        Intent resIntent = new Intent(context, MngTaskActivity.class);
        PendingIntent resPendingIntent = PendingIntent.getActivity(context, 0, resIntent, PendingIntent.FLAG_UPDATE_CURRENT );
    }
}
