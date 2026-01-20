package com.example.ordernotification;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Teste de Carregamento do Contexto")
class OrderNotificationServiceApplicationTests {

	@Test
	@DisplayName("Deve carregar o contexto da aplicação sem erros")
	void contextLoads() {
	}

}