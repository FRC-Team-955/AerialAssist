/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Diag;

import Core.*;
import ModClasses.*;
/*
 * @author RaiderPC
 */
public class Testing {
	MyJoystick joy;
	Drive drive;
	Catapult cat;
	Pickup pick;
	
	public Testing(MyJoystick joy1, Drive drive1, Catapult cat1, Pickup pick1) {
		joy=joy1;
		drive=drive1;
		cat=cat1;
		pick=pick1;
}
	public void run() {
		drive.run();
		cat.newCat();
		pickup();
		
		pick.piston();
		
	}
	
	private void pickup(){
		
		if (joy.getRawAxis(6) != 0) {
					pick.runMotor(joy.getRawAxis(6));

		}
		if (joy.getRawAxis(5) != 0){
							pick.runMotor(joy.getRawAxis(5));

		}
	
		
	}
	
	private void moveing (){
		double y = joy.getY();
		double x = joy.getX();
		drive.setSpeed(y+x, y-x);
	}

	private void catapult() {
		double speed = joy.getY();
		cat.catMotor.set(Math.abs(speed));  
		cat.catMotorTwo.set(Math.abs(speed));
	}
	
	private void dpad (){
		System.out.println("Dpad 5: " + joy.getRawAxis(5));
		System.out.println("Dpad 6: " + joy.getRawAxis(6));
	}
	
}
