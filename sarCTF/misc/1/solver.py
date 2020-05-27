import magic
import os

for i in range(1000):
	os.system("mv flag flag.txt")
	tp= magic.from_file("./flag.txt")
	if "bzip2" in tp:
		os.system("mv flag.txt flag.bz2")
		os.system("bzip2 -d flag.bz2")
	elif "gzip" in tp:
		os.system("mv flag.txt flag.gz")
		os.system("gunzip -d flag.gz")
	elif "tar" in tp:
		os.system("mv flag.txt flag.tar")
		os.system("tar xvf flag.tar")
	elif ("xz" in tp) or ("XZ" in tp):
		os.system("mv flag.txt flag.xz")
		os.system("xz -d flag.xz")
	elif "Zip" in tp:
		os.system("mv flag.txt flag.zip")
		os.system("unzip flag.zip")
	else:
		print "unrecognized"









