from pwn import *


p=remote("212.47.229.1", 33004)
#1
p.sendline("1")
#2
p.sendline("4")
#3
p.sendline("7")
#4
p.sendline("10")
#5
p.sendline("13")
#6
p.sendline("16")
#7
p.sendline("19")
#8
p.sendline("22")
#9
p.sendline("25")
#10
p.sendline("0.30000000000000004")

p.interactive()