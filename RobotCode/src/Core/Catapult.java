package Core;

import ModClasses.MyJoystick;
import ModClasses.MyTalon;
import ModClasses.Station;
import Utils.Config;
import Sensors.LimitSwitch;

/**
 * Activates a motor that loads and launches the catapult.
 *
 * @author Seraj B. and Warren E.
 */
public class Catapult 
{
    private MyTalon mtCat1 = new MyTalon(Config.Catapult.chnCat1);
    private MyTalon mtCat2 = new MyTalon(Config.Catapult.chnCat2);
    private LimitSwitch limitSwitch = new LimitSwitch(Utils.Config.Catapult.chnLimitSwitch, true);
    private double catSpeed = 0.0;
    private MyJoystick joy;

    public Catapult(MyJoystick newJoy) 
    {
        joy = newJoy;
    }
    
    /**
     * Checks if the button to shoot the cat is pressed, and then launches it, automatically cocking it
     */
    public void run()
    {
        if(joy.getButton(Config.Joystick.btManualFire))
            joy.setSwitch(Config.Joystick.btManualFire, true);
        
        if(joy.getButton(Config.Joystick.btAutoFire))
            joy.setSwitch(Config.Joystick.btManualFire, false);
        
        if(joy.getSwitch(Config.Joystick.btManualFire)) // Move cat on button hold, stop otherwise
        {
            catSpeed = 0;
            
            if(joy.getRawButton(Config.Joystick.btFireCatapult))
                catSpeed = Config.Catapult.manualSpeed;
        }    
        
        else // Move cat on button hold/press, stops "cocks" by its self
        {
            if(limitSwitch.get() || joy.getButton(Config.Joystick.btStopCatapult))
                catSpeed = 0;
            
            /*
                We're using getRawButton instead of getButton because if we set
                the motor speed once by using getButton the limit switch  still
                might be being activated since the catapult hasn't moved that 
                much since the last loop where button was pressed.
            */
            
            if(joy.getRawButton(Config.Joystick.btFireCatapult))
                catSpeed = Config.Catapult.fireSpeed;
        }
        
        mtCat1.set(catSpeed);
        mtCat2.set(catSpeed);
        
        Station.print(Config.Station.catapult, "Manual Fire: " + joy.getSwitch(Config.Joystick.btManualFire));
    }
    
    /**
     * Sets the cat to a specific speed
     * @param speed 
     */
    public void setCatMotor(double speed)
    {
        if(speed < 0)
        {
            System.out.println("NOPE");
            return;
        }
        
        else
        {
            mtCat1.set(speed);
            mtCat2.set(speed);
        }
    }
}