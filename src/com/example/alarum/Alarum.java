package com.example.alarum;


/**
 * Write a description of class Alarum here.
 * 
 * @author (Lorraine Lee) 
 * @version (0)
 */

import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;

import android.app.AlarmManager;

public class Alarum
{
    // instance variables
    private final long DEFAULT_ADVANCE = 1000*3600*12; // 12 hours
    private Date lowerBound;
    private Date displayTime;
    private Date upperBound;
    private boolean leftPressed = false;
    private AlarmManager alarmManager;
    /**
     * Constructor for objects of class Alarum
     */
    public Alarum()
    {
        // initialise instance variables
        this.lowerBound = new Date();
        this.displayTime = new Date(lowerBound.getTime()+DEFAULT_ADVANCE);
        this.upperBound = new Date(displayTime.getTime()+DEFAULT_ADVANCE);
    }

    /**
     * Chronological midpoint of two Date objects.
     * @param t1 A Date object.
     * @param t2 Another Date object.
     * @return a Date object representing a time halfway from t1 to t2.
     */
    private Date chronologicalMidpoint(Date t1, Date t2) {
        return new Date((t1.getTime()+t2.getTime())/2);
    }

    /**
     * Replaces the lower bound with the display time,
     * and the display time with the moment halfway between
     * the new lower bound and the upper bound.
     */
    public void leftPress()
    {
        // put your code here
        this.leftPressed = true;
        this.upperBound.setTime(this.displayTime.getTime());
        // interpolate between lower bound and display time
        this.displayTime = chronologicalMidpoint(this.lowerBound, this.displayTime);
    }

    /**
     * Replaces the upper bound with the display time,
     * and the display time with the moment halfway between
     * the new upper bound and the lower bound.
     */
    public void rightPress()
    {
        if (!leftPressed) {
            // Double the gap between the bounds by advancing the upper bound
            this.upperBound.setTime(2*this.upperBound.getTime()-this.lowerBound.getTime());
            // interpolate between display time and upper bound
            this.displayTime = chronologicalMidpoint(this.lowerBound, this.upperBound);
        }
        else {
            this.lowerBound.setTime(this.displayTime.getTime());
            this.displayTime = chronologicalMidpoint(this.displayTime, this.upperBound);
        }
    }
    
    /**
     * Get the instance's display time.
     * @return the epoch millisecond representing the Alarum's display time.
     */
    public long getDisplayTime() {
        return this.displayTime.getTime();
    }
    
    public String toString() {
        // h/t http://javatechniques.com/blog/dateformat-and-simpledateformat-examples/
        return DateFormat.getDateTimeInstance().format(this.displayTime);
    }
    
    public String getDay() {
    	// return (new Calendar()).get(Calendar.DAY_OF_WEEK);
    	SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
    	
    	return sdf.format(displayTime, new StringBuffer(40), new FieldPosition(0)).toString();
    }
    
    public boolean done() {
    	return Math.abs(this.lowerBound.getTime() - this.upperBound.getTime())<1000;
    }
    
    public void makeDone() {
    	// Stick a fork in it
    	
    	this.lowerBound = this.upperBound = this.displayTime;
    	// Since lowerBound==upperBound, done() will return true.
    }
    
    public void setAlarmManager(AlarmManager alarmManager) {
    	this.alarmManager = alarmManager;
    }
    
    public AlarmManager getAlarmManager() {
    	return this.alarmManager;
    }
}
