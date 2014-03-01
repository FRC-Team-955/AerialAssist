package Core;

import ModClasses.MySolenoid;
import ModClasses.MyJoystick;
import ModClasses.MyTalon;
import ModClasses.Station;
import Utils.Config;

/**
 *
 * @author raiderbot-4
 */
public class Pickup 
{
    private MySolenoid solPickup = new MySolenoid(Config.Pickup.solPortOne, Config.Pickup.solPortTwo);
    private MyTalon mtPickup = new MyTalon(Config.Pickup.chnPickUpMotor1);
    private double pickupSpeed = 0;
    private MyJoystick joy;

    public Pickup(MyJoystick newJoy) 
    {
        joy = newJoy;
    }

    /**
     * Runs when pickupSpeed is activated
     */
    public void run() 
    {
        // Setting pickupSpeed speed to inward/outward if pressed
        // Outward
        if(joy.getButton(Config.Joystick.btPickupOutward))
            pickupSpeed = -Config.Pickup.motorSpeed;
        
        // Inward
        if(joy.getButton(Config.Joystick.btPickupInward))
            pickupSpeed = Config.Pickup.motorSpeed;
        
        // Determine whether the pick up should be on
        if(joy.getButton(Config.Joystick.btStopPickupMotor))
          pickupSpeed = 0;
        
        // Flips the pickup solenoids when pressed
        if(joy.getButton(Config.Joystick.btMovePickupSols))
        {
            solPickup.flip();
            
            if(isUp()) // If pickup is up, stop motorz
                pickupSpeed = 0;
        }
        
        mtPickup.ramp(pickupSpeed);
        
        Station.print(Config.Station.pickupSpeed, "Pickup: " + mtPickup.get());
    }
    
    /**
     * Checks if the pickup is up, true means it is up
     * @return 
     */
    private boolean isUp()
    {
        // False means pickup is up
        return !solPickup.get();
    }
    
    /**
     * Puts the pickup solenoids up
     */
    public void up()
    {
        solPickup.set(false);
    }
    
    /**
     * Puts the pickup solenoids down
     */
    public void down()
    {
        solPickup.set(true);
    }
    
    /**
     * Runs the pickup motors inward
     */
    public void inward()
    {
        mtPickup.set(Config.Pickup.motorSpeed);
    }
    
    /**
     * Runs the pickup motors outward
     */
    public void outward()
    {
        mtPickup.set(-Config.Pickup.motorSpeed);
    }
    
    /**
     * Stops the pickup motors
     */
    public void stop()
    {
        mtPickup.set(0);
    }
}