package ModClasses;

import edu.wpi.first.wpilibj.Talon;
import Utils.Config;

/**
 *
 *  @author raiderbot-4
 */
public class MyTalon extends Talon 
{
    public MyTalon(int chan)
    {
        super(chan);
    }
    
    /**
     * Makes sure the motors do not accelerate too fast
     * @param newSpeed The speed that was requested
     */
    public void ramp(double newSpeed)
    {
        double curSpeed = super.get();
        double difference = Math.abs(newSpeed-curSpeed);
        
        if(difference > Config.MyTalon.rampRate)
        {
            if(newSpeed > curSpeed)
                newSpeed = curSpeed + Config.MyTalon.rampRate;
            
            else
                newSpeed = curSpeed - Config.MyTalon.rampRate;
        } 
         
        set(newSpeed);      
    }
}