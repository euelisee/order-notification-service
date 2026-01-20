package com.example.ordernotification.controller;

import com.example.ordernotification.domain.Pedido;
import com.example.ordernotification.dto.PedidoRequestDTO;
import com.example.ordernotification.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> criar(@RequestBody @Valid PedidoRequestDTO dto) {
        Pedido novoPedido = pedidoService.criarPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
    }
}