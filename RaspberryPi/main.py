#!/usr/bin/env python
import logging
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
	logging.basicConfig(filename='/var/log/robovision/robovision.log',level=logging.DEBUG)
	logging.debug("vision services started.")
	#logging.debug("uid {} euid {}".format(os.getuid(), os.geteuid()))

	# Table name  = team number, used for ip address
	tableName = "955"
	#table = NetworkTableClient(tableName)
	tableDirectory = '/' + tableName + '/'
	logging.debug("Network table initialized.")
	# Id's for reading data from networktable
	isGoalHotId = "isGoalHot"
	shutDownPi = "shutdown"
	debugId = "debugMode"
	runVisionId = "runVision"
	prefSideLeftId = "prefSideLeft"

	try:
		table.setValue(tableDirectory + isGoalHotId, False) # Set foundHotTarget

		while True:
			runVision = table.getValue(tableDirectory + runVisionId)
			debugMode = table.getValue(tableDirectory + debugId)
			prefSideLeft = table.getValue(tableDirectory + prefSideLeftId)

			if runVision: # Check if runVision is true
				vision.update(prefSideLeft, debugMode)	# Run vision, check whether debug mode is true
				table.setValue(tableDirectory + isGoalHotId, vision.getFoundHotTarget()) # Set foundHotTarget

			if table.getValue(tableDirectory + shutDownPi):
				os.sys("sudo shutdown -h now")

			time.sleep(1.0 / 4.0)
	except Exception as ex:
		logging.error(ex)
	logging.debug("Done!")
