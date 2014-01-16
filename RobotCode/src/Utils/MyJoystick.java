/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;
import edu.wpi.first.wpilibj.Joystick;
/**
 *
 * @author raiderbot-3
 */
public class MyJoystick extends Joystick{
    boolean [] buttonState = new boolean[12];
    public MyJoystick(int portNumber){
        super(portNumber);
    }
    public boolean Debounce(int button){
        for(int i = 0; i<buttonState.length; i++){
            buttonState[i] = false;
            
        }
        if(buttonState[button] == false != getRawButton(button) && getRawButton(button)){
            buttonState[button] = true;
            return false;
        }
        else{
            buttonState[button] = getRawButton(button);
            return false;
        }
    }
}
