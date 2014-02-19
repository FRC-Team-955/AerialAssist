package Core;

import ModClasses.MyTalon;
import ModClasses.MyJoystick;
import ModClasses.Station;
import Utils.Config;

/**
 * Controls the motors on the wheels of the bot.
 * @author Matthew S.
 */
public class Drive 
{
    private MyTalon mtLeft1 = new MyTalon(Config.Drive.chnLeft1);
    private MyTalon mtLeft2 = new MyTalon(Config.Drive.chnLeft2);
    private MyTalon mtLeft3 = new MyTalon(Config.Drive.chnLeft3);
    private MyTalon mtRight1 = new MyTalon(Config.Drive.chnRight1);
    private MyTalon mtRight2 = new MyTalon(Config.Drive.chnRight2);
    private MyTalon mtRight3 = new MyTalon(Config.Drive.chnRight3);
    private boolean isSlowMode = false;
    private boolean isDriveFlipped = false;
    MyJoystick joy;
    
    /**
     * Initializes the joystick
     * @param newJoy Name of the joystick
     */
    public Drive(MyJoystick newJoy)
    {
        joy = newJoy;
    }
    
    /**
     * Sets the motors to be controlled by the joystick.
     */
    public void run()
    {
        if(joy.getButton(Config.Joystick.btSlowMode))
            isSlowMode = !isSlowMode;
        
        if(joy.getButton(Config.Joystick.btFlipDriveDir))
            isDriveFlipped = !isDriveFlipped;
		
        double x = joy.getX() * Math.abs(joy.getX()) * 0.75;
        double y = joy.getY() * Math.abs(joy.getY());
        
        if(isDriveFlipped)
            y = -y;  
        
        double left = y + x;
        double right = y - x;
        
        if(isSlowMode)
        {
            left *= 0.5;
            right *= 0.5;
        }
        
        setSpeed(left, right);
        
        Station.print(Config.Station.driveFlipped, "Front Flipped: " + isDriveFlipped);
        Station.print(Config.Station.driveSlowMode, "Slow Mode: " + isSlowMode);
    }
    
    /**
    * Makes both sides go at the same speed.
    * @param speedLeft Speed of left talons.
    * @param speedRight Speed of right talons.
    */
    public void setSpeed(double speedLeft, double speedRight) 
    {
        speedLeft = -speedLeft;
        
        mtLeft1.ramp(speedLeft);
        mtLeft2.ramp(speedLeft);
        mtLeft3.ramp(speedLeft);
        
        mtRight1.ramp(speedRight);
        mtRight2.ramp(speedRight);
        mtRight3.ramp(speedRight);
    }
}