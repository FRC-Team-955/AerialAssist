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
    MyJoystick joy;
    boolean slowModeActive = false;
    
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
            slowModeActive = !slowModeActive;
        
        if(joy.getButton(Config.Joystick.btSwitchDriveDir))
            joy.flipSwitch(Config.Joystick.btSwitchDriveDir);
		
        double x = joy.getX();
        double y = joy.getY();

        if(joy.getSwitch(Config.Joystick.btSwitchDriveDir))
            y = -y;
        
        x *= Math.abs(x);
        y *= Math.abs(y);  
        
        double left = y + x;
        double right = y - x;
        
        if(slowModeActive)
        {
            left *= 0.5;
            right *= 0.5;
        }
        
        setSpeed(left, right);
        
        Station.print(Config.Station.drive, "Is Forward Flipped: " + joy.getSwitch(Config.Joystick.btSwitchDriveDir));
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