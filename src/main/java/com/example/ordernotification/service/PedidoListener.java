package com.example.ordernotification.service;

import com.example.ordernotification.domain.Pedido;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoListener {

    @SqsListener("fila-pedidos")
    public void ouvirPedido(Pedido pedido) {
        System.out.println("NOVO PEDIDO RECEBIDO DA FILA!");
        System.out.println("Cliente: " + pedido.getNome());
        System.out.println("Produto: " + pedido.getProduto());
        System.out.println("Data: " + pedido.getCriadoEm());
    }
}