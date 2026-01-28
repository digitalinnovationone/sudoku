# 📘 README – Alterações Implementadas no Sudoku

## ✨ Novas Funcionalidades

- **Salvar jogo**
  - Implementado método `saveGame(String filename)` que grava o estado atual do tabuleiro em um arquivo `.txt`.
  - Inclui valores das casas, se são fixos e o contador de jogadas (`moveCount`).

- **Carregar jogo**
  - Implementado método `loadGame(String filename)` que lê o arquivo salvo e reconstrói o tabuleiro.
  - Recupera também o número de jogadas realizadas.

- **Contador de jogadas (`moveCount`)**
  - Agora cada tentativa de jogada (válida ou inválida) é contabilizada.
  - Incremento ocorre tanto em `inputNumber()` quanto em `removeNumber()`.
  - O valor é exibido no status do jogo e salvo/carregado junto com o tabuleiro.

Definição da constante BOARD_TEMPLATE para exibir o tabuleiro corretamente.

📊 Melhorias de Exibição

Status do jogo (showGameStatus) agora mostra:

-Situação atual (incompleto, concluído, etc.).
-Quantidade de jogadas realizadas.
-Se o tabuleiro contém erros ou não.

Mensagens de feedback mais claras:

-Jogada aceita.
-Jogada inválida (número repetido na linha, coluna ou bloco).
-Posição fixa não pode ser alterada.
-Número removido com sucesso.

⚠️ Regras de Erro:

O jogo marca “O jogo contém erros” sempre que o tabuleiro viola as regras do Sudoku (número repetido em linha, coluna ou bloco).

Esse estado permanece até que o jogador corrija o tabuleiro (removendo ou ajustando o valor conflitante).

🚀 Como usar
Compile o projeto:

javac br/com/dio/*.java br/com/dio/model/*.java br/com/dio/util/*.java

Execute o jogo:

java br.com.dio.Main "0,0;4,false" "1,0;7,false" "2,0;9,true" "3,0;5,false" "4,0;8,true" "5,0;6,true" "6,0;2,true" "7,0;3,false" "8,0;1,false" "0,1;1,false" "1,1;3,true" "2,1;5,false" "3,1;4,false" "4,1;7,true" "5,1;2,false" "6,1;8,false" "7,1;9,true" "8,1;6,true" "0,2;2,false" "1,2;6,true" "2,2;8,false" "3,2;9,false" "4,2;1,true" "5,2;3,false" "6,2;7,false" "7,2;4,false" "8,2;5,true" "0,3;5,true" "1,3;1,false" "2,3;3,true" "3,3;7,false" "4,3;6,false" "5,3;4,false" "6,3;9,false" "7,3;8,true" "8,3;2,false" "0,4;8,false" "1,4;9,true" 
"2,4;7,false" "3,4;1,true" "4,4;2,true" "5,4;5,true" "6,4;3,false" "7,4;6,true" "8,4;4,false" "0,5;6,false" "1,5;4,true" "2,5;2,false" "3,5;3,false" "4,5;9,false" "5,5;8,false" "6,5;1,true" "7,5;5,false" "8,5;7,true" "0,6;7,true" "1,6;5,false" "2,6;4,false" "3,6;2,false" "4,6;3,true" "5,6;9,false" "6,6;6,false" "7,6;1,true" "8,6;8,false" "0,7;9,true" "1,7;8,true" "2,7;1,false" "3,7;6,false" "4,7;4,true" "5,7;7,false" "6,7;5,false" "7,7;2,true" "8,7;3,false" "0,8;3,false" "1,8;2,false" "2,8;6,true" "3,8;8,true" "4,8;5,true" "5,8;1,false" "6,8;4,true" "7,8;7,false" "8,8;9,false"

Use o menu para:

-Iniciar novo jogo
-Inserir ou remover números
-Visualizar tabuleiro
-Verificar status
-Salvar e carregar progresso

📌 Alterações de cores implementadas

Tabuleiro (showCurrentGame):

. em branco → cor branca (\u001B[37m.\u001B[0m)
Números fixos (do jogo original) → cor azul (\u001B[34m)
Números inseridos pelo jogador → cor verde (\u001B[32m)

Mensagens de status:

Erros → cor vermelha (\u001B[31m)
Sucesso → cor verde (\u001B[32m)
Avisos → cor amarela (\u001B[33m)

✅ Exemplo de saída no console
Quando você visualiza o tabuleiro (opção 4), ele aparece assim:

Seu jogo se encontra da seguinte forma
.  .  .  .  .  .  .  .  .
.  .  .  .  .  .  .  .  .
.  .  .  .  .  .  .  .  .
.  .  .  .  .  .  .  .  .
.  .  .  .  .  .  .  .  .
.  .  .  .  .  .  .  .  .
.  .  .  .  .  .  .  .  .
.  .  .  .  .  .  .  .  .
.  .  .  .  .  .  .  .  .

Os pontos aparecem em branco.

Se você colocar um número, ele aparece em verde.

Os números fixos do Sudoku aparecem em azul.