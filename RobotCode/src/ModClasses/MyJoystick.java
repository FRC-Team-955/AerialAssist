package ModClasses;

import edu.wpi.first.wpilibj.Joystick;
import Utils.Config;

/**
 * Adds a runDebounce function to the joystick
 * @author Seraj B.
 */
public class MyJoystick extends Joystick
{   
    // current button state
    private boolean [] gotPressed = new boolean[Config.Joystick.numberOfButtons];
    // previous button state
    private boolean [] lastButtonState = new boolean[Config.Joystick.numberOfButtons];
    private boolean [] switches = new boolean[Config.Joystick.numberOfButtons];
    
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
            switches[i] = false;
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
    
    /**
     * gets the value of a switch
     * @param port port number of the switch
     * @return value of a switch
     */
    public boolean getSwitch(int port)
    {
        return switches[port - 1];
    }
    
    /**
     * Sets a switch the value of a switch
     * @param port port number of a switch
     * @param newVal the value you want to set the switch to
     */
    public void setSwitch(int port, boolean newVal)
    {
        switches[port - 1] = newVal;
    }
    
    /**
     * Switches the values of a switch
     * @param port switch port number
     */
    public void flipSwitch(int port)
    {
        setSwitch(port, !getSwitch(port));
    }
    
    /**
     * Checks if the up dpad is pressed
     * @return true if the up dpad is pressed
     */
    public boolean getDpadUp()
    {
        return super.getRawAxis(Config.Joystick.chnDpadVert) > Config.Joystick.minDpadVal; 
    }
    
    /**
     *Checks if the down dpad is pressed
     *@return true if the down dpad is pressed
     */
    public boolean getDpadDown()
    {
        return super.getRawAxis(Config.Joystick.chnDpadVert) < -Config.Joystick.minDpadVal; 
    }
    
    /**
     * Checks if the left dpad is pressed 
     * @return true if the left dpad is pressed
     */
    public boolean getDpadLeft()
    {
        return super.getRawAxis(Config.Joystick.chnDpadHorz) < -Config.Joystick.minDpadVal; 
    }
    
    /**
     * Checks of the right dpad is pressed
     * @return true if the right dpad is pressed
     */
    public boolean getDpadRight()
    {
        return super.getRawAxis(Config.Joystick.chnDpadHorz) > Config.Joystick.minDpadVal; 
    }
}