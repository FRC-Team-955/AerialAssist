/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Diag;

import Core.Drive;
import ModClasses.MyJoystick;
import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author raiderbot-2
 */
public class Macro {
    
    MyJoystick joy;
    Drive drive;
            
    public Macro(MyJoystick joy, Drive drive){
    
    this.drive = drive;
    this.joy = joy;
    
    }
    
    public void run(){
    
    testJoysticks();
    testMotors();
    
    
    
    }
    
    
    public void testMotors(){
    
   try {
    drive.left1.set(1);
    drive.left2.set(1);
    drive.left3.set(1);
    
    drive.right1.set(1);
    drive.right2.set(1);
    drive.right3.set(1);
   }
    
   
   catch(Exception exception){
   
   System.err.print("Error in motors");
   
   
   }
    }
    
    public void testJoysticks(){
    try{
    joy.getX();
    joy.getY();
    joy.getAxis(Joystick.AxisType.kX);
    joy.getBumper();
    }
    
    
    catch(Exception exception){
    
    
        System.err.println("Error in Joystick");
    
    }
    }
    
    
}
