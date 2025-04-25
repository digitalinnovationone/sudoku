package br.com.dio;

import br.com.dio.model.CliMenu;
import static java.util.stream.Collectors.toMap;

import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        final var positions = Stream.of(args)
                .collect(toMap(
                        k -> k.split(";")[0],
                        v -> v.split(";")[1]
                ));

        new CliMenu(positions);
    }


}
