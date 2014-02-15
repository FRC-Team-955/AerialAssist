/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import ModClasses.MyJoystick;
import Utils.Config;
import edu.wpi.first.wpilibj.Timer;
import ModClasses.MyTalon;
import Sensor.LimitSwitch;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Activates a motor that loads and launches the catapult.
 *
 * @author Seraj B. and Warren E.
 */
public class Catapult {

    public MyTalon catMotor = new MyTalon(Config.Catapult.chnCat);
    public MyTalon catMotorTwo = new MyTalon(Config.Catapult.chnCatTwo);
	Timer fireTimer;
    MyJoystick joy;
    LimitSwitch limitSwitch;
    Pickup pickup = new Pickup();
	Timer preFireTimer;

    public Catapult() {
		this.preFireTimer = new Timer();
		this.fireTimer = new Timer();
        this.limitSwitch = new LimitSwitch(Utils.Config.Catapult.chnLS, true);
    }
    
    
    public void run () {
        // if (!ready){
        //     preFire();
        // }
        // if (joy.gotPressed(Config.Catapult.catFireButton)){
        //     fire();
        // }
        if(joy.getRawButton(Config.Catapult.catFireButton))
            motorSpeed = Config.Catapult.fireSpeed;
        
        else if(limitSwitch.get())
            motorSpeed = 0;
        
        catMotor.set(motorSpeed);
        
    }
    
    public void preFire(){
        catMotor.set(Config.Catapult.preFireSpeed);
        if (limitSwitch.get() == true) {
            catMotor.set(0);
        }
    } 
    
    public void fire() {
        fireTimer.start();
        catMotor.set(Config.Catapult.fireSpeed);
        if(fireTimer.get() > Config.Catapult.fireTime){
            catMotor.set(0);
        }
    
    }
	public void testCat(){
		System.out.println(limitSwitch.get());
//		catMotor.set(1);
//		catMotorTwo.set(1);
	}
	/*
	 * This is the new catapult code I quickly wrote that does not need timmer
	 * Look over this or make the fresshmen re-write it if you want
	 * We could make them do it sunday and just use this code for the time being
	 * Also I know I don't need the brackets but I like it anyways
	 * -Ryan
	 */
	double speed = 0;
	public void newCat () {
		if (limitSwitch.get()){
             speed =0;
        }
		if (joy.gotPressed(Config.Catapult.catFireButton)){
             speed = Config.Catapult.fireSpeed;
        }
		catMotor.set(speed);
	}
}
