package br.com.dio.model;

public enum MenuOption {
    START_GAME(1, "Iniciar um novo Jogo"),
    INPUT_NUMBER(2, "Colocar um novo número"),
    REMOVE_NUMBER(3, "Remover um número"),
    SHOW_CURRENT_GAME(4, "Visualizar jogo atual"),
    SHOW_GAME_STATUS(5, "Verificar status do jogo"),
    CLEAR_GAME(6, "Limpar jogo"),
    FINISH_GAME(7, "Finalizar jogo"),
    EXIT(8, "Sair");

    private final int option;
    private final String description;

    MenuOption(int option, String description) {
        this.option = option;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getOption() {
        return option;
    }

    public static MenuOption fromInt(int value) {
        for (MenuOption menuOption : values()) {
            if (menuOption.option == value) {
                return menuOption;
            }
        }
        return null; // Ou lançar uma exceção
    }
}