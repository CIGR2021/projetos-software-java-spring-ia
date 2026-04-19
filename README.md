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
```
### 2. Executar a Aplicação
No terminal do Manjaro, dentro da pasta do projeto:
```
Bash
mvn clean install
mvn spring-boot:run
```
📂 Estrutura de Tratamento de Erros
A aplicação inclui um CustomExceptionHandler para gerir ficheiros acima do limite permitido. A classe deve estar localizada em:
src/main/java/com/seuprojeto/exception/CustomExceptionHandler.java

```
Java
@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
            .body("Erro: O ficheiro excede o limite máximo de 10MB permitido.");
    }
}
```
🧪 Testes via Postman
#### A. Upload de Documentos (Múltiplos)
Método: POST

URL: http://localhost:8082/documentos/upload

Body: form-data

Key: file (selecione o tipo File no Postman)

Value: Selecione um ou mais ficheiros PDF/TXT.

#### B. Perguntar à IA
Método: POST

URL: http://localhost:8082/chat/perguntar

Headers: Content-Type: application/json

Body (raw JSON):
```
JSON
{
  "pergunta": "Quais são as partes envolvidas no contrato anexado?"
}
```
⚠️ Notas Técnicas (Manjaro Linux)
CORS: O backend está configurado com @CrossOrigin(origins = "*") para permitir testes fluídos em ambientes de desenvolvimento e Expo Go.

Firewall: Certifique-se de que a porta 8082 está aberta se testar a partir de outros dispositivos na mesma rede:
```
Bash
sudo ufw allow 8082/tcp
```
Persistência: Por utilizar H2 em memória, os documentos são apagados sempre que o servidor é reiniciado.

Projeto desenvolvido para otimização de fluxos de trabalho jurídicos.
