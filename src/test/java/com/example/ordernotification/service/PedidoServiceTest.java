package com.example.ordernotification.service;

import com.example.ordernotification.domain.Pedido;
import com.example.ordernotification.dto.PedidoRequestDTO;
import com.example.ordernotification.repository.PedidoRepository;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private SqsTemplate sqsTemplate;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    @DisplayName("Deve criar um pedido com sucesso e enviar para a fila")
    void deveCriarPedidoComSucesso() {
        PedidoRequestDTO dto = new PedidoRequestDTO("Netuno", "Cafe Especial", new BigDecimal("55.00"));
        Pedido pedidoSalvo = new Pedido("Netuno", "Cafe Especial", new BigDecimal("55.00"));

        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoSalvo);

        Pedido resultado = pedidoService.criarPedido(dto);

        assertNotNull(resultado);
        assertEquals("Netuno", resultado.getNome());

        verify(pedidoRepository, times(1)).save(any(Pedido.class));

        // Verifica se o SQS foi chamado com o nome da fila correto
        verify(sqsTemplate, times(1)).send(eq("fila-pedidos"), any(Pedido.class));
    }
}