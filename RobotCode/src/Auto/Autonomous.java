package Auto;

import Utils.Config;
import Core.Catapult;
import Core.Drive;
import Core.Pickup;
import ModClasses.Station;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Merfoo
 */
public class Autonomous
{  
    private int autoStep = 0;
    private boolean noBallNoVisionWhiteMode = false;
    private boolean oneBallNoVisionWhiteMode = false;
    private boolean twoBallNoVisionWhiteMode = false;
    private boolean oneBallVisionWhiteMode = false;
    private boolean twoBallVisionWhiteMode = false;
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
        vision.startVision();
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
        noBallNoVisionWhiteMode = false;
        oneBallNoVisionWhiteMode = false;
        twoBallNoVisionWhiteMode = false;
        oneBallVisionWhiteMode = false;
        twoBallVisionWhiteMode = false;
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
        if(Station.getDigitalIn(Config.Station.chnNoBallNoVisionWhite))
            noBallNoVisionWhiteMode = true;
        
        else if(Station.getDigitalIn(Config.Station.chnOneBallNoVisionWhite))
            oneBallNoVisionWhiteMode = true;
        
        else if(Station.getDigitalIn(Config.Station.chnTwoBallNoVisionWhite))
            twoBallNoVisionWhiteMode = true;
        
        else if(Station.getDigitalIn(Config.Station.chnOneBallVisionWhite))
            oneBallVisionWhiteMode = true;
        
        else if(Station.getDigitalIn(Config.Station.chnTwoBallVisionWhite))
            twoBallVisionWhiteMode = true;
    }
    
    /**
     * Runs the autonomous code
     */
    public void run()
    {
        String status = "";
        
        if(autoTimer.get() <= Config.Autonomous.maxAutoTime)
        {
            if(noBallNoVisionWhiteMode)
            {
                noBallNoVisionWhite();
                status = "NoBallNoVisionWhite: ";
            }
            
            else if(oneBallNoVisionWhiteMode)
            {
                oneBallNoVisionWhite();
                status = "OneBallNoVisionWhite: ";
            }
            
            else if(twoBallNoVisionWhiteMode)
            {
                twoBallNoVisionWhite();
                status = "TwoBallNoVisionWhite: ";
            }
            
            else if(oneBallVisionWhiteMode)
            {
                oneBallVisionWhite();
                status = "OneBallVisionWhite: ";
            }
            
            else if(twoBallVisionWhiteMode)
            {
                twoBallVisionWhite();
                status = "TwoBallVisionWhite: ";
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
    private void noBallNoVisionWhite()
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
     * Start white, shoot one ball, drive forward
     */
    private void oneBallNoVisionWhite()
    {
        switch(autoStep)
        {
            case 0: // Put pickup down
            {
                pickup.down();
                autoTimer.reset();
                autoStep++;
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
     * Shoot in high goal when its hot, move forward afterwards
     */
    private void oneBallVisionWhite()
    {
        switch(autoStep)
        {
            case 0: // Put pickup down
            {
                pickup.down();
                autoTimer.reset();
                autoStep++;
                break;
            }
            
            case 1: // See if goal we're pointed at is hot
            {
                if(vision.foundHotTarget())
                {
                    autoTimer.reset();
                    autoStep++;
                    break;
                }
            }
            
            case 2: // Shoot one ball
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
            
            case 3: // Drive to alliance zone 
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
     * Start white, shoot 1st ball, pick up 2nd ball, shoot 2nd ball
     */
    private void twoBallNoVisionWhite()
    {
        switch(autoStep)
        {
            case 0: // Put pickup down
            {
                pickup.down();
                autoTimer.reset();
                autoStep++;
                break;
            }
            
            case 1: // Shoot 1st ball
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
            
            case 5: // Put pickup down
            {
                pickup.down();
                autoTimer.reset();
                autoStep++;
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
            
            case 7: // Drive to alliance zone
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
    
    /** 
     * Start white, find goal thats hot turn to it, shoot, turn to other, shoot, move forward
     */
    private void twoBallVisionWhite()
    {
        if(autoGlobalTimer.get() <= Config.Autonomous.visionFindTime)
            shootFirst = vision.foundHotTarget();
        
        else
        {
            switch(autoStep)
            {
                case 0: // Put pickup down
                {
                    pickup.down();
                    autoTimer.reset();
                    autoStep++;
                    break;
                }
                
                case 1: // If goal in front of us is hot shootfirst, otherwise turn to other goal
                {
                    if(shootFirst || (autoTimer.get() >= Config.Autonomous.turnToOtherGoalTime))
                    {
                        drive.stop();
                        autoTimer.reset();
                        autoStep++;
                        break;
                    }
                    
                    else
                    {
                        if(startedLeft) // Turn to the right
                            drive.turnRight(Config.Autonomous.turnToOtherGoalSpeed, true);

                        else // Turn to the left
                            drive.turnLeft(Config.Autonomous.turnToOtherGoalSpeed, true);
                    }
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

                    if(autoTimer.get() >= Config.Autonomous.pickupBallTime)
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

                case 6: // Put pickup down
                {
                    pickup.down();
                    autoTimer.reset();
                    autoStep++;
                    break;
                }

                case 7: // Turn to other/original goal
                {
                    if(shootFirst) // If shot first, turn to other goal
                    {
                        if(startedLeft) // Turn to the right
                            drive.turnRight(Config.Autonomous.turnToOtherGoalSpeed, true);
                        
                        else // Turn to the left
                            drive.turnLeft(Config.Autonomous.turnToOtherGoalSpeed, true);
                    }
                    
                    else // Else, turn to original goal
                    {
                        if(startedLeft) // Turn to the left
                            drive.turnLeft(Config.Autonomous.turnToOtherGoalSpeed, true);
                        
                        else // Turn to the right
                            drive.turnRight(Config.Autonomous.turnToOtherGoalSpeed, true);
                    }
                    
                    if(autoTimer.get() >= Config.Autonomous.turnToOtherGoalTime)
                    {
                        drive.stop();
                        autoTimer.reset();
                        autoStep++;
                        break;
                    }
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
                    if(shootFirst) // If shot first, turn back to original goal
                    {
                        if(startedLeft) // Turn to the left
                            drive.turnLeft(Config.Autonomous.turnToOtherGoalSpeed, true);
                            
                        else // Turn to the right
                            drive.turnRight(Config.Autonomous.turnToOtherGoalSpeed, true);
                    }
                    
                    else if(!shootFirst || (autoTimer.get() >= Config.Autonomous.turnToOtherGoalTime))
                    {
                        drive.stop();
                        autoTimer.reset();
                        autoStep++;
                        break;
                    }
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
}