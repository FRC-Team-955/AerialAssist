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
    MySolenoid solenoidRight = new MySolenoid(Config.Pickup.solRightPortOne,Config.Pickup.solRightPortTwo);
   private MyTalon pickupTalon = new MyTalon(Config.Pickup.pickupTalon1);
  private  MyJoystick joy;
   private Timer timer = new Timer();
   private boolean ready = false;
    
    public Pickup(MyJoystick joy) {
        this.joy = joy;
        solenoidRight.on();
    }

    /**
     * runs when pickup is activated
     */    
    public void run() {
        //Turns on motor for a certain time
        if(joy.gotPressed(Config.Pickup.button)){
            solenoidRight.off();
            pickupTalon.set(Config.Pickup.pickupSpeed);
            timer.start();
        }

            //Once time is up turn off
        if(timer.get() > Config.Pickup.pickupTime) {
            solenoidRight.on();
            pickupTalon.set(0);
            ready = true;
            timer.stop();
            timer.reset();
        }
	}
        
	public void runMotor(double speed){
		pickupTalon.set(speed);
	}
	
		
    
}
