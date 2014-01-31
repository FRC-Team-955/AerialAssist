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
    double currentLoadingTime = 0;
    double currentFiringTime = 0;
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
        currentLoadingTime = loadTimer.get();
        currentFiringTime = fireTimer.get();
        if (joy.gotPressed(Config.Catapult.catLoadButton) == true) {
            loadTimer.start();
            
            if (currentLoadingTime < Config.Catapult.loadingTime) {
                catMotor.set(Config.Catapult.loadSpeed);
            }
            
            else {
                loadTimer.stop();
                loadTimer.reset();
            }
        }
        
        else if (joy.gotPressed(Config.Catapult.catFireButton) == true) {
            fireTimer.start();
            
            if (currentFiringTime < Config.Catapult.fireTime) {
                catMotor.set(Config.Catapult.fireSpeed);
            }
            
            else {
                fireTimer.stop();
                fireTimer.reset();
            }
        }
    }
}
