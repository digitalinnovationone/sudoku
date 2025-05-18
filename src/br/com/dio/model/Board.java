package br.com.dio.model;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import static br.com.dio.model.GameStatusEnum.COMPLETE;
import static br.com.dio.model.GameStatusEnum.INCOMPLETE;
import static br.com.dio.model.GameStatusEnum.NON_STARTED;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Board {

    private static final int SIZE = 9; // Definição do tamanho do tabuleiro
    private final List<List<Space>> spaces;

    public Board(final List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    public List<List<Space>> getSpaces() {
        return spaces;
    }

    // Método para iniciar o jogo com um menu interativo
    public void startGame() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n🧩 Bem-vindo ao Sudoku!");
            System.out.println("1 - Jogar");
            System.out.println("2 - Reiniciar");
            System.out.println("3 - Sair");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();

            if (choice == 1) {
                playGame(scanner); // Certifique-se de que este método exista!
            } else if (choice == 2) {
                reset();
                System.out.println("🔄 Jogo reiniciado!");
            } else if (choice == 3) {
                System.out.println("👋 Obrigado por jogar! Até a próxima.");
                break;
            } else {
                System.out.println("❌ Opção inválida! Escolha novamente.");
            }
        }

        scanner.close();
    }

    public GameStatusEnum getStatus() {
        if (spaces.stream().flatMap(Collection::stream).noneMatch(s -> !s.isFixed() && nonNull(s.getActual()))) {
            return NON_STARTED;
        }
        return spaces.stream().flatMap(Collection::stream).anyMatch(s -> isNull(s.getActual())) ? INCOMPLETE : COMPLETE;
    }

    public boolean hasErrors() {
        if (getStatus() == NON_STARTED) {
            return false;
        }
        return spaces.stream().flatMap(Collection::stream)
                .anyMatch(s -> nonNull(s.getActual()) && !s.getActual().equals(s.getExpected()));
    }

    public boolean changeValue(final int row, final int col, final int value) {
        if (!isValidMove(row, col, value)) {
            System.out.println("❌ Jogada inválida! Tente novamente.");
            return false;
        }

        var space = spaces.get(row).get(col);
        if (space.isFixed()) {
            System.out.println("🚫 Este espaço contém um número fixo e não pode ser alterado!");
            return false;
        }

        space.setActual(value);
        System.out.println("✅ Número inserido com sucesso!");
        return true;
    }

    public boolean clearValue(final int row, final int col) {
        var space = spaces.get(row).get(col);
        if (space.isFixed()) {
            return false;
        }

        space.clearSpace();
        return true;
    }

    public void reset() {
        spaces.forEach(c -> c.forEach(Space::clearSpace));
    }

    public boolean gameIsFinished() {
        boolean finished = !hasErrors() && getStatus().equals(COMPLETE);
    
        if (finished) {
            System.out.println("\n🎉 Parabéns! Você completou o Sudoku com sucesso! 🎉\n");
        }
    
        return finished;
    }

    // Método para exibir o tabuleiro formatado no terminal
    public void printBoard() {
        System.out.println("  0 1 2 3 4 5 6 7 8");
        System.out.println(" +------------------+");
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + "| ");
            for (int j = 0; j < SIZE; j++) {
                Space space = spaces.get(i).get(j); 
                System.out.print((space.getActual() == null ? "." : space.getActual()) + " ");
            }
            System.out.println("|");
        }
        System.out.println(" +------------------+");
    }

    // Método para validar a jogada do usuário
    public boolean isValidMove(int row, int col, int value) {
        // Verifica se a célula já contém um valor fixo
        Space space = spaces.get(row).get(col);
        if (space.isFixed()) {
            System.out.println("Este espaço não pode ser alterado!");
            return false;
        }

        // Verifica repetição na mesma linha
        for (int i = 0; i < SIZE; i++) {
            if (spaces.get(row).get(i).getActual() != null && spaces.get(row).get(i).getActual() == value) {
                System.out.println("Número já existe na linha!");
                return false;
            }
        }

        // Verifica repetição na mesma coluna
        for (int i = 0; i < SIZE; i++) {
            if (spaces.get(i).get(col).getActual() != null && spaces.get(i).get(col).getActual() == value) {
                System.out.println("Número já existe na coluna!");
                return false;
            }
        }

        // Verifica repetição no bloco 3x3
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Space currentSpace = spaces.get(startRow + i).get(startCol + j);
                if (currentSpace.getActual() != null && currentSpace.getActual() == value) {
                    System.out.println("Número já existe no bloco 3x3!");
                    return false;
                }
            }
        }

        return true; // O movimento é válido
    }
}