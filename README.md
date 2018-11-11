# RISCV-Accelerator
本项目基于RISC-V指令集架构，借助RISC-V开源项目Rocket Chip平台设计SoC,并通过协处理器(Rocket Custom Coprocessor，RoCC) 接口，采用高层次语言Chisel定制Cordic算法片上加速器，实现自定义指令以完成正余弦函数值的计算。
注：依赖的rocketchip的commit号为b745b3c；依赖的sifive-blocks的commit号为ee490d1.
