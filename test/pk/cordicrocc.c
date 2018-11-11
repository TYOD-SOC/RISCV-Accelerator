#include <assert.h>
#include <stdint.h>
#include <stdio.h>
#include "cordicrocc.h"

int main() {
  int32_t y,z;

  printf("Welcome to TYOD SOC!\n");
  printf("Trigonometric function is calculating...\n");

  sin(y,0x0000);
  printf("sin 0x0000 = 0x%lx\n", y);
  cos(z,0x0000); 
  printf("cos 0x0000 = 0x%lx\n", z);
   
  sin(y,0x1555);
  printf("sin 0x1555 = 0x%lx\n", y);
  cos(z,0x1555);
  printf("cos 0x1555 = 0x%lx\n", z);
 
  sin(y,0x2AAA);
  printf("sin 0x2AAA = 0x%lx\n", y);
  cos(z,0x2AAA);
  printf("cos 0x2AAA = 0x%lx\n", z);

  printf("Calculation is done!\n");
 
}
