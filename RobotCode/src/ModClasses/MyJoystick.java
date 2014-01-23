/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ModClasses;
import edu.wpi.first.wpilibj.Joystick;
/**
 *Adds a debounce function to the joystick
 * @author Seraj B.
 */
public class MyJoystick extends Joystick{
    boolean [] buttonState = new boolean[12];
    /**
     * constructor for the class
     * @param portNumber number of the joystick port
     */
    public MyJoystick(int portNumber){
        super(portNumber);
    }
    /**
     * The button is pressed if the button was pressed but not pressed if held
     * @param button number of the button being pressed
     * @return true if the button was pressed but false if held
     */
    public boolean Debounce(int button){
        for(int i = 0; i<buttonState.length; i++){
            buttonState[i] = false;
            
        }
        if(buttonState[button] == false && buttonState[button] != getRawButton(button) ){
            buttonState[button] = getRawButton(button);
            return true;
        }
        else{
            buttonState[button] = getRawButton(button);
            return false;
        }
    }
}