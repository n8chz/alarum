// h/t http://android-er.blogspot.com/2010/10/simple-example-of-alarm-service-using.html
package com.example.alarum;



import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;



public class MyAlarmService extends Service {



@Override

public void onCreate() {

// TODO Auto-generated method stub

	Toast.makeText(this, "MyAlarmService.onCreate()", Toast.LENGTH_LONG).show();

}



@Override

public IBinder onBind(Intent intent) {

// TODO Auto-generated method stub

	Toast.makeText(this, "MyAlarmService.onBind()", Toast.LENGTH_LONG).show();

	return null;

}



@Override

public void onDestroy() {

// TODO Auto-generated method stub

	super.onDestroy();

	Toast.makeText(this, "MyAlarmService.onDestroy()", Toast.LENGTH_LONG).show();

}



@Override

public void onStart(Intent intent, int startId) {

// TODO Auto-generated method stub

	super.onStart(intent, startId);

	// Toast.makeText(this, "MyAlarmService.onStart()", Toast.LENGTH_LONG).show();
	Uri alertUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
	// h/t http://stackoverflow.com/a/5687023/1269964
    if(alertUri == null){
        // alert is null, using backup
        alertUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if(alertUri == null){  // I can't see this ever being null (as always have a default notification) but just incase
            // alert backup is null, using 2nd backup
            alertUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);               
        }
    }
    // if (getApplicationContext() == null) System.out.println("ApplicationContext is null");
    // h/t http://stackoverflow.com/a/8568304/1269964
	Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), alertUri);
	if (r != null) r.play(); else System.out.println("r is null");

	// .popIt("Alarum", "Your Alarum time has arrived!");


}



@Override

public boolean onUnbind(Intent intent) {

// TODO Auto-generated method stub

	Toast.makeText(this, "MyAlarmService.onUnbind()", Toast.LENGTH_LONG).show();

	return super.onUnbind(intent);

}




}