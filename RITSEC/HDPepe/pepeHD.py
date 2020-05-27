from PIL import Image


img=Image.open("./image.exif")
#result=Image.new(mode = "RGB", size = img.size)
#pixelsRes=result.load()
pixels=img.load()
w,h = img.size
black=(0,0,0)
white=(255,255,255)
exif_data = img._getexif()
print exif_data
"""
for i in range(w):
	for j in range(h):
		if pixels[i,j] < (30,30,30,255):
			pixelsRes[i,j] = black
		else:
			pixelsRes[i,j] = white


result.save("thamer.png")
"""