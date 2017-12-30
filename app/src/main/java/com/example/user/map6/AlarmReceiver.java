package com.example.user.map6;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.app.PendingIntent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 0;
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        Intent myIntent = new Intent(context, AlarmReceiver.class); //what happens when u click on notfi?
        PendingIntent pd = PendingIntent.getActivity(context, NOTIFICATION_ID, myIntent, 0);

//        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pd);
        NotificationCompat.Builder myNoti = new NotificationCompat.Builder(context);
        // Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Uri alarmSound = Uri.parse("android.resource://" +
                context.getPackageName() +"/" + R.raw.alarm);
//        long[] vibrate = { 0, 100, 200, 300 };
//        myNoti.vibrate = vibrate;
        myNoti.setSound(alarmSound);
        myNoti.setContentTitle("WAKE UP!");
        myNoti.setContentText("You have almost reached the destination");
        // myNoti.setSound(alarmSound);
        myNoti.setSmallIcon(android.R.drawable.btn_star_big_on);
        myNoti.setContentIntent(pd);
        myNoti.setAutoCancel(true); //click and erase
        myNoti.setPriority(2);
        nm.notify(1,myNoti.build());

    }


}
