/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ModClasses;
import Utils.Config;
import edu.wpi.first.wpilibj.AnalogChannel;

/**
 *
 * @author Merfoo
 */
public class DigitalAnalogIn
{
    AnalogChannel analogChn;
    
    public DigitalAnalogIn(int chn)
    {
        analogChn = new AnalogChannel(chn);
    }
    
    public boolean get()
    {
        if(analogChn.getVoltage() > Config.toBeChanged.min)
            return true;
        
        return false;
    }
}
