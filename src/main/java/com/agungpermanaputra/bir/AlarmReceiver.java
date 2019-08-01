package com.agungpermanaputra.bir;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context arg0, Intent arg1) {
        String nama = arg1.getStringExtra("name");
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(arg0, notification);
        r.play();
        NotificationCompat.Builder mBuilder =   new NotificationCompat.Builder(arg0)
                .setSmallIcon(R.drawable.logo) // notification icon
                .setContentTitle("Baby Immunization Reminder") // title for notification
                .setContentText("Ayo Bunda sekarang waktunya imunisasi " + nama) // message for notification
                .setAutoCancel(true); // clear notification after click
        Intent intent = new Intent(arg0, alarm.class);
        PendingIntent pi = PendingIntent.getActivity(arg0,0,intent,Intent.FILL_IN_ACTION);
        mBuilder.setContentIntent(pi);
        NotificationManager mNotificationManager =
                (NotificationManager) arg0.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());

    }

}
