<!-- 
  Tags: Dev
  Label: 😂 Sudoku
  Description: Sudoku
  path_hook: hookfigma.hook7
-->

# Sudoku Game - Melhorias de Configuração

## 📋 Sobre o Projeto

Jogo de Sudoku em linha de comando com sistema flexível de configuração inicial. O projeto permite carregar configurações de jogo tanto via parâmetros de linha de comando quanto através de arquivos externos, proporcionando maior flexibilidade na criação de puzzles personalizados.

## 🏗️ Estrutura do Projeto

```
src/
├── br/com/dio/
│   ├── Main.java                    # Classe principal com menu interativo
│   ├── model/
│   │   ├── Board.java              # Lógica do tabuleiro
│   │   └── Space.java              # Representação de cada célula
│   └── util/
│       └── BoardTemplate.java      # Template visual do tabuleiro
```

## ⭐ Principais Melhorias Implementadas

### 1. 🔧 **Configuração Flexível de Puzzles**

**Funcionalidade Principal**: Sistema híbrido para carregar configurações iniciais do jogo.

#### **Método 1: Parâmetros de Linha de Comando**
```bash
java -jar sudoku.jar "0,0;5,true" "0,1;3,true" "1,2;7,false"
```

#### **Método 2: Arquivo de Configuração**
```bash
java -jar sudoku.jar parans.txt
```

**Formato do arquivo `parans.txt`:**
```
0,0;5,true
0,1;3,true
1,2;7,false
2,3;9,true
```

### 2. 📁 **Sistema de Carregamento de Arquivos**

```java
private static Map<String, String> loadGameConfig(String[] args) {
    if (args.length == 1) {
        String filePath = args[0];
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines.collect(Collectors.toMap(
                k -> k.split(";")[0],
                v -> v.split(";")[1]
            ));
        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo: " + e.getMessage());
            return Map.of();
        }
    }
    // Fallback para parâmetros tradicionais
    return Stream.of(args).collect(/*...*/)
}
```

### 3. 🎯 **Benefícios das Melhorias**

#### **Para Desenvolvedores:**
- **Flexibilidade**: Múltiplas formas de configurar puzzles
- **Manutenibilidade**: Separação clara entre configuração e lógica
- **Testabilidade**: Fácil criação de cenários de teste via arquivos

#### **Para Usuários:**
- **Facilidade**: Criação de puzzles personalizados sem recompilar
- **Organização**: Arquivos de configuração reutilizáveis
- **Compartilhamento**: Puzzles podem ser facilmente compartilhados

#### **Para o Sistema:**
- **Robustez**: Tratamento de erros de I/O
- **Performance**: Uso eficiente de Streams e NIO.2
- **Compatibilidade**: Mantém retrocompatibilidade com método original

## 🚀 Como Usar

### **Opção 1: Arquivo de Configuração (Recomendado)**
```bash
# Criar arquivo parans.txt
echo "0,0;5,true" > parans.txt
echo "1,1;3,true" >> parans.txt
echo "2,2;7,true" >> parans.txt

# Executar o jogo
java -jar sudoku.jar parans.txt
```

### **Opção 2: Parâmetros Diretos**
```bash
java -jar sudoku.jar "0,0;5,true" "1,1;3,true" "2,2;7,true"
```

### **Opção 3: Jogo Vazio**
```bash
java -jar sudoku.jar
```

## 📊 Menu Interativo

```
Selecione uma das opções a seguir
1 - Iniciar um novo Jogo
2 - Colocar um novo número
3 - Remover um número
4 - Visualizar jogo atual
5 - Verificar status do jogo
6 - Limpar jogo
7 - Finalizar jogo
8 - Sair
```

## 🔍 Formato de Configuração

**Sintaxe**: `linha,coluna;valor,fixo`

- **`linha,coluna`**: Coordenadas da célula (0-8)
- **`valor`**: Número inicial (1-9, ou 0 para vazio)
- **`fixo`**: `true` para células não editáveis, `false` para editáveis

**Exemplo:**
```
0,0;5,true    # Célula (0,0) com valor 5, não editável
1,2;0,false   # Célula (1,2) vazia, editável
3,4;9,true    # Célula (3,4) com valor 9, não editável
```

## 🎮 Funcionalidades do Jogo

- ✅ **Validação automática** de regras do Sudoku
- ✅ **Detecção de erros** em tempo real
- ✅ **Células fixas** não editáveis
- ✅ **Sistema de reset** para recomeçar
- ✅ **Verificação de conclusão** automática
- ✅ **Interface visual** clara do tabuleiro

## 🛠️ Tecnologias Utilizadas

- **Java 11+** - Recursos modernos de Streams e NIO.2
- **Files API** - Leitura eficiente de arquivos
- **Collectors** - Processamento funcional de dados
- **Scanner** - Interface de linha de comando

## 💡 Casos de Uso

### **1. Criação de Puzzles Personalizados**
```bash
# Puzzle fácil
java -jar sudoku.jar easy_parans.txt

# Puzzle difícil
java -jar sudoku.jar hard_parans.txt
```

### **2. Testes Automatizados**
```bash
# Puzzle quase completo para testar validação
java -jar sudoku.jar test_validation.config
```

### **3. Desenvolvimento Iterativo**
```bash
# Modificar arquivo de configuração
# Recarregar jogo instantaneamente
```

## 🔄 Exemplo Prático

**Arquivo: `exemplo.config`**
```
0,0;5,true
0,1;3,true
0,4;7,true
1,0;6,true
1,3;1,true
1,4;9,true
1,5;5,true
```

**Execução:**
```bash
java -jar sudoku.jar exemplo.config
```

**Resultado:** Tabuleiro iniciado com números pré-definidos, pronto para ser resolvido!

## 🎯 Vantagens da Implementação

| Aspecto | Antes | Depois |
|---------|-------|--------|
| **Configuração** | Apenas parâmetros CLI | CLI + Arquivos |
| **Flexibilidade** | Limitada | Alta |
| **Reutilização** | Difícil | Fácil |
| **Manutenção** | Complexa | Simples |
| **Teste** | Manual | Automatizável |

---

## 👨‍💻 Modificações e Melhorias

**Autor das Melhorias**: Fabiano
**Data**: 28/08/2025 
**Versão**: v2.0 - Enhanced Configuration System

### Contribuições Realizadas:
- ✅ Sistema de carregamento via arquivos de configuração
- ✅ Compatibilidade com múltiplos formatos de entrada
- ✅ Tratamento robusto de erros de I/O
- ✅ Melhoria na experiência do usuário

**Repositório Original**: [digitalinnovationone/sudoku]  
**Modificações por**: [Fabiano Rocha|fabiuniz/sudoku-dio] - [28/08/2025]

*Esta melhoria transforma o jogo em uma plataforma flexível para criação e resolução de puzzles Sudoku personalizados, mantendo a simplicidade de uso e adicionando poderosas capacidades de configuração.*