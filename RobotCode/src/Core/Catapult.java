/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import ModClasses.MyJoystick;
import Utils.Config;
import edu.wpi.first.wpilibj.Timer;
import ModClasses.MyTalon;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Activates a motor that loads and launches the catapult.
 *
 * @author Seraj B. and Warren E.
 */
public class Catapult {

    public MyTalon catMotor = new MyTalon(Config.Catapult.chnCat);
    Timer preFireTimer;
    Timer fireTimer;
    MyJoystick joy;
    DigitalInput limitSwitch;
    Pickup pickup = new Pickup();

    /**
     * Constructs timers for the loading time and firing time.
     */
    public Catapult() {
        this.limitSwitch = new DigitalInput(Utils.Config.Catapult.chnLS);
    }
    
    /**
     * Decides if the button to cock or fire the catapult is pressed then do 
     * the correct action
     */
    public void run () {
        if (pickup.ready){
            cock();
        }
        if (joy.gotPressed(Config.Catapult.catFireButton)){
            fire();
        }
        
        
    }
    
    public void cock(){
        catMotor.set(Config.Catapult.cockSpeed);
        if (limitSwitch.get() == true) {
            catMotor.set(0);
        }
    } 
    
    public void fire() {
        preFireTimer.start();
        catMotor.set(Config.Catapult.preFireSpeed);
        
        if (preFireTimer.get() > Config.Catapult.preFireTime) {
            catMotor.set(Config.Catapult.fireSpeed);
            preFireTimer.stop();
            preFireTimer.reset();
            fireTimer.start();
        }
        if(fireTimer.get() > Config.Catapult.fireTime){
            catMotor.set(0);
        }
       
    }
}
