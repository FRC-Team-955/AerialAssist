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
     * Runs when pickup is activated
     */
    public void run() 
    {
        // Setting pickup speed to inward/outward if pressed
        // Inward
        if(joy.getButton(Config.Joystick.btPickupInward))
            pickupSpeed = -Config.Pickup.motorSpeed;
        
        // Outward
        if(joy.getButton(Config.Joystick.btPickupOutward))
            pickupSpeed = Config.Pickup.motorSpeed;
        
        // Determine whether the pick up should be on
        if(joy.getButton(Config.Joystick.btRunPickupMotor))
          pickupSpeed = 0;
        
        mtPickup.set(pickupSpeed);
        
        // Flips the pickup solenoids when pressed
        if(joy.getButton(Config.Joystick.btMovePickup))
            solPickup.flip();
        
        Station.print(Config.Station.pickup, "Inward: " + joy.getSwitch(Config.Joystick.btPickupInward));
    }
    
    public void setPickup(boolean newState)
    {
        solPickup.set(newState);
    }
}

///**
//     * Runs when pickup is activated
//     */
//    public void run() 
//    {
//        // Setting pickup speed to inward/outward if pressed
//        
//        // Inward
//        if(joy.getButton(Config.Joystick.btPickupInward))
//            joy.flipSwitch(Config.Joystick.btPickupInward);
//        
//        // Outward
//        if(joy.getButton(Config.Joystick.btPickupOutward))
//            joy.flipSwitch(Config.Joystick.btPickupOutward);
//        
//        // Change pickup speed
//        if(joy.getSwitch(Config.Joystick.btPickupInward))
//            pickupSpeed = -Config.Pickup.motorSpeed;
//        
//        if(joy.getSwitch(Config.Joystick.btPickupOutward))
//            pickupSpeed = Config.Pickup.motorSpeed;
//        
//        // Determine whether the pick up should be on
//        if(joy.getButton(Config.Joystick.btRunPickupMotor))
//            joy.flipSwitch(Config.Joystick.btRunPickupMotor);
//        
//        if(joy.getSwitch(Config.Joystick.btRunPickupMotor))
//          pickupSpeed = 0;
//        
//        mtPickup.set(pickupSpeed);
//        
//        // Flips the pickup solenoids when pressed
//        if(joy.getButton(Config.Joystick.btMovePickup))
//            solPickup.flip();
//        
//        Station.print(Config.Station.pickup, "Inward: " + joy.getSwitch(Config.Joystick.btPickupInward));
//    }

//public void run() 
//    {
//        // Setting pickup speed to inward/outward if pressed
//        // Inward
//        if(joy.getButton(Config.Joystick.btPickupInward))
//            pickupSpeed = -Config.Pickup.motorSpeed;
//        
//        // Outward
//        if(joy.getButton(Config.Joystick.btPickupOutward))
//            pickupSpeed = Config.Pickup.motorSpeed;
//        
//        // Determine whether the pick up should be on
//        if(joy.getButton(Config.Joystick.btRunPickupMotor))
//            joy.flipSwitch(Config.Joystick.btRunPickupMotor);
//        
//        if(joy.getSwitch(Config.Joystick.btRunPickupMotor))
//          pickupSpeed = 0;
//        
//        mtPickup.set(pickupSpeed);
//        
//        // Flips the pickup solenoids when pressed
//        if(joy.getButton(Config.Joystick.btMovePickup))
//            solPickup.flip();
//        
//        Station.print(Config.Station.pickup, "Inward: " + joy.getSwitch(Config.Joystick.btPickupInward));
//    }