from pwn import *
import string

printable=string.ascii_letters+string.digits
printable+="+-{}/*._()[]"
context.log_level = 'error'
#print printable
flag=""
for j in range(1,1000):
	if(len(str(j))==1): ch="00"+str(j)+".c.out"
	if(len(str(j))==2): ch="0"+str(j)+".c.out"
	if(len(str(j))==3): ch=str(j)+".c.out"
	a=ELF(ch)
	for i in printable:
		b=a.process()
		b.recvline()
		b.sendline(i)
		if "Nope" not in b.recvline():
			print j," ",i
			flag+=i
			b.close()
			break
		b.close()

print flag