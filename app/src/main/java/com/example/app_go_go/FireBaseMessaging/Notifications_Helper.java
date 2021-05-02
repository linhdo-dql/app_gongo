package com.example.app_go_go.FireBaseMessaging;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.app_go_go.Activities.ChildActivities.StatusDetail;
import com.example.app_go_go.Activities.MainActivity;
import com.example.app_go_go.R;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.app_go_go.Activities.MainActivity.CHANEL_DESC;
import static com.example.app_go_go.Activities.MainActivity.CHANEL_ID;
import static com.example.app_go_go.Activities.MainActivity.CHANEL_NAME;

public class Notifications_Helper {

    public static void sendNotify_Like(Context context1, String title, String text, String id_stt, String acc_name) {
    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(context1, CHANEL_ID)
            .setSmallIcon(R.drawable.imagelogoo)
            .setContentTitle(title)
            .setContentText(text)
            .setSound(defaultSoundUri)
            .setPriority(NotificationCompat.PRIORITY_MAX);
            Intent intent1;
            if(id_stt.equals("stt"))
            {
                intent1 = new Intent(context1, StatusDetail.class);
            }
            else {
                intent1 = new Intent(context1, StatusDetail.class);
            }
        intent1.putExtra("type", id_stt);
        intent1.putExtra("id_stt", acc_name);
        PendingIntent contentIntent = PendingIntent.getActivity(context1, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        mbuilder.setContentIntent(contentIntent);
        NotificationManager notificationManager = (NotificationManager) context1.getSystemService(NOTIFICATION_SERVICE);
        int id = (int) System.currentTimeMillis();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel("like", "Yêu thích", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Yêu thích bài viết");
            NotificationManager manager = context1.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(id, mbuilder.build());
    }
}
