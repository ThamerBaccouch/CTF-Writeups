import base64


lines=open("text.txt","r").readlines()

test=""
for line in lines:
	test+=line
for i in range(50):
	try:
		test=base64.b64decode(test)[62:-79]
	except:
		print test
