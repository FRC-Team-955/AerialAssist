package Auto;

import Core.*;
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
    }
    
    public boolean foundHotTarget()
    {
        return table.getBoolean(Config.Vision.isGoalHotId);
    }
    
    public void setPrefSideLeft(boolean prefLeft)
    {
        table.putBoolean(Config.Vision.prefSideLeftId, prefLeft);
    }
    
    public void startVision()
    {
        table.putBoolean(Config.Vision.runVisionId, true);
    }
     
    public void stopVision()
    {
        table.putBoolean(Config.Vision.runVisionId, false);
    }
    
    public void turnOffPi()
    {
        table.putBoolean(Config.Vision.shutDownId, true);
    }
}