from pwn import *
context.log_level='error'
while(True):
	p=process(["./speed","bbb"])
	d="aaa"
	try:
		d=p.recvline()
	except:
		p.close()
	if ("size" not in d) and ("aaa" not in d):
		print d
		break
	p.close()









