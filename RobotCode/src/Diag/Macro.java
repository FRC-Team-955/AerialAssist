/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Diag;

import Core.Drive;
import ModClasses.MyJoystick;
import ModClasses.MyTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import Utils.Config;
import Core.Vision;

/**
 *
 * @author raiderbot-2
 */
public class Macro {

    MyJoystick joy;
    Timer timer;
    Drive drive;
    Vision vision = new Vision();

    public Macro(MyJoystick joy, Drive drive) {

        this.drive = drive;
        this.joy = joy;

    }

    public void run() {

        testJoysticks();
        testMotors();
        testCat();
        testPickup();

    }

    public void testMotors() {
        timer.start();
        if(timer.get() < Config.Diagnostics.forwardTime) {
            try {
                drive.setSpeed(1, 1);
            }

            catch (Exception exception) {

                System.err.print("Error in forward motors");

            }
        }
        else if(timer.get() < Config.Diagnostics.backTime + Config.Diagnostics.forwardTime) {
            try {
                drive.setSpeed(0, 0);
            }

            catch (Exception exception) {

                System.err.print("Error in backward motors");

            }
        }
        else if(timer.get() < Config.Diagnostics.leftTime + Config.Diagnostics.forwardTime + Config.Diagnostics.backTime) {
            try {
                drive.setSpeed(0, 1);
            }

            catch (Exception exception) {

                System.err.print("Error in left motors");

            }
        }
        else if(timer.get() < Config.Diagnostics.rightTime +Config.Diagnostics.forwardTime + Config.Diagnostics.backTime + Config.Diagnostics.leftTime) {
            try {
                drive.setSpeed(1, 0);
            }

            catch (Exception exception) {

                System.err.print("Error in right motors");

            }
        }
        else {
            drive.setSpeed(0,0);
        }
    }

    public void testJoysticks() {
        try {
            joy.getX();
            joy.getY();
            joy.getAxis(Joystick.AxisType.kX);
            joy.getBumper();
        }

        catch (Exception exception) {

            System.err.println("Error in Joystick");

        }
    }
    
    public void testCat() {
        Timer loadTimer = new Timer();
        MyTalon catMotor = new MyTalon(Config.Catapult.chnCat);
        Timer fireTimer = new Timer();
        Timer timer = new Timer();
        
        loadTimer.start();
        catMotor.set(Config.Catapult.loadSpeed);
        

        if (loadTimer.get() > Config.Catapult.loadingTime) {
            catMotor.set(0);
            loadTimer.stop();
            loadTimer.reset();
            timer.start();
        } 
        
        if (timer.get() > Config.Diagnostics.loadTime) {
            fireTimer.start();
            catMotor.set(Config.Catapult.fireSpeed);
        }
        
        if (fireTimer.get() > Config.Catapult.fireTime) {
            catMotor.set(0);
            fireTimer.stop();
            fireTimer.reset();

        }
    }
    
    public void testPickup() {
            MyTalon pickupTalon = new MyTalon(Config.Pickup.pickupTalon1);
        MyJoystick joy = new MyJoystick(Config.Joystick.chn);
        Timer timer = new Timer();
        
        try {
            pickupTalon.set(Config.Pickup.pickupSpeed);
            timer.start();
        

            //Once time is up turn off
            if(timer.get() > Config.Pickup.pickupTime) {
                pickupTalon.set(0);
                timer.stop();
                timer.reset();
            }
        }
        catch (Exception exception) {

            System.err.println("Error in Pickup");

        }
    }

}
