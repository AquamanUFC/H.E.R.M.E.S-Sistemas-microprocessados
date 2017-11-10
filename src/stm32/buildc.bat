del *.elf 
del *.o

arm-none-eabi-gcc -Llib/src -Ilib/inc -Ilib  -fno-builtin-free -fno-builtin-memset -mcpu=cortex-m3 -mthumb -c -o2 -Wall main.c -o lib/obj/demo.o
arm-none-eabi-gcc -Llib/src -Ilib/inc -Ilib -ggdb -fno-builtin-free -fno-builtin-memset -mcpu=cortex-m3 -mthumb -c -o2 -Wall lib/src/stm32f10x_usart.c -o lib/obj/stm32f10x_usart.o -DSTM32F10X_CL -Dassert_param(expr)
arm-none-eabi-gcc -Llib/src -Ilib/inc -Ilib -ggdb -fno-builtin-free -fno-builtin-memset -mcpu=cortex-m3 -mthumb -c -o2 -Wall lib/src/stm32f10x_rcc.c -o lib/obj/stm32f10x_rcc.o -DSTM32F10X_CL -Dassert_param(expr)
arm-none-eabi-gcc -Llib/src -Ilib/inc -Ilib -ggdb -fno-builtin-free -fno-builtin-memset -mcpu=cortex-m3 -mthumb -c -o2 -Wall lib/src/stm32f10x_gpio.c -o lib/obj/stm32f10x_gpio.o -DSTM32F10X_CL -Dassert_param(expr)
arm-none-eabi-gcc -Llib/src -Ilib/inc -Ilib -ggdb -fno-builtin-free -fno-builtin-memset -mcpu=cortex-m3 -mthumb -c -o2 -Wall lib/src/misc.c -o lib/obj/misc.o -DSTM32F10X_CL -Dassert_param(expr)

arm-none-eabi-as -mcpu=cortex-m3 -o lib/obj/m0s.o core/cm0.asm

arm-none-eabi-ld -Ttext 0x8003000 -Tdata 0x800A000 -Tbss 0x20000000 lib/obj/m0s.o lib/obj/stm32f10x_usart.o lib/obj/stm32f10x_rcc.o lib/obj/stm32f10x_gpio.o lib/obj/misc.o lib/obj/demo.o -o elf/m0s.elf

arm-none-eabi-objcopy -S -O ihex elf/m0s.elf output/cm0dsasm.hex

arm-none-eabi-size elf/m0s.elf

rem arm-none-eabi-objdump -d -S elf/m0s.elf
rem del *.elf 
rem del *.o
rem monta