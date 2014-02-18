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
    private MySolenoid solPickup = new MySolenoid(Config.Pickup.solRightPortOne, Config.Pickup.solRightPortTwo);
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
        
        mtPickup.ramp(pickupSpeed);
        
        // Flips the pickupSpeed solenoids when pressed
        if(joy.getButton(Config.Joystick.btMovePickupSols))
        {
            solPickup.flip();
            
            if(!solPickup.get()) // If solPick is false then stop pickupSpeed motorz
                pickupSpeed = 0;
        }
        
        Station.print(Config.Station.pickupSpeed, "Pickup: " + mtPickup.get());
    }
    
    public void setPickup(boolean newState)
    {
        solPickup.set(newState);
    }
}