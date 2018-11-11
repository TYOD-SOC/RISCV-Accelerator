NAME=cordicrocc

GCC=riscv32-unknown-elf-gcc
OBJCOPY=riscv32-unknown-elf-objcopy

all: ${NAME}.elf ${NAME}.bin ${NAME}.hex ${NAME}.dump

%.bin: %.elf
	$(OBJCOPY) -O binary --change-addresses=-0x80000000 --only-section .text $< $@

%.elf: %.S link.ld
	$(GCC) -Tlink.ld $< -nostdlib -static -o $@

%.dump: %.elf
	riscv32-unknown-elf-objdump -d $< > $@

%.hex: %.bin
	xxd -e $< | xxd -r | xxd -ps | tr -d  "\n" | sed "s/$$/ffffffff/" > $@

.PHONY:clean

clean:
	rm ${NAME}.hex ${NAME}.bin ${NAME}.dump ${NAME}.elf
