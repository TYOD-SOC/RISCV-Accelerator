bootrom_img = bootrom.img

GCC=riscv32-unknown-elf-gcc
OBJCOPY=riscv32-unknown-elf-objcopy

all: $(bootrom_img)

%.img: %.bin
	dd if=$< of=$@ bs=1024 count=1

%.bin: %.elf
	$(OBJCOPY) -O binary $< $@

%.elf: %.S linker.ld
	$(GCC) -Tlinker.ld $< -nostdlib -static -Wl,--no-gc-sections -o $@



