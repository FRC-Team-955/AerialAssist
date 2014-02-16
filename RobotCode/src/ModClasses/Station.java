package ModClasses;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationLCD;

/**
 *
 * @author Merfoo
 */
public class Station 
{
    /**
     * Gets the button status from the driverstation, 1 - 8 available.
     * @param iChan
     * @return 
     */
    public static boolean getDitigalIn(int iChan)
    {
        return DriverStation.getInstance().getDigitalIn(iChan);
    }
    
	/**
     * Gets the analog value from the driverstation, 1 - 8 available.
     * @param iChan
     * @return 
     */
    public static double getAnalogIn(int iChan)
    {
        return DriverStation.getInstance().getAnalogIn(iChan);
    }

    /**
     * Prints specified message to the driver station on the corresponding line
     * 1-6 are available.
     */
    public static void print(int line, String msg)
    {
        DriverStationLCD.Line printLine = null;
        
        switch(line)
        {
            case 1: printLine = DriverStationLCD.Line.kUser1; break;
            case 2: printLine = DriverStationLCD.Line.kUser2; break;
            case 3: printLine = DriverStationLCD.Line.kUser3; break;
            case 4: printLine = DriverStationLCD.Line.kUser4; break;
            case 5: printLine = DriverStationLCD.Line.kUser5; break;
            case 6: printLine = DriverStationLCD.Line.kUser6; break;
        }
        
        DriverStationLCD.getInstance().println(printLine, 1, msg);
        DriverStationLCD.getInstance().updateLCD(); 
    }
    
    public static void clearAllText()
    {
        DriverStationLCD.getInstance().updateLCD();
    }
}