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
    public class Periscope
    {
        public static final int chnSolUp = 1;
        public static final int chnSolDown = 2; 
        
        // Buttons
        public static final int btUp = 3;
        public static final int btDown = 4;
    }
    
    public class Drive
    {
        public static final int chnLeft1 = 5;
        public static final int chnLeft2 = 6;
        public static final int chnLeft3 = 7;
        public static final int chnRight1 = 8;
        public static final int chnRight2 = 9;
        public static final int chnRight3 = 10;
        public static final double rampRate = 0.125;
    }
    
    public class Joystick
    {
        public static final int chn = 1;
        public static final int numberOfButtons = 12;
    }
    
    public class Catapult
    {
        public static final double loadingTime = 7.0;
        public static final double fireTime = 7.0;      
        public static final double fireSpeed = 1.0;
        public static final double loadSpeed = 1.0;
        public static final int catLoadButton = 5;
        public static final int chnCat = 12;
        public static final int catFireButton = 6;
             
    }
    
    public class toBeChanged
    {
        public static final double min = 2.0; 
    }
    
    public class Pickup {   
        public static final double pickupSpeed = 1.0;
        public static final double pickupTime = 5.0;
        public static final int button = 5;
        public static final int pickupTalon1 = 5;
    }
    
    public class Auto {
        public static final double driveTime = 5.0;
    }
}
