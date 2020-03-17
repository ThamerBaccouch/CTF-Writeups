**## kobayashi-maru**

let's open IDA as a first step like always, and notice all of these functions :
![](https://i.ibb.co/cghG8Jk/func.png)


after some more analysis I found this:
![](https://i.ibb.co/Z140P1L/aaa.png)

we can notice right away the format string the program reads 19 characters with a subroutine then printf(input).
after some reverse engineering to find out how to reach that function i figured out the right input.
(you will see in my solver.py script a commented block of code i used it to determine the right input to get to this function)


well now after we reach that function call how should we exploit this ? we have 19 character limit and exit() just after printf() my approach was like this.

1. since relro is disabled we can change exit got entry to point to the same vulnerable function that way we get infinite loop of format string
2. NX is enabled, so no shellcoding. We can leak libc easily then use ret2libc ? okey let's see the possibility of that:
         - we can write system in the exit got entry then /bin/sh as an argument on the stack ? that turned out to be undoable because somehow the stack kept growing by 4 bytes at each call to our vulnerable function so if we write /bin/sh then on the second iteration write system it will be not positioned correctly and if we adjust it to be higher in the stack it get's overwritten so no good.
        - we can write system in the printf got entry and then gives /bin/sh as our input so that way the program will execute system('/bin/sh') yeah that will do the job.
        - one other solution is to find somewhere to write system|JUNK|/bin/sh on the stack and somehow jump there. This was my solution actually i didn't think of the previous one until now xD. how can we do that? well looking at the available ROP gadgets I saw leave; ret. leave will do the job perfectly because it execute mov esp,ebp and ebp is constant during this vulnerable subroutine so we write system to [ebp] and /bin/sh to [ebp+8] and then as a final step we write the adresse of our gadget to exit got entry and boom we have our shell.

feel free to check my solver.py script for implementation of the last approach.
