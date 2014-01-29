/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ModClasses;
import edu.wpi.first.wpilibj.Talon;
import Utils.Config;
/**
 *
 * @author raiderbot-4
 */
public class MyTalon extends Talon {
    public MyTalon(int chan){
        super(chan);
    }
        public void ramp(double goal){
        double speed = get();
        double pastVal = 0;
        if(Math.abs(goal-speed)<Math.abs(Config.Drive.rampRate)){
            
        }
    }
}
