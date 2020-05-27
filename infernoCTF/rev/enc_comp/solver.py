from PIL import Image


img=Image.open("encrypted1.png")

w,h=img.size

pixels=img.load()

for i in range(w):
	for j in range(h):
		print pixels[i,j]