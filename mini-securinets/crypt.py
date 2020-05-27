from pwn import *
import base64

context.log_level='error'
p=remote('3.89.187.28',1337)
q2=open("./2Q",'r').readline()
k9s=open("./9ks","r").readline()
for i in range(10):
	try:
		data=p.recvuntil("Paper,Scissors,Rock ?")
		data=data[:-24]
		print data
		if q2 == data:
			p.sendline("Paper")
			p.interactive()
		elif k9s == data:
			p.sendline("Rock")
		else:
			p.sendline("Scissors")
	except:
		p.interactive()

"""for i in range(5):
	data=p.recvuntil("Paper,Scissors,Rock ?")
	print data
	data=data[:-24]
	data= data[-4:]
	print data
	if "9k" in data:
		p.sendline("Rock")
	elif "2Q" in data:
		p.sendline("Paper")
	else:
		p.sendline("Scissors")
	print p.recvline()
	if "2Q==" in data:
		p.sendline("Paper")
	elif "9k=" in data:
		p.sendline("Rock")
	else:
		print "NOT"
	print p.recvline()
	print p.recvline()
"""