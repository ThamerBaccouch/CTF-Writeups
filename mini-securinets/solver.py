


f=open("./thamer","r").readlines()
res=""
for i in f:
	res+=i

print res.decode('hex')