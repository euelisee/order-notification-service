package com.example.ordernotification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record PedidoRequestDTO(
        @NotBlank(message = "O nome do cliente é obrigatório")
        String nome,

        @NotBlank(message = "O produto é obrigatório")
        String produto,

        @NotNull(message = "O valor é obrigatório")
        @Positive(message = "O valor deve ser maior que zero")
        BigDecimal valor
){

}