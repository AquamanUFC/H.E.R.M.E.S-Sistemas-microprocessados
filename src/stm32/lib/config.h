#ifndef _CONFIG
#define _CONFIG

#ifdef __cplusplus
 extern "C" {
#endif

#include "stm32f10x_usart.h"
#include "stm32f10x_rcc.h"
#include "stm32f10x_gpio.h"
#include "misc.h"

void piscarLedPlaca(void);
void configurarPinoC(void);
void piscarLed_8(int speed);
void GPIO_Configuration(void);
void USART_Configuration(void);
void NVIC_Configuration(void);
void UARTSend(const unsigned char *pucBuffer, unsigned long ulCount);
void UARTSendInt(const uint16_t pucBuffer, unsigned long ulCount);
#ifdef __cplusplus
}
#endif
#endif