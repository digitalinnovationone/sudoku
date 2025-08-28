# 🧩 Jogo de Sudoku em Java

Este projeto é uma implementação de um jogo de Sudoku em Java, focado na aplicação de conceitos de **Programação Orientada a Objetos** e **Programação Funcional** (a partir do Java 8).

O jogo permite ao usuário interagir com o tabuleiro através do terminal, inserindo ou removendo números, e verificando o status e a validade de sua solução.

## 🚀 Tecnologias e Conceitos Utilizados

* **Java 8+**: A base do projeto utiliza as funcionalidades modernas da linguagem.
* **Programação Orientada a Objetos (POO)**: Classes como `Board` e `Space` encapsulam a lógica do tabuleiro e de cada célula, tornando o código modular e fácil de manter.
* **Programação Funcional**: O código foi refatorado para utilizar a **Stream API** e **expressões lambda**, o que torna a lógica de verificação do tabuleiro mais concisa e legível.
* **Git e GitHub**: O projeto é versionado para demonstrar boas práticas de colaboração e controle de código-fonte.

## ✨ Melhorias e Refatorações

Este projeto é uma evolução do desafio prático da [Digital Innovation One (DIO)](https://www.dio.me/). As seguintes melhorias foram implementadas no código-fonte original:

* **Lógica de Status Otimizada**: Os métodos `getStatus()` e `hasErrors()` na classe `Board` foram reescritos para usar a Stream API. Isso substituiu loops tradicionais por uma abordagem mais declarativa e eficiente.
* **Código de Exibição Dinâmico**: O template de exibição do tabuleiro (`BoardTemplate.java`) foi alterado para construir a visualização dinamicamente, evitando um arquivo de string fixo. Isso torna o código mais flexível e fácil de adaptar para diferentes layouts.
* **Validações Robustas**: Adicionada a validação de coordenadas e a verificação de dados de entrada na classe `Main`, prevenindo erros comuns e tornando o jogo mais robusto para o usuário.
* **Encapsulamento e Segurança**: A classe `Space` foi ajustada para que a modificação de valores fixos seja impossível, garantindo a integridade do tabuleiro.

## ⚙️ Como Executar o Projeto

Para rodar o jogo, siga os passos abaixo:

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/EriveltonMGit/sudoku.git](https://github.com/EriveltonMGit/sudoku.git)
    ```
2.  **Compile o código:**
    Navegue até o diretório `sudoku` e compile o arquivo `Main.java` e as classes do pacote `br.com.dio`.
    ```bash
    javac -d bin src/br/com/dio/*.java src/br/com/dio/model/*.java src/br/com/dio/util/*.java
    ```
3.  **Execute o jogo:**
    O programa espera um conjunto de argumentos para inicializar o tabuleiro. Use os seguintes dados:

    ```bash
    java -cp bin br.com.dio.Main "0,0;4,false" "1,0;7,false" "2,0;9,true" "3,0;5,false" "4,0;8,true" "5,0;6,true" "6,0;2,true" "7,0;3,false" "8,0;1,false" "0,1;1,false" "1,1;3,true" "2,1;5,false" "3,1;4,false" "4,1;7,true" "5,1;2,false" "6,1;8,false" "7,1;9,true" "8,1;6,true" "0,2;2,false" "1,2;6,true" "2,2;8,false" "3,2;9,false" "4,2;1,true" "5,2;3,false" "6,2;7,false" "7,2;4,false" "8,2;5,true" "0,3;5,true" "1,3;1,false" "2,3;3,true" "3,3;7,false" "4,3;6,false" "5,3;4,false" "6,3;9,false" "7,3;8,true" "8,3;2,false" "0,4;8,false" "1,4;9,true" "2,4;7,false" "3,4;1,true" "4,4;2,true" "5,4;5,true" "6,4;3,false" "7,4;6,true" "8,4;4,false" "0,5;6,false" "1,5;4,true" "2,5;2,false" "3,5;3,false" "4,5;9,false" "5,5;8,false" "6,5;1,true" "7,5;5,false" "8,5;7,true" "0,6;7,true" "1,6;5,false" "2,6;4,false" "3,6;2,false" "4,6;3,true" "5,6;9,false" "6,6;6,false" "7,6;1,true" "8,6;8,false" "0,7;9,true" "1,7;8,true" "2,7;1,false" "3,7;6,false" "4,7;4,true" "5,7;7,false" "6,7;5,false" "7,7;2,true" "8,7;3,false" "0,8;3,false" "1,8;2,false" "2,8;6,true" "3,8;8,true" "4,8;5,true" "5,8;1,false" "6,8;4,true" "7,8;7,false" "8,8;9,false"
    ```

## 🤝 Contribuições

Este projeto foi desenvolvido como parte de um bootcamp da DIO e é aprimorado para fins de estudo. Sugestões e melhorias são sempre bem-vindas.

---

<div align="center">
  <img src="https://hermes.digitalinnovation.one/assets/diome-icon.png" width="30">
  <p>Feito com ❤️ por [Seu Nome/Usuário]</p>
</div>