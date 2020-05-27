import os
import zipfile

for i in range(200):
	print i
	cur=open("test","r").readlines()[0]
	if cur[-1]=='\n':
		cur=cur[:-1]
	if cur =="test":
		cur=open("test","r").readlines()[1]
	if cur[-1]=='\n':
		cur=cur[:-1]
	os.system("mv "+cur+" "+cur+".zip")
	with zipfile.ZipFile(cur+".zip") as file:
		file.extractall(pwd = cur)
	os.system("rm "+cur+".zip")
	os.system("ls |grep -v '.py'>test")
	











