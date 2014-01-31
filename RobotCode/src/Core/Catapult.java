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

/**
 * Activates a motor that loads and launches the catapult.
 *
 * @author Seraj B. and Warren E.
 */
public class Catapult {

    public MyTalon catMotor = new MyTalon(Config.Catapult.chnCat);
    Timer loadTimer;
    Timer fireTimer;
    MyJoystick joy;

    /**
     * Constructs timers for the loading time and firing time.
     */
    public Catapult() {
        this.loadTimer = new Timer();
        this.fireTimer = new Timer();
    }
    
    /**
     * Decides if the button to load or fire the catapult is pressed then do 
     * the correct action
     */
    public void runCat() {
        
        if (joy.gotPressed(Config.Catapult.catLoadButton) == true) {
            loadTimer.start();
            catMotor.set(Config.Catapult.loadSpeed);
        }

        if (loadTimer.get() > Config.Catapult.loadingTime) {
            catMotor.set(0);
            loadTimer.stop();
            loadTimer.reset();
        } 
        
        if (joy.gotPressed(Config.Catapult.catFireButton) == true) {
            fireTimer.start();
            catMotor.set(Config.Catapult.fireSpeed);
        }
        
        if (fireTimer.get() > Config.Catapult.fireTime) {
            catMotor.set(0);
            fireTimer.stop();
            fireTimer.reset();

        }
    }
}
