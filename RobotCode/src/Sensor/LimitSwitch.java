/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sensor;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 *
 * @author RaiderPC
 */
public class LimitSwitch {
	DigitalInput input;
	boolean notNormal;
	public LimitSwitch(int port, boolean flipped){
		input = new DigitalInput(port);
		notNormal = flipped;
	}
	
	public boolean get() {
		boolean value = input.get();
		if (notNormal)
			value = !value;
		return value;
	}
}