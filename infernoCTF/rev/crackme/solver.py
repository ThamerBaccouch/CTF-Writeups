from pwn import *



inp="1_4m_th3_wh1t3r0s3"
chr1="ADGJLQETUOZCBM10"
chr2="sfhkwryipxvn5238"
flag=""
for char in inp:
	ch=bin(ord(char))[2:].rjust(8,"\x30")
	f1=""
	f2=""
	for i in range(0,8,2):
		f1+=ch[i]
		f2+=ch[i+1]
	f1_new=""
	for i in f1:
		if i=='0':
			f1_new+="1"
		else:
			f1_new+="0"
	f2_new=""
	for i in f2:
		if i=='0':
			f2_new+="1"
		else:
			f2_new+="0"

	num1=int(f1_new,2)
	num2=int(f2_new,2)
	flag+=chr1[num1]
	flag+=chr2[num2]
print flag