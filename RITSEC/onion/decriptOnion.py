import base64

import re
base64_regex="^(?:[a-zA-Z0-9+\/]{4})*(?:|(?:[a-zA-Z0-9+\/]{3}=)|(?:[a-zA-Z0-9+\/]{2}==)|(?:[a-zA-Z0-9+\/]{1}===))$"

base32_regex="^(?:[A-Z2-7]{8})*(?:[A-Z2-7]{2}={6}|[A-Z2-7]{4}={4}|[A-Z2-7]{5}={3}|[A-Z2-7]{7}=)?$"

base16_regex="^[0-9A-F]+$"

flag=open("pass.txt").read()
path=[]

def show(path,flag):
	print "###############################################"
	print "flag length :",len(flag)
	print "last 100 of flag :",flag[len(flag)-100::]
	print "-----------------------------------------------"
	print "path length :",len(path)
	print "path :",path
	print "###############################################"
	
def get_Path(flag,i,path):
	print "testing base16 :",i
	one =0
	if re.match(base16_regex, flag):
		print "matched --- 16"
		one=1
		path.append(16)
		get_Path(base64.b16decode(flag),i+1,path)
		path.pop()
	
	print "testing base32 :",i
	if re.match(base32_regex, flag):
		print "matched --- 32"
		one=1
		path.append(32)
		get_Path(base64.b32decode(flag),i+1,path)
		path.pop()
	
	print "testing base64 :",i
	if re.match(base64_regex, flag):
		print "matched --- 64"
		one=1
		path.append(64)
		get_Path(base64.b64decode(flag),i+1,path)
		path.pop()
	if one == 0:
		show(path,flag)

get_Path(flag,1,path)


