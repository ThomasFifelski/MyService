package com.delta.myservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;



/**
 * Created by Vic on 13/9/10.
 */
public class MyService extends Service {
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
          //  Stage 2: Replace this section of the handler with an actual notification to the
          // notification bar.  Use any resources you need
          // (for instance your past notification project) for assistance in performing this task.
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
            Notification.Builder mBuilder = new Notification.Builder(MyService.this);
            mBuilder.setContentText("listen up");
            mBuilder.setContentTitle("this is your last warning");
            mBuilder.setSmallIcon(R.drawable.thug_mm);



//            Intent resultIntent = new Intent(this, MainActivity.class);
            PendingIntent resultPendingIntent = null;
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(0, mBuilder.build());

//            long endTime = System.currentTimeMillis() + 5*1000;
//            while (System.currentTimeMillis() < endTime) {
//                synchronized (this) {
//                    try {
//                        wait(endTime - System.currentTimeMillis());
//                    } catch (Exception e) {
//                    }
//                }
//            }
            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread hT = new HandlerThread("name",Thread.MIN_PRIORITY);{
//
//            Intent intent = new Intent(this, MyService.class);
//            startService(intent);




            hT.start();



            Looper myLooper = hT.getLooper() ;
        mServiceHandler = new ServiceHandler(myLooper);

        }

        /* todo:
         * Create a HandlerThread with 2 parameters

         *
         * - Get the Looper from the thread
         * - Use the Looper from the previous step to get a new ServiceHandler
         * - You will need the ServiceHandler from the previous step in other functions!
         * */
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
           Message msa = mServiceHandler.obtainMessage();
           msa.arg1 = startId;
         //send message
           mServiceHandler.sendMessage(msa);



        /*
        * todo:
        *
        * - Obtain the Message object from the service handler
        * - Assign the startID to the Message's first argument
        * */

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }
}
