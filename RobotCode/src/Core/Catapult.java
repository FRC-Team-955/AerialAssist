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
    double currentLoadingTime;
    double currentFiringTime;
    MyJoystick joy;

    /**
     * Constructs timers for the loading time and firing time.
     */
    public Catapult() {
        this.loadTimer = new Timer();
        this.fireTimer = new Timer();
        this.currentLoadingTime = loadTimer.get();
        this.currentFiringTime = fireTimer.get();

    }
    /**
     * Decides if the button to load or fire the catapult is pressed then do 
     * the correct action
     */
    public void loadFireCat() {
        if (joy.gotPressed[Config.Catapult.catFireButton] == true) {
            loadTimer.start();
            if (currentLoadingTime != Config.Catapult.loadingTime) {
                catMotor.set(Config.Catapult.loadSpeed);
            }
            else {
                loadTimer.stop();
                loadTimer.reset();
            }
        }
        else if (joy.gotPressed[Config.Catapult.catFireButton] == true) {
            fireTimer.start();
            if (currentFiringTime != Config.Catapult.fireTime) {
                catMotor.set(Config.Catapult.fireSpeed);
            }
            else {
                fireTimer.stop();
                fireTimer.reset();
            }
        }
    }

}
