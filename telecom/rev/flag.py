from pwn import *


#p=remote("192.168.125.17",6633)
p=process("./service")
pause()
p.send("7mc3yMy9COAgc5WG7Uw937A9um1iaY9w5qOy5glp")
p.interactive()
p.send("4pRdi9cHh5UDS7ElXxw2x1qlFj28PAh7Yx4Xf6bD")


