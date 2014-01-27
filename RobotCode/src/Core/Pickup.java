/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;
import ModClasses.MyTalon;
import edu.wpi.first.wpilibj.Timer;

import Utils.*;
/**
 *
 * @author raiderbot-4
 */
public class Pickup {
    MyTalon pickupTalon = new MyTalon(Config.Pickup.pickupTalon1);
    Main main = new Main();
    
    /**
     * runs when pickup is activated
     */
    public void run() {
        //Turns on motor for a certain time
        pickupTalon.set(Config.Pickup.pickupSpeed);
        double startTime = main.timer.get();
        pickupTalon.set(1);
        
        //Once time is up turn off
        if(main.timer.get() > Config.Pickup.pickupTime) {
            pickupTalon.set(0);
        }
       
    }
}
