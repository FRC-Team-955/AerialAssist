/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.DigitalOutput;
import Utils.Config;
/**
 *
 * @author raiderbot-2
 */
public class Compressor {
    private DigitalInput m_digInSensor = new DigitalInput(Config.Compressor.chnDigInSensor);
    private Relay m_digOutCompressor = new Relay(Config.Compressor.chnDigOutCompressor);

    public void run()
    {
        if(m_digInSensor.get())
            m_digOutCompressor.set(Relay.Value.kOff);

        else
            m_digOutCompressor.set(Relay.Value.kForward);
    }
}
