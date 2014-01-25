/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;
import edu.wpi.first.wpilibj.Timer;
import Core.*;
import Utils.*;
import ModClasses.*;

/**
 *
 * @author coders
 */
public class Auto {
    //Check if hot target
    //If hot target, then shoot and go forward
    //Else wait 5 seconds then shoot and go forward
    Timer timer = new Timer();
    boolean shot = false;
    Catapult catapult = new Catapult();
    MyJoystick joy = new MyJoystick(Config.Joystick.chnMyJoystick);
    Drive drive = new Drive(joy);
    
    public void run() { 
        //if(hot target) {
            boolean hot = true;
        //}
        if(hot && !shot) {    
            catapult.loadFireCat();
            timer.start();
            shot = true;
        }    
        if(shot) {
            drive.setSpeed(1,1);
            if (timer.get() > Config.Auto.driveTime) {
                drive.setSpeed(0,0);
            }    
         }
    }
}
