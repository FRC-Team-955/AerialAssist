/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package Core;
import Diag.Testing;
import ModClasses.*;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Main extends IterativeRobot {
    MyJoystick joy = new MyJoystick(Utils.Config.Joystick.chn);
    Drive drive = new Drive(joy);
	Catapult cat = new Catapult(joy);
	Pickup pick = new Pickup(joy);
	Compressor compressor = new Compressor();
//    Auto auto = new Auto();
	Testing test = new Testing(joy, drive, cat, pick);
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		// Setting to get regular drive Working properly on the ps3 Controller
        // should be 3, 2
//        joy.setAxisChannel(MyJoystick.AxisType.kX, 3);
//        joy.setAxisChannel(MyJoystick.AxisType.kY, 2);
//		System.out.println("Init called");
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
//        auto.run();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
//        drive.run();
        joy.update();
		test.run();
		compressor.run();
		
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
		
    }
    
}
