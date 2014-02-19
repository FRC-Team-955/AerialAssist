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
    
    public void reset()
    {
        //vision.turnOffPi();
        catapult.setCatMotor(0);
        drive.setSpeed(0, 0);
        pickup.setMotor(0);
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
        drive.setSpeed(0, 0);
        pickup.setMotor(0);
    }
    
    private void getAutoVersion()
    {
        if(Station.getDitigalIn(Config.Station.chnNoBallNoVisionWhite))
            noBallNoVisionWhiteMode = true;
        
        else if(Station.getDitigalIn(Config.Station.chnOneBallNoVisionWhite))
            oneBallNoVisionWhiteMode = true;
        
        else if(Station.getDitigalIn(Config.Station.chnTwoBallNoVisionWhite))
            twoBallNoVisionWhiteMode = true;
    }
    
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
                drive.setSpeed(Config.Autonomous.driveToAllianceSpeed, Config.Autonomous.driveToAllianceSpeed);
                
                if(autoTimer.get() >= Config.Autonomous.driveToAllianceTime)
                {
                    drive.setSpeed(0, 0);
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
     * Start white, drive forward to alliance zone, shoot
     */
    private void oneBallNoVisionWhite()
    {
        switch(autoStep)
        {
            case 0: // Drive to alliance zone 
            {
                drive.setSpeed(Config.Autonomous.driveToAllianceSpeed, Config.Autonomous.driveToAllianceSpeed);
                
                if(autoTimer.get() >= Config.Autonomous.driveToAllianceTime)
                {
                    drive.setSpeed(0, 0);
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
            
            case 1: // Shoot one ball
            {
                catapult.setCatMotor(Config.Catapult.fireSpeed);
                
                if(autoTimer.get() >= Config.Autonomous.maxShootTime)
                {
                    catapult.setCatMotor(0);
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
     * Start white, partially pick up 2nd ball, drive to alliance, shoot both
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
                
                if(autoTimer.get() >= Config.Autonomous.maxShootTime)
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
            
            case 4:
            {
                if(autoTimer.get() >= Config.Autonomous.pickupMoveUpTime)
                {
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
            
            case 5: // Shoot 2nd ball
            {
                catapult.setCatMotor(Config.Catapult.fireSpeed);
                
                if(autoTimer.get() >= Config.Autonomous.maxShootTime)
                {
                    catapult.setCatMotor(0);
                    autoTimer.reset();
                    autoStep++;
                }
                
                break;
            }
            
            case 6: // Drive to alliance zone
            {
                drive.setSpeed(Config.Autonomous.driveToAllianceTime, Config.Autonomous.driveToAllianceTime);
                
                if(autoTimer.get() >= Config.Autonomous.driveToAllianceTime)
                {
                    drive.setSpeed(0, 0);
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