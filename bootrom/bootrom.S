//-----------------UART.S----------------------//
//#include "cordicrocc.h"
#include "xcustom.h"
#include "riscv_test_rocc.h"
#define MSTATUS_XS 0x00018000
#define DRAM_BASE 0x80000000
#define CUSTOM_X 0
#define SIN 0
#define COS 1

.section .text.start, "ax", @progbits
.globl _start
_start:
  li s0, DRAM_BASE
  jr s0

.section .text.hang, "ax", @progbits
.globl _hang
_hang:
1:
  //open the custom function
  RVTEST_XS_ENABLE 

  // configure uart
  lui t0, 0x10013 
  li t1, 0x00050001 
  li t2, 0x00030001 
  li t3, 0x000001B1
  sw t1, 0x08(t0) 
  sw t2, 0x0c(t0) 
  sw t3, 0x18(t0) 

  li t1, 0x40
  sb t1, (t0)

  lui t1, %hi(label)
  addi t1, t1, %lo(label)

  //send string "Welcome to TYOD SoC\n"
2:
  lbu t2, (t1)
  again:
  	amoswap.w t3, t2, (t0)
  	srli t3, t3, 31
	bnez t3, again
  addi t1, t1, 1
  bnez t2, 2b

li s0, 0xffffffff
lui t3, 0x80000
3:
  li t6, 4
  li t5, 0
  li t4, 1
  loop:
    jal fetch
    slli t5, t5, 8
    add t5, t5, t1
    sub t6, t6, t4
    bnez t6, loop
	beq t5, s0, jmp
    sw t5, (t3)
    lbu s3,3(t3)       
    lbu a1,(t3)
    slli a1, a1, 16
    lhu a2,(t3)
    add a1, a1, a2
    sub s3, t4, s3
    bnez s3,sin
    ROCC_INSTRUCTION_RAW_R_R_R(CUSTOM_X,  10, 11, 0, COS)  
    addi t3, t3 ,4
    sw a0,(t3)
    li s1, 4
    li s2, 1
    jal transmit
  j 3b

   sin:
     ROCC_INSTRUCTION_RAW_R_R_R(CUSTOM_X,  10, 11, 0, SIN)
     addi t3, t3 ,4
     sw a0,(t3)
     li s1, 4
     li s2, 1
     jal transmit
  j 3b
   
  //send data 
   transmit:
      lbu t2, 3(t3)         
      send:
        amoswap.w t1, t2, (t0) 
        srli t1, t1, 31   
        bnez t1, send 
        sub  s1, s1, s2 
        addi t3, t3, -1  
      bnez s1,transmit
      ret

  //receive data 
  fetch:
    lw t1, 4(t0)
    srli t2, t1, 31
    bnez t2, fetch
    ret

  jmp:
    lui t3, 0x80000
    jr t3

.section .rodata
label: .asciz "Welcome to TYOD SoC\n"




