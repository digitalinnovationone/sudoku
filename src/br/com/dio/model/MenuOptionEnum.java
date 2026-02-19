package br.com.dio.model;

import java.util.Arrays;
import java.util.Optional;

public enum MenuOptionEnum {
    INICIAR(1, "Iniciar um novo Jogo"),
    ADICIONAR(2, "Colocar um novo número"),
    REMOVER(3, "Remover um número"),
    VISUALIZAR(4, "Visualizar jogo atual"),
    STATUS(5, "Verificar status do jogo"),
    LIMPAR(6, "Limpar jogo"),
    FINALIZAR(7, "Finalizar jogo"),
    SAIR(8, "Sair");

    private final int id;
    private final String label;

    MenuOptionEnum(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public static Optional<MenuOptionEnum> fromId(int id) {
        return Arrays.stream(values())
                .filter(opt -> opt.id == id)
                .findFirst();
    }

    @Override
    public String toString() {
        return id + " - " + label;
    }
}