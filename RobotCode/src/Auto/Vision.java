package Auto;

import Utils.Config;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 * @author RaiderPC
 */
public class Vision 
{
    private NetworkTable table;

    public Vision()
    {
        table = NetworkTable.getTable(Config.Vision.tableId);
        resetTable();
    }
    
    /**
     * Check if raspberry pi found hot target
     * @return 
     */
    public boolean foundHotTarget()
    {
        return table.getBoolean(Config.Vision.isGoalHotId);
    }
    
    /**
     * Tell raspberry pi side preference for hot target
     * @param prefLeft 
     */
    public void setPrefSideLeft(boolean prefLeft)
    {
        table.putBoolean(Config.Vision.prefSideLeftId, prefLeft);
    }
    
    /**
     * Turn off the raspberry pi
     */
    public void turnOffPi()
    {
        table.putBoolean(Config.Vision.shutDownId, true);
    }
    
    /**
     * Set debug mode for raspberry pi
     * @param state 
     */
    public void setDebugMode(boolean state)
    {
        table.putBoolean(Config.Vision.debugModeId, state);
    }
    
    /**
     * Initializes all the values in the vision network table
     */
    public void resetTable()
    {
        table.putBoolean(Config.Vision.prefSideLeftId, false);
        table.putBoolean(Config.Vision.debugModeId, true);
        table.putBoolean(Config.Vision.shutDownId, false);
        table.putBoolean(Config.Vision.isGoalHotId, false);
    }
}