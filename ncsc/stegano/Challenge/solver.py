from PIL import Image


#################### 1 ##############
"""
img=Image.new('RGB',(3000,2000),color="white")


f=open("./new.txt","r").readlines()
lst=list()
for line in f:
	line=line[1:-1].split("], [")
	line[-1]=line[-1][:-1]
	line[0]=line[0][1:]
	print line[0]
	print line[-1]
	for i in line:
		item=i.split(", ")
		lst.append((int(item[0]),int(item[1])))

pxl=img.load()

for i in lst:
	pxl[i[0],i[1]]=(0,0,0)

img.save("thamer.png")

"""

##################### 2 ############


img1=Image.open("./elliot.png")
img2=Image.open("./lastone.png")

w1,h1=img1.size
pxl1=img1.load()
pxl2=img2.load()

for i in range(w1):
	for j in range(h1):
		if(pxl1[i,j] != (0,0,0)):
			print pxl1[i,j]," ### ", pxl2[i,j]






