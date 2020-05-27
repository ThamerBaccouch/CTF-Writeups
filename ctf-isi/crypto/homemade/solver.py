
import binascii

cipher="83cfa8cea8cbaf9c96beb988bb8bfba2a4cc969cb1"
key=[0xca,0xfe]
b=binascii.unhexlify(cipher)
arr=[]
for i in b:
	arr.append(ord(i))

test=""
for index,value in enumerate(arr):
	test+=chr((value-1)^key[index%2])


print test


"""
test="aaaaaaaaaaa"
l=2
cipher=bytearray(((arr[i]-1 ^ key[i % l]) + 1 for i in range(0,len(test))))
print(str(binascii.hexlify(cipher)))
"""