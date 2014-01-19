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
 * @author raiderbot-4
 */
public class ConnorPeriscope {
    Solenoid periUp = new Solenoid(Config.periButtUp);
    Solenoid periDown = new Solenoid(Config.periButtDown);
    
    public void ConnorPeriscope(){
    }
    public void pos(boolean pos){
        periUp.set(pos);
        periDown.set(!pos);
    }
}
