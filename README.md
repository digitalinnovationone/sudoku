# 🚀 Execução Local

Se preferir rodar localmente (Windows, Linux ou Mac):

### Pré-requisitos
- Java 17 instalado
- Git instalado
- VS Code (opcional, mas recomendado)

### Compilação manual
No terminal, execute:
```bash
javac -d bin src/br/com/dio/Main.java src/br/com/dio/UIMain.java src/br/com/dio/model/*.java src/br/com/dio/util/*.java src/br/com/dio/service/*.java src/br/com/dio/ui/custom/screen/MainScreen.java src/br/com/dio/ui/custom/panel/*.java src/br/com/dio/ui/custom/frame/*.java src/br/com/dio/ui/custom/input/*.java src/br/com/dio/ui/custom/button/*.java
```

### Execução via terminal
```bash
java -cp bin br.com.dio.Main [argumentos]
```

### Execução via interface gráfica
```bash
java -cp bin br.com.dio.UIMain [argumentos]
```

### Usando o launch.json (VS Code)
Basta abrir o VS Code, ir em "Executar e Depurar" e escolher:
- Sudoku Terminal (Main)
- Sudoku Interface Gráfica (UIMain)

Os argumentos já estão configurados para o desafio DIO.

### Argumentos para tabuleiro customizado
Você pode passar os argumentos do desafio diretamente no launch.json ou no terminal, por exemplo:
```
0,0;4,false 1,0;7,false 2,0;9,true ... (etc)
```
Esses argumentos preenchem o tabuleiro inicial conforme o desafio.
# Jogo de Sudoku Funcional em Java

![Java](https://img.shields.io/badge/Java-17-blue)
![Swing](https://img.shields.io/badge/UI-Java%20Swing-orange)
![Status](https://img.shields.io/badge/Status-Concluído-brightgreen)

Este projeto é uma implementação de um jogo de Sudoku funcional, desenvolvido como parte do bootcamp de Java da [Digital Innovation One (DIO)](https://dio.me/). O repositório contém duas versões da aplicação: uma totalmente funcional via terminal e outra com uma interface gráfica interativa construída com Java Swing.

O desenvolvimento foi realizado em um ambiente na nuvem utilizando **GitHub Codespaces**, com todas as configurações necessárias para a execução de aplicações gráficas.

---



## ✨ Funcionalidades Implementadas

* **Duas Interfaces:** Jogue via terminal ou através de uma interface gráfica amigável.
* **Validação de Jogadas em Tempo Real (UI):** A interface gráfica impede jogadas inválidas (números repetidos em linhas, colunas ou blocos 3x3) e fornece feedback instantâneo ao usuário através de pop-ups.
* **Lógica Robusta:** O estado do jogo e as regras são gerenciados de forma organizada na camada de modelo e serviço, desacoplando a lógica da apresentação.
* **Ambiente de Desenvolvimento na Nuvem:** O projeto foi configurado para ser totalmente funcional no GitHub Codespaces, incluindo o suporte para aplicações gráficas (GUI forwarding via VNC) através do arquivo `devcontainer.json`.
* **(Opcional) Resolvedor Automático:** Implementação de um algoritmo de backtracking para encontrar a solução do tabuleiro. *(Remova esta linha se não tiver implementado o solver)*

---

## 🛠️ Tecnologias Utilizadas

* **Java 17**
* **Java Swing** (para a UI)
* **Git & GitHub** (para versionamento)
* **GitHub Codespaces** (como ambiente de desenvolvimento)
* **Dev Containers** (para configuração do ambiente)

---

## ⚙️ Como Executar o Projeto

Este projeto foi desenvolvido e configurado para rodar primariamente no GitHub Codespaces.

1.  **Fork:** Faça um fork deste repositório.
2.  **Codespaces:** Na página do seu fork, clique em `< > Code` -> `Codespaces` -> `Create codespace`.
3.  **Ambiente Gráfico:** A primeira vez que você criar o codespace, ele pode pedir para reconstruir o contêiner para adicionar as funcionalidades gráficas. Confirme.

#### Para a Versão de Terminal
1.  Garanta que você está na branch `main`: `git checkout main`
2.  Configure o `launch.json` para usar a `mainClass` **`br.com.dio.Main`**.
3.  Execute pelo painel "Run and Debug".

#### Para a Versão com Interface Gráfica
1.  Garanta que você está na branch `ui`: `git checkout ui`
2.  Configure o `launch.json` para usar a `mainClass` **`br.com.dio.UIMain`**.
3.  Execute pelo painel "Run and Debug".
4.  Vá para a aba "Portas" (Ports), encontre a porta do "Desktop VNC" (porta `6080`) e clique no ícone de globo (🌐).
5.  A senha para a área de trabalho virtual é `vscode`.

---

## 👨‍💻 Autor

* **Leandro da Silva Stampini**
* **LinkedIn:** [https://www.linkedin.com/in/leandro-da-silva-stampini-07b04aa3/](https://www.linkedin.com/in/leandro-da-silva-stampini-07b04aa3/)
* **Perfil DIO:** [https://www.dio.me/users/leandro_stampini](https://www.dio.me/users/leandro_stampini)

## 🙏 Agradecimentos

Agradeço à [Digital Innovation One](https://dio.me/) pela oportunidade e pelo desafio proposto.
