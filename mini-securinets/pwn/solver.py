def ROR(x, n, bits = 8):
    mask = (2**n) - 1
    mask_bits = x & mask
    return (x >> n) | (mask_bits << (bits - n))
 
def ROL(x, n, bits = 8):
    return ROR(x, bits - n, bits)


s="\xd0\x99\xe4\x99\xbe\xbc\x60\xba\xbe\xb3\x60"
res=""
for i in range(len(s)):
	if (i%2==1):
		a= ROL(ord(s[i]),1)
		res+=chr(a)
	else:
		a= ROR(ord(s[i]),1)
		res+=chr(a)
print res
