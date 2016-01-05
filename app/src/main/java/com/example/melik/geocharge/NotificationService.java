package com.example.melik.geocharge;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;

/**
 * Created by Melik on 04/01/2016.
 */
public class NotificationService extends Service {
    private int ID_NOTIFICATION =1;
    private BroadcastReceiver batteryChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
           createNotification();
        }
    };

    @Override
    public void onCreate() {
        registerReceiver(batteryChangeReceiver, new IntentFilter(Intent.ACTION_BATTERY_LOW));
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(batteryChangeReceiver);
        deleteNotification();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private  void createNotification(){
        Intent notificationIntent=new Intent(this,MapsActivity.class); //quand on appui dessus
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        PendingIntent contentIntent = PendingIntent.getService(this, 0, notificationIntent, 0);

        Notification notification=new Notification.Builder(this) //crée la notification
                .setWhen(System.currentTimeMillis())
                .setTicker("Géocharge")
                .setContentTitle("GéoCharge Alerte")
                .setSmallIcon(R.drawable.ic_stat_device_battery_alert)
                .setContentIntent(contentIntent)
                .setContentText("Votre batterie est faible, pensez à rechargez votre appareil à l'aide d'une borne près de vous !")
                .setStyle(new Notification.BigTextStyle().bigText("Votre batterie est faible, pensez à rechargez votre appareil à l'aide d'une borne près de vous !"))
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .build();

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(ID_NOTIFICATION, notification);
    }

    private void deleteNotification(){
        final NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        //la suppression de la notification se fait grâce a son ID
        notificationManager.cancel(ID_NOTIFICATION);
    }
}
