package br.com.dio;

import br.com.dio.model.MenuOptionEnum;
import java.util.stream.Stream;
import static java.util.stream.Collectors.*;
import static br.com.dio.util.Constants.*;
import static br.com.dio.controller.SudokuController.*;

public class Main {
    public static void main(String[] args) {
        final var positions = Stream.of(args)
                .collect(toMap(
                        k -> k.split(";")[0],
                        v -> v.split(";")[1]
                ));

        var continueGame = true;

        while (continueGame) {
            System.out.println("\n" + SELECIONE_OPCAO);
            Stream.of(MenuOptionEnum.values()).forEach(System.out::println);
            continueGame = handleMenuOption(positions);
        }
    }
}