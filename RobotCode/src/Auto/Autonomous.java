package Auto;

import Utils.Config;
import Core.Catapult;
import Core.Drive;
import Core.Pickup;
import ModClasses.Station;
import edu.wpi.first.wpilibj.Timer;

/**
 * @author Merfoo
 */
public class Autonomous
{  
	// CHEESY VISION
	private CheesyVisionServer server = CheesyVisionServer.getInstance();
    private final int listenPort = 1180;
	
    private int autoStep = 0;
    private boolean driveForwardOnlyMode = false;
    private boolean oneBallNoVisionMode = false;
    private boolean twoBallNoVisionMode = false;
    private boolean oneBallVisionMode = false;
    private boolean twoBallVisionMode = false;
	private boolean threeBallNoVisionMode = false;
	private boolean leftHotFirst = false;
	private boolean rightHotFirst = false;
    private boolean startedLeft = false;
	private boolean runAuto = false;
    private Timer autoGlobalTimer = new Timer();
    private Timer autoTimer = new Timer();
    private Vision vision = new Vision();
    private Catapult catapult;
    private Drive drive;
    private Pickup pickup;
    
    public Autonomous(Catapult newCatapult, Drive newDrive, Pickup newPickup)
    { 
		// CHEESY VISION
		server.setPort(listenPort);
        server.start();
		
        catapult = newCatapult;
        drive = newDrive;
        pickup = newPickup;
    }
    
    /**
     * Inits the autonomous class, starts/resets timers, reset autoStep, 
     * get auto Version
     */
    public void init()
    {
		// CHEESY VISION
		server.reset();
        server.startSamplingCounts();
		
        startedLeft = Station.getDigitalIn(Config.Station.chnPrefSideLeft);
        vision.resetTable();
        vision.setPrefSideLeft(startedLeft);
        vision.setDebugMode(Station.getDigitalIn(Config.Station.chnDebugMode));
        autoGlobalTimer.reset();
        autoGlobalTimer.start();
        autoTimer.reset();
        autoTimer.start();
        autoStep = 0;
        getAutoVersion();
    }
    
    /**
     * Ends autonomous, turns off raspberry pi
     */
    public void end()
    {
		// CHEESY VISION
		server.stopSamplingCounts();
		
        vision.turnOffPi();
        stopEverything();
        autoTimer.stop();
        autoGlobalTimer.stop();
        driveForwardOnlyMode = false;
        oneBallNoVisionMode = false;
        twoBallNoVisionMode = false;
        oneBallVisionMode = false;
        twoBallVisionMode = false;
		threeBallNoVisionMode = false;
		runAuto = false;
		leftHotFirst = false;
		rightHotFirst = false;
    }
    
    /**
     * Resets the vision networktable
     */
    public void resetVision()
    {
        vision.resetTable();
    }
    
    /**
     * Stop all moving parts on the robot except pickup
     */
    private void stopEverything()
    {
        catapult.stop();
        drive.stop();
        pickup.stop();
    }
    
    /**
     * Gets which autonomous code should be ran from driverstation digitalIn
     */
    private void getAutoVersion()
    {
        if(Station.getDigitalIn(Config.Station.chnDriveForwardOnly))
            driveForwardOnlyMode = true;
        
        else if(Station.getDigitalIn(Config.Station.chnOneBallNoVision))
            oneBallNoVisionMode = true;
        
        else if(Station.getDigitalIn(Config.Station.chnTwoBallNoVision))
            twoBallNoVisionMode = true;
        
        else if(Station.getDigitalIn(Config.Station.chnOneBallVision))
            oneBallVisionMode = true;
        
        else if(Station.getDigitalIn(Config.Station.chnTwoBallVision))
            twoBallVisionMode = true;
		
		else if(Station.getDigitalIn(Config.Station.chnThreeBallNoVision))
            threeBallNoVisionMode = true;
    }
    
    /**
     * Runs the autonomous code
     */
    public void run()
    {
        String status = "No Auto";
        
        if(autoTimer.get() <= Config.Autonomous.maxAutoTime)
        {
            if(driveForwardOnlyMode)
            {
                driveForwardOnly();
                status = "driveForwardOnly: ";
            }
            
            else if(oneBallNoVisionMode)
            {
                oneBallNoVision();
                status = "oneBallNoVision: ";
            }
            
            else if(twoBallNoVisionMode)
            {
                twoBallNoVision();
                status = "twoBallNoVision: ";
            }
            
            else if(oneBallVisionMode)
            {
                oneBallVision();
                status = "oneBallVision: ";
            }
            
            else if(twoBallVisionMode)
            {
                twoBallVision();
                status = "twoBallVision: ";
            }
            
			else if(threeBallNoVisionMode)
            {
                threeBallNoVision();
                status = "threeBallNoVision: ";
            }
			
            status += autoStep;
        }
                
        else 
        {
            stopEverything();
            status = "Auto Done";
        }
        
        Station.print(Config.Station.autonomousStep, status);
    }
    
