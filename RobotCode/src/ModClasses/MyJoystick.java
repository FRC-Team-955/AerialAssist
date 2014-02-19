package ModClasses;

import edu.wpi.first.wpilibj.Joystick;
import Utils.Config;

/**
 * Adds a runDebounce function to the joystick
 * @author Seraj B.
 */
public class MyJoystick extends Joystick
{
    private boolean [] gotPressed = new boolean[Config.Joystick.numberOfButtons];
    private boolean [] lastButtonState = new boolean[Config.Joystick.numberOfButtons];
    
    /**
     * constructor for the class
     * @param portNumber number of the joystick port
     */
    public MyJoystick(int portNumber)
    {
        super(portNumber);
        
        for(int i = 0; i < lastButtonState.length; i++)
        {
            lastButtonState[i] = false;
            gotPressed[i] = false;
        }
    }
    
    /**
     * The button is pressed if the button was pressed but not pressed if held
     * @param button number of the button being pressed
     * @return true if the button was pressed but false if held
     */
    public boolean runDebounce(int button)
    {
        boolean ret = false;
        
        if(!lastButtonState[button - 1] && super.getRawButton(button))
            ret = true;
        
        lastButtonState[button - 1] = super.getRawButton(button);
        
        return ret;
    }
    
    /**
     * Checks if the button was pressed
     * @param i number of the button
     * @return Whether the button was pressed of not
     */
    public boolean getButton(int i) 
    {
        return gotPressed[i - 1];
    }
    
    /**
     * Updates all the buttons
     */
    public void updateButtons()
    {
        for(int port = 1; port <= gotPressed.length; port++)
            gotPressed[port - 1] = runDebounce(port);
    }
    
    public boolean getDpadUp()
    {
        return super.getRawAxis(Config.Joystick.chnDpadVert) > Config.Joystick.minDpadVal; 
    }
    
    public boolean getDpadDown()
    {
        return super.getRawAxis(Config.Joystick.chnDpadVert) < -Config.Joystick.minDpadVal; 
    }
    
    public boolean getDpadLeft()
    {
        return super.getRawAxis(Config.Joystick.chnDpadHorz) < -Config.Joystick.minDpadVal; 
    }
    
    public boolean getDpadRight()
    {
        return super.getRawAxis(Config.Joystick.chnDpadHorz) > Config.Joystick.minDpadVal; 
    }
}