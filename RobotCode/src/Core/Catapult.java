package Core;

import ModClasses.MyJoystick;
import ModClasses.MyTalon;
import ModClasses.Station;
import Utils.Config;
import Sensor.LimitSwitch;

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
    
    public void run()
    {
        if(limitSwitch.get() || joy.getButton(Config.Joystick.btStopCatapult))
            catSpeed = 0;
        
        if(joy.getRawButton(Config.Joystick.btFireCatapult))
            catSpeed = Config.Catapult.fireSpeed;
        
        mtCat1.set(catSpeed);
        mtCat2.set(catSpeed);
        
        Station.print(Config.Station.catapult, "Cat Speed: " + mtCat1.get() + " - " + mtCat2);
    }
}