    /**
     * Moves forward to alliance zone
     */
    private void driveForwardOnly()
    {
        switch(autoStep)
        {
            case 0: // Drive to alliance zone 
            {
                drive.moveForward(Config.Autonomous.driveToAllianceSpeed, true);
                
                if(autoTimer.get() >= Config.Autonomous.driveToAllianceTime)
                {
                    drive.stop();
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
            
            default: // Means we finished or something went wrong
            {
                stopEverything();
            }
        }
    }
    
    /**
     * Shoot one ball, drive forward
     */
	// START COCKED
//	private void oneBallNoVision()
//    {
//        switch(autoStep)
//        {  
//			case 0:	// Put pickup down
//			{
//				pickup.down();
//				autoTimer.reset();
//				autoStep++;
//				break;
//			}
//				
//            case 1: // Cock catapult
//            {
//                catapult.fire();
//                
//                if(catapult.isCocked())
//                {
//                    catapult.stop();
//                    autoTimer.reset();
//                    autoStep++;
//                }
//                
//                break;
//            }
//            
//			case 2: // Get 2nd ball
//            {
//                pickup.inward();
//                
//                if(autoTimer.get() >= Config.Autonomous.pickupBallTime)
//                {
//                    autoTimer.reset();
//                    autoStep++;
//                }
//                
//                break;
//            }
//            
//            case 3: // Put pickup up to get ball properly
//            {
//                pickup.up();
//                autoTimer.reset();
//                autoStep++;
//                break;
//            }
//            
//            case 4: // Dont shoot till pick up is all the way up
//            {
//                if(autoTimer.get() >= Config.Autonomous.pickupMoveUpTime)
//                {
//                    pickup.stop();
//                    autoTimer.reset();
//                    autoStep++;
//                }
//                
//                break;
//            }
//				
//            case 5: // Drive to alliance zone 
//            {
//                drive.moveForward(Config.Autonomous.driveToAllianceSpeed, true);
//                
//                if(autoTimer.get() >= Config.Autonomous.driveToAllianceTime)
//                {
//                    drive.stop();
//                    autoTimer.reset();
//                    autoStep++;
//                }
//                
//                break;
//            }
//            
//			case 6: // Let ball settle in the catapult
//            {
//                if(autoTimer.get() >= Config.Autonomous.ballSettleTime)
//                {
//                    autoTimer.reset();
//                    autoStep++;
//                }
//                
//                break;
//            }
//				
//            case 7: // Shoot 1st ball
//            {
//                catapult.fire();
//                
//                if(autoTimer.get() >= Config.Autonomous.minShootTime && catapult.isCocked())
//                {
//                    catapult.stop();
//                    autoTimer.reset();
//                    autoStep++;
//                }
//                
//                break;
//            }
//				
//            default: // Means we finished or something went wrong
//            {
//                stopEverything();
//            }
//        }
//    }
	// START WHITE COCKED
    private void oneBallNoVision()
    {
        switch(autoStep)
        {  
            case 0: // Shoot one ball
            {
                catapult.fire();
                
                if(autoTimer.get() >= Config.Autonomous.minShootTime && catapult.isCocked())
                {
                    catapult.stop();
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
            
            case 1: // Drive to alliance zone 
            {
                drive.moveForward(Config.Autonomous.driveToAllianceSpeed, true);
                
                if(autoTimer.get() >= Config.Autonomous.driveToAllianceTime)
                {
                    drive.stop();
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
            
            default: // Means we finished or something went wrong
            {
                stopEverything();
            }
        }
    }
    
    /**
     * Shoot in high goal when its hot, move forward afterwards
     */
    private void oneBallVision()
    {
		if((server.getLeftStatus() && server.getRightStatus()) || autoGlobalTimer.get() >= Config.Autonomous.runVisionAutoAfter)
		{
			if(!runAuto)
			{
				runAuto = true;
				autoStep = 0;
				autoTimer.reset();
			}
			
			System.out.println("Shoot Time");
		}
		
		if(runAuto)
		{
			switch(autoStep)
			{  
				case 0: // Shoot one ball
				{
					catapult.fire();

					if(autoTimer.get() >= Config.Autonomous.minShootTime && catapult.isCocked())
					{
						catapult.stop();
						autoTimer.reset();
						autoStep++;
					}

					break;
				}

				case 1: // Drive to alliance zone 
				{
					drive.moveForward(Config.Autonomous.driveToAllianceSpeed, true);

					if(autoTimer.get() >= Config.Autonomous.driveToAllianceTime)
					{
						drive.stop();
						autoTimer.reset();
						autoStep++;
					}

					break;
				}

				default: // Means we finished or something went wrong
				{
					stopEverything();
				}
			}
		}
    }
    
    /** 
     * Shoot 1st ball, pick up 2nd ball, shoot 2nd ball
     */
	// START UNCOCKED
//	private void twoBallNoVision()
//    {
//        switch(autoStep)
//        {  
//			case 0:	// Put pickup down
//			{
//				pickup.down();
//				autoTimer.reset();
//				autoStep++;
//				break;
//			}
//				
//            case 1: // Cock catapult
//            {
//                catapult.fire();
//                
//                if(catapult.isCocked())
//                {
//                    catapult.stop();
//                    autoTimer.reset();
//                    autoStep++;
//                }
//                
//                break;
//            }
//            
//			case 2: // Get 2nd ball
//            {
//                pickup.inward();
//                
//                if(autoTimer.get() >= Config.Autonomous.pickupBallTime)
//                {
//                    autoTimer.reset();
//                    autoStep++;
//                }
//                
//                break;
//            }
//            
//            case 3: // Put pickup up to get ball properly
//            {
//                pickup.up();
//                autoTimer.reset();
//                autoStep++;
//                break;
//            }
//            
//            case 4: // Dont shoot till pick up is all the way up
//            {
//                if(autoTimer.get() >= Config.Autonomous.pickupMoveUpTime)
//                {
//                    pickup.stop();
//                    autoTimer.reset();
//                    autoStep++;
//                }
//                
//                break;
//            }
//				
//            case 5: // Drive to alliance zone 
//            {
//                drive.moveForward(Config.Autonomous.driveToAllianceSpeed, true);
//                
//                if(autoTimer.get() >= Config.Autonomous.driveToAllianceTime)
//                {
//                    drive.stop();
//                    autoTimer.reset();
//                    autoStep++;
//                }
//                
//                break;
//            }
//            
//			case 6: // Let ball settle in the catapult
//            {
//                if(autoTimer.get() >= Config.Autonomous.ballSettleTime)
//                {
//                    autoTimer.reset();
//                    autoStep++;
//                }
//                
//                break;
//            }
//				
//            case 7: // Shoot 1st ball
//            {
//                catapult.fire();
//                
//                if(autoTimer.get() >= Config.Autonomous.minShootTime && catapult.isCocked())
//                {
//                    catapult.stop();
//                    autoTimer.reset();
//                    autoStep++;
//                }
//                
//                break;
//            }
//				
//			// AFTER WE SHOT FIRST BALL IN ALLIANCE ZONE
//			case 8:	// Put pickup down
//			{
//				pickup.down();
//				pickup.inward();
//				autoTimer.reset();
//				autoStep++;
//				break;
//			}
//				
//			case 9: // Drive to 2nd ball
//            {
//                drive.moveBackward(Config.Autonomous.driveToAllianceSpeed, true);
//                
//                if(autoTimer.get() >= Config.Autonomous.driveToWhiteTime)
//                {
//                    drive.stop();
//                    autoTimer.reset();
//                    autoStep++;
//                }
//                
//                break;
//            }
//            
//			case 10: // Get 2nd ball
//            {
//                pickup.inward();
//                
//                if(autoTimer.get() >= Config.Autonomous.pickupBallTime)
//                {
//                    autoTimer.reset();
//                    autoStep++;
//                }
//                
//                break;
//            }
//            
//            case 11: // Put pickup up to get ball properly
//            {
//                pickup.up();
//                autoTimer.reset();
//                autoStep++;
//                break;
//            }
//            
//            case 12: // Dont shoot till pick up is all the way up
//            {
//                if(autoTimer.get() >= Config.Autonomous.pickupMoveUpTime)
//                {
//                    pickup.stop();
//                    autoTimer.reset();
//                    autoStep++;
//                }
//                
//                break;
//            }
//            
//			case 13: // Drive alliance zone
//            {
//                drive.moveForward(Config.Autonomous.driveToAllianceSpeed, true);
//                
//                if(autoTimer.get() >= Config.Autonomous.driveToAllianceTime * 2)
//                {
//                    drive.stop();
//                    autoTimer.reset();
//                    autoStep++;
//                }
//                
//                break;
//            }
//				
//			case 14: // Let ball settle in the catapult
//            {
//                if(autoTimer.get() >= Config.Autonomous.ballSettleTime)
//                {
//                    autoTimer.reset();
//                    autoStep++;
//                }
//                
//                break;
//            }
//				
//            case 15: // Shoot 2nd ball
//            {
//                catapult.fire();
//                
//                if(autoTimer.get() >= Config.Autonomous.minShootTime && catapult.isCocked())
//                {
//                    catapult.stop();
//                    autoTimer.reset();
//                    autoStep++;
//                }
//                
//                break;
//            }
//				
//            default: // Means we finished or something went wrong
//            {
//                stopEverything();
//            }
//        }
//    }
	// START FIRST BALL IN
    private void twoBallNoVision()
    {
        switch(autoStep)
        {
            case 0: // Shoot 1st ball
            {
                catapult.fire();
                
                if(autoTimer.get() >= Config.Autonomous.minShootTime && catapult.isCocked())
                {
                    catapult.stop();
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
            
            case 1: // Put pickup down
            {
                pickup.down();
                autoTimer.reset();
                autoStep++;
                break;
            }
				
            case 2: // Get 2nd ball
            {
                pickup.inward();
                
                if(autoTimer.get() >= Config.Autonomous.pickupBallTime)
                {
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
            
            case 3: // Put pickup up to get ball properly
            {
                pickup.up();
                autoTimer.reset();
                autoStep++;
                break;
            }
            
            case 4: // Dont shoot till pick up is all the way up
            {
                if(autoTimer.get() >= Config.Autonomous.pickupMoveUpTime)
                {
                    pickup.stop();
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
            
            case 5: // Let ball settle in the catapult
            {
                if(autoTimer.get() >= Config.Autonomous.ballSettleTime)
                {
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
				
            case 6: // Shoot 2nd ball
            {
                catapult.fire();
                
                if(autoTimer.get() >= Config.Autonomous.minShootTime && catapult.isCocked())
                {
                    catapult.stop();
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
            
            case 7: // Move alliance zone for mobility points
            {   
                drive.moveForward(Config.Autonomous.driveToAllianceSpeed, true);
                
                if(autoTimer.get() >= Config.Autonomous.driveToAllianceTime)
                {
                    drive.stop();
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
            
            default: // Means we finished or something went wrong
            {
                stopEverything();
            }
        }
    }
    
	/**
     * Find goal thats hot turn to it, shoot, turn to other, shoot, move forward
     */
    private void twoBallVision()
    {
		if(!leftHotFirst && !rightHotFirst)
		{
			if(server.getLeftStatus())
				leftHotFirst = true;
			
			if(server.getRightStatus())
				rightHotFirst = true;
		}
			
		else
		{
			switch(autoStep)
			{
				case 0: // Reset auto timer, put pickup down
				{
					pickup.down();
					autoTimer.reset();
                    autoStep++;
					break;
				}
					
				case 1: // Turn to hot goal
				{
					if(leftHotFirst)
						drive.turnLeft(Config.Autonomous.turnToOtherGoalSpeed, false);
					
					else
						drive.turnRight(Config.Autonomous.turnToOtherGoalSpeed, false);
					
					if(autoTimer.get() >= Config.Autonomous.turnToOtherGoalTime)
					{
						drive.stop();
						autoTimer.reset();
						autoStep++;
					}
					
					break;
				}
					
				case 2: // Shoot 1st ball
				{
					catapult.fire();

					if(autoTimer.get() >= Config.Autonomous.minShootTime && catapult.isCocked())
					{
						catapult.stop();
						autoTimer.reset();
						autoStep++;
					}

					break;
				}
					
				case 3: // Get 2nd ball
				{
					pickup.inward();

					if(autoTimer.get() >= Config.Autonomous.pickupBallTimeVision)
					{
						autoTimer.reset();
						autoStep++;
					}

					break;
				}
					
				case 4: // Put pickup up to get ball properly
				{
					pickup.up();
					autoTimer.reset();
					autoStep++;
					break;
				}

				case 5: // Wait for pickup to get up all the way
				{
					if(autoTimer.get() >= Config.Autonomous.pickupMoveUpTimeVision)
					{
						pickup.stop();
						autoTimer.reset();
						autoStep++;
					}

					break;
				}
				
				case 6: // Turn to other goal, which will be hot by now
				{
					if(leftHotFirst)
						drive.turnRight(Config.Autonomous.turnToOtherGoalSpeed, false);
					
					else
						drive.turnLeft(Config.Autonomous.turnToOtherGoalSpeed, false);
					
					if(autoTimer.get() >= Config.Autonomous.turnToOtherGoalTime * 2)
					{
						drive.stop();
						autoTimer.reset();
						autoStep++;
					}
					
					break;
				}
				
				case 7: // Let ball settle in after turning
				{
					if(autoTimer.get() >= Config.Autonomous.turnedBallSettleTimeVision)
					{
						autoTimer.reset();
						autoStep++;
					}
					
					break;
				}
					
				case 8: // Shoot 2nd ball
				{
					catapult.fire();

					if(autoTimer.get() >= Config.Autonomous.minShootTime && catapult.isCocked())
					{
						catapult.stop();
						autoTimer.reset();
						autoStep++;
					}

					break;
				}
					
				case 9: // Turn back to middle
				{
					if(leftHotFirst)
						drive.turnRight(Config.Autonomous.turnToOtherGoalSpeed, false);
					
					else
						drive.turnLeft(Config.Autonomous.turnToOtherGoalSpeed, false);
					
					if(autoTimer.get() >= Config.Autonomous.turnToOtherGoalTime)
					{
						drive.stop();
						autoTimer.reset();
						autoStep++;
					}
					
					break;
				}
					
				case 10: // Drive to alliance zone
				{
					drive.moveForward(Config.Autonomous.driveToAllianceSpeed, false);
					
					if(autoTimer.get() >= Config.Autonomous.driveToAllianceTime)
					{
						drive.stop();
						autoTimer.reset();
						autoStep++;
					}
					
					break;
				}
					
				default: // Means we finished or something went wrong
				{
					stopEverything();
				}
			}
		}
    }
	
    /** 
     * Shoot 1st ball, pick up 2nd ball, shoot 2nd ball
     */
    private void threeBallNoVision()
    {
        switch(autoStep)
        {
            case 0: // Shoot 1st ball
            {
                catapult.fire();
                
                if(autoTimer.get() >= Config.Autonomous.minShootTime && catapult.isCocked())
                {
                    catapult.stop();
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
            
            case 1: // Put pickup down
            {
                pickup.down();
                autoTimer.reset();
                autoStep++;
                break;
            }
				
            case 2: // Get 2nd ball
            {
                pickup.inward();
                
                if(autoTimer.get() >= Config.Autonomous.pickupBallTime)
                {
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
            
            case 3: // Put pickup up to get ball properly
            {
                pickup.up();
                autoTimer.reset();
                autoStep++;
                break;
            }
            
            case 4: // Dont shoot till pick up is all the way up
            {
                if(autoTimer.get() >= Config.Autonomous.pickupMoveUpTime)
                {
                    pickup.stop();
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
            
            case 5: // Let ball settle in the catapult
            {
                if(autoTimer.get() >= Config.Autonomous.ballSettleTime)
                {
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
				
            case 6: // Shoot 2nd ball
            {
                catapult.fire();
                
                if(autoTimer.get() >= Config.Autonomous.minShootTime && catapult.isCocked())
                {
                    catapult.stop();
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
            
            case 7: // Put pickup down
            {
                pickup.down();
                pickup.inward();
                autoTimer.reset();
                autoStep++;
                break;
            }
		
            case 8: // Move to 3rd ball
            {   
                drive.moveBackward(Config.Autonomous.driveToAllianceSpeed, true);
                
                if(autoTimer.get() >= Config.Autonomous.driveToAllianceTime)
                {
                    drive.stop();
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
            
            case 9: // Get 3rd ball
            {
                pickup.inward();
                
                if(autoTimer.get() >= Config.Autonomous.pickupBallTime)
                {
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
            
            case 10: // Put pickup up to get ball properly
            {
                pickup.up();
                autoTimer.reset();
                autoStep++;
                break;
            }
            
            case 11: // Dont shoot till pick up is all the way up
            {
                if(autoTimer.get() >= Config.Autonomous.pickupMoveUpTime)
                {
                    pickup.stop();
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
            
            case 12: // Move to 3rd ball
            {   
                drive.moveForward(Config.Autonomous.driveToAllianceSpeed, true);
                
                if(autoTimer.get() >= Config.Autonomous.driveToAllianceTime * 2)
                {
                    drive.stop();
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
            
            case 13: // Let ball settle in the catapult
            {
                if(autoTimer.get() >= Config.Autonomous.ballSettleTime)
                {
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
            
            case 14: // Shoot 3rd ball
            {
                catapult.fire();
                
                if(autoTimer.get() >= Config.Autonomous.minShootTime && catapult.isCocked())
                {
                    catapult.stop();
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
            
            default: // Means we finished or something went wrong
            {
                stopEverything();
            }
        }
    }
}