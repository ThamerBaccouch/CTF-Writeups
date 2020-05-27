n=221714271158005222817666050308401368660908569227103024781696324826668748920975811165767447795834564642795098601291978741922902819199320110937373351090463
c=181911219573527527238029965740171013916257176675727150871927442543795832273347838144595736203869921219722358397916992708414231324510901417903137979666013
e=65537
z=1
p=[57809,57809,64453,64453,64453,64453,1552903013,3157061689,3157061689,3157061689,3157061689,3157061689,3157061689,13572582255211282411,13572582255211282411,13572582255211282411]


def egcd(a, b):
 if (a == 0):
     return [b, 0, 1]
 else:
     g, y, x = egcd(b % a, a)
     return [g, x - (b // a) * y, y]
def modInv(a, m):
 g, x, y = egcd(a, m)
 if (g != 1):
     raise Exception("[-]No modular multiplicative inverse of %d under modulus %d" % (a, m))
 else:
     return x % m

phy=1
for a in p:
	phy *= (a-1)
d = modInv(e, phy)
m = pow(c, d, n)
print(hex(m)[2:])