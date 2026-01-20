#!/bin/bash

set -e

echo "Limpando a porta 8080..."
lsof -ti:8080 | xargs kill -9 2>/dev/null || true

PROJECT_ROOT=$(pwd)
echo "Iniciando o reset do ambiente na raiz: $PROJECT_ROOT"

# 1. Derrubada e Limpeza de Infraestrutura
echo "Removendo containers e volumes antigos..."
docker-compose down -v

# 2. Inicializacao da Infraestrutura (PostgreSQL e LocalStack)
echo "Subindo containers de infraestrutura..."
docker-compose up -d
echo "Aguardando 15 segundos para estabilizacao dos servicos cloud..."
sleep 15

# 3. Provisionamento de Infraestrutura como Codigo (Terraform)
echo "Aplicando configuracoes do Terraform..."
cd "$PROJECT_ROOT/infra/terraform"
terraform init
terraform apply -auto-approve
cd "$PROJECT_ROOT"

# 4. Validacao Tecnica e Execucao (Fail-Fast)
echo "Executando testes unitarios e validando integridade do codigo..."
if ./mvnw clean test; then
    echo "Testes aprovados. Iniciando a aplicacao Spring Boot..."
    ./mvnw spring-boot:run
else
    echo "ERRO: Os testes falharam. O sistema nao sera iniciado para evitar inconsistencias."
    exit 1
fi