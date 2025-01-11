# 📚 LitCatalog  

O **LitCatalog** é uma aplicação desenvolvida em **Java** que implementa um catálogo de livros interativo acessível via console.  
O sistema consome dados de uma **API externa** para buscar informações sobre livros, processa as respostas **JSON** e realiza operações de inserção e consulta em um banco **PostgreSQL**.  

---

## 🚀 Tecnologias Utilizadas  

- **☕ Java**: Linguagem de programação utilizada para desenvolver a aplicação.  
- **🌱 Spring Boot**: Framework que facilita a criação de aplicações Java standalone e de produção.  
- **🗂️ Spring Data JPA**: Abstração do Spring para o Java Persistence API, facilitando a implementação de repositórios de acesso a dados.  
- **🐘 PostgreSQL**: Sistema de gerenciamento de banco de dados relacional utilizado para armazenar as informações dos livros e autores.  
- **📦 Maven**: Ferramenta de automação de compilação e gerenciamento de dependências do projeto.  
- **🛠️ Jackson**: Biblioteca para processamento de JSON, utilizada para desserializar as respostas da API externa.  

---

## ✨ Funcionalidades do Programa  

- 🔎 **Busca de Livros**: Permite ao usuário buscar livros por título ou autor através de uma API externa e registrar essas informações no banco de dados.  
- 📋 **Listagem de Livros e Autores**: Possibilita listar todos os livros e autores registrados no sistema.  
- 🌍 **Filtragem por Idioma**: Permite listar livros registrados por idioma específico.  
- 🎭 **Autores Vivos em Ano Específico**: Possibilita listar autores que estavam vivos em um determinado ano.  
- 📈 **Top 10 Livros Mais Baixados**: Exibe os 10 livros mais baixados, conforme informações da API externa.  

---

## 🛠️ Objetivo  

Este projeto visa:  
- 📡 Praticar o consumo e desserialização de APIs.  
- 🗃️ Explorar o armazenamento e consulta de dados em um banco de dados relacional usando **Spring** e **Hibernate**.  
