from PIL import Image


img=Image.open("catto.png")

pixels=img.load()

w,h=img.size
new_img=Image.new(size=img.size,mode="RGB")
new_pixels=new_img.load()
flag=""
for i in range(w):
	for j in range(h):
		if(pixels[i,j][2]<=127 and pixels[i,j][2]>=32):
			flag+=chr(pixels[i,j][2])
new_img.save("res.png")
print flag


#(x,y)->(2*x+y,x+y) MOD N