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
 *
 * @author raiderbot-4
 */
public class Drive {
    MyTalon left1 = new MyTalon(Config.chnLeft1);
    MyTalon left2 = new MyTalon(Config.chnLeft2);
    MyTalon left3 = new MyTalon(Config.chnLeft3);
    
    MyTalon right1 = new MyTalon(Config.chnLeft1);
    MyTalon right2 = new MyTalon(Config.chnLeft1);
    MyTalon right3 = new MyTalon(Config.chnLeft1);
    public void setSpeed(MyJoystick joy){
        double x = joy.getX();
        double y = joy.getY();
        setLeft(y + x);
        setRight(y - x);
    }
    public void setLeft(double speed){
        left1.set(speed);
        left2.set(speed);
        left3.set(speed);
    }
    public void setRight(double speed){
        right1.set(speed);
        right2.set(speed);
        right3.set(speed);
    }
}
