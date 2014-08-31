package com.example.alarum;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
// import android.content.Context;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ArrayList<Alarum> alara = new ArrayList<Alarum>();
	private Alarum currentAlarum;
	private PendingIntent pendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Button leftButton = (Button) findViewById(R.id.leftButton);
		if (leftButton == null) System.out.println("null?");
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		this.currentAlarum = new Alarum();
		alara.add(currentAlarum);
		setContentView(R.layout.activity_main);
		this.updateDisplay();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    /**
     * Updates the text elements of the activity to represent the current
     * Alarum's display time.
     */
	public void updateDisplay() {
		((TextView) findViewById(R.id.displayTime)).setText(currentAlarum.toString());
		((TextView) findViewById(R.id.displayDayOfWeek)).setText(currentAlarum.getDay());
	}
	
    /**
     * Re-interpolates the upper and lower bounds of the activity's
     * current Alarum.  If the left button is pressed, the upper bound
     * is replaced with the display time.  If the right button is
     * pressed, the lower bound is replaced with the display time.
     * @param the View object representing whichever button was pressed.
     */
	public void buttonPress(View view) {
		if (view.getId() == R.id.leftButton) {
			currentAlarum.leftPress();
		}
		else {
			currentAlarum.rightPress();
		}
		updateDisplay();
		setAlarumIfDone(view);
	}

	/*
	public void longPress(View view) {
		currentAlarum.makeDone();
		setAlarumIfDone(view);
	}
	*/
	
    /**
     * Tests to see if the current Alarum is finished being set.
     * If so, creates an AlarmManager and accompanying Intent.
     * @param the View that triggered the change.
     */

	public void setAlarumIfDone(View view) {
		if (currentAlarum.done()) {
			// signal termination of interpolation with a green screen
			view.getRootView().setBackgroundColor(Color.rgb(0xd6, 0xec, 0x86));

			// h/t http://android-er.blogspot.com/2010/10/simple-example-of-alarm-service-using.html
			Intent myIntent = new Intent(MainActivity.this, MyAlarmService.class);
			// System.out.println("myIntent.getAction() -> "+myIntent.getAction());
			pendingIntent = PendingIntent.getService(MainActivity.this, 0, myIntent, 0);
			AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
			alarmManager.set(AlarmManager.RTC_WAKEUP, currentAlarum.getDisplayTime(), pendingIntent);
			currentAlarum.setAlarmManager(alarmManager);
			Toast.makeText(MainActivity.this, "Start Alarm", Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * Create and show a PopupDialog
	 * @param title Title for new PopupDialog
	 * @param message Message in new PopupDialog
	 */
	public void popIt( String title, String message ) {
	    new AlertDialog.Builder(this)
	    .setTitle( title )
	    .setMessage( message )
	    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface arg0, int arg1) {
	            //do stuff onclick of YES
	            // finish();
	        }
	    })
	    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface arg0, int arg1) {
	            //do stuff onclick of CANCEL
	            Toast.makeText(getBaseContext(), "You touched CANCEL", Toast.LENGTH_SHORT).show();
	        }
	    }).show();
	}


}
