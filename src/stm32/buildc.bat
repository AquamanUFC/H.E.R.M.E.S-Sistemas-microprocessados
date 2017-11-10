del *.elf 
del *.o

arm-none-eabi-gcc -Llib/src -Ilib/inc -Ilib  -fno-builtin-free -fno-builtin-memset -mcpu=cortex-m3 -mthumb -c -o2 -Wall main.c -o demo.o

arm-none-eabi-gcc -Llib/src -Ilib/inc -Ilib -ggdb -fno-builtin-free -fno-builtin-memset -mcpu=cortex-m3 -mthumb -c -o2 -Wall lib/src/stm32f10x_usart.c -o stm32f10x_usart.o -DSTM32F10X_CL -Dassert_param(expr)
arm-none-eabi-gcc -Llib/src -Ilib/inc -Ilib -ggdb -fno-builtin-free -fno-builtin-memset -mcpu=cortex-m3 -mthumb -c -o2 -Wall lib/src/stm32f10x_rcc.c -o stm32f10x_rcc.o -DSTM32F10X_CL -Dassert_param(expr)
arm-none-eabi-gcc -Llib/src -Ilib/inc -Ilib -ggdb -fno-builtin-free -fno-builtin-memset -mcpu=cortex-m3 -mthumb -c -o2 -Wall lib/src/stm32f10x_gpio.c -o stm32f10x_gpio.o -DSTM32F10X_CL -Dassert_param(expr)
arm-none-eabi-gcc -Llib/src -Ilib/inc -Ilib -ggdb -fno-builtin-free -fno-builtin-memset -mcpu=cortex-m3 -mthumb -c -o2 -Wall lib/src/misc.c -o misc.o -DSTM32F10X_CL -Dassert_param(expr)

arm-none-eabi-as -mcpu=cortex-m3 -o m0s.o cm0.asm

arm-none-eabi-ld -Ttext 0x8003000 -Tdata 0x800A000 -Tbss 0x20000000 m0s.o stm32f10x_usart.o stm32f10x_rcc.o stm32f10x_gpio.o misc.o demo.o -o m0s.elf

arm-none-eabi-objcopy -S -O ihex m0s.elf cm0dsasm.hex

arm-none-eabi-size m0s.elf

rem arm-none-eabi-objdump -d -S m0s.elf
rem del *.elf 
rem del *.o
rem monta