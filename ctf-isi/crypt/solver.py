import base64
f=open("./flag","r").read()
for i in range(4):
	f=base64.b64decode(f)

print f





