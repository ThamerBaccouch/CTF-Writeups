**## jumpdrive**

After some static analysis using IDA pro we see that the program reads the flag then takes our input and print it using printf(input) which is vulnerable format string.

The idea is to leak the stack using format string to get the flag.

you can check solver.py to see the script.
