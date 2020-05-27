from pwn import *


context.log_level="error"
flag=list()
for i in range(256):
	p=process("./pro")
	p.sendline("98416")
	p.sendline(str(i))
	try:
		p.recv(timeout=2)
		print "YES :",i
		flag.append(i)
		p.close()
	except:
		print "NO :",i

print flag
