# Portal Jurídico IA ⚖️🤖

Sistema inteligente para análise de documentos jurídicos, utilizando **Java 17, Spring Boot 4.x** e **Google Gemini API**. O projeto foca em extração de alta precisão e segurança de dados sensíveis.

## 🚀 Funcionalidades

- **Upload Múltiplo:** Envio simultâneo de diversos documentos para análise de contexto cruzado.
- **Análise Contextual:** IA treinada para responder perguntas baseando-se estritamente nos documentos enviados.
- **Gestão de Memória:** Integração com banco de dados H2 para armazenamento temporário de contexto.
- **Tratamento de Erros:** Handler customizado para limites de upload (erro 413) e segurança de rede (CORS).
- **Extração Avançada (PDFBox 3.x)**: Processamento otimizado de documentos PDF complexos com suporte a encoding UTF-8.
- **Validação de MIME Type (Apache Tika)**: Segurança reforçada que valida o "DNA" do arquivo, impedindo o upload de arquivos maliciosos mascarados.
- **Sanitização de Dados**: Tratamento automático de strings para evitar Prompt Injection e garantir a integridade das consultas à IA.
- **Gestão Segura de Credenciais**: Integração com variáveis de ambiente e arquivos .env, eliminando chaves expostas no código.

## 🛠️ Tecnologias Utilizadas

- **Backend:** Java 17+, Spring Boot 3.x
- **IA:** Google Generative AI (Gemini SDK - Model: 1.5 Flash/Pro)
- **Parser de Documentos**: Apache Tika & PDFBox 3.x
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

## 🔐 Segurança
### 2. Gestão de Variáveis de Ambiente
Para manter a segurança, não chumbamos a chave no application.properties.
Crie um arquivo .env na raiz do projeto ou configure no seu ambiente
`export GEMINI_API_KEY=sua_chave`

### 3. Executar a Aplicação
No terminal do Manjaro, dentro da pasta do projeto:
```
Bash
mvn clean install
mvn spring-boot:run
```
📂 Estrutura de Tratamento de Erros
A aplicação inclui um CustomExceptionHandler para gerir ficheiros acima do limite permitido.

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
🔐 Segurança e Boas Práticas
O projeto utiliza o padrão de injeção de dependência com fallback

⚠️ Notas de Desenvolvimento (Ambiente Manjaro)
Conflitos de Porta: Caso a porta 8082 esteja ocupada, utilize```fuser -k 8082/tcp```para liberar o processo.

Projeto desenvolvido para otimização de fluxos de trabalho jurídicos.
