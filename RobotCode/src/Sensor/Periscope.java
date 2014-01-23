/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Sensor;
import edu.wpi.first.wpilibj.Solenoid;
import Utils.Config;
import ModClasses.MyJoystick;
/**
 *
 * @author Seraj B.
 * switches solenoids for the periscope
 */
public class Periscope {
    Solenoid solUp = new Solenoid(Config.Periscope.chnSolUp);
    Solenoid solDown = new Solenoid(Config.Periscope.chnSolDown);
    
    public Periscope(){
   
    }
    /**
     * Turns one solenoid off if one is on and vice virsa
     * @param solSet determines which solenoid is being activated
     */
    public void set(boolean solSet){
        solUp.set(solSet);
        solDown.set(!solSet);
    }
}