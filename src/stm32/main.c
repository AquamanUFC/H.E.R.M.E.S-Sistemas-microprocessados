#include "config.h"

void init(void)
{
  int i;
  // Libera o clock para GPIO port C
  RCC->APB2ENR |= (1<<4);
  GPIOC->CRH&=~(0x0F<<20);
  GPIOC->CRH|=(1<<21);
  //Libera o clock pro GPIO port C
  
  GPIO_Configuration();
  for(i=0;i<5;i++){
	piscarLedPlaca();
	int j;
	for(j=0;j<100000;j++);
  }
  while(1) {
    
    //Função que envia e recebe o sinal do bluetooth
usart_rxtx();
    //Função que recebe o sinal do sensor
    // recebeSinalSensor(i);
  }
}



//Função que inicializa as portas do bluetooth
void usart_rxtx(void)
{
    const unsigned char welcome_str[] = " Welcome to Bluetooth!\r\n";
    uint16_t number = 456;

   

    /* NVIC Configuration */
    NVIC_Configuration();

    /* Configure the GPIOs */
    GPIO_Configuration();

    /* Configure the USART1 */
    USART_Configuration();

    /* Enable the USART1 Receive interrupt: this interrupt is generated when the
         USART1 receive data register is not empty */
    USART_ITConfig(USART1, USART_IT_RXNE, ENABLE);
    void USART1_Init(void);   

    /* print welcome information */
    UARTSend(welcome_str, sizeof(welcome_str));
    // UARTSendInt(number, sizeof(number));
}


/******************************************************************************/
/*            STM32F10x Peripherals Interrupt Handlers                        */
/******************************************************************************/

/**
  * @brief  This function handles USARTx global interrupt request
  * @param  None
  * @retval None
  */
void USART1_IRQHandler(void)
{
  int i;
  piscarLed_8(1000000);
  if ((USART1->SR & USART_FLAG_RXNE) != (u16)RESET)
  {
    i = USART_ReceiveData(USART1);
    if(i == '1'){
      GPIO_WriteBit(GPIOA,GPIO_Pin_8,Bit_SET);    // Set '1' on PA8
      UARTSend("LED ON\r\n",sizeof("LED ON\r\n"));  // Send message to UART1
    }
    else if(i == '0'){
      GPIO_WriteBit(GPIOA,GPIO_Pin_8,Bit_RESET);    // Set '0' on PA8
      UARTSend("LED OFF\r\n",sizeof("LED OFF\r\n"));
    }
  }
  else{
        GPIO_WriteBit(GPIOA,GPIO_Pin_8,Bit_SET);  
        for(i=100000;i>0;i--);
        GPIO_WriteBit(GPIOA,GPIO_Pin_8,Bit_RESET);
        UARTSend("LED OFF\r\n",sizeof("LED OFF\r\n"));
  }
}