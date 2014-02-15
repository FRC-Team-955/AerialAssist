/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ModClasses;
import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 * @author coders
 */
public class MySolenoid {
    //Declares solenoids
    Solenoid solenoidOne;
    Solenoid solenoidTwo;
    /**
     * Creates the solenoids
     * @param portOne   the slot the solenoid bumper is plugged into the cRio
     * @param portTwo   the ports on the solenoid bumper
     * @param portThree
     */
    public MySolenoid(int portOne, int portTwo) {
        //Creates solenoids
        solenoidOne = new Solenoid(portOne);
        solenoidTwo = new Solenoid(portTwo);
        
    }
    /**
     * Switches the solenoids between on and off positions
     */
    public void solenoidSwitch(){
        //Switches the solenoids, one is true, one false, just switches which in is which
        if (solenoidOne.get()) {
            solenoidOne.set(false);
            solenoidTwo.set(true);
            
        }
        //Switches in the other instance, when solenoidTwo is true, not solenoidOne
        else {
            solenoidOne.set(true);
            solenoidTwo.set(false);
        }
    }   
    /**
     * Sets the solenoids in the on position.
     */
    public void on() {
        //Sets solenoids in on position
        solenoidOne.set(true);
        solenoidTwo.set(false);
    }
    /**
     * Sets the solenoids in the off position.
     */
    public void off() {
        //Sets solenoids in off position
        solenoidOne.set(false);
        solenoidTwo.set(true);
    }
}