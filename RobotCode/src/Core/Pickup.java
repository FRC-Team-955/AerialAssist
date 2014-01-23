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
    Timer timer = new Timer();
    
    public void run() {
        timer.start();
        pickupTalon.set(Config.Pickup.pickupSpeed);
        
        if(timer.get() > Config.Pickup.pickupTime) {
            pickupTalon.set(0);
        }
       
    }
}
