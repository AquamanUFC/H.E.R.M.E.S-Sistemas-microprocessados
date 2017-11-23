#include "config.h"

// void configurar_pino_C(void){
//   RCC->APB2ENR |= (1<<4);
//   GPIOC->CRH&=~(0x0F<<20);
//   GPIOC->CRH|=(1<<21);
// }



void piscarLedPlaca(void)
{
    int i;
    GPIOC->BSRR=(1<<13);
    // GPIO_WriteBit(GPIOA,GPIO_Pin_8,Bit_SET);
    for(i=1000000;i>0;i--);
    GPIOC->BRR=(1<<13);
    // GPIO_WriteBit(GPIOA,GPIO_Pin_8,Bit_RESET);
    for(i=1000000;i>0;i--);
}

void piscarLed_8(int speed){
  int i;
    // GPIOC->BSRR=(1<<13);
    GPIO_WriteBit(GPIOA,GPIO_Pin_8,Bit_SET);
    for(i=speed;i>0;i--);
    // GPIOC->BRR=(1<<13);
    GPIO_WriteBit(GPIOA,GPIO_Pin_8,Bit_RESET);
    for(i=speed;i>0;i--);
}

void recebeSinalSensor(int *j){
    // int i;
    // // GPIOC->BSRR=(1<<13);
    // GPIO_WriteBit(GPIOA,GPIO_Pin_8,Bit_SET);
    // for(i=speed;i>0;i--);
    // // GPIOC->BRR=(1<<13);
    // GPIO_WriteBit(GPIOA,GPIO_Pin_8,Bit_RESET);
    // for(i=speed;i>0;i--);
  
  if(GPIO_ReadInputDataBit(GPIOA,GPIO_Pin_10))
    GPIO_SetBits(GPIOA,GPIO_Pin_8);
  else if(!GPIO_ReadInputDataBit(GPIOA,GPIO_Pin_10))
    GPIO_ResetBits(GPIOA, GPIO_Pin_8);
  // else
  //     GPIO_ResetBits(GPIOA,GPIO_Pin_8);
  //   int i;
  //   for(i=100000;i>0;i--);
  //   GPIO_ResetBits (GPIOB, GPIO_Pin_10);
}

/*******************************************************************************
* Function Name  : GPIO_Configuration
* Description    : Configures the different GPIO ports
*******************************************************************************/
void GPIO_Configuration(void)
{    
  /* Enable USART1 and GPIOA clock */
   RCC_APB2PeriphClockCmd(RCC_APB2Periph_USART1 | RCC_APB2Periph_GPIOA, ENABLE);
  GPIO_InitTypeDef GPIO_InitStructure;

  /* Configure (PA.8) as output */
  GPIO_InitStructure.GPIO_Pin   = GPIO_Pin_8;
  GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
  GPIO_InitStructure.GPIO_Mode  = GPIO_Mode_Out_PP;
  GPIO_Init(GPIOA, &GPIO_InitStructure); // Save

  /* Configure USART1 Tx (PA.09) as alternate function push-pull */
  GPIO_InitStructure.GPIO_Pin = GPIO_Pin_9;
  GPIO_InitStructure.GPIO_Mode = GPIO_Mode_AF_PP;
  GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
  GPIO_Init(GPIOA, &GPIO_InitStructure);

  /* Configure USART1 Rx (PA.10) as input floating */
  GPIO_InitStructure.GPIO_Pin = GPIO_Pin_10;
  GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IN_FLOATING;
  GPIO_Init(GPIOA, &GPIO_InitStructure);

  RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOB, ENABLE);
  /* Configure USART1 Rx (PA.10) as input floating */
  GPIO_InitStructure.GPIO_Pin = GPIO_Pin_10;
  GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IN_FLOATING;
  GPIO_Init(GPIOB, &GPIO_InitStructure);

}

/*******************************************************************************
* Function Name  : USART_Configuration
* Description    : Configures the USART1
*******************************************************************************/
void USART_Configuration(void)
{
  USART_InitTypeDef USART_InitStructure;

/* USART1 configuration ------------------------------------------------------*/
  USART_InitStructure.USART_BaudRate = 9600;    // Baud Rate
  USART_InitStructure.USART_WordLength = USART_WordLength_8b;
  USART_InitStructure.USART_StopBits = USART_StopBits_1;
  USART_InitStructure.USART_Parity = USART_Parity_No;
  USART_InitStructure.USART_HardwareFlowControl = USART_HardwareFlowControl_None;
  USART_InitStructure.USART_Mode = USART_Mode_Rx | USART_Mode_Tx;

  USART_Init(USART1, &USART_InitStructure);

  /* Enable USART1 */
  USART_Cmd(USART1, ENABLE);
}

/**
  * @brief  Configures the nested vectored interrupt controller.
  * @param  None
  * @retval None
  */
void NVIC_Configuration(void)
{
  NVIC_InitTypeDef NVIC_InitStructure;

  /* Enable the USARTx Interrupt */
  NVIC_InitStructure.NVIC_IRQChannel = USART1_IRQn;
  NVIC_InitStructure.NVIC_IRQChannelPreemptionPriority = 0;
  NVIC_InitStructure.NVIC_IRQChannelSubPriority = 0;
  NVIC_InitStructure.NVIC_IRQChannelCmd = ENABLE;
  NVIC_Init(&NVIC_InitStructure);
}





/*******************************************************************************
* Function Name  : UARTSend
* Description    : Send a string to the UART.
* Input          : - pucBuffer: buffers to be printed.
*                : - ulCount  : buffer's length
*******************************************************************************/
void UARTSend(const unsigned char *pucBuffer, unsigned long ulCount)
{
    //
    // Loop while there are more characters to send.
    //
    while(ulCount--)
    {
        USART_SendData(USART1, (uint16_t) *pucBuffer++);
        /* Loop until the end of transmission */
        while(USART_GetFlagStatus(USART1, USART_FLAG_TC) == RESET)
        {
            piscarLed_8(500000);
        }
    }
}

void UARTSendInt(const uint16_t pucBuffer, unsigned long ulCount)
{
    //
    // Loop while there are more characters to send.
    //
  USART_SendData(USART1, pucBuffer);
    // while(ulCount--)
    // {
    //     USART_SendData(USART1, pucBuffer);
    //     /* Loop until the end of transmission */
    //     while(USART_GetFlagStatus(USART1, USART_FLAG_TC) == RESET)
    //     {
    //         piscarLed_8();
    //     }
    // }
}


