# Sudoku Java 8

Projeto de Sudoku em Java, compatível com Java 8.

## Estrutura do projeto

- Pacote `br.com.dio.model` com classes `Board`, `Space` e enum `GameStatusEnum`
- Pacote `br.com.dio.util` com templates (não incluído neste exemplo)
- Classe principal para executar o jogo (deve ser criada pelo usuário)

## Como compilar e rodar

Compile as classes:

```
javac src/br/com/dio/model/*.java
```

Rode a aplicação (substitua pela sua classe main):

```
java -cp src br.com.dio.Main
```

## Notas

- O método `Board.printBoard()` imprime o tabuleiro no terminal.
- A validação segue regras básicas do Sudoku.
