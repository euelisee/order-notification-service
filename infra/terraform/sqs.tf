resource "aws_sqs_queue" "pedido_queue" {
  name                      = "fila-pedidos"
  delay_seconds             = 0
  message_retention_seconds = 86400 # 1 dia
}