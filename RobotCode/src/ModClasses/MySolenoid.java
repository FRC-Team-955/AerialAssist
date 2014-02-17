package ModClasses;

import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 * @author coders
 */
public class MySolenoid 
{
    //Declares solenoids
    private Solenoid solOne;
    private Solenoid solTwo;
    
    /**
     * Creates the solenoids
     * @param portOne   the slot the solenoid bumper is plugged into the cRio
     * @param portTwo   the ports on the solenoid bumper
     */
    public MySolenoid(int portOne, int portTwo) 
    {
        //Creates solenoids
        solOne = new Solenoid(portOne);
        solTwo = new Solenoid(portTwo);      
    }
    
    /**
     * Switches the solenoids between on and off positions
     */
    public void flip()
    {
        //Switches the solenoids, one is true, one false, just switches which in is which
        if (solOne.get()) 
        {
            solOne.set(false);
            solTwo.set(true);
        }
        
        //Switches in the other instance, when solenoidTwo is true, not solenoidOne
        else 
        {
            solOne.set(true);
            solTwo.set(false);
        }
    }   
    
    /**
     * Sets a solenoid value
     * @param newState the state of the value you want to set the solenoids to
     */
    public void set(boolean newState)
    {
        if(newState)
        {
            solOne.set(true);
            solTwo.set(false);
        }
        
        else
        {
            solOne.set(false);
            solTwo.set(true);
        }
    }
}