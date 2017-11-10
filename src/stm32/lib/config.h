#include "stm32f10x_usart.h"
// #include "stm32f10x_rcc.h"
#include "stm32f10x_gpio.h"
#include "misc.h"
void GPIO_Configuration(void);
void USART_Configuration(void);
void NVIC_Configuration(void);
void UARTSend(const unsigned char *pucBuffer, unsigned long ulCount);
void UARTSendInt(const uint16_t pucBuffer, unsigned long ulCount);
void piscarLedPlaca();
void piscarLed_8(int speed);