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
//		drive.run();
//		cat.newCat();
//		catapult();
		pickup();
	}
	
	public void pickup(){
		pick.runMotor(joy.getY());
		System.out.println(joy.getY());
		
	}
	
	public void moveing (){
		double y = joy.getY();
		double x = joy.getX();
		drive.setSpeed(y+x, y-x);
	}

	private void catapult() {
		double speed = joy.getY();
		cat.catMotor.set(speed);  
		cat.catMotorTwo.set(speed);
	}
	
}
