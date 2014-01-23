/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;
import Utils.Config;
import edu.wpi.first.wpilibj.Timer;
import ModClasses.MyTalon;
/**
 * Activates a motor that loads and launches the catapult.
 * @author Seraj B. and Warren E.
 */
public class Catapult {
    public MyTalon catMotor = new MyTalon(Config.Catapult.chnCat);
    Timer loadTimer;
    Timer fireTimer;
    double currentLoadingTime;
    double currentFiringTime;
/**
 * Constructs timers for the loading time and firing time.
 */    
    public Catapult(){
        this.loadTimer = new Timer();
        this.fireTimer = new Timer();
        this.currentLoadingTime = loadTimer.get();
        this.currentFiringTime = fireTimer.get();
    }
/**
 * Starts the loading process, then stops after a set amount of time.
 * @param loadingSpeed The speed at which the motor moves to load the ball. 
 */            
    public void loadCat(double loadingSpeed){
        loadTimer.start();
        if( currentLoadingTime != Config.Catapult.loadingTime){
            catMotor.set(loadingSpeed);
        }
        else{
            loadTimer.stop();
            loadTimer.reset();
        }
    }
    /**
     * Starts the firing process, then stops after a set amount of time.
     * @param fireSpeed The speed at which the motor moves to fire the ball.
     */
    public void fireCat(double fireSpeed){
        fireTimer.start();
        if( currentFiringTime != Config.Catapult.fireTime){
            catMotor.set(fireSpeed);
        }
        else{
            fireTimer.stop();
            fireTimer.reset();
        }
    }
}
