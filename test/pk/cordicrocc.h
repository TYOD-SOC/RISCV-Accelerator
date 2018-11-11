// See LICENSE for license details.

#ifndef SRC_MAIN_C_CORDICROCC_H
#define SRC_MAIN_C_CORDICROCC_H

#include "xcustom.h"

#define SIN 0
#define COS 1


#define XCUSTOM_ACC 0

#define sin(y, phase)                                       \
  ROCC_INSTRUCTION(XCUSTOM_ACC, y, phase, 0, SIN);          
#define cos(z, phase)                                       \
  ROCC_INSTRUCTION(XCUSTOM_ACC, z, phase, 0, COS);


#endif  // SRC_MAIN_C_ACCUMULATOR_H  
