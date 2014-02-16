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
        public static final int btFlipDriveDir = 10;
        public static final int btMovePickupSols = 6;
        public static final int btStopPickupMotor = 2;
        public static final int btPickupOutward = 4;
        public static final int btPickupInward = 1;
        public static final int btFireCatapult = 8;
        public static final int btStopCatapult = 7;
        public static final int btSlowMode = 9;
        public static final int btManualFire = 5;
        public static final int btAutoFire = 3;
    }
    
    public class Catapult
    {
        public static final int chnLimitSwitch = 1;
        public static final int chnCat1 = 10;
        public static final int chnCat2 = 6;
        public static final double fireSpeed = 1.0;   
        public static final double manualSpeed = 0.5;
    }
    
    public class Station
    {
        // Print lines, 1-6 only
        public static final int catapult = 1;
        public static final int drive = 2;
        public static final int pickup = 3;
        public static final int main = 4;
    }
    
    public class Pickup 
    {
        public static final int chnPickUpMotor1 = 1;
        public static final int solRightPortOne = 1;
        public static final int solRightPortTwo = 2;
        public static final double motorSpeed = 1.0; 
    }
    
    public class Compressor
    {
        public static final int chnDigOutCompressor = 2;
        public static final int chnDigInPressure = 5;
    }
    
    public class MyTalon
    {
        public static final double rampRate = 0.125;
    }
}