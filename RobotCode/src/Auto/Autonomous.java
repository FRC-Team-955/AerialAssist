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
    private int autoStep = 0;
    private boolean driveForwardOnlyMode = false;
    private boolean oneBallNoVisionMode = false;
    private boolean twoBallNoVisionMode = false;
    private boolean oneBallVisionMode = false;
    private boolean twoBallVisionMode = false;
    private boolean shootFirst = false;
    private boolean startedLeft = false;
    private Timer autoGlobalTimer = new Timer();
    private Timer autoTimer = new Timer();
    private Vision vision = new Vision();
    private Catapult catapult;
    private Drive drive;
    private Pickup pickup;
    
    public Autonomous(Catapult newCatapult, Drive newDrive, Pickup newPickup)
    { 
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
        vision.turnOffPi();
        stopEverything();
        autoTimer.stop();
        autoGlobalTimer.stop();
        driveForwardOnlyMode = false;
        oneBallNoVisionMode = false;
        twoBallNoVisionMode = false;
        oneBallVisionMode = false;
        twoBallVisionMode = false;
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
        switch(autoStep)
        {
            case 0: // See if goal we're pointed at is hot
            {
                if(vision.foundHotTarget() || autoGlobalTimer.get() >= 5)
                {
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
            
            case 1: // Shoot one ball
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
            
            case 2: // Drive to alliance zone 
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
     * Shoot 1st ball, pick up 2nd ball, shoot 2nd ball
     */
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
        if(autoGlobalTimer.get() <= Config.Autonomous.visionFindTime)
            shootFirst = vision.foundHotTarget();
        
        else
        {
            switch(autoStep)
            {       
                case 0: // Start the timer
                {
                    pickup.down();
                    autoTimer.reset();
                    autoTimer.start();
                    autoStep++;
                }
                
                case 1: // If we dont shoot first, turn to other goal
                {
                    if(shootFirst || (autoTimer.get() >= Config.Autonomous.turnToOtherGoalTime))
                    {
                        drive.stop();
                        autoTimer.reset();
                        autoStep++;
                    }
                    
                    else
                    {
                        if(startedLeft) // Turn to the right
                            drive.turnRight(Config.Autonomous.turnToOtherGoalSpeed, true);

                        else // Turn to the left
                            drive.turnLeft(Config.Autonomous.turnToOtherGoalSpeed, true);
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

                case 3: // If we didn't shoot first turn back before picking up ball
                {
                    if(shootFirst || autoTimer.get() >= Config.Autonomous.turnToOtherGoalTime)
                    {
                        drive.stop();
                        autoTimer.reset();
                        autoStep++;
                    }
                    
                    else
                    {
                        if(startedLeft)
                            drive.turnLeft(Config.Autonomous.turnToOtherGoalSpeed, true);
                        
                        else
                            drive.turnRight(Config.Autonomous.turnToOtherGoalSpeed, true);
                    }
                       
                    break;
                }
                
                case 4: // Get 2nd ball
                {
                    pickup.inward();

                    if(autoTimer.get() >= Config.Autonomous.pickupBallTime)
                    {
                        pickup.up();
                        autoTimer.reset();
                        autoStep++;
                    }

                    break;
                }

                case 5: // Dont shoot till pick up is all the way up
                {
                    if(autoTimer.get() >= Config.Autonomous.pickupMoveUpTime)
                    {
                        pickup.stop();
                        autoTimer.reset();
                        autoStep++;
                    }

                    break;
                }
                
                case 6: // Let ball settle in the catapult
                {
                    if(autoTimer.get() >= Config.Autonomous.ballSettleTime)
                    {
                        autoTimer.reset();
                        autoStep++;
                    }

                    break;
                }

                case 7: // Turn to other/original goal
                {
                    if(!shootFirst || autoTimer.get() >= Config.Autonomous.turnToOtherGoalTime)
                    {
                        drive.stop();
                        autoTimer.reset();
                        autoStep++;
                    }
                    
                    else // If shot first, turn to other goal
                    {
                        if(startedLeft) // Turn to the right
                            drive.turnRight(Config.Autonomous.turnToOtherGoalSpeed, true);
                        
                        else // Turn to the left
                            drive.turnLeft(Config.Autonomous.turnToOtherGoalSpeed, true);
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

                case 9: // If we shot first we need to turn back
                {                    
                    if(!shootFirst || (autoTimer.get() >= Config.Autonomous.turnToOtherGoalTime))
                    {
                        drive.stop();
                        autoTimer.reset();
                        autoStep++;
                    }
                    
                    else
                    {
                        if(startedLeft) // Turn to the left
                            drive.turnLeft(Config.Autonomous.turnToOtherGoalSpeed, true);

                        else // Turn to the right
                            drive.turnRight(Config.Autonomous.turnToOtherGoalSpeed, true);
                    }
                    
                    break;
                }
                
                case 10: // Drive to alliance zone
                {
                    drive.moveForward(Config.Autonomous.driveToAllianceTime, true);

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

/* CODE USED AT COMP, AUTO SHOOTING IN FRONT OF WHITE ZONE */
//package Auto;
//
//import Utils.Config;
//import Core.Catapult;
//import Core.Drive;
//import Core.Pickup;
//import ModClasses.Station;
//import edu.wpi.first.wpilibj.Timer;
//
///**
// * @author Merfoo
// */
//public class Autonomous
//{  
//    private int autoStep = 0;
//    private boolean driveForwardOnlyMode = false;
//    private boolean oneBallNoVisionMode = false;
//    private boolean twoBallNoVisionMode = false;
//    private boolean oneBallVisionMode = false;
//    private boolean twoBallVisionMode = false;
//    private boolean shootFirst = false;
//    private boolean startedLeft = false;
//    private Timer autoGlobalTimer = new Timer();
//    private Timer autoTimer = new Timer();
//    private Vision vision = new Vision();
//    private Catapult catapult;
//    private Drive drive;
//    private Pickup pickup;
//    
//    public Autonomous(Catapult newCatapult, Drive newDrive, Pickup newPickup)
//    { 
//        catapult = newCatapult;
//        drive = newDrive;
//        pickup = newPickup;
//    }
//    
//    /**
//     * Inits the autonomous class, starts/resets timers, reset autoStep, 
//     * get auto Version
//     */
//    public void init()
//    {
//        startedLeft = Station.getDigitalIn(Config.Station.chnPrefSideLeft);
//        vision.resetTable();
//        vision.setPrefSideLeft(startedLeft);
//        vision.setDebugMode(Station.getDigitalIn(Config.Station.chnDebugMode));
//        autoGlobalTimer.reset();
//        autoGlobalTimer.start();
//        autoTimer.reset();
//        autoTimer.start();
//        autoStep = 0;
//        getAutoVersion();
//    }
//    
//    /**
//     * Ends autonomous, turns off raspberry pi
//     */
//    public void end()
//    {
//        vision.turnOffPi();
//        stopEverything();
//        autoTimer.stop();
//        autoGlobalTimer.stop();
//        driveForwardOnlyMode = false;
//        oneBallNoVisionMode = false;
//        twoBallNoVisionMode = false;
//        oneBallVisionMode = false;
//        twoBallVisionMode = false;
//    }
//    
//    /**
//     * Resets the vision networktable
//     */
//    public void resetVision()
//    {
//        vision.resetTable();
//    }
//    
//    /**
//     * Stop all moving parts on the robot except pickup
//     */
//    private void stopEverything()
//    {
//        catapult.stop();
//        drive.stop();
//        pickup.stop();
//    }
//    
//    /**
//     * Gets which autonomous code should be ran from driverstation digitalIn
//     */
//    private void getAutoVersion()
//    {
//        if(Station.getDigitalIn(Config.Station.chnDriveForwardOnly))
//            driveForwardOnlyMode = true;
//        
//        else if(Station.getDigitalIn(Config.Station.chnOneBallNoVision))
//            oneBallNoVisionMode = true;
//        
//        else if(Station.getDigitalIn(Config.Station.chnTwoBallNoVision))
//            twoBallNoVisionMode = true;
//        
//        else if(Station.getDigitalIn(Config.Station.chnOneBallVision))
//            oneBallVisionMode = true;
//        
//        else if(Station.getDigitalIn(Config.Station.chnTwoBallVision))
//            twoBallVisionMode = true;
//    }
//    
//    /**
//     * Runs the autonomous code
//     */
//    public void run()
//    {
//        String status = "No Auto";
//        
//        if(autoTimer.get() <= Config.Autonomous.maxAutoTime)
//        {
//            if(driveForwardOnlyMode)
//            {
//                driveForwardOnly();
//                status = "driveForwardOnly: ";
//            }
//            
//            else if(oneBallNoVisionMode)
//            {
//                oneBallNoVision();
//                status = "oneBallNoVision: ";
//            }
//            
//            else if(twoBallNoVisionMode)
//            {
//                twoBallNoVision();
//                status = "twoBallNoVision: ";
//            }
//            
//            else if(oneBallVisionMode)
//            {
//                oneBallVision();
//                status = "oneBallVision: ";
//            }
//            
//            else if(twoBallVisionMode)
//            {
//                twoBallVision();
//                status = "twoBallVision: ";
//            }
//            
//            status += autoStep;
//        }
//                
//        else 
//        {
//            stopEverything();
//            status = "Auto Done";
//        }
//        
//        Station.print(Config.Station.autonomousStep, status);
//    }
//    
//    /**
//     * Moves forward to alliance zone
//     */
//    private void driveForwardOnly()
//    {
//        switch(autoStep)
//        {
//            case 0: // Drive to alliance zone 
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
//            default: // Means we finished or something went wrong
//            {
//                stopEverything();
//            }
//        }
//    }
//    
//    /**
//     * Shoot one ball, drive forward
//     */
//	private void oneBallNoVision()
//    {
//        switch(autoStep)
//        {  
//			case 0: // Put pickup down
//            {
//                pickup.down();
//                autoTimer.reset();
//                autoStep++;
//                break;
//            }
//				
//			case 1: // Get 2nd ball
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
//            case 2: // Put pickup up to get ball properly
//            {
//                pickup.up();
//                autoTimer.reset();
//                autoStep++;
//                break;
//            }
//            
//            case 3: // Dont shoot till pick up is all the way up
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
//            case 4: // Let ball settle in the catapult
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
//			case 5: // Drive to alliance zone 
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
//            case 6: // Shoot one ball
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
//    
//    /**
//     * Shoot in high goal when its hot, move forward afterwards
//     */
//    private void oneBallVision()
//    {
//        switch(autoStep)
//        {
//            case 0: // See if goal we're pointed at is hot
//            {
//                if(vision.foundHotTarget() || autoGlobalTimer.get() >= 5)
//                {
//                    autoTimer.reset();
//                    autoStep++;
//                }
//                
//                break;
//            }
//            
//            case 1: // Shoot one ball
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
//            case 2: // Drive to alliance zone 
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
//            default: // Means we finished or something went wrong
//            {
//                stopEverything();
//            }
//        }
//    }
//    
//	/** 
//     * Shoot 1st ball, pick up 2nd ball, shoot 2nd ball
//     */
//    private void twoBallNoVision()
//    {
//        switch(autoStep)
//        {
//			case 0: // Drive to alliance zone 
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
//            case 1: // Put pickup down
//            {
//                pickup.down();
//                autoTimer.reset();
//                autoStep++;
//                break;
//            }
//            
//            case 2: // Shoot 1st ball
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
//			case 3:	// Move back to original position
//			{
//				drive.moveBackward(Config.Autonomous.driveToAllianceSpeed, true);
//                pickup.inward();
//				
//                if(autoTimer.get() >= Config.Autonomous.driveToAllianceTime)
//                {
//                    drive.stop();
//                    autoTimer.reset();
//                    autoStep++;
//                }
//                
//                break;
//			}
//				
//            case 4: // Get 2nd ball
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
//            case 5: // Put pickup up to get ball properly
//            {
//                pickup.up();
//                autoTimer.reset();
//                autoStep++;
//                break;
//            }
//            
//            case 6: // Dont shoot till pick up is all the way up
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
//            case 7: // Let ball settle in the catapult
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
//			case 8:	// Move back to shooting position
//			{   
//				drive.moveForward(Config.Autonomous.driveToAllianceSpeed, true);
//                
//                if(autoTimer.get() >= Config.Autonomous.driveToAllianceTime)
//                {
//                    drive.stop();
//                    autoTimer.reset();
//                    autoStep++;
//                }
//                
//                break;
//			}
//				
//            case 9: // Shoot 2nd ball
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
//    
//	/**
//     * Find goal thats hot turn to it, shoot, turn to other, shoot, move forward
//     */
//    private void twoBallVision()
//    {
//        if(autoGlobalTimer.get() <= Config.Autonomous.visionFindTime)
//            shootFirst = vision.foundHotTarget();
//        
//        else
//        {
//            switch(autoStep)
//            {       
//                case 0: // Start the timer
//                {
//                    pickup.down();
//                    autoTimer.reset();
//                    autoTimer.start();
//                    autoStep++;
//                }
//                
//                case 1: // If we dont shoot first, turn to other goal
//                {
//                    if(shootFirst || (autoTimer.get() >= Config.Autonomous.turnToOtherGoalTime))
//                    {
//                        drive.stop();
//                        autoTimer.reset();
//                        autoStep++;
//                    }
//                    
//                    else
//                    {
//                        if(startedLeft) // Turn to the right
//                            drive.turnRight(Config.Autonomous.turnToOtherGoalSpeed, true);
//
//                        else // Turn to the left
//                            drive.turnLeft(Config.Autonomous.turnToOtherGoalSpeed, true);
//                    }
//                    
//                    break;
//                }
//                
//                case 2: // Shoot 1st ball
//                {
//                    catapult.fire();
//
//                    if(autoTimer.get() >= Config.Autonomous.minShootTime && catapult.isCocked())
//                    {
//                        catapult.stop();
//                        autoTimer.reset();
//                        autoStep++;
//                    }
//                    
//                    break;
//                }
//
//                case 3: // If we didn't shoot first turn back before picking up ball
//                {
//                    if(shootFirst || autoTimer.get() >= Config.Autonomous.turnToOtherGoalTime)
//                    {
//                        drive.stop();
//                        autoTimer.reset();
//                        autoStep++;
//                    }
//                    
//                    else
//                    {
//                        if(startedLeft)
//                            drive.turnLeft(Config.Autonomous.turnToOtherGoalSpeed, true);
//                        
//                        else
//                            drive.turnRight(Config.Autonomous.turnToOtherGoalSpeed, true);
//                    }
//                       
//                    break;
//                }
//                
//                case 4: // Get 2nd ball
//                {
//                    pickup.inward();
//
//                    if(autoTimer.get() >= Config.Autonomous.pickupBallTime)
//                    {
//                        pickup.up();
//                        autoTimer.reset();
//                        autoStep++;
//                    }
//
//                    break;
//                }
//
//                case 5: // Dont shoot till pick up is all the way up
//                {
//                    if(autoTimer.get() >= Config.Autonomous.pickupMoveUpTime)
//                    {
//                        pickup.stop();
//                        autoTimer.reset();
//                        autoStep++;
//                    }
//
//                    break;
//                }
//                
//                case 6: // Let ball settle in the catapult
//                {
//                    if(autoTimer.get() >= Config.Autonomous.ballSettleTime)
//                    {
//                        autoTimer.reset();
//                        autoStep++;
//                    }
//
//                    break;
//                }
//
//                case 7: // Turn to other/original goal
//                {
//                    if(!shootFirst || autoTimer.get() >= Config.Autonomous.turnToOtherGoalTime)
//                    {
//                        drive.stop();
//                        autoTimer.reset();
//                        autoStep++;
//                    }
//                    
//                    else // If shot first, turn to other goal
//                    {
//                        if(startedLeft) // Turn to the right
//                            drive.turnRight(Config.Autonomous.turnToOtherGoalSpeed, true);
//                        
//                        else // Turn to the left
//                            drive.turnLeft(Config.Autonomous.turnToOtherGoalSpeed, true);
//                    }
//                    
//                    break;
//                }
//                
//                case 8: // Shoot 2nd ball
//                {
//                    catapult.fire();
//
//                    if(autoTimer.get() >= Config.Autonomous.minShootTime && catapult.isCocked())
//                    {
//                        catapult.stop();
//                        autoTimer.reset();
//                        autoStep++;
//                    }
//
//                    break;
//                }
//
//                case 9: // If we shot first we need to turn back
//                {                    
//                    if(!shootFirst || (autoTimer.get() >= Config.Autonomous.turnToOtherGoalTime))
//                    {
//                        drive.stop();
//                        autoTimer.reset();
//                        autoStep++;
//                    }
//                    
//                    else
//                    {
//                        if(startedLeft) // Turn to the left
//                            drive.turnLeft(Config.Autonomous.turnToOtherGoalSpeed, true);
//
//                        else // Turn to the right
//                            drive.turnRight(Config.Autonomous.turnToOtherGoalSpeed, true);
//                    }
//                    
//                    break;
//                }
//                
//                case 10: // Drive to alliance zone
//                {
//                    drive.moveForward(Config.Autonomous.driveToAllianceTime, true);
//
//                    if(autoTimer.get() >= Config.Autonomous.driveToAllianceTime)
//                    {
//                        drive.stop();
//                        autoTimer.reset();
//                        autoStep++;
//                    }
//                    
//                    break;
//                }
//
//                default: // Means we finished or something went wrong
//                {
//                    stopEverything();
//                }
//            }
//        }
//    }
//}