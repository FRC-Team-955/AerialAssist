package Core;

import ModClasses.MyJoystick;
import ModClasses.MyTalon;
import ModClasses.Station;
import Utils.Config;
import Sensors.LimitSwitch;

/**
 * Activates a motor that loads and launches the catapultManualFire.
 *
 * @author Seraj B. and Warren E.
 */
public class Catapult 
{
    private MyTalon mtCat1 = new MyTalon(Config.Catapult.chnCat1);
    private MyTalon mtCat2 = new MyTalon(Config.Catapult.chnCat2);
    private LimitSwitch limitSwitch = new LimitSwitch(Utils.Config.Catapult.chnLimitSwitch, true);
    private double catSpeed = 0.0;
    private boolean isManualFireMode = false;
    private MyJoystick joy;

    public Catapult(MyJoystick newJoy) 
    {
        joy = newJoy;
    }
    
    /**
     * Runs the catapult motor when the button is pushed
     */
    public void run()
    {
        if(joy.getButton(Config.Joystick.btManualFire))
            isManualFireMode = true;
        
        if(joy.getButton(Config.Joystick.btAutoFire))
            isManualFireMode = false;
        
        if(isManualFireMode) // Move cat on button hold, stop otherwise
        {
            catSpeed = 0;
            
            if(joy.getRawButton(Config.Joystick.btFireCatapult))
                catSpeed = Config.Catapult.manualSpeed;
        }    
        
        else // Move cat on button hold/press, stops "cocks" by its self
        {
            if(isCocked() || joy.getButton(Config.Joystick.btStopCatapult))
                catSpeed = 0;
			
			/*
                We're using getRawButton instead of getButton because if we set
                the motor speed once by using getButton the limit switch  still
                might be being activated since the catapultManualFire hasn't moved that 
                much since the last loop where button was pressed.
            */
            
            if(joy.getRawButton(Config.Joystick.btFireCatapult))
                catSpeed = Config.Catapult.fireSpeed;
        }
        
        mtCat1.set(catSpeed);
        mtCat2.set(catSpeed);
        
        Station.print(Config.Station.catapultManualFire, "Manual Fire: " + isManualFireMode);
		
		System.out.println(limitSwitch.get());
    }
    
    /**
     * Fires the catapult motor to fire speed
     */
    public void fire()
    {
        mtCat1.set(Config.Catapult.fireSpeed);
        mtCat2.set(Config.Catapult.fireSpeed);
    }
    
    /**
     * Stops the catapult motors
     */
    public void stop()
    {
        mtCat1.set(0);
        mtCat2.set(0);
    }
    
    /**
     * Checks if the catapult is cocked based off limitswitch, true = cocked
     * @return 
     */
    public boolean isCocked()
    {
        return limitSwitch.get();
    }
	
	/**
	 * Sets the catapult motors
	 * @param speed 
	 */
	public void testCatapultOne(int speed)
	{
		mtCat1.set(speed);
	}
	
	public void testCatapultTwo(int speed)
	{
		mtCat2.set(speed);
	}
}