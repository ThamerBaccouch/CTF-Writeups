# ZURK

In this challenge we were given a binary file and a netcat connection so it's a typical pwn challenge where we will exploit the binary locally and then run our exploit remotely to gain shell access to the machine and read the flag.

## let's start first by static analysis with the one and only IDA:
### Looking at main function pseudo code using IDA decompiler:

`int __cdecl __noreturn main(int argc, const char **argv, const char **envp){

  welcome(*(_QWORD *)&argc, argv, envp);
  
  while ( 1 )
        do_move();
    
}`

there is nothing special just a welcome that prints a welcome message and an infinite loop that executes do_move function so let's check that function:

`int do_move()
{
  int result; // eax@2
  char s[64]; // [sp+0h] [bp-40h]@1

  puts("What would you like to do?");
  fgets(s, 50, stdin);
  s[strcspn(s, "\n")] = 0;
  if ( !strcmp(s, "go west") )
  {
    puts("You move west to arrive in a cave dimly lit by torches.");
    result = puts("The cave two tunnels, one going east and the other going west.");
  }
  else if ( !strcmp(s, "go east") )
  {
    puts("You move east to arrive in a cave dimly lit by torches.");
    result = puts("The cave two tunnels, one going east and the other going west.");
  }
  else
  {
    printf(s, "go east");
    result = puts(" is not a valid instruction.");
  }
  return result;
}`

We can spot the vulnerability direcly in the do_move function which is a format string where our input is passed as a first argument to a printf.

using gdb let's check the binary's protections:

* CANARY    : disabled
* FORTIFY   : disabled
* NX        : disabled
* PIE       : disabled
* RELRO     : Partial

As we can see everything is disabled.
We can use a ret2libc technique then ? or could we ?
I tried to use a ret2libc at first but didnt work because it's 64bit binary and we can only write in one area using printf since we have a null byte at our addresses which will end our string.

looking at the protections again we have NX disabled so ret2shellcode for the win!
we can write our shellcode byte by byte somewhere in the binary and then jumps to it.
.bss section seems a good condidate for the job because it's static and no leaking is necessary.

so let's start building our exploit structure :smiley: :
* with some try and error we can get our input offset on the stack (10)
* payload format |%_byte_x|%10hhn|padding|bss_addresse|
* write shellcode on the stack
* overwrite the saved instruction pointer on the stack to our shellcode address
* read the flag

you can check the sploit.py script to view the source code of our exploit.
