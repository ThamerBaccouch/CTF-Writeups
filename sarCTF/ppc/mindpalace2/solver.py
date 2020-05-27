from pwn import *

import string
rot13 = string.maketrans( 
    "ABCDEFGHIJKLMabcdefghijklmNOPQRSTUVWXYZnopqrstuvwxyz", 
    "NOPQRSTUVWXYZnopqrstuvwxyzABCDEFGHIJKLMabcdefghijklm")



p=remote("212.47.229.1", 33002)

p.recvline()
p.recvline()
p.recvline()
p.recvline()
for i in range(99):
	print i
	line=p.recvline()
	if( "Message" in line):
		data=line[len("Message:  "):-1]
		print data
		print p.recv()
		p.sendline(string.translate(data, rot13))
	else:
		p.interactive()

p.interactive()