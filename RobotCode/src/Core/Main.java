/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package Core;

import ModClasses.MyJoystick;
import ModClasses.Station;
import Utils.Config;
import Auto.Autonomous;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Compressor;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also updateButtons the manifest file in the resource
 * directory.
 */
public class Main extends IterativeRobot 
{
    MyJoystick joy = new MyJoystick(Config.Joystick.chn);
    Drive drive = new Drive(joy);
    Catapult catapult = new Catapult(joy);
    Pickup pickUp = new Pickup(joy);
    Autonomous auto = new Autonomous(catapult, drive, pickUp);
    Compressor compressor = new Compressor(Config.Compressor.chnDigInPressure, Config.Compressor.chnDigOutCompressor);
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        // Setting to get regular driveFlipped Working properly on the ps3 Controller should be 3, 2
        joy.setAxisChannel(MyJoystick.AxisType.kX, 3);
        joy.setAxisChannel(MyJoystick.AxisType.kY, 2);
        pickUp.setPickup(false);
        System.out.println("Init called: CODE FOR DISTRICT 1");
    }
    
    /**
     * This function is called once before autonomous
     */
    public void autonomousInit()
    {
        auto.init();
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
        auto.run();
    }
    
    /**
     * This function is called once before teloepPeriodic
     */
    public void teleopInit()
    {
        compressor.start();
        auto.stop();
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() 
    {
        Station.clearAllText();
        Station.print(Config.Station.mainCodeType, "CODE FOR DISTRICT 1");
        joy.updateButtons();
	drive.run();
        catapult.run();
        pickUp.run();
    }
}