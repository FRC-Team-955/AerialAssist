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

    public MyTalon left1 = new MyTalon(Config.Drive.chnLeft1);
    public MyTalon left2 = new MyTalon(Config.Drive.chnLeft2);
    public MyTalon left3 = new MyTalon(Config.Drive.chnLeft3);

    public MyTalon right1 = new MyTalon(Config.Drive.chnRight1);
    public MyTalon right2 = new MyTalon(Config.Drive.chnRight2);
    public MyTalon right3 = new MyTalon(Config.Drive.chnRight3);

    public void setSpeed(MyJoystick joy) {
        double x = joy.getX();
        double y = joy.getY();
        setSpeed(y + x, y - x);
    }

    public void setLeft(double speed) {
        left1.set(speed);
        left2.set(speed);
        left3.set(speed);
    }

    public void setRight(double speed) {
        right1.set(speed);
        right2.set(speed);
        right3.set(speed);
    }

    public void setSpeed(double left, double right) {
        setLeft(left);
        setRight(right);
    }
}
