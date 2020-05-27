from pwn import *
import re


p=remote("212.47.229.1", 33003)

p.recvline()
p.recvline()
p.recvline()
for i in range(100):
	print i
	data=p.recvline()[3:]
	data=re.sub(" AND "," & ",data)
	data=re.sub(" XOR "," ^ ",data)
	data=re.sub(" OR "," | ",data)
	p.sendline(str(eval(data)))
	p.recvuntil("Result: ")

p.interactive()



















