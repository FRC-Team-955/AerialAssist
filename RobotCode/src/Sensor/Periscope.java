/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Sensor;
import edu.wpi.first.wpilibj.Solenoid;
import Utils.Config;
import Utils.MyJoystick;
/**
 *
 * @author raiderbot-3
 */
public class Periscope {
    Solenoid solUp = new Solenoid(Config.chnSolUp);
    Solenoid solDown = new Solenoid(Config.chnSolDown);
    
    
    
    public Periscope(){
   
    }
    public void set(boolean solSet){
        solUp.set(solSet);
        solDown.set(!solSet);
    }
}
