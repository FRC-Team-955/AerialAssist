/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import ModClasses.MyTalon;
import ModClasses.MyJoystick;
import Utils.*;

/**
 *Controls the motors on the wheels of the bot.
 * @author Matthew S.
 */
public class Drive {

    public MyTalon left1 = new MyTalon(Config.Drive.chnLeft1);
    public MyTalon left2 = new MyTalon(Config.Drive.chnLeft2);
    public MyTalon left3 = new MyTalon(Config.Drive.chnLeft3);

    public MyTalon right1 = new MyTalon(Config.Drive.chnRight1);
    public MyTalon right2 = new MyTalon(Config.Drive.chnRight2);
    public MyTalon right3 = new MyTalon(Config.Drive.chnRight3);
    
    MyJoystick joy;
/**
 * Initializes the joystick
 * @param joy1 Name of the joystick
 */
    public Drive(MyJoystick joy1){
        joy = joy1;
}
/**
 * Sets the motors to be controlled by the joystick.
 */
    public void run() {
        double x = joy.getX();
        double y = joy.getY();
        
        x *= Math.abs(x);
        y *= Math.abs(y);
        setSpeed(y + x, y - x);
    }
/**
 * Sets the speed of the left talons.
 * @param speed Name of the speed of the talons.
 */
    public void setLeft(double speed) {
        left1.set(speed);
        left2.set(speed);
        left3.set(speed);
    }
/**
 * Sets the speed of the right talons.
 * @param speed Name of the speed of the talons.
 */
    public void setRight(double speed) {
        right1.set(speed);
        right2.set(speed);
        right3.set(speed);
    }
/**
 * Makes both sides go at the same speed.
 * @param left Speed of left talons.
 * @param right Speed of right talons.
 */
    public void setSpeed(double left, double right) {
        setLeft(left);
        setRight(right);
    }
}
