# Portal Jurídico IA ⚖️🤖

Sistema inteligente para análise de documentos jurídicos, utilizando **Java Spring Boot** no backend e **Google Gemini API** para processamento de linguagem natural.

## 🚀 Funcionalidades

- **Upload Múltiplo:** Envio de diversos documentos (PDF/Texto) simultaneamente via Postman ou App.
- **Análise Contextual:** IA treinada para responder perguntas baseando-se estritamente nos documentos enviados.
- **Gestão de Memória:** Integração com banco de dados H2 para armazenamento temporário de contexto.
- **Tratamento de Erros:** Handler customizado para limites de upload (erro 413) e segurança de rede (CORS).

## 🛠️ Tecnologias Utilizadas

- **Backend:** Java 17+, Spring Boot 3.x
- **IA:** Google Generative AI (Gemini SDK)
- **Banco de Dados:** H2 Database (em memória)
- **Ambiente de Desenvolvimento:** Manjaro Linux
- **Ferramenta de Testes:** Postman

## 📋 Pré-requisitos

- **Java JDK 17** ou superior.
- **Maven** instalado.
- **Chave de API do Gemini** (obtida no [Google AI Studio](https://aistudio.google.com/)).

## 🔧 Configuração e Instalação

### 1. Backend (Spring Boot)
Clone o repositório e configure as variáveis de ambiente em `src/main/resources/application.properties`:

```properties
# Configurações do Servidor
server.port=8082

# Limites de Upload (Ajustados para arquivos jurídicos)
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=50MB

# API Key Gemini
gemini.api.key=SUA_CHAVE_AQUI
