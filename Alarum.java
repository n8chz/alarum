 


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

public class Alarum
{
    // instance variables - replace the example below with your own
    private final long DEFAULT_ADVANCE = 1000*3600*12; // 12 hours
    private Date lowerBound;
    private Date displayTime;
    private Date upperBound;
    private boolean leftPressed = false;
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

    private Date chronologicalMidpoint(Date t1, Date t2) {
        return new Date((t1.getTime()+t2.getTime())/2);
    }

    /**
     * An example of a method - replace this comment with your own
     * 
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
     * An example of a method - replace this comment with your own
     * 
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
}
