from PIL import Image


directory="./Puz/p-"


new = Image.new('RGB', (340, 100),color=(255,255,255))
new_pix=new.load()


for i in range(340):
	img=Image.open(directory+str(i)+".png")
	img=img.convert('RGB')
	pix=img.load()
	print "image ",i
	for j in range(100):
		
		if pix[0,j] > (200,200,200):
			new_pix[i,j]=(255,255,255)
		else:
			new_pix[i,j]=(0,0,0) 




	

new.save("flag.png")