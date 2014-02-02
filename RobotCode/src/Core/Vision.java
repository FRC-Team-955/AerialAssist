/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

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
		table = NetworkTable.getTable(Config.NetworkTable.tableId);
	}
	
	public double getDistance()
	{
		return table.getNumber(Config.NetworkTable.goalDistanceId);
	}
	
	public boolean foundHotTarget()
	{
		return table.getBoolean(Config.NetworkTable.isGoalHotId);
	}
	
	public boolean foundHorzTarget()
	{
		return table.getBoolean(Config.NetworkTable.isGoalHorzId);
	}
	
	public void stopVision()
	{
		table.putBoolean(Config.NetworkTable.runVisionId, false);
	}
	
	public void runVision()
	{
		table.putBoolean(Config.NetworkTable.runVisionId, true);
	}
}