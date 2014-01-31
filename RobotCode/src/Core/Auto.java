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
    /**
     * Called from main while auto is active
     */
    public void run() { 
        //checks for hot target
        //if(hot target) {
            boolean hot = true;
        //}
        //if the target is hot and hasnt been shot then shoot
        if(hot && !shot) {    
            catapult.runCat();
            startTime = main.timer.get();
            shot = true;
        }    
        //Once shot drive forward
        if(shot) {
            drive.setSpeed(1,1);
            double time = startTime - main.timer.get();
            if (time > Config.Auto.driveTime) {
                drive.setSpeed(0,0);
            }    
         }
    }
}
