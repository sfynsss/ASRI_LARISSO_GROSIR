package com.asri.larissogrosirbaru.Activity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.asri.larissogrosirbaru.Activity.grosir.R;
import com.asri.larissogrosirbaru.Activity.grosir.frm_notification;

public class NotificationHelper {

    public static void displatNotification(Context context, String title, String body, String action) {

        Intent intent;
        if (TextUtils.isEmpty(action)) {
            intent = new Intent(context, frm_notification.class);
        } else {
            intent = new Intent(action);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                100,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, "LARISSO GROUP")
                        .setSmallIcon(R.drawable.logo_larisso1)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notifManager = NotificationManagerCompat.from(context);
        notifManager.notify(1, mBuilder.build());

    }

}
