import string

inp="R:I:T:S:E:C{:40:50:56:08:82:58:81:08:18:E:T:I:48:E:88:40:50:56:R:15:56:11:18:E:R:S:}"
mod_inp="59:87:57:51:85:80{:40:50:56:08:82:58:81:08:18:85:57:87:48:85:88:40:50:56:59:15:56:11:18:85:59:51:}"

lst= inp.split(":")[6:-1]
printrable="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"


def getUnique(arr):
	new_arr=[]
	for i in arr:
		if int(i) not in new_arr:
			new_arr.append(int(i))
	return new_arr
arr=[]
for i in lst:
	if i.isdigit():
		arr.append(i)

num_array=getUnique(arr)
print num_array
print len(num_array)
print printrable
mod_inp=mod_inp.replace("{","")
mod_inp=mod_inp.replace("}","")

print "###########################"
for i in getUnique(mod_inp.split(':')[0:-1]):
	print i , " -----occur----- ",mod_inp.count(str(i))