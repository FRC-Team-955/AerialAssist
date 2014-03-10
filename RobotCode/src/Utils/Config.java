package Utils;

/**
 *
 * @author raiderbot-3
 */
public class Config 
{
    public class Drive
    {
        // Motor channels
        public static final int chnLeft1 = 2; 
        public static final int chnLeft2 = 3;
        public static final int chnLeft3 = 4;
        public static final int chnRight1 = 7;
        public static final int chnRight2 = 8;
        public static final int chnRight3 = 9;
    }
    
    public class Joystick
    {
        public static final int chn = 1;
        public static final int numberOfButtons = 12;
        public static final int chnDpadVert = 5;
        public static final int chnDpadHorz = 6;
        public static final double minDpadVal = 0.2;
        
        // Buttons
        public static final int btFlipDriveDir = 12;
        public static final int btMovePickupSols = 4;
        public static final int btStopPickupMotor = 2;
        public static final int btPickupOutward = 3;
        public static final int btPickupInward = 1;
        public static final int btFireCatapult = 6;
        public static final int btStopCatapult = 5;
        public static final int btSlowMode = 9;
        public static final int btManualFire = 7;
        public static final int btAutoFire = 8;
    }
    
    public class Catapult
    {
        public static final int chnLimitSwitch = 1;
        public static final int chnCat1 = 10;
        public static final int chnCat2 = 6;
        public static final double fireSpeed = 1.0;   
        public static final double manualSpeed = 0.25;
    }
    
    public class Station
    {
        // Print lines, 1-6 only
        public static final int catapultManualFire = 1;
        public static final int driveFlipped = 2;
        public static final int driveSlowMode = 3;
        public static final int pickupSpeed = 4;
        public static final int autonomousStep = 5;
        public static final int mainCodeType = 6;
        
        // Digital Channels 1-8 available only
        public static final int chnDebugMode = 1;
        public static final int chnPrefSideLeft = 2;
        public static final int chnDriveForwardOnly = 3;
        public static final int chnOneBallNoVision = 4;
        public static final int chnTwoBallNoVision = 5;
        public static final int chnOneBallVision = 6;
        public static final int chnTwoBallVision = 7;
    }
    
    public class Pickup 
    {
        public static final int chnPickUpMotor1 = 1;
        public static final int solPortOne = 1;
        public static final int solPortTwo = 2;
        public static final double motorSpeed = 1.0; 
    }
    
    public class Compressor
    {
        public static final int chnDigOutCompressor = 2;
        public static final int chnDigInPressure = 3;
    }
    
    public class MyTalon
    {
        public static final double rampRate = 0.125;
    }
    
    public class Vision
    {
        public static final String tableId = "955";
        public static final String isGoalHotId = "isGoalHot";
        public static final String runVisionId = "runVision";
        public static final String shutDownId = "shutdown";
        public static final String prefSideLeftId = "prefSideLeft";
        public static final String debugModeId = "debugMode";
    }
    
    public class Autonomous
    {
        // Max auto time
        public static final int maxAutoTime = 10;
        
        // Shoot time
        public static final double minShootTime = 1;
        
        // Pickup time
        public static final double pickupBallTime = 1.0;
        public static final double pickupMoveUpTime = 0.75;
        public static final double ballSettleTime = 1.5;
        
        // Drive time
        public static final double driveToAllianceTime = 0.55;
        public static final double driveToAllianceSpeed = 1;
        public static final double turnToOtherGoalTime = 1;
        public static final double turnToOtherGoalSpeed = 0.75;
        
        // Vision
        public static final double visionFindTime = 1;
    }
}