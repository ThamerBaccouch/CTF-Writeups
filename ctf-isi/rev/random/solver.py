import random

random.seed('random')
arr=[0x12,0x5e,0x7a,0x3f,0x61,0x8,0x3d,0x66,0x34,0x2a,0x58,0x72,0x37,0x39,0x70,0x2d,0x2b,0x4c,0x15,0x68,0x75,0x5a,0x5,0xe,0x1f,0x58]
c = [i^random.randint(0,100) for i in arr]



print(''.join(chr(x) for x in c))