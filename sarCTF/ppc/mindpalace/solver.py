from pwn import *


context.log_level='error'
p=remote("212.47.229.1",33001)

f=""


for i in range(800):
	print i
	try:
		f+=p.recv(timeout=5)
	except:
		print "###########################################"
		print f
		break
print "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
print f





















