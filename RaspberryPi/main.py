#!/usr/bin/env python
import logging
import vision as vision

from ntClient import *
from datetime import datetime
import os
import sys
import time

# Id's for reading data from networktable
isGoalHotId = "isGoalHot"
shutDownPi = "shutdown"
debugId = "debugMode"
prefSideLeftId = "prefSideLeft"

################################################################################
#
# main
#
################################################################################
# if this script is run directly by python, then __name__ is '__main__'.  If it
# is run because it is imported, then __name__ is the module name.

if __name__ == '__main__':

	logging.basicConfig(filename='/var/log/robovision/robovision.log', filemode='w', level=logging.DEBUG)
	logging.debug("Vision services started.")

	# Table name  = team number, used for ip address
	tableName = "955"
	tableDirectory = '/' + tableName + '/'

	table = None
	debugMode = True
	prefSideLeft = True

	# Test for table existing by setting a value.  If table not accessible, wait and try.
	# Don't use network table if we pass "-n" option on command line.
	if not (len(sys.argv) > 1 and sys.argv[1] == '-n'):
		logging.debug("Using network table.")
		while True:
			try:
				logging.debug("Initialize network table.")
				table = NetworkTableClient(tableName)
				logging.debug("Network table initialized.")
				table.setValue(tableDirectory + isGoalHotId, False) # Set foundHotTarget
				break # Leave the while loop if the table is accessible
			except:
				logging.error("Error initializing network table.")
				time.sleep(1/4.)
	else:
		logging.debug("Not using network table.")

	try:
		while True:
			if table:
				debugMode = table.getValue(tableDirectory + debugId)
				prefSideLeft = table.getValue(tableDirectory + prefSideLeftId)

			vision.update(prefSideLeft, debugMode) # Run vision, check whether debug mode is true

			if table:
				table.setValue(tableDirectory + isGoalHotId, vision.getFoundHotTarget()) # Set foundHotTarget

			if table and table.getValue(tableDirectory + shutDownPi):
				os.system("sudo shutdown -h now")
				break

			now = datetime.utcnow()
			logging.debug("Time is {}:{}:{}".format(now.minute,now.second,now.microsecond/100000))
			time.sleep(1/100.)
	except Exception as ex:
		logging.error(ex)
	logging.debug("Done!")