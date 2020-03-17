**## black-hole**

Let's start first by static analysis:

![IDA  Pro decompilation](https://i.ibb.co/0DF104w/Screenshot-from-2020-03-17-20-29-17.png)

we can spot a BOF because we a while loop which reads our input character by character until it receives a "\n" character.

Great what's next then ?

we can overwrite the saved eip to jump to win function ? theoratically  we can but somehow it didn't work i keep  receiving segmentation fault inside printf internals. i didnt bother with it too much i decided to ROP it to leak libc then pop a shell using system(/bin/sh)

our first ROP chain will look like this :

----------------
pop rdi
----------------
libc_start_main_got_entry
----------------
puts_plt
----------------
entry point of the binary (to restart the program)
----------------

then send the character 'd' 7 times to get out of the second while loop in the picture to return.

we now have the libc leaked.
using the correct libc calculate offset of the  libc_start_main to get libc base
and from that we determine system and /bin/sh.

our second payload will look like this:
------------------
ret
------------------
pop rdi
------------------
/bin/sh
------------------
system
------------------

the first ret is to allign the stack because 64bit binaries need 16-bytes allignements on the stack.

and boom we have a shell.

you can check the solver.py script.



