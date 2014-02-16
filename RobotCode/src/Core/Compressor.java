package Core;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import Utils.Config;

/**
 *
 * @author raiderbot-2
 */
public class Compressor 
{
    private DigitalInput digInPressure = new DigitalInput(Config.Compressor.chnDigInPressure);
    private Relay digOutCompressor = new Relay(Config.Compressor.chnDigOutCompressor);

    public void run()
    {
        if(digInPressure.get())
            digOutCompressor.set(Relay.Value.kOff);

        else
            digOutCompressor.set(Relay.Value.kForward);
    }
}
