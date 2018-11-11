#include <stdint.h>
#include <stdio.h>
#include "cordicrocc.h"

int main() {
  int32_t x,y,z,i,t1,t2;
  uint32_t a;
  float b,c;
  printf("Welcome to TYOD SOC!\n");
  printf("Trigonometric function is calculating...\n");
  for(x=5461;x<=65536;x=x+5461){
    sin(y,x);
    i = x;
    a =(i*360)/65536;
    if(y<=65536){
      t1 = y;
      b = (t1/65536.0) - 1*(t1 >> 16&1);
      printf("sin %d = %f\n",a,b);}
    else{
      t1 = ~y+1;
      b = (-((t1/65536.0) - 1*(t1 >> 16&1)))-2;
      printf("sin %d = %f\n",a,b);}
   }
  for(x=5461;x<=65536;x=x+5461){
    cos(z,x); 
    i = x; 
    a = (i*360)/65536;
    if(z<=65536){
      t2 = z;
      c = (t2/65536.0) - 1*(t2 >> 16&1);
      printf("cos %d = %f\n",a,c);}
    else{
      t2 = ~z+1;
      c = (-((t2/65536.0) - 1*(t2 >> 16&1)))-2;
      printf("cos %d = %f\n",a,c);}
   }
      printf("Calculation is done!\n");}





















