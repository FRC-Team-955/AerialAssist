/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ModClasses;
import edu.wpi.first.wpilibj.Joystick;
import Utils.Config;
/**
 *Adds a debounce function to the joystick
 * @author Seraj B.
 */
public class MyJoystick extends Joystick{
    private boolean [] gotPressed = new boolean[Config.Joystick.numberOfButtons];
    private boolean [] buttonState = new boolean[Config.Joystick.numberOfButtons];
    
    /**
     * constructor for the class
     * @param portNumber number of the joystick port
     */
    public MyJoystick(int portNumber){
        super(portNumber);
        for(int i = 0; i<buttonState.length; i++){
            buttonState[i] = false;
            gotPressed[i] = false;
        }
    }
    /**
     * The button is pressed if the button was pressed but not pressed if held
     * @param button number of the button being pressed
     * @return true if the button was pressed but false if held
     */
    
    public boolean debounce(int button){
        
            
        
        if(buttonState[button] == false && buttonState[button] != getRawButton(button) ){
            buttonState[button] = getRawButton(button);
            return true;
        }
        else{
            buttonState[button] = getRawButton(button);
            return false;
        }
    }
    
    /**
     * Checks if the button was pressed
     * @param i number of the button
     * @return Whether the button was pressed of not
     */
    public boolean gotPressed(int i) {
        return gotPressed[i];
    }
    
    /**
     * Updates all the buttons
     */
    public void update(){
        for(int i = 0; i<gotPressed.length; i++){
            gotPressed[i] = debounce(i);
        }
    }
}