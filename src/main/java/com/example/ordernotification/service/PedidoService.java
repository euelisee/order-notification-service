package com.example.ordernotification.service;

import com.example.ordernotification.domain.Pedido;
import com.example.ordernotification.dto.PedidoRequestDTO;
import com.example.ordernotification.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.awspring.cloud.sqs.operations.SqsTemplate;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    private final SqsTemplate sqsTemplate;

    @Transactional
    public Pedido criarPedido(PedidoRequestDTO dto) {
        Pedido novoPedido = new Pedido(dto.nome(), dto.produto(), dto.valor());
        Pedido pedidoSalvo = pedidoRepository.save(novoPedido);

        sqsTemplate.send("fila-pedidos", pedidoSalvo);

        return pedidoSalvo;
    }
}