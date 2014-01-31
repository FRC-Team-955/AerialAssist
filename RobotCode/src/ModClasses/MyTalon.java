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
 *  @author raiderbot-4
 */
public class MyTalon extends Talon {
    public MyTalon(int chan){
        super(chan);
    }
    
    /**
     * Makes sure the motors do not accelerate too fast
     * @param goal The speed that was requested
     */
    public void ramp(double goal){
        double speed = get();
        double difference = Math.abs(goal-speed);
        
        if(difference > Config.Drive.rampRate && goal > 0)
            set(speed + Config.Drive.rampRate);      
        
        else if(difference > Config.Drive.rampRate && goal < 0)
            set(speed - Config.Drive.rampRate);      
               
        else if(difference < Config.Drive.rampRate) 
            set(goal);      
    }
}
