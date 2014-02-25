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
    private Timer autoGlobalTimer = new Timer();
    private Timer autoTimer = new Timer();
    //private Vision vision = new Vision();
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
//        vision.setPrefSideLeft(Station.getDitigalIn(Config.Station.chnPrefSideLeft));
//        vision.setDebugMode(Station.getDitigalIn(Config.Station.chnDebugMode));
//        vision.startVision();
        autoGlobalTimer.reset();
        autoGlobalTimer.start();
        autoTimer.reset();
        autoTimer.start();
        autoStep = 0;
        getAutoVersion();
    }
    
    /**
     * Resets/stops eveything autonomous
     */
    public void reset()
    {
        //vision.turnOffPi();
        stopEverything();
        autoTimer.stop();
        autoGlobalTimer.stop();
        noBallNoVisionWhiteMode = false;
        oneBallNoVisionWhiteMode = false;
        twoBallNoVisionWhiteMode = false;
    }
    
    /**
     * Stop all moving parts on the robot except pickup
     */
    private void stopEverything()
    {
        catapult.setCatMotor(0);
        drive.setSpeed(0, 0, false);
        pickup.setMotor(0);
    }
    
    /**
     * Gets which autonomous code should be ran from driverstation digitalIn
     */
    private void getAutoVersion()
    {
        if(Station.getDitigalIn(Config.Station.chnNoBallNoVisionWhite))
            noBallNoVisionWhiteMode = true;
        
        else if(Station.getDitigalIn(Config.Station.chnOneBallNoVisionWhite))
            oneBallNoVisionWhiteMode = true;
        
        else if(Station.getDitigalIn(Config.Station.chnTwoBallNoVisionWhite))
            twoBallNoVisionWhiteMode = true;
    }
    
    /**
     * Runs the autonomous code
     */
    public void run()
    {
        if(autoTimer.get() <= Config.Autonomous.maxAutoTime)
        {
            if(noBallNoVisionWhiteMode)
                noBallNoVisionWhite();
            
            else if(oneBallNoVisionWhiteMode)
                oneBallNoVisionWhite();
            
            else if(twoBallNoVisionWhiteMode)
                twoBallNoVisionWhite();
        }
        
        else 
            stopEverything();
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
                drive.setSpeed(Config.Autonomous.driveToAllianceSpeed, Config.Autonomous.driveToAllianceSpeed, true);
                
                if(autoTimer.get() >= Config.Autonomous.driveToAllianceTime)
                {
                    drive.setSpeed(0, 0, false);
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
                catapult.setCatMotor(Config.Catapult.fireSpeed);
                
                if(autoTimer.get() >= Config.Autonomous.minShootTime && catapult.isCocked())
                {
                    catapult.setCatMotor(0);
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
            
            case 2: // Drive to alliance zone 
            {
                drive.setSpeed(Config.Autonomous.driveToAllianceSpeed, Config.Autonomous.driveToAllianceSpeed, true);
                
                if(autoTimer.get() >= Config.Autonomous.driveToAllianceTime)
                {
                    drive.setSpeed(0, 0, false);
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
                catapult.setCatMotor(Config.Catapult.fireSpeed);
                
                if(autoTimer.get() >= Config.Autonomous.minShootTime && catapult.isCocked())
                {
                    catapult.setCatMotor(0);
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
            
            case 2: // Get 2nd ball
            {
                pickup.setMotor(Config.Pickup.motorSpeed);
                
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
                    pickup.setMotor(0);
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
                catapult.setCatMotor(Config.Catapult.fireSpeed);
                
                if(autoTimer.get() >= Config.Autonomous.minShootTime && catapult.isCocked())
                {
                    catapult.setCatMotor(0);
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
            
            case 7: // Drive to alliance zone
            {
                drive.setSpeed(Config.Autonomous.driveToAllianceTime, Config.Autonomous.driveToAllianceTime, true);
                
                if(autoTimer.get() >= Config.Autonomous.driveToAllianceTime)
                {
                    drive.setSpeed(0, 0, false);
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