from pwn import *
import hashlib,binascii
import time 
import crypt
context.log_level='error'
con = remote('ctfchallenges.ritsec.club',8080)

test = 1
i=1
while test :
	con.close()
	print "attempt ",i
	con = remote('ctfchallenges.ritsec.club',8080)
	con.recvline()
	con.recvline()
	lists=con.recvline()
	if( ("500-worst-passwords.txt" in lists) and ("darkweb2017-top10000.txt" in lists) and ("probable-v2-top12000.txt" in lists)):
		print lists
		break
	i+=1



lines=open("all.txt","r").readlines()
count=1

while(1):
	print count
	h=con.recvline()
	print h
	print "#########"
	if "$" not in h:
		print "md4"
		for line in lines:
			#print count
			#print line[0:-1]
			try:
				h2=hashlib.new('md4',line[0:-1].encode('utf-16le')).hexdigest()
				if h2 in h[0:-1]:
					print "FOUND"
					con.sendline(line[0:-1])
					print con.recvline()
					break
			except:
				print "not valid hash"
	else:
		print "sha512crypt"
		salt=h.split("$")[2]
		for line in lines:
			#print line[0:-1]
			#print line[0:-1]
			h2=crypt.crypt(line[0:-1],"$6$"+salt)
			if h2 == h[0:-1]:
				print "FOUND"
				con.sendline(line[0:-1])
				print con.recvline()
				break
	count +=1
			
