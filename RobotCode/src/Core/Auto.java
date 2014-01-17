/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;
import edu.wpi.first.wpilibj.Timer;
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
    
    public void run() { 
        //if(hot target) {
            boolean hot = true;
        //}
        if(hot && !shot) {    
            //shoot
            timer.start();
            shot = true;
        }    
        if(shot) {
            //drive.set(1,1);
            if (timer.get() > /*Set time in config*/ 10) {
                //drive.set(0,0);
            }    
         }
    }
}
