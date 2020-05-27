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

"""
img1=Image.open("./elliot.png")
img2=Image.open("./lastone.png")

w1,h1=img1.size
pxl1=img1.load()
pxl2=img2.load()

line1=""
line2=""
line3=""

for i in range(w1):
	for j in range(h1):
		if(pxl1[i,j]==(255, 0, 0)):
			line1+=chr(j)
print line1

"""

import base64
a ="O5SSO4TFEBZHK3TONFXGOIDPOV2CA33GEB2GS3LFEAQSA53FEBXGKZLEEB2G6IDHMV2CA2LUEBRGKZTPOJSSA5DPN4QGYYLUMUQCCIDQO4QDUIDUNBSV63BUON2F64BUOJ2F633GL4YXIIA="

print base64.b32decode(a)
a="U2VjdXJpbmV0c3t3M2xsX2YqKmtfczBjaTN0WV80bkRfd2gxdDNyMHMzfQ=="
print base64.b64decode(a)