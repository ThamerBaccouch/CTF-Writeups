#!/usr/bin/python
import math

flag='TODO'

voyelle=['A','E','I','O','U','Y']
output=[]
x=1

for c in flag:
	if c.upper() not in voyelle :
		a=((ord(c.upper())-64)+math.factorial(x)) %26
		output.append(chr(a+64))
		if x>3:
			x=1
		else:
			x+=1
	else:
		output.append(c.upper())


print output


#['I', 'T', 'A', 'E', 'A', 'M', 'K', 'I', 'T', 'E', 'K', 'X', 'E', 'S', 'O', 'O', 'K']