class Rectangle:

	def __init__(self, x = 0, y = 0, width = 0.0, height = 0.0):
		self.x = x
		self.y = y
		self.width = width
		self.height = height

	def __str__(self):
		return "x {0} y {1} width {2} height {3}".format(
			self.x, self.y, self.width, self.height)

	def getArea(self):
		return self.width * self.height