/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;
import ModClasses.*;
import edu.wpi.first.wpilibj.Timer;

import Utils.*;
/**
 *
 * @author raiderbot-4
 */
public class Pickup {
    MySolenoid solenoidLeft = new MySolenoid(Config.Pickup.solLeftPortOne,Config.Pickup.solLeftPortTwo);
    MySolenoid solenoidRight = new MySolenoid(Config.Pickup.solRightPortOne,Config.Pickup.solRightPortTwo);
    MyTalon pickupTalon = new MyTalon(Config.Pickup.pickupTalon1);
    MyJoystick joy = new MyJoystick(Config.Joystick.chn);
    Timer timer = new Timer();
    boolean ready = false;
    /**
     * runs when pickup is activated
     */
    public void run() {
        //Turns on motor for a certain time
        if(joy.gotPressed(Config.Pickup.button)){
            solenoidLeft.solenoidSwitch();
            solenoidRight.solenoidSwitch();
            pickupTalon.set(Config.Pickup.pickupSpeed);
            timer.start();
        }

            //Once time is up turn off
        if(timer.get() > Config.Pickup.pickupTime) {
            solenoidLeft.solenoidSwitch();
            solenoidRight.solenoidSwitch();
            pickupTalon.set(0);
            ready = true;
            timer.stop();
            timer.reset();
        }
        
    }
}
