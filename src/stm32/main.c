#include "config.h"
// #include "stm32f10x_usart.h"
// #include "stm32f10x_rcc.h"
// #include "stm32f10x_gpio.h"
// #include "misc.h"
// void UARTSend(const unsigned char *pucBuffer, unsigned long ulCount);
// void usart_rxtx(void);
// void USART1_IRQHandler(void);

void init(void)
{
  int i;
  // Libera o clock para GPIO port C
  // int i;
  RCC->APB2ENR |= (1<<4);
  
  GPIOC->CRH&=~(0x0F<<20);
  GPIOC->CRH|=(1<<21);
  for(i = 0;i<4;i++){
    GPIOC->BSRR=(1<<13);
    // GPIO_WriteBit(GPIOA,GPIO_Pin_8,Bit_SET);
    for(i=1000000;i>0;i--);
    GPIOC->BRR=(1<<13);
    // GPIO_WriteBit(GPIOA,GPIO_Pin_8,Bit_RESET);
    for(i=1000000;i>0;i--);
  }
  // usart_rxtx();
  while(1) {
    // piscarLedPlaca();
  }
}

// void piscarLedPlaca()
// {
//     int i;
//     GPIOC->BSRR=(1<<13);
//     // GPIO_WriteBit(GPIOA,GPIO_Pin_8,Bit_SET);
//     for(i=1000000;i>0;i--);
//     GPIOC->BRR=(1<<13);
//     // GPIO_WriteBit(GPIOA,GPIO_Pin_8,Bit_RESET);
//     for(i=1000000;i>0;i--);
// }

//Função que inicializa as portas do bluetooth
// void usart_rxtx(void)
// {
    // const unsigned char welcome_str[] = " Welcome to Bluetooth!\r\n";
    // uint16_t number = 456;
    // /* Enable USART1 and GPIOA clock */
    // RCC_APB2PeriphClockCmd(RCC_APB2Periph_USART1 | RCC_APB2Periph_GPIOA, ENABLE);

    // /* NVIC Configuration */
    // NVIC_Configuration();

    // /* Configure the GPIOs */
    // GPIO_Configuration();

    // /* Configure the USART1 */
    // USART_Configuration();

    // /* Enable the USART1 Receive interrupt: this interrupt is generated when the
    //      USART1 receive data register is not empty */
    // USART_ITConfig(USART1, USART_IT_RXNE, ENABLE);
    // void USART1_Init(void);   

    // /* print welcome information */
    // UARTSend(welcome_str, sizeof(welcome_str));
    // UARTSendInt(number, sizeof(number));
// }


/******************************************************************************/
/*            STM32F10x Peripherals Interrupt Handlers                        */
/******************************************************************************/

/**
  * @brief  This function handles USARTx global interrupt request
  * @param  None
  * @retval None
  */
// void USART1_IRQHandler(void)
// {
//   piscarLed_8(1000000);
//   if ((USART1->SR & USART_FLAG_RXNE) != (u16)RESET)
//   {
//     i = USART_ReceiveData(USART1);
//     if(i == '1'){
//       // GPIOC->BSRR=(1<<13);
//       // for(i=1000000;i>0;i--);
//       // GPIOC->BRR=(1<<13);
//       // for(i=1000000;i>0;i--);
//       GPIO_WriteBit(GPIOA,GPIO_Pin_8,Bit_SET);    // Set '1' on PA8
//       UARTSend("LED ON\r\n",sizeof("LED ON\r\n"));  // Send message to UART1
//     }
//     else if(i == '0'){
//       // GPIOC->BSRR=(1<<13);
//       // for(i=100000;i>0;i--);
//       // GPIOC->BRR=(1<<13);
//       // for(i=100000;i>0;i--);
//       GPIO_WriteBit(GPIOA,GPIO_Pin_8,Bit_RESET);    // Set '0' on PA8
//       UARTSend("LED OFF\r\n",sizeof("LED OFF\r\n"));
//     }
//   }
//   else{
//         GPIO_WriteBit(GPIOA,GPIO_Pin_8,Bit_SET);  
//         for(i=100000;i>0;i--);
//         GPIO_WriteBit(GPIOA,GPIO_Pin_8,Bit_RESET);
//         UARTSend("LED OFF\r\n",sizeof("LED OFF\r\n"));
//   }
// }