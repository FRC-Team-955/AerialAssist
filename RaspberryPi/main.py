import vision as vision
from ntClient import *
import time

################################################################################
#
# main
#
################################################################################
# if this script is run directly by python, then __name__ is '__main__'.  If it
# is run because it is imported, then __name__ is the module name.

if __name__ == '__main__': 

	# Table name  = team number, used for ip address
	tableName = "955"
	table = NetworkTableClient(tableName)
	tableDirectory = '/' + tableName + '/'

	# Id's for reading data from networktable
	isGoalHotId = "isGoalHot"
	isGoalHorzId = "isGoalHorz"
	goalDistanceId = "goalDistance"

	# Id's for setting data to networktable
	gyroAngleId = "gyroAngle"
	gyroAngle = 0.0

	# Get the pref side
	prefSide = table.getValue(tableDirectory + "prefSide")

	# Get the debug value
	debugMode = table.getValue(tableDirectory + "debugMode")

	while  True:
		gyroAngle = table.getValue(tableDirectory + gyroAngleId)
    	vision.update(gyroAngle, prefSide, debugMode)
    	table.setValue(tableDirectory + goalDistanceId, vision.getDistace())
    	table.setValue(tableDirectory + isGoalHotId, vision.getFoundHotTarget())
    	table.setValue(tableDirectory + isGoalHorzId, vision.getFoundHorzTarget())

    	time.sleep(1.0 / 4.0)