from pwn import *

p=process("./warmup")
payload=""
payload+="AAAAA"
pause()
p.sendline(payload)

p.interactive()


