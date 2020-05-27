import base64
import string
test=""
for n in range(1):
	lines=open("./Files/new.txt","r").readlines()
	for line in lines:
		if len(line) != 1:			
			test+=line[0:-1]
			print base64.b64decode(line[0:-1])
 


