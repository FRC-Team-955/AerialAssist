/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;
import Utils.Config;
import ModClasses.MyJoystick;
import edu.wpi.first.wpilibj.Timer;
import ModClasses.MyTalon;
/**
 *
 * @author raiderbot-4
 */
public class Catapult {
    public MyTalon catMotor = new MyTalon(Config.Catapult.chnCat);
    Timer loadTimer;
    Timer fireTimer;
    double currentLoadingTime;
    double currentFiringTime;
    
    public Catapult(){
        this.loadTimer = new Timer();
        this.fireTimer = new Timer();
        this.currentLoadingTime = loadTimer.get();
        this.currentFiringTime = fireTimer.get();
    }
            
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
