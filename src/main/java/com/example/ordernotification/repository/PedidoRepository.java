package com.example.ordernotification.repository;

import com.example.ordernotification.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
    Pedido save(Pedido novoPedido);
}