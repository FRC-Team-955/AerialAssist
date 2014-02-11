/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;

/**
 *
 * @author raiderbot-3
 */
public class Config 
{

    public static class Diagnostics {
        /*
            Times for Marco
        */
        public static double backTime = 0;
        public static double forwardTime = 0;
        public static double rightTime = 0;
        public static double leftTime = 0;
        public static double cockTime = 0;
        

    }
       public class Drive
    {
        /*
            Motor channels        
        */
        public static final int chnLeft1 = 1; 
        public static final int chnLeft2 = 2;
        public static final int chnLeft3 = 3;
        public static final int chnRight1 = 8;
        public static final int chnRight2 = 9;
        public static final int chnRight3 = 10;
        
		
		public static final int switchButton = 10;
        /*
            The speed of the steps at which the talon will take to get to the 
            target speed  
        */
        public static final double rampRate = 0.125;
    }
	public class NetworkTable
	{
		public static final String tableId = "955";
		public static final String gyroAngleId = "gyroAngle";
		public static final String goalDistanceId = "goalDistance";
		public static final String isGoalHotId = "isGoalHot";
		public static final String isGoalHorzId = "isGoalHorz";
		public static final String runVisionId = "runVision";
	}
    public class Joystick
    {
        /*
            The channel number of the buttons
        */
        
        public static final int chn = 1;
        
        /*
            The number of buttons of the joystick 
        */
        
        public static final int numberOfButtons = 12;
    }
    public class Catapult
    {
        /*
            The loading times for cock and firing
        */        
        public static final double fireTime = 7.0;
        public static final int chnLS = 1;
        
        /*
            The channel number for the catapult
        */
        public static final int chnCat = 12;
        
        /*
            The speeds at which the catapult motors moves to fire and load
        */
        public static final double preFireSpeed = -1.0;
        public static final double fireSpeed = -1.0;
        
        /*
            The buttons on the joystick for loading
        */
        public static final int catFireButton = 6;
             
    }
    public class toBeChanged
    {
        public static final double min = 2.0; 
    }
    public class Pickup {
        /*
            The talon number for the pick up motor
        */
        public static final int pickupTalon1 = 5;
        /*
            The speed that the pickup motor runs
        */
        public static final double pickupSpeed = 1.0;
        /*
            The time that the motor runs before stopping
        */
        public static final double pickupTime = 5.0;
        /*
            The button on the joystick that activates the pickup
        */
        public static final int button = 5;
    }
    public class Auto {
        /*
            The amount of time the robot will drive forward during auto
        */
        public static final double driveTime = 5.0;
    }
}
