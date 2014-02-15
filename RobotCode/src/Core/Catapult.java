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
    Timer fireTimer;
    MyJoystick joy;
    DigitalInput limitSwitch;
    boolean ready = false;
    double motorSpeed = 0;
    public Catapult() {
        this.limitSwitch = new DigitalInput(Utils.Config.Catapult.chnLS);
    
    }
    
    
    public void run () {
        // if (!ready){
        //     preFire();
        // }
        // if (joy.gotPressed(Config.Catapult.catFireButton)){
        //     fire();
        // }
        if(joy.getRawButton(Config.Catapult.catFireButton))
            motorSpeed = Config.Catapult.fireSpeed;
        
        else if(limitSwitch.get())
            motorSpeed = 0;
        
        catMotor.set(motorSpeed);
        
    }
    
//    public void preFire(){
//        motorSpeed =0;
//    //     catMotor.set(Config.Catapult.preFireSpeed);
//    //     if (limitSwitch.get() == true) {
//    //         catMotor.set(0);
//    //         ready = true;
//    //     }
//    // } 
//    }
//    public void fire() {
//    //     fireTimer.start();
//    //     catMotor.set(Config.Catapult.fireSpeed);
//    //     if(fireTimer.get() > Config.Catapult.fireTime){
//    //         catMotor.set(0);
//      
//       
//    }
}
