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
        if(Math.abs(goal-speed)<Config.Drive.rampRate){
            double toSet;
            if(speed-Math.abs(speed)==1){
                set(speed+Config.Drive.rampRate);
            }else if(speed-Math.abs(speed)==-1){
                set(speed-Config.Drive.rampRate);
            }
        }
    }
}
