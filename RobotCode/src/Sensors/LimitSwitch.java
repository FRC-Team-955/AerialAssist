package Sensors;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 *
 * @author RaiderPC
 */
public class LimitSwitch 
{
    private DigitalInput input;
    private boolean flipped;
    
    public LimitSwitch(int port, boolean wantFlipped)
    {
            input = new DigitalInput(port);
            flipped = wantFlipped;
    }

    /**
     * Returns position of the limit switch
     * @return 
     */
    public boolean get() 
    {
        boolean value = input.get();

        if (flipped)
                value = !value;

        return value;
    }
}