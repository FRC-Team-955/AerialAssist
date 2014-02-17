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
    private boolean shotAlready = false;
    private Timer shootTimer = new Timer();
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
    
    public void init()
    {
        vision.setPrefSideLeft(Station.getDitigalIn(Config.Station.chnPrefSideLeft));
        vision.startVision();
        autoTimer.start();
    }
    
    public void run()
    {
        if(autoTimer.get() < Config.Autonomous.maxAutoTime) // Run autonomous within autonomous time
        {
            if(autoTimer.get() < Config.Autonomous.driveForwardTime)    // Driveforward the first secs
                drive.setSpeed(Config.Autonomous.driveForwardSpeed, Config.Autonomous.driveForwardSpeed);
            
            else
            {
                drive.setSpeed(0, 0);
                
                if(!shotAlready)
                {
                    if(vision.foundHotTarget() || autoTimer.get() > 5)
                    {
                        shootTimer.start();
                        catapult.setCatMotor(Config.Catapult.fireSpeed);
                        shotAlready = true;
                    }
                }
            }
            
            if(shootTimer.get() >= Config.Autonomous.maxShootTime)
                catapult.setCatMotor(0);
        }
        
        else
        {
            catapult.setCatMotor(0);
            drive.setSpeed(0, 0);            
        }
    }
    
    public void stop()
    {
        vision.turnOffPi();
        catapult.setCatMotor(0);
        drive.setSpeed(0, 0);
        shotAlready = false;
        shootTimer.stop();
        shootTimer.reset();
        autoTimer.stop();
        autoTimer.reset();
    }
}