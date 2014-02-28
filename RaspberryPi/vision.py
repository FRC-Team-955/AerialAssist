import cv2
import math
import numpy as np
import logging
from rectangle import Rectangle

camera = cv2.VideoCapture(0)
foundHotTarget = False
viewAngleVert = 19.832 
resHalfY = 240
resHalfX = 320
distanceFromTarget = 229
horizTarget = Rectangle(0.0, 0.0, 23.5, 4.0)
imgNameIndex = 1
imgNameMax = 100

def update(prefSideLeft, isDebug):
	logging.debug("update() {} {}".format(isDebug, imgNameIndex))

	# Get color image from camera
	ret, img = camera.read() # img.shape 640x480 image

	# Convert to hsv img
	hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)

	# Keep only green objects
	# higher s = less white, higher v = less black
	lowerGreen = np.array([70, 70, 180])
	upperGreen = np.array([110, 130,255])
	filteredGreen = cv2.inRange(hsv, lowerGreen, upperGreen)

	# Filter out small objects
	filteredGreen = cv2.morphologyEx(filteredGreen, cv2.MORPH_OPEN, np.ones((3, 3)))

	# Find all contours, countours is vector of points that are connected to make up a shape
	contours, hierarchy = cv2.findContours(filteredGreen, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)
	
	# Resetting foundHotTarget
	global foundHotTarget
	foundHotTarget = False;

	# Parse contours, calculate distance and check if it's the hot target
	for shape in contours:
		target = Rectangle(*cv2.boundingRect(shape))

		# Filter out small contours
		if target.getArea() > 300 and target.getArea() < 10000:

			# If the width of the target is greater than its height then it's probably the hot target
			if target.width >= target.height * 2.5:
				foundHotTarget = inPrefSide(target.x + (target.width / 2), prefSideLeft)

				if isDebug:
					drawRect(img, target)
					viewAngle = computeAngle(horizTarget.height, target.height, distanceFromTarget)
					logging.debug("Hot Target: " + str(foundHotTarget) + ", New Angle: " + str(viewAngle))

	# Save img so we can analyze it
	if isDebug:
		saveImg(img)

def getFoundHotTarget():
	return foundHotTarget

def computeAngle(realHeight, targetHeight, distance):
	return math.atan(((realHeight / targetHeight) * resHalfY) / distance) * 180.0 / math.pi

def drawRect(img, rect):
	cv2.rectangle(img, (int(rect.x), int(rect.y)), (int(rect.x + rect.width), int(rect.y + rect.height)), 177, 2)

def round(value):
	return math.floor((value * 100) + 0.5) / 100

def saveImg(img):
	logging.debug("saving")
	global imgNameIndex

	if foundHotTarget:
		cv2.imwrite(str(imgNameIndex) + "HOT.png", img)

	else:
		cv2.imwrite(str(imgNameIndex) + ".png", img)

	imgNameIndex += 1
	if imgNameIndex > imgNameMax:
		imgNameIndex = 1

def inPrefSide(x, prefSideLeft):
	logging.debug("x: " + str(x))
	
	if prefSideLeft and x <= resHalfX:
		return True
	elif not prefSideLeft and x >= resHalfX:
		return True
	return False