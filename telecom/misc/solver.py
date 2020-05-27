from pwn import *


p=remote("192.168.125.13",6731)
t=[]
p.recvuntil("name:")
p.sendline("thamer")
for i in range(5000):
	print p.recvuntil("points")
	p.recvuntil("ice >")
	p.sendline("1")
	p.recvuntil("er >")
	p.sendline("1234")
	p.recvuntil("ce >")
	p.sendline("2")
	p.recvline()
	a=p.recvline()[32:-2]
	t.append(a)
	print i

if len(t) > len(set(t)):
	print "all element are unique "
else:
	print "yet3awdou"
a=open("./aaa.txt","w")
a.write(str(t))
a.close()
print t[5+0]
print t[5+226]
print t[5+227]
print t[5+228]
p.interactive()