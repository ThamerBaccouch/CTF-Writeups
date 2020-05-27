from PIL import Image




img=Image.open("Darlene.png")
pxl=img.load()
w,h=img.size

ch1=""
for i  in range(w):
	for j in range(h):
		if pxl[i,j][2]<127  and pxl[i,j][2]>31 :
			ch1+=chr(pxl[i,j][2])

print ch1







