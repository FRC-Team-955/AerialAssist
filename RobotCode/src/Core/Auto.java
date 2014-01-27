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
    boolean shot = false;
    Catapult catapult = new Catapult();
    MyJoystick joy = new MyJoystick(Config.Joystick.chn);
    Drive drive = new Drive(joy);
    Main main = new Main();
    double startTime;
    
    public void run() { 
        //if(hot target) {
            boolean hot = true;
        //}
        if(hot && !shot) {    
            catapult.loadFireCat();
            startTime = main.timer.get();
            shot = true;
        }    
        if(shot) {
            drive.setSpeed(1,1);
            double time = startTime - main.timer.get();
            if (time > Config.Auto.driveTime) {
                drive.setSpeed(0,0);
            }    
         }
    }
}
